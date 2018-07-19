package com.dpu.dao;

import org.hibernate.Session;

import com.dpu.entity.Truck;
import com.dpu.model.TruckModel;
public interface TruckDao extends GenericDao<Truck> {

	Truck add(Session session, TruckModel truckResponse);
	Truck findById(Session session,Long id);
	void update(Truck truck, Session session);
	
}
