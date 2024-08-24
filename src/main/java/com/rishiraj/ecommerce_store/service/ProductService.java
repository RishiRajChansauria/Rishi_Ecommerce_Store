package com.rishiraj.ecommerce_store.service;

import com.rishiraj.ecommerce_store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    Page<Product> findAllProducts(int pageNumber, int pageSize);

      Product createProduct(Product product);

      Product getProductById(int productId);
}
