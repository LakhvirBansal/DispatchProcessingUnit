package com.dpu.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dpu.constants.Iconstants;
import com.dpu.dao.PurchaseOrderDao;
import com.dpu.entity.Category;
import com.dpu.entity.Issue;
import com.dpu.entity.PurchaseOrder;
import com.dpu.entity.PurchaseOrderInvoice;
import com.dpu.entity.PurchaseOrderIssue;
import com.dpu.entity.PurchaseOrderUnitNos;
import com.dpu.entity.Type;
import com.dpu.entity.Vendor;
import com.dpu.model.CategoryModel;
import com.dpu.model.Failed;
import com.dpu.model.IssueModel;
import com.dpu.model.PurchaseOrderModel;
import com.dpu.model.Success;
import com.dpu.model.TypeResponse;
import com.dpu.model.VendorModel;
import com.dpu.service.CategoryService;
import com.dpu.service.IssueService;
import com.dpu.service.PurchaseOrderService;
import com.dpu.service.TypeService;
import com.dpu.service.VendorService;
import com.dpu.util.DateUtil;
import com.dpu.util.ValidationUtil;

@Component
public class PurchaseOrderServiceImpl implements PurchaseOrderService  {

	Logger logger = Logger.getLogger(PurchaseOrderServiceImpl.class);
	
	@Autowired
	IssueService issueService;

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	TypeService typeService;
	
	@Autowired
	PurchaseOrderDao poDao;

	@Value("${po_added_message}")
	private String po_added_message;

	@Value("${po_unable_to_add_message}")
	private String po_unable_to_add_message;

	@Value("${po_deleted_message}")
	private String po_deleted_message;

	@Value("${po_unable_to_delete_message}")
	private String po_unable_to_delete_message;

	@Value("${po_updated_message}")
	private String po_updated_message;

	@Value("${po_unable_to_update_message}")
	private String po_unable_to_update_message;

	@Value("${po_already_used_message}")
	private String po_already_used_message;
	
	@Value("${po_status_update}")
	private String po_status_update;

	@Value("${po_status_unable_to_update}")
	private String po_status_unable_to_update;

	@Value("${po_invoice_update_message}")
	private String po_invoice_update_message;

	@Value("${po_invoice_unable_update_message}")
	private String po_invoice_unable_update_message;

	@Value("${po_invoice_delete_message}")
	private String po_invoice_delete_message;

	@Value("${po_invoice_unable_delete_message}")
	private String po_invoice_unable_delete_message;

