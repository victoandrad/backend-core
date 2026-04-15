package com.victoandrad.backend.domain.permission.dto;

import com.victoandrad.backend.domain.permission.Permission;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PermissionCreateRequest(

        @NotBlank(message = "Name is required")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Name must contain only letters, numbers, and underscores")
        String name

) {
    public static Permission toEntity(PermissionCreateRequest request) {
        return new Permission(
                request.name()
        );
    }
}
