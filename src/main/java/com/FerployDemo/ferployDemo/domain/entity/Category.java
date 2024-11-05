package com.FerployDemo.ferployDemo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 카테고리 이름
    private String description; // 카테고리 설명

    @ManyToOne
    private Member member;      // 카테고리를 생성한 회원

    @OneToMany(mappedBy = "category")
    private List<NameCardCategory> nameCardCategories = new ArrayList<>();
}