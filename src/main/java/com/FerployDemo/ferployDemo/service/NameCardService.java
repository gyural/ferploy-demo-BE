package com.FerployDemo.ferployDemo.service;

import com.FerployDemo.ferployDemo.domain.entity.NameCard;
import com.FerployDemo.ferployDemo.repository.NameCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Optional;

@Service
public class NameCardService {
    private NameCardRepository nameCardRepository;

    public NameCardService(NameCardRepository nameCardRepository) {
        this.nameCardRepository = nameCardRepository;
    }

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
}
