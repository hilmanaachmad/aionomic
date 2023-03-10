/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * TlTrReak generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_tr_reak`")
@IdClass(TlTrReakId.class)
public class TlTrReak implements Serializable {

    private Long abIdak;
    private Integer abIdsi;
    private String abRsi;
    private Integer abFint;
    private LocalDateTime abCute;
    private String abInby;

    @Id
    @Column(name = "`ab_idak`", nullable = true, scale = 0, precision = 19)
    public Long getAbIdak() {
        return this.abIdak;
    }

    public void setAbIdak(Long abIdak) {
        this.abIdak = abIdak;
    }

    @Id
    @Column(name = "`ab_idsi`", nullable = false, scale = 0, precision = 10)
    public Integer getAbIdsi() {
        return this.abIdsi;
    }

    public void setAbIdsi(Integer abIdsi) {
        this.abIdsi = abIdsi;
    }

    @Column(name = "`ab_rsi`", nullable = true, length = 2147483647)
    public String getAbRsi() {
        return this.abRsi;
    }

    public void setAbRsi(String abRsi) {
        this.abRsi = abRsi;
    }

    @Column(name = "`ab_fint`", nullable = true, scale = 0, precision = 10)
    public Integer getAbFint() {
        return this.abFint;
    }

    public void setAbFint(Integer abFint) {
        this.abFint = abFint;
    }

    @Column(name = "`ab_cute`", nullable = true)
    public LocalDateTime getAbCute() {
        return this.abCute;
    }

    public void setAbCute(LocalDateTime abCute) {
        this.abCute = abCute;
    }

    @Column(name = "`ab_inby`", nullable = true, length = 2147483647)
    public String getAbInby() {
        return this.abInby;
    }

    public void setAbInby(String abInby) {
        this.abInby = abInby;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlTrReak)) return false;
        final TlTrReak tlTrReak = (TlTrReak) o;
        return Objects.equals(getAbIdak(), tlTrReak.getAbIdak()) &&
                Objects.equals(getAbIdsi(), tlTrReak.getAbIdsi());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbIdak(),
                getAbIdsi());
    }
}