/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class GetTaxResponse implements Serializable {


    @ColumnAlias("IdTax")
    private Integer idTax;

    @ColumnAlias("vendorCode")
    private String vendorCode;

    @ColumnAlias("UserName")
    private String userName;

    @ColumnAlias("ExemptionType")
    private String exemptionType;

    @ColumnAlias("ExemptionNumber")
    private String exemptionNumber;

    @ColumnAlias("DateFrom")
    private LocalDateTime dateFrom;

    @ColumnAlias("DateTo")
    private LocalDateTime dateTo;

    @ColumnAlias("CompanyCode")
    private Integer companyCode;

    @ColumnAlias("idExemption")
    private Integer idExemption;

    @ColumnAlias("fileNameExemption")
    private String fileNameExemption;

    @ColumnAlias("fileExemption")
    private String fileExemption;

    @ColumnAlias("Status")
    private String status;

    public Integer getIdTax() {
        return this.idTax;
    }

    public void setIdTax(Integer idTax) {
        this.idTax = idTax;
    }

    public String getVendorCode() {
        return this.vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getExemptionType() {
        return this.exemptionType;
    }

    public void setExemptionType(String exemptionType) {
        this.exemptionType = exemptionType;
    }

    public String getExemptionNumber() {
        return this.exemptionNumber;
    }

    public void setExemptionNumber(String exemptionNumber) {
        this.exemptionNumber = exemptionNumber;
    }

    public LocalDateTime getDateFrom() {
        return this.dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return this.dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getCompanyCode() {
        return this.companyCode;
    }

    public void setCompanyCode(Integer companyCode) {
        this.companyCode = companyCode;
    }

    public Integer getIdExemption() {
        return this.idExemption;
    }

    public void setIdExemption(Integer idExemption) {
        this.idExemption = idExemption;
    }

    public String getFileNameExemption() {
        return this.fileNameExemption;
    }

    public void setFileNameExemption(String fileNameExemption) {
        this.fileNameExemption = fileNameExemption;
    }

    public String getFileExemption() {
        return this.fileExemption;
    }

    public void setFileExemption(String fileExemption) {
        this.fileExemption = fileExemption;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetTaxResponse)) return false;
        final GetTaxResponse getTaxResponse = (GetTaxResponse) o;
        return Objects.equals(getIdTax(), getTaxResponse.getIdTax()) &&
                Objects.equals(getVendorCode(), getTaxResponse.getVendorCode()) &&
                Objects.equals(getUserName(), getTaxResponse.getUserName()) &&
                Objects.equals(getExemptionType(), getTaxResponse.getExemptionType()) &&
                Objects.equals(getExemptionNumber(), getTaxResponse.getExemptionNumber()) &&
                Objects.equals(getDateFrom(), getTaxResponse.getDateFrom()) &&
                Objects.equals(getDateTo(), getTaxResponse.getDateTo()) &&
                Objects.equals(getCompanyCode(), getTaxResponse.getCompanyCode()) &&
                Objects.equals(getIdExemption(), getTaxResponse.getIdExemption()) &&
                Objects.equals(getFileNameExemption(), getTaxResponse.getFileNameExemption()) &&
                Objects.equals(getFileExemption(), getTaxResponse.getFileExemption()) &&
                Objects.equals(getStatus(), getTaxResponse.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdTax(),
                getVendorCode(),
                getUserName(),
                getExemptionType(),
                getExemptionNumber(),
                getDateFrom(),
                getDateTo(),
                getCompanyCode(),
                getIdExemption(),
                getFileNameExemption(),
                getFileExemption(),
                getStatus());
    }
}