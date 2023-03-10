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

public class PrLineItemChangeCaoAssetRequest implements Serializable {


    @JsonProperty("pliModifiedAt")
    @NotNull
    private LocalDateTime pliModifiedAt;

    @JsonProperty("pliModifiedBy")
    @NotNull
    private String pliModifiedBy;

    @JsonProperty("pliCoa")
    private String pliCoa;

    @JsonProperty("assetNumber")
    private String assetNumber;

    @JsonProperty("bhId")
    private String bhId;

    @JsonProperty("pliCostCenterId")
    private String pliCostCenterId;

    @JsonProperty("pliCostCenterTitle")
    private String pliCostCenterTitle;

    @JsonProperty("pliId")
    @NotNull
    private Integer pliId;

    public LocalDateTime getPliModifiedAt() {
        return this.pliModifiedAt;
    }

    public void setPliModifiedAt(LocalDateTime pliModifiedAt) {
        this.pliModifiedAt = pliModifiedAt;
    }

    public String getPliModifiedBy() {
        return this.pliModifiedBy;
    }

    public void setPliModifiedBy(String pliModifiedBy) {
        this.pliModifiedBy = pliModifiedBy;
    }

    public String getPliCoa() {
        return this.pliCoa;
    }

    public void setPliCoa(String pliCoa) {
        this.pliCoa = pliCoa;
    }

    public String getAssetNumber() {
        return this.assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getBhId() {
        return this.bhId;
    }

    public void setBhId(String bhId) {
        this.bhId = bhId;
    }

    public String getPliCostCenterId() {
        return this.pliCostCenterId;
    }

    public void setPliCostCenterId(String pliCostCenterId) {
        this.pliCostCenterId = pliCostCenterId;
    }

    public String getPliCostCenterTitle() {
        return this.pliCostCenterTitle;
    }

    public void setPliCostCenterTitle(String pliCostCenterTitle) {
        this.pliCostCenterTitle = pliCostCenterTitle;
    }

    public Integer getPliId() {
        return this.pliId;
    }

    public void setPliId(Integer pliId) {
        this.pliId = pliId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrLineItemChangeCaoAssetRequest)) return false;
        final PrLineItemChangeCaoAssetRequest prLineItemChangeCaoAssetRequest = (PrLineItemChangeCaoAssetRequest) o;
        return Objects.equals(getPliModifiedAt(), prLineItemChangeCaoAssetRequest.getPliModifiedAt()) &&
                Objects.equals(getPliModifiedBy(), prLineItemChangeCaoAssetRequest.getPliModifiedBy()) &&
                Objects.equals(getPliCoa(), prLineItemChangeCaoAssetRequest.getPliCoa()) &&
                Objects.equals(getAssetNumber(), prLineItemChangeCaoAssetRequest.getAssetNumber()) &&
                Objects.equals(getBhId(), prLineItemChangeCaoAssetRequest.getBhId()) &&
                Objects.equals(getPliCostCenterId(), prLineItemChangeCaoAssetRequest.getPliCostCenterId()) &&
                Objects.equals(getPliCostCenterTitle(), prLineItemChangeCaoAssetRequest.getPliCostCenterTitle()) &&
                Objects.equals(getPliId(), prLineItemChangeCaoAssetRequest.getPliId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPliModifiedAt(),
                getPliModifiedBy(),
                getPliCoa(),
                getAssetNumber(),
                getBhId(),
                getPliCostCenterId(),
                getPliCostCenterTitle(),
                getPliId());
    }
}