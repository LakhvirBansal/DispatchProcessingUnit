package com.dpu.controller.vendor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.MainScreen;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.Vendor;
import com.dpu.model.VendorAdditionalContacts;
import com.dpu.model.VendorBillingLocation;
import com.dpu.util.VendorEditControllerAdditionalContactsRightMenu;
import com.dpu.util.VendorEditControllerBillingLocationRightMenu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class VendorEditController extends Application implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Pane addVendorPane;

	@FXML
	private TabPane tabPane;

	@FXML
	private TableColumn<VendorAdditionalContacts, String> additionalContact;

	@FXML
	private TableColumn<VendorAdditionalContacts, String> position;

	@FXML
	private TableColumn<VendorAdditionalContacts, String> phoneNo;

	@FXML
	private TableColumn<VendorAdditionalContacts, String> faxNo;

	@FXML
	private TableColumn<VendorAdditionalContacts, String> cellular;

	@FXML
	private TableColumn<VendorAdditionalContacts, String> email;

	@FXML
	private TableColumn<VendorAdditionalContacts, String> extension;

	@FXML
	private TableColumn<VendorAdditionalContacts, String> pager;

	@FXML
	private TableColumn<VendorAdditionalContacts, String> status;

	@FXML
	private TableColumn<VendorBillingLocation, String> address;

	@FXML
	private Button btnSaveVendor;

	@FXML
	private Button btnCancelVendor;

	@FXML
	private TableColumn<VendorBillingLocation, String> city;

	@FXML
	private TableColumn<VendorBillingLocation, String> contact;

	@FXML
	private TableColumn<VendorBillingLocation, String> fax;

	@FXML
	private TableColumn<VendorBillingLocation, String> name;

	@FXML
	private TableColumn<VendorBillingLocation, String> phone;

	@FXML
	private TableView<VendorAdditionalContacts> tableAdditionalContact;

	@FXML
	public TableView<VendorBillingLocation> tableBillingLocations;

	@FXML
	public TextField txtAddress;

	@FXML
	public TextField txtAfterHours;

	@FXML
	public TextField txtCellular;

	@FXML
	public TextField txtCity;

	@FXML
	public TextField txtCompany;

	@FXML
	public TextField txtContact;

	@FXML
	public TextField txtEmail;

	@FXML
	public TextField txtExt;

	@FXML
	public TextField txtFax;

	@FXML
	public TextField txtPager;

	@FXML
	public TextField txtPhone;

	@FXML
	public TextField txtPosition;

	@FXML
	public TextField txtPrefix;

	@FXML
	public TextField txtProvince;

	@FXML
	public TextField txtTollFree;

	@FXML
	public TextField txtUnitNo;

	@FXML
	public TextField txtWebsite;

	@FXML
	public TextField txtZip;

	@FXML
	public TableColumn<VendorBillingLocation, String> zip;

	@FXML
	Button btnUpdateVendor;

	public static int editIndex = -1;
	public static int addBillingLocation = 0;
	public static int addAddtionalContact = 0;
	public static VendorBillingLocation vendorBillingLocation = new VendorBillingLocation();
	public static VendorAdditionalContacts additionalContactModel = new VendorAdditionalContacts();
	public static ArrayList<VendorBillingLocation> listOfBilling = new ArrayList<VendorBillingLocation>(); 
	public static ArrayList<VendorAdditionalContacts> listOfAdditionalContact = new ArrayList<VendorAdditionalContacts>();

	public static Vendor vendor = new Vendor();

	Long vendorId = 0l;

	public static int selectedTabValue = 0;

	@FXML
	private void btnUpdateVendorAction() {
		editVendor();
		closeEditVendorScreen(btnUpdateVendor);

	}

	private void closeEditVendorScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}
	
	public static TableView<VendorAdditionalContacts> duplicateTableAdditionalContact;
	
	public static void fetchAdditionalContactsUsingDuplicate() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (VendorAddController.listOfAdditionalContact != null & !(VendorAddController.listOfAdditionalContact.isEmpty())) {
						ObservableList<VendorAdditionalContacts> data = FXCollections.observableArrayList(VendorAddController.listOfAdditionalContact);
						duplicateTableAdditionalContact.setItems(data);
						duplicateTableAdditionalContact.setVisible(true);
					} else {
						listOfAdditionalContact = new ArrayList<>();
						ObservableList<VendorAdditionalContacts> data = FXCollections.observableArrayList(listOfAdditionalContact);
						duplicateTableAdditionalContact.setItems(data);
						duplicateTableAdditionalContact.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}

		});
	}

	private void editVendor() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Vendor vendor = setVendorValue();
					String payload = mapper.writeValueAsString(vendor);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API + "/" + vendorId, null, payload);

					if (response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.vendorController.fetchVendors(response);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		duplicateTableBillingLocations = tableBillingLocations;
//		duplicateTableAdditionalContact = tableAdditionalContact;
		setColumnValues();
//		setAdditionalContactColumnValues();
		fetchBillingLocations();
//		fetchAdditionalContacts();

		/*txtCompany.setText(vendor.getName());
		txtAddress.setText(vendor.getAddress());
		txtUnitNo.setText(vendor.getUnitNo());
		txtCity.setText(vendor.getCity());
		txtProvince.setText(vendor.getProvinceState());
		txtZip.setText(vendor.getZip());
		txtEmail.setText(vendor.getEmail());
		txtWebsite.setText(vendor.getWebsite());
		txtContact.setText(vendor.getContact());
		txtPosition.setText(vendor.getPosition());
		txtPhone.setText(vendor.getPhone());
		txtExt.setText(vendor.getExt());
		txtFax.setText(vendor.getFax());
		txtPrefix.setText(vendor.getVendorPrefix());
		txtTollFree.setText(vendor.getTollfree());
		txtCellular.setText(vendor.getCellular());
		txtPager.setText(vendor.getPager());
		txtAfterHours.setText(vendor.getAfterHours());
		tabPane.getSelectionModel().select(selectedTabValue);*/

	}

	@Override
	public void start(Stage primaryStage) {

	}

	/*public static void main(String[] args) {
		launch(args);
	}*/

	private Vendor setVendorValue() {

		List<VendorBillingLocation> billingLocations = new ArrayList<VendorBillingLocation>();
		List<VendorAdditionalContacts> additionalContacts = new ArrayList<VendorAdditionalContacts>();

		// company.setCompanyId(companyId.toString());
		VendorAddController.vendor.setName(txtCompany.getText());
		VendorAddController.vendor.setAddress(txtAddress.getText());
		VendorAddController.vendor.setUnitNo(txtUnitNo.getText());
		VendorAddController.vendor.setCity(txtCity.getText());
		VendorAddController.vendor.setProvinceState(txtProvince.getText());
		VendorAddController.vendor.setZip(txtZip.getText());
		VendorAddController.vendor.setEmail(txtEmail.getText());
		VendorAddController.vendor.setWebsite(txtWebsite.getText());
		VendorAddController.vendor.setContact(txtContact.getText());
		VendorAddController.vendor.setPosition(txtPosition.getText());
		VendorAddController.vendor.setPhone(txtPhone.getText());
		VendorAddController.vendor.setExt(txtExt.getText());
		VendorAddController.vendor.setFax(txtFax.getText());
		VendorAddController.vendor.setVendorPrefix(txtPrefix.getText());
		VendorAddController.vendor.setTollfree(txtTollFree.getText());
		VendorAddController.vendor.setCellular(txtCellular.getText());
		VendorAddController.vendor.setPager(txtPager.getText());
		VendorAddController.vendor.setAfterHours(txtAfterHours.getText());

		// need to use for loop here

		if (VendorAddController.listOfBilling != null) {
			int sizeOfBilling = VendorAddController.listOfBilling.size();
			for (int i = 0; i < sizeOfBilling; i++) {
				VendorBillingLocation billingLocation = new VendorBillingLocation();
				VendorBillingLocation billingModel = VendorAddController.listOfBilling.get(i);
				if (billingModel.getVendorBillingLocationId() != null)
					billingLocation.setVendorBillingLocationId(billingModel.getVendorBillingLocationId());
				billingLocation.setName(billingModel.getName());
				billingLocation.setAddress(billingModel.getAddress());
				billingLocation.setCity(billingModel.getCity());
				billingLocation.setZip(billingModel.getZip());
				// need to get Status
				billingLocation.setStatusId(1l);
				billingLocation.setContact(billingModel.getContact());
				billingLocation.setPosition(txtPosition.getText());
				billingLocation.setEmail(txtEmail.getText());
				billingLocation.setCellular(txtCellular.getText());
				billingLocation.setPhone(billingModel.getPhone());
				billingLocation.setExt(txtExt.getText());
				billingLocation.setFax(billingModel.getFax());
				billingLocation.setTollfree(billingModel.getName());
				billingLocations.add(billingLocation);
			}
		}

		VendorAddController.vendor.setBillingLocations(billingLocations);

		// need to use for loop here
		if (VendorAddController.listOfAdditionalContact != null) {
			int sizeOfAdditionalContact = VendorAddController.listOfAdditionalContact.size();
			for (int i = 0; i < sizeOfAdditionalContact; i++) {

				VendorAdditionalContacts additionalContactModel = VendorAddController.listOfAdditionalContact.get(i);
				VendorAdditionalContacts additionalContact = new VendorAdditionalContacts();

				if (additionalContactModel.getVendorAdditionalContactId() != null)
					additionalContact.setVendorAdditionalContactId(additionalContactModel.getVendorAdditionalContactId());
				additionalContact.setVendorName(additionalContactModel.getVendorName());
				additionalContact.setPosition(additionalContactModel.getPosition());
				additionalContact.setPhone(additionalContactModel.getPhone());
				additionalContact.setExt(additionalContactModel.getExt());
				additionalContact.setFax(additionalContactModel.getFax());
				additionalContact.setPrefix(additionalContactModel.getPrefix());
				additionalContact.setCellular(additionalContactModel.getCellular());
				additionalContact.setStatusId(1l);
				additionalContact.setEmail(additionalContactModel.getEmail());
				additionalContacts.add(additionalContact);
			}
		}
		VendorAddController.vendor.setAdditionalContacts(additionalContacts);

		return VendorAddController.vendor;
	}

	public void initData(Vendor c) {
		vendorId =  c.getVendorId();
		txtCompany.setText(c.getName());
		txtContact.setText(c.getContact());
		txtAddress.setText(c.getAddress());
		txtPosition.setText(c.getPosition());
		txtUnitNo.setText(c.getUnitNo());
		txtPhone.setText(c.getPhone());
		txtExt.setText(c.getExt());
		txtCity.setText(c.getCity());
		txtFax.setText(c.getFax());
		txtPrefix.setText(c.getVendorPrefix());
		txtProvince.setText(c.getProvinceState());
		txtZip.setText(c.getZip());
		txtAfterHours.setText(c.getAfterHours());
		txtEmail.setText(c.getEmail());
		txtTollFree.setText(c.getTollfree());
		txtWebsite.setText(c.getWebsite());
		txtCellular.setText(c.getCellular());
		txtPager.setText(c.getPager());
	}

	@FXML
	private void btnCancelVendorAction() {
		listOfBilling = new ArrayList<VendorBillingLocation>();
		listOfAdditionalContact = new ArrayList<VendorAdditionalContacts>();
		vendor = new Vendor();
		closeAddVendorScreen(btnCancelVendor);
	}

	VendorEditControllerBillingLocationRightMenu rightMenu = new VendorEditControllerBillingLocationRightMenu();
	
	VendorEditControllerAdditionalContactsRightMenu rightMenuAdditionalContact = new VendorEditControllerAdditionalContactsRightMenu();
	
	@FXML
	private void btnSaveCompanyAction() {

		vendorId = VendorController.vendorId;
		// remove from listOfBilling if available in db

		/*
		 * if(listOfBilling != null && !(listOfBilling.isEmpty())){ int index =
		 * 0 ; int billingSize = listOfBilling.size(); for(int
		 * i=0;i<billingSize;i++){
		 * 
		 * if(listOfBilling.get(index).getBillingLocationId() != null){
		 * VendorBillingLocation bcm = listOfBilling.get(index);
		 * listOfBilling.remove(bcm); }else{ index++; } } }
		 */
		// remove from listOfAdditionalContact if available in db

		/*
		 * if(listOfAdditionalContact != null &&
		 * !(listOfAdditionalContact.isEmpty())){ int index = 0 ; int
		 * additionalContactSize = listOfAdditionalContact.size(); for(int
		 * i=0;i<additionalContactSize;i++){
		 * 
		 * if(listOfAdditionalContact.get(index).getAdditionalContactId() !=
		 * null){ AdditionalContact additionalContact =
		 * listOfAdditionalContact.get(index);
		 * listOfAdditionalContact.remove(additionalContact);
		 * 
		 * }else{ index++; } } }
		 */

		// listOfBilling = new ArrayList<VendorBillingLocation>();
		// listOfAdditionalContact = new ArrayList<AdditionalContact>();
		addVendor();
		closeAddVendorScreen(btnSaveVendor);
	}

	int additionalContactMenuCount = 0;

	@FXML
	void handleAddContMouseClick(MouseEvent event) {

		ContextMenu contextMenu = new ContextMenu();
		MenuItem add = new MenuItem("Add");
		rightMenuAdditionalContact.menuAdd(add, Iconstants.VENDOR_BASE_PACKAGE, Iconstants.XML_VENDOR_ADDITIONAL_CONTACT_ADD_SCREEN, Iconstants.ADD_NEW_ADDITIONAL_CONTACT);
		MenuItem edit = new MenuItem("Edit");
		rightMenuAdditionalContact.menuEdit(edit, Iconstants.VENDOR_BASE_PACKAGE, Iconstants.XML_VENDOR_ADDITIONAL_CONTACT_EDIT_SCREEN, Iconstants.EDIT_ADDITIONAL_CONTACT);
		MenuItem delete = new MenuItem("Delete");
		rightMenuAdditionalContact.menuDelete(delete, null, null, null);
		MenuItem duplicate = new MenuItem("Duplicate");
		MenuItem personalize = new MenuItem("Personalize");
		MenuItem filterBy = new MenuItem("Filter By");
		MenuItem filterByExclude = new MenuItem("Filter By Exclude");
		MenuItem clearAllFilters = new MenuItem("Clear All Filters");

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(add, edit, delete, duplicate, personalize, filterBy, filterByExclude, clearAllFilters);
			// When user right-click on Table
		tableAdditionalContact.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
					if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
						contextMenu.show(tableAdditionalContact, mouseEvent.getScreenX(), mouseEvent.getScreenY());
					} else {
						contextMenu.hide();
					}
				}
			}
		});
		/*// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//add = 1;
//				setValuesToCmpanyTextField();
				VendorAddController.addAddtionalContact = 1;
				VendorAddController.selectedTabValue = 1;
//				VendorAddController.vendor.setName(txtCompany.getText());
//				VendorAddController.vendor.setAddress(txtAddress.getText());
//				VendorAddController.vendor.setUnitNo(txtUnitNo.getText());
//				VendorAddController.vendor.setCity(txtCity.getText());
//				VendorAddController.vendor.setProvinceState(txtProvince.getText());
//				VendorAddController.vendor.setZip(txtZip.getText());
//				VendorAddController.vendor.setEmail(txtEmail.getText());
//				VendorAddController.vendor.setWebsite(txtWebsite.getText());
//				VendorAddController.vendor.setContact(txtContact.getText());
//				VendorAddController.vendor.setPosition(txtPosition.getText());
//				VendorAddController.vendor.setPhone(txtPhone.getText());
//				VendorAddController.vendor.setExt(txtExt.getText());
//				VendorAddController.vendor.setFax(txtFax.getText());
//				VendorAddController.vendor.setVendorPrefix(txtPrefix.getText());
//				VendorAddController.vendor.setTollfree(txtTollFree.getText());
//				VendorAddController.vendor.setCellular(txtCellular.getText());
//				VendorAddController.vendor.setPager(txtPager.getText());
//				VendorAddController.vendor.setAfterHours(txtAfterHours.getText());

				openAddAdditionalContactScreen();

//				try {
//					closeAddCompanyScreen(btnSaveCompany);
//
//				} catch (Exception e) {
//					e.printStackTrace();
//					System.exit(0);
//				}

			}
		});
		MenuItem item2 = new MenuItem("EDIT");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setValuesToCmpanyTextField();
				selectedTabValue = 1;
				addAddtionalContact = 0;
				editIndex = tableAdditionalContact.getSelectionModel().getSelectedIndex();
				additionalContactModel = tableAdditionalContact.getSelectionModel().getSelectedItem();

//				if (additionalContactModel.getAdditionalContactId() != null)
//					additionalContactIdPri = additionalContactModel.getAdditionalContactId();
//				openAddAdditionalContactScreen();
//				closeAddCompanyScreen(btnSaveCompany);

			}
		});

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 1;
//				setValuesToCmpanyTextField();
				editIndex = tableAdditionalContact.getSelectionModel().getSelectedIndex();
 
				Long additionalontactId = VendorAddController.listOfAdditionalContact.get(editIndex).getVendorAdditionalContactId();
				Long companyId = VendorController.vendorId;

				if (additionalontactId == null) {
					VendorAddController.listOfAdditionalContact.remove(editIndex);
					JOptionPane.showMessageDialog(null, "Additional Contact Deleted SuccessFully.", "Info", 1);

				} else {

					// hit api to delete Additional Conatct
					try {
						String response = DeleteAPIClient
								.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API + "/"
										+ companyId + "/additionalContacts/" + additionalontactId, null);
						VendorAddController.listOfAdditionalContact.remove(editIndex);

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
				// }
				editIndex = -1;
				VendorAddController.fetchAdditionalContactsUsingDuplicate();

			}
		});

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(item1, item2, item3);
		if (additionalContactMenuCount == 0) {
			additionalContactMenuCount++;
			// When user right-click on Table
			tableAdditionalContact.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
				@Override
				public void handle(ContextMenuEvent event) {
					contextMenu.show(tableAdditionalContact, event.getScreenX(), event.getScreenY());

				}

			});

		}*/
		
		
	
	}
	
	@FXML
	private void additionalContactsSelectionChanged() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					duplicateTableAdditionalContact = tableAdditionalContact;
					VendorAddController.listOfAdditionalContact = new ArrayList<>();
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(
							Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API + "/" + VendorController.vendorId + "/additionalContacts",
							null);

					if (response != null && response.length() > 0) {
						Vendor c = mapper.readValue(response, Vendor.class);

						// ----------------------------------------------

						if (c.getAdditionalContacts() != null) {
							int addtionalContactSize = c.getAdditionalContacts().size();
							for (int j = 0; j < addtionalContactSize; j++) {
								VendorAdditionalContacts additionalContact = new VendorAdditionalContacts();

//								additionalContact.set(c.getVendorId());
								additionalContact.setVendorAdditionalContactId(c.getAdditionalContacts().get(j).getVendorAdditionalContactId());
								additionalContact.setVendorName(c.getAdditionalContacts().get(j).getVendorName());
								additionalContact.setCellular(c.getAdditionalContacts().get(j).getCellular());
								additionalContact.setEmail(c.getAdditionalContacts().get(j).getEmail());
								additionalContact.setExt(c.getAdditionalContacts().get(j).getExt());
								additionalContact.setFax(c.getAdditionalContacts().get(j).getFax());
								additionalContact.setPrefix(c.getAdditionalContacts().get(j).getCellular());
								additionalContact.setPhone(c.getAdditionalContacts().get(j).getPhone());
								additionalContact.setPosition(c.getAdditionalContacts().get(j).getPosition());
								additionalContact.setStatusId(c.getAdditionalContacts().get(j).getStatusId());
								for(int i=0;i<VendorController.statusList.size();i++) {
									if(VendorController.statusList.get(i).getId().equals(c.getAdditionalContacts().get(j).getStatusId())) {
										additionalContact.setStatusName(VendorController.statusList.get(i).getStatus());
										break;
									}
								}

								VendorAddController.listOfAdditionalContact.add(additionalContact);
							}
						}
						
						fetchAdditionalContacts();

					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	private void openAddAdditionalContactScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.VENDOR_BASE_PACKAGE + Iconstants.XML_VENDOR_ADDITIONAL_CONTACT_ADD_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add Additional Contact");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeAddVendorScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void addVendor() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					ObjectMapper mapper = new ObjectMapper();
					Vendor vendor = setVendorValue();
					String payload = mapper.writeValueAsString(vendor);
					// companyId = Long.parseLong(company.getCompanyId());

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API + "/" + vendorId, null, payload);

					if (response != null) {
						// Success success = mapper.readValue(response,
						// Success.class);
						JOptionPane.showMessageDialog(null, "Vendor Updated Successfully.", "Info", 1);
					} else {
						// Failed failed = mapper.readValue(response,
						// Failed.class);
						JOptionPane.showMessageDialog(null, "Failed to Updated Vendor.", "Info", 1);
					}

					closeEditVendorScreen(btnSaveVendor);
					MainScreen.vendorController.fetchVendors(response);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	int billingLocationCountMenu = 0;
	public static Long billingLocationIdPri = 0l;
	public static Long additionalContactIdPri = 0l;

	public static TableView<VendorBillingLocation> duplicateTableBillingLocations;
	
	public static void fetchBillingLocationsUsingDuplicate() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (VendorAddController.listOfBilling != null & !(VendorAddController.listOfBilling.isEmpty())) {
						ObservableList<VendorBillingLocation> data = FXCollections.observableArrayList(VendorAddController.listOfBilling);
						duplicateTableBillingLocations.setItems(data);
						duplicateTableBillingLocations.setVisible(true);
					} else {
						listOfBilling = new ArrayList<>();
						ObservableList<VendorBillingLocation> data = FXCollections.observableArrayList(listOfBilling);
						duplicateTableBillingLocations.setItems(data);
						duplicateTableBillingLocations.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}

		});
	}
	
	@FXML
	public void handleMouseClick(MouseEvent arg0) {

		ContextMenu contextMenu = new ContextMenu();
		MenuItem add = new MenuItem("Add");
		rightMenu.menuAdd(add, Iconstants.VENDOR_BASE_PACKAGE, Iconstants.XML_VENDOR_BILLING_LOCATION_ADD_SCREEN, "Add New Billing Location");
		MenuItem edit = new MenuItem("Edit");
		rightMenu.menuEdit(edit, Iconstants.VENDOR_BASE_PACKAGE, Iconstants.XML_VENDOR_BILLING_LOCATION_EDIT_SCREEN, "Edit Billing Location");
		MenuItem delete = new MenuItem("Delete");
		rightMenu.menuDelete(delete, null, null, null);
		MenuItem duplicate = new MenuItem("Duplicate");
		MenuItem personalize = new MenuItem("Personalize");
		MenuItem filterBy = new MenuItem("Filter By");
		MenuItem filterByExclude = new MenuItem("Filter By Exclude");
		MenuItem clearAllFilters = new MenuItem("Clear All Filters");

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(add, edit, delete, duplicate, personalize, filterBy, filterByExclude, clearAllFilters);
			// When user right-click on Table
		tableBillingLocations.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
					if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
						contextMenu.show(tableBillingLocations, mouseEvent.getScreenX(), mouseEvent.getScreenY());
					} else {
						contextMenu.hide();
					}
				}
			}
		});
		
	/*	// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addBillingLocation = 1;
				selectedTabValue = 0;
				//add = 1;
//				setValuesToCmpanyTextField();
				openAddBillingLocationScreen();

//				try {
//					closeAddCompanyScreen(btnSaveVendor);
//
//				} catch (Exception e) {
//					e.printStackTrace();
//					System.exit(0);
//				}

			}
		});
		MenuItem item2 = new MenuItem("EDIT");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 0;
//				setValuesToCmpanyTextField();
				addBillingLocation = 0;
				editIndex = tableBillingLocations.getSelectionModel().getSelectedIndex();
				VendorAddController.VendorBillingLocation = tableBillingLocations.getSelectionModel().getSelectedItem();

				if (VendorAddController.VendorBillingLocation.getBillingLocationId() != null)
					billingLocationIdPri = VendorAddController.VendorBillingLocation.getBillingLocationId();

				openEditBillingLocationScreen();
//				closeAddCompanyScreen(btnSaveCompany);

			}
		});

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
//				selectedTabValue = 0;
//
//				editIndex = tableBillingLocations.getSelectionModel().getSelectedIndex();
//
//				// hit API to remove record from db.
//				// if (listOfBilling.get(editIndex).getBillingLocationId() ==
//				// null
//				// || listOfBilling.get(editIndex).getBillingLocationId() != 0l)
//				// {
//				Long billingId = listOfBilling.get(editIndex).getBillingLocationId();
//				Long companyId = listOfBilling.get(editIndex).getCompanyId();
//
//				if (billingId == null) {
//					JOptionPane.showMessageDialog(null, "Billing Location Deleted SuccessFully.", "Info", 1);
//					listOfBilling.remove(editIndex);
//				} else {
//
//					try {
//						String response = DeleteAPIClient
//								.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_DELETE_BILLING_LOCATION_API + "/"
//										+ companyId + "/billinglocations/" + billingId, null);
//						listOfBilling.remove(editIndex);
//
//						ObjectMapper mapper = new ObjectMapper();
//
//						if (response != null && response.contains("message")) {
//							Success success = mapper.readValue(response, Success.class);
//							JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
//						} else {
//							Failed failed = mapper.readValue(response, Failed.class);
//							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
//						}
//
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//				// }
//				editIndex = -1;
//
//				setValuesToCmpanyTextField();
//
//				try {
//					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
//							.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
//					Parent root = (Parent) fxmlLoader.load();
//					Stage stage = new Stage();
//					stage.initModality(Modality.APPLICATION_MODAL);
//					stage.setTitle("Add New Company");
//					stage.setScene(new Scene(root));
//					stage.show();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//				closeAddCompanyScreen(btnSaveCompany);

			}
		});

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(item1, item2, item3);

		if (billingLocationCountMenu == 0) {
			billingLocationCountMenu++;
			// When user right-click on Table
			tableBillingLocations.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
				@Override
				public void handle(ContextMenuEvent event) {
					contextMenu.show(tableBillingLocations, event.getScreenX(), event.getScreenY());

				}

			});
		}*/
	}

	private void openAddBillingLocationScreen() {

		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.VENDOR_BASE_PACKAGE + Iconstants.XML_VENDOR_BILLING_LOCATION_ADD_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add Billing Location");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void openEditBillingLocationScreen() {

		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.VENDOR_BASE_PACKAGE + Iconstants.XML_VENDOR_BILLING_LOCATION_EDIT_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Edit Billing Location");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// new added
	public void fetchBillingLocations() {
		// fetchColumns();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (VendorAddController.listOfBilling != null & !(VendorAddController.listOfBilling.isEmpty())) {
						ObservableList<VendorBillingLocation> data = FXCollections.observableArrayList(VendorAddController.listOfBilling);
						setColumnValues();
						duplicateTableBillingLocations.setVisible(true);
						duplicateTableBillingLocations.setItems(data);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}

		});
	}

	public void fetchAdditionalContacts() {
		// fetchColumns();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					if (VendorAddController.listOfAdditionalContact != null & !(VendorAddController.listOfAdditionalContact.isEmpty())) {
						ObservableList<VendorAdditionalContacts> data = FXCollections.observableArrayList(VendorAddController.listOfAdditionalContact);
						setAdditionalContactColumnValues();
						duplicateTableAdditionalContact.setItems(data);
						duplicateTableAdditionalContact.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}

		});
	}

	private void setAdditionalContactColumnValues() {

		additionalContact.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorAdditionalContacts, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorAdditionalContacts, String> param) {
						return new SimpleStringProperty(param.getValue().getVendorName() + "");
					}
				});
		position.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorAdditionalContacts, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorAdditionalContacts, String> param) {
						return new SimpleStringProperty(param.getValue().getPosition() + "");
					}
				});
		phoneNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorAdditionalContacts, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorAdditionalContacts, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone() + "");
					}
				});
		faxNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorAdditionalContacts, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorAdditionalContacts, String> param) {
						return new SimpleStringProperty(param.getValue().getFax() + "");
					}
				});
		cellular.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorAdditionalContacts, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorAdditionalContacts, String> param) {
						return new SimpleStringProperty(param.getValue().getCellular() + "");
					}
				});
		email.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorAdditionalContacts, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorAdditionalContacts, String> param) {
						return new SimpleStringProperty(param.getValue().getEmail() + "");
					}
				});
		extension.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorAdditionalContacts, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorAdditionalContacts, String> param) {
						return new SimpleStringProperty(param.getValue().getExt() + "");
					}
				});
		pager.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorAdditionalContacts, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorAdditionalContacts, String> param) {
						return new SimpleStringProperty(param.getValue().getPrefix() + "");
					}
				});
		status.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorAdditionalContacts, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorAdditionalContacts, String> param) {
						return new SimpleStringProperty(param.getValue().getStatusName() + "");
					}
				});
	}

	private void setColumnValues() {

		name.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
						return new SimpleStringProperty(param.getValue().getName() + "");
					}
				});
		address.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
						return new SimpleStringProperty(param.getValue().getAddress() + "");
					}
				});
		city.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
						return new SimpleStringProperty(param.getValue().getCity() + "");
					}
				});
		phone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone() + "");
					}
				});
		contact.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
						return new SimpleStringProperty(param.getValue().getContact() + "");
					}
				});
		zip.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
						return new SimpleStringProperty(param.getValue().getZip() + "");
					}
				});
		fax.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
						return new SimpleStringProperty(param.getValue().getFax() + "");
					}
				});
	}

	public void setValuesToCmpanyTextField() {
		vendor.setName(txtCompany.getText());
		vendor.setAddress(txtAddress.getText());
		vendor.setUnitNo(txtUnitNo.getText());
		vendor.setCity(txtCity.getText());
		vendor.setProvinceState(txtProvince.getText());
		vendor.setZip(txtZip.getText());
		vendor.setEmail(txtEmail.getText());
		vendor.setWebsite(txtWebsite.getText());
		vendor.setContact(txtContact.getText());
		vendor.setPosition(txtPosition.getText());
		vendor.setPhone(txtPhone.getText());
		vendor.setExt(txtExt.getText());
		vendor.setFax(txtFax.getText());
		vendor.setVendorPrefix(txtPrefix.getText());
		vendor.setTollfree(txtTollFree.getText());
		vendor.setCellular(txtCellular.getText());
		vendor.setPager(txtPager.getText());
		vendor.setAfterHours(txtAfterHours.getText());

	}

}