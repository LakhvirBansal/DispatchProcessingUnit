package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Type;

public interface TypeDao extends GenericDao<Type>{

	List<Type> getByNames(Long typeValue, String name, Session session);
}