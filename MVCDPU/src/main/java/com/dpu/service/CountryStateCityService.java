package com.dpu.service;

import java.util.List;

import com.dpu.model.CountryStateCityModel;

public interface CountryStateCityService {

	List<CountryStateCityModel> getAllCountries();

	List<CountryStateCityModel> getStatesByCountryId(Long countryId);

	List<CountryStateCityModel> getCitiesByStateId(Long stateId);

}
