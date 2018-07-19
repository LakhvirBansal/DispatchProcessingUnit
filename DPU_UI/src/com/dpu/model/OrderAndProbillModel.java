package com.dpu.model;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class OrderAndProbillModel implements Serializable{

	 private OrderModel orderModel;
	 private ProbilModel probilModel;
	public OrderModel getOrderModel() {
		return orderModel;
	}
	public void setOrderModel(OrderModel orderModel) {
		this.orderModel = orderModel;
	}
	public ProbilModel getProbilModel() {
		return probilModel;
	}
	public void setProbilModel(ProbilModel probilModel) {
		this.probilModel = probilModel;
	}
}
