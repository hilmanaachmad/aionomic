/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class QAccTypeBudgetHmapDepUserResponse implements Serializable {


    @ColumnAlias("acc_id")
    private Integer accId;

    @ColumnAlias("acc_title")
    private String accTitle;

    public Integer getAccId() {
        return this.accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public String getAccTitle() {
        return this.accTitle;
    }

    public void setAccTitle(String accTitle) {
        this.accTitle = accTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QAccTypeBudgetHmapDepUserResponse)) return false;
        final QAccTypeBudgetHmapDepUserResponse qaccTypeBudgetHmapDepUserResponse = (QAccTypeBudgetHmapDepUserResponse) o;
        return Objects.equals(getAccId(), qaccTypeBudgetHmapDepUserResponse.getAccId()) &&
                Objects.equals(getAccTitle(), qaccTypeBudgetHmapDepUserResponse.getAccTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccId(),
                getAccTitle());
    }
}