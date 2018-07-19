package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

//import org.apache.commons.validator.EmailValidator;
import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.DeleteAPIClient;
import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.AdditionalContact;
import com.dpu.model.BillingControllerModel;
import com.dpu.model.Category;
import com.dpu.model.Division;
import com.dpu.model.Failed;
import com.dpu.model.Sale;
import com.dpu.model.Success;
import com.dpu.model.Type;
import com.dpu.request.BillingLocation;
import com.dpu.request.CompanyModel;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CompanyEditController extends Application implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Pane addCompanyPane;

	@FXML
	private TabPane tabPane;

	@FXML
	private TableColumn<AdditionalContact, String> additionalContact, position, phoneNo, faxNo, cellular, email,
			extension, pager, status, function;

	@FXML
	private TableColumn<BillingControllerModel, String> address;

	@FXML
	private Button btnSaveCompany, btnEdit;

	@FXML
	private Button btnCancelCompany;

	@FXML
	private TableColumn<BillingControllerModel, String> city;

	@FXML
	private TableColumn<BillingControllerModel, String> contact;

	@FXML
	private TableColumn<BillingControllerModel, String> fax;

	@FXML
	private TableColumn<BillingControllerModel, String> name;

	@FXML
	private TableColumn<BillingControllerModel, String> phone;

	@FXML
	public TableView<AdditionalContact> tableAdditionalContact;

	@FXML
	public TableView<BillingControllerModel> tableBillingLocations;

	public static TableView<BillingControllerModel> duplicateTableBillingLocations;

	public static TableView<AdditionalContact> duplicateTableAdditionalContact;

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
	public ComboBox<String> ddlCountry, ddlCategory, ddlDivision, ddlSale;

	@FXML
	public TextField txtWebsite;

	@FXML
	public TextField txtZip;

	@FXML
	public TableColumn<BillingControllerModel, String> zip;

	@FXML
	Button btnUpdateCompany;

	public static int editIndex = -1;
	public static int addBillingLocation = 0;
	public static int addAddtionalContact = 0;
	public static BillingControllerModel billingControllerModel = new BillingControllerModel();
	public static AdditionalContact additionalContactModel = new AdditionalContact();
	public static ArrayList<BillingControllerModel> listOfBilling = new ArrayList<BillingControllerModel>();
	public static ArrayList<AdditionalContact> listOfAdditionalContact = new ArrayList<AdditionalContact>();

	public static CompanyModel company = new CompanyModel();

	Long companyId = 0l;

	public static int selectedTabValue = 0;

	@FXML
	private void btnUpdateCompanyAction() {

		companyId = CompanyController.companyId;
		boolean result = validateAddCompanyScreen();
		if (result) {
			editCompany();
			closeEditCompanyScreen(btnSaveCompany);
		}

	}

	@FXML
	private void handleEditAction() {
		btnEdit.setDisable(true);
		disableFields(false);
	}

	private void disableFields(boolean v) {
		btnSaveCompany.setDisable(v);
		txtAddress.setDisable(v);
		txtCity.setDisable(v);
		txtCompany.setDisable(v);
		txtEmail.setDisable(v);
		txtProvince.setDisable(v);
		txtUnitNo.setDisable(v);
		txtWebsite.setDisable(v);
		txtZip.setDisable(v);
		ddlCategory.setDisable(v);
		ddlCountry.setDisable(v);
		ddlDivision.setDisable(v);
		ddlSale.setDisable(v);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (CompanyController.flag == 1) {
			disableFields(true);
		}
		if (CompanyController.flag == 2) {
			btnEdit.setVisible(false);
		}
		duplicateTableBillingLocations = tableBillingLocations;
		duplicateTableAdditionalContact = tableAdditionalContact;
		fetchBillingLocations();
		fetchAdditionalContacts();
		setColumnValues();
		setAdditionalContactColumnValues();
		tabPane.getSelectionModel().select(selectedTabValue);
		// txtPhone.addEventFilter(KeyEvent.KEY_TYPED,
		// Validate.numeric_Validation(10));
		// txtFax.addEventFilter(KeyEvent.KEY_TYPED,
		// Validate.numeric_Validation(10));
		// txtCompany.addEventFilter(KeyEvent.KEY_TYPED,
		// Validate.letter_Validation(10));
	}

	@Override
	public void start(Stage primaryStage) {

	}

	private CompanyModel setCompanyValue() {

		List<BillingLocation> billingLocations = new ArrayList<BillingLocation>();
		List<com.dpu.request.AdditionalContact> additionalContacts = new ArrayList<com.dpu.request.AdditionalContact>();

		// company.setCompanyId(companyId.toString());
		company.setName(txtCompany.getText());
		company.setAddress(txtAddress.getText());
		company.setUnitNo(txtUnitNo.getText());
		company.setProvinceState(txtProvince.getText());
		company.setZip(txtZip.getText());
		company.setEmail(txtEmail.getText());
		company.setWebsite(txtWebsite.getText());
		company.setDivisionId(divisionList.get(ddlDivision.getSelectionModel().getSelectedIndex()).getDivisionId());
		company.setCategoryId(categoryList.get(ddlCategory.getSelectionModel().getSelectedIndex()).getCategoryId());
		company.setSaleId(saleList.get(ddlSale.getSelectionModel().getSelectedIndex()).getSaleId());
		company.setCountryId(countryList.get(ddlCountry.getSelectionModel().getSelectedIndex()).getTypeId());
		company.setWebsite(txtWebsite.getText());
		company.setCity(txtCity.getText());

		// need to use for loop hereddlCountry.set

		if (listOfBilling != null) {
			int sizeOfBilling = listOfBilling.size();
			for (int i = 0; i < sizeOfBilling; i++) {
				BillingLocation billingLocation = new BillingLocation();
				BillingControllerModel billingModel = listOfBilling.get(i);
				if (billingModel.getBillingLocationId() != null)
					billingLocation.setBillingLocationId(billingModel.getBillingLocationId());
				billingLocation.setName(billingModel.getName());
				billingLocation.setAddress(billingModel.getAddress());
				billingLocation.setCity(billingModel.getCity());
				billingLocation.setPhone(billingModel.getPhone());
				billingLocation.setContact(billingModel.getContact());
				billingLocation.setZip(billingModel.getZip());
				billingLocation.setStatus(1);
				billingLocation.setFax(billingModel.getFax());

				billingLocations.add(billingLocation);
			}
		}

		company.setBillingLocations(billingLocations);

		// need to use for loop here
		if (listOfAdditionalContact != null) {
			int sizeOfAdditionalContact = listOfAdditionalContact.size();
			for (int i = 0; i < sizeOfAdditionalContact; i++) {

				AdditionalContact additionalContactModel = listOfAdditionalContact.get(i);
				com.dpu.request.AdditionalContact additionalContact = new com.dpu.request.AdditionalContact();

				if (additionalContactModel.getAdditionalContactId() != null)
					additionalContact.setAdditionalContactId(additionalContactModel.getAdditionalContactId());
				additionalContact.setCustomerName(additionalContactModel.getCustomerName());
				additionalContact.setPosition(additionalContactModel.getPosition());
				additionalContact.setPhone(additionalContactModel.getPhone());
				additionalContact.setExt(additionalContactModel.getExt());
				additionalContact.setFax(additionalContactModel.getFax());
				additionalContact.setPrefix(additionalContactModel.getPrefix());
				additionalContact.setCellular(additionalContactModel.getCellular());
//				if (additionalContactModel.getStatusId().equalsIgnoreCase("Active"))
					additionalContact.setStatus(additionalContactModel.getStatusId());
//				else
//					additionalContact.setStatus(1l);

//				if (additionalContactModel.getFunction().equalsIgnoreCase("primary"))
					additionalContact.setFunctionId(additionalContactModel.getFunctionId());
//				else
//					additionalContact.setFunctionId(84l);
				additionalContact.setEmail(additionalContactModel.getEmail());
				additionalContacts.add(additionalContact);
			}
		}
		company.setAdditionalContacts(additionalContacts);

		return company;
	}

	List<Category> categoryList = null;
	List<Division> divisionList = null;
	List<Sale> saleList = null;
	List<Type> countryList = null;

	public void initData(CompanyModel companyModel) {
//		System.out.println("initData:::");
		companyId = companyModel.getCompanyId();
		txtCompany.setText(companyModel.getName());
		txtAddress.setText(companyModel.getAddress());
		txtUnitNo.setText(companyModel.getUnitNo());
		txtCity.setText(companyModel.getCity());
		txtProvince.setText(companyModel.getProvinceState());
		txtZip.setText(companyModel.getZip());
		txtEmail.setText(companyModel.getEmail());
		txtWebsite.setText(companyModel.getWebsite());
		divisionList = companyModel.getDivisionList();
		saleList = companyModel.getSaleList();
		categoryList = companyModel.getCategoryList();
		countryList = companyModel.getCountryList();

		for (int i = 0; i < companyModel.getDivisionList().size(); i++) {
			Division division = companyModel.getDivisionList().get(i);
			ddlDivision.getItems().add(division.getDivisionName());
			if (division.getDivisionId() == companyModel.getDivisionId()) {
				ddlDivision.getSelectionModel().select(i);
			}
		}

		for (int i = 0; i < companyModel.getSaleList().size(); i++) {
			Sale sale = companyModel.getSaleList().get(i);
			ddlSale.getItems().add(sale.getName());
			if (sale.getSaleId() == companyModel.getSaleId()) {
				ddlSale.getSelectionModel().select(i);
			}
		}

		for (int i = 0; i < companyModel.getCategoryList().size(); i++) {

			Category category = companyModel.getCategoryList().get(i);
			if (category != null) {
				if (category.getName() != null) {
					ddlCategory.getItems().add(category.getName());
					if (category.getCategoryId() == companyModel.getCategoryId()) {
						ddlCategory.getSelectionModel().select(i);
					}
				}
			}
		}

		for (int i = 0; i < companyModel.getCountryList().size(); i++) {

			Type country = companyModel.getCountryList().get(i);
			if (country != null) {
				if (country.getTypeName() != null) {
					ddlCountry.getItems().add(country.getTypeName());

					if (country.getTypeId() == companyModel.getCountryId()) {
						ddlCountry.getSelectionModel().select(i);
					}
				}
			}
		}
		// if (CompanyController.flag == 1) {
		// disableFields(true);
		// }
		// if (CompanyController.flag == 2) {
		// btnEdit.setVisible(false);
		// }
	}

	@FXML
	private void btnCancelCompanyAction() {
		listOfBilling = new ArrayList<BillingControllerModel>();
		listOfAdditionalContact = new ArrayList<AdditionalContact>();
		company = new CompanyModel();
		closeEditCompanyScreen(btnCancelCompany);
	}

	@FXML
	private void btnSaveCompanyAction() {

		companyId = CompanyController.companyId;
		editCompany();
		closeEditCompanyScreen(btnSaveCompany);
	}

	int additionalContactMenuCount = 0;

	@FXML
	void handleAddContMouseClick(MouseEvent event) {

		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				// setValuesToCmpanyTextField();
				addAddtionalContact = 1;
				selectedTabValue = 1;
				openAddAdditionalContactScreen();

			}
		});
		MenuItem item2 = new MenuItem("EDIT");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// setValuesToCmpanyTextField();
				selectedTabValue = 1;
				addAddtionalContact = 0;
				editIndex = tableAdditionalContact.getSelectionModel().getSelectedIndex();
				additionalContactModel = listOfAdditionalContact.get(tableAdditionalContact.getSelectionModel().getSelectedIndex());

				if (additionalContactModel.getAdditionalContactId() != null)
					additionalContactIdPri = additionalContactModel.getAdditionalContactId();
				openAddAdditionalContactScreen();
				// closeEditCompanyScreen(btnSaveCompany);

			}
		});

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 1;
				// setValuesToCmpanyTextField();
				editIndex = tableAdditionalContact.getSelectionModel().getSelectedIndex();

				Long additionalontactId = listOfAdditionalContact.get(editIndex).getAdditionalContactId();
				Long companyId = listOfAdditionalContact.get(editIndex).getCompanyId();

				if (additionalontactId == null) {
					listOfAdditionalContact.remove(editIndex);
					JOptionPane.showMessageDialog(null, "Additional Contact Deleted SuccessFully.", "Info", 1);

				} else {

					// hit api to delete Additional Conatct
					try {
						String response = DeleteAPIClient
								.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_DELETE_BILLING_LOCATION_API + "/"
										+ companyId + "/additionalcontacts/" + additionalontactId, null);
						listOfAdditionalContact.remove(editIndex);

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

				editIndex = -1;
				fetchAdditionalContactsUsingDuplicate();
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

		}

	}

	private void openAddAdditionalContactScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_EDIT_ADDITIONAL_CONTACT_SCREEN));
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

	private void closeEditCompanyScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void editCompany() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					ObjectMapper mapper = new ObjectMapper();
					CompanyModel company = setCompanyValue();
					String payload = mapper.writeValueAsString(company);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/" + companyId, null, payload);

					mapper.writeValueAsString(response);
					if (response != null) {
						Success success = mapper.readValue(response, Success.class);
						String message = success.getMessage();
						JOptionPane.showMessageDialog(null, message, "Info", 1);
					} else {
						JOptionPane.showMessageDialog(null, "Failed to Updated Company.", "Info", 1);
					}

					closeEditCompanyScreen(btnSaveCompany);
					MainScreen.companyController.fetchCompanies();
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

	@FXML
	public void handleMouseClick(MouseEvent arg0) {

		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				addBillingLocation = 1;
				selectedTabValue = 0;
				// setValuesToCmpanyTextField();
				openAddBillingLocationScreen();

			}
		});
		MenuItem item2 = new MenuItem("EDIT");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				selectedTabValue = 0;
				// setValuesToCmpanyTextField();
				addBillingLocation = 0;
				editIndex = tableBillingLocations.getSelectionModel().getSelectedIndex();
				billingControllerModel = listOfBilling.get(tableBillingLocations.getSelectionModel().getSelectedIndex());

				if (billingControllerModel.getBillingLocationId() != null)
					billingLocationIdPri = billingControllerModel.getBillingLocationId();

				openAddBillingLocationScreen();
				// closeEditCompanyScreen(btnSaveCompany);

			}
		});

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 0;

				editIndex = tableBillingLocations.getSelectionModel().getSelectedIndex();

				Long billingId = listOfBilling.get(editIndex).getBillingLocationId();
				Long companyId = listOfBilling.get(editIndex).getCompanyId();

				if (billingId == null) {
					JOptionPane.showMessageDialog(null, "Billing Location Deleted SuccessFully.", "Info", 1);
					listOfBilling.remove(editIndex);
				} else {

					try {
						String response = DeleteAPIClient
								.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_DELETE_BILLING_LOCATION_API + "/"
										+ companyId + "/billinglocations/" + billingId, null);
						listOfBilling.remove(editIndex);

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

				editIndex = -1;

				// setValuesToCmpanyTextField();

				fetchBillingLocationsUsingDuplicate();

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
		}
	}

	private void openAddBillingLocationScreen() {

		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_EDIT_BILLING_LOCATION_SCREEN));
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

	// new added
	public static void fetchBillingLocationsUsingDuplicate() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (listOfBilling != null & !(listOfBilling.isEmpty())) {
						ObservableList<BillingControllerModel> data = FXCollections.observableArrayList(listOfBilling);
						// setColumnValues();
						duplicateTableBillingLocations.setItems(data);
						duplicateTableBillingLocations.setVisible(true);
					} else {
						// listOfBilling = new ArrayList<>();
						ObservableList<BillingControllerModel> data = FXCollections.observableArrayList(listOfBilling);
						duplicateTableBillingLocations.setItems(data);
						duplicateTableBillingLocations.setVisible(true);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}

		});
	}

	public void fetchBillingLocations() {
		// fetchColumns();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (listOfBilling != null & !(listOfBilling.isEmpty())) {
						ObservableList<BillingControllerModel> data = FXCollections.observableArrayList(listOfBilling);
						setColumnValues();
						tableBillingLocations.setItems(data);
						tableBillingLocations.setVisible(true);
					}
				} catch (Exception e) {
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
						ObservableList<AdditionalContact> data = FXCollections
								.observableArrayList(listOfAdditionalContact);
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
						ObservableList<AdditionalContact> data = FXCollections
								.observableArrayList(listOfAdditionalContact);
						// setAdditionalContactColumnValues();
						duplicateTableAdditionalContact.setItems(data);
						duplicateTableAdditionalContact.setVisible(true);
					} else {
						// listOfAdditionalContact = new
						// ArrayList<AdditionalContact>();
						ObservableList<AdditionalContact> data = FXCollections
								.observableArrayList(listOfAdditionalContact);
						// setAdditionalContactColumnValues();
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
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getCustomerName() + "");
					}
				});
		position.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getPosition() + "");
					}
				});
		phoneNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone() + "");
					}
				});
		faxNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getFax() + "");
					}
				});
		cellular.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getCellular() + "");
					}
				});
		email.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getEmail() + "");
					}
				});
		extension.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getExt() + "");
					}
				});
		pager.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getPrefix() + "");
					}
				});
		status.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getStatusId() + "");
					}
				});

		function.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getFunction() + "");
					}
				});
	}

	private void setColumnValues() {

		name.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getName() + "");
					}
				});
		address.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getAddress() + "");
					}
				});
		city.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getCity() + "");
					}
				});
		phone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone() + "");
					}
				});
		contact.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getContact() + "");
					}
				});
		zip.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getZip() + "");
					}
				});
		fax.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getFax() + "");
					}
				});

	}

	// validation applying
	@FXML
	Label companyMsg, countryMsg, lblZip, lblProvince;

	Validate validate = new Validate();

	@FXML
	private void companyNameKeyPressed() {

		String name = txtCompany.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			companyMsg.setVisible(false);
			txtCompany.setStyle("-fx-focus-color: skyBlue;");
		} else {
			txtCompany.setStyle("-fx-focus-color: red;");
			txtCompany.requestFocus();
			companyMsg.setVisible(true);
		}
	}

	@FXML
	private void ddlCountryAction() {

		String name = ddlCountry.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			countryMsg.setVisible(false);
			ddlCountry.setStyle("-fx-focus-color: skyBlue;");
			if (ddlCountry.getSelectionModel().getSelectedItem().equals("usa")) {
				lblZip.setText("Zip");
				lblProvince.setText("State");
			} else if (ddlCountry.getSelectionModel().getSelectedItem().equals("canada")) {
				lblZip.setText("Postal");
				lblProvince.setText("Province");
			}
		} else {
			ddlCountry.setStyle("-fx-focus-color: red;");
			ddlCountry.requestFocus();
			countryMsg.setVisible(true);
		}
	}

	private boolean validateAddCompanyScreen() {
		boolean result = true;
		String customerName = txtCompany.getText();
		String country = ddlCountry.getSelectionModel().getSelectedItem();

		boolean blnName = validate.validateEmptyness(customerName);
		if (!blnName) {
			result = false;
			txtCompany.setStyle("-fx-text-box-border: red;");
			companyMsg.setText("Company Name is Mandatory");
			companyMsg.setTextFill(Color.RED);

		}

		boolean blnCountry = validate.validateEmptyness(country);
		if (!blnCountry) {
			result = false;
			ddlCountry.setStyle("-fx-border-color: red;");
			countryMsg.setVisible(true);
			countryMsg.setText("Country is Mandatory");
			countryMsg.setTextFill(Color.RED);

		}
		return result;

	}

}