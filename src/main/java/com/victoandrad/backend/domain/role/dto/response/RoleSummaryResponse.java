package com.victoandrad.backend.domain.role.dto.response;

import com.victoandrad.backend.domain.role.Role;

public record RoleSummaryResponse(
        Long id,
        String name
) {

    public static RoleSummaryResponse fromEntity(Role role) {
        return new RoleSummaryResponse(
                role.getId(),
                role.getName()
        );
    }
}
