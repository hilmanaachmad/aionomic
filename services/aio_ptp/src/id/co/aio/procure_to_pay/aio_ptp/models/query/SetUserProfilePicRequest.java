/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SetUserProfilePicRequest implements Serializable {


    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("username")
    @NotNull
    private String username;

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SetUserProfilePicRequest)) return false;
        final SetUserProfilePicRequest setUserProfilePicRequest = (SetUserProfilePicRequest) o;
        return Objects.equals(getImageUrl(), setUserProfilePicRequest.getImageUrl()) &&
                Objects.equals(getUsername(), setUserProfilePicRequest.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImageUrl(),
                getUsername());
    }
}