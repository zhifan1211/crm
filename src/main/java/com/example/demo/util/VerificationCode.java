package com.example.demo.util;

import java.time.LocalDateTime;
import java.util.Random;

public class VerificationCode {
    private String code;
    private LocalDateTime expireAt;

    public VerificationCode() {
        this.code = generateCode();
        this.expireAt = LocalDateTime.now().plusMinutes(10);
    }

    private String generateCode() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireAt);
    }

    @Override
    public String toString() {
        return code;
    }
}
