/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class QCompanyResponse implements Serializable {


    @ColumnAlias("company_id")
    private Integer companyId;

    @ColumnAlias("company")
    private String company;

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QCompanyResponse)) return false;
        final QCompanyResponse qcompanyResponse = (QCompanyResponse) o;
        return Objects.equals(getCompanyId(), qcompanyResponse.getCompanyId()) &&
                Objects.equals(getCompany(), qcompanyResponse.getCompany());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompanyId(),
                getCompany());
    }
}