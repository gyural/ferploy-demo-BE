package com.FerployDemo.ferployDemo.repository;

import com.FerployDemo.ferployDemo.domain.entity.NameCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NameCardRepository extends JpaRepository<NameCard, Long> {
    Optional<NameCard> findByName(String name);
}
