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
import com.dpu.model.VendorModel;
import com.dpu.service.VendorService;

@RestController
@RequestMapping(value = "vendor")
public class VendorController{

	Logger logger = Logger.getLogger(VendorController.class);
	
	@Autowired
	VendorService vendorService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${vendor_unable_to_add_message}")
	private String vendor_unable_to_add_message;

	@Value("${vendor_unable_to_delete_message}")
	private String vendor_unable_to_delete_message;
	
	@Value("${vendor_unable_to_update_message}")
	private String vendor_unable_to_update_message;
	
	/**
	 * this method is used to return all vendors data
	 * @return List<Vendors>
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		
		logger.info("VendorController getAll() starts");
		String json = new String();
		
		try {
			
			List<VendorModel> companyResponses = vendorService.getAll();
			
			if(companyResponses != null) {
				json = mapper.writeValueAsString(companyResponses);
			}
			
		} catch (Exception e) {
			logger.info("Exception inside VendorController getAll() :"+e.getMessage());
		}
		
		logger.info("VendorController getAll() ends");
		return json;
	}


	/**
	 * this method is used to add particular vendor
	 * @param vendorModel
	 * @return all vendors List
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody VendorModel vendorModel) {
		
		logger.info("VendorController add() starts");
		Object obj = null;
		try {
			obj = vendorService.addVendorData(vendorModel);
			
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			logger.info("Exception inside VendorController add() :"+e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,vendor_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("VendorController add() Ends");
		return obj;
	}

	/**
	 * this method is used to delete particular vendor
	 * @param vendorId
	 * @return List<vendor>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long vendorId) {

		logger.info(" VendorController delete() starts, vendorId :"+vendorId);
		Object obj = null;

		try {
			
			obj = vendorService.delete(vendorId);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside VendorController delete() :"+e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,vendor_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info(" VendorController delete() ends, vendorId :"+vendorId);
		return obj;
	}

	/**
	 * this method is used to update the vendor Record
	 * @param id
	 * @param vendorModel
	 * @return List<Vendor>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id, @RequestBody VendorModel vendorModel) {
		
		logger.info(" VendorController update() starts, vendorId :"+id);
		Object obj = null;
		try {
			obj = vendorService.update(id, vendorModel);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside VendorController update() :"+e.getMessage());
			e.printStackTrace();
			obj = new ResponseEntity<Object>(new Failed(0,vendor_unable_to_update_message, Iconstants.ERROR),HttpStatus.BAD_REQUEST);
		}
		logger.info(" VendorController update() ends, vendorId :"+id);
		return obj;
	}

	/**
	 * this method is used to get the particular vendor Data
	 * @param id
	 * @return particular vendor details
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("id") Long id) {
		
		logger.info(" VendorController get() starts, vendorId :"+id);
		String json = new String();
		try {
			VendorModel vendorResponse = vendorService.get(id);
			if(vendorResponse != null) {
				json = mapper.writeValueAsString(vendorResponse);
			}
		} catch (Exception e) {
			logger.info("Exception inside VendorController get() :"+e.getMessage());
		}

		logger.info(" VendorController get() ends, vendorId :"+id);
		return json;
	}
	
	/**
	 * this method is used when we click on add button on vendor screen
	 * @return master data for vendor
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info(" Inside VendorController openAdd() Starts ");
		String json = null;
		
		try {
			VendorModel vendorModel = vendorService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(vendorModel);
		} catch (Exception e) {
			logger.error(" Exception inside VendorController openAdd() :"+e.getMessage());
		}
		
		logger.info(" Inside VendorController openAdd() Ends ");
		return json;
	}
	
	/**
	 * this method is used to searchVendor based on vendor name
	 * @param vendorName
	 * @return List<vendor>
	 */
	@RequestMapping(value = "/{vendorName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchVendor(@PathVariable("vendorName") String vendorName) {
		
		logger.info("Inside VendorController searchVendor() Starts, vendorName :"+vendorName);
		String json = new String();
		
		try {
			List<VendorModel> vendorList = vendorService.getVendorByVendorName(vendorName);
			if(vendorList != null && vendorList.size() > 0) {
				json = mapper.writeValueAsString(vendorList);
			}
		} catch (Exception e) {
			logger.error("Exception inside VendorController searchVendor() is :" + e.getMessage());
		}
		
		logger.info(" Inside VendorController searchVendor() Ends, vendorName :"+vendorName);
		return json;
	}
	
	/**
	 * this method is used to get the additional contacts of particular vendor
	 * @param id
	 * @return additional contacts of particular vendor
	 */
	@RequestMapping(value = "/{id}/additionalContacts", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getVendorAdditionalContacts(@PathVariable("id") Long id) {
		
		logger.info(" VendorController getVendorAdditionalContacts() starts, vendorId :"+id);
		String json = new String();
		try {
			VendorModel vendorResponse = vendorService.getVendorContacts(id);
			if(vendorResponse != null) {
				json = mapper.writeValueAsString(vendorResponse);
			}
		} catch (Exception e) {
			logger.info("Exception inside VendorController getVendorAdditionalContacts() :"+e.getMessage());
		}

		logger.info(" VendorController getVendorAdditionalContacts() ends, vendorId :"+id);
		return json;
	}
	
	/**
	 * this method is used to delete additionalContact based on vendorId and additionalContactId
	 * @param vendorId
	 * @param additionalContactId
	 * @return Info regarding deletion
	 */
	@RequestMapping(value = "/{vendorid}/additionalContacts/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object deleteAdditionalContact(@PathVariable("vendorid") Long vendorId,@PathVariable("id") Long additionalContactId) {
		
		logger.info("VendorController deleteAdditionalContact() starts,vendorId :"+vendorId+", additionalContactId :"+additionalContactId);
		Object obj = null;
	
		try {
			
			obj = vendorService.deleteAdditionalContact(vendorId, additionalContactId);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside VendorController deleteAdditionalContact() :"+e.getMessage());
		}
		
		logger.info("VendorController deleteAdditionalContact() ends,vendorId :"+vendorId+", additionalContactId :"+additionalContactId);
		return obj;
	}
	
	/**
	 * this method is used to delete billingLocation based on vendorId and billingLocationId
	 * @param vendorId
	 * @param billingLocationId
	 * @return Info regarding deletion
	 */
	@RequestMapping(value = "/{vendorid}/billingLocation/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object deleteBillingLocation(@PathVariable("vendorid") Long vendorId,@PathVariable("id") Long billingLocationId) {
		
		logger.info("VendorController deleteBillingLocation() starts,vendorId :"+vendorId+", billingLocationId :"+billingLocationId);
		Object obj = null;
	
		try {
			
			obj = vendorService.deleteBillingLocation(vendorId, billingLocationId);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside CompanyController get() :"+e.getMessage());
		}
		
		logger.info("VendorController deleteBillingLocation() ends,vendorId :"+vendorId+", billingLocationId :"+billingLocationId);
		return obj;
	}
}
