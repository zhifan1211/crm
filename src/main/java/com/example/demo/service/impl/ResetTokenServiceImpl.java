package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.entity.ResetToken;
import com.example.demo.repository.ResetTokenRepository;
import com.example.demo.service.ResetTokenService;
import com.example.demo.util.TokenHash;

@Service
public class ResetTokenServiceImpl implements ResetTokenService{
	
	@Autowired
	public ResetTokenRepository resetTokenRepository;

    // 建立 Token 並回傳明文 tokenId
	@Override
	public String createToken(String phoneNumber) {
		String tokenId = UUID.randomUUID().toString();
        String tokenHash = TokenHash.hashToken(tokenId);

        ResetToken token = new ResetToken();
        token.setTokenId(tokenId); // 這個欄位是否存 DB 視你設計而定
        token.setTokenHash(tokenHash);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiredAt(LocalDateTime.now().plusMinutes(10));
        token.setUsed(false);
        token.setPhoneNumber(phoneNumber);

        resetTokenRepository.save(token);
        return tokenId;
	}

    // 驗證 token 合法性（拋出錯誤或回傳 token 資料）
	@Override
	public ResetToken verifyToken(String tokenId) {
		String tokenHash = TokenHash.hashToken(tokenId);

        ResetToken token = resetTokenRepository.findByTokenHash(tokenHash)
            .orElseThrow(() -> new RuntimeException("Token 無效"));

        if (token.isUsed()) {
            throw new RuntimeException("Token 已被使用");
        }

        if (token.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token 已過期");
        }

        return token;
	}

	@Override
	public void markTokenAsUsed(String tokenId) {
		String tokenHash = TokenHash.hashToken(tokenId);
        ResetToken token = resetTokenRepository.findByTokenHash(tokenHash)
            .orElseThrow(() -> new RuntimeException("找不到 token"));

        token.setUsed(true);
        resetTokenRepository.save(token);
	}
}
