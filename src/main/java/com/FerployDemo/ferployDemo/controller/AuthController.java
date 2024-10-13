package com.FerployDemo.ferployDemo.controller;

import com.FerployDemo.ferployDemo.config.JwtTokenProvider;
import com.FerployDemo.ferployDemo.domain.request.TokenRefreshRequest;
import com.FerployDemo.ferployDemo.domain.response.LoginResponse;
import com.FerployDemo.ferployDemo.domain.response.RefreshResponse;
import com.FerployDemo.ferployDemo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${google.client.id}")
    private String clientId;
    @Value("${google.client.secret}")
    private String clientSecret;
    @Value("${google.redirect.uri}")
    private String redirectUri;

    private final AuthService authService;

    @GetMapping("/login/oauth/google")
    public ResponseEntity<?> googleLogin(@RequestParam String code) {
        try {
            // 클라이언트 ID, Secret, 리다이렉트 URI를 서비스로 전달
            LoginResponse loginResponse = authService.handleGoogleOAuth(code, clientId, clientSecret, redirectUri);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    @GetMapping("/login/token")
    public ResponseEntity<?> tokenLogin(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Authorization 헤더에서 토큰 추출
            String accessToken = authorizationHeader.replace("Bearer ", "");
            return ResponseEntity.ok().body(authService.handleTokenLogin(accessToken));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: " + e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // Refresh Token을 통한 Access Token 재발급 엔드포인트
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody TokenRefreshRequest request) {
        try {
            String newAccessToken = authService.refreshAccessToken(
                    request.getRefreshToken(),
                    request.getId(),
                    request.getNickname(),
                    request.getRole()
            );
            RefreshResponse refreshResponse = new RefreshResponse();
            refreshResponse.setAccessToken(newAccessToken);
            refreshResponse.setRefreshToken(request.getRefreshToken());
            return ResponseEntity.ok(refreshResponse);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
    }
}
