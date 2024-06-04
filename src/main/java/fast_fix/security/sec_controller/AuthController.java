package fast_fix.security.sec_controller;

import fast_fix.domain.entity.User;
import fast_fix.security.sec_dto.RefreshRequestDto;
import fast_fix.security.sec_dto.TokenResponseDto;
import fast_fix.security.sec_service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Залогинить пользователя")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        try {
            TokenResponseDto response = authService.login(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/access")
    public ResponseEntity<Object> getNewAccessToken(@RequestBody RefreshRequestDto request) {
        try {
            TokenResponseDto response = authService.getAccessToken(request.getRefreshToken());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}