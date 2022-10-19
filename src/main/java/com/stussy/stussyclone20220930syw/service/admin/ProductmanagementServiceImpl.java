package com.stussy.stussyclone20220930syw.service.admin;

import com.stussy.stussyclone20220930syw.dto.admin.CategoryResponseDto;
import com.stussy.stussyclone20220930syw.dto.admin.ProductRegisterReqDTO;
import com.stussy.stussyclone20220930syw.repository.admin.ProductManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductmanagementServiceImpl implements ProductManagementService{

    private final ProductManagementRepository productManagementRepository;

    @Override
    public List<CategoryResponseDto> getCategoryList() throws Exception {
        return null;
    }
    @Override
    public void registerMst(ProductRegisterReqDTO productRegisterReqDTO) throws Exception {

    }
}
