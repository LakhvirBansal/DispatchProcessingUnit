package com.dpu.controller.vendor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.MainScreen;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.Vendor;
import com.dpu.model.VendorAdditionalContacts;
import com.dpu.model.VendorBillingLocation;
import com.dpu.request.CompanyModel;
import com.dpu.util.Validate;
import com.dpu.util.VendorAddControllerAdditionalContactsRightMenu;
import com.dpu.util.VendorAddControllerBillingLocationRightMenu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class VendorAddController extends Application implements Initializable {

	public static int add = 0;
	Validate validate = new Validate();
	int billingLocationCountMenu = 0;
	public static int addEditIndex = -1;
	int additionalContactCountMenu = 0;
	public static int selectedTabValue = 0;
	public static int addAddtionalContact = 0;
	public static Vendor vendor = new Vendor();
	public static VendorAdditionalContacts additionalContactModel = new VendorAdditionalContacts();
	public static VendorBillingLocation VendorBillingLocation = new VendorBillingLocation();
	public static ArrayList<VendorBillingLocation> listOfBilling = new ArrayList<VendorBillingLocation>();
	public static ArrayList<VendorAdditionalContacts> listOfAdditionalContact = new ArrayList<VendorAdditionalContacts>();
	
	public static int whichScreenAddOrEdit = 0;
	
	@FXML
	private Pane addVendorPane;

	@FXML
	public TableView<VendorBillingLocation> tableBillingLocations;

	public static TableView<VendorBillingLocation> duplicateTableBillingLocations;

	@FXML
	private TableView<VendorAdditionalContacts> tableAdditionalContact;

	public static TableView<VendorAdditionalContacts> duplicateTableAdditionalContact;

	@FXML
	private TableColumn<VendorAdditionalContacts, String> additionalContact, position, phoneNo, faxNo, cellular, email, extension, pager, status;

	@FXML
	private TableColumn<VendorBillingLocation, String> address, city, contact, fax, name, phone, zip;

	@FXML
	private Button btnSaveVendor, btnCancelVendor;

	@FXML
	public TextField txtAddress, txtAfterHours, txtCellular, txtCity, txtCompany, txtContact, txtEmail, txtExt, txtFax, txtPager, 
	txtPhone, txtPosition, txtPrefix, txtProvince, txtTollFree, txtUnitNo, txtWebsite, txtZip;

	@FXML
	private TabPane tabPane;
	
	@FXML
	private Label lblVendorName;

	public void fetchBillingLocations() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (listOfBilling != null & !(listOfBilling.isEmpty())) {
						ObservableList<VendorBillingLocation> data = FXCollections.observableArrayList(listOfBilling);
						setColumnValues();
						tableBillingLocations.setItems(data);
						tableBillingLocations.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}

		});
	}
	
	public static void fetchBillingLocationsUsingDuplicate() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (listOfBilling != null & !(listOfBilling.isEmpty())) {
						ObservableList<VendorBillingLocation> data = FXCollections.observableArrayList(listOfBilling);
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
	
	public void fetchAdditionalContacts() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (listOfAdditionalContact != null & !(listOfAdditionalContact.isEmpty())) {
						ObservableList<VendorAdditionalContacts> data = FXCollections.observableArrayList(VendorEditController.listOfAdditionalContact);
						setAdditionalContactColumnValues();
						tableAdditionalContact.setItems(data);
						tableAdditionalContact.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}

		});
	}
	
	public static void fetchAdditionalContactsUsingDuplicate() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (listOfAdditionalContact != null & !(listOfAdditionalContact.isEmpty())) {
						ObservableList<VendorAdditionalContacts> data = FXCollections.observableArrayList(listOfAdditionalContact);
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


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		duplicateTableBillingLocations = tableBillingLocations;
		duplicateTableAdditionalContact = tableAdditionalContact;
		setColumnValues();
		setAdditionalContactColumnValues();
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void billingLocationMouseClick(MouseEvent arg0) {
		VendorAddControllerBillingLocationRightMenu rightMenu = new VendorAddControllerBillingLocationRightMenu();
		ContextMenu contextMenu = new ContextMenu();
		MenuItem add = new MenuItem("Add");
		rightMenu.menuAdd(add, Iconstants.VENDOR_BASE_PACKAGE, Iconstants.XML_VENDOR_BILLING_LOCATION_ADD_SCREEN, Iconstants.ADD_NEW_BILLING_LOCATION);
		MenuItem edit = new MenuItem("Edit");
		rightMenu.menuEdit(edit, Iconstants.VENDOR_BASE_PACKAGE, Iconstants.XML_VENDOR_BILLING_LOCATION_EDIT_SCREEN, Iconstants.EDIT_BILLING_LOCATION);
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
	}

	@FXML
	public void additionalContactMouseClick(MouseEvent arg0) {
		VendorAddControllerAdditionalContactsRightMenu rightMenuAdditionalContacts = new VendorAddControllerAdditionalContactsRightMenu();
		ContextMenu contextMenu = new ContextMenu();
		MenuItem add = new MenuItem("Add");
		rightMenuAdditionalContacts.menuAdd(add, Iconstants.VENDOR_BASE_PACKAGE, Iconstants.XML_VENDOR_ADDITIONAL_CONTACT_ADD_SCREEN, "Add New Additional Contact");
		MenuItem edit = new MenuItem("Edit");
		rightMenuAdditionalContacts.menuEdit(edit, Iconstants.VENDOR_BASE_PACKAGE, Iconstants.XML_VENDOR_ADDITIONAL_CONTACT_EDIT_SCREEN, "Edit Additional Contact");
		MenuItem delete = new MenuItem("Delete");
		rightMenuAdditionalContacts.menuDelete(delete, null, null, null);
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
	}
	
	// Create ContextMenu
	@FXML
	public void handleMouseClick(MouseEvent arg0) {

	/*	// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				add = 1;
				selectedTabValue = 0;
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

				openAddBillingLocationScreen();

				try {
					closeAddVendorScreen(btnSaveVendor);

				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}

			}
		});
		MenuItem item2 = new MenuItem("EDIT");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 0;
				add = 0;
				addEditIndex = tableBillingLocations.getSelectionModel().getSelectedIndex();
				VendorBillingLocation = tableBillingLocations.getSelectionModel().getSelectedItem();
				openAddBillingLocationScreen();
				closeAddVendorScreen(btnSaveVendor);

			}
		});

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 0;
				addEditIndex = tableBillingLocations.getSelectionModel().getSelectedIndex();
				VendorEditController.listOfBilling.remove(addEditIndex);
				addEditIndex = -1;

				try {
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
				closeAddVendorScreen(btnSaveVendor);
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

	@FXML
	private void btnSaveVendorAction() {

		boolean result = validateAddVendorScreen();
		if (result) {
			addVendor();
			closeAddVendorScreen(btnSaveVendor);
		}

	}

	@FXML
	private void btnCancelVendorAction() {
		VendorEditController.listOfBilling = new ArrayList<VendorBillingLocation>();
		VendorEditController.listOfAdditionalContact = new ArrayList<VendorAdditionalContacts>();
		vendor = new Vendor();
		closeAddVendorScreen(btnCancelVendor);
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

					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API, null, payload);

					if (response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.vendorController.fetchVendors(response);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	private Vendor setVendorValue() {

		List<VendorBillingLocation> billingLocations = new ArrayList<VendorBillingLocation>();
		List<VendorAdditionalContacts> additionalContacts = new ArrayList<VendorAdditionalContacts>();

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

		// need to use for loop here

		if (listOfBilling != null) {
			int sizeOfBilling = listOfBilling.size();
			for (int i = 0; i < sizeOfBilling; i++) {
				VendorBillingLocation billingLocation = new VendorBillingLocation();
				VendorBillingLocation billingModel = listOfBilling.get(i);
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

		vendor.setBillingLocations(billingLocations);

		// need to use for loop here
		if (listOfAdditionalContact != null) {
			int sizeOfAdditionalContact = listOfAdditionalContact.size();
			for (int i = 0; i < sizeOfAdditionalContact; i++) {

				VendorAdditionalContacts additionalContactModel = listOfAdditionalContact.get(i);
				VendorAdditionalContacts additionalContact = new VendorAdditionalContacts();
				additionalContact.setVendorName(additionalContactModel.getVendorName());
				additionalContact.setPosition(additionalContactModel.getPosition());
				additionalContact.setPhone(additionalContactModel.getPhone());
				additionalContact.setExt(additionalContactModel.getExt());
				additionalContact.setFax(additionalContactModel.getFax());
				// set Pager in prefix.. chnage it
				additionalContact.setPrefix(additionalContactModel.getPrefix());
				additionalContact.setCellular(additionalContactModel.getCellular());
				// need to set Status here
				additionalContact.setStatusId(0l);
				additionalContact.setEmail(additionalContactModel.getEmail());

				additionalContacts.add(additionalContact);
			}
		}
		vendor.setAdditionalContacts(additionalContacts);
		return vendor;

	}

	public void initData(CompanyModel c) {
		// fetchBillingLocations();
		// fetchAdditionalContacts();
//		txtCompany.setText(c.getName());
//		txtContact.setText(c.getContact());
//		txtAddress.setText(c.getAddress());
//		txtPosition.setText(c.getPosition());
//		txtUnitNo.setText(c.getUnitNo());
//		txtPhone.setText(c.getPhone());
//		txtExt.setText(c.getExt());
//		txtCity.setText(c.getCity());
//		txtFax.setText(c.getFax());
//		txtPrefix.setText(c.getCompanyPrefix());
//		txtProvince.setText(c.getProvinceState());
//		txtZip.setText(c.getZip());
//		txtAfterHours.setText(c.getAfterHours());
//		txtEmail.setText(c.getEmail());
//		txtTollFree.setText(c.getTollfree());
//		txtWebsite.setText(c.getWebsite());
//		txtCellular.setText(c.getCellular());
//		txtPager.setText(c.getPager());
	}

	public StringBuffer validsteFields() {
		StringBuffer strBuff = new StringBuffer();
		String customerName = txtCompany.getText();
		String email = txtEmail.getText();

		if (customerName == null || customerName.trim().equals("")) {
			txtCompany.setStyle("-fx-focus-color: #87CEEB;");
			strBuff.append("Company Name is Mandatory\n");
		}
		if (email == null || email.trim().equals("")) {
			txtEmail.setStyle("-fx-focus-color: #87CEEB;");
			strBuff.append("Email is Mandatory\n");
		}

		return strBuff;
	}

	private boolean validateAddVendorScreen() {
		boolean response = true;
		String name = txtCompany.getText();
		boolean result = validate.validateEmptyness(name);

		if (!result) {
			
			response = false;
			txtCompany.setStyle("-fx-text-box-border: red;");
			lblVendorName.setVisible(true);
			lblVendorName.setText("Vendor Name is Mandatory");
			lblVendorName.setTextFill(Color.RED);
			
		} else if (!validate.validateLength(name, 5, 25)) {
			
			response = false;
			txtCompany.setStyle("-fx-text-box-border: red;");
			lblVendorName.setVisible(true);
			lblVendorName.setText("Min. length 5 and Max. length 25");
			lblVendorName.setTextFill(Color.RED);
		}
		return response;
	}
	
	@FXML
	private void vendorNameKeyReleased() {
		String name = txtCompany.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblVendorName.setText("");
			txtCompany.setStyle("-fx-focus-color: skyblue;");
			lblVendorName.setVisible(false);
			if (!validate.validateLength(name, 5, 25)) {
				
				txtCompany.setStyle("-fx-border-color: red;");
				lblVendorName.setVisible(true);
				lblVendorName.setText("Min. length 5 and Max. length 25");
				lblVendorName.setTextFill(Color.RED);
			}
		} else {
			txtCompany.setStyle("-fx-border-color: red;");
			lblVendorName.setVisible(true);
			lblVendorName.setText("Vendor Name is Mandatory");
			lblVendorName.setTextFill(Color.RED);
		}
	}

}