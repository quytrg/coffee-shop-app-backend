package com.project.coffeeshopapp.services.supplier;

import com.project.coffeeshopapp.dtos.request.supplier.SupplierCreateRequest;
import com.project.coffeeshopapp.dtos.request.supplier.SupplierSearchRequest;
import com.project.coffeeshopapp.dtos.request.supplier.SupplierUpdateRequest;
import com.project.coffeeshopapp.dtos.response.supplier.SupplierResponse;
import com.project.coffeeshopapp.dtos.response.supplier.SupplierSummaryResponse;
import org.springframework.data.domain.Page;

public interface ISupplierService {
    SupplierResponse createSupplier(SupplierCreateRequest supplierCreateRequest);
    SupplierResponse updateSupplier(Long id, SupplierUpdateRequest supplierUpdateRequest);
    SupplierResponse getSupplier(Long id);
    void softDeleteSupplier(Long id);
    Page<SupplierSummaryResponse> getSuppliers(SupplierSearchRequest supplierSearchRequest);
}
