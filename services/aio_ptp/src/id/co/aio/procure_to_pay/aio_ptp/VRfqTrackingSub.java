/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VRfqTrackingSub generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`v_rfq_tracking_sub`")
public class VRfqTrackingSub implements Serializable {

    private Integer rfqId;
    private Long vendorSubmitted;
    private String listvendor;
    private String companySendEmail;
    private String companyCode;

    @Id
    @Column(name = "`rfq_id`", nullable = true, scale = 0, precision = 10)
    public Integer getRfqId() {
        return this.rfqId;
    }

    public void setRfqId(Integer rfqId) {
        this.rfqId = rfqId;
    }

    @Column(name = "`vendor_submitted`", nullable = true, scale = 0, precision = 19)
    public Long getVendorSubmitted() {
        return this.vendorSubmitted;
    }

    public void setVendorSubmitted(Long vendorSubmitted) {
        this.vendorSubmitted = vendorSubmitted;
    }

    @Column(name = "`listvendor`", nullable = true, length = 16777215)
    public String getListvendor() {
        return this.listvendor;
    }

    public void setListvendor(String listvendor) {
        this.listvendor = listvendor;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VRfqTrackingSub)) return false;
        final VRfqTrackingSub vrfqTrackingSub = (VRfqTrackingSub) o;
        return Objects.equals(getRfqId(), vrfqTrackingSub.getRfqId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRfqId());
    }
}