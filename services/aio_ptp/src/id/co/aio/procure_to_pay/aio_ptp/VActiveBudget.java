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
 * VActiveBudget generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`v_active_budget`")
public class VActiveBudget implements Serializable {

    private Integer bhId = 0;
    private Integer buId;
    private Integer cid;
    private Integer accId;
    private Integer brId;
    private String bhYear;
    private String department;
    private String departmentId;
    private String bhEstDate;
    private String bhCurrency;
    private String ioNumber;
    private String ioName;
    private String coa;
    private Double bhAmount = 0.000D;
    private String userId;
    private LocalDateTime bhCreatedAt;
    private String bhCreatedBy = "SYSTEM";
    private String bhStatus = "active";
    private BigDecimal availableBudget;
    private Integer bdId;

    @Id
    @Column(name = "`bh_id`", nullable = false, scale = 0, precision = 10)
    public Integer getBhId() {
        return this.bhId;
    }

    public void setBhId(Integer bhId) {
        this.bhId = bhId;
    }

    @Column(name = "`bu_id`", nullable = true, scale = 0, precision = 10)
    public Integer getBuId() {
        return this.buId;
    }

    public void setBuId(Integer buId) {
        this.buId = buId;
    }

    @Column(name = "`c_id`", nullable = true, scale = 0, precision = 10)
    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    @Column(name = "`acc_id`", nullable = true, scale = 0, precision = 10)
    public Integer getAccId() {
        return this.accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    @Column(name = "`br_id`", nullable = true, scale = 0, precision = 10)
    public Integer getBrId() {
        return this.brId;
    }

    public void setBrId(Integer brId) {
        this.brId = brId;
    }

    @Column(name = "`bh_year`", nullable = true, length = 5)
    public String getBhYear() {
        return this.bhYear;
    }

    public void setBhYear(String bhYear) {
        this.bhYear = bhYear;
    }

    @Column(name = "`department`", nullable = true, length = 100)
    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Column(name = "`department_id`", nullable = true, length = 100)
    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "`bh_est_date`", nullable = true, length = 100)
    public String getBhEstDate() {
        return this.bhEstDate;
    }

    public void setBhEstDate(String bhEstDate) {
        this.bhEstDate = bhEstDate;
    }

    @Column(name = "`bh_currency`", nullable = true, length = 5)
    public String getBhCurrency() {
        return this.bhCurrency;
    }

    public void setBhCurrency(String bhCurrency) {
        this.bhCurrency = bhCurrency;
    }

    @Column(name = "`io_number`", nullable = true, length = 100)
    public String getIoNumber() {
        return this.ioNumber;
    }

    public void setIoNumber(String ioNumber) {
        this.ioNumber = ioNumber;
    }

    @Column(name = "`io_name`", nullable = true, length = 100)
    public String getIoName() {
        return this.ioName;
    }

    public void setIoName(String ioName) {
        this.ioName = ioName;
    }

    @Column(name = "`coa`", nullable = true, length = 100)
    public String getCoa() {
        return this.coa;
    }

    public void setCoa(String coa) {
        this.coa = coa;
    }

    @Column(name = "`bh_amount`", nullable = true, scale = 3, precision = 15)
    public Double getBhAmount() {
        return this.bhAmount;
    }

    public void setBhAmount(Double bhAmount) {
        this.bhAmount = bhAmount;
    }

    @Column(name = "`user_id`", nullable = true, length = 100)
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "`bh_created_at`", nullable = true)
    public LocalDateTime getBhCreatedAt() {
        return this.bhCreatedAt;
    }

    public void setBhCreatedAt(LocalDateTime bhCreatedAt) {
        this.bhCreatedAt = bhCreatedAt;
    }

    @Column(name = "`bh_created_by`", nullable = true, length = 100)
    public String getBhCreatedBy() {
        return this.bhCreatedBy;
    }

    public void setBhCreatedBy(String bhCreatedBy) {
        this.bhCreatedBy = bhCreatedBy;
    }

    @Column(name = "`bh_status`", nullable = true, length = 20)
    public String getBhStatus() {
        return this.bhStatus;
    }

    public void setBhStatus(String bhStatus) {
        this.bhStatus = bhStatus;
    }

    @Column(name = "`available_budget`", nullable = true, scale = 3, precision = 30)
    public BigDecimal getAvailableBudget() {
        return this.availableBudget;
    }

    public void setAvailableBudget(BigDecimal availableBudget) {
        this.availableBudget = availableBudget;
    }

    @Column(name = "`bd_id`", nullable = true, scale = 0, precision = 10)
    public Integer getBdId() {
        return this.bdId;
    }

    public void setBdId(Integer bdId) {
        this.bdId = bdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VActiveBudget)) return false;
        final VActiveBudget vactiveBudget = (VActiveBudget) o;
        return Objects.equals(getBhId(), vactiveBudget.getBhId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBhId());
    }
}