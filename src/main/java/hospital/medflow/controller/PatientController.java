package hospital.medflow.controller;

import hospital.medflow.dto.patient.PatientCreateDto;
import hospital.medflow.dto.patient.PatientDto;
import hospital.medflow.dto.patient.PatientUpdateDto;
import hospital.medflow.service.base.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static hospital.medflow.utils.ApiPath.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + VERSION + "/patients")
public class PatientController {

    private final PatientService service;

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('patient:read')")
    @GetMapping("/active")
    public ResponseEntity<Page<PatientDto>> getActivePatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "", required = false) String search
    ) {
        return ResponseEntity.ok(service.getActivePatients(page, size, search));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('patient:read')")
    @GetMapping("/released")
    public ResponseEntity<Page<PatientDto>> getReleasedPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(service.getReleasedPatients(page, size, search));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('patient:read')")
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('patient:create')")
    @PostMapping
    public ResponseEntity<PatientDto> create(@Valid @RequestBody PatientCreateDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('patient:update')")
    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> update(@PathVariable String id, @Valid @RequestBody PatientUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('patient:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('patient:patch')")
    @PatchMapping("/{id}/release")
    public ResponseEntity<Void> release(@PathVariable String id) {
        service.releasePatient(id);
        return ResponseEntity.ok().build();
    }
}