package com.hwt.simplesecurecipher.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String token;

    private boolean revoked;

    private boolean refresh;

    @ManyToOne
    private UserEntity user;
}
