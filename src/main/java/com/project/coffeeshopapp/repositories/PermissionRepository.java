package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAllByIdIn(Collection<Long> ids);
}
