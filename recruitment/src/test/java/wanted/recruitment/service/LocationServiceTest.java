package wanted.recruitment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import wanted.recruitment.common.config.QueryDslConfig;
import wanted.recruitment.common.exception.CustomException;
import wanted.recruitment.domain.company.Company;
import wanted.recruitment.domain.location.Location;
import wanted.recruitment.dto.company.CompanyRegisterRequestDto;
import wanted.recruitment.repository.company.CompanyRepository;
import wanted.recruitment.repository.location.CityRepository;
import wanted.recruitment.repository.location.CountryRepository;
import wanted.recruitment.repository.location.LocationRepository;
import wanted.recruitment.repository.location.RegionRepository;
import wanted.recruitment.service.location.LocationService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static wanted.recruitment.common.exception.CustomError.*;

@DataJpaTest
@Import({LocationServiceTest.TestConfig.class, QueryDslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class LocationServiceTest {

    @Autowired
    LocationService locationService;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    CompanyRepository companyRepository;

    private Company company;

    @BeforeEach
    void setUp() {
        company = companyRepository.findById(6L).get();
    }

    @Test
    @DisplayName("location entity 내 generateLocationId 검증")
    void generateLocationId() {
        CompanyRegisterRequestDto requestDto = CompanyRegisterRequestDto.builder().locations(Arrays.asList(1L, 9L, 77L)).name("인프런").build();
        locationService.register(requestDto, company);
        Location location = locationRepository.findById("1_9_77").get();

        assertEquals(location.getId(), "1_9_77");

        assertEquals(location.getCountry().getId(), 1L);
        assertEquals(location.getCountry().getName(), "한국");

        assertEquals(location.getCity().getId(), 9L);
        assertEquals(location.getCity().getName(), "경기");

        assertEquals(location.getRegion().getId(), 77L);
        assertEquals(location.getRegion().getName(), "성남시");
    }

    @Test
    @DisplayName("country, city, region 을 모두 등록해야 한다")
    void locationListSizeMustBe3() {
        List<Long> locationList = Arrays.asList(1L, 2L);
        CustomException customException = assertThrows(CustomException.class, () -> {
            locationService.register(CompanyRegisterRequestDto.builder().locations(locationList).name("인프런").build(), company);
        });

        assertEquals(customException.getCustomError(), CHOOSE_ALL_LOCATIONS);
    }

    @Test
    // 1 (25) 2 (26 41) 3 (42 49) 4 (50 59) 5 (60 64) 6 (62 69) 7 (70 74) 8 (x) 9 (75 105) 10 (106 123) 11 (124 135) 12 (136 150) 13 (151 164) 14 (165 186) 15 (187 209) 16 (210 227) 17 (228 229)
    @DisplayName("country 에 속하지 않는 city id 일때 throw error")
    void validCountryCityId() {
        List<Long> locationList = Arrays.asList(1L, 18L, 100L);
        CustomException customException = assertThrows(CustomException.class, () -> {
            locationService.register(CompanyRegisterRequestDto.builder().locations(locationList).name("인프런").build(), company);
        });

        assertEquals(customException.getCustomError(), KOREA_CITY_NOT_MATCH);
    }

    @Test
    @DisplayName("city 에 속하지 않는 region id 일때 throw error, `cityId 9L 인 경기` 에 속하지 않는 `regionId 228L 서귀포시")
    void validCityRegionId() {
        List<Long> locationList = Arrays.asList(1L, 9L, 228L);

        CustomException customException = assertThrows(CustomException.class, () -> {
            locationService.register(CompanyRegisterRequestDto.builder().locations(locationList).name("인프런").build(), company);
        });

        assertEquals(customException.getCustomError(), KYUNGKI_REGION_NOT_MATCH);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public LocationService locationService(LocationRepository locationRepository, CountryRepository countryRepository, CityRepository cityRepository, RegionRepository regionRepository) {
            return new LocationService(locationRepository, countryRepository,cityRepository, regionRepository);
        }
    }

}
