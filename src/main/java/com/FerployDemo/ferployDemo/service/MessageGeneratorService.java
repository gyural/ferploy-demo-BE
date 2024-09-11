package com.FerployDemo.ferployDemo.service;

import com.FerployDemo.ferployDemo.model.messages.ClientInfo;
import com.FerployDemo.ferployDemo.model.messages.MessageRequest;
import com.FerployDemo.ferployDemo.model.messages.MessageResponse;
import com.FerployDemo.ferployDemo.model.messages.MyInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageGeneratorService {
    @Value("${anthropic.api.key}")
    private String anthropicApiKey;

    @Value("${anthropic.api.url}")
    private String anthropicApiUrl;

    private RestTemplate restTemplate;


    public MessageGeneratorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateMessage(String promptType, MyInfo myInfo, ClientInfo clientInfo, String businessInfo) {
        String prompt = createPrompt(promptType, myInfo, clientInfo, businessInfo);
        MessageRequest request = new MessageRequest("claude-3-5-sonnet-20240620", 1024, prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", anthropicApiKey);
        headers.set("anthropic-version", "2023-06-01");

        HttpEntity<MessageRequest> entity = new HttpEntity<>(request, headers);

        MessageResponse response = restTemplate.postForObject(anthropicApiUrl, entity, MessageResponse.class);

        if (response != null && response.getContent() != null) {
            return response.getContent();
        } else {
            return "메시지 생성에 실패했습니다.";
        }
    }

    private String createPrompt(String promptType, MyInfo myInfo, ClientInfo clientInfo, String businessInfo) {
        StringBuilder promptBuilder = new StringBuilder();

        if ("welcome".equals(promptType)) {
            promptBuilder.append("다음 정보를 바탕으로 첫 인삿말을 생성해주세요.\n");
        } else if ("reminder".equals(promptType)) {
            promptBuilder.append("다음 정보를 바탕으로 리마인드 메시지를 생성해 주세요.\n");
        }

        promptBuilder.append("1. 나의 정보:\n")
                .append("   - 회사: ").append(myInfo.getCompany()).append("\n")
                .append("   - 직책: ").append(myInfo.getPosition()).append("\n")
                .append("   - 이름: ").append(myInfo.getName()).append("\n")
                .append("2. 고객 정보:\n")
                .append("   - 이름: ").append(clientInfo.getName()).append("\n")
                .append("   - 직책: ").append(clientInfo.getPosition()).append("\n")
                .append("   - 회사: ").append(clientInfo.getCompany()).append("\n")
                .append("3. 비즈니스 맥락:\n   - ").append(businessInfo).append("\n");

        if ("reminder".equals(promptType)) {
            promptBuilder.append("명절이나 계절 인사말도 함께 포함해 주세요.");
        }

        return promptBuilder.toString();
    }
}
