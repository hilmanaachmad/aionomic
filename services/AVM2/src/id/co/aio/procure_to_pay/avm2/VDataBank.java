/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VDataBank generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`v_dataBank`")
public class VDataBank implements Serializable {

    private Integer idBank;
    private Integer idDocBank;
    private Integer idVendor;
    private String vendorCode;
    private String countryKey;
    private String bankKey;
    private String bankName;
    private String userName;
    private String accountNumber;
    private String accountName;
    private String branch;
    private String currency;
    private String beneficiaryType;
    private String beneficiaryStatus;
    private String fileName;
    private String pathFile;
    private String status;

    @Id
    @Column(name = "`idBank`", nullable = false, scale = 0, precision = 10)
    public Integer getIdBank() {
        return this.idBank;
    }

    public void setIdBank(Integer idBank) {
        this.idBank = idBank;
    }

    @Column(name = "`idDocBank`", nullable = true, scale = 0, precision = 10)
    public Integer getIdDocBank() {
        return this.idDocBank;
    }

    public void setIdDocBank(Integer idDocBank) {
        this.idDocBank = idDocBank;
    }

    @Column(name = "`IdVendor`", nullable = true, scale = 0, precision = 10)
    public Integer getIdVendor() {
        return this.idVendor;
    }

    public void setIdVendor(Integer idVendor) {
        this.idVendor = idVendor;
    }

    @Column(name = "`VendorCode`", nullable = true, length = 10)
    public String getVendorCode() {
        return this.vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    @Column(name = "`CountryKey`", nullable = true, length = 3)
    public String getCountryKey() {
        return this.countryKey;
    }

    public void setCountryKey(String countryKey) {
        this.countryKey = countryKey;
    }

    @Column(name = "`BankKey`", nullable = true, length = 15)
    public String getBankKey() {
        return this.bankKey;
    }

    public void setBankKey(String bankKey) {
        this.bankKey = bankKey;
    }

    @Column(name = "`BankName`", nullable = true, length = 255)
    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "`UserName`", nullable = true, length = 255)
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "`AccountNumber`", nullable = true, length = 30)
    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Column(name = "`AccountName`", nullable = true, length = 60)
    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Column(name = "`Branch`", nullable = true, length = 255)
    public String getBranch() {
        return this.branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Column(name = "`Currency`", nullable = true, length = 4)
    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "`BeneficiaryType`", nullable = true, length = 50)
    public String getBeneficiaryType() {
        return this.beneficiaryType;
    }

    public void setBeneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    @Column(name = "`BeneficiaryStatus`", nullable = true, length = 50)
    public String getBeneficiaryStatus() {
        return this.beneficiaryStatus;
    }

    public void setBeneficiaryStatus(String beneficiaryStatus) {
        this.beneficiaryStatus = beneficiaryStatus;
    }

    @Column(name = "`FileName`", nullable = true, length = 2147483647)
    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "`PathFile`", nullable = true, length = 355)
    public String getPathFile() {
        return this.pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    @Column(name = "`Status`", nullable = false, length = 5)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VDataBank)) return false;
        final VDataBank vdataBank = (VDataBank) o;
        return Objects.equals(getIdBank(), vdataBank.getIdBank());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdBank());
    }
}