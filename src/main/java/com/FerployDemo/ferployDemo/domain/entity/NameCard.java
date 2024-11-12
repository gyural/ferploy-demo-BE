package com.FerployDemo.ferployDemo.domain.entity;

import com.FerployDemo.ferployDemo.domain.enums.ClientType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
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
    private String profileImg;
    private String bgColor;
    private String textColor;
    private String greetingMessage;
    private String savedDate;
    private boolean isMe;
    //@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //@JoinTable(
    //        name = "namecard_category",
    //        joinColumns = @JoinColumn(name = "namecard_id"),
    //        inverseJoinColumns = @JoinColumn(name = "category_id")
    //)
    //private Set<Category> categories = new HashSet<>();
}