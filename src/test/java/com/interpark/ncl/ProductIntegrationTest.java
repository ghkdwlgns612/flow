package com.interpark.ncl;

import com.interpark.ncl.domain.dto.ProductRecommendResponseDto;
import com.interpark.ncl.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductIntegrationTest {

    @Autowired
    private ProductService productService;


    @Test
    @DisplayName("키워드와 유저가 주어졌고 상품 리스트를 검색할 때 키워드가 가장 많이 일치하는 순서대로 정렬되었는지 키워드 포함이 갯수가 일치할 때 구매수로 잘 정렬되는지 확인")
    public void GivenKeywordAndUserNo_WhenFindList_ThenSortedByKeywordsAndBuyCnt() {
        String keywords = "못난이,밤고구마,10kg";
        String userNo = "++SSHT7qUTgwqtgo97CSWg==";
        String[] splitKeywords = keywords.split(",");
        boolean isSorted = true;
        boolean isSortedByCnt = true;

        List<ProductRecommendResponseDto> result = productService.getRecommendProducts(keywords, userNo);
        int[] cntKeywords = new int[result.size()];

        for (int i = 0; i < result.size(); i++) {
            String productName = result.get(i).getProductName();
            int cnt = 0;
            for (int j = 0; j < splitKeywords.length; j++) {
                if (productName.contains(splitKeywords[j])) {
                    cnt++;
                }
            }
            cntKeywords[i] = cnt;
        }

        for (int i = 1; i < cntKeywords.length; i++) {
            if (cntKeywords[i-1] < cntKeywords[i])
                isSorted = false;
            if (cntKeywords[i-1] == cntKeywords[i]) {
                if (result.get(i-1).getBuyCnt() < result.get(i).getBuyCnt()) {
                    isSortedByCnt = false;
                }
            }
        }
        Assertions.assertEquals(result.size(),5);
        Assertions.assertEquals(isSorted, true);
        Assertions.assertEquals(isSortedByCnt, true);
    }

    @Test
    @DisplayName("키워드와 유저가 주어졌고 상품 리스트를 검색했는데 일치하는 품목이 5개 이하일 때 성별 및 나이대에 맞는 정보를 잘 가져오는지 확인")
    public void GivenKeywordAndUserNo_WhenFindListLow5Equal_ThenAgeAndGender() {
        String keywords = "못난이";
        String userNo = "++SSHT7qUTgwqtgo97CSWg==";
        String gender = "F";
        String age = "20";
        String[] splitKeywords = keywords.split(",");
        boolean isGenderAndAge = true;

        List<ProductRecommendResponseDto> result = productService.getRecommendProducts(keywords, userNo);
        int[] cntKeywords = new int[result.size()];

        for (int i = 0; i < result.size(); i++) {
            String productName = result.get(i).getProductName();
            int cnt = 0;
            for (int j = 0; j < splitKeywords.length; j++) {
                if (productName.contains(splitKeywords[j])) {
                    cnt++;
                }
            }
            cntKeywords[i] = cnt;
        }

        for (int i = 0; i < cntKeywords.length; i++) {
            if (cntKeywords[i] == 0 &&
                    !(result.get(i).getRecommendAge().equals(age) || result.get(i).getRecommendGender().equals(gender))) {
                isGenderAndAge = false;
            }
        }
        Assertions.assertEquals(isGenderAndAge, true);
        Assertions.assertEquals(result.size(),5);
    }

    @Test
    @DisplayName("키워드와 유저가 주어졌고 상품 리스트를 검색했는데 일치하는 품목이 없으면서 사용자 정보가비어져있을 때 가장 구매순이 높은 순서대로 정렬이 되는지 확인")
    public void GivenKeywordAndUserNo_WhenLow5AndUserNull_ThenSortedBuyCnt() {
        String keywords = "카메라";
        String userNo = "++cZ0P6j6MYoixUOecKZcg==";
        boolean isSortedByCnt = true;
        boolean isSortedById = true;

        List<ProductRecommendResponseDto> result = productService.getRecommendProducts(keywords, userNo);
        for (int i = 1; i < result.size(); i++) {
            if (result.get(i-1).getBuyCnt() < result.get(i).getBuyCnt()) {
                isSortedByCnt = false;
            }
            if (result.get(i-1).getBuyCnt() == result.get(i).getBuyCnt()) {
                if (result.get(i-1).getId() > result.get(i).getId()) {
                    isSortedById = false;
                }
            }
        }
        Assertions.assertEquals(isSortedByCnt, true);
        Assertions.assertEquals(isSortedById, true);
    }
}
