/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QinsertBankRequest implements Serializable {


    @JsonProperty("countryCode")
    @NotNull
    private String countryCode;

    @JsonProperty("bankName")
    @NotNull
    private String bankName;

    @JsonProperty("userName")
    @NotNull
    private String userName;

    @JsonProperty("accountNumber")
    @NotNull
    private String accountNumber;

    @JsonProperty("accountName")
    @NotNull
    private String accountName;

    @JsonProperty("branch")
    @NotNull
    private String branch;

    @JsonProperty("currency")
    @NotNull
    private String currency;

    @JsonProperty("beneficiaryType")
    @NotNull
    private String beneficiaryType;

    @JsonProperty("beneficiaryStatus")
    @NotNull
    private String beneficiaryStatus;

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBranch() {
        return this.branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBeneficiaryType() {
        return this.beneficiaryType;
    }

    public void setBeneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public String getBeneficiaryStatus() {
        return this.beneficiaryStatus;
    }

    public void setBeneficiaryStatus(String beneficiaryStatus) {
        this.beneficiaryStatus = beneficiaryStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QinsertBankRequest)) return false;
        final QinsertBankRequest qinsertBankRequest = (QinsertBankRequest) o;
        return Objects.equals(getCountryCode(), qinsertBankRequest.getCountryCode()) &&
                Objects.equals(getBankName(), qinsertBankRequest.getBankName()) &&
                Objects.equals(getUserName(), qinsertBankRequest.getUserName()) &&
                Objects.equals(getAccountNumber(), qinsertBankRequest.getAccountNumber()) &&
                Objects.equals(getAccountName(), qinsertBankRequest.getAccountName()) &&
                Objects.equals(getBranch(), qinsertBankRequest.getBranch()) &&
                Objects.equals(getCurrency(), qinsertBankRequest.getCurrency()) &&
                Objects.equals(getBeneficiaryType(), qinsertBankRequest.getBeneficiaryType()) &&
                Objects.equals(getBeneficiaryStatus(), qinsertBankRequest.getBeneficiaryStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryCode(),
                getBankName(),
                getUserName(),
                getAccountNumber(),
                getAccountName(),
                getBranch(),
                getCurrency(),
                getBeneficiaryType(),
                getBeneficiaryStatus());
    }
}