/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class GetRoleUserMapListResponse implements Serializable {


    @ColumnAlias("role_id")
    private Integer roleId;

    @ColumnAlias("role_title")
    private String roleTitle;

    @ColumnAlias("ids")
    private String ids;

    @ColumnAlias("created_at")
    private String createdAt;

    @ColumnAlias("users_nik")
    private String usersNik;

    @ColumnAlias("users_name")
    private String usersName;

    @ColumnAlias("ur_status")
    private String urStatus;

    @ColumnAlias("ur_additional_data")
    private String urAdditionalData;

    @ColumnAlias("ur_from_date")
    private Date urFromDate;

    @ColumnAlias("ur_to_date")
    private Date urToDate;

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

    public String getIds() {
        return this.ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsersNik() {
        return this.usersNik;
    }

    public void setUsersNik(String usersNik) {
        this.usersNik = usersNik;
    }

    public String getUsersName() {
        return this.usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getUrStatus() {
        return this.urStatus;
    }

    public void setUrStatus(String urStatus) {
        this.urStatus = urStatus;
    }

    public String getUrAdditionalData() {
        return this.urAdditionalData;
    }

    public void setUrAdditionalData(String urAdditionalData) {
        this.urAdditionalData = urAdditionalData;
    }

    public Date getUrFromDate() {
        return this.urFromDate;
    }

    public void setUrFromDate(Date urFromDate) {
        this.urFromDate = urFromDate;
    }

    public Date getUrToDate() {
        return this.urToDate;
    }

    public void setUrToDate(Date urToDate) {
        this.urToDate = urToDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetRoleUserMapListResponse)) return false;
        final GetRoleUserMapListResponse getRoleUserMapListResponse = (GetRoleUserMapListResponse) o;
        return Objects.equals(getRoleId(), getRoleUserMapListResponse.getRoleId()) &&
                Objects.equals(getRoleTitle(), getRoleUserMapListResponse.getRoleTitle()) &&
                Objects.equals(getIds(), getRoleUserMapListResponse.getIds()) &&
                Objects.equals(getCreatedAt(), getRoleUserMapListResponse.getCreatedAt()) &&
                Objects.equals(getUsersNik(), getRoleUserMapListResponse.getUsersNik()) &&
                Objects.equals(getUsersName(), getRoleUserMapListResponse.getUsersName()) &&
                Objects.equals(getUrStatus(), getRoleUserMapListResponse.getUrStatus()) &&
                Objects.equals(getUrAdditionalData(), getRoleUserMapListResponse.getUrAdditionalData()) &&
                Objects.equals(getUrFromDate(), getRoleUserMapListResponse.getUrFromDate()) &&
                Objects.equals(getUrToDate(), getRoleUserMapListResponse.getUrToDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleId(),
                getRoleTitle(),
                getIds(),
                getCreatedAt(),
                getUsersNik(),
                getUsersName(),
                getUrStatus(),
                getUrAdditionalData(),
                getUrFromDate(),
                getUrToDate());
    }
}