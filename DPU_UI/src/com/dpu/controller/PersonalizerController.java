package com.dpu.controller;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class PersonalizerController extends Application {

  private TableView<cours> tableView;

  public ObservableList<cours> coursData ;//= FXCollections.observableArrayList();
  @Override

  public void start(Stage primaryStage) {

      this.tableView = new TableView<cours>();
      final TableColumn<cours, String> Cours = new TableColumn<cours, String>("ColumnName");
      final TableColumn<cours, Boolean> checkedCol = new TableColumn<cours, Boolean>("Checked");

      this.coursData  =FXCollections.observableArrayList(
                new cours("Analyse", "3"), 
                new cours("Analyse TP", "4"), 
                new cours("Thermo", "5"),
                new cours("Thermo TP", "7"),
                new cours("Chimie", "8"));

    tableView.setItems(this.coursData);

    tableView.getColumns().addAll(Cours, checkedCol);

    Cours.setCellValueFactory(new PropertyValueFactory<cours, String>("cours"));

    checkedCol.setCellValueFactory(new PropertyValueFactory<cours, Boolean>("checked"));

    checkedCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkedCol));
    checkedCol.setEditable(true);
    tableView.setEditable(true);

    final BorderPane root = new BorderPane();
    root.setCenter(tableView);

    coursData.addListener(new InvalidationListener() {

        @Override public void invalidated(Observable o) {
                System.out.println("checkBox change state ");
                //Here is my problem. 
                //When the user click on a checkBox , the method isn't call .
            }
        });
Scene scene = new Scene(root, 300, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static class cours {
    private StringProperty columnName;
    private StringProperty coursID;
    private BooleanProperty checked;

    public cours(String cours, String coursID) {
      this.columnName = new SimpleStringProperty(cours);
      this.coursID = new SimpleStringProperty(coursID);
      this.checked = new SimpleBooleanProperty(false);
    }

    public String getCours() {
      return columnName.get();
    }

    public String getCoursID() {
      return coursID.get();
    }

    public boolean isChecked() {
      return checked.get();
    }

    public void setCours(String cours) {
      this.columnName.set(cours);
    }

    public void setCoursID(String coursID) {
      this.coursID.set(coursID);
    }

    public void setChecked(boolean checked) {
      this.checked.set(checked);
    }

    public StringProperty coursProperty() {
      return columnName;
    }

    public StringProperty coursIDProperty() {
      return coursID;
    }

    public BooleanProperty checkedProperty() {
      return checked;
    }
  }
}

