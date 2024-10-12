package com.FerployDemo.ferployDemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
    public Map<String, Object> ocrNameCard(List<Map<String, String>> ocrNameCardList) {
        // 결과를 담을 리스트 생성
        List<Map<String, Object>> resultList = new ArrayList<>();

        // 각각의 이미지에 대해 OCR API 호출
        for (Map<String, String> ocrNameCard : ocrNameCardList) {
            try {
                // 각 이미지에 대해 OCR 처리
                Map<String, Object> ocrResult = getOneNameCardOcr(ocrNameCard);

                // OCR 처리 결과가 있으면 리스트에 추가
                resultList.add(ocrResult != null ? ocrResult : new HashMap<>());
            } catch (Exception e) {
                // 예외 발생 시 빈 객체 추가
                resultList.add(new HashMap<>());
            }
        }

        // 최종 결과를 맵에 담아 반환
        Map<String, Object> response = new HashMap<>();
        response.put("results", resultList);
        return response;
    }

    public Map<String, Object> getOneNameCardOcr(Map<String, String> ocrNameCard) {
        String requestId = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();

        // 요청 바디 설정
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("version", "V2");
        requestBody.put("requestId", requestId);
        requestBody.put("timestamp", timestamp);

        // 이미지 데이터 설정
        List<Map<String, String>> imageList = new ArrayList<>();
        Map<String, String> imageData = new HashMap<>();
        imageData.put("format", ocrNameCard.get("format"));
        imageData.put("data", ocrNameCard.get("base64Img"));
        imageData.put("name", "namecard-demo");
        imageList.add(imageData);

        requestBody.put("images", imageList);

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-OCR-SECRET", CLOVER_OCR_SECRET);

        // HTTP 요청 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // 클로버 OCR API 호출
            return restTemplate.postForObject(CLOVER_OCR_URL, entity, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("OCR 처리 중 오류 발생", e);
        }
    }
}
