package com.dpu.controller.vendor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.CompanyEditController;
import com.dpu.model.Status;
import com.dpu.model.Type;
import com.dpu.model.VendorAdditionalContacts;

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

public class VendorAdditionalContactEditController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnCancelAdditionalContact;

	@FXML
	private Button btnUpdateAdditionalContact;

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
	private ComboBox<String> ddlStatus;

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
	
	List<Status> statusList;

	/*private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient
							.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/openAdd", null);
					AdditionalContact driver = mapper.readValue(response, AdditionalContact.class);

					statusList = driver.getStatusList();
					fillDropDown(ddlStatus, statusList);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}*/
	
	@FXML
	void btnCancelAdditionalContactAction(ActionEvent event) {
//		try {
//			CompanyEditController.selectedTabValue =1;
//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
//					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
//			Parent root = (Parent) fxmlLoader.load();
//
//			Stage stage = new Stage();
//			stage.initModality(Modality.APPLICATION_MODAL);
//			stage.setTitle("Add New Company");
//			stage.setScene(new Scene(root));
//			stage.show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		CompanyEditController.selectedTabValue =0;
		closeAddAdditionalContactScreen(btnCancelAdditionalContact);
	}

	@FXML
	void btnUpdateAdditionalContactAction(ActionEvent event) {
		try {
			
			String additionalContact = txtAdditionalContact.getText();
			String position = txtPosition.getText();
			String phone = txtPhone.getText();
			String extension = txtExtension.getText();
			String fax = txtFax.getText();
			String pager = txtPager.getText();
			String cellular = txtCellular.getText();
			Long statusId =  statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId();
			String status = statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getStatus();

			String email = txtEmail.getText();
  			VendorAdditionalContacts bcm1 = new VendorAdditionalContacts(additionalContact, position, phone, fax, cellular, email, extension, pager, statusId);
			
  			bcm1.setStatusName(status);
  			VendorEditController.selectedTabValue =1;
			if (VendorEditController.addAddtionalContact == 0) {
  				VendorAddController.listOfAdditionalContact.set(VendorEditController.editIndex, bcm1);
			} else if(VendorEditController.addAddtionalContact == 1){
				VendorAddController.listOfAdditionalContact.add(bcm1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(VendorAddController.whichScreenAddOrEdit == 0) {
			VendorAddController.fetchAdditionalContactsUsingDuplicate();
		} else {
			VendorEditController.fetchAdditionalContactsUsingDuplicate();
		}
		closeAddAdditionalContactScreen(btnUpdateAdditionalContact);
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
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API + "/openAdd", null);
					com.dpu.request.AdditionalContact driver = mapper.readValue(response, com.dpu.request.AdditionalContact.class);

					statusList = driver.getStatusList();
//					fillDropDown(ddlStatus, statusList);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		if (VendorAddController.addAddtionalContact != 1) {
			if (VendorEditController.additionalContactModel != null) {
				txtAdditionalContact.setText(VendorEditController.additionalContactModel.getVendorName());
				txtPosition.setText(VendorEditController.additionalContactModel.getPosition());
				txtExtension.setText(VendorEditController.additionalContactModel.getExt());
				txtFax.setText(VendorEditController.additionalContactModel.getFax());
				txtCellular.setText(VendorEditController.additionalContactModel.getCellular());
				txtEmail.setText(VendorEditController.additionalContactModel.getEmail());
				txtPager.setText(VendorEditController.additionalContactModel.getPrefix());
				txtPhone.setText(VendorEditController.additionalContactModel.getPhone());
				if(VendorAdditionalContactAddController.statusList != null && VendorAdditionalContactAddController.statusList.size() > 0) {
					statusList = VendorAdditionalContactAddController.statusList;
				} else {
					fetchMasterDataForDropDowns();
				}
				
				ddlStatus.getItems().clear();
				for(int i=0; i < statusList.size(); i++) {
					Status status = statusList.get(i);
					ddlStatus.getItems().add(status.getStatus());
					if(status.getId().equals(VendorEditController.additionalContactModel.getStatusId())) {
						ddlStatus.getSelectionModel().select(i);
					}
				}
//			}
		}
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

}








