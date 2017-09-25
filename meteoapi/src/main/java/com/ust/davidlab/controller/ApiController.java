package com.ust.davidlab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${cities.url}")
    private String citiesUrl;

    @GetMapping(name = "/cities")
    public ResponseEntity<String> getCities(){
        String response = restTemplate.getForObject(citiesUrl, String.class);

        return ResponseEntity.ok(response);
    }

}
