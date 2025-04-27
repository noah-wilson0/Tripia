package com.Tripia.tourapi.scheduler.place.service;

import com.Tripia.tourapi.TourApiProperties;
import com.Tripia.tourapi.location.entity.AreaCode;
import com.Tripia.tourapi.location.entity.CityCode;
import com.Tripia.tourapi.location.service.AreaCodeService;
import com.Tripia.tourapi.location.service.CityCodeService;
import com.Tripia.tourapi.place.entity.Place;
import com.Tripia.tourapi.place.service.PlaceService;
import com.Tripia.tourapi.scheduler.place.entity.PlaceResponse;
import com.Tripia.utils.PlaceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PlaceSyncService {

    private final WebClient webClient;
    private final TourApiProperties tourApiProperties;
    private final AreaCodeService areaCodeService;
    private final CityCodeService cityCodeService;
    private final PlaceService placeService;

    @Transactional
    public void SyncPlace() {
        List<String> placeTypes = PlaceType.getPlaceTypes();
        for (String placeType : placeTypes) {
            PlaceResponse placeResponse = fetchPlaceItemsFromApi(placeType);
            if (placeResponse != null && placeResponse.getResponse() != null) {
                List<PlaceResponse.PlaceItem> tourSpotItems = placeResponse.getResponse().getBody().getItems().getItem();
                tourSpotItemsToDB(tourSpotItems);
            }
        }
    }

    private PlaceResponse fetchPlaceItemsFromApi(String placeType) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path(tourApiProperties.getTouristSpot())
                        .queryParam("numOfRows", 51138)
                        .queryParam("pageNo", 1)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "Tripia")
                        .queryParam("_type", "json")
                        .queryParam("contentTypeId", placeType)
                        .queryParam("serviceKey", tourApiProperties.getKey())
                        .build())
                .retrieve()
                .bodyToMono(PlaceResponse.class)
                .block();
    }

    private void tourSpotItemsToDB(List<PlaceResponse.PlaceItem> tourSpotItems) {
        List<String> contentIds = tourSpotItems.stream()
                .map(PlaceResponse.PlaceItem::getContentid)
                .collect(Collectors.toList());

        Map<String, Place> existingPlaces = placeService.findAllByContentIdIn(contentIds)
                .stream()
                .collect(Collectors.toMap(Place::getContentId, Function.identity()));

        Map<String, AreaCode> areaCodeMap = areaCodeService.findAll()
                .stream()
                .collect(Collectors.toMap(AreaCode::getAreaCode, Function.identity()));

        Map<String, List<CityCode>> cityCodeMap = cityCodeService.findAll()
                .stream()
                .collect(Collectors.groupingBy(cityCode -> cityCode.getAreaCode().getAreaCode()));

        for (PlaceResponse.PlaceItem placeItem : tourSpotItems) {

            if (isEmpty(placeItem.getAreacode())) {
                log.warn("지역코드가 없어서 저장하지 않습니다. title={}", placeItem.getTitle());
                continue;
            }

            if (isEmpty(placeItem.getSigungucode())) {
                log.warn("시군구코드가 없어서 저장하지 않습니다. title={}", placeItem.getTitle());
                continue;
            }

            log.info(placeItem.toString());

            Place existingPlace = existingPlaces.get(placeItem.getContentid());
            AreaCode areaCode = areaCodeMap.get(placeItem.getAreacode());
            CityCode cityCode = cityCodeMap.getOrDefault(placeItem.getAreacode(), List.of())
                    .stream()
                    .filter(filtercityCode -> filtercityCode.getCityCode().equals(placeItem.getSigungucode()))
                    .findFirst()
                    .orElse(null);

            if (areaCode == null || cityCode == null) {
                log.warn("AreaCode 또는 CityCode를 찾을 수 없습니다. title={}", placeItem.getTitle());
                continue;
            }

            if (existingPlace != null) {
                String cleanTel= placeItem.getTel();
                if (cleanTel != null && cleanTel.length() > 50) {
                    cleanTel= extractPhoneNumber(placeItem.getTel());
                }
                if (!isEquals(placeItem, existingPlace)) {
                    existingPlace.changeExceptAverageRatingAndLikeCount(
                            placeItem.getContentid(), placeItem.getTitle(),
                            cityCode, PlaceType.fromCode(placeItem.getContenttypeid()).getCode(),
                            placeItem.getAddr1(), placeItem.getAddr2(), placeItem.getFirstimage(),
                            placeItem.getMapx(), placeItem.getMapy(), cleanTel
                    );
                }
            } else {
                if (placeItem.getTel() != null && placeItem.getTel().length() > 50) {
                    placeItem.changeTel(extractPhoneNumber(placeItem.getTel()));
                }
                placeService.save(Place.builder()
                        .id(null)
                        .contentId(placeItem.getContentid())
                        .title(placeItem.getTitle())
                        .cityCode(cityCode)
                        .placeType(PlaceType.fromCode(placeItem.getContenttypeid()).getCode())
                        .addr1(placeItem.getAddr1())
                        .addr2(placeItem.getAddr2())
                        .image(placeItem.getFirstimage())
                        .longitude(placeItem.getMapx())
                        .latitude(placeItem.getMapy())
                        .tel(placeItem.getTel())
                        .build());
            }
        }
    }

    private boolean isEquals(PlaceResponse.PlaceItem placeItem, Place place) {
        return Objects.equals(placeItem.getContentid(), place.getContentId())
                && Objects.equals(placeItem.getTitle(), place.getTitle())
                && Objects.equals(placeItem.getAreacode(),
                areaCodeService.findByAreaCode(
                                cityCodeService.findAreaCodeByCityCodeAndAreaCodeId(
                                                place.getCityCode().getCityCode(),
                                                place.getCityCode().getAreaCode().getAreaCodeId()
                                        ).orElseThrow(() -> new IllegalArgumentException("AreaCode not found"))
                                        .getAreaCode()
                        ).orElseThrow(() -> new IllegalArgumentException("AreaCode not found"))
                        .getAreaCode()
        )
                && Objects.equals(placeItem.getAddr1(), place.getAddr1())
                && Objects.equals(placeItem.getAddr2(), place.getAddr2())
                && Objects.equals(placeItem.getFirstimage(), place.getImage())
                && Objects.equals(placeItem.getMapx(), place.getLongitude())
                && Objects.equals(placeItem.getMapy(), place.getLatitude())
                && Objects.equals(placeItem.getTel(), place.getTel());

    }

    public String extractPhoneNumber(String telRaw) {
        if (telRaw == null) return null;
        Matcher matcher = Pattern.compile("\\d{2,4}-\\d{3,4}-\\d{4}").matcher(telRaw);
        return matcher.find() ? matcher.group() : null;
    }
}
