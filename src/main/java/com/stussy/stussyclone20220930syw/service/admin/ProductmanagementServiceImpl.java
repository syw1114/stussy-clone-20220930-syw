package com.stussy.stussyclone20220930syw.service.admin;

import com.stussy.stussyclone20220930syw.dto.admin.CategoryResponseDto;
import com.stussy.stussyclone20220930syw.dto.admin.ProductMstOptionRespDto;
import com.stussy.stussyclone20220930syw.dto.admin.ProductRegisterReqDTO;
import com.stussy.stussyclone20220930syw.exception.CustomInternalServerErrorException;
import com.stussy.stussyclone20220930syw.repository.admin.ProductManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductmanagementServiceImpl implements ProductManagementService{

    private final ProductManagementRepository productManagementRepository;

    @Override
    public List<CategoryResponseDto> getCategoryList() throws Exception {
        List<CategoryResponseDto> categoryResponseDtos = new ArrayList<CategoryResponseDto>();
        productManagementRepository.getCategoryList().forEach(category -> {
            categoryResponseDtos.add(category.toDto());
        });
        return categoryResponseDtos;
    }
    @Override
    public void registerMst(ProductRegisterReqDTO productRegisterReqDTO) throws Exception {
        if(productManagementRepository.saveProductMst(productRegisterReqDTO.toEntity()) == 0){
            throw new CustomInternalServerErrorException("상품 등록 실패");
        }

    }

    @Override
    public List<ProductMstOptionRespDto> getProductMstList() throws Exception {
        List<ProductMstOptionRespDto> list = new ArrayList<>();
        productManagementRepository.getProductMstList().forEach(pdtMst ->{
            list.add(pdtMst.toDto());
        });
        return list;
    }
}
