package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SupplyUnit {
    KILOGRAM("kg", "kilogram"),       // Kilogram
    GRAM("g", "gram"),                // Gram
    LITER("l", "liter"),              // Lít
    MILLILITER("ml", "milliliter"),   // Mililít

    // Đơn vị đóng gói
    BAG("bag", "bag"),                // Bao
    BOX("box", "box"),                // Thùng/Hộp
    PACK("pack", "pack"),             // Gói
    BOTTLE("btl", "bottle"),          // Chai
    CAN("can", "can"),                // Lon
    CARTON("ctn", "carton"),          // Thùng carton
    CASE("case", "case"),             // Kiện

    // Đơn vị đếm
    PIECE("pc", "piece"),             // Cái/Chiếc
    DOZEN("dz", "dozen"),             // Tá (12 cái)
    UNIT("unit", "unit");             // Đơn vị

    private final String symbol;       // Ký hiệu ngắn gọn
    private final String value;        // Tên đầy đủ
}
