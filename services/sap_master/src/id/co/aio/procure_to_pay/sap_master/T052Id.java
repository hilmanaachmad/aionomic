/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.sap_master;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

public class T052Id implements Serializable {

    private String ztagg;
    private String zterm;

    public String getZtagg() {
        return this.ztagg;
    }

    public void setZtagg(String ztagg) {
        this.ztagg = ztagg;
    }

    public String getZterm() {
        return this.zterm;
    }

    public void setZterm(String zterm) {
        this.zterm = zterm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof T052)) return false;
        final T052 t052 = (T052) o;
        return Objects.equals(getZtagg(), t052.getZtagg()) &&
                Objects.equals(getZterm(), t052.getZterm());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getZtagg(),
                getZterm());
    }
}