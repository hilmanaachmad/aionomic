/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TlTrIn generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_tr_in`")
public class TlTrIn implements Serializable {

    private Integer abIdin;
    private String abNaor;
    private String abCoorsa;
    private String abNoce;
    private String abPo;
    private Date abRete;
    private Date abBaneda;
    private Integer abTyon;
    private String abTop;
    private Date abPale;
    private Integer abCcy;
    private Double abNal;
    private Integer abVat;
    private Double abVaue;
    private Double abTont;
    private String abDon;
    private String abInby;
    private String abSts;

    @Id
    @Column(name = "`ab_idin`", nullable = false, scale = 0, precision = 10)
    public Integer getAbIdin() {
        return this.abIdin;
    }

    public void setAbIdin(Integer abIdin) {
        this.abIdin = abIdin;
    }

    @Column(name = "`ab_naor`", nullable = true, length = 2147483647)
    public String getAbNaor() {
        return this.abNaor;
    }

    public void setAbNaor(String abNaor) {
        this.abNaor = abNaor;
    }

    @Column(name = "`ab_coorsa`", nullable = true, length = 10)
    public String getAbCoorsa() {
        return this.abCoorsa;
    }

    public void setAbCoorsa(String abCoorsa) {
        this.abCoorsa = abCoorsa;
    }

    @Column(name = "`ab_noce`", nullable = true, length = 50)
    public String getAbNoce() {
        return this.abNoce;
    }

    public void setAbNoce(String abNoce) {
        this.abNoce = abNoce;
    }

    @Column(name = "`ab_po`", nullable = true, length = 50)
    public String getAbPo() {
        return this.abPo;
    }

    public void setAbPo(String abPo) {
        this.abPo = abPo;
    }

    @Column(name = "`ab_rete`", nullable = true)
    public Date getAbRete() {
        return this.abRete;
    }

    public void setAbRete(Date abRete) {
        this.abRete = abRete;
    }

    @Column(name = "`ab_baneda`", nullable = false)
    public Date getAbBaneda() {
        return this.abBaneda;
    }

    public void setAbBaneda(Date abBaneda) {
        this.abBaneda = abBaneda;
    }

    @Column(name = "`ab_tyon`", nullable = true, scale = 0, precision = 10)
    public Integer getAbTyon() {
        return this.abTyon;
    }

    public void setAbTyon(Integer abTyon) {
        this.abTyon = abTyon;
    }

    @Column(name = "`ab_top`", nullable = false, length = 5)
    public String getAbTop() {
        return this.abTop;
    }

    public void setAbTop(String abTop) {
        this.abTop = abTop;
    }

    @Column(name = "`ab_pale`", nullable = true)
    public Date getAbPale() {
        return this.abPale;
    }

    public void setAbPale(Date abPale) {
        this.abPale = abPale;
    }

    @Column(name = "`ab_ccy`", nullable = true, scale = 0, precision = 10)
    public Integer getAbCcy() {
        return this.abCcy;
    }

    public void setAbCcy(Integer abCcy) {
        this.abCcy = abCcy;
    }

    @Column(name = "`ab_nal`", nullable = true, scale = 9, precision = 15)
    public Double getAbNal() {
        return this.abNal;
    }

    public void setAbNal(Double abNal) {
        this.abNal = abNal;
    }

    @Column(name = "`ab_vat`", nullable = true, scale = 0, precision = 10)
    public Integer getAbVat() {
        return this.abVat;
    }

    public void setAbVat(Integer abVat) {
        this.abVat = abVat;
    }

    @Column(name = "`ab_vaue`", nullable = true, scale = 9, precision = 15)
    public Double getAbVaue() {
        return this.abVaue;
    }

    public void setAbVaue(Double abVaue) {
        this.abVaue = abVaue;
    }

    @Column(name = "`ab_tont`", nullable = true, scale = 9, precision = 15)
    public Double getAbTont() {
        return this.abTont;
    }

    public void setAbTont(Double abTont) {
        this.abTont = abTont;
    }

    @Column(name = "`ab_don`", nullable = true, length = 2147483647)
    public String getAbDon() {
        return this.abDon;
    }

    public void setAbDon(String abDon) {
        this.abDon = abDon;
    }

    @Column(name = "`ab_inby`", nullable = true, length = 50)
    public String getAbInby() {
        return this.abInby;
    }

    public void setAbInby(String abInby) {
        this.abInby = abInby;
    }

    @Column(name = "`ab_sts`", nullable = true, length = 20)
    public String getAbSts() {
        return this.abSts;
    }

    public void setAbSts(String abSts) {
        this.abSts = abSts;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlTrIn)) return false;
        final TlTrIn tlTrIn = (TlTrIn) o;
        return Objects.equals(getAbIdin(), tlTrIn.getAbIdin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbIdin());
    }
}