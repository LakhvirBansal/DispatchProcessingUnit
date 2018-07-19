package com.dpu.controller.database;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.MainScreen;
import com.dpu.model.Accounts;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TaxCode;
import com.dpu.util.Validate;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TaxCodeAddController extends Application implements Initializable {

	@FXML
	Button btnSaveTaxCode, btnCancel;

	@FXML
	TextField txtTaxCode, txtDescription, txtPercentage, txtAccountSales, txtAccountRevenue;

	@FXML
	ComboBox<String> ddlTaxable;

	ObjectMapper mapper = new ObjectMapper();

	List<String> taxableList;

	Validate validate = new Validate();

	public static boolean isStart = true;

	@FXML
	private void btnSaveTaxCodeAction() {
//		boolean response = validateAddTaxCodeScreen();
//		if (response) {
			addTaxCode();
			closeAddTaxCodeScreen(btnSaveTaxCode);
//		}
	}
	
	@FXML
	private void btnCancelAction() {
		closeAddTaxCodeScreen(btnCancel);
	}

	private void closeAddTaxCodeScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

//	@FXML
//	Label lblTaxCode, lblTextField, lblAssociationWith, lblStatus;
	
	/*@FXML
	private void TaxCodeNameKeyPressed() {
		String name = txtTaxCode.getText();
		boolean result = validate.validateEmptyness(name);
		if(result) {
			lblTaxCode.setText("");
			txtTaxCode.setStyle("-fx-focus-color: skyblue;");
			lblTaxCode.setVisible(false);
		} else {
			txtTaxCode.setStyle("-fx-focus-color: red;");
			txtTaxCode.requestFocus();
			lblTaxCode.setVisible(true);
			lblTaxCode.setText("TaxCode Name is Mandatory");
			lblTaxCode.setTextFill(Color.RED);
		}
	}
	
	@FXML
	private void ddlTextFieldAction() {
		String textField = ddlTextField.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblTextField.setText("");
			ddlTextField.setStyle("-fx-focus-color: skyblue;");
			lblTextField.setVisible(false);
		} else {
//			ValidationController.str = validsteFields();
//			openValidationScreen();
//			ddlTextField.setStyle("-fx-focus-color: red;");
			ddlTextField.setStyle("-fx-border-color: red;");
			lblTextField.setVisible(true);
			lblTextField.setText("TextField is Mandatory");
			lblTextField.setTextFill(Color.RED);
		}
	}
	
	@FXML
	private void ddlAssociationWithAction() {
		String association = ddlAssociationWith.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(association);
		if (result) {
			lblAssociationWith.setText("");
			ddlAssociationWith.setStyle("-fx-focus-color: skyblue;");
			lblAssociationWith.setVisible(false);
		} else {
//			ValidationController.str = validsteFields();
//			openValidationScreen();
//			ddlTextField.setStyle("-fx-focus-color: red;");
			ddlAssociationWith.setStyle("-fx-border-color: red;");
			lblAssociationWith.setVisible(true);
			lblAssociationWith.setText("AssociationWith is Mandatory");
			lblAssociationWith.setTextFill(Color.RED);
		}
	}
	
	@FXML
	private void ddlStatusAction() {
		String status = ddlStatus.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(status);
		if (result) {
			lblStatus.setText("");
			ddlStatus.setStyle("-fx-focus-color: skyblue;");
			lblStatus.setVisible(false);
		} else {
//			ValidationController.str = validsteFields();
//			openValidationScreen();
//			ddlTextField.setStyle("-fx-focus-color: red;");
			ddlStatus.setStyle("-fx-border-color: red;");
			lblStatus.setVisible(true);
			lblStatus.setText("Status is Mandatory");
			lblStatus.setTextFill(Color.RED);
		}
	}
	
	private boolean validateAddTaxCodeScreen() {
		boolean response = true;
		String name = txtTaxCode.getText();
		String textField = ddlTextField.getSelectionModel().getSelectedItem();
		String association = ddlAssociationWith.getSelectionModel().getSelectedItem();
		String status = ddlStatus.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(name);

		if (!result) {
			
			response = false;
//			ValidationController.str = validsteFields();
//			openValidationScreen();
//			txtTaxCode.setStyle("-fx-focus-color: red;");
			txtTaxCode.setStyle("-fx-text-box-border: red;");
			lblTaxCode.setVisible(true);
			lblTaxCode.setText("TaxCode Name is Mandatory");
			lblTaxCode.setTextFill(Color.RED);
			
		} else if (!validate.validateLength(name, 5, 25)) {
			
			response = false;
//			openValidationScreen();
//			txtTaxCode.requestFocus();
//			txtTaxCode.setStyle("-fx-focus-color: red;");
			txtTaxCode.setStyle("-fx-text-box-border: red;");
			lblTaxCode.setVisible(true);
			lblTaxCode.setText("Min. length 5 and Max. length 25");
			lblTaxCode.setTextFill(Color.RED);
			return result;
		}
		result = validate.validateEmptyness(textField);
		if (!result) {
//			ValidationController.str = validsteFields();
//			openValidationScreen();
			response = false;
//			ddlTextField.setStyle("-fx-focus-color: red;");
			ddlTextField.setStyle("-fx-border-color: red;");
			lblTextField.setVisible(true);
			lblTextField.setText("TextField is Mandatory");
			lblTextField.setTextFill(Color.RED);
		}
		result = validate.validateEmptyness(association);
		if (!result) {
//			ValidationController.str = validsteFields();
//			openValidationScreen();
			response = false;
//			ddlAssociationWith.setStyle("-fx-focus-color: red;");
			ddlAssociationWith.setStyle("-fx-border-color: red;");
			lblAssociationWith.setVisible(true);
			lblAssociationWith.setText("AssociationWith is Mandatory");
			lblAssociationWith.setTextFill(Color.RED);
		}
		result = validate.validateEmptyness(status);
		if (!result) {
			response = false;
//			ValidationController.str = validsteFields();
//			openValidationScreen();
//			ddlStatus.setStyle("-fx-focus-color: red;");
			ddlStatus.setStyle("-fx-border-color: red;");
			lblStatus.setVisible(true);
			lblStatus.setText("Status is Mandatory");
			lblStatus.setTextFill(Color.RED);
		}
		return response;
	}

	public StringBuffer validsteFields() {
		StringBuffer strBuff = new StringBuffer();
		String name = txtTaxCode.getText();
		String textField = ddlTextField.getSelectionModel().getSelectedItem();
		String association = ddlAssociationWith.getSelectionModel().getSelectedItem();
		String status = ddlStatus.getSelectionModel().getSelectedItem();
		if (name.equals("")) {
			strBuff.append("Srvice Name is Mandatory\n");
		}
		if (textField == null) {
			strBuff.append("TextField Name is Mandatory\n");
		}
		if (association == null) {
			strBuff.append("Association Name is Mandatory\n");
		}
		if (status == null) {
			strBuff.append("Status Name is Mandatory\n");
		}
		return strBuff;
	}

	private Object openValidationScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMMON_BASE_PACKAGE + Iconstants.XML_VALIDATION_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Warning");
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

	private void addTaxCode() {

		Platform.runLater(new Runnable() {

			@Override
			@SuppressWarnings("unchecked")
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					TaxCode taxCode = setTaxCodeValue();
					String payload = mapper.writeValueAsString(taxCode);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_TAX_CODE_API,
							null, payload);
					if (MainScreen.taxCodeController != null) {
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
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillDropDowns();
		fetchMasterDataForDropDowns();
	}
	
	List<Accounts> gLAccounts = null;
	List<String> parentName = null;
	
	private void fillDropDowns() {
		taxableList = new ArrayList<>();
		taxableList.add(Iconstants.YES);
		taxableList.add(Iconstants.NO);
		for(int i=0;i<taxableList.size();i++) {
			ddlTaxable.getItems().add(taxableList.get(i));
		}
	}

	private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TAX_CODE_API + "/openAdd", null);
					TaxCode taxCode = mapper.readValue(response, TaxCode.class);
					gLAccounts = taxCode.getGlAccountRevenueList();
					parentName = new ArrayList<>();
					for(Accounts accounts2: gLAccounts) {
						parentName.add(accounts2.getAccountName());
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@FXML
	private void txtAccountSalesKeyPressed(KeyEvent event) {
	
		String value = event.getText().trim();
		AutoCompletionTextFieldBinding aa = null;
		if(!value.equals("")) {
			
			aa = new AutoCompletionTextFieldBinding(txtAccountSales, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection>() {
				@Override
				public Collection call(AutoCompletionBinding.ISuggestionRequest param) {
					List<String> filteredList = new ArrayList<>();
					for(int i=0;i<parentName.size();i++) {
						if(parentName.get(i).contains(param.getUserText())) {
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
		if(!value.equals("")) {
			
			aa = new AutoCompletionTextFieldBinding(txtAccountRevenue, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection>() {
				@Override
				public Collection call(AutoCompletionBinding.ISuggestionRequest param) {
					List<String> filteredList = new ArrayList<>();
					for(int i=0;i<parentName.size();i++) {
						if(parentName.get(i).contains(param.getUserText())) {
							filteredList.add(parentName.get(i));
						}
					}
					return filteredList;
				}
			});
		} 
	}
	
	private TaxCode setTaxCodeValue() {
		TaxCode taxCode = new TaxCode();
		taxCode.setTaxCode(txtTaxCode.getText());
		taxCode.setDescription(txtDescription.getText());
		if(ddlTaxable.getSelectionModel().getSelectedItem().equals(Iconstants.YES)) {
			taxCode.setTaxable(true);
		} else {
			taxCode.setTaxable(false);
		}
		int salesFound = 0, revenueFound = 0;
		for(int i=0;i<gLAccounts.size();i++) {
			if(gLAccounts.get(i).getAccountName().equals(txtAccountSales.getText())) {
				taxCode.setGlAccountSaleId(gLAccounts.get(i).getAccountId());
				salesFound = 1;
			}
			if(gLAccounts.get(i).getAccountName().equals(txtAccountRevenue.getText())) {
				taxCode.setGlAccountRevenueId(gLAccounts.get(i).getAccountId());
				revenueFound = 1;
			}
			if(salesFound == 1 && revenueFound == 1) {
				break;
			}
		}
		taxCode.setPercentage(Double.parseDouble(txtPercentage.getText()));
		return taxCode;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

}