/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class GetRfgvendorTrackingResponse implements Serializable {


    @ColumnAlias("rfq_id")
    private Integer rfqId;

    @ColumnAlias("rfq_created_by")
    private String rfqCreatedBy;

    @ColumnAlias("rfq_created_at")
    private LocalDateTime rfqCreatedAt;

    @ColumnAlias("rfq_modified_by")
    private String rfqModifiedBy;

    @ColumnAlias("rfq_modified_at")
    private LocalDateTime rfqModifiedAt;

    @ColumnAlias("rfq_ref")
    private String rfqRef;

    @ColumnAlias("rfq_status")
    private String rfqStatus;

    @ColumnAlias("listvendor")
    private String listvendor;

    public Integer getRfqId() {
        return this.rfqId;
    }

    public void setRfqId(Integer rfqId) {
        this.rfqId = rfqId;
    }

    public String getRfqCreatedBy() {
        return this.rfqCreatedBy;
    }

    public void setRfqCreatedBy(String rfqCreatedBy) {
        this.rfqCreatedBy = rfqCreatedBy;
    }

    public LocalDateTime getRfqCreatedAt() {
        return this.rfqCreatedAt;
    }

    public void setRfqCreatedAt(LocalDateTime rfqCreatedAt) {
        this.rfqCreatedAt = rfqCreatedAt;
    }

    public String getRfqModifiedBy() {
        return this.rfqModifiedBy;
    }

    public void setRfqModifiedBy(String rfqModifiedBy) {
        this.rfqModifiedBy = rfqModifiedBy;
    }

    public LocalDateTime getRfqModifiedAt() {
        return this.rfqModifiedAt;
    }

    public void setRfqModifiedAt(LocalDateTime rfqModifiedAt) {
        this.rfqModifiedAt = rfqModifiedAt;
    }

    public String getRfqRef() {
        return this.rfqRef;
    }

    public void setRfqRef(String rfqRef) {
        this.rfqRef = rfqRef;
    }

    public String getRfqStatus() {
        return this.rfqStatus;
    }

    public void setRfqStatus(String rfqStatus) {
        this.rfqStatus = rfqStatus;
    }

    public String getListvendor() {
        return this.listvendor;
    }

    public void setListvendor(String listvendor) {
        this.listvendor = listvendor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetRfgvendorTrackingResponse)) return false;
        final GetRfgvendorTrackingResponse getRfgvendorTrackingResponse = (GetRfgvendorTrackingResponse) o;
        return Objects.equals(getRfqId(), getRfgvendorTrackingResponse.getRfqId()) &&
                Objects.equals(getRfqCreatedBy(), getRfgvendorTrackingResponse.getRfqCreatedBy()) &&
                Objects.equals(getRfqCreatedAt(), getRfgvendorTrackingResponse.getRfqCreatedAt()) &&
                Objects.equals(getRfqModifiedBy(), getRfgvendorTrackingResponse.getRfqModifiedBy()) &&
                Objects.equals(getRfqModifiedAt(), getRfgvendorTrackingResponse.getRfqModifiedAt()) &&
                Objects.equals(getRfqRef(), getRfgvendorTrackingResponse.getRfqRef()) &&
                Objects.equals(getRfqStatus(), getRfgvendorTrackingResponse.getRfqStatus()) &&
                Objects.equals(getListvendor(), getRfgvendorTrackingResponse.getListvendor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRfqId(),
                getRfqCreatedBy(),
                getRfqCreatedAt(),
                getRfqModifiedBy(),
                getRfqModifiedAt(),
                getRfqRef(),
                getRfqStatus(),
                getListvendor());
    }
}