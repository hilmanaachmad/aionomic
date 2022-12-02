/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VPurchaseRequest generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`v_purchase_request`")
public class VPurchaseRequest implements Serializable {

    private Integer id = 0;
    private Integer prId = 0;
    private Integer cid;
    private Integer departmentId;
    private String prNumber;
    private long item = 0L;
    private String prDesc;
    private Date requestDate;
    private LocalDateTime releaseDate;
    private Date eta;
    private Double qty;
    private String uom;
    private BigDecimal unitPrice;
    private String currency;
    private BigDecimal prAmount;
    private String prStatus;
    private String pliStatus;
    private String rfqNumber;
    private String rfqStatus;
    private String poNumber;
    private LocalDateTime poRequestDate;
    private String poStatus;
    private BigDecimal poQty;
    private String poCreatedBy;
    private String poCreatedByName;
    private String matGroupId;
    private String matGroupDesc;
    private String pliCreatedBy;
    private String closedBy;
    private String closedReason;
    private Integer accId;
    private String pliAssetNumber;
    private String pliCostCenterId;
    private Integer bhId;
    private String prPurchaseBy;
    private String ioNumber;
    private String sapPrNumber;

    @Id
    @Column(name = "`id`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`pr_id`", nullable = true, scale = 0, precision = 10)
    public Integer getPrId() {
        return this.prId;
    }

    public void setPrId(Integer prId) {
        this.prId = prId;
    }

