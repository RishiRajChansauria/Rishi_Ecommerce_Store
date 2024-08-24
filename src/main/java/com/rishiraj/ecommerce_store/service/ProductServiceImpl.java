package com.rishiraj.ecommerce_store.service;

import com.rishiraj.ecommerce_store.model.Category;
import com.rishiraj.ecommerce_store.model.Product;
import com.rishiraj.ecommerce_store.repositories.CategoryRepository;
import com.rishiraj.ecommerce_store.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceImpl implements ProductService{

    private final RestTemplate restTemplate;
    private final RedisTemplate redisTemplate;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(RestTemplate restTemplate, RedisTemplate redisTemplate, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Value("${fakestoreapi.products.url}")
    private String productsUrl;

    @Override
    public Page<Product> findAllProducts(int pageNumber, int pageSize) {
        Page<Product> allProducts = productRepository.findAll(PageRequest.of(pageNumber,pageSize, Sort.by("price").ascending()));
        if(allProducts == null){
            throw new NullPointerException();
        }
        return allProducts;
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

    @Override
    public Product getProductById(int productId) {
        Product productFromRedis = (Product) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCTS_" + productId);
        if(productFromRedis == null){
            Product productFromDb = productRepository.getById(productId);
            if(productFromDb == null){
                throw new NullPointerException();
            }
            redisTemplate.opsForHash().put("PRODUCTS", "PRODUCTS_"+productId, productFromDb);
            return productFromDb;
        }
        return productFromRedis;
    }
}
