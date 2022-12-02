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

public class GetDataPodetailResponse implements Serializable {


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

    @ColumnAlias("vat")
    private Double vat;

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

    @ColumnAlias("sap_po_number")
    private String sapPoNumber;

    @ColumnAlias("c_address")
    private String caddress;

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

    public Double getVat() {
        return this.vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
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

    public String getSapPoNumber() {
        return this.sapPoNumber;
    }

    public void setSapPoNumber(String sapPoNumber) {
        this.sapPoNumber = sapPoNumber;
    }

    public String getCaddress() {
        return this.caddress;
    }

    public void setCaddress(String caddress) {
        this.caddress = caddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetDataPodetailResponse)) return false;
        final GetDataPodetailResponse getDataPodetailResponse = (GetDataPodetailResponse) o;
        return Objects.equals(getId(), getDataPodetailResponse.getId()) &&
                Objects.equals(getCompanyId(), getDataPodetailResponse.getCompanyId()) &&
                Objects.equals(getStatusRfq(), getDataPodetailResponse.getStatusRfq()) &&
                Objects.equals(getDocType(), getDataPodetailResponse.getDocType()) &&
                Objects.equals(getPurchOrg(), getDataPodetailResponse.getPurchOrg()) &&
                Objects.equals(getPurchGroup(), getDataPodetailResponse.getPurchGroup()) &&
                Objects.equals(getVendorCode(), getDataPodetailResponse.getVendorCode()) &&
                Objects.equals(getVendorName(), getDataPodetailResponse.getVendorName()) &&
                Objects.equals(getCurrency(), getDataPodetailResponse.getCurrency()) &&
                Objects.equals(getPaymentMethod(), getDataPodetailResponse.getPaymentMethod()) &&
                Objects.equals(getTop(), getDataPodetailResponse.getTop()) &&
                Objects.equals(getReference(), getDataPodetailResponse.getReference()) &&
                Objects.equals(getPpn(), getDataPodetailResponse.getPpn()) &&
                Objects.equals(getShipmentTo(), getDataPodetailResponse.getShipmentTo()) &&
                Objects.equals(getSubTotal(), getDataPodetailResponse.getSubTotal()) &&
                Objects.equals(getVat(), getDataPodetailResponse.getVat()) &&
                Objects.equals(getTotalVat(), getDataPodetailResponse.getTotalVat()) &&
                Objects.equals(getTotalAmount(), getDataPodetailResponse.getTotalAmount()) &&
                Objects.equals(getAdditionalNotesPo(), getDataPodetailResponse.getAdditionalNotesPo()) &&
                Objects.equals(getAdditionalNotesLegal(), getDataPodetailResponse.getAdditionalNotesLegal()) &&
                Objects.equals(getStatus(), getDataPodetailResponse.getStatus()) &&
                Objects.equals(getCreatedDate(), getDataPodetailResponse.getCreatedDate()) &&
                Objects.equals(getLastApprovedDate(), getDataPodetailResponse.getLastApprovedDate()) &&
                Objects.equals(getVendorNotes(), getDataPodetailResponse.getVendorNotes()) &&
                Objects.equals(getSapPoNumber(), getDataPodetailResponse.getSapPoNumber()) &&
                Objects.equals(getCaddress(), getDataPodetailResponse.getCaddress());
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
                getVat(),
                getTotalVat(),
                getTotalAmount(),
                getAdditionalNotesPo(),
                getAdditionalNotesLegal(),
                getStatus(),
                getCreatedDate(),
                getLastApprovedDate(),
                getVendorNotes(),
                getSapPoNumber(),
                getCaddress());
    }
}