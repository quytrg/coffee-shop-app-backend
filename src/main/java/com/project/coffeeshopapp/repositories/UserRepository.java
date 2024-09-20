package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.enums.UserStatus;
import com.project.coffeeshopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumberAndStatusAndDeleted(String phoneNumber, UserStatus status, Boolean deleted);
    Optional<User> findByIdAndStatusAndDeleted(Long id, UserStatus status, Boolean deleted);

    @Modifying
    @Query("UPDATE User u SET u.deleted = true WHERE u.id = :id")
    void softDelete(@Param("id") Long id);
}
