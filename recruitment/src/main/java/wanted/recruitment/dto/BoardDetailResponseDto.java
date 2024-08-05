package wanted.recruitment.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailResponseDto {
    private Long companyId;
    private String companyName;
    private String countryName;
    private String cityName;
    private String position;
    private int reward;
    private String skill;
    private String description;
    private List<Long> boardIdList;

    @QueryProjection
    public BoardDetailResponseDto(Long companyId, String companyName, String countryName, String cityName, String position, int reward, String skill, String description) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.countryName = countryName;
        this.cityName = cityName;
        this.position = position;
        this.reward = reward;
        this.skill = skill;
        this.description = description;
    }

}
