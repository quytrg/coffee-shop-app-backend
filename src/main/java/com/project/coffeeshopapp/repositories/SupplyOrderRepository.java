package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.SupplyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyOrderRepository extends JpaRepository<SupplyOrder, Long> {
}
