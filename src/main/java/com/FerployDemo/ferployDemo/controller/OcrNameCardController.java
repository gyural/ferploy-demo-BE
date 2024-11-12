package com.FerployDemo.ferployDemo.controller;

import com.FerployDemo.ferployDemo.service.OcrNameCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ocr")
@RequiredArgsConstructor
public class OcrNameCardController {

    private final OcrNameCardService ocrNameCardService;

    @PostMapping("/namecard")
    public Map<String, Object> processNameCard(@RequestBody List<Map<String, String>> ocrNameCardList) {
        return ocrNameCardService.ocrNameCard(ocrNameCardList);
    }
}