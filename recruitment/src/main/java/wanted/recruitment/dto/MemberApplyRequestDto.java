package wanted.recruitment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberApplyRequestDto {
    private Long boardId;
    private Long memberId;
}
