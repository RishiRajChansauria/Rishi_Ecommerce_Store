package com.rishiraj.ecommerce_store.repositories;

import com.rishiraj.ecommerce_store.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByTitle(String title);
}
