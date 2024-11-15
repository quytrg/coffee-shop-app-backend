package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @EntityGraph(attributePaths = {"orderItems.productVariant.product"})
    Optional<Order> findWithDetailsById(Long id);
}
