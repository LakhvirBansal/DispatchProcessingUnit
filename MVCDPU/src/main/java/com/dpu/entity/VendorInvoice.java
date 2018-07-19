package com.dpu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vendor_invoice")
public class VendorInvoice {

	@Id
	@Column(name = "vendor_invoice_id")
	@GeneratedValue
	private Long vendorInvoiceId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendor_id")
	private Vendor vendor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "division_id")
	private Division division;
	
	@Column(name = "invoice_date")
	private Date invoiceDate;
	
	@Column(name = "invoice_no")
	private String invoiceNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id")
	private Type currency;
	
	@Column(name = "created_by")
	private Long created_by;

	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "modified_by")
	private Long modified_by;
	
	@Column(name = "modified_on")
	private Date modifiedOn;

	public Long getVendorInvoiceId() {
		return vendorInvoiceId;
	}

	public void setVendorInvoiceId(Long vendorInvoiceId) {
		this.vendorInvoiceId = vendorInvoiceId;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Type getCurrency() {
		return currency;
	}

	public void setCurrency(Type currency) {
		this.currency = currency;
	}
	
	
	
}
