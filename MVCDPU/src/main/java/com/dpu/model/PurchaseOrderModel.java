package com.dpu.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class PurchaseOrderModel implements Serializable{

	 
	private static final long serialVersionUID = 1L;

	private Long id;

	private String title;
	
	private String vendorName;
	private Long vendorId;
	private List<VendorModel> vendorList;
	
	private String categoryName;
	private Long categoryId;
	private List<CategoryModel> categoryList;
	
	private String unitTypeName;
	private Long unitTypeId;
	private List<TypeResponse> unitTypeList;
	
	private String issueName;
	private Long issueId;
	private List<IssueModel> issueList;
	
	private String statusName;
	private Long statusId;
	private List<TypeResponse> statusList;
	
	private String issueStatusName;
	private Long issueStatusId;
	private List<TypeResponse> issueStatusList;

	private String message;
	
	private List<IssueModel> issues;
	
	private Long PoNo;
	
	private Boolean isComplete;
	private Long completeStatusId;
	
	// for invoice status
	private String invoiceNo;
	private Double amount;
	private String invoiceDate;
	
	private Long invoiceStatusId;
	private String currentStatusVal;
	List<String> issueIds;
	List<String> issueStatusIds;
	private List<String> selectedUnitNos;
	private List<String> allUnitNos;

	public List<IssueModel> getIssues() {
		return issues;
	}

	public void setIssues(List<IssueModel> issues) {
		this.issues = issues;
	}

	public List<String> getIssueIds() {
		return issueIds;
	}

	public void setIssueIds(List<String> issueIds) {
		this.issueIds = issueIds;
	}

	public List<String> getIssueStatusIds() {
		return issueStatusIds;
	}

	public void setIssueStatusIds(List<String> issueStatusIds) {
		this.issueStatusIds = issueStatusIds;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public List<TypeResponse> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<TypeResponse> statusList) {
		this.statusList = statusList;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public List<VendorModel> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<VendorModel> vendorList) {
		this.vendorList = vendorList;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public List<IssueModel> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<IssueModel> issueList) {
		this.issueList = issueList;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<CategoryModel> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryModel> categoryList) {
		this.categoryList = categoryList;
	}

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	public Long getUnitTypeId() {
		return unitTypeId;
	}

	public void setUnitTypeId(Long unitTypeId) {
		this.unitTypeId = unitTypeId;
	}

	public List<TypeResponse> getUnitTypeList() {
		return unitTypeList;
	}

	public void setUnitTypeList(List<TypeResponse> unitTypeList) {
		this.unitTypeList = unitTypeList;
	}

	public Long getPoNo() {
		return PoNo;
	}

	public void setPoNo(Long poNo) {
		PoNo = poNo;
	}

	public Boolean getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Long getCompleteStatusId() {
		return completeStatusId;
	}

	public void setCompleteStatusId(Long completeStatusId) {
		this.completeStatusId = completeStatusId;
	}

	public Long getInvoiceStatusId() {
		return invoiceStatusId;
	}

	public void setInvoiceStatusId(Long invoiceStatusId) {
		this.invoiceStatusId = invoiceStatusId;
	}

	public String getCurrentStatusVal() {
		return currentStatusVal;
	}

	public void setCurrentStatusVal(String currentStatusVal) {
		this.currentStatusVal = currentStatusVal;
	}

	public String getIssueStatusName() {
		return issueStatusName;
	}

	public void setIssueStatusName(String issueStatusName) {
		this.issueStatusName = issueStatusName;
	}

	public Long getIssueStatusId() {
		return issueStatusId;
	}

	public void setIssueStatusId(Long issueStatusId) {
		this.issueStatusId = issueStatusId;
	}

	public List<TypeResponse> getIssueStatusList() {
		return issueStatusList;
	}

	public void setIssueStatusList(List<TypeResponse> issueStatusList) {
		this.issueStatusList = issueStatusList;
	}

	public List<IssueModel> getIssue() {
		return issues;
	}

	public void setIssue(List<IssueModel> issue) {
		this.issues = issue;
	}

	public List<String> getSelectedUnitNos() {
		return selectedUnitNos;
	}

	public void setSelectedUnitNos(List<String> selectedUnitNos) {
		this.selectedUnitNos = selectedUnitNos;
	}

	public List<String> getAllUnitNos() {
		return allUnitNos;
	}

	public void setAllUnitNos(List<String> allUnitNos) {
		this.allUnitNos = allUnitNos;
	}	/*public List<Long> getIssueIds() {
		return issueIds;
	}

	public void setIssueIds(List<Long> issueIds) {
		this.issueIds = issueIds;
	}*/
}
