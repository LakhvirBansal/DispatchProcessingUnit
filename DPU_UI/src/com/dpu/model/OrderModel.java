package com.dpu.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class OrderModel implements Serializable{

	 
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String companyName;
	private Long companyId;
	private List<Company> companyList;
	
	private String billingLocationName;
	private Long billingLocationId;
	private List<CompanyBillingLocation> billingLocationList;
	
	private String contactName;
	private Long contactId;
	private List<CompanyAdditionalContacts> contactsList;
	
	private String temperatureName;
	private Long temperatureId;
	private List<Type> temperatureList;
	
	private Double temperatureValue;

	private String temperatureTypeName;
	private Long temperatureTypeId;
	private List<Type> temperatureTypeList;
	
	private Double rate;
	
	private Long poNo;
	
	private String currencyName;
	private Long currencyId;
	private List<Type> currencyList;
	
	private List<ProbilModel> probilList;
	
	private List<Shipper> shipperConsineeList;
	
	private List<Type> pickupList;
	
	private List<Type> deliveryList;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getBillingLocationName() {
		return billingLocationName;
	}
	
	public void setBillingLocationName(String billingLocationName) {
		this.billingLocationName = billingLocationName;
	}
	
	public Long getBillingLocationId() {
		return billingLocationId;
	}
	
	public void setBillingLocationId(Long billingLocationId) {
		this.billingLocationId = billingLocationId;
	}
	
	public List<CompanyBillingLocation> getBillingLocationList() {
		return billingLocationList;
	}
	
	public void setBillingLocationList(
			List<CompanyBillingLocation> billingLocationList) {
		this.billingLocationList = billingLocationList;
	}
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public Long getContactId() {
		return contactId;
	}
	
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	
	public List<CompanyAdditionalContacts> getContactsList() {
		return contactsList;
	}
	
	public void setContactsList(List<CompanyAdditionalContacts> contactsList) {
		this.contactsList = contactsList;
	}
	
	public String getTemperatureName() {
		return temperatureName;
	}
	
	public void setTemperatureName(String temperatureName) {
		this.temperatureName = temperatureName;
	}
	
	public Long getTemperatureId() {
		return temperatureId;
	}
	
	public void setTemperatureId(Long temperatureId) {
		this.temperatureId = temperatureId;
	}
	
	public List<Type> getTemperatureList() {
		return temperatureList;
	}
	
	public void setTemperatureList(List<Type> temperatureList) {
		this.temperatureList = temperatureList;
	}
	
	public Double getTemperatureValue() {
		return temperatureValue;
	}
	
	public void setTemperatureValue(Double temperatureValue) {
		this.temperatureValue = temperatureValue;
	}
	public String getTemperatureTypeName() {
		return temperatureTypeName;
	}
	
	public void setTemperatureTypeName(String temperatureTypeName) {
		this.temperatureTypeName = temperatureTypeName;
	}
	
	public Long getTemperatureTypeId() {
		return temperatureTypeId;
	}
	
	public void setTemperatureTypeId(Long temperatureTypeId) {
		this.temperatureTypeId = temperatureTypeId;
	}
	
	public List<Type> getTemperatureTypeList() {
		return temperatureTypeList;
	}
	
	public void setTemperatureTypeList(List<Type> temperatureTypeList) {
		this.temperatureTypeList = temperatureTypeList;
	}
	
	public Double getRate() {
		return rate;
	}
	
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	public Long getPoNo() {
		return poNo;
	}
	
	public void setPoNo(Long poNo) {
		this.poNo = poNo;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}
	
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public Long getCurrencyId() {
		return currencyId;
	}
	
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	
	public List<Type> getCurrencyList() {
		return currencyList;
	}
	
	public void setCurrencyList(List<Type> currencyList) {
		this.currencyList = currencyList;
	}

	public List<ProbilModel> getProbilList() {
		return probilList;
	}

	public void setProbilList(List<ProbilModel> probilList) {
		this.probilList = probilList;
	}

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	public List<Shipper> getShipperConsineeList() {
		return shipperConsineeList;
	}

	public void setShipperConsineeList(List<Shipper> shipperConsineeList) {
		this.shipperConsineeList = shipperConsineeList;
	}

	public List<Type> getPickupList() {
		return pickupList;
	}

	public void setPickupList(List<Type> pickupList) {
		this.pickupList = pickupList;
	}

	public List<Type> getDeliveryList() {
		return deliveryList;
	}

	public void setDeliveryList(List<Type> deliveryList) {
		this.deliveryList = deliveryList;
	}
	
	private Company companyResponse;

	public Company getCompanyResponse() {
		return companyResponse;
	}

	public void setCompanyResponse(Company companyResponse) {
		this.companyResponse = companyResponse;
	}
	
}
