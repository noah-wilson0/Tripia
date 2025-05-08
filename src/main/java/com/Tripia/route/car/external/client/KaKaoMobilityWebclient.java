package com.Tripia.route.car.external.client;

import com.Tripia.config.properties.KaKaoMobilityApiProperties;
import com.Tripia.route.car.external.dto.KaKaoMobilityResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
@Slf4j
@Component
public class KaKaoMobilityWebclient {

    @Qualifier("kaKaoMobilityWebClient")
    private final WebClient webClient;

    private final KaKaoMobilityApiProperties kaKaoMobilityApiProperties;

    public KaKaoMobilityWebclient(
            @Qualifier("kaKaoMobilityWebClient") WebClient webClient,
            KaKaoMobilityApiProperties kaKaoMobilityApiProperties
    ) {
        this.webClient = webClient;
        this.kaKaoMobilityApiProperties = kaKaoMobilityApiProperties;
    }

    public KaKaoMobilityResponse fetchRouteFromApi(String origin, String destination){
        log.info("fetchRouteFromApi:"+origin+destination);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(kaKaoMobilityApiProperties.getRoute())
                        .queryParam("origin", origin)
                        .queryParam("destination", destination)
                        .build())
                .retrieve()
                .bodyToMono(KaKaoMobilityResponse.class)
                .block();
    }

}
