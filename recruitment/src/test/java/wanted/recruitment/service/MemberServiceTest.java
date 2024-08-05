package wanted.recruitment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import wanted.recruitment.common.config.QueryDslConfig;
import wanted.recruitment.common.exception.CustomException;
import wanted.recruitment.domain.Member;
import wanted.recruitment.dto.MemberApplyRequestDto;
import wanted.recruitment.repository.member.MemberRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static wanted.recruitment.common.exception.CustomError.*;

@DataJpaTest
@Import({MemberServiceTest.TestConfig.class, QueryDslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    private Long validBoardId;
    private Long memberId;
    private MemberApplyRequestDto requestDto;

    @BeforeEach
    void setUp() {
        validBoardId = 36L;
        Member member = Member.builder().submitted(false).build();
        memberRepository.save(member);
        memberId = member.getId();
        System.out.println("Saved member: " + memberRepository.findById(memberId).orElse(null));
        requestDto = MemberApplyRequestDto.builder().boardId(validBoardId).memberId(memberId).build();
    }
    @Test
    void apply() {
        memberService.memberApply(requestDto);
        assertEquals(memberRepository.findById(memberId).get().isSubmitted(), Boolean.TRUE);
    }

    @Test
    @DisplayName("사용자는 1회만 지원 가능합니다.")
    void memberCanApplyOnce() {
        memberService.memberApply(requestDto); // 이미 지원을 했음
        CustomException customException = assertThrows(CustomException.class, () -> {
            memberService.memberApply(requestDto);
        });

        assertEquals(customException.getCustomError(), SUBMIT_TILL_ONCE);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public MemberService memberService(MemberRepository memberRepository) {
            return new MemberService(memberRepository);
        }
    }

}
