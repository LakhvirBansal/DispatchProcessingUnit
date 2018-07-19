package com.dpu.controller.order;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.MainScreen;
import com.dpu.model.AdditionalContact;
import com.dpu.model.BillingControllerModel;
import com.dpu.model.Company;
import com.dpu.model.Failed;
import com.dpu.model.OrderModel;
import com.dpu.model.OrderPickUpDeliveryModel;
import com.dpu.model.ProbilModel;
import com.dpu.model.Shipper;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OrderEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateOrder, btnCancel;
	
	@FXML
	TextField txtCallerName, txtPONo;
	
	@FXML
	ComboBox<String> ddlCustomer, ddlAdditionalContacts, ddlBillingLocation, ddlShipper, ddlConsignee, ddlCurrency, ddlDelivery, 
	ddlPickup, ddlProbill;
	
	@FXML
	ComboBox<String> ddlStatus;
	
	Long orderId = 0l;
	Validate validate = new Validate();
	List<Status> statusList;
	public static OrderModel orderModel;
	List<Company> companyList = null;
	List<Shipper> shipperList = null;
	List<Shipper> consigneeList = null;
	List<AdditionalContact> additionalContactsList = null;
	List<BillingControllerModel> billingLocations = null;
	List<Type> currencyList = null, deliveryList = null, pickupList = null;
	static public List<Type> temperatureList = null, temperatureTypeList = null;
	List<Type> typeList, operationList, timeZoneList;
	Long papsCustomBrokerTypeId, parsCustomBrokerTypeId = 0l;
	ObjectMapper mapper = new ObjectMapper();
	
	@FXML
	private void btnCancelAction() {
		closeEditOrderScreen(btnCancel);
	}
	
	@FXML
	private void btnAddProbillAction() {
		Integer value = probillDropDownList.get(probillDropDownList.size() - 1);
		ddlProbill.getItems().add((value + 1) + "");
		probillDropDownList.add(value + 1);
	}
	
	@FXML
	private void btnTemperatureAction() {
		try {
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.ORDER_BASE_PACKAGE + Iconstants.XML_TEMPERATURE_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Temperature");
	        stage.setScene(new Scene(root)); 
	        stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@FXML
	private void btnAddPickupNoAction() {
	}
	
	@FXML
	private void btnRemoveProbillAction() {
		if(probillDropDownList.size() == 1) {
			return;
		}
		if(ddlProbill.getSelectionModel().getSelectedItem() != null) {
			probillDropDownList.remove(ddlProbill.getSelectionModel().getSelectedIndex());
			ddlProbill.getItems().remove(ddlProbill.getSelectionModel().getSelectedIndex());
			ddlProbill.getItems().clear();
			ObservableList<Integer> data = FXCollections.observableArrayList(probillDropDownList);
			for(Integer i : data) {
				ddlProbill.getItems().add(i + "");
			}
			ddlProbill.getSelectionModel().select(0);
			ddlProbill.setVisibleRowCount(probillDropDownList.size());
		}
	}
	
	/*private boolean validateEditHandlingScreen() {
		String name = txtHandling.getText();
		String status = ddlStatus.getSelectionModel().getSelectedItem();

		
		boolean result = validate.validateEmptyness(name);
		if(!result) {
			txtHandling.setTooltip(new Tooltip("Handling Name is Mandatory"));
			txtHandling.setStyle("-fx-focus-color: red;");
			txtHandling.requestFocus();
			return result;
		}
		result = validate.validateEmptyness(status);
		if(!result) {
			ddlStatus.setTooltip(new Tooltip("Status is Mandatory"));
			ddlStatus.setStyle("-fx-focus-color: red;");
			ddlStatus.requestFocus();
			return result;
		}
		return result;
	}*/
	
	@FXML
	private void btnUpdateOrderAction() {
//		boolean response = validateEditHandlingScreen();
//		if(response) {
			editOrder();
			closeEditOrderScreen(btnUpdateOrder);
//		}
	}
	
	private void closeEditOrderScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void editOrder() {
		
		Platform.runLater(new Runnable() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					OrderModel orderModel = setOrderValue();
					String payload = mapper.writeValueAsString(orderModel);
					System.out.println("AYYYY: " + payload);
					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_ORDER_API + "/" + orderId, null, payload);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<OrderModel> orderModelList = (List<OrderModel>) success.getResultList();
						String res = mapper.writeValueAsString(orderModelList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.orderController.fillOrders(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}

		});
	}
	
	List<Integer> probillDropDownList = new ArrayList<Integer>();
	
	List<ProbilModel> probils = null;
	
	@FXML
	TextField txtPickpupScheduledDate, txtPickpupScheduledTime, txtPickpupMABDate, txtPickpupMABTime,
	txtDeliverScheduledDate, txtDeliverScheduledTime, txtDeliverMABDate, txtDeliverMABTime, txtDelivery1, txtPickup1;
	
	public static OrderModel updateOrderModel = new OrderModel();
	
	private OrderModel setOrderValue() {
		updateOrderModel.setId(orderId);
		updateOrderModel.setCompanyId(companyList.get(ddlCustomer.getSelectionModel().getSelectedIndex()).getCompanyId());
		updateOrderModel.setBillingLocationId(billingLocations.get(ddlBillingLocation.getSelectionModel().getSelectedIndex()).getBillingLocationId());
		updateOrderModel.setContactId(additionalContactsList.get(ddlAdditionalContacts.getSelectionModel().getSelectedIndex()).getAdditionalContactId());
		updateOrderModel.setCurrencyId(currencyList.get(ddlCurrency.getSelectionModel().getSelectedIndex()).getTypeId());
		List<ProbilModel> probilModelList = new ArrayList<>();
		for(int i=0;i<probils.size();i++) {
			ProbilModel probilModel = new ProbilModel();
			probilModel.setId(probils.get(i).getId());
			probilModel.setProbilNo(probils.get(i).getProbilNo());
			probilModel.setShipperId(shipperList.get(ddlShipper.getSelectionModel().getSelectedIndex()).getShipperId());
			probilModel.setConsineeId(consigneeList.get(ddlConsignee.getSelectionModel().getSelectedIndex()).getShipperId());
			probilModel.setPickupId(pickupList.get(ddlPickup.getSelectionModel().getSelectedIndex()).getTypeId());
			probilModel.setDeliveryId(deliveryList.get(ddlDelivery.getSelectionModel().getSelectedIndex()).getTypeId());
			probilModel.setPickupScheduledDate(txtPickpupScheduledDate.getText());
			probilModel.setPickupScheduledTime(txtPickpupScheduledTime.getText());
			probilModel.setPickupMABDate(txtPickpupMABDate.getText());
			probilModel.setPickupMABTime(txtPickpupMABTime.getText());
			probilModel.setDeliverScheduledDate(txtDeliverScheduledDate.getText());
			probilModel.setDeliverScheduledTime(txtDeliverScheduledTime.getText());
			probilModel.setDeliveryMABDate(txtDeliverMABDate.getText());
			probilModel.setDeliveryMABTime(txtDeliverMABTime.getText());
			List<OrderPickUpDeliveryModel> orderPickupDeliveryModelList = new ArrayList<>();
			OrderPickUpDeliveryModel orderPickUpDeliveryModel = new OrderPickUpDeliveryModel();
			orderPickUpDeliveryModel.setId(probils.get(i).getOrderPickUpDeliveryList().get(0).getId());
			orderPickUpDeliveryModel.setPickupDeliveryNo(txtPickup1.getText());
			orderPickUpDeliveryModel.setTypeId(1l);
			OrderPickUpDeliveryModel orderPickUpDeliveryModel1 = new OrderPickUpDeliveryModel();
			orderPickUpDeliveryModel1.setId(probils.get(i).getOrderPickUpDeliveryList().get(1).getId());
			orderPickUpDeliveryModel1.setPickupDeliveryNo(txtDelivery1.getText());
			orderPickUpDeliveryModel1.setTypeId(2l);
			orderPickupDeliveryModelList.add(orderPickUpDeliveryModel);
			orderPickupDeliveryModelList.add(orderPickUpDeliveryModel1);
			probilModel.setOrderPickUpDeliveryList(orderPickupDeliveryModelList);
			probilModelList.add(probilModel);
		}
		updateOrderModel.setProbilList(probilModelList);
		if(updateOrderModel.getTemperatureValue() == null) {
			updateOrderModel.setTemperatureId(temperatureId);
			updateOrderModel.setTemperatureTypeId(temperatureTypeId);
			updateOrderModel.setTemperatureValue(temperatureValue);
		}
		return updateOrderModel;
	}
	
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for(int i=0;i<list.size();i++) {
			Object object = list.get(i);
			if(object != null && object instanceof Company) {
				Company company = (Company) object;
				comboBox.getItems().add(company.getName());
			}
			if(object != null && object instanceof Shipper) {
				Shipper shipper = (Shipper) object;
				comboBox.getItems().add(shipper.getLocationName());
			}
			if(object != null && object instanceof Type) {
				Type type = (Type) object;
				comboBox.getItems().add(type.getTypeName());
			}
			if(object != null && object instanceof AdditionalContact) {

				AdditionalContact additionalContact = (AdditionalContact) object;
				comboBox.getItems().add(additionalContact.getCustomerName());
			}
			if(object != null && object instanceof BillingControllerModel) {
				
				BillingControllerModel billingLocation = (BillingControllerModel) object;
				comboBox.getItems().add(billingLocation.getName());
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void start(Stage primaryStage) {
	}
	
	@FXML
	Label lblFaxConsignee, lblFaxShipper, lblAddressShipper, lblPhoneShipper, lblAddressConsginee, lblPhoneConsignee;

	public static Double temperatureValue;
	public static Long temperatureId, temperatureTypeId;

	public void initData(OrderModel orderModel) {
		ddlAdditionalContacts.getItems().clear();
		ddlBillingLocation.getItems().clear();
		ddlCustomer.getItems().clear();
		orderId = orderModel.getId();
		companyList = orderModel.getCompanyList();
		for(int i = 0; i< companyList.size();i++) {
			Company company = companyList.get(i);
			ddlCustomer.getItems().add(company.getName());
			if(company.getCompanyId().equals(orderModel.getCompanyId())) {
				ddlCustomer.getSelectionModel().select(i);
			}
		}
		
		
		
		additionalContactsList = orderModel.getCompanyResponse().getAdditionalContacts();
		for(int i = 0; i< additionalContactsList.size();i++) {
			AdditionalContact additionalContacts = additionalContactsList.get(i);
			ddlAdditionalContacts.getItems().add(additionalContacts.getCustomerName());
			if(additionalContacts.getAdditionalContactId().equals(orderModel.getContactId())) {
				ddlAdditionalContacts.getSelectionModel().select(i);
			}
		}
		
		billingLocations = orderModel.getCompanyResponse().getBillingLocations();
		for(int i = 0; i < billingLocations.size();i++) {
			BillingControllerModel billingLocation = billingLocations.get(i);
			ddlBillingLocation.getItems().add(billingLocation.getName());
			if(billingLocation.getBillingLocationId().equals(orderModel.getBillingLocationId())) {
				ddlBillingLocation.getSelectionModel().select(i);
			}
		}
		
//		txtPONo.setText(orderModel.getPoNo() + "");
		
		probils = orderModel.getProbilList();
		for(int i = 0; i < probils.size();i++) {
			ProbilModel probilModel = probils.get(i);
			ddlProbill.getItems().add(probilModel.getId() + "");
			ddlProbill.getSelectionModel().select(0);
		}
		
		shipperList = orderModel.getShipperConsineeList();
		for(int i = 0; i < shipperList.size();i++) {
			Shipper shipper = shipperList.get(i);
			ddlShipper.getItems().add(shipper.getLocationName());
			if(shipper.getShipperId().equals(probils.get(0).getShipperId())) {
				ddlShipper.getSelectionModel().select(i);
			}
		}
		
		consigneeList = orderModel.getShipperConsineeList();
		for(int i = 0; i < consigneeList.size();i++) {
			Shipper consginee = consigneeList.get(i);
			ddlConsignee.getItems().add(consginee.getLocationName());
			if(consginee.getShipperId().equals(probils.get(0).getShipperId())) {
				ddlConsignee.getSelectionModel().select(i);
			}
		}
		List<OrderPickUpDeliveryModel> orderPickUpDeliveryList = probils.get(0).getOrderPickUpDeliveryList();
		if(orderPickUpDeliveryList != null && orderPickUpDeliveryList.size() > 0) {
			for(OrderPickUpDeliveryModel orderPickUpDeliveryModel : orderPickUpDeliveryList) {
				if(orderPickUpDeliveryModel.getTypeId().equals(1l)) {
					txtPickup1.setText(orderPickUpDeliveryModel.getPickupDeliveryNo());
				}
				if(orderPickUpDeliveryModel.getTypeId().equals(2l)) {
					txtDelivery1.setText(orderPickUpDeliveryModel.getPickupDeliveryNo());
				}
			}
		}
		
		pickupList= orderModel.getPickupList();
		if(pickupList != null && pickupList.size() > 0) {
			for(int i=0;i<pickupList.size();i++) {
				Type type = pickupList.get(i);
				ddlPickup.getItems().add(type.getTypeName());
				if(type.getTypeId().equals(probils.get(0).getPickupId())) {
					ddlPickup.getSelectionModel().select(i);
				}
			}
		}
		
		deliveryList = orderModel.getDeliveryList();
		if(deliveryList != null && deliveryList.size() > 0) {
			for(int i=0;i<deliveryList.size();i++) {
				Type type = deliveryList.get(i);
				ddlDelivery.getItems().add(type.getTypeName());
				if(type.getTypeId().equals(probils.get(0).getDeliveryId())){
					ddlDelivery.getSelectionModel().select(i);
				}
			}
		}
		
		lblAddressShipper.setText(probils.get(0).getShipperList().get(0).getAddress());
		lblPhoneShipper.setText(probils.get(0).getShipperList().get(0).getPhone());
		lblFaxShipper.setText(probils.get(0).getShipperList().get(0).getFax());
		
		lblAddressConsginee.setText(probils.get(0).getConsineeList().get(0).getAddress());
		lblPhoneConsignee.setText(probils.get(0).getConsineeList().get(0).getPhone());
		lblFaxConsignee.setText(probils.get(0).getConsineeList().get(0).getFax());
		
		txtPickpupScheduledDate.setText(probils.get(0).getPickupScheduledDate());
		txtPickpupScheduledTime.setText(probils.get(0).getPickupScheduledTime());
		
		txtPickpupMABDate.setText(probils.get(0).getPickupMABDate());
		txtPickpupMABTime.setText(probils.get(0).getPickupMABTime());
		
		txtDeliverScheduledDate.setText(probils.get(0).getDeliverScheduledDate());
		txtDeliverScheduledTime.setText(probils.get(0).getDeliverScheduledTime());
		
		txtDeliverMABDate.setText(probils.get(0).getDeliveryMABDate());
		txtDeliverMABTime.setText(probils.get(0).getDeliveryMABTime());
		
		currencyList = orderModel.getCurrencyList();
		if(currencyList != null && currencyList.size() > 0) {
			for(int i=0;i<currencyList.size();i++) {
				Type type = currencyList.get(i);
				ddlCurrency.getItems().add(type.getTypeName());
				if(type.getTypeId().equals(orderModel.getCurrencyId())) {
					ddlCurrency.getSelectionModel().select(i);
				}
			}
		}
		
		temperatureList = orderModel.getTemperatureList();
		temperatureTypeList = orderModel.getTemperatureTypeList();
		temperatureId = orderModel.getTemperatureId();
		temperatureTypeId = orderModel.getTemperatureTypeId();
		temperatureValue = orderModel.getTemperatureValue();
		
		ddlCustomer.valueProperty().addListener(new ChangeListener<String>() {
	        
			@SuppressWarnings("rawtypes")
			@Override public void changed(ObservableValue ov, String t, String t1) {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						try {
							Long companyId = companyList.get(ddlCustomer.getSelectionModel().getSelectedIndex()).getCompanyId();
							String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_ORDER_API + "/" + companyId + "/getData", null);
							Company companyResponse = mapper.readValue(response, Company.class);
							additionalContactsList = companyResponse.getAdditionalContacts();
							ddlAdditionalContacts.getItems().clear();
							ddlBillingLocation.getItems().clear();

							if(additionalContactsList != null && additionalContactsList.size() > 0) {
								fillDropDown(ddlAdditionalContacts, additionalContactsList);
							}
							billingLocations = companyResponse.getBillingLocations();
							if(billingLocations != null && billingLocations.size() > 0) {
								fillDropDown(ddlBillingLocation, billingLocations);
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
						}
					}
				});
			}
			
	    });
		ddlShipper.valueProperty().addListener(new ChangeListener<String>() {
	        
			@SuppressWarnings("rawtypes")
			@Override public void changed(ObservableValue ov, String t, String t1) {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						try {
							Long shipperId = shipperList.get(ddlShipper.getSelectionModel().getSelectedIndex()).getShipperId();
							String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API + "/" + shipperId, null);
							Shipper shipperResponse = mapper.readValue(response, Shipper.class);
							if(shipperResponse != null) {
								if(shipperResponse.getAddress() != null) {
									lblAddressShipper.setText(shipperResponse.getAddress());
								}
								if(shipperResponse.getFax() != null) {
									lblFaxShipper.setText(shipperResponse.getFax());
								}
								if(shipperResponse.getAddress() != null) {
									lblPhoneShipper.setText(shipperResponse.getPhone());
								}
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
						}
					}
				});
			}
	    });
		ddlConsignee.valueProperty().addListener(new ChangeListener<String>() {
	        
			@SuppressWarnings("rawtypes")
			@Override public void changed(ObservableValue ov, String t, String t1) {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						try {
							Long consigneeId = consigneeList.get(ddlConsignee.getSelectionModel().getSelectedIndex()).getShipperId();
							String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API + "/" + consigneeId, null);
							Shipper shipperResponse = mapper.readValue(response, Shipper.class);
							if(shipperResponse != null) {
								if(shipperResponse.getAddress() != null) {
									lblAddressConsginee.setText(shipperResponse.getAddress());
								}
								if(shipperResponse.getFax() != null) {
									lblFaxConsignee.setText(shipperResponse.getFax());
								}
								if(shipperResponse.getAddress() != null) {
									lblPhoneConsignee.setText(shipperResponse.getPhone());
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
						}
					}
				});
			}
			
	    });
	}
}