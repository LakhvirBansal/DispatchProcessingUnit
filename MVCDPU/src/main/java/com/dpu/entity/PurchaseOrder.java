package com.dpu.entity;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
@Table(name = "purchase_order")
public class PurchaseOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "purchase_order_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendor_id")
	private Vendor vendor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_type_id")
	private Type unitType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Type status;
	
	@Column(name = "invoice_no")
	private String invoiceNo;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "po_no")
	private Long poNo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder")
	private List<PurchaseOrderIssue> poIssues;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder")
	private List<PurchaseOrderUnitNos> poUnitNos;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder")
	private List<PurchaseOrderInvoice> poInvoices;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Type getStatus() {
		return status;
	}

	public void setStatus(Type status) {
		this.status = status;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Type getUnitType() {
		return unitType;
	}

	public void setUnitType(Type unitType) {
		this.unitType = unitType;
	}

	public List<PurchaseOrderIssue> getPoIssues() {
		return poIssues;
	}

	public void setPoIssues(List<PurchaseOrderIssue> poIssues) {
		this.poIssues = poIssues;
	}

	public Long getPoNo() {
		return poNo;
	}

	public void setPoNo(Long poNo) {
		this.poNo = poNo;
	}

	public List<PurchaseOrderUnitNos> getPoUnitNos() {
		return poUnitNos;
	}

	public void setPoUnitNos(List<PurchaseOrderUnitNos> poUnitNos) {
		this.poUnitNos = poUnitNos;
	}

	public List<PurchaseOrderInvoice> getPoInvoices() {
		return poInvoices;
	}

	public void setPoInvoices(List<PurchaseOrderInvoice> poInvoices) {
		this.poInvoices = poInvoices;
	}

}
