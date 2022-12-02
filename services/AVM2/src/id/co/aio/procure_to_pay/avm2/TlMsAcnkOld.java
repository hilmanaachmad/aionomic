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
import javax.persistence.Table;

/**
 * TlMsAcnkOld generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_ms_acnk_old`")
public class TlMsAcnkOld implements Serializable {

    private Integer abIdng;
    private String abCoorsa;
    private String abCoey;
    private String abBaey;
    private String abUme;
    private String abAcer;
    private String abAcme;
    private String abBch;
    private String abCcy;
    private String abBepe;
    private String abBeus;
    private String abSuan;
    private String abInby;
    private LocalDateTime abCute;
    private Integer abFlag;
    private String abMece;

    @Id
    @Column(name = "`ab_idng`", nullable = false, scale = 0, precision = 10)
    public Integer getAbIdng() {
        return this.abIdng;
    }

    public void setAbIdng(Integer abIdng) {
        this.abIdng = abIdng;
    }

    @Column(name = "`ab_coorsa`", nullable = true, length = 10)
    public String getAbCoorsa() {
        return this.abCoorsa;
    }

    public void setAbCoorsa(String abCoorsa) {
        this.abCoorsa = abCoorsa;
    }

    @Column(name = "`ab_coey`", nullable = true, length = 3)
    public String getAbCoey() {
        return this.abCoey;
    }

    public void setAbCoey(String abCoey) {
        this.abCoey = abCoey;
    }

    @Column(name = "`ab_baey`", nullable = true, length = 15)
    public String getAbBaey() {
        return this.abBaey;
    }

    public void setAbBaey(String abBaey) {
        this.abBaey = abBaey;
    }

    @Column(name = "`ab_ume`", nullable = true, length = 255)
    public String getAbUme() {
        return this.abUme;
    }

    public void setAbUme(String abUme) {
        this.abUme = abUme;
    }

    @Column(name = "`ab_acer`", nullable = true, length = 38)
    public String getAbAcer() {
        return this.abAcer;
    }

    public void setAbAcer(String abAcer) {
        this.abAcer = abAcer;
    }

    @Column(name = "`ab_acme`", nullable = true, length = 60)
    public String getAbAcme() {
        return this.abAcme;
    }

    public void setAbAcme(String abAcme) {
        this.abAcme = abAcme;
    }

    @Column(name = "`ab_bch`", nullable = true, length = 255)
    public String getAbBch() {
        return this.abBch;
    }

    public void setAbBch(String abBch) {
        this.abBch = abBch;
    }

    @Column(name = "`ab_ccy`", nullable = true, length = 5)
    public String getAbCcy() {
        return this.abCcy;
    }

    public void setAbCcy(String abCcy) {
        this.abCcy = abCcy;
    }

    @Column(name = "`ab_bepe`", nullable = true, length = 50)
    public String getAbBepe() {
        return this.abBepe;
    }

    public void setAbBepe(String abBepe) {
        this.abBepe = abBepe;
    }

    @Column(name = "`ab_beus`", nullable = true, length = 50)
    public String getAbBeus() {
        return this.abBeus;
    }

    public void setAbBeus(String abBeus) {
        this.abBeus = abBeus;
    }

    @Column(name = "`ab_suan`", nullable = true, length = 255)
    public String getAbSuan() {
        return this.abSuan;
    }

    public void setAbSuan(String abSuan) {
        this.abSuan = abSuan;
    }

    @Column(name = "`ab_inby`", nullable = true, length = 50)
    public String getAbInby() {
        return this.abInby;
    }

    public void setAbInby(String abInby) {
        this.abInby = abInby;
    }

    @Column(name = "`ab_cute`", nullable = true)
    public LocalDateTime getAbCute() {
        return this.abCute;
    }

    public void setAbCute(LocalDateTime abCute) {
        this.abCute = abCute;
    }

    @Column(name = "`ab_flag`", nullable = true, scale = 0, precision = 10)
    public Integer getAbFlag() {
        return this.abFlag;
    }

    public void setAbFlag(Integer abFlag) {
        this.abFlag = abFlag;
    }

    @Column(name = "`ab_mece`", nullable = true, length = 50)
    public String getAbMece() {
        return this.abMece;
    }

    public void setAbMece(String abMece) {
        this.abMece = abMece;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlMsAcnkOld)) return false;
        final TlMsAcnkOld tlMsAcnkOld = (TlMsAcnkOld) o;
        return Objects.equals(getAbIdng(), tlMsAcnkOld.getAbIdng());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbIdng());
    }
}