package com.dpu.entity;

public class CompanyWorkingHours {

    private int workingId;
    private String workingDay;
    private String open1;
    private String close1;
    private String open2;
    private String close2;
    private int is24Hrs;
    private int additionalContactId;
	public int getWorkingId() {
		return workingId;
	}
	public void setWorkingId(int workingId) {
		this.workingId = workingId;
	}
	public String getWorkingDay() {
		return workingDay;
	}
	public void setWorkingDay(String workingDay) {
		this.workingDay = workingDay;
	}
	public String getOpen1() {
		return open1;
	}
	public void setOpen1(String open1) {
		this.open1 = open1;
	}
	public String getClose1() {
		return close1;
	}
	public void setClose1(String close1) {
		this.close1 = close1;
	}
	public String getOpen2() {
		return open2;
	}
	public void setOpen2(String open2) {
		this.open2 = open2;
	}
	public String getClose2() {
		return close2;
	}
	public void setClose2(String close2) {
		this.close2 = close2;
	}
	public int getIs24Hrs() {
		return is24Hrs;
	}
	public void setIs24Hrs(int is24Hrs) {
		this.is24Hrs = is24Hrs;
	}
	public int getAdditionalContactId() {
		return additionalContactId;
	}
	public void setAdditionalContactId(int additionalContactId) {
		this.additionalContactId = additionalContactId;
	}
}
