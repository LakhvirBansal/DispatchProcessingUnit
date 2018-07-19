package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Category;
import com.dpu.entity.Type;

public interface CategoryDao extends GenericDao<Category> {

	List<Category> findAll(Session session);

	Category findById(Long id, Session session);

	List<Category> getCategoryByCategoryName(Session session,
			String categoryName);

	List<Category> getCategoriesBasedOnType(String unitTypeName, Session session);

	List<Category> getCategoriesBasedOnType(Type unitType, Session session);

}
