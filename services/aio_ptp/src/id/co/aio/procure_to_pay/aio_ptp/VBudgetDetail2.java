/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VBudgetDetail2 generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`v_budget_detail_2`")
public class VBudgetDetail2 implements Serializable {

    private Integer bdId = 0;
    private Integer bhId = 0;
    private Double bdOriginal;
    private String coa;
    private Double bdAfterAdjustment;
    private String bdDocumentId;
    private String typeData;
    private LocalDateTime bdCreatedAt;
    private String bdCreatedBy;
    private String bdRemarks;
    private Double valueAdjustment;
    private Double valueReclass;
    private Double valueAdditional;

    @Id
    @Column(name = "`bd_id`", nullable = false, scale = 0, precision = 10)
    public Integer getBdId() {
        return this.bdId;
    }

    public void setBdId(Integer bdId) {
        this.bdId = bdId;
    }

    @Column(name = "`bh_id`", nullable = true, scale = 0, precision = 10)
    public Integer getBhId() {
        return this.bhId;
    }

    public void setBhId(Integer bhId) {
        this.bhId = bhId;
    }

    @Column(name = "`bd_original`", nullable = true, scale = 3, precision = 30)
    public Double getBdOriginal() {
        return this.bdOriginal;
    }

    public void setBdOriginal(Double bdOriginal) {
        this.bdOriginal = bdOriginal;
    }

    @Column(name = "`coa`", nullable = true, length = 100)
    public String getCoa() {
        return this.coa;
    }

    public void setCoa(String coa) {
        this.coa = coa;
    }

    @Column(name = "`bd_after_adjustment`", nullable = true, scale = 3, precision = 30)
    public Double getBdAfterAdjustment() {
        return this.bdAfterAdjustment;
    }

    public void setBdAfterAdjustment(Double bdAfterAdjustment) {
        this.bdAfterAdjustment = bdAfterAdjustment;
    }

    @Column(name = "`bd_document_id`", nullable = true, length = 100)
    public String getBdDocumentId() {
        return this.bdDocumentId;
    }

    public void setBdDocumentId(String bdDocumentId) {
        this.bdDocumentId = bdDocumentId;
    }

    @Column(name = "`type_data`", nullable = true, length = 100)
    public String getTypeData() {
        return this.typeData;
    }

    public void setTypeData(String typeData) {
        this.typeData = typeData;
    }

    @Column(name = "`bd_created_at`", nullable = true)
    public LocalDateTime getBdCreatedAt() {
        return this.bdCreatedAt;
    }

    public void setBdCreatedAt(LocalDateTime bdCreatedAt) {
        this.bdCreatedAt = bdCreatedAt;
    }

    @Column(name = "`bd_created_by`", nullable = true, length = 100)
    public String getBdCreatedBy() {
        return this.bdCreatedBy;
    }

    public void setBdCreatedBy(String bdCreatedBy) {
        this.bdCreatedBy = bdCreatedBy;
    }

    @Column(name = "`bd_remarks`", nullable = true, length = 65535)
    public String getBdRemarks() {
        return this.bdRemarks;
    }

    public void setBdRemarks(String bdRemarks) {
        this.bdRemarks = bdRemarks;
    }

    @Column(name = "`value_adjustment`", nullable = true, scale = 3, precision = 52)
    public Double getValueAdjustment() {
        return this.valueAdjustment;
    }

    public void setValueAdjustment(Double valueAdjustment) {
        this.valueAdjustment = valueAdjustment;
    }

    @Column(name = "`value_reclass`", nullable = true, scale = 3, precision = 52)
    public Double getValueReclass() {
        return this.valueReclass;
    }

    public void setValueReclass(Double valueReclass) {
        this.valueReclass = valueReclass;
    }

    @Column(name = "`value_additional`", nullable = true, scale = 3, precision = 52)
    public Double getValueAdditional() {
        return this.valueAdditional;
    }

    public void setValueAdditional(Double valueAdditional) {
        this.valueAdditional = valueAdditional;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VBudgetDetail2)) return false;
        final VBudgetDetail2 vbudgetDetail2 = (VBudgetDetail2) o;
        return Objects.equals(getBdId(), vbudgetDetail2.getBdId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBdId());
    }
}