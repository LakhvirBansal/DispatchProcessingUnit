package com.dpu.controller.planner;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.Login;
import com.dpu.model.Driver;
import com.dpu.model.Trailer;
import com.dpu.model.Truck;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PlannerController extends Application implements Initializable {

	@FXML
	TableColumn<Object, String> list;
	
	@FXML
	Tab tabPaneTrucks;
	
	@FXML
	private void tabPaneTrucksSelection() {
//		fetchLists(1);
	}
	
	@FXML
	TableView<Object> tblLists;
	
	@FXML
	private void tabPaneTrailersSelection() {
		fetchLists(2);
	}
	
	@FXML
	private void tabPaneDriversSelection() {
		fetchLists(0);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

	double widthForTable = 0.0;
	
	@FXML
	DatePicker datePicker;
	
	@FXML
	AnchorPane rightAnchorPane;
	
	@FXML
	BorderPane borderPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		fetchLists(0);
		datePicker.setEditable(false);
//		tblPlanner.getColumns().remo
		Login.setWidthForAll(rightAnchorPane, null);
		widthForTable = Login.setWidthForAllInPlanner(null, tblPlanner);
		/**
		 * For future reference.
		 */
		//this shifts datepicker to the right.
		datePicker.setLayoutX(Login.width - 370);
		LocalDate localDate = LocalDate.now();
		int start = localDate.getDayOfMonth();
		int month = localDate.getMonthValue();
		int year = localDate.getYear();
		LocalDate localDateTimeAfterOnWeek = localDate.plus(1, ChronoUnit.WEEKS);
		int endDate = localDateTimeAfterOnWeek.getDayOfMonth();
		int endMonth = localDateTimeAfterOnWeek.getMonthValue();
		int endYear = localDateTimeAfterOnWeek.getYear();
//		showAgain(year, start, month, endYear, endDate, endMonth);
//		showAgain(2017, 1, 3, 2070, 31, 12);
//		LocalDate startList = LocalDate.of(2017, 3, 1);
//		LocalDate endList = LocalDate.of(2070, 12, 31);
		returnShortList(localDateStringList, 0, 0, null);
	}
	
	@FXML
	private void btnRightAction() {
		startIndex++;
		endIndex++;
		returnShortList(localDateStringList, startIndex, endIndex, null);
	}
	
	@FXML
	private void btnTodayAction() {
		returnShortList(localDateStringList, todayStartIndex, todayEndIndex, null);
	}
	
	@FXML
	private void datePickerAction() {
		LocalDate selectedDate = datePicker.getValue();
		returnShortList(localDateStringList, -1, -1, selectedDate);
		
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		try {
			if(tblLists != null) {
				list = (TableColumn<Object, String>) tblLists.getColumns().get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fetchLists(Integer value) {
		
		fetchColumns();
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = null;
					switch (value) {
						case 0:
							response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_DRIVER_SPECIFIC_API, null);
							break;
						case 1:
							response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TRUCK_SPECIFIC_API, null);
							break;
						case 2:
							response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TRAILER_SPECIFIC_API, null);
							break;
					}
					fillLists(response, value);
//					fillPlannerTable(response, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@FXML
	private void btnLeftAction() {
		startIndex--;
		endIndex--;
		returnShortList(localDateStringList, startIndex, endIndex, null);
	}
	
	@FXML
	TableView<Object> tblPlanner;
	
	private void createTableColumns(int startYr, int startDt, int startMonth, int endYr, int endDt, int endMonth) {
//		
//		LocalDate startList = LocalDate.of(2017, 3, 1);
//		LocalDate endList = LocalDate.of(2070, 12, 31);
//		List<String> completeList = datesBetween(startList, endList);
		
		
//		LocalDate start = LocalDate.of(startYr, startMonth, startDt);
//		LocalDate end = LocalDate.of(endYr, endMonth, endDt);
//		List<String> ret = datesBetween(start, end);
		
//		List<String> ret = returnShortList(localDateStringList, 0, 0);
//		for(int i=0;i<ret.size();i++) {
//			TableColumn<Object, String> tblColumn = new TableColumn<Object, String>(ret.get(i));
//			tblPlanner.getColumns().add(i, tblColumn);
//			tblPlanner.getColumns().get(i).setPrefWidth(widthForTable / 8.25);
//		}
	}
	
	List<TableColumn<Object, String>> listOfColumns = null;
	
	
	private void showAgain(int startYr, int startDt, int startMonth, int endYr, int endDt, int endMonth) {

		LocalDate startList = LocalDate.of(startYr, startMonth, startDt);
		LocalDate endList = LocalDate.of(endYr, endMonth, endDt);
		List<String> completeList = datesBetween(startList, endList);
		for(int i=0;i<completeList.size();i++) {
			TableColumn<Object, String> tblColumn = new TableColumn<Object, String>(completeList.get(i));
			listOfColumns.add(tblColumn);
//			tblPlanner.getColumns().add(i, tblColumn);
//			tblPlanner.getColumns().get(i).setPrefWidth(widthForTable / 7);
		}
	}
	
	public static List<String> datesBetween(LocalDate start, LocalDate end) {
		List<String> ret = new ArrayList<String>();
		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
			String dt = date.getDayOfMonth() + "";
			String month = date.getMonth() + "";
			String day = date.getDayOfWeek() + "";
			ret.add(dt + "-" + month + " (" + day + ")");
		}
		return ret;
	}
	
	List<String> localDateStringList = new ArrayList<>();
	int startIndex = 0;
	int todayStartIndex = 0;
	int endIndex = 0;
	int todayEndIndex = 0;
	
	private List<String> returnShortList(List<String> localDateStringList, int startIndex, int endIndex, LocalDate selectedDate) {
		tblPlanner.getColumns().clear();
		LocalDate start = LocalDate.of(2017, 3, 1);
		LocalDate end = LocalDate.of(2070, 12, 31);
		int i = 0;
		LocalDate today = LocalDate.now();
		LocalDate localDateTimeAfterOnWeek = null;
		if(selectedDate != null) {
			for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
				if(date.equals(selectedDate)) {
					startIndex = i;
					this.startIndex = startIndex;
					localDateTimeAfterOnWeek = selectedDate.plus(1, ChronoUnit.WEEKS);
				}
				if(date.equals(localDateTimeAfterOnWeek)) {
					endIndex = i;
					this.endIndex = endIndex;
				}
				i++;
				String dt = date.getDayOfMonth() + "";
				String month = date.getMonth() + "";
				String day = date.getDayOfWeek() + "";
				localDateStringList.add(dt + "-" + month + " (" + day + ")");
			}
		}
		if(startIndex == 0 && endIndex == 0) {
			for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
				if(date.equals(today)) {
					startIndex = i;
					this.startIndex = startIndex;
					this.todayStartIndex = startIndex;
					localDateTimeAfterOnWeek = today.plus(1, ChronoUnit.WEEKS);
				}
				if(date.equals(localDateTimeAfterOnWeek)) {
					endIndex = i;
					this.endIndex = endIndex;
					this.todayEndIndex = endIndex;
				}
				i++;
				String dt = date.getDayOfMonth() + "";
				String month = date.getMonth() + "";
				String day = date.getDayOfWeek() + "";
				localDateStringList.add(dt + "-" + month + " (" + day + ")");
				this.localDateStringList = localDateStringList;
			}
		}
		List<String> smallList = new ArrayList<>();
		for(int j= startIndex;j<endIndex;j++) {
			smallList.add(localDateStringList.get(j));
		}
		for(int k=0;k<smallList.size();k++) {
			TableColumn<Object, String> tblColumn = new TableColumn<Object, String>(smallList.get(k));
			tblPlanner.getColumns().add(k, tblColumn);
			tblPlanner.getColumns().get(k).setPrefWidth(widthForTable / 8.25);
		}
		return smallList;
	}
	
	
	List<Driver> drivers = null;
	List<Truck> trucks = null;
	List<Trailer> trailers = null;
	ObjectMapper mapper = new ObjectMapper();
	
	public void fillPlannerTable(String response, Integer value) {
		
		try {
			ObservableList<Object> data = null;
			switch (value) {
				case 0:
					drivers = new ArrayList<Driver>();
					setColumnValues(value);
					if(response != null && response.length() > 0) {
						Driver c[] = mapper.readValue(response, Driver[].class);
						for(Driver ccl : c) {
							drivers.add(ccl);
						}
						data = FXCollections.observableArrayList(drivers);
					} else {
						data = FXCollections.observableArrayList(drivers);
					}
					tblPlanner.setItems(data);
					tblPlanner.setVisible(true);
					break;
				case 1:
					trucks = new ArrayList<Truck>();
					setColumnValues(value);
					if(response != null && response.length() > 0) {
						System.out.println("truck response: " + response);
						Truck c[] = mapper.readValue(response, Truck[].class);
						for(Truck ccl : c) {
							trucks.add(ccl);
						}
						data = FXCollections.observableArrayList(trucks);
					} else {
						data = FXCollections.observableArrayList(trucks);
					}
					tblPlanner.setItems(data);
					tblPlanner.setVisible(true);
					break;
				case 2:
					trailers = new ArrayList<Trailer>();
					setColumnValues(value);
					if(response != null && response.length() > 0) {
						Trailer c[] = mapper.readValue(response, Trailer[].class);
						for(Trailer ccl : c) {
							trailers.add(ccl);
						}
						data = FXCollections.observableArrayList(trailers);
					} else {
						data = FXCollections.observableArrayList(trailers);
					}
					tblPlanner.setItems(data);
					tblPlanner.setVisible(true);
					break;
			}
		} catch (Exception e) {
			System.out.println("PlannerController: fillLists(): "+ e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fillLists(String response, Integer value) {
		
		try {
			ObservableList<Object> data = null;
			switch (value) {
				case 0:
					drivers = new ArrayList<Driver>();
					setColumnValues(value);
					if(response != null && response.length() > 0) {
						Driver c[] = mapper.readValue(response, Driver[].class);
						for(Driver ccl : c) {
							drivers.add(ccl);
						}
						data = FXCollections.observableArrayList(drivers);
					} else {
						data = FXCollections.observableArrayList(drivers);
					}
					tblLists.setItems(data);
					tblLists.setVisible(true);
					break;
				case 1:
					trucks = new ArrayList<Truck>();
					setColumnValues(value);
					if(response != null && response.length() > 0) {
						System.out.println("truck response: " + response);
						Truck c[] = mapper.readValue(response, Truck[].class);
						for(Truck ccl : c) {
							trucks.add(ccl);
						}
						data = FXCollections.observableArrayList(trucks);
					} else {
						data = FXCollections.observableArrayList(trucks);
					}
					tblLists.setItems(data);
					tblLists.setVisible(true);
					break;
				case 2:
					trailers = new ArrayList<Trailer>();
					setColumnValues(value);
					if(response != null && response.length() > 0) {
						Trailer c[] = mapper.readValue(response, Trailer[].class);
						for(Trailer ccl : c) {
							trailers.add(ccl);
						}
						data = FXCollections.observableArrayList(trailers);
					} else {
						data = FXCollections.observableArrayList(trailers);
					}
					tblLists.setItems(data);
					tblLists.setVisible(true);
					break;
			}
		} catch (Exception e) {
			System.out.println("PlannerController: fillLists(): "+ e.getMessage());
		}
	}
	
	private void setColumnValues(Integer value) {
			
		switch (value) {
		case 0:
			list.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<Object, String> param) {
					Driver paramDriver = (Driver) param.getValue();
					return new SimpleStringProperty(paramDriver.getFullName() + "");
				}
			});
			break;
		case 1:
			/*if(drivers != null && drivers.size() > 0) {
				list.getColumns().removeAll(drivers);
			}*/
			list.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<Object, String> param) {
					Truck paramTruck = (Truck) param.getValue();
					return new SimpleStringProperty(paramTruck.getOwner() + "");
				}
			});
			break;

		case 2:
			list.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<Object, String> param) {
					Trailer paramTrailer = (Trailer) param.getValue();
					return new SimpleStringProperty(paramTrailer.getOwner() + "");
				}
			});
			break;

		}
		
	}
}
