package com.dpu.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.dpu.dao.CategoryDao;
import com.dpu.dao.OrderDao;
import com.dpu.entity.Company;
import com.dpu.entity.CompanyAdditionalContacts;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.entity.Order;
import com.dpu.entity.OrderPickupDropNo;
import com.dpu.entity.Probil;
import com.dpu.entity.Shipper;
import com.dpu.entity.Status;
import com.dpu.entity.Type;
import com.dpu.model.CompanyResponse;
import com.dpu.model.Failed;
import com.dpu.model.OrderModel;
import com.dpu.model.OrderPickUpDeliveryModel;
import com.dpu.model.ProbilModel;
import com.dpu.model.ShipperModel;
import com.dpu.model.Success;
import com.dpu.model.TypeResponse;
import com.dpu.service.CompanyService;
import com.dpu.service.OrderService;
import com.dpu.service.ShipperService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;

@Component
public class OrderServiceImpl implements OrderService {

	Logger logger = Logger.getLogger(OrderServiceImpl.class);

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	StatusService statusService;
	
	@Autowired
	TypeService typeService;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	ShipperService shipperService;
	
	@Value("${order_unable_to_add_message}")
	private String order_unable_to_add_message;

	@Value("${order_unable_to_delete_message}")
	private String order_unable_to_delete_message;
	
	@Value("${order_unable_to_update_message}")
	private String order_unable_to_update_message;
	
	@Value("${order_added_message}")
	private String order_added_message;
	
	@Value("${order_deleted_message}")
	private String order_deleted_message;
	
	@Value("${order_updated_message}")
	private String order_updated_message;
	
	@Value("${order_already_used_message}")
	private String order_already_used_message;
	
	@Value("${probil_deleted_message}")
	private String probil_deleted_message;
	
	@Value("${probil_unable_to_delete_message}")
	private String probil_unable_to_delete_message;
	
	@Value("${probil_already_used_message}")
	private String probil_already_used_message;
	
	private Order addOrder(OrderModel orderModel, Session session) {
		Company company = (Company) session.get(Company.class, orderModel.getCompanyId());
		CompanyBillingLocation billingLocation = (CompanyBillingLocation) session.get(CompanyBillingLocation.class, orderModel.getBillingLocationId());
		CompanyAdditionalContacts additionalContacts = (CompanyAdditionalContacts) session.get(CompanyAdditionalContacts.class, orderModel.getContactId());
		Type temp = (Type) session.get(Type.class, orderModel.getTemperatureId());
		Type tempType = (Type) session.get(Type.class, orderModel.getTemperatureTypeId());
		Type currency = (Type) session.get(Type.class, orderModel.getCurrencyId());
		
		Order order = new Order();
		BeanUtils.copyProperties(orderModel, order);
		order.setCompany(company);
		order.setBillingLocation(billingLocation);
		order.setContact(additionalContacts);
		order.setTemperature(temp);
		order.setTemperatureType(tempType);
		order.setCurrency(currency);
		orderDao.saveOrder(session, order);
		return order;
		
	}
	
