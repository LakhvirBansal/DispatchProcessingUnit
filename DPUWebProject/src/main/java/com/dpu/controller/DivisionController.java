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
import com.dpu.model.DivisionReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.DivisionService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "division")
public class DivisionController extends MessageProperties {

	Logger logger = Logger.getLogger(DivisionController.class);

	@Autowired
	DivisionService divisionService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${division_unable_to_add_message}")
	private String division_unable_to_add_message;

	@Value("${division_unable_to_delete_message}")
	private String division_unable_to_delete_message;
	
	@Value("${division_unable_to_update_message}")
	private String division_unable_to_update_message;

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllDivisions() {
		
		logger.info("DivisionController getAllDivisions() starts");
		String json = null;
		
		try {
			List<DivisionReq> lstDivisionReqs = divisionService.getAll("");
			if (lstDivisionReqs != null && lstDivisionReqs.size() > 0) {
				json = mapper.writeValueAsString(lstDivisionReqs);
			}
		} catch (Exception e) {
			logger.info("Exception inside DivisionController getAllDiviion() :"+ e.getMessage());
		}
		
		logger.info("DivisionController getAllDivisions() ends");
		return json;
	}

	@RequestMapping(value = "/{divisionname}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchDivisionByDivisionName(@PathVariable("divisionname") String divisionName) {
		
		logger.info("DivisionController searchDivisionByDivisionName starts, divisionName :"+divisionName);
		String json = new String();
		
		try {
			List<DivisionReq> lstDivisions = divisionService.getAll(divisionName);
			if (lstDivisions != null && lstDivisions.size() > 0) {
				json = mapper.writeValueAsString(lstDivisions);
			}
		} catch (Exception e) {
			logger.info("Exception inside DivisionController getAllDivision() :"+ e.getMessage());
		}
		
		logger.info("DivisionController searchDivisionByDivisionName ends, divisionName :"+divisionName);
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object addDivision(@RequestBody DivisionReq divisionReq) {
		
		logger.info("DivisionController addDivision() starts");
		Object obj = null;
		try {

			Object result = divisionService.add(divisionReq);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.info("Exception inside DivisionController addDivision() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, division_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("DivisionController addDivision() ends");
		return obj;

	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long id) {
		
		logger.info("DivisionController delete() starts, divisionId :"+id);
		Object obj = null;
		try {
			Object result = divisionService.delete(id);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.info("Exception inside DivisionController delete() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, division_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("DivisionController delete() ends, divisionId :"+id);
		return obj;

	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id, @RequestBody DivisionReq divisionReq) {

		logger.info("DivisionController update() starts, divisionId :" + id);
		Object obj = null;
		try {
			divisionReq.setDivisionId(id);
			Object result = divisionService.update(id, divisionReq);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.info("Exception inside DivisionController update() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, division_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("DivisionController update() ends, divisionId :" + id);
		return obj;
	}

	@RequestMapping(value = "/{divisionId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("divisionId") Long id) {
		
		logger.info("DivisionController get() starts, divisionId :"+id);
		String json = null;
		
		try {
			DivisionReq divisionReq = divisionService.get(id);
			if (divisionReq != null) {
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(divisionReq);
			}
		} catch (Exception e) {
			logger.info("Exception inside DivisionController get() :"+ e.getMessage());
		}
		
		logger.info("DivisionController get() ends, divisionId :"+id);
		return json;
	}

}
