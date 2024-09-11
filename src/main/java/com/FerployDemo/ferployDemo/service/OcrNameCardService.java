package com.FerployDemo.ferployDemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class OcrNameCardService {
    @Value("${CLOVER_OCR_SECRET}")
    private String CLOVER_OCR_SECRET;
    @Value("${CLOVER_OCR_URL}")
    private String CLOVER_OCR_URL;

    private RestTemplate restTemplate = new RestTemplate();

    public OcrNameCardService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> ocrNameCard(String format, String base64Image) {
        String requestId = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();

        // 요청 바디 설정
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("version", "V2");
        requestBody.put("requestId", requestId);
        requestBody.put("timestamp", timestamp);

        // 이미지 데이터 설정
        Map<String, String> image = new HashMap<>();
        image.put("format", format);
        image.put("data", base64Image);
        image.put("name", "namecard-demo");

        requestBody.put("images", new Object[]{image});

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-OCR-SECRET", CLOVER_OCR_SECRET);

        // HTTP 요청 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // 클로버 OCR API 호출
        try {
            // postForObject를 사용하여 POST 요청 보내기
            Map<String, Object> response = restTemplate.postForObject(
                    CLOVER_OCR_URL, entity, Map.class);

            // 응답 처리
            if (response != null) {
                return response;
            } else {
                throw new RuntimeException("OCR API 호출 실패: 응답이 null");
            }
        } catch (Exception e) {
            throw new RuntimeException("OCR 처리 중 오류 발생", e);
        }
    }

}
