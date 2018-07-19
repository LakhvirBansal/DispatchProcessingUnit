package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dpu.dao.CarrierAdditionalContactsDao;
import com.dpu.dao.CarrierDao;
import com.dpu.entity.Carrier;
import com.dpu.entity.CarrierAdditionalContacts;
import com.dpu.model.CarrierAdditionalContactsModel;
import com.dpu.model.CarrierModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CarrierAdditionalContactService;
import com.dpu.service.CarrierService;
import com.dpu.service.StatusService;
import com.dpu.util.MessageProperties;

@Component
public class CarrierServiceImpl extends MessageProperties implements
		CarrierService {

	Logger logger = Logger.getLogger(CarrierServiceImpl.class);

	@Autowired
	StatusService statusService;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	CarrierDao carrierDao;

	@Autowired
	CarrierAdditionalContactService carrierAdditionalContactService;

	@Autowired
	CarrierAdditionalContactsDao carrierAdditionalContactsDao;

	@Value("${carrier_unable_to_add_message}")
	private String carrier_unable_to_add_message;

	@Value("${carrier_added_message}")
	private String carrier_added_message;

	@Value("${carrier_deleted_message}")
	private String carrier_deleted_message;

	@Value("${carrier_unable_to_delete_message}")
	private String carrier_unable_to_delete_message;

	@Value("${carrier_dependent_message}")
	private String carrier_dependent_message;

	@Value("${carrier_unable_to_update_message}")
	private String carrier_unable_to_update_message;

	@Value("${carrier_updated_message}")
	private String carrier_updated_message;

	@Value("${carrierAdditionalContact_deleted_message}")
	private String carrierAdditionalContact_deleted_message;

	@Value("${carrierAdditionalContact_unable_to_delete_message}")
	private String carrierAdditionalContact_unable_to_delete_message;

	@Override
	public List<CarrierModel> getAll( ) {

		List<Carrier> listOfCarrier = carrierDao.findAll();
		//Query query = session.createQuery("from Carrier");
		//List<Carrier> listOfCarrier = query.list();
		List<CarrierModel> returnResponse = new ArrayList<CarrierModel>();

		if (listOfCarrier != null && !listOfCarrier.isEmpty()) {
			for (Carrier carrier : listOfCarrier) {
				CarrierModel response = new CarrierModel();
				setCarrierData(carrier, response);
				returnResponse.add(response);
			}

		}

		return returnResponse;
	}

	private void setCarrierData(Carrier carrier, CarrierModel response) {

		response.setCarrierId(carrier.getCarrierId());
		response.setName(carrier.getName());
		response.setAddress(carrier.getAddress());
		response.setCellular(carrier.getCellular());
		response.setCity(carrier.getCity());
		response.setContact(carrier.getContact());
		response.setEmail(carrier.getEmail());
		response.setExt(carrier.getExt());
		response.setFax(carrier.getFax());
		response.setPhone(carrier.getPhone());
		response.setPosition(carrier.getPosition());
		response.setPrefix(carrier.getPrefix());
		response.setProvinceState(carrier.getProvinceState());
		response.setTollfree(carrier.getTollfree());
		response.setUnitNo(carrier.getUnitNo());
		response.setWebsite(carrier.getWebsite());
		response.setZip(carrier.getZip());

	}

	@Override
	public Object delete(Long carrierId) {

		logger.info("Inside CarrierServiceImpl delete() starts , carrierId : "
				+ carrierId);
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			Carrier carrier = carrierDao.findById(carrierId, session);

			if (carrier != null) {
				tx = session.beginTransaction();
				List<CarrierAdditionalContacts> carrierAddContacts = carrier
						.getCarrierAdditionalContact();
				if (carrierAddContacts != null && !carrierAddContacts.isEmpty()) {
					for (CarrierAdditionalContacts carrierAdditionalContacts : carrierAddContacts) {
						carrierAdditionalContactsDao.deleteAdditionalContact(
								carrierAdditionalContacts, session);
					}
				}

				carrierDao.deleteCarrier(carrier, session);

				if (tx != null) {
					tx.commit();
				}
			} else {
				return createFailedObject(carrier_unable_to_delete_message);
			}

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(carrier_dependent_message);
			}
			return createFailedObject(carrier_unable_to_delete_message);
		} finally {

			if (session != null) {
				session.close();
			}
		}
		return createSuccessObject(carrier_deleted_message);
	}

	private Object createFailedObject(String errorMessage) {

		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;

	}

	private Object createSuccessObject(String message) {

		Success success = new Success();
		success.setMessage(message);
		success.setResultList(getAll());
		return success;

	}

	private Object createAdditionalContactSuccessObject(String message) {

		Success success = new Success();
		success.setMessage(message);
		// success.setResultList(getAll());
		return success;

	}

	@Override
	public Object updateCarrier(Long id, CarrierModel carrierResponse) {

		Carrier carrier = carrierDao.findById(id);
		Session session = null;
		Transaction tx = null;

		try {
			if (carrier != null) {
				session = sessionFactory.openSession();
				tx = session.beginTransaction();

				carrierDao.updateData(carrier, carrierResponse, session);
			} else {
				return createFailedObject(carrier_unable_to_update_message);
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			return createFailedObject(carrier_unable_to_update_message);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		return createSuccessObject(carrier_updated_message);
	}

	@Override
	public CarrierModel get(Long id) {

		Session session = null;
		CarrierModel carrierResponse = new CarrierModel();
		try {
			session = sessionFactory.openSession();
			// Carrier carrier = carrierDao.findById(id, session);
			Carrier carrier = (Carrier) session.get(Carrier.class, id);
			if (carrier != null) {
				setCarrierData(carrier, carrierResponse);

				List<CarrierAdditionalContacts> carrierAddContacts = carrierAdditionalContactService
						.getAll(id, session);

				if (carrierAddContacts != null && !carrierAddContacts.isEmpty()) {
					List<CarrierAdditionalContactsModel> addContacts = new ArrayList<CarrierAdditionalContactsModel>();

					for (CarrierAdditionalContacts carrierAdditionalContact : carrierAddContacts) {
						CarrierAdditionalContactsModel addContact = new CarrierAdditionalContactsModel();
						BeanUtils.copyProperties(addContact,
								carrierAdditionalContact);
						addContacts.add(addContact);
					}

					carrierResponse
							.setCarrierAdditionalContactModel(addContacts);
				}

			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return carrierResponse;
	}

	@Override
	public Object addCarrierData(CarrierModel carrierResponse) {

		logger.info("Inside CarrierServiceImpl addCarrierData() starts");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Carrier carrier = setCarrierValues(carrierResponse);
			carrier = carrierDao.insertCarrierData(carrier, session);

			List<CarrierAdditionalContactsModel> additionalContacts = carrierResponse
					.getCarrierAdditionalContactModel();
			if (additionalContacts != null && !additionalContacts.isEmpty()) {
				for (CarrierAdditionalContactsModel additionalContact : additionalContacts) {
					CarrierAdditionalContacts comAdditionalContact = setAdditionalContactData(
							additionalContact, carrier);
					carrierAdditionalContactsDao.insertAdditionalContacts(
							comAdditionalContact, session);
				}
			}

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			logger.error("Exception inside CarrierServiceImpl addCarrierData() :"
					+ e.getMessage());
			return createFailedObject(carrier_unable_to_add_message);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside CarrierServiceImpl addCarrierData() ends");
		return createSuccessObject(carrier_added_message);

	}

	private CarrierAdditionalContacts setAdditionalContactData(
			CarrierAdditionalContactsModel additionalContact, Carrier carrier) {

		CarrierAdditionalContacts carrierAdditionalContact = new CarrierAdditionalContacts();
		carrierAdditionalContact.setCellular(additionalContact.getCellular());
		carrierAdditionalContact.setCustomerName(additionalContact
				.getCustomerName());
		carrierAdditionalContact.setEmail(additionalContact.getEmail());
		carrierAdditionalContact.setCarrier(carrier);
		carrierAdditionalContact.setExt(additionalContact.getExt());
		carrierAdditionalContact.setFax(additionalContact.getFax());
		carrierAdditionalContact.setPhone(additionalContact.getPhone());
		carrierAdditionalContact.setPosition(additionalContact.getPosition());
		carrierAdditionalContact.setPrefix(additionalContact.getPrefix());
		carrierAdditionalContact.setStatus(statusService.get(additionalContact
				.getStatusId()));
		return carrierAdditionalContact;

	}

	private Carrier setCarrierValues(CarrierModel carrierResponse) {

		Carrier carrier = new Carrier();
		carrier.setName(carrierResponse.getName());
		carrier.setAddress(carrierResponse.getAddress());
		carrier.setCellular(carrierResponse.getCellular());
		carrier.setCity(carrierResponse.getCity());
		carrier.setContact(carrierResponse.getContact());
		carrier.setEmail(carrierResponse.getEmail());
		carrier.setExt(carrierResponse.getExt());
		carrier.setFax(carrierResponse.getFax());
		carrier.setPhone(carrierResponse.getPhone());
		carrier.setPosition(carrierResponse.getPosition());
		carrier.setPrefix(carrierResponse.getPrefix());
		carrier.setProvinceState(carrierResponse.getProvinceState());
		carrier.setTollfree(carrierResponse.getTollfree());
		carrier.setUnitNo(carrierResponse.getUnitNo());
		carrier.setWebsite(carrierResponse.getWebsite());
		carrier.setZip(carrierResponse.getZip());
		return carrier;

	}

	@Override
	public CarrierAdditionalContactsModel getContactById(Long id) {

		logger.info("Inside CarrierServiceImpl getContactById() starts");
		Session session = null;

		CarrierAdditionalContactsModel carrierAdditionalContactResponse = new CarrierAdditionalContactsModel();
		try {
			session = sessionFactory.openSession();

			CarrierAdditionalContacts carrierAdditionalContact = (CarrierAdditionalContacts) session
					.get(CarrierAdditionalContacts.class, id);

			BeanUtils.copyProperties(carrierAdditionalContact,
					carrierAdditionalContactResponse);

		} catch (Exception e) {

			logger.error("Exception Inside CarrierServiceImpl getContactById() :  "
					+ e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		logger.info("Inside CarrierServiceImpl getContactById() ends");
		return carrierAdditionalContactResponse;
	}

	@Override
	public List<CarrierAdditionalContactsModel> getContactByCarrierId(
			Long carrierId) {

		logger.info("Inside CarrierServiceImpl getContactByCarrierId() starts");
		Session session = null;

		session = sessionFactory.openSession();
		List<CarrierAdditionalContactsModel> carrierAdditionalContactsResponse = new ArrayList<CarrierAdditionalContactsModel>();
		List<CarrierAdditionalContacts> carrierAdditionalContacts = carrierDao
				.getAdditionalContactsByCarrierId(carrierId, session);

		try {

			if (carrierAdditionalContacts != null
					&& !carrierAdditionalContacts.isEmpty()) {
				for (CarrierAdditionalContacts carrierAdditionalContact : carrierAdditionalContacts) {
					CarrierAdditionalContactsModel carrierAdditionalContactsModel = new CarrierAdditionalContactsModel();
					org.springframework.beans.BeanUtils.copyProperties(
							carrierAdditionalContact,
							carrierAdditionalContactsModel);
					carrierAdditionalContactsResponse
							.add(carrierAdditionalContactsModel);
				}
			}

		} catch (Exception e) {

			logger.error("Exception Inside CarrierServiceImpl getContactByCarrierId() :  "
					+ e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		logger.info("Inside CarrierServiceImpl getContactByCarrierId() ends");
		return carrierAdditionalContactsResponse;
	}

	@Override
	public List<CarrierModel> getCarriersByCarrierName(String carrierName) {

		Session session = null;
		List<CarrierModel> response = new ArrayList<CarrierModel>();
		try {

			session = sessionFactory.openSession();
			List<Carrier> carrierList = carrierDao.getCarriersByCarrierName(
					carrierName, session);
			if (carrierList != null && !carrierList.isEmpty()) {
				for (Carrier carrier : carrierList) {
					CarrierModel companyModel = new CarrierModel();
					org.springframework.beans.BeanUtils.copyProperties(carrier,
							companyModel);
					response.add(companyModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return response;
	}

	@Override
	public List<CarrierModel> getAllCarriersIdAndName() {

		Session session = null;
		List<CarrierModel> returnResponse = new ArrayList<CarrierModel>();

		try {
			session = sessionFactory.openSession();
			List<Object[]> listOfCarrier = carrierDao
					.findCarrierIdAndName(session);

			if (listOfCarrier != null && !listOfCarrier.isEmpty()) {
				for (Object[] row : listOfCarrier) {
					CarrierModel carrierModel = new CarrierModel();
					carrierModel.setCarrierId((Long) row[0]);
					carrierModel.setName(String.valueOf(row[1]));
					returnResponse.add(carrierModel);
				}
			}

		} catch (Exception e) {

		}
		return returnResponse;
	}

	@Override
	public Object deleteAdditionalContactByAdditionalContactId(Long contactId) {

		logger.info("Inside CarrierServiceImpl deleteAdditionalContactByAdditionalContactId() starts");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			CarrierAdditionalContacts carrierAdditionalContact = carrierDao
					.findByAdditionalContactId(contactId, session);

			if (carrierAdditionalContact != null) {
				tx = session.beginTransaction();
				carrierAdditionalContactsDao.deleteAdditionalContact(
						carrierAdditionalContact, session);

				if (tx != null) {
					tx.commit();
				}
			} else {
				return createFailedObject(carrierAdditionalContact_unable_to_delete_message);
			}
		} catch (Exception e) {

			return createFailedObject(carrierAdditionalContact_unable_to_delete_message);
		} finally {

			if (session != null) {
				session.close();
			}
		}
		return createAdditionalContactSuccessObject(carrierAdditionalContact_deleted_message);
	}

}
