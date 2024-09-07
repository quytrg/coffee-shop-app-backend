package com.project.coffeeshopapp.services.role;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.dtos.request.role.RoleCreateRequest;
import com.project.coffeeshopapp.dtos.request.role.RoleUpdateRequest;
import com.project.coffeeshopapp.dtos.response.role.RoleResponse;
import com.project.coffeeshopapp.mappers.RoleMapper;
import com.project.coffeeshopapp.models.Permission;
import com.project.coffeeshopapp.models.Role;
import com.project.coffeeshopapp.repositories.PermissionRepository;
import com.project.coffeeshopapp.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public RoleResponse createRole(RoleCreateRequest roleCreateRequest) {
        Role newRole = roleMapper.roleCreateRequestToRole(roleCreateRequest);

        List<Permission> permissions = permissionRepository.findAllByIdIn(roleCreateRequest.getPermissionIds());
        newRole.setPermissions(new HashSet<>(permissions));

        Role savedRole = roleRepository.save(newRole);
        return roleMapper.roleToRoleResponse(savedRole);
    }

    @Override
    @Transactional
    public RoleResponse updateRole(Long id, RoleUpdateRequest roleUpdateRequest) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("role" ,"Role not found with id: " + id));

        roleMapper.roleUpdateRequestToRole(roleUpdateRequest, role);

        if (roleUpdateRequest.getPermissionIds() != null) {
            // permissionIds always have number of items >= 1 because of size validation in roleUpdateRequest
            List<Permission> permissions = permissionRepository.findAllByIdIn(roleUpdateRequest.getPermissionIds());
            role.setPermissions(new HashSet<>(permissions));
        }

        Role updatedRole = roleRepository.save(role);
        return roleMapper.roleToRoleResponse(updatedRole);
    }
}