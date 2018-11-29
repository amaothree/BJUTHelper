package com.assignment.amao.bjuthelper;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class Event extends BmobObject {
    private String title;
    private String detail;
    private int money;
    private String address;
    private String status;
    private String customerId;
    private String helperId;
    private String customerName;
    private String helperName;
    private String customerPhone;
    private String helperPhone;

    private User customer;
    private User helper;

    public Event(String title, String detail, int money, String address, String status, String customerId, String helperId) {
        this.title = title;
        this.detail = detail;
        this.money = money;
        this.address = address;
        this.status = status;
        this.customerId = customerId;
        this.helperId = helperId;

        customer = (User) BmobUser.getObjectByKey(customerId);
        helper = (User) BmobUser.getObjectByKey(helperId);

        customerName = customer.getUsername();
        customerPhone = customer.getMobilePhoneNumber();

        helperName = helper.getUsername();
        helperPhone = helper.getMobilePhoneNumber();

    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public int getMoney() {
        return money;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getHelperId() {
        return helperId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getHelperName() {
        return helperName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getHelperPhone() {
        return helperPhone;
    }
}
