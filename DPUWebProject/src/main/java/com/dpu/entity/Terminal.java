package com.dpu.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
@Table(name = "terminalmaster")
public class Terminal {

	@Id
	@Column(name = "terminal_id")
	@GeneratedValue
	private Long terminalId;

	@Column(name = "terminal_name")
	//@JsonProperty(value = "terminal_name")
	private String terminalName;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "terminalservice", joinColumns = { @JoinColumn(name = "terminal_id")},inverseJoinColumns={@JoinColumn(name="service_id")})
	private Set<Service> services = new HashSet<Service>();

	@Column(name = "created_by")
	//@JsonProperty(value = "created_by")
	private String createdBy;

	@Column(name = "created_on")
	//@JsonProperty(value = "created_on")
	private Date createdOn;

	@Column(name = "modified_by")
	//@JsonProperty(value = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_on")
	//@JsonProperty(value = "modified_on")
	private Date modifiedOn;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipper_id")
	private Shipper shipper;
	
	public Set<Service> getServices() {
		return services;
	}

	public void setServices(Set<Service> services) {
		this.services = services;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Shipper getShipper() {
		return shipper;
	}

	public void setShipper(Shipper shipper) {
		this.shipper = shipper;
	}
//	public Status getStatusmaster() {
//		return statusmaster;
//	}
//
//	public void setStatusmaster(Status statusmaster) {
//		this.statusmaster = statusmaster;
//	}

	
	
	
}
