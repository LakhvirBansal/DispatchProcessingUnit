package com.dpu.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
@Table(name = "issue")
public class Issue implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "issue_id")
	private Long id;
	
	@Column(name = "issue_name")
	private String issueName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vmc_id")
	private VehicleMaintainanceCategory vmc;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_type_id")
	private Type unitType;

	@Column(name = "unit_no")
	private String unitNo;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reported_by_id")
	private Driver reportedBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Type status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public VehicleMaintainanceCategory getVmc() {
		return vmc;
	}

	public void setVmc(VehicleMaintainanceCategory vmc) {
		this.vmc = vmc;
	}

	public Driver getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(Driver reportedBy) {
		this.reportedBy = reportedBy;
	}

	public Type getStatus() {
		return status;
	}

	public void setStatus(Type status) {
		this.status = status;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Type getUnitType() {
		return unitType;
	}

	public void setUnitType(Type unitType) {
		this.unitType = unitType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
