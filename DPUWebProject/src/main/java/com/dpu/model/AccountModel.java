package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
@JsonIgnoreProperties(ignoreUnknown = true) 
@JsonSerialize(include = Inclusion.NON_NULL)
public class AccountModel {

 
	private Long accountId;

	private String accountName;
	
	private String account;
	
	private Long currencyId;
	private String currencyName;
	private List<TypeResponse> currencyList;
	
	private Long accountTypeId;
	private String accountTypeName;
	private List<TypeResponse> accountTypeList;
	
	private Long parentAccountId;
	private String parentAccountName;
	private List<AccountModel> parentAccountList;
	
	private Long taxCodeId;
	private String taxCodeName;
	private List<TaxCodeModel> taxCodeList;
	
	private String description;

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

	public List<TypeResponse> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<TypeResponse> currencyList) {
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

	public List<TypeResponse> getAccountTypeList() {
		return accountTypeList;
	}

	public void setAccountTypeList(List<TypeResponse> accountTypeList) {
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

	public List<AccountModel> getParentAccountList() {
		return parentAccountList;
	}

	public void setParentAccountList(List<AccountModel> parentAccountList) {
		this.parentAccountList = parentAccountList;
	}

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

	public List<TaxCodeModel> getTaxCodeList() {
		return taxCodeList;
	}

	public void setTaxCodeList(List<TaxCodeModel> taxCodeList) {
		this.taxCodeList = taxCodeList;
	}
	
	
}
