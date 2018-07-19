package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonIgnoreProperties(ignoreUnknown = true) 
@JsonSerialize(include = Inclusion.NON_NULL)
public class Accounts {
 
	private Long accountId;

	private String accountName;
	
	private String account;
	
	private Long currencyId;
	private String currencyName;
	private List<Type> currencyList;
	
	private Long accountTypeId;
	private String accountTypeName;
	private List<Type> accountTypeList;
	
	private Long parentAccountId;
	private String parentAccountName;
	private List<Accounts> parentAccountList;
	
	private Long taxCodeId;
	private String taxCodeName;
	private List<TaxCode> taxCodeList;
	
	private String description;

	public Long getTaxCodeId() {
		return taxCodeId;
	}

	public void setTaxCodeId(Long taxCodeId) {
		this.taxCodeId = taxCodeId;
	}

	public String getTaxCodeName() {
		return taxCodeName;
	}

	public void setTaxCodeName(String taxCodeName) {
		this.taxCodeName = taxCodeName;
	}

	public List<TaxCode> getTaxCodeList() {
		return taxCodeList;
	}

	public void setTaxCodeList(List<TaxCode> taxCodeList) {
		this.taxCodeList = taxCodeList;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public List<Type> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<Type> currencyList) {
		this.currencyList = currencyList;
	}

	public Long getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(Long accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public List<Type> getAccountTypeList() {
		return accountTypeList;
	}

	public void setAccountTypeList(List<Type> accountTypeList) {
		this.accountTypeList = accountTypeList;
	}

	public Long getParentAccountId() {
		return parentAccountId;
	}

	public void setParentAccountId(Long parentAccountId) {
		this.parentAccountId = parentAccountId;
	}

	public String getParentAccountName() {
		return parentAccountName;
	}

	public void setParentAccountName(String parentAccountName) {
		this.parentAccountName = parentAccountName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Accounts> getParentAccountList() {
		return parentAccountList;
	}

	public void setParentAccountList(List<Accounts> parentAccountList) {
		this.parentAccountList = parentAccountList;
	}
	
	
}
