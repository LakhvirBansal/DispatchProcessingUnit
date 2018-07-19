package com.dpu.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class CurrencyModel {

	private Long id;

	private String currencyName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArrangedWith() {
		return currencyName;
	}

	public void setArrangedWith(String currencyName) {
		this.currencyName = currencyName;
	}
}
