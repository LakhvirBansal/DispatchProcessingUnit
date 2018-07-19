package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
@JsonIgnoreProperties(ignoreUnknown = true) 
@JsonSerialize(include = Inclusion.NON_NULL)
public class TaxCodeModel {

 
	private Long taxCodeId;

	private String taxCode;
	
	private String description;
	
	private Double percentage;
	
	private Boolean taxable;
	
	private Long glAccountSaleId;
	private String glAccountSaleName;
	private List<AccountModel> glAccountSaleList;
	
	private Long glAccountRevenueId;
	private String glAccountRevenueName;
	private List<AccountModel> glAccountRevenueList;

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

	public Long getGlAccountSaleId() {
		return glAccountSaleId;
	}

	public void setGlAccountSaleId(Long glAccountSaleId) {
		this.glAccountSaleId = glAccountSaleId;
	}

	public String getGlAccountSaleName() {
		return glAccountSaleName;
	}

	public void setGlAccountSaleName(String glAccountSaleName) {
		this.glAccountSaleName = glAccountSaleName;
	}

	public List<AccountModel> getGlAccountSaleList() {
		return glAccountSaleList;
	}

	public void setGlAccountSaleList(List<AccountModel> glAccountSaleList) {
		this.glAccountSaleList = glAccountSaleList;
	}

	public Long getGlAccountRevenueId() {
		return glAccountRevenueId;
	}

	public void setGlAccountRevenueId(Long glAccountRevenueId) {
		this.glAccountRevenueId = glAccountRevenueId;
	}

	public String getGlAccountRevenueName() {
		return glAccountRevenueName;
	}

	public void setGlAccountRevenueName(String glAccountRevenueName) {
		this.glAccountRevenueName = glAccountRevenueName;
	}

	public List<AccountModel> getGlAccountRevenueList() {
		return glAccountRevenueList;
	}

	public void setGlAccountRevenueList(List<AccountModel> glAccountRevenueList) {
		this.glAccountRevenueList = glAccountRevenueList;
	}

	
}
