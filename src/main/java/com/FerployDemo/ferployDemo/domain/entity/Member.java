package com.FerployDemo.ferployDemo.domain.entity;

import com.FerployDemo.ferployDemo.common.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Member {  // 클래스 이름은 일반적으로 대문자로 시작하는 것이 관례입니다.
    @Id
    private String id; // google oauth ID
    private String name; // 유저 이름
    private String email; // 유저 구글 이메일
    private String profilePicture; // 프로필 사진 URL 또는 경로
    private String google_acc;
    private String google_refresh;
    private Role role;
}