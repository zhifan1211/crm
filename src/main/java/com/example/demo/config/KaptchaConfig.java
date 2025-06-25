package com.example.demo.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    @Bean
    DefaultKaptcha producer() {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "120"); // 圖片寬度
        properties.setProperty("kaptcha.image.height", "40"); // 圖片高度
        properties.setProperty("kaptcha.textproducer.font.size", "36"); // 字體大小
        properties.setProperty("kaptcha.textproducer.char.length", "4"); // 驗證碼長度
        properties.setProperty("kaptcha.textproducer.font.names", "Arial,Courier"); // 字體
        properties.setProperty("kaptcha.textproducer.char.string", "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"); // 避免易混淆字
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise"); // 雜訊線條
        properties.setProperty("kaptcha.background.clear.from", "white"); // 背景色
        properties.setProperty("kaptcha.background.clear.to", "white");   // 背景色

        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(new Config(properties));
        return kaptcha;
    }
}

