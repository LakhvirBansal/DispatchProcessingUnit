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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AccountsAddController extends Application implements Initializable {

	@FXML
	Button btnSaveAccounts, btnCancel;

	@FXML
	TextField txtAccountNo, txtAccountName, txtSubAccount, txtDefaultTaxCode;

	@FXML
	TextArea txtDescription;
	
	@FXML
	ComboBox<String> ddlAccountType, ddlCurrency;

	ObjectMapper mapper = new ObjectMapper();

	Validate validate = new Validate();

	public static boolean isStart = true;

	@FXML
	private void btnSaveAccountsAction() {
//		boolean response = validateAddAccountsScreen();
//		if (response) {
			addAccounts();
			closeAddAccountsScreen(btnSaveAccounts);
//		}
	}

	@FXML
	private void btnCancelAction() {
		closeAddAccountsScreen(btnCancel);
	}
	
	private void closeAddAccountsScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

//	@FXML
//	Label lblAccounts, lblTextField, lblAssociationWith, lblStatus;
	
	/*@FXML
	private void AccountsNameKeyPressed() {
		String name = txtAccounts.getText();
		boolean result = validate.validateEmptyness(name);
		if(result) {
			lblAccounts.setText("");
			txtAccounts.setStyle("-fx-focus-color: skyblue;");
			lblAccounts.setVisible(false);
		} else {
			txtAccounts.setStyle("-fx-focus-color: red;");
			txtAccounts.requestFocus();
			lblAccounts.setVisible(true);
			lblAccounts.setText("Accounts Name is Mandatory");
			lblAccounts.setTextFill(Color.RED);
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
	
	private boolean validateAddAccountsScreen() {
		boolean response = true;
		String name = txtAccounts.getText();
		String textField = ddlTextField.getSelectionModel().getSelectedItem();
		String association = ddlAssociationWith.getSelectionModel().getSelectedItem();
		String status = ddlStatus.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(name);

		if (!result) {
			
			response = false;
//			ValidationController.str = validsteFields();
//			openValidationScreen();
//			txtAccounts.setStyle("-fx-focus-color: red;");
			txtAccounts.setStyle("-fx-text-box-border: red;");
			lblAccounts.setVisible(true);
			lblAccounts.setText("Accounts Name is Mandatory");
			lblAccounts.setTextFill(Color.RED);
			
		} else if (!validate.validateLength(name, 5, 25)) {
			
			response = false;
//			openValidationScreen();
//			txtAccounts.requestFocus();
//			txtAccounts.setStyle("-fx-focus-color: red;");
			txtAccounts.setStyle("-fx-text-box-border: red;");
			lblAccounts.setVisible(true);
			lblAccounts.setText("Min. length 5 and Max. length 25");
			lblAccounts.setTextFill(Color.RED);
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
		String name = txtAccounts.getText();
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

	private void addAccounts() {

		Platform.runLater(new Runnable() {

			@Override
			@SuppressWarnings("unchecked")
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Accounts Accounts = setAccountsValue();
					String payload = mapper.writeValueAsString(Accounts);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_ACCOUNTS_API,
							null, payload);
					if (MainScreen.accountsController != null) {
						try {
							Success success = mapper.readValue(response, Success.class);
							List<Accounts> AccountsList = (List<Accounts>) success.getResultList();
							String res = mapper.writeValueAsString(AccountsList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							MainScreen.accountsController.fillAccounts(res);
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

	List<Accounts> parentAccountList = null;
	List<String> parentName = null;
	List<Type> typeList = null;
	List<String> taxCodeList = null;
	List<Type> currencyList = null;
	List<TaxCode> allTaxCodes = null;
	private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_ACCOUNTS_API + "/openAdd", null);
					Accounts accounts = mapper.readValue(response, Accounts.class);
					parentAccountList = accounts.getParentAccountList();
					typeList = accounts.getAccountTypeList();
					fillDropDown(ddlAccountType, typeList);
					currencyList = accounts.getCurrencyList();
					fillDropDown(ddlCurrency, currencyList);

					parentName = new ArrayList<>();
					for(Accounts accounts2: parentAccountList) {
						parentName.add(accounts2.getAccountName());
					}
					allTaxCodes = accounts.getTaxCodeList();
					taxCodeList = new ArrayList<>();
					for(TaxCode taxCode: allTaxCodes) {
						taxCodeList.add(taxCode.getTaxCode());
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			if (object != null && object instanceof Type) {
				Type associatedWith = (Type) object;
				comboBox.getItems().add(associatedWith.getTypeName());
			}
			if (object != null && object instanceof Status) {
				Status status = (Status) object;
				comboBox.getItems().add(status.getStatus());
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@FXML
	private void txtSubAccountKeyPressed(KeyEvent event) {
	
		String value = event.getText().trim();
		AutoCompletionTextFieldBinding aa = null;
		if(!value.equals("")) {
			
			aa = new AutoCompletionTextFieldBinding(txtSubAccount, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection>() {
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
	private void txtDefaultTaxCodeKeyPressed(KeyEvent event) {
	
		String value = event.getText().trim();
		AutoCompletionTextFieldBinding aa = null;
		if(!value.equals("")) {
			
			aa = new AutoCompletionTextFieldBinding(txtDefaultTaxCode, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection>() {
				@Override
				public Collection call(AutoCompletionBinding.ISuggestionRequest param) {
					List<String> filteredList = new ArrayList<>();
					for(int i=0;i<taxCodeList.size();i++) {
						if(taxCodeList.get(i).contains(param.getUserText())) {
							filteredList.add(taxCodeList.get(i));
						}
					}
					return filteredList;
				}
			});
		} 
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchMasterDataForDropDowns();
	}
	
	private Accounts setAccountsValue() {
		Accounts accounts = new Accounts();
		accounts.setAccount(txtAccountNo.getText());
		accounts.setAccountName(txtAccountName.getText());
		for(int i=0;i<parentAccountList.size();i++) {
			if(parentAccountList.get(i).getAccountName().equals(txtSubAccount.getText())) {
				accounts.setParentAccountId(parentAccountList.get(i).getAccountId());
				break;
			}
		}
		accounts.setDescription(txtDescription.getText());
		accounts.setAccountTypeId(typeList.get(ddlAccountType.getSelectionModel().getSelectedIndex()).getTypeId());
		accounts.setCurrencyId(currencyList.get(ddlCurrency.getSelectionModel().getSelectedIndex()).getTypeId());
		for(int i=0;i<allTaxCodes.size();i++) {
			if(allTaxCodes.get(i).getTaxCode().equals(txtDefaultTaxCode.getText())) {
				accounts.setTaxCodeId(allTaxCodes.get(i).getTaxCodeId());
				break;
			}
		}
		return accounts;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

}