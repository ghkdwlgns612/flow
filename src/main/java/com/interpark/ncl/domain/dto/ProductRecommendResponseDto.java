package com.interpark.ncl.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductRecommendResponseDto {
    private Integer id;
    private Integer price;
    private Integer discountPrice;
    private String productName;
    private Integer recommendAge;
    private String recommendGender;
    private Integer buyCnt;

    @Builder
    public ProductRecommendResponseDto(Integer id, Integer price, Integer discountPrice, String productName, Integer recommendAge, String recommendGender, Integer buyCnt) {
        this.id = id;
        this.price = price;
        this.discountPrice = discountPrice;
        this.productName = productName;
        this.recommendAge = recommendAge;
        this.recommendGender = recommendGender;
        this.buyCnt = buyCnt;
    }
}
