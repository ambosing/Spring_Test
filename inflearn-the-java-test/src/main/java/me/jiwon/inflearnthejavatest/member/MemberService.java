package me.jiwon.inflearnthejavatest.member;


import me.jiwon.inflearnthejavatest.domain.Member;
import me.jiwon.inflearnthejavatest.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}
