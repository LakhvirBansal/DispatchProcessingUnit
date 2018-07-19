package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.model.CountryStateCityModel;
import com.dpu.service.CountryStateCityService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "country")
public class CountryStateCityController extends MessageProperties {

	Logger logger = Logger.getLogger(CountryStateCityController.class);

	@Autowired
	CountryStateCityService countryStateCityService;

	ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * this method is used to get all countries
	 * @return List<countries>
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllCountries() {

		logger.info("Inside CountryStateCityController getAllCountries() Starts ");
		String json = null;

		try {
			List<CountryStateCityModel> responses = countryStateCityService.getAllCountries();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside CountryStateCityController getAllCountries() :"+ e.getMessage());
		}
		
		logger.info("Inside CountryStateCityController getAllCountries() Ends ");
		return json;
	}

	/**
	 * this method is used to get states based on countryId
	 * @param countryId
	 * @return List<States>
	 */
	@RequestMapping(value = "/{countryId}/states", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getStatesByCountryId(@PathVariable("countryId") Long countryId) {

		logger.info("Inside CountryStateCityController getStatesByCountryId() Starts ");
		String json = null;

		try {
			List<CountryStateCityModel> responses = countryStateCityService.getStatesByCountryId(countryId);
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside CountryStateCityController getStatesByCountryId() :"+ e.getMessage());
		}
		
		logger.info("Inside CountryStateCityController getStatesByCountryId() Ends ");
		return json;
	}
	
	@RequestMapping(value = "/states/{stateId}/city", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getCitiesByStateId(@PathVariable("stateId") Long stateId) {

		logger.info("Inside CountryStateCityController getCitiesByStateId() Starts ");
		String json = null;

		try {
			List<CountryStateCityModel> responses = countryStateCityService.getCitiesByStateId(stateId);
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside CountryStateCityController getCitiesByStateId() :"+ e.getMessage());
		}
		
		logger.info("Inside CountryStateCityController getCitiesByStateId() Ends ");
		return json;
	}

}
