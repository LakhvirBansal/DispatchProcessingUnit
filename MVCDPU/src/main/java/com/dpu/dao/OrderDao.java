package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Category;
import com.dpu.entity.Order;
import com.dpu.entity.OrderPickupDropNo;
import com.dpu.entity.Probil;

public interface OrderDao extends GenericDao<Category> {

	List<Order> findAll(Session session);

	void saveOrder(Session session, Order order);

	void saveProbil(Session session, Probil probil);

	void savePickUpDrop(Session session, OrderPickupDropNo pickUpDropNo);

	Long getMaxProbilNo(Session session);

	Order findByOrderId(Long orderId, Session session);

	List<Order> findOrderByCompanyName(Session session, String companyName);

	Probil getProbilData(Long orderId, Long probilId, Session session);

	Probil getProbilByProbilId(Long orderId, Long probilId, Session session);

	void updateOrder(Session session, Order order);

	void updateProbil(Session session, Probil probil);

	void updatePickUpDrop(Session session, OrderPickupDropNo pickUpDropNo);

}
