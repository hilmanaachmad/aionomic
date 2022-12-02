/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.procedure;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class SpBudgetDetail1ResponseContent implements Serializable {


    @ColumnAlias("bh_id")
    private Integer bhId;

    @ColumnAlias("bh_year")
    private String bhYear;

    @ColumnAlias("io_number")
    private String ioNumber;

    @ColumnAlias("io_name")
    private String ioName;

    @ColumnAlias("c_id")
    private Integer cid;

    @ColumnAlias("br_id")
    private Integer brId;

    @ColumnAlias("acc_id")
    private Integer accId;

    @ColumnAlias("department_id")
    private String departmentId;

    @ColumnAlias("bh_status")
    private String bhStatus;

    @ColumnAlias("budget_original")
    private BigDecimal budgetOriginal;

    @ColumnAlias("budget_adjusment")
    private BigDecimal budgetAdjusment;

    @ColumnAlias("budget_additional")
    private BigDecimal budgetAdditional;

    @ColumnAlias("budget_reclass")
    private BigDecimal budgetReclass;

    @ColumnAlias("budget_pr")
    private BigDecimal budgetPr;

    @ColumnAlias("budget_po")
    private BigDecimal budgetPo;

    @ColumnAlias("budget_gr")
    private BigDecimal budgetGr;

    @ColumnAlias("budget_after_adjustment")
    private BigDecimal budgetAfterAdjustment;

    @ColumnAlias("budget_available")
    private BigDecimal budgetAvailable;

    public Integer getBhId() {
        return this.bhId;
    }

    public void setBhId(Integer bhId) {
        this.bhId = bhId;
    }

    public String getBhYear() {
        return this.bhYear;
    }

    public void setBhYear(String bhYear) {
        this.bhYear = bhYear;
    }

    public String getIoNumber() {
        return this.ioNumber;
    }

    public void setIoNumber(String ioNumber) {
        this.ioNumber = ioNumber;
    }

    public String getIoName() {
        return this.ioName;
    }

    public void setIoName(String ioName) {
        this.ioName = ioName;
    }

    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getBrId() {
        return this.brId;
    }

    public void setBrId(Integer brId) {
        this.brId = brId;
    }

    public Integer getAccId() {
        return this.accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getBhStatus() {
        return this.bhStatus;
    }

    public void setBhStatus(String bhStatus) {
        this.bhStatus = bhStatus;
    }

    public BigDecimal getBudgetOriginal() {
        return this.budgetOriginal;
    }

    public void setBudgetOriginal(BigDecimal budgetOriginal) {
        this.budgetOriginal = budgetOriginal;
    }

    public BigDecimal getBudgetAdjusment() {
        return this.budgetAdjusment;
    }

    public void setBudgetAdjusment(BigDecimal budgetAdjusment) {
        this.budgetAdjusment = budgetAdjusment;
    }

    public BigDecimal getBudgetAdditional() {
        return this.budgetAdditional;
    }

    public void setBudgetAdditional(BigDecimal budgetAdditional) {
        this.budgetAdditional = budgetAdditional;
    }

    public BigDecimal getBudgetReclass() {
        return this.budgetReclass;
    }

    public void setBudgetReclass(BigDecimal budgetReclass) {
        this.budgetReclass = budgetReclass;
    }

    public BigDecimal getBudgetPr() {
        return this.budgetPr;
    }

    public void setBudgetPr(BigDecimal budgetPr) {
        this.budgetPr = budgetPr;
    }

    public BigDecimal getBudgetPo() {
        return this.budgetPo;
    }

    public void setBudgetPo(BigDecimal budgetPo) {
        this.budgetPo = budgetPo;
    }

    public BigDecimal getBudgetGr() {
        return this.budgetGr;
    }

    public void setBudgetGr(BigDecimal budgetGr) {
        this.budgetGr = budgetGr;
    }

    public BigDecimal getBudgetAfterAdjustment() {
        return this.budgetAfterAdjustment;
    }

    public void setBudgetAfterAdjustment(BigDecimal budgetAfterAdjustment) {
        this.budgetAfterAdjustment = budgetAfterAdjustment;
    }

    public BigDecimal getBudgetAvailable() {
        return this.budgetAvailable;
    }

    public void setBudgetAvailable(BigDecimal budgetAvailable) {
        this.budgetAvailable = budgetAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpBudgetDetail1ResponseContent)) return false;
        final SpBudgetDetail1ResponseContent spBudgetDetail1responseContent = (SpBudgetDetail1ResponseContent) o;
        return Objects.equals(getBhId(), spBudgetDetail1responseContent.getBhId()) &&
                Objects.equals(getBhYear(), spBudgetDetail1responseContent.getBhYear()) &&
                Objects.equals(getIoNumber(), spBudgetDetail1responseContent.getIoNumber()) &&
                Objects.equals(getIoName(), spBudgetDetail1responseContent.getIoName()) &&
                Objects.equals(getCid(), spBudgetDetail1responseContent.getCid()) &&
                Objects.equals(getBrId(), spBudgetDetail1responseContent.getBrId()) &&
                Objects.equals(getAccId(), spBudgetDetail1responseContent.getAccId()) &&
                Objects.equals(getDepartmentId(), spBudgetDetail1responseContent.getDepartmentId()) &&
                Objects.equals(getBhStatus(), spBudgetDetail1responseContent.getBhStatus()) &&
                Objects.equals(getBudgetOriginal(), spBudgetDetail1responseContent.getBudgetOriginal()) &&
                Objects.equals(getBudgetAdjusment(), spBudgetDetail1responseContent.getBudgetAdjusment()) &&
                Objects.equals(getBudgetAdditional(), spBudgetDetail1responseContent.getBudgetAdditional()) &&
                Objects.equals(getBudgetReclass(), spBudgetDetail1responseContent.getBudgetReclass()) &&
                Objects.equals(getBudgetPr(), spBudgetDetail1responseContent.getBudgetPr()) &&
                Objects.equals(getBudgetPo(), spBudgetDetail1responseContent.getBudgetPo()) &&
                Objects.equals(getBudgetGr(), spBudgetDetail1responseContent.getBudgetGr()) &&
                Objects.equals(getBudgetAfterAdjustment(), spBudgetDetail1responseContent.getBudgetAfterAdjustment()) &&
                Objects.equals(getBudgetAvailable(), spBudgetDetail1responseContent.getBudgetAvailable());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBhId(),
                getBhYear(),
                getIoNumber(),
                getIoName(),
                getCid(),
                getBrId(),
                getAccId(),
                getDepartmentId(),
                getBhStatus(),
                getBudgetOriginal(),
                getBudgetAdjusment(),
                getBudgetAdditional(),
                getBudgetReclass(),
                getBudgetPr(),
                getBudgetPo(),
                getBudgetGr(),
                getBudgetAfterAdjustment(),
                getBudgetAvailable());
    }
}