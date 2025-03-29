package com.Tripia.tourapi.scheduler.location.service;

import com.Tripia.tourapi.TourApiProperties;
import com.Tripia.tourapi.location.entity.AreaCode;
import com.Tripia.tourapi.location.service.AreaCodeService;
import com.Tripia.tourapi.scheduler.location.entity.locationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AreaCodeSyncService {

    private final WebClient webClient;
    private final TourApiProperties tourApiProperties;
    private final AreaCodeService areaCodeService;

    @Transactional
    public void syncAreaCodes(){
        locationResponse response = fetchAreaCodeItemsFromApi();

        if (response != null && response.getResponse() != null) {
            List<locationResponse.AreaCodeItem> items =
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

    private void areaCodeItemsToDB(List<locationResponse.AreaCodeItem> items) {
        for (locationResponse.AreaCodeItem item : items) {
            saveAreaCode(item.getName(), item.getCode());
        }

    }

    private void saveAreaCode(String name, String code) {
        areaCodeService.save(new AreaCode(null, name, code));
    }
}
