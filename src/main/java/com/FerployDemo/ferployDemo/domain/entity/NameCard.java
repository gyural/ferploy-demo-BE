package com.FerployDemo.ferployDemo.domain.entity;

import com.FerployDemo.ferployDemo.domain.enums.ClientType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NameCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //저장한 회원
    @ManyToOne
    private Member member;

    private String name;
    private String companyName;
    private String position;
    private String mobileNumber;
    private String email;
    private String contactNumber;
    private ClientType clientType; // enum으로 수정된 고객 타입
    private String place;
    private String memo;
    private String address;
    private String homepage;
    private String nameCardImg;

}