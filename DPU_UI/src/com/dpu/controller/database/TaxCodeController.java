package com.dpu.controller.database;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.DeleteAPIClient;
import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.Login;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TaxCode;

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

public class TaxCodeController extends Application implements Initializable {

	@FXML
	TableView<TaxCode> tblTaxCode;

	@FXML
	TableColumn<TaxCode, String> taxCode, description, percentage;

	ObjectMapper mapper = new ObjectMapper();

	@FXML
	TextField txtSearchTaxCode;

	@FXML
	AnchorPane root, anchorPaneTaxCode;
	
	public static int flag = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(root, tblTaxCode);
		Login.setWidthForAll(anchorPaneTaxCode, null);
		fetchTaxCodes();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	@FXML
	private void btnAddTaxCodeAction() {
		openAddTaxCodeScreen();
	}

	@FXML
	private void btnGoTaxCodeAction() {
		String searchTaxCode = txtSearchTaxCode.getText();

		if (searchTaxCode != null && searchTaxCode.length() > 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TAX_CODE_API + "/" + searchTaxCode + "/search",
								null);
						fillTaxCodes(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});

		}

		if (searchTaxCode != null && searchTaxCode.length() == 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TAX_CODE_API,
								null);
						fillTaxCodes(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	List<TaxCode> TaxCodes = null;

	@FXML
	ImageView btnGoTaxCode;

	/*
	 * public void newMethod() {
	 * txtSearchTaxCode.addEventFilter(KeyEvent.KEY_PRESSED, new
	 * EventHandler<KeyEvent>() {
	 * 
	 * @Override public void handle(KeyEvent event) { if (event.getCode() ==
	 * KeyCode.TAB) {
	 * 
	 * } } }); }
	 */

	public void fillTaxCodes(String response) {

		try {
			TaxCodes = new ArrayList<TaxCode>();
			setColumnValues();
			ObservableList<TaxCode> data = null;
			if (response != null && response.length() > 0) {
				TaxCode c[] = mapper.readValue(response, TaxCode[].class);
				for (TaxCode ccl : c) {
					TaxCodes.add(ccl);
				}
				data = FXCollections.observableArrayList(TaxCodes);

			} else {
				data = FXCollections.observableArrayList(TaxCodes);
			}
			tblTaxCode.setItems(data);

			tblTaxCode.setVisible(true);
		} catch (Exception e) {
			System.out.println("TaxCodeController: fillTaxCodes(): " + e.getMessage());
		}
	}

	@FXML
	private void btnEditTaxCodeAction() {
		flag = 2;
		TaxCode dpuTaxCode = TaxCodes.get(tblTaxCode.getSelectionModel().getSelectedIndex());
		if (dpuTaxCode != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TAX_CODE_API + "/" + dpuTaxCode.getTaxCodeId(),
								null);
						if (response != null && response.length() > 0) {
							TaxCode c = mapper.readValue(response, TaxCode.class);
							TaxCodeEditController taxCodeEditController = (TaxCodeEditController) openEditTaxCodeScreen();
							taxCodeEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private void editTaxCodeAction() {
		flag = 1;
		TaxCode dpuTaxCode = TaxCodes.get(tblTaxCode.getSelectionModel().getSelectedIndex());
		if (dpuTaxCode != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TAX_CODE_API + "/" + dpuTaxCode.getTaxCodeId(),
								null);
						if (response != null && response.length() > 0) {
							TaxCode c = mapper.readValue(response, TaxCode.class);
							TaxCodeEditController taxCodeEditController = (TaxCodeEditController) openEditTaxCodeScreen();
							taxCodeEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private Object openEditTaxCodeScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.TAX_CODE_BASE_PACKAGE + Iconstants.XML_TAX_CODE_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.EDIT_TAX_CODE);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return null;
	}

	public static void openAddTaxCodeScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(TaxCodeController.class.getClassLoader()
					.getResource(Iconstants.TAX_CODE_BASE_PACKAGE + Iconstants.XML_TAX_CODE_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.ADD_NEW_TAX_CODE);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		taxCode = (TableColumn<TaxCode, String>) tblTaxCode.getColumns().get(0);
		description = (TableColumn<TaxCode, String>) tblTaxCode.getColumns().get(1);
		percentage = (TableColumn<TaxCode, String>) tblTaxCode.getColumns().get(2);
	}

	public void fetchTaxCodes() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TAX_CODE_API,
							null);
					TaxCode s[] = mapper.readValue(response, TaxCode[].class);
					TaxCodes = new ArrayList<TaxCode>();
					for (TaxCode ccl : s) {
						TaxCodes.add(ccl);
					}
					ObservableList<TaxCode> data = FXCollections.observableArrayList(TaxCodes);

					setColumnValues();
					tblTaxCode.setItems(data);

					tblTaxCode.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
		
		double width = Login.width;
		int noOfColumns = tblTaxCode.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tblTaxCode.getColumns().get(i).setMinWidth(width / noOfColumns);
		}
		txtSearchTaxCode.setLayoutX(width - (txtSearchTaxCode.getPrefWidth() + btnGoTaxCode.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGoTaxCode.setLayoutX(width - (btnGoTaxCode.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
	}

	@FXML
	private void btnDeleteTaxCodeAction() {
		TaxCode TaxCode = tblTaxCode.getSelectionModel().getSelectedItem();
		if (TaxCode != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_TAX_CODE_API + "/" + TaxCode.getTaxCodeId(),
								null);
						// fillTaxCodes(response);
						try {
							Success success = mapper.readValue(response, Success.class);
							List<TaxCode> TaxCodeList = (List<TaxCode>) success.getResultList();
							String res = mapper.writeValueAsString(TaxCodeList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillTaxCodes(res);
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
						 * failed.getMessage(), "Info", 1); } fetchTaxCodes();
						 */
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	private void setColumnValues() {

		taxCode.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TaxCode, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<TaxCode, String> param) {
						return new SimpleStringProperty(param.getValue().getTaxCode() + "");
					}
				});
		description.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TaxCode, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<TaxCode, String> param) {
						return new SimpleStringProperty(param.getValue().getDescription() + "");
					}
				});
		percentage.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TaxCode, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<TaxCode, String> param) {
						return new SimpleStringProperty(param.getValue().getPercentage() + "");
					}
				});

	}

	TaxCode temp;
	Date lastClickTime;

	@FXML
	private void handleRowSelect() {
		tblTaxCode.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

					System.out.println("clicked:::::::::::::");
				}
			}
		});
	}

	// ADD MENU

	public int tblTaxCoderMenuCount = 0;

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

		// Add MenuItem to ContextMenu contextMenu.getItems().addAll(item1,
		contextMenu.getItems().addAll(item1, item2, item3, item4, item5, item6);
		if (tblTaxCoderMenuCount == 0) {
			tblTaxCoderMenuCount++; // When user right-click on Table
			tblTaxCode.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

						if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

							if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.SECONDARY)) {
								contextMenu.show(tblTaxCode, mouseEvent.getScreenX(), mouseEvent.getScreenY());
							} else if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.PRIMARY)
									&& ((MouseEvent) mouseEvent).getClickCount() == 2) {
								editTaxCodeAction();

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