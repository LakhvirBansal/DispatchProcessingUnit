package com.dpu.controller;
	
import java.net.URL;
import java.util.ResourceBundle;

import com.dpu.constants.Iconstants;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Login extends Application implements Initializable{
	
	@FXML
	TextField txtUsername;
	
	@FXML
	PasswordField txtPassword;
	
	@FXML
	Button btnLogin;
	
	@FXML
	Button btnCancel;
	
	@FXML
	MenuBar mnuBarDatamaintenance;
	
	public static double width = 0.0, height = 0.0;

	public static void setWidthForAll(Object obj, TableView<?> tableView) {
		if(obj != null && obj instanceof Pane) {
			Pane pane = (Pane) obj;
			pane.setPrefWidth(stage.getWidth());
			if(tableView != null)
				tableView.setPrefWidth(stage.getWidth());
		}
		
		if(obj != null && obj instanceof ScrollPane) {
//			ScrollPane pane = (ScrollPane) obj;
//			pane.prefWidthProperty().bind(stage.widthProperty());
//			if(tableView != null)
//				tableView.prefWidthProperty().bind(stage.widthProperty());
			ScrollPane pane = (ScrollPane) obj;
			pane.setPrefWidth(stage.getWidth());
			if(tableView != null)
				tableView.setPrefWidth(stage.getWidth());
		}
//		if(tableView != null)
//			tableView.prefWidthProperty().bind(stage.widthProperty());
		if(tableView != null)
			tableView.setPrefWidth(stage.getWidth());
	}
	
	public static void setWidthForAllForPlannerBorderPane(Object obj, TableView<?> tableView) {
		if(obj != null && obj instanceof Pane) {
			Pane pane = (Pane) obj;
			pane.prefWidthProperty().bind(stage.widthProperty());
			if(tableView != null)
				tableView.prefWidthProperty().bind(stage.widthProperty());
		}
		if(tableView != null)
			tableView.prefWidthProperty().bind(stage.widthProperty());
	}
	
	public static double setWidthForAllInPlanner(Object obj, TableView<?> tableView) {
		if(obj != null && obj instanceof Pane) {
			Pane pane = (Pane) obj;
			pane.prefWidthProperty().bind(stage.widthProperty());
			if(tableView != null)
				tableView.prefWidthProperty().bind(stage.widthProperty());
		}
		if(tableView != null)
			tableView.prefWidthProperty().bind(stage.widthProperty());
		
		return tableView.getPrefWidth();
				
	}
	
	@FXML
	private void btnLoginAction() {
		try {
			authenticateUser();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@FXML
	private void btnCancelAction() {
		try {
			closeLoginScreen(btnCancel);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void start(Stage primaryStage) { 
		try {
			Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(Iconstants.COMMON_BASE_PACKAGE + Iconstants.XML_LOGIN_SCREEN));
			Scene scene = new Scene(parent);
			primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream(Iconstants.COMMON_BASE_PACKAGE + "application-image.png")));
			primaryStage.setScene(scene);
			primaryStage.setTitle(Iconstants.LOGIN);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtUsername.setText("admin");
		txtPassword.setText("admin");
	}
	
	private void closeLoginScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void authenticateUser() throws Exception{
		String un  = txtUsername.getText();
		String pwd = txtPassword.getText();
		if(un.equals("admin") && pwd.equals("admin")) {
			openMainScreen();
			closeLoginScreen(btnLogin);
		} else {
//			showDialog("Message", null, "Invalid Credentials");
		}
	}
	
	static Stage stage = null;
	static Scene scene = null;
	
	private void openMainScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.COMMON_BASE_PACKAGE + Iconstants.XML_MAIN_SCREEN));
	        Parent root = (Parent) fxmlLoader.load();
	        VBox vBox  = (VBox)root;
	        MenuBar mnuBar = (MenuBar) vBox.getChildren().get(0);
	        MenuBar mnuBarDatamaintenance = (MenuBar) vBox.getChildren().get(2);
	        mnuBarDatamaintenance.setVisible(false);
	        
	        Stage stage = new Stage();
	        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream(Iconstants.COMMON_BASE_PACKAGE + "application-image.png")));
	       
	        /**
	         * following line has been commented to disallow minimize window functionality
	         */
	        
	        //stage.initModality(Modality.APPLICATION_MODAL);
	        //stage.initStyle(StageStyle.UNDECORATED);
	        stage.setTitle(Iconstants.DASHBOARD);
	        scene = new Scene(root);
	        stage.setScene(scene); 
	       
	        //stage.setMaximized(true);
	        width = primaryScreenBounds.getWidth();
	        height = primaryScreenBounds.getHeight();
	        stage.setWidth(width);
	        stage.setHeight(height);
	        stage.setMinHeight(Iconstants.STAGE_MIN_HEIGHT);
	        stage.setMinWidth(Iconstants.STAGE_MIN_WIDTH);
	        this.stage = stage;
	        mnuBar.prefWidthProperty().bind(stage.widthProperty());
	        //fxmlLoader.setController(MainScreen.class);
	        stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
/*	private void showDialog(String title, String headerText, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(message);
		alert.show();
	}*/
}
