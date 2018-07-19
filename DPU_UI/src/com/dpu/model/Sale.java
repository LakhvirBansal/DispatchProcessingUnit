package com.dpu.model;

public class Sale {

	private Long saleId;

	private String name;

	private String status;

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public Long getSaleId() {

		return saleId;
	}

	public void setSaleId(Long saleId) {

		this.saleId = saleId;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

}
