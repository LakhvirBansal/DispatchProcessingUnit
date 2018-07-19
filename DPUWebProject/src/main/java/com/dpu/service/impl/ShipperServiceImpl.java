package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dpu.dao.ShipperDao;
import com.dpu.entity.Country;
import com.dpu.entity.Shipper;
import com.dpu.entity.State;
import com.dpu.entity.Status;
import com.dpu.model.CountryStateCityModel;
import com.dpu.model.Failed;
import com.dpu.model.ShipperModel;
import com.dpu.model.Success;
import com.dpu.service.CountryStateCityService;
import com.dpu.service.ShipperService;
import com.dpu.service.StatusService;

@Component
public class ShipperServiceImpl implements ShipperService {

	Logger logger = Logger.getLogger(ShipperServiceImpl.class);

	@Autowired
	ShipperDao shipperDao;

	@Autowired
	StatusService statusService;

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	CountryStateCityService countryStateCityService;
	
	@Value("${shipper_added_message}")
	private String shipper_added_message;
	
	@Value("${shipper_unable_to_add_message}")
	private String shipper_unable_to_add_message;
	
	@Value("${shipper_deleted_message}")
	private String shipper_deleted_message;
	
	@Value("${shipper_unable_to_delete_message}")
	private String shipper_unable_to_delete_message;
	
	@Value("${shipper_updated_message}")
	private String shipper_updated_message;
	
	@Value("${shipper_unable_to_update_message}")
	private String shipper_unable_to_update_message;
	
	@Value("${shipper_already_used_message}")
	private String shipper_already_used_message;

