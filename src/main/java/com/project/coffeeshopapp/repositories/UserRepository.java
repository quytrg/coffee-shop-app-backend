package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumberAndIsActiveAndDeleted(String phoneNumber, Boolean isActive, Boolean deleted);
    Optional<User> findByIdAndIsActiveAndDeleted(Long id, Boolean isActive, Boolean deleted);

    @Modifying
    @Query("UPDATE User u SET u.deleted = true WHERE u.id = :id")
    void softDelete(@Param("id") Long id);
}
