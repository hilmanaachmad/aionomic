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
 * VGrTracking generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`v_gr_tracking`")
public class VGrTracking implements Serializable {

    private Integer id = 0;
    private int idPoLineItem = 0;
    private String sapGrNumber;
    private String assignName;
    private String sapPoNumber;
    private Integer idGr;
    private Integer idPoHeader;
    private Integer idPrHeader;
    private Integer idPrLineItemHeader;
    private String item;
    private String uom;
    private Double unitPrice;
    private String itemTotalAmount;
    private String matlGroup;
    private String matlGroupDesc;
    private String plant;
    private LocalDateTime eta;
    private String ioNumber;
    private String costCenter;
    private String coa;
    private String assetsNo;
    private String prNo;
    private String prNoItem;
    private String sapPrNo;
    private BigDecimal discount;
    private BigDecimal deliveryQty;
    private LocalDateTime deliveryDate;
    private String status;
    private String comment;
    private BigDecimal confirmQty;
    private String confirmUnit;
    private LocalDateTime receivedDate;
    private String condition;
    private String remarks;
    private String files;
    private String assign;
    private String deliveryNumber;
    private String companyId;
    private Integer bhId;
    private String pliCoa;
    private String sapPoLine;

    @Id
    @Column(name = "`id`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`id_po_line_item`", nullable = false, scale = 0, precision = 10)
    public int getIdPoLineItem() {
        return this.idPoLineItem;
    }

    public void setIdPoLineItem(int idPoLineItem) {
        this.idPoLineItem = idPoLineItem;
    }

    @Column(name = "`sap_gr_number`", nullable = true, length = 50)
    public String getSapGrNumber() {
        return this.sapGrNumber;
    }

    public void setSapGrNumber(String sapGrNumber) {
        this.sapGrNumber = sapGrNumber;
    }

    @Column(name = "`assign_name`", nullable = true, length = 100)
    public String getAssignName() {
        return this.assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }

    @Column(name = "`sap_po_number`", nullable = true, length = 20)
    public String getSapPoNumber() {
        return this.sapPoNumber;
    }

    public void setSapPoNumber(String sapPoNumber) {
        this.sapPoNumber = sapPoNumber;
    }

    @Column(name = "`id_gr`", nullable = true, scale = 0, precision = 10)
    public Integer getIdGr() {
        return this.idGr;
    }

    public void setIdGr(Integer idGr) {
        this.idGr = idGr;
    }

    @Column(name = "`id_po_header`", nullable = true, scale = 0, precision = 10)
    public Integer getIdPoHeader() {
        return this.idPoHeader;
    }

    public void setIdPoHeader(Integer idPoHeader) {
        this.idPoHeader = idPoHeader;
    }

    @Column(name = "`id_pr_header`", nullable = true, scale = 0, precision = 10)
    public Integer getIdPrHeader() {
        return this.idPrHeader;
    }

    public void setIdPrHeader(Integer idPrHeader) {
        this.idPrHeader = idPrHeader;
    }

    @Column(name = "`id_pr_line_item_header`", nullable = true, scale = 0, precision = 10)
    public Integer getIdPrLineItemHeader() {
        return this.idPrLineItemHeader;
    }

    public void setIdPrLineItemHeader(Integer idPrLineItemHeader) {
        this.idPrLineItemHeader = idPrLineItemHeader;
    }

