package com.FerployDemo.ferployDemo.service;

import com.FerployDemo.ferployDemo.common.Role;
import com.FerployDemo.ferployDemo.config.JwtTokenProvider;
import com.FerployDemo.ferployDemo.domain.entity.Member;
import com.FerployDemo.ferployDemo.domain.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.FerployDemo.ferployDemo.common.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GoogleOAuthService googleOAuthService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse handleGoogleOAuth(String code, String clientId, String clientSecret, String redirectUri) {
        // 1. Google OAuth에서 토큰과 사용자 정보 가져오기
        Map<String, Object> tokens = googleOAuthService.getGoogleTokens(code, clientId, clientSecret, redirectUri);

        String accessToken = (String) tokens.get("access_token");
        String refreshToken = (String) tokens.get("refresh_token");

        Map<String, Object> userInfo = googleOAuthService.getGoogleUserInfo(accessToken);
        // 2. 사용자 정보로 Member 저장
        String userId = (String) userInfo.get("id");
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");
        String profilePicture = (String) userInfo.get("picture");

        Member member = memberService.saveMember(userId, email, name, profilePicture, USER, accessToken, refreshToken);
        // Access Token 및 Refresh Token 생성 (Role은 임시로 USER 고정)
        String accessJWT = jwtTokenProvider.generateAccessToken(
                (String) member.getId(),
                (String) member.getName(),
                USER // 임시 고정된 역할
        );
        System.out.println("HER");

        String refreshJWT = jwtTokenProvider.generateRefreshToken();

        LoginResponse response = new LoginResponse();
        response.setUserInfo(userInfo);
        response.setRefreshToken(refreshJWT);
        response.setAccessToken(accessJWT);
        return response;
    }

    // Refresh Token을 이용한 Access Token 재발급
    public String refreshAccessToken(String refreshToken, String id, String nickname, Role role) {
        return jwtTokenProvider.refreshAccessToken(refreshToken, id, nickname, role);
    }
}
