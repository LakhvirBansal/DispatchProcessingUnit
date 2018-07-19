package com.dpu.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dpu.constants.Iconstants;
import com.dpu.model.CategoryModel;
import com.dpu.model.Failed;
import com.dpu.model.IssueModel;
import com.dpu.model.PurchaseOrderModel;
import com.dpu.model.TypeResponse;
import com.dpu.service.CategoryService;
import com.dpu.service.PurchaseOrderService;
import com.dpu.util.DateUtil;
import com.dpu.util.ValidationUtil;

@Controller
public class WebPOController {

	@Autowired
	PurchaseOrderService purchaseOrderService;
	
	@Autowired
	CategoryService categoryService;

	Logger logger = Logger.getLogger(WebPOController.class);

	@RequestMapping(value = "/showpo/status/Complete", method = RequestMethod.GET)
	@ResponseBody
	public Object showCompletePOs() {
		List<PurchaseOrderModel> lstPOs = purchaseOrderService.getStatusPOs("Complete");
		return lstPOs;
	}

	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	@RequestMapping(value = "/showpo/status/Active", method = RequestMethod.GET)
	@ResponseBody
	public Object showActivePOs() {
		List<PurchaseOrderModel> lstPOs = purchaseOrderService.getStatusPOs("Active");
		return lstPOs;
	}

	@RequestMapping(value = "/showpo/status/Invoiced", method = RequestMethod.GET)
	@ResponseBody
	public Object showInvoicedPOs() {
		List<PurchaseOrderModel> lstPOs = purchaseOrderService.getStatusPOs("Invoiced");
		return lstPOs;
	}

	@RequestMapping(value = "/showpo", method = RequestMethod.GET)
	public ModelAndView showPOScreenByStatus() {
		ModelAndView modelAndView = new ModelAndView();
		List<PurchaseOrderModel> lstPOs = purchaseOrderService.getStatusPOs("Active");
		modelAndView.addObject("LIST_PO", lstPOs);
		modelAndView.setViewName("po");
		return modelAndView;
	}

