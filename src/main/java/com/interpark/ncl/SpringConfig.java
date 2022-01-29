package com.interpark.ncl;

import com.interpark.ncl.repository.ProductRepository;
import com.interpark.ncl.repository.UserRepository;
import com.interpark.ncl.service.ProductService;
import com.interpark.ncl.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public SpringConfig(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @Bean
    public ProductService productService() {
        return new ProductServiceImpl(userRepository, productRepository());
    }

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository(entityManager);
    }
}
