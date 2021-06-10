package toy.shoppingmall.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.Member;
import toy.shoppingmall.repository.MemberRepository;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        //given
        Member member = new Member("kim");

        //when
        memberService.join(member);

        //then
        assertThat(member).isEqualTo(memberRepository.findById(member.getId()).get());
     }

     @Test
     public void 중복_회원_예외() {
         //given
         Member member1 = new Member("kim");
         Member member2 = new Member("kim");

         //when
         memberService.join(member1);

         //then
         assertThrows(IllegalStateException.class, () -> memberService.join(member2));
     }
}