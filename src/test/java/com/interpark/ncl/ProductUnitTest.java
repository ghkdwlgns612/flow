package com.interpark.ncl;


import com.interpark.ncl.repository.UserRepository;
import com.interpark.ncl.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductUnitTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductService productService;

    @Test
    @DisplayName("")
    public void Given_When_Then() {

    }
}
