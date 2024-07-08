package com.rishiraj.ecommerce_store.service;

import com.rishiraj.ecommerce_store.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<Product> findAllProducts();

      Product createProduct(Product product);
}
