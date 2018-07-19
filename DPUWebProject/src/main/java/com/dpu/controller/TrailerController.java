package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dpu.model.Success;
import com.dpu.model.TrailerModel;
import com.dpu.service.TrailerService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "trailer")
public class TrailerController extends MessageProperties {
	
	Logger logger = Logger.getLogger(TrailerController.class);

	@Autowired
	TrailerService trailerService;

	ObjectMapper mapper = new ObjectMapper();
	
	
	/**
	 * this method is used to get all trailers
	 * @return List<trailers>
	 * @author lakhvir
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		
		logger.info("Inside TrailerController getAll() Starts ");
		String json = new String();
		
		try {
			List<TrailerModel> lstTrailers = trailerService.getAll();

			if(lstTrailers != null && !lstTrailers.isEmpty()) {
				json = mapper.writeValueAsString(lstTrailers);
			}
			
		} catch (Exception e) {
			logger.error("Exception inside  TrailerController getAll() :" + e.getMessage());
		}
		
		logger.info("Inside TrailerController getAll() Ends ");
		return json;
	}
	
	/**
	 * this method is used to add the trailer
	 * @param trailerRequest
	 * @return all trailers List
	 * @author lakhvir
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody TrailerModel trailerRequest) {
		
		logger.info("Inside TrailerController add() starts");
		Object obj = null;
		try {
			Object response = trailerService.add(trailerRequest);
			if (response instanceof Success) {
				obj = new ResponseEntity<Object>(response, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside TrailerController add() :" + e.getMessage());
		}
		
		logger.info("Inside TrailerController add() Ends");
		return obj;
	}

	/**
	 * this method is used to delete the trailer based on trailerId
	 * @param trailerId
	 * @return List<Trailers>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long trailerId) {

		logger.info("Inside TrailerController delete() starts");
		Object obj = null;
		try {

			obj = trailerService.delete(trailerId);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside TrailerController delete() :" + e.getMessage());
		}
		
		logger.info("Inside TrailerController delete() ends");
		return obj;
	}

	/**
	 * this method is used to update the update the trailer based on trailerId
	 * @param trailerId
	 * @param trailerRequest
	 * @return List<trailer>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long trailerId,@RequestBody TrailerModel trailerRequest) {

		logger.info("Inside TrailerController update() starts, trailerId :"+trailerId);
		Object obj = null;
		try {
			Object response = trailerService.update(trailerId,trailerRequest);
			if (response instanceof Success) {
				obj = new ResponseEntity<Object>(response,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside TrailerController update() :" + e.getMessage());
		}
		
		logger.info("Inside TrailerController update() ends, trailerId :"+trailerId);
		return obj;
	}

	/**
	 * this method is used to get the trailer based on trailerId
	 * @param id
	 * @return particular trailer info
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("id") Long id) {
		
		logger.info("Inside TrailerController get() starts, trailerId :"+id);
		String json = new String();
		
		try {
			TrailerModel trailerRequest = trailerService.get(id);
			if (trailerRequest != null) {
				json = mapper.writeValueAsString(trailerRequest);
			}
		} catch (Exception e) {
			logger.error("Exception inside TrailerController delete() :" + e.getMessage());
		}
		
		logger.info("Inside TrailerController get() ends, trailerId :"+id);
		return json;
	}
	
	/*private Trailer setTrailerValues(TrailerRequest trailerRequest){
		Trailer trailer = new Trailer();
		trailer.setTrailerId(trailerRequest.getTrailerId());
		trailer.setUnitNo(trailerRequest.getUnitNo());
		trailer.setUsage(trailerRequest.getUsage());
		trailer.setOwner(trailerRequest.getOwner());
		trailer.setDivision(trailerRequest.getDivision());
		trailer.setoOName(trailerRequest.getoOName());
		trailer.setTerminal(trailerRequest.getTerminal());
		trailer.setCategory(trailerRequest.getCategory());
		trailer.setTrailerType(trailerRequest.getTrailerType());
		trailer.setStatus(trailerRequest.getStatus());
		trailer.setFinance(trailerRequest.getFinance());
		
		return trailer;
	}*/
	
	/**
	 * this method is used when we click on add trailer
	 * @return masterdata for trailer
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		logger.info(" Inside TrailerController openAdd() Starts ");
		String json = null;
		try {
			TrailerModel trailerReq = trailerService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(trailerReq);
		} catch (Exception e) {
			logger.error("Exception inside TrailerController openAdd() :" + e.getMessage());
		}
		logger.info(" Inside TrailerController openAdd() Ends ");
		return json;
	}
	
	/**
	 * this method is used to return Specific data (trailerId, owner)
	 * @return List<TrailerRequest>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/specificData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getSpecificData() {

		logger.info("Inside TrailerController getSpecificData() Starts");
		String json = new String();

		try {
			List<TrailerModel> trailerData = trailerService.getSpecificData();
			if (trailerData != null && trailerData.size() > 0) {
				json = mapper.writeValueAsString(trailerData);
			}
		} catch (Exception e) {
			logger.error("Exception inside TrailerController getSpecificData() is :"+ e.getMessage());
		}

		logger.info("Inside TrailerController getSpecificData() ends");
		return json;
	}
	
}
