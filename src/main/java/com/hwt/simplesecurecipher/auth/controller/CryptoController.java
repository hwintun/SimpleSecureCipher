package com.hwt.simplesecurecipher.auth.controller;

import com.hwt.simplesecurecipher.auth.dto.DecryptRequest;
import com.hwt.simplesecurecipher.auth.dto.DecryptResponse;
import com.hwt.simplesecurecipher.auth.dto.EncryptRequest;
import com.hwt.simplesecurecipher.auth.dto.EncryptResponse;
import com.hwt.simplesecurecipher.auth.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/crypto")
@RequiredArgsConstructor
public class CryptoController {
    private final CryptoService cryptoService;

    @PostMapping("/encrypt")
    public ResponseEntity<EncryptResponse> encrypt(@RequestBody EncryptRequest request, Principal principal) throws Exception {
        return ResponseEntity.ok(cryptoService.encrypt(principal.getName(), request.plainText()));
    }

    @PostMapping("/decrypt")
    public ResponseEntity<DecryptResponse> decrypt(@RequestBody DecryptRequest request, Principal principal) throws Exception {
        return ResponseEntity.ok(cryptoService.decrypt(principal.getName(), request.cipherText(), request.iv(), request.keyId()));
    }
}
