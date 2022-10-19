package com.stussy.stussyclone20220930syw.service.admin;


import com.stussy.stussyclone20220930syw.dto.admin.CategoryResponseDto;
import com.stussy.stussyclone20220930syw.dto.admin.ProductRegisterReqDTO;

import java.util.List;

public interface ProductManagementService {
    public List<CategoryResponseDto> getCategoryList() throws Exception;
    public void registerMst(ProductRegisterReqDTO productRegisterReqDTO) throws Exception;

}
