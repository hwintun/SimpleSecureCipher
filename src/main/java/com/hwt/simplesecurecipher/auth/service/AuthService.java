package com.hwt.simplesecurecipher.auth.service;

import com.hwt.simplesecurecipher.auth.entity.TokenEntity;
import com.hwt.simplesecurecipher.auth.entity.UserEntity;
import com.hwt.simplesecurecipher.auth.repository.TokenRepository;
import com.hwt.simplesecurecipher.auth.repository.UserRepository;
import com.hwt.simplesecurecipher.auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final TokenRepository tokenRepo;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public Map<String, String> register(String username, String password, String role) {
        if (userRepo.findByUsername(username).isPresent())
            throw new RuntimeException("User already exists");

        UserEntity user = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(Set.of(role))
                .build();

        userRepo.save(user);

        return login(username, password);
    }

    public Map<String, String> login(String username, String password) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        UserEntity user = userRepo.findByUsername(username).orElseThrow();
        String accessToken = jwtUtil.generateToken(user.getUsername(), new ArrayList<>(user.getRoles()));
        String refreshToken = UUID.randomUUID().toString();

        revokeTokens(user);

        tokenRepo.save(TokenEntity.builder()
                .token(accessToken)
                .user(user)
                .revoked(false)
                .refresh(false)
                .build());

        tokenRepo.save(TokenEntity.builder()
                .token(refreshToken)
                .user(user)
                .revoked(false)
                .refresh(true)
                .build());

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    public Map<String, String> refresh(String refreshToken) {
        TokenEntity token = tokenRepo.findByToken(refreshToken).orElseThrow();
        if (token.isRevoked() || !token.isRefresh())
            throw new RuntimeException("Invalid refresh token");

        UserEntity user = token.getUser();

        String newAccess = jwtUtil.generateToken(user.getUsername(), new ArrayList<>(user.getRoles()));
        tokenRepo.save(TokenEntity.builder()
                .token(newAccess)
                .user(user)
                .revoked(false)
                .refresh(false)
                .build());

        return Map.of("accessToken", newAccess);
    }

    public void logout(String accessToken) {
        TokenEntity token = tokenRepo.findByToken(accessToken).orElseThrow();
        token.setRevoked(true);
        tokenRepo.save(token);
    }

    public void promoteToAdmin(String username) {
        UserEntity user = userRepo.findByUsername(username).orElseThrow();
        user.getRoles().add("ROLE_ADMIN");
        userRepo.save(user);
    }

    public void demoteFromAdmin(String username) {
        UserEntity user = userRepo.findByUsername(username).orElseThrow();
        user.getRoles().remove("ROLE_ADMIN");
        userRepo.save(user);
    }

    private void revokeTokens(UserEntity user) {
        tokenRepo.findByUser(user).forEach(t -> {
            t.setRevoked(true);
            tokenRepo.save(t);
        });
    }
}
