package com.FerployDemo.ferployDemo.controller;

import com.FerployDemo.ferployDemo.model.messages.ClientInfo;
import com.FerployDemo.ferployDemo.model.messages.MyInfo;
import com.FerployDemo.ferployDemo.service.MessageGeneratorService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/message")
@RequiredArgsConstructor
public class MessageGeneratorController {
    private final MessageGeneratorService messageGeneratorService;// 이렇게 많이 사용해요!

//    public MessageGeneratorController(MessageGeneratorService messageGeneratorService) {
//        this.messageGeneratorService = messageGeneratorService;
//    }

    @PostMapping("/generate")
    public String generateMessage(@RequestBody MessageGenerationRequest request) {
        return messageGeneratorService.generateMessage(
                request.getPromptType(),
                request.getMyInfo(),
                request.getClientInfo(),
                request.getBusinessInfo()
        );
    }
    @Data
    public static class MessageGenerationRequest {
        private String promptType;
        private MyInfo myInfo;
        private ClientInfo clientInfo;
        private String businessInfo;
    }

}
