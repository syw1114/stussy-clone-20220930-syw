package com.stussy.stussyclone20220930syw.api;

import com.stussy.stussyclone20220930syw.aop.annotation.LogAspect;
import com.stussy.stussyclone20220930syw.dto.CMRespDto;
import com.stussy.stussyclone20220930syw.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductService productService;

    @LogAspect
    @GetMapping("/collections/{category}")
    public ResponseEntity<?> getCollection(@PathVariable String category, int page) throws Exception {
        return ResponseEntity.ok(new CMRespDto<>("Successfully",productService.getProductList(category, page)));
    }
}
