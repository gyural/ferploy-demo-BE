// CategoryController.java
package com.FerployDemo.ferployDemo.controller;

import com.FerployDemo.ferployDemo.domain.entity.Category;
import com.FerployDemo.ferployDemo.dto.CategoryRequest;
import com.FerployDemo.ferployDemo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // 멤버의 모든 카테고리 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Category>> getMemberCategories(@PathVariable String memberId) {
        return ResponseEntity.ok(categoryService.getMemberCategories(memberId));
    }

    // 카테고리 생성 DTO
    @PostMapping("/member/{memberId}")
    public ResponseEntity<Category> createCategory(
            @PathVariable String memberId,
            @RequestBody CategoryRequest request
    ) {
        return ResponseEntity.ok(categoryService.createCategory(memberId, request));
    }

    // 특정 카테고리에 명함 추가
    @PutMapping("/{categoryId}/namecards/{nameCardId}")
    public ResponseEntity<Void> addNameCardToCategory(
            @PathVariable Long categoryId,
            @PathVariable Long nameCardId,
            @RequestParam String memberId  // 권한 체크를 위한 memberId
    ) {
        categoryService.addNameCardToCategory(memberId, categoryId, nameCardId);
        return ResponseEntity.ok().build();
    }

    // 카테고리 삭제
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long categoryId,
            @RequestParam String memberId  // 권한 체크를 위한 memberId
    ) {
        categoryService.deleteCategory(memberId, categoryId);
        return ResponseEntity.ok().build();
    }
}