package com.dpu.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class RoleModel {

	private Long id;

	private String roleName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArrangedWith() {
		return roleName;
	}

	public void setArrangedWith(String roleName) {
		this.roleName = roleName;
	}

}
