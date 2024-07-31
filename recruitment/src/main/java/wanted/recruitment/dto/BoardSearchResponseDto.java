package wanted.recruitment.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class BoardSearchResponseDto {

    private Long boardId;
    private String companyName;
    private String countryName;
    private String cityName;
    private String position;
    private int reward;
    private String skill;

    @QueryProjection
    public BoardSearchResponseDto(Long boardId, String companyName, String countryName, String cityName, String position, int reward, String skill) {
        this.boardId = boardId;
        this.companyName = companyName;
        this.countryName = countryName;
        this.cityName = cityName;
        this.position = position;
        this.reward = reward;
        this.skill = skill;
    }
}
