package com.interpark.ncl.repository;

import com.interpark.ncl.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final EntityManager em;

    public List<Product> findAllByKeywords(List<String> keywords) {
        String sql = "SELECT p FROM Product p WHERE p.productName LIKE ";
        for (int i=0; i<keywords.size();i++) {
            if (i != 0) {
                sql += " OR p.productName LIKE ";
            }
            sql += "\'%"+ keywords.get(i) +"%\'";
        }
        List<Product> result = em.createQuery(sql, Product.class).getResultList();
        return result;
    }

    public List<Product> findAllByGenderAndAge (String gender, Integer age) {
        String sql = "SELECT p FROM Product p WHERE p.recommendGender = :gender AND p.recommendAge = :age ORDER BY p.buyCnt DESC";
        return em.createQuery(sql, Product.class)
                .setParameter("gender",gender)
                .setParameter("age",age)
                .getResultList();
    }

    public List<Product> findAllByBuyCnt (Pageable pageable) {
        String sql = "SELECT p FROM Product p ORDER BY p.buyCnt DESC, p.id ASC ";
        return em.createQuery(sql, Product.class)
                .setFirstResult(0)
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }
}
