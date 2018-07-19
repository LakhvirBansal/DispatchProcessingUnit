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
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TerminalResponse;
import com.dpu.service.TerminalService;
import com.dpu.util.MessageProperties;


@RestController
@RequestMapping(value = "terminal")
public class TerminalController extends MessageProperties {
	
	Logger logger = Logger.getLogger(TerminalController.class);
	
	@Autowired
	TerminalService terminalService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${terminal_unable_to_add_message}")
	private String terminal_unable_to_add_message;

	@Value("${terminal_unable_to_delete_message}")
	private String terminal_unable_to_delete_message;
	
	@Value("${terminal_unable_to_update_message}")
	private String terminal_unable_to_update_message;

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllTerminals() {
		
		logger.info("TerminalController getAllTerminals() starts ");
		String json = null;
		
		try {
			List<TerminalResponse> lstTerminalRes = terminalService.getAllTerminals();
			if (lstTerminalRes != null && lstTerminalRes.size() > 0) {
				json = mapper.writeValueAsString(lstTerminalRes);
			}
		} catch (Exception e) {
			logger.error("Exception inside TerminalController getAllTerminals() :" + e.getMessage());
		}
		
		logger.info("TerminalController getAllTerminals() ends ");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody TerminalResponse terminalResp) {
		
		logger.info("TerminalController add() starts ");
		Object obj = null;
		
		try {
			Object response = terminalService.addTerminal(terminalResp);
			if (response instanceof Success) {
				obj = new ResponseEntity<Object>(response,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside TerminalController add() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, terminal_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}	
		
		logger.info("TerminalController add() ends ");
		return obj;
	}

	

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long id) {
		
		logger.info("TerminalController delete() starts, terminalId :"+id);
		Object obj = null;
		
		try {
			Object response = terminalService.deleteTerminal(id);
			if (response instanceof Success) {
				obj = new ResponseEntity<Object>(response,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside TerminalController delete() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, terminal_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("TerminalController delete() ends, terminalId :"+id);
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id, @RequestBody TerminalResponse terminalRes) {
		
		logger.info("TerminalController update() starts, terminalId :"+id);
		Object obj = null;
		
		try {
			Object response = terminalService.updateTerminal(id, terminalRes);
			if (response instanceof Success) {
				obj = new ResponseEntity<Object>(response,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside TerminalController update() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, terminal_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("TerminalController update() ends, terminalId :"+id);
		return obj;
	}

	@RequestMapping(value = "/{terminalid}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("terminalid") Long id) {
		
		logger.info("TerminalController get() starts, terminalId :"+id);
		String json = null;
		
		try {
			TerminalResponse terminalResponse = terminalService.getTerminal(id);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(terminalResponse);
		} catch (Exception e) {
			logger.error("Exception inside TerminalController get() :" + e.getMessage());
		}
		
		logger.info("TerminalController get() ends, terminalId :"+id);
		return json;
	}

	@RequestMapping(value = "/{terminalName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchTerminal(@PathVariable("terminalName") String terminalName) {
		
		logger.info("Inside TerminalController search, terminalName :"+terminalName);
		String json = new String();
		
		try {
			List<TerminalResponse> terminalList = terminalService.getTerminalByTerminalName(terminalName);
			if(terminalList != null && terminalList.size() > 0) {
				json = mapper.writeValueAsString(terminalList);
			}
		} catch (Exception e) {
			logger.error("Exception inside TerminalController search is :" + e.getMessage());
		}
		logger.info(" Inside TerminalController terminalService() Starts, terminalName :"+terminalName);
		return json;
	}
	
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info(" Inside TerminalController openAdd() Starts ");
		String json = null;
		
		try {
			TerminalResponse terResponse = terminalService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(terResponse);
		} catch (Exception e) {
			logger.error("Exception inside TerminalController openAdd is :" + e.getMessage());
		}
		
		logger.info(" Inside TerminalController openAdd() Ends ");
		return json;
	}
}
