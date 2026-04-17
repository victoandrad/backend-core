package com.victoandrad.backend.domain.role.dto.response;

import com.victoandrad.backend.domain.role.Role;
import com.victoandrad.backend.domain.permission.dto.response.PermissionResponse;

import java.util.Set;
import java.util.stream.Collectors;

public record RoleDetailResponse(
        Long id,
        String name,
        Set<PermissionResponse> permissions
) {

    public static RoleDetailResponse fromEntity(Role role) {
        return new RoleDetailResponse(
                role.getId(),
                role.getName(),
                role.getPermissions()
                        .stream()
                        .map(PermissionResponse::fromEntity)
                        .collect(Collectors.toSet())
        );
    }
}