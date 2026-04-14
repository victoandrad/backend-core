package com.victoandrad.backend.dto.permission.response;

import com.victoandrad.backend.domain.entity.Permission;

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
