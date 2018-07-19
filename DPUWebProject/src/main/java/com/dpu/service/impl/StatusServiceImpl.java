package com.dpu.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.StatusDao;
import com.dpu.entity.Status;
import com.dpu.service.StatusService;

@Component
public class StatusServiceImpl implements StatusService {
	
	@Autowired
	StatusDao statusDao;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<Status> getAll() {
		return statusDao.findAll();
	}

	@Override
	public Status get(Long statusId) {
		return statusDao.findById(statusId);
	}
	
	@Override
	public Status getByName(String name) {

		Session session = null;
		Status status = null;
		try {
			session = sessionFactory.openSession();
			status = statusDao.getByName(name, session);


		} finally {
			if (session != null) {
				session.close();
			}
		}

		return status;
	}

}
