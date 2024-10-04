package com.FerployDemo.ferployDemo.service;

import com.FerployDemo.ferployDemo.domain.entity.Member;
import com.FerployDemo.ferployDemo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member saveMember(String email, String name, String profilePicture) {
        Member member = new Member();
        member.setEmail(email);
        member.setName(name);
        member.setProfilePicture(profilePicture);

        return memberRepository.save(member);
    }
}
