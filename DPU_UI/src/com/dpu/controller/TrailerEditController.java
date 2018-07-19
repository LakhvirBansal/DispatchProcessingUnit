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
import com.dpu.model.Trailer;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TrailerEditController extends Application implements Initializable {

	@FXML
	Button btnUpdateTrailer, btnEdit, btnDateStamp, btnPrint ,btnCancelTrailer;

	Long trailerId = 0l;

	@FXML
	TextField txtUnitNo, txtUsage, txtOwner, txtOoName, txtFinance;
	@FXML
	TextArea txtNotes;
	@FXML
	ComboBox<String> ddlStatus, ddlCategory, ddlDivision, ddlTerminal, ddlTrailerType;

	@FXML
	private void btnCancelTrailerAction() {
		closeEditTrailerScreen(btnCancelTrailer);
	}
	
	@FXML
	private void btnUpdateTrailerAction() {

		boolean result = validateEditTrailerScreen();
		if (result) {
			editTrailer();
			closeEditTrailerScreen(btnUpdateTrailer);
		}
	}

	private void editTrailer() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Trailer trailer = setTrailerValue();
					String payload = mapper.writeValueAsString(trailer);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_TRAILER_API + "/" + trailerId, null, payload);
					// MainScreen.trailerController.fillTrailer(response);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Trailer> trailerList = (List<Trailer>) success.getResultList();
						String res = mapper.writeValueAsString(trailerList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.trailerController.fillTrailer(res);
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

	private Trailer setTrailerValue() {
		Trailer trailer = new Trailer();
		trailer.setUnitNo(Long.parseLong(txtUnitNo.getText()));
		trailer.setOwner(txtOwner.getText());
		trailer.setoOName(txtOoName.getText());
		trailer.setCategoryId(categoryList.get(ddlCategory.getSelectionModel().getSelectedIndex()).getCategoryId());
		trailer.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		trailer.setUsage(txtUsage.getText());
		trailer.setDivisionId(divisionList.get(ddlDivision.getSelectionModel().getSelectedIndex()).getDivisionId());
		trailer.setTerminalId(terminalList.get(ddlTerminal.getSelectionModel().getSelectedIndex()).getTerminalId());
		trailer.setTrailerTypeId(
				trailerTypeList.get(ddlTrailerType.getSelectionModel().getSelectedIndex()).getTypeId());
		trailer.setFinance(txtFinance.getText());
		return trailer;
	}

	private void closeEditTrailerScreen(Button btnSaveTrailer) {
		Stage loginStage = (Stage) btnSaveTrailer.getScene().getWindow();
		loginStage.close();

	}

	private void disableFields(boolean v) {
		btnDateStamp.setDisable(v);
		btnPrint.setDisable(v);
		btnUpdateTrailer.setDisable(v);
		txtFinance.setDisable(v);
		txtNotes.setDisable(v);
		txtOoName.setDisable(v);
		txtOwner.setDisable(v);
		txtUnitNo.setDisable(v);
		txtUsage.setDisable(v);
		ddlCategory.setDisable(v);
		ddlDivision.setDisable(v);
		ddlStatus.setDisable(v);
		ddlTerminal.setDisable(v);
		ddlTrailerType.setDisable(v);

	}

	@FXML
	private void handleEditAction() {
		btnEdit.setDisable(true);
		disableFields(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		txtUnitNo.addEventFilter(KeyEvent.KEY_TYPED, Validate.numeric_Validation(10));
		if (TrailerController.flag == 1) {
			disableFields(true);
		}
		if (TrailerController.flag == 2) {
			btnEdit.setVisible(false);
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

	ObjectMapper mapper = new ObjectMapper();

	List<Category> categoryList = null;

	List<Status> statusList = null;

	List<Division> divisionList = null;

	List<Terminal> terminalList = null;

	List<Type> trailerTypeList = null;

	public void initData(Trailer t) {
		trailerId = t.getTrailerId();
		txtUnitNo.setText(String.valueOf(t.getUnitNo()));
		txtOwner.setText(t.getOwner());
		txtOoName.setText(t.getUsage());
		txtUsage.setText(t.getUsage());
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
		trailerTypeList = t.getTrailerTypeList();
		for (int i = 0; i < t.getTrailerTypeList().size(); i++) {
			Type trailer = t.getTrailerTypeList().get(i);
			ddlTrailerType.getItems().add(trailer.getTypeName());
			if (trailer.getTypeId() == t.getTrailerTypeId()) {
				ddlTrailerType.getSelectionModel().select(i);
			}
		}
	}

	// validation Applying

	@FXML
	Label unitMsg;

	Validate validate = new Validate();

	private boolean validateEditTrailerScreen() {

		boolean result = true;
		String additionalContact = txtUnitNo.getText();

		boolean blnAdditionalContact = validate.validateEmptyness(additionalContact);
		if (!blnAdditionalContact) {
			result = false;
			txtUnitNo.setStyle("-fx-text-box-border: red;");
			unitMsg.setVisible(true);
			unitMsg.setText("Unit No is Mandatory and Must be Numeric");
			unitMsg.setTextFill(Color.RED);
		}

		return result;
	}

	@FXML
	private void trailerUnitKeyPressed() {

		String name = txtUnitNo.getText();
		boolean result = validate.validateEmptyness(name);

		if (!result) {
			txtUnitNo.setStyle("-fx-focus-color: red;");
			txtUnitNo.requestFocus();
			unitMsg.setVisible(true);
			unitMsg.setText("Unit No is Mandatory and Must be Numeric");
			unitMsg.setTextFill(Color.RED);
		} else {
			txtUnitNo.setStyle("-fx-focus-color: skyblue;");
			unitMsg.setVisible(false);
		}
	}

}