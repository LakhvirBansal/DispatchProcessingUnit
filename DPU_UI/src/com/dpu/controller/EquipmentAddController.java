package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Equipment;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EquipmentAddController extends Application implements Initializable {

	@FXML
	Button btnSaveEquipment, btnCancel;

	@FXML
	TextField txtName, txtDescription;

	@FXML
	ComboBox<String> ddlType;
	@FXML
	Label lblName, lblDescription, lblType;

	Validate validate = new Validate();

	@FXML
	private void txtNameKeyTyped() {
		txtName.setStyle("-fx-focus-color: #87CEEB;");
	}

	@FXML
	private void ddlTypeAction() {
		ddlType.setStyle("-fx-focus-color: #87CEEB;");
	}
	
	@FXML
	private void btnCancelAction() {
		closeAddEquipmentScreen(btnCancel);
	}

	private boolean validateAddEquipmentScreen() {
		boolean response = true;
		String name = txtName.getText();
		String description = txtDescription.getText();
		String type = ddlType.getSelectionModel().getSelectedItem();

		boolean result = validate.validateEmptyness(name);
		if (!result) {
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			txtName.setStyle("-fx-focus-color: red;");
			lblName.setVisible(true);
			lblName.setText("Equipment Name is Mandatory");
			lblName.setTextFill(Color.RED);

		} else if (!validate.validateLength(name, 5, 25)) {
			response = false;
			txtName.setStyle("-fx-focus-color: red;");
			lblName.setVisible(true);
			lblName.setText("Min. length 5 and Max. length 25");
			lblName.setTextFill(Color.RED);
			return result;
		}
		result = validate.validateEmptyness(description);
		if (!result) {
			response = false;
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			txtDescription.setStyle("-fx-focus-color: red;");
			lblDescription.setVisible(true);
			lblDescription.setText("Description name is Mandatory");
			lblDescription.setTextFill(Color.RED);
		}
		result = validate.validateEmptyness(type);
		if (!result) {
			response = false;
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			ddlType.setStyle("-fx-focus-color: red;");
			lblType.setVisible(true);
			lblType.setText("Type name is Mandatory");
			lblType.setTextFill(Color.RED);
		}
		return response;
	}

	public StringBuffer validsteFields() {
		StringBuffer strBuff = new StringBuffer();
		String name = txtName.getText();
		String description = txtDescription.getText();
		String type = ddlType.getSelectionModel().getSelectedItem();
		if (name.equals("")) {
			strBuff.append("Equipment Name is Mandatory\n");
		}
		if (description.equals("")) {
			strBuff.append("Descriptionis Mandatory\n");
		}
		if (type == null) {
			strBuff.append("Type is Mandatory\n");
		}
		return strBuff;
	}

	private Object openValidationScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMMON_BASE_PACKAGE + Iconstants.XML_VALIDATION_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Warning");
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@FXML
	private void btnSaveEquipmentAction() {
		boolean result = validateAddEquipmentScreen();
		if (result) {
			addEquipment();
			closeAddEquipmentScreen(btnSaveEquipment);
		}
	}

	private void closeAddEquipmentScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	List<Type> cList = null;

	public void fetchTypes() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TYPE_API, null);
					System.out.println(response);
					if (response != null && response.length() > 0) {
						Type c[] = mapper.readValue(response, Type[].class);
						cList = new ArrayList<Type>();
						for (Type ccl : c) {
							ddlType.getItems().add(ccl.getTypeName());
							cList.add(ccl);
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}
		});
	}

	private void addEquipment() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Equipment equipment = setEquipmentValue();
					String payload = mapper.writeValueAsString(equipment);
					System.out.println("Add Payload: " + payload);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API,
							null, payload);
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

					/*
					 * if(response != null && response.contains("message")) {
					 * Success success = mapper.readValue(response,
					 * Success.class); JOptionPane.showMessageDialog(null,
					 * success.getMessage() , "Info", 1); } else { Failed failed
					 * = mapper.readValue(response, Failed.class);
					 * JOptionPane.showMessageDialog(null, failed.getMessage(),
					 * "Info", 1); }
					 */
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchTypes();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	private Equipment setEquipmentValue() {
		Equipment equipment = new Equipment();
		equipment.setEquipmentName(txtName.getText());
		equipment.setDescription(txtDescription.getText());
		equipment.setTypeId(cList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		return equipment;
	}
}