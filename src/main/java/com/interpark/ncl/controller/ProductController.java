package com.interpark.ncl.controller;

import com.interpark.ncl.domain.dto.ProductRecommendResponseDto;
import com.interpark.ncl.domain.dto.ResultResponseDto;
import com.interpark.ncl.service.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product/recommended")
    public ResultResponseDto<List<ProductRecommendResponseDto>> recommendProduct(@RequestParam @NonNull String keywords, @RequestParam String userNo){
        List<ProductRecommendResponseDto> result = productService.getRecommendProducts(keywords, userNo);
        return new ResultResponseDto(HttpStatus.OK.value() * 10, "추천 완료",result);
    }
}
