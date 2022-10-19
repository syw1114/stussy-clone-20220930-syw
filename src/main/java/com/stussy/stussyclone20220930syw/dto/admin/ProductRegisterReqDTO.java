package com.stussy.stussyclone20220930syw.dto.admin;

import com.stussy.stussyclone20220930syw.domain.Product;
import lombok.Data;

import javax.validation.constraints.Min;


@Data
public class ProductRegisterReqDTO {

    private String category;
    private String name;
    @Min(value = 100,message = "가격은 최소 100원입니다")
    private int price;
    private String simpleInfo;
    private String detailInfo;
    private String optionInfo;
    private String managementInfo;
    private String shippingInfo;


    public Product toEntity() {
        return Product.builder()

                .build();
    }
}
