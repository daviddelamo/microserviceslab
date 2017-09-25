package com.ust.davidlab.controller;

import com.ust.davidlab.dto.CityDTO;
import com.ust.davidlab.model.City;
import com.ust.davidlab.model.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CityController {

    @Autowired
    CityRepository cityRepository;

    @GetMapping(value = "/cities", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    public ResponseEntity<List<CityDTO>> getCities(){
        List<CityDTO> citiesList = new ArrayList<CityDTO>();
        Iterable<City> cities = cityRepository.findAll();
        ModelMapper mapper = new ModelMapper();
        for (City city: cities){
            CityDTO cityDTO = new CityDTO();
            mapper.map(city, cityDTO);
            citiesList.add(cityDTO);
        }

        return ResponseEntity.ok(citiesList);
    }

}
