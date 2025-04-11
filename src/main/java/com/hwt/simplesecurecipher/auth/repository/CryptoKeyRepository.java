package com.hwt.simplesecurecipher.auth.repository;

import com.hwt.simplesecurecipher.auth.entity.CryptoKey;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CryptoKeyRepository extends CrudRepository<CryptoKey, String> {
    Optional<CryptoKey> findByUserIdAndKeyId(String userId, String keyId);
}
