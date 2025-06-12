package com.example.demo.util;

public class TokenHash {
	public static String hashToken(String tokenId) {
        return Hash.getHash(tokenId); // 不加鹽，保持一致性即可
    }
}
