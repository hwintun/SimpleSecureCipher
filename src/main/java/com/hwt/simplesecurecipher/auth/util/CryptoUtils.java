package com.hwt.simplesecurecipher.auth.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;

public class CryptoUtils {
    public static SecretKey generateKey() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256);
        return generator.generateKey();
    }

    public static byte[] generateIV() {
        byte[] iv = new byte[12]; // GCM IV = 12 bytes
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    public static byte[] encryptAESGCM(byte[] plaintext, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec params = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, params);
        return cipher.doFinal(plaintext);
    }

    public static byte[] decryptAESGCM(byte[] ciphertext, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec params = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, params);
        return cipher.doFinal(ciphertext);
    }
}
