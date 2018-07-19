package com.dpu.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Sale")
public class Sale {

	@Id
	@Column(name = "sale_id")
	@GeneratedValue
	private Long saleId;

	@Column(name = "name")
	private String name;

	@ManyToOne()
	@JoinColumn(name = "status_id")
	private Status status;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sale")
	private Set<Company> company = new HashSet<>();

	public Set<Company> getCompany() {

		return company;
	}

	public void setCompany(Set<Company> company) {

		this.company = company;
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

	public Status getStatus() {

		return status;
	}

	public void setStatus(Status status) {

		this.status = status;
	}

}
