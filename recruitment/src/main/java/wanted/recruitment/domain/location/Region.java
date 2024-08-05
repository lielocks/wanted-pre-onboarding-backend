package wanted.recruitment.domain.location;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Region {
    @Id
    @Column(name = "region_id")
    private Long id;

    private String name;
}