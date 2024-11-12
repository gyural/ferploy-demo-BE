package com.FerployDemo.ferployDemo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<NameCard> nameCards = new HashSet<>();

    @ManyToOne
    private Member member;

    public void addNameCard(NameCard nameCard) {
        nameCards.add(nameCard);
        nameCard.getCategories().add(this);
    }

    public void removeNameCard(NameCard nameCard) {
        nameCards.remove(nameCard);
        nameCard.getCategories().remove(this);
    }
}
