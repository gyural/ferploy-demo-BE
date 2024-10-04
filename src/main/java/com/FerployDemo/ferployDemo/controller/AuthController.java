package com.FerployDemo.ferployDemo.controller;


import com.FerployDemo.ferployDemo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.net.URLDecoder;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${google.client.id}")
    private String clientId;
    @Value("${google.client.secret}")
    private String clientSecret;
    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Autowired
    AuthService authService;

    @GetMapping("/login/oauth/google")
    public ResponseEntity<?> googleLogin(@RequestParam String code) {
        try {
            // 클라이언트 ID, Secret, 리다이렉트 URI를 서비스로 전달
            Map<String, Object> userInfo = authService.handleGoogleOAuth(code, clientId, clientSecret, redirectUri);

            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
