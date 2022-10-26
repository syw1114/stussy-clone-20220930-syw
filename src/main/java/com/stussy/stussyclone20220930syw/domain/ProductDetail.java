package com.stussy.stussyclone20220930syw.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class ProductDetail {
    private int id;
    private int pdt_id;
    private int size_id;
    private String pdt_color;
    private int pdt_stock;
}
