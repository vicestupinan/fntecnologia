package com.vmestupinan.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vmestupinan.products.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameIgnoreCase(String name);
}
