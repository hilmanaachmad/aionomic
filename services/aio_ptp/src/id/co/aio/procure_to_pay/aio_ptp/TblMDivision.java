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
 * TblMDivision generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tbl_m_division`")
public class TblMDivision implements Serializable {

    private Integer divisionId;
    private String divisionTitle;
    private String divisionCode;
    private String divCreatedBy;
    private LocalDateTime divCreatedAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`division_id`", nullable = false, scale = 0, precision = 10)
    public Integer getDivisionId() {
        return this.divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    @Column(name = "`division_title`", nullable = true, length = 100)
    public String getDivisionTitle() {
        return this.divisionTitle;
    }

    public void setDivisionTitle(String divisionTitle) {
        this.divisionTitle = divisionTitle;
    }

    @Column(name = "`division_code`", nullable = true, length = 255)
    public String getDivisionCode() {
        return this.divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    @Column(name = "`div_created_by`", nullable = true, length = 100)
    public String getDivCreatedBy() {
        return this.divCreatedBy;
    }

    public void setDivCreatedBy(String divCreatedBy) {
        this.divCreatedBy = divCreatedBy;
    }

    @Column(name = "`div_created_at`", nullable = false)
    public LocalDateTime getDivCreatedAt() {
        return this.divCreatedAt;
    }

    public void setDivCreatedAt(LocalDateTime divCreatedAt) {
        this.divCreatedAt = divCreatedAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TblMDivision)) return false;
        final TblMDivision tblMdivision = (TblMDivision) o;
        return Objects.equals(getDivisionId(), tblMdivision.getDivisionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDivisionId());
    }
}