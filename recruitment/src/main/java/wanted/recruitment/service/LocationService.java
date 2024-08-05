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

        if (locations.get(1) != 8L && locations.size() != 3) {
            throw new CustomException(CHOOSE_ALL_LOCATIONS);
        }

        Long countryId = locations.get(0);
        Long cityId = locations.get(1);
        Long regionId = locations.get(2);

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new CustomException(COUNTRY_NOT_EXIST));
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CustomException(CITY_NOT_EXIST));
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new CustomException(REGION_NOT_EXIST));

        if (countryId == 1L && (cityId < 1L || cityId > 17L)) {
            throw new CustomException(KOREA_CITY_NOT_MATCH);
        }
        if (!isValidRegionForCity(cityId, regionId)) {
            throwRegionNotMatchException(cityId);
        }

        Location location = locationRepository.save(new Location(country, city, region, company));
        company.setLocation(location);
    }

    private boolean isValidRegionForCity(Long cityId, Long regionId) {
        switch (cityId.intValue()) {
            case 1: return regionId >= 1 && regionId <= 25;
            case 2: return regionId >= 26 && regionId <= 41;
            case 3: return regionId >= 42 && regionId <= 49;
            case 4: return regionId >= 50 && regionId <= 59;
            case 5: return regionId >= 60 && regionId <= 64;
            case 6: return regionId >= 62 && regionId <= 69;
            case 7: return regionId >= 70 && regionId <= 74;
            case 8: return true; // 세종은 시/도가 없음
            case 9: return regionId >= 75 && regionId <= 105;
            case 10: return regionId >= 106 && regionId <= 123;
            case 11: return regionId >= 124 && regionId <= 135;
            case 12: return regionId >= 136 && regionId <= 150;
            case 13: return regionId >= 151 && regionId <= 164;
            case 14: return regionId >= 165 && regionId <= 186;
            case 15: return regionId >= 187 && regionId <= 209;
            case 16: return regionId >= 210 && regionId <= 227;
            case 17: return regionId >= 228 && regionId <= 229;
            default: return false;
        }
    }

    private void throwRegionNotMatchException(Long cityId) {
        switch (cityId.intValue()) {
            case 1:
                throw new CustomException(SEOUL_REGION_NOT_MATCH);
            case 2:
                throw new CustomException(BUSAN_REGION_NOT_MATCH);
            case 3:
                throw new CustomException(DAEGU_REGION_NOT_MATCH);
            case 4:
                throw new CustomException(INCHEON_REGION_NOT_MATCH);
            case 5:
                throw new CustomException(GWANGJU_REGION_NOT_MATCH);
            case 6:
                throw new CustomException(DAEJEON_REGION_NOT_MATCH);
            case 7:
                throw new CustomException(ULSAN_REGION_NOT_MATCH);
            case 8:
                throw new CustomException(SAEJONG_REGION_NOT_MATCH);
            case 9:
                throw new CustomException(KYUNGKI_REGION_NOT_MATCH);
            case 10:
                throw new CustomException(KANGWON_REGION_NOT_MATCH);
            case 11:
                throw new CustomException(CHUNGBOOK_REGION_NOT_MATCH);
            case 12:
                throw new CustomException(CHUNGNAM_REGION_NOT_MATCH);
            case 13:
                throw new CustomException(JEONBOOK_REGION_NOT_MATCH);
            case 14:
                throw new CustomException(JEONNAM_REGION_NOT_MATCH);
            case 15:
                throw new CustomException(KYEONGBOOK_REGION_NOT_MATCH);
            case 16:
                throw new CustomException(KYEONGNAM_REGION_NOT_MATCH);
            case 17:
                throw new CustomException(JEJU_REGION_NOT_MATCH);
            default:
                throw new CustomException(REGION_NOT_EXIST);
        }
    }

}
