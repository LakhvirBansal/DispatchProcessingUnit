/**
 * 
 */
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
import com.dpu.model.Division;
import com.dpu.model.Failed;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author jagvir
 *
 */
public class DivisionAddController extends Application implements Initializable {

	@FXML
	Button btnSaveDivision, btnCancel;

	@FXML
	TextField txtDivisionName, txtDivisionCode, txtFedral, txtProvincial, txtSCAC, txtCarrierCode, txtContractPrefix,
			txtInvoicePrefix;
	@FXML
	CheckBox chkIncludeInManagementReporting, chkIncludeInAccountingTransfers;
	Validate validate = new Validate();
	@FXML
	ComboBox<String> ddlStatus;
	@FXML
	Label lblDivisionCode, lblDivisionName, lblStatus, lblFedral, lblProvincial, lblSCAC, lblCarierCode,
			lblContactPrefix, lblInvoicePrefix;

	@FXML
	private void btnSaveDivisionAction() {
		boolean res = validateAddCategoryScreen();
		if (res) {
			addDivision();
			closeAddDivisionScreen(btnSaveDivision);
		}
	}
	
	@FXML
	private void btnCancelAction() {
		closeAddDivisionScreen(btnCancel);
	}

	List<Status> cList = null;

	private boolean validateAddCategoryScreen() {
		boolean response = true;
		String divisionCode = txtDivisionCode.getText();
		String divisionName = txtDivisionName.getText();
		String fedral = txtFedral.getText();
		String provincial = txtProvincial.getText();
		String scac = txtSCAC.getText();
		String carrierCode = txtCarrierCode.getText();
		String contractPrefix = txtContractPrefix.getText();
		String invoicePrefix = txtInvoicePrefix.getText();
		String status = ddlStatus.getSelectionModel().getSelectedItem();

		boolean result = validate.validateEmptyness(divisionCode);

		if (!result) {
			response = false;
			txtDivisionCode.setStyle("-fx-focus-color: red;");
			lblDivisionCode.setVisible(true);
			lblDivisionCode.setText("Category Name is Mandatory");
			lblDivisionCode.setTextFill(Color.RED);
			// return result;
		} else if (!validate.validateLength(divisionCode, 5, 25)) {
			response = false;
			txtDivisionCode.setStyle("-fx-focus-color: red;");
			lblDivisionCode.setVisible(true);
			lblDivisionCode.setText("Min. length 5 and Max. length 25");
			lblDivisionCode.setTextFill(Color.RED);
			return result;

		}
		result = validate.validateEmptyness(divisionName);
		if (!result) {
			response = false;
			txtDivisionName.setStyle("-fx-focus-color: red;");
			lblDivisionName.setVisible(true);
			lblDivisionName.setText("Division Name is Mandatory");
			lblDivisionName.setTextFill(Color.RED);
		} else if (!validate.validateLength(divisionName, 5, 25)) {
			response = false;
			txtDivisionName.setStyle("-fx-focus-color: red;");
			lblDivisionName.setVisible(true);
			lblDivisionName.setText("Min. length 5 and Max. length 25");
			lblDivisionName.setTextFill(Color.RED);
			return result;
		}
		result = validate.validateEmptyness(fedral);
		if (!result) {
			response = false;
			txtFedral.setStyle("-fx-focus-color: red;");
			lblFedral.setVisible(true);
			lblFedral.setText("Fedral Name is Mandatory");
			lblFedral.setTextFill(Color.RED);
		} else if (!validate.validateLength(fedral, 5, 25)) {
			response = false;
			txtFedral.setStyle("-fx-focus-color: red;");
			lblFedral.setVisible(true);
			lblFedral.setText("Min. length 5 and Max. length 25");
			lblFedral.setTextFill(Color.RED);
			return result;
		}
		result = validate.validateEmptyness(provincial);
		if (!result) {
			response = false;
			txtProvincial.setStyle("-fx-focus-color: red;");
			lblProvincial.setVisible(true);
			lblProvincial.setText("Provincial Name is Mandatory");
			lblProvincial.setTextFill(Color.RED);
		} else if (!validate.validateLength(provincial, 5, 25)) {
			response = false;
			txtProvincial.setStyle("-fx-focus-color: red;");
			lblProvincial.setVisible(true);
			lblProvincial.setText("Min. length 5 and Max. length 25");
			lblProvincial.setTextFill(Color.RED);
			return result;
		}
		result = validate.validateEmptyness(scac);
		if (!result) {
			response = false;
			txtSCAC.setStyle("-fx-focus-color: red;");
			lblSCAC.setVisible(true);
			lblSCAC.setText("SCAC Name is Mandatory");
			lblSCAC.setTextFill(Color.RED);
		} else if (!validate.validateLength(scac, 5, 25)) {
			response = false;
			txtSCAC.setStyle("-fx-focus-color: red;");
			lblSCAC.setVisible(true);
			lblSCAC.setText("Min. length 5 and Max. length 25");
			lblSCAC.setTextFill(Color.RED);
			return result;
		}
		result = validate.validateEmptyness(carrierCode);
		if (!result) {
			response = false;
			txtCarrierCode.setStyle("-fx-focus-color: red;");
			lblCarierCode.setVisible(true);
			lblCarierCode.setText("Carrier Code is Mandatory");
			lblCarierCode.setTextFill(Color.RED);
		} else if (!validate.validateLength(carrierCode, 5, 25)) {
			response = false;
			txtCarrierCode.setStyle("-fx-focus-color: red;");
			lblCarierCode.setVisible(true);
			lblCarierCode.setText("Min. length 5 and Max. length 25");
			lblCarierCode.setTextFill(Color.RED);
			return result;
		}
		result = validate.validateEmptyness(contractPrefix);
		if (!result) {
			response = false;
			txtContractPrefix.setStyle("-fx-focus-color: red;");
			lblContactPrefix.setVisible(true);
			lblContactPrefix.setText("Contract Prefix is Mandatory");
			lblContactPrefix.setTextFill(Color.RED);
		} else if (!validate.validateLength(contractPrefix, 5, 25)) {
			response = false;
			txtContractPrefix.setStyle("-fx-focus-color: red;");
			lblContactPrefix.setVisible(true);
			lblContactPrefix.setText("Min. length 5 and Max. length 25");
			lblContactPrefix.setTextFill(Color.RED);
			return result;
		}
		result = validate.validateEmptyness(invoicePrefix);
		if (!result) {
			response = false;
			txtInvoicePrefix.setStyle("-fx-focus-color: red;");
			lblInvoicePrefix.setVisible(true);
			lblInvoicePrefix.setText("Invoice Prefix is Mandatory");
			lblInvoicePrefix.setTextFill(Color.RED);
		} else if (!validate.validateLength(invoicePrefix, 5, 25)) {
			response = false;
			txtInvoicePrefix.setStyle("-fx-focus-color: red;");
			lblInvoicePrefix.setVisible(true);
			lblInvoicePrefix.setText("Min. length 5 and Max. length 25");
			lblInvoicePrefix.setTextFill(Color.RED);
			return result;
		}
		result = validate.validateEmptyness(status);
		if (!result) {
			response = false;
			ddlStatus.setStyle("-fx-focus-color: red;");
			lblStatus.setVisible(true);
			lblInvoicePrefix.setText("Status is Mandatory");
			lblInvoicePrefix.setTextFill(Color.RED);
		}
		return response;
	}

