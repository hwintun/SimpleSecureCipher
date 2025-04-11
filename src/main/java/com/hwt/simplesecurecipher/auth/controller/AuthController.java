package com.hwt.simplesecurecipher.auth.controller;

import com.hwt.simplesecurecipher.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> req) {
        return authService.register(req.get("username"), req.get("password"), req.get("role"));
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> req) {
        return authService.login(req.get("username"), req.get("password"));
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> req) {
        return authService.refresh(req.get("refreshToken"));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            authService.logout(header.substring(7));
        }
    }

    @PostMapping("/promote")
    @PreAuthorize("hasRole('ADMIN')")
    public void promote(@RequestBody Map<String, String> req) {
        authService.promoteToAdmin(req.get("username"));
    }

    @PostMapping("/demote")
    @PreAuthorize("hasRole('ADMIN')")
    public void demote(@RequestBody Map<String, String> req) {
        authService.demoteFromAdmin(req.get("username"));
    }

    @GetMapping("/me")
    public String profile(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return "You are logged in with token: " + header;
    }
}
