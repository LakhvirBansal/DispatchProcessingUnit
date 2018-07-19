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
import com.dpu.model.Failed;
import com.dpu.model.IssueModel;
import com.dpu.service.IssueService;

@Controller
public class WebIssueController {

	@Autowired
	IssueService issueService;
	
	Logger logger = Logger.getLogger(WebIssueController.class);
	
	@RequestMapping(value = "/showissue", method = RequestMethod.GET)
	public ModelAndView showIssueScreen() {
		ModelAndView modelAndView = new ModelAndView();
		List<IssueModel> lstIssues = issueService.getAll();
		modelAndView.addObject("LIST_ISSUE", lstIssues);
		modelAndView.setViewName("issue");
		return modelAndView;
	}
	
	
	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}
	@RequestMapping(value = "/issue/getopenadd" , method = RequestMethod.GET)
	@ResponseBody public IssueModel getOpenAdd() {
		IssueModel issueModel = null;
		try {
			issueModel = issueService.getOpenAdd();
		} catch (Exception e) {
			System.out.println(e);
		}
		return issueModel;
	}
	
	@RequestMapping(value = "/issue/getunitno/category/{category}/unittype/{unittype}" , method = RequestMethod.GET)
	@ResponseBody public IssueModel getUnitNo(@PathVariable("category") Long categoryId, @PathVariable("unittype") Long unitTypeId) {
		IssueModel issueModel = null;
		try {
			issueModel = issueService.getUnitNo(categoryId, unitTypeId);
		} catch (Exception e) {
			System.out.println(e);
		}
		return issueModel;
	}
	
	@RequestMapping(value = "/saveissue" , method = RequestMethod.POST)
	@ResponseBody public Object saveIssue(@ModelAttribute("issue") IssueModel issueModel, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if(session != null) {
			if(session.getAttribute("un") != null) {
				Object response = issueService.addIssue(issueModel);
				if(response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/getissue/issueId" , method = RequestMethod.GET)
	@ResponseBody  public IssueModel getIssue(@RequestParam("issueId") Long issueId) {
		IssueModel issueModel = null;
		try {
			issueModel = issueService.get(issueId);
		} catch (Exception e) {
			System.out.println(e);
			logger.info("Exception in getCategory is: " + e);
		}
		return issueModel;
	}
	
	@RequestMapping(value = "/updateissue" , method = RequestMethod.POST)
	@ResponseBody public Object updateIssue(@ModelAttribute("issue") IssueModel issueModel, @RequestParam("issueid") Long issueId, HttpServletRequest request) {
		
		HttpSession session = request.getSession();

		if(session != null) {
			if(session.getAttribute("un") != null) {
				Object response = issueService.update(issueId, issueModel);
				if(response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/deleteissue/{issueid}" , method = RequestMethod.GET)
	@ResponseBody public Object deleteTerminal(@PathVariable("issueid") Long issueId) {
		Object response = issueService.delete(issueId);
		if(response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
}
