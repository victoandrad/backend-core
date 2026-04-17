package com.victoandrad.backend.domain.user.dto.response;

import com.victoandrad.backend.domain.user.User;
import com.victoandrad.backend.domain.user.enums.Gender;

import java.time.LocalDate;

public record UserSummaryResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String phone,
        LocalDate birthDate,
        Gender gender,
        String profileImageUrl
) {

    public static UserSummaryResponse fromEntity(User user) {
        return new UserSummaryResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail().getValue(),
                user.getPhone().getValue(),
                user.getBirthDate(),
                user.getGender(),
                user.getProfileImageUrl()
        );
    }
}