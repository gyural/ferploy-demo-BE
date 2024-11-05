package com.FerployDemo.ferployDemo.service;

import com.FerployDemo.ferployDemo.domain.entity.Category;
import com.FerployDemo.ferployDemo.domain.entity.NameCard;
import com.FerployDemo.ferployDemo.domain.entity.NameCardCategory;
import com.FerployDemo.ferployDemo.repository.CategoryRepository;
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

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public void addNameCardToCategory(Long categoryId, Long nameCardId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        NameCard nameCard = nameCardRepository.findById(nameCardId)
                .orElseThrow(() -> new RuntimeException("NameCard not found"));

        NameCardCategory nameCardCategory = new NameCardCategory();
        nameCardCategory.setCategory(category);
        nameCardCategory.setNameCard(nameCard);

        nameCardCategoryRepository.save(nameCardCategory);
    }
}
