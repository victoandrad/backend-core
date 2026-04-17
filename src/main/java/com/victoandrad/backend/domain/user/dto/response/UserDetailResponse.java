package com.victoandrad.backend.domain.user.dto.response;

import com.victoandrad.backend.domain.permission.dto.response.PermissionResponse;
import com.victoandrad.backend.domain.role.dto.response.RoleDetailResponse;
import com.victoandrad.backend.domain.user.User;
import com.victoandrad.backend.domain.user.enums.Gender;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public record UserDetailResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String phone,
        LocalDate birthDate,
        Gender gender,
        String profileImageUrl,
        Set<RoleDetailResponse> roles,
        Set<PermissionResponse> directPermissions,
        Set<PermissionResponse> deniedPermissions,
        Set<PermissionResponse> allowedPermissions
) {

    public static UserDetailResponse fromEntity(User user) {
        return new UserDetailResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail().getValue(),
                user.getPhone().getValue(),
                user.getBirthDate(),
                user.getGender(),
                user.getProfileImageUrl(),
                user.getRoles()
                        .stream()
                        .map(RoleDetailResponse::fromEntity)
                        .collect(Collectors.toSet()),
                user.getDirectPermissions()
                        .stream()
                        .map(PermissionResponse::fromEntity)
                        .collect(Collectors.toSet()),
                user.getDeniedPermissions()
                        .stream()
                        .map(PermissionResponse::fromEntity)
                        .collect(Collectors.toSet()),
                user.getAllowedPermissions()
                        .stream()
                        .map(PermissionResponse::fromEntity)
                        .collect(Collectors.toSet())
        );
    }
}