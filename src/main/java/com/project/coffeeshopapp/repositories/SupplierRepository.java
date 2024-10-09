package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
