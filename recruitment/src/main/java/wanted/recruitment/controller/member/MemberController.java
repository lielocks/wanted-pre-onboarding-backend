package wanted.recruitment.controller.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wanted.recruitment.dto.member.MemberApplyRequestDto;
import wanted.recruitment.service.member.MemberService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public/member")
public class MemberController {

    private final MemberService memberService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/apply")
    public void apply(@RequestBody MemberApplyRequestDto requestDto) {
        memberService.memberApply(requestDto);
    }

}
