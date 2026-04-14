package com.victoandrad.backend.service;

import com.victoandrad.backend.domain.entity.Permission;
import com.victoandrad.backend.domain.entity.Role;
import com.victoandrad.backend.dto.role.request.RoleCreateRequest;
import com.victoandrad.backend.dto.role.request.RoleUpdateRequest;
import com.victoandrad.backend.dto.role.response.RoleResponse;
import com.victoandrad.backend.repository.PermissionRepository;
import com.victoandrad.backend.repository.RoleRepository;
import com.victoandrad.backend.repository.UserRepository;
import com.victoandrad.backend.service.exception.BusinessException;
import com.victoandrad.backend.service.exception.NotFoundException;
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
    public List<RoleResponse> findAll() {
        return roleRepository
                .findAll()
                .stream()
                .map(RoleResponse::fromEntity)
                .toList();
    }

    // ==============================

    @Transactional
    public RoleResponse findById(Long id) {
        return roleRepository
                .findById(id)
                .map(RoleResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Role not found"));
    }

    // ==============================

    @Transactional
    public RoleResponse insert(RoleCreateRequest request) {

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

        return RoleResponse.fromEntity(roleRepository.save(role));
    }

    // ==============================

    @Transactional
    public RoleResponse update(Long id, RoleUpdateRequest request) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        Set<Permission> permissions = new HashSet<>(
                permissionRepository.findAllById(request.permissionIds())
        );

        if (permissions.size() != request.permissionIds().size()) {
            throw new BusinessException("Some permissions not found");
        }

        role.setPermissions(permissions);

        return RoleResponse.fromEntity(role);
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
