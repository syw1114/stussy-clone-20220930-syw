package com.stussy.stussyclone20220930syw.service;


import com.stussy.stussyclone20220930syw.domain.Product;
import com.stussy.stussyclone20220930syw.dto.CollectionListRespDto;
import com.stussy.stussyclone20220930syw.dto.ProductRespDto;
import com.stussy.stussyclone20220930syw.exception.CustomValidationException;
import com.stussy.stussyclone20220930syw.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<CollectionListRespDto> getProductList(String category, int page) throws Exception {
        List<CollectionListRespDto> productList = new ArrayList<CollectionListRespDto>();
        Map<String,Object> map = new HashMap<String, Object>();

        map.put("category", category);
        map.put("index", (page - 1) * 16);

        productRepository.getProductList(map).forEach(collectionsProduct -> {
            productList.add(collectionsProduct.toDto());
        });

        return productList;
    }

    @Override
    public ProductRespDto getProduct(int pdtId) throws Exception {
        Product product = productRepository.getProduct(pdtId);

        if(product == null) {
            Map<String, String> errormap = new HashMap<String, String>();
            errormap.put("error" , "등록되지 않은 상품입니다.");
            throw new CustomValidationException("GetProduct Error",errormap);
        }

        Map<String, List<Map<String,Object>>> pdtColors = new HashMap<String, List<Map<String,Object>>>();
        List<String> pdtImgs = new ArrayList<String>();
        product.getPdt_dtls().forEach(dtl -> {
            if(!pdtColors.containsKey(dtl.getPdt_color())) {
                pdtColors.put(dtl.getPdt_color(), new ArrayList<Map<String, Object>>());
            }
        });

        product.getPdt_dtls().forEach(dtl -> {
            //맵완성
            Map<String,Object> pdtDtilIdAndSize = new HashMap<String, Object>();
            pdtDtilIdAndSize.put("pdtDtilId", dtl.getId());
            pdtDtilIdAndSize.put("sizeId", dtl.getSize_id());
            pdtDtilIdAndSize.put("sizeName", dtl.getSize_name());
            pdtDtilIdAndSize.put("pdtStock", dtl.getPdt_stock());

            //맵이 여기에 들어감 리스트에 정리돼서
            pdtColors.get(dtl.getPdt_color()).add(pdtDtilIdAndSize);
        });

        product.getPdt_imgs().forEach(img -> {
            pdtImgs.add(img.getSave_name());
        });

            ProductRespDto dto = ProductRespDto.builder()
                    .pdtId(product.getId())
                    .pdtName(product.getPdt_name())
                    .pdtPrice(product.getPdt_price())
                    .pdtSimpleInfo(product.getPdt_simple_info())
                    .pdtDetailInfo(product.getPdt_detail_info())
                    .pdtOptionInfo(product.getPdt_option_info())
                    .pdtManagementInfo(product.getPdt_management_info())
                    .pdtShippingInfo(product.getPdt_shipping_info())
                    .pdtColors(pdtColors)
                    .pdtImgs(pdtImgs)
                    .build();

        return  dto;
    }
}
