package com.hwt.simplesecurecipher.auth.dto;

public record DecryptRequest(String cipherText, String iv, String keyId) {
}
