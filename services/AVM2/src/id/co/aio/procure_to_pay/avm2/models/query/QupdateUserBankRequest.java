/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QupdateUserBankRequest implements Serializable {


    @JsonProperty("userName")
    @NotNull
    private String userName;

    @JsonProperty("vendorCode")
    @NotNull
    private String vendorCode;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVendorCode() {
        return this.vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QupdateUserBankRequest)) return false;
        final QupdateUserBankRequest qupdateUserBankRequest = (QupdateUserBankRequest) o;
        return Objects.equals(getUserName(), qupdateUserBankRequest.getUserName()) &&
                Objects.equals(getVendorCode(), qupdateUserBankRequest.getVendorCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(),
                getVendorCode());
    }
}