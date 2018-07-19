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
import com.dpu.model.Failed;
import com.dpu.model.HandlingModel;
import com.dpu.model.Success;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HandlingController extends Application implements Initializable {

	@FXML
	TableView<HandlingModel> tblHandling;

	@FXML
	TableColumn<HandlingModel, String> handling;

	ObjectMapper mapper = new ObjectMapper();

	@FXML
	TextField txtSearchHandling;

	public static int flag = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(root, tblHandling);
		Login.setWidthForAll(anchorPaneHandling, null);
		fetchHandling();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	@FXML
	private void btnAddHandlingAction() {
		openAddHandlingScreen();
	}

	@FXML
	AnchorPane anchorPaneHandling, root;

	@FXML
	private void btnGoHandlingAction() {
		String searchHandling = txtSearchHandling.getText();

		if (searchHandling != null && searchHandling.length() > 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_HANDLING_API + "/" + searchHandling + "/search",
								null);
						fillHandling(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});

		}

		if (searchHandling != null && searchHandling.length() == 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_HANDLING_API,
								null);
						fillHandling(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	List<HandlingModel> handlings = null;

	@FXML
	ImageView btnGoHandling;

	public void fillHandling(String response) {

		try {
			handlings = new ArrayList<HandlingModel>();
			setColumnValues();
			ObservableList<HandlingModel> data = null;
			if (response != null && response.length() > 0) {
				HandlingModel c[] = mapper.readValue(response, HandlingModel[].class);
				for (HandlingModel ccl : c) {
					handlings.add(ccl);
				}
				data = FXCollections.observableArrayList(handlings);

			} else {
				data = FXCollections.observableArrayList(handlings);
			}
			tblHandling.setItems(data);

			tblHandling.setVisible(true);
		} catch (Exception e) {
			System.out.println("HandlingController: fillHandling(): " + e.getMessage());
		}
	}

	@FXML
	private void btnEditHandlingAction() {
		flag = 2;
		HandlingModel handling = handlings.get(tblHandling.getSelectionModel().getSelectedIndex());
		if (handling != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_HANDLING_API + "/" + handling.getId(), null);
						if (response != null && response.length() > 0) {
							HandlingModel c = mapper.readValue(response, HandlingModel.class);
							HandlingEditController handlingEditController = (HandlingEditController) openEditHandlingScreen();
							handlingEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private Object openEditHandlingScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.HANDLING_BASE_PACKAGE + Iconstants.XML_HANDLING_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.EDIT_HANDLING);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public static void openAddHandlingScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(HandlingController.class.getClassLoader()
					.getResource(Iconstants.HANDLING_BASE_PACKAGE + Iconstants.XML_HANDLING_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.ADD_NEW_HANDLING);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		handling = (TableColumn<HandlingModel, String>) tblHandling.getColumns().get(0);
	}

	public void fetchHandling() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_HANDLING_API,
							null);
					HandlingModel s[] = mapper.readValue(response, HandlingModel[].class);
					handlings = new ArrayList<HandlingModel>();
					for (HandlingModel ccl : s) {
						handlings.add(ccl);
					}
					ObservableList<HandlingModel> data = FXCollections.observableArrayList(handlings);

					setColumnValues();
					tblHandling.setItems(data);

					tblHandling.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
		
		double width = Login.width;
		int noOfColumns = tblHandling.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tblHandling.getColumns().get(i).setMinWidth(width / noOfColumns);
		}
		txtSearchHandling.setLayoutX(width - (txtSearchHandling.getPrefWidth() + btnGoHandling.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGoHandling.setLayoutX(width - (btnGoHandling.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
	}

	@FXML
	private void btnDeleteHandlingAction() {
		HandlingModel handling = tblHandling.getSelectionModel().getSelectedItem();
		if (handling != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_HANDLING_API + "/" + handling.getId(), null);
						try {
							Success success = mapper.readValue(response, Success.class);
							List<HandlingModel> handlingList = (List<HandlingModel>) success.getResultList();
							String res = mapper.writeValueAsString(handlingList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillHandling(res);
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

		handling.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<HandlingModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<HandlingModel, String> param) {
						return new SimpleStringProperty(param.getValue().getName() + "");
					}
				});
	}

	// ADD MENU

	public int tblServicerMenuCount = 0;

	@FXML
	public void handleAddContMouseClick(MouseEvent event) {

		tblHandling.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					flag = 1;
					HandlingModel handling = handlings.get(tblHandling.getSelectionModel().getSelectedIndex());
					if (handling != null) {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								try {
									ObjectMapper mapper = new ObjectMapper();
									String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER
											+ Iconstants.URL_HANDLING_API + "/" + handling.getId(), null);
									if (response != null && response.length() > 0) {
										HandlingModel c = mapper.readValue(response, HandlingModel.class);
										HandlingEditController handlingEditController = (HandlingEditController) openEditHandlingScreen();
										handlingEditController.initData(c);
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

		// Create ContextMenu ContextMenu contextMenu = new ContextMenu();

		// MenuItem item1 = new MenuItem("ADD"); item1.setOnAction(new
		// EventHandler<ActionEvent>() {
		//
		// @Override public void handle(ActionEvent event) { }
		//
		// }); MenuItem item2 = new MenuItem("EDIT"); item2.setOnAction(new
		// EventHandler<ActionEvent>() {
		//
		// @Override public void handle(ActionEvent event) {
		//
		// } });
		//
		// MenuItem item3 = new MenuItem("DELETE"); item3.setOnAction(new
		// EventHandler<ActionEvent>() {
		//
		// @Override public void handle(ActionEvent event) {
		//
		// } });
		//
		// MenuItem item4 = new MenuItem("PERSONALIZE"); item1.setOnAction(new
		// EventHandler<ActionEvent>() {
		//
		// @Override public void handle(ActionEvent event) { }
		//
		// }); MenuItem item5 = new MenuItem("DUPLICATE"); item2.setOnAction(new
		// EventHandler<ActionEvent>() {
		//
		// @Override public void handle(ActionEvent event) {
		//
		// } });
		//
		// MenuItem item6 = new MenuItem("FILTER BY"); item3.setOnAction(new
		// EventHandler<ActionEvent>() {
		//
		// @Override public void handle(ActionEvent event) {
		//
		// } });
		//
		//
		//
		// // Add MenuItem to ContextMenu contextMenu.getItems().addAll(item1,
		// item2, item3, item4, item5, item6); if (tblServicerMenuCount == 0) {
		// tblServicerMenuCount++; // When user right-click on Table
		// tblHandling.setOnContextMenuRequested(new
		// EventHandler<ContextMenuEvent>() {
		//
		// @Override public void handle(ContextMenuEvent event) {
		// contextMenu.show(tblHandling, event.getScreenX(),
		// event.getScreenY());
		//
		// }
		//
		// });
		//
		// }
		//
	}

}