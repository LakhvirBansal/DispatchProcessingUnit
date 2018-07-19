package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ValidationController extends Application implements Initializable {

	@FXML
	public Text txtMsg;
	@FXML
	public TextArea taMsg;

	@FXML
	Button btnGotIt;

	public static StringBuffer str = new StringBuffer();

	@FXML
	private void btnGotItAction() {
		closeAddServiceScreen(btnGotIt);
	}

	private void closeAddServiceScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
		str = null;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		taMsg.appendText(str.toString());
		taMsg.setStyle("-fx-text-fill: red; -fx-font-size: 12;");

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
