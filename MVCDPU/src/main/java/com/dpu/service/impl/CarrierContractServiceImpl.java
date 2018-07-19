package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.dpu.common.AllList;
import com.dpu.dao.ArrangedWithDao;
import com.dpu.dao.CarrierAdditionalContactsDao;
import com.dpu.dao.CarrierContractDao;
import com.dpu.dao.CarrierDao;
import com.dpu.dao.CategoryDao;
import com.dpu.dao.DispatcherDao;
import com.dpu.dao.DivisionDao;
import com.dpu.dao.DriverDao;
import com.dpu.dao.EquipmentDao;
import com.dpu.entity.CarrierContract;
import com.dpu.model.ArrangedWithModel;
import com.dpu.model.CarrierContractModel;
import com.dpu.model.CarrierModel;
import com.dpu.model.CategoryModel;
import com.dpu.model.DispatcherModel;
import com.dpu.model.DivisionReq;
import com.dpu.model.DriverModel;
import com.dpu.model.EquipmentReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TypeResponse;
import com.dpu.service.ArrangedWithService;
import com.dpu.service.CarrierAdditionalContactService;
import com.dpu.service.CarrierContractService;
import com.dpu.service.CarrierService;
import com.dpu.service.CategoryService;
import com.dpu.service.DispatcherService;
import com.dpu.service.DivisionService;
import com.dpu.service.DriverService;
import com.dpu.service.EquipmentService;
import com.dpu.service.TypeService;

@Component
public class CarrierContractServiceImpl implements CarrierContractService {

	Logger logger = Logger.getLogger(CarrierContractServiceImpl.class);

	@Autowired
	DispatcherDao dispatcherdao;

	@Autowired
	CarrierAdditionalContactService carrierAdditionalContactService;

	@Autowired
	CarrierAdditionalContactsDao carrierAdditionalContactsDao;

	@Autowired
	ArrangedWithService arrangedWithService;

	@Autowired
	CarrierContractDao carrierContractDao;

	@Autowired
	ArrangedWithDao arrangedWithDao;

	@Autowired
	CarrierDao carrierDao;

	@Autowired
	DivisionDao divisionDao;

	@Autowired
	CarrierService carrierService;

	@Autowired
	DriverService driverService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	TypeService typeService;

	@Autowired
	EquipmentService equipmentService;

	@Autowired
	DivisionService divisionService;

	@Autowired
	DispatcherDao dispatcherDao;

	// @Autowired
	// ArrangedWithDao arrangedWithDao;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	DriverDao driverDao;

	@Autowired
	DispatcherService dispatcherService;

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	EquipmentDao equipmentDao;

	@Value("${CarrierContract_updated_message}")
	private String CarrierContract_updated_message;

	@Value("${CarrierContract_unable_to_update_message}")
	private String CarrierContract_unable_to_update_message;

	@Value("${CarrierContract_added_message}")
	private String CarrierContract_added_message;

	@Value("${CarrierContract_unable_to_add_message}")
	private String CarrierContract_unable_to_add_message;

	@Value("${CarrierContract_unable_to_delete_message}")
	private String CarrierContract_unable_to_delete_message;

	@Value("${CarrierContract_dependent_message}")
	private String CarrierContract_dependent_message;

	@Value("${CarrierContract_deleted_message}")
	private String CarrierContract_deleted_message;

