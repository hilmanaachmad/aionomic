/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TlMsCa generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_ms_ca`")
public class TlMsCa implements Serializable {

    private Integer abIdry;
    private String abCame;
    private Integer abPade;
    private String abStry;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ab_idry`", nullable = false, scale = 0, precision = 10)
    public Integer getAbIdry() {
        return this.abIdry;
    }

    public void setAbIdry(Integer abIdry) {
        this.abIdry = abIdry;
    }

    @Column(name = "`ab_came`", nullable = true, length = 255)
    public String getAbCame() {
        return this.abCame;
    }

    public void setAbCame(String abCame) {
        this.abCame = abCame;
    }

    @Column(name = "`ab_pade`", nullable = true, scale = 0, precision = 10)
    public Integer getAbPade() {
        return this.abPade;
    }

    public void setAbPade(Integer abPade) {
        this.abPade = abPade;
    }

    @Column(name = "`ab_stry`", nullable = true, length = 20)
    public String getAbStry() {
        return this.abStry;
    }

    public void setAbStry(String abStry) {
        this.abStry = abStry;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlMsCa)) return false;
        final TlMsCa tlMsCa = (TlMsCa) o;
        return Objects.equals(getAbIdry(), tlMsCa.getAbIdry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbIdry());
    }
}