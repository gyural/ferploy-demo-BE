// CategoryController.java
package com.FerployDemo.ferployDemo.controller;

import com.FerployDemo.ferployDemo.domain.entity.Category;
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

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @PutMapping("/{id}/namecards/{nameCardId}")
    public ResponseEntity<Void> addNameCardToCategory(
            @PathVariable Long id,
            @PathVariable Long nameCardId
    ) {
        categoryService.addNameCardToCategory(id, nameCardId);
        return ResponseEntity.ok().build();
    }
}