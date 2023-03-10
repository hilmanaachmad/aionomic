/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangeRfqsetNullRequest implements Serializable {


    @JsonProperty("rfqvCreatedAt")
    @NotNull
    private LocalDateTime rfqvCreatedAt;

    @JsonProperty("rfqvModifiedBy")
    @NotNull
    private String rfqvModifiedBy;

    @JsonProperty("rfqvModifiedAt")
    @NotNull
    private LocalDateTime rfqvModifiedAt;

    @JsonProperty("rfqvStatus")
    @NotNull
    private String rfqvStatus;

    @JsonProperty("rfqvVendorCode")
    @NotNull
    private String rfqvVendorCode;

    @JsonProperty("rfqvVendorEmail")
    private String rfqvVendorEmail;

    @JsonProperty("rfqvVendorPhone")
    private String rfqvVendorPhone;

    @JsonProperty("rfqvVendorName")
    @NotNull
    private String rfqvVendorName;

    @JsonProperty("rfqvVendorAddress")
    @NotNull
    private String rfqvVendorAddress;

    @JsonProperty("rfqvVendorTitle")
    @NotNull
    private String rfqvVendorTitle;

    @JsonProperty("rfqvId")
    @NotNull
    private Integer rfqvId;

    public LocalDateTime getRfqvCreatedAt() {
        return this.rfqvCreatedAt;
    }

    public void setRfqvCreatedAt(LocalDateTime rfqvCreatedAt) {
        this.rfqvCreatedAt = rfqvCreatedAt;
    }

    public String getRfqvModifiedBy() {
        return this.rfqvModifiedBy;
    }

    public void setRfqvModifiedBy(String rfqvModifiedBy) {
        this.rfqvModifiedBy = rfqvModifiedBy;
    }

    public LocalDateTime getRfqvModifiedAt() {
        return this.rfqvModifiedAt;
    }

    public void setRfqvModifiedAt(LocalDateTime rfqvModifiedAt) {
        this.rfqvModifiedAt = rfqvModifiedAt;
    }

    public String getRfqvStatus() {
        return this.rfqvStatus;
    }

    public void setRfqvStatus(String rfqvStatus) {
        this.rfqvStatus = rfqvStatus;
    }

    public String getRfqvVendorCode() {
        return this.rfqvVendorCode;
    }

    public void setRfqvVendorCode(String rfqvVendorCode) {
        this.rfqvVendorCode = rfqvVendorCode;
    }

    public String getRfqvVendorEmail() {
        return this.rfqvVendorEmail;
    }

    public void setRfqvVendorEmail(String rfqvVendorEmail) {
        this.rfqvVendorEmail = rfqvVendorEmail;
    }

    public String getRfqvVendorPhone() {
        return this.rfqvVendorPhone;
    }

    public void setRfqvVendorPhone(String rfqvVendorPhone) {
        this.rfqvVendorPhone = rfqvVendorPhone;
    }

    public String getRfqvVendorName() {
        return this.rfqvVendorName;
    }

    public void setRfqvVendorName(String rfqvVendorName) {
        this.rfqvVendorName = rfqvVendorName;
    }

    public String getRfqvVendorAddress() {
        return this.rfqvVendorAddress;
    }

    public void setRfqvVendorAddress(String rfqvVendorAddress) {
        this.rfqvVendorAddress = rfqvVendorAddress;
    }

    public String getRfqvVendorTitle() {
        return this.rfqvVendorTitle;
    }

    public void setRfqvVendorTitle(String rfqvVendorTitle) {
        this.rfqvVendorTitle = rfqvVendorTitle;
    }

    public Integer getRfqvId() {
        return this.rfqvId;
    }

    public void setRfqvId(Integer rfqvId) {
        this.rfqvId = rfqvId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChangeRfqsetNullRequest)) return false;
        final ChangeRfqsetNullRequest changeRfqsetNullRequest = (ChangeRfqsetNullRequest) o;
        return Objects.equals(getRfqvCreatedAt(), changeRfqsetNullRequest.getRfqvCreatedAt()) &&
                Objects.equals(getRfqvModifiedBy(), changeRfqsetNullRequest.getRfqvModifiedBy()) &&
                Objects.equals(getRfqvModifiedAt(), changeRfqsetNullRequest.getRfqvModifiedAt()) &&
                Objects.equals(getRfqvStatus(), changeRfqsetNullRequest.getRfqvStatus()) &&
                Objects.equals(getRfqvVendorCode(), changeRfqsetNullRequest.getRfqvVendorCode()) &&
                Objects.equals(getRfqvVendorEmail(), changeRfqsetNullRequest.getRfqvVendorEmail()) &&
                Objects.equals(getRfqvVendorPhone(), changeRfqsetNullRequest.getRfqvVendorPhone()) &&
                Objects.equals(getRfqvVendorName(), changeRfqsetNullRequest.getRfqvVendorName()) &&
                Objects.equals(getRfqvVendorAddress(), changeRfqsetNullRequest.getRfqvVendorAddress()) &&
                Objects.equals(getRfqvVendorTitle(), changeRfqsetNullRequest.getRfqvVendorTitle()) &&
                Objects.equals(getRfqvId(), changeRfqsetNullRequest.getRfqvId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRfqvCreatedAt(),
                getRfqvModifiedBy(),
                getRfqvModifiedAt(),
                getRfqvStatus(),
                getRfqvVendorCode(),
                getRfqvVendorEmail(),
                getRfqvVendorPhone(),
                getRfqvVendorName(),
                getRfqvVendorAddress(),
                getRfqvVendorTitle(),
                getRfqvId());
    }
}