	@RequestMapping(value = "/{poId}/complete/{statusId}", method = RequestMethod.GET)
	@ResponseBody
	public Object changeToCompleteStatus(@PathVariable("poId") Long poId, @PathVariable("statusId") Long statusId) {
		PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
		purchaseOrderModel.setCurrentStatusVal("Active");
		Object response = purchaseOrderService.updateStatus(poId, statusId, purchaseOrderModel);
		if (response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/{poId}/complete/{statusId}/invoiced", method = RequestMethod.GET)
	@ResponseBody
	public Object changeToInvoicedStatus(@PathVariable("poId") Long poId, @PathVariable("statusId") Long statusId,
			HttpServletRequest httpServletRequest) {
		PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
		String invoiceDate = httpServletRequest.getParameter("invoiceDate");
		String invoiceNo = httpServletRequest.getParameter("invoiceNo");
		String invoiceAmount = httpServletRequest.getParameter("invoiceAmount");
		purchaseOrderModel.setInvoiceNo(invoiceNo);
		purchaseOrderModel.setAmount(Double.parseDouble(invoiceAmount));
		purchaseOrderModel.setCurrentStatusVal("Complete");
		invoiceDate = DateUtil.rearrangeDate(invoiceDate);
		purchaseOrderModel.setInvoiceDate(invoiceDate);
		Object response = purchaseOrderService.updateStatus(poId, statusId, purchaseOrderModel);
		if (response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/po/getopenadd", method = RequestMethod.GET)
	@ResponseBody
	public PurchaseOrderModel getOpenAdd() {
		PurchaseOrderModel purchaseOrderModel = null;
		try {
			purchaseOrderModel = purchaseOrderService.getOpenAdd();

			List<TypeResponse> issueList = purchaseOrderModel.getIssueStatusList();
			List<TypeResponse> trimmedIssueStatusList = new ArrayList<TypeResponse>();
			for (TypeResponse type : issueList) {
				if (type.getTypeName().equals("Assigned") || type.getTypeName().equals("Complete")
						|| type.getTypeName().equals("Incomplete")) {
					trimmedIssueStatusList.add(type);
				}
			}

			purchaseOrderModel.setIssueStatusList(trimmedIssueStatusList);

		} catch (Exception e) {
			System.out.println(e);
		}
		return purchaseOrderModel;
	}

	@RequestMapping(value = "/po/getissues/category/{category}/unittype/{unittype}", method = RequestMethod.GET)
	@ResponseBody
	public List<IssueModel> getCategoryAndUnitTypeIssues(@PathVariable("category") Long categoryId,
			@PathVariable("unittype") Long unitTypeId) {
		List<IssueModel> issueModelList = null;
		try {
			issueModelList = purchaseOrderService.getCategoryAndUnitTypeIssues(categoryId, unitTypeId);
		} catch (Exception e) {
			System.out.println(e);
		}
		return issueModelList;
	}

	@RequestMapping(value = "/savepo", method = RequestMethod.POST)
	@ResponseBody
	public Object savePO(@ModelAttribute PurchaseOrderModel purchaseOrderModel, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				List<String> issueIds = purchaseOrderModel.getIssueIds();
				List<String> issueStatusIds = purchaseOrderModel.getIssueStatusIds();

				List<IssueModel> issueList = new ArrayList<IssueModel>();
				if(issueIds != null && issueIds.size() > 0) {
					
					for (int i = 0; i < issueIds.size(); i++) {
	
						IssueModel issueModel = new IssueModel();
						issueModel.setId(Long.parseLong(issueIds.get(i)));
						issueModel.setStatusId(Long.parseLong(issueStatusIds.get(i)));
						issueList.add(issueModel);
					}
				}
				purchaseOrderModel.setIssues(issueList);

				Object response = purchaseOrderService.addPO(purchaseOrderModel);
				if (response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE),
				HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getpo/poId", method = RequestMethod.GET)
	@ResponseBody
	public PurchaseOrderModel getPO(@RequestParam("poId") Long poId) {
		PurchaseOrderModel purchaseOrderModel = null;
		try {
			purchaseOrderModel = purchaseOrderService.get(poId);
			if(!ValidationUtil.isNull(purchaseOrderModel.getCategoryId()) && !ValidationUtil.isNull(purchaseOrderModel.getUnitTypeId())) {
				List<IssueModel> issueModelList = purchaseOrderService.getCategoryAndUnitTypeIssues(
						purchaseOrderModel.getCategoryId(), purchaseOrderModel.getUnitTypeId());
				if (purchaseOrderModel.getIssueList() != null && purchaseOrderModel.getIssueList().size() > 0) {
					purchaseOrderModel.getIssueList().addAll(issueModelList);
				} else {
					purchaseOrderModel.setIssueList(issueModelList);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.info("Exception in getCategory is: " + e);
		}
		return purchaseOrderModel;
	}
	
	@RequestMapping(value = "/getinvoice/poId", method = RequestMethod.GET)
	@ResponseBody
	public PurchaseOrderModel getInvoice(@RequestParam("poId") Long poId) {
		PurchaseOrderModel purchaseOrderModel = null;
		try {

			purchaseOrderModel = (PurchaseOrderModel) purchaseOrderService.getInvoiceData(poId);
		} catch (Exception e) {
			System.out.println(e);
			logger.info("Exception in getCategory is: " + e);
		}
		return purchaseOrderModel;
	}

	@RequestMapping(value = "/updatepo", method = RequestMethod.POST)
	@ResponseBody
	public Object updatePO(@ModelAttribute PurchaseOrderModel purchaseOrderModel, @RequestParam("poid") Long poId,
			HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				List<String> issueIds = purchaseOrderModel.getIssueIds();
				List<String> issueStatusIds = purchaseOrderModel.getIssueStatusIds();

				List<IssueModel> issueList = new ArrayList<IssueModel>();
				for (int i = 0; i < issueIds.size(); i++) {

					IssueModel issueModel = new IssueModel();
					issueModel.setId(Long.parseLong(issueIds.get(i)));
					issueModel.setStatusId(Long.parseLong(issueStatusIds.get(i)));
					issueList.add(issueModel);
				}
				purchaseOrderModel.setIssues(issueList);
				Object response = purchaseOrderService.update(poId, purchaseOrderModel);
				if (response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE),
				HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/deletepo/{poid}", method = RequestMethod.GET)
	@ResponseBody
	public Object deletePO(@PathVariable("poid") Long poId) {
		Object response = purchaseOrderService.delete(poId);
		if (response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/getcategories/unittype/{unittype}", method = RequestMethod.GET)
	@ResponseBody
	public List<CategoryModel> getCategories(@PathVariable("unittype") String unitType) {
		List<CategoryModel> categories = categoryService.getCategoriesBasedOnType(unitType);
		return categories;
	}
	
	/*@RequestMapping(value = "/getissuesbasedonunitnoandunittype/unittypeid/{unittypeid}/unitno/{unitno}", method = RequestMethod.GET)
	@ResponseBody
	public List<IssueModel> getIssuesByUnitNo(@PathVariable("unittypeid") String unitTypeId, @PathVariable("unitno") String unitNo) {
		Long unitTId = Long.parseLong(unitTypeId);
		Long unitN = Long.parseLong(unitNo);
		
		List<IssueModel> issues = purchaseOrderService.getUnitNoIssues(unitTId, unitN);
		return issues;
	}*/
	
	@RequestMapping(value = "/getissuesbasedonunitnoandunittype/unittypeid/{unittypeid}", method = RequestMethod.POST)
	@ResponseBody
	public List<IssueModel> getIssuesByUnitNoAndUnitTypeId(@PathVariable("unittypeid") String unitTypeId, @ModelAttribute PurchaseOrderModel purchaseOrderModel) {
		Long unitTId = Long.parseLong(unitTypeId);
		
		List<IssueModel> issues = purchaseOrderService.getUnitNoIssues(unitTId, purchaseOrderModel);
		return issues;
	}
	
	@RequestMapping(value = "/updateinvoice", method = RequestMethod.POST)
	@ResponseBody
	public Object updateInvoice(PurchaseOrderModel purchaseOrderModel, @RequestParam("poid") Long poId,
			HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				if(purchaseOrderModel.getInvoiceDate() != null) {
					String invoiceDt = DateUtil.rearrangeDate(purchaseOrderModel.getInvoiceDate());
					purchaseOrderModel.setInvoiceDate(invoiceDt);
				}
				Object response = purchaseOrderService.updateInvoice(poId, purchaseOrderModel);
				if (response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE),
				HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/deleteinvoice/{poid}" , method = RequestMethod.GET)
	@ResponseBody public Object deleteInvoice(@PathVariable("poid") Long poId) {
		Object response = purchaseOrderService.deleteInvoice(poId);
		if(response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
}
