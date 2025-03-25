package com.Tripia.tMapPublicTransportation;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/tmap")
public class tMapSendApi {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/route")
    public ResponseEntity<String> sendToTmapApi(@RequestBody tMapPublicTransPortationRequest tMapPublicTransPortationRequest) {
        String url = "https://apis.openapi.sk.com/transit/routes";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("appKey", "api key");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<tMapPublicTransPortationRequest> entity = new HttpEntity<>(tMapPublicTransPortationRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        System.out.println("요청 데이터: " + tMapPublicTransPortationRequest);

        return response;
    }



}