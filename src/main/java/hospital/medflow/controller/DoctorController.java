package hospital.medflow.controller;

import hospital.medflow.config.CustomUserDetails;
import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.booking.BookingDto;
import hospital.medflow.dto.doctor.DoctorCreateDto;
import hospital.medflow.dto.doctor.DoctorDto;
import hospital.medflow.dto.doctor.DoctorUpdateDto;
import hospital.medflow.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static hospital.medflow.utils.ApiPath.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + VERSION + "/doctors")
public class DoctorController {

    private final DoctorService service;

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('doctor:read')")
    @GetMapping
    public ResponseEntity<DataList<List<DoctorDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search
    ) {
        return ResponseEntity.ok(service.getAll(
                BaseCriteria.builder().
                        search(search).
                        page(page).
                        size(size).
                        build()
        ));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('doctor:read')")
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasRole('DOCTOR')")
    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingDto>> getBookings(@AuthenticationPrincipal CustomUserDetails currentUser) {
        String id = currentUser.getId();
        return ResponseEntity.ok(service.getBookingsByDoctorId(id));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('doctor:create')")
    @PostMapping("/")
    public ResponseEntity<DoctorDto> create(@Valid @RequestBody DoctorCreateDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('doctor:update')")
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> update(@PathVariable String id, @Valid @RequestBody DoctorUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('doctor:delete')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}