package com.interpark.ncl.service;

import com.interpark.ncl.domain.Product;
import com.interpark.ncl.domain.Users;
import com.interpark.ncl.domain.dto.ProductRecommendResponseDto;
import com.interpark.ncl.error.exception.BlankKewordException;
import com.interpark.ncl.error.exception.NotFoundUserException;
import com.interpark.ncl.repository.ProductRepository;
import com.interpark.ncl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductRecommendResponseDto> getRecommendProducts(String keywords, String userNo) {
        HashMap<Product,Integer> map = new HashMap<>();
        List<ProductRecommendResponseDto> result = new ArrayList<>();
        Integer resultCnt = 5;

        if (keywords.isEmpty())
            throw new BlankKewordException();
        List<String> keywordsToStringArr = new LinkedList<>(Arrays.asList(keywords.split(",")));
        Integer keywordsLen = keywordsToStringArr.size();
        //유저 조회 및 상품 조회
        Users user = userRepository.findUserByUserNo(userNo).orElseThrow(NotFoundUserException::new);
        List<Product> products = productRepository.findAllByKeywords(keywordsToStringArr);
        log.info("키워드 일치하는 모든 상품들 : {}개",products.size());

        //키워드와 일치하는 상품 조회 구매순서로 정렬.
        List<Product> sortedList = getSortedByKeywordAndBuyCnt(map, keywordsToStringArr, keywordsLen, products);

        if (sortedList.size() < 5) {   //상품 목록이 5개 미만일 경우 성별 + 연령 으로 추가. 구매순서로 정렬된것 가져오기.
            result = getAddResult(result,sortedList.size(),sortedList);
            resultCnt -= sortedList.size();

            if (user.getGender() == null || user.getAge() == null) { //유저의 나이 혹은 성별정보가 없는경우. 바로 리턴해야함.
                List<Product> productsByUserNull = productRepository.findAllByBuyCnt(PageRequest.of(0, resultCnt));
                log.info("사용자 정보 누락. 바로 리턴", productsByUserNull.size());
                return getAddResult(result, resultCnt, productsByUserNull);
            }

            List<Product> productsByGenderAndAge = productRepository.findAllByGenderAndAge(user.getGender(), (user.getAge() / 10) * 10);
            log.info("성별 + 나이대 : {}개",productsByGenderAndAge.size());
            if (resultCnt - productsByGenderAndAge.size() <= 0) { //성별 + 나이대로 5개를 다 채운경우
                return getAddResult(result, resultCnt, productsByGenderAndAge);
            }

            result = getAddResult(result,productsByGenderAndAge.size(),productsByGenderAndAge); //성별 + 나이대로 5개를 다 못채운 경우
            resultCnt -= productsByGenderAndAge.size();

            if (resultCnt > 0) { //구매순서에 맞게 나머지 채우기.
                List<Product> productsByBuyCnt = productRepository.findAllByBuyCnt(PageRequest.of(0, resultCnt));
                log.info("가장 구매수가 많은 순서 : {}개",productsByBuyCnt.size());
                result = getAddResult(result,productsByBuyCnt.size(),productsByBuyCnt);
            }
        } else {
            return getAddResult(result, 5, sortedList);
        }
        return result;
    }

    private List<ProductRecommendResponseDto> getAddResult(List<ProductRecommendResponseDto> result, Integer resultCnt, List<Product> products) {
        for (int i = 0; i < resultCnt; i++)
            result.add(getProductRecommendResponse(products.get(i)));
        return result;
    }

    private ProductRecommendResponseDto getProductRecommendResponse(Product product) {
        return ProductRecommendResponseDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .recommendAge(product.getRecommendAge())
                .buyCnt(product.getBuyCnt())
                .discountPrice(product.getDiscountPrice())
                .recommendGender(product.getRecommendGender())
                .price(product.getPrice())
                .build();
    }

    private static List<Product> getSortedByKeywordAndBuyCnt(HashMap<Product, Integer> map, List<String> keywordsToStringArr, Integer keywordsLen, List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            Product tmpProduct = products.get(i);
            for (int j = 0; j < keywordsLen; j++) {
                String str = keywordsToStringArr.get(j);
                if (tmpProduct.getProductName().contains(str)) {
                    map.put(tmpProduct, map.getOrDefault(tmpProduct,0) + 1);
                }
            }
        }

        List<Product> sortedList = sortMapByValue(map);
        return sortedList;
    }

    private static List<Product> sortMapByValue(Map<Product, Integer> map) {
        List<Map.Entry<Product, Integer>> entries = new LinkedList<>(map.entrySet());
        Collections.sort(entries, (o1, o2) -> {
            if (o1.getValue() == o2.getValue()) {
                return o2.getKey().getBuyCnt().compareTo(o1.getKey().getBuyCnt());
            }
            return o2.getValue().compareTo(o1.getValue());
        });

        List<Product> result = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : entries) {
            result.add(entry.getKey());
        }
        return result;
    }
}