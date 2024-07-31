package wanted.recruitment.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Location {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public Location(Country country, City city, Region region, Company company) {
        this.id = generateLocationId(country, city, region);
        this.country = country;
        this.city = city;
        this.region = region;
        this.company = company;
    }

    private String generateLocationId(Country country, City city, Region region) {
        return country.getId() + "_" + city.getId() + "_" + region.getId();
    }
}