    @Column(name = "`c_id`", nullable = true, scale = 0, precision = 10)
    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    @Column(name = "`department_id`", nullable = true, scale = 0, precision = 10)
    public Integer getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "`pr_number`", nullable = true, length = 100)
    public String getPrNumber() {
        return this.prNumber;
    }

    public void setPrNumber(String prNumber) {
        this.prNumber = prNumber;
    }

    @Column(name = "`item`", nullable = false, scale = 0, precision = 19)
    public long getItem() {
        return this.item;
    }

    public void setItem(long item) {
        this.item = item;
    }

    @Column(name = "`pr_desc`", nullable = true, length = 550)
    public String getPrDesc() {
        return this.prDesc;
    }

    public void setPrDesc(String prDesc) {
        this.prDesc = prDesc;
    }

    @Column(name = "`request_date`", nullable = true)
    public Date getRequestDate() {
        return this.requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    @Column(name = "`release_date`", nullable = true)
    public LocalDateTime getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(name = "`eta`", nullable = true)
    public Date getEta() {
        return this.eta;
    }

    public void setEta(Date eta) {
        this.eta = eta;
    }

    @Column(name = "`qty`", nullable = true, scale = 9, precision = 11)
    public Double getQty() {
        return this.qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    @Column(name = "`uom`", nullable = true, length = 20)
    public String getUom() {
        return this.uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    @Column(name = "`unit_price`", nullable = true, scale = 2, precision = 20)
    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "`currency`", nullable = true, length = 5)
    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "`pr_amount`", nullable = true, scale = 2, precision = 19)
    public BigDecimal getPrAmount() {
        return this.prAmount;
    }

    public void setPrAmount(BigDecimal prAmount) {
        this.prAmount = prAmount;
    }

    @Column(name = "`pr_status`", nullable = true, length = 20)
    public String getPrStatus() {
        return this.prStatus;
    }

    public void setPrStatus(String prStatus) {
        this.prStatus = prStatus;
    }

    @Column(name = "`pli_status`", nullable = true, length = 20)
    public String getPliStatus() {
        return this.pliStatus;
    }

    public void setPliStatus(String pliStatus) {
        this.pliStatus = pliStatus;
    }

    @Column(name = "`rfq_number`", nullable = true, length = 100)
    public String getRfqNumber() {
        return this.rfqNumber;
    }

    public void setRfqNumber(String rfqNumber) {
        this.rfqNumber = rfqNumber;
    }

    @Column(name = "`rfq_status`", nullable = true, length = 100)
    public String getRfqStatus() {
        return this.rfqStatus;
    }

    public void setRfqStatus(String rfqStatus) {
        this.rfqStatus = rfqStatus;
    }

    @Column(name = "`po_number`", nullable = true, length = 20)
    public String getPoNumber() {
        return this.poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    @Column(name = "`po_request_date`", nullable = true)
    public LocalDateTime getPoRequestDate() {
        return this.poRequestDate;
    }

    public void setPoRequestDate(LocalDateTime poRequestDate) {
        this.poRequestDate = poRequestDate;
    }

    @Column(name = "`po_status`", nullable = true, length = 39)
    public String getPoStatus() {
        return this.poStatus;
    }

    public void setPoStatus(String poStatus) {
        this.poStatus = poStatus;
    }

    @Column(name = "`po_qty`", nullable = true, scale = 9, precision = 20)
    public BigDecimal getPoQty() {
        return this.poQty;
    }

    public void setPoQty(BigDecimal poQty) {
        this.poQty = poQty;
    }

    @Column(name = "`po_created_by`", nullable = true, length = 255)
    public String getPoCreatedBy() {
        return this.poCreatedBy;
    }

    public void setPoCreatedBy(String poCreatedBy) {
        this.poCreatedBy = poCreatedBy;
    }

    @Column(name = "`po_created_by_name`", nullable = true, length = 100)
    public String getPoCreatedByName() {
        return this.poCreatedByName;
    }

    public void setPoCreatedByName(String poCreatedByName) {
        this.poCreatedByName = poCreatedByName;
    }

    @Column(name = "`mat_group_id`", nullable = true, length = 20)
    public String getMatGroupId() {
        return this.matGroupId;
    }

    public void setMatGroupId(String matGroupId) {
        this.matGroupId = matGroupId;
    }

    @Column(name = "`mat_group_desc`", nullable = true, length = 255)
    public String getMatGroupDesc() {
        return this.matGroupDesc;
    }

    public void setMatGroupDesc(String matGroupDesc) {
        this.matGroupDesc = matGroupDesc;
    }

    @Column(name = "`pli_created_by`", nullable = true, length = 100)
    public String getPliCreatedBy() {
        return this.pliCreatedBy;
    }

    public void setPliCreatedBy(String pliCreatedBy) {
        this.pliCreatedBy = pliCreatedBy;
    }

    @Column(name = "`closed_by`", nullable = true, length = 100)
    public String getClosedBy() {
        return this.closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    @Column(name = "`closed_reason`", nullable = true, length = 2147483647)
    public String getClosedReason() {
        return this.closedReason;
    }

    public void setClosedReason(String closedReason) {
        this.closedReason = closedReason;
    }

    @Column(name = "`acc_id`", nullable = true, scale = 0, precision = 10)
    public Integer getAccId() {
        return this.accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    @Column(name = "`pli_asset_number`", nullable = true, length = 100)
    public String getPliAssetNumber() {
        return this.pliAssetNumber;
    }

    public void setPliAssetNumber(String pliAssetNumber) {
        this.pliAssetNumber = pliAssetNumber;
    }

    @Column(name = "`pli_cost_center_id`", nullable = true, length = 20)
    public String getPliCostCenterId() {
        return this.pliCostCenterId;
    }

    public void setPliCostCenterId(String pliCostCenterId) {
        this.pliCostCenterId = pliCostCenterId;
    }

    @Column(name = "`bh_id`", nullable = true, scale = 0, precision = 10)
    public Integer getBhId() {
        return this.bhId;
    }

    public void setBhId(Integer bhId) {
        this.bhId = bhId;
    }

    @Column(name = "`pr_purchase_by`", nullable = true, length = 100)
    public String getPrPurchaseBy() {
        return this.prPurchaseBy;
    }

    public void setPrPurchaseBy(String prPurchaseBy) {
        this.prPurchaseBy = prPurchaseBy;
    }

    @Column(name = "`io_number`", nullable = true, length = 100)
    public String getIoNumber() {
        return this.ioNumber;
    }

    public void setIoNumber(String ioNumber) {
        this.ioNumber = ioNumber;
    }

    @Column(name = "`sap_pr_number`", nullable = true, length = 50)
    public String getSapPrNumber() {
        return this.sapPrNumber;
    }

    public void setSapPrNumber(String sapPrNumber) {
        this.sapPrNumber = sapPrNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VPurchaseRequest)) return false;
        final VPurchaseRequest vpurchaseRequest = (VPurchaseRequest) o;
        return Objects.equals(getId(), vpurchaseRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}