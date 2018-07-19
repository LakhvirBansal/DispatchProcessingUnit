package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.BillingControllerModel;
import com.dpu.model.Country;
import com.dpu.util.Validate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BillingAddController implements Initializable {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnCancelBillingLocation, btnSaveBillingLocation;

	@FXML
	private TextField txtAddress, txtAddress1, txtCity, txtProvince, txtCompany, txtContact, txtFax, txtPhone, txtZip;

	@FXML
	private ComboBox<String> ddlCountry;
	
	@FXML
	public void txtProvinceKeyPressed() {
		
	}
	
	@FXML
	void btnSaveBillingLocationAction(ActionEvent event) {

		boolean result = validateAddCompanyScreen();
		if (result) {
			try {

				String company = txtCompany.getText();
				String address = txtAddress.getText();
				String address2 = txtAddress1.getText();
				String city = txtCity.getText();
				String province = txtProvince.getText();
				String phone = txtPhone.getText();
				String contact = txtContact.getText();
				String zip = txtZip.getText();
				String fax = txtFax.getText();
				Long countryId = countryList.get(ddlCountry.getSelectionModel().getSelectedIndex()).getCountryId();
				// Long statusId = Long.parseLong("1");
				BillingControllerModel bcm1 = new BillingControllerModel(company, address, city, phone, contact, zip,
						fax, address2, province, countryId);

				if (CompanyAddController.add == 0) {
					CompanyEditController.listOfBilling.set(CompanyAddController.addEditIndex, bcm1);
				} else if (CompanyAddController.add == 1) {
					CompanyEditController.listOfBilling.add(bcm1);
				}

			} catch (Exception e) {
			}
			CompanyAddController.fetchBillingLocationsUsingDuplicate();
			closeAddBillingScreen(btnSaveBillingLocation);
		}

	}

	private void closeAddBillingScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@FXML
	void btnCancelBillingLocationAction(ActionEvent event) {

		closeAddBillingScreen(btnCancelBillingLocation);
	}

	List<Country> countryList = null;
	
	private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {
			ObjectMapper mapper = new ObjectMapper();

			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_GET_COUNTRIES + "/openAdd", null);
					Country country = mapper.readValue(response, Country.class);

//					countryList = country.getCountryList();
					if (countryList != null)
						fillDropDown(ddlCountry, countryList);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for(int i=0;i<list.size();i++) {
			Object object = list.get(i);
			if(object != null && object instanceof Country) {
				Country country = (Country) object;
				comboBox.getItems().add(country.getCountryName());
			}
		}
	}

	@FXML
	Label lblCompany, lblAddress, lblCity, lblZip, lblFax, lblPhone, lblContact;

	Validate validate = new Validate();

	@FXML
	private void companyNameKeyPressed() {

		String name = txtCompany.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCompany.setTextFill(Color.BLACK);
			txtCompany.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtCompany.setStyle("-fx-focus-color: red;");
			txtCompany.requestFocus();
			lblCompany.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblCompany.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyAdderssKeyPressed() {

		String name = txtAddress.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblAddress.setTextFill(Color.BLACK);
			txtAddress.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtAddress.setStyle("-fx-focus-color: red;");
			txtAddress.requestFocus();
			lblAddress.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblAddress.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyCityKeyPressed() {

		String name = txtCity.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCity.setTextFill(Color.BLACK);
			txtCity.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtCity.setStyle("-fx-focus-color: red;");
			txtCity.requestFocus();
			lblCity.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblCity.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyZipKeyPressed() {

		String name = txtZip.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblZip.setTextFill(Color.BLACK);
			txtZip.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtZip.setStyle("-fx-focus-color: red;");
			txtZip.requestFocus();
			lblZip.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblZip.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyFaxKeyPressed() {

		String name = txtFax.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblFax.setTextFill(Color.BLACK);
			txtFax.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtFax.setStyle("-fx-focus-color: red;");
			txtFax.requestFocus();
			lblFax.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblFax.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyPhoneKeyPressed() {

		String name = txtPhone.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblPhone.setTextFill(Color.BLACK);
			txtPhone.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtPhone.setStyle("-fx-focus-color: red;");
			txtPhone.requestFocus();
			lblPhone.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblPhone.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyContactKeyPressed() {

		String name = txtContact.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblContact.setTextFill(Color.BLACK);
			txtContact.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtContact.setStyle("-fx-focus-color: red;");
			txtContact.requestFocus();
			lblContact.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblContact.setTextFill(Color.RED);
		}
	}

	private boolean validateAddCompanyScreen() {

		boolean result = true;
		String customerName = txtCompany.getText();
		String address = txtAddress.getText();
		String phone = txtPhone.getText();
		String fax = txtFax.getText();
		String city = txtCity.getText();
		String zip = txtZip.getText();
		String contact = txtContact.getText();

		boolean blnName = validate.validateEmptyness(customerName);
		if (!blnName) {
			result = false;
			txtCompany.setStyle("-fx-text-box-border: red;");
			lblCompany.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblCompany.setTextFill(Color.RED);

		}

		boolean blnAddress = validate.validateEmptyness(address);
		if (!blnAddress) {
			result = false;
			txtAddress.setStyle("-fx-text-box-border: red;");
			lblAddress.setVisible(true);
			// lblAddress.setText("Address is Mandatory");
			lblAddress.setTextFill(Color.RED);
		}

		boolean blnPhone = validate.validateEmptyness(phone);
		if (!blnPhone) {
			result = false;
			txtPhone.setStyle("-fx-text-box-border: red;");
			lblPhone.setVisible(true);
			// lblPhone.setText("Phone Number is Mandatory");
			lblPhone.setTextFill(Color.RED);
		}

		boolean blnFax = validate.validateEmptyness(fax);
		if (!blnFax) {
			result = false;
			txtFax.setStyle("-fx-text-box-border: red;");
			lblFax.setVisible(true);
			// lblFax.setText("Fax Number is Mandatory");
			lblFax.setTextFill(Color.RED);
		}

		boolean blnCity = validate.validateEmptyness(city);
		if (!blnCity) {
			result = false;
			txtCity.setStyle("-fx-text-box-border: red;");
			lblCity.setVisible(true);
			// lblFax.setText("Fax Number is Mandatory");
			lblCity.setTextFill(Color.RED);
		}

		boolean blnZip = validate.validateEmptyness(zip);
		if (!blnZip) {
			result = false;
			txtZip.setStyle("-fx-text-box-border: red;");
			lblZip.setVisible(true);
			// lblFax.setText("Fax Number is Mandatory");
			lblZip.setTextFill(Color.RED);
		}

		boolean blnContact = validate.validateEmptyness(contact);
		if (!blnContact) {
			result = false;
			txtContact.setStyle("-fx-text-box-border: red;");
			lblContact.setVisible(true);
			// lblFax.setText("Fax Number is Mandatory");
			lblContact.setTextFill(Color.RED);
		}

		return result;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtPhone.addEventFilter(KeyEvent.KEY_TYPED, Validate.numeric_Validation(10));
		//txtCompany.addEventFilter(KeyEvent.KEY_TYPED, Validate.letter_Validation(10));

		if (CompanyAddController.add != 1) {
			if (CompanyAddController.billingControllerModel != null) {
				txtCompany.setText(CompanyAddController.billingControllerModel.getName());
				txtPhone.setText(CompanyAddController.billingControllerModel.getPhone());
				txtAddress.setText(CompanyAddController.billingControllerModel.getAddress());
				txtCity.setText(CompanyAddController.billingControllerModel.getCity());
				txtContact.setText(CompanyAddController.billingControllerModel.getContact());
				txtZip.setText(CompanyAddController.billingControllerModel.getZip());
				txtFax.setText(CompanyAddController.billingControllerModel.getFax());
			}
		}
		fetchMasterDataForDropDowns();
	}

}