package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.DPUService;
import com.dpu.model.Failed;
import com.dpu.model.Status;
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

public class ServiceEditController extends Application implements Initializable {

	@FXML
	Button btnUpdateService, btnEdit, btnCancel;

	Long serviceId = 0l;

	@FXML
	TextField txtService;

	Validate validate = new Validate();
	@FXML
	Label lblService, lblTextField, lblAssociationWith, lblStatus;

	@FXML
	ComboBox<String> ddlTextField, ddlAssociationWith, ddlStatus;

	@FXML
	private void serviceNameKeyPressed() {
		String name = txtService.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblService.setText("");
			txtService.setStyle("-fx-focus-color: skyblue;");
			lblService.setVisible(false);
		} else {
			txtService.setStyle("-fx-focus-color: red;");
			txtService.requestFocus();
			lblService.setVisible(true);
			lblService.setText("Service Name is Mandatory");
			lblService.setTextFill(Color.RED);
		}
	}
	
	@FXML
	private void btnCancelAction() {
		closeEditServiceScreen(btnCancel);
	}

	@FXML
	private void ddlTextFieldAction() {
		String textField = ddlTextField.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblTextField.setText("");
			ddlTextField.setStyle("-fx-focus-color: skyblue;");
			lblTextField.setVisible(false);
		} else {
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			// ddlTextField.setStyle("-fx-focus-color: red;");
			ddlTextField.setStyle("-fx-border-color: red;");
			lblTextField.setVisible(true);
			lblTextField.setText("TextField is Mandatory");
			lblTextField.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddlAssociationWithAction() {
		String association = ddlAssociationWith.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(association);
		if (result) {
			lblAssociationWith.setText("");
			ddlAssociationWith.setStyle("-fx-focus-color: skyblue;");
			lblAssociationWith.setVisible(false);
		} else {
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			// ddlTextField.setStyle("-fx-focus-color: red;");
			ddlAssociationWith.setStyle("-fx-border-color: red;");
			lblAssociationWith.setVisible(true);
			lblAssociationWith.setText("AssociationWith is Mandatory");
			lblAssociationWith.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddlStatusAction() {
		String status = ddlStatus.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(status);
		if (result) {
			lblStatus.setText("");
			ddlStatus.setStyle("-fx-focus-color: skyblue;");
			lblStatus.setVisible(false);
		} else {
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			// ddlTextField.setStyle("-fx-focus-color: red;");
			ddlStatus.setStyle("-fx-border-color: red;");
			lblStatus.setVisible(true);
			lblStatus.setText("Status is Mandatory");
			lblStatus.setTextFill(Color.RED);
		}
	}

	private boolean validateEditServiceScreen() {
		boolean response = true;
		String name = txtService.getText();
		String textField = ddlTextField.getSelectionModel().getSelectedItem();
		String association = ddlAssociationWith.getSelectionModel().getSelectedItem();
		String status = ddlStatus.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(name);

		if (!result) {

			response = false;
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			// txtService.setStyle("-fx-focus-color: red;");
			txtService.setStyle("-fx-text-box-border: red;");
			lblService.setVisible(true);
			lblService.setText("Service Name is Mandatory");
			lblService.setTextFill(Color.RED);

		} else if (!validate.validateLength(name, 5, 25)) {

			response = false;
			// openValidationScreen();
			// txtService.requestFocus();
			// txtService.setStyle("-fx-focus-color: red;");
			txtService.setStyle("-fx-text-box-border: red;");
			lblService.setVisible(true);
			lblService.setText("Min. length 5 and Max. length 25");
			lblService.setTextFill(Color.RED);
			return result;
		}
		result = validate.validateEmptyness(textField);
		if (!result) {
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			response = false;
			// ddlTextField.setStyle("-fx-focus-color: red;");
			ddlTextField.setStyle("-fx-border-color: red;");
			lblTextField.setVisible(true);
			lblTextField.setText("TextField is Mandatory");
			lblTextField.setTextFill(Color.RED);
		}
		result = validate.validateEmptyness(association);
		if (!result) {
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			response = false;
			// ddlAssociationWith.setStyle("-fx-focus-color: red;");
			ddlAssociationWith.setStyle("-fx-border-color: red;");
			lblAssociationWith.setVisible(true);
			lblAssociationWith.setText("AssociationWith is Mandatory");
			lblAssociationWith.setTextFill(Color.RED);
		}
		result = validate.validateEmptyness(status);
		if (!result) {
			response = false;
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			// ddlStatus.setStyle("-fx-focus-color: red;");
			ddlStatus.setStyle("-fx-border-color: red;");
			lblStatus.setVisible(true);
			lblStatus.setText("Status is Mandatory");
			lblStatus.setTextFill(Color.RED);
		}
		return response;
	}

	public StringBuffer validsteFields() {
		StringBuffer strBuff = new StringBuffer();
		String name = txtService.getText();
		String textField = ddlTextField.getSelectionModel().getSelectedItem();
		String association = ddlAssociationWith.getSelectionModel().getSelectedItem();
		String status = ddlStatus.getSelectionModel().getSelectedItem();
		if (name.equals("")) {
			strBuff.append("Srvice Name is Mandatory,\n");
		}
		if (textField == null) {
			strBuff.append("TextField Name is Mandatory,\n");
		}
		if (association == null) {
			strBuff.append("Association Name is Mandatory,\n");
		}
		if (status == null) {
			strBuff.append("Status Name is Mandatory\n");
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
	private void btnUpdateServiceAction() {
		boolean response = validateEditServiceScreen();
		if (response) {
			editService();
			closeEditServiceScreen(btnUpdateService);
		}
	}

	private void closeEditServiceScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void editService() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					DPUService service = setServiceValue();
					String payload = mapper.writeValueAsString(service);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_SERVICE_API + "/" + serviceId, null, payload);
					// MainScreen.serviceController.fillServices(response);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<DPUService> serviceList = (List<DPUService>) success.getResultList();
						String res = mapper.writeValueAsString(serviceList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.serviceController.fillServices(res);
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
					// MainScreen.serviceController.fetchServices();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}

		});
	}

	List<Type> typeList, associatedWithList;

	List<Status> statusList;

	private DPUService setServiceValue() {
		DPUService dPUService = new DPUService();
		dPUService.setServiceName(txtService.getText());
		dPUService.setTextFieldId(typeList.get(ddlTextField.getSelectionModel().getSelectedIndex()).getTypeId());
		dPUService.setAssociationWithId(
				associatedWithList.get(ddlAssociationWith.getSelectionModel().getSelectedIndex()).getTypeId());
		dPUService.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		return dPUService;
	}

	@FXML
	public void handleEditAction() {
		btnEdit.setDisable(true);
		disableFields(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (ServiceController.flag == 1) {
			disableFields(true);
		}
		if (ServiceController.flag == 2) {
			btnEdit.setVisible(false);
		}
	}

	private void disableFields(boolean v) {
		btnUpdateService.setDisable(v);
		txtService.setDisable(v);
		ddlTextField.setDisable(v);
		ddlAssociationWith.setDisable(v);
		ddlStatus.setDisable(v);
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public void initData(DPUService service) {
		serviceId = service.getServiceId();
		txtService.setText(service.getServiceName());
		typeList = service.getTextFieldList();
		associatedWithList = service.getAssociatedWithList();
		statusList = service.getStatusList();
		for (int i = 0; i < service.getTextFieldList().size(); i++) {
			Type type = service.getTextFieldList().get(i);
			ddlTextField.getItems().add(type.getTypeName());
			if (type.getTypeId() == service.getTextFieldId()) {
				ddlTextField.getSelectionModel().select(i);
			}
		}

		for (int i = 0; i < service.getAssociatedWithList().size(); i++) {
			Type type = service.getAssociatedWithList().get(i);
			ddlAssociationWith.getItems().add(type.getTypeName());
			if (type.getTypeId() == service.getAssociationWithId()) {
				ddlAssociationWith.getSelectionModel().select(i);
			}
		}

		for (int i = 0; i < service.getStatusList().size(); i++) {
			Status status = service.getStatusList().get(i);
			ddlStatus.getItems().add(status.getStatus());
			if (status.getId() == service.getStatusId()) {
				ddlStatus.getSelectionModel().select(i);
			}
		}
	}
}