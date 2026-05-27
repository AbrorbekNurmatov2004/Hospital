package hospital.medflow.controller;

import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.role.RoleCreateDto;
import hospital.medflow.dto.role.RoleDto;
import hospital.medflow.dto.role.RoleUpdateDto;
import hospital.medflow.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static hospital.medflow.utils.ApiPath.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + VERSION + "/auth-role")
public class RoleController {

    private final RoleService service;

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('role:read')")
    @GetMapping
    public ResponseEntity<DataList<List<RoleDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search
    ) {
        return ResponseEntity.ok(service.getAll(
                BaseCriteria.builder()
                        .search(search)
                        .page(page)
                        .size(size)
                        .build()
        ));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('role:read')")
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> get(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('role:create')")
    @PostMapping
    public ResponseEntity<RoleDto> create(@Valid @RequestBody RoleCreateDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('role:update')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> update(@PathVariable String id, @Valid @RequestBody RoleUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('role:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
