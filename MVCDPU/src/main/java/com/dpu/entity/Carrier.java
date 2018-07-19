package com.dpu.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "carrier")
public class Carrier {

	/**
	 * 
	 */

	@Id
	@Column(name = "carrier_id")
	@GeneratedValue
	private Long carrierId;

	@Column(name = "carrier_name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "unit_no")
	private String unitNo;

	@Column(name = "city")
	private String city;

	@Column(name = "province_state")
	private String provinceState;

	@Column(name = "zip")
	private String zip;

	@Column(name = "website")
	private String website;

	@Column(name = "contact")
	private String contact;

	@Column(name = "position")
	private String position;

	@Column(name = "phone")
	private String phone;

	@Column(name = "ext")
	private String ext;

	@Column(name = "fax")
	private String fax;

	@Column(name = "prefix")
	private String prefix;

	@Column(name = "tollfree")
	private String tollfree;

	@Column(name = "cellular")
	private String cellular;

	@Column(name = "email")
	private String email;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "carrier")
	private List<CarrierAdditionalContacts> carrierAdditionalContacts;

	public Long getCarrierId() {

		return carrierId;
	}

	public void setCarrierId(Long carrierId) {

		this.carrierId = carrierId;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getAddress() {

		return address;
	}

	public void setAddress(String address) {

		this.address = address;
	}

	public String getUnitNo() {

		return unitNo;
	}

	public void setUnitNo(String unitNo) {

		this.unitNo = unitNo;
	}

	public String getCity() {

		return city;
	}

	public void setCity(String city) {

		this.city = city;
	}

	public String getProvinceState() {

		return provinceState;
	}

	public void setProvinceState(String provinceState) {

		this.provinceState = provinceState;
	}

	public String getZip() {

		return zip;
	}

	public void setZip(String zip) {

		this.zip = zip;
	}

	public String getWebsite() {

		return website;
	}

	public void setWebsite(String website) {

		this.website = website;
	}

	public String getContact() {

		return contact;
	}

	public void setContact(String contact) {

		this.contact = contact;
	}

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

	public String getTollfree() {

		return tollfree;
	}

	public void setTollfree(String tollfree) {

		this.tollfree = tollfree;
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

	public List<CarrierAdditionalContacts> getCarrierAdditionalContact() {

		return carrierAdditionalContacts;
	}

	public void setCarrierAdditionalContact(
			List<CarrierAdditionalContacts> carrierAdditionalContacts) {

		this.carrierAdditionalContacts = carrierAdditionalContacts;
	}

}
