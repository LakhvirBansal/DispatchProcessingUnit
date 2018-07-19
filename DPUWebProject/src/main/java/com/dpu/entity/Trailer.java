package com.dpu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
@Table(name = "trailer")
public class Trailer {

	@Id
	@Column(name = "trailer_id")
	@GeneratedValue
	private Long trailerId;
	
	@Column(name = "unit_no")
	private String unitNo;
	
	@Column(name = "trailer_usage")
	private String usage;
	
	@Column(name = "owner")
	private String owner;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "division_id")
	private Division division;
	
	@Column(name = "oo_name")
	private String oOName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "terminal_id")
	private Terminal terminal;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trailer_type_id")
	private Type type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;
	
	@Column(name = "finance")
	private String finance;

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getoOName() {
		return oOName;
	}

	public void setoOName(String oOName) {
		this.oOName = oOName;
	}

	public String getFinance() {
		return finance;
	}

	public void setFinance(String finance) {
		this.finance = finance;
	}

	public Long getTrailerId() {
		return trailerId;
	}

	public void setTrailerId(Long trailerId) {
		this.trailerId = trailerId;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	
	
}
