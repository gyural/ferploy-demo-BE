package com.FerployDemo.ferployDemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v3/api-docs/swagger-config")
@RequiredArgsConstructor
public class HealtyCheck {
    @GetMapping
    public ResponseEntity check() {
        return ResponseEntity.ok().build();
    }
}
