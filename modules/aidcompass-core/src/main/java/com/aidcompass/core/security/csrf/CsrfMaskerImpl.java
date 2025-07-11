package com.aidcompass.core.security.csrf;

import lombok.RequiredArgsConstructor;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;


@RequiredArgsConstructor
public class CsrfMaskerImpl implements CsrfMasker {

    @Override
    public String mask(String csrfToken) {
        byte [] csrfBytes = csrfToken.getBytes(StandardCharsets.UTF_8);
        byte [] mask = new byte [csrfBytes.length];
        new SecureRandom().nextBytes(mask);

        byte [] xoredCsrf = xor(mask, csrfBytes);

        if (xoredCsrf != null){
            byte[] maskedCsrfToken = ByteBuffer.allocate(xoredCsrf.length + mask.length)
                    .put(mask)
                    .put(xoredCsrf)
                    .array();
            return Base64.getUrlEncoder().encodeToString(maskedCsrfToken);
        }
        return null;
    }

    private byte [] xor(byte [] maskBytes, byte [] csrfBytes) {
        if (csrfBytes.length < maskBytes.length) {
            return null;
        } else {
            int len = maskBytes.length;
            byte[] xoredCsrf = new byte[len];
            System.arraycopy(csrfBytes, 0, xoredCsrf, 0, len);

            for(int i = 0; i < len; i++) {
                xoredCsrf[i] ^= maskBytes[i];
            }

            return xoredCsrf;
        }
    }
}
