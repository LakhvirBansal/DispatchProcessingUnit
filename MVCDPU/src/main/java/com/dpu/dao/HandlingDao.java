/**
 * 
 */
package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Handling;

public interface HandlingDao extends GenericDao<Handling> {

	List<Handling> findAll(Session session);

	Handling findById(Long id, Session session);

	List<Handling> getHandlingByHandlingName(Session session, String handlingName);
}