	@Override
	public Object add(ShipperModel shipperModel) {
		Object obj = null;
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			Shipper shipper = new Shipper();
			BeanUtils.copyProperties(shipperModel, shipper);
			if(shipperModel.getStatusId() != null){
				shipper.setStatus((Status) session.get(Status.class, shipperModel.getStatusId()));
			}
			if(shipperModel.getCountryId() != null){
				Country country = (Country) session.get(Country.class, shipperModel.getCountryId());
				shipper.setCountry(country);
			}
			if(shipperModel.getStateId() != null) {
				State state = (State) session.get(State.class, shipperModel.getStateId());
				shipper.setState(state);
			}
			
			shipperDao.save(shipper, session);
			tx.commit();
			obj = createSuccessObject(shipper_added_message);
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			obj = createFailedObject(shipper_unable_to_add_message);
		} finally {
			if(session != null){
				session.close();
			}
		}
		return obj;
	}

	private Object createSuccessObject(String message) {
		Success success = new Success();
		success.setMessage(message);
		//success.setCode(code);
		success.setResultList(getAll());
		return success;
	}

	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
	//	failed.setCode(code);
		//failed.setResultList(getAll());
		return failed;
	}

	public Object createAlreadyExistObject(String msg) {
		Failed failed = new Failed();
		failed.setMessage(msg);
		//failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object update(Long id, ShipperModel shipperModel) {

		Object obj = null;
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			Shipper shipper = (Shipper) session.get(Shipper.class, id);
			
			if (shipper != null) {
				tx = session.beginTransaction();
				String[] ignoreProp = new String[1];
				ignoreProp[0] = "shipperId";
				BeanUtils.copyProperties(shipperModel, shipper, ignoreProp);
				
				if(shipperModel.getStatusId() != null){
					shipper.setStatus((Status) session.get(Status.class, shipperModel.getStatusId()));
				}
				if(shipperModel.getCountryId() != null){
					Country country = (Country) session.get(Country.class, shipperModel.getCountryId());
					shipper.setCountry(country);
				}
				if(shipperModel.getStateId() != null) {
					State state = (State) session.get(State.class, shipperModel.getStateId());
					shipper.setState(state);
				}
				shipperDao.update(shipper, session);
				tx.commit();
				obj = createSuccessObject(shipper_updated_message);
			}
		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			logger.error("Exception inside ShipperServiceImpl update() :" + e.getMessage());
			obj = createFailedObject(shipper_unable_to_update_message);
		} finally {
			if( session != null ){
				session.close();
			}
		}
		return obj;
	}

	@Override
	public Object delete(Long id) {

		logger.info("ShipperServiceImpl delete() starts.");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Shipper shipper = (Shipper) session.get(Shipper.class, id);
			if (shipper != null) {
				session.delete(shipper);
				tx.commit();
			} else {
				return createFailedObject(shipper_unable_to_delete_message);
			}
		} catch (Exception e) {
			logger.info("Exception inside ShipperServiceImpl delete() : " + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createAlreadyExistObject(shipper_already_used_message);
			}
			return createFailedObject(shipper_unable_to_delete_message);
		} finally {

			if (session != null) {
				session.close();
			}
		}

		logger.info("ShipperServiceImpl delete() ends.");
		return createSuccessObject(shipper_deleted_message);
	}

	@Override
	public List<ShipperModel> getAll() {

		Session session = null;
		List<ShipperModel> responses = new ArrayList<ShipperModel>();
		try {
			session = sessionFactory.openSession();
			List<Shipper> shipperlist = shipperDao.findAll(session);

			if (shipperlist != null && !shipperlist.isEmpty()) {
				for (Shipper shipper : shipperlist) {
					ShipperModel shipperResponse = new ShipperModel();
					BeanUtils.copyProperties(shipper, shipperResponse);
					
					if (shipper.getStatus() != null) {
						shipperResponse.setStatus(shipper.getStatus().getStatus());
					}
					
					if(shipper.getCountry() != null) {
						shipperResponse.setCountryName(shipper.getCountry().getCountryName());
					}
					
					if(shipper.getState() != null) {
						shipperResponse.setStateName(shipper.getState().getStateName());
					}
					responses.add(shipperResponse);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return responses;
	}

	@Override
	public ShipperModel get(Long id) {

		Session session = null;
		ShipperModel response = new ShipperModel();

		try {
			session = sessionFactory.openSession();
			Shipper shipper = shipperDao.findById(id, session);
			if (shipper != null) {
				BeanUtils.copyProperties(shipper, response);
				
				if (shipper.getStatus() != null) {
					response.setStatusId(shipper.getStatus().getId());
				}
				
				if(shipper.getCountry() != null) {
					response.setCountryId(shipper.getCountry().getCountryId());
					List<CountryStateCityModel> stateList = countryStateCityService.getStatesByCountryId(shipper.getCountry().getCountryId());
					response.setStateList(stateList);
				}
				
				if(shipper.getState() != null) {
					response.setStateId(shipper.getState().getStateId());
				}
				
				List<Status> statusList = statusService.getAll();
				response.setStatusList(statusList);
				
				List<CountryStateCityModel> countryList = countryStateCityService.getAllCountries();
				response.setCountryList(countryList);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return response;
	}

	@Override
	public ShipperModel getParticularData(Long id) {

		Session session = null;
		ShipperModel response = new ShipperModel();

		try {
			session = sessionFactory.openSession();
			Shipper shipper = (Shipper) session.get(Shipper.class, id);
			if (shipper != null) {
				BeanUtils.copyProperties(shipper, response);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return response;
	}

	@Override
	public ShipperModel getMasterData() {

		ShipperModel model = new ShipperModel();

		List<Status> statusList = statusService.getAll();
		model.setStatusList(statusList);
		
		List<CountryStateCityModel> countryList = countryStateCityService.getAllCountries();
		model.setCountryList(countryList);
		
		return model;
	}

	@Override
	public List<ShipperModel> getShipperByCompanyName(String companyName) {

		Session session = null;
		List<Shipper> shipperList = null;
		List<ShipperModel> responses = new ArrayList<ShipperModel>();
		try {
			session = sessionFactory.openSession();
			if (companyName != null && companyName.length() > 0) {
				shipperList = shipperDao.findByLoactionName(companyName, session);
			}

			if (shipperList != null && !shipperList.isEmpty()) {
				for (Shipper shipper : shipperList) {
					ShipperModel shipperResponse = new ShipperModel();
					BeanUtils.copyProperties(shipper, shipperResponse);
					if (shipper.getStatus() != null) {
						shipperResponse.setStatus(shipper.getStatus().getStatus());
					}
					
					if(shipper.getCountry() != null) {
						shipperResponse.setCountryName(shipper.getCountry().getCountryName());
					}
					
					if(shipper.getState() != null) {
						shipperResponse.setStateName(shipper.getState().getStateName());
					}
					responses.add(shipperResponse);
				}
			}
		}  finally {
			if (session != null) {
				session.close();
			}
		}
		return responses;
	}

	@Override
	public List<ShipperModel> getSpecificData(Session session) {

		List<ShipperModel> categories = new ArrayList<ShipperModel>();

		try {
			List<Object[]> shipperData = shipperDao.getSpecificData(session, "Shipper", "shipperId", "locationName");

			if (shipperData != null && !shipperData.isEmpty()) {
				for (Object[] row : shipperData) {
					ShipperModel shipper = new ShipperModel();
					shipper.setShipperId((Long) row[0]);
					shipper.setLocationName(String.valueOf(row[1]));
					categories.add(shipper);
				}
			}
		} catch (Exception e) {

		}

		return categories;
	}

}
