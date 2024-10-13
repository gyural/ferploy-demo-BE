package com.FerployDemo.ferployDemo.controller;

import com.FerployDemo.ferployDemo.domain.entity.NameCard;
import com.FerployDemo.ferployDemo.dto.NameCardDTO;
import com.FerployDemo.ferployDemo.service.NameCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/namecard")
public class NameCardController {
    @Autowired
    private NameCardService nameCardService;

    @PostMapping("/create")
    public ResponseEntity<NameCard> create(@RequestBody NameCard nameCard) {
        NameCard newNameCard =  nameCardService.createNameCard(nameCard);
        if(newNameCard != null){
            return ResponseEntity.ok(newNameCard);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Authorization 헤더에서 토큰 추출
            String accessToken = authorizationHeader.replace("Bearer ", "");
            List<NameCardDTO> nameCards = nameCardService.findAllNameCard(accessToken);
            return ResponseEntity.ok(nameCards);
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
