/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class GetMappingUserPurchaseGroupResponse implements Serializable {


    @ColumnAlias("upurgr_id")
    private Integer upurgrId;

    @ColumnAlias("purchase_group_id")
    private String purchaseGroupId;

    @ColumnAlias("purchase_group")
    private String purchaseGroup;

    @ColumnAlias("udep_created_at")
    private LocalDateTime udepCreatedAt;

    @ColumnAlias("udep_created_by")
    private String udepCreatedBy;

    @ColumnAlias("listUsers")
    private String listUsers;

    @ColumnAlias("nameUsers")
    private String nameUsers;

    @ColumnAlias("jumlah_user")
    private Long jumlahUser;

    @ColumnAlias("STATUS")
    private String status;

    public Integer getUpurgrId() {
        return this.upurgrId;
    }

    public void setUpurgrId(Integer upurgrId) {
        this.upurgrId = upurgrId;
    }

    public String getPurchaseGroupId() {
        return this.purchaseGroupId;
    }

    public void setPurchaseGroupId(String purchaseGroupId) {
        this.purchaseGroupId = purchaseGroupId;
    }

    public String getPurchaseGroup() {
        return this.purchaseGroup;
    }

    public void setPurchaseGroup(String purchaseGroup) {
        this.purchaseGroup = purchaseGroup;
    }

    public LocalDateTime getUdepCreatedAt() {
        return this.udepCreatedAt;
    }

    public void setUdepCreatedAt(LocalDateTime udepCreatedAt) {
        this.udepCreatedAt = udepCreatedAt;
    }

    public String getUdepCreatedBy() {
        return this.udepCreatedBy;
    }

    public void setUdepCreatedBy(String udepCreatedBy) {
        this.udepCreatedBy = udepCreatedBy;
    }

    public String getListUsers() {
        return this.listUsers;
    }

    public void setListUsers(String listUsers) {
        this.listUsers = listUsers;
    }

    public String getNameUsers() {
        return this.nameUsers;
    }

    public void setNameUsers(String nameUsers) {
        this.nameUsers = nameUsers;
    }

    public Long getJumlahUser() {
        return this.jumlahUser;
    }

    public void setJumlahUser(Long jumlahUser) {
        this.jumlahUser = jumlahUser;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetMappingUserPurchaseGroupResponse)) return false;
        final GetMappingUserPurchaseGroupResponse getMappingUserPurchaseGroupResponse = (GetMappingUserPurchaseGroupResponse) o;
        return Objects.equals(getUpurgrId(), getMappingUserPurchaseGroupResponse.getUpurgrId()) &&
                Objects.equals(getPurchaseGroupId(), getMappingUserPurchaseGroupResponse.getPurchaseGroupId()) &&
                Objects.equals(getPurchaseGroup(), getMappingUserPurchaseGroupResponse.getPurchaseGroup()) &&
                Objects.equals(getUdepCreatedAt(), getMappingUserPurchaseGroupResponse.getUdepCreatedAt()) &&
                Objects.equals(getUdepCreatedBy(), getMappingUserPurchaseGroupResponse.getUdepCreatedBy()) &&
                Objects.equals(getListUsers(), getMappingUserPurchaseGroupResponse.getListUsers()) &&
                Objects.equals(getNameUsers(), getMappingUserPurchaseGroupResponse.getNameUsers()) &&
                Objects.equals(getJumlahUser(), getMappingUserPurchaseGroupResponse.getJumlahUser()) &&
                Objects.equals(getStatus(), getMappingUserPurchaseGroupResponse.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUpurgrId(),
                getPurchaseGroupId(),
                getPurchaseGroup(),
                getUdepCreatedAt(),
                getUdepCreatedBy(),
                getListUsers(),
                getNameUsers(),
                getJumlahUser(),
                getStatus());
    }
}