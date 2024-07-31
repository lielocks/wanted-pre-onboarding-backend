package wanted.recruitment.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class City {
    @Id
    @Column(name = "city_id")
    private Long id;

    private String name;
}