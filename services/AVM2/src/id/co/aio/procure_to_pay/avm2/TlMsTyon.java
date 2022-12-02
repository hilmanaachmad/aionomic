/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TlMsTyon generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_ms_tyon`")
public class TlMsTyon implements Serializable {

    private Integer abIdpetr;
    private String abDon;

    @Id
    @Column(name = "`ab_idpetr`", nullable = false, scale = 0, precision = 10)
    public Integer getAbIdpetr() {
        return this.abIdpetr;
    }

    public void setAbIdpetr(Integer abIdpetr) {
        this.abIdpetr = abIdpetr;
    }

    @Column(name = "`ab_don`", nullable = true, length = 50)
    public String getAbDon() {
        return this.abDon;
    }

    public void setAbDon(String abDon) {
        this.abDon = abDon;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlMsTyon)) return false;
        final TlMsTyon tlMsTyon = (TlMsTyon) o;
        return Objects.equals(getAbIdpetr(), tlMsTyon.getAbIdpetr());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbIdpetr());
    }
}