package com.dpu.controller.vendor;

import java.net.URL;
import java.util.ResourceBundle;

import com.dpu.model.VendorBillingLocation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VendorBillingEditController implements Initializable {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnCancelBillingLocation;

	@FXML
	private Button btnUpdateBillingLocation;

	@FXML
	private TextField txtAddress;

	@FXML
	private TextField txtCity;

	@FXML
	private TextField txtCompany;

	@FXML
	private TextField txtContact;

	@FXML
	private TextField txtFax;

	@FXML
	private TextField txtPhone;

	@FXML
	private TextField txtZip;

	@FXML
	void btnUpdateBillingLocationAction(ActionEvent event) {
		try {

			String company = txtCompany.getText();
			String address = txtAddress.getText();
			String city = txtCity.getText();
			String phone = txtPhone.getText();
			String contact = txtContact.getText();
			String zip = txtZip.getText();
			String fax = txtFax.getText();
			Long statusId =Long.parseLong("1");
			VendorBillingLocation bcm1 = new VendorBillingLocation(company, address, city, phone, contact, zip, fax,statusId);

			if (VendorEditController.addBillingLocation == 0 ) {
				if (VendorEditController.billingLocationIdPri != 0l)
					bcm1.setVendorBillingLocationId(VendorEditController.billingLocationIdPri);
				
				VendorAddController.listOfBilling.set(VendorEditController.editIndex, bcm1);
				VendorEditController.billingLocationIdPri = 0l;

			} else if (VendorEditController.addBillingLocation == 1) {
				VendorEditController.listOfBilling.add(bcm1);
				
			}
			closeAddBillingScreen(btnUpdateBillingLocation);
			if(VendorAddController.whichScreenAddOrEdit == 0) {
				VendorAddController.fetchBillingLocationsUsingDuplicate();
			} else {
				VendorEditController.fetchBillingLocationsUsingDuplicate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		closeAddBillingScreen(btnUpdateBillingLocation);

	}

	private void closeAddBillingScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@FXML
	void btnCancelBillingLocationAction(ActionEvent event) {
//		try {
//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
//					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
//			Parent root = (Parent) fxmlLoader.load();
//
//			Stage stage = new Stage();
//			stage.initModality(Modality.APPLICATION_MODAL);
//			stage.setTitle("Update Company");
//			stage.setScene(new Scene(root));
//			stage.show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		closeAddBillingScreen(btnCancelBillingLocation);
	}

	/*@FXML
	void initialize() {
		assert btnUpdateBillingLocation != null : "fx:id=\"btnSaveBillingLocation\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtCity != null : "fx:id=\"txtCity\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtCompany != null : "fx:id=\"txtCompany\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtContact != null : "fx:id=\"txtContact\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtFax != null : "fx:id=\"txtFax\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtPhone != null : "fx:id=\"txtPhone\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtZip != null : "fx:id=\"txtZip\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";

	}*/

	public void initialize(URL location, ResourceBundle resources) {

//		if (VendorEditController.addBillingLocation != 1) {
			if (VendorAddController.VendorBillingLocation != null) {
				txtCompany.setText(VendorAddController.VendorBillingLocation.getName());
				txtAddress.setText(VendorAddController.VendorBillingLocation.getAddress());
				txtCity.setText(VendorAddController.VendorBillingLocation.getCity());
				txtPhone.setText(VendorAddController.VendorBillingLocation.getPhone());
				txtContact.setText(VendorAddController.VendorBillingLocation.getPhone());
				txtZip.setText(VendorAddController.VendorBillingLocation.getZip());
				txtFax.setText(VendorAddController.VendorBillingLocation.getFax());
			}
//		}

	}
}