package com.stussy.stussyclone20220930syw.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class ProductImg {
    private int id;
    private int pdt_id;
    private String origin_name;
    private String save_name;

}
