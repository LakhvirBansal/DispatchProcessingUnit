package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ShipperEditController extends Application implements Initializable {

	@FXML
	Button btnUpdateShipper, btnEdit, btnDirections, btnHours, btnNotes, btnReports, btnAC;

	@FXML
	TextField txtLocationName, txtContact, txtAddress, txtPosition, txtUnitNo, txtPhone, txtExt, txtCity, txtFax,
			txtPrefix, txtProvince, txtTollFree, txtPlant, txtCellNumber, txtZone, txtEmail, txtLeadTime, txtTimeZone,
			txtImporter;

	@FXML
	TextArea txtInternalNotes, txtStandardNotes;

	@FXML
	ComboBox<String> ddlStatus;

	@FXML
	CheckBox ETAToPickupAlert, ETAToDeliverAlert, RegisteredCSAFacility, RegisteredCTPATFacility,
			WarehouseorCrossDockFacility;
	private Long shipperId = 0l;

	@FXML
	private void btnUpdateShipperAction() {

		boolean result = validateShipperScreen();
		if (result) {
			editShipper();
			closeEditShipperScreen(btnUpdateShipper);
		}
	}
	
	@FXML
	Button btnCancelShipper;
	
	@FXML
	private void btnCancelShipperAction() {
			closeEditShipperScreen(btnCancelShipper);
	}

	private void editShipper() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Shipper shipper = setShipperValue();
					String payload = mapper.writeValueAsString(shipper);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API + "/" + shipperId, null, payload);
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

	private void closeEditShipperScreen(Button btnSaveTrailer) {
		Stage loginStage = (Stage) btnSaveTrailer.getScene().getWindow();
		loginStage.close();

	}

	private void disableFields(boolean v) {
		btnUpdateShipper.setDisable(v);
		btnAC.setDisable(v);
		btnDirections.setDisable(v);
		btnHours.setDisable(v);
		btnNotes.setDisable(v);
		btnReports.setDisable(v);
		txtAddress.setDisable(v);
		txtCellNumber.setDisable(v);
		txtCity.setDisable(v);
		txtContact.setDisable(v);
		txtEmail.setDisable(v);
		txtExt.setDisable(v);
		txtFax.setDisable(v);
		txtImporter.setDisable(v);
		txtInternalNotes.setDisable(v);
		txtLeadTime.setDisable(v);
		txtLocationName.setDisable(v);
		txtPhone.setDisable(v);
		txtPlant.setDisable(v);
		txtPosition.setDisable(v);
		txtPrefix.setDisable(v);
		txtProvince.setDisable(v);
		txtStandardNotes.setDisable(v);
		txtTimeZone.setDisable(v);
		txtTollFree.setDisable(v);
		txtUnitNo.setDisable(v);
		txtZone.setDisable(v);
		ddlStatus.setDisable(v);
		ETAToPickupAlert.setDisable(v);
		ETAToDeliverAlert.setDisable(v);
		RegisteredCSAFacility.setDisable(v);
		RegisteredCTPATFacility.setDisable(v);
		WarehouseorCrossDockFacility.setDisable(v);

	}

	@FXML
	public void handleEditAction() {
		btnEdit.setDisable(true);
		disableFields(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (ShipperController.flag == 1) {
			disableFields(true);
		}
		if (ShipperController.flag == 2) {
			btnEdit.setVisible(false);
		}

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

	List<Company> companyList = null;

	List<Status> statusList = null;

	public void initData(Shipper s) {
		shipperId = s.getShipperId();
		statusList = s.getStatusList();
		for (int i = 0; i < s.getStatusList().size(); i++) {
			Status status = s.getStatusList().get(i);
			ddlStatus.getItems().add(status.getStatus());
			if (status.getId() == s.getStatusId()) {
				ddlStatus.getSelectionModel().select(i);
			}
		}
		/*
		 * companyList = s.getCompanyList(); for(int i = 0; i<
		 * s.getCompanyList().size();i++) { Company company =
		 * s.getCompanyList().get(i);
		 * ddlCompany.getItems().add(company.getName());
		 * if(company.getCompanyId() == s.getCompanyId()) {
		 * ddlCompany.getSelectionModel().select(i); } }
		 */
		txtLocationName.setText(s.getLocationName());
		txtContact.setText(s.getContact());
		txtAddress.setText(s.getAddress());
		txtPosition.setText(s.getPosition());
		txtUnitNo.setText(s.getUnit());
		txtPhone.setText(s.getPhone());
		txtExt.setText(s.getExt());
		txtCity.setText(s.getCity());
		txtFax.setText(s.getFax());
		txtPrefix.setText(s.getPrefix());
		txtProvince.setText(s.getProvinceState());
		txtTollFree.setText(s.getTollFree());
		txtPlant.setText(s.getPlant());
		txtCellNumber.setText(s.getPhone());
		txtZone.setText(s.getZone());
		txtEmail.setText(s.getEmail());
		txtLeadTime.setText(s.getLeadTime());
		txtTimeZone.setText(s.getTimeZone());
		txtImporter.setText(s.getImporter());
		txtInternalNotes.setText(s.getInternalNotes());
		txtStandardNotes.setText(s.getStandardNotes());
	}

	// validation Applying

	@FXML
	Label locationMsg, statusMsg;

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