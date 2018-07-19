package com.dpu.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dpu.constants.Iconstants;
import com.dpu.model.CategoryModel;
import com.dpu.model.Failed;
import com.dpu.service.CategoryService;

@Controller
public class WebCategoryController {

	@Autowired
	CategoryService categoryService;
	
	Logger logger = Logger.getLogger(WebCategoryController.class);
	
	@RequestMapping(value = "/showcategory", method = RequestMethod.GET)
	public ModelAndView showCategoryScreen() {
		ModelAndView modelAndView = new ModelAndView();
		List<CategoryModel> lstCategories = categoryService.getAll();
		modelAndView.addObject("LIST_CATEGORY", lstCategories);
		modelAndView.setViewName("category");
		return modelAndView;
	}
	
	@RequestMapping(value = "/getopenadd" , method = RequestMethod.GET)
	@ResponseBody public CategoryModel getOpenAdd() {
		CategoryModel CategoryModel = null;
		try {
			CategoryModel = categoryService.getOpenAdd();
		} catch (Exception e) {
			System.out.println(e);
		}
		return CategoryModel;
	}
	
	@RequestMapping(value = "/savecategory" , method = RequestMethod.POST)
	@ResponseBody public Object saveCategory(@ModelAttribute("cat") CategoryModel CategoryModel, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if(session != null) {
			if(session.getAttribute("un") != null) {
				Object response = categoryService.addCategory(CategoryModel);
				if(response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE), HttpStatus.BAD_REQUEST);
	}
	
	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}
	
	@RequestMapping(value = "/getcategory/categoryId" , method = RequestMethod.GET)
	@ResponseBody  public CategoryModel getCategory(@RequestParam("categoryId") Long categoryId) {
		CategoryModel CategoryModel = null;
		try {
			CategoryModel = categoryService.get(categoryId);
		} catch (Exception e) {
			System.out.println(e);
			logger.info("Exception in getCategory is: " + e);
		}
		return CategoryModel;
	}
	
	@RequestMapping(value = "/updatecategory" , method = RequestMethod.POST)
	@ResponseBody public Object updateCategory(@ModelAttribute("cat") CategoryModel CategoryModel, @RequestParam("categoryid") Long categoryId, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if(session != null) {
			if(session.getAttribute("un") != null) {
				Object response = categoryService.update(categoryId, CategoryModel);
				if(response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/deletecategory/{categoryid}" , method = RequestMethod.GET)
	@ResponseBody public Object deleteCategory(@PathVariable("categoryid") Long categoryId) {
		Object response = categoryService.delete(categoryId);
		if(response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
}