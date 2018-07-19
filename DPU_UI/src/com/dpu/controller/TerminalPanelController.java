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
import com.dpu.model.Terminal;

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

public class TerminalPanelController extends Application implements Initializable {

	@FXML
	TableView<Terminal> tblTerminal;

	public List<Terminal> tList = null;

	@FXML
	TableColumn<Terminal, String> terminalName, location;

	public static int flag = 0;

	@FXML
	private void btnAddTerminalAction() {
		openAddTerminalScreen();
	}

	@FXML
	AnchorPane root, anchorPaneTerminal;

	@FXML
	TextField txtSearchTerminal;

	@FXML
	private void btnGoTerminalAction() {
		String searchTerminal = txtSearchTerminal.getText();
		if (searchTerminal != null && searchTerminal.length() > 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TERMINAL_API + "/" + searchTerminal + "/search",
								null);
						fillTerminal(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});

		}

		if (searchTerminal != null && searchTerminal.length() == 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TERMINAL_API,
								null);
						fillTerminal(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});

		}
	}

	List<Terminal> dList = null;

	ObjectMapper mapper = new ObjectMapper();

	@FXML
	ImageView btnGoTerminal;

	public void fillTerminal(String response) {

		try {
			ObservableList<Terminal> data = null;
			dList = new ArrayList<Terminal>();
			setColumnValues();
			if (response != null && response.length() > 0) {
				Terminal c[] = mapper.readValue(response, Terminal[].class);
				for (Terminal ccl : c) {
					dList.add(ccl);
				}
				data = FXCollections.observableArrayList(dList);
			} else {
				data = FXCollections.observableArrayList(dList);
			}
			tblTerminal.setItems(data);
			tblTerminal.setVisible(true);
		} catch (Exception e) {
			System.out.println("TerminalController: fillTerminal(): " + e.getMessage());
		}
	}

	@FXML
	private void btnEditTerminalAction() {
		flag = 2;
		Terminal terminal = tblTerminal.getSelectionModel().getSelectedItem();
		if (terminal != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TERMINAL_API + "/" + terminal.getTerminalId(),
								null);
						if (response != null && response.length() > 0) {
							Terminal c = mapper.readValue(response, Terminal.class);
							TerminalEditController terminalEditController = (TerminalEditController) openEditTerminalScreen();
							terminalEditController.initData(c);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	private void editTerminalAction() {
		flag = 1;
		Terminal terminal = tblTerminal.getSelectionModel().getSelectedItem();
		if (terminal != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TERMINAL_API + "/" + terminal.getTerminalId(),
								null);
						if (response != null && response.length() > 0) {
							Terminal c = mapper.readValue(response, Terminal.class);
							TerminalEditController terminalEditController = (TerminalEditController) openEditTerminalScreen();
							terminalEditController.initData(c);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	@FXML
	private void btnDeleteTerminalAction() {
		Terminal terminal = tblTerminal.getSelectionModel().getSelectedItem();
		if (terminal != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TERMINAL_API + "/" + terminal.getTerminalId(),
								null);
						try {
							Success success = mapper.readValue(response, Success.class);
							List<Terminal> terminalList = (List<Terminal>) success.getResultList();
							String res = mapper.writeValueAsString(terminalList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillTerminal(res);
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

	public void openAddTerminalScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.TERMINAL_BASE_PACKAGE + Iconstants.XML_TERMINAL_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.ADD_NEW_TERMINAL);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private Object openEditTerminalScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.TERMINAL_BASE_PACKAGE + Iconstants.XML_TERMINAL_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.EDIT_TERMINAL);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(root, tblTerminal);
		Login.setWidthForAll(anchorPaneTerminal, null);
		fetchTerminals();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		System.out.println("[fetchColumns]: Enter ");
		terminalName = (TableColumn<Terminal, String>) tblTerminal.getColumns().get(0);
		location = (TableColumn<Terminal, String>) tblTerminal.getColumns().get(1);
		System.out.println("[fetchColumns]: Exit ");
	}

	public void fetchTerminals() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TERMINAL_API,
							null);
					if (response != null && response.length() > 0) {
						Terminal t[] = mapper.readValue(response, Terminal[].class);
						tList = new ArrayList<Terminal>();
						for (Terminal ttl : t) {
							tList.add(ttl);
						}
						ObservableList<Terminal> data = FXCollections.observableArrayList(tList);

						setColumnValues();
						tblTerminal.setItems(data);

						tblTerminal.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
		
		double width = Login.width;
		int noOfColumns = tblTerminal.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tblTerminal.getColumns().get(i).setMinWidth(width / noOfColumns);
		}
		txtSearchTerminal.setLayoutX(width - (txtSearchTerminal.getPrefWidth() + btnGoTerminal.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGoTerminal.setLayoutX(width - (btnGoTerminal.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
	}

	private void setColumnValues() {

		terminalName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Terminal, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Terminal, String> param) {
						return new SimpleStringProperty(param.getValue().getTerminalName() + "");
					}
				});
		location.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Terminal, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Terminal, String> param) {
						return new SimpleStringProperty(param.getValue().getShipperName() + "");
					}
				});
	}

	// ADD MENU

	public int tblTerminalMenuCount = 0;

	@FXML
	public void handleAddContMouseClick(MouseEvent event) {

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
		if (tblTerminalMenuCount == 0) {
			tblTerminalMenuCount++; // When user right-click on Table
			tblTerminal.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

						if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.SECONDARY)) {
							contextMenu.show(tblTerminal, mouseEvent.getScreenX(), mouseEvent.getScreenY());
						} else if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.PRIMARY)
								&& ((MouseEvent) mouseEvent).getClickCount() == 2) {
							editTerminalAction();

						} else if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.PRIMARY)) {
							contextMenu.hide();

						}
					}

				}

			});

		}

	}
}
