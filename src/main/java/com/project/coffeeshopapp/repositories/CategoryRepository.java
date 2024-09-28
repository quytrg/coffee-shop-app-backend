package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Modifying
    @Query("UPDATE Category c SET c.deleted = true WHERE c.id = :id")
    void softDelete(@Param("id") Long id);

    @Override
    @EntityGraph(attributePaths = {"images"})
    Page<Category> findAll(Pageable pageable);
}