	public void fetchStatus() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_STATUS_API, null);
					if (response != null && response.length() > 0) {
						Status c[] = mapper.readValue(response, Status[].class);
						cList = new ArrayList<Status>();
						for (Status ccl : c) {
							ddlStatus.getItems().add(ccl.getStatus());
							cList.add(ccl);
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}
		});
	}

	private void closeAddDivisionScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void addDivision() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Division division = setDivisionValue();
					String payload = mapper.writeValueAsString(division);
					System.out.println(payload + " pykiad");
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_DIVISION_API,
							null, payload);
					System.out.println(response);
					// MainScreen.divisionController.fillDivisions(response);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Division> divisionList = (List<Division>) success.getResultList();
						String res = mapper.writeValueAsString(divisionList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.divisionController.fillDivisions(res);
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
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	private Division setDivisionValue() {
		Division division = new Division();
		division.setDivisionCode(txtDivisionCode.getText());
		division.setDivisionName(txtDivisionName.getText());
		division.setStatusId(cList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		division.setFedral(txtFedral.getText());
		division.setProvincial(txtProvincial.getText());
		division.setScac(txtSCAC.getText());
		division.setCarrierCode(txtCarrierCode.getText());
		division.setContractPrefix(txtContractPrefix.getText());
		division.setInvoicePrefix(txtInvoicePrefix.getText());
		return division;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchStatus();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}
}