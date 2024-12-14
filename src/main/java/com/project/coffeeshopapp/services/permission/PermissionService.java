package com.project.coffeeshopapp.services.permission;

import com.project.coffeeshopapp.dtos.request.permission.PermissionSearchRequest;
import com.project.coffeeshopapp.dtos.response.permission.PermissionSummaryResponse;
import com.project.coffeeshopapp.mappers.PermissionMapper;
import com.project.coffeeshopapp.models.Permission;
import com.project.coffeeshopapp.repositories.PermissionRepository;
import com.project.coffeeshopapp.specifications.PermissionSpecification;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {
    private final PaginationUtil paginationUtil;
    private final SortUtil sortUtil;
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<PermissionSummaryResponse> getPermissions(PermissionSearchRequest permissionSearchRequest) {
        Sort sort = sortUtil.createSort(
                permissionSearchRequest.getSortBy(),
                permissionSearchRequest.getSortDir()
        );
        Pageable pageable = paginationUtil.createPageable(
                permissionSearchRequest.getPage(),
                permissionSearchRequest.getSize(),
                sort
        );
        Specification<Permission> specification = PermissionSpecification.builder()
                .keyword(permissionSearchRequest.getKeyword())
                .build();
        Page<Permission> permissions = permissionRepository.findAll(specification, pageable);
        return permissions.map(permissionMapper::permissionToPermissionSummaryResponse);
    }
}
