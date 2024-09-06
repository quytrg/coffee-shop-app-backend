package com.project.coffeeshopapp.services.role;

import com.project.coffeeshopapp.dtos.request.role.RoleCreateRequest;
import com.project.coffeeshopapp.dtos.response.role.RoleResponse;
import com.project.coffeeshopapp.mappers.RoleMapper;
import com.project.coffeeshopapp.models.Permission;
import com.project.coffeeshopapp.models.Role;
import com.project.coffeeshopapp.repositories.PermissionRepository;
import com.project.coffeeshopapp.repositories.RoleRepository;
import com.project.coffeeshopapp.services.user.UserService;
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

        List<Permission> permissions = permissionRepository.findAllById(roleCreateRequest.getPermissionIds());
        newRole.setPermissions(new HashSet<>(permissions));

        Role savedRole = roleRepository.save(newRole);
        return roleMapper.roleToRoleResponse(savedRole);
    }
}
