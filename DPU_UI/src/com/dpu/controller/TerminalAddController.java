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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TerminalAddController extends Application implements Initializable {

	@FXML
	Button btnSaveTerminal;

	@FXML
	Button btnCancel, btnAddAvailableServices, btnAddNewLocation;

	@FXML
	TextField txtTerminalName;
	@FXML
	Label lblTerminalName, lblLocation, lblAvailableServices;
	Validate validate = new Validate();
	@FXML
	ComboBox<DPUService> ddlAvailableServices;
	@FXML
	ComboBox<String> ddlLocation;

	@FXML
	private void btnSaveTerminalAction() {
		boolean result = validateAddTerminalScreen();
		if (result) {
			addTerminal();
			closeAddTerminalScreen(btnSaveTerminal);
		}
	}
	
	@FXML
	private void btnCancelAction() {
		closeAddTerminalScreen(btnCancel);
	}

	private void closeAddTerminalScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private boolean validateAddTerminalScreen() {
		boolean response = true;
		String name = txtTerminalName.getText();
		String service = ddlAvailableServices.getSelectionModel().getSelectedItem().toString();
		String location = ddlLocation.getSelectionModel().getSelectedItem();

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

	@FXML
	private void btnCancelTerminalAction() {

	}
	///////

	// Terminal terminal = mapper.readValue(response, Terminal.class);
	// serviceList = terminal.getServiceList();

	private void createComboBox(List<DPUService> serviceList) {
		// ComboBox<DPUService> combo = new ComboBox<>();
		CheckBox btn = new CheckBox();
		ddlAvailableServices.getItems().addAll(serviceList);
		ddlAvailableServices.setCellFactory(listView -> new CheckItemListCell());
	}

	class CheckItemListCell extends ListCell<DPUService> {
		private final CheckBox btn;

		CheckItemListCell() {
			// setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			btn = new CheckBox();
		}

		@Override
		protected void updateItem(DPUService item, boolean empty) {
			super.updateItem(item, empty);

			if (item == null || empty) {
				setGraphic(null);
			} else {
				btn.setText(item.getServiceName());
				// btn.selectedProperty().setValue(item.selected);
				setGraphic(btn);
			}
		}
	}

	///////
	private void addTerminal() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Terminal terminal = setTerminalValue();
					String payload = mapper.writeValueAsString(terminal);
					System.out.println("terminal add payload: " + payload);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_TERMINAL_API,
							null, payload);
					// MainScreen.terminalController.fillTerminal(response);
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
					 * if(response != null && response.contains("message")) {
					 * Success success = mapper.readValue(response,
					 * Success.class); JOptionPane.showMessageDialog(null,
					 * success.getMessage() , "Info", 1); } else { Failed failed
					 * = mapper.readValue(response, Failed.class);
					 * JOptionPane.showMessageDialog(null, failed.getMessage(),
					 * "Info", 1); }
					 */
					// TerminalPanelController tm=new TerminalPanelController();
					// tm.fetchTerminals();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}

	@FXML
	private void btnAddNewLocationAction() {
		closeAddTerminalScreen(btnAddNewLocation);
		ShipperController.openAddShipperScreen();
	}

	@FXML
	private void btnAddAvailableServicesAction() {
		closeAddTerminalScreen(btnAddAvailableServices);
		ServiceController.openAddServiceScreen();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchMasterDataForDropDowns();
	}

	ObjectMapper mapper = new ObjectMapper();

	List<DPUService> serviceList = null;

	List<Shipper> shipperList = null;

	private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					String response = GetAPIClient
							.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TERMINAL_API + "/openAdd", null);
					Terminal terminal = mapper.readValue(response, Terminal.class);
					serviceList = terminal.getServiceList();
					// fillDropDown(ddlAvailableServices, serviceList);
					createComboBox(serviceList);
					shipperList = terminal.getShipperList();
					fillDropDown(ddlLocation, shipperList);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		if (comboBox != null) {
			comboBox.getItems().clear();
			for (int i = 0; i < list.size(); i++) {
				Object object = list.get(i);
				if (object != null && object instanceof DPUService) {
					DPUService dpuService = (DPUService) object;
					comboBox.getItems().add(dpuService.getServiceName());
				}
				if (object != null && object instanceof Shipper) {
					Shipper shipper = (Shipper) object;
					comboBox.getItems().add(shipper.getLocationName());
				}
			}
		}
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
}
