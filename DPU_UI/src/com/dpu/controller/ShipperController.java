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
import com.dpu.model.Shipper;
import com.dpu.model.Success;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.ScrollPane;
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

public class ShipperController extends Application implements Initializable {

	@FXML
	TableView<Shipper> tblShipper;

	@FXML
	TableColumn<Shipper, String> company, address, unit, city, ps, phone, prefix, tollfree, plant, cellNumber, email,
			importer;

	@FXML
	AnchorPane anchorPaneShipper, root, secondOuterAnchorPane;

	public static int flag = 0;

	@FXML
	ImageView btnGoShipper;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(root, tblShipper);
		Login.setWidthForAll(anchorPaneShipper, null);
		Login.setWidthForAll(scrollPane, null);
		Login.setWidthForAll(secondOuterAnchorPane, null);
		fetchShippers();
//		anchorPaneShipper.set
	}

	@Override
	public void start(Stage primaryStage) {
	}

	@FXML
	private void btnAddShipperAction() {
		openAddShipperScreen();
	}

	@FXML
	private void btnEditShipperAction() {
		flag = 2;
		Shipper shipper = tblShipper.getSelectionModel().getSelectedItem();
		System.out.println(shipper);
		if (shipper != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API + "/" + shipper.getShipperId(),
								null);
						if (response != null && response.length() > 0) {
							Shipper c = mapper.readValue(response, Shipper.class);
							ShipperEditController shipperEditController = (ShipperEditController) openEditShipperScreen();
							shipperEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private void editShipperAction() {
		flag = 1;
		Shipper shipper = tblShipper.getSelectionModel().getSelectedItem();
		System.out.println(shipper);
		if (shipper != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API + "/" + shipper.getShipperId(),
								null);
						if (response != null && response.length() > 0) {
							Shipper c = mapper.readValue(response, Shipper.class);
							ShipperEditController shipperEditController = (ShipperEditController) openEditShipperScreen();
							shipperEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private Object openEditShipperScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.SHIPPER_BASE_PACKAGE + Iconstants.XML_SHIPPER_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.EDIT_SHIPPER);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@FXML
	private void btnDeleteShipperAction() {
		Shipper shipper = tblShipper.getSelectionModel().getSelectedItem();
		if (shipper != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API + "/" + shipper.getShipperId(),
								null);
						try {
							Success success = mapper.readValue(response, Success.class);
							List<Shipper> shipperList = (List<Shipper>) success.getResultList();
							String res = mapper.writeValueAsString(shipperList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillShippers(res);
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

	public static void openAddShipperScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(ShipperController.class.getClassLoader()
					.getResource(Iconstants.SHIPPER_BASE_PACKAGE + Iconstants.XML_SHIPPER_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.ADD_NEW_SHIPPER);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		company = (TableColumn<Shipper, String>) tblShipper.getColumns().get(0);
		address = (TableColumn<Shipper, String>) tblShipper.getColumns().get(1);
		unit = (TableColumn<Shipper, String>) tblShipper.getColumns().get(2);
		city = (TableColumn<Shipper, String>) tblShipper.getColumns().get(3);
		ps = (TableColumn<Shipper, String>) tblShipper.getColumns().get(4);
		phone = (TableColumn<Shipper, String>) tblShipper.getColumns().get(5);
		prefix = (TableColumn<Shipper, String>) tblShipper.getColumns().get(6);
		tollfree = (TableColumn<Shipper, String>) tblShipper.getColumns().get(7);
		plant = (TableColumn<Shipper, String>) tblShipper.getColumns().get(8);
		cellNumber = (TableColumn<Shipper, String>) tblShipper.getColumns().get(9);
		email = (TableColumn<Shipper, String>) tblShipper.getColumns().get(10);
		importer = (TableColumn<Shipper, String>) tblShipper.getColumns().get(11);
	}

	@FXML
	ScrollPane scrollPane;
	
	public void fetchShippers() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API, null);
					Shipper s[] = mapper.readValue(response, Shipper[].class);
					List<Shipper> cList = new ArrayList<Shipper>();
					for (Shipper ccl : s) {
						cList.add(ccl);
					}
					ObservableList<Shipper> data = FXCollections.observableArrayList(cList);

					setColumnValues();
					tblShipper.setItems(data);

					tblShipper.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.. " + e, "Info", 1);
				}
			}
		});
		
		double width = Login.width;
		int noOfColumns = tblShipper.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tblShipper.getColumns().get(i).setMinWidth((width / noOfColumns) + Iconstants.MIN_WIDTH_COLUMN);
		}
