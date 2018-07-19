/**
 * 
 */
package com.dpu.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.StatusDao;
import com.dpu.entity.Status;

@Repository
public class StatusDaoImpl extends GenericDaoImpl<Status> implements
		StatusDao {

	@Override
	public Status getByName(String name, Session session) {
		Query query = session.createQuery("from Status where status =:name ");
		query.setParameter("name", name);
		return (Status) query.uniqueResult();
	}

}
