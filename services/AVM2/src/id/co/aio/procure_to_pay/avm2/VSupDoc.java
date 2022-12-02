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
 * VSupDoc generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`v_supDoc`")
public class VSupDoc implements Serializable {

    private Integer abIdnt;
    private Integer abIdor;
    private String abNant;
    private String abNale;
    private String abJugs;
    private String abDon;
    private String abStnt;
    private String abVsi;
    private String abPathfile;
    private String abCoorsa;
    private String status;

    @Id
    @Column(name = "`ab_idnt`", nullable = false, scale = 0, precision = 10)
    public Integer getAbIdnt() {
        return this.abIdnt;
    }

    public void setAbIdnt(Integer abIdnt) {
        this.abIdnt = abIdnt;
    }

    @Column(name = "`ab_idor`", nullable = true, scale = 0, precision = 10)
    public Integer getAbIdor() {
        return this.abIdor;
    }

    public void setAbIdor(Integer abIdor) {
        this.abIdor = abIdor;
    }

    @Column(name = "`ab_nant`", nullable = true, length = 2147483647)
    public String getAbNant() {
        return this.abNant;
    }

    public void setAbNant(String abNant) {
        this.abNant = abNant;
    }

    @Column(name = "`ab_nale`", nullable = true, length = 2147483647)
    public String getAbNale() {
        return this.abNale;
    }

    public void setAbNale(String abNale) {
        this.abNale = abNale;
    }

    @Column(name = "`ab_jugs`", nullable = true, length = 2147483647)
    public String getAbJugs() {
        return this.abJugs;
    }

    public void setAbJugs(String abJugs) {
        this.abJugs = abJugs;
    }

    @Column(name = "`ab_don`", nullable = true, length = 2000)
    public String getAbDon() {
        return this.abDon;
    }

    public void setAbDon(String abDon) {
        this.abDon = abDon;
    }

    @Column(name = "`ab_stnt`", nullable = true, length = 20)
    public String getAbStnt() {
        return this.abStnt;
    }

    public void setAbStnt(String abStnt) {
        this.abStnt = abStnt;
    }

    @Column(name = "`ab_vsi`", nullable = true, length = 20)
    public String getAbVsi() {
        return this.abVsi;
    }

    public void setAbVsi(String abVsi) {
        this.abVsi = abVsi;
    }

    @Column(name = "`ab_pathfile`", nullable = true, length = 355)
    public String getAbPathfile() {
        return this.abPathfile;
    }

    public void setAbPathfile(String abPathfile) {
        this.abPathfile = abPathfile;
    }

    @Column(name = "`ab_coorsa`", nullable = true, length = 10)
    public String getAbCoorsa() {
        return this.abCoorsa;
    }

    public void setAbCoorsa(String abCoorsa) {
        this.abCoorsa = abCoorsa;
    }

    @Column(name = "`status`", nullable = false, length = 5)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VSupDoc)) return false;
        final VSupDoc vsupDoc = (VSupDoc) o;
        return Objects.equals(getAbIdnt(), vsupDoc.getAbIdnt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbIdnt());
    }
}