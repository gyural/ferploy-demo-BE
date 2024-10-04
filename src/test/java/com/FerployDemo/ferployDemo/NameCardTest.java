package com.FerployDemo.ferployDemo;


import com.FerployDemo.ferployDemo.controller.NameCardController;
import com.FerployDemo.ferployDemo.domain.entity.NameCard;
import com.FerployDemo.ferployDemo.repository.NameCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class NameCardTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NameCardRepository nameCardRepository;

    private NameCard nameCard;

    @BeforeEach
    public void setup() {
        // Test data setup
        nameCard = new NameCard();
        nameCard.setName("Test User");
        nameCard.setEmail("testuser@example.com");
    }

    @Test
    public void testCreateNameCard() throws Exception {
        mockMvc.perform(post("/api/v1/namecard/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test User\", \"email\": \"testuser@example.com\"}")) // JSON으로 요청
                .andExpect(status().isOk());

        // Validate that the name card is saved in the database
        Optional<NameCard> savedNameCardOpt = nameCardRepository.findByName("Test User");
        assertTrue(savedNameCardOpt.isPresent()); // Optional이 비어있지 않은지 확인
        NameCard savedNameCard = savedNameCardOpt.get(); // Optional에서 NameCard 객체 가져오기

        assertEquals("Test User", savedNameCard.getName());
        assertEquals("testuser@example.com", savedNameCard.getEmail());
    }

    @Test
    public void testUpdateNameCard() throws Exception {
        // Create a name card first
        nameCardRepository.save(nameCard);

        NameCard updatedNameCard = new NameCard();
        updatedNameCard.setName("Updated User");
        updatedNameCard.setEmail("updateduser@example.com");

        mockMvc.perform(put("/api/v1/namecard/" + nameCard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated User\", \"email\": \"updateduser@example.com\"}")) // JSON으로 요청
                .andExpect(status().isOk());

        // Validate that the name card is updated in the database
        NameCard fetchedNameCard = nameCardRepository.findById(nameCard.getId()).orElse(null);
        assertNotNull(fetchedNameCard);
        assertEquals("Updated User", fetchedNameCard.getName());
        assertEquals("updateduser@example.com", fetchedNameCard.getEmail());
    }
}