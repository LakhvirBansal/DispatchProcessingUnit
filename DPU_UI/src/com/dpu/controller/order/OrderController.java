package com.dpu.controller.order;

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
import com.dpu.model.OrderModel;
import com.dpu.model.ProbilModel;
import com.dpu.model.Success;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class OrderController extends Application implements Initializable {

	ObjectMapper mapper = new ObjectMapper();
	
	@FXML
	TableView<OrderModel> tblOrder;
	
	List<OrderModel> orders = null;
	List<ProbilModel> probills = null;

	@FXML
	TextField txtSearchOrders;
	
	@FXML
	TableColumn<OrderModel, String> billingLocationName, contactName, temperatureName, temperatureValue, temperatureTypeName, 
	rate, poNo, currencyName, customerName;
	
	@FXML
	AnchorPane equipmentParentAnchorPane;

	Node detailsPane;
	
	@FXML
	private void btnAddOrderAction() {
		openAddOrderScreen();
	}
	
	private Object openEditOrderScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.ORDER_BASE_PACKAGE + Iconstants.XML_ORDER_EDIT_SCREEN));
	        Parent root = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle(Iconstants.EDIT_ORDER);
	        stage.setScene(new Scene(root)); 
	        stage.show();
	        return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	@FXML
	private void btnEditOrderAction() {
		OrderModel orderModel = orders.get(tblOrder.getSelectionModel().getSelectedIndex());
		if(orderModel != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_ORDER_API + "/" + orderModel.getId(), null);
						System.out.println("OpenEditResponse: " + response);
						if(response != null && response.length() > 0) {
							OrderModel c = mapper.readValue(response, OrderModel.class);
							OrderEditController orderEditController = (OrderEditController) openEditOrderScreen();
							orderEditController.initData(c);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again.." + e , "Info", 1);
					}
				}
			});
		}
	}
	
	@FXML
	private void btnGoOrderAction() {
	}
	
	private void openAddOrderScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.ORDER_BASE_PACKAGE + Iconstants.XML_ORDER_ADD_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle(Iconstants.ADD_NEW_ORDER);
	        stage.setScene(new Scene(root)); 
	        stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	Integer entryScreenCounter = 0;
	
	private Integer showInnerTable() {
		System.out.println("counter value: " + entryScreenCounter);
		if(entryScreenCounter == -1 || entryScreenCounter == 1) {
			tblOrder.setRowFactory(tv -> new TableRow<OrderModel>() {
	            {
	                selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
	                    if(detailsPane != null) {
		                	if (isNowSelected) { 
		                        getChildren().add(detailsPane);
		                    } else {
		                        getChildren().remove(detailsPane);
		                    }
	                    }
	                    this.requestLayout();
	                });
	            }
	
	            @Override
	            protected double computePrefHeight(double width) {
	                if (isSelected() && detailsPane != null) {
	                	return super.computePrefHeight(width)+detailsPane.prefHeight(getWidth());
	                } else {
	                    return super.computePrefHeight(width);
	                }
	            }
	
	            @Override
	            protected void layoutChildren() {
	                super.layoutChildren();
	                if (isSelected() && detailsPane != null) {
	                    double width = getWidth();
	//                    double paneHeight = detailsPane.prefHeight(width);
	                    double paneHeight = 100;
	//                    detailsPane.resizeRelocate(0, getHeight()-paneHeight, width, paneHeight);
	                    detailsPane.resizeRelocate(0, getHeight()-detailsPane.prefHeight(width), width, paneHeight);
	                } 
	            }
	        });
		}
		entryScreenCounter = 2;
		return entryScreenCounter;
	}
	
	private void hideInnerTable() {
		tblOrder.getSelectionModel().clearSelection(tblOrder.getSelectionModel().getSelectedIndex());
	}
	
	@FXML
	AnchorPane root, anchorPaneOrder;

	@FXML
	ImageView btnGoOrder;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(root, tblOrder);
		Login.setWidthForAll(anchorPaneOrder, null);
		fetchOrders();
	}
	
	@Override
	public void start(Stage primaryStage) {
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		customerName = (TableColumn<OrderModel, String>) tblOrder.getColumns().get(0);
		billingLocationName = (TableColumn<OrderModel, String>) tblOrder.getColumns().get(1);
		contactName = (TableColumn<OrderModel, String>) tblOrder.getColumns().get(2);
		temperatureName = (TableColumn<OrderModel, String>) tblOrder.getColumns().get(3);
		temperatureValue = (TableColumn<OrderModel, String>) tblOrder.getColumns().get(4);
		temperatureTypeName = (TableColumn<OrderModel, String>) tblOrder.getColumns().get(5);
		rate = (TableColumn<OrderModel, String>) tblOrder.getColumns().get(6);
		poNo = (TableColumn<OrderModel, String>) tblOrder.getColumns().get(7);
		currencyName = (TableColumn<OrderModel, String>) tblOrder.getColumns().get(8);
	}
	
	/*@FXML
	private void btnAddEquipmentAction() {
		openAddEquipmentScreen();
	}*/
	
	/*private void openAddEquipmentScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.EQUIPMENT_BASE_PACKAGE + Iconstants.XML_EQUIPMENT_ADD_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Add New Equipment");
	        stage.setScene(new Scene(root)); 
	        stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}*/
	
	@FXML
	private void btnDeleteOrderAction() {
		OrderModel orderModel = tblOrder.getSelectionModel().getSelectedItem();
		if(orderModel != null) {
			Platform.runLater(new Runnable() {
				
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_ORDER_API + "/" + orderModel.getId(), null);
						try {
							Success success = mapper.readValue(response, Success.class);
							List<OrderModel> orderModelList = (List<OrderModel>) success.getResultList();
							String res = mapper.writeValueAsString(orderModelList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillOrders(res);
						} catch (Exception e) {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage());
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
		}
	}
	
	@FXML
	private void txtSearchOrdersAction() {
		String searchOrders = txtSearchOrders.getText();
		if(searchOrders != null && searchOrders.length() > 0) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_ORDER_API + "/" + searchOrders + "/search", null);
						fillOrders(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
			
		}
		
		if(searchOrders != null && searchOrders.length() == 0) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_ORDER_API, null);
						fillOrders(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
			
		}
	}
	
	/*@FXML
	private void btnEditEquipmentAction() {
		
		Equipment equipment = equipments.get(tblEquipment.getSelectionModel().getSelectedIndex());
		if(equipment != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API + "/" + equipment.getEquipmentId(), null);
						System.out.println("EE:: " + response);
						if(response != null && response.length() > 0) {
							Equipment e = mapper.readValue(response, Equipment.class);
							EquipmentEditController equipmentEditController = (EquipmentEditController) openEditEquipmentScreen();
							equipmentEditController.initData(e);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again.." + e , "Info", 1);
					}
				}
			});
		}
	}*/
	
	public void fetchOrders() {
	
		fetchColumns();
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_ORDER_API, null);
					fillOrders(response);
				} catch (Exception e) {
				}
			}
		});
		
		double width = Login.width;
		int noOfColumns = tblOrder.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tblOrder.getColumns().get(i).setMinWidth(width / noOfColumns);
		}
		txtSearchOrders.setLayoutX(width - (txtSearchOrders.getPrefWidth() + btnGoOrder.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGoOrder.setLayoutX(width - (btnGoOrder.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));

	}
	
	public Node fetchProbills(long orderId) {
		
		final TableView<ProbilModel> tblProbill = new TableView<ProbilModel>();

		Node node = fetchColumnsForProbills(tblProbill);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_ORDER_API + "/" + orderId, null);
					fillProbills(response, tblProbill);
				} catch (Exception e) {
				}
			}
		});
		return node;
	}
	
	private void fillProbills(String response, TableView<ProbilModel> tblProbill) {

		ObservableList<ProbilModel> data = null;
		probills = new ArrayList<ProbilModel>();
		fetchColumns();
		if(response != null && response.length() > 0) {
		
			try {
				OrderModel orderModel = mapper.readValue(response, OrderModel.class);
				List<ProbilModel> c = orderModel.getProbilList();
				/*for(ProbilModel ccl : c) {
					probills.add(ccl);
				}*/
				data = FXCollections.observableArrayList(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			data = FXCollections.observableArrayList(probills);
		}
		tblProbill.setItems(data);
        tblProbill.setVisible(true);
       
	}
	
	@SuppressWarnings("unchecked")
	private Node fetchColumnsForProbills(TableView<ProbilModel> tblProbill) {
		
		TableColumn<ProbilModel, String> firstCol = new TableColumn<ProbilModel, String>("Probill No");
		TableColumn<ProbilModel, String> secCol = new TableColumn<ProbilModel, String>("Shipper Name");
		TableColumn<ProbilModel, String> thirdCol = new TableColumn<ProbilModel, String>("Consignee Name");
		TableColumn<ProbilModel, String> fourthCol = new TableColumn<ProbilModel, String>("Pickup Dt.");
		TableColumn<ProbilModel, String> fifthCol = new TableColumn<ProbilModel, String>("Pickup Time");
		TableColumn<ProbilModel, String> sixthCol = new TableColumn<ProbilModel, String>("Delivery Dt.");
		TableColumn<ProbilModel, String> seventhCol = new TableColumn<ProbilModel, String>("Delivery Time");

        firstCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<>("probilNo"));
        
        secCol.setMinWidth(100);
        secCol.setCellValueFactory(new PropertyValueFactory<>("shipperName"));
 
        thirdCol.setMinWidth(100);
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("consineeName"));
        
        fourthCol.setMinWidth(100);
        fourthCol.setCellValueFactory(new PropertyValueFactory<>("pickupMABDate"));
        
        fifthCol.setMinWidth(100);
        fifthCol.setCellValueFactory(new PropertyValueFactory<>("pickupMABTime"));
        
        sixthCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<>("deliveryMABDate"));
        
        seventhCol.setMinWidth(100);
        seventhCol.setCellValueFactory(new PropertyValueFactory<>("deliveryMABTime"));
        tblProbill.getColumns().addAll(firstCol, secCol,thirdCol,fourthCol,fifthCol,sixthCol,seventhCol);
        final VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().add(tblProbill);
        return vbox;
	}

	public void fillOrders(String response) {
		
		try {
			ObservableList<OrderModel> data = null;
			orders = new ArrayList<OrderModel>();
			setColumnValues();
			if(response != null && response.length() > 0) {
				OrderModel c[] = mapper.readValue(response, OrderModel[].class);
				for(OrderModel ccl : c) {
					orders.add(ccl);
				}
				counter = new int[orders.size()];
				data = FXCollections.observableArrayList(orders);
			} else {
				data = FXCollections.observableArrayList(orders);
			}
			entryScreenCounter = -1;
			showInnerTable();
			tblOrder.setItems(data);
            tblOrder.setVisible(true);
            
		} catch (Exception e) {
			System.out.println("OrderController: fillOrders(): "+ e.getMessage());
		}
	}
	
	private void setColumnValues() {
		
		customerName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderModel, String>, ObservableValue<String>>() {
					
					@Override
					public ObservableValue<String> call(CellDataFeatures<OrderModel, String> param) {
						return new SimpleStringProperty(param.getValue().getCompanyName() + "");
					}
				});
		billingLocationName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<OrderModel, String> param) {
				return new SimpleStringProperty(param.getValue().getBillingLocationName() + "");
			}
		});
		contactName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<OrderModel, String> param) {
				return new SimpleStringProperty(param.getValue().getContactName() + "");
			}
		});
		temperatureName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<OrderModel, String> param) {
				return new SimpleStringProperty(param.getValue().getTemperatureName() + "");
			}
		});
		temperatureValue.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<OrderModel, String> param) {
				return new SimpleStringProperty(param.getValue().getTemperatureValue() + "");
			}
		});
		temperatureTypeName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<OrderModel, String> param) {
				return new SimpleStringProperty(param.getValue().getTemperatureTypeName() + "");
			}
		});
		rate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<OrderModel, String> param) {
				return new SimpleStringProperty(param.getValue().getRate() + "");
			}
		});
		poNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<OrderModel, String> param) {
				return new SimpleStringProperty(param.getValue().getPoNo() + "");
			}
		});
		currencyName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<OrderModel, String> param) {
				return new SimpleStringProperty(param.getValue().getCurrencyName() + "");
			}
		});
	}
	
	int counter[];
	
	@FXML
	private void tblOrderMouseClicked(MouseEvent event) {
		if(event.getButton() == MouseButton.PRIMARY && counter[tblOrder.getSelectionModel().getSelectedIndex()] == 0 && event.getClickCount() == 2) {
            detailsPane = fetchProbills(tblOrder.getSelectionModel().getSelectedItem().getId());
            counter[tblOrder.getSelectionModel().getSelectedIndex()]++;
            entryScreenCounter = 1;
            showInnerTable();
		} else if(event.getButton() == MouseButton.PRIMARY && counter[tblOrder.getSelectionModel().getSelectedIndex()] >= 1 && event.getClickCount() == 2){
			 counter[tblOrder.getSelectionModel().getSelectedIndex()] = 0;
			 hideInnerTable();
		}
	}
	
	// ADD MENU

/*			public int tblEquipmentMenuCount = 0;

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
				if (tblEquipmentMenuCount == 0) {
					tblEquipmentMenuCount++;
					// When user right-click on Table
					tblEquipment.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
						@Override
						public void handle(ContextMenuEvent event) {
							contextMenu.show(tblEquipment, event.getScreenX(), event.getScreenY());

						}

					});

				}

			}
*/}