	private void addProbil(ProbilModel probilModel, Order order, Session session) {
		
		Probil probil = new Probil();
		Long maxProbilNo = orderDao.getMaxProbilNo(session);
		
		BeanUtils.copyProperties(probilModel, probil);
		Shipper shipper = (Shipper) session.get(Shipper.class, probilModel.getShipperId());
		Shipper consinee = (Shipper) session.get(Shipper.class, probilModel.getConsineeId());
		Type pickUp = (Type) session.get(Type.class, probilModel.getPickupId());
		Type delivery = (Type) session.get(Type.class, probilModel.getDeliveryId());
		
		probil.setConsine(consinee);
		probil.setShipper(shipper);
		probil.setPickUp(pickUp);
		probil.setDelivery(delivery);
		probil.setOrder(order);
		probil.setProbilNo(maxProbilNo+1);
		
		String pickUpScheduledDate = probilModel.getPickupScheduledDate();
		String pickUpMabDate = probilModel.getPickupMABDate();
		String deliveryScheduledDate = probilModel.getDeliverScheduledDate();
		String deliveryMabData = probilModel.getDeliveryMABDate();
		
		probil.setPickupScheduledDate(changeStringToDate(pickUpScheduledDate));
		probil.setPickupMABDate(changeStringToDate(pickUpMabDate));
		probil.setDeliverScheduledDate(changeStringToDate(deliveryScheduledDate));
		probil.setDeliveryMABDate(changeStringToDate(deliveryMabData));
		
		String pickUpScheduledTime = probilModel.getPickupScheduledTime();
		String pickUpMabTime = probilModel.getPickupMABTime();
		String deliveryScheduledTime = probilModel.getDeliverScheduledTime();
		String deliveryMabTime = probilModel.getDeliveryMABTime();
		
		probil.setDeliverScheduledTime(changeStringToTime(deliveryScheduledTime));
		probil.setDeliveryMABTime(changeStringToTime(deliveryMabTime));
		probil.setPickupScheduledTime(changeStringToTime(pickUpScheduledTime));
		probil.setPickupMABTime(changeStringToTime(pickUpMabTime));
		
		orderDao.saveProbil(session, probil);
		
		addPickUpDelivery(probilModel, probil, session);
	}
	
	private void addPickUpDelivery(ProbilModel probilModel, Probil probil, Session session) {
		List<OrderPickUpDeliveryModel> orderPickUpDeliveryList = probilModel.getOrderPickUpDeliveryList();
		
		if(orderPickUpDeliveryList != null && !orderPickUpDeliveryList.isEmpty()){
			
			for (OrderPickUpDeliveryModel orderPickUpDeliveryModel : orderPickUpDeliveryList) {
				OrderPickupDropNo pickUpDropNo = new OrderPickupDropNo();
				BeanUtils.copyProperties(orderPickUpDeliveryModel, pickUpDropNo);
				pickUpDropNo.setProbil(probil);
				
				orderDao.savePickUpDrop(session, pickUpDropNo);
			}
		}
		
	}

	@Override
	public Object addOrder(OrderModel orderModel) {
		
		logger.info("Inside OrderServiceImpl addOrder() starts ");
		Session session = null;
		Transaction tx = null;
		
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Order order = addOrder(orderModel, session);
			List<ProbilModel> probils = orderModel.getProbilList();
			if(probils != null && !probils.isEmpty()){
				for (ProbilModel probilModel : probils) {
					addProbil(probilModel, order, session);
				}
			}
			
		} catch(Exception e){
			if(tx != null){
				tx.rollback();
			}
			return createFailedObject(order_unable_to_add_message);
		} finally{
			if(tx != null){
				tx.commit();
			} 
			if(session != null){
				session.close();
			}
		}
		
