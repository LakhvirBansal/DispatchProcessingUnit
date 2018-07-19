package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.AddtionalCarrierContact;
import com.dpu.model.ArrangedWithModel;
import com.dpu.model.CarrierContractModel;
import com.dpu.model.CarrierModel;
import com.dpu.model.Category;
import com.dpu.model.DispatcherModel;
import com.dpu.model.Division;
import com.dpu.model.Driver;
import com.dpu.model.Equipment;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.Type;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CarrierContractAddController extends Application implements Initializable {

	@FXML
	Button btnSaveCarrierContract, btnCancelCarrierContract;

	@FXML
	TextField txtCarrierName, txtArrangedWith, txtContractNo, txtCarrierInfo, txtDivisionName, txtContractRate,
			txtCarrierRat, txtEquipment, txtHrs, txtMiles, txtDispatched, txtCreatedBy, txtincExpires, txtCargo,
			txtLibility, txtTransDoc, txtMcNo, txtDotNo;

	@FXML
	ComboBox<String> ddlCurrancy, ddlCategory, ddlRole, ddlCommodity, ddlDivision, ddlDispatcher, ddlArrangedWith,
			ddlDriver, ddlEquipment, ddlCarrier;

	ObjectMapper mapper = new ObjectMapper();
	List<CarrierModel> carrierList = null;
	List<Division> divisionList = null;
	List<Driver> driverList = null;
	List<Category> categoryList = null;
	List<Equipment> equipmentList = null;
	List<ArrangedWithModel> arrangedWithList = null;
	List<Type> currencyList = null;
	List<Type> roleList = null;
	List<Type> commodityList = null;
	List<DispatcherModel> dispatcherList = null;

	private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					String response = GetAPIClient
							.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CARRIER_CONTRACT_API + "/openAdd", null);
					CarrierContractModel carrierContractModel = mapper.readValue(response, CarrierContractModel.class);

					carrierList = carrierContractModel.getCarrierList();
					fillDropDown(ddlCarrier, carrierList);

					categoryList = carrierContractModel.getCategoryList();
					fillDropDown(ddlCategory, categoryList);

					driverList = carrierContractModel.getDriverList();
					fillDropDown(ddlDriver, driverList);

					equipmentList = carrierContractModel.getEquipmentList();
					fillDropDown(ddlEquipment, equipmentList);

					divisionList = carrierContractModel.getDivisionList();
					fillDropDown(ddlDivision, divisionList);

					arrangedWithList = carrierContractModel.getArrangedWithList();
					fillDropDown(ddlArrangedWith, arrangedWithList);

					currencyList = carrierContractModel.getCurrencyList();
					fillDropDown(ddlCurrancy, currencyList);

					categoryList = carrierContractModel.getCategoryList();
					fillDropDown(ddlCategory, categoryList);

					roleList = carrierContractModel.getRoleList();
					fillDropDown(ddlRole, roleList);

					commodityList = carrierContractModel.getCommodityList();
					fillDropDown(ddlCommodity, commodityList);

					dispatcherList = carrierContractModel.getDispatcherList();
					fillDropDown(ddlDispatcher, dispatcherList);

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);

				}
			}
		});
	}

	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			if (object != null && object instanceof CarrierModel) {
				CarrierModel carrierModel = (CarrierModel) object;
				comboBox.getItems().add(carrierModel.getName());
			}
			if (object != null && object instanceof Driver) {
				Driver driver = (Driver) object;
				comboBox.getItems().add(driver.getFirstName() + " " + driver.getLastName());
			}
			if (object != null && object instanceof Category) {
				Category category = (Category) object;
				comboBox.getItems().add(category.getName());
			}
			if (object != null && object instanceof Division) {
				Division division = (Division) object;
				comboBox.getItems().add(division.getDivisionName());
			}
			if (object != null && object instanceof Equipment) {
				Equipment equipment = (Equipment) object;
				comboBox.getItems().add(equipment.getEquipmentName());
			}
			if (object != null && object instanceof Type) {
				Type type = (Type) object;
				comboBox.getItems().add(type.getTypeName());
			}
			if (object != null && object instanceof DispatcherModel) {
				DispatcherModel type = (DispatcherModel) object;
				comboBox.getItems().add(String.valueOf(type.getId()));
			}
			if (object != null && object instanceof ArrangedWithModel) {
				ArrangedWithModel type = (ArrangedWithModel) object;
				comboBox.getItems().add(type.getArrangedWith());
			}
		}
	}

	private CarrierContractModel setCarrierContractValue() {
		CarrierContractModel carrierContractModel = new CarrierContractModel();
		carrierContractModel.setArrangedWithId(
				arrangedWithList.get(ddlArrangedWith.getSelectionModel().getSelectedIndex()).getId());
		carrierContractModel
				.setCurrencyId(currencyList.get(ddlCurrancy.getSelectionModel().getSelectedIndex()).getTypeId());
		carrierContractModel.setRoleId(roleList.get(ddlRole.getSelectionModel().getSelectedIndex()).getTypeId());
		carrierContractModel
				.setCommodityId(commodityList.get(ddlCommodity.getSelectionModel().getSelectedIndex()).getTypeId());
		carrierContractModel
				.setDispatcherId(dispatcherList.get(ddlDispatcher.getSelectionModel().getSelectedIndex()).getId());
		carrierContractModel
				.setCarrierId(carrierList.get(ddlCarrier.getSelectionModel().getSelectedIndex()).getCarrierId());
		carrierContractModel
				.setDivisionId(divisionList.get(ddlDivision.getSelectionModel().getSelectedIndex()).getDivisionId());
		carrierContractModel
				.setDriverId(driverList.get(ddlDriver.getSelectionModel().getSelectedIndex()).getDriverId());
		carrierContractModel
				.setCategoryId(categoryList.get(ddlCategory.getSelectionModel().getSelectedIndex()).getCategoryId());
		carrierContractModel.setEquipmentId(
				equipmentList.get(ddlEquipment.getSelectionModel().getSelectedIndex()).getEquipmentId());

		carrierContractModel.setContractRate(Double.parseDouble(txtContractRate.getText()));
		carrierContractModel.setCarrierRat(txtCarrierRat.getText());
		carrierContractModel.setHours(txtHrs.getText());
		carrierContractModel.setMiles(txtMiles.getText());
		carrierContractModel.setContractNo(txtContractNo.getText());
		carrierContractModel.setCreatedBy(Long.parseLong(txtCreatedBy.getText()));
		carrierContractModel.setInsExpires(txtincExpires.getText());
		carrierContractModel.setCargo(txtCargo.getText());
		carrierContractModel.setLiabity(txtLibility.getText());
		carrierContractModel.setTransDoc(txtTransDoc.getText());
		carrierContractModel.setmCno(txtMcNo.getText());
		carrierContractModel.setdOTno(txtDotNo.getText());
		return carrierContractModel;

	}

	private void addCarrierContract() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					CarrierContractModel carrierContractModel = setCarrierContractValue();
					String payload = mapper.writeValueAsString(carrierContractModel);
					System.out.println("Payload::: " + payload);
					String response = PostAPIClient
							.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_CARRIER_CONTRACT_API, null, payload);
					System.out.println("res::  " + response);
					if (response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.carrierContractController.fetchCarriersContracts();
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	private void closeCarrierContractScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fetchMasterDataForDropDowns();

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@FXML
	private void btnSaveCarrierContractAction() {
		addCarrierContract();
		closeCarrierContractScreen(btnSaveCarrierContract);
	}

	@FXML
	private void btnCancelCarrierContractAction() {
		closeCarrierContractScreen(btnSaveCarrierContract);
	}
}
