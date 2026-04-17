package com.victoandrad.backend.domain.role;

import com.victoandrad.backend.domain.permission.Permission;
import com.victoandrad.backend.domain.role.dto.response.RoleSummaryResponse;
import com.victoandrad.backend.domain.user.UserRepository;
import com.victoandrad.backend.domain.role.dto.request.RoleCreateRequest;
import com.victoandrad.backend.domain.role.dto.request.RoleUpdateRequest;
import com.victoandrad.backend.domain.role.dto.response.RoleDetailResponse;
import com.victoandrad.backend.domain.permission.PermissionRepository;
import com.victoandrad.backend.shared.exception.BusinessException;
import com.victoandrad.backend.shared.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    // ==============================
    // FIELDS
    // ==============================

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final UserRepository userRepository;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    @Autowired
    public RoleService(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            UserRepository userRepository
    ) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
    }

    // ==============================
    // METHODS
    // ==============================

    @Transactional
    public List<RoleSummaryResponse> findAll() {
        return roleRepository
                .findAll()
                .stream()
                .map(RoleSummaryResponse::fromEntity)
                .toList();
    }

    // ==============================

    @Transactional
    public RoleDetailResponse findById(Long id) {
        return roleRepository
                .findById(id)
                .map(RoleDetailResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Role not found"));
    }

    // ==============================

    @Transactional
    public RoleDetailResponse insert(RoleCreateRequest request) {

        if (roleRepository.existsByName(request.name())) {
            throw new BusinessException("Role already exists");
        }

        Role role = new Role(request.name());

        Set<Permission> permissions = new HashSet<>(
                permissionRepository
                .findAllById(request.permissionIds())
        );

        if (permissions.size() != request.permissionIds().size()) {
            throw new BusinessException("Some permissions not found");
        }

        permissions.forEach(role::addPermission);

        return RoleDetailResponse.fromEntity(roleRepository.save(role));
    }

    // ==============================

    @Transactional
    public RoleDetailResponse update(Long id, RoleUpdateRequest request) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        Set<Permission> permissions = new HashSet<>(
                permissionRepository.findAllById(request.permissionIds())
        );

        if (permissions.size() != request.permissionIds().size()) {
            throw new BusinessException("Some permissions not found");
        }

        role.setPermissions(permissions);

        return RoleDetailResponse.fromEntity(role);
    }

    // ==============================

    @Transactional
    public void delete(Long id) {

        if (!roleRepository.existsById(id)) {
            throw new NotFoundException("Role not found");
        }

        if (userRepository.existsByRoles_Id(id)) {
            throw new BusinessException("Role is in use");
        }

        roleRepository.deleteById(id);
    }
}
