package com.FerployDemo.ferployDemo.service;

import com.FerployDemo.ferployDemo.domain.entity.Category;
import com.FerployDemo.ferployDemo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import com.FerployDemo.ferployDemo.domain.entity.Member;
import com.FerployDemo.ferployDemo.domain.entity.NameCard;
import com.FerployDemo.ferployDemo.domain.entity.NameCardCategory;
import com.FerployDemo.ferployDemo.dto.CategoryRequest;
import com.FerployDemo.ferployDemo.repository.CategoryRepository;
import com.FerployDemo.ferployDemo.repository.MemberRepository;
import com.FerployDemo.ferployDemo.repository.NameCardCategoryRepository;
import com.FerployDemo.ferployDemo.repository.NameCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final NameCardRepository nameCardRepository;
    private final NameCardCategoryRepository nameCardCategoryRepository;
    private final MemberRepository memberRepository;

    // 멤버의 모든 카테고리 조회
    public List<Category> getMemberCategories(String memberId) {
        return categoryRepository.findByMemberId(memberId);
    }

    // 카테고리 생성
    @Transactional
    public Category createCategory(String memberId, CategoryRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setMember(member);

        return categoryRepository.save(category);
    }

    // 카테고리에 명함 추가
    @Transactional
    public void addNameCardToCategory(String memberId, Long categoryId, Long nameCardId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 카테고리 소유자 체크
        if (!category.getMember().getId().equals(memberId)) {
            throw new RuntimeException("Not authorized");
        }

        NameCard nameCard = nameCardRepository.findById(nameCardId)
                .orElseThrow(() -> new RuntimeException("NameCard not found"));

        // 이미 해당 카테고리에 명함이 있는지 체크
        boolean exists = nameCardCategoryRepository.existsByCategoryAndNameCard(category, nameCard);
        if (exists) {
            throw new RuntimeException("NameCard already exists in this category");
        }

        NameCardCategory nameCardCategory = new NameCardCategory();
        nameCardCategory.setCategory(category);
        nameCardCategory.setNameCard(nameCard);

        nameCardCategoryRepository.save(nameCardCategory);
    }

    // 카테고리 삭제
    @Transactional
    public void deleteCategory(String memberId, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 카테고리 소유자 체크
        if (!category.getMember().getId().equals(memberId)) {
            throw new RuntimeException("Not authorized");
        }

        // 연관된 NameCardCategory 먼저 삭제
        nameCardCategoryRepository.deleteByCategoryId(categoryId);

        // 카테고리 삭제
        categoryRepository.delete(category);
    }
}
