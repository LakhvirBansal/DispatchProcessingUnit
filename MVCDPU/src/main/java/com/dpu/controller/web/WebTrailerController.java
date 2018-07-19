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
import com.dpu.model.TrailerModel;
import com.dpu.service.TrailerService;

@Controller
public class WebTrailerController {

	@Autowired
	TrailerService trailerService;

	Logger logger = Logger.getLogger(WebTrailerController.class);

	@RequestMapping(value = "/showtrailer", method = RequestMethod.GET)
	public ModelAndView showTrailerScreen() {
		ModelAndView modelAndView = new ModelAndView();
		List<TrailerModel> lstTrailers = trailerService.getAll();
		modelAndView.addObject("LIST_TRAILER", lstTrailers);
		modelAndView.setViewName("trailer");
		return modelAndView;
	}

	@RequestMapping(value = "/trailer/getopenadd", method = RequestMethod.GET)
	@ResponseBody
	public TrailerModel getOpenAdd() {
		TrailerModel TrailerModel = null;
		try {
			TrailerModel = trailerService.getOpenAdd();
		} catch (Exception e) {
			System.out.println(e);
		}
		return TrailerModel;
	}

	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	@RequestMapping(value = "/savetrailer", method = RequestMethod.POST)
	@ResponseBody
	public Object saveTrailer(@ModelAttribute("trailer") TrailerModel TrailerModel, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				Object response = trailerService.add(TrailerModel);
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

	@RequestMapping(value = "/gettrailer/trailerId", method = RequestMethod.GET)
	@ResponseBody
	public TrailerModel getTrailer(@RequestParam("trailerId") Long trailerId) {
		TrailerModel TrailerModel = null;
		try {
			TrailerModel = trailerService.get(trailerId);
		} catch (Exception e) {
			System.out.println(e);
			logger.info("Exception in getCategory is: " + e);
		}
		return TrailerModel;
	}

	@RequestMapping(value = "/updatetrailer", method = RequestMethod.POST)
	@ResponseBody
	public Object updateTrailer(@ModelAttribute("trailer") TrailerModel TrailerModel,
			@RequestParam("trailerid") Long trailerId, HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				Object response = trailerService.update(trailerId, TrailerModel);
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

	@RequestMapping(value = "/deletetrailer/{trailerid}", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteTrailer(@PathVariable("trailerid") Long trailerId) {
		Object response = trailerService.delete(trailerId);
		if (response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
}
