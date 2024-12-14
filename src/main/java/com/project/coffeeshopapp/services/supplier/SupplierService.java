package com.project.coffeeshopapp.services.supplier;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.dtos.request.supplier.SupplierCreateRequest;
import com.project.coffeeshopapp.dtos.request.supplier.SupplierSearchRequest;
import com.project.coffeeshopapp.dtos.request.supplier.SupplierUpdateRequest;
import com.project.coffeeshopapp.dtos.response.supplier.SupplierResponse;
import com.project.coffeeshopapp.dtos.response.supplier.SupplierSummaryResponse;
import com.project.coffeeshopapp.mappers.SupplierMapper;
import com.project.coffeeshopapp.models.Supplier;
import com.project.coffeeshopapp.repositories.SupplierRepository;
import com.project.coffeeshopapp.specifications.SupplierSpecification;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SupplierService implements ISupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final PaginationUtil paginationUtil;
    private final SortUtil sortUtil;

    @Override
    @Transactional
    public SupplierResponse createSupplier(SupplierCreateRequest supplierCreateRequest) {
        // map SupplierCreateRequest to Supplier
        Supplier supplier = supplierMapper.supplierCreateRequestToSupplier(supplierCreateRequest);
        // create a supplier
        Supplier savedSupplier = supplierRepository.save(supplier);
        // map a new Supplier to SupplierResponse
        return supplierMapper.supplierToSupplierResponse(savedSupplier);
    }

    @Override
    @Transactional
    public SupplierResponse updateSupplier(Long id, SupplierUpdateRequest supplierUpdateRequest) {
        // check if supplier ID exists
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("supplier", "Supplier not found with id: " + id));
        // map SupplierUpdateRequest to Supplier
        supplierMapper.supplierUpdateRequestToSupplier(supplierUpdateRequest, supplier);
        // save update
        Supplier updatedSupplier = supplierRepository.save(supplier);
        return supplierMapper.supplierToSupplierResponse(updatedSupplier);
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierResponse getSupplier(Long id) {
        // check if supplier ID exists
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("supplier", "Supplier not found with id: " + id));
        return supplierMapper.supplierToSupplierResponse(supplier);
    }

    @Override
    @Transactional
    public void softDeleteSupplier(Long id) {
        // check if supplier ID exists
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("supplier", "Supplier not found with id: " + id));
        supplierRepository.softDelete(id);
    }

    @Override
    public Page<SupplierSummaryResponse> getSuppliers(SupplierSearchRequest supplierSearchRequest) {
        Sort sort = sortUtil.createSort(
                supplierSearchRequest.getSortBy(),
                supplierSearchRequest.getSortDir()
        );
        Pageable pageable = paginationUtil.createPageable(
                supplierSearchRequest.getPage(),
                supplierSearchRequest.getSize(),
                sort
        );
        Specification<Supplier> specification = SupplierSpecification.builder()
                .keyword(supplierSearchRequest.getKeyword())
                .rating(supplierSearchRequest.getMinRating(), supplierSearchRequest.getMaxRating())
                .build();
        Page<Supplier> suppliers = supplierRepository.findAll(specification, pageable);
        return suppliers.map(supplierMapper::supplierToSupplierSummaryResponse);
    }
}
