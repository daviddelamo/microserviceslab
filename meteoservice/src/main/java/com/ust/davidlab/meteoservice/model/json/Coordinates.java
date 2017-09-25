package com.ust.davidlab.meteoservice.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates {

    //coord.lon City geo location, longitude
    //coord.lat City geo location, latitude
	String lon;
	String lat;
}
