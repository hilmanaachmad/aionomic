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
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VRfqVendorQuotation generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`v_rfq_vendor_quotation`")
public class VRfqVendorQuotation implements Serializable {

    private Integer rfqvId = 0;
    private String rfqNumber;
    private LocalDateTime rfqvCreatedAt;
    private Integer rfqvTimeLimit;
    private String companySendEmail;
    private String companyCode;
    private LocalDateTime overTimeLimit;
    private String status;
    private String rfqStatus = "active";
    private String vendorCode;
    private String vendorEmail;
    private String rfqSumAttachment;

    @Id
    @Column(name = "`rfqv_id`", nullable = false, scale = 0, precision = 10)
    public Integer getRfqvId() {
        return this.rfqvId;
    }

    public void setRfqvId(Integer rfqvId) {
        this.rfqvId = rfqvId;
    }

    @Column(name = "`rfq_number`", nullable = true, length = 100)
    public String getRfqNumber() {
        return this.rfqNumber;
    }

    public void setRfqNumber(String rfqNumber) {
        this.rfqNumber = rfqNumber;
    }

    @Column(name = "`rfqv_created_at`", nullable = true)
    public LocalDateTime getRfqvCreatedAt() {
        return this.rfqvCreatedAt;
    }

    public void setRfqvCreatedAt(LocalDateTime rfqvCreatedAt) {
        this.rfqvCreatedAt = rfqvCreatedAt;
    }

    @Column(name = "`rfqv_time_limit`", nullable = true, scale = 0, precision = 10)
    public Integer getRfqvTimeLimit() {
        return this.rfqvTimeLimit;
    }

    public void setRfqvTimeLimit(Integer rfqvTimeLimit) {
        this.rfqvTimeLimit = rfqvTimeLimit;
    }

    @Column(name = "`company_send_email`", nullable = true, length = 255)
    public String getCompanySendEmail() {
        return this.companySendEmail;
    }

    public void setCompanySendEmail(String companySendEmail) {
        this.companySendEmail = companySendEmail;
    }

    @Column(name = "`company_code`", nullable = true, length = 100)
    public String getCompanyCode() {
        return this.companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Column(name = "`over_time_limit`", nullable = true)
    public LocalDateTime getOverTimeLimit() {
        return this.overTimeLimit;
    }

    public void setOverTimeLimit(LocalDateTime overTimeLimit) {
        this.overTimeLimit = overTimeLimit;
    }

    @Column(name = "`status`", nullable = true, length = 20)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "`rfq_status`", nullable = true, length = 100)
    public String getRfqStatus() {
        return this.rfqStatus;
    }

    public void setRfqStatus(String rfqStatus) {
        this.rfqStatus = rfqStatus;
    }

    @Column(name = "`vendor_code`", nullable = true, length = 150)
    public String getVendorCode() {
        return this.vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    @Column(name = "`vendor_email`", nullable = true, length = 255)
    public String getVendorEmail() {
        return this.vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    @Column(name = "`rfq_sum_attachment`", nullable = true, length = 2147483647)
    public String getRfqSumAttachment() {
        return this.rfqSumAttachment;
    }

    public void setRfqSumAttachment(String rfqSumAttachment) {
        this.rfqSumAttachment = rfqSumAttachment;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VRfqVendorQuotation)) return false;
        final VRfqVendorQuotation vrfqVendorQuotation = (VRfqVendorQuotation) o;
        return Objects.equals(getRfqvId(), vrfqVendorQuotation.getRfqvId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRfqvId());
    }
}