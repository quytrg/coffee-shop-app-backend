package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SupplyOrderStatus implements BaseEnum{
    PENDING("pending"),      //-- Đơn hàng mới tạo
    CONFIRMED("confirmed"),    //-- Đã xác nhận với nhà cung cấp
    SHIPPING("shipping"),     //-- Đang vận chuyển
    DELIVERED("delivered"),    //-- Đã giao hàng
    COMPLETED("completed"),    //-- Đã hoàn thành (đã kiểm tra và nhập kho)
    CANCELLED("cancelled"),    //-- Đã hủy
    RETURNED("returned");      //-- Đã trả hàng
    private final String value;
}
