package wanted.recruitment.dto;

import lombok.Data;

@Data
public class BoardRegisterRequestDto {
    private Long companyId;
    private String position;
    private int reward;
    private String description;
    private String skill;
}
