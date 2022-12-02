/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QupdateCertiDomRequest implements Serializable {


    @JsonProperty("fileName")
    @NotNull
    private String fileName;

    @JsonProperty("filePath")
    @NotNull
    private String filePath;

    @JsonProperty("idCertiDom")
    @NotNull
    private String idCertiDom;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getIdCertiDom() {
        return this.idCertiDom;
    }

    public void setIdCertiDom(String idCertiDom) {
        this.idCertiDom = idCertiDom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QupdateCertiDomRequest)) return false;
        final QupdateCertiDomRequest qupdateCertiDomRequest = (QupdateCertiDomRequest) o;
        return Objects.equals(getFileName(), qupdateCertiDomRequest.getFileName()) &&
                Objects.equals(getFilePath(), qupdateCertiDomRequest.getFilePath()) &&
                Objects.equals(getIdCertiDom(), qupdateCertiDomRequest.getIdCertiDom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFileName(),
                getFilePath(),
                getIdCertiDom());
    }
}