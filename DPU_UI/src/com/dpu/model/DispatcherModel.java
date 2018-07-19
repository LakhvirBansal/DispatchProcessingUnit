package com.dpu.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DispatcherModel {
	private Long id;

	private String arrangedWith;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArrangedWith() {
		return arrangedWith;
	}

	public void setArrangedWith(String arrangedWith) {
		this.arrangedWith = arrangedWith;
	}
}
