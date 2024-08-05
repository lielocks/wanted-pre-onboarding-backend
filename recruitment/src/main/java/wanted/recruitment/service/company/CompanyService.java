package wanted.recruitment.service.company;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.recruitment.domain.company.Company;
import wanted.recruitment.dto.company.CompanyRegisterRequestDto;
import wanted.recruitment.repository.company.CompanyRepository;
import wanted.recruitment.service.location.LocationService;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final LocationService locationService;

    @Transactional
    public Long register(CompanyRegisterRequestDto requestDto) {
        Company savedCompany = companyRepository.save(Company.builder().name(requestDto.getName()).build());
        locationService.register(requestDto, savedCompany);
        return savedCompany.getId();
    }

}
