package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
