package com.dpu.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class Terminal {
	private Long terminalId;
	private String terminalName;
	private String availableServices;
	private String createdBy;
	private Date createdOn;
	private String modifiedBy;
	private Date modifiedOn;
	private String status;
	private Long statusId;
	private List<Status> statusList;
	private List<Long> serviceIds;
	private List<DPUService> serviceList;
	private Long shipperId;
	private String shipperName;
	private List<Shipper> shipperList;
	
	public List<DPUService> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<DPUService> serviceList) {
		this.serviceList = serviceList;
	}

	public List<Long> getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(List<Long> serviceIds) {
		this.serviceIds = serviceIds;
	}
	
	public Long getTerminalId() {
		return terminalId;
	}
	
	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}
	
	public String getTerminalName() {
		return terminalName;
	}
	
	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}
	
	public String getAvailableServices() {
		return availableServices;
	}
	
	public void setAvailableServices(String availableServices) {
		this.availableServices = availableServices;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	public Long getShipperId() {
		return shipperId;
	}
	public void setShipperId(Long shipperId) {
		this.shipperId = shipperId;
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public List<Shipper> getShipperList() {
		return shipperList;
	}

	public void setShipperList(List<Shipper> shipperList) {
		this.shipperList = shipperList;
	}
}
