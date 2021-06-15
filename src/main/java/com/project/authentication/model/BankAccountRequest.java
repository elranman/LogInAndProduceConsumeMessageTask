package com.project.authentication.model;

import java.io.Serializable;

public class BankAccountRequest implements Serializable {

    private int accountId;
    private String customerName;
    private String branchId;
    private String customerType;
    private String customerCredit;

    public BankAccountRequest(int accountId, String customerName, String branchId, String customerType, String customerCredit) {
        this.accountId = accountId;
        this.customerName = customerName;
        this.branchId = branchId;
        this.customerType = customerType;
        this.customerCredit = customerCredit;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerCredit() {
        return customerCredit;
    }

    public void setCustomerCredit(String customerCredit) {
        this.customerCredit = customerCredit;
    }

}
