
package com.dpu.controller.vendor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.DeleteAPIClient;
import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.Login;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Vendor;
import com.dpu.model.VendorAdditionalContacts;
import com.dpu.model.VendorBillingLocation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class VendorController extends Application implements Initializable {

	static VendorAddController companyAddController;
	String filterBy = "Filter By ";
	String newText = filterBy;
	MouseEvent me;

	@FXML
	Pane root, headerPaneVendor;
	
	/*private void filterBySelectedValue() {
		
		tblCompany.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			  
			@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				// Check whether item is selected and set value of selected item
				// to Label
				String valu = "";
				Object val = "";
				if (tblCompany.getSelectionModel().getSelectedItem() != null) {
					TableViewSelectionModel selectionModel = tblCompany.getSelectionModel();
					ObservableList selectedCells = selectionModel.getSelectedCells();
					TablePosition tablePosition = (TablePosition) selectedCells.get(0);
					if(!newValue.equals(null))
							  val = tablePosition.getTableColumn().getCellData(newValue);
					if(val != null)
						valu = val + "";
					 
					int countt = 0;
					
					if (valu.length() > 0 && countt == 0) {
						countt =1;
						String value = valu.toLowerCase();
						ObservableList<CompanyModel> subentries = FXCollections.observableArrayList();

						long count = tblCompany.getColumns().stream().count();
						for (int i = 0; i < tblCompany.getItems().size(); i++) {
							for (int j = 0; j < count; j++) {
								String entry = "" + tblCompany.getColumns().get(j).getCellData(i);
								if (entry.toLowerCase().contains(value)) {
									subentries.add(tblCompany.getItems().get(i));
									break;
								}
							}
						}
						tblCompany.setItems(subentries);
					}

				}

			}

		});

		 
	}*/


	@FXML
	TableView<Vendor> tblVendor;
	@FXML
	private ImageView btnGoVendor;

	@FXML
	private TextField txtGoVendor;

	@FXML
	private void btnGoVendorAction() {
		String searchVendor = txtGoVendor.getText().trim();

		if (searchVendor != null && searchVendor.length() > 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API + "/" + searchVendor + "/search",
								null);
						fetchSearchVendors(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}

		if (searchVendor != null && searchVendor.length() == 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API,
								null);
						fetchSearchVendors(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	public List<Vendor> cList = new ArrayList<Vendor>();

	@FXML
	TableColumn<Vendor, String> unitNo, name, email, city, ps, phone, home, fax, afterHours;
	
	@FXML
	TextField txtSearchVendor;

	@FXML
	private void btnAddVendorAction() {
		VendorEditController.selectedTabValue = 0;
		VendorAddController.listOfBilling = new ArrayList<VendorBillingLocation>();
		VendorAddController.listOfAdditionalContact = new ArrayList<VendorAdditionalContacts>();
		VendorAddController.vendor = new Vendor();
		openAddVendorScreen();

	}

	public static Long vendorId = 0l;

	public static List<Status> statusList = null;
	
	@FXML
	private void btnEditVendorAction() {
		VendorAddController.listOfBilling = new ArrayList<VendorBillingLocation>();
//		VendorEditController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
		VendorAddController.vendor = new Vendor();

		VendorAddController.selectedTabValue = 0;

		Vendor selectedIndexVendor = cList.get(tblVendor.getSelectionModel().getSelectedIndex());
		vendorId = selectedIndexVendor.getVendorId();

		Vendor vendor = tblVendor.getSelectionModel().getSelectedItem();
		if (vendor != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API + "/" + vendor.getVendorId(),
								null);

						if (response != null && response.length() > 0) {
							Vendor c = mapper.readValue(response, Vendor.class);

							// ----------------------------------------------

							if (c.getBillingLocations() != null) {
								int billingSize = c.getBillingLocations().size();
								for (int i = 0; i < billingSize; i++) {

									VendorBillingLocation bcm = new VendorBillingLocation();
//									bcm.set(c.getVendorId());
									bcm.setVendorBillingLocationId(c.getBillingLocations().get(i).getVendorBillingLocationId());
									bcm.setAddress(c.getBillingLocations().get(i).getAddress());
									bcm.setCity(c.getBillingLocations().get(i).getCity());
									bcm.setName(c.getBillingLocations().get(i).getName());
									bcm.setContact(c.getBillingLocations().get(i).getContact());
									bcm.setFax(c.getBillingLocations().get(i).getFax());
									bcm.setPhone(c.getBillingLocations().get(i).getPhone());
									bcm.setZip(c.getBillingLocations().get(i).getZip());
									VendorAddController.listOfBilling.add(bcm);
								}
							}
							
							statusList = c.getStatusList();

							/**
							 * can uncomment this block if u want to get additionalcontacts without clicking on second tab in edit screen.
							 */
							/*if (c.getAdditionalContacts() != null) {
								int addtionalContactSize = c.getAdditionalContacts().size();
								for (int j = 0; j < addtionalContactSize; j++) {
									VendorAdditionalContacts additionalContact = new VendorAdditionalContacts();

//									additionalContact.setCompanyId(c.getVendorId());
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
									for(int i=0;i<c.getStatusList().size();i++) {
										if(c.getStatusList().get(i).getId().equals(c.getAdditionalContacts().get(j).getStatusId())) {
											additionalContact.setStatusName(c.getStatusList().get(i).getStatus());
											break;
										}
									}
									VendorAddController.listOfAdditionalContact.add(additionalContact);
								}
							}*/

							// -----------------------------------------------------
							VendorEditController vendorEditController = (VendorEditController) openEditVendorScreen();
							vendorEditController.initData(c);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	@FXML
	private void btnDeleteVendorAction() {
		// CompanyModel company =
		// tblCompany.getSelectionModel().getSelectedItem();
		Vendor vendor = cList.get(tblVendor.getSelectionModel().getSelectedIndex());
		if (vendor != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API + "/" + vendor.getVendorId(),
								null);
						fetchVendors(response);
						JOptionPane.showMessageDialog(null, "Vendor Deleted Successfully", "Info", 1);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	private void openAddVendorScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.VENDOR_BASE_PACKAGE + Iconstants.XML_VENDOR_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.ADD_NEW_VENDOR);
			stage.setScene(new Scene(root));
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Object openEditVendorScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.VENDOR_BASE_PACKAGE + Iconstants.XML_VENDOR_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.EDIT_VENDOR);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*private Object openAddDuplicateCompanyScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add Company");
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

	static boolean unitNumber = true;
	static boolean namee = true;
	static boolean emaill = true;
	static boolean cityy = true;
	static boolean pss = true;
	static boolean phoneNumber = true;
	static boolean homeNumber = true;
	static boolean faxNumber = true;
	static boolean afterHourss = true;

	/*@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(root, tblCompany);
		Login.setWidthForAll(headerPaneCompany, null);
		// textfield.setVisible(false);
		fetchCompanies();
		unitNo.setVisible(unitNumber);
		name.setVisible(namee);
		email.setVisible(emaill);
		city.setVisible(cityy);
		ps.setVisible(pss);
		phone.setVisible(phoneNumber);
		home.setVisible(homeNumber);
		fax.setVisible(faxNumber);
		afterHours.setVisible(afterHourss);
	}*/

	@Override
	public void start(Stage stage) {
	}

	/*
	 * public static void main(String[] args) { launch(args); }
	 */
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		unitNo = (TableColumn<Vendor, String>) tblVendor.getColumns().get(0);
		name = (TableColumn<Vendor, String>) tblVendor.getColumns().get(1);
		email = (TableColumn<Vendor, String>) tblVendor.getColumns().get(2);
		city = (TableColumn<Vendor, String>) tblVendor.getColumns().get(3);
		ps = (TableColumn<Vendor, String>) tblVendor.getColumns().get(4);
		phone = (TableColumn<Vendor, String>) tblVendor.getColumns().get(5);
		home = (TableColumn<Vendor, String>) tblVendor.getColumns().get(6);
		fax = (TableColumn<Vendor, String>) tblVendor.getColumns().get(7);
		afterHours = (TableColumn<Vendor, String>) tblVendor.getColumns().get(8);
	}

	
	
	public void fetchVendors() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ObservableList<Vendor> data = null;
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API, null);
					if (response != null && response.length() > 0) {
						Vendor c[] = mapper.readValue(response, Vendor[].class);
						cList = new ArrayList<Vendor>();
						for (Vendor ccl : c) {
							cList.add(ccl);
						}
						data = FXCollections.observableArrayList(cList);
					} else {
						data = FXCollections.observableArrayList(cList);
					}
					setColumnValues();
					tblVendor.setItems(data);

					tblVendor.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
		
		double width = Login.width;
		int noOfColumns = tblVendor.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tblVendor.getColumns().get(i).setMinWidth(width / noOfColumns);
		}
		txtSearchVendor.setLayoutX(width - (txtSearchVendor.getPrefWidth() + btnGoVendor.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGoVendor.setLayoutX(width - (btnGoVendor.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
	}
	
	public void fetchVendors(String response) {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				ObservableList<Vendor> data = null;
				try {
					ObjectMapper mapper = new ObjectMapper();
					if (response != null && response.length() > 0) {
						Success success = mapper.readValue(response, Success.class);
						cList = new ArrayList<Vendor>();
						List<Vendor> vendorList = (List<Vendor>) success.getResultList();
						String res = mapper.writeValueAsString(vendorList);
						Vendor c[] = mapper.readValue(res, Vendor[].class);
						for(Vendor ccl : c) {
							cList.add(ccl);
						}
						data = FXCollections.observableArrayList(cList);
					} else {
						data = FXCollections.observableArrayList(cList);
					}
					setColumnValues();
					tblVendor.setItems(data);

					tblVendor.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	/*private void fetchCompanies(String response) {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					ObservableList<CompanyModel> data = null;
					cList = new ArrayList<CompanyModel>();
					CompanyResponse compRes = mapper.readValue(response, CompanyResponse.class);

					if (response != null && response.length() > 0) {
						List<CompanyModel> c = compRes.getResultList();
						for (CompanyModel ccl : c) {
							cList.add(ccl);
						}
						data = FXCollections.observableArrayList(cList);
					} else {
						data = FXCollections.observableArrayList(cList);
					}
					setColumnValues();
					tblCompany.setItems(data);
					tblCompany.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Againnn.." + e, "Info", 1);
				}
			}
		});
	}*/

	private void fetchSearchVendors(String response) {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					ObservableList<Vendor> data = null;
					cList = new ArrayList<Vendor>();

					if (response != null && response.length() > 0) {
						Vendor c[] = mapper.readValue(response, Vendor[].class);

						for (Vendor ccl : c) {
							cList.add(ccl);
						}
						data = FXCollections.observableArrayList(cList);
					} else {
						data = FXCollections.observableArrayList(cList);
					}
					setColumnValues();
					tblVendor.setItems(data);
					tblVendor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Againnnnnnnnn.." + e, "Info", 1);
				}
			}
		});
	}

	private void setColumnValues() {

	unitNo.setCellValueFactory(
		new Callback<TableColumn.CellDataFeatures<Vendor, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Vendor, String> param) {
				return new SimpleStringProperty(param.getValue().getUnitNo() + "");
			}
		});
	name.setCellValueFactory(
		new Callback<TableColumn.CellDataFeatures<Vendor, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Vendor, String> param) {
				return new SimpleStringProperty(param.getValue().getName() + "");
			}
		});
	email.setCellValueFactory(
			new Callback<TableColumn.CellDataFeatures<Vendor, String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<Vendor, String> param) {
					return new SimpleStringProperty(param.getValue().getEmail() + "");
				}
			});
	city.setCellValueFactory(
			new Callback<TableColumn.CellDataFeatures<Vendor, String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<Vendor, String> param) {
					return new SimpleStringProperty(param.getValue().getCity() + "");
				}
			});
	ps.setCellValueFactory(
			new Callback<TableColumn.CellDataFeatures<Vendor, String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<Vendor, String> param) {
					return new SimpleStringProperty(param.getValue().getProvinceState() + "");
				}
			});
	phone.setCellValueFactory(
			new Callback<TableColumn.CellDataFeatures<Vendor, String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<Vendor, String> param) {
					return new SimpleStringProperty(param.getValue().getPhone() + "");
				}
			});
	home.setCellValueFactory(
			new Callback<TableColumn.CellDataFeatures<Vendor, String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<Vendor, String> param) {
					return new SimpleStringProperty(param.getValue().getCellular() + "");
				}
			});
	fax.setCellValueFactory(
			new Callback<TableColumn.CellDataFeatures<Vendor, String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<Vendor, String> param) {
					return new SimpleStringProperty(param.getValue().getFax() + "");
				}
			});
	afterHours.setCellValueFactory(
			new Callback<TableColumn.CellDataFeatures<Vendor, String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<Vendor, String> param) {
					return new SimpleStringProperty(param.getValue().getAfterHours() + "");
				}
			});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(root, tblVendor);
		Login.setWidthForAll(headerPaneVendor, null);
		fetchVendors();
	}

	// ADD MENU

	public int tblCompanyMenuCount = 0;

	// Create ContextMenu
	ContextMenu contextMenu = new ContextMenu();

	@FXML
	public void handleAddContMouseClick(MouseEvent arg0) {
		/*me = arg0;
		contextMenu = new ContextMenu();
		MenuItem add = new MenuItem("Add");
		menuAdd(add);
		MenuItem edit = new MenuItem("Edit");
		menuEdit(edit);
		MenuItem delete = new MenuItem("Delete");
		menuDelete(delete);
		MenuItem duplicate = new MenuItem("Duplicate");
		menuDuplicate(duplicate);
		MenuItem personalize = new MenuItem("Personalize");
		menuPersonalize(personalize);
		MenuItem filterBy = new MenuItem("Filter By");
		menuFilterBy(filterBy);
		MenuItem filterByExclude = new MenuItem("Filter By Exclude");
		menuFilterByExclude(filterByExclude);
		MenuItem clearAllFilters = new MenuItem("Clear All Filters");
		menuClearAllFilter(clearAllFilters);

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(add, edit, delete, duplicate, personalize, filterBy, filterByExclude,
				clearAllFilters);
		if (tblCompanyMenuCount == 0) {
			tblCompanyMenuCount++;
			// When user right-click on Table
			tblCompany.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
						if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.SECONDARY)) {
							contextMenu.show(tblCompany, mouseEvent.getScreenX(), mouseEvent.getScreenY());
							//textfield.setVisible(false);
							// contextMenu1 = new ContextMenu();
							// contextMenu1.hide();
						} else {
							contextMenu.hide();
						}
					}
				}

			});

		}

	}*/

	/*private void menuClearAllFilter(MenuItem item8) {
		item8.setStyle("-fx-padding: 0 10 0 10;");
		item8.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			}
		});
	}

	private void menuFilterByExclude(MenuItem item7) {
		item7.setStyle("-fx-padding: 0 10 0 10;");
		item7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			}
		});
	}

	private void menuFilterBy(MenuItem item6) {
		item6.setStyle("-fx-padding: 0 10 0 10;");

		item6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// textfield.setVisible(true);
				item6.setText(newText);
//				filterBySelectedValue();
			}
		});
	}

	private void menuPersonalize(MenuItem item5) {
		item5.setStyle("-fx-padding: 0 10 0 10;");
		item5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
							.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_PERSONALIZE_SCREEN));

					Parent root = (Parent) fxmlLoader.load();

					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.setTitle("PERSONALIZE");
					stage.setScene(new Scene(root));
					stage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void menuDuplicate(MenuItem item4) {
		item4.setStyle("-fx-padding: 0 10 0 10;");
		item4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				VendorEditController.listOfBilling = new ArrayList<BillingControllerModel>();
				VendorEditController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
				VendorEditController.company = new CompanyModel();

				VendorEditController.selectedTabValue = 0;

				CompanyModel companyy = cList.get(tblCompany.getSelectionModel().getSelectedIndex());
				companyId = companyy.getCompanyId();

				CompanyModel company = tblCompany.getSelectionModel().getSelectedItem();

				if (company != null) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							try {
								ObjectMapper mapper = new ObjectMapper();
								String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER
										+ Iconstants.URL_COMPANY_API + "/" + company.getCompanyId(), null);

								if (response != null && response.length() > 0) {
									CompanyModel c = mapper.readValue(response, CompanyModel.class);

									if (c.getBillingLocations() != null) {
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
											VendorEditController.listOfBilling.add(bcm);
										}
									}

									if (c.getAdditionalContacts() != null) {
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

											VendorEditController.listOfAdditionalContact.add(additionalContact);
										}
									}

									VendorAddController companyAddController = (VendorAddController) openAddDuplicateCompanyScreen();
									companyAddController.initData(c);
								}
							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
							}
						}
					});
				}

			}
		});
	}

	private void menuDelete(MenuItem item3) {
		item3.setStyle("-fx-padding: 0 10 0 10;");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				CompanyModel company = cList.get(tblCompany.getSelectionModel().getSelectedIndex());
				if (company != null) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							try {
								String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER
										+ Iconstants.URL_COMPANY_API + "/" + company.getCompanyId(), null);

								fetchCompanies(response);
								JOptionPane.showMessageDialog(null, "Company Deleted Successfully", "Info", 1);
							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
							}
						}
					});
				}
			}
		});
	}

	private void menuEdit(MenuItem item2) {
		item2.setStyle("-fx-padding: 0 10 0 10;");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				VendorEditController.listOfBilling = new ArrayList<BillingControllerModel>();
				VendorEditController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
				VendorEditController.company = new CompanyModel();

				VendorEditController.selectedTabValue = 0;

				CompanyModel companyy = cList.get(tblCompany.getSelectionModel().getSelectedIndex());
				companyId = companyy.getCompanyId();
				CompanyModel company = tblCompany.getSelectionModel().getSelectedItem();
				if (company != null) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							try {
								ObjectMapper mapper = new ObjectMapper();
								String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER
										+ Iconstants.URL_COMPANY_API + "/" + company.getCompanyId(), null);

								if (response != null && response.length() > 0) {
									CompanyModel c = mapper.readValue(response, CompanyModel.class);

									if (c.getBillingLocations() != null) {
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
											VendorEditController.listOfBilling.add(bcm);
										}
									}

									if (c.getAdditionalContacts() != null) {
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

											VendorEditController.listOfAdditionalContact.add(additionalContact);
										}
									}

									VendorEditController companyEditController = (VendorEditController) openEditCompanyScreen();
									companyEditController.initData(c);
								}
							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
							}
						}
					});
				}
			}
		});
	}

	private void menuAdd(MenuItem item1) {
		item1.setStyle("-fx-padding: 0 10 0 10;");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				VendorEditController.selectedTabValue = 0;
				VendorAddController.listOfBilling = new ArrayList<BillingControllerModel>();
				VendorAddController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
				VendorAddController.company = new Vendor();
				openAddCompanyScreen();

			}
		});
	}*/

	/*public void getLoadNewMenu(MouseEvent mouseEvent) {
		contextMenu = new ContextMenu();
		MenuItem add = new MenuItem("Add");
		menuAdd(add);
		MenuItem edit = new MenuItem("Edit");
		menuEdit(edit);
		MenuItem delete = new MenuItem("Delete");
		menuDelete(delete);
		MenuItem duplicate = new MenuItem("Duplicate");
		menuDuplicate(duplicate);
		MenuItem personalize = new MenuItem("Personalize");
		menuPersonalize(personalize);
		MenuItem filterBy = new MenuItem(newText);
		menuFilterBy(filterBy);
		MenuItem filterByExclude = new MenuItem("Filter By Exclude");
		menuFilterByExclude(filterByExclude);
		MenuItem clearAllFilters = new MenuItem("Clear All Filters");
		menuClearAllFilter(clearAllFilters);

		contextMenu.getItems().addAll(add, edit, delete, duplicate, personalize, filterBy, filterByExclude,
				clearAllFilters);

		if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
			if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.SECONDARY)) {
				//textfield.setVisible(false);
				contextMenu.show(tblCompany, mouseEvent.getScreenX(), mouseEvent.getScreenY());

			} else {
				contextMenu.hide();
			}
		}
		// contextMenu1 = new ContextMenu();
		 * */
	}

}