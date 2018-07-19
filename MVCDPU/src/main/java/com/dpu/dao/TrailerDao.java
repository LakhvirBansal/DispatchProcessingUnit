package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Trailer;


public interface TrailerDao extends GenericDao<Trailer> {

	List<Trailer> findAll(Session session);

	Trailer findById(Long trailerId, Session session);

	void save(Trailer trailer, Session session);

	void update(Trailer trailer, Session session);

	void delete(Trailer trailer, Session session);

	
}
