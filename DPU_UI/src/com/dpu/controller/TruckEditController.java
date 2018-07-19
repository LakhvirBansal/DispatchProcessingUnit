package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Category;
import com.dpu.model.Division;
import com.dpu.model.Failed;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Terminal;
import com.dpu.model.Truck;
import com.dpu.model.Type;
import com.dpu.util.Validate;
//import com.dpu.validations.TruckValidator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TruckEditController extends Application implements Initializable {

	@FXML
	Button btnUpdateTruck,btnEdit, btnCancelTruck;

	Long truckId = 0l;

	@FXML
	TextField txtUnitNo, txtUsage, txtOwner, txtOoName, txtFinance;

	@FXML
	ComboBox<String> ddlStatus, ddlCategory, ddlDivision, ddlTerminal, ddlTruckType;

	@FXML
	Label lblUnitNo;

	@FXML
	private void btnUpdateTruckAction() {
		if (validateEditTruckScreen()) {
			editTruck();
			closeEditTruckScreen(btnUpdateTruck);
		}
	}
	
	@FXML
	private void btnCancelTruckAction() {
			closeEditTruckScreen(btnCancelTruck);
	}

	private void closeEditTruckScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void editTruck() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Truck truck = setTruckValue();
					String payload = mapper.writeValueAsString(truck);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_TRUCK_API + "/" + truckId, null, payload);
					// MainScreen.truckController.fillTruck(response);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Truck> truckList = (List<Truck>) success.getResultList();
						String res = mapper.writeValueAsString(truckList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.truckController.fillTruck(res);
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
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}

	private void disableFields(boolean v) {
		btnUpdateTruck.setDisable(v);
		txtFinance.setDisable(v);
		txtOoName.setDisable(v);
		txtOwner.setDisable(v);
		txtUnitNo.setDisable(v);
		txtUsage.setDisable(v);
		ddlCategory.setDisable(v);
		ddlDivision.setDisable(v);
		ddlStatus.setDisable(v);
		ddlTerminal.setDisable(v);
		ddlTruckType.setDisable(v);

	}

	@FXML
	private void handleEditAction() {
		btnEdit.setDisable(true);
		disableFields(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (TruckController.flag == 1) {
			disableFields(true);
		}
		if (TruckController.flag == 2) {
			btnEdit.setVisible(false);
		}
		txtUnitNo.addEventFilter(KeyEvent.KEY_TYPED, Validate.numeric_Validation(10));
	}

	@Override
	public void start(Stage primaryStage) {
	}

	private Truck setTruckValue() {
		Truck truck = new Truck();
		truck.setUnitNo(Integer.parseInt(txtUnitNo.getText()));
		truck.setOwner(txtOwner.getText());
		truck.setoOName(txtOoName.getText());
		truck.setTruchUsage(txtUsage.getText());
		truck.setFinance(txtFinance.getText());
		truck.setCategoryId(categoryList.get(ddlCategory.getSelectionModel().getSelectedIndex()).getCategoryId());
		truck.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		truck.setDivisionId(divisionList.get(ddlDivision.getSelectionModel().getSelectedIndex()).getDivisionId());
		truck.setTerminalId(terminalList.get(ddlTerminal.getSelectionModel().getSelectedIndex()).getTerminalId());
		truck.setTruckTypeId(truckTypeList.get(ddlTruckType.getSelectionModel().getSelectedIndex()).getTypeId());
		return truck;
	}

	ObjectMapper mapper = new ObjectMapper();

	List<Category> categoryList = null;

	List<Status> statusList = null;

	List<Division> divisionList = null;

	List<Terminal> terminalList = null;

	List<Type> truckTypeList = null;

	public void initData(Truck t) {
		truckId = t.getTruckId();
		txtUnitNo.setText(String.valueOf(t.getUnitNo()));
		txtOwner.setText(t.getOwner());
		txtOoName.setText(t.getoOName());
		txtUsage.setText(t.getTruchUsage());
		txtFinance.setText(t.getFinance());
		categoryList = t.getCategoryList();
		for (int i = 0; i < t.getCategoryList().size(); i++) {
			Category category = t.getCategoryList().get(i);
			ddlCategory.getItems().add(category.getName());
			if (category.getCategoryId() == t.getCategoryId()) {
				ddlCategory.getSelectionModel().select(i);
			}
		}
		statusList = t.getStatusList();
		for (int i = 0; i < t.getStatusList().size(); i++) {
			Status status = t.getStatusList().get(i);
			ddlStatus.getItems().add(status.getStatus());
			if (status.getId() == t.getStatusId()) {
				ddlStatus.getSelectionModel().select(i);
			}
		}
		divisionList = t.getDivisionList();
		for (int i = 0; i < t.getDivisionList().size(); i++) {
			Division division = t.getDivisionList().get(i);
			ddlDivision.getItems().add(division.getDivisionName());
			if (division.getDivisionId() == t.getDivisionId()) {
				ddlDivision.getSelectionModel().select(i);
			}
		}
		terminalList = t.getTerminalList();
		for (int i = 0; i < t.getTerminalList().size(); i++) {
			Terminal terminal = t.getTerminalList().get(i);
			ddlTerminal.getItems().add(terminal.getTerminalName());
			if (terminal.getTerminalId() == t.getTerminalId()) {
				ddlTerminal.getSelectionModel().select(i);
			}
		}
		truckTypeList = t.getTruckTypeList();
		for (int i = 0; i < t.getTruckTypeList().size(); i++) {
			Type truck = t.getTruckTypeList().get(i);
			ddlTruckType.getItems().add(truck.getTypeName());
			if (truck.getTypeId() == t.getTruckTypeId()) {
				ddlTruckType.getSelectionModel().select(i);
			}
		}
	}

	// validation Applying

	@FXML
	Label unitMsg;

	Validate validate = new Validate();

	private boolean validateEditTruckScreen() {

		boolean result = true;
		String additionalContact = txtUnitNo.getText();

		boolean blnAdditionalContact = validate.validateEmptyness(additionalContact);
		if (!blnAdditionalContact) {
			result = false;
			txtUnitNo.setStyle("-fx-text-box-border: red;");
			unitMsg.setVisible(true);
			unitMsg.setText("Unit No is Mandatory");
			unitMsg.setTextFill(Color.RED);
		}

		return result;
	}

	@FXML
	private void truckUnitKeyPressed() {

		String name = txtUnitNo.getText();
		boolean result = validate.validateEmptyness(name);

		if (!result) {
			txtUnitNo.setStyle("-fx-focus-color: red;");
			txtUnitNo.requestFocus();
			unitMsg.setVisible(true);
			unitMsg.setText("Unit No is Mandatory");
			unitMsg.setTextFill(Color.RED);
		} else {
			txtUnitNo.setStyle("-fx-focus-color: skyblue;");
			unitMsg.setVisible(false);
		}
	}
}