package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Trailer {

	private Long trailerId;
	private Long unitNo;
	private String usage;
	private String owner;
	private String division;
	private Long divisionId;
	private List<Division> divisionList;
	private String oOName;
	private String terminal;
	private Long terminalId;
	private List<Terminal> terminalList;
	private String category;
	private Long categoryId;
	private List<Category> categoryList;
	private String trailerType;
	private Long trailerTypeId;
	private List<Type> trailerTypeList;
	private String status;
	private Long statusId;
	private List<Status> statusList;
	private String finance;
	
	public List<Division> getDivisionList() {
		return divisionList;
	}

	public void setDivisionList(List<Division> divisionList) {
		this.divisionList = divisionList;
	}

	public List<Terminal> getTerminalList() {
		return terminalList;
	}

	public void setTerminalList(List<Terminal> terminalList) {
		this.terminalList = terminalList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Type> getTrailerTypeList() {
		return trailerTypeList;
	}

	public void setTrailerTypeList(List<Type> trailerTypeList) {
		this.trailerTypeList = trailerTypeList;
	}

	public List<Status> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}

	public String getFinance() {
		return finance;
	}

	public void setFinance(String finance) {
		this.finance = finance;
	}

	public Long getTrailerId() {
		return trailerId;
	}

	public void setTrailerId(Long trailerId) {
		this.trailerId = trailerId;
	}

	public Long getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(Long unitNo) {
		this.unitNo = unitNo;
	}

	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}

	
	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	

	public Long getTrailerTypeId() {
		return trailerTypeId;
	}

	public void setTrailerTypeId(Long trailerTypeId) {
		this.trailerTypeId = trailerTypeId;
	}

	public Long getStatusId() {
		return statusId;
	}

	

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	



	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getoOName() {
		return oOName;
	}

	public void setoOName(String oOName) {
		this.oOName = oOName;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTrailerType() {
		return trailerType;
	}

	public void setTrailerType(String trailerType) {
		this.trailerType = trailerType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}