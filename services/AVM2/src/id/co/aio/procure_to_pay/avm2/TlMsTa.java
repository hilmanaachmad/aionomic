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
 * TlMsTa generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_ms_ta`")
public class TlMsTa implements Serializable {

    private Integer abIdax;
    private Integer abCc;
    private String abCoorsa;
    private String abUme;
    private String abTape;
    private String abTaey;
    private String abTade;
    private String abSkb;
    private String abExor;
    private String abExon;
    private LocalDateTime abDafrom;
    private LocalDateTime abDato;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ab_idax`", nullable = false, scale = 0, precision = 10)
    public Integer getAbIdax() {
        return this.abIdax;
    }

    public void setAbIdax(Integer abIdax) {
        this.abIdax = abIdax;
    }

    @Column(name = "`ab_cc`", nullable = true, scale = 0, precision = 10)
    public Integer getAbCc() {
        return this.abCc;
    }

    public void setAbCc(Integer abCc) {
        this.abCc = abCc;
    }

    @Column(name = "`ab_coorsa`", nullable = true, length = 20)
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

    @Column(name = "`ab_tape`", nullable = true, length = 50)
    public String getAbTape() {
        return this.abTape;
    }

    public void setAbTape(String abTape) {
        this.abTape = abTape;
    }

    @Column(name = "`ab_taey`", nullable = true, length = 20)
    public String getAbTaey() {
        return this.abTaey;
    }

    public void setAbTaey(String abTaey) {
        this.abTaey = abTaey;
    }

    @Column(name = "`ab_tade`", nullable = true, length = 255)
    public String getAbTade() {
        return this.abTade;
    }

    public void setAbTade(String abTade) {
        this.abTade = abTade;
    }

    @Column(name = "`ab_skb`", nullable = true, length = 16)
    public String getAbSkb() {
        return this.abSkb;
    }

    public void setAbSkb(String abSkb) {
        this.abSkb = abSkb;
    }

    @Column(name = "`ab_exor`", nullable = true, length = 15)
    public String getAbExor() {
        return this.abExor;
    }

    public void setAbExor(String abExor) {
        this.abExor = abExor;
    }

    @Column(name = "`ab_exon`", nullable = true, length = 5)
    public String getAbExon() {
        return this.abExon;
    }

    public void setAbExon(String abExon) {
        this.abExon = abExon;
    }

    @Column(name = "`ab_dafrom`", nullable = true)
    public LocalDateTime getAbDafrom() {
        return this.abDafrom;
    }

    public void setAbDafrom(LocalDateTime abDafrom) {
        this.abDafrom = abDafrom;
    }

    @Column(name = "`ab_dato`", nullable = true)
    public LocalDateTime getAbDato() {
        return this.abDato;
    }

    public void setAbDato(LocalDateTime abDato) {
        this.abDato = abDato;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlMsTa)) return false;
        final TlMsTa tlMsTa = (TlMsTa) o;
        return Objects.equals(getAbIdax(), tlMsTa.getAbIdax());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbIdax());
    }
}