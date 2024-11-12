package com.FerployDemo.ferployDemo.repository;

import com.FerployDemo.ferployDemo.domain.entity.Category;
import com.FerployDemo.ferployDemo.domain.entity.NameCard;
import com.FerployDemo.ferployDemo.domain.entity.NameCardCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NameCardCategoryRepository extends JpaRepository<NameCardCategory, Long> {
    boolean existsByCategoryAndNameCard(Category category, NameCard nameCard);
    void deleteByCategoryId(Long categoryId);
}