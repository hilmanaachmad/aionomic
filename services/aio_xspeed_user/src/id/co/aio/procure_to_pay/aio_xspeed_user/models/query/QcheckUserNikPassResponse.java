/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_xspeed_user.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class QcheckUserNikPassResponse implements Serializable {


    @ColumnAlias("lg_nik")
    private String lgNik;

    @ColumnAlias("lg_name")
    private String lgName;

    public String getLgNik() {
        return this.lgNik;
    }

    public void setLgNik(String lgNik) {
        this.lgNik = lgNik;
    }

    public String getLgName() {
        return this.lgName;
    }

    public void setLgName(String lgName) {
        this.lgName = lgName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QcheckUserNikPassResponse)) return false;
        final QcheckUserNikPassResponse qcheckUserNikPassResponse = (QcheckUserNikPassResponse) o;
        return Objects.equals(getLgNik(), qcheckUserNikPassResponse.getLgNik()) &&
                Objects.equals(getLgName(), qcheckUserNikPassResponse.getLgName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLgNik(),
                getLgName());
    }
}