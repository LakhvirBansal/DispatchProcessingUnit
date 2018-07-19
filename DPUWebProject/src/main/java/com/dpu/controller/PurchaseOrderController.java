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
import com.dpu.model.PurchaseOrderModel;
import com.dpu.model.Success;
import com.dpu.service.IssueService;
import com.dpu.service.PurchaseOrderService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "po")
public class PurchaseOrderController extends MessageProperties {

	Logger logger = Logger.getLogger(PurchaseOrderController.class);

	@Autowired
	PurchaseOrderService poService;

	@Autowired
	IssueService issueService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${po_unable_to_add_message}")
	private String po_unable_to_add_message;

	@Value("${po_unable_to_delete_message}")
	private String po_unable_to_delete_message;
	
	@Value("${po_unable_to_update_message}")
	private String po_unable_to_update_message;

	@Value("${po_status_unable_to_update}")
	private String po_status_unable_to_update;
	/**
	 * this method is used when we click on add PO button
	 * @return openAdd data
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info("Inside PurchaseOrderController openAdd() Starts ");
		String json = null;

		try {
			PurchaseOrderModel model = poService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(model);
		} catch (Exception e) {
			logger.error(" Exception inside PurchaseOrderController openAdd() :"+ e.getMessage());
		}

		logger.info("Inside PurchaseOrderController openAdd() ends ");
		return json;
	}
	
	/**
	 * this method is used to get the issues based on category and unitType(Truck, trailer)
	 * @param categoryId
	 * @param unitTypeId
	 * @return issues data
	 */
	@RequestMapping(value = "/category/{categoryId}/unitType/{unitTypeId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getCategoryAndUnitTypeIssues(@PathVariable("categoryId") Long categoryId, @PathVariable("unitTypeId") Long unitTypeId) {
		
		logger.info("Inside PurchaseOrderController getCategoryAndUnitTypeIssues() Starts ");
		String json = null;

		try {
			List<IssueModel> issueList = poService.getCategoryAndUnitTypeIssues(categoryId, unitTypeId);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(issueList);
		} catch (Exception e) {
			logger.error(" Exception inside PurchaseOrderController getCategoryAndUnitTypeIssues() :"+ e.getMessage());
		}

		logger.info("Inside PurchaseOrderController getCategoryAndUnitTypeIssues() ends ");
		return json;
	}
	
	@RequestMapping(value = "/unitType/{unitTypeId}/unitNo/{unitNo}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getIssuesBasedOnUnitTypeAndNo(@PathVariable("unitTypeId") Long unitTypeId,
			@PathVariable("unitNo") Long unitNo) {

		logger.info("Inside PurchaseOrderController getIssuesBasedOnUnitTypeAndNo() Starts ");
		String json = null;

		try {
			List<IssueModel> issueList = poService.getUnitNoIssues(unitTypeId, unitNo);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(issueList);
		} catch (Exception e) {
			logger.error(" Exception inside PurchaseOrderController getCategoryAndUnitTypeIssues() :" + e.getMessage());
		}

		logger.info("Inside PurchaseOrderController getCategoryAndUnitTypeIssues() ends ");
		return json;
	}

