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
import com.dpu.model.AccountModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.AccountService;

@RestController
@RequestMapping(value = "account")
public class AccountController {

	Logger logger = Logger.getLogger(AccountController.class);

	@Autowired
	AccountService accountService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${account_unable_to_add_message}")
	private String account_unable_to_add_message;

	@Value("${account_unable_to_delete_message}")
	private String account_unable_to_delete_message;
	
	@Value("${account_unable_to_update_message}")
	private String account_unable_to_update_message;

	/**
	 * this method is used to get all accounts
	 * @return List<accounts>
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {

		logger.info("Inside AccountController getAll() Starts ");
		String json = null;

		try {
			List<AccountModel> responses = accountService.getAll();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside AccountController getAll() :"+ e.getMessage());
		}
		
		logger.info("Inside AccountController getAll() Ends ");
		return json;
	}

	/**
	 * this method is used to add the account
	 * @param accountModel
	 * @return List<accounts>
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody AccountModel accountModel) {

		logger.info("Inside AccountController add() starts ");
		Object obj = null;
		
		try {

			Object result = accountService.addAccount(accountModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside AccountController add() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,account_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside AccountController add() ends ");
		return obj;
	}

	/**
	 * this method is used to delete the account based on accountId
	 * @param id
	 * @return List<accounts>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long accountId) {

		logger.info("Inside AccountController delete() Starts, accountId is :" + accountId);
		Object obj = null;

		try {
			Object result = accountService.delete(accountId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside AccountController delete() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,account_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside AccountController delete() Ends, accountId is :" + accountId);
		return obj;

	}

	/**
	 * this method is used to update the account based on accountId
	 * @param accountId
	 * @param accountModel
	 * @return List<accounts>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long accountId, @RequestBody AccountModel accountModel) {

		logger.info("Inside AccountController update() Starts, accountId is :" + accountId);
		Object obj = null;
		try {
			Object result = accountService.update(accountId, accountModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside AccountController update() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,account_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside AccountController update() Ends, accountId is :" + accountId);
		return obj;
	}

	/**
	 * this method is used to get account data based on accountId
	 * @param accountId
	 * @return taxCodeData
	 */
	@RequestMapping(value = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAccountById(@PathVariable("accountId") Long accountId) {
		
		logger.info("Inside AccountController getTaxcodeById() Starts, accountId:"+ accountId);
		String json = null;

		try {

			AccountModel accountModel = accountService.get(accountId);

			if (accountModel != null) {
				json = mapper.writeValueAsString(accountModel);
			}
		} catch (Exception e) {
			logger.error("Exception inside AccountController getTaxcodeById() :"+ e.getMessage());
		}

		logger.info("Inside AccountController getTaxcodeById() Ends, accountId:"+ accountId);
		return json;
	}

	/**
	 * this method is used when we click on add button on account screen
	 * send master data
	 * @return master data for add account
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info("Inside AccountController openAdd() Starts ");
		String json = null;

		try {
			AccountModel model = accountService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(model);
		} catch (Exception e) {
			logger.error(" Exception inside AccountController openAdd() :"+ e.getMessage());
		}

		logger.info("Inside AccountController openAdd() ends ");
		return json;
	}

	/**
	 * this method is used to get account data based on account name
	 * @param accountName
	 * @return List<account>
	 */
	@RequestMapping(value = "/{accountName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchAccount(@PathVariable("accountName") String accountName) {

		logger.info("Inside AccountController searchAccount() Starts, accountName :"+ accountName);
		String json = new String();

		try {
			List<AccountModel> accountList = accountService.getAccountByAccountName(accountName);
			if (accountList != null && accountList.size() > 0) {
				json = mapper.writeValueAsString(accountList);
			}
		} catch (Exception e) {
			logger.error("Exception inside AccountController searchAccount() is :"+ e.getMessage());
		}

		logger.info(" Inside AccountController searchAccount() Ends, accountName :"+ accountName);
		return json;
	}

	/**
	 * this method is used to get specific account data (id and name)
	 * @return account Id and name
	 */
	
	@RequestMapping(value = "/specificData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getSpecificData() {

		logger.info("Inside AccountController getSpecificData() Starts ");
		String json = new String();

		try {
			List<AccountModel> accountsList = accountService.getSpecificData();
			if (accountsList != null && accountsList.size() > 0) {
				json = mapper.writeValueAsString(accountsList);
			}
		} catch (Exception e) {
			logger.error("Exception inside AccountController getSpecificData() is :"+ e.getMessage());
		}

		logger.info("Inside AccountController getSpecificData() Ends ");
		return json;
	}
}
