package com.dpu.dao.impl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.TypeDao;
import com.dpu.entity.Type;

@Repository
public class TypeDaoImpl extends GenericDaoImpl<Type> implements TypeDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Type> getByNames(Long typeValue, String name, Session session) {
		Query query = session.createQuery("from Type where value =:typeValue and typeName =:name ");
		query.setParameter("typeValue", typeValue);
		query.setParameter("name", name);
		return query.list();
	}

}
