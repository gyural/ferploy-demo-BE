package com.FerployDemo.ferployDemo.controller;

import com.FerployDemo.ferployDemo.config.JwtTokenProvider;
import com.FerployDemo.ferployDemo.domain.entity.NameCard;
import com.FerployDemo.ferployDemo.domain.request.PostCreateNameCardRequest;
import com.FerployDemo.ferployDemo.domain.response.GetNameCardListResponse;
import com.FerployDemo.ferployDemo.dto.NameCardDTO;
import com.FerployDemo.ferployDemo.repository.NameCardRepository;
import com.FerployDemo.ferployDemo.dto.NameCardDTO;
import com.FerployDemo.ferployDemo.service.NameCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/namecard")
@RequiredArgsConstructor
public class NameCardController {

    private  final NameCardService nameCardService;
    private final JwtTokenProvider jwtTokenProvider;
    private final NameCardRepository nameCardRepository;

    @PostMapping
    public ResponseEntity<NameCardDTO> create(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody PostCreateNameCardRequest nameCardRequest) {

        String memberId = extractMemberIdFromToken(authorizationHeader);
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        NameCardDTO newNameCard = nameCardService.createNameCard(nameCardRequest, memberId);

        if (newNameCard != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newNameCard);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String extractMemberIdFromToken(String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        if (!jwtTokenProvider.validateToken(accessToken)) {
            return null;  // Token is invalid, return null
        }
        Map<String, Object> userInfo = jwtTokenProvider.getUserFromToken(accessToken);
        return (String) userInfo.get("id");
    }
    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Authorization 헤더에서 토큰 추출
            String accessToken = authorizationHeader.replace("Bearer ", "");
            GetNameCardListResponse nameCardsList = nameCardService.findAllNameCard(accessToken);
            return ResponseEntity.ok(nameCardsList);
        } catch (IllegalAccessException e) {
            // 401 에러 - Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            // 500 에러 - Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<NameCard> updateNameCard(@PathVariable Long id, @RequestBody NameCard newNameCard) {
        NameCard updatedNameCard = nameCardService.updateNameCard(id, newNameCard);

        if(updatedNameCard != null){
            return ResponseEntity.ok(updatedNameCard);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}
