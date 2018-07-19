package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Issue;
import com.dpu.entity.PurchaseOrder;
import com.dpu.entity.PurchaseOrderInvoice;
import com.dpu.entity.PurchaseOrderIssue;
import com.dpu.entity.PurchaseOrderUnitNos;
import com.dpu.entity.Type;

public interface PurchaseOrderDao extends GenericDao<PurchaseOrder> {

	List<PurchaseOrder> findAll(Session session);

	PurchaseOrder findById(Long id, Session session);

	void addPurchaseOrder(PurchaseOrder po, List<PurchaseOrderIssue> poIssues, List<Issue> issues, Type assignStatus,
			List<PurchaseOrderUnitNos> poUnitNos, Session session);

	Long getMaxPoNO(Session session);

	void update(PurchaseOrder po, List<PurchaseOrderIssue> poIssues, List<Issue> issues,
			List<PurchaseOrderUnitNos> poUnitNos, Session session);

	void updateStatus(PurchaseOrder po, Type status, Session session);

	List<PurchaseOrder> getStatusPOs(Session session, String statusVal);

	void createInvoice(PurchaseOrderInvoice invoice, Session session);

	PurchaseOrderInvoice getPOInvoice(Session session, Long poId);

	void deleteInvoice(PurchaseOrderInvoice purchaseOrderInvoice, Session session);

}
