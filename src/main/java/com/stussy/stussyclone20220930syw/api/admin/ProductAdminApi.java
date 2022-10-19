package com.stussy.stussyclone20220930syw.api.admin;

import com.stussy.stussyclone20220930syw.aop.annotation.LogAspect;
import com.stussy.stussyclone20220930syw.aop.annotation.ValidAspect;
import com.stussy.stussyclone20220930syw.dto.CMRespDto;
import com.stussy.stussyclone20220930syw.dto.admin.ProductRegisterReqDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@RequestMapping("/api/admin")
public class ProductAdminApi {

    @LogAspect
    @ValidAspect
    @PostMapping("product")
    //aop가 알아서 validated 를 해줌
    public ResponseEntity<?> registerProductMst(@Validated @RequestBody ProductRegisterReqDTO productRegisterReqDTO, BindingResult bindingResult) {


        return ResponseEntity.created(null)
                .body(new CMRespDto<>("Register Successfully", null));
    }
    @GetMapping("/product/category")
    public ResponseEntity<?> getCategoryList(){

        return ResponseEntity.ok().body(new CMRespDto<>("Get Successfully", null));
    }
}
