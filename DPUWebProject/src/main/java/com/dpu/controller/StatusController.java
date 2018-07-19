package com.dpu.controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dpu.entity.Status;
import com.dpu.service.StatusService;
import com.dpu.util.MessageProperties;


@RestController
@RequestMapping(value = "status")
public class StatusController extends MessageProperties {
	
	Logger logger = Logger.getLogger(StatusController.class);
	
	@Autowired
	StatusService statusService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		
		logger.info("StatusController getAll() starts");
		String json = null;
		
		try {
			List<Status> ListStatus = statusService.getAll();
			if(ListStatus != null) {
				json = mapper.writeValueAsString(ListStatus);
			}
		} catch (Exception e) {
			logger.info("Exception inside StatusController getAll() :"+e.getMessage());
		}
		
		logger.info("StatusController getAll() ends");
		return json;
	}

}
