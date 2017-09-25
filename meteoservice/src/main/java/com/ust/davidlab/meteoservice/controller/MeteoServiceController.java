package com.ust.davidlab.meteoservice.controller;


import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ust.davidlab.meteoservice.model.Meteo;
import com.ust.davidlab.meteoservice.model.MeteoRespository;
import com.ust.davidlab.meteoservice.model.json.WeatherApi;

import lombok.SneakyThrows;

@RestController
public class MeteoServiceController {
	
	@Autowired
	RestTemplate rest;

	@Value("${weatherapi.url}")
	String weatherUrl;
	
	@Value("${weatherapi.api}")
	String weatherApi;
	
	@Autowired
	BackupBean backup;
	
	@GetMapping(value="/meteo", produces={
			MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<WeatherApi> getMeteo(@RequestParam(required = true) String city){

		StringBuilder url = new StringBuilder(weatherUrl).append("weather?q=").append(city).append("&lang=es&appid=").append(weatherApi);
		WeatherApi response = rest.getForObject(url.toString(), WeatherApi.class);
		
		backup.backupWeatherData(city, response);
		
		return ResponseEntity.ok(response);
	}
	
	
}
