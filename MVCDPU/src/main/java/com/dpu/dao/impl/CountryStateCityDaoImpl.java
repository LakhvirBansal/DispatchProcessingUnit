package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CountryStateCityDao;

@Repository
public class CountryStateCityDaoImpl implements CountryStateCityDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllCountries(Session session) {

		String countryQuery = "SELECT country_id, country_name, country_code FROM country ";
		Query query = session.createSQLQuery(countryQuery);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findStatesByCountryId(Long countryId, Session session) {

		String countryQuery = "SELECT state_id, state_name, state_code FROM state WHERE country_id = :countryId order by state_code";
		Query query = session.createSQLQuery(countryQuery);
		query.setParameter("countryId", countryId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findCitiesByStateId(Long stateId, Session session) {

		String countryQuery = "SELECT city_id, city_name, city_code FROM city WHERE state_id = :stateId ";
		Query query = session.createSQLQuery(countryQuery);
		query.setParameter("stateId", stateId);
		return query.list();
	}


}
