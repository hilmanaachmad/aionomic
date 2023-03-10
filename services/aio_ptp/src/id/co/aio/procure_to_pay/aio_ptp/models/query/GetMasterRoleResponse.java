/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class GetMasterRoleResponse implements Serializable {


    @ColumnAlias("role_id")
    private Integer roleId;

    @ColumnAlias("role_title")
    private String roleTitle;

    @ColumnAlias("role_status")
    private String roleStatus;

    @ColumnAlias("role_created_by")
    private String roleCreatedBy;

    @ColumnAlias("role_created_at")
    private LocalDateTime roleCreatedAt;

    @ColumnAlias("auth_ids")
    private String authIds;

    @ColumnAlias("auth_codes")
    private String authCodes;

    @ColumnAlias("auth_descs")
    private String authDescs;

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleTitle() {
        return this.roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    public String getRoleStatus() {
        return this.roleStatus;
    }

    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
    }

    public String getRoleCreatedBy() {
        return this.roleCreatedBy;
    }

    public void setRoleCreatedBy(String roleCreatedBy) {
        this.roleCreatedBy = roleCreatedBy;
    }

    public LocalDateTime getRoleCreatedAt() {
        return this.roleCreatedAt;
    }

    public void setRoleCreatedAt(LocalDateTime roleCreatedAt) {
        this.roleCreatedAt = roleCreatedAt;
    }

    public String getAuthIds() {
        return this.authIds;
    }

    public void setAuthIds(String authIds) {
        this.authIds = authIds;
    }

    public String getAuthCodes() {
        return this.authCodes;
    }

    public void setAuthCodes(String authCodes) {
        this.authCodes = authCodes;
    }

    public String getAuthDescs() {
        return this.authDescs;
    }

    public void setAuthDescs(String authDescs) {
        this.authDescs = authDescs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetMasterRoleResponse)) return false;
        final GetMasterRoleResponse getMasterRoleResponse = (GetMasterRoleResponse) o;
        return Objects.equals(getRoleId(), getMasterRoleResponse.getRoleId()) &&
                Objects.equals(getRoleTitle(), getMasterRoleResponse.getRoleTitle()) &&
                Objects.equals(getRoleStatus(), getMasterRoleResponse.getRoleStatus()) &&
                Objects.equals(getRoleCreatedBy(), getMasterRoleResponse.getRoleCreatedBy()) &&
                Objects.equals(getRoleCreatedAt(), getMasterRoleResponse.getRoleCreatedAt()) &&
                Objects.equals(getAuthIds(), getMasterRoleResponse.getAuthIds()) &&
                Objects.equals(getAuthCodes(), getMasterRoleResponse.getAuthCodes()) &&
                Objects.equals(getAuthDescs(), getMasterRoleResponse.getAuthDescs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleId(),
                getRoleTitle(),
                getRoleStatus(),
                getRoleCreatedBy(),
                getRoleCreatedAt(),
                getAuthIds(),
                getAuthCodes(),
                getAuthDescs());
    }
}