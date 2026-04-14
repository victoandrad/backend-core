package com.victoandrad.backend.controller.v1;

import com.victoandrad.backend.dto.role.request.RoleCreateRequest;
import com.victoandrad.backend.dto.role.request.RoleUpdateRequest;
import com.victoandrad.backend.dto.role.response.RoleResponse;
import com.victoandrad.backend.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    // ==============================
    // FIELDS
    // ==============================

    private final RoleService roleService;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // ==============================
    // METHODS
    // ==============================

    @GetMapping
    public ResponseEntity<List<RoleResponse>> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    // ==============================

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    // ==============================

    @PostMapping
    public ResponseEntity<RoleResponse> insert(
            @RequestBody @Valid RoleCreateRequest request
    ) {
        RoleResponse response = roleService.insert(request);
        return ResponseEntity.status(201).body(response);
    }

    // ==============================

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid RoleUpdateRequest request
    ) {
        return ResponseEntity.ok(roleService.update(id, request));
    }

    // ==============================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}