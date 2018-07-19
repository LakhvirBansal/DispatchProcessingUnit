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
import com.dpu.model.Accounts;
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

public class AccountsController extends Application implements Initializable {

	@FXML
	TableView<Accounts> tblAccounts;

	@FXML
	TableColumn<Accounts, String> accountNo, accountName, accountType, parent;

	ObjectMapper mapper = new ObjectMapper();

	@FXML
	TextField txtSearchAccounts;

	@FXML
	AnchorPane root, anchorPaneAccounts;

	public static int flag = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(root, tblAccounts);
		Login.setWidthForAll(anchorPaneAccounts, null);
		fetchAccountss();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	@FXML
	private void btnAddAccountsAction() {
		openAddAccountsScreen();
	}

	@FXML
	private void btnGoAccountsAction() {
		String searchAccounts = txtSearchAccounts.getText();

		if (searchAccounts != null && searchAccounts.length() > 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_ACCOUNTS_API + "/" + searchAccounts + "/search",
								null);
						fillAccounts(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});

		}

		if (searchAccounts != null && searchAccounts.length() == 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_ACCOUNTS_API,
								null);
						fillAccounts(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	List<Accounts> Accountss = null;

	@FXML
	ImageView btnGoAccounts;

	/*
	 * public void newMethod() {
	 * txtSearchAccounts.addEventFilter(KeyEvent.KEY_PRESSED, new
	 * EventHandler<KeyEvent>() {
	 * 
	 * @Override public void handle(KeyEvent event) { if (event.getCode() ==
	 * KeyCode.TAB) {
	 * 
	 * } } }); }
	 */

	public void fillAccounts(String response) {

		try {
			Accountss = new ArrayList<Accounts>();
			setColumnValues();
			ObservableList<Accounts> data = null;
			if (response != null && response.length() > 0) {
				Accounts c[] = mapper.readValue(response, Accounts[].class);
				for (Accounts ccl : c) {
					Accountss.add(ccl);
				}
				data = FXCollections.observableArrayList(Accountss);

			} else {
				data = FXCollections.observableArrayList(Accountss);
			}
			tblAccounts.setItems(data);

			tblAccounts.setVisible(true);
		} catch (Exception e) {
			System.out.println("AccountsController: fillAccountss(): " + e.getMessage());
		}
	}

	@FXML
	private void btnEditAccountsAction() {
		flag = 2;
		Accounts dpuAccounts = Accountss.get(tblAccounts.getSelectionModel().getSelectedIndex());
		if (dpuAccounts != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_ACCOUNTS_API + "/" + dpuAccounts.getAccountId(),
								null);
						if (response != null && response.length() > 0) {
							Accounts c = mapper.readValue(response, Accounts.class);
							AccountsEditController AccountsEditController = (AccountsEditController) openEditAccountsScreen();
							AccountsEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private void editAccountsAction() {
		flag = 1;
		Accounts dpuAccounts = Accountss.get(tblAccounts.getSelectionModel().getSelectedIndex());
		if (dpuAccounts != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_ACCOUNTS_API + "/" + dpuAccounts.getAccountId(),
								null);
						if (response != null && response.length() > 0) {
							Accounts c = mapper.readValue(response, Accounts.class);
							AccountsEditController AccountsEditController = (AccountsEditController) openEditAccountsScreen();
							AccountsEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private Object openEditAccountsScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.ACCOUNTS_BASE_PACKAGE + Iconstants.XML_ACCOUNTS_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.EDIT_ACCOUNT);
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return null;
	}

	public static void openAddAccountsScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(AccountsController.class.getClassLoader()
					.getResource(Iconstants.ACCOUNTS_BASE_PACKAGE + Iconstants.XML_ACCOUNTS_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(Iconstants.ADD_NEW_ACCOUNT);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		accountNo = (TableColumn<Accounts, String>) tblAccounts.getColumns().get(0);
		accountName = (TableColumn<Accounts, String>) tblAccounts.getColumns().get(1);
		accountType = (TableColumn<Accounts, String>) tblAccounts.getColumns().get(2);
		parent = (TableColumn<Accounts, String>) tblAccounts.getColumns().get(3);
	}

	public void fetchAccountss() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_ACCOUNTS_API,
							null);
					Accounts s[] = mapper.readValue(response, Accounts[].class);
					Accountss = new ArrayList<Accounts>();
					for (Accounts ccl : s) {
						Accountss.add(ccl);
					}
					ObservableList<Accounts> data = FXCollections.observableArrayList(Accountss);

					setColumnValues();
					tblAccounts.setItems(data);

					tblAccounts.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
		
		double width = Login.width;
		int noOfColumns = tblAccounts.getColumns().size();
		for (int i = 0; i < noOfColumns; i++) {
			tblAccounts.getColumns().get(i).setMinWidth(width / noOfColumns);
		}
		txtSearchAccounts.setLayoutX(width - (txtSearchAccounts.getPrefWidth() + btnGoAccounts.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
		btnGoAccounts.setLayoutX(width - (btnGoAccounts.getFitWidth() + Iconstants.FIX_WIDTH_FROM_RIGHT));
	}

	@FXML
	private void btnDeleteAccountsAction() {
		Accounts Accounts = tblAccounts.getSelectionModel().getSelectedItem();
		if (Accounts != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_ACCOUNTS_API + "/" + Accounts.getAccountId(),
								null);
						// fillAccountss(response);
						try {
							Success success = mapper.readValue(response, Success.class);
							List<Accounts> AccountsList = (List<Accounts>) success.getResultList();
							String res = mapper.writeValueAsString(AccountsList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillAccounts(res);
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
						 * failed.getMessage(), "Info", 1); } fetchAccountss();
						 */
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	private void setColumnValues() {

		accountNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Accounts, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Accounts, String> param) {
						return new SimpleStringProperty(param.getValue().getAccount() + "");
					}
				});
		accountName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Accounts, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Accounts, String> param) {
						return new SimpleStringProperty(param.getValue().getAccountName() + "");
					}
				});
		accountType.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Accounts, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Accounts, String> param) {
						return new SimpleStringProperty(param.getValue().getAccountTypeName() + "");
					}
				});
		parent.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Accounts, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Accounts, String> param) {
						if (param.getValue().getParentAccountName() != null) {
							return new SimpleStringProperty(param.getValue().getParentAccountName() + "");
						} else {
							return new SimpleStringProperty("");
						}
					}
				});

	}

	Accounts temp;
	Date lastClickTime;

	@FXML
	private void handleRowSelect() {
		tblAccounts.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

					System.out.println("clicked:::::::::::::");
				}
			}
		});
	}

	// ADD MENU

	public int tblAccountsrMenuCount = 0;

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
		if (tblAccountsrMenuCount == 0) {
			tblAccountsrMenuCount++; // When user right-click on Table
			tblAccounts.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

						if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

							if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.SECONDARY)) {
								contextMenu.show(tblAccounts, mouseEvent.getScreenX(), mouseEvent.getScreenY());
							} else if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.PRIMARY)
									&& ((MouseEvent) mouseEvent).getClickCount() == 2) {
								editAccountsAction();

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