package com.project.authentication.model;

import java.io.Serializable;

public class BankAccountDetailsResponse extends GeneralResponse implements Serializable {
    private String bankAcountJSONDetails;

    public BankAccountDetailsResponse(String bankAcountJSONDetails) {
        this.bankAcountJSONDetails = bankAcountJSONDetails;
    }

    public String getBankAcountJSONDetails() {
        return bankAcountJSONDetails;
    }

    public void setBankAcountJSONDetails(String bankAcountJSONDetails) {
        this.bankAcountJSONDetails = bankAcountJSONDetails;
    }
}
