package com.dpu.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarrierContractModel {

	
	private Long contractNoId;

	private String contractNo;

	private Double contractRate;

	private String carrierRat;

	private String hours;

	private String miles;

	private Date dispatched;

	private Long createdBy;

	private String insExpires;

	private String cargo;

	private String liabity;

	private String transDoc;

	private String mCno;

	private String dOTno;

	private String carrierName;
	private Long carrierId;
	private List<CarrierModel> carrierList;
	
	private String arrangedWithName;
	private Long arrangedWithId;
	private List<ArrangedWithModel> arrangedWithList;
	

	private String driverName;
	private Long driverId;
	private List<DriverModel> driverList;

	private String currencyName;
	private Long currencyId;
	private List<TypeResponse> currencyList;

	private String categoryName;
	private Long categoryId;
	private List<CategoryModel> categoryList;

	private String roleName;
	private Long roleId;
	private List<TypeResponse> roleList;

	private String equipmentName;
	private Long equipmentId;
	private List<EquipmentReq> equipmentList;

	private String commodityName;
	private Long commodityId;
	private List<TypeResponse> commodityList;

	private String divisionName;
	private Long divisionId;
	private List<DivisionReq> divisionList;

	private String dispatcherName;
	private Long dispatcherId;
	private List<DispatcherModel> dispatcherList;

	public Long getContractNoId() {
		return contractNoId;
	}

	public void setContractNoId(Long contractNoId) {
		this.contractNoId = contractNoId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Double getContractRate() {
		return contractRate;
	}

	public void setContractRate(Double contractRate) {
		this.contractRate = contractRate;
	}

	public String getCarrierRat() {
		return carrierRat;
	}

	public void setCarrierRat(String carrierRat) {
		this.carrierRat = carrierRat;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getMiles() {
		return miles;
	}

	public void setMiles(String miles) {
		this.miles = miles;
	}

	public Date getDispatched() {
		return dispatched;
	}

	public void setDispatched(Date dispatched) {
		this.dispatched = dispatched;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getInsExpires() {
		return insExpires;
	}

	public void setInsExpires(String insExpires) {
		this.insExpires = insExpires;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getLiabity() {
		return liabity;
	}

	public void setLiabity(String liabity) {
		this.liabity = liabity;
	}

	public String getTransDoc() {
		return transDoc;
	}

	public void setTransDoc(String transDoc) {
		this.transDoc = transDoc;
	}

	public String getmCno() {
		return mCno;
	}

	public void setmCno(String mCno) {
		this.mCno = mCno;
	}

	public String getdOTno() {
		return dOTno;
	}

	public void setdOTno(String dOTno) {
		this.dOTno = dOTno;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getArrangedWithName() {
		return arrangedWithName;
	}

	public void setArrangedWithName(String arrangedWithName) {
		this.arrangedWithName = arrangedWithName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getDispatcherName() {
		return dispatcherName;
	}

	public void setDispatcherName(String dispatcherName) {
		this.dispatcherName = dispatcherName;
	}

	
	public Long getCarrierId() {
	
		return carrierId;
	}

	
	public void setCarrierId(Long carrierId) {
	
		this.carrierId = carrierId;
	}

	
	public List<CarrierModel> getCarrierList() {
	
		return carrierList;
	}

	
	public void setCarrierList(List<CarrierModel> carrierList) {
	
		this.carrierList = carrierList;
	}

	
	public Long getArrangedWithId() {
	
		return arrangedWithId;
	}

	
	public void setArrangedWithId(Long arrangedWithId) {
	
		this.arrangedWithId = arrangedWithId;
	}

	 
	public List<ArrangedWithModel> getArrangedWithList() {
	
		return arrangedWithList;
	}

	
	public void setArrangedWithList(List<ArrangedWithModel> arrangedWithList) {
	
		this.arrangedWithList = arrangedWithList;
	}

	public Long getDriverId() {
	
		return driverId;
	}

	
	public void setDriverId(Long driverId) {
	
		this.driverId = driverId;
	}

	
	public List<DriverModel> getDriverList() {
	
		return driverList;
	}

	
	public void setDriverList(List<DriverModel> driverList) {
	
		this.driverList = driverList;
	}

	
	public Long getCurrencyId() {
	
		return currencyId;
	}

	
	public void setCurrencyId(Long currencyId) {
	
		this.currencyId = currencyId;
	}

	
	public List<TypeResponse> getCurrencyList() {
	
		return currencyList;
	}

	
	public void setCurrencyList(List<TypeResponse> currencyList) {
	
		this.currencyList = currencyList;
	}

	
	public Long getCategoryId() {
	
		return categoryId;
	}

	
	public void setCategoryId(Long categoryId) {
	
		this.categoryId = categoryId;
	}

	
	public List<CategoryModel> getCategoryList() {
	
		return categoryList;
	}

	
	public void setCategoryList(List<CategoryModel> categoryList) {
	
		this.categoryList = categoryList;
	}

	
	public Long getRoleId() {
	
		return roleId;
	}

	
	public void setRoleId(Long roleId) {
	
		this.roleId = roleId;
	}

	
	public List<TypeResponse> getRoleList() {
	
		return roleList;
	}

	
	public void setRoleList(List<TypeResponse> roleList) {
	
		this.roleList = roleList;
	}

	
	public Long getEquipmentId() {
	
		return equipmentId;
	}

	
	public void setEquipmentId(Long equipmentId) {
	
		this.equipmentId = equipmentId;
	}

	
	public List<EquipmentReq> getEquipmentList() {
	
		return equipmentList;
	}

	
	public void setEquipmentList(List<EquipmentReq> equipmentList) {
	
		this.equipmentList = equipmentList;
	}

	
	public Long getCommodityId() {
	
		return commodityId;
	}

	
	public void setCommodityId(Long commodityId) {
	
		this.commodityId = commodityId;
	}

	
	public List<TypeResponse> getCommodityList() {
	
		return commodityList;
	}

	
	public void setCommodityList(List<TypeResponse> commodityList) {
	
		this.commodityList = commodityList;
	}

	
	public Long getDivisionId() {
	
		return divisionId;
	}

	
	public void setDivisionId(Long divisionId) {
	
		this.divisionId = divisionId;
	}

	
	public List<DivisionReq> getDivisionList() {
	
		return divisionList;
	}

	
	public void setDivisionList(List<DivisionReq> divisionList) {
	
		this.divisionList = divisionList;
	}

	
	public Long getDispatcherId() {
	
		return dispatcherId;
	}

	
	public void setDispatcherId(Long dispatcherId) {
	
		this.dispatcherId = dispatcherId;
	}

	
	public List<DispatcherModel> getDispatcherList() {
	
		return dispatcherList;
	}

	
	public void setDispatcherList(List<DispatcherModel> dispatcherList) {
	
		this.dispatcherList = dispatcherList;
	}
	
	

}
