package com.victoandrad.backend.domain.role;

import com.victoandrad.backend.domain.role.dto.RoleCreateRequest;
import com.victoandrad.backend.domain.role.dto.RoleUpdateRequest;
import com.victoandrad.backend.domain.role.dto.RoleResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
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