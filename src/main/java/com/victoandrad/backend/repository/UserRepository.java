package com.victoandrad.backend.repository;

import com.victoandrad.backend.domain.entity.User;
import com.victoandrad.backend.domain.valueobject.Email;
import com.victoandrad.backend.domain.valueobject.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(Email email);

    Optional<User> findByPhone(Phone phone);

    boolean existsByUsername(String username);

    boolean existsByEmail(Email email);

    boolean existsByRoles_Id(Long roleId);

    @Query("""
        SELECT DISTINCT u FROM User u
        LEFT JOIN FETCH u.roles r
        LEFT JOIN FETCH r.permissions
        LEFT JOIN FETCH u.directPermissions
        LEFT JOIN FETCH u.deniedPermissions
        WHERE u.email = :email
    """)
    Optional<User> findByEmailForAuth(Email email);
}
