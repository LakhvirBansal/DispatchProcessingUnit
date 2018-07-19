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
import com.dpu.model.OrderModel;
import com.dpu.model.ProbilModel;
import com.dpu.model.Success;
import com.dpu.service.CategoryService;
import com.dpu.service.OrderService;
import com.dpu.util.MessageProperties;


@RestController
@RequestMapping(value = "order")
public class OrderController extends MessageProperties {

	Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	OrderService orderService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${order_unable_to_add_message}")
	private String order_unable_to_add_message;

	@Value("${order_unable_to_delete_message}")
	private String order_unable_to_delete_message;
	
	@Value("${order_unable_to_update_message}")
	private String order_unable_to_update_message;
	
	@Value("${probil_unable_to_delete_message}")
	private String probil_unable_to_delete_message;

	/**
	 * this method is used to get all Orders data
	 * @return List<Orders>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		
		logger.info("Inside OrderController getAll() Starts ");
		String json = null;
		
		try {
			List<OrderModel> responses = orderService.getAllOrders();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside OrderController getAll()"+e.getMessage());
		}
		logger.info("Inside OrderController getAll() Ends");
		return json;
	}

	/**
	 * this method is used to add the Order
	 * @param orderModel
	 * @return List<probils>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody OrderModel orderModel) {

		logger.info("Inside OrderController add() starts ");
		Object obj = null;
		try {

			Object response = orderService.addOrder(orderModel);
			if (response instanceof Success) {
				obj = new ResponseEntity<Object>(response, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside OrderController add() :"+e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,order_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside OrderController add() Ends");
		return obj;
	}

	/**
	 * this method is used to delete the particular order
	 * @param id
	 * @return List<Order>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object deleteOrder(@PathVariable("id") Long orderId) {
		
		logger.info("Inside OrderController deleteOrder() Starts, orderId is :" + orderId);
		Object obj = null;
		
		try {
			Object result = orderService.deleteOrder(orderId);
			
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside OrderController deleteOrder() :"+e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,order_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside OrderController deleteOrder() Ends, orderId is :" + orderId);
		return obj;

	}

	/**
	 * this method is used to delete probil by probilId
	 * @param orderId
	 * @param probilId
	 * @return info regarding delete
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{orderId}/probil/{probilId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object deleteProbilByProbilId(@PathVariable("orderId") Long orderId, @PathVariable("probilId") Long probilId) {
		
		logger.info("Inside OrderController deleteProbilByProbilId() Starts, orderId is :" + orderId+": probilId :"+probilId);
		Object obj = null;
		
		try {
			Object result = orderService.deleteProbil(orderId, probilId);
			
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside OrderController deleteOrder() :"+e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,probil_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside OrderController deleteProbilByProbilId() Starts, orderId is :" + orderId+": probilId :"+probilId);
		return obj;

	}
	
	/**
	 * this method is used to update the orderData
	 * @param id
	 * @param orderModel
	 * @return List<OrderModel>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long orderId, @RequestBody OrderModel orderModel) {
		
		logger.info("Inside OrderController update() Starts, orderId is :" + orderId);
		Object obj = null;
		
		try {
			Object response = orderService.update(orderId, orderModel);
			if (response instanceof Success) {
				obj = new ResponseEntity<Object>(response, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside OrderController update() :"+e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,order_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside OrderController update() ends, orderId is :" + orderId);
		return obj;
	}

	/**
	 * this method is used to get particular order based on orderId
	 * @param orderId
	 * @return orderData
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getParticularOrder(@PathVariable("orderId") Long orderId) {
		
		logger.info("Inside OrderController getParticularOrder() Starts, orderId:" + orderId);
		String json = null;
		
		try {
			
			OrderModel orderModel = orderService.getOrderByOrderId(orderId);

			if (orderModel != null) {
				json = mapper.writeValueAsString(orderModel);
			}
		} catch (Exception e) {
			logger.error("Exception inside OrderController getParticularOrder() :" + e.getMessage());
		}
		
		logger.info("Inside OrderController getParticularOrder() Ends, orderId:" + orderId);
		return json;
	}

	/**
	 * this method is used when we click on add button on Order screen to send master data
	 * @return master data for add Order
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info(" Inside OrderController openAdd() Starts ");
		String json = null;
		
		try {
			OrderModel orderModel = orderService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(orderModel);
		} catch (Exception e) {
			logger.error(" Exception inside OrderController openAdd() :"+e.getMessage());
		}
		
		logger.info(" Inside OrderController openAdd() Ends ");
		return json;
	}
	
	/**
	 * this method is used to get the specific company billingLocations and contacts
	 * @param companyId
	 * @return company related billingaccounts and contacts
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{companyId}/getData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getCompanyData(@PathVariable("companyId") Long companyId) {
		
		logger.info(" Inside OrderController getCompanyData() Starts ");
		String json = null;
		
		try {
			CompanyResponse companyResponse = orderService.getCompanyData(companyId);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(companyResponse);
		} catch (Exception e) {
			logger.error(" Exception inside OrderController getCompanyData() :"+e.getMessage());
		}
		
		logger.info(" Inside OrderController getCompanyData() Ends ");
		return json;
	}
	
	/**
	 * this method is used to get orders based on company name
	 * @param companyName
	 * @return List<Order>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{companyName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchOrder(@PathVariable("companyName") String companyName) {
		
		logger.info("Inside OrderController searchOrder() Starts, companyName :"+companyName);
		String json = new String();
		
		try {
			List<OrderModel> orderList = orderService.getOrdersByCompanyName(companyName);
			if(orderList != null && orderList.size() > 0) {
				json = mapper.writeValueAsString(orderList);
			}
		} catch (Exception e) {
			logger.error("Exception inside OrderController searchOrder() is :" + e.getMessage());
		}
		
		logger.info(" Inside OrderController searchOrder() Ends, companyName :"+companyName);
		return json;
	}
	
	/**
	 * this method is used to get the probil data based on probilId
	 * @param orderId
	 * @param probilId
	 * @return probilData
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{orderId}/probil/{probilId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getProbilByProbilId(@PathVariable("orderId") Long orderId, @PathVariable("probilId") Long probilId) {
		
		logger.info("Inside OrderController getProbilByProbilId() Starts, orderId:" + orderId+", probilId"+probilId);
		String json = null;
		
		try {
			
			ProbilModel probilModel = orderService.getProbilByProbilId(orderId, probilId);

			if (probilModel != null) {
				json = mapper.writeValueAsString(probilModel);
			}
		} catch (Exception e) {
			logger.error("Exception inside OrderController getProbilByProbilId() :" + e.getMessage());
		}
		
		logger.info("Inside OrderController getProbilByProbilId() ends, orderId:" + orderId+", probilId"+probilId);
		return json;
	}
	
}
