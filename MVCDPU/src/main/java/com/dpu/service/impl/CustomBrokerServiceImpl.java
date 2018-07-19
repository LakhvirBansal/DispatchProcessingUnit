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
import org.springframework.stereotype.Component;

import com.dpu.common.AllList;
import com.dpu.common.CommonProperties;
import com.dpu.dao.CustomBrokerDao;
import com.dpu.dao.CustomBrokerTypeDao;
import com.dpu.entity.CustomBroker;
import com.dpu.entity.CustomBrokerType;
import com.dpu.entity.Status;
import com.dpu.entity.Type;
import com.dpu.model.CustomBrokerResponse;
import com.dpu.model.CustomBrokerTypeModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TypeResponse;
import com.dpu.service.CustomBrokerService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;

@Component
public class CustomBrokerServiceImpl implements CustomBrokerService {

	@Autowired
	CustomBrokerDao customBrokerDao;

	 
	@Autowired
	StatusService statusService;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	TypeService typeService;

	@Autowired
	CustomBrokerTypeDao customBrokerTypeDao;

	Logger logger = Logger.getLogger(CustomBrokerServiceImpl.class);

	private Object createSuccessObject(String msg, long code) {

		Success success = new Success();
		success.setCode(code);
		success.setMessage(msg);
		success.setResultList(getAll());
		return success;
	}

