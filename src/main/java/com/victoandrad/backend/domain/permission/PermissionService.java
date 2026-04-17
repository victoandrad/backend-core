package com.victoandrad.backend.domain.permission;

import com.victoandrad.backend.domain.permission.dto.request.PermissionCreateRequest;
import com.victoandrad.backend.domain.permission.dto.response.PermissionResponse;
import com.victoandrad.backend.shared.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    // ==============================
    // FIELDS
    // ==============================

    private final PermissionRepository permissionRepository;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    // ==============================
    // METHODS
    // ==============================

    @Transactional
    public List<PermissionResponse> findAll() {
        return permissionRepository
                .findAll()
                .stream()
                .map(PermissionResponse::fromEntity)
                .toList();
    }

    @Transactional
    public PermissionResponse findById(Long id) {
        return permissionRepository
                .findById(id)
                .map(PermissionResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
    }

    @Transactional
    public PermissionResponse insert(PermissionCreateRequest request) {
        return PermissionResponse.fromEntity(
                permissionRepository.save(PermissionCreateRequest.toEntity(request))
        );
    }
}
