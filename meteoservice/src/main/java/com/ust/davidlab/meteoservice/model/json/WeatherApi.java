package com.ust.davidlab.meteoservice.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApi {
	public Coordinates coord;
	public Main main;
	public List<Weather> weather;
}
