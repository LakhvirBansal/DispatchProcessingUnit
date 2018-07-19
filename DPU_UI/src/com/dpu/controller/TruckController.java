package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.DeleteAPIClient;
import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.Truck;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TruckController extends Application implements Initializable {

	@FXML
	TableView<Truck> tblTruck;

	@FXML
	TableColumn<Truck, String> unitNo, owner, oOName, category, status, usage, division, terminal, truckType, finance;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(root, tblTruck);
		Login.setWidthForAll(TruckMain, null);
		fetchTrucks();
	}

	@FXML
	Pane TruckMain;

	@FXML
	AnchorPane root;
	public static int flag = 0;

	@FXML
	private void btnAddTruckAction() {
		openAddTruckScreen();
	}

	List<Truck> truckList = null;

	ObjectMapper mapper = new ObjectMapper();

	@FXML
	TextField txtSearchTruck;

	@FXML
	ImageView btnGoTruck;

	public void fillTruck(String response) {

		try {
			ObservableList<Truck> data = null;
			truckList = new ArrayList<Truck>();
			setColumnValues();
			if (response != null && response.length() > 0) {
				Truck c[] = mapper.readValue(response, Truck[].class);
				for (Truck ccl : c) {
					truckList.add(ccl);
				}
				data = FXCollections.observableArrayList(truckList);
			} else {
				data = FXCollections.observableArrayList(truckList);
			}
			tblTruck.setItems(data);
			tblTruck.setVisible(true);
		} catch (Exception e) {
			System.out.println("TruckController: fillTruck(): " + e.getMessage());
		}
	}

	@FXML
	private void btnEditTruckAction() {
		flag = 2;
		Truck truck = tblTruck.getSelectionModel().getSelectedItem();
		if (truck != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						System.out.println(truck.getTruckId());
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TRUCK_API + "/" + truck.getTruckId(), null);
						System.out.println(response);
						if (response != null && response.length() > 0) {
							Truck t = mapper.readValue(response, Truck.class);
							TruckEditController truckEditController = (TruckEditController) openEditTruckScreen();
							truckEditController.initData(t);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private void editTruckAction() {
		flag = 1;
		Truck truck = tblTruck.getSelectionModel().getSelectedItem();
		if (truck != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						System.out.println(truck.getTruckId());
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TRUCK_API + "/" + truck.getTruckId(), null);
						System.out.println(response);
						if (response != null && response.length() > 0) {
							Truck t = mapper.readValue(response, Truck.class);
							TruckEditController truckEditController = (TruckEditController) openEditTruckScreen();
							truckEditController.initData(t);
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
	private void btnDeleteTruckAction() {
		Truck truck = tblTruck.getSelectionModel().getSelectedItem();
		if (truck != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TRUCK_API + "/" + truck.getTruckId(), null);
						System.out.println(response);
						// fillTruck(response);
						try {
							Success success = mapper.readValue(response, Success.class);
							List<Truck> truckList = (List<Truck>) success.getResultList();
							String res = mapper.writeValueAsString(truckList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillTruck(res);
						} catch (Exception e) {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage());
						}
						/*
						 * if(response != null && response.contains("message"))
						 * { Success success = mapper.readValue(response,
						 * Success.class); JOptionPane.showMessageDialog(null,
						 * success.getMessage() , "Info", 1); } else { Failed
						 * failed = mapper.readValue(response, Failed.class);
						 * JOptionPane.showMessageDialog(null,
						 * failed.getMessage(), "Info", 1); }
						 */
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	private Object openEditTruckScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.TRUCK_BASE_PACKAGE + Iconstants.XML_TRUCK_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.EDIT_TRUCK);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	private void openAddTruckScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.TRUCK_BASE_PACKAGE + Iconstants.XML_TRUCK_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.ADD_NEW_TRUCK);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void start(Stage primaryStage) {
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		unitNo = (TableColumn<Truck, String>) tblTruck.getColumns().get(0);
		owner = (TableColumn<Truck, String>) tblTruck.getColumns().get(1);
		oOName = (TableColumn<Truck, String>) tblTruck.getColumns().get(2);
		category = (TableColumn<Truck, String>) tblTruck.getColumns().get(3);
		status = (TableColumn<Truck, String>) tblTruck.getColumns().get(4);
		usage = (TableColumn<Truck, String>) tblTruck.getColumns().get(5);
		division = (TableColumn<Truck, String>) tblTruck.getColumns().get(6);
		terminal = (TableColumn<Truck, String>) tblTruck.getColumns().get(7);
		truckType = (TableColumn<Truck, String>) tblTruck.getColumns().get(8);
		finance = (TableColumn<Truck, String>) tblTruck.getColumns().get(9);
	}

	public void fetchTrucks() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TRUCK_API, null);
					Truck c[] = mapper.readValue(response, Truck[].class);
					List<Truck> tList = new ArrayList<Truck>();
					for (Truck truck : c) {
						tList.add(truck);
					}
					ObservableList<Truck> data = FXCollections.observableArrayList(tList);

					setColumnValues();
					tblTruck.setItems(data);

					tblTruck.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
		
		double width = Login.width;
		int noOfColumns = tblTruck.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tblTruck.getColumns().get(i).setMinWidth(width / noOfColumns);
		}
		txtSearchTruck.setLayoutX(width - (txtSearchTruck.getPrefWidth() + btnGoTruck.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGoTruck.setLayoutX(width - (btnGoTruck.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
	}

	private void setColumnValues() {

		unitNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Truck, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
						return new SimpleStringProperty(param.getValue().getUnitNo() + "");
					}
				});
		owner.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Truck, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
				return new SimpleStringProperty(param.getValue().getOwner() + "");
			}
		});
		oOName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Truck, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
						return new SimpleStringProperty(param.getValue().getoOName() + "");
					}
				});
		category.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Truck, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
						return new SimpleStringProperty(param.getValue().getCatogoryName() + "");
					}
				});
		status.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Truck, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
						return new SimpleStringProperty(param.getValue().getStatusName() + "");
					}
				});
		usage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Truck, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
				return new SimpleStringProperty(param.getValue().getTruchUsage() + "");
			}
		});
		division.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Truck, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
						return new SimpleStringProperty(param.getValue().getDivisionName() + "");
					}
				});
		terminal.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Truck, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
						return new SimpleStringProperty(param.getValue().getTerminalName() + "");
					}
				});
		truckType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Truck, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
						return new SimpleStringProperty(param.getValue().getTruckType() + "");
					}
				});
		finance.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Truck, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
						return new SimpleStringProperty(param.getValue().getFinance() + "");
					}
				});
	}

	// ADD MENU

	public int tblTruckMenuCount = 0;

	@FXML
	public void handleAddContMouseClick(MouseEvent event) {

		// Create ContextMenu
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

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(item1, item2, item3, item4, item5, item6);
		if (tblTruckMenuCount == 0) {
			tblTruckMenuCount++;
			// When user right-click on Table
			tblTruck.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

						if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

							if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.SECONDARY)) {
								contextMenu.show(tblTruck, mouseEvent.getScreenX(), mouseEvent.getScreenY());
							} else if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.PRIMARY)
									&& ((MouseEvent) mouseEvent).getClickCount() == 2) {
								editTruckAction();

							} else if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.PRIMARY)) {
								contextMenu.hide();

							}
						}

					}

				}

			});

		}

	}
}