package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.supplier.SupplierCreateRequest;
import com.project.coffeeshopapp.dtos.request.supplier.SupplierUpdateRequest;
import com.project.coffeeshopapp.dtos.response.supplier.SupplierResponse;
import com.project.coffeeshopapp.models.Supplier;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier supplierCreateRequestToSupplier(SupplierCreateRequest supplierCreateRequest);
    SupplierResponse supplierToSupplierResponse(Supplier supplier);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // ignore null value
    void supplierUpdateRequestToSupplier(SupplierUpdateRequest supplierUpdateRequest, @MappingTarget Supplier supplier);
}
