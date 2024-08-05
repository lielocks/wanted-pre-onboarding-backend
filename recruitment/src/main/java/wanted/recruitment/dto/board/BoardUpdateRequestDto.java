package wanted.recruitment.dto.board;

import lombok.Data;

@Data
public class BoardUpdateRequestDto {
    private Long boardId;
    private String position;
    private Integer reward;
    private String description;
    private String skill;
}
