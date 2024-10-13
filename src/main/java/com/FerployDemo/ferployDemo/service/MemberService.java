package com.FerployDemo.ferployDemo.service;

import com.FerployDemo.ferployDemo.common.Role;
import com.FerployDemo.ferployDemo.domain.entity.Member;
import com.FerployDemo.ferployDemo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member saveMember(String id, String email, String name, String profilePicture,
                             Role role, String accToken, String refreshToken) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            //토큰이 변화 없다면 기존 member리턴
            if(member.get().getGoogle_acc() == accToken && member.get().getGoogle_refresh() == refreshToken) {
                return member.get();
            }
            //토큰 변화가 있다면 갱신된 member리턴
            member.get().setGoogle_acc(accToken);
            member.get().setGoogle_refresh(refreshToken);
            return member.get();
        }

        Member newMember = new Member();
        newMember.setId(id);
        newMember.setEmail(email);
        newMember.setName(name);
        newMember.setProfilePicture(profilePicture);
        newMember.setRole(role);
        newMember.setGoogle_acc(accToken);
        newMember.setGoogle_refresh(refreshToken);

        return memberRepository.save(newMember);
    }
}
