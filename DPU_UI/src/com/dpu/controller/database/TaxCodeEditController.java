package com.dpu.controller.database;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.MainScreen;
import com.dpu.model.Accounts;
import com.dpu.model.Failed;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.TaxCode;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TaxCodeEditController extends Application implements Initializable {

	@FXML
	Button btnUpdateTaxCode, btnEdit, btnCancel;

	Long taxCodeId = 0l;

	@FXML
	TextField txtTaxCode, txtDescription, txtPercentage, txtAccountSales, txtAccountRevenue;

	Validate validate = new Validate();

	@FXML
	Label lblTaxCode, lblTextField, lblAssociationWith, lblStatus;

	@FXML
	ComboBox<String> ddlTaxable;
	
	@FXML
	private void btnCancel() {
		closeEditTaxCodeScreen(btnCancel);
	}

	/*
	 * @FXML private void taxCodeNameKeyPressed() { String name =
	 * txtTaxCode.getText(); boolean result = validate.validateEmptyness(name);
	 * if (result) { lblTaxCode.setText("");
	 * txtTaxCode.setStyle("-fx-focus-color: skyblue;");
	 * lblTaxCode.setVisible(false); } else {
	 * txtTaxCode.setStyle("-fx-focus-color: red;"); txtTaxCode.requestFocus();
	 * lblTaxCode.setVisible(true);
	 * lblTaxCode.setText("TaxCode Name is Mandatory");
	 * lblTaxCode.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void ddlTextFieldAction() { String textField =
	 * ddlTextField.getSelectionModel().getSelectedItem(); boolean result =
	 * validate.validateEmptyness(textField); if (result) {
	 * lblTextField.setText("");
	 * ddlTextField.setStyle("-fx-focus-color: skyblue;");
	 * lblTextField.setVisible(false); } else { // ValidationController.str =
	 * validsteFields(); // openValidationScreen(); //
	 * ddlTextField.setStyle("-fx-focus-color: red;");
	 * ddlTextField.setStyle("-fx-border-color: red;");
	 * lblTextField.setVisible(true);
	 * lblTextField.setText("TextField is Mandatory");
	 * lblTextField.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void ddlAssociationWithAction() { String association =
	 * ddlAssociationWith.getSelectionModel().getSelectedItem(); boolean result
	 * = validate.validateEmptyness(association); if (result) {
	 * lblAssociationWith.setText("");
	 * ddlAssociationWith.setStyle("-fx-focus-color: skyblue;");
	 * lblAssociationWith.setVisible(false); } else { //
	 * ValidationController.str = validsteFields(); // openValidationScreen();
	 * // ddlTextField.setStyle("-fx-focus-color: red;");
	 * ddlAssociationWith.setStyle("-fx-border-color: red;");
	 * lblAssociationWith.setVisible(true);
	 * lblAssociationWith.setText("AssociationWith is Mandatory");
	 * lblAssociationWith.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void ddlStatusAction() { String status =
	 * ddlStatus.getSelectionModel().getSelectedItem(); boolean result =
	 * validate.validateEmptyness(status); if (result) { lblStatus.setText("");
	 * ddlStatus.setStyle("-fx-focus-color: skyblue;");
	 * lblStatus.setVisible(false); } else { // ValidationController.str =
	 * validsteFields(); // openValidationScreen(); //
	 * ddlTextField.setStyle("-fx-focus-color: red;");
	 * ddlStatus.setStyle("-fx-border-color: red;"); lblStatus.setVisible(true);
	 * lblStatus.setText("Status is Mandatory");
	 * lblStatus.setTextFill(Color.RED); } }
	 * 
	 * private boolean validateEditTaxCodeScreen() { boolean response = true;
	 * String name = txtTaxCode.getText(); String textField =
	 * ddlTextField.getSelectionModel().getSelectedItem(); String association =
	 * ddlAssociationWith.getSelectionModel().getSelectedItem(); String status =
	 * ddlStatus.getSelectionModel().getSelectedItem(); boolean result =
	 * validate.validateEmptyness(name);
	 * 
	 * if (!result) {
	 * 
	 * response = false; // ValidationController.str = validsteFields(); //
	 * openValidationScreen(); // txtTaxCode.setStyle("-fx-focus-color: red;");
	 * txtTaxCode.setStyle("-fx-text-box-border: red;");
	 * lblTaxCode.setVisible(true);
	 * lblTaxCode.setText("TaxCode Name is Mandatory");
	 * lblTaxCode.setTextFill(Color.RED);
	 * 
	 * } else if (!validate.validateLength(name, 5, 25)) {
	 * 
	 * response = false; // openValidationScreen(); //
	 * txtTaxCode.requestFocus(); //
	 * txtTaxCode.setStyle("-fx-focus-color: red;");
	 * txtTaxCode.setStyle("-fx-text-box-border: red;");
	 * lblTaxCode.setVisible(true);
	 * lblTaxCode.setText("Min. length 5 and Max. length 25");
	 * lblTaxCode.setTextFill(Color.RED); return result; } result =
	 * validate.validateEmptyness(textField); if (!result) { //
	 * ValidationController.str = validsteFields(); // openValidationScreen();
	 * response = false; // ddlTextField.setStyle("-fx-focus-color: red;");
	 * ddlTextField.setStyle("-fx-border-color: red;");
	 * lblTextField.setVisible(true);
	 * lblTextField.setText("TextField is Mandatory");
	 * lblTextField.setTextFill(Color.RED); } result =
	 * validate.validateEmptyness(association); if (!result) { //
	 * ValidationController.str = validsteFields(); // openValidationScreen();
	 * response = false; //
	 * ddlAssociationWith.setStyle("-fx-focus-color: red;");
	 * ddlAssociationWith.setStyle("-fx-border-color: red;");
	 * lblAssociationWith.setVisible(true);
	 * lblAssociationWith.setText("AssociationWith is Mandatory");
	 * lblAssociationWith.setTextFill(Color.RED); } result =
	 * validate.validateEmptyness(status); if (!result) { response = false; //
	 * ValidationController.str = validsteFields(); // openValidationScreen();
	 * // ddlStatus.setStyle("-fx-focus-color: red;");
	 * ddlStatus.setStyle("-fx-border-color: red;"); lblStatus.setVisible(true);
	 * lblStatus.setText("Status is Mandatory");
	 * lblStatus.setTextFill(Color.RED); } return response; }
	 * 
	 * public StringBuffer validsteFields() { StringBuffer strBuff = new
	 * StringBuffer(); String name = txtTaxCode.getText(); String textField =
	 * ddlTextField.getSelectionModel().getSelectedItem(); String association =
	 * ddlAssociationWith.getSelectionModel().getSelectedItem(); String status =
	 * ddlStatus.getSelectionModel().getSelectedItem(); if (name.equals("")) {
	 * strBuff.append("Srvice Name is Mandatory,\n"); } if (textField == null) {
	 * strBuff.append("TextField Name is Mandatory,\n"); } if (association ==
	 * null) { strBuff.append("Association Name is Mandatory,\n"); } if (status
	 * == null) { strBuff.append("Status Name is Mandatory\n"); } return
	 * strBuff; }
	 * 
	 * private Object openValidationScreen() { try { FXMLLoader fxmlLoader = new
	 * FXMLLoader(getClass().getClassLoader()
	 * .getResource(Iconstants.COMMON_BASE_PACKAGE +
	 * Iconstants.XML_VALIDATION_SCREEN));
	 * 
	 * Parent root = (Parent) fxmlLoader.load();
	 * 
	 * Stage stage = new Stage();
	 * stage.initModality(Modality.APPLICATION_MODAL);
	 * stage.setTitle("Warning"); stage.setScene(new Scene(root)); stage.show();
	 * return fxmlLoader.getController(); } catch (Exception e) {
	 * e.printStackTrace(); } return null; }
	 */

	@FXML
	private void btnUpdateTaxCodeAction() {
		// boolean response = validateEditTaxCodeScreen();
		// if (response) {
		editTaxCode();
		closeEditTaxCodeScreen(btnUpdateTaxCode);
		// }
	}

	private void closeEditTaxCodeScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	List<String> parentName = null;

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@FXML
	private void txtAccountSalesKeyPressed(KeyEvent event) {

		String value = event.getText().trim();
		AutoCompletionTextFieldBinding aa = null;
		if (!value.equals("")) {

			aa = new AutoCompletionTextFieldBinding(txtAccountSales,
					new Callback<AutoCompletionBinding.ISuggestionRequest, Collection>() {
						@Override
						public Collection call(AutoCompletionBinding.ISuggestionRequest param) {
							List<String> filteredList = new ArrayList<>();
							for (int i = 0; i < parentName.size(); i++) {
								if (parentName.get(i).contains(param.getUserText())) {
									filteredList.add(parentName.get(i));
								}
							}
							return filteredList;
						}
					});
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@FXML
	private void txtAccountRevenueKeyPressed(KeyEvent event) {

		String value = event.getText().trim();
		AutoCompletionTextFieldBinding aa = null;
		if (!value.equals("")) {

			aa = new AutoCompletionTextFieldBinding(txtAccountRevenue,
					new Callback<AutoCompletionBinding.ISuggestionRequest, Collection>() {
						@Override
						public Collection call(AutoCompletionBinding.ISuggestionRequest param) {
							List<String> filteredList = new ArrayList<>();
							for (int i = 0; i < parentName.size(); i++) {
								if (parentName.get(i).contains(param.getUserText())) {
									filteredList.add(parentName.get(i));
								}
							}
							return filteredList;
						}
					});
		}
	}

	private void editTaxCode() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					TaxCode TaxCode = setTaxCodeValue();
					String payload = mapper.writeValueAsString(TaxCode);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_TAX_CODE_API + "/" + taxCodeId, null, payload);
					// MainScreen.TaxCodeController.fillTaxCodes(response);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<TaxCode> TaxCodeList = (List<TaxCode>) success.getResultList();
						String res = mapper.writeValueAsString(TaxCodeList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.taxCodeController.fillTaxCodes(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}

		});
	}

	List<Type> typeList, associatedWithList;

	List<Status> statusList;

	private TaxCode setTaxCodeValue() {

		TaxCode taxCode = new TaxCode();
		taxCode.setTaxCode(txtTaxCode.getText());
		taxCode.setDescription(txtDescription.getText());
		if (ddlTaxable.getSelectionModel().getSelectedItem().equals(Iconstants.YES)) {
			taxCode.setTaxable(true);
		} else {
			taxCode.setTaxable(false);
		}
		taxCode.setPercentage(Double.parseDouble(txtPercentage.getText()));
		int salesFound = 0, revenueFound = 0;
		for (int i = 0; i < glSaleRevenueList.size(); i++) {
			if (glSaleRevenueList.get(i).getAccountName().equals(txtAccountSales.getText())) {
				taxCode.setGlAccountSaleId(glSaleRevenueList.get(i).getAccountId());
				salesFound = 1;
			}
			if (glSaleRevenueList.get(i).getAccountName().equals(txtAccountRevenue.getText())) {
				taxCode.setGlAccountRevenueId(glSaleRevenueList.get(i).getAccountId());
				revenueFound = 1;
			}
			if (salesFound == 1 && revenueFound == 1) {
				break;
			}
		}
		return taxCode;
	}

	@FXML
	public void handleEditAction() {
		btnEdit.setDisable(true);
		disableFields(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (TaxCodeController.flag == 1) {
			disableFields(true);
		}
		if (TaxCodeController.flag == 2) {
			btnEdit.setVisible(false);
		}
		fillDropDowns();
	}

	private void disableFields(boolean v) {
		btnUpdateTaxCode.setDisable(v);
		txtTaxCode.setDisable(v);
		txtAccountRevenue.setDisable(v);
		txtAccountSales.setDisable(v);
		txtDescription.setDisable(v);
		txtPercentage.setDisable(v);
		ddlTaxable.setDisable(v);
	}

	@Override
	public void start(Stage primaryStage) {
	}

	List<String> taxableList;

	private void fillDropDowns() {
		taxableList = new ArrayList<>();
		taxableList.add(Iconstants.YES);
		taxableList.add(Iconstants.NO);
		for (int i = 0; i < taxableList.size(); i++) {
			ddlTaxable.getItems().add(taxableList.get(i));
		}
	}

	List<Accounts> glSaleRevenueList = null;

	int salesInit = 0, revenueInit = 0;

	public void initData(TaxCode taxCode) {
		taxCodeId = taxCode.getTaxCodeId();
		txtTaxCode.setText(taxCode.getTaxCode());
		txtDescription.setText(taxCode.getDescription());
		if (taxCode.getTaxable()) {
			ddlTaxable.getSelectionModel().select(0);
		} else {
			ddlTaxable.getSelectionModel().select(1);
		}
		txtPercentage.setText(String.valueOf(taxCode.getPercentage()));
		glSaleRevenueList = taxCode.getGlAccountRevenueList();
		for (int i = 0; i < glSaleRevenueList.size(); i++) {
			if (glSaleRevenueList.get(i).getAccountId().equals(taxCode.getGlAccountSaleId())) {
				txtAccountSales.setText(glSaleRevenueList.get(i).getAccountName());
				salesInit = 1;
			}
			if (glSaleRevenueList.get(i).getAccountId().equals(taxCode.getGlAccountRevenueId())) {
				txtAccountRevenue.setText(glSaleRevenueList.get(i).getAccountName());
				revenueInit = 1;
			}
			if (salesInit == 1 && revenueInit == 1) {
				break;
			}
		}

		parentName = new ArrayList<>();
		for (Accounts accounts2 : glSaleRevenueList) {
			parentName.add(accounts2.getAccountName());
		}
	}
}