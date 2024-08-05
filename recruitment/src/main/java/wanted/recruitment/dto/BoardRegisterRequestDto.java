package wanted.recruitment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardRegisterRequestDto {
    private Long companyId;
    private String position;
    private int reward;
    private String description;
    private String skill;
}
