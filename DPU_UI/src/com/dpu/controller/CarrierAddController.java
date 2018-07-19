
package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.AddtionalCarrierContact;
import com.dpu.model.CarrierModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
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

/**
 * @author jagvir
 *
 */
public class CarrierAddController extends Application implements Initializable {

	@FXML
	private Pane addCarrierPane;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> additionalContact;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> position;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> phoneNo;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> faxNo;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> cellular;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> email;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> extension;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> pager;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> status;
	@FXML
	private TableColumn<AddtionalCarrierContact, String> function;
	@FXML
	private TableView<AddtionalCarrierContact> tableAdditionalContact;

	@FXML
	private Button btnSaveCarrier;

	@FXML
	private Button btnCancelCarrier;

	@FXML
	public TextField txtCarrier;

	@FXML
	public TextField txtAddress;

	@FXML
	public TextField txtUnit;

	@FXML
	public TextField txtCity;

	@FXML
	public TextField txtPS;

	@FXML
	public TextField txtPCZe;

	@FXML
	public TextField txtWebsite;

	@FXML
	public TextField txtContact;

	@FXML
	public TextField txtPosition;

	@FXML
	public TextField txtPhone;

	@FXML
	public TextField txtExt;

	@FXML
	public TextField txtFax;

	@FXML
	public TextField txtPrefix;

	@FXML
	public TextField txtTollFree;

	@FXML
	public TextField txtCell;

	@FXML
	public TextField txtEmail;

	@FXML
	private TabPane tabPane;

	public static int addEditIndex = -1;
	public static int add = 0;
	public static int addAddtionalContact = 0;
	public static int selectedTabValue = 0;
	public static AddtionalCarrierContact additionalContactModel = new AddtionalCarrierContact();
	public static ArrayList<AddtionalCarrierContact> listOfAdditionalContact = new ArrayList<AddtionalCarrierContact>();
	public static CarrierModel carrierModel = new CarrierModel();

	int additionalContactCountMenu = 0;

	private CarrierModel setCarrierValue() {
		List<com.dpu.request.AdditionalContact> additionalContacts = new ArrayList<com.dpu.request.AdditionalContact>();

		carrierModel.setAddress(txtAddress.getText());
		carrierModel.setCellular("cellular");
		carrierModel.setCity(txtCity.getText());
		carrierModel.setContact(txtContact.getText());
		carrierModel.setEmail(txtEmail.getText());
		carrierModel.setExt(txtExt.getText());
		carrierModel.setFax(txtFax.getText());
		carrierModel.setPhone(txtPhone.getText());
		carrierModel.setPosition(txtPosition.getText());
		carrierModel.setPrefix(txtPrefix.getText());
		carrierModel.setProvinceState(txtPS.getText());
		carrierModel.setTollfree(txtTollFree.getText());
		carrierModel.setUnitNo(txtUnit.getText());
		carrierModel.setWebsite(txtWebsite.getText());
		carrierModel.setName(txtCarrier.getText());
		// carrierModel.setZip("null");

		if (CarrierAddController.listOfAdditionalContact != null) {
			int sizeOfAdditionalContact = CarrierAddController.listOfAdditionalContact.size();
			for (int i = 0; i < sizeOfAdditionalContact; i++) {

				AddtionalCarrierContact additionalContactModel = CarrierAddController.listOfAdditionalContact.get(i);
				com.dpu.request.AdditionalContact additionalContact = new com.dpu.request.AdditionalContact();
				additionalContact.setCustomerName(additionalContactModel.getCustomerName());
				additionalContact.setPosition(additionalContactModel.getPosition());
				additionalContact.setPhone(additionalContactModel.getPhone());
				additionalContact.setExt(additionalContactModel.getExt());
				additionalContact.setFax(additionalContactModel.getFax());
				// set Pager in prefix.. chnage it
				additionalContact.setPrefix(additionalContactModel.getPrefix());
				additionalContact.setCellular(additionalContactModel.getCellular());
				// need to set Status here
				additionalContact.setStatus(0l);
				additionalContact.setEmail(additionalContactModel.getEmail());

				additionalContacts.add(additionalContact);
			}
		}

		carrierModel.setCarrierAdditionalContactModel(additionalContacts);
		return carrierModel;
	}

