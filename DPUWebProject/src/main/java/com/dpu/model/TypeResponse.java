package com.dpu.model;

import org.codehaus.jackson.annotate.JsonIgnore;

public class TypeResponse {

	private Long typeId;

	private String typeName;

	@JsonIgnore
	private Long typeValue;

	public Long getTypeValue() {

		return typeValue;
	}

	public void setTypeValue(Long typeValue) {

		this.typeValue = typeValue;
	}

	public Long getTypeId() {

		return typeId;
	}

	public void setTypeId(Long typeId) {

		this.typeId = typeId;
	}

	public String getTypeName() {

		return typeName;
	}

	public void setTypeName(String typeName) {

		this.typeName = typeName;
	}

}
