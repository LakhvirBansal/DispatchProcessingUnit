package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.TrailerDao;
import com.dpu.entity.Trailer;

@Repository
public class TrailerDaoImpl extends GenericDaoImpl<Trailer> implements TrailerDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Trailer> findAll(Session session) {
		StringBuilder sb = new StringBuilder(
				" select t from Trailer t left join fetch t.division left join fetch t.terminal left join fetch t.category left join fetch t.type ")
				.append(" left join fetch t.status ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public Trailer findById(Long trailerId, Session session) {
		StringBuilder sb = new StringBuilder(
				" select t from Trailer t left join fetch t.division left join fetch t.terminal left join fetch t.category left join fetch t.type ")
				.append("left join fetch t.status where t.trailerId =:trailerId");
		Query query = session.createQuery(sb.toString());
		query.setParameter("trailerId", trailerId);
		return (Trailer) query.uniqueResult();
	}

	@Override
	public void save(Trailer trailer, Session session) {
		session.save(trailer);
	}

	@Override
	public void update(Trailer trailer, Session session) {
		session.update(trailer);
		
	}

	@Override
	public void delete(Trailer trailer, Session session) {
		session.delete(trailer);
	}

}
