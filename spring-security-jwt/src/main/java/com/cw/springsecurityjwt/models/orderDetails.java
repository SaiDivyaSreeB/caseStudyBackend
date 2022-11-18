package com.cw.springsecurityjwt.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Document(collection="orders")
public class orderDetails {
    @Id
    String orderId;
    //@ApiModelProperty(notes= "the person's email")
    @NotEmpty(message = "Email cant be empty")
    String userEmailId;
    @NotEmpty(message="Washer Name can't be empty")
    String washerName;
    @NotEmpty(message="Wash pack can't be empty")
    String washpack;
    @NotEmpty(message="phone no can't be empty")
    long phoneNo;
    @NotEmpty(message="Pincode cant be empty")
    String areapincode;
    @NotEmpty(message = "status cant be empty")
    String status;
    @NotEmpty(message="car field cant be empty")
    Car cars;
    String addon;
    //default constructor
    public orderDetails(){

    }

    //constructor
    public orderDetails(String orderId, String userEmailId, String washerName, String washpack, long phoneNo, String areapincode, String status, Car cars, String addon) {
        this.orderId = orderId;
        this.userEmailId = userEmailId;
        this.washerName = washerName;
        this.washpack = washpack;
        this.phoneNo = phoneNo;
        this.areapincode = areapincode;
        this.status = status;
        this.cars = cars;
        this.addon=addon;
    }

    //getters and setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getWasherName() {
        return washerName;
    }

    public void setWasherName(String washerName) {
        this.washerName = washerName;
    }

    public String getWashpack() {
        return washpack;
    }

    public void setWashpack(String washpack) {
        this.washpack = washpack;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAreapincode() {
        return areapincode;
    }

    public void setAreapincode(String areapincode) {
        this.areapincode = areapincode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Car getCars() {
        return cars;
    }

    public void setCars(Car cars) {
        this.cars = cars;
    }
    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon;
    }

    @Override
    public String toString() {
        return "orderDetails{" +
                "orderId='" + orderId + '\'' +
                ", userEmailId='" + userEmailId + '\'' +
                ", washerName='" + washerName + '\'' +
                ", washpack='" + washpack + '\'' +
                ", phoneNo=" + phoneNo +
                ", areapincode='" + areapincode + '\'' +
                ", status='" + status + '\'' +
                ", cars=" + cars +
                ", addon='" + addon + '\'' +
                '}';
    }
}
