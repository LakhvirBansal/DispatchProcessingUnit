package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.entity.Status;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ShipperModel {

	private Long shipperId;

	private String locationName;

	private String address;

	private String unit;

	private String city;

	private String postalZip;

	private String zone;

	private String leadTime;

	private String timeZone;

	private String contact;

	private String position;

	private String phone;

	private String ext;

	private String fax;

	private String prefix;

	private String tollFree;

	private String plant;

	private String email;

	private String importer;

	private String internalNotes;

	private String standardNotes;
	
	private List<CompanyResponse> companyList;
	
	private String status;
	private Long statusId;
	private List<Status> statusList;
	
	private String stateName;
	private Long stateId;
	private List<CountryStateCityModel> stateList;
	
	private String countryName;
	private Long countryId;
	private List<CountryStateCityModel> countryList;

	public List<CompanyResponse> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<CompanyResponse> companyList) {
		this.companyList = companyList;
	}

	public Long getShipperId() {
		return shipperId;
	}

	public void setShipperId(Long shipperId) {
		this.shipperId = shipperId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalZip() {
		return postalZip;
	}

	public void setPostalZip(String postalZip) {
		this.postalZip = postalZip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
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

	public String getTollFree() {
		return tollFree;
	}

	public void setTollFree(String tollFree) {
		this.tollFree = tollFree;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImporter() {
		return importer;
	}

	public void setImporter(String importer) {
		this.importer = importer;
	}

	public String getInternalNotes() {
		return internalNotes;
	}

	public void setInternalNotes(String internalNotes) {
		this.internalNotes = internalNotes;
	}

	public String getStandardNotes() {
		return standardNotes;
	}

	public void setStandardNotes(String standardNotes) {
		this.standardNotes = standardNotes;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public List<Status> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public List<CountryStateCityModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<CountryStateCityModel> stateList) {
		this.stateList = stateList;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public List<CountryStateCityModel> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<CountryStateCityModel> countryList) {
		this.countryList = countryList;
	}
}