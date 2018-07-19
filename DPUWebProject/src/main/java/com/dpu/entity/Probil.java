package com.dpu.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
@Table(name = "probil")
public class Probil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "probil_id")
	private Long id;
	
	@Column(name = "probil_no")
	private Long probilNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipper_id")
	private Shipper shipper;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consinee_id")
	private Shipper consine;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pickup")
	private Type pickUp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliver")
	private Type delivery;
	
	@Column(name = "pickup_scheduled_date")
	private Date pickupScheduledDate;
	
	@Column(name = "pickup_scheduled_time")
	private Date pickupScheduledTime;
	
	@Column(name = "deliver_scheduled_date")
	private Date deliverScheduledDate;
	
	@Column(name = "deliver_scheduled_time")
	private Date deliverScheduledTime;
	
	@Column(name = "pickup_mab_date")
	private Date pickupMABDate;
	
	@Column(name = "pickup_mab_time")
	private Date pickupMABTime;
	
	@Column(name = "delivery_mab_date")
	private Date deliveryMABDate;
	
	@Column(name = "delivery_mab_time")
	private Date deliveryMABTime;
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = OrderPickupDropNo.class)
	@JoinColumn(name = "probil_id", referencedColumnName = "probil_id")
	private List<OrderPickupDropNo> orderPickupDropNos;
	
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Shipper getShipper() {
		return shipper;
	}

	public void setShipper(Shipper shipper) {
		this.shipper = shipper;
	}

	public Shipper getConsine() {
		return consine;
	}

	public void setConsine(Shipper consine) {
		this.consine = consine;
	}

	public Type getPickUp() {
		return pickUp;
	}

	public void setPickUp(Type pickUp) {
		this.pickUp = pickUp;
	}

	public Type getDelivery() {
		return delivery;
	}

	public void setDelivery(Type delivery) {
		this.delivery = delivery;
	}

	public Date getPickupScheduledDate() {
		return pickupScheduledDate;
	}

	public void setPickupScheduledDate(Date pickupScheduledDate) {
		this.pickupScheduledDate = pickupScheduledDate;
	}

	public Date getPickupScheduledTime() {
		return pickupScheduledTime;
	}

	public void setPickupScheduledTime(Date pickupScheduledTime) {
		this.pickupScheduledTime = pickupScheduledTime;
	}

	public Date getDeliverScheduledDate() {
		return deliverScheduledDate;
	}

	public void setDeliverScheduledDate(Date deliverScheduledDate) {
		this.deliverScheduledDate = deliverScheduledDate;
	}

	public Date getDeliverScheduledTime() {
		return deliverScheduledTime;
	}

	public void setDeliverScheduledTime(Date deliverScheduledTime) {
		this.deliverScheduledTime = deliverScheduledTime;
	}

	public Date getDeliveryMABDate() {
		return deliveryMABDate;
	}

	public void setDeliveryMABDate(Date deliveryMABDate) {
		this.deliveryMABDate = deliveryMABDate;
	}

	public Date getDeliveryMABTime() {
		return deliveryMABTime;
	}

	public void setDeliveryMABTime(Date deliveryMABTime) {
		this.deliveryMABTime = deliveryMABTime;
	}

	public Date getPickupMABDate() {
		return pickupMABDate;
	}

	public void setPickupMABDate(Date pickupMABDate) {
		this.pickupMABDate = pickupMABDate;
	}

	public Date getPickupMABTime() {
		return pickupMABTime;
	}

	public void setPickupMABTime(Date pickupMABTime) {
		this.pickupMABTime = pickupMABTime;
	}

	public List<OrderPickupDropNo> getOrderPickupDropNos() {
		return orderPickupDropNos;
	}

	public void setOrderPickupDropNos(List<OrderPickupDropNo> orderPickupDropNos) {
		this.orderPickupDropNos = orderPickupDropNos;
	}

}
