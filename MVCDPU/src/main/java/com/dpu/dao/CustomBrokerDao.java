package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.CustomBroker;

public interface CustomBrokerDao extends GenericDao<CustomBroker>{
	List<CustomBroker> findAll(String customBrokerName,Session session);

	CustomBroker findById(Long id, Session session);

	//List<CustomBroker> getCustomBrokerByCustomBrokerName(Session session, String customBrokerName);
}
