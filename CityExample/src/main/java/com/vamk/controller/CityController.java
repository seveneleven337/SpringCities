package com.vamk.controller;

import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vamk.entity.City;
import com.vamk.repository.CityRepository;

@RestController
public class CityController {

	@Autowired
	public CityRepository cityRepository;
	
	@GetMapping("/cities")
	public Iterable<City> getCitys() {
		return cityRepository.findAll();
	}
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
	@PostMapping("/city")
	public City Create(@RequestBody City city) {
		return cityRepository.save(city);
	}
	
	@GetMapping("/importCities")
	public String importCities() {
		//Json, jsonArray, JsonObbect are used to from json dependency
		JSONParser parser = new JSONParser();
		try {
			Object Arr = parser.parse(new FileReader("city.json"));
			JSONArray cities = (JSONArray) Arr;
			Iterator<JSONObject> iterator = cities.iterator(); 
			while(iterator.hasNext()) {
				JSONObject obj = (JSONObject) iterator.next();
				ObjectMapper mapper = new ObjectMapper();
				City city = mapper.readValue(obj.toString(), City.class);
				cityRepository.save(city);
			}
			return "Import ok";
		} catch(Exception e){
			return "Import failed " + e.toString();
		}
		
	}
}
