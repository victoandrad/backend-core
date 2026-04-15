package com.victoandrad.backend.domain.permission.dto;

import com.victoandrad.backend.domain.permission.Permission;

public record PermissionResponse(
        Long id,
        String name
) {
    public static PermissionResponse fromEntity(Permission permission) {
        return new PermissionResponse(
                permission.getId(),
                permission.getName()
        );
    }
}
