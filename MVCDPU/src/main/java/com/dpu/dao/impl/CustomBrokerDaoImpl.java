package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dpu.dao.CustomBrokerDao;
import com.dpu.entity.CustomBroker;

/**
 * @author anuj
 *
 */

@Repository
@Transactional
public class CustomBrokerDaoImpl extends GenericDaoImpl<CustomBroker> implements CustomBrokerDao{
	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomBroker> findAll(String customBrokerName, Session session) {
		
		StringBuilder queryAppender = new StringBuilder("select c from CustomBroker c "); //join fetch c.status
		if(customBrokerName != null) {
			queryAppender.append(" where c.customBrokerName like '%" + customBrokerName + "%'");
		}
		List<CustomBroker> customBrokerList = session.createQuery(queryAppender.toString()).list();
		return customBrokerList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CustomBroker findById(Long customBrokerId,Session session) {
		
		StringBuilder queryAppender = new StringBuilder("select c from CustomBroker c  where c.customBrokerId =:customBrokerId");//join fetch c.status
		Query query = session.createQuery(queryAppender.toString());
		query.setParameter("customBrokerId", customBrokerId);
		List<CustomBroker> customBrokerList = query.list();
		if(customBrokerList != null && customBrokerList.size() > 0) {
			return customBrokerList.get(0);
		}
		return null;
	}
/*
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomBroker> getCustomBrokerByCustomBrokerName(Session session, String customBrokerName) {
		StringBuilder sb = new StringBuilder("select s from CustomBroker c join fetch c.status where c.customBrokerName like :customBrokerName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("customBrokerName", "%"+customBrokerName+"%");
		return query.list();
	}*/

}
