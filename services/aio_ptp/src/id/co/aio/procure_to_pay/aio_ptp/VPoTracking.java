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
 * VPoTracking generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`v_po_tracking`")
public class VPoTracking implements Serializable {

    private Integer id = 0;
    private String sapPoNumber;
    private String vendorName;
    private String prNo;
    private String department;
    private String prNoItem;
    private String item;
    private LocalDateTime createdDate;
    private Date lastApprovedDate;
    private BigDecimal poQty;
    private String uom;
    private String currency;
    private BigDecimal unitPrice;
    private int idPo = 0;
    private BigDecimal itemTotalAmount;
    private LocalDateTime eta;
    private String status;
    private String statusPo;
    private String poCreator;
    private String prCreator;
    private String prRepName;
    private String requestByName;
    private BigDecimal deliveryItemQty;
    private BigDecimal deliveryItemTotalAmount;
    private String companyId;
    private String departmentCode;
    private Integer year;
    private BigDecimal qtyNeedsDelivered;
    private BigDecimal amountNeedsDelivered;
    private BigDecimal qtyNeedsInvoiced;
    private BigDecimal amountNeedsInvoiced;
    private String receivedBy;

    @Id
    @Column(name = "`id`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`sap_po_number`", nullable = true, length = 20)
    public String getSapPoNumber() {
        return this.sapPoNumber;
    }

    public void setSapPoNumber(String sapPoNumber) {
        this.sapPoNumber = sapPoNumber;
    }

    @Column(name = "`vendor_name`", nullable = true, length = 255)
    public String getVendorName() {
        return this.vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Column(name = "`pr_no`", nullable = true, length = 30)
    public String getPrNo() {
        return this.prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo;
    }

    @Column(name = "`department`", nullable = true, length = 100)
    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Column(name = "`pr_no_item`", nullable = true, length = 255)
    public String getPrNoItem() {
        return this.prNoItem;
    }

    public void setPrNoItem(String prNoItem) {
        this.prNoItem = prNoItem;
    }

    @Column(name = "`item`", nullable = true, length = 255)
    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "`created_date`", nullable = true)
    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "`last_approved_date`", nullable = true)
    public Date getLastApprovedDate() {
        return this.lastApprovedDate;
    }

    public void setLastApprovedDate(Date lastApprovedDate) {
        this.lastApprovedDate = lastApprovedDate;
    }

    @Column(name = "`po_qty`", nullable = true, scale = 9, precision = 20)
    public BigDecimal getPoQty() {
        return this.poQty;
    }

    public void setPoQty(BigDecimal poQty) {
        this.poQty = poQty;
    }

    @Column(name = "`uom`", nullable = true, length = 255)
    public String getUom() {
        return this.uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    @Column(name = "`currency`", nullable = true, length = 50)
    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "`unit_price`", nullable = true, scale = 2, precision = 30)
    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "`id_po`", nullable = false, scale = 0, precision = 10)
    public int getIdPo() {
        return this.idPo;
    }

    public void setIdPo(int idPo) {
        this.idPo = idPo;
    }

    @Column(name = "`item_total_amount`", nullable = true, scale = 2, precision = 30)
    public BigDecimal getItemTotalAmount() {
        return this.itemTotalAmount;
    }

    public void setItemTotalAmount(BigDecimal itemTotalAmount) {
        this.itemTotalAmount = itemTotalAmount;
    }

    @Column(name = "`eta`", nullable = true)
    public LocalDateTime getEta() {
        return this.eta;
    }

    public void setEta(LocalDateTime eta) {
        this.eta = eta;
    }

    @Column(name = "`status`", nullable = true, length = 39)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "`status_po`", nullable = true, length = 24)
    public String getStatusPo() {
        return this.statusPo;
    }

    public void setStatusPo(String statusPo) {
        this.statusPo = statusPo;
    }

    @Column(name = "`po_creator`", nullable = true, length = 100)
    public String getPoCreator() {
        return this.poCreator;
    }

    public void setPoCreator(String poCreator) {
        this.poCreator = poCreator;
    }

    @Column(name = "`pr_creator`", nullable = true, length = 100)
    public String getPrCreator() {
        return this.prCreator;
    }

    public void setPrCreator(String prCreator) {
        this.prCreator = prCreator;
    }

    @Column(name = "`pr_rep_name`", nullable = true, length = 100)
    public String getPrRepName() {
        return this.prRepName;
    }

    public void setPrRepName(String prRepName) {
        this.prRepName = prRepName;
    }

    @Column(name = "`request_by_name`", nullable = true, length = 100)
    public String getRequestByName() {
        return this.requestByName;
    }

    public void setRequestByName(String requestByName) {
        this.requestByName = requestByName;
    }

    @Column(name = "`delivery_item_qty`", nullable = true, scale = 4, precision = 21)
    public BigDecimal getDeliveryItemQty() {
        return this.deliveryItemQty;
    }

    public void setDeliveryItemQty(BigDecimal deliveryItemQty) {
        this.deliveryItemQty = deliveryItemQty;
    }

    @Column(name = "`delivery_item_total_amount`", nullable = true, scale = 9, precision = 23)
    public BigDecimal getDeliveryItemTotalAmount() {
        return this.deliveryItemTotalAmount;
    }

    public void setDeliveryItemTotalAmount(BigDecimal deliveryItemTotalAmount) {
        this.deliveryItemTotalAmount = deliveryItemTotalAmount;
    }

    @Column(name = "`company_id`", nullable = true, length = 50)
    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Column(name = "`department_code`", nullable = true, length = 255)
    public String getDepartmentCode() {
        return this.departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    @Column(name = "`year`", nullable = true, scale = 0, precision = 10)
    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Column(name = "`qty_needs_delivered`", nullable = true, scale = 4, precision = 21)
    public BigDecimal getQtyNeedsDelivered() {
        return this.qtyNeedsDelivered;
    }

    public void setQtyNeedsDelivered(BigDecimal qtyNeedsDelivered) {
        this.qtyNeedsDelivered = qtyNeedsDelivered;
    }

    @Column(name = "`amount_needs_delivered`", nullable = true, scale = 2, precision = 65)
    public BigDecimal getAmountNeedsDelivered() {
        return this.amountNeedsDelivered;
    }

    public void setAmountNeedsDelivered(BigDecimal amountNeedsDelivered) {
        this.amountNeedsDelivered = amountNeedsDelivered;
    }

    @Column(name = "`qty_needs_invoiced`", nullable = true, scale = 4, precision = 21)
    public BigDecimal getQtyNeedsInvoiced() {
        return this.qtyNeedsInvoiced;
    }

    public void setQtyNeedsInvoiced(BigDecimal qtyNeedsInvoiced) {
        this.qtyNeedsInvoiced = qtyNeedsInvoiced;
    }

    @Column(name = "`amount_needs_invoiced`", nullable = true, scale = 2, precision = 65)
    public BigDecimal getAmountNeedsInvoiced() {
        return this.amountNeedsInvoiced;
    }

    public void setAmountNeedsInvoiced(BigDecimal amountNeedsInvoiced) {
        this.amountNeedsInvoiced = amountNeedsInvoiced;
    }

    @Column(name = "`received_by`", nullable = true, length = 16777215)
    public String getReceivedBy() {
        return this.receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VPoTracking)) return false;
        final VPoTracking vpoTracking = (VPoTracking) o;
        return Objects.equals(getId(), vpoTracking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}