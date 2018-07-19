package com.dpu.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class IssueModel implements Serializable{

	 
	private static final long serialVersionUID = 1L;

	private Long id;

	private String title;
	
	private String description;
	
	private String vmcName;
	private Long vmcId;
	private List<VehicleMaintainanceCategoryModel> vmcList;
	
	private String categoryName;
	private Long categoryId;
	private List<CategoryModel> categoryList;
	
	private String unitTypeName;
	private Long unitTypeId;
	private List<TypeResponse> unitTypeList;
	
	private String unitNo;
	//private Long unitId;
	//private List<TypeResponse> deliveryList;
	
	private String reportedByName;
	private Long reportedById;
	private List<DriverModel> reportedByList;
	
	private String statusName;
	private Long statusId;
	private List<TypeResponse> statusList;
	
	private List<String> unitNos;

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

	public String getVmcName() {
		return vmcName;
	}

	public void setVmcName(String vmcName) {
		this.vmcName = vmcName;
	}

	public Long getVmcId() {
		return vmcId;
	}

	public void setVmcId(Long vmcId) {
		this.vmcId = vmcId;
	}

	public List<VehicleMaintainanceCategoryModel> getVmcList() {
		return vmcList;
	}

	public void setVmcList(List<VehicleMaintainanceCategoryModel> vmcList) {
		this.vmcList = vmcList;
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

	public String getReportedByName() {
		return reportedByName;
	}

	public void setReportedByName(String reportedByName) {
		this.reportedByName = reportedByName;
	}

	public Long getReportedById() {
		return reportedById;
	}

	public void setReportedById(Long reportedById) {
		this.reportedById = reportedById;
	}

	public List<DriverModel> getReportedByList() {
		return reportedByList;
	}

	public void setReportedByList(List<DriverModel> reportedByList) {
		this.reportedByList = reportedByList;
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

	public List<String> getUnitNos() {
		return unitNos;
	}

	public void setUnitNos(List<String> unitNos) {
		this.unitNos = unitNos;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
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

	public List<TypeResponse> getUnitTypeList() {
		return unitTypeList;
	}

	public void setUnitTypeList(List<TypeResponse> unitTypeList) {
		this.unitTypeList = unitTypeList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
