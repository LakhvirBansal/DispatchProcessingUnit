package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.entity.Status;
@JsonIgnoreProperties(ignoreUnknown = true) 
@JsonSerialize(include = Inclusion.NON_NULL)
public class HandlingModel {

 
	private Long id;

	private String name;

	private Long statusId;
	private String statusName;
	private List<Status> statusList;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getStatusId() {
		return statusId;
	}
	
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	
	public String getStatusName() {
		return statusName;
	}
	
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public List<Status> getStatusList() {
		return statusList;
	}
	
	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}
	
}
