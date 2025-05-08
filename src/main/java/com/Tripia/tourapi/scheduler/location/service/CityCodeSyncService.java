package com.Tripia.tourapi.scheduler.location.service;

import com.Tripia.config.properties.TourApiProperties;
import com.Tripia.tourapi.location.entity.AreaCode;
import com.Tripia.tourapi.location.entity.CityCode;
import com.Tripia.tourapi.location.service.AreaCodeService;
import com.Tripia.tourapi.location.service.CityCodeService;
import com.Tripia.tourapi.scheduler.location.entity.locationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@Service
public class CityCodeSyncService {


    private final WebClient webClient;
    private final TourApiProperties tourApiProperties;
    private final AreaCodeService areaCodeService;
    private final CityCodeService cityCodeService;

    public CityCodeSyncService(@Qualifier("tourApiWebClient")WebClient webClient, TourApiProperties tourApiProperties, AreaCodeService areaCodeService, CityCodeService cityCodeService) {
        this.webClient = webClient;
        this.tourApiProperties = tourApiProperties;
        this.areaCodeService = areaCodeService;
        this.cityCodeService = cityCodeService;
    }

    @Transactional
    public void syncAreaCodes(){
        List<AreaCode> areaCodes = areaCodeService.findAll();

        for (AreaCode areaCode : areaCodes) {

            locationResponse response = fetchAreaCodeItemsFromApi(areaCode.getAreaCode());

            if (response != null && response.getResponse() != null) {
                List<locationResponse.CodeItem> items =
                        response.getResponse().getBody().getItems().getItem();
                cityCodeItemsToDB(items, areaCode.getAreaCode());
            }
        }

    }

    private locationResponse fetchAreaCodeItemsFromApi(String areaCode) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(tourApiProperties.getLocation())
                        .queryParam("numOfRows", 50)
                        .queryParam("pageNo",1)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("areaCode",areaCode)
                        .queryParam("MobileApp", "Tripia")
                        .queryParam("_type", "json")
                        .queryParam("serviceKey", tourApiProperties.getKey())
                        .build())
                .retrieve()
                .bodyToMono(locationResponse.class)
                .block();
    }

    private void cityCodeItemsToDB(List<locationResponse.CodeItem> items, String areaCode) {
        for (locationResponse.CodeItem item : items) {
            Optional<AreaCode> findAreaCode = areaCodeService.findByAreaCode(areaCode);
            Optional<CityCode> findCityCode = cityCodeService.findByCityCodeAndAreaCodeId(item.getCode(), findAreaCode.get().getAreaCodeId());
            if (findCityCode.isPresent()){
                CityCode savedCityCode = findCityCode.get();
                if (!isEquals(item, savedCityCode)) {
                    savedCityCode.changeAll(savedCityCode.getName(), savedCityCode.getCityCode());
                }
            }else{
                saveCityCode(item.getName(), item.getCode(), areaCode);
            }

        }

    }

    private boolean isEquals(locationResponse.CodeItem ciTyCode, CityCode savedCityCode) {
        return Objects.equals(ciTyCode.getCode(), savedCityCode.getCityCode())
                && Objects.equals(ciTyCode.getName(), savedCityCode.getName());
    }

    private void saveCityCode(String name, String cityCode, String areaCode) {
        Optional<AreaCode> findAreaCode = areaCodeService.findByAreaCode(areaCode);

        if (findAreaCode.isEmpty()) {
            log.warn("AreaCode가 존재하지 않아 CityCode 저장을 생략함: name={}, code={}", name, cityCode);
            return;
        }

        cityCodeService.save(CityCode.createCityCode(cityCode, name, findAreaCode.get()));
    }

}
