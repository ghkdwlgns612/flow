package com.interpark.ncl.service;

import com.interpark.ncl.domain.dto.ProductRecommendResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductRecommendResponseDto> getRecommendProducts(String keywords, String userNo);
}
