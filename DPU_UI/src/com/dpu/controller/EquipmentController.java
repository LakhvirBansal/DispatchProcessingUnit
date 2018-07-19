package com.dpu.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.DeleteAPIClient;
import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Equipment;
import com.dpu.model.Failed;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EquipmentController extends Application implements Initializable {

	ObjectMapper mapper = new ObjectMapper();

	@FXML
	TextField txtSearchEquipment;

	@FXML
	TableView<Equipment> tblEquipment;

	List<Equipment> equipments = null;
	public static int flag = 0;

	@FXML
	TableColumn<Equipment, String> name, type, description;

	@FXML
	AnchorPane anchorPaneEquipment, root;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// final ProgressIndicator progressIndicator = new ProgressIndicator(0);
		Login.setWidthForAll(root, tblEquipment);
		Login.setWidthForAll(anchorPaneEquipment, null);
		fetchEquipments();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		name = (TableColumn<Equipment, String>) tblEquipment.getColumns().get(0);
		type = (TableColumn<Equipment, String>) tblEquipment.getColumns().get(1);
		description = (TableColumn<Equipment, String>) tblEquipment.getColumns().get(2);
	}

	@FXML
	private void btnAddEquipmentAction() {
		openAddEquipmentScreen();
	}

	@FXML
	private void handleRowSelect() {
		tblEquipment.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					flag = 1;
					// System.out.println("clicked:::::::::::::");
					Equipment equipment = equipments.get(tblEquipment.getSelectionModel().getSelectedIndex());
					if (equipment != null) {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								try {
									ObjectMapper mapper = new ObjectMapper();
									String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER
											+ Iconstants.URL_EQUIPMENT_API + "/" + equipment.getEquipmentId(), null);
									System.out.println("EE:: " + response);
									if (response != null && response.length() > 0) {
										Equipment e = mapper.readValue(response, Equipment.class);
										EquipmentEditController equipmentEditController = (EquipmentEditController) openEditEquipmentScreen();
										equipmentEditController.initData(e);
									}
								} catch (Exception e) {
									e.printStackTrace();
									JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
								}
							}
						});
					}
				}
			}
		});
	}

	private void openAddEquipmentScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.EQUIPMENT_BASE_PACKAGE + Iconstants.XML_EQUIPMENT_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.ADD_NEW_EQUIPMENT);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@FXML
	private void btnDeleteEquipmentAction() {
		Equipment equipment = tblEquipment.getSelectionModel().getSelectedItem();
		if (equipment != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API + "/" + equipment.getEquipmentId(),
								null);
						/*
						 * if(response != null && response.contains("message"))
						 * { Success success = mapper.readValue(response,
						 * Success.class); JOptionPane.showMessageDialog(null,
						 * success.getMessage() , "Info", 1); } else { Failed
						 * failed = mapper.readValue(response, Failed.class);
						 * JOptionPane.showMessageDialog(null,
						 * failed.getMessage(), "Info", 1); }
						 */
						try {
							Success success = mapper.readValue(response, Success.class);
							List<Equipment> equipmentList = (List<Equipment>) success.getResultList();
							String res = mapper.writeValueAsString(equipmentList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillEquipments(res);
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

	@FXML
	private void btnGoEquipmentAction() {
		String searchEquipment = txtSearchEquipment.getText();
		if (searchEquipment != null && searchEquipment.length() > 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API
								+ "/" + searchEquipment + "/search", null);
						fillEquipments(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});

		}

		if (searchEquipment != null && searchEquipment.length() == 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API,
								null);
						fillEquipments(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});

		}
	}

	@FXML
	private void btnEditEquipmentAction() {
		flag = 2;
		Equipment equipment = equipments.get(tblEquipment.getSelectionModel().getSelectedIndex());
		if (equipment != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API + "/" + equipment.getEquipmentId(),
								null);
						System.out.println("EE:: " + response);
						if (response != null && response.length() > 0) {
							Equipment e = mapper.readValue(response, Equipment.class);
							EquipmentEditController equipmentEditController = (EquipmentEditController) openEditEquipmentScreen();
							// equipmentEditController.initData(e);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private Object openEditEquipmentScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.EQUIPMENT_BASE_PACKAGE + Iconstants.XML_EQUIPMENT_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.EDIT_EQUIPMENT);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@FXML
	AnchorPane equipmentParentAnchorPane;

	@FXML
	ImageView btnGoEquipment;

	public void fetchEquipments() {

		fetchColumns();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					// long startTime = new Date().getTime();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API,
							null);
					fillEquipments(response);
					/*
					 * System.out.println(response); if(response != null &&
					 * response.length() > 0) { Equipment c[] =
					 * mapper.readValue(response, Equipment[].class); equipments
					 * = new ArrayList<Equipment>(); for(Equipment ccl : c) {
					 * equipments.add(ccl); } ObservableList<Equipment> data =
					 * FXCollections.observableArrayList(equipments);
					 * 
					 * setColumnValues(); tblEquipment.setItems(data);
					 * 
					 * 
					 * tblEquipment.setVisible(true);
					 * System.out.println("Time to fetch equipments: " + (new
					 * Date().getTime() - startTime)); }
					 */
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}
		});
		
		double width = Login.width;
		int noOfColumns = tblEquipment.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tblEquipment.getColumns().get(i).setMinWidth(width / noOfColumns);
		}
		txtSearchEquipment.setLayoutX(width - (txtSearchEquipment.getPrefWidth() + btnGoEquipment.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGoEquipment.setLayoutX(width - (btnGoEquipment.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));

	}

	public void fillEquipments(String response) {

		try {
			ObservableList<Equipment> data = null;
			equipments = new LinkedList<Equipment>();
			setColumnValues();
			if (response != null && response.length() > 0) {
				Equipment c[] = mapper.readValue(response, Equipment[].class);
				for (Equipment ccl : c) {
					System.out.println(ccl.getEquipmentName());
					equipments.add(ccl);
				}
				data = FXCollections.observableArrayList(equipments);
			} else {
				data = FXCollections.observableArrayList(equipments);
			}
			tblEquipment.setItems(data);
			tblEquipment.setVisible(true);
		} catch (Exception e) {
			System.out.println("EquipmentController: fillEquipments(): " + e.getMessage());
		}
	}

	private void setColumnValues() {

		name.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Equipment, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Equipment, String> param) {
						return new SimpleStringProperty(param.getValue().getEquipmentName() + "");
					}
				});
		type.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Equipment, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Equipment, String> param) {
						return new SimpleStringProperty(param.getValue().getType() + "");
					}
				});
		description.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Equipment, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Equipment, String> param) {
						return new SimpleStringProperty(param.getValue().getDescription() + "");
					}
				});
	}
	// ADD MENU

	@FXML
	Label lblName, lblType, lblDescription;

	// tblEquipment.getScene().getStylesheets().add(Iconstants.EQUIPMENT_BASE_PACKAGE
	// + "sample.css");
	// lblName.setStyle("-fx-text-fill: red;");
	/*
	 * selectedCells.addListener(new ListChangeListener<TablePosition>() {
	 * 
	 * @Override public void onChanged(Change change) { for (TablePosition pos :
	 * selectedCells) {
	 * System.out.println("Cell selected in row "+pos.getRow()+" and column "+
	 * pos.getColumn()); } } });
	 */
	/*
	 * System.out.println("inside table");
	 * lblName.addEventHandler(MouseEvent.MOUSE_CLICKED, new
	 * EventHandler<MouseEvent>() { public void handle(MouseEvent e) {
	 * System.out.println("1111111111");
	 * tblEquipment.getScene().getStylesheets().add(Iconstants.
	 * EQUIPMENT_BASE_PACKAGE + "sample.css");
	 * lblName.setStyle("-fx-text-fill: red;");
	 * 
	 * } }); lblName.setOnMouseClicked(new EventHandler<MouseEvent>() { public
	 * void handle(MouseEvent mouseEvent) { System.out.println("2222222222222");
	 * if(mouseEvent.getButton() == MouseButton.PRIMARY) {
	 * tblEquipment.getScene().getStylesheets().add(Iconstants.
	 * EQUIPMENT_BASE_PACKAGE + "sample.css");
	 * lblName.setStyle("-fx-text-fill: red;"); } } });
	 * lblType.setOnMouseClicked(new EventHandler<Event>() { public void
	 * handle(Event event) {
	 * tblEquipment.getScene().getStylesheets().add(Iconstants.
	 * EQUIPMENT_BASE_PACKAGE + "sample.css");
	 * lblType.setStyle("-fx-text-fill: red;");
	 * 
	 * }; });
	 */
	/*
	 * TableColumn<Equipment, ?> col1 = columns.get(0);
	 * col1.addEventHandler(MouseEvent.MOUSE_CLICKED, new
	 * EventHandler<MouseEvent>() {
	 * 
	 * @Override public void handle(MouseEvent event) {
	 * System.out.println("cell clicked!"); } });
	 */

	/*
	 * final ObservableList<TablePosition> selectedCells =
	 * tblEquipment.getSelectionModel().getSelectedCells();
	 * 
	 * selectedCells.addListener(new ListChangeListener<TablePosition>() {
	 * 
	 * @Override public void onChanged(Change change) {
	 * System.out.println("gggggg"); for (TablePosition pos : selectedCells) {
	 * tblEquipment.getScene().getStylesheets().add(Iconstants.
	 * EQUIPMENT_BASE_PACKAGE + "sample.css"); switch (pos.getColumn()) { case
	 * 0: lblName.setStyle("-fx-text-fill: red;"); break; case 1:
	 * lblType.setStyle("-fx-text-fill: red;"); break; case 2:
	 * lblDescription.setStyle("-fx-text-fill: red;"); break; default: break; }
	 * } } });
	 */

	// tblEquipment.setOnMouseClicked(new EventHandler<MouseEvent>(){

	// @Override
	// public void handle(MouseEvent event){
	// System.out.println("Here 1");

	// }
	// });
	/*
	 * lblType.setOnMouseClicked(new EventHandler<MouseEvent>(){
	 * 
	 * @Override public void handle(MouseEvent event){
	 * System.out.println("Here 2");
	 * tblEquipment.getScene().getStylesheets().add(Iconstants.
	 * EQUIPMENT_BASE_PACKAGE + "sample.css");
	 * lblType.setStyle("-fx-text-fill: red;"); } });
	 * lblDescription.setOnMouseClicked(new EventHandler<MouseEvent>(){
	 * 
	 * @Override public void handle(MouseEvent event){
	 * System.out.println("Here 3");
	 * tblEquipment.getScene().getStylesheets().add(Iconstants.
	 * EQUIPMENT_BASE_PACKAGE + "sample.css");
	 * lblDescription.setStyle("-fx-text-fill: red;"); } });
	 */

	String selectedColumnName = null;
	boolean searchByHeader = false;

	@FXML
	private void lblNameMouseClick(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			searchByHeader = true;
			selectedColumnName = lblName.getText();
			lblName.setStyle("-fx-text-fill: red;");
			lblType.setStyle("-fx-text-fill: black;");
		}
	}

	@FXML
	private void anchorPaneKeyTyped(KeyEvent event) {
		if (searchByHeader) {
			String key = event.getText();
			// System.out.println(key);
			// final KeyCombination keyComb1=new
			// KeyCodeCombination(KeyCode.I,KeyCombination.CONTROL_DOWN);
			if (key != null && key.length() > 0) {
				// String keyTyped = event.getCharacter();
				// System.out.println(key);
				search(key, selectedColumnName);
			}
		}
	}

	private void search(String value, String columnName) {
		for (int i = 0; i < equipments.size(); i++) {
			Equipment equipment = equipments.get(i);
			if (selectedColumnName.equals(columnName) && equipment.getEquipmentName().startsWith(value)) {
				// System.out.println("index: " + i + " equipmentName: " +
				// equipment.getEquipmentName() + " columnName: " + columnName +
				// " header/boolean: " + searchByHeader + " valuekeytyped: " +
				// value);
				tblEquipment.getSelectionModel().select(equipment);
				break;
			}
		}
	}

	@FXML
	private void lblTypeMouseClick(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			lblName.setStyle("-fx-text-fill: black;");
			lblType.setStyle("-fx-text-fill: red;");
		}
	}

	/*
	 * public int tblEquipmentMenuCount = 0;
	 * 
	 * @FXML public void handleAddContMouseClick(MouseEvent event) {
	 * 
	 * // Create ContextMenu ContextMenu contextMenu = new ContextMenu();
	 * 
	 * MenuItem item1 = new-------------- MenuItem("ADD"); item1.setOnAction(new
	 * EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent event) { }
	 * 
	 * }); MenuItem item2 = new MenuItem("EDIT"); item2.setOnAction(new
	 * EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent event) {
	 * 
	 * } });
	 * 
	 * MenuItem item3 = new MenuItem("DELETE"); item3.setOnAction(new
	 * EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent event) {
	 * 
	 * } });
	 * 
	 * MenuItem item4 = new MenuItem("PERSONALIZE"); item1.setOnAction(new
	 * EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent event) { }
	 * 
	 * }); MenuItem item5 = new MenuItem("DUPLICATE"); item2.setOnAction(new
	 * EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent event) {
	 * 
	 * } });
	 * 
	 * MenuItem item6 = new MenuItem("FILTER BY"); item3.setOnAction(new
	 * EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent event) {
	 * 
	 * } });
	 * 
	 * 
	 * 
	 * // Add MenuItem to ContextMenu contextMenu.getItems().addAll(item1,
	 * item2, item3, item4, item5, item6); if (tblEquipmentMenuCount == 0) {
	 * tblEquipmentMenuCount++; // When user right-click on Table
	 * tblEquipment.setOnContextMenuRequested(new
	 * EventHandler<ContextMenuEvent>() {
	 * 
	 * @Override public void handle(ContextMenuEvent event) {
	 * contextMenu.show(tblEquipment, event.getScreenX(), event.getScreenY());
	 * 
	 * }
	 * 
	 * });
	 * 
	 * }
	 * 
	 * }
	 */
}