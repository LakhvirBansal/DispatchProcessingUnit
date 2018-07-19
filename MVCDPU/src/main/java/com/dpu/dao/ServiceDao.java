
package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Service;

public interface ServiceDao extends GenericDao<Service>{
	List<Object[]> getServiceData();

	List<Service> findAll(Session session);

	Service findById(Long id, Session session);

	List<Service> getServiceByServiceName(Session session, String serviceName);
}
