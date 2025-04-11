package com.hwt.simplesecurecipher.auth.service;

import com.hwt.simplesecurecipher.auth.dto.DecryptResponse;
import com.hwt.simplesecurecipher.auth.dto.EncryptResponse;
import com.hwt.simplesecurecipher.auth.entity.CryptoKey;
import com.hwt.simplesecurecipher.auth.repository.CryptoKeyRepository;
import com.hwt.simplesecurecipher.auth.util.CryptoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CryptoService {
    private final CryptoKeyRepository keyRepo;

    public EncryptResponse encrypt(String username, String plainText) throws Exception {
        byte[] iv = CryptoUtils.generateIV();
        SecretKey key = CryptoUtils.generateKey();
        String keyId = UUID.randomUUID().toString();

        byte[] cipher = CryptoUtils.encryptAESGCM(plainText.getBytes(), key, iv);

        keyRepo.save(CryptoKey.builder()
                .userId(username)
                .keyId(keyId)
                .secretKey(key.getEncoded())
                .createdAt(Instant.now())
                .build());

        return new EncryptResponse(
                Base64.getEncoder().encodeToString(cipher),
                Base64.getEncoder().encodeToString(iv),
                keyId
        );
    }

    public DecryptResponse decrypt(String username, String cipherTextB64, String ivB64, String keyId) throws Exception {
        CryptoKey storedKey = keyRepo.findByUserIdAndKeyId(username, keyId)
                .orElseThrow(() -> new IllegalArgumentException("Encryption key not found"));

        SecretKey key = new SecretKeySpec(storedKey.getSecretKey(), "AES");
        byte[] iv = Base64.getDecoder().decode(ivB64);
        byte[] cipherText = Base64.getDecoder().decode(cipherTextB64);

        byte[] plain = CryptoUtils.decryptAESGCM(cipherText, key, iv);
        return new DecryptResponse(new String(plain));
    }
}
