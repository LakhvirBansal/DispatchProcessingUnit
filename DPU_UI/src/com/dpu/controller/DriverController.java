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
import com.dpu.model.Driver;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DriverController extends Application implements Initializable {

	@FXML
	TableView<Driver> tblDriver;

	public List<Driver> dList = null;
	public static int flag = 0;

	@FXML
	TableColumn<Driver, String> driverCode, firstName, lastName, address, unit, city, province, postalCode, home, faxNo,
			cellular, pager, email, driverClass;

	@FXML
	TextField txtSearchDriver;

	@FXML
	AnchorPane root, anchorPaneDriver;

	@FXML
	public void btnAddDriverAction() {
		openAddDriverScreen();
	}

	ObjectMapper mapper = new ObjectMapper();

	@FXML
	ImageView btnGoDriver;

	public void fillDriver(String response) {

		try {
			ObservableList<Driver> data = null;
			dList = new ArrayList<Driver>();
			setColumnValues();
			if (response != null && response.length() > 0) {
				Driver c[] = mapper.readValue(response, Driver[].class);
				for (Driver ccl : c) {
					dList.add(ccl);
				}
				data = FXCollections.observableArrayList(dList);
			} else {
				data = FXCollections.observableArrayList(dList);
			}
			tblDriver.setItems(data);
			tblDriver.setVisible(true);
		} catch (Exception e) {
			System.out.println("DriverController: fillDriver(): " + e.getMessage());
		}
	}

	@FXML
	private void btnGoDriverAction() {
		String searchDriver = txtSearchDriver.getText();
		if (searchDriver != null && searchDriver.length() > 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API + "/" + searchDriver + "/search",
								null);
						fillDriver(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});

		}

		if (searchDriver != null && searchDriver.length() == 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API,
								null);
						fillDriver(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});

		}
	}

	private void openAddDriverScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.DRIVER_BASE_PACKAGE + Iconstants.XML_DRIVER_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.ADD_NEW_DRIVER);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(root, tblDriver);
		Login.setWidthForAll(anchorPaneDriver, null);
		fetchDrivers();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

	public void fetchDrivers() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();

					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API, null);
					if (response != null && response.length() > 0) {
						Driver d[] = mapper.readValue(response, Driver[].class);
						dList = new ArrayList<Driver>();
						for (Driver ddl : d) {
							dList.add(ddl);
						}
						ObservableList<Driver> data = FXCollections.observableArrayList(dList);

						setColumnValues();
						tblDriver.setItems(data);

						tblDriver.setVisible(true);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
		double width = Login.width;
		int noOfColumns = tblDriver.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tblDriver.getColumns().get(i).setMinWidth(width / noOfColumns);
		}
		txtSearchDriver.setLayoutX(width - (txtSearchDriver.getPrefWidth() + btnGoDriver.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGoDriver.setLayoutX(width - (btnGoDriver.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		driverCode = (TableColumn<Driver, String>) tblDriver.getColumns().get(0);
		firstName = (TableColumn<Driver, String>) tblDriver.getColumns().get(1);
		lastName = (TableColumn<Driver, String>) tblDriver.getColumns().get(2);
		address = (TableColumn<Driver, String>) tblDriver.getColumns().get(3);
		unit = (TableColumn<Driver, String>) tblDriver.getColumns().get(4);
		city = (TableColumn<Driver, String>) tblDriver.getColumns().get(5);
		province = (TableColumn<Driver, String>) tblDriver.getColumns().get(6);
		postalCode = (TableColumn<Driver, String>) tblDriver.getColumns().get(7);
		home = (TableColumn<Driver, String>) tblDriver.getColumns().get(8);
		faxNo = (TableColumn<Driver, String>) tblDriver.getColumns().get(9);
		cellular = (TableColumn<Driver, String>) tblDriver.getColumns().get(10);
		pager = (TableColumn<Driver, String>) tblDriver.getColumns().get(11);
		email = (TableColumn<Driver, String>) tblDriver.getColumns().get(12);
		driverClass = (TableColumn<Driver, String>) tblDriver.getColumns().get(13);
	}

	@FXML
	private void btnDeleteDriverAction() {
		Driver driver = tblDriver.getSelectionModel().getSelectedItem();
		if (driver != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API + "/" + driver.getDriverId(), null);
						// MainScreen.driverController.fillDriver(response);
						try {
							Success success = mapper.readValue(response, Success.class);
							List<Driver> driverList = (List<Driver>) success.getResultList();
							String res = mapper.writeValueAsString(driverList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillDriver(res);
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
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	@FXML
	private void btnEditDriverAction() {
		flag = 2;
		Driver driver = tblDriver.getSelectionModel().getSelectedItem();
		System.out.println(driver + "   driver:: ");
		if (driver != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API + "/" + driver.getDriverId(), null);
						Success s = mapper.readValue(response, Success.class);
						String s1 = mapper.writeValueAsString(s.getResultList());
						if (response != null && response.length() > 0) {
							Driver c = mapper.readValue(s1, Driver.class);
							DriverEditController driverEditController = (DriverEditController) openEditDriverScreen();
							driverEditController.initData(c);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private void editDriverAction() {
		flag = 1;
		Driver driver = tblDriver.getSelectionModel().getSelectedItem();
		System.out.println(driver + "   driver:: ");
		if (driver != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API + "/" + driver.getDriverId(), null);
						System.out.println("DriverID:  "+driver.getDriverId());
						if (response != null && response.length() > 0) {
							Driver c = mapper.readValue(response, Driver.class);
							DriverEditController driverEditController = (DriverEditController) openEditDriverScreen();
							driverEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private Object openEditDriverScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.DRIVER_BASE_PACKAGE + Iconstants.XML_DRIVER_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.EDIT_DRIVER);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setColumnValues() {

		driverCode.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
						return new SimpleStringProperty(param.getValue().getDriverCode() + "");
					}
				});
		firstName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
						return new SimpleStringProperty(param.getValue().getFirstName() + "");
					}
				});
		lastName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
						return new SimpleStringProperty(param.getValue().getLastName() + "");
					}
				});
		address.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
						return new SimpleStringProperty(param.getValue().getAddress() + "");
					}
				});
		unit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getUnit() + "");
			}
		});
		city.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getCity() + "");
			}
		});
		province.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
						return new SimpleStringProperty(param.getValue().getPvs() + "");
					}
				});
		postalCode.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
						return new SimpleStringProperty(param.getValue().getPostalCode() + "");
					}
				});
		home.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getHome() + "");
			}
		});
		faxNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
						return new SimpleStringProperty(param.getValue().getFaxNo() + "");
					}
				});
		cellular.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
						return new SimpleStringProperty(param.getValue().getCellular() + "");
					}
				});
		pager.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
						return new SimpleStringProperty(param.getValue().getPager() + "");
					}
				});
		email.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
						return new SimpleStringProperty(param.getValue().getEmail() + "");
					}
				});
		driverClass.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
						return new SimpleStringProperty(param.getValue().getDriverClassName() + "");
					}
				});
	}

	// ADD MENU

	public int tblDriverMenuCount = 0;

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
		if (tblDriverMenuCount == 0) {
			tblDriverMenuCount++;
			// When user right-click on Table
			tblDriver.setOnMouseClicked(new EventHandler<MouseEvent>() {
				int click = 0;

				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

						if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.SECONDARY)) {
							contextMenu.show(tblDriver, mouseEvent.getScreenX(), mouseEvent.getScreenY());

						} else {
							contextMenu.hide();
							click++;

						}
						if (click == 2) {
							editDriverAction();
							click = 0;
						}

					}

				}
			});

		}

	}

}