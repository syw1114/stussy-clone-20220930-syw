package com.stussy.stussyclone20220930syw.service.admin;

import com.stussy.stussyclone20220930syw.domain.ProductImg;
import com.stussy.stussyclone20220930syw.dto.admin.*;
import com.stussy.stussyclone20220930syw.exception.CustomInternalServerErrorException;
import com.stussy.stussyclone20220930syw.exception.CustomValidationException;
import com.stussy.stussyclone20220930syw.repository.admin.ProductManagementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductmanagementServiceImpl implements ProductManagementService{

    //ProductmanagementServiceImpl 가 생성될때 productManagementRepository 에 값이 주입됨.


    //표현식을쓰면 yml에 있는걸 가져옴.
    @Value("${file.path}")
    private String filePath;
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

    @Override
    public List<?> getSizeList(int productId) throws Exception {
        List<ProductSizeOptionRespDto> list = new ArrayList<ProductSizeOptionRespDto>();

        productManagementRepository.getSizeList(productId).forEach(size ->{
            list.add(size.toDto());
        });
        return list;
    }

    @Override
    public void checkDuplicatedColor(ProductRegisterDtlReqDto productRegisterDtlReqDto) throws Exception {

        if(productManagementRepository.findProductColor(productRegisterDtlReqDto.toEntity()) > 0){
            Map<String,String> errorMap = new HashMap<String,String>();
            errorMap.put("error", "이미 등록된 색상입니다.");
            throw new CustomValidationException("Duplicated Error", errorMap);
        }
    }

    @Override
    public void registerDtl(ProductRegisterDtlReqDto productRegisterDtlReqDto) throws Exception {
       if(productManagementRepository.saveProductDtl(productRegisterDtlReqDto.toEntity()) == 0) {
           throw new CustomInternalServerErrorException("상품 등록 오류");
       }
    }

    @Override
    public void registerImg(ProductImgReqDto productImgReqDto) throws Exception {
        log.info("pdtId >>> " + productImgReqDto.getPdtId());

        if (productImgReqDto.getFiles() == null) {
            Map<String,String> errorMap = new HashMap<String,String>();
            errorMap.put("error", "이미지를 선택하지 않았습니다.");
            throw new CustomValidationException("ImgError", errorMap);
        }

        List<ProductImg> productImgs = new ArrayList<ProductImg>();

        productImgReqDto.getFiles().forEach(file -> {
            String originName = file.getOriginalFilename();
            //. 을 찾아라 .의 위치에서부터 잘라라 ex) sdfas.png
            String extension = originName.substring(originName.lastIndexOf("."));
            //"-" 을 "" 으로 바꿔라.
            String saveName = UUID.randomUUID().toString().replace("-","") + extension;

            // 해당 폴더의 저파일명까지 경로를 만들어줌.
            Path path = Paths.get(filePath + "product/" + saveName);

            //파일객체를 만들어주고
            File f = new File(filePath + "product");
            //해당경로에 이폴더가 존재하냐
            if(!f.exists()) {
                f.mkdirs();
            }

            //file.getbytes를 path에 써라
            try {
                Files.write(path, file.getBytes());
            } catch (IOException e) {
                throw new CustomInternalServerErrorException(e.getMessage());
            }

            productImgs.add(ProductImg.builder()
                            .pdt_id(productImgReqDto.getPdtId())
                            .origin_name(originName)
                            .save_name(saveName)
                            .build());
        });
        productManagementRepository.saveProductImg(productImgs);
    }
}
