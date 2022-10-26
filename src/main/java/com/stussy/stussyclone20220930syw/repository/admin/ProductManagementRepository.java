package com.stussy.stussyclone20220930syw.repository.admin;

import com.stussy.stussyclone20220930syw.domain.OptionProductMst;
import com.stussy.stussyclone20220930syw.domain.OptionproductSize;
import com.stussy.stussyclone20220930syw.domain.Product;
import com.stussy.stussyclone20220930syw.domain.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductManagementRepository {
    public List<ProductCategory> getCategoryList() throws Exception;
    public int saveProductMst(Product product) throws Exception;

    public List<OptionProductMst> getProductMstList() throws Exception;

    public List<OptionproductSize> getSizeList(int productId) throws Exception;
}
