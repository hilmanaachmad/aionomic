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

public class UpdateStatusRfqRequest implements Serializable {


    @JsonProperty("status")
    private String status;

    @JsonProperty("rlsId")
    @NotNull
    private Integer rlsId;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRlsId() {
        return this.rlsId;
    }

    public void setRlsId(Integer rlsId) {
        this.rlsId = rlsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateStatusRfqRequest)) return false;
        final UpdateStatusRfqRequest updateStatusRfqRequest = (UpdateStatusRfqRequest) o;
        return Objects.equals(getStatus(), updateStatusRfqRequest.getStatus()) &&
                Objects.equals(getRlsId(), updateStatusRfqRequest.getRlsId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(),
                getRlsId());
    }
}