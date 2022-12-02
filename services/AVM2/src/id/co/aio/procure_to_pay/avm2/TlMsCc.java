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
 * TlMsCc generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_ms_cc`")
public class TlMsCc implements Serializable {

    private String abCoorsa;
    private String akont;
    private String abParm;
    private String abAcup;

    @Id
    @Column(name = "`ab_coorsa`", nullable = true, length = 10)
    public String getAbCoorsa() {
        return this.abCoorsa;
    }

    public void setAbCoorsa(String abCoorsa) {
        this.abCoorsa = abCoorsa;
    }

    @Column(name = "`AKONT`", nullable = true, length = 255)
    public String getAkont() {
        return this.akont;
    }

    public void setAkont(String akont) {
        this.akont = akont;
    }

    @Column(name = "`ab_parm`", nullable = true, length = 4)
    public String getAbParm() {
        return this.abParm;
    }

    public void setAbParm(String abParm) {
        this.abParm = abParm;
    }

    @Column(name = "`ab_acup`", nullable = true, length = 4)
    public String getAbAcup() {
        return this.abAcup;
    }

    public void setAbAcup(String abAcup) {
        this.abAcup = abAcup;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlMsCc)) return false;
        final TlMsCc tlMsCc = (TlMsCc) o;
        return Objects.equals(getAbCoorsa(), tlMsCc.getAbCoorsa());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbCoorsa());
    }
}