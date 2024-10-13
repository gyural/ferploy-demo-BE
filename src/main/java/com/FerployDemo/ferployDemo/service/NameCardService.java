package com.FerployDemo.ferployDemo.service;

import com.FerployDemo.ferployDemo.config.JwtTokenProvider;
import com.FerployDemo.ferployDemo.domain.entity.NameCard;
import com.FerployDemo.ferployDemo.dto.NameCardDTO;
import com.FerployDemo.ferployDemo.repository.NameCardRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NameCardService {
    private final NameCardRepository nameCardRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public NameCard createNameCard(NameCard nameCard) {
        NameCard savedNameCard = nameCardRepository.save(nameCard);
        Optional<NameCard> newNameCard = Optional.ofNullable(savedNameCard);

        if(newNameCard.isPresent()){
            return newNameCard.get();
        }
        return null;

    }
    // Update existing NameCard by id
    public NameCard updateNameCard(Long id, NameCard newNameCard) {
        return nameCardRepository.findById(id).map(existingNameCard -> {
            updateFields(existingNameCard, newNameCard);
            return nameCardRepository.save(existingNameCard);
        }).orElse(null);
    }
    public void updateFields(NameCard existingNameCard, NameCard newNameCard) {
        Field[] fields = NameCard.class.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            // ID는 갱신 X
            if (field.getName() == "id"){
                continue;
            }
            try {
                Object newValue = field.get(newNameCard);
                if (newValue != null) {
                    field.set(existingNameCard, newValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public List<NameCardDTO> findAllNameCard(String accToken) throws IllegalAccessException {
        if (jwtTokenProvider.validateToken(accToken)) {
            Map<String, Object> userInfo = jwtTokenProvider.getUserFromToken(accToken);
            String memberId = (String) userInfo.get("id");

            List<NameCard> nameCards = nameCardRepository.findNameCardsByMember_Id(memberId);

            return nameCards.stream()
                    .map(nameCard -> {
                        NameCardDTO dto = new NameCardDTO();
                        // member 필드를 제외한 나머지 필드만 복사
                        BeanUtils.copyProperties(nameCard, dto, "member");
                        return dto;
                    })
                    .collect(Collectors.toList());
        } else {
            throw new IllegalAccessException("Invalid Access Token");
        }
    }
}
