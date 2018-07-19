package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.entity.Status;

@JsonSerialize(include = Inclusion.NON_NULL)
public class CustomBrokerResponse {
	
	private Long customBrokerId;
	private String customBrokerName;
	
	private List<TypeResponse> typeList;
	private Long typeId;
	/*private String contactName;
	private String phone;
	private String extention;
	private String faxNumber;
	private String status;
	private Long statusId;
	
	private String email;
	private String website;*/
	
	private List<CustomBrokerTypeModel> customBrokerTypes;
	
	private List<TypeResponse> operationList;
	
	private List<TypeResponse> timeZoneList;
	
	private List<Status> statusList;
	
	
	
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
	public List<TypeResponse> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<TypeResponse> typeList) {
		this.typeList = typeList;
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
	public List<TypeResponse> getOperationList() {
		return operationList;
	}
	public void setOperationList(List<TypeResponse> operationList) {
		this.operationList = operationList;
	}
	public List<TypeResponse> getTimeZoneList() {
		return timeZoneList;
	}
	public void setTimeZoneList(List<TypeResponse> timeZoneList) {
		this.timeZoneList = timeZoneList;
	}
	public List<Status> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}
	
}
