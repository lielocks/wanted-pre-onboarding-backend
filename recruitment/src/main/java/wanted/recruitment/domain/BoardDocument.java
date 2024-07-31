package wanted.recruitment.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Mapping(mappingPath = "elastic/board-mapping.json")
@Setting(settingPath = "elastic/board-setting.json")
@Document(indexName = "board")
public class BoardDocument {

    @Id
    @Field(type = FieldType.Keyword)
    private Long boardId;

    @Field(type = FieldType.Text)
    private String companyName;

    @Field(type = FieldType.Text)
    private String countryName;

    @Field(type = FieldType.Text)
    private String cityName;

    @Field(type = FieldType.Text)
    private String position;

    @Field(type = FieldType.Integer)
    private int reward;

    @Field(type = FieldType.Text)
    private String skill;

    @Builder
    public BoardDocument(Long boardId, String companyName, String countryName, String cityName, String position, int reward, String skill) {
        this.boardId = boardId;
        this.companyName = companyName;
        this.countryName = countryName;
        this.cityName = cityName;
        this.position = position;
        this.reward = reward;
        this.skill = skill;
    }

    public static BoardDocument from(Board board) {
        return BoardDocument.builder()
                .boardId(board.getId())
                .companyName(board.getCompany().getName())
                .countryName(board.getCompany().getLocation().getCountry().getName())
                .cityName(board.getCompany().getLocation().getCity().getName())
                .position(board.getPosition())
                .reward(board.getReward())
                .skill(board.getSkill())
                .build();
    }

}
