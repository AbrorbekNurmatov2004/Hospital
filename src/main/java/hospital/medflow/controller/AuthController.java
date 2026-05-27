package hospital.medflow.controller;

import hospital.medflow.dto.login.LoginRequest;
import hospital.medflow.dto.login.LoginResponse;
import hospital.medflow.dto.login.TokenDto;
import hospital.medflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static hospital.medflow.utils.ApiPath.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + VERSION + "/auth")
public class AuthController {

    private final UserService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(@RequestParam String token) {
        return ResponseEntity.ok(service.refreshToken(token));
    }

}
