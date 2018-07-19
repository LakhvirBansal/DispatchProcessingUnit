package com.dpu.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.dpu.entity.Status;
import com.dpu.model.DivisionReq;
import com.dpu.model.Failed;
import com.dpu.service.DivisionService;
import com.dpu.service.StatusService;

@Controller
public class WebDivisionController {

	@Autowired
	DivisionService divisionService;
	
	@Autowired
	StatusService statusService;
	
	Logger logger = Logger.getLogger(WebDivisionController.class);
	
	@RequestMapping(value = "/showdivision", method = RequestMethod.GET)
	public ModelAndView showDivisionScreen(@RequestParam(value = "msg", required = false) String msg) {
		ModelAndView modelAndView = new ModelAndView();
		List<DivisionReq> lstDivisions = divisionService.getAll("");
		modelAndView.addObject("LIST_DIVISION", lstDivisions);
		if(msg != null && msg.length() > 0) {
			modelAndView.addObject("msg", msg);
		}
		modelAndView.setViewName("division");
		return modelAndView;
	}
	
	@RequestMapping(value = "/getStatus" , method = RequestMethod.GET)
	@ResponseBody public List<Status> getStatus() {
		List<Status> status = null;
		try {
			status = statusService.getAll();
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}
	
	
	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}
	@RequestMapping(value = "/savedivision" , method = RequestMethod.POST)
	@ResponseBody public Object saveDivision(@ModelAttribute("division") DivisionReq divisionReq, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if(session != null) {
			if(session.getAttribute("un") != null) {
				Object response = divisionService.add(divisionReq);
				if(response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/getdivision/divisionId" , method = RequestMethod.GET)
	@ResponseBody  public DivisionReq getDivision(@RequestParam("divisionId") Long divisionId) {
		DivisionReq divisionReq = null;
		try {
			divisionReq = divisionService.get(divisionId);
		} catch (Exception e) {
			System.out.println(e);
			logger.info("Exception in getCategory is: " + e);
		}
		return divisionReq;
	}
	
	@RequestMapping(value = "/updatedivision" , method = RequestMethod.POST)
	@ResponseBody public Object updateDivision(@ModelAttribute("division") DivisionReq divisionReq, @RequestParam("divisionid") Long divisionId, HttpServletRequest request) {
		
		HttpSession session = request.getSession();

		if(session != null) {
			if(session.getAttribute("un") != null) {
				Object response = divisionService.update(divisionId, divisionReq);
				if(response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/deletedivision/{divisionid}" , method = RequestMethod.GET)
	@ResponseBody public Object deleteDivision(@PathVariable("divisionid") Long divisionId) {
		Object response = divisionService.delete(divisionId);
		if(response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
}
