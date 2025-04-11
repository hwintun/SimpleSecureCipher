package com.hwt.simplesecurecipher.auth.dto;

public record EncryptResponse(String cipherText, String iv, String keyId) {
}
