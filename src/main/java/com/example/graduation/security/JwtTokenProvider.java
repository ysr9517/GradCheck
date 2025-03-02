package com.example.graduation.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtTokenProvider {
    private final String SECRET_KEY = "graduationprojectsecretkeygraduationprojectsecretkey";
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    private final Key key = Keys.jmacShaKeyFor(SECRET_KEY.getBytes());
}
