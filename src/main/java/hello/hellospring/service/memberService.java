package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class memberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 회원가입
    public Long join (Member member) {

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
/*        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(member1 -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }); */

        // 여기서 잠깐! Optional을 그대로 꺼내는 건 권장되지 않는다.
        // 그래서 위의 식을 아래와 같이 변경했다.
        // findByName()의 결과가 Optional로 반환되기에 가능한 일!

        memberRepository.findByName(member.getName())
                .ifPresent(member1 -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
       return memberRepository.findAll();
    }

    // 회원 한 명 조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
