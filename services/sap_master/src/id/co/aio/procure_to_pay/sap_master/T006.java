/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.sap_master;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * T006 generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`T006`")
@IdClass(T006Id.class)
public class T006 implements Serializable {

    private String msehi;
    private String mseh3;
    private String mseh6;
    private String mseht;
    private String msehl;

    @Id
    @Column(name = "`MSEHI`", nullable = false, length = 3)
    public String getMsehi() {
        return this.msehi;
    }

    public void setMsehi(String msehi) {
        this.msehi = msehi;
    }

    @Id
    @Column(name = "`MSEH3`", nullable = false, length = 3)
    public String getMseh3() {
        return this.mseh3;
    }

    public void setMseh3(String mseh3) {
        this.mseh3 = mseh3;
    }

    @Id
    @Column(name = "`MSEH6`", nullable = false, length = 6)
    public String getMseh6() {
        return this.mseh6;
    }

    public void setMseh6(String mseh6) {
        this.mseh6 = mseh6;
    }

    @Column(name = "`MSEHT`", nullable = true, length = 10)
    public String getMseht() {
        return this.mseht;
    }

    public void setMseht(String mseht) {
        this.mseht = mseht;
    }

    @Column(name = "`MSEHL`", nullable = true, length = 30)
    public String getMsehl() {
        return this.msehl;
    }

    public void setMsehl(String msehl) {
        this.msehl = msehl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof T006)) return false;
        final T006 t006 = (T006) o;
        return Objects.equals(getMsehi(), t006.getMsehi()) &&
                Objects.equals(getMseh3(), t006.getMseh3()) &&
                Objects.equals(getMseh6(), t006.getMseh6());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMsehi(),
                getMseh3(),
                getMseh6());
    }
}