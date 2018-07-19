package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Company;
import com.dpu.model.Failed;
import com.dpu.model.Shipper;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ShipperAddController extends Application implements Initializable {

	@FXML
	Button btnSaveShipper;
	
	@FXML
	Button btnCancelShipper;

	@FXML
	TextField txtLocationName, txtContact, txtAddress, txtPosition, txtUnitNo, txtPhone, txtExt, txtCity, txtFax,
			txtPrefix, txtProvince, txtTollFree, txtPlant, txtCellNumber, txtZone, txtEmail, txtLeadTime, txtTimeZone,
			txtImporter;

	@FXML
	TextArea txtInternalNotes, txtStandardNotes;

	@FXML
	ComboBox<String> ddlStatus;

	@FXML
	private void btnSaveShipperAction() {

		boolean result = validateShipperScreen();
		if (result) {
			addShipper();
			closeAddShipperScreen(btnSaveShipper);
		}
	}

	@FXML
	private void btnCancelShipperAction() {
			closeAddShipperScreen(btnCancelShipper);
	}
	
	private void closeAddShipperScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void addShipper() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Shipper shipper = setShipperValue();
					String payload = mapper.writeValueAsString(shipper);
					System.out.println(payload);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API,
							null, payload);
					if (MainScreen.shipperController != null) {
						try {
							Success success = mapper.readValue(response, Success.class);
							List<Shipper> shipperList = (List<Shipper>) success.getResultList();
							String res = mapper.writeValueAsString(shipperList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							MainScreen.shipperController.fillShippers(res);
						} catch (Exception e) {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage());
						}
					} else if (MainScreen.terminalController != null) {
						MainScreen.terminalController.openAddTerminalScreen();
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
		fetchMasterDataForDropDowns();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	ObjectMapper mapper = new ObjectMapper();

	List<Company> companyList = null;

	List<Status> statusList = null;

	private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					String response = GetAPIClient
							.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API + "/openAdd", null);
					Shipper shipper = mapper.readValue(response, Shipper.class);
					/*
					 * companyList = shipper.getCompanyList();
					 * fillDropDown(ddlCompany, companyList);
					 */
					statusList = shipper.getStatusList();
					fillDropDown(ddlStatus, statusList);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			if (object != null && object instanceof Status) {
				Status status = (Status) object;
				comboBox.getItems().add(status.getStatus());
			}
			/*
			 * if(object != null && object instanceof Company) { Company company
			 * = (Company) object; comboBox.getItems().add(company.getName()); }
			 */
		}
	}

	private Shipper setShipperValue() {
		Shipper shipper = new Shipper();
		shipper.setLocationName(txtLocationName.getText());
		shipper.setContact(txtContact.getText());
		shipper.setAddress(txtAddress.getText());
		shipper.setPosition(txtPosition.getText());
		shipper.setUnit(txtUnitNo.getText());
		shipper.setPhone(txtPhone.getText());
		shipper.setExt(txtExt.getText());
		shipper.setCity(txtCity.getText());
		shipper.setFax(txtFax.getText());
		shipper.setPrefix(txtPrefix.getText());
		shipper.setProvinceState(txtProvince.getText());
		shipper.setTollFree(txtTollFree.getText());
		shipper.setPlant(txtPlant.getText());
		shipper.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		// cellnumber yet to be done
		shipper.setZone(txtZone.getText());
		shipper.setEmail(txtEmail.getText());
		shipper.setLeadTime(txtLeadTime.getText());
		shipper.setTimeZone(txtTimeZone.getText());
		shipper.setImporter(txtImporter.getText());
		shipper.setInternalNotes(txtInternalNotes.getText());
		shipper.setStandardNotes(txtStandardNotes.getText());
		return shipper;
	}

	// validation Applying

			@FXML
			Label locationMsg,statusMsg;

			
			Validate validate = new Validate();

			private boolean validateShipperScreen() {

				boolean result = true;
				String additionalContact = txtLocationName.getText();

				boolean blnAdditionalContact = validate.validateEmptyness(additionalContact);
				if (!blnAdditionalContact) {
					result = false;
					txtLocationName.setStyle("-fx-text-box-border: red;");
					locationMsg.setVisible(true);
					locationMsg.setText("Location is Mandatory");
					locationMsg.setTextFill(Color.RED);
				}

				String status = ddlStatus.getSelectionModel().getSelectedItem();
				boolean blnStatus = validate.validateEmptyness(status);
				if (!blnStatus) {
					result = false;
					ddlStatus.setStyle("-fx-text-box-border: red;");
					statusMsg.setVisible(true);
					statusMsg.setText("Status is Mandatory");
					statusMsg.setTextFill(Color.RED);
				}
				return result;
			}

			@FXML
			private void shipperLocationKeyPressed() {

				String name = txtLocationName.getText();
				boolean result = validate.validateEmptyness(name);

				if (!result) {
					txtLocationName.setStyle("-fx-focus-color: red;");
					txtLocationName.requestFocus();
					locationMsg.setVisible(true);
					locationMsg.setText("Location is Mandatory");
					locationMsg.setTextFill(Color.RED);
				} else {
					txtLocationName.setStyle("-fx-focus-color: skyblue;");
					locationMsg.setVisible(false);
				}
			}
			
			@FXML
			private void ddlStatusAction() {
				
				String status = ddlStatus.getSelectionModel().getSelectedItem();
				boolean result = validate.validateEmptyness(status);
				if (result) {
					ddlStatus.setStyle("-fx-focus-color: skyblue;");
					statusMsg.setVisible(false);
				} else {
	 
					ddlStatus.setStyle("-fx-border-color: red;");
					statusMsg.setVisible(true);
					statusMsg.setText("Status is Mandatory");
					statusMsg.setTextFill(Color.RED);
				}
			}
}