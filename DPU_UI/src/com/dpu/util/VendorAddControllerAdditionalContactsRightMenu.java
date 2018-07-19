package com.dpu.util;

import com.dpu.controller.vendor.VendorAddController;
import com.dpu.controller.vendor.VendorEditController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VendorAddControllerAdditionalContactsRightMenu {

	public void menuAdd(MenuItem item1, String basePackage, String screen, String title) {
		item1.setStyle("-fx-padding: 0 10 0 10;");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				VendorAddController.addAddtionalContact = 1;
				VendorAddController.selectedTabValue = 1;
//				CompanyAddController.listOfBilling = new ArrayList<BillingControllerModel>();
//				CompanyAddController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
//				CompanyAddController.company = new CompanyModel();
				openScreen(basePackage, screen, title);
			}
		});
	}
	
	public void menuDelete(MenuItem item1, String basePackage, String screen, String title) {
		item1.setStyle("-fx-padding: 0 10 0 10;");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				VendorAddController.selectedTabValue = 1;
				VendorAddController.addEditIndex = VendorAddController.duplicateTableAdditionalContact.getSelectionModel().getSelectedIndex();
				VendorAddController.listOfAdditionalContact.remove(VendorAddController.addEditIndex);
				VendorAddController.fetchAdditionalContactsUsingDuplicate();
				VendorAddController.addEditIndex = -1;
			}
		});
	}
	
	public Object menuEdit(MenuItem item2, String basePackage, String screen, String title) {
		Object object = null;
		item2.setStyle("-fx-padding: 0 10 0 10;");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
//				selectedTabValue = 0;
				VendorAddController.add = 0;
				VendorAddController.addEditIndex = VendorAddController.duplicateTableAdditionalContact.getSelectionModel().getSelectedIndex();
				VendorEditController.editIndex = VendorAddController.duplicateTableAdditionalContact.getSelectionModel().getSelectedIndex();
				VendorAddController.additionalContactModel = VendorAddController.duplicateTableAdditionalContact.getSelectionModel().getSelectedItem();
				if (VendorEditController.additionalContactModel.getVendorAdditionalContactId() != null)
					VendorEditController.additionalContactIdPri = VendorAddController.additionalContactModel.getVendorAdditionalContactId();
 				openScreen(basePackage, screen, title);
//				CompanyEditController.listOfBilling = new ArrayList<BillingControllerModel>();
//				CompanyEditController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
//				CompanyEditController.company = new CompanyModel();
//
//				CompanyEditController.selectedTabValue = 0;

//				CompanyModel companyy = cList.get(tblCompany.getSelectionModel().getSelectedIndex());
//				companyId = companyy.getCompanyId();
//				CompanyModel company = tblCompany.getSelectionModel().getSelectedItem();
//				if (company != null) {
//					Platform.runLater(new Runnable() { 
//
//						@Override
//						public void run() {
//							try {
//								ObjectMapper mapper = new ObjectMapper();
//								String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER
//										+ Iconstants.URL_COMPANY_API + "/" + company.getCompanyId(), null);
//
//								if (response != null && response.length() > 0) {
//									CompanyModel c = mapper.readValue(response, CompanyModel.class);

									/*if (c.getBillingLocations() != null) {
										int billingSize = c.getBillingLocations().size();
										for (int i = 0; i < billingSize; i++) {

											BillingControllerModel bcm = new BillingControllerModel();
											bcm.setCompanyId(c.getCompanyId());
											bcm.setBillingLocationId(
													c.getBillingLocations().get(i).getBillingLocationId());
											bcm.setAddress(c.getBillingLocations().get(i).getAddress());
											bcm.setCity(c.getBillingLocations().get(i).getCity());
											bcm.setName(c.getBillingLocations().get(i).getName());
											bcm.setContact(c.getBillingLocations().get(i).getContact());
											bcm.setFax(c.getBillingLocations().get(i).getFax());
											bcm.setPhone(c.getBillingLocations().get(i).getPhone());
											bcm.setZip(c.getBillingLocations().get(i).getZip());
											CompanyEditController.listOfBilling.add(bcm);
										}
									}*/

									/*if (c.getAdditionalContacts() != null) {
										int addtionalContactSize = c.getAdditionalContacts().size();
										for (int j = 0; j < addtionalContactSize; j++) {
											AdditionalContact additionalContact = new AdditionalContact();

											additionalContact.setCompanyId(c.getCompanyId());
											additionalContact.setAdditionalContactId(
													c.getAdditionalContacts().get(j).getAdditionalContactId());
											additionalContact.setCustomerName(
													c.getAdditionalContacts().get(j).getCustomerName());
											additionalContact
													.setCellular(c.getAdditionalContacts().get(j).getCellular());
											additionalContact.setEmail(c.getAdditionalContacts().get(j).getEmail());
											additionalContact.setExt(c.getAdditionalContacts().get(j).getExt());
											additionalContact.setFax(c.getAdditionalContacts().get(j).getFax());
											additionalContact.setPrefix(c.getAdditionalContacts().get(j).getCellular());
											additionalContact.setPhone(c.getAdditionalContacts().get(j).getPhone());
											additionalContact
													.setPosition(c.getAdditionalContacts().get(j).getPosition());
											additionalContact.setStatusId("Active");

											CompanyEditController.listOfAdditionalContact.add(additionalContact);
										}
									}*/

//							openScreen(basePackage, screen, title);
//									classa.initData(c);
//								}
//							} catch (Exception e) {
//								e.printStackTrace();
//								JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
//							}
//						}
//					});
				}
//			}
		});
		return object;
	}
	
	/*private void openAddScreen(String basePackage, String screen, String title) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(basePackage + screen));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
    	private Object openScreen(String basePackage, String screen, String title) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(basePackage + screen));

   			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
