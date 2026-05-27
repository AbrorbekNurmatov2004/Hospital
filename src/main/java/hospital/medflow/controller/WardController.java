package hospital.medflow.controller;

import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.ward.WardCreateDto;
import hospital.medflow.dto.ward.WardDto;
import hospital.medflow.dto.ward.WardUpdateDto;
import hospital.medflow.service.WardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hospital.medflow.utils.ApiPath.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + VERSION + "/wards")
public class WardController {

    private final WardService service;

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('ward:read')")
    @GetMapping
    public ResponseEntity<DataList<List<WardDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.getAll(
                BaseCriteria.builder().
                        page(page).
                        size(size).
                        build()
        ));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('ward:read')")
    @GetMapping("/{id}")
    public ResponseEntity<WardDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('ward:create')")
    @PostMapping
    public ResponseEntity<WardDto> create(@Valid @RequestBody WardCreateDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('ward:update')")
    @PutMapping("/{id}")
    public ResponseEntity<WardDto> update(@PathVariable String id, @Valid @RequestBody WardUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('ward:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}