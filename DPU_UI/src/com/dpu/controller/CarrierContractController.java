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
import com.dpu.model.CarrierContractModel;
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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CarrierContractController extends Application implements Initializable {

	public List<CarrierContractModel> cList = new ArrayList<CarrierContractModel>();
	@FXML
	TableView<CarrierContractModel> tableCarrierContract;
	public static int flag = 0;
	
	@FXML
	Pane root, innerRootPane;

	@FXML
	TableColumn<CarrierContractModel, String> contractNo, contractRate, carrierRat, hours, miles, dispatched, createdBy,
			insExpires, cargo, liabity, transDoc, mCno, dOTno, carrierName, arrangedWithName, driverName, currencyName,
			categoryName, roleName, equipmentName, commodityName, divisionName, dispatcherName;
	
	@FXML
	TextField txtSearchCarrierContract;
	
	@FXML
	ImageView btnGo;

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		contractNo = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(0);
		contractRate = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(1);
		hours = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(2);
		miles = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(3);
		dispatched = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(4);
		insExpires = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(5);
		cargo = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(6);
		liabity = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(7);
		transDoc = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(8);
		mCno = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(9);
		carrierName = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(10);
		arrangedWithName = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(11);
		driverName = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(12);
		currencyName = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(13);
		categoryName = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(14);
		roleName = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(15);
		equipmentName = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(16);
		commodityName = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(17);
		divisionName = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(18);
		dispatcherName = (TableColumn<CarrierContractModel, String>) tableCarrierContract.getColumns().get(19);

	}

	private void setColumnValues() {

		contractNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getContractNo() + "");
					}
				});
		contractRate.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getContractRate() + "");
					}
				});
		hours.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getHours() + "");
					}
				});
		miles.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getMiles() + "");
					}
				});
		dispatched.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getDispatched() + "");
					}
				});
		insExpires.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getInsExpires() + "");
					}
				});
		cargo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getCargo() + "");
					}
				});
		liabity.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getLiabity() + "");
					}
				});
		transDoc.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getTransDoc() + "");
					}
				});
		mCno.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getmCno() + "");
					}
				});
		carrierName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getCarrierName() + "");
					}
				});
		arrangedWithName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getArrangedWithName() + "");
					}
				});
		driverName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getDriverName() + "");
					}
				});
		currencyName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getCurrencyName() + "");
					}
				});
		categoryName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getCategoryName() + "");
					}
				});
		roleName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getRoleName() + "");
					}
				});
		equipmentName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getEquipmentName() + "");
					}
				});
		commodityName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getCommodityName() + "");
					}
				});
		divisionName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getDivisionName() + "");
					}
				});
		dispatcherName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierContractModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierContractModel, String> param) {
						return new SimpleStringProperty(param.getValue().getDispatcherName() + "");
					}
				});

	}

	public void fetchCarriersContracts() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ObservableList<CarrierContractModel> data = null;
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient
							.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CARRIER_CONTRACT_API, null);
					if (response != null && response.length() > 0) {
						CarrierContractModel c[] = mapper.readValue(response, CarrierContractModel[].class);
						cList = new ArrayList<CarrierContractModel>();
						for (CarrierContractModel ccl : c) {
							cList.add(ccl);
						}
						data = FXCollections.observableArrayList(cList);
					} else {
						data = FXCollections.observableArrayList(cList);
					}
					setColumnValues();
					tableCarrierContract.setItems(data);

					tableCarrierContract.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
		
		double width = Login.width;
		int noOfColumns = tableCarrierContract.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tableCarrierContract.getColumns().get(i).setMinWidth(width / noOfColumns);
		}
		txtSearchCarrierContract.setLayoutX(width - (txtSearchCarrierContract.getPrefWidth() + btnGo.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGo.setLayoutX(width - (btnGo.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
	}

	@FXML
	private void btnDeleteCarrierContractAction() {
		CarrierContractModel carrierModel = cList.get(tableCarrierContract.getSelectionModel().getSelectedIndex());
		if (carrierModel != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER
								+ Iconstants.URL_CARRIER_CONTRACT_API + "/" + carrierModel.getContractNoId(), null);
						fetchCarriersContracts();
						JOptionPane.showMessageDialog(null, "Carrier Contract Deleted Successfully", "Info", 1);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}

	}

	private void openCarrierContractAddScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
					Iconstants.CARRIER_CONTRACT_BASE_PACKAGE + Iconstants.XML_CARRIER_CONTRACT_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.ADD_NEW_CARRIER_CONTRACT);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	private Object openEditCarrierContractScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
					Iconstants.CARRIER_CONTRACT_BASE_PACKAGE + Iconstants.XML_CARRIER_CONTRACT_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.EDIT_CARRIER_CONTRACT);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@FXML
	private void btnEditCarrierContractAction() {
		flag = 2;
		CarrierContractModel carrierContractModel = tableCarrierContract.getSelectionModel().getSelectedItem();
		if (carrierContractModel != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CARRIER_CONTRACT_API + "/" + carrierContractModel.getContractNoId(), null);
						Success s = mapper.readValue(response, Success.class);
						String s1 = mapper.writeValueAsString(s.getResultList());
						if (response != null && response.length() > 0) {
							CarrierContractModel c = mapper.readValue(s1, CarrierContractModel.class);
							CarrierContractEditController carrierContractEditController = (CarrierContractEditController) openEditCarrierContractScreen();
							carrierContractEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
						e.printStackTrace();
					}
				}
			});
		}
	}

	@FXML
	private void handleRowSelect() {
		flag = 1;
		tableCarrierContract.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					flag = 1;
					CarrierContractModel carrierContractModel = tableCarrierContract.getSelectionModel()
							.getSelectedItem();
					if (carrierContractModel != null) {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								try {
									System.out.println("id   " + carrierContractModel.getContractNoId());
									ObjectMapper mapper = new ObjectMapper();
									String response = GetAPIClient
											.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CARRIER_CONTRACT_API
													+ "/" + carrierContractModel.getContractNoId(), null);
									if (response != null && response.length() > 0) {
										CarrierContractModel c = mapper.readValue(response, CarrierContractModel.class);
//										System.out.println("muiles::  " + c.getMiles());
										CarrierContractEditController carrierContractEditController = (CarrierContractEditController) openEditCarrierContractScreen();
										carrierContractEditController.initData(c);
									}
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
									e.printStackTrace();
								}
							}
						});
					}

				}
			}
		});
	}

	@FXML
	private void btnGoCarrierContractAction() {
	}

	@FXML
	private void btnAddCarrierContractAction() {
		openCarrierContractAddScreen();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Login.setWidthForAll(root, tableCarrierContract);
		Login.setWidthForAll(innerRootPane, null);
		fetchCarriersContracts();

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
