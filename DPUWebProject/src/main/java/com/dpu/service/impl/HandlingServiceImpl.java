package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dpu.dao.HandlingDao;
import com.dpu.entity.Handling;
import com.dpu.entity.Status;
import com.dpu.model.Failed;
import com.dpu.model.HandlingModel;
import com.dpu.model.Success;
import com.dpu.service.HandlingService;
import com.dpu.service.StatusService;

@Component
public class HandlingServiceImpl implements HandlingService {

	Logger logger = Logger.getLogger(HandlingServiceImpl.class);

	@Autowired
	HandlingDao handlingDao;

	@Autowired
	StatusService statusService;

	@Autowired
	SessionFactory sessionFactory;

	@Value("${handling_added_message}")
	private String handling_added_message;

	@Value("${handling_unable_to_add_message}")
	private String handling_unable_to_add_message;

	@Value("${handling_deleted_message}")
	private String handling_deleted_message;

	@Value("${handling_unable_to_delete_message}")
	private String handling_unable_to_delete_message;

	@Value("${handling_updated_message}")
	private String handling_updated_message;

	@Value("${handling_unable_to_update_message}")
	private String handling_unable_to_update_message;

	@Value("${handling_already_used_message}")
	private String handling_already_used_message;

	@Override
	public List<HandlingModel> getAll() {

		logger.info("HandlingServiceImpl getAll() starts ");
		Session session = null;
		List<HandlingModel> handlingList = new ArrayList<HandlingModel>();

		try {
			session = sessionFactory.openSession();
			List<Handling> handlings = handlingDao.findAll(session);

			if (handlings != null && !handlings.isEmpty()) {
				for (Handling handling : handlings) {
					HandlingModel handlingModel = new HandlingModel();
					handlingModel.setId(handling.getId());
					handlingModel.setName(handling.getName());
					handlingModel.setStatusName(handling.getStatus()
							.getStatus());
					;
					handlingList.add(handlingModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("HandlingServiceImpl getAll() ends ");
		return handlingList;
	}

	private Object createSuccessObject(String msg) {

		Success success = new Success();
		success.setMessage(msg);
		success.setResultList(getAll());
		return success;
	}

	private Object createFailedObject(String msg) {

		Failed failed = new Failed();
		failed.setMessage(msg);
		// failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object addHandling(HandlingModel handlingModel) {

		logger.info("HandlingServiceImpl addHandling() starts ");
		Handling handling = null;
		try {
			handling = setHandlingValues(handlingModel);
			handlingDao.save(handling);

		} catch (Exception e) {
			logger.info("Exception inside HandlingServiceImpl addHandling() :"
					+ e.getMessage());
			return createFailedObject(handling_unable_to_add_message);

		}

		logger.info("HandlingServiceImpl addHandling() ends ");
		return createSuccessObject(handling_added_message);
	}

	private Handling setHandlingValues(HandlingModel handlingModel) {

		Handling handling = new Handling();
		handling.setName(handlingModel.getName());
		Status status = statusService.get(handlingModel.getStatusId());
		handling.setStatus(status);
		return handling;
	}

	@Override
	public Object update(Long id, HandlingModel handlingModel) {

		logger.info("HandlingServiceImpl update() starts.");
		try {
			Handling handling = handlingDao.findById(id);

			if (handling != null) {
				handling.setName(handlingModel.getName());
				Status status = statusService.get(handlingModel.getStatusId());
				handling.setStatus(status);
				handlingDao.update(handling);
			} else {
				return createFailedObject(handling_unable_to_update_message);
			}

		} catch (Exception e) {
			logger.info("Exception inside HandlingServiceImpl update() :"
					+ e.getMessage());
			return createFailedObject(handling_unable_to_update_message);
		}

		logger.info("HandlingServiceImpl update() ends.");
		return createSuccessObject(handling_updated_message);
	}

	@Override
	public Object delete(Long id) {

		logger.info("HandlingServiceImpl delete() starts.");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Handling handling = (Handling) session.get(Handling.class, id);
			if (handling != null) {
				session.delete(handling);
				tx.commit();
			} else {
				return createFailedObject(handling_unable_to_delete_message);
			}

		} catch (Exception e) {
			logger.info("Exception inside HandlingServiceImpl delete() : "
					+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(handling_already_used_message);
			}
			return createFailedObject(handling_unable_to_delete_message);
		} finally {
			/*
			 * if(tx != null){ tx.commit(); }
			 */
			if (session != null) {
				session.close();
			}
		}

		logger.info("HandlingServiceImpl delete() ends.");
		return createSuccessObject(handling_deleted_message);
	}

	@Override
	public HandlingModel get(Long id) {

		logger.info("HandlingServiceImpl get() starts.");
		Session session = null;
		HandlingModel handlingModel = new HandlingModel();

		try {

			session = sessionFactory.openSession();
			Handling handling = handlingDao.findById(id, session);

			if (handling != null) {

				handlingModel.setId(handling.getId());
				handlingModel.setName(handling.getName());
				handlingModel.setStatusId(handling.getStatus().getId());

				List<Status> statusList = statusService.getAll();
				handlingModel.setStatusList(statusList);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("HandlingServiceImpl get() ends.");
		return handlingModel;
	}

	@Override
	public HandlingModel getOpenAdd() {

		logger.info("HandlingServiceImpl getOpenAdd() starts ");
		HandlingModel handlingModel = new HandlingModel();

		List<Status> statusList = statusService.getAll();
		handlingModel.setStatusList(statusList);

		logger.info("HandlingServiceImpl getOpenAdd() ends ");
		return handlingModel;
	}

	@Override
	public List<HandlingModel> getHandlingByHandlingName(String handlingName) {

		logger.info("HandlingServiceImpl getHandlingByHandlingName() starts ");
		Session session = null;
		List<HandlingModel> handlings = new ArrayList<HandlingModel>();

		try {
			session = sessionFactory.openSession();
			List<Handling> handlingList = handlingDao
					.getHandlingByHandlingName(session, handlingName);
			if (handlingList != null && !handlingList.isEmpty()) {
				for (Handling handling : handlingList) {
					HandlingModel handlingObj = new HandlingModel();
					handlingObj.setId(handling.getId());
					handlingObj.setName(handling.getName());
					handlingObj.setStatusName(handling.getStatus().getStatus());
					handlings.add(handlingObj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("HandlingServiceImpl getHandlingByHandlingName() ends ");
		return handlings;
	}

	@Override
	public List<HandlingModel> getSpecificData() {

		Session session = sessionFactory.openSession();
		List<HandlingModel> handlings = new ArrayList<HandlingModel>();
		
		try {
			List<Object[]> handlingData = handlingDao.getSpecificData(session,
					"Handling", "id", "name");

			
			if (handlingData != null && !handlingData.isEmpty()) {
				for (Object[] row : handlingData) {
					HandlingModel handlingObj = new HandlingModel();
					handlingObj.setId((Long) row[0]);
					handlingObj.setName(String.valueOf(row[1]));
					handlings.add(handlingObj);
				}
			}
		} catch (Exception e) {

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return handlings;
	}

}
