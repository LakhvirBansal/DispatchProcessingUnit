package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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

}
