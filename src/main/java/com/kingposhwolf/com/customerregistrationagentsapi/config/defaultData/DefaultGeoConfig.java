package com.kingposhwolf.com.customerregistrationagentsapi.config.defaultData;

import com.kingposhwolf.com.customerregistrationagentsapi.models.District;
import com.kingposhwolf.com.customerregistrationagentsapi.models.Region;
import com.kingposhwolf.com.customerregistrationagentsapi.models.Ward;
import com.kingposhwolf.com.customerregistrationagentsapi.repositories.district.DistrictRepository;
import com.kingposhwolf.com.customerregistrationagentsapi.repositories.region.RegionRepository;
import com.kingposhwolf.com.customerregistrationagentsapi.repositories.ward.WardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DefaultGeoConfig {

    private static final String DAR_ES_SALAAM = "Dar es Salaam";
    private static final Map<String, String[]> DISTRICT_WARDS = Map.of(
            "Ilala", new String[]{"Mchikichini", "Kariakoo"},
            "Kinondoni", new String[]{"Msasani", "Kinondoni"},
            "Temeke", new String[]{"Temeke Ward1", "Temeke Ward2"}
    );

    @Bean
    CommandLineRunner initGeoData(RegionRepository regionRepository,
                                  DistrictRepository districtRepository,
                                  WardRepository wardRepository) {
        return args -> {
            Region region = findOrCreateRegion(regionRepository, DAR_ES_SALAAM);

            DISTRICT_WARDS.forEach((districtName, wards) -> {
                District district = findOrCreateDistrict(districtRepository, districtName, region);
                createWards(wardRepository, wards, district);
            });
        };
    }

    private Region findOrCreateRegion(RegionRepository repository, String name) {
        return repository.findByName(name).orElseGet(() -> {
            Region region = new Region();
            region.setName(name);
            repository.save(region);
            log.info("Region created: {}", name);
            return region;
        });
    }

    private District findOrCreateDistrict(DistrictRepository repository, String name, Region region) {
        return repository.findByName(name).orElseGet(() -> {
            District district = new District();
            district.setName(name);
            district.setRegion(region);
            repository.save(district);
            log.info("District created: {}", name);
            return district;
        });
    }

    private void createWards(WardRepository repository, String[] wardNames, District district) {
        for (String wardName : wardNames) {
            repository.findByName(wardName).orElseGet(() -> {
                Ward ward = new Ward();
                ward.setName(wardName);
                ward.setDistrict(district);
                repository.save(ward);
                log.info("Ward created: {}", wardName);
                return ward;
            });
        }
    }
}