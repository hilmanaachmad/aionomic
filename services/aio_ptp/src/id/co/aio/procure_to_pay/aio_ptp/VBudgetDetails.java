/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VBudgetDetails generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`v_budget_details`")
public class VBudgetDetails implements Serializable {

    private Integer bdId = 0;
    private int bhId = 0;
    private String year;
    private String io;
    private Integer cid;
    private Integer brId;
    private Integer accId;
    private String bhStatus = "active";
    private String departmentId;
    private LocalDateTime datetime;
    private String type;
    private String docNumber;
    private String remarks;
    private BigDecimal budgetOriginal;
    private BigDecimal reclass;
    private BigDecimal additional;
    private BigDecimal adjustment;
    private BigDecimal reserved;
    private BigDecimal commitment;
    private BigDecimal actual;
    private BigDecimal budgetAfterAdjustment;
    private BigDecimal available;
    private String creator = "SYSTEM";
    private String createdBy;
    private String coa;
    private String department;
    private String ioName;
    private Integer bdAdditionalDocId;
    private String bdAdditionalDocType;

    @Id
    @Column(name = "`bd_id`", nullable = true, scale = 0, precision = 10)
    public Integer getBdId() {
        return this.bdId;
    }

    public void setBdId(Integer bdId) {
        this.bdId = bdId;
    }

    @Column(name = "`bh_id`", nullable = false, scale = 0, precision = 10)
    public int getBhId() {
        return this.bhId;
    }

    public void setBhId(int bhId) {
        this.bhId = bhId;
    }

    @Column(name = "`year`", nullable = true, length = 5)
    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Column(name = "`io`", nullable = true, length = 100)
    public String getIo() {
        return this.io;
    }

    public void setIo(String io) {
        this.io = io;
    }

    @Column(name = "`c_id`", nullable = true, scale = 0, precision = 10)
    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    @Column(name = "`br_id`", nullable = true, scale = 0, precision = 10)
    public Integer getBrId() {
        return this.brId;
    }

    public void setBrId(Integer brId) {
        this.brId = brId;
    }

    @Column(name = "`acc_id`", nullable = true, scale = 0, precision = 10)
    public Integer getAccId() {
        return this.accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    @Column(name = "`bh_status`", nullable = true, length = 20)
    public String getBhStatus() {
        return this.bhStatus;
    }

    public void setBhStatus(String bhStatus) {
        this.bhStatus = bhStatus;
    }

    @Column(name = "`department_id`", nullable = true, length = 100)
    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "`datetime`", nullable = true)
    public LocalDateTime getDatetime() {
        return this.datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Column(name = "`type`", nullable = true, length = 100)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "`doc_number`", nullable = true, length = 100)
    public String getDocNumber() {
        return this.docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    @Column(name = "`remarks`", nullable = true, length = 65535)
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "`budget_original`", nullable = true, scale = 3, precision = 30)
    public BigDecimal getBudgetOriginal() {
        return this.budgetOriginal;
    }

    public void setBudgetOriginal(BigDecimal budgetOriginal) {
        this.budgetOriginal = budgetOriginal;
    }

    @Column(name = "`reclass`", nullable = true, scale = 3, precision = 30)
    public BigDecimal getReclass() {
        return this.reclass;
    }

    public void setReclass(BigDecimal reclass) {
        this.reclass = reclass;
    }

    @Column(name = "`additional`", nullable = true, scale = 3, precision = 30)
    public BigDecimal getAdditional() {
        return this.additional;
    }

    public void setAdditional(BigDecimal additional) {
        this.additional = additional;
    }

    @Column(name = "`adjustment`", nullable = true, scale = 3, precision = 30)
    public BigDecimal getAdjustment() {
        return this.adjustment;
    }

    public void setAdjustment(BigDecimal adjustment) {
        this.adjustment = adjustment;
    }

    @Column(name = "`reserved`", nullable = true, scale = 3, precision = 31)
    public BigDecimal getReserved() {
        return this.reserved;
    }

    public void setReserved(BigDecimal reserved) {
        this.reserved = reserved;
    }

    @Column(name = "`commitment`", nullable = true, scale = 3, precision = 31)
    public BigDecimal getCommitment() {
        return this.commitment;
    }

    public void setCommitment(BigDecimal commitment) {
        this.commitment = commitment;
    }

    @Column(name = "`actual`", nullable = true, scale = 3, precision = 31)
    public BigDecimal getActual() {
        return this.actual;
    }

    public void setActual(BigDecimal actual) {
        this.actual = actual;
    }

    @Column(name = "`budget_after_adjustment`", nullable = true, scale = 3, precision = 31)
    public BigDecimal getBudgetAfterAdjustment() {
        return this.budgetAfterAdjustment;
    }

    public void setBudgetAfterAdjustment(BigDecimal budgetAfterAdjustment) {
        this.budgetAfterAdjustment = budgetAfterAdjustment;
    }

    @Column(name = "`available`", nullable = true, scale = 3, precision = 30)
    public BigDecimal getAvailable() {
        return this.available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    @Column(name = "`creator`", nullable = true, length = 100)
    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name = "`created_by`", nullable = true, length = 100)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "`coa`", nullable = true, length = 100)
    public String getCoa() {
        return this.coa;
    }

    public void setCoa(String coa) {
        this.coa = coa;
    }

    @Column(name = "`department`", nullable = true, length = 100)
    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Column(name = "`io_name`", nullable = true, length = 100)
    public String getIoName() {
        return this.ioName;
    }

    public void setIoName(String ioName) {
        this.ioName = ioName;
    }

    @Column(name = "`bd_additional_doc_id`", nullable = true, scale = 0, precision = 10)
    public Integer getBdAdditionalDocId() {
        return this.bdAdditionalDocId;
    }

    public void setBdAdditionalDocId(Integer bdAdditionalDocId) {
        this.bdAdditionalDocId = bdAdditionalDocId;
    }

    @Column(name = "`bd_additional_doc_type`", nullable = true, length = 100)
    public String getBdAdditionalDocType() {
        return this.bdAdditionalDocType;
    }

    public void setBdAdditionalDocType(String bdAdditionalDocType) {
        this.bdAdditionalDocType = bdAdditionalDocType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VBudgetDetails)) return false;
        final VBudgetDetails vbudgetDetails = (VBudgetDetails) o;
        return Objects.equals(getBdId(), vbudgetDetails.getBdId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBdId());
    }
}