/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

public class TlTrStceId implements Serializable {

    private Integer abIdst;
    private Integer abIdin;

    public Integer getAbIdst() {
        return this.abIdst;
    }

    public void setAbIdst(Integer abIdst) {
        this.abIdst = abIdst;
    }

    public Integer getAbIdin() {
        return this.abIdin;
    }

    public void setAbIdin(Integer abIdin) {
        this.abIdin = abIdin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlTrStce)) return false;
        final TlTrStce tlTrStce = (TlTrStce) o;
        return Objects.equals(getAbIdst(), tlTrStce.getAbIdst()) &&
                Objects.equals(getAbIdin(), tlTrStce.getAbIdin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbIdst(),
                getAbIdin());
    }
}