package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.PurchaseOrderDao;
import com.dpu.entity.Issue;
import com.dpu.entity.PurchaseOrder;
import com.dpu.entity.PurchaseOrderInvoice;
import com.dpu.entity.PurchaseOrderIssue;
import com.dpu.entity.PurchaseOrderUnitNos;
import com.dpu.entity.Type;

@Repository
public class PurchaseOrderDaoImpl extends GenericDaoImpl<PurchaseOrder> implements PurchaseOrderDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<PurchaseOrder> findAll(Session session) {
		StringBuilder sb = new StringBuilder(
				" select i from PurchaseOrder i left join fetch i.vendor left join fetch i.category left join fetch i.unitType left join fetch i.status ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public PurchaseOrder findById(Long id, Session session) {
		StringBuilder sb = new StringBuilder(
				" select i from PurchaseOrder i left join fetch i.vendor left join fetch i.category left join fetch i.unitType left join fetch i.status where i.id =:poId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("poId", id);
		return (PurchaseOrder) query.uniqueResult();
	}

	@Override
	public void addPurchaseOrder(PurchaseOrder po, List<PurchaseOrderIssue> poIssues, List<Issue> issues,
			Type assignStatus, List<PurchaseOrderUnitNos> poUnitNos, Session session) {

		session.save(po);

		for (PurchaseOrderIssue purchaseOrderIssue : poIssues) {
			/*
			 * Issue issue = purchaseOrderIssue.getIssue(); issue.setStatus(assignStatus); session.update(issue);
			 */
			purchaseOrderIssue.setPurchaseOrder(po);
			session.save(purchaseOrderIssue);
		}

		for (PurchaseOrderUnitNos purchaseOrderUnitNos : poUnitNos) {
			purchaseOrderUnitNos.setPurchaseOrder(po);
			session.save(purchaseOrderUnitNos);
		}
		for (Issue issue : issues) {
			// issue.setStatus(assignStatus);
			session.update(issue);
		}
	}

	@Override
	public Long getMaxPoNO(Session session) {
		Long returnVal = 999l; // PO number starts from 1000
		Long maxVal = (Long) session.createQuery(" select max(poNo) from PurchaseOrder ").uniqueResult();
		if (maxVal != null) {
			returnVal = maxVal;
		}
		return returnVal;
	}

	@Override
	public void update(PurchaseOrder po, List<PurchaseOrderIssue> poIssues, List<Issue> issues,
			List<PurchaseOrderUnitNos> poUnitNos, Session session) {

		session.update(po);

		// insert updated issues
		for (PurchaseOrderIssue purchaseOrderIssue : poIssues) {
			/*
			 * Issue issue = purchaseOrderIssue.getIssue(); issue.setStatus(assignStatus); session.update(issue);
			 */
			purchaseOrderIssue.setPurchaseOrder(po);
			session.save(purchaseOrderIssue);
		}

		for (PurchaseOrderUnitNos purchaseOrderUnitNos : poUnitNos) {
			purchaseOrderUnitNos.setPurchaseOrder(po);
			session.save(purchaseOrderUnitNos);

		}
		for (Issue issue : issues) {
			session.update(issue);
		}

	}

	@Override
	public void updateStatus(PurchaseOrder po, Type status, Session session) {
		po.setStatus(status);
		session.update(po);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PurchaseOrder> getStatusPOs(Session session, String statusVal) {
		StringBuilder sb = new StringBuilder(
				" select i from PurchaseOrder i left join fetch i.vendor left join fetch i.category left join fetch i.unitType left join fetch i.status ")
				.append(" where i.status.typeName = :statusVal ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("statusVal", statusVal);
		return query.list();
	}

	@Override
	public void createInvoice(PurchaseOrderInvoice invoice, Session session) {
		session.save(invoice);
	}

	@Override
	public PurchaseOrderInvoice getPOInvoice(Session session, Long poId) {
		StringBuilder sb = new StringBuilder(" select p from PurchaseOrderInvoice p where p.purchaseOrder.id =:poId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("poId", poId);
		return (PurchaseOrderInvoice) query.list().get(0);
	}

	@Override
	public void deleteInvoice(PurchaseOrderInvoice purchaseOrderInvoice, Session session) {
		session.delete(purchaseOrderInvoice);
	}

}