	@RequestMapping(value = "/unitType/{unitTypeId}/issues", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object getIssuesBasedOnUnitTypeAndUnitNos(@PathVariable("unitTypeId") Long unitTypeId,
			@RequestBody PurchaseOrderModel poModel) {

		logger.info("Inside PurchaseOrderController getIssuesBasedOnUnitTypeAndUnitNos() Starts ");
		String json = null;

		try {
			List<IssueModel> issueList = poService.getUnitNoIssues(unitTypeId, poModel);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(issueList);
		} catch (Exception e) {
			logger.error(" Exception inside PurchaseOrderController getIssuesBasedOnUnitTypeAndUnitNos() :"
					+ e.getMessage());
		}

		logger.info("Inside PurchaseOrderController getIssuesBasedOnUnitTypeAndUnitNos() ends ");
		return json;
	}
	/**
	 * this method is used to add the PO.
	 * @param poModel
	 * @return List<PO>
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody PurchaseOrderModel poModel) {

		logger.info("Inside PurchaseOrderController add() starts ");
		Object obj = null;
		
		try {

			Object result = poService.addPO(poModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController add() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, po_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside PurchaseOrderController add() ends ");
		return obj;
	}
	
	/**
	 * this method is used to get all PO
	 * @return all PO data
	 */
	@RequestMapping(value = "/status/{statusValue}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll(@PathVariable("statusValue") String statusVal) {

		logger.info("Inside PurchaseOrderController getAll() Starts ");
		String json = null;

		try {
			List<PurchaseOrderModel> responses = poService.getStatusPOs(statusVal);
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController getAll() :"+ e.getMessage());
		}
		
		logger.info("Inside PurchaseOrderController getAll() Ends ");
		return json;
	}

	/**
	 * this method is used to get the particular PO data
	 * @param purchaseOrderId
	 * @return get particular PO data
	 */
	@RequestMapping(value = "/{purchaseOrderId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getPurchaseOrderById(@PathVariable("purchaseOrderId") Long purchaseOrderId) {
		
		logger.info("Inside PurchaseOrderController getPurchaseOrderById() Starts, issueId:"+ purchaseOrderId);
		String json = null;

		try {

			PurchaseOrderModel poModel = poService.get(purchaseOrderId);

			if (poModel != null) {
				json = mapper.writeValueAsString(poModel);
			}
		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController getPurchaseOrderById() :"+ e.getMessage());
		}

		logger.info("Inside PurchaseOrderController getPurchaseOrderById() Ends, issueId:"+ purchaseOrderId);
		return json;
	}

	@RequestMapping(value = "/{poId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("poId") Long poId, @RequestBody PurchaseOrderModel poModel) {

		logger.info("Inside PurchaseOrderController update() Starts, poId is :" + poId);
		Object obj = null;
		try {
			Object result = poService.update(poId, poModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController update() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, po_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside PurchaseOrderController update() Ends, issueId is :" + poId);
		return obj;
	}
	
	@RequestMapping(value = "/{poId}/getInvoice", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getInvoice(@PathVariable("poId") Long poId) {

		logger.info("Inside PurchaseOrderController update() Starts, poId is :" + poId);
		Object obj = null;
		try {
			Object result = poService.getInvoiceData(poId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController update() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, po_unable_to_update_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside PurchaseOrderController update() Ends, issueId is :" + poId);
		return obj;
	}

	@RequestMapping(value = "/{poId}/updateinvoice", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object updateInvoice(@PathVariable("poId") Long poId, @RequestBody PurchaseOrderModel poModel) {

		logger.info("Inside PurchaseOrderController update() Starts, poId is :" + poId);
		Object obj = null;
		try {
			Object result = poService.updateInvoice(poId, poModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController update() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, po_unable_to_update_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside PurchaseOrderController update() Ends, issueId is :" + poId);
		return obj;
	}

	@RequestMapping(value = "/{poId}/deleteinvoice", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object deleteInvoice(@PathVariable("poId") Long poId) {

		logger.info("Inside PurchaseOrderController deleteInvoice() Starts, poId is :" + poId);
		Object obj = null;
		try {
			Object result = poService.deleteInvoice(poId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController update() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, po_unable_to_update_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside PurchaseOrderController update() Ends, issueId is :" + poId);
		return obj;
	}

	@RequestMapping(value = "/{poId}/status/{statusId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object updateStatus(@PathVariable("poId") Long poId, @PathVariable("statusId") Long statusId, @RequestBody PurchaseOrderModel poModel) {

		logger.info("Inside PurchaseOrderController updateStatus() Starts, poId is :" + poId +": statusId :"+statusId);
		Object obj = null;
		try {
			Object result = poService.updateStatus(poId, statusId, poModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController updateStatus() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, po_status_unable_to_update, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside PurchaseOrderController updateStatus() Ends, issueId is :" + poId+": statusId :"+statusId);
		return obj;
	}
	/**
	 * this method is used to delete the PO
	 * @param poId
	 * @return List<PO>
	 */
	@RequestMapping(value = "/{poId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("poId") Long poId) {

		logger.info("Inside PurchaseOrderController delete() Starts, poId is :" + poId);
		Object obj = null;

		try {
			Object result = poService.delete(poId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController delete() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, po_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		logger.info("Inside PurchaseOrderController delete() Ends, poId is :" + poId);
		return obj;

	}

	@RequestMapping(value = "/{poNo}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchPOByPoNo(@PathVariable("poNo") Long poNo) {

		logger.info("Inside PurchaseOrderController searchIssue() Starts, issueName :"+ poNo);
		String json = new String();

		try {
			List<PurchaseOrderModel> poList = poService.getPoByPoNo(poNo);
			if (poList != null && poList.size() > 0) {
				json = mapper.writeValueAsString(poList);
			}
		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController searchIssue() is :"+ e.getMessage());
		}

		logger.info(" Inside PurchaseOrderController searchIssue() Ends, issueName :"+ poNo);
		return json;
	}

	
	@RequestMapping(value = "/specificData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getSpecificData() {

		logger.info("Inside PurchaseOrderController getSpecificData() Starts ");
		String json = new String();

		try {
			/*List<IssueModel> issueList = issueService.getSpecificData();
			if (issueList != null && issueList.size() > 0) {
				json = mapper.writeValueAsString(issueList);
			}*/
		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController getSpecificData() is :"+ e.getMessage());
		}

		logger.info("Inside PurchaseOrderController getSpecificData() Ends ");
		return json;
	}

	/**
	 * this method is used when we click on add button on issue screen send master data
	 * 
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


}
