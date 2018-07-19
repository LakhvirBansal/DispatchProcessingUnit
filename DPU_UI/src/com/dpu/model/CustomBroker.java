package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class CustomBroker {
	
	private Long customBrokerId;
	private String customBrokerName;
	private List<Type> typeList;
	private Long typeId;
	private List<CustomBrokerTypeModel> customBrokerTypes;
	private List<Type> operationList;
	private List<Type> timeZoneList;
	private List<Status> statusList;
	
	public List<Type> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}
	public List<Type> getOperationList() {
		return operationList;
	}
	public void setOperationList(List<Type> operationList) {
		this.operationList = operationList;
	}
	public List<Type> getTimeZoneList() {
		return timeZoneList;
	}
	public void setTimeZoneList(List<Type> timeZoneList) {
		this.timeZoneList = timeZoneList;
	}
	public Long getCustomBrokerId() {
		return customBrokerId;
	}
	public void setCustomBrokerId(Long customBrokerId) {
		this.customBrokerId = customBrokerId;
	}
	
	
	public String getCustomBrokerName() {
		return customBrokerName;
	}
	public void setCustomBrokerName(String customBrokerName) {
		this.customBrokerName = customBrokerName;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public List<CustomBrokerTypeModel> getCustomBrokerTypes() {
		return customBrokerTypes;
	}
	public void setCustomBrokerTypes(List<CustomBrokerTypeModel> customBrokerTypes) {
		this.customBrokerTypes = customBrokerTypes;
	}
	public List<Status> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}
}
