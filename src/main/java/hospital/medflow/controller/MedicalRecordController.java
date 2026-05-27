package hospital.medflow.controller;

import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.medicalRecord.MedicalRecordCreateDto;
import hospital.medflow.dto.medicalRecord.MedicalRecordDto;
import hospital.medflow.service.base.MedicalRecordService;
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
@RequestMapping(API + VERSION + "records")
public class MedicalRecordController {

    private final MedicalRecordService service;

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('record:read')")
    @GetMapping
    public ResponseEntity<DataList<List<MedicalRecordDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search
    ) {
        return ResponseEntity.ok(service.getAll(BaseCriteria.builder().
                search(search).
                page(page).
                size(size).
                build()
        ));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('record:create')")
    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('record:create')")
    @PostMapping
    public ResponseEntity<MedicalRecordDto> create(@Valid @RequestBody MedicalRecordCreateDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('record:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('record:read')")
    @GetMapping("/patient/{id}")
    public ResponseEntity<List<MedicalRecordDto>> getPatientHistory(@PathVariable String id) {
        return ResponseEntity.ok(service.getPatientHistory(id));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('record:read')")
    @GetMapping("/detail/{id}")
    public ResponseEntity<MedicalRecordDto> getRecordDetail(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }
}