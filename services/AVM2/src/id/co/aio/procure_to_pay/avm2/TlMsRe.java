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
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * TlMsRe generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_ms_re`")
@IdClass(TlMsReId.class)
public class TlMsRe implements Serializable {

    private String abBnd;
    private String abCoey;
    private String abReme;

    @Id
    @Column(name = "`ab_bnd`", nullable = true, length = 255)
    public String getAbBnd() {
        return this.abBnd;
    }

    public void setAbBnd(String abBnd) {
        this.abBnd = abBnd;
    }

    @Id
    @Column(name = "`ab_coey`", nullable = true, length = 255)
    public String getAbCoey() {
        return this.abCoey;
    }

    public void setAbCoey(String abCoey) {
        this.abCoey = abCoey;
    }

    @Column(name = "`ab_reme`", nullable = true, length = 255)
    public String getAbReme() {
        return this.abReme;
    }

    public void setAbReme(String abReme) {
        this.abReme = abReme;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlMsRe)) return false;
        final TlMsRe tlMsRe = (TlMsRe) o;
        return Objects.equals(getAbBnd(), tlMsRe.getAbBnd()) &&
                Objects.equals(getAbCoey(), tlMsRe.getAbCoey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbBnd(),
                getAbCoey());
    }
}