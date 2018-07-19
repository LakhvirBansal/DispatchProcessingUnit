package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.common.CommonProperties;
import com.dpu.dao.DivisionDao;
import com.dpu.entity.Division;
import com.dpu.entity.Status;
import com.dpu.model.DivisionReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.DivisionService;
import com.dpu.service.StatusService;

/**
 * @author jagvir
 *
 */
@Component
public class DivisionServiceImpl implements DivisionService {

	Logger logger = Logger.getLogger(DivisionServiceImpl.class);

	@Autowired
	DivisionDao divisionDao;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	StatusService statusService;

	private Object createSuccessObject(String msg, long code) {
		Success success = new Success();
		success.setCode(code);
		success.setMessage(msg);
		success.setResultList(getAll(""));
		return success;
	}

	private Object createFailedObject(String msg, long code) {
		Failed failed = new Failed();
		failed.setCode(code);
		failed.setMessage(msg);
		//failed.setResultList(getAll(""));
		return failed;
	}

	public Object createAlreadyExistObject(String msg, long code) {
		Failed failed = new Failed();
		failed.setCode(code);
		failed.setMessage(msg);
		//failed.setResultList(getAll(""));
		return failed;
	}

	@Override
	public Object update(Long id, DivisionReq divisionReq) {
		
		logger.info("DivisionServiceImpl update() starts, divisionId :"+id);
		
		try {
			Division division = divisionDao.findById(id);
			if (division != null) {
				division.setDivisionCode(divisionReq.getDivisionCode());
				division.setDivisionName(divisionReq.getDivisionName());
				division.setFedral(divisionReq.getFedral());
				division.setProvincial(divisionReq.getProvincial());
				division.setSCAC(divisionReq.getScac());
				division.setCarrierCode(divisionReq.getCarrierCode());
				division.setContractPrefix(divisionReq.getContractPrefix());
				division.setInvoicePrefix(divisionReq.getInvoicePrefix());
				Status status = statusService.get(divisionReq.getStatusId());
				division.setStatus(status);
				division.setModifiedBy("jagvir");
				division.setModifiedOn(new Date());
				divisionDao.update(division);
			 } else{
				 return createFailedObject(CommonProperties.Division_unable_to_update_message, Long.parseLong(CommonProperties.Division_unable_to_update_code)); 
			 }
		} catch (Exception e) {
			logger.error("Exception inside DivisionServiceImpl update() :"+ e.getMessage());
			return createFailedObject(CommonProperties.Division_unable_to_update_message, Long.parseLong(CommonProperties.Division_unable_to_update_code));
		}

		logger.info("DivisionServiceImpl update() ends, divisionId :"+id);
		return createSuccessObject(CommonProperties.Division_updated_message, Long.parseLong(CommonProperties.Division_updated_code));
	}

	@Override
	public Object delete(Long id) {

		logger.info("DivisionServiceImpl delete() starts, divisionId :"+id);
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			Division division = (Division) session.get(Division.class, id);
			
			if (division != null) {
				tx = session.beginTransaction();
				session.delete(division);
				tx.commit();
			} else {
				return createFailedObject(CommonProperties.Division_unable_to_delete_message,Long.parseLong(CommonProperties.Division_unable_to_delete_code));
			}
		} catch (Exception e) {
			logger.info("Exception inside DivisionServiceImpl delete() : "+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createAlreadyExistObject(CommonProperties.Division_already_used_message,Long.parseLong(CommonProperties.Division_already_used_code));
			}
			return createFailedObject(CommonProperties.Division_unable_to_delete_message,Long.parseLong(CommonProperties.Division_unable_to_delete_code));
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("DivisionServiceImpl delete() ends, divisionId :"+id);
		return createSuccessObject(CommonProperties.Division_deleted_message,Long.parseLong(CommonProperties.Division_deleted_code));
	}

	@Override
	public DivisionReq get(Long id) {
		
		logger.info("DivisionServiceImpl get() starts, divisionId :"+id);
		Division division = divisionDao.findById(id);
		DivisionReq response = null;
		
		if (division != null) {
			response = new DivisionReq();
			response.setDivisionId(division.getDivisionId());
			response.setDivisionCode(division.getDivisionCode());
			response.setDivisionName(division.getDivisionName());
			response.setFedral(division.getFedral());
			response.setProvincial(division.getProvincial());
			response.setScac(division.getSCAC());
			response.setCarrierCode(division.getCarrierCode());
			response.setContractPrefix(division.getContractPrefix());
			response.setInvoicePrefix(division.getInvoicePrefix());
			response.setStatusId(division.getStatus().getId());

			List<Status> statusList = statusService.getAll();

			if (statusList != null && !statusList.isEmpty()) {
				response.setStatusList(statusList);
			}

		}

		logger.info("DivisionServiceImpl get() ends, divisionId :"+id);
		return response;

	}

	@Override
	public List<DivisionReq> getAll(String divisionName) {
		
		logger.info("DivisionServiceImpl getAll() starts, divisionName :"+divisionName);
		List<Division> lstDivision = null;
		List<DivisionReq> divisionResponse = new ArrayList<DivisionReq>();
		
		if (divisionName != null && divisionName.length() > 0) {
			Criterion criterion = Restrictions.like("divisionName",divisionName, MatchMode.ANYWHERE);
			lstDivision = divisionDao.find(criterion);
		} else {
			lstDivision = divisionDao.findAll();
		}
		if (lstDivision != null && lstDivision.size() > 0) {
			for (Division division : lstDivision) {
				DivisionReq divisionReq = new DivisionReq();
				divisionReq.setDivisionCode(division.getDivisionCode());
				divisionReq.setProvincial(division.getProvincial());
				divisionReq.setFedral(division.getFedral());
				divisionReq.setDivisionName(division.getDivisionName());
				divisionReq.setStatus(division.getStatus().getStatus());
				divisionReq.setDivisionId(division.getDivisionId());
				divisionResponse.add(divisionReq);
			}
		}
		
		logger.info("DivisionServiceImpl getAll() ends, divisionName :"+divisionName);
		return divisionResponse;
	}

	@Override
	public Division getDivisionByName(String divisionName) {
		
		Session session = null;
		Division division = null;
		try {
			session = sessionFactory.openSession();
			List<Division> divisions = divisionDao.findDivisionsByName(divisionName, session);

			if (divisions != null && !divisions.isEmpty()) {
				division = divisions.get(0);
			}
		} catch (Exception e) {

		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return division;
	}

	
	@Override
	public Object add(DivisionReq divisionReq) {
		logger.info("DivisionServiceImpl: add():  STARTS");
		Session session = null;
		Transaction tx = null;
		try {

			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			divisionDao.add(session, divisionReq);
			if (tx != null) {
				tx.commit();
			}

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.error("Exception inside DivisionServiceImpl add() :"+ e.getMessage());
			return createFailedObject(CommonProperties.Division_unable_to_add_message,Long.parseLong(CommonProperties.Division_unable_to_add_code));

		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("DivisionServiceImpl: add():  ENDS");
		return createSuccessObject(CommonProperties.Division_added_message,Long.parseLong(CommonProperties.Division_added_code));
	}

}
