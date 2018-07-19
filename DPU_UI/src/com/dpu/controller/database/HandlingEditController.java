package com.dpu.controller.database;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.MainScreen;
import com.dpu.model.Failed;
import com.dpu.model.HandlingModel;
import com.dpu.model.Status;
import com.dpu.model.Success;
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

public class HandlingEditController extends Application implements Initializable {

	@FXML
	Button btnUpdateHandling, btnEdit, btnCancel;

	Long handlingId = 0l;

	@FXML
	TextField txtHandling;
	@FXML
	Label lblHandling, lblStatus;
	Validate validate = new Validate();

	@FXML
	ComboBox<String> ddlStatus;

	private boolean validateEditHandlingScreen() {
		boolean response = true;
		String name = txtHandling.getText();
		String status = ddlStatus.getSelectionModel().getSelectedItem();

		boolean result = validate.validateEmptyness(name);
		if (!result) {
			txtHandling.setStyle("-fx-focus-color: red;");
			lblHandling.setVisible(true);
			lblHandling.setText("Handling Name is Mandatory");
			lblHandling.setTextFill(Color.RED);
		} else if (!validate.validateLength(name, 5, 25)) {
			response = false;
			txtHandling.setStyle("-fx-focus-color: red;");
			lblHandling.setVisible(true);
			lblHandling.setText("Min. length 5 and Max. length 25");
			lblHandling.setTextFill(Color.RED);
			return result;
		}
		result = validate.validateEmptyness(status);
		if (!result) {
			response = false;
			ddlStatus.setStyle("-fx-focus-color: red;");
			lblStatus.setVisible(true);
			lblStatus.setText("Status is Mandatory");
			lblStatus.setTextFill(Color.RED);
		}
		return response;
	}

	@FXML
	private void btnCancelAction() {
		closeEditHandlingScreen(btnCancel);
	}
	
	@FXML
	private void btnUpdateHandlingAction() {
		boolean response = validateEditHandlingScreen();
		if (response) {
			editHandling();
			closeEditHandlingScreen(btnUpdateHandling);
		}
	}

	private void closeEditHandlingScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void editHandling() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					HandlingModel handling = setHandlingValue();
					String payload = mapper.writeValueAsString(handling);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_HANDLING_API + "/" + handlingId, null, payload);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<HandlingModel> handlingList = (List<HandlingModel>) success.getResultList();
						String res = mapper.writeValueAsString(handlingList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.handlingController.fillHandling(res);
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

	List<Status> statusList;

	private HandlingModel setHandlingValue() {
		HandlingModel dPUService = new HandlingModel();
		dPUService.setName(txtHandling.getText());
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
		if (HandlingController.flag == 1) {
			disableFields(true);
		}
		if (HandlingController.flag == 2) {
			btnEdit.setVisible(false);
		}
	}

	private void disableFields(boolean v) {
		btnUpdateHandling.setDisable(v);
		txtHandling.setDisable(v);
		ddlStatus.setDisable(v);
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public void initData(HandlingModel handling) {
		handlingId = handling.getId();
		txtHandling.setText(handling.getName());
		statusList = handling.getStatusList();
		for (int i = 0; i < handling.getStatusList().size(); i++) {
			Status status = handling.getStatusList().get(i);
			ddlStatus.getItems().add(status.getStatus());
			if (status.getId() == handling.getStatusId()) {
				ddlStatus.getSelectionModel().select(i);
			}
		}
	}
}