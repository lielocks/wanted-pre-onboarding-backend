package wanted.recruitment.dto.board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardDocumentResponseDto {
    private Long boardId;
    private String companyName;
    private String countryName;
    private String cityName;
    private String position;
    private int reward;
    private String skill;
}
