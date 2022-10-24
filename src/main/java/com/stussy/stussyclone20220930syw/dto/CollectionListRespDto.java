package com.stussy.stussyclone20220930syw.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Builder
public class CollectionListRespDto {
    private int productId;
    private String productName;
    private int productPrice;
    private String mainImg;
    private int productTotalCount;


}
