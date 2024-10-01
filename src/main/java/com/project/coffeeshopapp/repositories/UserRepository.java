package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.enums.UserStatus;
import com.project.coffeeshopapp.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumberAndStatusAndDeleted(String phoneNumber, UserStatus status, Boolean deleted);
    Optional<User> findByIdAndStatusAndDeleted(Long id, UserStatus status, Boolean deleted);

    @Modifying
    @Query("UPDATE User u SET u.deleted = true WHERE u.id = :id")
    void softDelete(@Param("id") Long id);

    @Override
    @EntityGraph(attributePaths = {"role"})
    Page<User> findAll(Specification<User> specification, Pageable pageable);
}
