package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
