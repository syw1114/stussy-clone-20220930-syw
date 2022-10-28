package com.stussy.stussyclone20220930syw.service;

import com.stussy.stussyclone20220930syw.dto.CollectionListRespDto;

import java.util.List;

public interface ProductService {
    public List<CollectionListRespDto> getProductList(String category, int page) throws Exception;
    public Object getProduct(int pdtId) throws Exception;
}
