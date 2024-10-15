package com.project.coffeeshopapp.services.supplyorder;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.customexceptions.UnitMismatchException;
import com.project.coffeeshopapp.dtos.request.supplyorder.SupplyOrderCreateRequest;
import com.project.coffeeshopapp.dtos.request.supplyorder.SupplyOrderSearchRequest;
import com.project.coffeeshopapp.dtos.request.supplyorder.SupplyOrderUpdateRequest;
import com.project.coffeeshopapp.dtos.request.supplyorderitem.SupplyOrderItemRequest;
import com.project.coffeeshopapp.dtos.response.supplyorder.SupplyOrderResponse;
import com.project.coffeeshopapp.dtos.response.supplyorder.SupplyOrderSummaryResponse;
import com.project.coffeeshopapp.mappers.SupplyOrderItemMapper;
import com.project.coffeeshopapp.mappers.SupplyOrderMapper;
import com.project.coffeeshopapp.models.Ingredient;
import com.project.coffeeshopapp.models.Supplier;
import com.project.coffeeshopapp.models.SupplyOrder;
import com.project.coffeeshopapp.models.SupplyOrderItem;
import com.project.coffeeshopapp.repositories.IngredientRepository;
import com.project.coffeeshopapp.repositories.SupplierRepository;
import com.project.coffeeshopapp.repositories.SupplyOrderRepository;
import com.project.coffeeshopapp.specifications.SupplyOrderSpecification;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplyOrderService implements ISupplyOrderService {
    private final SupplierRepository supplierRepository;
    private final SupplyOrderRepository supplyOrderRepository;
    private final IngredientRepository ingredientRepository;
    private final SupplyOrderMapper supplyOrderMapper;
    private final SupplyOrderItemMapper supplyOrderItemMapper;
    private final PaginationUtil paginationUtil;
    private final SortUtil sortUtil;

    @Override
    @Transactional
    public SupplyOrderResponse createSupplyOrder(SupplyOrderCreateRequest supplyOrderCreateRequest) {
        // check if supplier ID exists
        Supplier supplier = supplierRepository.findById(supplyOrderCreateRequest.getSupplierId())
                .orElseThrow(() -> new DataNotFoundException("Supplier", "Supplier not found with ID: "
                        + supplyOrderCreateRequest.getSupplierId()));

        // map `SupplyOrderCreateRequest` to `SupplyOrder` entity
        SupplyOrder supplyOrder = supplyOrderMapper.supplyOrderCreateRequestToSupplyOrder(supplyOrderCreateRequest);
        supplyOrder.setSupplier(supplier);

        // map Ingredients by ID for quick access
        Map<Long, Ingredient> ingredientMap = getIngredientMap(
                supplyOrderCreateRequest.getSupplyOrderItems()
        );

        // process SupplyOrderItems
        List<SupplyOrderItem> supplyOrderItems = processSupplyOrderItems(
                supplyOrderCreateRequest.getSupplyOrderItems(),
                ingredientMap,
                supplyOrder
        );
        // set supplyOrderItems
        supplyOrder.setSupplyOrderItems(supplyOrderItems);

        // calculate total amount
        BigDecimal totalAmount = supplyOrderItems.stream()
                .map(SupplyOrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // set totalAmount
        supplyOrder.setTotalAmount(totalAmount);

        // generate and set orderCode
        String orderCode = generateOrderCode();
        supplyOrder.setOrderCode(orderCode);

        // save supply order
        SupplyOrder newSupplyOrder = supplyOrderRepository.save(supplyOrder);
        return supplyOrderMapper.supplyOrderToSupplyOrderResponse(newSupplyOrder);
    }

    @Override
    @Transactional
    public SupplyOrderResponse updateSupplyOrder(Long id, SupplyOrderUpdateRequest supplyOrderUpdateRequest) {
        // check if supply order ID exists
        SupplyOrder supplyOrder = supplyOrderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("SupplyOrder", "SupplyOrder not found with ID: " + id));
        // map SupplyOrderUpdateRequest to SupplyOrder (update fields not null from request)
        supplyOrderMapper.supplyOrderUpdateRequestToSupplyOrder(
                supplyOrderUpdateRequest,
                supplyOrder
        );

        // if supplier is updated
        Optional.ofNullable(supplyOrderUpdateRequest.getSupplierId())
                .ifPresent(supplierId -> {
                    Supplier supplier = supplierRepository.findById(supplierId)
                            .orElseThrow(() -> new DataNotFoundException("Supplier", "Supplier not found with ID: "
                                    + supplierId));
                    supplyOrder.setSupplier(supplier);
                });

        // if supplyOrderItems are updated
        Optional.ofNullable(supplyOrderUpdateRequest.getSupplyOrderItems())
                .ifPresent(supplyOrderItems -> {
                    // detach existing items
                    supplyOrder.getSupplyOrderItems().clear();

                    // map Ingredients by ID for quick access
                    Map<Long, Ingredient> ingredientMap = getIngredientMap(
                            supplyOrderItems
                    );

                    // reprocess SupplyOrderItems
                    List<SupplyOrderItem> updatedSupplyOrderItems = processSupplyOrderItems(
                            supplyOrderItems,
                            ingredientMap,
                            supplyOrder
                    );
                    // set supplyOrderItems
                    supplyOrder.getSupplyOrderItems().addAll(updatedSupplyOrderItems);

                    // recalculate total amount
                    BigDecimal totalAmount = updatedSupplyOrderItems.stream()
                            .map(SupplyOrderItem::getSubtotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    // set totalAmount
                    supplyOrder.setTotalAmount(totalAmount);
                });

        // save supply order
        SupplyOrder updatedSupplyOrder = supplyOrderRepository.save(supplyOrder);
        return supplyOrderMapper.supplyOrderToSupplyOrderResponse(updatedSupplyOrder);
    }

    /**
     * Retrieves a map of Ingredient IDs to Ingredient entities for the given item requests.
     *
     * @param itemRequests List of SupplyOrderItemRequest
     * @return Map of Ingredient ID to Ingredient
     */
    private Map<Long, Ingredient> getIngredientMap(List<SupplyOrderItemRequest> itemRequests) {
        // extract all Ingredient IDs
        List<Long> ingredientIds = itemRequests.stream()
                .map(SupplyOrderItemRequest::getIngredientId)
                .distinct()
                .collect(Collectors.toList());

        // batch fetch Ingredients
        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientIds);
        if (ingredients.size() != ingredientIds.size()) {
            Set<Long> foundIds = ingredients.stream()
                    .map(Ingredient::getId)
                    .collect(Collectors.toSet());
            List<Long> missingIds = ingredientIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
            throw new DataNotFoundException("Ingredient", "Ingredients not found with IDs: " + missingIds);
        }
        // map Ingredients by ID for quick access
        return ingredients.stream()
                .collect(Collectors.toMap(Ingredient::getId, Function.identity()));
    }

    /**
     * Processes a list of SupplyOrderItemRequest and returns a list of SupplyOrderItem entities.
     *
     * @param itemRequests List of SupplyOrderItemRequest
     * @param ingredientMap Map of Ingredient ID to Ingredient
     * @param supplyOrder The parent SupplyOrder entity
     * @return List of SupplyOrderItem
     */
    private List<SupplyOrderItem> processSupplyOrderItems(
            List<SupplyOrderItemRequest> itemRequests,
            Map<Long, Ingredient> ingredientMap,
            SupplyOrder supplyOrder) {

        List<SupplyOrderItem> supplyOrderItems = new ArrayList<>();

        for (SupplyOrderItemRequest itemRequest : itemRequests) {
            // retrieve Ingredient from ingredientMap by ID
            Ingredient ingredient = ingredientMap.get(itemRequest.getIngredientId());

            // verify that the unit of itemRequest matches the default unit of the ingredient
            if (!ingredient.getDefaultUnit().equals(itemRequest.getUnit())) {
                throw new UnitMismatchException("Unit mismatch: Expected unit "
                        + ingredient.getDefaultUnit() + " for ingredientId " + itemRequest.getIngredientId());
            }

            // Map to SupplyOrderItem entity
            SupplyOrderItem supplyOrderItem = supplyOrderItemMapper
                    .supplyOrderItemRequestToSupplyOrderItem(itemRequest);
            supplyOrderItem.setIngredient(ingredient);
            supplyOrderItem.setSupplyOrder(supplyOrder);

            // Calculate Subtotal: (Price * (1 - Discount%)) * Quantity)
            BigDecimal subtotal = getSubtotal(supplyOrderItem, ingredient);
            supplyOrderItem.setSubtotal(subtotal);

            // Add to list
            supplyOrderItems.add(supplyOrderItem);
        }

        return supplyOrderItems;
    }

    private BigDecimal getSubtotal(SupplyOrderItem supplyOrderItem, Ingredient ingredient) {
        BigDecimal price = supplyOrderItem.getPrice();
        BigDecimal quantity = BigDecimal.valueOf(supplyOrderItem.getQuantity());
        BigDecimal discount = BigDecimal
                .valueOf(supplyOrderItem.getDiscount() != null ? supplyOrderItem.getDiscount() : 0)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal priceAfterDiscount = price.multiply(BigDecimal.valueOf(1).subtract(discount));

        BigDecimal subtotal = priceAfterDiscount.multiply(quantity);
        // Ensure subtotal is non-negative
        if (subtotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Subtotal cannot be negative for item with Ingredient ID: "
                    + ingredient.getId());
        }
        return subtotal;
    }

    private String generateOrderCode() {
        SecureRandom random = new SecureRandom();
        int randomNum = random.nextInt(10000); // 4-digit secure random number
        String dateTimePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"));
        return String.format("SO-%s-%04d", dateTimePart, randomNum);
    }

    @Override
    @Transactional(readOnly = true)
    public SupplyOrderResponse getSupplyOrder(Long id) {
        // check if supply order ID exists
        SupplyOrder supplyOrder = supplyOrderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("SupplyOrder", "SupplyOrder not found with ID: " + id));
        return supplyOrderMapper.supplyOrderToSupplyOrderResponse(supplyOrder);
    }

    @Override
    @Transactional
    public void softDeleteSupplyOrder(Long id) {
        // check if supply order ID exists
        SupplyOrder supplyOrder = supplyOrderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("SupplyOrder", "SupplyOrder not found with ID: " + id));
        supplyOrderRepository.softDelete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupplyOrderSummaryResponse> getSupplyOrders(SupplyOrderSearchRequest supplyOrderSearchRequest) {
        Sort sort = sortUtil.createSort(
                supplyOrderSearchRequest.getSortBy(),
                supplyOrderSearchRequest.getSortDir()
        );
        Pageable pageable = paginationUtil.createPageable(
                supplyOrderSearchRequest.getPage(),
                supplyOrderSearchRequest.getSize(),
                sort
        );
        Specification<SupplyOrder> specification = SupplyOrderSpecification.builder()
                .keyword(supplyOrderSearchRequest.getKeyword())
                .totalAmount(
                        supplyOrderSearchRequest.getMinTotalAmount(),
                        supplyOrderSearchRequest.getMaxTotalAmount()
                )
                .status(supplyOrderSearchRequest.getStatus())
                .paymentMethod(supplyOrderSearchRequest.getPaymentMethod())
                .paymentStatus(supplyOrderSearchRequest.getPaymentStatus())
                .expectedDeliveryDate(
                        supplyOrderSearchRequest.getExpectedDeliveryDateFrom(),
                        supplyOrderSearchRequest.getExpectedDeliveryDateTo()
                )
                .actualDeliveryDate(
                        supplyOrderSearchRequest.getActualDeliveryDateFrom(),
                        supplyOrderSearchRequest.getActualDeliveryDateTo()
                )
                .supplierIds(supplyOrderSearchRequest.getSupplierIds())
                .ingredientIds(supplyOrderSearchRequest.getIngredientIds())
                .build();
        Page<SupplyOrder> supplyOrders = supplyOrderRepository.findAll(specification, pageable);
        return supplyOrders.map(supplyOrderMapper::supplyOrderToSupplyOrderSummaryResponse);
    }
}
