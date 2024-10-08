package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);

    @Modifying
    @Query("UPDATE Role r SET r.deleted = true WHERE r.id = :id")
    void softDelete(@Param("id") Long id);

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions p WHERE r.id = :id")
    Optional<Role> findByIdWithPermissions(@Param("id") Long id);
}