		logger.info("Inside OrderServiceImpl addOrder() starts ");
		return createSuccessObject(order_added_message,"add");
	}
	
	

	private Date changeStringToTime(String timeVal) {
		String[] stArr = timeVal.split(":");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(stArr[0]));
		cal.set(Calendar.MINUTE,Integer.parseInt(stArr[1]));
		cal.set(Calendar.SECOND,Integer.parseInt(stArr[2]));
		Date date = cal.getTime();
		return date;
	}

	private Date changeStringToDate(String dateVal) {
		String[] stArr = dateVal.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(stArr[0]), Integer.parseInt(stArr[1]), Integer.parseInt(stArr[2]));
		Date date = cal.getTime();
		return date;
}

	private Object createSuccessObject(String message, String type) {
		Success success = new Success();
		success.setMessage(message);
		if(!type.equals("deleteProbil")){
			success.setResultList(getAllOrders());
		}
		return success;
	}
	
	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	@Override
	public Object update(Long orderId, OrderModel orderModel) {
		
		logger.info(" OrderServiceImpl update() starts, orderId :"+orderId); 
		Session session = null;
		Transaction tx = null;
		
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Order order = updateOrder(orderModel, session);
			
			List<ProbilModel> probilList = orderModel.getProbilList();
			if(probilList != null && ! probilList.isEmpty()){
				for (ProbilModel probilModel : probilList) {
					updateProbil(probilModel, order, session);
				}
			}
		} catch(Exception e){
			if(tx != null){
				tx.rollback();
			}
			logger.info(" Exception inside OrderServiceImpl update() :"+e.getMessage());
			return createFailedObject(order_unable_to_update_message);
		} finally{
			if(tx != null){
				tx.commit();
			} 
			if(session != null){
				session.close();
			}
		}
		
		logger.info(" OrderServiceImpl update() ends, orderId :"+orderId);
		return createSuccessObject(order_updated_message,"update");
	}
	
	private Order updateOrder(OrderModel orderModel, Session session) {
		
		Company company = (Company) session.get(Company.class, orderModel.getCompanyId());
		CompanyBillingLocation billingLocation = (CompanyBillingLocation) session.get(CompanyBillingLocation.class, orderModel.getBillingLocationId());
		CompanyAdditionalContacts additionalContacts = (CompanyAdditionalContacts) session.get(CompanyAdditionalContacts.class, orderModel.getContactId());
		Type temp = (Type) session.get(Type.class, orderModel.getTemperatureId());
		Type tempType = (Type) session.get(Type.class, orderModel.getTemperatureTypeId());
		Type currency = (Type) session.get(Type.class, orderModel.getCurrencyId());
		
		Order order = (Order) session.get(Order.class, orderModel.getId());
		BeanUtils.copyProperties(orderModel, order);
		order.setCompany(company);
		order.setBillingLocation(billingLocation);
		order.setContact(additionalContacts);
		order.setTemperature(temp);
		order.setTemperatureType(tempType);
		order.setCurrency(currency);
		orderDao.updateOrder(session, order);
		return order;
		
	}

	private void updateProbil(ProbilModel probilModel, Order order, Session session) {
		
		Probil probil = null;
		if(probilModel.getId() != null){
			probil = (Probil) session.get(Probil.class, probilModel.getId());
		} else{
			 probil = new Probil();
		}
		
		BeanUtils.copyProperties(probilModel, probil);
		
		if(probilModel.getId() == null){
			Long maxProbilNo = orderDao.getMaxProbilNo(session);
			probil.setProbilNo(maxProbilNo + 1);
		}
		
		Shipper shipper = (Shipper) session.get(Shipper.class, probilModel.getShipperId());
		Shipper consinee = (Shipper) session.get(Shipper.class, probilModel.getConsineeId());
		Type pickUp = (Type) session.get(Type.class, probilModel.getPickupId());
		Type delivery = (Type) session.get(Type.class, probilModel.getDeliveryId());
		
		probil.setConsine(consinee);
		probil.setShipper(shipper);
		probil.setPickUp(pickUp);
		probil.setDelivery(delivery);
		probil.setOrder(order);
		
		String pickUpScheduledDate = probilModel.getPickupScheduledDate();
		String pickUpMabDate = probilModel.getPickupMABDate();
		String deliveryScheduledDate = probilModel.getDeliverScheduledDate();
		String deliveryMabData = probilModel.getDeliveryMABDate();
		
		probil.setPickupScheduledDate(changeStringToDate(pickUpScheduledDate));
		probil.setPickupMABDate(changeStringToDate(pickUpMabDate));
		probil.setDeliverScheduledDate(changeStringToDate(deliveryScheduledDate));
		probil.setDeliveryMABDate(changeStringToDate(deliveryMabData));
		
		String pickUpScheduledTime = probilModel.getPickupScheduledTime();
		String pickUpMabTime = probilModel.getPickupMABTime();
		String deliveryScheduledTime = probilModel.getDeliverScheduledTime();
		String deliveryMabTime = probilModel.getDeliveryMABTime();
		
		probil.setDeliverScheduledTime(changeStringToTime(deliveryScheduledTime));
		probil.setDeliveryMABTime(changeStringToTime(deliveryMabTime));
		probil.setPickupScheduledTime(changeStringToTime(pickUpScheduledTime));
		probil.setPickupMABTime(changeStringToTime(pickUpMabTime));
		
		orderDao.updateProbil(session, probil);
		
		updatePickUpDelivery(probilModel, probil, session);
	}

	private void updatePickUpDelivery(ProbilModel probilModel, Probil probil, Session session) {
		List<OrderPickUpDeliveryModel> orderPickUpDeliveryList = probilModel.getOrderPickUpDeliveryList();
		
		if(orderPickUpDeliveryList != null && !orderPickUpDeliveryList.isEmpty()){
			
			for (OrderPickUpDeliveryModel orderPickUpDeliveryModel : orderPickUpDeliveryList) {
				OrderPickupDropNo pickUpDropNo = new OrderPickupDropNo();
				BeanUtils.copyProperties(orderPickUpDeliveryModel, pickUpDropNo);
				pickUpDropNo.setProbil(probil);
				
				orderDao.updatePickUpDrop(session, pickUpDropNo);
			}
		}
		
	}
	@Override
	public Object deleteOrder(Long orderId) {

		logger.info("Inside OrderServiceImpl deleteOrder() starts, orderId :"+ orderId);
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Order order = (Order) session.get(Order.class, orderId);
			if(order != null){
				List<Probil> probilList = order.getProbils();
				if(probilList != null && !probilList.isEmpty()){
					for (Probil probil : probilList) {
						List<OrderPickupDropNo> orderPickUpDropNos = probil.getOrderPickupDropNos();
						if(orderPickUpDropNos != null && !orderPickUpDropNos.isEmpty()){
							for (OrderPickupDropNo orderPickupDropNo : orderPickUpDropNos) {
								session.delete(orderPickupDropNo);
							}
						}
						
						session.delete(probil);
					}
				}
				
				session.delete(order);
				tx.commit();
				
			} else{
				return createFailedObject(order_unable_to_delete_message);
			}
		}catch(Exception e){
			logger.error("Exception Inside OrderServiceImpl deleteOrder() :"+ e.getMessage());
			if(tx != null){
				tx.rollback();
			}
			if(e instanceof ConstraintViolationException){
				return createFailedObject(order_already_used_message);
			}
			return createFailedObject(order_unable_to_delete_message);
		} finally{
			/*if(tx != null){
				tx.commit();
			}*/
			if(session != null){
				session.close();
			}
		}
		logger.info("Inside OrderServiceImpl deleteOrder() ends, orderId :"+ orderId);
		return createSuccessObject(order_deleted_message,"order");
	}
	
	
	@Override
	public Object deleteProbil(Long orderId, Long probilId) {
	
		logger.info("Inside OrderServiceImpl deleteProbil() starts, orderId :"+ orderId+": probilId :"+probilId);
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Probil probil = (Probil) orderDao.getProbilByProbilId(orderId, probilId, session);
			if(probil != null){
				List<OrderPickupDropNo> orderPickUpDropNos = probil.getOrderPickupDropNos();
				if(orderPickUpDropNos != null && !orderPickUpDropNos.isEmpty()){
					for (OrderPickupDropNo orderPickupDropNo : orderPickUpDropNos) {
						session.delete(orderPickupDropNo);
					}
				}
						
				session.delete(probil);
				
				tx.commit();
				
			} else{
				return createFailedObject(probil_unable_to_delete_message);
			}
		}catch(Exception e){
			logger.error("Exception Inside OrderServiceImpl deleteProbil() :"+ e.getMessage());
			if(tx != null){
				tx.rollback();
			}
			if(e instanceof ConstraintViolationException){
				return createFailedObject(probil_already_used_message);
			}
			return createFailedObject(probil_unable_to_delete_message);
		} finally{
			/*if(tx != null){
				tx.commit();
			}*/
			if(session != null){
				session.close();
			}
		}
		
		logger.info("Inside OrderServiceImpl deleteProbil() ends, orderId :"+ orderId+": probilId :"+probilId);
		return createSuccessObject(probil_deleted_message,"deleteProbil");
	}
	
	@Override
	public List<OrderModel> getAllOrders() {

		logger.info("Inside OrderServiceImpl getAllOrders() starts");
		Session session = null;
		List<OrderModel> allOrders = new ArrayList<OrderModel>();
		
		try{
			
			session = sessionFactory.openSession();
			List<Order> orders = orderDao.findAll(session);
			
			if(orders != null && ! orders.isEmpty()){
				for (Order order : orders) {
					OrderModel orderModel = new OrderModel();
					BeanUtils.copyProperties(order, orderModel);
					orderModel.setCompanyName(order.getCompany().getName());
					orderModel.setBillingLocationName(order.getBillingLocation().getName());
					orderModel.setContactName(order.getContact().getCustomerName());
					orderModel.setTemperatureName(order.getTemperature().getTypeName());
					orderModel.setTemperatureTypeName(order.getTemperatureType().getTypeName());
					orderModel.setCurrencyName(order.getCurrency().getTypeName());
					allOrders.add(orderModel);
				}
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		logger.info("Inside OrderServiceImpl getAllOrders() starts");
		return allOrders;
	}

	@Override
	public OrderModel getOpenAdd() {

		OrderModel order = new OrderModel();
		Session session = sessionFactory.openSession();
		
		try{
		@SuppressWarnings("unused")
		List<Status> statusList = AllList.getStatusList(session);
		
		List<TypeResponse> temperatureList = AllList.getTypeResponse(session, 12l);
		order.setTemperatureList(temperatureList);
		
		List<TypeResponse> temperatureTypeList = AllList.getTypeResponse(session, 13l);
		order.setTemperatureTypeList(temperatureTypeList);
		
		//List<CompanyResponse> companyData = companyService.getCompanyData();
		List<CompanyResponse> companyData = AllList.getCompanyList(session, "Company", "companyId", "name");
		order.setCompanyList(companyData);
		
		List<TypeResponse> currencyList = AllList.getTypeResponse(session, 9l);
		order.setCurrencyList(currencyList);
		
		List<ShipperModel> shipperConsineeList = shipperService.getSpecificData(session);
		order.setShipperConsineeList(shipperConsineeList);
		
		List<TypeResponse> pickUpTypes = AllList.getTypeResponse(session, 10l);
		order.setPickupList(pickUpTypes);
		
		List<TypeResponse> deliveryTypes = AllList.getTypeResponse(session, 11l);
		order.setDeliveryList(deliveryTypes);
		}catch(Exception e){
			
		}finally{
			if(session != null){
				session.close();
			}
		}
		return order;
	}

	@Override
	public CompanyResponse getCompanyData(Long companyId) {
		CompanyResponse companyResponse = companyService.getCompanyBillingLocationAndContacts(companyId);
		return companyResponse;
	}

	@Override
	public OrderModel getOrderByOrderId(Long orderId) {
	
		Session session = null;
		OrderModel orderModel = new OrderModel();
		
		try{
			
			session = sessionFactory.openSession();
			Order order = orderDao.findByOrderId(orderId, session);
			
			if(order != null){
				/*for (Order order : orders) {*/
					/*OrderModel orderModel = new OrderModel();*/
					BeanUtils.copyProperties(order, orderModel);
					orderModel.setCompanyName(order.getCompany().getName());
					orderModel.setCompanyId(order.getCompany().getCompanyId());
					
					orderModel.setBillingLocationName(order.getBillingLocation().getName());
					orderModel.setBillingLocationId(order.getBillingLocation().getBillingLocationId());
					
					orderModel.setContactName(order.getContact().getCustomerName());
					orderModel.setContactId(order.getContact().getAdditionalContactId());
					
					orderModel.setTemperatureName(order.getTemperature().getTypeName());
					orderModel.setTemperatureId(order.getTemperature().getTypeId());
					
					orderModel.setTemperatureTypeName(order.getTemperatureType().getTypeName());
					orderModel.setTemperatureTypeId(order.getTemperatureType().getTypeId());
					
					orderModel.setCurrencyName(order.getCurrency().getTypeName());
					orderModel.setCurrencyId(order.getCurrency().getTypeId());
					
					List<Probil> probilList = order.getProbils();
					List<ProbilModel> probils = new ArrayList<ProbilModel>();
					for (Probil probil : probilList) {
						
						/*Date d = probil.getPickupScheduledDate();*/
								/*System.out.println("the date is "+d);*/
						ProbilModel probilModel = new ProbilModel();
						BeanUtils.copyProperties(probil, probilModel);
						probilModel.setConsineeName(probil.getConsine().getLocationName());
						probilModel.setConsineeId(probil.getConsine().getShipperId());
						List<ShipperModel> consineeList = new ArrayList<ShipperModel>();
						consineeList.add(shipperService.getParticularData(probil.getConsine().getShipperId()));
						probilModel.setConsineeList(consineeList);
						
						probilModel.setShipperName(probil.getShipper().getLocationName());
						probilModel.setShipperId(probil.getShipper().getShipperId());
						List<ShipperModel> shipperList = new ArrayList<ShipperModel>();
						shipperList.add(shipperService.getParticularData(probil.getShipper().getShipperId()));
						probilModel.setShipperList(shipperList);
						
						probilModel.setPickupName(probil.getPickUp().getTypeName());
						probilModel.setPickupId(probil.getPickUp().getTypeId());
						
						probilModel.setDeliveryName(probil.getDelivery().getTypeName());
						probilModel.setDeliveryId(probil.getDelivery().getTypeId());
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String pickUpScheduledDate = sdf.format(probil.getPickupScheduledDate());
						String deliverScheduledDate = sdf.format(probil.getDeliverScheduledDate());
						String pickUpMABDate = sdf.format(probil.getPickupMABDate());
						String deliverMABDate = sdf.format(probil.getDeliveryMABDate());
						
						probilModel.setPickupScheduledDate(pickUpScheduledDate);
						probilModel.setPickupMABDate(pickUpMABDate);
						probilModel.setDeliverScheduledDate(deliverScheduledDate);
						probilModel.setDeliveryMABDate(deliverMABDate);
						
						SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
						String pickUpScheduledTime = localDateFormat.format(probil.getPickupScheduledTime());
						String pickUpMABTime = localDateFormat.format(probil.getPickupMABTime());
						String deliverScheduledTime = localDateFormat.format(probil.getDeliverScheduledTime());
						String deliverMABTime =  localDateFormat.format(probil.getDeliveryMABTime());
						
						probilModel.setPickupScheduledTime(pickUpScheduledTime);
						probilModel.setPickupMABTime(pickUpMABTime);
						probilModel.setDeliverScheduledTime(deliverScheduledTime);
						probilModel.setDeliveryMABTime(deliverMABTime);
						
						List<OrderPickupDropNo> probilPickUpDrop = probil.getOrderPickupDropNos();
						List<OrderPickUpDeliveryModel> OrderPickUpDeliveryModels = getProbilPickUpDropData(probilPickUpDrop);
						probilModel.setOrderPickUpDeliveryList(OrderPickUpDeliveryModels);
						probils.add(probilModel);
					}
					
					orderModel.setProbilList(probils);
					
					List<TypeResponse> temperatureList = AllList.getTypeResponse(session, 12l);
					orderModel.setTemperatureList(temperatureList);
					
					List<TypeResponse> temperatureTypeList = AllList.getTypeResponse(session, 13l);
					orderModel.setTemperatureTypeList(temperatureTypeList);
					
					//List<CompanyResponse> companyData = companyService.getCompanyData();
					List<CompanyResponse> companyData = AllList.getCompanyList(session, "Company", "companyId", "name");
					orderModel.setCompanyList(companyData);
					
					List<TypeResponse> currencyList = AllList.getTypeResponse(session, 9l);
					orderModel.setCurrencyList(currencyList);
					
					List<ShipperModel> shipperConsineeList = shipperService.getSpecificData(session);
					orderModel.setShipperConsineeList(shipperConsineeList);
					
					List<TypeResponse> pickUpTypes = AllList.getTypeResponse(session, 10l);;
					orderModel.setPickupList(pickUpTypes);
					
					List<TypeResponse> deliveryTypes = AllList.getTypeResponse(session, 11l);;
					orderModel.setDeliveryList(deliveryTypes);
					
					CompanyResponse companyResponse = companyService.getCompanyBillingLocationAndContacts(order.getCompany().getCompanyId());
					if(companyResponse != null){
						orderModel.setCompanyResponse(companyResponse);
					}
					
			/*	}*/
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return orderModel;
	}

	private List<OrderPickUpDeliveryModel> getProbilPickUpDropData(List<OrderPickupDropNo> probilPickUpDrop) {
		List<OrderPickUpDeliveryModel> orderPickUpDeliveryModels = new ArrayList<OrderPickUpDeliveryModel>();
		
		if(probilPickUpDrop != null && !probilPickUpDrop.isEmpty()){
			for (OrderPickupDropNo orderPickupDropNo : probilPickUpDrop) {
				OrderPickUpDeliveryModel OrderPickUpDeliveryModel = new OrderPickUpDeliveryModel();
				BeanUtils.copyProperties(orderPickupDropNo, OrderPickUpDeliveryModel);
				orderPickUpDeliveryModels.add(OrderPickUpDeliveryModel);
			}
		}
		
		return orderPickUpDeliveryModels;
	}

	@Override
	public List<OrderModel> getOrdersByCompanyName(String companyName) {

		Session session = null;
		List<OrderModel> allOrders = new ArrayList<OrderModel>();
		
		try{
			
			session = sessionFactory.openSession();
			List<Order> orders = orderDao.findOrderByCompanyName(session, companyName);
			
			if(orders != null && ! orders.isEmpty()){
				for (Order order : orders) {
					OrderModel orderModel = new OrderModel();
					BeanUtils.copyProperties(order, orderModel);
					orderModel.setCompanyName(order.getCompany().getName());
					orderModel.setBillingLocationName(order.getBillingLocation().getName());
					orderModel.setContactName(order.getContact().getCustomerName());
					orderModel.setTemperatureName(order.getTemperature().getTypeName());
					orderModel.setTemperatureTypeName(order.getTemperatureType().getTypeName());
					orderModel.setCurrencyName(order.getCurrency().getTypeName());
					
					allOrders.add(orderModel);
				}
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return allOrders;
	}

	@Override
	public ProbilModel getProbilByProbilId(Long orderId, Long probilId) {
		
		Session session = null;
		ProbilModel probilModel = new ProbilModel();
		
		try{
			session = sessionFactory.openSession();
			Probil probil = orderDao.getProbilData(orderId, probilId, session);
			if(probil != null){
				BeanUtils.copyProperties(probil, probilModel);
				probilModel.setConsineeName(probil.getConsine().getLocationName());
				probilModel.setShipperName(probil.getShipper().getLocationName());
				probilModel.setPickupName(probil.getPickUp().getTypeName());
				probilModel.setDeliveryName(probil.getDelivery().getTypeName());
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String pickUpScheduledDate = sdf.format(probil.getPickupScheduledDate());
				String deliverScheduledDate = sdf.format(probil.getDeliverScheduledDate());
				String pickUpMABDate = sdf.format(probil.getPickupMABDate());
				String deliverMABDate = sdf.format(probil.getDeliveryMABDate());
				
				probilModel.setPickupScheduledDate(pickUpScheduledDate);
				probilModel.setPickupMABDate(pickUpMABDate);
				probilModel.setDeliverScheduledDate(deliverScheduledDate);
				probilModel.setDeliveryMABDate(deliverMABDate);
				
				SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
				String pickUpScheduledTime = localDateFormat.format(probil.getPickupScheduledTime());
				String pickUpMABTime = localDateFormat.format(probil.getPickupMABTime());
				String deliverScheduledTime = localDateFormat.format(probil.getDeliverScheduledTime());
				String deliverMABTime =  localDateFormat.format(probil.getDeliveryMABTime());
				
				probilModel.setPickupScheduledTime(pickUpScheduledTime);
				probilModel.setPickupMABTime(pickUpMABTime);
				probilModel.setDeliverScheduledTime(deliverScheduledTime);
				probilModel.setDeliveryMABTime(deliverMABTime);
			}
			
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return probilModel;
		
	}

	

	

}
