package com.victoandrad.backend.dto.role.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record RoleUpdateRequest(

        @NotEmpty
        Set<Long> permissionIds

) {}