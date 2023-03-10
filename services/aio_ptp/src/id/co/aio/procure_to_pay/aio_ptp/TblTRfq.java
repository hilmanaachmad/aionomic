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
 * TblTRfq generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tbl_t_rfq`")
public class TblTRfq implements Serializable {

    private Integer rfqId;
    private String rfqCreatedBy = "SYSTEM";
    private LocalDateTime rfqCreatedAt;
    private String rfqModifiedBy = "SYSTEM";
    private LocalDateTime rfqModifiedAt;
    private String rfqRef;
    private String rfqStatus = "active";
    private String rfqDelAttachment;
    private String rfqSumAttachment;
    private String rfqCreatedName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`rfq_id`", nullable = false, scale = 0, precision = 10)
    public Integer getRfqId() {
        return this.rfqId;
    }

    public void setRfqId(Integer rfqId) {
        this.rfqId = rfqId;
    }

    @Column(name = "`rfq_created_by`", nullable = true, length = 100)
    public String getRfqCreatedBy() {
        return this.rfqCreatedBy;
    }

    public void setRfqCreatedBy(String rfqCreatedBy) {
        this.rfqCreatedBy = rfqCreatedBy;
    }

    @Column(name = "`rfq_created_at`", nullable = true)
    public LocalDateTime getRfqCreatedAt() {
        return this.rfqCreatedAt;
    }

    public void setRfqCreatedAt(LocalDateTime rfqCreatedAt) {
        this.rfqCreatedAt = rfqCreatedAt;
    }

    @Column(name = "`rfq_modified_by`", nullable = true, length = 100)
    public String getRfqModifiedBy() {
        return this.rfqModifiedBy;
    }

    public void setRfqModifiedBy(String rfqModifiedBy) {
        this.rfqModifiedBy = rfqModifiedBy;
    }

    @Column(name = "`rfq_modified_at`", nullable = true)
    public LocalDateTime getRfqModifiedAt() {
        return this.rfqModifiedAt;
    }

    public void setRfqModifiedAt(LocalDateTime rfqModifiedAt) {
        this.rfqModifiedAt = rfqModifiedAt;
    }

    @Column(name = "`rfq_ref`", nullable = true, length = 100)
    public String getRfqRef() {
        return this.rfqRef;
    }

    public void setRfqRef(String rfqRef) {
        this.rfqRef = rfqRef;
    }

    @Column(name = "`rfq_status`", nullable = true, length = 100)
    public String getRfqStatus() {
        return this.rfqStatus;
    }

    public void setRfqStatus(String rfqStatus) {
        this.rfqStatus = rfqStatus;
    }

    @Column(name = "`rfq_del_attachment`", nullable = true, length = 2147483647)
    public String getRfqDelAttachment() {
        return this.rfqDelAttachment;
    }

    public void setRfqDelAttachment(String rfqDelAttachment) {
        this.rfqDelAttachment = rfqDelAttachment;
    }

    @Column(name = "`rfq_sum_attachment`", nullable = true, length = 2147483647)
    public String getRfqSumAttachment() {
        return this.rfqSumAttachment;
    }

    public void setRfqSumAttachment(String rfqSumAttachment) {
        this.rfqSumAttachment = rfqSumAttachment;
    }

    @Column(name = "`rfq_created_name`", nullable = true, length = 100)
    public String getRfqCreatedName() {
        return this.rfqCreatedName;
    }

    public void setRfqCreatedName(String rfqCreatedName) {
        this.rfqCreatedName = rfqCreatedName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TblTRfq)) return false;
        final TblTRfq tblTrfq = (TblTRfq) o;
        return Objects.equals(getRfqId(), tblTrfq.getRfqId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRfqId());
    }
}