package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dpu.dao.ServiceDao;
import com.dpu.dao.ShipperDao;
import com.dpu.dao.TerminalDao;
import com.dpu.entity.Service;
import com.dpu.entity.Status;
import com.dpu.entity.Terminal;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TerminalResponse;
import com.dpu.service.ServiceService;
import com.dpu.service.ShipperService;
import com.dpu.service.StatusService;
import com.dpu.service.TerminalService;

@Component
public class TerminalServiceImpl implements TerminalService {

	Logger logger = Logger.getLogger(TerminalServiceImpl.class);
	
	@Autowired
	TerminalDao terminalDao;

	@Autowired
	StatusService statusService;
	
	@Autowired
	ServiceService serviceService;
	
	@Autowired
	ShipperService shipperService;

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	ShipperDao shipperDao;
	
	@Autowired
	ServiceDao serviceDao;
	
	@Value("${terminal_added_code}")
	public String terminalAddedCode;

	@Value("${terminal_added_message}")
	public String terminalAddedMessage;

	@Value("${terminal_unable_to_add_code}")
	public String terminalUnableToAddCode;

	@Value("${terminal_unable_to_add_message}")
	public String terminalUnableToAddMessage;

	@Value("${terminal_deleted_code}")
	public String terminalDeletedCode;

	@Value("${terminal_deleted_message}")
	public String terminalDeletedMessage;

	@Value("${terminal_unable_to_delete_code}")
	public String terminalUnableToDeleteCode;

	@Value("${terminal_unable_to_delete_message}")
	public String terminalUnableToDeleteMessage;

	@Value("${terminal_updated_code}")
	public String terminalUpdateCode;

	@Value("${terminal_updated_message}")
	public String terminalUpdateMessage;

	@Value("${terminal_unable_to_update_code}")
	public String terminalUnableToUpdateCode;

	@Value("${terminal_unable_to_update_message}")
	public String terminalUnableToUpdateMessage;
	
	@Value("${terminal_already_used_message}")
	private String terminal_already_used_message;
	
