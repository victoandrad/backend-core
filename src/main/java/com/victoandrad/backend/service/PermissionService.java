package com.victoandrad.backend.service;

import com.victoandrad.backend.dto.permission.request.PermissionCreateRequest;
import com.victoandrad.backend.dto.permission.response.PermissionResponse;
import com.victoandrad.backend.repository.PermissionRepository;
import com.victoandrad.backend.service.exception.NotFoundException;
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

    public List<PermissionResponse> findAll() {
        return permissionRepository
                .findAll()
                .stream()
                .map(PermissionResponse::fromEntity)
                .toList();
    }

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
