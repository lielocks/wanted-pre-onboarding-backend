package wanted.recruitment.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Country {
    @Id
    @Column(name = "country_id")
    private Long id;

    private String name;
}
