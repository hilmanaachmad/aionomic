/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QinsertNpwpRequest implements Serializable {


    @JsonProperty("idVendor")
    @NotNull
    private String idVendor;

    @JsonProperty("fileName")
    @NotNull
    private String fileName;

    @JsonProperty("fileUrl")
    @NotNull
    private String fileUrl;

    public String getIdVendor() {
        return this.idVendor;
    }

    public void setIdVendor(String idVendor) {
        this.idVendor = idVendor;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QinsertNpwpRequest)) return false;
        final QinsertNpwpRequest qinsertNpwpRequest = (QinsertNpwpRequest) o;
        return Objects.equals(getIdVendor(), qinsertNpwpRequest.getIdVendor()) &&
                Objects.equals(getFileName(), qinsertNpwpRequest.getFileName()) &&
                Objects.equals(getFileUrl(), qinsertNpwpRequest.getFileUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdVendor(),
                getFileName(),
                getFileUrl());
    }
}