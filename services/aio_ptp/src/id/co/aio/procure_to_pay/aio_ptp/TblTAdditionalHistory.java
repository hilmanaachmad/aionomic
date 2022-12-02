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
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * TblTAdditionalHistory generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tbl_t_additional_history`")
public class TblTAdditionalHistory implements Serializable {

    private Integer adId;
    private Integer baId;
    private String adEmployeeName;
    private String adAction;
    private LocalDateTime adTimestamp;
    private String adComment;
    private TblTBudgetAdditional tblTbudgetAdditional;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ad_id`", nullable = false, scale = 0, precision = 10)
    public Integer getAdId() {
        return this.adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    @Column(name = "`ba_id`", nullable = true, scale = 0, precision = 10)
    public Integer getBaId() {
        return this.baId;
    }

    public void setBaId(Integer baId) {
        this.baId = baId;
    }

    @Column(name = "`ad_employee_name`", nullable = true, length = 255)
    public String getAdEmployeeName() {
        return this.adEmployeeName;
    }

    public void setAdEmployeeName(String adEmployeeName) {
        this.adEmployeeName = adEmployeeName;
    }

    @Column(name = "`ad_action`", nullable = true, length = 100)
    public String getAdAction() {
        return this.adAction;
    }

    public void setAdAction(String adAction) {
        this.adAction = adAction;
    }

    @Column(name = "`ad_timestamp`", nullable = true)
    public LocalDateTime getAdTimestamp() {
        return this.adTimestamp;
    }

    public void setAdTimestamp(LocalDateTime adTimestamp) {
        this.adTimestamp = adTimestamp;
    }

    @Column(name = "`ad_comment`", nullable = true, length = 2147483647)
    public String getAdComment() {
        return this.adComment;
    }

    public void setAdComment(String adComment) {
        this.adComment = adComment;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`ba_id`", referencedColumnName = "`ba_id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`tbl_t_additional_history_ibfk_1`"))
    @Fetch(FetchMode.JOIN)
    public TblTBudgetAdditional getTblTbudgetAdditional() {
        return this.tblTbudgetAdditional;
    }

    public void setTblTbudgetAdditional(TblTBudgetAdditional tblTbudgetAdditional) {
        if(tblTbudgetAdditional != null) {
            this.baId = tblTbudgetAdditional.getBaId();
        }

        this.tblTbudgetAdditional = tblTbudgetAdditional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TblTAdditionalHistory)) return false;
        final TblTAdditionalHistory tblTadditionalHistory = (TblTAdditionalHistory) o;
        return Objects.equals(getAdId(), tblTadditionalHistory.getAdId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdId());
    }
}