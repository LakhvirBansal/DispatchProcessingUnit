package com.dpu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.entity.Status;

@JsonSerialize(include = Inclusion.NON_NULL)
public class TruckModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long truckId;
	private String truchUsage;
	private String owner;
	private String oOName;

	private String finance;
	private String unitNo;
	private Date createdOn;
	private String createdBy;
	private String modifiedBy;
	private Date modifiedOn;

	private String divisionName;
	private Long divisionId;
	private List<DivisionReq> divisionList;

	private String terminalName;
	private Long terminalId;
	private List<TerminalResponse> terminalList;

	private String catogoryName;
	private Long categoryId;
	private List<CategoryModel> categoryList;

	private String statusName;
	private Long statusId;
	private List<Status> statusList;

	private Long truckTypeId;
	private String truckType;
	private List<TypeResponse> truckTypeList;
	
	public List<TypeResponse> getTruckTypeList() {
		return truckTypeList;
	}

	public void setTruckTypeList(List<TypeResponse> truckTypeList) {
		this.truckTypeList = truckTypeList;
	}
/*
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}*/

	public List<DivisionReq> getDivisionList() {
		return divisionList;
	}

	public void setDivisionList(List<DivisionReq> divisionList) {
		this.divisionList = divisionList;
	}

	public List<TerminalResponse> getTerminalList() {
		return terminalList;
	}

	public void setTerminalList(List<TerminalResponse> terminalList) {
		this.terminalList = terminalList;
	}

	public List<CategoryModel> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryModel> categoryList) {
		this.categoryList = categoryList;
	}

	public Long getTruckTypeId() {
		return truckTypeId;
	}

	public void setTruckTypeId(Long truckTypeId) {
		this.truckTypeId = truckTypeId;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getCatogoryName() {
		return catogoryName;
	}

	public void setCatogoryName(String catogoryName) {
		this.catogoryName = catogoryName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public List<Status> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}

	public String getTruchUsage() {
		return truchUsage;
	}

	public void setTruchUsage(String truchUsage) {
		this.truchUsage = truchUsage;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getoOName() {
		return oOName;
	}

	public void setoOName(String oOName) {
		this.oOName = oOName;
	}

	public String getTruckType() {
		return truckType;
	}

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}

	public String getFinance() {
		return finance;
	}

	public void setFinance(String finance) {
		this.finance = finance;
	}

	public Long getTruckId() {
		return truckId;
	}

	public void setTruckId(Long truckId) {
		this.truckId = truckId;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

}
