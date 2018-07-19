package com.dpu.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dpu.entity.Status;
import com.dpu.model.ArrangedWithModel;
import com.dpu.model.CarrierModel;
import com.dpu.model.CategoryModel;
import com.dpu.model.CompanyResponse;
import com.dpu.model.DispatcherModel;
import com.dpu.model.DivisionReq;
import com.dpu.model.DriverModel;
import com.dpu.model.EquipmentReq;
import com.dpu.model.SaleReq;
import com.dpu.model.TerminalResponse;
import com.dpu.model.TypeResponse;

public class AllList {

	/**
	 * To get CategoryList
	 * 
	 * @param session
	 * @param tableName
	 * @param firstColumn
	 * @param secondColumn
	 * @return categoryList
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<CategoryModel> getCategoryList(Session session,
			String tableName, String firstColumn, String secondColumn) {

		List<CategoryModel> categoryList = new ArrayList<CategoryModel>();

		try {
			Query query = session.createQuery(" select " + firstColumn + " , "
					+ secondColumn + " from " + tableName);
			List<Object[]> data = query.list();
			if (data != null && !data.isEmpty()) {
				Iterator<Object[]> operationIt = data.iterator();

				while (operationIt.hasNext()) {
					Object o[] = (Object[]) operationIt.next();
					CategoryModel type = new CategoryModel();
					type.setCategoryId(Long.parseLong(String.valueOf(o[0])));
					type.setName(String.valueOf(o[1]));
					categoryList.add(type);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return categoryList;
	}

	
	/**
	 * To get companyList
	 * 
	 * @param session
	 * @param tableName
	 * @param firstColumn
	 * @param secondColumn
	 * @return companyList
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<CompanyResponse> getCompanyList(Session session,
			String tableName, String firstColumn, String secondColumn) {

		List<CompanyResponse> companyList = new ArrayList<CompanyResponse>();

		try {
			Query query = session.createQuery(" select " + firstColumn + " , "
					+ secondColumn + " from " + tableName);
			List<Object[]> data = query.list();
			if (data != null && !data.isEmpty()) {
				Iterator<Object[]> operationIt = data.iterator();

				while (operationIt.hasNext()) {
					Object o[] = (Object[]) operationIt.next();
					CompanyResponse type = new CompanyResponse();
					type.setCompanyId(Long.parseLong(String.valueOf(o[0])));
					type.setName(String.valueOf(o[1]));
					companyList.add(type);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return companyList;
	}

	/**
	 * To get divisionList
	 * 
	 * @param session
	 * @param tableName
	 * @param firstColumn
	 * @param secondColumn
	 * @return divisionList
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<DivisionReq> getDivisionList(Session session,
			String tableName, String firstColumn, String secondColumn) {

		List<DivisionReq> divisionList = new ArrayList<DivisionReq>();

		try {
			Query query = session.createQuery(" select " + firstColumn + " , "
					+ secondColumn + " from " + tableName);
			List<Object[]> data = query.list();
			if (data != null && !data.isEmpty()) {
				Iterator<Object[]> operationIt = data.iterator();

				while (operationIt.hasNext()) {
					Object o[] = (Object[]) operationIt.next();
					DivisionReq type = new DivisionReq();
					type.setDivisionId(Long.parseLong(String.valueOf(o[0])));
					type.setDivisionName(String.valueOf(o[1]));
					divisionList.add(type);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return divisionList;
	}

	/**
	 * To get terminalList
	 * 
	 * @param session
	 * @param tableName
	 * @param firstColumn
	 * @param secondColumn
	 * @return terminalList
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<TerminalResponse> getTerminalList(Session session,
			String tableName, String firstColumn, String secondColumn) {

		List<TerminalResponse> terminalList = new ArrayList<TerminalResponse>();

		try {
			Query query = session.createQuery(" select " + firstColumn + " , "
					+ secondColumn + " from " + tableName);
			List<Object[]> data = query.list();
			if (data != null && !data.isEmpty()) {
				Iterator<Object[]> operationIt = data.iterator();

				while (operationIt.hasNext()) {
					Object o[] = (Object[]) operationIt.next();
					TerminalResponse type = new TerminalResponse();
					type.setTerminalId(Long.parseLong(String.valueOf(o[0])));
					type.setTerminalName(String.valueOf(o[1]));
					terminalList.add(type);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return terminalList;
	}

	/**
	 * To get saleList
	 * 
	 * @param session
	 * @param tableName
	 * @param firstColumn
	 * @param secondColumn
	 * @return saleList
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<SaleReq> getSaleList(Session session, String tableName,
			String firstColumn, String secondColumn) {

		List<SaleReq> saleList = new ArrayList<SaleReq>();

		try {
			Query query = session.createQuery(" select " + firstColumn + " , "
					+ secondColumn + " from " + tableName);
			List<Object[]> data = query.list();
			if (data != null && !data.isEmpty()) {
				Iterator<Object[]> operationIt = data.iterator();

				while (operationIt.hasNext()) {
					Object o[] = (Object[]) operationIt.next();
					SaleReq type = new SaleReq();
					type.setSaleId(Long.parseLong(String.valueOf(o[0])));
					type.setName(String.valueOf(o[1]));
					saleList.add(type);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return saleList;
	}

	/**
	 * To get typeResponseList
	 * 
	 * @param session
	 * @param tableName
	 * @param firstColumn
	 * @param secondColumn
	 * @return typeResponseList
	 */
	@SuppressWarnings("unchecked")
	public static List<TypeResponse> getTypeResponse(Session session, Long val) {

		Query queryOperation = session
				.createQuery("select typeId,typeName from Type where value = "
						+ val);
		List<Object> operationListObj = queryOperation.list();
		List<TypeResponse> typeResponseList = new ArrayList<TypeResponse>();
		Iterator<Object> operationIt = operationListObj.iterator();

		while (operationIt.hasNext()) {
			Object o[] = (Object[]) operationIt.next();
			TypeResponse typeResponse = new TypeResponse();
			typeResponse.setTypeId(Long.parseLong(String.valueOf(o[0])));
			typeResponse.setTypeName(String.valueOf(o[1]));
			typeResponseList.add(typeResponse);
		}

		return typeResponseList;

	}

