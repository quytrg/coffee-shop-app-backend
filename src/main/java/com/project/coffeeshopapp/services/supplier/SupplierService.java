package com.project.coffeeshopapp.services.supplier;

import com.project.coffeeshopapp.dtos.request.supplier.SupplierCreateRequest;
import com.project.coffeeshopapp.dtos.response.supplier.SupplierResponse;
import com.project.coffeeshopapp.mappers.SupplierMapper;
import com.project.coffeeshopapp.models.Supplier;
import com.project.coffeeshopapp.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SupplierService implements ISupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

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
}
