/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.procedure;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class SpBudgetDetail1Response implements Serializable {


    @ColumnAlias("content")
    private List<SpBudgetDetail1ResponseContent> content;

    public List<SpBudgetDetail1ResponseContent> getContent() {
        return this.content;
    }

    public void setContent(List<SpBudgetDetail1ResponseContent> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpBudgetDetail1Response)) return false;
        final SpBudgetDetail1Response spBudgetDetail1response = (SpBudgetDetail1Response) o;
        return Objects.equals(getContent(), spBudgetDetail1response.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent());
    }
}