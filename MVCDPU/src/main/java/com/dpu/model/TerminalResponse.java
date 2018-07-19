package com.dpu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.entity.Status;

@JsonSerialize(include = Inclusion.NON_NULL)
public class TerminalResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private List<String> stringServiceIds;

	public TerminalResponse() {
		// TODO Auto-generated constructor stub
	}

	public List<String> getStringServiceIds() {
		return stringServiceIds;
	}

	public void setStringServiceIds(List<String> stringServiceIds) {
		this.stringServiceIds = stringServiceIds;
	}

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

	private List<ShipperModel> shipperList;

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

	public List<ShipperModel> getShipperList() {
		return shipperList;
	}

	public void setShipperList(List<ShipperModel> shipperList) {
		this.shipperList = shipperList;
	}
}
