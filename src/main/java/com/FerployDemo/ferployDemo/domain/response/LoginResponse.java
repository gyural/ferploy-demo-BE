package com.FerployDemo.ferployDemo.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class LoginResponse {
    private Map<String, Object> userInfo;  // 사용자 정보 (id, email, name 등)
    private String accessToken;            // Access Token
    private String refreshToken;           // Refresh Token
}
