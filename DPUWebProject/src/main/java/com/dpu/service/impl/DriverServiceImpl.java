package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dpu.common.AllList;
import com.dpu.constants.Iconstants;
import com.dpu.dao.CategoryDao;
import com.dpu.dao.DivisionDao;
import com.dpu.dao.DriverDao;
import com.dpu.dao.TerminalDao;
import com.dpu.entity.Category;
import com.dpu.entity.Country;
import com.dpu.entity.Division;
import com.dpu.entity.Driver;
import com.dpu.entity.State;
import com.dpu.entity.Status;
import com.dpu.entity.Terminal;
import com.dpu.entity.Type;
import com.dpu.model.CategoryModel;
import com.dpu.model.CountryStateCityModel;
import com.dpu.model.DivisionReq;
import com.dpu.model.DriverModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TerminalResponse;
import com.dpu.model.TypeResponse;
import com.dpu.service.CategoryService;
import com.dpu.service.CountryStateCityService;
import com.dpu.service.DivisionService;
import com.dpu.service.DriverService;
import com.dpu.service.StatusService;
import com.dpu.service.TerminalService;
import com.dpu.service.TypeService;
import com.dpu.util.ValidationUtil;

@Component
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverDao driverDao;

	@Autowired
	StatusService statusService;

	@Autowired
	TypeService typeService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	DivisionService divisionService;

	@Autowired
	TerminalService terminalService;

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	DivisionDao divisionDao;

	@Autowired
	TerminalDao terminalDao;

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	CountryStateCityService countryStateCityService;

	Logger logger = Logger.getLogger(DriverServiceImpl.class);
	

	@Value("${Driver_added_message}")
	private String Driver_added_message;
	
	@Value("${Driver_unable_to_add_message}")
	private String Driver_unable_to_add_message;
	
	@Value("${Driver_deleted_message}")
	private String Driver_deleted_message;
	
	@Value("${Driver_unable_to_delete_message}")
	private String Driver_unable_to_delete_message;
	
	@Value("${Driver_updated_message}")
	private String Driver_updated_message;
	
	@Value("${Driver_unable_to_update_message}")
	private String Driver_unable_to_update_message;
	
	@Value("${Driver_dependent_message}")
	private String Driver_dependent_message;
	
	@Value("${Driver_code_already_exist}")
	private String driver_code_already_exist;

	@Override
	public Object addDriver(DriverModel driverModel) {

		logger.info("Inside DriverServiceImpl addDriver() starts");
		Object obj = null;
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Driver driver = new Driver();
			BeanUtils.copyProperties(driverModel, driver);
			setDriverValues(driver, driverModel, session);
			driverDao.save(driver, session);
			tx.commit();
			obj = createSuccessObject(Driver_added_message);
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			logger.error("Exception inside DriverServiceImpl addDriver() :"+ e.getMessage());
		
			if(e instanceof ConstraintViolationException){
				ConstraintViolationException c = (ConstraintViolationException) e;
				String constraintName = c.getConstraintName();
				if(Iconstants.UNIQUE_DRIVER_CODE.equals(constraintName)) {
					return createFailedObject(driver_code_already_exist);
				}
			}
			obj = createFailedObject(Driver_unable_to_add_message);
		}

		logger.info("Inside DriverServiceImpl addDriver() Ends");
		return obj;
	}

	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	private Object createSuccessObject(String message) {
		Success success = new Success();
		success.setMessage(message);
		success.setResultList(getAllDriver());
		return success;
	}

	private void setDriverValues(Driver driver, DriverModel driverModel, Session session) {
		
		if (!ValidationUtil.isNull(driverModel.getCategoryId())) {
			Category category = (Category) session.get(Category.class, driverModel.getCategoryId());
			driver.setCategory(category);
		}
		
		if (!ValidationUtil.isNull(driverModel.getDivisionId())) {
			Division division = (Division) session.get(Division.class, driverModel.getDivisionId());
			driver.setDivision(division);
		}
		
		if (!ValidationUtil.isNull(driverModel.getTerminalId())) {
			Terminal terminal = (Terminal) session.get(Terminal.class, driverModel.getTerminalId());
			driver.setTerminal(terminal);
		}
		
		if (!ValidationUtil.isNull(driverModel.getStatusId())) {
			Status status = (Status) session.get(Status.class, driverModel.getStatusId());
			driver.setStatus(status);
		}
		
		if (!ValidationUtil.isNull(driverModel.getRoleId())) {
			Type role = (Type) session.get(Type.class, driverModel.getRoleId());
			driver.setRole(role);
		}
		
		if (!ValidationUtil.isNull(driverModel.getDriverClassId())) {
			Type driverClass = (Type) session.get(Type.class, driverModel.getDriverClassId());
			driver.setDriverClass(driverClass);
		}
		
		if (!ValidationUtil.isNull(driverModel.getCountryId())) {
			Country country = (Country) session.get(Country.class, driverModel.getCountryId());
			driver.setCountry(country);
		}
		
		if (!ValidationUtil.isNull(driverModel.getStateId())) {
			State state = (State) session.get(State.class, driverModel.getStateId());
			driver.setState(state);
		}
		
	}

	@Override
	public Object updateDriver(Long driverId, DriverModel driverModel) {

		logger.info("Inside DriverServiceImpl updateDriver() Starts, driverId :"+ driverId);
		Object obj = null;
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			Driver driver = (Driver) session.get(Driver.class, driverId);

			if (driver != null) {
				tx = session.beginTransaction();
				String[] ignoreProp = new String[1];
				ignoreProp[0] = "driverId";
				BeanUtils.copyProperties(driverModel, driver, ignoreProp);
				setDriverValues(driver, driverModel, session);
				driverDao.update(driver, session);
				tx.commit();
				obj = createSuccessObject(Driver_updated_message);
			} else {
				obj = createFailedObject(Driver_unable_to_update_message);
			}

		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			logger.error("Exception inside DriverServiceImpl updateDriver() :"+ e.getMessage());
			obj = createFailedObject(Driver_unable_to_update_message);
		} finally {
			if(session != null){
				session.close();
			}
		}

		logger.info("Inside DriverServiceImpl updateDriver() ends, driverId :"+ driverId);
		return obj;
	}

	@Override
	public Object deleteDriver(Long driverId) {

		logger.info("Inside DriverServiceImpl deleteDriver() starts, driverId :"+ driverId);
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			Driver driver = driverDao.findById(driverId);

			if (driver != null) {
				session.delete(driver);
				if(tx != null){
					tx.commit();
				}
			} else {
				return createFailedObject(Driver_unable_to_delete_message);
			}
		} catch (Exception e) {
			logger.error("Exceptiom inside DriverServiceImpl deleteDriver() :"+ e.getMessage());
			if(tx != null){
				tx.rollback();
			}
			if(e instanceof ConstraintViolationException){
				return createFailedObject(Driver_dependent_message);
			}
			return createFailedObject(Driver_unable_to_delete_message);
		} finally{
			 
			if(session != null){
				session.close();
			}
		}

		logger.info("Inside DriverServiceImpl deleteDriver() ends, driverId :"+ driverId);
		return createSuccessObject(Driver_deleted_message);

	}

	@Override
	public List<DriverModel> getAllDriver() {

		logger.info("Inside DriverServiceImpl getAllDriver() starts ");
		Session session = null;
		List<DriverModel> drivers = null;

		try {
			session = sessionFactory.openSession();
			List<Driver> listOfDriver = driverDao.findAll(session);
			drivers = setDriverData(listOfDriver);

		} catch (Exception e) {
			logger.error("Exception inside DriverServiceImpl getAllDriver():"+ e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside DriverServiceImpl getAllDriver() ends ");
		return drivers;
	}

	private List<DriverModel> setDriverData(List<Driver> listOfDriver) {

		List<DriverModel> drivers = new ArrayList<DriverModel>();
		if (listOfDriver != null && !listOfDriver.isEmpty()) {
			for (Driver driver : listOfDriver) {
				DriverModel driverModel = new DriverModel();
				BeanUtils.copyProperties(driver, driverModel);
				
				if(driver.getCategory() != null){
					driverModel.setCategoryName(driver.getCategory().getName());
				}
				
				if (driver.getTerminal() != null) {
					driverModel.setTerminalName(driver.getTerminal().getTerminalName());
				}
				
				if (driver.getStatus() != null) {
					driverModel.setStatusName(driver.getStatus().getStatus());
				}
				
				if (driver.getDivision() != null) {
					driverModel.setDivisionName(driver.getDivision().getDivisionName());
				}
				
				if (driver.getDriverClass() != null) {
					driverModel.setDriverClassName(driver.getDriverClass().getTypeName());
				}
				
				if (driver.getRole() != null) {
					driverModel.setRoleName(driver.getRole().getTypeName());
				}
				
				if(driver.getCountry() != null){
					driverModel.setCountryName(driver.getCountry().getCountryName());
				}
				
				if (driver.getState() != null) {
					driverModel.setStateName(driver.getState().getStateName());
				}
				
				drivers.add(driverModel);
			}
		}

		return drivers;
	}

	@Override
	public Object getDriverByDriverId(Long driverId) {

		logger.info("Inside DriverServiceImpl getDriverByDriverId() starts, driverId :"+ driverId);
		Session session = null;
		DriverModel driverModel = new DriverModel();
		try {
			session = sessionFactory.openSession();
			Driver driver = driverDao.findById(driverId, session);

			if (driver != null) {
				BeanUtils.copyProperties(driver, driverModel);
				if(driver.getCategory() != null){
					driverModel.setCategoryId(driver.getCategory().getCategoryId());
				}
				if (driver.getStatus() != null) {
					driverModel.setStatusId(driver.getStatus().getId());
				}
				if (driver.getDivision() != null) {
					driverModel.setDivisionId(driver.getDivision().getDivisionId());
				}
				if (driver.getDriverClass() != null) {
					driverModel.setDriverClassId(driver.getDriverClass().getTypeId());
				}
				if(driver.getRole() != null) {
					driverModel.setRoleId(driver.getRole().getTypeId());
				}
				if(driver.getTerminal() != null){
					driverModel.setTerminalId(driver.getTerminal().getTerminalId());
				}
				if(driver.getCountry() != null){
					driverModel.setCountryId(driver.getCountry().getCountryId());
				}
				if(driver.getState() != null){
					driverModel.setStateId(driver.getState().getStateId());
				}
				
				List<Status> statusList = AllList.getStatusList(session);
				driverModel.setStatusList(statusList);

				List<TypeResponse> roleList = AllList.getTypeResponse(session, 6l);
				driverModel.setRoleList(roleList);

				List<TypeResponse> driverClassList = AllList.getTypeResponse(session, 5l);
				driverModel.setDriverClassList(driverClassList);

				List<CategoryModel> categoryList = categoryService.getCategoriesBasedOnType("Driver");
				driverModel.setCategoryList(categoryList);
				/*
				 * List<CategoryModel> categoryList = AllList.getCategoryList(session, "Category", "categoryId",
				 * "name"); driverModel.setCategoryList(categoryList);
				 */

				List<DivisionReq> divisionList = AllList.getDivisionList(session, "Division", "divisionId", "divisionName");
				driverModel.setDivisionList(divisionList);

				List<TerminalResponse> terminalList = AllList.getTerminalList(session, "Terminal", "terminalId", "terminalName");
				driverModel.setTerminalList(terminalList);
				
				List<CountryStateCityModel> countryList = countryStateCityService.getAllCountries();
				driverModel.setCountryList(countryList);
				
				if(driver.getCountry() != null){
					List<CountryStateCityModel> stateList = countryStateCityService.getStatesByCountryId(driver.getCountry().getCountryId());
					driverModel.setStateList(stateList);
				}
			} 
		}  finally {
			if (session != null) {
				session.close();
			}
		}
		return driverModel;
	}

	/*private Object createSuccessObjectForParRecord(String message,
			DriverReq response) {
		Success success = new Success();
		success.setMessage(message);
		success.setResultList(response);
		return success;
	}*/

	public boolean isDriverExist(String driverCode) {
		boolean isDriverExist = false;

		Criterion driverCriteria = Restrictions.eqOrIsNull("driverCode",
				driverCode);
		List<Driver> drivers = driverDao.find(driverCriteria);

		if (drivers.size() == 0) {

			return isDriverExist;
		}

		isDriverExist = true;
		return isDriverExist;

	}

	@Override
	public DriverModel getOpenAdd() {

		DriverModel driver = new DriverModel();
		Session session = sessionFactory.openSession();
		
		try{
			List<Status> statusList = AllList.getStatusList(session);
			driver.setStatusList(statusList);

			List<TypeResponse> roleList = AllList.getTypeResponse(session, 6l);
			driver.setRoleList(roleList);

			List<TypeResponse> driverClassList = AllList.getTypeResponse(session, 5l);
			driver.setDriverClassList(driverClassList);

			List<CategoryModel> categoryList = categoryService.getCategoriesBasedOnType("Driver");
			driver.setCategoryList(categoryList);

			/*
			 * List<CategoryModel> categoryList = AllList.getCategoryList(session,"Category", "categoryId", "name");
			 * driver.setCategoryList(categoryList);
			 */
			List<DivisionReq> divisionList = AllList.getDivisionList(session,"Division", "divisionId", "divisionName");
			driver.setDivisionList(divisionList);
		
		 
			List<TerminalResponse> terminalList = AllList.getTerminalList(session, "Terminal", "terminalId", "terminalName");
			driver.setTerminalList(terminalList);
			
			List<CountryStateCityModel> countryList = countryStateCityService.getAllCountries();
			driver.setCountryList(countryList);
	
		} finally{
			if(session != null){
				session.close();
			}
		}

		return driver;
	}

	@Override
	public List<DriverModel> getDriverByDriverCodeOrName(String driverCodeOrName) {

		List<DriverModel> driverModelList = new ArrayList<DriverModel>();
		List<Driver> driverList = driverDao.searchDriverByDriverCodeOrName(driverCodeOrName);
		driverModelList = setDriverData(driverList);
		return driverModelList;
	}

	@Override
	public List<DriverModel> getSpecificData() {
		
		List<Object[]> driverIdNameList = driverDao.getDriverIdAndName();
		List<DriverModel> driverList = new ArrayList<DriverModel>();
		
		if(driverIdNameList != null && ! driverIdNameList.isEmpty()){
			for (Object[] row : driverIdNameList) {
				DriverModel driver = new DriverModel();
				driver.setDriverId(Long.parseLong(String.valueOf(row[0])));
				driver.setFullName(String.valueOf(row[1]) +" "+String.valueOf(row[2]));
				driverList.add(driver);
			}
		}
		
		return driverList;
	}

}
