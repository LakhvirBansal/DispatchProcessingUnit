package com.dpu.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dpu.dao.DivisionDao;
import com.dpu.entity.Division;
import com.dpu.entity.Status;
import com.dpu.model.DivisionReq;
import com.dpu.service.StatusService;

@Repository
public class DivisionDaoImpl extends GenericDaoImpl<Division> implements DivisionDao {

	@Autowired
	StatusService statusService;

	@Override
	public Division add(Session session, DivisionReq divisionReq) {
		logger.info("DivisionDaoImpl: add(): STARTS");
		Division division = null;
		try {

			division = setDivisionValues(divisionReq);
			Status status = (Status) session.get(Status.class,divisionReq.getStatusId());
			division.setStatus(status);

			Long divisionId = (Long) session.save(division);
			division.setDivisionId(divisionId);
		} catch (Exception e) {
			logger.fatal("DivisionDaoImpl: add(): Exception: " + e.getMessage());
		}

		logger.info("DivisionDaoImpl: add(): ENDS");

		return division;

	}

	private Division setDivisionValues(DivisionReq divisionReq) {

		logger.info("DivisionDaoImpl: setDivisionValues(): STARTS");

		Division division = new Division();
		division.setDivisionCode(divisionReq.getDivisionCode());
		division.setDivisionName(divisionReq.getDivisionName());
		division.setFedral(divisionReq.getFedral());
		division.setProvincial(divisionReq.getProvincial());
		division.setSCAC(divisionReq.getScac());
		division.setCarrierCode(divisionReq.getCarrierCode());
		division.setContractPrefix(divisionReq.getContractPrefix());
		division.setInvoicePrefix(divisionReq.getInvoicePrefix());
		// Status status = statusService.get(divisionReq.getStatusId());
		// division.setStatus(status);
		division.setCreatedBy("jagvir");
		division.setCreatedOn(new Date());
		division.setModifiedBy("jagvir");
		division.setModifiedOn(new Date());

		logger.info("DivisionDaoImpl: setDivisionValues(): ENDS");

		return division;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Division> findDivisionsByName(String divisionName, Session session) {
		Query query = session.createQuery("from Division where divisionCode =:divisionName ");
		query.setParameter("divisionName", divisionName);
		return query.list();
	}

}
