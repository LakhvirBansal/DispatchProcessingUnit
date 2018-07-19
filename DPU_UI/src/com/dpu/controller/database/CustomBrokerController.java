package com.dpu.controller.database;

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
import com.dpu.model.CustomBroker;
import com.dpu.model.CustomBrokerTypeModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;

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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CustomBrokerController extends Application implements Initializable {

	@FXML
	TableView<CustomBroker> tblCustomBroker;

	@FXML
	TableColumn<CustomBroker, String> customBrokerName, pars, paps, parsName, parsPhone, parsEmail, parsLink, papsName,
			papsPhone, papsEmail, papsLink;

	ObjectMapper mapper = new ObjectMapper();

	@FXML
	TextField txtSearchCustomBroker;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(TruckMain, tblCustomBroker);
		fetchCustomBroker();
	}

	@FXML
	Pane TruckMain;
	
	@FXML
	ImageView btnGoCustomBroker;

	@Override
	public void start(Stage primaryStage) {
	}

	@FXML
	private void btnAddCustomBrokerAction() {
		openAddCustomBrokerScreen();
	}

	@FXML
	private void btnGoCustomBrokerAction() {
		String searchCustomBroker = txtSearchCustomBroker.getText();

		if (searchCustomBroker != null && searchCustomBroker.length() > 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER
								+ Iconstants.URL_CUSTOM_BROKER_API + "/" + searchCustomBroker + "/search", null);
						fillCustomBrokers(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});

		}

		if (searchCustomBroker != null && searchCustomBroker.length() == 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient
								.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CUSTOM_BROKER_API, null);
						fillCustomBrokers(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	List<CustomBroker> customBrokers = null;

	public void fillCustomBrokers(String response) {

		try {
			customBrokers = new ArrayList<CustomBroker>();
			setColumnValues();
			ObservableList<CustomBroker> data = null;
			if (response != null && response.length() > 0) {
				CustomBroker c[] = mapper.readValue(response, CustomBroker[].class);
				for (CustomBroker ccl : c) {
					customBrokers.add(ccl);
				}
				data = FXCollections.observableArrayList(customBrokers);

			} else {
				data = FXCollections.observableArrayList(customBrokers);
			}
			tblCustomBroker.setItems(data);

			tblCustomBroker.setVisible(true);
		} catch (Exception e) {
			System.out.println("CustomBrokerController: fillCustomBroker(): " + e.getMessage());
		}
	}

	private Object openEditCustomBrokerScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.CUSTOM_BROKER_BASE_PACKAGE + Iconstants.XML_CUSTOM_BROKER_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.EDIT_CUSTOM_BROKER);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void openAddCustomBrokerScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(CustomBrokerController.class.getClassLoader()
					.getResource(Iconstants.CUSTOM_BROKER_BASE_PACKAGE + Iconstants.XML_CUSTOM_BROKER_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.ADD_NEW_CUSTOM_BROKER);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		customBrokerName = (TableColumn<CustomBroker, String>) tblCustomBroker.getColumns().get(0);
		pars = (TableColumn<CustomBroker, String>) tblCustomBroker.getColumns().get(1);
		ObservableList<TableColumn<CustomBroker, ?>> parsChildColumns = pars.getColumns();
		parsName = (TableColumn<CustomBroker, String>) parsChildColumns.get(0);
		parsEmail = (TableColumn<CustomBroker, String>) parsChildColumns.get(1);
		parsPhone = (TableColumn<CustomBroker, String>) parsChildColumns.get(2);
		parsLink = (TableColumn<CustomBroker, String>) parsChildColumns.get(3);

		paps = (TableColumn<CustomBroker, String>) tblCustomBroker.getColumns().get(2);
		ObservableList<TableColumn<CustomBroker, ?>> papsChildColumns = paps.getColumns();
		papsName = (TableColumn<CustomBroker, String>) papsChildColumns.get(0);
		papsEmail = (TableColumn<CustomBroker, String>) papsChildColumns.get(1);
		papsPhone = (TableColumn<CustomBroker, String>) papsChildColumns.get(2);
		papsLink = (TableColumn<CustomBroker, String>) papsChildColumns.get(3);
	}

	public void fetchCustomBroker() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CUSTOM_BROKER_API,
							null);
					fillCustomBrokers(response);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
		double width = Login.width;
		int noOfColumns = tblCustomBroker.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tblCustomBroker.getColumns().get(i).setMinWidth(width / noOfColumns);
			if (tblCustomBroker.getColumns().get(i).getColumns() != null
					&& tblCustomBroker.getColumns().get(i).getColumns().size() > 0) {
				int noOfInnerColumns = tblCustomBroker.getColumns().get(i).getColumns().size();

				for (int j = 0; j < noOfInnerColumns; j++) {
					double mainColumnWidth = tblCustomBroker.getColumns().get(i).getWidth();
					tblCustomBroker.getColumns().get(i).getColumns().get(j).setMinWidth(mainColumnWidth / noOfInnerColumns);
				}
			}
		}
		txtSearchCustomBroker.setLayoutX(width - (txtSearchCustomBroker.getPrefWidth() + btnGoCustomBroker.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGoCustomBroker.setLayoutX(width - (btnGoCustomBroker.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
	}

	@FXML
	private void btnDeleteCustomBrokerAction() {
		CustomBroker customBroker = tblCustomBroker.getSelectionModel().getSelectedItem();
		if (customBroker != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER
								+ Iconstants.URL_CUSTOM_BROKER_API + "/" + customBroker.getCustomBrokerId(), null);
						try {
							Success success = mapper.readValue(response, Success.class);
							List<CustomBroker> customBrokerList = (List<CustomBroker>) success.getResultList();
							String res = mapper.writeValueAsString(customBrokerList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillCustomBrokers(res);
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
	}

	private void setColumnValues() {

		customBrokerName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						return new SimpleStringProperty(param.getValue().getCustomBrokerName() + "");
					}
				});

		parsName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						List<CustomBrokerTypeModel> list = param.getValue().getCustomBrokerTypes();
						if (list != null && list.size() > 0) {
							for (CustomBrokerTypeModel customBrokerTypeModel : list) {
								if (customBrokerTypeModel.getTypeName() != null
										&& customBrokerTypeModel.getTypeName().equals(Iconstants.PARS)) {
									return new SimpleStringProperty(customBrokerTypeModel.getContactName() + "");
								}
							}
						}
						return new SimpleStringProperty("");
					}
				});
		papsName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						List<CustomBrokerTypeModel> list = param.getValue().getCustomBrokerTypes();
						if (list != null && list.size() > 0) {
							for (CustomBrokerTypeModel customBrokerTypeModel : list) {
								if (customBrokerTypeModel.getTypeName() != null
										&& customBrokerTypeModel.getTypeName().equals(Iconstants.PAPS)) {
									return new SimpleStringProperty(customBrokerTypeModel.getContactName() + "");
								}
							}
						}
						return new SimpleStringProperty("");
					}
				});
		parsPhone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						List<CustomBrokerTypeModel> list = param.getValue().getCustomBrokerTypes();
						if (list != null && list.size() > 0) {
							for (CustomBrokerTypeModel customBrokerTypeModel : list) {
								if (customBrokerTypeModel.getTypeName() != null
										&& customBrokerTypeModel.getTypeName().equals(Iconstants.PARS)) {
									return new SimpleStringProperty(customBrokerTypeModel.getPhone() + "");
								}
							}
						}
						return new SimpleStringProperty("");
					}
				});
		papsPhone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						List<CustomBrokerTypeModel> list = param.getValue().getCustomBrokerTypes();
						if (list != null && list.size() > 0) {
							for (CustomBrokerTypeModel customBrokerTypeModel : list) {
								if (customBrokerTypeModel.getTypeName() != null
										&& customBrokerTypeModel.getTypeName().equals(Iconstants.PAPS)) {
									return new SimpleStringProperty(customBrokerTypeModel.getPhone() + "");
								}
							}
						}
						return new SimpleStringProperty("");
					}
				});
		parsEmail.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						List<CustomBrokerTypeModel> list = param.getValue().getCustomBrokerTypes();
						if (list != null && list.size() > 0) {
							for (CustomBrokerTypeModel customBrokerTypeModel : list) {
								if (customBrokerTypeModel.getTypeName() != null
										&& customBrokerTypeModel.getTypeName().equals(Iconstants.PARS)) {
									return new SimpleStringProperty(customBrokerTypeModel.getEmail() + "");
								}
							}
						}
						return new SimpleStringProperty("");
					}
				});
		papsEmail.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						List<CustomBrokerTypeModel> list = param.getValue().getCustomBrokerTypes();
						if (list != null && list.size() > 0) {
							for (CustomBrokerTypeModel customBrokerTypeModel : list) {
								if (customBrokerTypeModel.getTypeName() != null
										&& customBrokerTypeModel.getTypeName().equals(Iconstants.PAPS)) {
									return new SimpleStringProperty(customBrokerTypeModel.getEmail() + "");
								}
							}
						}
						return new SimpleStringProperty("");
					}
				});
		parsLink.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						List<CustomBrokerTypeModel> list = param.getValue().getCustomBrokerTypes();
						if (list != null && list.size() > 0) {
							for (CustomBrokerTypeModel customBrokerTypeModel : list) {
								if (customBrokerTypeModel.getTypeName() != null
										&& customBrokerTypeModel.getTypeName().equals(Iconstants.PARS)) {
									return new SimpleStringProperty(customBrokerTypeModel.getTrackerLink() + "");
								}
							}
						}
						return new SimpleStringProperty("");
					}
				});
		papsLink.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						List<CustomBrokerTypeModel> list = param.getValue().getCustomBrokerTypes();
						if (list != null && list.size() > 0) {
							for (CustomBrokerTypeModel customBrokerTypeModel : list) {
								if (customBrokerTypeModel.getTypeName() != null
										&& customBrokerTypeModel.getTypeName().equals(Iconstants.PAPS)) {
									return new SimpleStringProperty(customBrokerTypeModel.getTrackerLink() + "");
								}
							}
						}
						return new SimpleStringProperty("");
					}
				});
	}

	public static int flag = 0;

	@FXML
	private void btnEditCustomBrokerAction() {
		flag = 2;
		CustomBroker customBroker = tblCustomBroker.getSelectionModel().getSelectedItem();
		if (customBroker != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER
								+ Iconstants.URL_CUSTOM_BROKER_API + "/" + customBroker.getCustomBrokerId(), null);
						System.out.println(response);
						if (response != null && response.length() > 0) {
							CustomBroker c = mapper.readValue(response, CustomBroker.class);
							CustomBrokerEditController customBrokerEditController = (CustomBrokerEditController) openEditCustomBrokerScreen();
							System.out.println("broke1r:: " + customBrokerEditController);
							customBrokerEditController.initData(c);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private void editCustomBrokerAction() {
		flag = 1;
		CustomBroker customBroker = tblCustomBroker.getSelectionModel().getSelectedItem();
		if (customBroker != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER
								+ Iconstants.URL_CUSTOM_BROKER_API + "/" + customBroker.getCustomBrokerId(), null);
						System.out.println("customID: " + customBroker.getCustomBrokerId());
						if (response != null && response.length() > 0) {
							CustomBroker c = mapper.readValue(response, CustomBroker.class);
							CustomBrokerEditController customBrokerEditController = (CustomBrokerEditController) openEditCustomBrokerScreen();
							System.out.println("broker:: " + customBrokerEditController);
							customBrokerEditController.initData(c);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	public int tblServicerMenuCount = 0;

	@FXML
	public void tblCustomBrokerAction(MouseEvent event) {

		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			}

		});
		MenuItem item2 = new MenuItem("EDIT");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		});

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		});

		MenuItem item4 = new MenuItem("PERSONALIZE");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			}

		});
		MenuItem item5 = new MenuItem("DUPLICATE");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		});

		MenuItem item6 = new MenuItem("FILTER BY");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		});

		// Add MenuItem to ContextMenu contextMenu.getItems().addAll(item1,
		contextMenu.getItems().addAll(item1, item2, item3, item4, item5, item6);
		if (tblServicerMenuCount == 0) {
			tblServicerMenuCount++; // When user right-click on Table
			tblCustomBroker.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

						if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.SECONDARY)) {
							contextMenu.show(tblCustomBroker, mouseEvent.getScreenX(), mouseEvent.getScreenY());
						} else if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.PRIMARY)
								&& ((MouseEvent) mouseEvent).getClickCount() == 2) {
							editCustomBrokerAction();

						} else if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.PRIMARY)) {
							contextMenu.hide();

						}
					}

				}

			});

		}

	}

}
