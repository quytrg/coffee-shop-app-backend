package com.project.coffeeshopapp.services.order;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.dtos.request.order.OrderCreateRequest;
import com.project.coffeeshopapp.dtos.request.orderitem.OrderItemCreateRequest;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchDeductRequest;
import com.project.coffeeshopapp.dtos.response.order.OrderResponse;
import com.project.coffeeshopapp.mappers.OrderItemMapper;
import com.project.coffeeshopapp.mappers.OrderMapper;
import com.project.coffeeshopapp.models.*;
import com.project.coffeeshopapp.repositories.OrderRepository;
import com.project.coffeeshopapp.repositories.ProductVariantRepository;
import com.project.coffeeshopapp.services.stockbatch.IStockBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class OrderService implements IOrderService{
    private final OrderMapper orderMapper;
    private final ProductVariantRepository productVariantRepository;
    private final OrderItemMapper orderItemMapper;
    private final IStockBatchService stockBatchService;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest orderCreateRequest) {
        // map OrderCreateRequest to Order
        Order order = orderMapper.orderCreateRequestToOrder(orderCreateRequest);

        // build productVariantMap for quick access
        Map<Long, ProductVariant> productVariantMap = getProductVariantMap(orderCreateRequest.getOrderItems());
        // process OrderItems
        List<OrderItem> orderItems = processOrderItems(
                orderCreateRequest.getOrderItems(),
                productVariantMap,
                order
        );
        // set OrderItems
        order.setOrderItems(orderItems);

        // calculate totalAmount
        BigDecimal totalAmount = orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // set totalAmount
        order.setTotalAmount(totalAmount);

        // calculate returnAmount
        BigDecimal returnAmount = order.getReceivedAmount().subtract(totalAmount);
        // ensure returnAmount is non-negative
        if (returnAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Return amount cannot be negative");
        }
        order.setReturnAmount(returnAmount);

        // generate and set orderCode
        String orderCode = generateOrderCode();
        order.setOrderCode(orderCode);

        // update StockBatch corresponding to each item
        updateStockBatch(order.getOrderItems());

        // retrieves user who created order
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            User user = userDetails.getUser();
            order.setUser(user);
        }

        // Save Order
        Order savedOrder = orderRepository.save(order);
        return orderMapper.orderToOrderResponse(savedOrder);
    }

    /**
     * Retrieves a map of ProductVariant IDs to ProductVariant entities for the given item requests.
     *
     * @param itemRequests List of OrderItemCreateRequest
     * @return Map of ProductVariant ID to ProductVariant
     */
    private Map<Long, ProductVariant> getProductVariantMap(List<OrderItemCreateRequest> itemRequests) {
        // extract all ProductVariant IDs
        List<Long> productVariantIds = itemRequests.stream()
                .map(OrderItemCreateRequest::getProductVariantId)
                .distinct()
                .toList();

        // batch fetch ProductVariants
        List<ProductVariant> productVariants = productVariantRepository.findAllById(productVariantIds);
        if (productVariants.size() != productVariantIds.size()) {
            Set<Long> foundIds = productVariants.stream()
                    .map(ProductVariant::getId)
                    .collect(Collectors.toSet());
            List<Long> missingIds = productVariantIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
            throw new DataNotFoundException("ProductVariant", "ProductVariants not found with IDs: " + missingIds);
        }
        // map ProductVariants by ID for quick access
        return productVariants.stream()
                .collect(Collectors.toMap(ProductVariant::getId, Function.identity()));
    }

    /**
     * Processes a list of OrderItemCreateRequest and returns a list of OrderItem entities.
     *
     * @param itemRequests List of OrderItemCreateRequest
     * @param productVariantMap Map of ProductVariant ID to ProductVariant
     * @param order The parent Order entity
     * @return List of OrderItem
     */
    private List<OrderItem> processOrderItems(
            List<OrderItemCreateRequest> itemRequests,
            Map<Long, ProductVariant> productVariantMap,
            Order order) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemCreateRequest itemRequest : itemRequests) {
            // retrieve ProductVariant from productVariantMap by ID
            ProductVariant productVariant = productVariantMap.get(itemRequest.getProductVariantId());

            // Map to OrderItem entity
            OrderItem orderItem = orderItemMapper
                    .orderItemCreateRequestToOrderItem(itemRequest);
            // map another fields
            // map price from productVariant
            orderItem.setPrice(productVariant.getPrice());

            orderItem.setProductVariant(productVariant);
            orderItem.setOrder(order);

            // Calculate Subtotal: (Price * (1 - Discount%)) * Quantity)
            BigDecimal subtotal = getSubtotal(orderItem, productVariant);
            orderItem.setSubtotal(subtotal);

            // Add to list
            orderItems.add(orderItem);
        }

        return orderItems;
    }

    private BigDecimal getSubtotal(OrderItem orderItem, ProductVariant productVariant) {
        BigDecimal price = productVariant.getPrice();
        BigDecimal quantity = BigDecimal.valueOf(orderItem.getQuantity());
        BigDecimal discount = BigDecimal
                .valueOf(orderItem.getDiscount() != null ? orderItem.getDiscount() : 0)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal priceAfterDiscount = price.multiply(BigDecimal.valueOf(1).subtract(discount));

        BigDecimal subtotal = priceAfterDiscount.multiply(quantity);
        // Ensure subtotal is non-negative
        if (subtotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Subtotal cannot be negative for item with ProductVariant ID: "
                    + productVariant.getId());
        }
        return subtotal;
    }

    private String generateOrderCode() {
        SecureRandom random = new SecureRandom();
        int randomNum = random.nextInt(10000); // 4-digit secure random number
        String dateTimePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"));
        return String.format("PO-%s-%04d", dateTimePart, randomNum);
    }

    private void updateStockBatch(List<OrderItem> orderItems) {
        orderItems.forEach(orderItem -> {
            List<ProductVariantIngredient> productVariantIngredients = orderItem.getProductVariant().getIngredients();
            productVariantIngredients.forEach(productVariantIngredient -> {
                BigDecimal quantityToDeduct = productVariantIngredient.getQuantity().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                StockBatchDeductRequest stockBatchDeductRequest = new StockBatchDeductRequest(
                        productVariantIngredient.getIngredient().getId(),
                        quantityToDeduct
                );
                stockBatchService.deductStockBatch(stockBatchDeductRequest);
            });
        });
    }
}
