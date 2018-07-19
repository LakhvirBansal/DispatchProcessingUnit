package com.dpu.service;

import java.util.List;

import com.dpu.model.CompanyResponse;
import com.dpu.model.OrderModel;
import com.dpu.model.ProbilModel;
public interface OrderService {

	Object update(Long id, OrderModel orderModel);

	List<OrderModel> getAllOrders();

	OrderModel getOpenAdd();

	CompanyResponse getCompanyData(Long companyId);

	Object addOrder(OrderModel orderModel);

	OrderModel getOrderByOrderId(Long orderId);

	Object deleteOrder(Long orderId);

	List<OrderModel> getOrdersByCompanyName(String companyName);

	ProbilModel getProbilByProbilId(Long orderId, Long probilId);

	Object deleteProbil(Long orderId, Long probilId);

}
