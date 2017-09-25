package com.ust.davidlab.meteoservice.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ust.davidlab.meteoservice.model.Meteo;
import com.ust.davidlab.meteoservice.model.MeteoRespository;
import com.ust.davidlab.meteoservice.model.json.WeatherApi;

import lombok.SneakyThrows;

@Service
public class BackupBean {
	
	@Autowired
	MeteoRespository meteoRepository;
	
	@Async
	@SneakyThrows
	public CompletableFuture<Void> backupWeatherData(String city, WeatherApi weather) {
		
		Thread.sleep(10000);
		
		ObjectMapper mapper = new ObjectMapper();
		String weatherJson = mapper.writeValueAsString(weather);
		
		Meteo meteo = new Meteo();
		meteo.setCity_id(city);
		meteo.setLastRefresh(new Timestamp(new Date().getTime()));
		meteo.setWeather(weatherJson);
		
		meteoRepository.save(meteo);
		
		return CompletableFuture.completedFuture(null);
	}

}
