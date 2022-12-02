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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TlMsEm generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_ms_em`")
public class TlMsEm implements Serializable {

    private Integer abIdil;
    private String abCoorsa;
    private String abUme;
    private Integer abCor;
    private String abEmss;
    private String abPuon;
    private Integer abRfus;
    private String abRon;
    private String abInby;
    private LocalDateTime createdDate;
    private String abName;
    private String abMobilePhone;
    private String abPassword;
    private String abPosition;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ab_idil`", nullable = false, scale = 0, precision = 10)
    public Integer getAbIdil() {
        return this.abIdil;
    }

    public void setAbIdil(Integer abIdil) {
        this.abIdil = abIdil;
    }

    @Column(name = "`ab_coorsa`", nullable = true, length = 10)
    public String getAbCoorsa() {
        return this.abCoorsa;
    }

    public void setAbCoorsa(String abCoorsa) {
        this.abCoorsa = abCoorsa;
    }

    @Column(name = "`ab_ume`", nullable = true, length = 255)
    public String getAbUme() {
        return this.abUme;
    }

    public void setAbUme(String abUme) {
        this.abUme = abUme;
    }

    @Column(name = "`ab_cor`", nullable = true, scale = 0, precision = 10)
    public Integer getAbCor() {
        return this.abCor;
    }

    public void setAbCor(Integer abCor) {
        this.abCor = abCor;
    }

    @Column(name = "`ab_emss`", nullable = true, length = 100)
    public String getAbEmss() {
        return this.abEmss;
    }

    public void setAbEmss(String abEmss) {
        this.abEmss = abEmss;
    }

    @Column(name = "`ab_puon`", nullable = true, length = 5)
    public String getAbPuon() {
        return this.abPuon;
    }

    public void setAbPuon(String abPuon) {
        this.abPuon = abPuon;
    }

    @Column(name = "`ab_rfus`", nullable = true, scale = 0, precision = 10)
    public Integer getAbRfus() {
        return this.abRfus;
    }

    public void setAbRfus(Integer abRfus) {
        this.abRfus = abRfus;
    }

    @Column(name = "`ab_ron`", nullable = true, length = 2147483647)
    public String getAbRon() {
        return this.abRon;
    }

    public void setAbRon(String abRon) {
        this.abRon = abRon;
    }

    @Column(name = "`ab_inby`", nullable = true, length = 50)
    public String getAbInby() {
        return this.abInby;
    }

    public void setAbInby(String abInby) {
        this.abInby = abInby;
    }

    @Column(name = "`created_date`", nullable = true)
    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "`ab_name`", nullable = true, length = 75)
    public String getAbName() {
        return this.abName;
    }

    public void setAbName(String abName) {
        this.abName = abName;
    }

    @Column(name = "`ab_mobile_phone`", nullable = true, length = 25)
    public String getAbMobilePhone() {
        return this.abMobilePhone;
    }

    public void setAbMobilePhone(String abMobilePhone) {
        this.abMobilePhone = abMobilePhone;
    }

    @Column(name = "`ab_password`", nullable = true, length = 75)
    public String getAbPassword() {
        return this.abPassword;
    }

    public void setAbPassword(String abPassword) {
        this.abPassword = abPassword;
    }

    @Column(name = "`ab_position`", nullable = true, length = 55)
    public String getAbPosition() {
        return this.abPosition;
    }

    public void setAbPosition(String abPosition) {
        this.abPosition = abPosition;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlMsEm)) return false;
        final TlMsEm tlMsEm = (TlMsEm) o;
        return Objects.equals(getAbIdil(), tlMsEm.getAbIdil());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbIdil());
    }
}