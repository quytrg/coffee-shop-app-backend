package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
