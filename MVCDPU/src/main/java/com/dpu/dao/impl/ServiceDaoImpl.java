package com.dpu.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dpu.dao.ServiceDao;
import com.dpu.entity.Service;

@Repository
public class ServiceDaoImpl extends GenericDaoImpl<Service> implements ServiceDao {
	@Autowired
	SessionFactory sessionFactory;
	
	Logger logger = Logger.getLogger(ServiceDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getServiceData() {
		
		logger.info("ServiceDaoImpl getServiceData() starts ");
		Session session = null;
		List<Object[]> returnList = null;
		
		try {
			session = sessionFactory.openSession();
			Query query = session.createSQLQuery(" select service_id, service_name from service ");
			returnList = query.list();

		} catch (Exception e) {
			logger.info("Exception in ServiceDaoImpl getServiceData() :"+e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		logger.info("ServiceDaoImpl getServiceData() ends ");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Service> findAll(Session session) {

		logger.info("ServiceDaoImpl findAll() starts ");
		
		StringBuilder sb = new StringBuilder("select s from Service s join fetch s.status join fetch s.associationWith join fetch s.textField ");
		Query query = session.createQuery(sb.toString());
		
		logger.info("ServiceDaoImpl findAll() ends ");
		return query.list();
	}

	@Override
	public Service findById(Long id, Session session) {
		
		logger.info("ServiceDaoImpl findById() starts, serviceId :"+id);
		
		StringBuilder sb = new StringBuilder("select s from Service s join fetch s.status join fetch s.associationWith join fetch s.textField where s.serviceId =:serviceId");
		Query query = session.createQuery(sb.toString());
		query.setParameter("serviceId", id);
		
		logger.info("ServiceDaoImpl findById() ends, serviceId :"+id);
		return (Service) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Service> getServiceByServiceName(Session session, String serviceName) {
		
		logger.info("ServiceDaoImpl getServiceByServiceName() starts, serviceName :"+serviceName);
		
		StringBuilder sb = new StringBuilder("select s from Service s join fetch s.status join fetch s.associationWith join fetch s.textField where s.serviceName like :serviceName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("serviceName", "%"+serviceName+"%");
		
		logger.info("ServiceDaoImpl getServiceByServiceName() ends, serviceName :"+serviceName);
		return query.list();
	}

}
