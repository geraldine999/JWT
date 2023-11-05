package com.example.demo.security.jwt;

import java.sql.Date;

import java.time.LocalDate;
import java.time.Period;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public String createJwt(UserDetails userDetails) {

        String key = jwtProperties.getKey();

        return Jwts.builder().setSubject(userDetails.getUsername()).claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(Date.valueOf(LocalDate.now()))
                .setExpiration(Date.valueOf(LocalDate.now().plus(Period.ofDays(2))))
                .signWith(Keys.hmacShaKeyFor(key.getBytes())).compact();
    }

}