	@FXML
	void handleAddContMouseClick(MouseEvent event) {

		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addAddtionalContact = 1;
				carrierModel.setAddress(txtAddress.getText());
				carrierModel.setCellular(txtCell.getText());
				carrierModel.setCity(txtCity.getText());
				carrierModel.setContact(txtContact.getText());
				carrierModel.setEmail(txtEmail.getText());
				carrierModel.setExt(txtExt.getText());
				carrierModel.setFax(txtFax.getText());
				carrierModel.setPhone(txtPhone.getText());
				carrierModel.setPosition(txtPosition.getText());
				carrierModel.setPrefix(txtPrefix.getText());
				carrierModel.setProvinceState(txtPS.getText());
				carrierModel.setTollfree(txtTollFree.getText());
				carrierModel.setUnitNo(txtUnit.getText());
				carrierModel.setWebsite(txtWebsite.getText());
				carrierModel.setName(txtCarrier.getText());

				openAddAdditionalContactScreen();

				try {
					closeAddCarrierScreen(btnSaveCarrier);
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
				add = 0;
				selectedTabValue = 1;
				addAddtionalContact = 0;
				addEditIndex = tableAdditionalContact.getSelectionModel().getSelectedIndex();
				System.out.println("index: " + addEditIndex);
				additionalContactModel = tableAdditionalContact.getSelectionModel().getSelectedItem();
				openAddAdditionalContactScreen();
				closeAddCarrierScreen(btnSaveCarrier);

			}
		});

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 1;
				addEditIndex = tableAdditionalContact.getSelectionModel().getSelectedIndex();
				CarrierAddController.listOfAdditionalContact.remove(addEditIndex);
				addEditIndex = -1;

				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
							.getResource(Iconstants.CARRIER_BASE_PACKAGE + Iconstants.XML_CARRIER_ADD_SCREEN));
					Parent root = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.setTitle("Add New Carrier");
					stage.setScene(new Scene(root));
					stage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
				closeAddCarrierScreen(btnSaveCarrier);

			}
		});

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(item1, item2, item3);

		if (additionalContactCountMenu == 0) {
			additionalContactCountMenu++;
			// When user right-click on Table
			tableAdditionalContact.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
				@Override
				public void handle(ContextMenuEvent event) {
					contextMenu.show(tableAdditionalContact, event.getScreenX(), event.getScreenY());

				}

			});

		}

	}

	@FXML
	void initialize() {
		assert addCarrierPane != null : "fx:id=\"addCarierPane\" was not injected: check your FXML file 'AddCarrier.fxml'.";
		assert tableAdditionalContact != null : "fx:id=\"tableAdditionalContact\" was not injected: check your FXML file 'AddCarrier.fxml'.";
		// assert txtCarrier != null : "fx:id=\"txtCarier\" was not injected:
		// check
		// your FXML file 'AddCarrier.fxml'.";
		// assert txtAddress != null : "fx:id=\"txtAddress\" was not injected:
		// check
		// your FXML file 'AddCompany.fxml'.";
		// assert txtUnit != null : "fx:id=\"txtAddress\" was not injected:
		// check
		// your FXML file 'AddCompany.fxml'.";
		// assert txtCity != null : "fx:id=\"txtAddress\" was not injected:
		// check
		// your FXML file 'AddCompany.fxml'.";
		// assert txtPS != null : "fx:id=\"txtAddress\" was not injected: check
		// your
		// FXML file 'AddCompany.fxml'.";
		// assert txtWebsite != null : "fx:id=\"txtAddress\" was not injected:
		// check
		// your FXML file 'AddCompany.fxml'.";
		// assert txtContact != null : "fx:id=\"txtAddress\" was not injected:
		// check
		// your FXML file 'AddCompany.fxml'.";
		// assert txtPosition != null : "fx:id=\"txtAddress\" was not injected:
		// check your FXML file 'AddCompany.fxml'.";
		// assert txtPhone != null : "fx:id=\"txtAddress\" was not injected:
		// check
		// your FXML file 'AddCompany.fxml'.";
		// assert txtExt != null : "fx:id=\"txtAddress\" was not injected: check
		// your FXML file 'AddCompany.fxml'.";
		// assert txtFax != null : "fx:id=\"txtAddress\" was not injected: check
		// your FXML file 'AddCompany.fxml'.";
		// assert txtPrefix != null : "fx:id=\"txtAddress\" was not injected:
		// check
		// your FXML file 'AddCompany.fxml'.";
		// assert txtTollFree != null : "fx:id=\"txtAddress\" was not injected:
		// check your FXML file 'AddCompany.fxml'.";
		// assert txtCell != null : "fx:id=\"txtAddress\" was not injected:
		// check
		// your FXML file 'AddCompany.fxml'.";
		// assert txtEmail != null : "fx:id=\"txtAddress\" was not injected:
		// check
		// your FXML file 'AddCompany.fxml'.";
	}

	private void openAddAdditionalContactScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
					Iconstants.CARRIER_BASE_PACKAGE + Iconstants.XML_ADD_CARRIER_ADDITIONAL_CONTACT_SCREEN));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add Additional Carrier Contact");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeAddCarrierScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void addCarrier() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					CarrierModel carrierModel = setCarrierValue();
					// System.out.println("size:::"+carrierModel.getAdditionalContacts().size());

					String payload = mapper.writeValueAsString(carrierModel);

					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_CARRIER_API,
							null, payload);

					if (response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.carrierController.fetchCarriers();
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	private void setAdditionalContactColumnValues() {

		additionalContact.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getCustomerName() + "");
					}
				});
		position.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getPosition() + "");
					}
				});

		phoneNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone() + "");
					}
				});

		faxNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getFax() + "");
					}
				});

		cellular.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getCellular() + "");
					}
				});

		email.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getEmail() + "");
					}
				});
		extension.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getExt() + "");
					}
				});

		status.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getStatusName() + "");
					}
				});
		function.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getFunction() + "");
					}
				});

	}

	public void fetchAdditionalCarrierContacts() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (CarrierAddController.listOfAdditionalContact != null
							& !(CarrierAddController.listOfAdditionalContact.isEmpty())) {

						ObservableList<AddtionalCarrierContact> data = FXCollections
								.observableArrayList(CarrierAddController.listOfAdditionalContact);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fetchAdditionalCarrierContacts();
		txtAddress.setText(CarrierAddController.carrierModel.getAddress());
		txtCarrier.setText(CarrierAddController.carrierModel.getName());
		txtCell.setText(CarrierAddController.carrierModel.getCellular());
		txtCity.setText(CarrierAddController.carrierModel.getCity());
		txtContact.setText(CarrierAddController.carrierModel.getContact());
		txtEmail.setText(CarrierAddController.carrierModel.getEmail());
		txtExt.setText(CarrierAddController.carrierModel.getExt());
		txtFax.setText(CarrierAddController.carrierModel.getFax());
		txtPCZe.setText("null");
		txtPhone.setText(CarrierAddController.carrierModel.getPhone());
		txtPosition.setText(CarrierAddController.carrierModel.getPosition());
		txtPrefix.setText(CarrierAddController.carrierModel.getPrefix());
		txtPS.setText(CarrierAddController.carrierModel.getProvinceState());
		txtTollFree.setText(CarrierAddController.carrierModel.getTollfree());
		txtUnit.setText(CarrierAddController.carrierModel.getUnitNo());
		txtWebsite.setText(CarrierAddController.carrierModel.getWebsite());
		tabPane.getSelectionModel().select(1);

		// TODO Auto-generated method stub

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@FXML
	private void btnCancelCarrierAction() {
		closeAddCarrierScreen(btnCancelCarrier);
	}

	@FXML
	private void btnSaveCarrierAction() {
		boolean result = validateCarrierScreen();
		if (result) {
			addCarrier();
			closeAddCarrierScreen(btnSaveCarrier);
		}
	}

	// validation Applying

	@FXML
	Label carrierMsg;

	Validate validate = new Validate();

	private boolean validateCarrierScreen() {

		boolean result = true;
		String carrier = txtCarrier.getText();

		boolean blnCarrier = validate.validateEmptyness(carrier);
		if (!blnCarrier) {
			result = false;
			txtCarrier.setStyle("-fx-text-box-border: red;");
			carrierMsg.setVisible(true);
			carrierMsg.setText("Carrier Name is Mandatory");
			carrierMsg.setTextFill(Color.RED);
		}

		return result;
	}

	@FXML
	private void carrierKeyPressed() {

		String name = txtCarrier.getText();
		boolean result = validate.validateEmptyness(name);

		if (!result) {
			txtCarrier.setStyle("-fx-focus-color: red;");
			txtCarrier.requestFocus();
			carrierMsg.setVisible(true);
			carrierMsg.setText("Carrier Name is Mandatory");
			carrierMsg.setTextFill(Color.RED);
		} else {
			txtCarrier.setStyle("-fx-focus-color: skyblue;");
			carrierMsg.setVisible(false);
		}
	}

}
