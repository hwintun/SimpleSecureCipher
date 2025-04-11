package com.hwt.simplesecurecipher.auth.repository;

import com.hwt.simplesecurecipher.auth.entity.TokenEntity;
import com.hwt.simplesecurecipher.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    List<TokenEntity> findByUser(UserEntity user);
    Optional<TokenEntity> findByToken(String token);
}
