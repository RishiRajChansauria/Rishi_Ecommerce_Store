package com.rishiraj.ecommerce_store.repositories;

import com.rishiraj.ecommerce_store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product product);
}
