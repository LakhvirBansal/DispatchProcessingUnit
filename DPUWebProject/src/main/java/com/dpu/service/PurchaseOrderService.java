package com.dpu.service;

import java.util.List;

import com.dpu.model.IssueModel;
import com.dpu.model.PurchaseOrderModel;

public interface PurchaseOrderService {

	Object delete(Long id);

	List<PurchaseOrderModel> getAll();

	PurchaseOrderModel getOpenAdd();

	PurchaseOrderModel get(Long id);
	
	Object addPO(PurchaseOrderModel poModel);

	List<IssueModel> getCategoryAndUnitTypeIssues(Long categoryId, Long unitTypeId);

	Object update(Long poId, PurchaseOrderModel poModel);

	List<PurchaseOrderModel> getPoByPoNo(Long poNo);

	Object updateStatus(Long poId, Long statusId, PurchaseOrderModel poModel);

	List<PurchaseOrderModel> getStatusPOs(String statusVal);

	List<IssueModel> getUnitNoIssues(Long unitTypeId, Long unitNo);

	List<IssueModel> getUnitNoIssues(Long unitTypeId, PurchaseOrderModel poModel);

	Object updateInvoice(Long poId, PurchaseOrderModel poModel);

	Object getInvoiceData(Long poId);

	Object deleteInvoice(Long poId);

}
