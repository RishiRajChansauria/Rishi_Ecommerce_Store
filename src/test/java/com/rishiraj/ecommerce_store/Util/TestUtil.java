package com.rishiraj.ecommerce_store.Util;

import com.rishiraj.ecommerce_store.model.Product;

public class TestUtil {

    public Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("test product");
        product.setDescription("Test product");
        product.setPrice(100.00);
        product.setCategory(null);
        product.setWeight(10);
        product.setHeight(5);
        return product;
    }
}
