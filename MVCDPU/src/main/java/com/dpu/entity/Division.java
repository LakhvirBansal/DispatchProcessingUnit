/**
 * 
 */
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author jagvir
 *
 */

@Entity
@Table(name = "division")
// @JsonSerialize(include = Inclusion.NON_NULL)
public class Division {

	@Id
	@Column(name = "division_id")
	@GeneratedValue
	private long divisionId;

	@Column(name = "division_code")
	private String divisionCode;

	@Column(name = "division_name")
	private String divisionName;

	@Column(name = "federal")
	private String fedral;

	@Column(name = "provincial")
	private String provincial;

	@Column(name = "SCAC")
	private String SCAC;

	@Column(name = "carrier_code")
	private String carrierCode;

	@Column(name = "contact_prefix")
	private String contractPrefix;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "division")
	private Set<Company> company = new HashSet<>();

	@Column(name = "invoice_prefix")
	private String invoicePrefix;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_on")
	private Date modifiedOn;

	public Set<Company> getCompany() {

		return company;
	}

	public void setCompany(Set<Company> company) {

		this.company = company;
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

	public long getDivisionId() {

		return divisionId;
	}

	public void setDivisionId(long divisionId) {

		this.divisionId = divisionId;
	}

	public Status getStatus() {

		return status;
	}

	public void setStatus(Status status) {

		this.status = status;
	}

	public String getDivisionCode() {

		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {

		this.divisionCode = divisionCode;
	}

	public String getDivisionName() {

		return divisionName;
	}

	public void setDivisionName(String divisionName) {

		this.divisionName = divisionName;
	}

	public String getFedral() {

		return fedral;
	}

	public void setFedral(String fedral) {

		this.fedral = fedral;
	}

	public String getProvincial() {

		return provincial;
	}

	public void setProvincial(String provincial) {

		this.provincial = provincial;
	}

	public String getSCAC() {

		return SCAC;
	}

	public void setSCAC(String sCAC) {

		SCAC = sCAC;
	}

	public String getCarrierCode() {

		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {

		this.carrierCode = carrierCode;
	}

	public String getContractPrefix() {

		return contractPrefix;
	}

	public void setContractPrefix(String contractPrefix) {

		this.contractPrefix = contractPrefix;
	}

	public String getInvoicePrefix() {

		return invoicePrefix;
	}

	public void setInvoicePrefix(String invoicePrefix) {

		this.invoicePrefix = invoicePrefix;
	}

	public Date getCreatedOn() {

		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {

		this.createdOn = createdOn;
	}

	public String getCreatedBy() {

		return createdBy;
	}

	public void setCreatedBy(String createdBy) {

		this.createdBy = createdBy;
	}

}
