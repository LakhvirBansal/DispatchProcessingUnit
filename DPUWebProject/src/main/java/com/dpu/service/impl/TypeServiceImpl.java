package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.TypeDao;
import com.dpu.entity.Type;
import com.dpu.model.TypeResponse;
import com.dpu.service.TypeService;

@Component
public class TypeServiceImpl implements TypeService {
	
	Logger logger = Logger.getLogger(TypeServiceImpl.class);

	@Autowired
	TypeDao typeDao;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<TypeResponse> getAll(Long typeValue) {
		List<TypeResponse> response = new ArrayList<TypeResponse>();
		Criterion eqcriterion = Restrictions.eq("value", typeValue);
		List<Type> types = typeDao.find(eqcriterion);
		
		if(types != null  && types.size() > 0) {
			for(Type type : types) {
				TypeResponse typeResponse = new TypeResponse();
				typeResponse.setTypeId(type.getTypeId());
				typeResponse.setTypeName(type.getTypeName());
				typeResponse.setTypeValue(type.getValue());
				response.add(typeResponse);
			}
		}
		return response;
	}

	@Override
	public Type get(Long typeId) {
		return typeDao.findById(typeId);
	}

	@Override
	public Type getByName(Long typeValue, String name) {

		Session session = null;
		Type type = null;
		try {
			session = sessionFactory.openSession();
			List<Type> getByNames = typeDao.getByNames(typeValue, name, session);

			if (getByNames != null && !getByNames.isEmpty()) {
				type = getByNames.get(0);
			}
		} catch (Exception e) {

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return type;
	}
}
