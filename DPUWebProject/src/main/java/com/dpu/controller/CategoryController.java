package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.constants.Iconstants;
import com.dpu.model.CategoryModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CategoryService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "category")
public class CategoryController extends MessageProperties {

	Logger logger = Logger.getLogger(CategoryController.class);

	@Autowired
	CategoryService categoryService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${category_unable_to_add_message}")
	private String category_unable_to_add_message;

	@Value("${category_unable_to_delete_message}")
	private String category_unable_to_delete_message;
	
	@Value("${category_unable_to_update_message}")
	private String category_unable_to_update_message;

	/**
	 * this method is used to get all categories
	 * @return List<Categories>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {

		logger.info("Inside CategoryController getAll() Starts ");
		String json = null;

		try {
			List<CategoryModel> responses = categoryService.getAll();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside CategoryController getAll()"+ e.getMessage());
		}
		logger.info("Inside CategoryController getAll() Ends");
		return json;
	}

	/**
	 * this method is used to add the category
	 * @param categoryReq
	 * @return List<category>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody CategoryModel categoryReq) {

		logger.info("Inside CategoryController add() Starts ");
		Object obj = null;
		
		try {
			Object result = categoryService.addCategory(categoryReq);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside CategoryController add() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,category_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside CategoryController add() ends ");
		return obj;
	}

	/**
	 * this method is used to delete the category based on categoryId
	 * @param id
	 * @return List<category>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long id) {
		
		logger.info("Inside CategoryController delete() Starts, categoryId is :" + id);
		Object obj = null;

		try {
			Object result = categoryService.delete(id);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside CategoryController delete() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,category_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside CategoryController delete() Ends, categoryId is :" + id);
		return obj;

	}

	/**
	 * this method is used to update the categoryData based on categoryID
	 * @param id
	 * @param categoryReq
	 * @return List<Categories>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id, @RequestBody CategoryModel categoryReq) {
		
		logger.info("Inside CategoryController update() Starts, categoryId is :" + id);
		Object obj = null;
		
		try {
			Object result = categoryService.update(id, categoryReq);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside CategoryController update() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,category_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside CategoryController update() Ends, categoryId is :" + id);
		return obj;
	}

	/**
	 * this method is used to get category data based on categoryId
	 * @param id
	 * @return categorydata
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getCategoryById(@PathVariable("categoryId") Long id) {

		logger.info("Inside CategoryController getCategoryById() Starts, categoryId:"+ id);
		String json = null;

		try {
			CategoryModel categoryReq = categoryService.get(id);

			if (categoryReq != null) {
				json = mapper.writeValueAsString(categoryReq);
			}
		} catch (Exception e) {
			logger.error("Exception inside CategoryController getCategoryById() :"+ e.getMessage());
		}

		logger.info("Inside CategoryController getCategoryById() Ends, categoryId:"+ id);
		return json;
	}

	/**
	 * this method is used when we click on add button on category screen to
	 * send master data
	 * @return master data for add category
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info("CategoryController openAdd() starts");
		String json = null;

		try {
			CategoryModel CategoryReq = categoryService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(CategoryReq);
		} catch (Exception e) {
			logger.error(" Exception inside CategoryController openAdd() :"+ e.getMessage());
		}

		logger.info("CategoryController openAdd() ends");
		return json;
	}

	/**
	 * this method is used to get category based on category name
	 * @param categoryName
	 * @return List<category>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{categoryName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchCategory(@PathVariable("categoryName") String categoryName) {
	
		logger.info("Inside CategoryController searchCategory() Starts, categoryName :"+ categoryName);
		String json = new String();

		try {
			List<CategoryModel> categoryList = categoryService.getCategoryByCategoryName(categoryName);
			if (categoryList != null && categoryList.size() > 0) {
				json = mapper.writeValueAsString(categoryList);
			}
		} catch (Exception e) {
			logger.error("Exception inside CategoryController searchCategory() :"+ e.getMessage());
		}

		logger.info(" Inside CategoryController searchCategory() Ends, categoryName :"+ categoryName);
		return json;
	}

	/**
	 * this method is used to get specific categoryData (Id and name)
	 * @return category specific data
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/specificData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getSpecificData() {

		logger.info("CategoryController getSpecificData() starts");
		String json = new String();

		try {
			List<CategoryModel> categoryList = categoryService.getSpecificData();
			if (categoryList != null && categoryList.size() > 0) {
				json = mapper.writeValueAsString(categoryList);
			}
		} catch (Exception e) {
			logger.error("Exception inside CategoryController getSpecificData() is :"+ e.getMessage());
		}

		logger.info("CategoryController getSpecificData() ends");
		return json;
	}
}