	@Override
	public List<CarrierContractModel> getAllCarrierContract() {

		logger.info("Inside CarrierContractServiceImpl getAllCarrierContract() starts ");
		Session session = null;
		List<CarrierContractModel> returnResponse = new ArrayList<CarrierContractModel>();

		try {
			session = sessionFactory.openSession();
			List<CarrierContract> listOfCarrierContract = carrierContractDao
					.findAllCarrierContract(session);

			if (listOfCarrierContract != null
					&& !listOfCarrierContract.isEmpty()) {
				for (CarrierContract carrierContract : listOfCarrierContract) {
					CarrierContractModel response = new CarrierContractModel();
					setCarrierContractData(carrierContract, response);
					returnResponse.add(response);
				}

			}
		} catch (Exception e) {
			logger.error("Exception inside CarrierContractServiceImpl getAllCarrierContract() :"
					+ e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		logger.info("Inside CarrierContractServiceImpl getAllCarrierContract() ends ");
		return returnResponse;
	}

	private void setCarrierContractData(CarrierContract carrierContract,
			CarrierContractModel response) {

		response.setContractNoId(carrierContract.getContractNoId());
		response.setArrangedWithName(carrierContract.getArrangedWith()
				.getCustomerName());

		response.setCargo(carrierContract.getCargo());
		response.setCarrierName(carrierContract.getCarrier().getName());
		response.setCarrierRat(carrierContract.getCarrierRat());
		response.setCategoryName(carrierContract.getCategory().getName());

		response.setCommodityName(carrierContract.getCommodity().getTypeName());
		response.setContractNo(carrierContract.getContractNo());
		response.setContractNoId(carrierContract.getContractNoId());
		response.setContractRate(carrierContract.getContractRate());
		response.setCreatedBy(carrierContract.getCreatedBy());
		response.setCurrencyName(carrierContract.getCurrency().getTypeName());
		response.setDispatched(carrierContract.getDispatched());
		response.setDispatcherName(carrierContract.getDispatcher()
				.getDispatcherName());
		response.setDivisionName(carrierContract.getDivision()
				.getDivisionName());
		response.setdOTno(carrierContract.getdOTno());
		response.setDriverName(carrierContract.getDriver().getFirstName());
		response.setEquipmentName(carrierContract.getEquipment()
				.getEquipmentName());
		response.setHours(carrierContract.getHours());
		response.setInsExpires(carrierContract.getInsExpires());
		response.setLiabity(carrierContract.getLiabity());
		response.setmCno(carrierContract.getmCno());
		response.setMiles(carrierContract.getMiles());
		response.setRoleName(carrierContract.getRole().getTypeName());
		response.setTransDoc(carrierContract.getTransDoc());

		/*
		 * List<CarrierModel> carrierList = carrierService.getAll();
		 * response.setCarrierList(carrierList);
		 * 
		 * List<CategoryReq> categoryList = categoryService.getAll();
		 * response.setCategoryList(categoryList);
		 * 
		 * List<TypeResponse> commodityList = typeService.getAll(17l);
		 * response.setCommodityList(commodityList);
		 * 
		 * List<TypeResponse> currencyList = typeService.getAll(9l);
		 * response.setCurrencyList(currencyList);
		 * 
		 * List<DispatcherModel> dispatcherList = dispatcherService
		 * .getAllDispatcher(); response.setDispatcherList(dispatcherList);
		 * 
		 * List<DivisionReq> divisionList = divisionService.getAll("");
		 * response.setDivisionList(divisionList);
		 * 
		 * List<DriverReq> driverList = driverService.getAllDriver();
		 * response.setDriverList(driverList);
		 * 
		 * List<EquipmentReq> equipmentList = equipmentService.getAll("");
		 * response.setEquipmentList(equipmentList);
		 * 
		 * List<TypeResponse> roleList = typeService.getAll(6l);
		 * response.setRoleList(roleList);
		 * 
		 * List<CarrierAdditionalContactsModel> arrangedWithList =
		 * carrierAdditionalContactService .getAll();
		 * response.setArrangedWithList(arrangedWithList);
		 */

	}

	private void setCarrierContractDataById(CarrierContract carrierContract,
			CarrierContractModel response) {

		Session session = sessionFactory.openSession();
		
		try{
			response.setContractNoId(carrierContract.getContractNoId());
			response.setCargo(carrierContract.getCargo());
			response.setCarrierRat(carrierContract.getCarrierRat());
			response.setContractNo(carrierContract.getContractNo());
			response.setContractNoId(carrierContract.getContractNoId());
			response.setContractRate(carrierContract.getContractRate());
			response.setCreatedBy(carrierContract.getCreatedBy());
			response.setDispatched(carrierContract.getDispatched());
			response.setdOTno(carrierContract.getdOTno());
			response.setHours(carrierContract.getHours());
			response.setInsExpires(carrierContract.getInsExpires());
			response.setLiabity(carrierContract.getLiabity());
			response.setmCno(carrierContract.getmCno());
			response.setMiles(carrierContract.getMiles());
			response.setTransDoc(carrierContract.getTransDoc());
		 

			List<CarrierModel> carrierList = AllList.getCarrierList(session, "Carrier", "carrierId", "name");
			response.setCarrierList(carrierList);

			List<CategoryModel> categoryList = AllList.getCategoryList(session, "Category", "categoryId", "name");
			response.setCategoryList(categoryList);

			List<TypeResponse> commodityList = AllList.getTypeResponse(session, 17l);
			response.setCommodityList(commodityList);

			List<TypeResponse> currencyList = AllList.getTypeResponse(session, 9l);
			response.setCurrencyList(currencyList);

			List<DispatcherModel> dispatcherList = AllList.getDispatcherList(session, "Dispatcher", "id", "dispatcherName");
			response.setDispatcherList(dispatcherList);

			List<DivisionReq> divisionList = AllList.getDivisionList(session, "Division", "divisionId", "divisionName");
			response.setDivisionList(divisionList);

			List<DriverModel> driverList = AllList.getDriverList(session, "Driver", "driverId", "firstName");
			response.setDriverList(driverList);

			List<EquipmentReq> equipmentList = AllList.getEquipmentList(session, "Equipment", "equipmentId", "equipmentName");
			response.setEquipmentList(equipmentList);

			List<TypeResponse> roleList = AllList.getTypeResponse(session, 6l);
			response.setRoleList(roleList);

			List<ArrangedWithModel> arrangedWithList = AllList.getArrangedWithList(session, "ArrangedWith", "id", "arrangedWith");
			response.setArrangedWithList(arrangedWithList);
		
			}catch(Exception e){
			
			}finally{
				if(session != null){
					session.close();
				}
			}
	}

	@Override
	public Object addCarrierContract(CarrierContractModel carrierContractModel) {

		Session session = null;
		logger.info("Inside CarrierContractServiceImpl addCarrierContract() starts");
		Object obj = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			CarrierContract carrierContract = new CarrierContract();
			BeanUtils.copyProperties(carrierContractModel, carrierContract);

			carrierContract.setCarrier(carrierDao.findById(carrierContractModel
					.getCarrierId()));
			carrierContract.setArrangedWith(carrierAdditionalContactsDao
					.findById(carrierContractModel.getArrangedWithId()));

			carrierContract.setDriver(driverDao.findById(carrierContractModel
					.getDriverId()));
			carrierContract.setCurrency(typeService.get(carrierContractModel
					.getCurrencyId()));
			carrierContract.setCategory(categoryDao
					.findById(carrierContractModel.getCategoryId()));
			carrierContract.setRole(typeService.get(carrierContractModel
					.getRoleId()));
			carrierContract.setEquipment(equipmentDao
					.findById(carrierContractModel.getEquipmentId()));
			carrierContract.setCommodity(typeService.get(carrierContractModel
					.getCommodityId()));
			carrierContract.setDivision(divisionDao
					.findById(carrierContractModel.getDivisionId()));
			carrierContract.setDispatcher(dispatcherdao
					.findById(carrierContractModel.getDispatcherId()));
			session.save(carrierContract);
			tx.commit();
			obj = createSuccessObject(CarrierContract_added_message);
		} catch (Exception e) {
			logger.error("Exception inside CarrierContractServiceImpl addCarrierContract() :"
					+ e.getMessage());
			tx.rollback();
			obj = createFailedObject(CarrierContract_unable_to_add_message);
		}

		logger.info("Inside CarrierContractServiceImpl addCarrierContract() Ends");
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
		success.setResultList(getAllCarrierContract());
		return success;
	}

	@Override
	public CarrierContractModel getOpenAdd() {

		Session session = sessionFactory.openSession();
		CarrierContractModel carrierContractModel = new CarrierContractModel();
   
		try{
	    
			List<CarrierModel> carrierList = AllList.getCarrierList(session, "Carrier", "carrierId", "name");
			carrierContractModel.setCarrierList(carrierList);
			 
			List<Object[]> arrangedWithListObj = carrierContractDao.getSpecificData(session, "ArrangedWith", "id", "arrangedWith");
			List<ArrangedWithModel> arrangedWithList = new ArrayList<ArrangedWithModel>();
			Iterator<Object[]> arrangedWithItr = arrangedWithListObj.iterator();
		
			while(arrangedWithItr.hasNext())
			{
				Object o[] = (Object[])arrangedWithItr.next();
				ArrangedWithModel arrangedWithModel  = new ArrangedWithModel();
				arrangedWithModel.setId(Long.parseLong(String.valueOf(o[0])));
				arrangedWithModel.setArrangedWith(String.valueOf(o[1]));
				arrangedWithList.add(arrangedWithModel);
			}
			 
			carrierContractModel.setArrangedWithList(arrangedWithList);
 
			List<DriverModel> driverList = AllList.getDriverList(session, "Driver", "driverId", "firstName");
			carrierContractModel.setDriverList(driverList);

			List<TypeResponse> currencyList = AllList.getTypeResponse(session, 9l);
			carrierContractModel.setCurrencyList(currencyList);
 
			List<CategoryModel> categoryList = AllList.getCategoryList(session, "Category", "categoryId", "categoryId");
			carrierContractModel.setCategoryList(categoryList);

			List<TypeResponse> roleList = AllList.getTypeResponse(session, 6l);
			carrierContractModel.setRoleList(roleList);

					
			List<EquipmentReq> equipmentList = AllList.getEquipmentList(session, "Equipment", "equipmentId", "equipmentName");
			carrierContractModel.setEquipmentList(equipmentList);

			List<TypeResponse> commodityList = AllList.getTypeResponse(session, 17l);
			carrierContractModel.setCommodityList(commodityList);

			
			List<DivisionReq> divisionList = AllList.getDivisionList(session, "Division", "divisionId", "divisionName");
			carrierContractModel.setDivisionList(divisionList);

		 
			List<Object[]> dispatcherListObj = carrierContractDao.getSpecificData(session, "Dispatcher", "id", "dispatcherName");
			
			List<DispatcherModel> dispatcherList = new ArrayList<DispatcherModel>();
			Iterator<Object[]> dispatcherItr = dispatcherListObj.iterator();
		
			while(dispatcherItr.hasNext())
			{
				Object o[] = (Object[])dispatcherItr.next();
				DispatcherModel dispatcherReq  = new DispatcherModel();
				dispatcherReq.setId(Long.parseLong(String.valueOf(o[0])));
				dispatcherReq.setArrangedWith(String.valueOf(o[1]));
				dispatcherList.add(dispatcherReq);
			}
			carrierContractModel.setDispatcherList(dispatcherList);
			
	}catch(Exception e){
	
	}finally{
		if(session != null){
			session.close();
		}
}
		return carrierContractModel;
	}

	@Override
	public Object deleteCarrierContract(Long carrierContractId) {

		logger.info("Inside CarrierContractServiceImpl deleteCarrierContract() starts, driverId :"
				+ carrierContractId);
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			CarrierContract carrierContract = carrierContractDao
					.findById(carrierContractId);

			if (carrierContract != null) {
				session.delete(carrierContract);
				if (tx != null) {
					tx.commit();
				}
			} else {
				return createFailedObject(CarrierContract_unable_to_delete_message);
			}
		} catch (Exception e) {
			logger.error("Exceptiom inside CarrierContractServiceImpl deleteCarrierContract() :"
					+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(CarrierContract_dependent_message);
			}
			return createFailedObject(CarrierContract_unable_to_delete_message);
		} finally {
			
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside CarrierContractServiceImpl deleteCarrierContract() ends, driverId :"
				+ carrierContractId);
		return createSuccessObject(CarrierContract_deleted_message);

	}

