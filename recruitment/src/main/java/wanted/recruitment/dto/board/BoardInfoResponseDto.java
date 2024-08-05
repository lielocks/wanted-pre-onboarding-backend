package wanted.recruitment.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardInfoResponseDto {
    private Long companyId;
    private String companyName;
    private String countryName;
    private String cityName;
    private String position;
    private int reward;
    private String skill;

    @QueryProjection
    public BoardInfoResponseDto(Long companyId, String companyName, String countryName, String cityName, String position, int reward, String skill) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.countryName = countryName;
        this.cityName = cityName;
        this.position = position;
        this.reward = reward;
        this.skill = skill;
    }

}