	private Object createFailedObject(String msg, long code) {

		Failed failed = new Failed();
		failed.setCode(code);
		failed.setMessage(msg);
		failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object add(CustomBrokerResponse customBrokerReponse) {

		logger.info("Inside CustomBrokerServiceImpl add() starts ");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			CustomBroker customBroker = addCustomBroker(customBrokerReponse,
					session);

			List<CustomBrokerTypeModel> customBrokerTypes = customBrokerReponse
					.getCustomBrokerTypes();
			if (customBrokerTypes != null && !customBrokerTypes.isEmpty()) {
				for (CustomBrokerTypeModel customBrokerTypeModel : customBrokerTypes) {
					addCustomBrokerType(customBrokerTypeModel, customBroker,
							session);
				}
			}

		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerServiceImpl add() :"
					+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			return createFailedObject(
					CommonProperties.custombroker_unable_to_add_message, 0l);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside CustomBrokerServiceImpl add() ends ");
		return createSuccessObject(CommonProperties.custombroker_added_message,
				0l);
	}

	private void addCustomBrokerType(
			CustomBrokerTypeModel customBrokerTypeModel,
			CustomBroker customBroker, Session session) {

		CustomBrokerType customBrokerType = new CustomBrokerType();
		BeanUtils.copyProperties(customBrokerTypeModel, customBrokerType);
		Type operation = (Type) session.get(Type.class,
				customBrokerTypeModel.getOperationId());
		Type timeZone = (Type) session.get(Type.class,
				customBrokerTypeModel.getTimeZoneId());
		Type type = (Type) session.get(Type.class,
				customBrokerTypeModel.getTypeId());
		Status status = (Status) session.get(Status.class,
				customBrokerTypeModel.getStatusId());

		customBrokerType.setCustomBroker(customBroker);
		customBrokerType.setStatus(status);
		customBrokerType.setOperation(operation);
		customBrokerType.setTimeZone(timeZone);
		customBrokerType.setType(type);
		session.saveOrUpdate(customBrokerType);
	}

	private CustomBroker addCustomBroker(
			CustomBrokerResponse customBrokerReponse, Session session) {

		CustomBroker customBroker = new CustomBroker();
		Type type = (Type) session.get(Type.class,
				customBrokerReponse.getTypeId());
		customBroker.setType(type);
		customBroker.setCustomBrokerName(customBrokerReponse
				.getCustomBrokerName());
		session.save(customBroker);
		return customBroker;

	}

	@Override
	public Object update(Long id, CustomBrokerResponse customBrokerReponse) {

		logger.info("Inside CustomBrokerServiceImpl update() starts ");
		Session session = null;
		Transaction tx = null;

		try {

			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			CustomBroker customBroker = (CustomBroker) session.get(
					CustomBroker.class, id);

			if (customBroker != null) {

				List<CustomBrokerTypeModel> customBrokerTypeModels = customBrokerReponse
						.getCustomBrokerTypes();
				if (customBrokerReponse.getTypeId() == 50l) {
					for (CustomBrokerTypeModel customBrokerTypeModel : customBrokerTypeModels) {
						addCustomBrokerType(customBrokerTypeModel,
								customBroker, session);
					}
				} else {
					List<CustomBrokerType> customBrokerTypes = customBroker
							.getCustomerBrokerTypes(); // already mapped list
					if (customBrokerTypes != null
							&& !customBrokerTypes.isEmpty()) {

						for (CustomBrokerType customBrokerType : customBrokerTypes) {

							if (customBrokerType.getType().getTypeId() == customBrokerReponse
									.getTypeId()) {
								for (CustomBrokerTypeModel customBrokerTypeModel : customBrokerTypeModels) {
									if (customBrokerTypeModel.getTypeId() == customBrokerType
											.getType().getTypeId()) {
										updateCustomBrokerType(
												customBrokerTypeModel,
												customBrokerType, session);
										break;
									}
								}
							} else {
								session.delete(customBrokerType);
							}
						}
					}
				}

				Type type = (Type) session.get(Type.class,
						customBrokerReponse.getTypeId());
				customBroker.setType(type);
				customBroker.setCustomBrokerName(customBrokerReponse
						.getCustomBrokerName());
				session.saveOrUpdate(customBroker);
			}

		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerServiceImpl update() :"
					+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			return createFailedObject(
					CommonProperties.custombroker_unable_to_update_message, 0l);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside CustomBrokerServiceImpl update() ends ");
		return createSuccessObject(
				CommonProperties.custombroker_updated_message, 0l);
	}

	private void updateCustomBrokerType(
			CustomBrokerTypeModel customBrokerTypeModel,
			CustomBrokerType customBrokerType, Session session) {

		BeanUtils.copyProperties(customBrokerTypeModel, customBrokerType);
		Type operation = (Type) session.get(Type.class,
				customBrokerTypeModel.getOperationId());
		Type timeZone = (Type) session.get(Type.class,
				customBrokerTypeModel.getTimeZoneId());
		Type type = (Type) session.get(Type.class,
				customBrokerTypeModel.getTypeId());
		Status status = (Status) session.get(Status.class,
				customBrokerTypeModel.getStatusId());

		customBrokerType.setStatus(status);
		customBrokerType.setOperation(operation);
		customBrokerType.setTimeZone(timeZone);
		customBrokerType.setType(type);
		session.saveOrUpdate(customBrokerType);
	}

	@Override
	public Object delete(Long id) {

		logger.info("Inside CustomBrokerServiceImpl delete() starts ");
		CustomBroker customBroker = null;
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			customBroker = (CustomBroker) session.get(CustomBroker.class, id);
			if (customBroker != null) {
				List<CustomBrokerType> customBrokers = customBroker
						.getCustomerBrokerTypes();

				if (customBrokers != null && !customBrokers.isEmpty()) {
					for (CustomBrokerType customBrokerType : customBrokers) {
						session.delete(customBrokerType);
					}
				}

				session.delete(customBroker);
			}

		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerServiceImpl delete() :"
					+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(
						CommonProperties.custombroker_dependent_message, 0l);
			}
			return createFailedObject(
					CommonProperties.custombroker_unable_to_delete_message, 0l);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside CustomBrokerServiceImpl delete() ends ");
		return createSuccessObject(
				CommonProperties.custombroker_deleted_message, 0l);
	}

	@Override
	public List<CustomBrokerResponse> getAll() {

		logger.info("Inside CustomBrokerServiceImpl getAll() starts ");
		Session session = null;
		List<CustomBrokerResponse> customBrokerResponseList = new ArrayList<CustomBrokerResponse>();
		try {

			session = sessionFactory.openSession();
			List<CustomBroker> customBrokerList = customBrokerDao.findAll(null,
					session);

			if (customBrokerList != null && !customBrokerList.isEmpty()) {
				for (CustomBroker customBroker : customBrokerList) {
					Long customerBrokerId = customBroker.getCustomBrokerId();
					CustomBrokerResponse customBrokerResponseObj = new CustomBrokerResponse();
					customBrokerResponseObj.setCustomBrokerId(customerBrokerId);
					customBrokerResponseObj.setCustomBrokerName(customBroker
							.getCustomBrokerName());

					List<CustomBrokerType> customBrokerTypeList = customBrokerTypeDao
							.getAll(session, customerBrokerId);

					if (customBrokerTypeList != null
							&& !customBrokerTypeList.isEmpty()) {
						List<CustomBrokerTypeModel> customBrokerTypeModelList = new ArrayList<CustomBrokerTypeModel>();

						for (CustomBrokerType customBrokerType : customBrokerTypeList) {
							String customerBrokerTypeName = customBrokerType
									.getType().getTypeName();
							CustomBrokerTypeModel customBrokerTypeModel = new CustomBrokerTypeModel();
							BeanUtils.copyProperties(customBrokerType,
									customBrokerTypeModel);

							if (customerBrokerTypeName != null) {
								customBrokerTypeModel
										.setTypeName(customerBrokerTypeName);
							}

							customBrokerTypeModelList
									.add(customBrokerTypeModel);
							customBrokerResponseObj
									.setCustomBrokerTypes(customBrokerTypeModelList);
						}
					}

					customBrokerResponseList.add(customBrokerResponseObj);
				}

			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside CustomBrokerServiceImpl getAll() ends ");
		return customBrokerResponseList;
	}

	@Override
	public CustomBrokerResponse get(Long id) {

		logger.info("Inside CustomBrokerServiceImpl get() starts ");
		Session session = null;
		CustomBrokerResponse customBrokerResponseObj = new CustomBrokerResponse();

		try {
			session = sessionFactory.openSession();
			CustomBroker customBroker = customBrokerDao.findById(id, session);

			if (customBroker != null) {
				customBrokerResponseObj.setCustomBrokerId(customBroker
						.getCustomBrokerId());
				customBrokerResponseObj.setCustomBrokerName(customBroker
						.getCustomBrokerName());
				customBrokerResponseObj.setTypeId(customBroker.getType()
						.getTypeId());

				List<CustomBrokerType> customBrokerTypeList = customBroker
						.getCustomerBrokerTypes();
				if (customBrokerTypeList != null
						&& !customBrokerTypeList.isEmpty()) {

					List<CustomBrokerTypeModel> customBrokerTypes = new ArrayList<CustomBrokerTypeModel>();
					for (CustomBrokerType customBrokerType : customBrokerTypeList) {
						CustomBrokerTypeModel customBrokerTypeModel = new CustomBrokerTypeModel();
						BeanUtils.copyProperties(customBrokerType,
								customBrokerTypeModel);
						customBrokerTypeModel.setOperationId(customBrokerType
								.getOperation().getTypeId());
						customBrokerTypeModel.setStatusId(customBrokerType
								.getStatus().getId());
						customBrokerTypeModel.setTimeZoneId(customBrokerType
								.getTimeZone().getTypeId());
						customBrokerTypeModel.setTypeId(customBrokerType
								.getType().getTypeId());
						customBrokerTypes.add(customBrokerTypeModel);

					}

					customBrokerResponseObj
							.setCustomBrokerTypes(customBrokerTypes);
				}

				List<Status> statusList = statusService.getAll();
				customBrokerResponseObj.setStatusList(statusList);

				List<TypeResponse> operationList = typeService.getAll(15l);
				customBrokerResponseObj.setOperationList(operationList);

				List<TypeResponse> timeZoneList = typeService.getAll(16l);
				customBrokerResponseObj.setTimeZoneList(timeZoneList);

				List<TypeResponse> typeList = typeService.getAll(14l);
				customBrokerResponseObj.setTypeList(typeList);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside CustomBrokerServiceImpl get() ends ");
		return customBrokerResponseObj;
	}

	 
	@Override
	public CustomBrokerResponse getOpenAdd() {

		logger.info("Inside CustomBrokerServiceImpl getOpenAdd() starts ");
		CustomBrokerResponse customBrokerResponse = new CustomBrokerResponse();
		Session session = sessionFactory.openSession();
	
		try {
			List<Status> statusList = AllList.getStatusList(session);
			if(statusList != null && !statusList.isEmpty())
				customBrokerResponse.setStatusList(statusList);

			List<TypeResponse> operationList = AllList.getTypeResponse(session,15l);
			if(operationList != null && !operationList.isEmpty())
				customBrokerResponse.setOperationList(operationList);

			List<TypeResponse> zoneList = AllList.getTypeResponse(session,16l);
			if(zoneList != null && !zoneList.isEmpty())
				customBrokerResponse.setTimeZoneList(zoneList);
			
			List<TypeResponse> typeList = AllList.getTypeResponse(session,14l);
			if(typeList != null && !typeList.isEmpty())
				customBrokerResponse.setTypeList(typeList);

			logger.info("Inside CustomBrokerServiceImpl getOpenAdd() ends ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return customBrokerResponse;
	}

/*private List<Status> getStatusList(Session session){
	Query queryStatus = session
			.createQuery("select id,status from Status");
	List<Object> statusListObj = queryStatus.list();
	List<Status> statusList = new ArrayList<Status>();
	Iterator<Object> it = statusListObj.iterator();
	 
	while(it.hasNext())
	{
		Object o[] = (Object[])it.next();
		 Status status = new Status();
		 status.setId(Long.parseLong(String.valueOf(o[0])));
		 status.setStatus(String.valueOf(o[1]));
		 statusList.add(status);
	}
	return statusList;
}*/
	/*private List<TypeResponse> getTypeResponse(Session session ,Long val){
		
		logger.info("Inside CustomBrokerServiceImpl getTypeResponse() starts ");
		Query queryOperation = session
				.createQuery("select typeId,typeName from Type where value = "+val);
		List<Object> operationListObj = queryOperation.list();
		List<TypeResponse> operationList = new ArrayList<TypeResponse>();
		Iterator<Object> operationIt = operationListObj.iterator();
		
		while(operationIt.hasNext())
		{
			Object o[] = (Object[])operationIt.next();
			TypeResponse type = new TypeResponse();
			type.setTypeId(Long.parseLong(String.valueOf(o[0])));
			type.setTypeName(String.valueOf(o[1]));
			operationList.add(type);
		}
		logger.info("Inside CustomBrokerServiceImpl getTypeResponse() ends ");
		return operationList;
		
	}*/
	@Override
	public List<CustomBrokerResponse> getCustomBrokerByCustomBrokerName(
			String customBrokerName) {

		logger.info("Inside CustomBrokerServiceImpl getCustomBrokerByCustomBrokerName() starts ");
		Session session = null;
		List<CustomBrokerResponse> customBrokerResponseList = new ArrayList<CustomBrokerResponse>();
		List<CustomBroker> customBrokerList = null;

		try {
			session = sessionFactory.openSession();
			if (customBrokerName != null && customBrokerName.length() > 0) {
				customBrokerList = customBrokerDao.findAll(customBrokerName,
						session);
			}
			if (customBrokerList != null && !customBrokerList.isEmpty()) {
				for (CustomBroker customBroker : customBrokerList) {
					CustomBrokerResponse customBrokerResponseObj = new CustomBrokerResponse();
					customBrokerResponseObj.setCustomBrokerId(customBroker
							.getCustomBrokerId());
					customBrokerResponseObj.setCustomBrokerName(customBroker
							.getCustomBrokerName());
					customBrokerResponseList.add(customBrokerResponseObj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside CustomBrokerServiceImpl getCustomBrokerByCustomBrokerName() ends ");
		return customBrokerResponseList;
	}

}
