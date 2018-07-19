package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.model.TypeResponse;
import com.dpu.service.TypeService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "masterData")
public class TypeController extends MessageProperties {

	Logger logger = Logger.getLogger(TypeController.class);
	
	@Autowired
	TypeService typeService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(value = "/type", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllTypes() {
		
		logger.info("TypeController getAllTypes() STARTS");
		String json = new String();
		
		try {
			List<TypeResponse> typeResponses = typeService.getAll(1l);
			
			if(typeResponses != null) {
				json = mapper.writeValueAsString(typeResponses);
			}
			
		} catch (Exception e) {
			logger.info("Exception inside TypeController getAllTypes() :"+e.getMessage());
		}
		
		logger.info("TypeController getAllTypes() ends");
		return json;
	}

	@RequestMapping(value = "/textFields", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllTextFields() {
		
		logger.info("TypeController getAllTextFields() STARTS");
		String json = new String();
		
		try {
			
			List<TypeResponse> typeResponses = typeService.getAll(2l);
			
			if(typeResponses != null) {
				json = mapper.writeValueAsString(typeResponses);
			}
			
		} catch (Exception e) {
			logger.info("Exception inside TypeController getAllTextFields() :"+e.getMessage());
		}
		
		logger.info("TypeController getAllTextFields() ends");
		return json;
	}
	
	@RequestMapping(value = "/assosiationWith", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllAssosiationWith() {
		
		logger.info("TypeController getAllAssosiationWith() STARTS");
		String json = new String();
		
		try {
			List<TypeResponse> typeResponses = typeService.getAll(3l);
			
			if(typeResponses != null) {
				json = mapper.writeValueAsString(typeResponses);
			}
			
		} catch (Exception e) {
			logger.info("Exception inside TypeController getAllAssosiationWith() :"+e.getMessage());
		}
		
		logger.info("TypeController getAllAssosiationWith() ends");
		return json;
	}
}
