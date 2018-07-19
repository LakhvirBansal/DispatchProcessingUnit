package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.NewBorderAgent;
import com.dpu.model.Status;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AddCustomBrokerController extends Application implements Initializable {

	@FXML
	Button btnSave, btnCancel;

	@FXML
	TextField txtFaxNo, txtPhoneNo, txtContactName, txtCustomerBroker, txtEmailAddress, txtWebSite, txtExtension;

	@FXML
	ComboBox<String> ddlStatus;

	@FXML
	private TableColumn<NewBorderAgent, String> officeAgent;

	@FXML
	private TableColumn<NewBorderAgent, String> borderCrossing;

	@FXML
	public TableView<NewBorderAgent> tableBorderAgent;

	List<Status> cList = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchStatus();
		fetchBorderAgents();

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

	public void fetchStatus() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_STATUS_API, null);
					if (response != null && response.length() > 0) {
						Status c[] = mapper.readValue(response, Status[].class);
						cList = new ArrayList<Status>();
						for (Status ccl : c) {
							ddlStatus.getItems().add(ccl.getStatus());
							cList.add(ccl);
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}
		});
	}

	@FXML
	private void btnAddOfficeAgentToList() {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.CUSTOM_BROKER_BASE_PACKAGE + Iconstants.XML_NEW_BORDER_AGENT));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("New Border Agent");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<NewBorderAgent> listOfBorderAgents = new ArrayList<NewBorderAgent>();

	public void fetchBorderAgents() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (listOfBorderAgents != null & !(listOfBorderAgents.isEmpty())) {
						ObservableList<NewBorderAgent> data = FXCollections.observableArrayList(listOfBorderAgents);
						setColumnValues();
						tableBorderAgent.setItems(data);
						tableBorderAgent.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}

		});
	}

	private void setColumnValues() {

		officeAgent.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<NewBorderAgent, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<NewBorderAgent, String> param) {
						return new SimpleStringProperty(param.getValue().getBorderAgent() + "");
					}
				});
		borderCrossing.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<NewBorderAgent, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<NewBorderAgent, String> param) {
						return new SimpleStringProperty(param.getValue().getBorderCrossing() + "");
					}
				});

	}

}
