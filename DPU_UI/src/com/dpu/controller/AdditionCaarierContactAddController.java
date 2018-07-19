package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.AddtionalCarrierContact;
import com.dpu.model.Company;
import com.dpu.model.Status;
import com.dpu.model.Type;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdditionCaarierContactAddController implements Initializable {

	@FXML
	private Button btnCancelAdditionalContact;

	@FXML
	private Button btnSaveAdditionalContact;

	@FXML
	private CheckBox chkActualDelivery;

	@FXML
	private CheckBox chkActualPickupDetails;

	@FXML
	private CheckBox chkETADeliveryDetails;

	@FXML
	private CheckBox chkETAPickupDetails;

	@FXML
	private CheckBox chkOrderConfirmation;

	@FXML
	private ComboBox<String> ddlStatus,ddlFunction;

	@FXML
	private TextField txtAdditionalContact;

	@FXML
	private TextField txtCellular;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtExtension;

	@FXML
	private TextField txtFax;

	@FXML
	private TextField txtPager;

	@FXML
	private TextField txtPhone;

	@FXML
	private TextField txtPosition;

	// @FXML
	// void initialize() {
	// assert btnCancelAdditionalContact != null :
	// "fx:id=\"btnCancelAdditionalContact\" was not injected: check your FXML
	// file 'AdditionalContactAddScreen.fxml'.";
	// assert btnSaveAdditionalContact != null :
	// "fx:id=\"btnSaveAdditionalContact\" was not injected: check your FXML
	// file 'AdditionalContactAddScreen.fxml'.";
	// assert ddlStatus != null : "fx:id=\"ddlStatus\" was not injected: check
	// your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtAdditionalContact != null : "fx:id=\"txtAdditionalContact\" was
	// not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtCellular != null : "fx:id=\"txtCellular\" was not injected:
	// check your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check
	// your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtExtension != null : "fx:id=\"txtExtension\" was not injected:
	// check your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtFax != null : "fx:id=\"txtFax\" was not injected: check your
	// FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtPager != null : "fx:id=\"txtPager\" was not injected: check
	// your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtPhone != null : "fx:id=\"txtPhone\" was not injected: check
	// your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtPosition != null : "fx:id=\"txtPosition\" was not injected:
	// check your FXML file 'AdditionalContactAddScreen.fxml'.";
	//
	// }

	@FXML
	void btnCancelAdditionalContactAction(ActionEvent event) {
		openAddCarrierScree();
		closeAddAdditionalContactScreen(btnCancelAdditionalContact);
	}

	@FXML
	void btnSaveAdditionalContactAction(ActionEvent event) {
		try {

			String additionalContact = txtAdditionalContact.getText();
			String position = txtPosition.getText();
			String phone = txtPhone.getText();
			String extension = txtExtension.getText();
			String fax = txtFax.getText();
			String pager = txtPager.getText();
			String cellular = txtCellular.getText();
			String status = ddlStatus.getSelectionModel().getSelectedItem();
			String email = txtEmail.getText();
			String function = ddlFunction.getSelectionModel().getSelectedItem();

			AddtionalCarrierContact addtionalCarrierContact = new AddtionalCarrierContact(additionalContact, position,
					phone, extension, fax, pager, cellular, status, email,function);
			if (CarrierAddController.addAddtionalContact == 0) {
				CarrierAddController.listOfAdditionalContact.set(CarrierAddController.addEditIndex,
						addtionalCarrierContact);
			} else if (CarrierAddController.addAddtionalContact == 1) {
				CarrierAddController.listOfAdditionalContact.add(addtionalCarrierContact);
			}
			// CarrierAddController.listOfAdditionalContact.add(addtionalCarrierContact);
			openAddCarrierScree();

		} catch (Exception e) {
			e.printStackTrace();
		}

		closeAddAdditionalContactScreen(btnSaveAdditionalContact);
	}

	private void openAddCarrierScree() {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.CARRIER_BASE_PACKAGE + Iconstants.XML_CARRIER_ADD_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add New Carrier");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void closeAddAdditionalContactScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}
	private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(
							Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/openAddAdditionalContact", null);
					Company company = mapper.readValue(response, Company.class);

					List<Status> statusList = company.getStatusList();
					fillDropDown(ddlStatus, statusList);

					List<Type> functionList = company.getFunctionList();
					fillDropDown(ddlFunction, functionList);
					System.out.println(statusList.size() + "  " + functionList.size());

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {

		for (int i = 0; i < list.size(); i++) {

			Object object = list.get(i);

			if (object != null && object instanceof Type) {
				Type type = (Type) object;
				comboBox.getItems().add(type.getTypeName());
			}

			if (object != null && object instanceof Status) {
				Status status = (Status) object;
				comboBox.getItems().add(status.getStatus());
			}

		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchMasterDataForDropDowns();
		if (CarrierAddController.addAddtionalContact != 1) {
			if (CarrierAddController.additionalContactModel != null) {
				txtAdditionalContact.setText(CarrierAddController.additionalContactModel.getCustomerName());
				txtPosition.setText(CarrierAddController.additionalContactModel.getPosition());
				txtExtension.setText(CarrierAddController.additionalContactModel.getExt());
				txtFax.setText(CarrierAddController.additionalContactModel.getFax());
				txtCellular.setText(CarrierAddController.additionalContactModel.getCellular());
				txtEmail.setText(CarrierAddController.additionalContactModel.getEmail());
				txtPager.setText(CarrierAddController.additionalContactModel.getPrefix());
				txtPhone.setText(CarrierAddController.additionalContactModel.getPhone());
			}

		}
	}

}
