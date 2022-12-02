/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.sql.Date;
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
 * TblTPrLineItem generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tbl_t_pr_line_item`")
public class TblTPrLineItem implements Serializable {

    private Integer pliId;
    private Integer prId;
    private Integer bhId;
    private String pliCreatedBy = "SYSTEM";
    private LocalDateTime pliCreatedAt;
    private String pliModifiedBy = "SYSTEM";
    private LocalDateTime pliModifiedAt;
    private String pliStatus = "active";
    private String pliDesc;
    private String pliSpec;
    private String pliUom;
    private Double pliQty;
    private String pliCurrency;
    private Date pliEta;
    private String pliCostCenterId;
    private String pliCostCenterTitle;
    private String pliCoa;
    private String pliAssetNumber;
    private String pliMatGroupId;
    private String pliMatGroupDesc;
    private Double pliUnitPrice;
    private String pliReason;
    private String pliAdditionalData;
    private String pliLineItemSap;
    private TblTBudgetHeader tblTbudgetHeader;
    private TblTPr tblTpr;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`pli_id`", nullable = false, scale = 0, precision = 10)
    public Integer getPliId() {
        return this.pliId;
    }

    public void setPliId(Integer pliId) {
        this.pliId = pliId;
    }

    @Column(name = "`pr_id`", nullable = true, scale = 0, precision = 10)
    public Integer getPrId() {
        return this.prId;
    }

    public void setPrId(Integer prId) {
        this.prId = prId;
    }

    @Column(name = "`bh_id`", nullable = true, scale = 0, precision = 10)
    public Integer getBhId() {
        return this.bhId;
    }

    public void setBhId(Integer bhId) {
        this.bhId = bhId;
    }

    @Column(name = "`pli_created_by`", nullable = true, length = 100)
    public String getPliCreatedBy() {
        return this.pliCreatedBy;
    }

    public void setPliCreatedBy(String pliCreatedBy) {
        this.pliCreatedBy = pliCreatedBy;
    }

    @Column(name = "`pli_created_at`", nullable = true)
    public LocalDateTime getPliCreatedAt() {
        return this.pliCreatedAt;
    }

    public void setPliCreatedAt(LocalDateTime pliCreatedAt) {
        this.pliCreatedAt = pliCreatedAt;
    }

    @Column(name = "`pli_modified_by`", nullable = true, length = 100)
    public String getPliModifiedBy() {
        return this.pliModifiedBy;
    }

    public void setPliModifiedBy(String pliModifiedBy) {
        this.pliModifiedBy = pliModifiedBy;
    }

    @Column(name = "`pli_modified_at`", nullable = true)
    public LocalDateTime getPliModifiedAt() {
        return this.pliModifiedAt;
    }

    public void setPliModifiedAt(LocalDateTime pliModifiedAt) {
        this.pliModifiedAt = pliModifiedAt;
    }

    @Column(name = "`pli_status`", nullable = true, length = 20)
    public String getPliStatus() {
        return this.pliStatus;
    }

    public void setPliStatus(String pliStatus) {
        this.pliStatus = pliStatus;
    }

    @Column(name = "`pli_desc`", nullable = true, length = 550)
    public String getPliDesc() {
        return this.pliDesc;
    }

    public void setPliDesc(String pliDesc) {
        this.pliDesc = pliDesc;
    }

    @Column(name = "`pli_spec`", nullable = true, length = 200)
    public String getPliSpec() {
        return this.pliSpec;
    }

    public void setPliSpec(String pliSpec) {
        this.pliSpec = pliSpec;
    }

    @Column(name = "`pli_uom`", nullable = true, length = 20)
    public String getPliUom() {
        return this.pliUom;
    }

    public void setPliUom(String pliUom) {
        this.pliUom = pliUom;
    }

    @Column(name = "`pli_qty`", nullable = true, scale = 9, precision = 11)
    public Double getPliQty() {
        return this.pliQty;
    }

    public void setPliQty(Double pliQty) {
        this.pliQty = pliQty;
    }

    @Column(name = "`pli_currency`", nullable = true, length = 5)
    public String getPliCurrency() {
        return this.pliCurrency;
    }

    public void setPliCurrency(String pliCurrency) {
        this.pliCurrency = pliCurrency;
    }

    @Column(name = "`pli_eta`", nullable = true)
    public Date getPliEta() {
        return this.pliEta;
    }

    public void setPliEta(Date pliEta) {
        this.pliEta = pliEta;
    }

    @Column(name = "`pli_cost_center_id`", nullable = true, length = 20)
    public String getPliCostCenterId() {
        return this.pliCostCenterId;
    }

    public void setPliCostCenterId(String pliCostCenterId) {
        this.pliCostCenterId = pliCostCenterId;
    }

    @Column(name = "`pli_cost_center_title`", nullable = true, length = 255)
    public String getPliCostCenterTitle() {
        return this.pliCostCenterTitle;
    }

    public void setPliCostCenterTitle(String pliCostCenterTitle) {
        this.pliCostCenterTitle = pliCostCenterTitle;
    }

    @Column(name = "`pli_coa`", nullable = true, length = 20)
    public String getPliCoa() {
        return this.pliCoa;
    }

    public void setPliCoa(String pliCoa) {
        this.pliCoa = pliCoa;
    }

    @Column(name = "`pli_asset_number`", nullable = true, length = 100)
    public String getPliAssetNumber() {
        return this.pliAssetNumber;
    }

    public void setPliAssetNumber(String pliAssetNumber) {
        this.pliAssetNumber = pliAssetNumber;
    }

    @Column(name = "`pli_mat_group_id`", nullable = true, length = 20)
    public String getPliMatGroupId() {
        return this.pliMatGroupId;
    }

    public void setPliMatGroupId(String pliMatGroupId) {
        this.pliMatGroupId = pliMatGroupId;
    }

    @Column(name = "`pli_mat_group_desc`", nullable = true, length = 255)
    public String getPliMatGroupDesc() {
        return this.pliMatGroupDesc;
    }

    public void setPliMatGroupDesc(String pliMatGroupDesc) {
        this.pliMatGroupDesc = pliMatGroupDesc;
    }

    @Column(name = "`pli_unit_price`", nullable = true, scale = 2, precision = 20)
    public Double getPliUnitPrice() {
        return this.pliUnitPrice;
    }

    public void setPliUnitPrice(Double pliUnitPrice) {
        this.pliUnitPrice = pliUnitPrice;
    }

    @Column(name = "`pli_reason`", nullable = true, length = 255)
    public String getPliReason() {
        return this.pliReason;
    }

    public void setPliReason(String pliReason) {
        this.pliReason = pliReason;
    }

    @Column(name = "`pli_additional_data`", nullable = true, length = 2147483647)
    public String getPliAdditionalData() {
        return this.pliAdditionalData;
    }

    public void setPliAdditionalData(String pliAdditionalData) {
        this.pliAdditionalData = pliAdditionalData;
    }

    @Column(name = "`pli_line_item_sap`", nullable = true, length = 255)
    public String getPliLineItemSap() {
        return this.pliLineItemSap;
    }

    public void setPliLineItemSap(String pliLineItemSap) {
        this.pliLineItemSap = pliLineItemSap;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`bh_id`", referencedColumnName = "`bh_id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`tbl_t_pr_line_item_ibfk_1`"))
    @Fetch(FetchMode.JOIN)
    public TblTBudgetHeader getTblTbudgetHeader() {
        return this.tblTbudgetHeader;
    }

    public void setTblTbudgetHeader(TblTBudgetHeader tblTbudgetHeader) {
        if(tblTbudgetHeader != null) {
            this.bhId = tblTbudgetHeader.getBhId();
        }

        this.tblTbudgetHeader = tblTbudgetHeader;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`pr_id`", referencedColumnName = "`pr_id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`tbl_t_pr_line_item_ibfk_2`"))
    @Fetch(FetchMode.JOIN)
    public TblTPr getTblTpr() {
        return this.tblTpr;
    }

    public void setTblTpr(TblTPr tblTpr) {
        if(tblTpr != null) {
            this.prId = tblTpr.getPrId();
        }

        this.tblTpr = tblTpr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TblTPrLineItem)) return false;
        final TblTPrLineItem tblTprLineItem = (TblTPrLineItem) o;
        return Objects.equals(getPliId(), tblTprLineItem.getPliId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPliId());
    }
}