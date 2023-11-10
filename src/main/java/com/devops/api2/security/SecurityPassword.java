package com.devops.api2.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * SpringSecurity password generator
 * DB에 저장되는 값
 * 단방향 암호화
 * sejin
 */
public class SecurityPassword{
    public static void main(String[] args) {
        // 비밀번호 생성
        //String password = "Purchase!@#4";

        String gwPassword = "GroupWare!@#4";

        // BCryptPasswordEncoder 객체 생성
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi
        //$2a$10$5KlRVeKPcDYlPDQ30KZ5VOVwQcTIt3AZmIfkk2tpMP1QFVTDezRAC

        // 비밀번호 암호화 / 단방향 암호화
        String hashedPassword = passwordEncoder.encode(gwPassword);

        System.out.println("Original password: " + gwPassword);
        System.out.println("Hashed password: " + hashedPassword);

        // 비밀번호 확인
        boolean isMatch = passwordEncoder.matches(gwPassword, hashedPassword);
        System.out.println("password match? " + isMatch);
    }
}
