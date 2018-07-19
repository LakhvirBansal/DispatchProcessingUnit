package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Category;
import com.dpu.model.Failed;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CategoryAddController extends Application implements Initializable {

	@FXML
	Button btnSaveCategory, btnCancel;

	@FXML
	TextField txtCategory;
	Validate validate = new Validate();

	@FXML
	ComboBox<String> ddlType, ddlStatus, ddlHighlight;

	@FXML
	Label lblType, lblCategory, lblStatus, lblHighlight;

	ObjectMapper mapper = new ObjectMapper();

	@FXML
	private void btnSaveCategoryAction() {
		boolean response = validateAddCategoryScreen();
		if (response) {
			addCategory();
			closeAddCategoryScreen(btnSaveCategory);
		}
	}

	private void closeAddCategoryScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}
	
	@FXML
	private void btnCancelAction() {
		closeAddCategoryScreen(btnCancel);
	}
	
	private boolean validateAddCategoryScreen() {
		boolean response = true;
		String name = txtCategory.getText();
		String textField = ddlType.getSelectionModel().getSelectedItem();
		String status = ddlStatus.getSelectionModel().getSelectedItem();
		String highLight = ddlHighlight.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(name);

		if (!result) {
			response = false;
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			txtCategory.setStyle("-fx-focus-color: red;");
			lblCategory.setVisible(true);
			lblCategory.setText("Category Name is Mandatory");
			lblCategory.setTextFill(Color.RED);
			// return result;
		} else if (!validate.validateLength(name, 5, 25)) {
			response = false;
			txtCategory.setStyle("-fx-focus-color: red;");
			lblCategory.setVisible(true);
			lblCategory.setText("Min. length 5 and Max. length 25");
			lblCategory.setTextFill(Color.RED);
			return result;

		}
		result = validate.validateEmptyness(textField);
		if (!result) {
			response = false;
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			ddlType.setStyle("-fx-focus-color: red;");
			lblType.setVisible(true);
			lblType.setText("Type Name is Mandatory");
			lblType.setTextFill(Color.RED);
		}
		result = validate.validateEmptyness(status);
		if (!result) {
			response = false;
			// ValidationController.str = validsteFields();
			// openValidationScreen();
			ddlStatus.setStyle("-fx-focus-color: red;");
			lblStatus.setVisible(true);
			lblStatus.setText("Status name is Mandatory");
			lblStatus.setTextFill(Color.RED);
		}
		result = validate.validateEmptyness(highLight);
		if (!result) {
			response = false;
//			ValidationController.str = validsteFields();
//			openValidationScreen();
			ddlType.setStyle("-fx-focus-color: red;");
			lblHighlight.setVisible(true);
			lblHighlight.setText("HighLight name is Mandatory");
			lblHighlight.setTextFill(Color.RED);
		}
		return response;
	}

	public StringBuffer validsteFields() {
		StringBuffer strBuff = new StringBuffer();
		String name = txtCategory.getText();
		String highLight = ddlHighlight.getSelectionModel().getSelectedItem();
		String association = ddlStatus.getSelectionModel().getSelectedItem();
		String status = ddlType.getSelectionModel().getSelectedItem();
		if (name.equals("")) {
			strBuff.append("Category Name is Mandatory\n");
		}
		if (highLight == null) {
			strBuff.append("Hiighlight is Mandatory\n");
		}
		if (association == null) {
			strBuff.append("Status is Mandatory\n");
		}
		if (status == null) {
			strBuff.append("Type is Mandatory\n");
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
	}

	private void addCategory() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Category category = setCategoryValue();
					String payload = mapper.writeValueAsString(category);

					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API,
							null, payload);
					// MainScreen.categoryController.fillCategories(response);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Category> categoryList = (List<Category>) success.getResultList();
						String res = mapper.writeValueAsString(categoryList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.categoryController.fillCategories(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}
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
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	List<Type> typeList, highlightList;

	List<Status> statusList;

	private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					String response = GetAPIClient
							.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API + "/openAdd", null);
					Category category = mapper.readValue(response, Category.class);
					typeList = category.getTypeList();
					fillDropDown(ddlType, typeList);
					statusList = category.getStatusList();
					fillDropDown(ddlStatus, statusList);
					highlightList = category.getHighlightList();
					fillDropDown(ddlHighlight, highlightList);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			if (object != null && object instanceof Type && comboBox.equals(ddlType)) {
				Type type = (Type) object;
				comboBox.getItems().add(type.getTypeName());
			}
			if (object != null && object instanceof Type && comboBox.equals(ddlHighlight)) {
				Type highlight = (Type) object;
				comboBox.getItems().add(highlight.getTypeName());
			}
			if (object != null && object instanceof Status) {
				Status status = (Status) object;
				comboBox.getItems().add(status.getStatus());
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchMasterDataForDropDowns();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	private Category setCategoryValue() {
		Category category = new Category();
		category.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		category.setName(txtCategory.getText());
		category.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		category.setHighlightId(highlightList.get(ddlHighlight.getSelectionModel().getSelectedIndex()).getTypeId());
		return category;
	}
}