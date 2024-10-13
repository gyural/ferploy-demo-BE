package com.FerployDemo.ferployDemo.repository;

import com.FerployDemo.ferployDemo.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository <Member, String> {

}
