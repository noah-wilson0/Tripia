package com.Tripia.config;


import com.Tripia.config.properties.KaKaoMobilityApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@RequiredArgsConstructor
public class KaKaoMobilityWebClientConfig {
    private final KaKaoMobilityApiProperties kaKaoMobilityApiProperties;

    @Bean
    public WebClient kaKaoMobilityWebClient() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(kaKaoMobilityApiProperties.getBaseurl());
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        return WebClient.builder()
                .uriBuilderFactory(factory)
                .defaultHeader(HttpHeaders.AUTHORIZATION,
                        kaKaoMobilityApiProperties.getBasekey()+kaKaoMobilityApiProperties.getKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(ExchangeStrategies
                        .builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer
                                .defaultCodecs()
                                .maxInMemorySize(30*1024*1024))
                        .build())
                .build();
    }
}
