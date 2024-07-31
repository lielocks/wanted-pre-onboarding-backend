package wanted.recruitment.dto;

import lombok.Data;

@Data
public class BoardUpdateRequestDto {
    private Long boardId;
    private String position;
    private Integer reward;
    private String description;
    private String skill;
}
