package com.Tripia.tourapi.scheduler.touristspot.service;

import com.Tripia.tourapi.TourApiProperties;
import com.Tripia.tourapi.scheduler.touristspot.entity.TourSpotResponse;
import com.Tripia.tourapi.touristspot.entity.TourSpot;
import com.Tripia.tourapi.touristspot.service.TourSpotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * tel만 255길이를 넘어서는 문제가 있으므로 임시방편으로 정제 메서드 추가하겠음.
 * 추 후 다른 필드들도 정제할 수 있게 변경하기
 */
@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class TourSpotSyncService {

    private final WebClient webClient;
    private final TourApiProperties tourApiProperties;
    private final TourSpotService tourSpotService;
    @Transactional
    public void SyncTourSpot() {
        TourSpotResponse tourSpotResponse = fetchTourSpotItemsFromApi();
        if (tourSpotResponse != null && tourSpotResponse.getResponse() !=null) {
            List<TourSpotResponse.TourSpotItem> tourSpotItems = tourSpotResponse.getResponse().getBody().getItems().getItem();

            tourSpotItemsToDB(tourSpotItems);
        }

    }

    private TourSpotResponse fetchTourSpotItemsFromApi() {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .path(tourApiProperties.getTouristSpot())
                .queryParam("numOfRows", 51138)
                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "Tripia")
                .queryParam("_type", "json")
                .queryParam("serviceKey", tourApiProperties.getKey())
                .build())
                .retrieve()
                .bodyToMono(TourSpotResponse.class)
                .block();
    }

    private void tourSpotItemsToDB(List<TourSpotResponse.TourSpotItem> tourSpotItems) {
        for (TourSpotResponse.TourSpotItem tourSpotItem : tourSpotItems) {

            log.info(tourSpotItem.toString());

            Optional<TourSpot> findTourSpot = tourSpotService.findByContentId(tourSpotItem.getContentid());

            if (findTourSpot.isPresent()) {
                TourSpot getTourSpot = findTourSpot.get();

                if (getTourSpot.getTel().length()>255) {
                    getTourSpot.changeTel(extractPhoneNumber(findTourSpot.get().getTel()));
                }
                if (!isEquals(tourSpotItem, getTourSpot)) {
                    getTourSpot.changeAll(tourSpotItem.getContentid(),tourSpotItem.getTitle(),
                            tourSpotItem.getAreacode(), tourSpotItem.getAddr1(),
                            tourSpotItem.getAddr2(), tourSpotItem.getFirstimage(),
                            tourSpotItem.getMapx(), tourSpotItem.getMapy(),
                            tourSpotItem.getTel());
                }
            }else{
                if (tourSpotItem.getTel().length()>255) {
                    tourSpotItem.changeTel(extractPhoneNumber(tourSpotItem.getTel()));
                }
                tourSpotService.save(new TourSpot(
                        null,
                        tourSpotItem.getContentid(),tourSpotItem.getTitle(),
                        tourSpotItem.getAreacode(), tourSpotItem.getAddr1(),
                        tourSpotItem.getAddr2(), tourSpotItem.getFirstimage(),
                        tourSpotItem.getMapx(), tourSpotItem.getMapy(),
                        tourSpotItem.getTel()));
            }
        }
    }

    private boolean isEquals(TourSpotResponse.TourSpotItem tourSpotItem,
                             TourSpot tourSpot) {
        return Objects.equals(tourSpotItem.getContentid(), tourSpot.getContentId())
                && Objects.equals(tourSpot.getTitle(), tourSpot.getTitle())
                && Objects.equals(tourSpotItem.getAreacode(), tourSpot.getAreaCode())
                && Objects.equals(tourSpotItem.getAddr1(), tourSpot.getAddr1())
                && Objects.equals(tourSpotItem.getAddr2(), tourSpot.getAddr2())
                && Objects.equals(tourSpotItem.getFirstimage(), tourSpot.getImage())
                && Objects.equals(tourSpotItem.getMapx(), tourSpot.getLongitude())
                && Objects.equals(tourSpotItem.getMapy(), tourSpot.getLatitude())
                && Objects.equals(tourSpotItem.getTel(),tourSpot.getTel());
    }

    public String extractPhoneNumber(String telRaw) {
        if (telRaw == null) return null;
        // 정규표현식으로 전화번호 형태만 추출 (국번-번호 or 4자리 국번 대응)
        Matcher matcher = Pattern.compile("\\d{2,4}-\\d{3,4}-\\d{4}").matcher(telRaw);
        return matcher.find() ? matcher.group() : null;
    }

}
