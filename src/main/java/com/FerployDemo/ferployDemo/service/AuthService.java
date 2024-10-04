package com.FerployDemo.ferployDemo.service;

import com.FerployDemo.ferployDemo.domain.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private GoogleOAuthService googleOAuthService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenService tokenService;

    public Map<String, Object> handleGoogleOAuth(String code, String clientId, String clientSecret, String redirectUri) {
        // 1. Google OAuth에서 토큰과 사용자 정보 가져오기
        Map<String, Object> tokens = googleOAuthService.getGoogleTokens(code, clientId, clientSecret, redirectUri);
        String accessToken = (String) tokens.get("access_token");
        String refreshToken = (String) tokens.get("refresh_token");

        Map<String, Object> userInfo = googleOAuthService.getGoogleUserInfo(accessToken);

        // 2. 사용자 정보로 Member 저장
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");
        String profilePicture = (String) userInfo.get("picture");

        Member member = memberService.saveMember(email, name, profilePicture);

        // 3. 토큰 저장
        tokenService.saveToken(accessToken, refreshToken, member);

        return userInfo;
    }
}
