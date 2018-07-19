package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.AdditionalContact;
import com.dpu.model.Company;
import com.dpu.model.Status;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AdditionalContactEditController implements Initializable {

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
	private ComboBox<String> ddlStatus,ddlFunction;

	@FXML
	private TextField txtAdditionalContact;

	@FXML
	private TextField txtCellular,txtStatus,txtFunction;

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
		 
		CompanyEditController.selectedTabValue = 0;
		closeAddAdditionalContactScreen(btnCancelAdditionalContact);
	}

	@FXML
	void btnUpdateAdditionalContactAction(ActionEvent event) {

		boolean result = validateAddCompanyScreen();
		if (result) {
			try {

				String additionalContact = txtAdditionalContact.getText();
				String position = txtPosition.getText();
				String phone = txtPhone.getText();
				String extension = txtExtension.getText();
				String fax = txtFax.getText();
				String pager = txtPager.getText();
				String cellular = txtCellular.getText();
				Long status = statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId();
				String email = txtEmail.getText();
				Long function = functionList.get(ddlFunction.getSelectionModel().getSelectedIndex()).getTypeId();
				AdditionalContact bcm1 = new AdditionalContact(additionalContact, position, phone, fax, cellular, email,
						extension, pager, status,function);
				if(CompanyEditController.additionalContactIdPri != 0l)
					bcm1.setAdditionalContactId(CompanyEditController.additionalContactIdPri);
					
				CompanyEditController.selectedTabValue = 1;
			
					// if(CompanyAddController.addEditIndex != -1){
				if (CompanyEditController.addAddtionalContact == 0) {
					CompanyEditController.listOfAdditionalContact.set(CompanyEditController.editIndex, bcm1);
				} else if (CompanyEditController.addAddtionalContact == 1) {
					CompanyEditController.listOfAdditionalContact.add(bcm1);
				}
			 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			CompanyEditController.selectedTabValue = 0;
			//CompanyEditController companyEditController = new CompanyEditController();
			CompanyEditController.fetchAdditionalContactsUsingDuplicate();
			closeAddAdditionalContactScreen(btnUpdateAdditionalContact);
		}
	}

	private void closeAddAdditionalContactScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	public List<Status> statusList = null;
	public List<Type> functionList = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		fetchMasterDataForDropDowns();
		if (CompanyEditController.addAddtionalContact != 1) {
			if (CompanyEditController.additionalContactModel != null) {

				txtAdditionalContact.setText(CompanyEditController.additionalContactModel.getCustomerName());
				txtPosition.setText(CompanyEditController.additionalContactModel.getPosition());
				txtExtension.setText(CompanyEditController.additionalContactModel.getExt());
				txtFax.setText(CompanyEditController.additionalContactModel.getFax());
				txtCellular.setText(CompanyEditController.additionalContactModel.getCellular());
				txtEmail.setText(CompanyEditController.additionalContactModel.getEmail());
				txtPager.setText(CompanyEditController.additionalContactModel.getPrefix());
				txtPhone.setText(CompanyEditController.additionalContactModel.getPhone());
			//	ddlStatus.setText("Active");
			//	ddlFunction.setText(CompanyEditController.additionalContactModel.getFunction());
				if(AdditionalContactAddController.statusList == null) {
					
					fetchMasterDataForDropDowns();
				} else {
					statusList = AdditionalContactAddController.statusList;
					functionList = AdditionalContactAddController.functionList;
					
				}
				
				if(statusList != null && statusList.size() > 0) {
					for(int i=0;i<statusList.size();i++) {

						ddlStatus.getItems().add(statusList.get(i).getStatus());
						if(statusList.get(i).getId().equals(CompanyEditController.additionalContactModel.getStatusId())) {
							ddlStatus.getSelectionModel().select(i);
						}
					}
				}
				
				if(functionList != null && functionList.size() > 0) {
					for(int i=0;i<functionList.size();i++) {

						ddlFunction.getItems().add(functionList.get(i).getTypeName());
						if(functionList.get(i).getTypeId().equals(CompanyEditController.additionalContactModel.getFunction())) {
							ddlFunction.getSelectionModel().select(i);
						}
					}
				}
			}
		}

	}

	private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient
							.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/openAddAdditionalContact", null);
					Company company = mapper.readValue(response, Company.class);
					 
					statusList = company.getStatusList();
					fillDropDown(ddlStatus, statusList);
					
					functionList = company.getFunctionList();
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
	 
	
	// validation Applying
		@FXML
		Label additionalContactMsg;

		Validate validate = new Validate();

		private boolean validateAddCompanyScreen() {

			boolean result = true;
			String additionalContact = txtAdditionalContact.getText();
		 
			boolean blnAdditionalContact = validate.validateEmptyness(additionalContact);
			if (!blnAdditionalContact) {
				result = false;
				txtAdditionalContact.setStyle("-fx-text-box-border: red;");
				additionalContactMsg.setVisible(true);
				additionalContactMsg.setText("Additional Contact is Mandatory");
				additionalContactMsg.setTextFill(Color.RED);
			}
			
			return result;
		}

		@FXML
		private void companyAdditionalContactKeyPressed() {

			String name = txtAdditionalContact.getText();
			boolean result = validate.validateEmptyness(name);
			
			if (!result) {
				txtAdditionalContact.setStyle("-fx-focus-color: red;");
				txtAdditionalContact.requestFocus();
				additionalContactMsg.setVisible(true);
				additionalContactMsg.setText("Additional Contact is Mandatory");
				additionalContactMsg.setTextFill(Color.RED);
			}  else{
				txtAdditionalContact.setStyle("-fx-focus-color: skyblue;");
				additionalContactMsg.setVisible(false);
			}
		}
}
