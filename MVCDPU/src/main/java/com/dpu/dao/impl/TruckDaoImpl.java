package com.dpu.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dpu.dao.TruckDao;
import com.dpu.entity.Category;
import com.dpu.entity.Division;
import com.dpu.entity.Status;
import com.dpu.entity.Terminal;
import com.dpu.entity.Truck;
import com.dpu.entity.Type;
import com.dpu.model.TruckModel;
import com.dpu.service.StatusService;
import com.dpu.util.ValidationUtil;

@Repository
@Transactional
public class TruckDaoImpl extends GenericDaoImpl<Truck> implements TruckDao {

	@Autowired
	StatusService statusService;

	@Override
	public Truck add(Session session, TruckModel truckModel) {
		logger.info("TruckDaoImpl: add(): STARTS");

		Truck truck = setTruckValues(truckModel);

		if (!ValidationUtil.isNull(truckModel.getStatusId())) {
			Status status = (Status) session.get(Status.class, truckModel.getStatusId());
			truck.setStatus(status);
		}

		if (!ValidationUtil.isNull(truckModel.getDivisionId())) {
			Division division = (Division) session.get(Division.class, truckModel.getDivisionId());
			truck.setDivision(division);
		}

		if (!ValidationUtil.isNull(truckModel.getCategoryId())) {
			Category category = (Category) session.get(Category.class, truckModel.getCategoryId());
			truck.setCategory(category);
		}

		if (!ValidationUtil.isNull(truckModel.getTerminalId())) {
			Terminal terminal = (Terminal) session.get(Terminal.class, truckModel.getTerminalId());
			truck.setTerminal(terminal);
		}

		if (!ValidationUtil.isNull(truckModel.getTruckTypeId())) {
			Type type = (Type) session.get(Type.class, truckModel.getTruckTypeId());
			truck.setType(type);
		}

		Long truckId = (Long) session.save(truck);

		truck.setTruckId(truckId);

		logger.info("TruckDaoImpl: add(): ENDS");

		return truck;

	}

	private Truck setTruckValues(TruckModel truckModel) {

		logger.info("TruckDaoImpl: setTruckValues(): STARTS");

		Truck truck = new Truck();
		truck.setUnitNo(truckModel.getUnitNo());
		truck.setOwner(truckModel.getOwner());
		truck.setoOName(truckModel.getoOName());
		truck.setUsage(truckModel.getTruchUsage());
		truck.setFinance(truckModel.getFinance());
		truck.setCreatedBy("jagvir");
		truck.setCreatedOn(new Date());
		truck.setModifiedBy("jagvir");
		truck.setModifiedOn(new Date());

		logger.info("TruckDaoImpl: setTruckValues(): ENDS");

		return truck;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Truck findById(Session session,Long id) {

		 Query query = session.createQuery("from Truck where truckId = "+id);
		 List<Truck> truck = query.list();
		 return truck.get(0);
		  
	}

	@Override
	public void update(Truck truck, Session session) {
		session.update(truck);
		
	}
}
