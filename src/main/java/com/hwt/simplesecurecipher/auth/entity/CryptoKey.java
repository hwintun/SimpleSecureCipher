package com.hwt.simplesecurecipher.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String keyId;

    @Lob
    private byte[] secretKey;

    private Instant createdAt;
}
