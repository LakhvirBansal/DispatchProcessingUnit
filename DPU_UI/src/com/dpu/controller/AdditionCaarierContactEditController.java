/**
 * 
 */
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

/**
 * @author jagvir
 *
 */
public class AdditionCaarierContactEditController implements Initializable {

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
	private ComboBox<String> ddlStatus, ddlFunction;

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

	private void openAddCarrierScree() {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.CARRIER_BASE_PACKAGE + Iconstants.XML_CARRIER_Edit_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Edit Outside Carrier");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
					phone, extension, fax, pager, cellular, status, email, function);

			if (CarrierEditController.additionalContactIdPri != 0l)
				addtionalCarrierContact.setAdditionalContactId(CarrierEditController.additionalContactIdPri);

			if (CarrierEditController.addAddtionalContact == 0) {
				CarrierEditController.listOfAdditionalContact.set(CarrierEditController.addEditIndex,
						addtionalCarrierContact);
			} else if (CarrierEditController.addAddtionalContact == 1) {
				CarrierEditController.listOfAdditionalContact.add(addtionalCarrierContact);
			}
			// CarrierAddController.listOfAdditionalContact.add(addtionalCarrierContact);

			CarrierEditController.fetchAdditionalContactsUsingDuplicate();
			closeEditAdditionalContactScreen(btnSaveAdditionalContact);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void btnCancelAdditionalContactAction(ActionEvent event) {
		openAddCarrierScree();
		closeEditAdditionalContactScreen(btnCancelAdditionalContact);
	}

	private void closeEditAdditionalContactScreen(Button clickedButton) {
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
	public void initialize(URL arg0, ResourceBundle arg1) {
		fetchMasterDataForDropDowns();
		System.out.println("size::" + CarrierEditController.listOfAdditionalContact.size());
		// TODO Auto-generated method stub
		if (CarrierEditController.addAddtionalContact != 1) {
			if (CarrierEditController.additionalContactModel != null) {
				txtAdditionalContact.setText(CarrierEditController.additionalContactModel.getCustomerName());
				txtPosition.setText(CarrierEditController.additionalContactModel.getPosition());
				txtExtension.setText(CarrierEditController.additionalContactModel.getExt());
				txtFax.setText(CarrierEditController.additionalContactModel.getFax());
				txtCellular.setText(CarrierEditController.additionalContactModel.getCellular());
				txtEmail.setText(CarrierEditController.additionalContactModel.getEmail());
				txtPager.setText(CarrierEditController.additionalContactModel.getPrefix());
				txtPhone.setText(CarrierEditController.additionalContactModel.getPhone());
			}
		}

	}

}
