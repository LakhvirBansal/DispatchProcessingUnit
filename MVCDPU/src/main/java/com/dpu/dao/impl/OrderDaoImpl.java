package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.OrderDao;
import com.dpu.entity.Category;
import com.dpu.entity.Order;
import com.dpu.entity.OrderPickupDropNo;
import com.dpu.entity.Probil;

@Repository
public class OrderDaoImpl extends GenericDaoImpl<Category> implements OrderDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findAll(Session session) {
		
		StringBuilder sb = new StringBuilder("select o from Order o join fetch o.company join fetch o.billingLocation join fetch o.contact ")
		.append(" join fetch o.temperature join fetch o.temperatureType join fetch o.currency ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public Order findByOrderId(Long orderId, Session session) {
		StringBuilder sb = new StringBuilder("select o from Order o join fetch o.company join fetch o.billingLocation join fetch o.contact ")
		.append(" join fetch o.temperature join fetch o.temperatureType join fetch o.currency where o.id =:orderId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("orderId", orderId);
		return (Order) query.uniqueResult();
	}

	@Override
	public void saveOrder(Session session, Order order) {
		session.save(order);
		
	}

	@Override
	public void saveProbil(Session session, Probil probil) {
		session.save(probil);
		
	}
	
	@Override
	public Long getMaxProbilNo(Session session) {
		Long returnVal = 0l;
		Long maxVal = (Long) session.createQuery(" select max(probil.probilNo) from Probil probil ").uniqueResult();
		if(maxVal != null){
			returnVal = maxVal;
		}
		//System.out.println("the max probil no is :"+maxVal);
		return returnVal;
	}

	@Override
	public void savePickUpDrop(Session session, OrderPickupDropNo pickUpDropNo) {
		session.save(pickUpDropNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findOrderByCompanyName(Session session, String companyName) {
		
		StringBuilder sb = new StringBuilder("select o from Order o join fetch o.company c join fetch o.billingLocation join fetch o.contact ")
		.append(" join fetch o.temperature join fetch o.temperatureType join fetch o.currency where c.name like :companyName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("companyName", "%"+companyName+"%");
		return query.list();
	}

	@Override
	public Probil getProbilData(Long orderId, Long probilId, Session session) {

		StringBuilder sb = new StringBuilder(" select p from Probil p join fetch p.shipper join fetch p.consine join fetch p.pickUp ")
		.append(" join fetch p.delivery where p.order.id = :orderId and p.id =:probilId ");
		
		Query query = session.createQuery(sb.toString());
		query.setParameter("orderId", orderId);
		query.setParameter("probilId", probilId);
		return (Probil) query.uniqueResult();
	}

	@Override
	public Probil getProbilByProbilId(Long orderId, Long probilId, Session session) {

		StringBuilder sb = new StringBuilder(" select p from Probil p  ")
		.append(" where p.order.id = :orderId and p.id =:probilId ");
		
		Query query = session.createQuery(sb.toString());
		query.setParameter("orderId", orderId);
		query.setParameter("probilId", probilId);
		return (Probil) query.uniqueResult();
	}

	@Override
	public void updateOrder(Session session, Order order) {
		session.saveOrUpdate(order);
	}

	@Override
	public void updateProbil(Session session, Probil probil) {
		session.saveOrUpdate(probil);
	}

	@Override
	public void updatePickUpDrop(Session session, OrderPickupDropNo pickUpDropNo) {
		session.saveOrUpdate(pickUpDropNo);
	}

	

}
