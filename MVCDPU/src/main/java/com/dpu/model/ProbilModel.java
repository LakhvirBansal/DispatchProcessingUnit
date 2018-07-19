package com.dpu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ProbilModel implements Serializable{

	 
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long probilNo;
	
	private String shipperName;
	private Long shipperId;
	private List<ShipperModel> shipperList;
	
	private String consineeName;
	private Long consineeId;
	private List<ShipperModel> consineeList;
	
	private String pickupName;
	private Long pickupId;
	private List<TypeResponse> pickupList;
	
	private String deliveryName;
	private Long deliveryId;
	private List<TypeResponse> deliveryList;
	
	/*private Date pickupScheduledDate;
	
	private Date pickupScheduledTime;
	
	private Date deliverScheduledDate;
	
	private Date deliverScheduledTime;
	
	private Date pickupMABDate;
	
	private Date pickupMABTime;
	
	private Date deliveryMABDate;
	
	private Date deliveryMABTime;*/
	
	private String pickupScheduledDate;
	
	private String pickupScheduledTime;
	
	private String deliverScheduledDate;
	
	private String deliverScheduledTime;
	
	private String pickupMABDate;
	
	private String pickupMABTime;
	
	private String deliveryMABDate;
	
	private String deliveryMABTime;

	private List<OrderPickUpDeliveryModel> OrderPickUpDeliveryList;
	
	// for fetching probils
	private String companyName;
	
	private String billingLocationName;
	
	private String contactName;
	
	private String temperatureType;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProbilNo() {
		return probilNo;
	}

	public void setProbilNo(Long probilNo) {
		this.probilNo = probilNo;
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public Long getShipperId() {
		return shipperId;
	}

	public void setShipperId(Long shipperId) {
		this.shipperId = shipperId;
	}

	public List<ShipperModel> getShipperList() {
		return shipperList;
	}

	public void setShipperList(List<ShipperModel> shipperList) {
		this.shipperList = shipperList;
	}

	public String getConsineeName() {
		return consineeName;
	}

	public void setConsineeName(String consineeName) {
		this.consineeName = consineeName;
	}

	public Long getConsineeId() {
		return consineeId;
	}

	public void setConsineeId(Long consineeId) {
		this.consineeId = consineeId;
	}

	public List<ShipperModel> getConsineeList() {
		return consineeList;
	}

	public void setConsineeList(List<ShipperModel> consineeList) {
		this.consineeList = consineeList;
	}

	public String getPickupName() {
		return pickupName;
	}

	public void setPickupName(String pickupName) {
		this.pickupName = pickupName;
	}

	public Long getPickupId() {
		return pickupId;
	}

	public void setPickupId(Long pickupId) {
		this.pickupId = pickupId;
	}

	public List<TypeResponse> getPickupList() {
		return pickupList;
	}

	public void setPickupList(List<TypeResponse> pickupList) {
		this.pickupList = pickupList;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public List<TypeResponse> getDeliveryList() {
		return deliveryList;
	}

	public void setDeliveryList(List<TypeResponse> deliveryList) {
		this.deliveryList = deliveryList;
	}

	public List<OrderPickUpDeliveryModel> getOrderPickUpDeliveryList() {
		return OrderPickUpDeliveryList;
	}

	public void setOrderPickUpDeliveryList(
			List<OrderPickUpDeliveryModel> orderPickUpDeliveryList) {
		OrderPickUpDeliveryList = orderPickUpDeliveryList;
	}

	public String getPickupScheduledDate() {
		return pickupScheduledDate;
	}

	public void setPickupScheduledDate(String pickupScheduledDate) {
		this.pickupScheduledDate = pickupScheduledDate;
	}

	public String getDeliverScheduledDate() {
		return deliverScheduledDate;
	}

	public void setDeliverScheduledDate(String deliverScheduledDate) {
		this.deliverScheduledDate = deliverScheduledDate;
	}

	public String getPickupMABDate() {
		return pickupMABDate;
	}

	public void setPickupMABDate(String pickupMABDate) {
		this.pickupMABDate = pickupMABDate;
	}

	public String getDeliveryMABDate() {
		return deliveryMABDate;
	}

	public void setDeliveryMABDate(String deliveryMABDate) {
		this.deliveryMABDate = deliveryMABDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPickupScheduledTime() {
		return pickupScheduledTime;
	}

	public void setPickupScheduledTime(String pickupScheduledTime) {
		this.pickupScheduledTime = pickupScheduledTime;
	}

	public String getDeliverScheduledTime() {
		return deliverScheduledTime;
	}

	public void setDeliverScheduledTime(String deliverScheduledTime) {
		this.deliverScheduledTime = deliverScheduledTime;
	}

	public String getPickupMABTime() {
		return pickupMABTime;
	}

	public void setPickupMABTime(String pickupMABTime) {
		this.pickupMABTime = pickupMABTime;
	}

	public String getDeliveryMABTime() {
		return deliveryMABTime;
	}

	public void setDeliveryMABTime(String deliveryMABTime) {
		this.deliveryMABTime = deliveryMABTime;
	}

	
}
