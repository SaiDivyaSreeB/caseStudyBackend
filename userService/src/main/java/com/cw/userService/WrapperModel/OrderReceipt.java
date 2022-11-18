package com.cw.userService.WrapperModel;

public class OrderReceipt {
    private String orderId;
    private String CustomerEmail;
    private String washerName;
    private String washPackName;
    private String washPackDetails;
    private int washPackPrice;

    public OrderReceipt(){

    }
    public OrderReceipt(String orderId, String customerEmail, String washerName, String washPackName, String washPackDetails, int washPackPrice) {
        this.orderId = orderId;
        CustomerEmail = customerEmail;
        this.washerName = washerName;
        this.washPackName = washPackName;
        this.washPackDetails = washPackDetails;
        this.washPackPrice = washPackPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerEmail() {
        return CustomerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        CustomerEmail = customerEmail;
    }

    public String getWasherName() {
        return washerName;
    }

    public void setWasherName(String washerName) {
        this.washerName = washerName;
    }

    public String getWashPackName() {
        return washPackName;
    }

    public void setWashPackName(String washPackName) {
        this.washPackName = washPackName;
    }

    public String getWashPackDetails() {
        return washPackDetails;
    }

    public void setWashPackDetails(String washPackDetails) {
        this.washPackDetails = washPackDetails;
    }

    public int getWashPackPrice() {
        return washPackPrice;
    }

    public void setWashPackPrice(int washPackPrice) {
        this.washPackPrice = washPackPrice;
    }

    @Override
    public String toString() {
        return "OrderReceipt{" +
                "orderId='" + orderId + '\'' +
                ", CustomerEmail='" + CustomerEmail + '\'' +
                ", washerName='" + washerName + '\'' +
                ", washPackName='" + washPackName + '\'' +
                ", washPackDetails='" + washPackDetails + '\'' +
                ", washPackPrice=" + washPackPrice +
                '}';
    }
}
