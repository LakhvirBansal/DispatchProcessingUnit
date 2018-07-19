package com.dpu.util;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.DeleteAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.vendor.VendorAddController;
import com.dpu.controller.vendor.VendorController;
import com.dpu.controller.vendor.VendorEditController;
import com.dpu.model.Failed;
import com.dpu.model.Success;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VendorEditControllerBillingLocationRightMenu {

	public void menuAdd(MenuItem item1, String basePackage, String screen, String title) {
		item1.setStyle("-fx-padding: 0 10 0 10;");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				VendorAddController.add = 1;
				VendorAddController.selectedTabValue = 0;
				VendorAddController.whichScreenAddOrEdit = 1;
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
				VendorAddController.selectedTabValue = 0;
				VendorAddController.whichScreenAddOrEdit = 1;
				VendorAddController.addEditIndex = VendorEditController.duplicateTableBillingLocations.getSelectionModel().getSelectedIndex();
//				VendorAddController.listOfBilling.remove(VendorAddController.addEditIndex);
				
				//-----------------------------------------------------
				
				Long billingLocationId = VendorAddController.listOfBilling.get(VendorAddController.addEditIndex).getVendorBillingLocationId();
				Long companyId = VendorController.vendorId;

				if (billingLocationId == null) {
					VendorAddController.listOfBilling.remove(VendorAddController.addEditIndex);
					JOptionPane.showMessageDialog(null, "Billing Location Deleted SuccessFully.", "Info", 1);

				} else {

					// hit api to delete Additional Conatct
					try {
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API + "/" + companyId + "/billingLocation/" + billingLocationId, null);
						VendorAddController.listOfBilling.remove(VendorAddController.addEditIndex);

						ObjectMapper mapper = new ObjectMapper();

						if (response != null && response.contains("message")) {
							Success success = mapper.readValue(response, Success.class);
							JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
						} else {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				VendorEditController.fetchBillingLocationsUsingDuplicate();
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
				VendorAddController.whichScreenAddOrEdit = 1;
				VendorAddController.addEditIndex = VendorEditController.duplicateTableBillingLocations.getSelectionModel().getSelectedIndex();
				VendorEditController.editIndex = VendorEditController.duplicateTableBillingLocations.getSelectionModel().getSelectedIndex();
				VendorEditController.vendorBillingLocation = VendorAddController.listOfBilling.get(VendorAddController.addEditIndex);
				if (VendorEditController.vendorBillingLocation.getVendorBillingLocationId() != null)
					VendorEditController.billingLocationIdPri = VendorEditController.vendorBillingLocation.getVendorBillingLocationId();
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