	@Override
	public List<PurchaseOrderModel> getAll() {

		logger.info("PurchaseOrderServiceImpl getAll() starts ");
		Session session = null;
		List<PurchaseOrderModel> poList = new ArrayList<PurchaseOrderModel>();
		try {
			session = sessionFactory.openSession();
			List<PurchaseOrder> pos = poDao.findAll(session);
			// poList = setPOData(pos, poList);
			
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("PurchaseOrderServiceImpl getAll() ends ");
		return poList;
	}
	
	@Override
	public List<PurchaseOrderModel> getStatusPOs(String statusVal) {
		
		logger.info("PurchaseOrderServiceImpl getAll() starts ");
		Session session = null;
		List<PurchaseOrderModel> poList = new ArrayList<PurchaseOrderModel>();
		try {
			session = sessionFactory.openSession();
			List<PurchaseOrder> pos = poDao.getStatusPOs(session, statusVal);
			poList = setPOData(pos, poList, statusVal);
			
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("PurchaseOrderServiceImpl getAll() ends ");
		return poList;
	}

	@Override
	public Object getInvoiceData(Long poId) {
		logger.info("PurchaseOrderServiceImpl getAll() starts ");
		Session session = null;
		PurchaseOrderModel poData = new PurchaseOrderModel();
		try {
			session = sessionFactory.openSession();
			PurchaseOrderInvoice poInvoice = poDao.getPOInvoice(session, poId);
			poData.setId(poId);
			poData.setInvoiceNo(poInvoice.getInvoiceNo());

			Date invoiceDate = poInvoice.getInvoiceDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (!StringUtils.isEmpty(invoiceDate)) {
				poData.setInvoiceDate(sdf.format(invoiceDate));
			}
			poData.setAmount(poInvoice.getAmount());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("PurchaseOrderServiceImpl getAll() ends ");
		return poData;
	}

	private List<PurchaseOrderModel> setPOData(List<PurchaseOrder> pos, List<PurchaseOrderModel> poList,
			String statusVal) {
		
		if(pos != null && !pos.isEmpty()) {
			for (PurchaseOrder purchaseOrder : pos) {
				PurchaseOrderModel poModel = new PurchaseOrderModel();
				poModel.setId(purchaseOrder.getId());
				poModel.setPoNo(purchaseOrder.getPoNo());
				poModel.setMessage(purchaseOrder.getMessage());
				Boolean isSuccess = true;
				
				if (!ValidationUtil.isNull(purchaseOrder.getCategory())) {
					poModel.setCategoryName(purchaseOrder.getCategory().getName());
				}

				String status = null;
				if (!ValidationUtil.isNull(purchaseOrder.getStatus())) {
					status = purchaseOrder.getStatus().getTypeName();
					poModel.setStatusName(status);
				}

				if(status.equals("Invoiced")){
					poModel.setInvoiceNo(purchaseOrder.getInvoiceNo());
				}
				
				if (!ValidationUtil.isNull(purchaseOrder.getUnitType())) {
					poModel.setUnitTypeName(purchaseOrder.getUnitType().getTypeName());
				} else {
					isSuccess = false;
				}
				if (!ValidationUtil.isNull(purchaseOrder.getVendor())) {
					poModel.setVendorName(purchaseOrder.getVendor().getName());
				} else {
					isSuccess = false;
				}
				poList.add(poModel);
				
				if (statusVal.equals("Active")) {
					List<PurchaseOrderIssue> poIssues = purchaseOrder.getPoIssues();

					if (poIssues != null && !poIssues.isEmpty()) {

						for (PurchaseOrderIssue purchaseOrderIssue : poIssues) {
							Issue issue = purchaseOrderIssue.getIssue();
							if (!"Complete".equals(issue.getStatus().getTypeName())
									&& !"Incomplete".equals(issue.getStatus().getTypeName())) {
								isSuccess = false;
								break;
							}
						}

						poModel.setIsComplete(isSuccess);
						poModel.setCompleteStatusId(109l);
					}
				}

				if (statusVal.equals("Complete")) {
					poModel.setInvoiceStatusId(110l);
				}

			}
		}
		
		return poList;
	}


	private Object createSuccessObject(String msg, String statusVal) {

		Success success = new Success();
		success.setMessage(msg);
		success.setResultList(getStatusPOs(statusVal));
		return success;
	}

	private Object createFailedObject(String msg) {

		Failed failed = new Failed();
		failed.setMessage(msg);
		return failed;
	}

	@Override
	public Object update(Long id, PurchaseOrderModel poModel) {

		logger.info("PurchaseOrderServiceImpl update() starts.");
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			PurchaseOrder po = (PurchaseOrder) session.get(PurchaseOrder.class, id);
			List<PurchaseOrderIssue> poIssues = new ArrayList<PurchaseOrderIssue>();
			List<PurchaseOrderUnitNos> poUnitNos = new ArrayList<PurchaseOrderUnitNos>();
			List<Issue> issues = new ArrayList<Issue>();
			if (po != null) {
				setPoValues(poModel, po, poIssues, issues, session, Iconstants.UPDATE_PO, poUnitNos);
				poDao.update(po, poIssues, issues, poUnitNos, session);
				tx.commit();
			} else {
				return createFailedObject(po_unable_to_update_message);
			}

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.info("Exception inside PurchaseOrderServiceImpl update() :"+ e.getMessage());
			return createFailedObject(po_unable_to_update_message);
		} finally {
			if(session != null) {
				session.close();
			}
		}

		logger.info("PurchaseOrderServiceImpl update() ends.");
		return createSuccessObject(po_updated_message, poModel.getCurrentStatusVal());
	}

	@Override
	public Object updateInvoice(Long poId, PurchaseOrderModel poModel) {
		logger.info("PurchaseOrderServiceImpl updateInvoice() starts.");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			PurchaseOrder po = (PurchaseOrder) session.get(PurchaseOrder.class, poId);

			if (po != null) {
				List<PurchaseOrderInvoice> poInvoices = po.getPoInvoices();

				if (poInvoices != null && !poInvoices.isEmpty()) {
					for (PurchaseOrderInvoice purchaseOrderInvoice : poInvoices) {
						purchaseOrderInvoice.setAmount(poModel.getAmount());
						purchaseOrderInvoice.setInvoiceNo(poModel.getInvoiceNo());
						purchaseOrderInvoice.setInvoiceDate(DateUtil.changeStringToDate(poModel.getInvoiceDate()));
						session.update(purchaseOrderInvoice);
					}
				}
				tx.commit();
			} else {
				return createFailedObject(po_invoice_unable_update_message);
			}

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.info("Exception inside PurchaseOrderServiceImpl updateInvoice() :" + e.getMessage());
			return createFailedObject(po_invoice_unable_update_message);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("PurchaseOrderServiceImpl updateInvoice() ends.");
		return createSuccessObject(po_invoice_update_message, "Invoiced");
	}

	@Override
	public Object delete(Long id) {

		logger.info("PurchaseOrderServiceImpl delete() starts.");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			PurchaseOrder purchaseOrder = (PurchaseOrder) session.get(PurchaseOrder.class, id);
			
			if (purchaseOrder != null) {
				
				List<PurchaseOrderUnitNos> poUnitNos = purchaseOrder.getPoUnitNos();
				if (poUnitNos != null && !poUnitNos.isEmpty()) {
					for (PurchaseOrderUnitNos purchaseOrderUnitNos : poUnitNos) {
						session.delete(purchaseOrderUnitNos);
					}
				}

				List<PurchaseOrderIssue> poIssues = purchaseOrder.getPoIssues();
				if(poIssues != null && ! poIssues.isEmpty()) {
					Type status = (Type) session.get(Type.class, 103l);
					for (PurchaseOrderIssue purchaseOrderIssue : poIssues) {

						Issue issue = purchaseOrderIssue.getIssue();
						issue.setStatus(status);
						session.update(issue);

						session.delete(purchaseOrderIssue);
					}
				}
				
				session.delete(purchaseOrder);
				tx.commit();
			} else {
				return createFailedObject(po_unable_to_delete_message);
			}

		} catch (Exception e) {
			logger.info("Exception inside PurchaseOrderServiceImpl delete() : " + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(po_already_used_message);
			}
			return createFailedObject(po_unable_to_delete_message);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("PurchaseOrderServiceImpl delete() ends.");
		return createSuccessObject(po_deleted_message, "Active");
	}

	@Override
	public PurchaseOrderModel get(Long id) {

		logger.info("PurchaseOrderServiceImpl get() starts.");
		Session session = null;
		PurchaseOrderModel poModel = new PurchaseOrderModel();

		try {

			session = sessionFactory.openSession();
			PurchaseOrder po = poDao.findById(id, session);

			if (po != null) {

				poModel.setId(po.getId());

				if (!ValidationUtil.isNull(po.getCategory())) {
					poModel.setCategoryId(po.getCategory().getCategoryId());
				}
				poModel.setMessage(po.getMessage());

				if (!ValidationUtil.isNull(po.getUnitType())) {
					poModel.setUnitTypeId(po.getUnitType().getTypeId());
				}

				if (!ValidationUtil.isNull(po.getVendor())) {
					poModel.setVendorId(po.getVendor().getVendorId());
				}

				if (!ValidationUtil.isNull(po.getStatus())) {
					poModel.setStatusId(po.getStatus().getTypeId());
				}
				
				/*if(po.getStatus().getTypeName().equals("Invoiced")) {
					poModel.setInvoiceNo(po.getInvoiceNo());
				}*/
				
				List<IssueModel> issueModels = new ArrayList<IssueModel>();
				List<PurchaseOrderIssue> poIssues = po.getPoIssues();
				
				if(poIssues != null && !poIssues.isEmpty()) {
					for (PurchaseOrderIssue purchaseOrderIssue : poIssues) {
						Issue issue = purchaseOrderIssue.getIssue();
						IssueModel issueObj = new IssueModel();
						issueObj.setId(issue.getId());
						issueObj.setTitle(issue.getIssueName());
						issueObj.setUnitNo(issue.getUnitNo());

						if (!ValidationUtil.isNull(issue.getVmc())) {
							issueObj.setVmcName(issue.getVmc().getName());
						}

						if (!ValidationUtil.isNull(issue.getReportedBy())) {
							issueObj.setReportedByName(issue.getReportedBy().getFirstName());
						}

						if (!ValidationUtil.isNull(issue.getUnitType())) {
							issueObj.setUnitTypeName(issue.getUnitType().getTypeName());
						}

						if (!ValidationUtil.isNull(issue.getCategory())) {
							issueObj.setCategoryName(issue.getCategory().getName());
						}

						if (!ValidationUtil.isNull(issue.getStatus())) {
							issueObj.setStatusName(issue.getStatus().getTypeName());
							issueObj.setStatusId(issue.getStatus().getTypeId());
						}

						issueModels.add(issueObj);

					}
					
					poModel.setIssueList(issueModels);
				}
				
				List<PurchaseOrderUnitNos> PurchaseOrderUnitNos = po.getPoUnitNos();
				if (PurchaseOrderUnitNos != null && !PurchaseOrderUnitNos.isEmpty()) {
					List<String> poUnitNos = new ArrayList<String>();
					for (PurchaseOrderUnitNos purchaseOrderUnitNos : PurchaseOrderUnitNos) {
						poUnitNos.add(purchaseOrderUnitNos.getUnitNo());
					}

					poModel.setSelectedUnitNos(poUnitNos);
				}

				getOpenAddData(poModel);

				Long categoryId = 0l;
				if (po.getCategory() != null) {
					categoryId = po.getCategory().getCategoryId();
				}
				if (!ValidationUtil.isNull(po.getUnitType())) {
					IssueModel issueModel = issueService.getUnitNo(categoryId, po.getUnitType().getTypeId());
					poModel.setAllUnitNos(issueModel.getUnitNos());

					List<CategoryModel> categoriesBasedOnUnitType = categoryService.getCategoriesBasedOnType(po.getUnitType().getTypeName());
					poModel.setCategoryList(categoriesBasedOnUnitType);
				}

			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("PurchaseOrderServiceImpl get() ends.");
		return poModel;
	}

	@Override
	public PurchaseOrderModel getOpenAdd() {

		logger.info("PurchaseOrderServiceImpl getOpenAdd() starts ");
		PurchaseOrderModel poModel = new PurchaseOrderModel();
		
		getOpenAddData(poModel);
		logger.info("PurchaseOrderServiceImpl getOpenAdd() ends ");
		return poModel;
	}

	private void getOpenAddData(PurchaseOrderModel poModel) {
		
		List<VendorModel> vendorList = vendorService.getSpecificData();
		poModel.setVendorList(vendorList);
		
	/*	List<CategoryModel> categoryList = categoryService.getSpecificData();
		poModel.setCategoryList(categoryList);*/
		
		List<TypeResponse> unitTypeList = typeService.getAll(25l);
		poModel.setUnitTypeList(unitTypeList);
		
		List<TypeResponse> issueStatusList = typeService.getAll(23l);
		poModel.setIssueStatusList(issueStatusList);
		
	}

	@Override
	public List<PurchaseOrderModel> getPoByPoNo(Long issueName) {

		logger.info("PurchaseOrderServiceImpl getPoByPoNo() starts ");
		Session session = null;
		List<PurchaseOrderModel> issueList = new ArrayList<PurchaseOrderModel>();

		try {
			session = sessionFactory.openSession();
			//List<Issue> issues = issueDao.getIssueByIssueName(session, issueName);
			//issueList = setIssueData(issues, issueList);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("PurchaseOrderServiceImpl getPoByPoNo() ends ");
		return issueList;
	}
	
	@Override
	public Object addPO(PurchaseOrderModel poModel) {

		logger.info("PurchaseOrderServiceImpl addPO() starts ");
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			PurchaseOrder po = new PurchaseOrder();
			List<PurchaseOrderIssue> poIssues = new ArrayList<PurchaseOrderIssue>();
			List<PurchaseOrderUnitNos> poUnitNos = new ArrayList<PurchaseOrderUnitNos>();
			List<Issue> issues = new ArrayList<Issue>();
			setPoValues(poModel, po, poIssues, issues, session, Iconstants.ADD_PO, poUnitNos);
			Type assignStatus = typeService.get(106l);
			poDao.addPurchaseOrder(po, poIssues, issues, assignStatus, poUnitNos, session);
			tx.commit();
		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			logger.info("Exception inside PurchaseOrderServiceImpl addPO() :" + e.getMessage());
			return createFailedObject(po_unable_to_add_message);

		} finally {
			if(session != null){
				session.close();
			}
		}

		logger.info("PurchaseOrderServiceImpl addPO() ends ");
		return createSuccessObject(po_added_message, "Active");
	}

	private void setPoValues(PurchaseOrderModel poModel, PurchaseOrder po, List<PurchaseOrderIssue> poIssues,
			List<Issue> issues, Session session, String type, List<PurchaseOrderUnitNos> poUnitNos) {
		
		List<IssueModel> issueData = poModel.getIssue();
		Boolean isSuccess = true;
		if (!ValidationUtil.isNull(poModel.getUnitTypeId())) {
			Type unitType = (Type) session.get(Type.class, poModel.getUnitTypeId());
			po.setUnitType(unitType);
		} else {
			isSuccess = false;
		}

		if (!ValidationUtil.isNull(poModel.getCategoryId())) {
			Category category = (Category) session.get(Category.class, poModel.getCategoryId());
			po.setCategory(category);
		}
		if (!ValidationUtil.isNull(poModel.getVendorId())) {
			Vendor vendor = (Vendor) session.get(Vendor.class, poModel.getVendorId());
			po.setVendor(vendor);
		} else {
			isSuccess = false;
		}
		List<String> unitNos = poModel.getSelectedUnitNos();
		
		if(Iconstants.ADD_PO.equals(type)) {
			Long poNo = poDao.getMaxPoNO(session);
			po.setPoNo(poNo+1);
			poModel.setStatusId(108l);
			Type status = (Type) session.get(Type.class, poModel.getStatusId());
			po.setStatus(status);
		} else {
			// existing issues
			List<PurchaseOrderIssue> existingPoIssues = po.getPoIssues();
			if (existingPoIssues != null && !existingPoIssues.isEmpty()) {
				Type openStatus = typeService.get(103l);
				for (PurchaseOrderIssue purchaseOrderIssue : existingPoIssues) {
					Issue issue = purchaseOrderIssue.getIssue();
					issue.setStatus(openStatus);
					session.update(issue);
					session.delete(purchaseOrderIssue);
				}
			}

			List<PurchaseOrderUnitNos> existingPoUnitNos = po.getPoUnitNos();

			if (existingPoUnitNos != null && !existingPoUnitNos.isEmpty()) {
				for (PurchaseOrderUnitNos purchaseOrderUnitNos : existingPoUnitNos) {
					session.delete(purchaseOrderUnitNos);
				}
			}

		}
		
		po.setMessage(poModel.getMessage());

		if (issueData != null && !issueData.isEmpty()) {
			
			for (IssueModel issueModel : issueData) {
				Long issueId = issueModel.getId();
				PurchaseOrderIssue poIssue = new PurchaseOrderIssue();
				Issue issue = (Issue) session.get(Issue.class, issueId);
				poIssue.setIssue(issue);
				poIssues.add(poIssue);

				if (!ValidationUtil.isNull(issueModel.getStatusId())) {
					Type issueStatus = (Type) session.get(Type.class, issueModel.getStatusId());
					if (!"Complete".equals(issue.getStatus().getTypeName())
							&& !"Incomplete".equals(issue.getStatus().getTypeName())) {
						isSuccess = false;
					}
					issue.setStatus(issueStatus);
				}
				issues.add(issue);
			}
		}
		
		if (!isSuccess
				&& (poModel.getCurrentStatusVal().equalsIgnoreCase("Complete") || poModel.getCurrentStatusVal()
						.equalsIgnoreCase("Invoiced"))) {
			// if all conditions not fullfilled we mark PO as active.
			Type status = (Type) session.get(Type.class, 108l);
			po.setStatus(status);
			// if status is invoiced and all conditions not fullfilled we delete po invoices
			if (poModel.getCurrentStatusVal().equalsIgnoreCase("Invoiced")) {
				List<PurchaseOrderInvoice> poInvoices = po.getPoInvoices();
				if (poInvoices != null && !poInvoices.isEmpty()) {
					for (PurchaseOrderInvoice purchaseOrderInvoice : poInvoices) {
						session.delete(purchaseOrderInvoice);
					}
				}
			}
		}
		if (unitNos != null) {
			for (String unitNo : unitNos) {
				PurchaseOrderUnitNos poUnitNo = new PurchaseOrderUnitNos();
				poUnitNo.setUnitNo(unitNo);
				poUnitNos.add(poUnitNo);
			}
		}

	}

	@Override
	public List<IssueModel> getCategoryAndUnitTypeIssues(Long categoryId, Long unitTypeId) {

		Session session = null;
		List<IssueModel> issues = new ArrayList<IssueModel>();
		try {
			session = sessionFactory.openSession();
			issues = issueService.getIssueforCategoryAndUnitType(categoryId, unitTypeId, session);
		} finally {
			if(session != null){
				session.close();
			}
		}
		
		return issues;
	}

	@Override
	public List<IssueModel> getUnitNoIssues(Long unitTypeId, Long unitNo) {
		
		Session session = null;
		List<IssueModel> issues = new ArrayList<IssueModel>();
		try {
			session = sessionFactory.openSession();
			issues = issueService.getIssuesBasedOnUnitTypeAndNo(unitTypeId, unitNo, session);
		} finally {
			if(session != null){
				session.close();
			}
		}
		
		return issues;
	}

	@Override
	public List<IssueModel> getUnitNoIssues(Long unitTypeId, PurchaseOrderModel poModel) {
		Session session = null;
		List<IssueModel> issues = new ArrayList<IssueModel>();
		try {
			session = sessionFactory.openSession();
			issues = issueService.getIssuesBasedOnUnitTypeAndUnitNos(unitTypeId, poModel.getSelectedUnitNos(), session);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return issues;
	}

	@Override
	public Object updateStatus(Long poId, Long statusId, PurchaseOrderModel poModel) {
	
		logger.info("PurchaseOrderServiceImpl updateStatus() starts.");
		Session session = null;
		Transaction tx = null;
		Type status = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			PurchaseOrder po = (PurchaseOrder) session.get(PurchaseOrder.class, poId);
			if (po != null) {
				status = (Type) session.get(Type.class, statusId);
				poDao.updateStatus(po, status, session);
				
				if(Iconstants.PO_INVOICE_STATUS.equals(status.getTypeName())) {
					PurchaseOrderInvoice invoice = createPOInvoice(po, poModel);
					poDao.createInvoice(invoice, session);
				}
				tx.commit();
			} else {
				return createFailedObject(po_status_unable_to_update);
			}

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.info("Exception inside PurchaseOrderServiceImpl updateStatus() :"+ e.getMessage());
			return createFailedObject(po_status_unable_to_update);
		} finally {
			if(session != null) {
				session.close();
			}
		}

		logger.info("PurchaseOrderServiceImpl updateStatus() ends.");
		return createSuccessObject(po_status_update, poModel.getCurrentStatusVal());
	}

	private PurchaseOrderInvoice createPOInvoice(PurchaseOrder po, PurchaseOrderModel poModel) {

		PurchaseOrderInvoice invoice = new PurchaseOrderInvoice();
		invoice.setAmount(poModel.getAmount());
		invoice.setInvoiceNo(poModel.getInvoiceNo());
		invoice.setInvoiceDate(DateUtil.changeStringToDate(poModel.getInvoiceDate()));
		invoice.setPurchaseOrder(po);
		return invoice;
	}

	@Override
	public Object deleteInvoice(Long poId) {

		logger.info("PurchaseOrderServiceImpl deleteInvoice() starts.");
		Session session = null;
		Transaction tx = null;
		Type status = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			PurchaseOrder po = (PurchaseOrder) session.get(PurchaseOrder.class, poId);
			if (po != null) {

				List<PurchaseOrderInvoice> poInvoices = po.getPoInvoices();
				if (poInvoices != null && !poInvoices.isEmpty()) {
					for (PurchaseOrderInvoice purchaseOrderInvoice : poInvoices) {
						poDao.deleteInvoice(purchaseOrderInvoice, session);
					}
				}
				status = (Type) session.get(Type.class, 109l);
				poDao.updateStatus(po, status, session);

				tx.commit();
			} else {
				return createFailedObject(po_invoice_unable_delete_message);
			}

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.info("Exception inside PurchaseOrderServiceImpl deleteInvoice() :" + e.getMessage());
			return createFailedObject(po_invoice_unable_delete_message);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("PurchaseOrderServiceImpl deleteInvoice() ends.");
		return createSuccessObject(po_invoice_delete_message, "Invoiced");
	}

}
