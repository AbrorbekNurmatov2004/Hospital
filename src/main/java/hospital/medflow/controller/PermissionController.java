package hospital.medflow.controller;

import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.permission.PermissionCreateDto;
import hospital.medflow.dto.permission.PermissionDto;
import hospital.medflow.dto.permission.PermissionUpdateDto;
import hospital.medflow.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static hospital.medflow.utils.ApiPath.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + VERSION + "/permissions")
public class PermissionController {

    private final PermissionService service;

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('permission:read')")
    @GetMapping
    public ResponseEntity<DataList<List<PermissionDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search
    ) {
        return ResponseEntity.ok(service.getAll(
                BaseCriteria.builder()
                        .page(page)
                        .search(search)
                        .size(size)
                        .build()
        ));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('permission:read')")
    @GetMapping("/{id}")
    public ResponseEntity<PermissionDto> get(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('permission:create')")
    @PostMapping
    public ResponseEntity<PermissionDto> create(@Valid @RequestBody PermissionCreateDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('permission:update')")
    @PutMapping("/{id}")
    public ResponseEntity<PermissionDto> update(@PathVariable String id, @Valid @RequestBody PermissionUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('permission:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
