package wanted.recruitment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.recruitment.common.exception.CustomException;
import wanted.recruitment.domain.*;
import wanted.recruitment.dto.CompanyRegisterRequestDto;
import wanted.recruitment.repository.location.CityRepository;
import wanted.recruitment.repository.location.CountryRepository;
import wanted.recruitment.repository.location.LocationRepository;
import wanted.recruitment.repository.location.RegionRepository;

import java.util.List;

import static wanted.recruitment.common.exception.CustomError.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;

    @Transactional
    public void register(CompanyRegisterRequestDto requestDto, Company company) {
        List<Long> locations = requestDto.getLocations();

        if (locations.size() < 3) {
            throw new CustomException(CHOOSE_ALL_LOCATIONS);
        }

        Country country = countryRepository.findById(locations.get(0))
                .orElseThrow(() -> new CustomException(COUNTRY_NOT_EXIST));
        City city = cityRepository.findById(locations.get(1))
                .orElseThrow(() -> new CustomException(CITY_NOT_EXIST));
        Region region = regionRepository.findById(locations.get(2))
                .orElseThrow(() -> new CustomException(REGION_NOT_EXIST));

        locationRepository.save(new Location(country, city, region, company));
    }

}
