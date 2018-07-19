package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Shipper;

public interface ShipperDao extends GenericDao<Shipper> {

	List<Shipper> findAll(Session session);

	Shipper findById(Long id, Session session);

	List<Shipper> findByLoactionName(String locationName, Session session);

	void save(Shipper shipper, Session session);

	void update(Shipper shipper, Session session);

}
