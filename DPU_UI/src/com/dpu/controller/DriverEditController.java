package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Category;
import com.dpu.model.Division;
import com.dpu.model.Driver;
import com.dpu.model.Failed;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Terminal;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DriverEditController extends Application implements Initializable {

	@FXML
	Button btnUpdateDriver,btnEdit;

	@FXML
	Button btnCancelDriver;
	
	Long driverId = 0l;

	@FXML
	TextField txtCode, txtFirstName, txtLastName, txtAddress, txtUnit, txtPostal, txtHome, txtCity, txtFaxNo,
			txtCellular, txtPager, txtDivision, txtEmail, txtProvince;

	@FXML
	ComboBox<String> ddlTerminal, ddlCategory, ddlRole, ddlStatus, ddlDriverClass, ddlDivision;


	@FXML
	private void btnCancelDriverAction() {
		closeEditDriverScreen(btnCancelDriver);
	}
	
	@FXML
	private void btnUpdateDriverAction() {
		boolean result = validateAddDriverScreen();
		if (result) {
			editDriver();
			closeEditDriverScreen(btnUpdateDriver);
		}

	}

	private void closeEditDriverScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void editDriver() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Driver driver = setDriverValue();
					String payload = mapper.writeValueAsString(driver);
					System.out.println("PP:: " + payload);
					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API + "/" + driverId, null, payload);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Driver> driverList = (List<Driver>) success.getResultList();
						String res = mapper.writeValueAsString(driverList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.driverController.fillDriver(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}
					// MainScreen.driverController.fillDriver(response);

					/*
					 * if(response != null && response.contains("message")) {
					 * Success success = mapper.readValue(response,
					 * Success.class); JOptionPane.showMessageDialog(null,
					 * success.getMessage() , "Info", 1); } else { Failed failed
					 * = mapper.readValue(response, Failed.class);
					 * JOptionPane.showMessageDialog(null, failed.getMessage(),
					 * "Info", 1); }
					 */
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}
	@FXML
	public void handleEditAction() {
		btnEdit.setDisable(true);
		disableFields(false);
	}

	private void disableFields(boolean v) {
		btnUpdateDriver.setDisable(v);
		txtAddress.setDisable(v);
		txtCellular.setDisable(v);
		txtCity.setDisable(v);
		txtCode.setDisable(v);
		txtDivision.setDisable(v);
		txtEmail.setDisable(v);
		txtFaxNo.setDisable(v);
		txtFirstName.setDisable(v);
		txtHome.setDisable(v);
		txtLastName.setDisable(v);
		txtPager.setDisable(v);
		txtPostal.setDisable(v);
		txtProvince.setDisable(v);
		txtUnit.setDisable(v);
		ddlCategory.setDisable(v);
		ddlDivision.setDisable(v);
		ddlDriverClass.setDisable(v);
		ddlRole.setDisable(v);
		ddlStatus.setDisable(v);
		ddlTerminal.setDisable(v);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (DriverController.flag == 1) {
			disableFields(true);
		}
		if (DriverController.flag == 2) {
			btnEdit.setVisible(false);
		}
	}

	@Override
	public void start(Stage primaryStage) {
	}

	private Driver setDriverValue() {
		Driver driver = new Driver();
		driver.setDriverCode(txtCode.getText());
		driver.setFirstName(txtFirstName.getText());
		driver.setLastName(txtLastName.getText());
		driver.setAddress(txtAddress.getText());
		driver.setUnit(txtUnit.getText());
		driver.setCity(txtCity.getText());
		driver.setPostalCode(txtPostal.getText());
		driver.setEmail(txtEmail.getText());
		driver.setHome(txtHome.getText());
		driver.setFaxNo(txtFaxNo.getText());
		driver.setCellular(txtCellular.getText());
		driver.setPager(txtPager.getText());
		driver.setPvs(txtProvince.getText());
		driver.setDivisionId(divisionList.get(ddlDivision.getSelectionModel().getSelectedIndex()).getDivisionId());
		driver.setTerminalId(terminalList.get(ddlTerminal.getSelectionModel().getSelectedIndex()).getTerminalId());
		driver.setCategoryId(categoryList.get(ddlCategory.getSelectionModel().getSelectedIndex()).getCategoryId());
		driver.setRoleId(roleList.get(ddlRole.getSelectionModel().getSelectedIndex()).getTypeId());
		driver.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		driver.setDriverClassId(classList.get(ddlDriverClass.getSelectionModel().getSelectedIndex()).getTypeId());
		return driver;
	}

	List<Terminal> terminalList = null;

	List<Category> categoryList = null;

	List<Type> roleList = null, classList = null;

	List<Status> statusList = null;

	List<Division> divisionList = null;

	public void initData(Driver driver) {
		driverId = driver.getDriverId();
		txtCode.setText(driver.getDriverCode());
		txtFirstName.setText(driver.getFirstName());
		txtLastName.setText(driver.getLastName());
		txtAddress.setText(driver.getAddress());
		txtUnit.setText(driver.getUnit());
		txtCity.setText(driver.getCity());
		txtPostal.setText(driver.getPostalCode());
		txtEmail.setText(driver.getCity());
		txtHome.setText(driver.getHome());
		txtFaxNo.setText(driver.getFaxNo());
		txtProvince.setText(driver.getPvs());
		txtCellular.setText(driver.getCellular());
		txtPager.setText(driver.getPager());
		divisionList = driver.getDivisionList();
		terminalList = driver.getTerminalList();
		categoryList = driver.getCategoryList();
		roleList = driver.getRoleList();
		statusList = driver.getStatusList();
		classList = driver.getDriverClassList();
		for (int i = 0; i < driver.getDivisionList().size(); i++) {
			Division division = driver.getDivisionList().get(i);
			ddlDivision.getItems().add(division.getDivisionName());
			if (division.getDivisionId() == driver.getDivisionId()) {
				ddlDivision.getSelectionModel().select(i);
			}
		}
		for (int i = 0; i < driver.getTerminalList().size(); i++) {
			Terminal terminal = driver.getTerminalList().get(i);
			ddlTerminal.getItems().add(terminal.getTerminalName());
			if (terminal.getTerminalId() == driver.getTerminalId()) {
				ddlTerminal.getSelectionModel().select(i);
			}
		}
		for (int i = 0; i < driver.getCategoryList().size(); i++) {
			Category category = driver.getCategoryList().get(i);
			ddlCategory.getItems().add(category.getName());
			if (category.getCategoryId() == driver.getCategoryId()) {
				ddlCategory.getSelectionModel().select(i);
			}
		}
		for (int i = 0; i < driver.getRoleList().size(); i++) {
			Type role = driver.getRoleList().get(i);
			ddlRole.getItems().add(role.getTypeName());
			if (role.getTypeId() == driver.getRoleId()) {
				ddlRole.getSelectionModel().select(i);
			}
		}
		for (int i = 0; i < driver.getDriverClassList().size(); i++) {
			Type driverClass = driver.getDriverClassList().get(i);
			ddlDriverClass.getItems().add(driverClass.getTypeName());
			if (driverClass.getTypeId() == driver.getDriverClassId()) {
				ddlDriverClass.getSelectionModel().select(i);
			}
		}
		for (int i = 0; i < driver.getStatusList().size(); i++) {
			Status status = driver.getStatusList().get(i);
			ddlStatus.getItems().add(status.getStatus());
			if (status.getId() == driver.getStatusId()) {
				ddlStatus.getSelectionModel().select(i);
			}
		}
	}

	@FXML
	Label lblCode, lblFirstName, lblLastName, lblAddress, lblUnit, lblCity, lblZip, lblEmail, lblHome, lblCellular,
			lblDivision, lblTerminal, lblCategory, lblStatus, lblFax, lblPager, lblRole, lblDriverClass, lblProvince;

	Validate validate = new Validate();

	private boolean validateAddDriverScreen() {

		boolean result = true;
		String driverCode = txtCode.getText();
		String firstName = txtFirstName.getText();
		String lastName = txtLastName.getText();
		String address = txtAddress.getText();
		String unit = txtUnit.getText();
		String city = txtCity.getText();
		String postal = txtPostal.getText();
		String email = txtEmail.getText();
		String province = txtProvince.getText();
		String home = txtHome.getText();
		String cellular = txtCellular.getText();
		String faxNo = txtFaxNo.getText();
		String pager = txtPager.getText();

		String terminal = ddlTerminal.getSelectionModel().getSelectedItem();
		String category = ddlCategory.getSelectionModel().getSelectedItem();
		String role = ddlRole.getSelectionModel().getSelectedItem();
		String status = ddlStatus.getSelectionModel().getSelectedItem();
		String driverClass = ddlDriverClass.getSelectionModel().getSelectedItem();
		String division = ddlDivision.getSelectionModel().getSelectedItem();

		boolean blnDriverCode = validate.validateEmptyness(driverCode);
		if (!blnDriverCode) {
			result = false;
			txtCode.setStyle("-fx-text-box-border: red;");
			lblCode.setVisible(true);
			// lblCode.setText("Company Name is Mandatory");
			lblCode.setTextFill(Color.RED);

		}

		boolean blnFirstName = validate.validateEmptyness(firstName);
		if (!blnFirstName) {
			result = false;
			txtFirstName.setStyle("-fx-text-box-border: red;");
			lblFirstName.setVisible(true);
			// lblFirstName.setText("Address is Mandatory");
			lblFirstName.setTextFill(Color.RED);
		}

		boolean blnLastName = validate.validateEmptyness(lastName);
		if (!blnLastName) {
			result = false;
			txtLastName.setStyle("-fx-text-box-border: red;");
			lblLastName.setVisible(true);
			// lblLastName.setText("Phone Number is Mandatory");
			lblLastName.setTextFill(Color.RED);
		}

		boolean blnAddress = validate.validateEmptyness(address);
		if (!blnAddress) {
			result = false;
			txtAddress.setStyle("-fx-text-box-border: red;");
			lblAddress.setVisible(true);
			// lblAddress.setText("Fax Number is Mandatory");
			lblAddress.setTextFill(Color.RED);
		}

		boolean blnUnit = validate.validateEmptyness(unit);
		if (!blnUnit) {
			result = false;
			txtUnit.setStyle("-fx-text-box-border: red;");
			lblUnit.setVisible(true);
			// lblUnit.setText("Fax Number is Mandatory");
			lblUnit.setTextFill(Color.RED);
		}

		boolean blnCity = validate.validateEmptyness(city);
		if (!blnCity) {
			result = false;
			txtCity.setStyle("-fx-text-box-border: red;");
			lblCity.setVisible(true);
			// lblCity.setText("Fax Number is Mandatory");
			lblCity.setTextFill(Color.RED);
		}

		boolean blnPostal = validate.validateEmptyness(postal);
		if (!blnPostal) {
			result = false;
			txtPostal.setStyle("-fx-text-box-border: red;");
			lblZip.setVisible(true);
			// lblZip.setText("Fax Number is Mandatory");
			lblZip.setTextFill(Color.RED);
		}

		boolean blnEmail = validate.validateEmptyness(email);
		if (!blnEmail) {
			result = false;
			txtEmail.setStyle("-fx-text-box-border: red;");
			lblEmail.setVisible(true);
			// lblEmail.setText("Fax Number is Mandatory");
			lblEmail.setTextFill(Color.RED);
		}

		boolean blnProvince = validate.validateEmptyness(province);
		if (!blnProvince) {
			result = false;
			txtProvince.setStyle("-fx-text-box-border: red;");
			lblProvince.setVisible(true);
			// lblProvince.setText("Fax Number is Mandatory");
			lblProvince.setTextFill(Color.RED);
		}

		boolean blnHome = validate.validateEmptyness(home);
		if (!blnHome) {
			result = false;
			txtHome.setStyle("-fx-text-box-border: red;");
			lblHome.setVisible(true);
			// lblHome.setText("Fax Number is Mandatory");
			lblHome.setTextFill(Color.RED);
		}
		boolean blnCellular = validate.validateEmptyness(cellular);
		if (!blnCellular) {
			result = false;
			txtCellular.setStyle("-fx-text-box-border: red;");
			lblCellular.setVisible(true);
			// lblCellular.setText("Fax Number is Mandatory");
			lblCellular.setTextFill(Color.RED);
		}
		boolean blnFax = validate.validateEmptyness(faxNo);
		if (!blnFax) {
			result = false;
			txtFaxNo.setStyle("-fx-text-box-border: red;");
			lblFax.setVisible(true);
			// lblFax.setText("Fax Number is Mandatory");
			lblFax.setTextFill(Color.RED);
		}

		boolean blnPager = validate.validateEmptyness(pager);
		if (!blnPager) {
			result = false;
			txtPager.setStyle("-fx-text-box-border: red;");
			lblPager.setVisible(true);
			// lblPager.setText("Fax Number is Mandatory");
			lblPager.setTextFill(Color.RED);
		}

		boolean blnTerminal = validate.validateEmptyness(terminal);
		if (!blnTerminal) {
			result = false;
			ddlTerminal.setStyle("-fx-text-box-border: red;");
			lblTerminal.setVisible(true);
			// lblTerminal.setText("Fax Number is Mandatory");
			lblTerminal.setTextFill(Color.RED);
		}

		boolean blnCategory = validate.validateEmptyness(category);
		if (!blnCategory) {
			result = false;
			ddlCategory.setStyle("-fx-text-box-border: red;");
			lblCategory.setVisible(true);
			// lblCategory.setText("Fax Number is Mandatory");
			lblCategory.setTextFill(Color.RED);
		}

		boolean blnRole = validate.validateEmptyness(role);
		if (!blnRole) {
			result = false;
			ddlRole.setStyle("-fx-text-box-border: red;");
			lblRole.setVisible(true);
			// lblRole.setText("Fax Number is Mandatory");
			lblRole.setTextFill(Color.RED);
		}

		boolean blnStatus = validate.validateEmptyness(status);
		if (!blnStatus) {
			result = false;
			ddlStatus.setStyle("-fx-text-box-border: red;");
			lblStatus.setVisible(true);
			// lblStatus.setText("Fax Number is Mandatory");
			lblStatus.setTextFill(Color.RED);
		}

		boolean blnDriverClass = validate.validateEmptyness(driverClass);
		if (!blnDriverClass) {
			result = false;
			ddlDriverClass.setStyle("-fx-text-box-border: red;");
			lblDriverClass.setVisible(true);
			// lblDriverClass.setText("Fax Number is Mandatory");
			lblDriverClass.setTextFill(Color.RED);
		}

		boolean blnDivision = validate.validateEmptyness(division);
		if (!blnDivision) {
			result = false;
			ddlDivision.setStyle("-fx-text-box-border: red;");
			lblDivision.setVisible(true);
			// lblDivision.setText("Fax Number is Mandatory");
			lblDivision.setTextFill(Color.RED);
		}

		return result;
	}

	@FXML
	private void driverCodeKeyPressed() {

		String name = txtCode.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCode.setTextFill(Color.BLACK);
			txtCode.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtCode.setStyle("-fx-focus-color: red;");
			txtCode.requestFocus();
			lblCode.setVisible(true);
			// lblCode.setText("Company Name is Mandatory");
			lblCode.setTextFill(Color.RED);
		}
	}

	@FXML
	private void driverProvinceKeyPressed() {

		String name = txtProvince.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblProvince.setTextFill(Color.BLACK);
			txtProvince.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtProvince.setStyle("-fx-focus-color: red;");
			txtProvince.requestFocus();
			lblProvince.setVisible(true);
			// lblProvince.setText("Company Name is Mandatory");
			lblProvince.setTextFill(Color.RED);
		}
	}

	@FXML
	private void driverFirstNameKeyPressed() {

		String name = txtFirstName.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblFirstName.setTextFill(Color.BLACK);
			txtFirstName.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtFirstName.setStyle("-fx-focus-color: red;");
			txtFirstName.requestFocus();
			lblFirstName.setVisible(true);
			// lblFirstName.setText("Company Name is Mandatory");
			lblFirstName.setTextFill(Color.RED);
		}
	}

	@FXML
	private void driverLastNameKeyPressed() {

		String name = txtLastName.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblLastName.setTextFill(Color.BLACK);
			txtLastName.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtLastName.setStyle("-fx-focus-color: red;");
			txtLastName.requestFocus();
			lblLastName.setVisible(true);
			// lblLastName.setText("Company Name is Mandatory");
			lblLastName.setTextFill(Color.RED);
		}
	}

	@FXML
	private void driverAddressKeyPressed() {

		String name = txtAddress.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblAddress.setTextFill(Color.BLACK);
			txtAddress.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtAddress.setStyle("-fx-focus-color: red;");
			txtAddress.requestFocus();
			lblAddress.setVisible(true);
			// lblAddress.setText("Company Name is Mandatory");
			lblAddress.setTextFill(Color.RED);
		}
	}

	@FXML
	private void driverUnitKeyPressed() {

		String name = txtUnit.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblUnit.setTextFill(Color.BLACK);
			txtUnit.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtUnit.setStyle("-fx-focus-color: red;");
			txtUnit.requestFocus();
			lblUnit.setVisible(true);
			// lblUnit.setText("Company Name is Mandatory");
			lblUnit.setTextFill(Color.RED);
		}
	}

	@FXML
	private void driverCityKeyPressed() {

		String name = txtCity.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCity.setTextFill(Color.BLACK);
			txtCity.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtCity.setStyle("-fx-focus-color: red;");
			txtCity.requestFocus();
			lblCity.setVisible(true);
			// lblCity.setText("Company Name is Mandatory");
			lblCity.setTextFill(Color.RED);
		}
	}

	@FXML
	private void driverPostalKeyPressed() {

		String name = txtPostal.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblZip.setTextFill(Color.BLACK);
			txtPostal.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtPostal.setStyle("-fx-focus-color: red;");
			txtPostal.requestFocus();
			lblZip.setVisible(true);
			// lblZip.setText("Company Name is Mandatory");
			lblZip.setTextFill(Color.RED);
		}
	}

	@FXML
	private void driverEmailKeyPressed() {

		String name = txtEmail.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblEmail.setTextFill(Color.BLACK);
			txtEmail.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtEmail.setStyle("-fx-focus-color: red;");
			txtEmail.requestFocus();
			lblEmail.setVisible(true);
			// lblEmail.setText("Company Name is Mandatory");
			lblEmail.setTextFill(Color.RED);
		}
	}

	@FXML
	private void driverHomeKeyPressed() {

		String name = txtHome.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblHome.setTextFill(Color.BLACK);
			txtHome.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtHome.setStyle("-fx-focus-color: red;");
			txtHome.requestFocus();
			lblHome.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblHome.setTextFill(Color.RED);
		}
	}

	@FXML
	private void driverCellularKeyPressed() {

		String name = txtCellular.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCellular.setTextFill(Color.BLACK);
			txtCellular.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtCellular.setStyle("-fx-focus-color: red;");
			txtCellular.requestFocus();
			lblCellular.setVisible(true);
			// lblCellular.setText("Company Name is Mandatory");
			lblCellular.setTextFill(Color.RED);
		}
	}

	@FXML
	private void driverFaxKeyPressed() {

		String name = txtFaxNo.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblFax.setTextFill(Color.BLACK);
			txtFaxNo.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtFaxNo.setStyle("-fx-focus-color: red;");
			txtFaxNo.requestFocus();
			lblFax.setVisible(true);
			// lblFax.setText("Company Name is Mandatory");
			lblFax.setTextFill(Color.RED);
		}
	}

	@FXML
	private void driverPagerKeyPressed() {

		String name = txtPager.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblPager.setTextFill(Color.BLACK);
			txtPager.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtPager.setStyle("-fx-focus-color: red;");
			txtPager.requestFocus();
			lblPager.setVisible(true);
			// lblPager.setText("Company Name is Mandatory");
			lblPager.setTextFill(Color.RED);
		}
	}

	@FXML
	private void driverTerminalKeyPressed() {

		String textField = ddlTerminal.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblStatus.setText("");
			ddlTerminal.setStyle("-fx-focus-color: skyblue;");
			lblStatus.setVisible(false);
		} else {
			ddlTerminal.setStyle("-fx-border-color: red;");
			lblStatus.setVisible(true);
			lblStatus.setText("TextField is Mandatory");
			lblStatus.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddlCategoryKeyPressed() {

		String textField = ddlCategory.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblCategory.setText("");
			ddlCategory.setStyle("-fx-focus-color: skyblue;");
			lblCategory.setVisible(false);
		} else {
			ddlCategory.setStyle("-fx-border-color: red;");
			lblCategory.setVisible(true);
			lblCategory.setText("TextField is Mandatory");
			lblCategory.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddlRoleKeyPressed() {

		String textField = ddlRole.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblRole.setText("");
			ddlRole.setStyle("-fx-focus-color: skyblue;");
			lblRole.setVisible(false);
		} else {
			ddlRole.setStyle("-fx-border-color: red;");
			lblRole.setVisible(true);
			lblRole.setText("TextField is Mandatory");
			lblRole.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddlStatusKeyPressed() {

		String textField = ddlStatus.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblStatus.setText("");
			ddlStatus.setStyle("-fx-focus-color: skyblue;");
			lblStatus.setVisible(false);
		} else {
			ddlStatus.setStyle("-fx-border-color: red;");
			lblStatus.setVisible(true);
			lblStatus.setText("TextField is Mandatory");
			lblStatus.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddlDriverClassKeyPressed() {

		String textField = ddlDriverClass.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblDriverClass.setText("");
			ddlDriverClass.setStyle("-fx-focus-color: skyblue;");
			lblDriverClass.setVisible(false);
		} else {
			ddlDriverClass.setStyle("-fx-border-color: red;");
			lblDriverClass.setVisible(true);
			lblDriverClass.setText("TextField is Mandatory");
			lblDriverClass.setTextFill(Color.RED);
		}
	}

	@FXML
	private void ddlDivisionKeyPressed() {

		String textField = ddlDivision.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblDivision.setText("");
			ddlDivision.setStyle("-fx-focus-color: skyblue;");
			lblDivision.setVisible(false);
		} else {
			ddlDivision.setStyle("-fx-border-color: red;");
			lblDivision.setVisible(true);
			lblDivision.setText("TextField is Mandatory");
			lblDivision.setTextFill(Color.RED);
		}
	}
}