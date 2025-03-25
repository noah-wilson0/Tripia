package com.Tripia.tourapi.scheduler.location.service;

import com.Tripia.tourapi.TourApiProperties;
import com.Tripia.tourapi.location.entity.AreaCode;
import com.Tripia.tourapi.location.repository.AreaCodeRepository;
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

    private final WebClient tourApiWebClient;
    private final TourApiProperties tourApiProperties;
    private final AreaCodeService areaCodeService;

    @Transactional
    public void syncAreaCodes(){
        locationResponse result = tourApiWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(tourApiProperties.getLocation())
                        .queryParam("numOfRows",20)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "Tripia")
                        .queryParam("_type", "json")
                        .queryParam("serviceKey", tourApiProperties.getKey())
                        .build())
                .retrieve()
                .bodyToMono(locationResponse.class)
                .block();

        if (result != null && result.getResponse() != null) {
            List<locationResponse.AreaCodeItem> items =
                    result.getResponse().getBody().getItems().getItem();
            syncAreaCodesHandler(items);
        }

    }

    private void syncAreaCodesHandler(List<locationResponse.AreaCodeItem> items) {
        for (locationResponse.AreaCodeItem item : items) {
            String name = item.getName();
            String code = item.getCode();

            AreaCode existing = areaCodeService.findByName(name);

            if (existing == null) {
                saveAreaCode(name, code);
            } else if (!existing.getCode().equals(code)) {
                updateAreaCode(existing, code);
            }
        }

    }

    private static void updateAreaCode(AreaCode existing, String code) {
        existing.changeCode(code);
    }

    private void saveAreaCode(String name, String code) {
        areaCodeService.save(new AreaCode(null, name, code));
    }
}
