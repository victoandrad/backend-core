package com.victoandrad.backend.domain.role.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record RoleUpdateRequest(

        @NotEmpty
        Set<Long> permissionIds

) {}