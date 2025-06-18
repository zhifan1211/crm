package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .cors(cors -> {}) // 用預設 cors 設定
	        .authorizeHttpRequests(authz -> authz
	            .requestMatchers("/admin/login", "/admin/logout").permitAll()
	            .requestMatchers("/admin/**").authenticated()
	            .anyRequest().permitAll()
	        )
	        .formLogin(form -> form.disable())
	        .httpBasic(basic -> basic.disable());
	    return http.build();
	}

}
