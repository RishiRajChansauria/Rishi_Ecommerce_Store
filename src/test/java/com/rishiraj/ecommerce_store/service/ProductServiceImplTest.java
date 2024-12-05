package com.rishiraj.ecommerce_store.service;

import com.rishiraj.ecommerce_store.Util.TestUtil;
import com.rishiraj.ecommerce_store.model.Product;
import com.rishiraj.ecommerce_store.repositories.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RedisTemplate redisTemplate;

    @Mock
    private HashOperations<String, String, Object> hashOperations;

    @InjectMocks
    ProductServiceImpl productService;

    TestUtil testUtil = new TestUtil();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    public void testGetByProductId_ProductInRedis() {
        Product product1 = testUtil.createProduct();

        when(hashOperations.get("PRODUCTS", "PRODUCTS_" + product1.getId())).thenReturn(product1);
        Product result = productService.getProductById(Math.toIntExact(product1.getId()));
        assertEquals(product1, result);
        verify(hashOperations, times(1)).get("PRODUCTS", "PRODUCTS_" + product1.getId());
        verifyNoInteractions(productRepository);
    }


    @Test
    public void testGetByProductId_ProductNotInRedis() {
        Product product1 = testUtil.createProduct();
        when(hashOperations.get("PRODUCTS", "PRODUCTS_" + product1.getId())).thenReturn(null);
        when(productRepository.getById(Math.toIntExact(product1.getId()))).thenReturn(product1);
        Product result = productService.getProductById(Math.toIntExact(product1.getId()));
        assertEquals(product1, result);
        verify(hashOperations, times(1)).get("PRODUCTS", "PRODUCTS_" + product1.getId());
        verify(productRepository, times(1)).getById(Math.toIntExact(product1.getId()));
        verify(hashOperations, times(1)).put("PRODUCTS", "PRODUCTS_" + product1.getId(), product1);
    }

    @Test
    public void testGetByProductId_ProductNotFound() {
        Product product1 = testUtil.createProduct();
        when(hashOperations.get("PRODUCTS", "PRODUCTS_" + product1.getId())).thenReturn(null);
        when(productRepository.getById(Math.toIntExact(product1.getId()))).thenReturn(null);
        assertThrows(NullPointerException.class , () -> productService.getProductById(Math.toIntExact(product1.getId())));
        verify(hashOperations, times(1)).get("PRODUCTS", "PRODUCTS_" + product1.getId());
        verify(productRepository, times(1)).getById(Math.toIntExact(product1.getId()));
        verify(hashOperations, never()).put(anyString(), anyString(), any());
    }

}
