package wanted.recruitment.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.recruitment.common.exception.CustomError;
import wanted.recruitment.common.exception.CustomException;
import wanted.recruitment.domain.member.Member;
import wanted.recruitment.dto.member.MemberApplyRequestDto;
import wanted.recruitment.repository.member.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void memberApply(MemberApplyRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new CustomException(CustomError.MEMBER_NOT_FOUND));

        if (!member.isSubmitted()) {
            member.setSubmitted();
        } else throw new CustomException(CustomError.SUBMIT_TILL_ONCE);
    }
}
