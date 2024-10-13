package com.FerployDemo.ferployDemo.controller;

import com.FerployDemo.ferployDemo.domain.entity.NameCard;
import com.FerployDemo.ferployDemo.service.NameCardService;
import org.springframework.beans.factory.annotation.Autowired;
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
