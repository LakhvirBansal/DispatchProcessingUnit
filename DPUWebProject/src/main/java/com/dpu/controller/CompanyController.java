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
import com.dpu.model.CompanyResponse;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CompanyService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "company")
public class CompanyController extends MessageProperties {

	Logger logger = Logger.getLogger(CompanyController.class);

	@Autowired
	CompanyService companyService;

	ObjectMapper mapper = new ObjectMapper();

	@Value("${company_unable_to_add_message}")
	private String company_unable_to_add_message;

	@Value("${company_unable_to_delete_message}")
	private String company_unable_to_delete_message;

	@Value("${company_unable_to_update_message}")
	private String company_unable_to_update_message;

	/**
	 * this method is used to get all the companies
	 * 
	 * @return all companies
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {

		logger.info("CompanyController getAll() starts");
		String json = new String();
		try {

			List<CompanyResponse> companyResponses = companyService.getAll();

			if (companyResponses != null) {
				json = mapper.writeValueAsString(companyResponses);
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception inside CompanyController getAll() :"
					+ e.getMessage());
			Failed failed = new Failed();
			failed.setMessage("Internal Server Error");
			return failed;
		}
		logger.info("CompanyController getAll() ends");
		return json;
	}

	/**
	 * this method is used to add company
	 * 
	 * @param companyResponse
	 * @return all companies data
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody CompanyResponse companyResponse) {

		logger.info("CompanyController add() starts");
		Object obj = null;
		try {
			obj = companyService.addCompanyData(companyResponse);

			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.info("Exception inside CompanyController add() :"
					+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,
					company_unable_to_add_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info("CompanyController add() Ends");
		return obj;
	}

	/**
	 * this method is used to delete the company
	 * 
	 * @param companyId
	 * @return List<Company>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long companyId) {

		logger.info(" CompanyController delete() starts ");
		Object obj = null;

		try {

			obj = companyService.delete(companyId);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside CompanyController delete() :"
					+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,
					company_unable_to_delete_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info(" CompanyController delete() ends ");
		return obj;
	}

	/**
	 * this method is used to update the record
	 * 
	 * @param id
	 * @param companyResponse
	 * @return List<Company>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id,
			@RequestBody CompanyResponse companyResponse) {

		logger.info(" CompanyController delete() starts, companyId :" + id);
		Object obj = null;
		try {
			obj = companyService.update(id, companyResponse);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside CompanyController update() :"
					+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,
					company_unable_to_update_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}
		logger.info(" CompanyController delete() ends, companyId :" + id);
		return obj;
	}

	/**
	 * this method is used to get the particular company Data
	 * 
	 * @param id
	 * @return particular company details
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("id") Long id) {

		logger.info(" CompanyController get() starts, companyId :" + id);
		String json = new String();
		try {
			CompanyResponse companyResponse = companyService.get(id);
			if (companyResponse != null) {
				json = mapper.writeValueAsString(companyResponse);
			}
		} catch (Exception e) {
			logger.info("Exception inside CompanyController get() :"
					+ e.getMessage());
		}

		logger.info(" CompanyController get() ends, companyId :" + id);
		return json;
	}

	/**
	 * this method is used when we click on add button on company screen
	 * 
	 * @return master data for company
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {

		logger.info(" Inside CompanyController openAdd() Starts ");
		String json = null;

		try {
			CompanyResponse companyResponse = companyService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(companyResponse);
		} catch (Exception e) {
			logger.error(" Exception inside CompanyController openAdd() :"
					+ e.getMessage());
		}

		logger.info(" Inside CompanyController openAdd() Ends ");
		return json;
	}
	
	/**
	 * this method is used when we click on add button on company screen
	 * 
	 * @return master data for company
	 * @author sumit
	 */
	@RequestMapping(value = "/openAddAdditionalContact", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAddAdditionalContact() {

		logger.info(" Inside CompanyController openAddAdditionalContact() Starts ");
		String json = null;

		try {
			CompanyResponse companyResponse = companyService.getOpenAddAdditionalContact();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(companyResponse);
		} catch (Exception e) {
			logger.error(" Exception inside CompanyController openAddAdditionalContact() :"
					+ e.getMessage());
		}

		logger.info(" Inside CompanyController openAddAdditionalContact() Ends ");
		return json;
	}

	/**
	 * this method is used to searchCompany based on company name
	 * 
	 * @param companyName
	 * @return List<Companies>
	 */
	@RequestMapping(value = "/{companyName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchCompany(@PathVariable("companyName") String companyName) {

		logger.info("Inside CompanyController searchCompany() Starts, companyName :"
				+ companyName);
		String json = new String();

		try {
			List<CompanyResponse> companyList = companyService
					.getCompanyByCompanyName(companyName);
			if (companyList != null && companyList.size() > 0) {
				json = mapper.writeValueAsString(companyList);
			}
		} catch (Exception e) {
			logger.error("Exception inside CompanyController searchCompany() is :"
					+ e.getMessage());
		}

		logger.info(" Inside CompanyController searchCompany() Ends, companyName :"
				+ companyName);
		return json;
	}
}