//		System.out.println(Login.height + " height at shipper" );
//		tblShipper.setPrefHeight(Login.stage.getHeight() - Iconstants.TABLE_VIEW_HEIGHT);
//		scrollPane.setFitToHeight(false);
		txtSearchShipper.setLayoutX(width - (txtSearchShipper.getPrefWidth() + btnGoShipper.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGoShipper.setLayoutX(width - (btnGoShipper.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		System.out.println("Table di height: " + tblShipper.getMaxHeight() + " : " + tblShipper.getMinHeight() + " : " + tblShipper.getPrefHeight());
		Login.scene.widthProperty().addListener(new ChangeListener<Number>() {
	        	
	        	@Override 
	        	public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
	        		double width = (double) newSceneWidth;
	        		root.setMaxWidth(width);
	        		anchorPaneShipper.setMaxWidth(width);
	        		tblShipper.setMaxWidth(width);
	        		scrollPane.setMaxWidth(width);
	        		secondOuterAnchorPane.setMaxWidth(width);
	        		txtSearchShipper.setLayoutX(width - (txtSearchShipper.getPrefWidth() + btnGoShipper.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
	        		btnGoShipper.setLayoutX(width - (btnGoShipper.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
	        		
	        		width = Login.width;
	        		int noOfColumns = tblShipper.getColumns().size();
	        		for (int i = 0; i < noOfColumns; i++) {
	        			tblShipper.getColumns().get(i).setMinWidth((width / noOfColumns) + Iconstants.MIN_WIDTH_COLUMN);
	        		}
	        	}

     	});
     	Login.scene.heightProperty().addListener(new ChangeListener<Number>() {
	        	@Override 
	        	public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
	        		double height = (double) newSceneHeight;
	        		root.setMaxHeight(height);
	        		anchorPaneShipper.setMaxHeight(height);
	        		secondOuterAnchorPane.setMaxHeight(height);
	        		scrollPane.setPrefHeight(height);
	        		tblShipper.setMinHeight(-1.0);
	        		System.out.println(newSceneHeight + " newSceneHeight: " + tblShipper.getPrefHeight() + " table di height");
	        	}
     	});
		
	}

	List<Shipper> shippers = null;

	ObjectMapper mapper = new ObjectMapper();

	@FXML
	TextField txtSearchShipper;

	@FXML
	private void btnGoShipperAction() {
		String search = txtSearchShipper.getText();
		if (search != null && search.length() > 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API + "/" + search + "/search", null);
						fillShippers(response);
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
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API,
								null);
						fillShippers(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});

		}
	}

	public void fillShippers(String response) {

		try {
			ObservableList<Shipper> data = null;
			shippers = new ArrayList<Shipper>();
			setColumnValues();
			if (response != null && response.length() > 0) {
				Shipper c[] = mapper.readValue(response, Shipper[].class);
				for (Shipper ccl : c) {
					shippers.add(ccl);
				}
				data = FXCollections.observableArrayList(shippers);
			} else {
				data = FXCollections.observableArrayList(shippers);
			}
			tblShipper.setItems(data);
			tblShipper.setVisible(true);
		} catch (Exception e) {
			System.out.println("ShipperController: fillShippers(): " + e.getMessage());
		}
	}

	private void setColumnValues() {

		company.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shipper, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
						return new SimpleStringProperty(param.getValue().getLocationName() + "");
					}
				});
		address.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shipper, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
						return new SimpleStringProperty(param.getValue().getAddress() + "");
					}
				});
		unit.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shipper, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
						return new SimpleStringProperty(param.getValue().getUnit() + "");
					}
				});
		city.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shipper, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
						return new SimpleStringProperty(param.getValue().getCity() + "");
					}
				});
		ps.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Shipper, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
				return new SimpleStringProperty(param.getValue().getProvinceState() + "");
			}
		});
		phone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shipper, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone() + "");
					}
				});
		prefix.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shipper, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
						return new SimpleStringProperty(param.getValue().getPrefix() + "");
					}
				});
		tollfree.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shipper, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
						return new SimpleStringProperty(param.getValue().getTollFree() + "");
					}
				});
		plant.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shipper, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
						return new SimpleStringProperty(param.getValue().getPlant() + "");
					}
				});
		cellNumber.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shipper, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone() + "");
					}
				});
		email.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shipper, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
						return new SimpleStringProperty(param.getValue().getEmail() + "");
					}
				});
		importer.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shipper, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
						return new SimpleStringProperty(param.getValue().getImporter() + "");
					}
				});
	}

	// ADD MENU

	public int tblShipperMenuCount = 0;

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
		if (tblShipperMenuCount == 0) {
			tblShipperMenuCount++;
			// When user right-click on Table
			tblShipper.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

						if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

							if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.SECONDARY)) {
								contextMenu.show(tblShipper, mouseEvent.getScreenX(), mouseEvent.getScreenY());
							} else if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.PRIMARY)
									&& ((MouseEvent) mouseEvent).getClickCount() == 2) {
								editShipperAction();

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
