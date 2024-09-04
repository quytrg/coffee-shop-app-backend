package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumberAndIsActiveAndDeleted(String phoneNumber, Boolean isActive, Boolean deleted);
    Optional<User> findByIdAndIsActiveAndDeleted(Long id, Boolean isActive, Boolean deleted);
}
