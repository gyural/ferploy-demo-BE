package com.FerployDemo.ferployDemo.config;

import com.FerployDemo.ferployDemo.common.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenProvider {

    private final String MY_KEY = "gyuseong-secret-key-that-should-be-much-longer-to-meet-HS512-requirements";
    private final String SECRET_KEY = Base64.getEncoder().encodeToString(MY_KEY.getBytes());
    private final long AccessTokenExpirationTime = 3600 * 6L * 1000; // 6 hours (밀리초 단위)
    private final long RefreshTokenExpirationTime = 3600 * 24 * 60L * 1000; // 60 days (밀리초 단위)

    // Access Token 생성 (id, nickname, role 포함)
    public String generateAccessToken(String id, String nickname, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("nickname", nickname);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + AccessTokenExpirationTime))  // 6시간 만료
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // Refresh Token 생성 (추가 정보 없이)
    public String generateRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + RefreshTokenExpirationTime))  // 60일 만료
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // 토큰에서 사용자 이름 추출
    public String getUserFromToken(String token) {
        return getClaims(token).getSubject();  // subject는 username
    }



    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            return !getClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    // Refresh Token을 사용하여 새로운 Access Token 발급
    public String refreshAccessToken(String refreshToken, String id, String nickname, Role role) {
        if (validateToken(refreshToken)) {
            return generateAccessToken(id, nickname, role);
        } else {
            throw new RuntimeException("Invalid or expired refresh token");
        }
    }
    // 내부에서 Claims를 추출하는 메소드
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
