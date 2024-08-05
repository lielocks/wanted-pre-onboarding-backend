package wanted.recruitment.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import wanted.recruitment.common.config.QueryDslConfig;
import wanted.recruitment.domain.company.Company;
import wanted.recruitment.dto.company.CompanyRegisterRequestDto;
import wanted.recruitment.repository.company.CompanyRepository;
import wanted.recruitment.service.company.CompanyService;
import wanted.recruitment.service.location.LocationService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({LocationServiceTest.TestConfig.class, QueryDslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CompanyServiceTest {

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    LocationService locationService;

    @Test
    void companyRegister() {
        List<Long> locationList = Arrays.asList(1L, 9L, 77L);
        CompanyRegisterRequestDto requestDto = CompanyRegisterRequestDto.builder().locations(locationList).name("인프런").build();
        Long companyId = companyService.register(requestDto);

        Company savedCompany = companyRepository.findById(companyId).get();
        assertEquals(savedCompany.getName(), "인프런");
        assertEquals(savedCompany.getLocation().getCountry().getId(), 1L);
        assertEquals(savedCompany.getLocation().getCity().getId(), 9L);
        assertEquals(savedCompany.getLocation().getRegion().getId(), 77L);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CompanyService companyService(CompanyRepository companyRepository, LocationService locationService) {
            return new CompanyService(companyRepository, locationService);
        }
    }

}
