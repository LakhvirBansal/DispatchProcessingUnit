package com.dpu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.entity.Status;

@JsonSerialize(include = Inclusion.NON_NULL)
public class DriverModel implements Serializable{

	 
	private static final long serialVersionUID = 1L;

	private Long driverId;

	private String driverCode;

	private String firstName;

	private String lastName;

	private String address;

	private String unit;
	
	private String city;
 
	private String postalCode;

	private String email;

	private String home;
	 
	private String faxNo;

	private String cellular;
	 
	private String pager;
	
	private String fullName;
	
	private String createdBy;
	 
	private Date createdOn;
	 
	private String divisionName;
	private Long divisionId;
	private List<DivisionReq> divisionList;

	private String terminalName;
	private Long terminalId;
	private List<TerminalResponse> terminalList;

	private String categoryName;
	private Long categoryId;
	private List<CategoryModel> categoryList;
	 
	private String roleName;
	private Long roleId;
	private List<TypeResponse> roleList;
	 
	private String statusName;
	private Long statusId;
	private List<Status> statusList;
	 
	private String driverClassName;
	private Long driverClassId;
	private List<TypeResponse> driverClassList;
	
	private String stateName;
	private Long stateId;
	private List<CountryStateCityModel> stateList;
	
	private String countryName;
	private Long countryId;
	private List<CountryStateCityModel> countryList;
	
	public List<DivisionReq> getDivisionList() {
		return divisionList;
	}

	public void setDivisionList(List<DivisionReq> divisionList) {
		this.divisionList = divisionList;
	}

	public List<TerminalResponse> getTerminalList() {
		return terminalList;
	}

	public void setTerminalList(List<TerminalResponse> terminalList) {
		this.terminalList = terminalList;
	}

	public List<CategoryModel> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryModel> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Status> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}

	public List<TypeResponse> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<TypeResponse> roleList) {
		this.roleList = roleList;
	}

	public List<TypeResponse> getDriverClassList() {
		return driverClassList;
	}

	public void setDriverClassList(List<TypeResponse> driverClassList) {
		this.driverClassList = driverClassList;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getCellular() {
		return cellular;
	}

	public void setCellular(String cellular) {
		this.cellular = cellular;
	}

	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public Long getDriverClassId() {
		return driverClassId;
	}

	public void setDriverClassId(Long driverClassId) {
		this.driverClassId = driverClassId;
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

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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
