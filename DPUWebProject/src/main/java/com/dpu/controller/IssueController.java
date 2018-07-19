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
import com.dpu.model.CategoryModel;
import com.dpu.model.Failed;
import com.dpu.model.IssueModel;
import com.dpu.model.Success;
import com.dpu.service.IssueService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "issue")
public class IssueController extends MessageProperties {

	Logger logger = Logger.getLogger(IssueController.class);

	@Autowired
	IssueService issueService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${issue_unable_to_add_message}")
	private String issue_unable_to_add_message;

	@Value("${issue_unable_to_delete_message}")
	private String issue_unable_to_delete_message;
	
	@Value("${issue_unable_to_update_message}")
	private String issue_unable_to_update_message;
	
	@Value("${issue_status_unable_to_update}")
	private String issue_status_unable_to_update;

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {

		logger.info("Inside IssueController getAll() Starts ");
		String json = null;

		try {
			List<IssueModel> responses = issueService.getAll();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside IssueController getAll() :"+ e.getMessage());
		}
		
		logger.info("Inside IssueController getAll() Ends ");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody IssueModel issueModel) {

		logger.info("Inside IssueController add() starts ");
		Object obj = null;
		
		try {

			Object result = issueService.addIssue(issueModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside IssueController add() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,issue_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside IssueController add() ends ");
		return obj;
	}

	@RequestMapping(value = "/{issueId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("issueId") Long issueId) {

		logger.info("Inside IssueController delete() Starts, issueId is :" + issueId);
		Object obj = null;

		try {
			Object result = issueService.delete(issueId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside IssueController delete() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,issue_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		logger.info("Inside IssueController delete() Ends, issueId is :" + issueId);
		return obj;

	}

	@RequestMapping(value = "/{issueId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("issueId") Long issueId, @RequestBody IssueModel issueModel) {

		logger.info("Inside IssueController update() Starts, issueId is :" + issueId);
		Object obj = null;
		try {
			Object result = issueService.update(issueId, issueModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside IssueController update() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,issue_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside IssueController update() Ends, issueId is :" + issueId);
		return obj;
	}


	@RequestMapping(value = "/{issueId}/status/{statusId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object updateStatus(@PathVariable("issueId") Long issueId, @PathVariable("statusId") Long statusId) {

		logger.info("Inside IssueController updateStatus() Starts, issueId is :" + issueId +": statusId :"+statusId);
		Object obj = null;
		try {
			Object result = issueService.updateStatus(issueId, statusId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside IssueController updateStatus() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,issue_status_unable_to_update, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside IssueController updateStatus() Ends, issueId is :" + issueId +": statusId :"+statusId);
		return obj;
	}
	
	@RequestMapping(value = "/{issueId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getIssueByIssueId(@PathVariable("issueId") Long issueId) {
		
		logger.info("Inside IssueController getIssueByIssueId() Starts, issueId:"+ issueId);
		String json = null;

		try {

			IssueModel issueModel = issueService.get(issueId);

			if (issueModel != null) {
				json = mapper.writeValueAsString(issueModel);
			}
		} catch (Exception e) {
			logger.error("Exception inside IssueController getIssueByIssueId() :"+ e.getMessage());
		}

		logger.info("Inside IssueController getIssueByIssueId() Ends, issueId:"+ issueId);
		return json;
	}

	/**
	 * this method is used when we click on add button on issue screen
	 * send master data
	 * @return master data for add handling
	 * @author lakhvir
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info("Inside IssueController openAdd() Starts ");
		String json = null;

		try {
			IssueModel model = issueService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(model);
		} catch (Exception e) {
			logger.error(" Exception inside IssueController openAdd() :"+ e.getMessage());
		}

		logger.info("Inside IssueController openAdd() ends ");
		return json;
	}
	
	/**
	 * this method is used when we click on add button on issue screen
	 * send master data
	 * @return master data for add handling
	 * @author lakhvir
	 */
	@RequestMapping(value = "/unitType/{unitTypeName}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getCategorybasedOnUnitType(@PathVariable("unitTypeName") String unitTypeName) {

		logger.info("Inside IssueController getUnitNo() Starts ");
		String json = null;

		try {
			List<CategoryModel> model = issueService.getUnitCategories(unitTypeName);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(model);
		} catch (Exception e) {
			logger.error(" Exception inside IssueController openAdd() :" + e.getMessage());
		}

		logger.info("Inside IssueController openAdd() ends ");
		return json;
	}

	/**
	 * this method is used when we click on add button on issue screen send master data
	 * 
	 * @return master data for add handling
	 * @author lakhvir
	 */
	@RequestMapping(value = "/category/{categoryId}/unitType/{unitTypeId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getUnitNo(@PathVariable("categoryId") Long categoryId, @PathVariable("unitTypeId") Long unitTypeId ) {
		
		logger.info("Inside IssueController getUnitNo() Starts ");
		String json = null;

		try {
			IssueModel model = issueService.getUnitNo(categoryId, unitTypeId);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(model);
		} catch (Exception e) {
			logger.error(" Exception inside IssueController openAdd() :"+ e.getMessage());
		}

		logger.info("Inside IssueController openAdd() ends ");
		return json;
	}

	@RequestMapping(value = "/{issueName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchIssue(@PathVariable("issueName") String issueName) {

		logger.info("Inside IssueController searchIssue() Starts, issueName :"+ issueName);
		String json = new String();

		try {
			List<IssueModel> handlingList = issueService.getIssueByIssueName(issueName);
			if (handlingList != null && handlingList.size() > 0) {
				json = mapper.writeValueAsString(handlingList);
			}
		} catch (Exception e) {
			logger.error("Exception inside IssueController searchIssue() is :"+ e.getMessage());
		}

		logger.info(" Inside IssueController searchIssue() Ends, issueName :"+ issueName);
		return json;
	}

	
	@RequestMapping(value = "/specificData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getSpecificData() {

		logger.info("Inside IssueController getSpecificData() Starts ");
		String json = new String();

		try {
			List<IssueModel> issueList = issueService.getSpecificData();
			if (issueList != null && issueList.size() > 0) {
				json = mapper.writeValueAsString(issueList);
			}
		} catch (Exception e) {
			logger.error("Exception inside IssueController getSpecificData() is :"+ e.getMessage());
		}

		logger.info("Inside IssueController getSpecificData() Ends ");
		return json;
	}
}
