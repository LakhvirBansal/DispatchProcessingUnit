package com.dpu.controller.vendor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.AdditionalContact;
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

public class VendorAdditionalContactAddController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

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

	@FXML
	void btnCancelAdditionalContactAction(ActionEvent event) {
		/*try {
			CompanyAddController.selectedTabValue = 1;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_ADD_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add New Company");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CompanyAddController.selectedTabValue = 0;*/
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

			Long statusId = statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId();
			String status = statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getStatus();
			String email = txtEmail.getText();
			VendorAdditionalContacts bcm1 = new VendorAdditionalContacts(additionalContact, position, phone, fax, cellular, email, extension, pager, statusId);
			bcm1.setStatusName(status);
			VendorAddController.selectedTabValue = 1;
//			if (VendorAddController.addEditIndex != -1) {
				if (VendorAddController.addAddtionalContact == 0) {
					VendorAddController.listOfAdditionalContact.set(VendorAddController.addEditIndex, bcm1);
				} else if (VendorAddController.addAddtionalContact == 1) {
					VendorAddController.listOfAdditionalContact.add(bcm1);
				}
//			}

//			VendorAddController.selectedTabValue = 0;
			closeAddAdditionalContactScreen(btnSaveAdditionalContact);
			if(VendorAddController.whichScreenAddOrEdit == 0) {
				VendorAddController.fetchAdditionalContactsUsingDuplicate();
			} else {
				VendorEditController.fetchAdditionalContactsUsingDuplicate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeAddAdditionalContactScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchMasterDataForDropDowns();
//		if (CompanyAddController.addAddtionalContact != 1) {
//			if (CompanyAddController.additionalContactModel != null) {
//
//				txtAdditionalContact.setText(CompanyAddController.additionalContactModel.getCustomerName());
//				txtPosition.setText(CompanyAddController.additionalContactModel.getPosition());
//				txtExtension.setText(CompanyAddController.additionalContactModel.getExt());
//				txtFax.setText(CompanyAddController.additionalContactModel.getFax());
//				txtCellular.setText(CompanyAddController.additionalContactModel.getCellular());
//				txtEmail.setText(CompanyAddController.additionalContactModel.getEmail());
//				txtPager.setText(CompanyAddController.additionalContactModel.getPrefix());
//				txtPhone.setText(CompanyAddController.additionalContactModel.getPhone());
//				// ddlStatus.getSelectionModel().getSelectedItem();
//
//			}
//		}

	}

	public static List<Status> statusList = null;
	
	private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient
							.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API + "/openAdd", null);
					com.dpu.request.AdditionalContact driver = mapper.readValue(response,
							com.dpu.request.AdditionalContact.class);

					statusList = driver.getStatusList();
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
