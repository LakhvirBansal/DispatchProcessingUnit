package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.common.CommonProperties;
import com.dpu.dao.ServiceDao;
import com.dpu.entity.Service;
import com.dpu.entity.Status;
import com.dpu.entity.Type;
import com.dpu.model.DPUService;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TypeResponse;
import com.dpu.service.ServiceService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;

@Component
public class ServiceServiceImpl implements ServiceService {
	@Autowired
	ServiceDao serviceDao;

	@Autowired
	StatusService statusService;

	@Autowired
	TypeService typeService;

	@Autowired
	SessionFactory sessionFactory;

	Logger logger = Logger.getLogger(ServiceServiceImpl.class);

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
		//failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object add(DPUService dpuService) {
		
		logger.info("ServiceServiceImpl add() starts ");
		Service service = null;
		try {
			service = setServiceValues(dpuService);
			serviceDao.save(service);

		} catch (Exception e) {
			logger.error("Exception inside ServiceServiceImpl add() :"+ e.getMessage());
			return createFailedObject(CommonProperties.service_unable_to_add_message,Long.parseLong(CommonProperties.service_unable_to_add_code));
		}

		logger.info("ServiceServiceImpl add() ends ");
		return createSuccessObject(CommonProperties.service_added_message,Long.parseLong(CommonProperties.service_added_code));
	}


	private Service setServiceValues(DPUService dpuService) {
		Service service = new Service();
		service.setServiceName(dpuService.getServiceName());
		Status status = statusService.get(dpuService.getStatusId());
		Type textField = typeService.get(dpuService.getTextFieldId());
		service.setTextField(textField);
		Type associateWith = typeService.get(dpuService.getAssociationWithId());
		service.setAssociationWith(associateWith);
		service.setStatus(status);
		return service;
	}

	@Override
	public Object update(Long id, DPUService dpuService) {
		
		logger.info("ServiceServiceImpl update() starts, serviceId :"+id);
		Service service = null;
		
		try {
			service = serviceDao.findById(id);
			service.setServiceName(dpuService.getServiceName());
			Status status = statusService.get(dpuService.getStatusId());
			Type textField = typeService.get(dpuService.getTextFieldId());
			service.setTextField(textField);
			Type associateWith = typeService.get(dpuService.getAssociationWithId());
			service.setAssociationWith(associateWith);
			service.setStatus(status);
			serviceDao.update(service);

		} catch (Exception e) {
			logger.error("Exception inside ServiceServiceImpl update() :"+ e.getMessage());
			return createFailedObject(CommonProperties.service_unable_to_update_message,Long.parseLong(CommonProperties.service_unable_to_update_code));

		}
		
		logger.info("ServiceServiceImpl update() ends, serviceId :"+id);
		return createSuccessObject(CommonProperties.service_updated_message,Long.parseLong(CommonProperties.service_unable_to_update_code));
	}

	@Override
	public Object delete(Long id) {
		
		logger.info("ServiceServiceImpl delete() starts, serviceId :"+id);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			Service service = (Service) session.get(Service.class, id);
			
			if(service != null){
				tx = session.beginTransaction();
				session.delete(service);
				tx.commit();
			} else{
				return createFailedObject(CommonProperties.service_unable_to_delete_message,0l);
			}
			

		} catch (Exception e) {
			logger.error("Exception inside ServiceServiceImpl delete() :"+ e.getMessage());
			if(tx != null){
				tx.rollback();
			}
			if(e instanceof ConstraintViolationException){
				return createFailedObject(CommonProperties.service_dependent_message,0l);
			}
			return createFailedObject(CommonProperties.service_unable_to_delete_message,Long.parseLong(CommonProperties.service_unable_to_delete_code));
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		logger.info("ServiceServiceImpl delete() ends, serviceId :"+id);
		return createSuccessObject(CommonProperties.service_deleted_message,Long.parseLong(CommonProperties.service_deleted_code));

	}

	@Override
	public List<DPUService> getAll() {

		logger.info("ServiceServiceImpl getAll() starts ");
		Session session = null;
		List<DPUService> servicesList = new ArrayList<DPUService>();

		try {

			session = sessionFactory.openSession();
			List<Service> serviceList = serviceDao.findAll(session);

			if (serviceList != null && !serviceList.isEmpty()) {
				for (Service service : serviceList) {
					DPUService serviceObj = new DPUService();
					serviceObj.setAssociationWith(service.getAssociationWith().getTypeName());
					serviceObj.setServiceName(service.getServiceName());
					serviceObj.setServiceId(service.getServiceId());
					serviceObj.setStatus(service.getStatus().getStatus());
					serviceObj.setTextField(service.getTextField().getTypeName());
					servicesList.add(serviceObj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("ServiceServiceImpl getAll() ends ");
		return servicesList;
	}

	@Override
	public DPUService get(Long id) {

		logger.info("ServiceServiceImpl get() starts, serviceId :"+id);
		Session session = null;
		DPUService dpuService = new DPUService();

		try {

			session = sessionFactory.openSession();
			Service service = serviceDao.findById(id, session);

			if (service != null) {
				dpuService.setServiceId(service.getServiceId());
				dpuService.setTextFieldId(service.getTextField().getTypeId());
				dpuService.setStatusId(service.getStatus().getId());
				dpuService.setAssociationWithId(service.getAssociationWith().getTypeId());
				dpuService.setServiceName(service.getServiceName());
				
				List<Status> statusList = statusService.getAll();
				dpuService.setStatusList(statusList);

				List<TypeResponse> textFieldList = typeService.getAll(2l);
				dpuService.setTextFieldList(textFieldList);

				List<TypeResponse> associatedWithList = typeService.getAll(3l);
				dpuService.setAssociatedWithList(associatedWithList);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("ServiceServiceImpl get() ends, serviceId :"+id);
		return dpuService;
	}

	@Override
	public DPUService getOpenAdd() {

		logger.info("ServiceServiceImpl getOpenAdd() starts");
		DPUService service = new DPUService();

		List<Status> statusList = statusService.getAll();
		service.setStatusList(statusList);

		List<TypeResponse> textFieldList = typeService.getAll(2l);
		service.setTextFieldList(textFieldList);

		List<TypeResponse> associatedWithList = typeService.getAll(3l);
		service.setAssociatedWithList(associatedWithList);

		logger.info("ServiceServiceImpl getOpenAdd() ends");
		return service;
	}

	@Override
	public List<DPUService> getServiceByServiceName(String serviceName) {

		logger.info("ServiceServiceImpl getServiceByServiceName() starts, serviceName :"+serviceName);
		Session session = null;
		List<DPUService> servicesList = new ArrayList<DPUService>();

		try {
			session = sessionFactory.openSession();
			List<Service> serviceList = serviceDao.getServiceByServiceName(session, serviceName);
			
			if (serviceList != null && !serviceList.isEmpty()) {
				for (Service service : serviceList) {
					DPUService serviceObj = new DPUService();
					serviceObj.setAssociationWith(service.getAssociationWith().getTypeName());
					serviceObj.setServiceName(service.getServiceName());
					serviceObj.setServiceId(service.getServiceId());
					serviceObj.setStatus(service.getStatus().getStatus());
					serviceObj.setTextField(service.getTextField().getTypeName());
					servicesList.add(serviceObj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("ServiceServiceImpl getServiceByServiceName() starts, serviceName :"+serviceName);
		return servicesList;
	}

	@Override
	public List<DPUService> getServiceData() {

		logger.info("ServiceServiceImpl getServiceData() starts");
		List<Object[]> serviceData = serviceDao.getServiceData();
		List<DPUService> returnServ = new ArrayList<DPUService>();

		if (serviceData != null && !serviceData.isEmpty()) {
			for (Object[] row : serviceData) {
				DPUService res = new DPUService();
				res.setServiceId(Long.valueOf(String.valueOf(row[0])));
				res.setServiceName(String.valueOf(row[1]));
				returnServ.add(res);
			}
		}

		logger.info("ServiceServiceImpl getServiceData() ends");
		return returnServ;
	}
}
