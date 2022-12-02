/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class GetEmailListResponse implements Serializable {


    @ColumnAlias("ab_id")
    private Integer abId;

    @ColumnAlias("ab_position")
    private String abPosition;

    @ColumnAlias("ab_email")
    private String abEmail;

    @ColumnAlias("ab_location")
    private String abLocation;

    public Integer getAbId() {
        return this.abId;
    }

    public void setAbId(Integer abId) {
        this.abId = abId;
    }

    public String getAbPosition() {
        return this.abPosition;
    }

    public void setAbPosition(String abPosition) {
        this.abPosition = abPosition;
    }

    public String getAbEmail() {
        return this.abEmail;
    }

    public void setAbEmail(String abEmail) {
        this.abEmail = abEmail;
    }

    public String getAbLocation() {
        return this.abLocation;
    }

    public void setAbLocation(String abLocation) {
        this.abLocation = abLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetEmailListResponse)) return false;
        final GetEmailListResponse getEmailListResponse = (GetEmailListResponse) o;
        return Objects.equals(getAbId(), getEmailListResponse.getAbId()) &&
                Objects.equals(getAbPosition(), getEmailListResponse.getAbPosition()) &&
                Objects.equals(getAbEmail(), getEmailListResponse.getAbEmail()) &&
                Objects.equals(getAbLocation(), getEmailListResponse.getAbLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbId(),
                getAbPosition(),
                getAbEmail(),
                getAbLocation());
    }
}