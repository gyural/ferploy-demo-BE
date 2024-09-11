package com.FerployDemo.ferployDemo.controller;

import com.FerployDemo.ferployDemo.service.MessageGeneratorService;
import com.FerployDemo.ferployDemo.service.OcrNameCardService;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OcrNameCardController {

    private final OcrNameCardService ocrNameCardService;

    public OcrNameCardController(OcrNameCardService ocrNameCardService) {
        this.ocrNameCardService = ocrNameCardService;
    }

    @PostMapping("api/namecard")
    public Map<String, Object> processNameCard(@RequestBody NameCardRequest request) {
        // 이미지의 base64 인코딩 데이터와 포맷을 서비스에 전달
        return ocrNameCardService.ocrNameCard(request.getImgFormat(), request.getBase64Img());
    }

    // 요청에서 사용할 데이터 클래스를 정의
    @Data
    public static class NameCardRequest {
        private String base64Img;
        private String imgFormat;
    }
}