	@Override
	public List<TerminalResponse> getAllTerminals() {
		
		logger.info(" TerminalServiceImpl getAllTerminals() starts");
		Session session = null;
		List<TerminalResponse> terminalRespList = new ArrayList<TerminalResponse>();
		
		try{
			session = sessionFactory.openSession();
			List<Terminal> terminalList = terminalDao.findAll(null, session);
			
			if(terminalList != null && !terminalList.isEmpty()){
				for (Terminal terminal : terminalList) {
					TerminalResponse terminalResponseObj = new TerminalResponse();
					terminalResponseObj.setTerminalName(terminal.getTerminalName());
					terminalResponseObj.setTerminalId(terminal.getTerminalId());
					terminalResponseObj.setShipperName(terminal.getShipper().getLocationName());
					terminalRespList.add(terminalResponseObj);
				}
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		logger.info(" TerminalServiceImpl getAllTerminals() ends");
		return terminalRespList;
	}

	@Override
	public Object addTerminal(TerminalResponse terminalResponse) {
		
		logger.info(" TerminalServiceImpl addTerminal() starts");
		Object obj = null;
		
		try {
			Terminal terminal= setTerminalValues(terminalResponse);
			terminalDao.save(terminal);
			obj = createSuccessObject(terminalAddedMessage);
		} catch (Exception e) {
			obj = createFailedObject(terminalUnableToAddMessage);
		}
		
		logger.info(" TerminalServiceImpl addTerminal() starts");
		return obj;
	}

	private Terminal setTerminalValues(TerminalResponse terminalResponse) {
		Terminal terminal  = new Terminal();
		terminal.setTerminalName(terminalResponse.getTerminalName());
		Status status = statusService.get(terminalResponse.getStatusId());
		Set<Service> services = new HashSet<Service>();
		if(terminalResponse.getServiceIds() != null && terminalResponse.getServiceIds().size() > 0) {
			for(int i=0;i<terminalResponse.getServiceIds().size();i++) {
				Long serviceId = terminalResponse.getServiceIds().get(i);
				Service service = serviceDao.findById(serviceId);
				if(service != null) {
					services.add(service);
				}
			}
		}
		terminal.setServices(services);
		terminal.setShipper(shipperDao.findById(terminalResponse.getShipperId()));
		terminal.setModifiedBy("gagan");
		terminal.setModifiedOn(new Date());
		terminal.setStatus(status);
		return terminal;
	}

	@Override
	public Object deleteTerminal(Long id) {
		
		logger.info("TerminalServiceImpl delete() starts.");
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			Terminal terminal = (Terminal) session.get(Terminal.class, id);
			if(terminal != null){
				tx = session.beginTransaction();
				session.delete(terminal);
				tx.commit();
			} else{
				return createFailedObject(terminalUnableToDeleteMessage);
			}
			
		} catch (Exception e) {
			logger.info("Exception inside TerminalServiceImpl delete() : " + e.getMessage());
			if(tx != null){
				tx.rollback();
			}
			if(e instanceof ConstraintViolationException){
				return createFailedObject("Terminal already used");
			}
			return createFailedObject(terminalUnableToDeleteMessage);
		} finally{
			/*if(tx != null){
				tx.commit();
			}*/
			if(session != null){
				session.close();
			}
		}
		
		logger.info("TerminalServiceImpl delete() ends.");
		return createSuccessObject(terminalDeletedMessage);
	}

	@Override
	public TerminalResponse getTerminal(Long id) {
		
		logger.info("TerminalServiceImpl getTerminal() starts, terminalId :"+id);
		Session session = null;
		TerminalResponse terminalResp = new TerminalResponse();
		
		try {
			session = sessionFactory.openSession();
			Terminal terminal = terminalDao.findById(session, id);
			if(terminal != null){
				terminalResp.setTerminalId(terminal.getTerminalId());
				terminalResp.setTerminalName(terminal.getTerminalName());
				terminalResp.setShipperId(terminal.getShipper().getShipperId());
				terminalResp.setShipperList(shipperService.getAll());
				Set<Service> services = terminal.getServices();
				terminalResp.setServiceList(serviceService.getAll());
				List<Long> serviceIds = new ArrayList<Long>();
				for(Service service : services) {
					serviceIds.add(service.getServiceId());
				}
				terminalResp.setServiceIds(serviceIds);
			}		
		}  finally {
			if(session != null) {
				session.close();
			}
		}
		
		logger.info("TerminalServiceImpl getTerminal() ends, terminalId :"+id);
		return terminalResp;
	}

	@Override
	public TerminalResponse getOpenAdd() {
		
		logger.info("TerminalServiceImpl getOpenAdd() starts");
		Session session = sessionFactory.openSession();
		TerminalResponse tresponse = new TerminalResponse();
		
		try{
		tresponse.setServiceList(serviceService.getServiceData());
		tresponse.setShipperList(shipperService.getSpecificData(session));
		}catch(Exception e){
			
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("TerminalServiceImpl getOpenAdd() ends");
		return tresponse;
	}

	@Override
	public Object updateTerminal(Long id, TerminalResponse terminalResponse) {
		
		Object obj = null;
		logger.info("TerminalServiceImpl updateTerminal() starts, terminalId :"+id);
		
		try {
			terminalResponse.setStatusId(1l);
			Terminal terminal= setTerminalValues(terminalResponse);
			terminal.setTerminalId(id);
			terminalDao.update(terminal);
			obj = createSuccessObject(terminalUpdateMessage);
		} catch (Exception e) {
			obj = createFailedObject(terminalUnableToUpdateMessage);
		}
		
		logger.info("TerminalServiceImpl updateTerminal() ends, terminalId :"+id);
		return obj;
	}
	
	private Object createSuccessObject(String message) {
		Success success = new Success();
		success.setMessage(message);
		success.setResultList(getAllTerminals());
		return success;
	}
	
	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		failed.setResultList(getAllTerminals());
		return failed;
	}

	@Override
	public List<TerminalResponse> getTerminalByTerminalName(String terminalName) {
		
		logger.info("TerminalServiceImpl getTerminalByTerminalName() starts, terminalName :"+terminalName);
		Session session = null;
		List<Terminal> terminalList = null;
		List<TerminalResponse> termList = new ArrayList<TerminalResponse>();
		
		try {
			session = sessionFactory.openSession();
			if(terminalName != null && terminalName.length() > 0) {
				terminalList = terminalDao.findAll(terminalName, session);
			}
			
			if(terminalList != null && !terminalList.isEmpty()){
				for (Terminal terminal: terminalList) {
					TerminalResponse terminalObj = new TerminalResponse();
					terminalObj.setTerminalId(terminal.getTerminalId());
					terminalObj.setTerminalName(terminal.getTerminalName());
					terminalObj.setShipperName(terminal.getShipper().getLocationName());
					termList.add(terminalObj);
				}
			}
		} finally {
			if(session != null) {
				session.close();
			}
		}
	
		logger.info("TerminalServiceImpl getTerminalByTerminalName() starts, terminalName :"+terminalName);
		return termList;
	}


}
