/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class GetUserRoleResponse implements Serializable {


    @ColumnAlias("user_id")
    private String userId;

    @ColumnAlias("auth")
    private String auth;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuth() {
        return this.auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetUserRoleResponse)) return false;
        final GetUserRoleResponse getUserRoleResponse = (GetUserRoleResponse) o;
        return Objects.equals(getUserId(), getUserRoleResponse.getUserId()) &&
                Objects.equals(getAuth(), getUserRoleResponse.getAuth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(),
                getAuth());
    }
}