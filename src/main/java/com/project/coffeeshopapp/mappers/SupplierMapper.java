package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.supplier.SupplierCreateRequest;
import com.project.coffeeshopapp.dtos.response.supplier.SupplierResponse;
import com.project.coffeeshopapp.models.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier supplierCreateRequestToSupplier(SupplierCreateRequest supplierCreateRequest);
    SupplierResponse supplierToSupplierResponse(Supplier supplier);
}
