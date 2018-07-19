package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class CustomBrokerTypeModel {
	
	private Long customBrokerTypeId;
	private List<Status> statusList;
	private Long statusId;
	private String contactName;
	private List<Type> operationList;
	private Long operationId;
	private String phone;
	private String extention;
	private String faxNumber;
	private List<Type> timeZoneList;
	private Long timeZoneId;
	private String email;
	private String trackerLink;
	private List<Type> typeList;
	private Long typeId;
	private String typeName;
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	public List<Type> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}
	public Long getCustomBrokerTypeId() {
		return customBrokerTypeId;
	}
	public void setCustomBrokerTypeId(Long customBrokerTypeId) {
		this.customBrokerTypeId = customBrokerTypeId;
	}
	public List<Status> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public Long getOperationId() {
		return operationId;
	}
	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getExtention() {
		return extention;
	}
	public void setExtention(String extention) {
		this.extention = extention;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public Long getTimeZoneId() {
		return timeZoneId;
	}
	public void setTimeZoneId(Long timeZoneId) {
		this.timeZoneId = timeZoneId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTrackerLink() {
		return trackerLink;
	}
	public void setTrackerLink(String trackerLink) {
		this.trackerLink = trackerLink;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	
}
