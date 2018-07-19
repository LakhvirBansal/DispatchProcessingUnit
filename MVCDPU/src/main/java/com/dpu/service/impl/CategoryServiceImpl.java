package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.common.CommonProperties;
import com.dpu.dao.CategoryDao;
import com.dpu.entity.Category;
import com.dpu.entity.Status;
import com.dpu.entity.Type;
import com.dpu.model.CategoryModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TypeResponse;
import com.dpu.service.CategoryService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;

@Component
public class CategoryServiceImpl implements CategoryService {

	Logger logger = Logger.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	StatusService statusService;

	@Autowired
	TypeService typeService;

	@Autowired
	SessionFactory sessionFactory;

	private Object createSuccessObject(String msg, long code) {
		Success success = new Success();
		success.setCode(code);
		success.setMessage(msg);
		success.setResultList(getAll());
		return success;
	}

	private Object createFailedObject(String msg, long code) {
		Failed failed = new Failed();
		failed.setCode(code);
		failed.setMessage(msg);
		//failed.setResultList(getAll());
		return failed;
	}

	public Object createAlreadyExistObject(String msg, long code) {
		Failed failed = new Failed();
		failed.setCode(code);
		failed.setMessage(msg);
	//	failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object addCategory(CategoryModel categoryReq) {

		logger.info("CategoryServiceImpl addCategory() starts");
		Category category = null;
		
		try {
			category = setCategoryValues(categoryReq);
			categoryDao.save(category);

		} catch (Exception e) {
			logger.info("Exception inside CategoryServiceImpl addCategory() :"+ e.getMessage());
			return createFailedObject(CommonProperties.category_unable_to_add_message,Long.parseLong(CommonProperties.category_unable_to_add_code));

		}
		
		logger.info("CategoryServiceImpl addCategory() Ends");
		return createSuccessObject(CommonProperties.category_added_message,Long.parseLong(CommonProperties.category_added_code));
	}

	private Category setCategoryValues(CategoryModel categoryReq) {
		
		logger.info("CategoryServiceImpl setCategoryValues() starts");
		
		Category category = new Category();
		category.setName(categoryReq.getName());
		Status status = statusService.get(categoryReq.getStatusId());
		Type highlight = typeService.get(categoryReq.getHighlightId());
		category.setHighLight(highlight);
		Type type = typeService.get(categoryReq.getTypeId());
		category.setType(type);
		category.setStatus(status);
		
		logger.info("CategoryServiceImpl setCategoryValues() ends");
		return category;
	}

	@Override
	public Object update(Long id, CategoryModel categoryReq) {
		
		logger.info("CategoryServiceImpl update() starts, categoryId :"+id);
		Category category = null;
		
		try {
			category = categoryDao.findById(id);
			if (category != null) {
				category = setCategoryData(category, categoryReq);
				category = categoryDao.update(category);
			} else{
				return createFailedObject(CommonProperties.category_unable_to_update_message,Long.parseLong(CommonProperties.category_unable_to_update_code));
			}

		} catch (Exception e) {
			logger.info("Exception inside CategoryServiceImpl updateCategory() :"+ e.getMessage());
			return createFailedObject(CommonProperties.category_unable_to_update_message,Long.parseLong(CommonProperties.category_unable_to_update_code));
		}
		
		logger.info("CategoryServiceImpl update() ends, categoryId :"+id);
		return createSuccessObject(CommonProperties.category_updated_message,Long.parseLong(CommonProperties.category_updated_code));
	}

	private Category setCategoryData(Category category, CategoryModel categoryReq) {
	
		category.setName(categoryReq.getName());

		Status status = statusService.get(categoryReq.getStatusId());
		category.setStatus(status);

		Type highlight = typeService.get(categoryReq.getHighlightId());
		category.setHighLight(highlight);

		Type type = typeService.get(categoryReq.getTypeId());
		category.setType(type);
		
		return category;

	}

	@Override
	public Object delete(Long id) {

		logger.info("CategoryServiceImpl delete() starts, categoryId :"+id);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			Category category = (Category) session.get(Category.class, id);
			
			if (category != null) {
				tx = session.beginTransaction();
				session.delete(category);
				tx.commit();
			} else {
				return createFailedObject(CommonProperties.category_unable_to_delete_message,Long.parseLong(CommonProperties.category_deleted_code));
			}
			
		} catch (Exception e) {
			logger.info("Exception inside HandlingServiceImpl delete() : "+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createAlreadyExistObject(CommonProperties.category_already_used_message,Long.parseLong(CommonProperties.category_already_used_code));
			}
			return createFailedObject(CommonProperties.category_unable_to_delete_message,Long.parseLong(CommonProperties.category_deleted_code));
		} finally {

			if (session != null) {
				session.close();
			}
		}

