package com.dpu.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendorAdditionalContactsModel {

	////@JsonProperty("add_contact_id")
	private Long vendorAdditionalContactId;

	////@JsonProperty("customer_name")
	private String vendorName;

	//@JsonProperty("position")
	private String position;

	//@JsonProperty("phone")
	private String phone;

	//@JsonProperty("ext")
	private String ext;

	//@JsonProperty("fax")
	private String fax;

	//@JsonProperty("additional_contact_prefix")
	private String prefix;

	//@JsonProperty("cellular")
	private String cellular;

	//@JsonProperty("status")
	private Long statusId;
	private String statusName;

	private String email;

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

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Long getVendorAdditionalContactId() {
		return vendorAdditionalContactId;
	}

	public void setVendorAdditionalContactId(Long vendorAdditionalContactId) {
		this.vendorAdditionalContactId = vendorAdditionalContactId;
	}
}
