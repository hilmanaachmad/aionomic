/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QinsertPicRequest implements Serializable {


    @JsonProperty("namePIC")
    @NotNull
    private String namePic;

    @JsonProperty("emailPIC")
    @NotNull
    private String emailPic;

    @JsonProperty("phonePIC")
    @NotNull
    private String phonePic;

    @JsonProperty("selectPositionPIC")
    @NotNull
    private String selectPositionPic;

    @JsonProperty("selectAreaPIC")
    @NotNull
    private String selectAreaPic;

    @JsonProperty("password")
    @NotNull
    private String password;

    @JsonProperty("userName")
    @NotNull
    private String userName;

    public String getNamePic() {
        return this.namePic;
    }

    public void setNamePic(String namePic) {
        this.namePic = namePic;
    }

    public String getEmailPic() {
        return this.emailPic;
    }

    public void setEmailPic(String emailPic) {
        this.emailPic = emailPic;
    }

    public String getPhonePic() {
        return this.phonePic;
    }

    public void setPhonePic(String phonePic) {
        this.phonePic = phonePic;
    }

    public String getSelectPositionPic() {
        return this.selectPositionPic;
    }

    public void setSelectPositionPic(String selectPositionPic) {
        this.selectPositionPic = selectPositionPic;
    }

    public String getSelectAreaPic() {
        return this.selectAreaPic;
    }

    public void setSelectAreaPic(String selectAreaPic) {
        this.selectAreaPic = selectAreaPic;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QinsertPicRequest)) return false;
        final QinsertPicRequest qinsertPicRequest = (QinsertPicRequest) o;
        return Objects.equals(getNamePic(), qinsertPicRequest.getNamePic()) &&
                Objects.equals(getEmailPic(), qinsertPicRequest.getEmailPic()) &&
                Objects.equals(getPhonePic(), qinsertPicRequest.getPhonePic()) &&
                Objects.equals(getSelectPositionPic(), qinsertPicRequest.getSelectPositionPic()) &&
                Objects.equals(getSelectAreaPic(), qinsertPicRequest.getSelectAreaPic()) &&
                Objects.equals(getPassword(), qinsertPicRequest.getPassword()) &&
                Objects.equals(getUserName(), qinsertPicRequest.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNamePic(),
                getEmailPic(),
                getPhonePic(),
                getSelectPositionPic(),
                getSelectAreaPic(),
                getPassword(),
                getUserName());
    }
}