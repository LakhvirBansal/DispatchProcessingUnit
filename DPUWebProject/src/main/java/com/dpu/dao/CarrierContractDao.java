package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.CarrierContract;

public interface CarrierContractDao extends GenericDao<CarrierContract> {

	List<CarrierContract> findAllCarrierContract(Session session);

}
