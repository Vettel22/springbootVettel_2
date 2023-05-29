package likelion.springbootvettel.service;

import likelion.springbootvettel.domain.Member;
import likelion.springbootvettel.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MemberServiceImpl implements MemberService
{
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> findAll()
    {
        return memberRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Member findById(Long id)
    {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent())
        {
            return optionalMember.get();
        }
        else
        {
            throw new IllegalStateException("예외발생");
        }
    }
}
