/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class GetRfqsummaryResponse implements Serializable {


    @ColumnAlias("rfq_number")
    private String rfqNumber;

    @ColumnAlias("item")
    private Long item;

    @ColumnAlias("description")
    private String description;

    @ColumnAlias("rfqv_id")
    private Integer rfqvId;

    @ColumnAlias("rls_id")
    private Integer rlsId;

    @ColumnAlias("vendor_code")
    private String vendorCode;

    @ColumnAlias("vendor_name")
    private String vendorName;

    @ColumnAlias("quality")
    private String quality;

    @ColumnAlias("summary_overwrite")
    private String summaryOverwrite;

    @ColumnAlias("summary_reason")
    private String summaryReason;

    @ColumnAlias("rfqv_duration")
    private String rfqvDuration;

    @ColumnAlias("unit_price")
    private BigDecimal unitPrice;

    @ColumnAlias("top")
    private String top;

    @ColumnAlias("attachments")
    private String attachments;

    @ColumnAlias("qty")
    private Double qty;

    @ColumnAlias("liq_unit_price")
    private BigDecimal liqUnitPrice;

    @ColumnAlias("liq_amount")
    private BigDecimal liqAmount;

    @ColumnAlias("total_price")
    private Long totalPrice;

    @ColumnAlias("lead_time")
    private Long leadTime;

    @ColumnAlias("summary_rank")
    private String summaryRank;

    public String getRfqNumber() {
        return this.rfqNumber;
    }

    public void setRfqNumber(String rfqNumber) {
        this.rfqNumber = rfqNumber;
    }

    public Long getItem() {
        return this.item;
    }

    public void setItem(Long item) {
        this.item = item;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRfqvId() {
        return this.rfqvId;
    }

    public void setRfqvId(Integer rfqvId) {
        this.rfqvId = rfqvId;
    }

    public Integer getRlsId() {
        return this.rlsId;
    }

    public void setRlsId(Integer rlsId) {
        this.rlsId = rlsId;
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

    public String getQuality() {
        return this.quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getSummaryOverwrite() {
        return this.summaryOverwrite;
    }

    public void setSummaryOverwrite(String summaryOverwrite) {
        this.summaryOverwrite = summaryOverwrite;
    }

    public String getSummaryReason() {
        return this.summaryReason;
    }

    public void setSummaryReason(String summaryReason) {
        this.summaryReason = summaryReason;
    }

    public String getRfqvDuration() {
        return this.rfqvDuration;
    }

    public void setRfqvDuration(String rfqvDuration) {
        this.rfqvDuration = rfqvDuration;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTop() {
        return this.top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getAttachments() {
        return this.attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public Double getQty() {
        return this.qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public BigDecimal getLiqUnitPrice() {
        return this.liqUnitPrice;
    }

    public void setLiqUnitPrice(BigDecimal liqUnitPrice) {
        this.liqUnitPrice = liqUnitPrice;
    }

    public BigDecimal getLiqAmount() {
        return this.liqAmount;
    }

    public void setLiqAmount(BigDecimal liqAmount) {
        this.liqAmount = liqAmount;
    }

    public Long getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getLeadTime() {
        return this.leadTime;
    }

    public void setLeadTime(Long leadTime) {
        this.leadTime = leadTime;
    }

    public String getSummaryRank() {
        return this.summaryRank;
    }

    public void setSummaryRank(String summaryRank) {
        this.summaryRank = summaryRank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetRfqsummaryResponse)) return false;
        final GetRfqsummaryResponse getRfqsummaryResponse = (GetRfqsummaryResponse) o;
        return Objects.equals(getRfqNumber(), getRfqsummaryResponse.getRfqNumber()) &&
                Objects.equals(getItem(), getRfqsummaryResponse.getItem()) &&
                Objects.equals(getDescription(), getRfqsummaryResponse.getDescription()) &&
                Objects.equals(getRfqvId(), getRfqsummaryResponse.getRfqvId()) &&
                Objects.equals(getRlsId(), getRfqsummaryResponse.getRlsId()) &&
                Objects.equals(getVendorCode(), getRfqsummaryResponse.getVendorCode()) &&
                Objects.equals(getVendorName(), getRfqsummaryResponse.getVendorName()) &&
                Objects.equals(getQuality(), getRfqsummaryResponse.getQuality()) &&
                Objects.equals(getSummaryOverwrite(), getRfqsummaryResponse.getSummaryOverwrite()) &&
                Objects.equals(getSummaryReason(), getRfqsummaryResponse.getSummaryReason()) &&
                Objects.equals(getRfqvDuration(), getRfqsummaryResponse.getRfqvDuration()) &&
                Objects.equals(getUnitPrice(), getRfqsummaryResponse.getUnitPrice()) &&
                Objects.equals(getTop(), getRfqsummaryResponse.getTop()) &&
                Objects.equals(getAttachments(), getRfqsummaryResponse.getAttachments()) &&
                Objects.equals(getQty(), getRfqsummaryResponse.getQty()) &&
                Objects.equals(getLiqUnitPrice(), getRfqsummaryResponse.getLiqUnitPrice()) &&
                Objects.equals(getLiqAmount(), getRfqsummaryResponse.getLiqAmount()) &&
                Objects.equals(getTotalPrice(), getRfqsummaryResponse.getTotalPrice()) &&
                Objects.equals(getLeadTime(), getRfqsummaryResponse.getLeadTime()) &&
                Objects.equals(getSummaryRank(), getRfqsummaryResponse.getSummaryRank());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRfqNumber(),
                getItem(),
                getDescription(),
                getRfqvId(),
                getRlsId(),
                getVendorCode(),
                getVendorName(),
                getQuality(),
                getSummaryOverwrite(),
                getSummaryReason(),
                getRfqvDuration(),
                getUnitPrice(),
                getTop(),
                getAttachments(),
                getQty(),
                getLiqUnitPrice(),
                getLiqAmount(),
                getTotalPrice(),
                getLeadTime(),
                getSummaryRank());
    }
}