package com.satvik.financetracker.controllers;

import com.satvik.financetracker.DTO.Request.LoginRequestDto;
import com.satvik.financetracker.DTO.Request.UserRequest;
import com.satvik.financetracker.DTO.Response.LoginResponseDto;
import com.satvik.financetracker.DTO.Response.UserResponse;
import com.satvik.financetracker.models.User;
import com.satvik.financetracker.security.AuthService;
import com.satvik.financetracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userReq) {
        try {
            // map DTO -> Entity
            User user = new User();
            user.setName(userReq.getName());
            user.setAge(userReq.getAge() == null ? 0 : userReq.getAge());
            user.setUsername(userReq.getUsername());
            user.setPassword(passwordEncoder.encode(userReq.getPassword()));
            user.setSavings(userReq.getSavings());
            user.setSalary(userReq.getSalary());
            user.setEmail(userReq.getEmail());

            // save
            User saved = userService.saveUser(user);

            // map Entity -> Response DTO
            UserResponse resp = UserResponse.builder()
                    .id(saved.getId())
                    .name(saved.getName())
                    .age(saved.getAge())
                    .username(saved.getUsername())
                    .savings(saved.getSavings())
                    .salary(saved.getSalary())
                    .email(saved.getEmail())
                    .expenseCount(saved.getExpenses() == null ? 0 : saved.getExpenses().size())
                    .categoryCount(saved.getCategories() == null ? 0 : saved.getCategories().size())
                    .build();

            return new ResponseEntity<>(resp, HttpStatus.CREATED);

        } catch (IllegalArgumentException ex) {
            // Handle duplicate user or other IllegalArgumentException
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "status", HttpStatus.BAD_REQUEST.value(),
                            "error", "Bad Request",
                            "message", ex.getMessage()
                    ));
        } catch (Exception ex) {
            // Catch unexpected errors
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "error", "Internal Server Error",
                            "message", ex.getMessage()
                    ));
        }
    }

}
