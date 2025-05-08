package com.Tripia.tourapi.scheduler.location.service;

import com.Tripia.config.properties.TourApiProperties;
import com.Tripia.tourapi.location.entity.AreaCode;
import com.Tripia.tourapi.location.service.AreaCodeService;
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

public class AreaCodeSyncService {


    private final WebClient webClient;
    private final TourApiProperties tourApiProperties;
    private final AreaCodeService areaCodeService;

    public AreaCodeSyncService(@Qualifier("tourApiWebClient")WebClient webClient, TourApiProperties tourApiProperties, AreaCodeService areaCodeService) {
        this.webClient = webClient;
        this.tourApiProperties = tourApiProperties;
        this.areaCodeService = areaCodeService;
    }

    @Transactional
    public void syncAreaCodes(){
        locationResponse response = fetchAreaCodeItemsFromApi();

        if (response != null && response.getResponse() != null) {
            List<locationResponse.CodeItem> items =
                    response.getResponse().getBody().getItems().getItem();
            areaCodeItemsToDB(items);
        }

    }

    private locationResponse fetchAreaCodeItemsFromApi() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(tourApiProperties.getLocation())
                        .queryParam("numOfRows", 20)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "Tripia")
                        .queryParam("_type", "json")
                        .queryParam("serviceKey", tourApiProperties.getKey())
                        .build())
                .retrieve()
                .bodyToMono(locationResponse.class)
                .block();
    }

    private void areaCodeItemsToDB(List<locationResponse.CodeItem> items) {
        for (locationResponse.CodeItem item : items) {
            Optional<AreaCode> existing = areaCodeService.findByName(item.getName());
            if (existing.isPresent()){
                AreaCode savedAreaCode = existing.get();
                if (!isEquals(item, savedAreaCode)) {
                    log.info("변경");
                    savedAreaCode.changeAll(savedAreaCode.getName(), savedAreaCode.getAreaCode());
                }
            }else{
                log.info("저장{}",item.toString());
                saveAreaCode(item.getName(), item.getCode());
            }

        }

    }

    private boolean isEquals(locationResponse.CodeItem areaCode, AreaCode savedAreaCode) {
        return Objects.equals(areaCode.getCode(), savedAreaCode.getAreaCode())
                && Objects.equals(areaCode.getName(), savedAreaCode.getName());
    }

    private void saveAreaCode(String name, String code) {
        areaCodeService.save(new AreaCode(null, null,name, code));
    }
}
