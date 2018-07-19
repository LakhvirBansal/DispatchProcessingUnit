package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dpu.dao.VehicleMaintainanceCategoryDao;
import com.dpu.entity.VehicleMaintainanceCategory;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.VehicleMaintainanceCategoryModel;
import com.dpu.service.StatusService;
import com.dpu.service.VehicleMaintainanceCategoryService;

@Component
public class VehicleMaintainanceCategoryServiceImpl implements VehicleMaintainanceCategoryService {

	Logger logger = Logger.getLogger(VehicleMaintainanceCategoryServiceImpl.class);

	@Autowired
	VehicleMaintainanceCategoryDao vehicleMaintainanceCategoryDao;

	@Autowired
	StatusService statusService;

	@Autowired
	SessionFactory sessionFactory;

	@Value("${vmc_added_message}")
	private String vmc_added_message;

	@Value("${vmc_unable_to_add_message}")
	private String vmc_unable_to_add_message;

	@Value("${vmc_deleted_message}")
	private String vmc_deleted_message;

	@Value("${vmc_unable_to_delete_message}")
	private String vmc_unable_to_delete_message;

	@Value("${vmc_updated_message}")
	private String vmc_updated_message;

	@Value("${vmc_unable_to_update_message}")
	private String vmc_unable_to_update_message;

	@Value("${vmc_already_used_message}")
	private String vmc_already_used_message;

