package com.rishiraj.ecommerce_store.controller;

import com.rishiraj.ecommerce_store.model.Product;
import com.rishiraj.ecommerce_store.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public Page<Product> getAllProducts(@RequestParam("PageNumber") int pageNumber, @RequestParam("PageSize") int pageSize){
        return productService.findAllProducts(pageNumber, pageSize);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/product")
    public ResponseEntity<Product> getProductById(@RequestParam("productId") int productId){
        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
