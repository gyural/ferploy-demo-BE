package com.FerployDemo.ferployDemo.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Member {  // 클래스 이름은 일반적으로 대문자로 시작하는 것이 관례입니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본키
    private String name; // 유저 이름
    private String email; // 유저 구글 이메일
    private String profilePicture; // 프로필 사진 URL 또는 경로

}