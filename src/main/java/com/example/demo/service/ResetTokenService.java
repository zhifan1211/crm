package com.example.demo.service;

import com.example.demo.model.entity.ResetToken;

public interface ResetTokenService {
    String createToken(String phoneNumber);
    ResetToken verifyToken(String tokenId);
    void markTokenAsUsed(String tokenId);
}
