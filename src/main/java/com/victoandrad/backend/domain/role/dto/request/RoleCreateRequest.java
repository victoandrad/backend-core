package com.victoandrad.backend.domain.role.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public record RoleCreateRequest(

        @NotBlank
        @Pattern(
                regexp = "^[A-Z]+(_[A-Z]+)*$",
                message = "Role name must be uppercase with underscores (e.g. ADMIN_ROLE)"
        )
        String name,

        @NotEmpty
        Set<Long> permissionIds

) {}