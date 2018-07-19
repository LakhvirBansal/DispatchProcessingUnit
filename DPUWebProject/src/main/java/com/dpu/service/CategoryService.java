/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Category;
import com.dpu.entity.Type;
import com.dpu.model.CategoryModel;

/**
 * @author jagvir
 *
 */
public interface CategoryService {
	Object addCategory(CategoryModel categoryReq);

	Object update(Long id, CategoryModel categoryReq);

	Object delete(Long id);

	List<CategoryModel> getAll( );

	CategoryModel getOpenAdd();

	CategoryModel get(Long id);

	List<CategoryModel> getCategoryByCategoryName(String categoryName);
	
	Category getCategory(Long categoryId);

	List<CategoryModel> getSpecificData();

	List<CategoryModel> getCategoriesBasedOnType(String unitTypeName);

	List<CategoryModel> getCategoriesBasedOnType(Type unitType);

}
