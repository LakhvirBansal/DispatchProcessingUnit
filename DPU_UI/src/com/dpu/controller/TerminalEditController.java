package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.DPUService;
import com.dpu.model.Failed;
import com.dpu.model.Shipper;
import com.dpu.model.Success;
import com.dpu.model.Terminal;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TerminalEditController extends Application implements Initializable {
	@FXML
	Button btnUpdateTerminal, btnEdit, btnCancel;

	Long terminalId = 0l;

	@FXML
	TextField txtTerminalName, txtLocation;

	@FXML
	ComboBox<Object> ddlAvailableServices, ddlLocation;
	@FXML
	Label lblTerminalName, lblLocation, lblAvailableServices;
	Validate validate = new Validate();

	@FXML
	private void btnUpdateTerminalAction() {
		boolean res = validateEditTerminalScreen();
		if (res) {
			editTerminal();
			closeEditTerminalScreen(btnUpdateTerminal);
		}

	}
	
	@FXML
	private void btnCancelAction() {
		closeEditTerminalScreen(btnCancel);
	}

	private boolean validateEditTerminalScreen() {
		boolean response = true;
		String name = txtTerminalName.getText();
		String service = ddlAvailableServices.getSelectionModel().getSelectedItem().toString();
		String location = ddlLocation.getSelectionModel().getSelectedItem().toString();

		boolean result = validate.validateEmptyness(name);
		if (!result) {
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			txtTerminalName.setStyle("-fx-focus-color: red;");
			lblTerminalName.setVisible(true);
			lblTerminalName.setText("Terminal Name is Mandatory");
			lblTerminalName.setTextFill(Color.RED);

		} else if (!validate.validateLength(name, 5, 25)) {
			response = false;
			txtTerminalName.setStyle("-fx-focus-color: red;");
			lblTerminalName.setVisible(true);
			lblTerminalName.setText("Min. length 5 and Max. length 25");
			lblTerminalName.setTextFill(Color.RED);
			return result;
		}
		result = validate.validateEmptyness(service);
		if (!result) {
			response = false;
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			ddlAvailableServices.setStyle("-fx-focus-color: red;");
			lblAvailableServices.setVisible(true);
			lblAvailableServices.setText("Service name is Mandatory");
			lblAvailableServices.setTextFill(Color.RED);
		}
		result = validate.validateEmptyness(location);
		if (!result) {
			response = false;
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			ddlLocation.setStyle("-fx-focus-color: red;");
			lblLocation.setVisible(true);
			lblLocation.setText("Location name is Mandatory");
			lblLocation.setTextFill(Color.RED);
		}
		return response;
	}

	private void closeEditTerminalScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void editTerminal() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Terminal terminal = setTerminalValue();
					String payload = mapper.writeValueAsString(terminal);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_TERMINAL_API + "/" + terminalId, null, payload);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Terminal> terminalList = (List<Terminal>) success.getResultList();
						String res = mapper.writeValueAsString(terminalList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.terminalController.fillTerminal(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}
					/*
					 * if (response != null && response.contains("message")) {
					 * Success success = mapper.readValue(response,
					 * Success.class); JOptionPane.showMessageDialog(null,
					 * success.getMessage(), "Info", 1); } else { Failed failed
					 * = mapper.readValue(response, Failed.class);
					 * JOptionPane.showMessageDialog(null, failed.getMessage(),
					 * "Info", 1); }
					 */
					// MainScreen.terminalController.fetchTerminals();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}

	@FXML
	public void handleEditAction() {
		btnEdit.setDisable(true);
		disableFields(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		System.out.println("initilize");
		if (TerminalPanelController.flag == 1) {
			disableFields(true);
		}
		if (TerminalPanelController.flag == 2) {
			btnEdit.setVisible(false);
		}
	}

	private void disableFields(boolean v) {
		btnUpdateTerminal.setDisable(v);
//		txtLocation.setDisable(v);
		txtTerminalName.setDisable(v);
		ddlAvailableServices.setDisable(v);
		ddlLocation.setDisable(v);
	}

	@Override
	public void start(Stage primaryStage) {
	}

	private Terminal setTerminalValue() {
		Terminal terminal = new Terminal();
		terminal.setTerminalName(txtTerminalName.getText());
		terminal.setShipperId(shipperList.get(ddlLocation.getSelectionModel().getSelectedIndex()).getShipperId());
		List<Long> serviceIds = new ArrayList<>();
		Long serviceId = serviceList.get(ddlAvailableServices.getSelectionModel().getSelectedIndex()).getServiceId();
		terminal.setStatusId(0l);
		serviceIds.add(serviceId);
		terminal.setServiceIds(serviceIds);
		return terminal;
	}

	List<Shipper> shipperList = null;

	List<DPUService> serviceList = null;

	public void initData(Terminal t) {
		System.out.println("initData");
		terminalId = t.getTerminalId();
		txtTerminalName.setText(t.getTerminalName());
		shipperList = t.getShipperList();
		for (int i = 0; i < t.getShipperList().size(); i++) {
			Shipper shipper = t.getShipperList().get(i);
			System.out.println(t.getShipperList().size()+" :::  locationName: "+shipper.getLocationName());
			ddlLocation.getItems().add(shipper.getLocationName());
			if (shipper.getShipperId() == t.getShipperId()) {
				ddlLocation.getSelectionModel().select(i);
			}
		}
		serviceList = t.getServiceList();
		for (int i = 0; i < t.getServiceList().size(); i++) {
			DPUService service = t.getServiceList().get(i);
			ddlAvailableServices.getItems().add(service.getServiceName());
			for (int j = 0; j < t.getServiceIds().size(); j++) {
				if (service.getServiceId() == t.getServiceIds().get(j)) {
					ddlAvailableServices.getSelectionModel().select(i);
				}
			}
		}
	}
}