	@Override
	public List<VehicleMaintainanceCategoryModel> getAll() {

		logger.info("VehicleMaintainanceCategoryServiceImpl getAll() starts ");
		Session session = null;
		List<VehicleMaintainanceCategoryModel> vehicleMaintainancecategoryList = new ArrayList<VehicleMaintainanceCategoryModel>();

		try {
			session = sessionFactory.openSession();
			List<VehicleMaintainanceCategory> vmcList = vehicleMaintainanceCategoryDao.findAll(session);

			if (vmcList != null && !vmcList.isEmpty()) {
				for (VehicleMaintainanceCategory category : vmcList) {
					VehicleMaintainanceCategoryModel vehicleMaintainanceCategoryModel = new VehicleMaintainanceCategoryModel();
					vehicleMaintainanceCategoryModel.setDescription(category.getDescription());
					vehicleMaintainanceCategoryModel.setName(category.getName());
					vehicleMaintainanceCategoryModel.setId(category.getId());
					vehicleMaintainancecategoryList.add(vehicleMaintainanceCategoryModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("VehicleMaintainanceCategoryServiceImpl getAll() ends ");
		return vehicleMaintainancecategoryList;
	}

	private Object createSuccessObject(String msg) {

		Success success = new Success();
		success.setMessage(msg);
		success.setResultList(getAll());
		return success;
	}

	private Object createFailedObject(String msg) {

		Failed failed = new Failed();
		failed.setMessage(msg);
		// failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object addVMC(VehicleMaintainanceCategoryModel vehicleMaintainanceCategoryModel) {

		logger.info("VehicleMaintainanceCategoryServiceImpl addVMC() starts ");
		VehicleMaintainanceCategory vehicleMaintainanceCategory = null;
		
		try {
			vehicleMaintainanceCategory = setVMCValues(vehicleMaintainanceCategoryModel);
			vehicleMaintainanceCategoryDao.save(vehicleMaintainanceCategory);

		} catch (Exception e) {
			logger.info("Exception inside VehicleMaintainanceCategoryServiceImpl addVMC() :" + e.getMessage());
			return createFailedObject(vmc_unable_to_add_message);

		}

		logger.info("VehicleMaintainanceCategoryServiceImpl addVMC() ends ");
		return createSuccessObject(vmc_added_message);
	}

	private VehicleMaintainanceCategory setVMCValues(VehicleMaintainanceCategoryModel vehicleMaintainanceCategoryModel) {

		VehicleMaintainanceCategory vehicleMaintainanceCategory = new VehicleMaintainanceCategory();
		vehicleMaintainanceCategory.setName(vehicleMaintainanceCategoryModel.getName());
		vehicleMaintainanceCategory.setDescription(vehicleMaintainanceCategoryModel.getDescription());
		return vehicleMaintainanceCategory;
	}

	@Override
	public Object update(Long id, VehicleMaintainanceCategoryModel vmcModel) {

		logger.info("VehicleMaintainanceCategoryServiceImpl update() starts.");
		try {
			VehicleMaintainanceCategory vmc = vehicleMaintainanceCategoryDao.findById(id);

			if (vmc != null) {
				vmc.setName(vmcModel.getName());
				vmc.setDescription(vmcModel.getDescription());
				vehicleMaintainanceCategoryDao.update(vmc);
			} else {
				return createFailedObject(vmc_unable_to_update_message);
			}

		} catch (Exception e) {
			logger.info("Exception inside VehicleMaintainanceCategoryServiceImpl update() :"+ e.getMessage());
			return createFailedObject(vmc_unable_to_update_message);
		}

		logger.info("VehicleMaintainanceCategoryServiceImpl update() ends.");
		return createSuccessObject(vmc_updated_message);
	}

	@Override
	public Object delete(Long id) {

		logger.info("VehicleMaintainanceCategoryServiceImpl delete() starts.");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			VehicleMaintainanceCategory vmc = (VehicleMaintainanceCategory) session.get(VehicleMaintainanceCategory.class, id);
			if (vmc != null) {
				session.delete(vmc);
				tx.commit();
			} else {
				return createFailedObject(vmc_unable_to_delete_message);
			}

		} catch (Exception e) {
			logger.info("Exception inside VehicleMaintainanceCategoryServiceImpl delete() : " + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(vmc_already_used_message);
			}
			return createFailedObject(vmc_unable_to_delete_message);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("VehicleMaintainanceCategoryServiceImpl delete() ends.");
		return createSuccessObject(vmc_deleted_message);
	}

	@Override
	public VehicleMaintainanceCategoryModel get(Long id) {

		logger.info("VehicleMaintainanceCategoryServiceImpl get() starts.");
		Session session = null;
		VehicleMaintainanceCategoryModel vehicleMaintainanceCategoryModel = new VehicleMaintainanceCategoryModel();

		try {

			session = sessionFactory.openSession();
			VehicleMaintainanceCategory vehicleMaintainanceCategory = vehicleMaintainanceCategoryDao.findById(id, session);

			if (vehicleMaintainanceCategory != null) {

				vehicleMaintainanceCategoryModel.setId(vehicleMaintainanceCategory.getId());
				vehicleMaintainanceCategoryModel.setName(vehicleMaintainanceCategory.getName());
				vehicleMaintainanceCategoryModel.setDescription(vehicleMaintainanceCategory.getDescription());
				//vehicleMaintainanceCategoryModel.setStatusId(vehicleMaintainanceCategory.getStatus().getId());

			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("VehicleMaintainanceCategoryServiceImpl get() ends.");
		return vehicleMaintainanceCategoryModel;
	}

	/*@Override
	public HandlingModel getOpenAdd() {

		logger.info("HandlingServiceImpl getOpenAdd() starts ");
		HandlingModel handlingModel = new HandlingModel();

		List<Status> statusList = statusService.getAll();
		handlingModel.setStatusList(statusList);

		logger.info("HandlingServiceImpl getOpenAdd() ends ");
		return handlingModel;
	}*/

	@Override
	public List<VehicleMaintainanceCategoryModel> getVmcByVmcName(String vmcName) {

		logger.info("VehicleMaintainanceCategoryServiceImpl getVmcByVmcName() starts ");
		Session session = null;
		List<VehicleMaintainanceCategoryModel> vmcLists = new ArrayList<VehicleMaintainanceCategoryModel>();

		try {
			session = sessionFactory.openSession();
			List<VehicleMaintainanceCategory> vmcList = vehicleMaintainanceCategoryDao.getVmcByVmcName(session, vmcName);
			if (vmcList != null && !vmcList.isEmpty()) {
				for (VehicleMaintainanceCategory vmc : vmcList) {
					VehicleMaintainanceCategoryModel vmcObj = new VehicleMaintainanceCategoryModel();
					vmcObj.setId(vmc.getId());
					vmcObj.setName(vmc.getName());
					vmcObj.setDescription(vmc.getDescription());
					vmcLists.add(vmcObj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("VehicleMaintainanceCategoryServiceImpl getVmcByVmcName() ends ");
		return vmcLists;
	}

	@Override
	public List<VehicleMaintainanceCategoryModel> getSpecificData() {
		
		Session session = sessionFactory.openSession();
		List<VehicleMaintainanceCategoryModel> vmcs = new ArrayList<VehicleMaintainanceCategoryModel>();
		
		try {
			List<Object[]> vmcData = vehicleMaintainanceCategoryDao.getSpecificData(session, "VehicleMaintainanceCategory", "id", "name");

			if (vmcData != null && !vmcData.isEmpty()) {
				for (Object[] row : vmcData) {
					VehicleMaintainanceCategoryModel vmcObj = new VehicleMaintainanceCategoryModel();
					vmcObj.setId((Long) row[0]);
					vmcObj.setName(String.valueOf(row[1]));
					vmcs.add(vmcObj);
				}
			}
		}  finally {
			if (session != null) {
				session.close();
			}
		}
		return vmcs;
	}

}
