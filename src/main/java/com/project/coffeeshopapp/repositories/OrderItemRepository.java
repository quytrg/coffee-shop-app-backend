package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.customrepositories.OrderItemRepositoryCustom;
import com.project.coffeeshopapp.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemRepositoryCustom {
}
