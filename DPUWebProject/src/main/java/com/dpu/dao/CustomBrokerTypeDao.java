package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.CustomBrokerType;

public interface CustomBrokerTypeDao  extends GenericDao<CustomBrokerType>{

	List<CustomBrokerType> getAll(Session session, Long customerBrokerId);
	 

}