		logger.info("CategoryServiceImpl delete() ends, categoryId :"+id);
		return createSuccessObject(CommonProperties.category_deleted_message,Long.parseLong(CommonProperties.category_unable_to_delete_code));
	}

	@Override
	public List<CategoryModel> getAll( ) {
	
		logger.info("CategoryServiceImpl getAll() starts");
		Session session = null;
		List<CategoryModel> categoriesList = new ArrayList<CategoryModel>();

		try {

			session = sessionFactory.openSession();
			List<Category> categories = categoryDao.findAll(session);

			if (categories != null && !categories.isEmpty()) {
				for (Category category : categories) {
					CategoryModel categoryReq = new CategoryModel();
					categoryReq.setCategoryId(category.getCategoryId());
					categoryReq.setName(category.getName());
					categoryReq.setHighlightName(category.getHighLight().getTypeName());
					categoryReq.setTypeName(category.getType().getTypeName());
					categoryReq.setStatusName(category.getStatus().getStatus());
					categoriesList.add(categoryReq);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	
		logger.info("CategoryServiceImpl getAll() ends");
		return categoriesList;
	}

	@Override
	public CategoryModel get(Long id) {
		
		logger.info("CategoryServiceImpl get() starts, categoryId :"+id);
		Session session = null;
		CategoryModel categoryReq = new CategoryModel();

		try {

			session = sessionFactory.openSession();
			Category category = categoryDao.findById(id, session);

			if (category != null) {

				categoryReq.setCategoryId(category.getCategoryId());
				categoryReq.setName(category.getName());
				categoryReq.setStatusId(category.getStatus().getId());
				categoryReq.setTypeId(category.getType().getTypeId());
				categoryReq.setHighlightId(category.getHighLight().getTypeId());

				List<Status> statusList = statusService.getAll();
				categoryReq.setStatusList(statusList);

				List<TypeResponse> typeList = typeService.getAll(3l);
				categoryReq.setTypeList(typeList);

				List<TypeResponse> highlightList = typeService.getAll(4l);
				categoryReq.setHighlightList(highlightList);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	
		logger.info("CategoryServiceImpl get() ends, categoryId :"+id);
		return categoryReq;
	}

	@Override
	public CategoryModel getOpenAdd() {
		
		logger.info("CategoryServiceImpl getOpenAdd() starts");
		CategoryModel categoryReq = new CategoryModel();

		List<Status> statusList = statusService.getAll();
		categoryReq.setStatusList(statusList);

		List<TypeResponse> typeList = typeService.getAll(3l);
		categoryReq.setTypeList(typeList);

		List<TypeResponse> highlightList = typeService.getAll(4l);
		categoryReq.setHighlightList(highlightList);
		
		logger.info("CategoryServiceImpl getOpenAdd() ends");
		return categoryReq;
	}

	@Override
	public List<CategoryModel> getCategoryByCategoryName(String categoryName) {
		
		logger.info("CategoryServiceImpl getCategoryByCategoryName() starts, categoryName :"+categoryName);
		Session session = null;
		List<CategoryModel> categories = new ArrayList<CategoryModel>();

		try {
			session = sessionFactory.openSession();
			List<Category> categoryList = categoryDao.getCategoryByCategoryName(session, categoryName);
			
			if (categoryList != null && !categoryList.isEmpty()) {
				for (Category category : categoryList) {
					CategoryModel categoryObj = new CategoryModel();
					categoryObj.setCategoryId(category.getCategoryId());
					categoryObj.setName(category.getName());
					categoryObj.setHighlightName(category.getHighLight().getTypeName());
					categoryObj.setTypeName(category.getType().getTypeName());
					categoryObj.setStatusName(category.getStatus().getStatus());
					categories.add(categoryObj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		logger.info("CategoryServiceImpl getCategoryByCategoryName() ends, categoryName :"+categoryName);
		return categories;
	}

	@Override
	public Category getCategory(Long categoryId) {
		Category category = categoryDao.findById(categoryId);
		return category;
	}

	@Override
	public List<CategoryModel> getSpecificData() {
		
		Session session = null;
		List<CategoryModel> categories = null;
		try{
			session = sessionFactory.openSession();
			List<Object[]> categoryData = categoryDao.getSpecificData(session,"Category","categoryId", "name");

			categories = new ArrayList<CategoryModel>();
			if (categoryData != null && !categoryData.isEmpty()) {
				for (Object[] row : categoryData) {
					CategoryModel categoryObj = new CategoryModel();
					categoryObj.setCategoryId((Long) row[0]);
					categoryObj.setName(String.valueOf(row[1]));
					categories.add(categoryObj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		

		return categories;
	}

	@Override
	public List<CategoryModel> getCategoriesBasedOnType(String unitTypeName) {

		Session session = null;
		List<CategoryModel> categories = null;
		try {
			session = sessionFactory.openSession();
			List<Category> categoryList = categoryDao.getCategoriesBasedOnType(unitTypeName, session);

			categories = new ArrayList<CategoryModel>();
			if (categoryList != null && !categoryList.isEmpty()) {
				for (Category category : categoryList) {
					CategoryModel categoryObj = new CategoryModel();
					categoryObj.setCategoryId(category.getCategoryId());
					categoryObj.setName(category.getName());
					categoryObj.setHighlightName(category.getHighLight().getTypeName());
					categoryObj.setTypeName(category.getType().getTypeName());
					categoryObj.setStatusName(category.getStatus().getStatus());
					categories.add(categoryObj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return categories;
	}

	@Override
	public List<CategoryModel> getCategoriesBasedOnType(Type unitType) {

		Session session = null;
		List<CategoryModel> categories = null;
		try {
			session = sessionFactory.openSession();
			List<Category> categoryList = categoryDao.getCategoriesBasedOnType(unitType, session);

			categories = new ArrayList<CategoryModel>();
			if (categoryList != null && !categoryList.isEmpty()) {
				for (Category category : categoryList) {
					CategoryModel categoryObj = new CategoryModel();
					categoryObj.setCategoryId(category.getCategoryId());
					categoryObj.setName(category.getName());
					categoryObj.setHighlightName(category.getHighLight().getTypeName());
					categoryObj.setTypeName(category.getType().getTypeName());
					categoryObj.setStatusName(category.getStatus().getStatus());
					categories.add(categoryObj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return categories;
	}

}
