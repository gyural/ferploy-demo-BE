package com.FerployDemo.ferployDemo.service;

import com.FerployDemo.ferployDemo.config.JwtTokenProvider;
import com.FerployDemo.ferployDemo.domain.entity.Member;
import com.FerployDemo.ferployDemo.domain.entity.NameCard;
import com.FerployDemo.ferployDemo.domain.request.PostCreateNameCardRequest;
import com.FerployDemo.ferployDemo.domain.response.GetNameCardListResponse;
import com.FerployDemo.ferployDemo.dto.NameCardDTO;
import com.FerployDemo.ferployDemo.repository.MemberRepository;
import com.FerployDemo.ferployDemo.repository.NameCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    private final MemberRepository memberRepository;

    public NameCardDTO createNameCard(PostCreateNameCardRequest nameCardRequest, String memberId) {
        Member targetMember = getMemberById(memberId);
        NameCard targetNameCard = mapToNameCard(nameCardRequest, targetMember);

        NameCard savedNameCard = nameCardRepository.save(targetNameCard);
        return convertToDTO(savedNameCard);
    }

    private Member getMemberById(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    private NameCard mapToNameCard(PostCreateNameCardRequest nameCardRequest, Member targetMember) {
        NameCard targetNameCard = new NameCard();
        targetNameCard.setMember(targetMember);
        BeanUtils.copyProperties(nameCardRequest, targetNameCard);
        return targetNameCard;
    }
    public NameCardDTO getNameCardById(Long id){
        Optional<NameCard> nameCard = nameCardRepository.findById(id);
        if(!nameCard.isPresent()){
            throw new EntityNotFoundException("Cannot Find NameCard by id = ");
        }
        NameCardDTO dto = new NameCardDTO();
        // member 필드를 제외한 나머지 필드만 복사
        BeanUtils.copyProperties(nameCard.get(), dto, "member");
        return dto;
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
            if (field.getName() == "id") {
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

    public GetNameCardListResponse findAllNameCard(String accToken) throws IllegalAccessException {
        if (!jwtTokenProvider.validateToken(accToken)) {
            throw new IllegalAccessException("Invalid Access Token");
        }

        // Get memberId from token
        Map<String, Object> userInfo = jwtTokenProvider.getUserFromToken(accToken);
        String memberId = (String) userInfo.get("id");

        // Fetch name cards for the member and map to NameCardDTO
        List<NameCardDTO> myNameCardList = nameCardRepository.findNameCardsByMember_Id(memberId).stream()
                .filter(NameCard::isMe)
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        List<NameCardDTO> otherNameCardList = nameCardRepository.findNameCardsByMember_Id(memberId).stream()
                .filter(nameCard -> !nameCard.isMe())
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new GetNameCardListResponse(myNameCardList, otherNameCardList);
    }

    // Helper method to map NameCard to NameCardDTO
    private NameCardDTO convertToDTO(NameCard nameCard) {
        NameCardDTO dto = new NameCardDTO();
        BeanUtils.copyProperties(nameCard, dto, "member");
        return dto;
    }
}
