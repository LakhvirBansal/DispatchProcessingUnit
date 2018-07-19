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
import com.dpu.model.Division;
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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DivisionController extends Application implements Initializable {

	@FXML
	TableView<Division> tblDivision;

	public List<Division> divisionList = null;

	@FXML
	TableColumn<Division, String> code, name, fedralaccount, provincialaccount;

	public static int flag = 0;

	@FXML
	private void btnAddDivisionAction() {
		openAddDivisionScreen();
	}

	@FXML
	private void handleRowSelect() {
		tblDivision.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					flag = 1;
					Division division = tblDivision.getSelectionModel().getSelectedItem();
					if (division != null) {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								try {
									ObjectMapper mapper = new ObjectMapper();
									String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER
											+ Iconstants.URL_DIVISION_API + "/" + division.getDivisionId(), null);
									System.out.println("byid response: division: " + response);
									if (response != null && response.length() > 0) {

										Division division = mapper.readValue(response, Division.class);
										DivisionEditController divisionEditController = (DivisionEditController) openEditDivisionScreen();
										divisionEditController.initData(division);
									}
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
								}
							}
						});
					}

				}
			}
		});
	}

	@FXML
	private void btnEditDivisionAction() {
		flag = 2;
		Division division = tblDivision.getSelectionModel().getSelectedItem();
		if (division != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_DIVISION_API + "/" + division.getDivisionId(),
								null);
						System.out.println("byid response: division: " + response);
						if (response != null && response.length() > 0) {

							Division division = mapper.readValue(response, Division.class);
							DivisionEditController divisionEditController = (DivisionEditController) openEditDivisionScreen();
							divisionEditController.initData(division);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	@FXML
	TextField txtSearchDivision;

	@FXML
	private void btnGoDivisionAction() {
		String search = txtSearchDivision.getText();
		if (search != null && search.length() > 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_DIVISION_API + "/" + search + "/search", null);
						fillDivisions(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});

		}

		if (search != null && search.length() == 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_DIVISION_API,
								null);
						fillDivisions(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});

		}
	}

	@FXML
	private void btnDeleteDivisionAction() {
		Division division = tblDivision.getSelectionModel().getSelectedItem();
		if (division != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_DIVISION_API + "/" + division.getDivisionId(),
								null);

						try {
							Success success = mapper.readValue(response, Success.class);
							List<Division> divisionList = (List<Division>) success.getResultList();
							String res = mapper.writeValueAsString(divisionList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillDivisions(res);
						} catch (Exception e) {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage());
						}
						/*
						 * if (response != null && response.contains("message"))
						 * { Success success = mapper.readValue(response,
						 * Success.class); JOptionPane.showMessageDialog(null,
						 * success.getMessage(), "Info", 1); } else { Failed
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

	private void openAddDivisionScreen() {
		System.out.println("openAddDivisionScreen");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.DIVISION_BASE_PACKAGE + Iconstants.XML_DIVISION_ADD_SCREEN));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.ADD_NEW_DIVISION);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private Object openEditDivisionScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.DIVISION_BASE_PACKAGE + Iconstants.XML_DIVISION_EDIT_SCREEN));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.EDIT_DIVISION);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	List<Division> divisions = null;

	ObjectMapper mapper = new ObjectMapper();

	public void fillDivisions(String response) {
		System.out.println(response + " ::: in fill Division:");
		try {
			divisions = new ArrayList<Division>();
			setColumnValues();
			ObservableList<Division> data = null;
			if (response != null && response.length() > 0) {
				Division c[] = mapper.readValue(response, Division[].class);
				for (Division ccl : c) {
					divisions.add(ccl);
				}
				System.out.println("division di list: " + divisions.size());
				data = FXCollections.observableArrayList(divisions);
			} else {
				data = FXCollections.observableArrayList(divisions);
			}
			tblDivision.setItems(data);
			tblDivision.setVisible(true);
		} catch (Exception e) {
			System.out.println("DivisionController: fillDivisions(): " + e.getMessage());
		}
	}

	@FXML
	Pane root;

	@FXML
	AnchorPane anchorPaneDivision;

	@FXML
	ImageView btnGoDivision;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(root, tblDivision);
		Login.setWidthForAll(anchorPaneDivision, null);
		fetchDivisions();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		code = (TableColumn<Division, String>) tblDivision.getColumns().get(0);
		name = (TableColumn<Division, String>) tblDivision.getColumns().get(1);
		fedralaccount = (TableColumn<Division, String>) tblDivision.getColumns().get(2);
		provincialaccount = (TableColumn<Division, String>) tblDivision.getColumns().get(3);
	}

	public void fetchDivisions() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_DIVISION_API,
							null);
					if (response != null && response.length() > 0) {
						Division d[] = mapper.readValue(response, Division[].class);
						divisionList = new ArrayList<Division>();
						for (Division dl : d) {
							divisionList.add(dl);
						}
						ObservableList<Division> data = FXCollections.observableArrayList(divisionList);

						setColumnValues();
						tblDivision.setItems(data);

						tblDivision.setVisible(true);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
		
		double width = Login.width;
		int noOfColumns = tblDivision.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tblDivision.getColumns().get(i).setMinWidth((width / noOfColumns) + Iconstants.MIN_WIDTH_COLUMN);
		}
		txtSearchDivision.setLayoutX(width - (txtSearchDivision.getPrefWidth() + btnGoDivision.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGoDivision.setLayoutX(width - (btnGoDivision.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
	}

	private void setColumnValues() {

		code.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Division, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Division, String> param) {
						return new SimpleStringProperty(param.getValue().getDivisionCode() + "");
					}
				});
		name.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Division, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Division, String> param) {
						return new SimpleStringProperty(param.getValue().getDivisionName() + "");
					}
				});

		fedralaccount.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Division, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Division, String> param) {
						return new SimpleStringProperty(param.getValue().getFedral() + "");
					}
				});
		provincialaccount.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Division, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Division, String> param) {
						return new SimpleStringProperty(param.getValue().getProvincial() + "");
					}
				});
	}

	// ADD MENU

	public int tblDivisionMenuCount = 0;

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
		if (tblDivisionMenuCount == 0) {
			tblDivisionMenuCount++;
			// When user right-click on Table
			tblDivision.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
				@Override
				public void handle(ContextMenuEvent event) {
					contextMenu.show(tblDivision, event.getScreenX(), event.getScreenY());

				}

			});

		}

	}
}