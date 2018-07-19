package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dpu.dao.ShipperDao;
import com.dpu.entity.Shipper;
@Repository
public class ShipperDaoImpl extends GenericDaoImpl<Shipper> implements ShipperDao {

	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Shipper> findByLoactionName(String locationName, Session session) {
		StringBuilder sb = new StringBuilder( " select t from Shipper t where t.locationName =:locationname ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("locationname", locationName);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Shipper> findAll(Session session) {
		StringBuilder sb = new StringBuilder( " select s from Shipper s left join fetch s.status left join fetch s.country left join fetch s.state ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public Shipper findById(Long id, Session session) {
		StringBuilder sb = new StringBuilder( " select s from Shipper s left join fetch s.status left join fetch s.country left join fetch s.state where s.shipperId =:shipperId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("shipperId", id);
		return (Shipper) query.uniqueResult();
	}

	@Override
	public void save(Shipper shipper, Session session) {
		session.save(shipper);
	}

	@Override
	public void update(Shipper shipper, Session session) {
		session.update(shipper);
	}

}
