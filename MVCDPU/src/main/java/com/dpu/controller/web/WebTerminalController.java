package com.dpu.controller.web;

import java.util.ArrayList;
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
import com.dpu.model.TerminalResponse;
import com.dpu.service.TerminalService;

@Controller
public class WebTerminalController {

	@Autowired
	TerminalService terminalService;

	Logger logger = Logger.getLogger(WebTerminalController.class);

	@RequestMapping(value = "/showterminal", method = RequestMethod.GET)
	public ModelAndView showTerminalScreen() {
		ModelAndView modelAndView = new ModelAndView();
		List<TerminalResponse> lstTerminals = terminalService.getAllTerminals();
		modelAndView.addObject("LIST_TERMINAL", lstTerminals);
		modelAndView.setViewName("terminal");
		return modelAndView;
	}

	@RequestMapping(value = "/terminal/getopenadd", method = RequestMethod.GET)
	@ResponseBody
	public TerminalResponse getOpenAdd() {
		TerminalResponse terminalResponse = null;
		try {
			terminalResponse = terminalService.getOpenAdd();
		} catch (Exception e) {
			System.out.println(e);
		}
		return terminalResponse;
	}

	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	@RequestMapping(value = "/saveterminal", method = RequestMethod.POST)
	@ResponseBody
	public Object saveTerminal(@ModelAttribute("terminal") TerminalResponse terminalResponse, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				List<String> stringServiceIds = terminalResponse.getStringServiceIds();
				List<Long> serviceIds = new ArrayList<Long>();
				for (int i = 0; i < stringServiceIds.size(); i++) {
					serviceIds.add(Long.parseLong(stringServiceIds.get(i)));
				}
				terminalResponse.setServiceIds(serviceIds);
				terminalResponse.setStatusId(1l);
				Object response = terminalService.addTerminal(terminalResponse);
				if (response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE),
				HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getterminal/terminalId", method = RequestMethod.GET)
	@ResponseBody
	public TerminalResponse getTerminal(@RequestParam("terminalId") Long terminalId) {
		TerminalResponse terminalResponse = null;
		try {
			terminalResponse = terminalService.getTerminal(terminalId);
		} catch (Exception e) {
			System.out.println(e);
			logger.info("Exception in getCategory is: " + e);
		}
		return terminalResponse;
	}

	@RequestMapping(value = "/updateterminal", method = RequestMethod.POST)
	@ResponseBody
	public Object updateTerminal(@ModelAttribute("terminal") TerminalResponse terminalResponse,
			@RequestParam("terminalid") Long terminalId, HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				List<String> stringServiceIds = terminalResponse.getStringServiceIds();
				List<Long> serviceIds = new ArrayList<Long>();
				for (int i = 0; i < stringServiceIds.size(); i++) {
					serviceIds.add(Long.parseLong(stringServiceIds.get(i)));
				}
				terminalResponse.setServiceIds(serviceIds);
				terminalResponse.setStatusId(1l);
				Object response = terminalService.updateTerminal(terminalId, terminalResponse);
				if (response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE),
				HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/deleteterminal/{terminalid}", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteTerminal(@PathVariable("terminalid") Long terminalId) {
		Object response = terminalService.deleteTerminal(terminalId);
		if (response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
}
