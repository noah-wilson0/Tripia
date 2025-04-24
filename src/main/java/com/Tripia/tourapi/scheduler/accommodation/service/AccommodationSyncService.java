package com.Tripia.tourapi.scheduler.accommodation.service;


import com.Tripia.tourapi.TourApiProperties;
import com.Tripia.tourapi.accommodation.entity.Accommodation;
import com.Tripia.tourapi.accommodation.service.AccommodationService;
import com.Tripia.tourapi.scheduler.accommodation.entity.AccommodationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AccommodationSyncService {
    private final WebClient webClient;
    private final TourApiProperties tourApiProperties;
    private final AccommodationService accommodationService;

    @Transactional
    public void syncAccommodation() {
        AccommodationResponse response = fetchAccommodationItemsFromApi();
        if (response != null && response.getResponse() != null) {
            List<AccommodationResponse.AccommodationItem> accommodationItems =
                    response.getResponse().getBody().getItems().getItem();

            accommodationItemsToDB(accommodationItems);
        }
    }


    private AccommodationResponse fetchAccommodationItemsFromApi() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(tourApiProperties.getAccommodation())
                        .queryParam("numOfRows", 4000)
                        .queryParam("pageNo", 1)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "Tripia")
                        .queryParam("_type", "json")
                        .queryParam("serviceKey", tourApiProperties.getKey())
                        .build())

                .retrieve()
                .bodyToMono(AccommodationResponse.class)
                .block();
    }

    private void accommodationItemsToDB(List<AccommodationResponse.AccommodationItem> accommodationItems) {



        for (AccommodationResponse.AccommodationItem accommodationItem : accommodationItems) {
            Optional<Accommodation> existing = accommodationService.findByContentId(accommodationItem.getContentid());
            if (existing.isPresent()) {
                Accommodation savedAccommodation  = existing.get();
                if (!isEquals(accommodationItem, savedAccommodation)) {
                    savedAccommodation.changeAll(
                        accommodationItem.getContentid(),
                        accommodationItem.getTitle(),
                        accommodationItem.getAreacode(),
                        accommodationItem.getAddr1(),
                        accommodationItem.getAddr2(),
                        accommodationItem.getFirstimage(),
                        accommodationItem.getMapx(),
                        accommodationItem.getMapy(),
                        accommodationItem.getTel()
                    );
                }
            } else {
                accommodationService.save(new Accommodation(
                        null,
                        accommodationItem.getContentid(),
                        accommodationItem.getTitle(),
                        accommodationItem.getAreacode(),
                        accommodationItem.getAddr1(),
                        accommodationItem.getAddr2(),
                        accommodationItem.getFirstimage(),
                        accommodationItem.getMapx(),
                        accommodationItem.getMapy(),
                        accommodationItem.getTel()
                ));

            }
        }
    }

    private static boolean isEquals(AccommodationResponse.AccommodationItem item, Accommodation saved) {
        return Objects.equals(saved.getContentId(),item.getContentid())
                && Objects.equals(saved.getTitle(), item.getTitle())
                && Objects.equals(saved.getCode(), item.getAreacode())
                && Objects.equals(saved.getAddr1(), item.getAddr1())
                && Objects.equals(saved.getAddr2(), item.getAddr2())
                && Objects.equals(saved.getImage(), item.getFirstimage())
                && Objects.equals(saved.getLongitude(), item.getMapx())
                && Objects.equals(saved.getLatitude(), item.getMapy())
                && Objects.equals(saved.getTel(), item.getTel());
    }



}
