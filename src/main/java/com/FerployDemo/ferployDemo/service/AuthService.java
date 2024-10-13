package com.FerployDemo.ferployDemo.service;

import com.FerployDemo.ferployDemo.common.Role;
import com.FerployDemo.ferployDemo.config.JwtTokenProvider;
import com.FerployDemo.ferployDemo.domain.entity.Member;
import com.FerployDemo.ferployDemo.domain.response.LoginResponse;
import com.FerployDemo.ferployDemo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.FerployDemo.ferployDemo.common.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GoogleOAuthService googleOAuthService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
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
    public LoginResponse handleTokenLogin(String accessToken) {
        // 1. 토큰 유효성 검사
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("Invalid Access Token");
        }

        // 2. 토큰에서 사용자 정보 추출
        Map<String, Object> userInfoToken = jwtTokenProvider.getUserFromToken(accessToken);
        String memberId = (String) userInfoToken.get("id");

        // 3. 사용자 정보 조회
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) {
            throw new IllegalArgumentException("Invalid Access Token");
        }

        // 4. 사용자 정보가 존재할 경우 사용자 정보 추출 및 토큰 재발급
        Member member = optionalMember.get();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", member.getId());
        userInfo.put("email", member.getEmail());
        userInfo.put("name", member.getName());
        userInfo.put("picture", member.getProfilePicture());

        // 5. 새로운 Access Token 및 Refresh Token 생성
        String newAccessToken = jwtTokenProvider.generateAccessToken(member.getId(), member.getName(), member.getRole());
        String refreshToken = jwtTokenProvider.generateRefreshToken();

        // 6. LoginResponse 생성 및 반환
        LoginResponse response = new LoginResponse();
        response.setUserInfo(userInfo);
        response.setAccessToken(newAccessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }
    // Refresh Token을 이용한 Access Token 재발급
    public String refreshAccessToken(String refreshToken, String id, String nickname, Role role) {
        return jwtTokenProvider.refreshAccessToken(refreshToken, id, nickname, role);
    }
}
