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

public class DeleteBudgetUploadAdjustmentRequest implements Serializable {


    @JsonProperty("buId")
    @NotNull
    private String buId;

    public String getBuId() {
        return this.buId;
    }

    public void setBuId(String buId) {
        this.buId = buId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeleteBudgetUploadAdjustmentRequest)) return false;
        final DeleteBudgetUploadAdjustmentRequest deleteBudgetUploadAdjustmentRequest = (DeleteBudgetUploadAdjustmentRequest) o;
        return Objects.equals(getBuId(), deleteBudgetUploadAdjustmentRequest.getBuId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBuId());
    }
}