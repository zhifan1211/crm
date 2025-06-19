package com.example.demo;

import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.util.Hash;

@SpringBootTest
public class Sha256HashTest {
    public static void main(String[] args) {
        String password = "otterpoint";
        String salt = "1234"; // 你可以隨便設一組新 salt
        String hash = Hash.getHash(password, salt); // 用你原本的工具類
        System.out.println("hash=" + hash);
        System.out.println("salt=" + salt);
    }
}
