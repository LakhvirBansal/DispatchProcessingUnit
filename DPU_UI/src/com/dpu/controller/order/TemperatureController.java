package com.dpu.controller.order;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TemperatureController extends Application implements Initializable {

	@FXML
	ComboBox<String> ddlTemperature, ddlTemperatureType;
	
	@FXML
	TextField txtTemperature;
	
	Validate validate = new Validate();

	/*@FXML
	private void txtNameKeyTyped() {
		txtName.setStyle("-fx-focus-color: #87CEEB;");
	}
	
	@FXML
	private void ddlTypeAction() {
		ddlType.setStyle("-fx-focus-color: #87CEEB;");
	}
	
	private boolean validateAddEquipmentScreen() {
		String name = txtName.getText();
		String type = ddlType.getSelectionModel().getSelectedItem();
		
		boolean result = validate.validateEmptyness(name);
		if(!result) {
			txtName.setTooltip(new Tooltip("Equipment Name is Mandatory"));
			txtName.setStyle("-fx-focus-color: red;");
			txtName.requestFocus();
			return result;
		}
		result = validate.validateEmptyness(type);
		if(!result) {
			ddlType.setTooltip(new Tooltip("Type is Mandatory"));
			ddlType.setStyle("-fx-focus-color: red;");
			ddlType.requestFocus();
			return result;
		}
		return result;
	}*/
	
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for(int i=0;i<list.size();i++) {
			Object object = list.get(i);
			if(object != null && object instanceof Type) {
				Type type = (Type) object;
				comboBox.getItems().add(type.getTypeName());
			}
		}
	}
	
	/*private void addOrder() {
		
		Platform.runLater(new Runnable() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Equipment equipment = setEquipmentValue();
					String payload = mapper.writeValueAsString(equipment);
					System.out.println("Add Payload: " + payload);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API, null, payload);
					System.out.println(response);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Equipment> equipmentList = (List<Equipment>) success.getResultList();
						String res = mapper.writeValueAsString(equipmentList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.equipmentController.fillEquipments(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}

					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}*/

	@FXML
	Button btnUpdateTemperature;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			if(OrderAddController.temperatureList != null && OrderAddController.temperatureList.size() > 0) {
				fillDropDown(ddlTemperature, OrderAddController.temperatureList);
				fillDropDown(ddlTemperatureType, OrderAddController.temperatureTypeList);
			} else if(OrderEditController.updateOrderModel.getTemperatureId() != null) {
				List<Type> temperatureList = OrderEditController.temperatureList;
				List<Type> temperatureTypeList = OrderEditController.temperatureTypeList;
				if(temperatureList != null && temperatureList.size() > 0) {
					for(int i=0;i<temperatureList.size();i++) {
						Type type = temperatureList.get(i);
						ddlTemperature.getItems().add(type.getTypeName());
						if(type.getTypeId() == OrderEditController.updateOrderModel.getTemperatureId()) {
							ddlTemperature.getSelectionModel().select(i);
						}
					}
				}
				
				if(temperatureTypeList != null && temperatureTypeList.size() > 0) {
					for(int i=0;i<temperatureTypeList.size();i++) {
						Type type = temperatureTypeList.get(i);
						ddlTemperatureType.getItems().add(type.getTypeName());
						if(type.getTypeId() == OrderEditController.updateOrderModel.getTemperatureTypeId()) {
							ddlTemperatureType.getSelectionModel().select(i);
						}
					}
				}
				
				if(OrderEditController.updateOrderModel.getTemperatureValue() != null) {
					txtTemperature.setText(OrderEditController.updateOrderModel.getTemperatureValue() + "");
				}
			} else {
				List<Type> temperatureList = OrderEditController.temperatureList;
				List<Type> temperatureTypeList = OrderEditController.temperatureTypeList;
				if(temperatureList != null && temperatureList.size() > 0) {
					for(int i=0;i<temperatureList.size();i++) {
						Type type = temperatureList.get(i);
						ddlTemperature.getItems().add(type.getTypeName());
						if(type.getTypeId() == OrderEditController.temperatureId) {
							ddlTemperature.getSelectionModel().select(i);
						}
					}
				}
				
				if(temperatureTypeList != null && temperatureTypeList.size() > 0) {
					for(int i=0;i<temperatureTypeList.size();i++) {
						Type type = temperatureTypeList.get(i);
						ddlTemperatureType.getItems().add(type.getTypeName());
						if(type.getTypeId() == OrderEditController.temperatureTypeId) {
							ddlTemperatureType.getSelectionModel().select(i);
						}
					}
				}
				txtTemperature.setText(OrderEditController.temperatureValue + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void btnUpdateTemperatureAction() {
		if(OrderAddController.orderModel != null && OrderAddController.orderModel.getBillingLocationId() != null) {
			OrderAddController.orderModel.setTemperatureId(OrderAddController.temperatureList.get(ddlTemperature.getSelectionModel().getSelectedIndex()).getTypeId());
			OrderAddController.orderModel.setTemperatureTypeId(OrderAddController.temperatureTypeList.get(ddlTemperatureType.getSelectionModel().getSelectedIndex()).getTypeId());
			OrderAddController.orderModel.setTemperatureValue(Double.parseDouble(txtTemperature.getText()));
		} else {
			OrderEditController.updateOrderModel.setTemperatureId(OrderEditController.temperatureList.get(ddlTemperature.getSelectionModel().getSelectedIndex()).getTypeId());
			OrderEditController.updateOrderModel.setTemperatureTypeId(OrderEditController.temperatureTypeList.get(ddlTemperatureType.getSelectionModel().getSelectedIndex()).getTypeId());
			OrderEditController.updateOrderModel.setTemperatureValue(Double.parseDouble(txtTemperature.getText()));
		}
		closeTemperatureScreen(btnUpdateTemperature);
	}
	
	private void closeTemperatureScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	@Override
	public void start(Stage primaryStage) {
	}

	/*private Equipment setEquipmentValue() {
		Equipment equipment = new Equipment();
		equipment.setEquipmentName(txtName.getText());
		equipment.setDescription(txtDescription.getText());
		equipment.setTypeId(cList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		return equipment;
	}*/
}