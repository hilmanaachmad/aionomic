/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class QvendorListOrderResponse implements Serializable {


    @ColumnAlias("id")
    private Integer id;

    @ColumnAlias("company_id")
    private String companyId;

    @ColumnAlias("status_rfq")
    private String statusRfq;

    @ColumnAlias("doc_type")
    private String docType;

    @ColumnAlias("purch_org")
    private String purchOrg;

    @ColumnAlias("purch_group")
    private String purchGroup;

    @ColumnAlias("vendor_code")
    private String vendorCode;

    @ColumnAlias("vendor_name")
    private String vendorName;

    @ColumnAlias("currency")
    private String currency;

    @ColumnAlias("payment_method")
    private String paymentMethod;

    @ColumnAlias("top")
    private String top;

    @ColumnAlias("reference")
    private String reference;

    @ColumnAlias("ppn")
    private String ppn;

    @ColumnAlias("shipment_to")
    private String shipmentTo;

    @ColumnAlias("sub_total")
    private BigDecimal subTotal;

    @ColumnAlias("total_vat")
    private BigDecimal totalVat;

    @ColumnAlias("total_amount")
    private BigDecimal totalAmount;

    @ColumnAlias("additional_notes_po")
    private String additionalNotesPo;

    @ColumnAlias("additional_notes_legal")
    private String additionalNotesLegal;

    @ColumnAlias("status")
    private String status;

    @ColumnAlias("created_date")
    private LocalDateTime createdDate;

    @ColumnAlias("last_approved_date")
    private LocalDateTime lastApprovedDate;

    @ColumnAlias("vendor_notes")
    private String vendorNotes;

    @ColumnAlias("vat")
    private Double vat;

    @ColumnAlias("packing_cost")
    private BigDecimal packingCost;

    @ColumnAlias("shipping_handling")
    private BigDecimal shippingHandling;

    @ColumnAlias("other_cost")
    private BigDecimal otherCost;

    @ColumnAlias("pr_creator")
    private String prCreator;

    @ColumnAlias("status_representative")
    private String statusRepresentative;

    @ColumnAlias("department_code")
    private String departmentCode;

    @ColumnAlias("c_code")
    private String ccode;

    @ColumnAlias("c_title")
    private String ctitle;

    @ColumnAlias("pr_created_name")
    private String prCreatedName;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getStatusRfq() {
        return this.statusRfq;
    }

    public void setStatusRfq(String statusRfq) {
        this.statusRfq = statusRfq;
    }

    public String getDocType() {
        return this.docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getPurchOrg() {
        return this.purchOrg;
    }

    public void setPurchOrg(String purchOrg) {
        this.purchOrg = purchOrg;
    }

    public String getPurchGroup() {
        return this.purchGroup;
    }

    public void setPurchGroup(String purchGroup) {
        this.purchGroup = purchGroup;
    }

    public String getVendorCode() {
        return this.vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTop() {
        return this.top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPpn() {
        return this.ppn;
    }

    public void setPpn(String ppn) {
        this.ppn = ppn;
    }

    public String getShipmentTo() {
        return this.shipmentTo;
    }

    public void setShipmentTo(String shipmentTo) {
        this.shipmentTo = shipmentTo;
    }

    public BigDecimal getSubTotal() {
        return this.subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTotalVat() {
        return this.totalVat;
    }

    public void setTotalVat(BigDecimal totalVat) {
        this.totalVat = totalVat;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAdditionalNotesPo() {
        return this.additionalNotesPo;
    }

    public void setAdditionalNotesPo(String additionalNotesPo) {
        this.additionalNotesPo = additionalNotesPo;
    }

    public String getAdditionalNotesLegal() {
        return this.additionalNotesLegal;
    }

    public void setAdditionalNotesLegal(String additionalNotesLegal) {
        this.additionalNotesLegal = additionalNotesLegal;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastApprovedDate() {
        return this.lastApprovedDate;
    }

    public void setLastApprovedDate(LocalDateTime lastApprovedDate) {
        this.lastApprovedDate = lastApprovedDate;
    }

    public String getVendorNotes() {
        return this.vendorNotes;
    }

    public void setVendorNotes(String vendorNotes) {
        this.vendorNotes = vendorNotes;
    }

    public Double getVat() {
        return this.vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public BigDecimal getPackingCost() {
        return this.packingCost;
    }

    public void setPackingCost(BigDecimal packingCost) {
        this.packingCost = packingCost;
    }

    public BigDecimal getShippingHandling() {
        return this.shippingHandling;
    }

    public void setShippingHandling(BigDecimal shippingHandling) {
        this.shippingHandling = shippingHandling;
    }

    public BigDecimal getOtherCost() {
        return this.otherCost;
    }

    public void setOtherCost(BigDecimal otherCost) {
        this.otherCost = otherCost;
    }

    public String getPrCreator() {
        return this.prCreator;
    }

    public void setPrCreator(String prCreator) {
        this.prCreator = prCreator;
    }

    public String getStatusRepresentative() {
        return this.statusRepresentative;
    }

    public void setStatusRepresentative(String statusRepresentative) {
        this.statusRepresentative = statusRepresentative;
    }

    public String getDepartmentCode() {
        return this.departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getCcode() {
        return this.ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public String getCtitle() {
        return this.ctitle;
    }

    public void setCtitle(String ctitle) {
        this.ctitle = ctitle;
    }

    public String getPrCreatedName() {
        return this.prCreatedName;
    }

    public void setPrCreatedName(String prCreatedName) {
        this.prCreatedName = prCreatedName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QvendorListOrderResponse)) return false;
        final QvendorListOrderResponse qvendorListOrderResponse = (QvendorListOrderResponse) o;
        return Objects.equals(getId(), qvendorListOrderResponse.getId()) &&
                Objects.equals(getCompanyId(), qvendorListOrderResponse.getCompanyId()) &&
                Objects.equals(getStatusRfq(), qvendorListOrderResponse.getStatusRfq()) &&
                Objects.equals(getDocType(), qvendorListOrderResponse.getDocType()) &&
                Objects.equals(getPurchOrg(), qvendorListOrderResponse.getPurchOrg()) &&
                Objects.equals(getPurchGroup(), qvendorListOrderResponse.getPurchGroup()) &&
                Objects.equals(getVendorCode(), qvendorListOrderResponse.getVendorCode()) &&
                Objects.equals(getVendorName(), qvendorListOrderResponse.getVendorName()) &&
                Objects.equals(getCurrency(), qvendorListOrderResponse.getCurrency()) &&
                Objects.equals(getPaymentMethod(), qvendorListOrderResponse.getPaymentMethod()) &&
                Objects.equals(getTop(), qvendorListOrderResponse.getTop()) &&
                Objects.equals(getReference(), qvendorListOrderResponse.getReference()) &&
                Objects.equals(getPpn(), qvendorListOrderResponse.getPpn()) &&
                Objects.equals(getShipmentTo(), qvendorListOrderResponse.getShipmentTo()) &&
                Objects.equals(getSubTotal(), qvendorListOrderResponse.getSubTotal()) &&
                Objects.equals(getTotalVat(), qvendorListOrderResponse.getTotalVat()) &&
                Objects.equals(getTotalAmount(), qvendorListOrderResponse.getTotalAmount()) &&
                Objects.equals(getAdditionalNotesPo(), qvendorListOrderResponse.getAdditionalNotesPo()) &&
                Objects.equals(getAdditionalNotesLegal(), qvendorListOrderResponse.getAdditionalNotesLegal()) &&
                Objects.equals(getStatus(), qvendorListOrderResponse.getStatus()) &&
                Objects.equals(getCreatedDate(), qvendorListOrderResponse.getCreatedDate()) &&
                Objects.equals(getLastApprovedDate(), qvendorListOrderResponse.getLastApprovedDate()) &&
                Objects.equals(getVendorNotes(), qvendorListOrderResponse.getVendorNotes()) &&
                Objects.equals(getVat(), qvendorListOrderResponse.getVat()) &&
                Objects.equals(getPackingCost(), qvendorListOrderResponse.getPackingCost()) &&
                Objects.equals(getShippingHandling(), qvendorListOrderResponse.getShippingHandling()) &&
                Objects.equals(getOtherCost(), qvendorListOrderResponse.getOtherCost()) &&
                Objects.equals(getPrCreator(), qvendorListOrderResponse.getPrCreator()) &&
                Objects.equals(getStatusRepresentative(), qvendorListOrderResponse.getStatusRepresentative()) &&
                Objects.equals(getDepartmentCode(), qvendorListOrderResponse.getDepartmentCode()) &&
                Objects.equals(getCcode(), qvendorListOrderResponse.getCcode()) &&
                Objects.equals(getCtitle(), qvendorListOrderResponse.getCtitle()) &&
                Objects.equals(getPrCreatedName(), qvendorListOrderResponse.getPrCreatedName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getCompanyId(),
                getStatusRfq(),
                getDocType(),
                getPurchOrg(),
                getPurchGroup(),
                getVendorCode(),
                getVendorName(),
                getCurrency(),
                getPaymentMethod(),
                getTop(),
                getReference(),
                getPpn(),
                getShipmentTo(),
                getSubTotal(),
                getTotalVat(),
                getTotalAmount(),
                getAdditionalNotesPo(),
                getAdditionalNotesLegal(),
                getStatus(),
                getCreatedDate(),
                getLastApprovedDate(),
                getVendorNotes(),
                getVat(),
                getPackingCost(),
                getShippingHandling(),
                getOtherCost(),
                getPrCreator(),
                getStatusRepresentative(),
                getDepartmentCode(),
                getCcode(),
                getCtitle(),
                getPrCreatedName());
    }
}