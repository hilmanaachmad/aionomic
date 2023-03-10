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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TblMMatGroupCoa generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tbl_m_mat_group_coa`")
public class TblMMatGroupCoa implements Serializable {

    private Integer mgcId;
    private String materialId;
    private String material;
    private String coaId;
    private String coaTitle;
    private String mgcCreatedBy = "SYSTEM";
    private LocalDateTime mgcCreatedAt;
    private String mgcStatus = "active";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`mgc_id`", nullable = false, scale = 0, precision = 10)
    public Integer getMgcId() {
        return this.mgcId;
    }

    public void setMgcId(Integer mgcId) {
        this.mgcId = mgcId;
    }

    @Column(name = "`material_id`", nullable = true, length = 100)
    public String getMaterialId() {
        return this.materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    @Column(name = "`material`", nullable = true, length = 100)
    public String getMaterial() {
        return this.material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Column(name = "`coa_id`", nullable = true, length = 100)
    public String getCoaId() {
        return this.coaId;
    }

    public void setCoaId(String coaId) {
        this.coaId = coaId;
    }

    @Column(name = "`coa_title`", nullable = true, length = 100)
    public String getCoaTitle() {
        return this.coaTitle;
    }

    public void setCoaTitle(String coaTitle) {
        this.coaTitle = coaTitle;
    }

    @Column(name = "`mgc_created_by`", nullable = true, length = 100)
    public String getMgcCreatedBy() {
        return this.mgcCreatedBy;
    }

    public void setMgcCreatedBy(String mgcCreatedBy) {
        this.mgcCreatedBy = mgcCreatedBy;
    }

    @Column(name = "`mgc_created_at`", nullable = true)
    public LocalDateTime getMgcCreatedAt() {
        return this.mgcCreatedAt;
    }

    public void setMgcCreatedAt(LocalDateTime mgcCreatedAt) {
        this.mgcCreatedAt = mgcCreatedAt;
    }

    @Column(name = "`mgc_status`", nullable = true, length = 20)
    public String getMgcStatus() {
        return this.mgcStatus;
    }

    public void setMgcStatus(String mgcStatus) {
        this.mgcStatus = mgcStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TblMMatGroupCoa)) return false;
        final TblMMatGroupCoa tblMmatGroupCoa = (TblMMatGroupCoa) o;
        return Objects.equals(getMgcId(), tblMmatGroupCoa.getMgcId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMgcId());
    }
}