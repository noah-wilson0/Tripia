package com.Tripia.tourapi.scheduler;

import com.Tripia.tourapi.TourApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    private final TourApiProperties tourApiProperties;
    @Bean
    public WebClient tourApiWebClient() {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(tourApiProperties.getBaseurl());
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        return WebClient.builder()
                .uriBuilderFactory(factory)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
