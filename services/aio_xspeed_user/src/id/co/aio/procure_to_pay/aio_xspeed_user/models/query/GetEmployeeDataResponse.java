/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_xspeed_user.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class GetEmployeeDataResponse implements Serializable {


    @ColumnAlias("lg_email_aio")
    private String lgEmailAio;

    @ColumnAlias("lg_nik")
    private String lgNik;

    public String getLgEmailAio() {
        return this.lgEmailAio;
    }

    public void setLgEmailAio(String lgEmailAio) {
        this.lgEmailAio = lgEmailAio;
    }

    public String getLgNik() {
        return this.lgNik;
    }

    public void setLgNik(String lgNik) {
        this.lgNik = lgNik;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetEmployeeDataResponse)) return false;
        final GetEmployeeDataResponse getEmployeeDataResponse = (GetEmployeeDataResponse) o;
        return Objects.equals(getLgEmailAio(), getEmployeeDataResponse.getLgEmailAio()) &&
                Objects.equals(getLgNik(), getEmployeeDataResponse.getLgNik());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLgEmailAio(),
                getLgNik());
    }
}