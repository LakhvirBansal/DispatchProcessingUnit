package com.dpu.controller.database;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.MainScreen;
import com.dpu.model.CustomBroker;
import com.dpu.model.CustomBrokerTypeModel;
import com.dpu.model.Failed;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CustomBrokerEditController extends Application implements Initializable {

	@FXML
	Button btnUpdateCustomBroker, btnEdit, btnCancel;

	@FXML
	TextField txtCustomerBrokerName, txtContactNamePAPS, txtCentralPhonePAPS, txtExtensionPAPS, txtCentralFaxPAPS,
			txtEmailPAPS, txtTrackerLinkPAPS, txtContactNamePARS, txtCentralPhonePARS, txtExtensionPARS,
			txtCentralFaxPARS, txtEmailPARS, txtTrackerLinkPARS;

	@FXML
	ComboBox<String> ddlOperationPAPS, ddl24HoursPAPS, ddlStatusPAPS, ddlOperationPARS, ddl24HoursPARS, ddlStatusPARS,
			ddlType;

	Long customBrokerId = 0l;

	Validate validate = new Validate();

	List<Status> statusList;

	private Long customBrokerTypeId;

	@FXML
	ComboBox<String> ddlStatus;
	
	@FXML
	private void btnCancelAction() {
		closeEditCustomBrokerScreen(btnCancel);
	}

	/*
	 * private boolean validateEditHandlingScreen() { String name =
	 * txtHandling.getText(); String status =
	 * ddlStatus.getSelectionModel().getSelectedItem();
	 * 
	 * 
	 * boolean result = validate.validateEmptyness(name); if(!result) {
	 * txtHandling.setTooltip(new Tooltip("Handling Name is Mandatory"));
	 * txtHandling.setStyle("-fx-focus-color: red;");
	 * txtHandling.requestFocus(); return result; } result =
	 * validate.validateEmptyness(status); if(!result) {
	 * ddlStatus.setTooltip(new Tooltip("Status is Mandatory"));
	 * ddlStatus.setStyle("-fx-focus-color: red;"); ddlStatus.requestFocus();
	 * return result; } return result; }
	 */

	@FXML
	Label lblCustomBrokerName, lblContactNamePAPS, lblOperationPAPS, lbl24HoursPAPS, lblStatusPAPS, lblContactNamePARS,
			lblOperationPARS, lbl24HoursPARS, lblStatusPARS;

	@FXML
	Label lblType;

	boolean validatePAPS, validatePARS;

	@FXML
	private void btnUpdateCustomBrokerAction() {
		boolean response = validateEditCustomBrokerScreen();
		if (response) {
			editCustomBroker();
			closeEditCustomBrokerScreen(btnUpdateCustomBroker);
		}
	}

	private boolean validateEditCustomBrokerScreen() {
		boolean response = true;
		String name = txtCustomerBrokerName.getText();
		boolean result = validate.validateEmptyness(name);

		if (!result) {

			response = false;
			txtCustomerBrokerName.setStyle("-fx-text-box-border: red;");
			lblCustomBrokerName.setVisible(true);
			lblCustomBrokerName.setText("CustomBroker Name is Mandatory");
			lblCustomBrokerName.setTextFill(Color.RED);

		} else if (!validate.validateLength(name, 5, 25)) {

			response = false;
			txtCustomerBrokerName.setStyle("-fx-text-box-border: red;");
			lblCustomBrokerName.setVisible(true);
			lblCustomBrokerName.setText("Min. length 5 and Max. length 25");
			lblCustomBrokerName.setTextFill(Color.RED);
		}

		String type = ddlType.getSelectionModel().getSelectedItem();
		result = validate.validateEmptyness(type);
		if (!result) {

			response = false;
			ddlType.setStyle("-fx-border-color: red;");
			lblType.setVisible(true);
			lblType.setText("Choose any type");
			lblType.setTextFill(Color.RED);

		}
		if (validatePAPS) {
			String contactNamePAPS = txtContactNamePAPS.getText();
			result = validate.validateEmptyness(contactNamePAPS);
			if (!result) {

				response = false;
				txtContactNamePAPS.setStyle("-fx-text-box-border: red;");
				lblContactNamePAPS.setVisible(true);
				lblContactNamePAPS.setText("Contact Name is Mandatory");
				lblContactNamePAPS.setTextFill(Color.RED);

			} else if (!validate.validateLength(contactNamePAPS, 5, 25)) {

				response = false;
				txtContactNamePAPS.setStyle("-fx-text-box-border: red;");
				lblContactNamePAPS.setVisible(true);
				lblContactNamePAPS.setText("Min. length 5 and Max. length 25");
				lblContactNamePAPS.setTextFill(Color.RED);
			}

			String operationPAPS = ddlOperationPAPS.getSelectionModel().getSelectedItem();
			result = validate.validateEmptyness(operationPAPS);
			if (!result) {

				response = false;
				ddlOperationPAPS.setStyle("-fx-border-color: red;");
				lblOperationPAPS.setVisible(true);
				lblOperationPAPS.setText("Operation is Mandatory");
				lblOperationPAPS.setTextFill(Color.RED);

			}

			String twentyHoursPAPS = ddl24HoursPAPS.getSelectionModel().getSelectedItem();
			result = validate.validateEmptyness(twentyHoursPAPS);
			if (!result) {

				response = false;
				ddl24HoursPAPS.setStyle("-fx-border-color: red;");
				lbl24HoursPAPS.setVisible(true);
				lbl24HoursPAPS.setText("Choose 24 Hours option");
				lbl24HoursPAPS.setTextFill(Color.RED);

			}

			String statusPAPS = ddlStatusPAPS.getSelectionModel().getSelectedItem();
			result = validate.validateEmptyness(statusPAPS);
			if (!result) {

				response = false;
				ddlStatusPAPS.setStyle("-fx-border-color: red;");
				lblStatusPAPS.setVisible(true);
				lblStatusPAPS.setText("Choose any status");
				lblStatusPAPS.setTextFill(Color.RED);

			}
		}

		if (validatePARS) {
			String contactNamePARS = txtContactNamePARS.getText();
			result = validate.validateEmptyness(contactNamePARS);
			if (!result) {

				response = false;
				txtContactNamePARS.setStyle("-fx-text-box-border: red;");
				lblContactNamePARS.setVisible(true);
				lblContactNamePARS.setText("Contact Name is Mandatory");
				lblContactNamePARS.setTextFill(Color.RED);

			} else if (!validate.validateLength(contactNamePARS, 5, 25)) {

				response = false;
				txtContactNamePARS.setStyle("-fx-text-box-border: red;");
				lblContactNamePARS.setVisible(true);
				lblContactNamePARS.setText("Min. length 5 and Max. length 25");
				lblContactNamePARS.setTextFill(Color.RED);
			}

			String operationPARS = ddlOperationPARS.getSelectionModel().getSelectedItem();
			result = validate.validateEmptyness(operationPARS);
			if (!result) {

				response = false;
				ddlOperationPARS.setStyle("-fx-border-color: red;");
				lblOperationPARS.setVisible(true);
				lblOperationPARS.setText("Operation is Mandatory");
				lblOperationPARS.setTextFill(Color.RED);

			}

			String twentyHoursPARS = ddl24HoursPARS.getSelectionModel().getSelectedItem();
			result = validate.validateEmptyness(twentyHoursPARS);
			if (!result) {

				response = false;
				ddl24HoursPARS.setStyle("-fx-border-color: red;");
				lbl24HoursPARS.setVisible(true);
				lbl24HoursPARS.setText("Choose 24 Hours option");
				lbl24HoursPARS.setTextFill(Color.RED);

			}

			String statusPARS = ddlStatusPARS.getSelectionModel().getSelectedItem();
			result = validate.validateEmptyness(statusPARS);
			if (!result) {

				response = false;
				ddlStatusPARS.setStyle("-fx-border-color: red;");
				lblStatusPARS.setVisible(true);
				lblStatusPARS.setText("Choose any status");
				lblStatusPARS.setTextFill(Color.RED);

			}
		}
		return response;
	}

	@FXML
	private void customerBrokerNameKeyReleased() {
		String name = txtCustomerBrokerName.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCustomBrokerName.setText("");
			txtCustomerBrokerName.setStyle("-fx-focus-color: skyblue;");
			lblCustomBrokerName.setVisible(false);
			if (!validate.validateLength(name, 5, 25)) {

				txtCustomerBrokerName.setStyle("-fx-border-color: red;");
				lblCustomBrokerName.setVisible(true);
				lblCustomBrokerName.setText("Min. length 5 and Max. length 25");
				lblCustomBrokerName.setTextFill(Color.RED);
			}
		} else {
			txtCustomerBrokerName.setStyle("-fx-border-color: red;");
			lblCustomBrokerName.setVisible(true);
			lblCustomBrokerName.setText("CustomBroker Name is Mandatory");
			lblCustomBrokerName.setTextFill(Color.RED);
		}
	}

	@FXML
	private void contactNamePAPSKeyReleased() {
		String name = txtContactNamePAPS.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblContactNamePAPS.setText("");
			txtContactNamePAPS.setStyle("-fx-focus-color: skyblue;");
			lblContactNamePAPS.setVisible(false);
			if (!validate.validateLength(name, 5, 25)) {

				txtContactNamePAPS.setStyle("-fx-border-color: red;");
				lblContactNamePAPS.setVisible(true);
				lblContactNamePAPS.setText("Min. length 5 and Max. length 25");
				lblContactNamePAPS.setTextFill(Color.RED);
			}
		} else {
			txtContactNamePAPS.setStyle("-fx-border-color: red;");
			lblContactNamePAPS.setVisible(true);
			lblContactNamePAPS.setText("Contact Name is Mandatory");
			lblContactNamePAPS.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddlOperationPAPSAction() {
		String operationPAPS = ddlOperationPAPS.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(operationPAPS);
		if (result) {
			lblOperationPAPS.setText("");
			ddlOperationPAPS.setStyle("-fx-focus-color: skyblue;");
			lblOperationPAPS.setVisible(false);
		} else {
			ddlOperationPAPS.setStyle("-fx-border-color: red;");
			lblOperationPAPS.setVisible(true);
			lblOperationPAPS.setText("Operation is Mandatory");
			lblOperationPAPS.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddl24HoursPAPSAction() {
		String twentyFourHoursPAPS = ddl24HoursPAPS.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(twentyFourHoursPAPS);
		if (result) {
			lbl24HoursPAPS.setText("");
			ddl24HoursPAPS.setStyle("-fx-focus-color: skyblue;");
			lbl24HoursPAPS.setVisible(false);
		} else {
			ddl24HoursPAPS.setStyle("-fx-border-color: red;");
			lbl24HoursPAPS.setVisible(true);
			lbl24HoursPAPS.setText("Choose 24 Hours option");
			lbl24HoursPAPS.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddlStatusPAPSAction() {
		String twentyFourHoursPAPS = ddlStatusPAPS.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(twentyFourHoursPAPS);
		if (result) {
			lblStatusPAPS.setText("");
			ddlStatusPAPS.setStyle("-fx-focus-color: skyblue;");
			lblStatusPAPS.setVisible(false);

		} else {
			ddlStatusPAPS.setStyle("-fx-border-color: red;");
			lblStatusPAPS.setVisible(true);
			lblStatusPAPS.setText("Choose any status");
			lblStatusPAPS.setTextFill(Color.RED);
		}

	}

	@FXML
	private void contactNamePARSKeyReleased() {
		String name = txtContactNamePARS.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblContactNamePARS.setText("");
			txtContactNamePARS.setStyle("-fx-focus-color: skyblue;");
			lblContactNamePARS.setVisible(false);
			if (!validate.validateLength(name, 5, 25)) {

				txtContactNamePARS.setStyle("-fx-border-color: red;");
				lblContactNamePARS.setVisible(true);
				lblContactNamePARS.setText("Min. length 5 and Max. length 25");
				lblContactNamePARS.setTextFill(Color.RED);
			}
		} else {
			txtContactNamePARS.setStyle("-fx-border-color: red;");
			lblContactNamePARS.setVisible(true);
			lblContactNamePARS.setText("Contact Name is Mandatory");
			lblContactNamePARS.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddlOperationPARSAction() {
		String operationPAPS = ddlOperationPARS.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(operationPAPS);
		if (result) {
			lblOperationPARS.setText("");
			ddlOperationPARS.setStyle("-fx-focus-color: skyblue;");
			lblOperationPARS.setVisible(false);
		} else {
			ddlOperationPARS.setStyle("-fx-border-color: red;");
			lblOperationPARS.setVisible(true);
			lblOperationPARS.setText("Operation is Mandatory");
			lblOperationPARS.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddl24HoursPARSAction() {
		String twentyFourHoursPAPS = ddl24HoursPARS.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(twentyFourHoursPAPS);
		if (result) {
			lbl24HoursPARS.setText("");
			ddl24HoursPARS.setStyle("-fx-focus-color: skyblue;");
			lbl24HoursPARS.setVisible(false);
		} else {
			ddl24HoursPARS.setStyle("-fx-border-color: red;");
			lbl24HoursPARS.setVisible(true);
			lbl24HoursPARS.setText("Choose 24 Hours option");
			lbl24HoursPARS.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddlStatusPARSAction() {
		String twentyFourHoursPAPS = ddlStatusPARS.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(twentyFourHoursPAPS);
		if (result) {
			lblStatusPARS.setText("");
			ddlStatusPARS.setStyle("-fx-focus-color: skyblue;");
			lblStatusPARS.setVisible(false);
		} else {
			ddlStatusPARS.setStyle("-fx-border-color: red;");
			lblStatusPARS.setVisible(true);
			lblStatusPARS.setText("Choose any status");
			lblStatusPARS.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddlTypeAction() {
		String type = ddlType.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(type);
		if (result) {
			lblType.setText("");
			ddlType.setStyle("-fx-focus-color: skyblue;");
			lblType.setVisible(false);
		} else {
			ddlType.setStyle("-fx-border-color: red;");
			lblType.setVisible(true);
			lblType.setText("Choose any type");
			lblType.setTextFill(Color.RED);
		}
	}

	private void closeEditCustomBrokerScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void editCustomBroker() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					CustomBroker customBroker = setCustomBrokerValue();
					String payload = mapper.writeValueAsString(customBroker);
					System.out.println("AYYYY: " + payload);
					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_CUSTOM_BROKER_API + "/" + customBrokerId, null,
							payload);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<CustomBroker> customBrokerList = (List<CustomBroker>) success.getResultList();
						String res = mapper.writeValueAsString(customBrokerList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.customBrokerController.fillCustomBrokers(res);
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

	private CustomBroker setCustomBrokerValue() {
		CustomBroker customBroker = new CustomBroker();
		customBroker.setCustomBrokerId(customBrokerId);
		customBroker.setCustomBrokerName(txtCustomerBrokerName.getText());
		List<CustomBrokerTypeModel> customBrokerTypeModelList = new ArrayList<>();
		CustomBrokerTypeModel customBrokerTypeModel = null;
		switch (ddlType.getSelectionModel().getSelectedItem()) {
		case Iconstants.CUSTOM_BROKER_PAPS:
			customBrokerTypeModel = setPAPS(customBroker);
			customBrokerTypeModelList.add(customBrokerTypeModel);
			break;
		case Iconstants.CUSTOM_BROKER_PARS:
			customBrokerTypeModel = setPARS(customBroker);
			customBrokerTypeModelList.add(customBrokerTypeModel);
			break;
		default:
			customBrokerTypeModel = setPAPS(customBroker);
			customBrokerTypeModelList.add(customBrokerTypeModel);
			customBrokerTypeModel = setPARS(customBroker);
			customBrokerTypeModelList.add(customBrokerTypeModel);
		}
		customBroker.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		customBroker.setCustomBrokerTypes(customBrokerTypeModelList);
		return customBroker;
	}

	private CustomBrokerTypeModel setPAPS(CustomBroker customBroker) {
		CustomBrokerTypeModel customBrokerTypeModel = new CustomBrokerTypeModel();
		for (Type type : typeList) {
			if (type.getTypeName().equals(Iconstants.CUSTOM_BROKER_PAPS)) {
				customBrokerTypeModel.setTypeId(type.getTypeId());
				break;
			}
		}
		if (papsCustomBrokerTypeId != null && papsCustomBrokerTypeId != 0l) {
			customBrokerTypeModel.setCustomBrokerTypeId(papsCustomBrokerTypeId);
		}
		// customBrokerTypeModel.setCustomBrokerTypeId(customBrokerTypeId);
		customBrokerTypeModel.setContactName(txtContactNamePAPS.getText());
		customBrokerTypeModel
				.setOperationId(operationList.get(ddlOperationPAPS.getSelectionModel().getSelectedIndex()).getTypeId());
		customBrokerTypeModel.setPhone(txtCentralPhonePAPS.getText());
		customBrokerTypeModel.setExtention(txtExtensionPAPS.getText());
		customBrokerTypeModel.setFaxNumber(txtCentralFaxPAPS.getText());
		customBrokerTypeModel
				.setTimeZoneId(timeZoneList.get(ddl24HoursPAPS.getSelectionModel().getSelectedIndex()).getTypeId());
		customBrokerTypeModel.setEmail(txtEmailPAPS.getText());
		customBrokerTypeModel.setTrackerLink(txtTrackerLinkPAPS.getText());
		customBrokerTypeModel.setStatusId(statusList.get(ddlStatusPAPS.getSelectionModel().getSelectedIndex()).getId());
		// customBrokerTypeModel.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		return customBrokerTypeModel;
	}

	private CustomBrokerTypeModel setPARS(CustomBroker customBroker) {
		CustomBrokerTypeModel customBrokerTypeModel = new CustomBrokerTypeModel();
		for (Type type : typeList) {
			if (type.getTypeName().equals(Iconstants.CUSTOM_BROKER_PARS)) {
				customBrokerTypeModel.setTypeId(type.getTypeId());
				break;
			}
		}
		if (parsCustomBrokerTypeId != null && parsCustomBrokerTypeId != 0l) {
			customBrokerTypeModel.setCustomBrokerTypeId(parsCustomBrokerTypeId);
		}
		customBrokerTypeModel.setContactName(txtContactNamePARS.getText());
		customBrokerTypeModel
				.setOperationId(operationList.get(ddlOperationPARS.getSelectionModel().getSelectedIndex()).getTypeId());
		customBrokerTypeModel.setPhone(txtCentralPhonePARS.getText());
		customBrokerTypeModel.setExtention(txtExtensionPARS.getText());
		customBrokerTypeModel.setFaxNumber(txtCentralFaxPARS.getText());
		customBrokerTypeModel
				.setTimeZoneId(timeZoneList.get(ddl24HoursPARS.getSelectionModel().getSelectedIndex()).getTypeId());
		customBrokerTypeModel.setEmail(txtEmailPARS.getText());
		customBrokerTypeModel.setTrackerLink(txtTrackerLinkPARS.getText());
		customBrokerTypeModel.setStatusId(statusList.get(ddlStatusPARS.getSelectionModel().getSelectedIndex()).getId());
		// customBrokerTypeModel.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		return customBrokerTypeModel;
	}

	@FXML
	public void handleEditAction() {
		btnEdit.setDisable(true);
		disableFields(false);
	}

	private void disableFields(boolean v) {
		btnUpdateCustomBroker.setDisable(v);
		txtCentralFaxPAPS.setDisable(v);
		txtCentralFaxPARS.setDisable(v);
		txtCentralPhonePAPS.setDisable(v);
		txtCentralPhonePARS.setDisable(v);
		txtContactNamePAPS.setDisable(v);
		txtContactNamePARS.setDisable(v);
		txtCustomerBrokerName.setDisable(v);
		txtEmailPAPS.setDisable(v);
		txtEmailPARS.setDisable(v);
		txtExtensionPAPS.setDisable(v);
		txtExtensionPARS.setDisable(v);
		txtTrackerLinkPAPS.setDisable(v);
		txtTrackerLinkPARS.setDisable(v);
		ddl24HoursPAPS.setDisable(v);
		ddl24HoursPARS.setDisable(v);
		ddlOperationPAPS.setDisable(v);
		ddlOperationPARS.setDisable(v);
//		ddlStatus.setDisable(v);
		ddlStatusPAPS.setDisable(v);
		ddlStatusPARS.setDisable(v);
		ddlType.setDisable(v);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (CustomBrokerController.flag == 1) {
			disableFields(true);
		}
		if (CustomBrokerController.flag == 2) {
			btnEdit.setVisible(false);
		}

		ddlType.valueProperty().addListener(new ChangeListener<String>() {

			@SuppressWarnings("rawtypes")
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						try {
							String typeValue = ddlType.getSelectionModel().getSelectedItem();
							if (typeValue != null) {
								switch (typeValue) {
								case "PARS":
									validatePARS = true;
									validatePAPS = false;
									clearPAPS();
									setPARSEditable(false);
									setPAPSEditable(true);
									break;

								case "PAPS":
									validatePARS = false;
									validatePAPS = true;
									clearPARS();
									setPARSEditable(true);
									setPAPSEditable(false);
									break;

								case "Both":
									validatePAPS = true;
									validatePARS = true;
									setPAPSEditable(false);
									setPARSEditable(false);
									break;
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

	private void setPAPSEditable(boolean val) {
		txtContactNamePAPS.setDisable(val);
		ddlOperationPAPS.setDisable(val);
		txtCentralPhonePAPS.setDisable(val);
		txtExtensionPAPS.setDisable(val);
		txtCentralFaxPAPS.setDisable(val);
		ddl24HoursPAPS.setDisable(val);
		txtEmailPAPS.setDisable(val);
		txtTrackerLinkPAPS.setDisable(val);
		ddlStatusPAPS.setDisable(val);
	}

	private void setPARSEditable(boolean val) {
		txtContactNamePARS.setDisable(val);
		ddlOperationPARS.setDisable(val);
		txtCentralPhonePARS.setDisable(val);
		txtExtensionPARS.setDisable(val);
		txtCentralFaxPARS.setDisable(val);
		ddl24HoursPARS.setDisable(val);
		txtEmailPARS.setDisable(val);
		txtTrackerLinkPARS.setDisable(val);
		ddlStatusPARS.setDisable(val);
	}

	private void clearPAPS() {
		txtContactNamePAPS.setText("");
		// ddlOperationPAPS.getSelectionModel().select(0);
		txtCentralPhonePAPS.setText("");
		txtExtensionPAPS.setText("");
		txtCentralFaxPAPS.setText("");
		// ddl24HoursPAPS.getSelectionModel().select(0);
		txtEmailPAPS.setText("");
		txtTrackerLinkPAPS.setText("");
		// ddlStatusPAPS.getSelectionModel().select(0);
	}

	private void clearPARS() {
		txtContactNamePARS.setText("");
		// ddlOperationPARS.getSelectionModel().select(0);
		txtCentralPhonePARS.setText("");
		txtExtensionPARS.setText("");
		txtCentralFaxPARS.setText("");
		// ddl24HoursPARS.getSelectionModel().select(0);
		txtEmailPARS.setText("");
		txtTrackerLinkPARS.setText("");
		// ddlStatusPARS.getSelectionModel().select(0);
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public void initData(CustomBroker customBroker) {
		customBrokerId = customBroker.getCustomBrokerId();
		txtCustomerBrokerName.setText(customBroker.getCustomBrokerName());
		String customBrokerType = null;
		for (Type type : customBroker.getTypeList()) {
			if (type.getTypeId() == customBroker.getTypeId()) {
				customBrokerType = type.getTypeName();
				break;
			}
		}
		switch (customBrokerType) {
		case Iconstants.CUSTOM_BROKER_PAPS:
			validatePAPS = true;
			validatePARS = false;
			setPAPS(customBroker, 0);
			setOther(customBrokerType);
			break;
		case Iconstants.CUSTOM_BROKER_PARS:
			validatePAPS = false;
			validatePARS = true;
			customBrokerTypeId = customBroker.getCustomBrokerTypes().get(0).getCustomBrokerTypeId();
			setPARS(customBroker, 0);
			setOther(customBrokerType);
			break;
		default:
			validatePAPS = true;
			validatePARS = true;
			setBoth(customBroker, 0);
			break;
		}
		customBrokerTypeId = customBroker.getCustomBrokerTypes().get(0).getCustomBrokerTypeId();
		typeList = customBroker.getTypeList();
		for (int i = 0; i < typeList.size(); i++) {
			Type type = typeList.get(i);
			ddlType.getItems().add(type.getTypeName());
			if (type.getTypeId() == customBroker.getTypeId()) {
				ddlType.getSelectionModel().select(i);
			}
		}
	}

	List<Type> typeList, operationList, timeZoneList;

	private void setOther(String customBrokerType) {
		if (customBrokerType.equals(Iconstants.CUSTOM_BROKER_PAPS)) {
			for (int i = 0; i < operationList.size(); i++) {
				Type type = operationList.get(i);
				ddlOperationPARS.getItems().add(type.getTypeName());
			}
			for (int i = 0; i < timeZoneList.size(); i++) {
				Type type = timeZoneList.get(i);
				ddl24HoursPARS.getItems().add(type.getTypeName());
			}
			for (int i = 0; i < statusList.size(); i++) {
				Status status = statusList.get(i);
				ddlStatusPARS.getItems().add(status.getStatus());
			}
		} else if (customBrokerType.equals(Iconstants.CUSTOM_BROKER_PARS)) {
			for (int i = 0; i < operationList.size(); i++) {
				Type type = operationList.get(i);
				ddlOperationPAPS.getItems().add(type.getTypeName());
			}
			for (int i = 0; i < timeZoneList.size(); i++) {
				Type type = timeZoneList.get(i);
				ddl24HoursPAPS.getItems().add(type.getTypeName());
			}
			for (int i = 0; i < statusList.size(); i++) {
				Status status = statusList.get(i);
				ddlStatusPAPS.getItems().add(status.getStatus());
			}
		}
	}

	Long papsCustomBrokerTypeId, parsCustomBrokerTypeId = 0l;

	private void setPAPS(CustomBroker customBroker, int index) {
		CustomBrokerTypeModel customBrokerTypeModel = customBroker.getCustomBrokerTypes().get(index);
		txtContactNamePAPS.setText(customBrokerTypeModel.getContactName());
		operationList = customBroker.getOperationList();
		papsCustomBrokerTypeId = customBroker.getCustomBrokerTypes().get(0).getCustomBrokerTypeId();
		for (int i = 0; i < operationList.size(); i++) {
			Type type = operationList.get(i);
			ddlOperationPAPS.getItems().add(type.getTypeName());
			if (type.getTypeId() == customBrokerTypeModel.getOperationId()) {
				ddlOperationPAPS.getSelectionModel().select(i);
			}
		}
		txtCentralPhonePAPS.setText(customBrokerTypeModel.getPhone());
		txtExtensionPAPS.setText(customBrokerTypeModel.getExtention());
		txtCentralFaxPAPS.setText(customBrokerTypeModel.getFaxNumber());
		timeZoneList = customBroker.getTimeZoneList();
		for (int i = 0; i < timeZoneList.size(); i++) {
			Type type = timeZoneList.get(i);
			ddl24HoursPAPS.getItems().add(type.getTypeName());
			if (type.getTypeId() == customBrokerTypeModel.getTimeZoneId()) {
				ddl24HoursPAPS.getSelectionModel().select(i);
			}
		}
		txtEmailPAPS.setText(customBrokerTypeModel.getEmail());
		txtTrackerLinkPAPS.setText(customBrokerTypeModel.getTrackerLink());
		statusList = customBroker.getStatusList();
		for (int i = 0; i < statusList.size(); i++) {
			Status status = statusList.get(i);
			ddlStatusPAPS.getItems().add(status.getStatus());
			if (status.getId() == customBrokerTypeModel.getStatusId()) {
				ddlStatusPAPS.getSelectionModel().select(i);
			}
		}
	}

	private void setPARS(CustomBroker customBroker, int index) {
		CustomBrokerTypeModel customBrokerTypeModel = customBroker.getCustomBrokerTypes().get(index);
		txtContactNamePARS.setText(customBrokerTypeModel.getContactName());
		operationList = customBroker.getOperationList();
		parsCustomBrokerTypeId = customBroker.getCustomBrokerTypes().get(0).getCustomBrokerTypeId();
		for (int i = 0; i < operationList.size(); i++) {
			Type type = operationList.get(i);
			ddlOperationPARS.getItems().add(type.getTypeName());
			if (type.getTypeId() == customBrokerTypeModel.getOperationId()) {
				ddlOperationPARS.getSelectionModel().select(i);
			}
		}
		txtCentralPhonePARS.setText(customBrokerTypeModel.getPhone());
		txtExtensionPARS.setText(customBrokerTypeModel.getExtention());
		txtCentralFaxPARS.setText(customBrokerTypeModel.getFaxNumber());
		timeZoneList = customBroker.getTimeZoneList();
		for (int i = 0; i < timeZoneList.size(); i++) {
			Type type = timeZoneList.get(i);
			ddl24HoursPARS.getItems().add(type.getTypeName());
			if (type.getTypeId() == customBrokerTypeModel.getTimeZoneId()) {
				ddl24HoursPARS.getSelectionModel().select(i);
			}
		}
		txtEmailPARS.setText(customBrokerTypeModel.getEmail());
		txtTrackerLinkPARS.setText(customBrokerTypeModel.getTrackerLink());
		statusList = customBroker.getStatusList();
		for (int i = 0; i < statusList.size(); i++) {
			Status status = statusList.get(i);
			ddlStatusPARS.getItems().add(status.getStatus());
			if (status.getId() == customBrokerTypeModel.getStatusId()) {
				ddlStatusPARS.getSelectionModel().select(i);
			}
		}
	}

	private void setBoth(CustomBroker customBroker, int index) {
		setPAPS(customBroker, 0);
		setPARS(customBroker, 1);
	}
}