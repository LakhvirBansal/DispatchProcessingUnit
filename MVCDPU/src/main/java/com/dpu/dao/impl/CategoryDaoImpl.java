package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CategoryDao;
import com.dpu.entity.Category;
import com.dpu.entity.Type;

@Repository
public class CategoryDaoImpl extends GenericDaoImpl<Category> implements CategoryDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findAll(Session session) {

		StringBuilder sb = new StringBuilder(
				"select c from Category c join fetch c.status join fetch c.highLight join fetch c.type ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public Category findById(Long id, Session session) {
		StringBuilder sb = new StringBuilder(
				"select c from Category c join fetch c.status join fetch c.highLight join fetch c.type where c.categoryId =:categoryId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("categoryId", id);
		return (Category) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoryByCategoryName(Session session,
			String categoryName) {
		StringBuilder sb = new StringBuilder(
				"select c from Category c join fetch c.status join fetch c.highLight join fetch c.type where c.name like :categoryName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("categoryName", "%" + categoryName + "%");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoriesBasedOnType(String unitTypeName, Session session) {
		StringBuilder sb = new StringBuilder(
				"select c from Category c join fetch c.status join fetch c.highLight join fetch c.type where c.type.typeName =:unitTypeName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("unitTypeName", unitTypeName);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoriesBasedOnType(Type unitType, Session session) {
		StringBuilder sb = new StringBuilder(
				"select c from Category c join fetch c.status join fetch c.highLight join fetch c.type where c.type =:unitType ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("unitType", unitType);
		return query.list();
	}

}
