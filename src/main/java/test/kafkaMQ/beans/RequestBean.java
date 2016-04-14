package test.kafkaMQ.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RequestBean implements Serializable{
	private String logisticProviderID;
	private String mailNo;
	private String mailType;
	private Double weight;
	private String senAreaCode;
	private String recAreaCode;
	private String senCityCode;
	private String recCityCode;
	private String senProvCode;
	private String senCity;
	private String senCountyCode;
	private String senAddress;
	private String senName;
	private String senMobile;
	private String senPhone;
	private String recProvCode;
	private String recCity;
	private String recCountyCode;
	private String recAddress;
	private String recName;
	private String recMobile;
	private String recPhone;
	private String typeOfContents;
	private String nameOfCoutents;
	private String mailCode;
	private String recDatetime;
	private String insuranceValue;

	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getLogisticProviderID() {
		return logisticProviderID;
	}
	public void setLogisticProviderID(String logisticProviderID) {
		this.logisticProviderID = logisticProviderID;
	}
	public String getMailNo() {
		return mailNo;
	}
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	public String getSenAreaCode() {
		return senAreaCode;
	}
	public void setSenAreaCode(String senAreaCode) {
		this.senAreaCode = senAreaCode;
	}
	public String getRecAreaCode() {
		return recAreaCode;
	}
	public void setRecAreaCode(String recAreaCode) {
		this.recAreaCode = recAreaCode;
	}
	public String getSenCityCode() {
		return senCityCode;
	}
	public void setSenCityCode(String senCityCode) {
		this.senCityCode = senCityCode;
	}
	public String getRecCityCode() {
		return recCityCode;
	}
	public void setRecCityCode(String recCityCode) {
		this.recCityCode = recCityCode;
	}
	public String getSenProvCode() {
		return senProvCode;
	}
	public void setSenProvCode(String senProvCode) {
		this.senProvCode = senProvCode;
	}
	public String getSenCity() {
		return senCity;
	}
	public void setSenCity(String senCity) {
		this.senCity = senCity;
	}
	public String getSenAddress() {
		return senAddress;
	}
	public void setSenAddress(String senAddress) {
		this.senAddress = senAddress;
	}
	public String getSenName() {
		return senName;
	}
	public void setSenName(String senName) {
		this.senName = senName;
	}
	public String getSenMobile() {
		return senMobile;
	}
	public void setSenMobile(String senMobile) {
		this.senMobile = senMobile;
	}
	public String getSenPhone() {
		return senPhone;
	}
	public void setSenPhone(String senPhone) {
		this.senPhone = senPhone;
	}
	public String getRecProvCode() {
		return recProvCode;
	}
	public void setRecProvCode(String recProvCode) {
		this.recProvCode = recProvCode;
	}
	public String getRecCity() {
		return recCity;
	}
	public void setRecCity(String recCity) {
		this.recCity = recCity;
	}
	public String getSenCountyCode() {
		return senCountyCode;
	}
	public void setSenCountyCode(String senCountyCode) {
		this.senCountyCode = senCountyCode;
	}
	public String getRecCountyCode() {
		return recCountyCode;
	}
	public void setRecCountyCode(String recCountyCode) {
		this.recCountyCode = recCountyCode;
	}
	public String getRecAddress() {
		return recAddress;
	}
	public void setRecAddress(String recAddress) {
		this.recAddress = recAddress;
	}
	public String getRecName() {
		return recName;
	}
	public void setRecName(String recName) {
		this.recName = recName;
	}
	public String getRecMobile() {
		return recMobile;
	}
	public void setRecMobile(String recMobile) {
		this.recMobile = recMobile;
	}
	public String getRecPhone() {
		return recPhone;
	}
	public void setRecPhone(String recPhone) {
		this.recPhone = recPhone;
	}
	public String getTypeOfContents() {
		return typeOfContents;
	}
	public void setTypeOfContents(String typeOfContents) {
		this.typeOfContents = typeOfContents;
	}
	public String getNameOfCoutents() {
		return nameOfCoutents;
	}
	public void setNameOfCoutents(String nameOfCoutents) {
		this.nameOfCoutents = nameOfCoutents;
	}
	public String getMailCode() {
		return mailCode;
	}
	public void setMailCode(String mailCode) {
		this.mailCode = mailCode;
	}
	public String getRecDatetime() {
		return recDatetime;
	}
	public void setRecDatetime(String recDatetime) {
		this.recDatetime = recDatetime;
	}
	public String getInsuranceValue() {
		return insuranceValue;
	}
	public void setInsuranceValue(String insuranceValue) {
		this.insuranceValue = insuranceValue;
	}
}
