package hospital.medflow.controller;

import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.booking.BookingCreateDto;
import hospital.medflow.dto.booking.BookingDto;
import hospital.medflow.dto.booking.BookingUpdateDto;
import hospital.medflow.model.enums.BookingStatus;
import hospital.medflow.service.BookingService;
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
@RequestMapping(API + VERSION + "/bookings")
public class BookingController {

    private final BookingService service;

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('booking:read')")
    @GetMapping
    public ResponseEntity<DataList<List<BookingDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "", required = false) String search
    ) {
        return ResponseEntity.ok(service.getAll(
                BaseCriteria.builder().
                        search(search).
                        page(page).
                        size(size).
                        build()
        ));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('booking:read')")
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('booking:create')")
    @PostMapping
    public ResponseEntity<BookingDto> create(@Valid @RequestBody BookingCreateDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('booking:update')")
    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> update(@PathVariable String id, @Valid @RequestBody BookingUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('booking:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('booking:patch')")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable String id, @RequestParam BookingStatus status) {
        service.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
