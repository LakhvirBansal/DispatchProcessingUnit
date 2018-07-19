package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.entity.Status;

/**
 * @author jagvir
 *
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class DPUService {
	
	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setServiceResponse(Integer serviceResponse) {
		this.serviceResponse = serviceResponse;
	}

	public Long getTextFieldId() {
		return textFieldId;
	}

	public void setTextFieldId(Long textFieldId) {
		this.textFieldId = textFieldId;
	}

	public Long getAssociationWithId() {
		return associationWithId;
	}

	public void setAssociationWithId(Long associationWithId) {
		this.associationWithId = associationWithId;
	}

	//@JsonProperty(value = "service_id")
	private Long serviceId;

	//@JsonProperty(value = "service_name")
	private String serviceName;

	//@JsonProperty(value = "service_response")
	private Integer serviceResponse;

	//@JsonProperty(value = "status")
	private String status;
	
	private String textField;
	
	private String associationWith;
	
	private Long statusId;
	
	private Long textFieldId;
	
	private Long associationWithId;
	
	private List<Status> statusList;
	
	private List<TypeResponse> textFieldList;
	
	private List<TypeResponse> associatedWithList;

	public List<Status> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}

	public List<TypeResponse> getTextFieldList() {
		return textFieldList;
	}

	public void setTextFieldList(List<TypeResponse> textFieldList) {
		this.textFieldList = textFieldList;
	}

	public List<TypeResponse> getAssociatedWithList() {
		return associatedWithList;
	}

	public void setAssociatedWithList(List<TypeResponse> associatedWithList) {
		this.associatedWithList = associatedWithList;
	}

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public String getAssociationWith() {
		return associationWith;
	}

	public void setAssociationWith(String associationWith) {
		this.associationWith = associationWith;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getServiceResponse() {
		return serviceResponse;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

}
