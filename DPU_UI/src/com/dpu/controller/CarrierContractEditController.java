package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
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

public class CarrierContractEditController extends Application implements Initializable {
	Long carrierContractId = 0l;
	@FXML
	Button btnUpdateCarrierContract, btnCancelCarrierContract, btnEdit;

	@FXML
	TextField txtContractNo, txtCarrierInfo, txtDivisionName, txtContractRate, txtCarrierRat, txtEquipment, txtHrs,
			txtMiles, txtDispatched, txtCreatedBy, txtincExpires, txtCargo, txtLibility, txtTransDoc, txtMcNo, txtDotNo;

	@FXML
	ComboBox<String> ddlCurrancy, ddlCategory, ddlRole, ddlCommodity, ddlDivision, ddlDispatcher, ddlArrangedWith,
			ddlDriver, ddlEquipment, ddlCarrier;
	List<CarrierModel> carrierList = null;
	List<Division> divisionList = null;
	List<Driver> driverList = null;
	List<Category> categoryList = null;
	List<Equipment> equipmentList = null;
	List<Type> arrangedWithList = null;
	List<Type> currencyList = null;
	List<Type> roleList = null;
	List<Type> commodityList = null;
	List<Type> dispatcherList = null;

	@FXML
	public void handleEditAction() {
		btnEdit.setDisable(true);
		disableFields(false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (CarrierContractController.flag == 1) {
			disableFields(true);
		}
		if (CarrierContractController.flag == 2) {
			btnEdit.setVisible(false);
		}

	}

	private void disableFields(boolean v) {
		btnUpdateCarrierContract.setDisable(v);

		txtCargo.setDisable(v);
		txtContractRate.setDisable(v);
		txtCarrierRat.setDisable(v);
		txtHrs.setDisable(v);
		txtMiles.setDisable(v);
		txtContractNo.setDisable(v);
		txtCreatedBy.setDisable(v);
		txtincExpires.setDisable(v);
		txtLibility.setDisable(v);
		txtTransDoc.setDisable(v);
		txtMcNo.setDisable(v);
		txtDotNo.setDisable(v);
		ddlArrangedWith.setDisable(v);
		ddlCarrier.setDisable(v);
		ddlCategory.setDisable(v);
		ddlCommodity.setDisable(v);
		ddlCurrancy.setDisable(v);
		ddlDispatcher.setDisable(v);
		ddlDivision.setDisable(v);
		ddlDriver.setDisable(v);
		ddlEquipment.setDisable(v);
		ddlRole.setDisable(v);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@FXML
	private void btnUpdateCarrierContractAction() {
		editCarrierContract();
		closeEditDriverScreen(btnUpdateCarrierContract);
	}

	@FXML
	private void btnCancelCarrierContractAction() {

	}

	private void closeEditDriverScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private CarrierContractModel setCarrierContractValue() {
		CarrierContractModel carrierContractModel = new CarrierContractModel();
		carrierContractModel.setArrangedWithId(
				arrangedWithList.get(ddlArrangedWith.getSelectionModel().getSelectedIndex()).getTypeId());
		carrierContractModel
				.setCurrencyId(currencyList.get(ddlCurrancy.getSelectionModel().getSelectedIndex()).getTypeId());
		carrierContractModel.setRoleId(roleList.get(ddlRole.getSelectionModel().getSelectedIndex()).getTypeId());
		carrierContractModel
				.setCommodityId(commodityList.get(ddlCommodity.getSelectionModel().getSelectedIndex()).getTypeId());
		carrierContractModel
				.setDispatcherId(dispatcherList.get(ddlDispatcher.getSelectionModel().getSelectedIndex()).getTypeId());
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

	private void editCarrierContract() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					CarrierContractModel carrierContractModel = setCarrierContractValue();
					String payload = mapper.writeValueAsString(carrierContractModel);
					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_CARRIER_CONTRACT_API + "/" + carrierContractId, null,
							payload);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<CarrierContractModel> carrierContractList = (List<CarrierContractModel>) success
								.getResultList();
						String res = mapper.writeValueAsString(carrierContractList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.carrierContractController.fetchCarriersContracts();
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}

	public void initData(CarrierContractModel carrierContractModel) {

		carrierContractId = carrierContractModel.getContractNoId();
		txtCargo.setText(carrierContractModel.getCargo());
		txtContractRate.setText(String.valueOf(carrierContractModel.getContractRate()));
		txtCarrierRat.setText(carrierContractModel.getCarrierRat());
		txtHrs.setText(String.valueOf(carrierContractModel.getHours()));
		txtMiles.setText(carrierContractModel.getMiles());
		txtContractNo.setText(carrierContractModel.getContractNo());
		txtCreatedBy.setText(String.valueOf(carrierContractModel.getCreatedBy()));
		txtincExpires.setText(carrierContractModel.getInsExpires());
		txtLibility.setText(carrierContractModel.getLiabity());
		txtTransDoc.setText(carrierContractModel.getTransDoc());
		txtMcNo.setText(carrierContractModel.getmCno());
		txtDotNo.setText(carrierContractModel.getdOTno());

		for (int i = 0; i < carrierContractModel.getCarrierList().size(); i++) {
			CarrierModel carrierModel = carrierContractModel.getCarrierList().get(i);
			ddlCarrier.getItems().add(carrierModel.getName());
			if (carrierModel.getCarrierId() == carrierContractModel.getCarrierId()) {
				ddlCarrier.getSelectionModel().select(i);
			}
		}

		for (int i = 0; i < carrierContractModel.getDivisionList().size(); i++) {
			Division division = carrierContractModel.getDivisionList().get(i);
			ddlCarrier.getItems().add(division.getDivisionName());
			if (division.getDivisionId() == carrierContractModel.getDivisionId()) {
				ddlDivision.getSelectionModel().select(i);
			}
		}
		for (int i = 0; i < carrierContractModel.getDriverList().size(); i++) {
			Driver driver = carrierContractModel.getDriverList().get(i);
			ddlCarrier.getItems().add(driver.getFirstName());
			if (driver.getDriverId() == carrierContractModel.getDriverId()) {
				ddlDriver.getSelectionModel().select(i);
			}
		}
		for (int i = 0; i < carrierContractModel.getCategoryList().size(); i++) {
			Category category = carrierContractModel.getCategoryList().get(i);
			ddlCategory.getItems().add(category.getName());
			if (category.getCategoryId() == carrierContractModel.getCategoryId()) {
				ddlCategory.getSelectionModel().select(i);
			}
		}
		for (int i = 0; i < carrierContractModel.getEquipmentList().size(); i++) {
			Equipment equipment = carrierContractModel.getEquipmentList().get(i);
			ddlEquipment.getItems().add(equipment.getEquipmentName());
			if (equipment.getEquipmentId() == carrierContractModel.getEquipmentId()) {
				ddlDriver.getSelectionModel().select(i);
			}
		}
		for (int i = 0; i < carrierContractModel.getArrangedWithList().size(); i++) {
			ArrangedWithModel type = carrierContractModel.getArrangedWithList().get(i);
			ddlArrangedWith.getItems().add(type.getArrangedWith());
			if (type.getId() == carrierContractModel.getArrangedWithId()) {
				ddlArrangedWith.getSelectionModel().select(i);
			}
		}
		for (int i = 0; i < carrierContractModel.getCurrencyList().size(); i++) {
			Type type = carrierContractModel.getCurrencyList().get(i);
			ddlCurrancy.getItems().add(type.getTypeName());
			if (type.getTypeId() == carrierContractModel.getCurrencyId()) {
				ddlCurrancy.getSelectionModel().select(i);
			}
		}
		for (int i = 0; i < carrierContractModel.getRoleList().size(); i++) {
			Type type = carrierContractModel.getRoleList().get(i);
			ddlRole.getItems().add(type.getTypeName());
			if (type.getTypeId() == carrierContractModel.getRoleId()) {
				ddlRole.getSelectionModel().select(i);
			}
		}
		for (int i = 0; i < carrierContractModel.getCommodityList().size(); i++) {
			Type type = carrierContractModel.getCommodityList().get(i);
			ddlCommodity.getItems().add(type.getTypeName());
			if (type.getTypeId() == carrierContractModel.getCommodityId()) {
				ddlCommodity.getSelectionModel().select(i);
			}
		}
		for (int i = 0; i < carrierContractModel.getDispatcherList().size(); i++) {
			DispatcherModel type = carrierContractModel.getDispatcherList().get(i);
			ddlDispatcher.getItems().add(type.getArrangedWith());
			if (type.getId() == carrierContractModel.getDispatcherId()) {
				ddlDispatcher.getSelectionModel().select(i);
			}
		}
	}
}
