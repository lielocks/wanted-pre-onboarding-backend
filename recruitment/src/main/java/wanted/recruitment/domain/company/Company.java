package wanted.recruitment.domain.company;

import jakarta.persistence.*;
import lombok.*;
import wanted.recruitment.domain.board.Board;
import wanted.recruitment.domain.location.Location;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Company {
    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(mappedBy = "company",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Location location;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    public void setLocation(Location location) {
        this.location = location;
    }
}
