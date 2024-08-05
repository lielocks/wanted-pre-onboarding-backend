package wanted.recruitment.domain;

import jakarta.persistence.*;
import lombok.*;
import wanted.recruitment.common.model.BaseTimeEntity;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Board extends BaseTimeEntity {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int reward;

    private String description;

    private String position;

    private String skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public void setPosition(String position) {
        this.position = position;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", reward=" + reward +
                ", description='" + description + '\'' +
                ", position='" + position + '\'' +
                ", skill='" + skill + '\'' +
                '}';
    }
}