/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uci.rest.model;

/**
 *
 * @author Thanh
 */
public class Order {
    String firstName, lastName, email, telephone, address, city, state, country,
            shippingAddress, shippingCity, shippingState, shippingCountry, cardNumber, billingZip;
    int shippingOption, CCV, shippingZip, quantity, expirationMonth, expirationYear;
    float grandTotal;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    public void setShippingOption(int shippingOption) {
        this.shippingOption = shippingOption;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public void setCCV(int CCV) {
        this.CCV = CCV;
    }

    public void setShippingZip(int shippingZip) {
        this.shippingZip = shippingZip;
    }

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }
    
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
    public void setGrandTotal(float grandTotal){
        this.grandTotal = grandTotal;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public int getShippingOption() {
        return shippingOption;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public String getShippingState() {
        return shippingState;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public int getCCV() {
        return CCV;
    }

    public int getShippingZip() {
        return shippingZip;
    }

    public String getBillingZip() {
        return billingZip;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public float getGrandTotal() {
        return grandTotal;
    }
    
    
    
}
