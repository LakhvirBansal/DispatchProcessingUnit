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
@Table(name = "tax_code")
public class TaxCode {

	@Id
	@Column(name = "tax_code_id")
	@GeneratedValue
	private Long taxCodeId;
	
	@Column(name = "tax_code")
	private String taxCode;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "percentage")
	private Double percentage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gl_account_for_sales")
	private Account accountForSale;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gl_accounts_for_revenue")
	private Account accountForRevenue;
	
	@Column(name = "taxable")
	private Boolean taxable;
	
	@Column(name = "created_by")
	private Long created_by;

	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "modified_by")
	private Long modified_by;
	
	@Column(name = "modified_on")
	private Date modifiedOn;

	public Long getTaxCodeId() {
		return taxCodeId;
	}

	public void setTaxCodeId(Long taxCodeId) {
		this.taxCodeId = taxCodeId;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Boolean getTaxable() {
		return taxable;
	}

	public void setTaxable(Boolean taxable) {
		this.taxable = taxable;
	}

	public Long getCreated_by() {
		return created_by;
	}

	public void setCreated_by(Long created_by) {
		this.created_by = created_by;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getModified_by() {
		return modified_by;
	}

	public void setModified_by(Long modified_by) {
		this.modified_by = modified_by;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Account getAccountForSale() {
		return accountForSale;
	}

	public void setAccountForSale(Account accountForSale) {
		this.accountForSale = accountForSale;
	}

	public Account getAccountForRevenue() {
		return accountForRevenue;
	}

	public void setAccountForRevenue(Account accountForRevenue) {
		this.accountForRevenue = accountForRevenue;
	}

}
