package com.FerployDemo.ferployDemo.service;

import com.FerployDemo.ferployDemo.domain.entity.Member;
import com.FerployDemo.ferployDemo.domain.entity.Token;
import com.FerployDemo.ferployDemo.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public Token saveToken(String accessToken, String refreshToken, Member member) {
        Token token = new Token();
        token.setAccToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setMember(member);

        return tokenRepository.save(token);
    }
}
