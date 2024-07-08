package com.rishiraj.ecommerce_store.service;

import com.rishiraj.ecommerce_store.model.Category;
import com.rishiraj.ecommerce_store.model.Product;
import com.rishiraj.ecommerce_store.repositories.CategoryRepository;
import com.rishiraj.ecommerce_store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final RestTemplate restTemplate;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(RestTemplate restTemplate, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.restTemplate = restTemplate;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Value("${fakestoreapi.products.url}")
    private String productsUrl;

    @Override
    public List<Product> findAllProducts() {
        ResponseEntity<Product[]> response = restTemplate.getForEntity(productsUrl, Product[].class);
        if(response == null){
            throw new NullPointerException();
        }
        return Arrays.asList(response.getBody());
    }

    @Override
    public Product createProduct(Product product) {
        Category productCategory = product.getCategory();
        Category category = categoryRepository.findByTitle(productCategory.getTitle());
        if(category == null){
            Category newCategory = new Category();
            newCategory.setTitle(productCategory.getTitle());
            Category responseCategory = categoryRepository.save(newCategory);
            product.setCategory(responseCategory);
        } else {
            product.setCategory(category);
        }
        Product createdProduct = productRepository.save(product);
        return createdProduct;
    }
}