	@Override
	public Object updateCarrierContract(Long carrierContractId,
			CarrierContractModel carrierContractModel) {

		logger.info("Inside CarrierContractServiceImpl updateCarrierContract() Starts, carrierContractId :"
				+ carrierContractId);
		Object obj = null;

		try {
			CarrierContract carrierContract = carrierContractDao
					.findById(carrierContractId);

			if (carrierContract != null) {
				String[] ignoreProp = new String[1];
				ignoreProp[0] = "contractNoId";
				BeanUtils.copyProperties(carrierContractModel, carrierContract,
						ignoreProp);

				carrierContract.setCarrier(carrierDao
						.findById(carrierContractModel.getCarrierId()));
				carrierContract.setArrangedWith(carrierAdditionalContactsDao
						.findById(carrierContract.getArrangedWith()));
				carrierContract.setDriver(driverDao
						.findById(carrierContractModel.getDriverId()));
				carrierContract.setCurrency(typeService
						.get(carrierContractModel.getCurrencyId()));
				carrierContract.setCategory(categoryDao
						.findById(carrierContractModel.getCategoryId()));
				carrierContract.setRole(typeService.get(carrierContractModel
						.getRoleId()));
				carrierContract.setEquipment(equipmentDao
						.findById(carrierContractModel.getEquipmentId()));
				carrierContract.setCommodity(typeService
						.get(carrierContractModel.getCommodityId()));
				carrierContract.setDivision(divisionDao
						.findById(carrierContractModel.getDivisionId()));
				carrierContract.setDispatcher(dispatcherDao
						.findById(carrierContractModel.getDispatcherId()));
				carrierContractDao.update(carrierContract);
				obj = createSuccessObject(CarrierContract_updated_message);
			} else {
				obj = createFailedObject(CarrierContract_unable_to_update_message);
			}

		} catch (Exception e) {
			logger.error("Exception inside CarrierContractServiceImpl updateCarrierContract() :"
					+ e.getMessage());
			obj = createFailedObject(CarrierContract_unable_to_update_message);
		}

		logger.info("Inside DriverServiceImpl updateCarrierContract() ends, carrierContractId :"
				+ carrierContractId);
		return obj;
	}

	@Override
	public Object getCarrierContractById(Long carrierContractId) {

		logger.info("Inside CarrierContractServiceImpl getCarrierContractById() Starts, carrierContractId :"
				+ carrierContractId);

		Session session = null;
		Object obj = null;
		String message = "CarrierContract data get Successfully";

		try {
			session = sessionFactory.openSession();

			CarrierContract carrierContract = (CarrierContract) session.get(CarrierContract.class, carrierContractId);
			CarrierContractModel response = new CarrierContractModel();
			setCarrierContractDataById(carrierContract, response);
			obj = createSuccessObjectForParRecord(message, response);

		} catch (Exception e) {
			message = "Error while getting record";
			obj = createFailedObject(message);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return obj;
	}

	private Object createSuccessObjectForParRecord(String message,
			CarrierContractModel response) {

		Success success = new Success();
		success.setMessage(message);
		success.setResultList(response);
		return success;
	}
}
