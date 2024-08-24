package com.rishiraj.ecommerce_store.repositories;

import com.rishiraj.ecommerce_store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product product);
    List<Product> findAll();
    Product getById(int productId);
}
