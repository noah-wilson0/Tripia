package com.Tripia.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "kakaomobility")
@Getter
public class KaKaoMobilityApiProperties {
    private final String basekey;
    private final String key;
    private final String baseurl;
    private final String route;

    @ConstructorBinding
    public KaKaoMobilityApiProperties(String basekey, String key, String baseurl, String route) {
        this.basekey = basekey;
        this.key = key;
        this.baseurl = baseurl;
        this.route = route;
    }
}
