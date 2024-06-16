package com.rishiraj.service;

import com.rishiraj.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final RestTemplate restTemplate;

    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${fakestoreapi.products.url}")
    private String productsUrl;

    @Override
    public List<Product> findAllProducts() {
        Product[] allProducts =  restTemplate.getForObject(productsUrl, Product[].class);
        if(allProducts == null){
            throw new NullPointerException();
        }
        return Arrays.asList(allProducts);
    }
}