    @Column(name = "`item`", nullable = true, length = 255)
    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "`uom`", nullable = true, length = 255)
    public String getUom() {
        return this.uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    @Column(name = "`unit_price`", nullable = true, scale = 2, precision = 50)
    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "`item_total_amount`", nullable = true, length = 255)
    public String getItemTotalAmount() {
        return this.itemTotalAmount;
    }

    public void setItemTotalAmount(String itemTotalAmount) {
        this.itemTotalAmount = itemTotalAmount;
    }

    @Column(name = "`matl_group`", nullable = true, length = 255)
    public String getMatlGroup() {
        return this.matlGroup;
    }

    public void setMatlGroup(String matlGroup) {
        this.matlGroup = matlGroup;
    }

    @Column(name = "`matl_group_desc`", nullable = true, length = 255)
    public String getMatlGroupDesc() {
        return this.matlGroupDesc;
    }

    public void setMatlGroupDesc(String matlGroupDesc) {
        this.matlGroupDesc = matlGroupDesc;
    }

    @Column(name = "`plant`", nullable = true, length = 255)
    public String getPlant() {
        return this.plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    @Column(name = "`eta`", nullable = true)
    public LocalDateTime getEta() {
        return this.eta;
    }

    public void setEta(LocalDateTime eta) {
        this.eta = eta;
    }

    @Column(name = "`io_number`", nullable = true, length = 30)
    public String getIoNumber() {
        return this.ioNumber;
    }

    public void setIoNumber(String ioNumber) {
        this.ioNumber = ioNumber;
    }

    @Column(name = "`cost_center`", nullable = true, length = 255)
    public String getCostCenter() {
        return this.costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    @Column(name = "`coa`", nullable = true, length = 255)
    public String getCoa() {
        return this.coa;
    }

    public void setCoa(String coa) {
        this.coa = coa;
    }

    @Column(name = "`assets_no`", nullable = true, length = 30)
    public String getAssetsNo() {
        return this.assetsNo;
    }

    public void setAssetsNo(String assetsNo) {
        this.assetsNo = assetsNo;
    }

    @Column(name = "`pr_no`", nullable = true, length = 30)
    public String getPrNo() {
        return this.prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo;
    }

    @Column(name = "`pr_no_item`", nullable = true, length = 255)
    public String getPrNoItem() {
        return this.prNoItem;
    }

    public void setPrNoItem(String prNoItem) {
        this.prNoItem = prNoItem;
    }

    @Column(name = "`sap_pr_no`", nullable = true, length = 50)
    public String getSapPrNo() {
        return this.sapPrNo;
    }

    public void setSapPrNo(String sapPrNo) {
        this.sapPrNo = sapPrNo;
    }

    @Column(name = "`discount`", nullable = true, scale = 3, precision = 65)
    public BigDecimal getDiscount() {
        return this.discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Column(name = "`delivery_qty`", nullable = true, scale = 9, precision = 20)
    public BigDecimal getDeliveryQty() {
        return this.deliveryQty;
    }

    public void setDeliveryQty(BigDecimal deliveryQty) {
        this.deliveryQty = deliveryQty;
    }

    @Column(name = "`delivery_date`", nullable = true)
    public LocalDateTime getDeliveryDate() {
        return this.deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Column(name = "`status`", nullable = true, length = 50)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "`comment`", nullable = true, length = 255)
    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "`confirm_qty`", nullable = true, scale = 3, precision = 20)
    public BigDecimal getConfirmQty() {
        return this.confirmQty;
    }

    public void setConfirmQty(BigDecimal confirmQty) {
        this.confirmQty = confirmQty;
    }

    @Column(name = "`confirm_unit`", nullable = true, length = 255)
    public String getConfirmUnit() {
        return this.confirmUnit;
    }

    public void setConfirmUnit(String confirmUnit) {
        this.confirmUnit = confirmUnit;
    }

    @Column(name = "`received_date`", nullable = true)
    public LocalDateTime getReceivedDate() {
        return this.receivedDate;
    }

    public void setReceivedDate(LocalDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    @Column(name = "`condition`", nullable = true, length = 100)
    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Column(name = "`remarks`", nullable = true, length = 255)
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "`files`", nullable = true, length = 65535)
    public String getFiles() {
        return this.files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    @Column(name = "`assign`", nullable = true, length = 255)
    public String getAssign() {
        return this.assign;
    }

    public void setAssign(String assign) {
        this.assign = assign;
    }

    @Column(name = "`delivery_number`", nullable = true, length = 50)
    public String getDeliveryNumber() {
        return this.deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    @Column(name = "`company_id`", nullable = true, length = 50)
    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Column(name = "`bh_id`", nullable = true, scale = 0, precision = 10)
    public Integer getBhId() {
        return this.bhId;
    }

    public void setBhId(Integer bhId) {
        this.bhId = bhId;
    }

    @Column(name = "`pli_coa`", nullable = true, length = 20)
    public String getPliCoa() {
        return this.pliCoa;
    }

    public void setPliCoa(String pliCoa) {
        this.pliCoa = pliCoa;
    }

    @Column(name = "`sap_po_line`", nullable = true, length = 100)
    public String getSapPoLine() {
        return this.sapPoLine;
    }

    public void setSapPoLine(String sapPoLine) {
        this.sapPoLine = sapPoLine;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VGrTracking)) return false;
        final VGrTracking vgrTracking = (VGrTracking) o;
        return Objects.equals(getId(), vgrTracking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}