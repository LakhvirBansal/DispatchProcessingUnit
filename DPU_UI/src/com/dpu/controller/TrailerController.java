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
import com.dpu.model.Trailer;

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

public class TrailerController extends Application implements Initializable {

	@FXML
	TableView<Trailer> tblTrailer;

	@FXML
	TableColumn<Trailer, String> unitNo, owner, oOName, category, status, usage, division, terminal, trailerType,
			finance;

	@FXML
	AnchorPane trailerRootAnchorPane;

	@FXML
	Pane trailerPane;
	public static int flag = 0;
	
	@FXML
	TextField txtSearchTrailer;
	
	@FXML
	ImageView btnGoTrailer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(trailerPane, tblTrailer);
		fetchTrailers();
	}

	@FXML
	private void btnAddTrailerAction() {
		openAddTrailerScreen();
	}

	@FXML
	private void btnEditTrailerAction() {
		flag = 2;
		Trailer trailer = tblTrailer.getSelectionModel().getSelectedItem();
		if (trailer != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TRAILER_API + "/" + trailer.getTrailerId(),
								null);
						if (response != null && response.length() > 0) {
							Trailer c = mapper.readValue(response, Trailer.class);
							TrailerEditController trailerEditController = (TrailerEditController) openEditTrailerScreen();
							trailerEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private void editTrailerAction() {
		flag = 1;
		Trailer trailer = tblTrailer.getSelectionModel().getSelectedItem();
		if (trailer != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TRAILER_API + "/" + trailer.getTrailerId(),
								null);
						if (response != null && response.length() > 0) {
							Trailer c = mapper.readValue(response, Trailer.class);
							TrailerEditController trailerEditController = (TrailerEditController) openEditTrailerScreen();
							trailerEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private Object openEditTrailerScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.TRAILER_BASE_PACKAGE + Iconstants.XML_TRAILER_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.EDIT_TRAILER);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@FXML
	private void btnDeleteTrailerAction() {
		Trailer trailer = tblTrailer.getSelectionModel().getSelectedItem();
		if (trailer != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TRAILER_API + "/" + trailer.getTrailerId(),
								null);
						// MainScreen.trailerController.fillTrailer(response);
						try {
							Success success = mapper.readValue(response, Success.class);
							List<Trailer> trailerList = (List<Trailer>) success.getResultList();
							String res = mapper.writeValueAsString(trailerList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillTrailer(res);
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

	private void openAddTrailerScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.TRAILER_BASE_PACKAGE + Iconstants.XML_TRAILER_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.ADD_NEW_TRAILER);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	List<Trailer> trailerList = null;

	ObjectMapper mapper = new ObjectMapper();

	public void fillTrailer(String response) {

		try {
			ObservableList<Trailer> data = null;
			trailerList = new ArrayList<Trailer>();
			setColumnValues();
			if (response != null && response.length() > 0) {
				Trailer c[] = mapper.readValue(response, Trailer[].class);
				for (Trailer ccl : c) {
					trailerList.add(ccl);
				}
				data = FXCollections.observableArrayList(trailerList);
			} else {
				data = FXCollections.observableArrayList(trailerList);
			}
			tblTrailer.setItems(data);
			tblTrailer.setVisible(true);
		} catch (Exception e) {
			System.out.println("DriverController: fillDriver(): " + e.getMessage());
		}
	}

	@Override
	public void start(Stage primaryStage) {

	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		unitNo = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(0);
		owner = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(1);
		oOName = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(2);
		category = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(3);
		status = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(4);
		usage = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(5);
		division = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(6);
		terminal = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(7);
		trailerType = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(8);
		finance = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(9);
	}

	public void fetchTrailers() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TRAILER_API, null);
					Trailer c[] = mapper.readValue(response, Trailer[].class);
					List<Trailer> tList = new ArrayList<Trailer>();
					for (Trailer Trailer : c) {
						tList.add(Trailer);
					}
					ObservableList<Trailer> data = FXCollections.observableArrayList(tList);

					setColumnValues();
					tblTrailer.setItems(data);

					tblTrailer.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
		double width = Login.width;
		int noOfColumns = tblTrailer.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tblTrailer.getColumns().get(i).setMinWidth((width / noOfColumns) + 100);
		}
		txtSearchTrailer.setLayoutX(width - (txtSearchTrailer.getPrefWidth() + btnGoTrailer.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGoTrailer.setLayoutX(width - (btnGoTrailer.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
	}

	private void setColumnValues() {

		unitNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trailer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
						return new SimpleStringProperty(param.getValue().getUnitNo() + "");
					}
				});
		owner.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trailer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
						return new SimpleStringProperty(param.getValue().getOwner() + "");
					}
				});
		oOName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trailer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
						return new SimpleStringProperty(param.getValue().getoOName() + "");
					}
				});
		category.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trailer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
						return new SimpleStringProperty(param.getValue().getCategory() + "");
					}
				});
		status.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trailer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
						return new SimpleStringProperty(param.getValue().getStatus() + "");
					}
				});
		usage.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trailer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
						return new SimpleStringProperty(param.getValue().getUsage() + "");
					}
				});
		division.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trailer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
						return new SimpleStringProperty(param.getValue().getDivision() + "");
					}
				});
		terminal.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trailer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
						return new SimpleStringProperty(param.getValue().getTerminal() + "");
					}
				});
		trailerType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trailer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
						return new SimpleStringProperty(param.getValue().getTrailerType() + "");
					}
				});
		finance.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Trailer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
						return new SimpleStringProperty(param.getValue().getFinance() + "");
					}
				});
	}

	// ADD MENU

	public int tblTrailerMenuCount = 0;

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
		if (tblTrailerMenuCount == 0) {
			tblTrailerMenuCount++;
			// When user right-click on Table
			tblTrailer.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

						if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

							if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.SECONDARY)) {
								contextMenu.show(tblTrailer, mouseEvent.getScreenX(), mouseEvent.getScreenY());
							} else if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.PRIMARY)
									&& ((MouseEvent) mouseEvent).getClickCount() == 2) {
								editTrailerAction();

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