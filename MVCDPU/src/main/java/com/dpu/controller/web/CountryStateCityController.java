package com.dpu.controller.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dpu.service.CountryStateCityService;

@Controller
public class CountryStateCityController {

	@Autowired
	CountryStateCityService countryStateCityService;
	
	Logger logger = Logger.getLogger(WebDriverController.class);
	
	@RequestMapping(value = "/states/{countryid}" , method = RequestMethod.GET)
	@ResponseBody public Object getStates(@PathVariable("countryid") Long countryId) {
		Object response = countryStateCityService.getStatesByCountryId(countryId);
		return response;
	}
}
