package hospital.medflow.controller;

import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.user.ChangePasswordDto;
import hospital.medflow.dto.user.UserCreateDto;
import hospital.medflow.dto.user.UserDto;
import hospital.medflow.dto.user.UserUpdateDto;
import hospital.medflow.service.UserService;
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
@RequestMapping(API + VERSION + "/users")
public class UserController {

    private final UserService service;

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('user:read')")
    @GetMapping
    public ResponseEntity<DataList<List<UserDto>>> getAll(
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

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('user:read')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('user:create')")
    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserCreateDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('user:update')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable String id, @Valid @RequestBody UserUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PreAuthorize(value = "@securityUtils.superAdmin() or hasAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordDto dto) {
        service.changePassword(dto);
        return ResponseEntity.noContent().build();
    }
}