	/**
	 * To get statusList
	 * 
	 * @param session
	 * @param tableName
	 * @param firstColumn
	 * @param secondColumn
	 * @return statusList
	 */
	@SuppressWarnings("unchecked")
	public static List<Status> getStatusList(Session session) {

		Query queryStatus = session.createQuery("select id,status from Status");
		List<Object> statusListObj = queryStatus.list();
		List<Status> statusList = new ArrayList<Status>();
		Iterator<Object> it = statusListObj.iterator();

		while (it.hasNext()) {
			Object o[] = (Object[]) it.next();
			Status status = new Status();
			status.setId(Long.parseLong(String.valueOf(o[0])));
			status.setStatus(String.valueOf(o[1]));
			statusList.add(status);
		}
		return statusList;
	}

	/**
	 * To get carrierList
	 * 
	 * @param session
	 * @param tableName
	 * @param firstColumn
	 * @param secondColumn
	 * @return carrierList
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<CarrierModel> getCarrierList(Session session,
			String tableName, String firstColumn, String secondColumn) {

		List<CarrierModel> carrierList = new ArrayList<CarrierModel>();

		try {
			Query query = session.createQuery(" select " + firstColumn + " , "
					+ secondColumn + " from " + tableName);
			List<Object[]> data = query.list();
			if (data != null && !data.isEmpty()) {
				Iterator<Object[]> operationIt = data.iterator();

				while (operationIt.hasNext()) {
					Object o[] = (Object[]) operationIt.next();
					CarrierModel type = new CarrierModel();
					type.setCarrierId(Long.parseLong(String.valueOf(o[0])));
					type.setName(String.valueOf(o[1]));
					carrierList.add(type);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return carrierList;
	}

	/**
	 * To get driverList
	 * 
	 * @param session
	 * @param tableName
	 * @param firstColumn
	 * @param secondColumn
	 * @return driverList
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<DriverModel> getDriverList(Session session,
			String tableName, String firstColumn, String secondColumn) {

		List<DriverModel> driverList = new ArrayList<DriverModel>();

		try {
			Query query = session.createQuery(" select " + firstColumn + " , "
					+ secondColumn + " from " + tableName);
			List<Object[]> data = query.list();
			if (data != null && !data.isEmpty()) {
				Iterator<Object[]> operationIt = data.iterator();

				while (operationIt.hasNext()) {
					Object o[] = (Object[]) operationIt.next();
					DriverModel type = new DriverModel();
					type.setDriverId(Long.parseLong(String.valueOf(o[0])));
					type.setFirstName(String.valueOf(o[1]));
					driverList.add(type);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return driverList;
	}

	/**
	 * To get equipmentList
	 * 
	 * @param session
	 * @param tableName
	 * @param firstColumn
	 * @param secondColumn
	 * @return equipmentList
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<EquipmentReq> getEquipmentList(Session session,
			String tableName, String firstColumn, String secondColumn) {

		List<EquipmentReq> equipmentList = new ArrayList<EquipmentReq>();

		try {
			Query query = session.createQuery(" select " + firstColumn + " , "
					+ secondColumn + " from " + tableName);
			List<Object[]> data = query.list();
			if (data != null && !data.isEmpty()) {
				Iterator<Object[]> operationIt = data.iterator();

				while (operationIt.hasNext()) {
					Object o[] = (Object[]) operationIt.next();
					EquipmentReq type = new EquipmentReq();
					type.setEquipmentId(Long.parseLong(String.valueOf(o[0])));
					type.setEquipmentName(String.valueOf(o[1]));
					equipmentList.add(type);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return equipmentList;
	}
	
	/**
	 * To get dispatcherList
	 * 
	 * @param session
	 * @param tableName
	 * @param firstColumn
	 * @param secondColumn
	 * @return dispatcherList
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<DispatcherModel> getDispatcherList(Session session,
			String tableName, String firstColumn, String secondColumn) {

		List<DispatcherModel> dispatcherList = new ArrayList<DispatcherModel>();

		try {
			Query query = session.createQuery(" select " + firstColumn + " , "
					+ secondColumn + " from " + tableName);
			List<Object[]> data = query.list();
			if (data != null && !data.isEmpty()) {
				Iterator<Object[]> operationIt = data.iterator();

				while (operationIt.hasNext()) {
					Object o[] = (Object[]) operationIt.next();
					DispatcherModel type = new DispatcherModel();
					type.setId(Long.parseLong(String.valueOf(o[0])));
					type.setArrangedWith(String.valueOf(o[1]));
					dispatcherList.add(type);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dispatcherList;
	}
	
	
	/**
	 * To get arrangedWithList
	 * 
	 * @param session
	 * @param tableName
	 * @param firstColumn
	 * @param secondColumn
	 * @return arrangedWithList
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<ArrangedWithModel> getArrangedWithList(Session session,
			String tableName, String firstColumn, String secondColumn) {

		List<ArrangedWithModel> arrangedWithList = new ArrayList<ArrangedWithModel>();

		try {
			Query query = session.createQuery(" select " + firstColumn + " , "
					+ secondColumn + " from " + tableName);
			List<Object[]> data = query.list();
			if (data != null && !data.isEmpty()) {
				Iterator<Object[]> operationIt = data.iterator();

				while (operationIt.hasNext()) {
					Object o[] = (Object[]) operationIt.next();
					ArrangedWithModel type = new ArrangedWithModel();
					type.setId(Long.parseLong(String.valueOf(o[0])));
					type.setArrangedWith(String.valueOf(o[1]));
					arrangedWithList.add(type);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return arrangedWithList;
	}
	
}
