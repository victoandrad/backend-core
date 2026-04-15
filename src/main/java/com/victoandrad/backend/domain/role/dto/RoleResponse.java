package com.victoandrad.backend.domain.role.dto;

import com.victoandrad.backend.domain.role.Role;
import com.victoandrad.backend.domain.permission.dto.PermissionResponse;

import java.util.Set;
import java.util.stream.Collectors;

public record RoleResponse(
        Long id,
        String name,
        Set<PermissionResponse> permissions
) {
    public static RoleResponse fromEntity(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getPermissions()
                        .stream()
                        .map(PermissionResponse::fromEntity)
                        .collect(Collectors.toSet())
        );
    }
}