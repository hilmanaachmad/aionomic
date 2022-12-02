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
 * TlMsAk generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_ms_ak`")
public class TlMsAk implements Serializable {

    private String abCoorsa;
    private String abCc;
    private String abAkont;

    @Id
    @Column(name = "`ab_coorsa`", nullable = true, length = 10)
    public String getAbCoorsa() {
        return this.abCoorsa;
    }

    public void setAbCoorsa(String abCoorsa) {
        this.abCoorsa = abCoorsa;
    }

    @Column(name = "`ab_cc`", nullable = true, length = 5)
    public String getAbCc() {
        return this.abCc;
    }

    public void setAbCc(String abCc) {
        this.abCc = abCc;
    }

    @Column(name = "`ab_akont`", nullable = true, length = 20)
    public String getAbAkont() {
        return this.abAkont;
    }

    public void setAbAkont(String abAkont) {
        this.abAkont = abAkont;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlMsAk)) return false;
        final TlMsAk tlMsAk = (TlMsAk) o;
        return Objects.equals(getAbCoorsa(), tlMsAk.getAbCoorsa());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbCoorsa());
    }
}