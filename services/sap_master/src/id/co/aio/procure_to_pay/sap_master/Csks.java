/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.sap_master;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Csks generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`CSKS`")
@IdClass(CsksId.class)
public class Csks implements Serializable {

    private String kostl;
    private Date datbi;
    private String kokrs;
    private Date datab;
    private String bukrs;
    private String gsber;
    private String kosar;
    private String verak;
    private String prctr;
    private String werks;

    @Id
    @Column(name = "`KOSTL`", nullable = false, length = 10)
    public String getKostl() {
        return this.kostl;
    }

    public void setKostl(String kostl) {
        this.kostl = kostl;
    }

    @Id
    @Column(name = "`DATBI`", nullable = false)
    public Date getDatbi() {
        return this.datbi;
    }

    public void setDatbi(Date datbi) {
        this.datbi = datbi;
    }

    @Id
    @Column(name = "`KOKRS`", nullable = false, length = 4)
    public String getKokrs() {
        return this.kokrs;
    }

    public void setKokrs(String kokrs) {
        this.kokrs = kokrs;
    }

    @Column(name = "`DATAB`", nullable = true)
    public Date getDatab() {
        return this.datab;
    }

    public void setDatab(Date datab) {
        this.datab = datab;
    }

    @Column(name = "`BUKRS`", nullable = true, length = 4)
    public String getBukrs() {
        return this.bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }

    @Column(name = "`GSBER`", nullable = true, length = 4)
    public String getGsber() {
        return this.gsber;
    }

    public void setGsber(String gsber) {
        this.gsber = gsber;
    }

    @Column(name = "`KOSAR`", nullable = true, length = 1)
    public String getKosar() {
        return this.kosar;
    }

    public void setKosar(String kosar) {
        this.kosar = kosar;
    }

    @Column(name = "`VERAK`", nullable = true, length = 20)
    public String getVerak() {
        return this.verak;
    }

    public void setVerak(String verak) {
        this.verak = verak;
    }

    @Column(name = "`PRCTR`", nullable = true, length = 10)
    public String getPrctr() {
        return this.prctr;
    }

    public void setPrctr(String prctr) {
        this.prctr = prctr;
    }

    @Column(name = "`WERKS`", nullable = true, length = 4)
    public String getWerks() {
        return this.werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Csks)) return false;
        final Csks csks = (Csks) o;
        return Objects.equals(getKostl(), csks.getKostl()) &&
                Objects.equals(getDatbi(), csks.getDatbi()) &&
                Objects.equals(getKokrs(), csks.getKokrs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKostl(),
                getDatbi(),
                getKokrs());
    }
}