package com.stussy.stussyclone20220930syw.api.admin;

import com.stussy.stussyclone20220930syw.aop.annotation.LogAspect;
import com.stussy.stussyclone20220930syw.aop.annotation.ValidAspect;
import com.stussy.stussyclone20220930syw.dto.CMRespDto;
import com.stussy.stussyclone20220930syw.dto.admin.ProductRegisterReqDTO;
import com.stussy.stussyclone20220930syw.service.admin.ProductManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class ProductAdminApi {

    private final ProductManagementService productManagementService;

    @LogAspect
    @ValidAspect
    @PostMapping("product")
    //aop가 알아서 validated 를 해줌
    public ResponseEntity<?> registerProductMst(@Validated @RequestBody ProductRegisterReqDTO productRegisterReqDTO, BindingResult bindingResult) throws Exception {

        String name = productRegisterReqDTO.getName();

        Random random = new Random();

        for(int i =0; i<100; i++) {

            productRegisterReqDTO.setCategory(i / 10 + 1);
            productRegisterReqDTO.setName(name + (i+1));
            productRegisterReqDTO.setPrice((random.nextInt(10) +1 ) * 100000);
            productManagementService.registerMst(productRegisterReqDTO);
        }
        return ResponseEntity.created(null)
                .body(new CMRespDto<>("Register Successfully", true));
    }
    @GetMapping("/product/category")
    public ResponseEntity<?> getCategoryList() throws Exception {

        return ResponseEntity.ok().body(new CMRespDto<>("Get Successfully", productManagementService.getCategoryList()));
    }

    @GetMapping("/option/products/mst")
    public ResponseEntity<?> getProductMstList() throws Exception {
        return ResponseEntity.ok()
                .body(new CMRespDto<>("get Successfully", productManagementService.getProductMstList()));
    }

    @GetMapping("/option/products/size/{productId}")
    public ResponseEntity<?> getSizeList(@PathVariable int productId) throws Exception {
        return ResponseEntity.ok()
                .body(new CMRespDto<>("get Successfully", productManagementService.getSizeList(productId)));
        //프로덕트 아이디에 따라서 사이즈를 다르게 받아옴.
    }

}
