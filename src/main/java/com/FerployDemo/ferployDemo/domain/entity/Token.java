package com.FerployDemo.ferployDemo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본키
    private String accToken; // JWT 토큰
    private String refreshToken; // 리프레시 토큰 (필요한 경우)

    @OneToOne // 회원과 관계를 설정
    private Member member; // 이 토큰과 관련된 회원
}