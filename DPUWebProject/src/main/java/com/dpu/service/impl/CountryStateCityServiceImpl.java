package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CountryStateCityDao;
import com.dpu.model.CountryStateCityModel;
import com.dpu.service.CountryStateCityService;

@Component
public class CountryStateCityServiceImpl implements CountryStateCityService {

	Logger logger = Logger.getLogger(CountryStateCityServiceImpl.class);

	@Autowired
	CountryStateCityDao countryStateCityDao;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<CountryStateCityModel> getAllCountries() {

		logger.info("CountryStateCityServiceImpl getAllCountries() starts ");
		Session session = null;
		List<CountryStateCityModel> countryStateCityList = new ArrayList<CountryStateCityModel>();

		try {
			session = sessionFactory.openSession();
			List<Object[]> countryData = countryStateCityDao.findAllCountries(session);

			if (countryData != null && !countryData.isEmpty()) {
				for (Object[] row : countryData) {
					CountryStateCityModel countryStateCityModel = new CountryStateCityModel();
					countryStateCityModel.setCountryId(Long.parseLong(String.valueOf(row[0])));
					countryStateCityModel.setCountryName(String.valueOf(row[1]));
					countryStateCityModel.setCountryCode(String.valueOf(row[2]));
					countryStateCityList.add(countryStateCityModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("CountryStateCityServiceImpl getAllCountries() ends ");
		return countryStateCityList;
	}
	
	@Override
	public List<CountryStateCityModel> getStatesByCountryId(Long countryId) {

		logger.info("CountryStateCityServiceImpl getStatesByCountryId() starts ");
		Session session = null;
		List<CountryStateCityModel> countryStateCityList = new ArrayList<CountryStateCityModel>();

		try {
			session = sessionFactory.openSession();
			List<Object[]> countryData = countryStateCityDao.findStatesByCountryId(countryId, session);

			if (countryData != null && !countryData.isEmpty()) {
				for (Object[] row : countryData) {
					CountryStateCityModel countryStateCityModel = new CountryStateCityModel();
					countryStateCityModel.setStateId(Long.parseLong(String.valueOf(row[0])));
					countryStateCityModel.setStateName(String.valueOf(row[1]));
					countryStateCityModel.setStateCode(String.valueOf(row[2]));
					countryStateCityList.add(countryStateCityModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("CountryStateCityServiceImpl getStatesByCountryId() ends ");
		return countryStateCityList;
	}

	@Override
	public List<CountryStateCityModel> getCitiesByStateId(Long stateId) {

		logger.info("CountryStateCityServiceImpl getCitiesByStateId() starts ");
		Session session = null;
		List<CountryStateCityModel> countryStateCityList = new ArrayList<CountryStateCityModel>();

		try {
			session = sessionFactory.openSession();
			List<Object[]> cityData = countryStateCityDao.findCitiesByStateId(stateId, session);

			if (cityData != null && !cityData.isEmpty()) {
				for (Object[] row : cityData) {
					CountryStateCityModel countryStateCityModel = new CountryStateCityModel();
					countryStateCityModel.setCityId(Long.parseLong(String.valueOf(row[0])));
					countryStateCityModel.setCityName(String.valueOf(row[1]));
					countryStateCityModel.setCityCode(String.valueOf(row[2]));
					countryStateCityList.add(countryStateCityModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("CountryStateCityServiceImpl getCitiesByStateId() ends ");
		return countryStateCityList;
	}

}
