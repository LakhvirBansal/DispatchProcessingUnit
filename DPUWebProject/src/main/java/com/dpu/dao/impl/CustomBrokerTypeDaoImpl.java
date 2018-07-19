package com.dpu.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CustomBrokerTypeDao;
import com.dpu.entity.CustomBrokerType;

@Repository
public class CustomBrokerTypeDaoImpl extends GenericDaoImpl<CustomBrokerType>
		implements CustomBrokerTypeDao {

	@Override
	public List<CustomBrokerType> getAll(Session session, Long customerBrokerId) {
		
		List<CustomBrokerType> customBrokerTypeList = new ArrayList<CustomBrokerType>();
		if(customerBrokerId != null){
			StringBuilder queryAppender = new StringBuilder("select cbt from CustomBrokerType cbt ");
			queryAppender.append("where customBroker = " + customerBrokerId );
			customBrokerTypeList = session.createQuery(queryAppender.toString()).list();
		}
		
		return customBrokerTypeList;
	}

}
