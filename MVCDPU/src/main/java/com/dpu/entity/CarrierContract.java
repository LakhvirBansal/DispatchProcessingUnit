package com.dpu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carriercontract")
public class CarrierContract {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "carrier_contract_id")
	private Long contractNoId;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "contract_rate")
	private Double contractRate;

	@Column(name = "carrier_rate")
	private String carrierRat;

	@Column(name = "hours")
	private String hours;

	@Column(name = "miles")
	private String miles;

	@Column(name = "dispatched")
	private Date dispatched;

	@Column(name = "createdBy")
	private Long createdBy;

	@Column(name = "ins_expires")
	private String insExpires;

	@Column(name = "cargo")
	private String cargo;

	@Column(name = "libility")
	private String liabity;

	@Column(name = "trans_doc")
	private String transDoc;

	@Column(name = "mc_no")
	private String mCno;

	@Column(name = "dot_no")
	private String dOTno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carrier_id")
	private Carrier carrier;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "arrangedwith_id")
	private CarrierAdditionalContacts arrangedWith;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_id")
	private Driver driver;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id")
	private Type currency;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Type role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_id")
	private Equipment equipment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commodity_id")
	private Type commodity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "division_id")
	private Division division;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dispatcher_id")
	private Dispatcher dispatcher;

	public Long getContractNoId() {

		return contractNoId;
	}

	public void setContractNoId(Long contractNoId) {

		this.contractNoId = contractNoId;
	}

	public String getContractNo() {

		return contractNo;
	}

	public void setContractNo(String contractNo) {

		this.contractNo = contractNo;
	}

	public Double getContractRate() {

		return contractRate;
	}

	public void setContractRate(Double contractRate) {

		this.contractRate = contractRate;
	}

	public String getCarrierRat() {

		return carrierRat;
	}

	public void setCarrierRat(String carrierRat) {

		this.carrierRat = carrierRat;
	}

	public String getHours() {

		return hours;
	}

	public void setHours(String hours) {

		this.hours = hours;
	}

	public String getMiles() {

		return miles;
	}

	public void setMiles(String miles) {

		this.miles = miles;
	}

	public Date getDispatched() {

		return dispatched;
	}

	public void setDispatched(Date dispatched) {

		this.dispatched = new Date();
	}

	public Long getCreatedBy() {

		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {

		this.createdBy = createdBy;
	}

	public String getInsExpires() {

		return insExpires;
	}

	public void setInsExpires(String insExpires) {

		this.insExpires = insExpires;
	}

	public String getCargo() {

		return cargo;
	}

	public void setCargo(String cargo) {

		this.cargo = cargo;
	}

	public String getLiabity() {

		return liabity;
	}

	public void setLiabity(String liabity) {

		this.liabity = liabity;
	}

	public String getTransDoc() {

		return transDoc;
	}

	public void setTransDoc(String transDoc) {

		this.transDoc = transDoc;
	}

	public String getmCno() {

		return mCno;
	}

	public void setmCno(String mCno) {

		this.mCno = mCno;
	}

	public String getdOTno() {

		return dOTno;
	}

	public void setdOTno(String dOTno) {

		this.dOTno = dOTno;
	}

	public Type getCurrency() {

		return currency;
	}

	public void setCurrency(Type currency) {

		this.currency = currency;
	}

	public Carrier getCarrier() {

		return carrier;
	}

	public void setCarrier(Carrier carrier) {

		this.carrier = carrier;
	}

	public CarrierAdditionalContacts getArrangedWith() {

		return arrangedWith;
	}

	public void setArrangedWith(CarrierAdditionalContacts arrangedWith) {

		this.arrangedWith = arrangedWith;
	}

	public Driver getDriver() {

		return driver;
	}

	public void setDriver(Driver driver) {

		this.driver = driver;
	}

	public Category getCategory() {

		return category;
	}

	public void setCategory(Category category) {

		this.category = category;
	}

	public Type getRole() {

		return role;
	}

	public void setRole(Type role) {

		this.role = role;
	}

	public Equipment getEquipment() {

		return equipment;
	}

	public void setEquipment(Equipment equipment) {

		this.equipment = equipment;
	}

	public Type getCommodity() {

		return commodity;
	}

	public void setCommodity(Type commodity) {

		this.commodity = commodity;
	}

	public Division getDivision() {

		return division;
	}

	public void setDivision(Division division) {

		this.division = division;
	}

	public Dispatcher getDispatcher() {

		return dispatcher;
	}

	public void setDispatcher(Dispatcher dispatcher) {

		this.dispatcher = dispatcher;
	}

}
