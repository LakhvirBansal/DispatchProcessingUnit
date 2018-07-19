package com.dpu.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dpu.model.EmployeeModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.EmployeeService;

@Controller
public class LoginController {

	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value = "/authUser", method = RequestMethod.POST)
	public ModelAndView authenticateUser(@RequestParam("username") String username,@RequestParam("password") String password, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		if(username != null && username.length() > 0 && password != null && password.length() > 0) {
			EmployeeModel employeeModel = new EmployeeModel();
			employeeModel.setUsername(username);
			employeeModel.setPassword(password);
			Object response = employeeService.getUserByLoginCredentials(employeeModel);
			if(response instanceof Success) {
				Success success = (Success) response;
				HttpSession session = request.getSession();
				session.setAttribute("un", username);
				modelAndView.setViewName("redirect:showuser");
//				modelAndView.setViewName("homepage");
			} else {
				modelAndView.addObject("error", "Invalid Username/Password");
				modelAndView.setViewName("redirect:login");
			}
		}
		return modelAndView;
	}

	ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping(value = "/loginuser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object loginUser(@RequestHeader("username") String username, @RequestHeader("password") String password) {

		Object obj = null;
		try {


			EmployeeModel employeeModel = new EmployeeModel();
			employeeModel.setUsername(username);
			employeeModel.setPassword(password);
			Object result = employeeService
					.getUserByLoginCredentials(employeeModel);
			if (result instanceof Success) {
				Success s = (Success) result;
				return mapper.writeValueAsString(s);
				// obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				Failed f = (Failed) result;
				return mapper.writeValueAsString(f);
				// obj = new ResponseEntity<Object>(result,
				// HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
		}

		return obj;
	}
	
	@RequestMapping(value = {"/login" , "/"})
	public ModelAndView showLoginScreen() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	@RequestMapping(value = {"/logout"})
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		if(request != null) {
			request.getSession().invalidate();
		}
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	@RequestMapping(value = {"/home"})
	public ModelAndView showHomepageScreen() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("homepage");
		return modelAndView;
	}
}
