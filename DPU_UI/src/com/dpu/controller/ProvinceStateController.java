package com.dpu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.ProvinceStateResponse;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ProvinceStateController {
 
	private String title;
	private List<?> list;
	private TableView<ProvinceStateResponse> table = new TableView<ProvinceStateResponse>();
	
	public ProvinceStateController(String title, List<?> list) {
		this.title = title;
		this.list = list;
	}
 
    
    public void openWindow() {
    	Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle(title);
        stage.setWidth(300);
        stage.setHeight(500);
        table.setEditable(false);
        
        TableColumn<ProvinceStateResponse, String> firstNameCol = new TableColumn<ProvinceStateResponse, String>("Code");
        TableColumn<ProvinceStateResponse, String> lastNameCol = new TableColumn<ProvinceStateResponse, String>("Name");
        
        firstNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProvinceStateResponse,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<ProvinceStateResponse, String> param) {
				return new SimpleStringProperty(param.getValue().getStateCode() + "");
			}
		});
		lastNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProvinceStateResponse,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<ProvinceStateResponse, String> param) {
				return new SimpleStringProperty(param.getValue().getStateName() + "");
			}
		});
        table.getColumns().addAll(firstNameCol, lastNameCol);
        callProvinceAPI(null);
        final VBox vbox = new VBox();
        vbox.setPrefWidth(300);
        vbox.setPrefHeight(500);
        vbox.getChildren().addAll(table);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }
    
    public void callProvinceAPI(String provinceName) {
    	
    	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = null;
					if(provinceName != null) {
						response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_PROVINCE_API + "/" + provinceName + "/", null);
					} else {
						response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_PROVINCE_API, null);
					}
					fillProvinces(response);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
    }
    
    public void fillProvinces(String response) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();

			List<ProvinceStateResponse> provinces = new ArrayList<>();
			if(response != null && response.length() > 0) {
				ProvinceStateResponse d[] = mapper.readValue(response, ProvinceStateResponse[].class);
				for(ProvinceStateResponse ddl : d) {
					provinces.add(ddl);
				}
			}
			ObservableList<ProvinceStateResponse> data = FXCollections.observableArrayList(provinces);
			table.setItems(data);
            table.setVisible(true);
		} catch (Exception e) {
			System.out.println("ProvinceStateController: fillProvinces(): "+ e.getMessage());
		}
	}
    
}