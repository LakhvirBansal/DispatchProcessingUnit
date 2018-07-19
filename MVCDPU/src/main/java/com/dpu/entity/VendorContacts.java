package com.dpu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vendor_additional_contacts")
public class VendorContacts {

	@Id
	@Column(name = "vendor_additional_contact_id")
	@GeneratedValue
	private Long vendorAdditionalContactId;

	@Column(name = "vendor_name")
	private String vendorName;

	@Column(name = "position")
	private String position;

	@Column(name = "phone")
	private String phone;

	@Column(name = "ext")
	private String ext;

	@Column(name = "fax")
	private String fax;

	@Column(name = "additional_contact_prefix")
	private String prefix;

	@Column(name = "cellular")
	private String cellular;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;

	@Column(name = "email")
	private String email;

	/*
	 * @Transient private Map<Integer, List<CompanyWorkingHours>> map = new
	 * HashMap<>();
	 */

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendor_id")
	private Vendor vendorObj;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getCellular() {
		return cellular;
	}

	public void setCellular(String cellular) {
		this.cellular = cellular;
	}

	public String getEmail() {
		return email;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getVendorAdditionalContactId() {
		return vendorAdditionalContactId;
	}

	public void setVendorAdditionalContactId(Long vendorAdditionalContactId) {
		this.vendorAdditionalContactId = vendorAdditionalContactId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Vendor getVendorObj() {
		return vendorObj;
	}

	public void setVendorObj(Vendor vendorObj) {
		this.vendorObj = vendorObj;
	}

	/*
	 * public Vendor getVendor() { return vendor; }
	 * 
	 * public void setVendor(Vendor vendor) { this.vendor = vendor; }
	 */

}
