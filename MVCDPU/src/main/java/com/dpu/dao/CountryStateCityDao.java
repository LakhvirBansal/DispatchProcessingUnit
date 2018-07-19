package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

public interface CountryStateCityDao {

	List<Object[]> findAllCountries(Session session);

	List<Object[]> findStatesByCountryId(Long countryId, Session session);

	List<Object[]> findCitiesByStateId(Long stateId, Session session);
}
