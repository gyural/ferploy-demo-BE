package com.FerployDemo.ferployDemo.repository;

import com.FerployDemo.ferployDemo.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

}
