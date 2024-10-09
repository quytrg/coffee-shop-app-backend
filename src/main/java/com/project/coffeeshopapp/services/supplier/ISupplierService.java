package com.project.coffeeshopapp.services.supplier;

import com.project.coffeeshopapp.dtos.request.supplier.SupplierCreateRequest;
import com.project.coffeeshopapp.dtos.response.supplier.SupplierResponse;

public interface ISupplierService {
    SupplierResponse createSupplier(SupplierCreateRequest supplierCreateRequest);
}
