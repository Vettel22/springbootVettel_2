package likelion.springbootvettel.service;

import likelion.springbootvettel.domain.Member;

import java.util.List;

public interface MemberService {
    public Member save(Member member);

    public Member findById(Long id);

    public List<Member> findAll();
}
