package com.victoandrad.backend.domain.permission;

import com.victoandrad.backend.domain.permission.dto.PermissionCreateRequest;
import com.victoandrad.backend.domain.permission.dto.PermissionResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    // ==============================
    // FIELDS
    // ==============================

    private final PermissionService permissionService;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    // ==============================
    // METHODS
    // ==============================

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> findAll() {
        return ResponseEntity.ok(permissionService.findAll());
    }

    // ==============================

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(permissionService.findById(id));
    }

    // ==============================

    @PostMapping
    public ResponseEntity<PermissionResponse> insert(
            @RequestBody @Valid PermissionCreateRequest request
    ) {
        PermissionResponse response = permissionService.insert(request);

        return ResponseEntity
                .status(201)
                .body(response);
    }
}