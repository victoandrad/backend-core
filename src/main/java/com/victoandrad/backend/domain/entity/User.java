package com.victoandrad.backend.domain.entity;

import com.victoandrad.backend.domain.enums.Gender;
import com.victoandrad.backend.domain.valueobject.Email;
import com.victoandrad.backend.domain.valueobject.Phone;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true))
    private Email email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "phone", nullable = false))
    private Phone phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    protected User() {
    }

    public User(
            String firstName,
            String lastName,
            String username,
            Email email,
            Phone phone,
            String password,
            LocalDate birthDate,
            Gender gender,
            String profileImageUrl
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
    }
}
