package com.stussy.stussyclone20220930syw.domain;

import com.stussy.stussyclone20220930syw.dto.admin.ProductMstOptionRespDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionProductMst {
    private int pdt_id;
    private String category;
    private String pdt_name;

    //엔티티객체를 Dto로
    public ProductMstOptionRespDto toDto(){
        return ProductMstOptionRespDto.builder()
                .pdtId(pdt_id)
                .category(category)
                .pdtName(pdt_name)
                .build();
    }
}
