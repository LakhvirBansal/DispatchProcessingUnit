
package com.dpu.dao;

import org.hibernate.Session;

import com.dpu.entity.Status;


public interface StatusDao extends GenericDao<Status>{

	Status getByName(String name, Session session);

}
