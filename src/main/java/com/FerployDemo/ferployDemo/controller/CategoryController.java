package com.FerployDemo.ferployDemo.controller;

import com.FerployDemo.ferployDemo.config.JwtTokenProvider;
import com.FerployDemo.ferployDemo.domain.entity.Category;
import com.FerployDemo.ferployDemo.service.CategoryService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.ITokenSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final JwtTokenProvider jwtTokenProvider;
    private final CategoryService categoryService;

    @GetMapping("/member-categories")
    public ResponseEntity<List<Category>> getCategoriesByMemberId(
            @RequestHeader("Authorization") String acctoken) {

        // Extract member ID from the token
        Map<String, Object> userInfo = jwtTokenProvider.getUserFromToken(acctoken.replace("Bearer ", ""));
        String memberId = (String) userInfo.get("id");
        // Fetch categories for the member
        List<Category> categories = categoryService.findByMemberId(memberId);

        return ResponseEntity.ok(categories);
    }
}
