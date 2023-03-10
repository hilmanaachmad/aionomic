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
 * TlTrRenk generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_tr_renk`")
public class TlTrRenk implements Serializable {

    private Integer abIdst;
    private String abNaor;
    private String abCry;
    private String abBame;
    private String abEmor;
    private String abCty;
    private String abBch;
    private Integer abSts;
    private String abSwt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ab_idst`", nullable = false, scale = 0, precision = 10)
    public Integer getAbIdst() {
        return this.abIdst;
    }

    public void setAbIdst(Integer abIdst) {
        this.abIdst = abIdst;
    }

    @Column(name = "`ab_naor`", nullable = true, length = 2147483647)
    public String getAbNaor() {
        return this.abNaor;
    }

    public void setAbNaor(String abNaor) {
        this.abNaor = abNaor;
    }

    @Column(name = "`ab_cry`", nullable = true, length = 50)
    public String getAbCry() {
        return this.abCry;
    }

    public void setAbCry(String abCry) {
        this.abCry = abCry;
    }

    @Column(name = "`ab_bame`", nullable = true, length = 2147483647)
    public String getAbBame() {
        return this.abBame;
    }

    public void setAbBame(String abBame) {
        this.abBame = abBame;
    }

    @Column(name = "`ab_emor`", nullable = true, length = 2147483647)
    public String getAbEmor() {
        return this.abEmor;
    }

    public void setAbEmor(String abEmor) {
        this.abEmor = abEmor;
    }

    @Column(name = "`ab_cty`", nullable = true, length = 50)
    public String getAbCty() {
        return this.abCty;
    }

    public void setAbCty(String abCty) {
        this.abCty = abCty;
    }

    @Column(name = "`ab_bch`", nullable = true, length = 2147483647)
    public String getAbBch() {
        return this.abBch;
    }

    public void setAbBch(String abBch) {
        this.abBch = abBch;
    }

    @Column(name = "`ab_sts`", nullable = true, scale = 0, precision = 10)
    public Integer getAbSts() {
        return this.abSts;
    }

    public void setAbSts(Integer abSts) {
        this.abSts = abSts;
    }

    @Column(name = "`ab_swt`", nullable = true, length = 50)
    public String getAbSwt() {
        return this.abSwt;
    }

    public void setAbSwt(String abSwt) {
        this.abSwt = abSwt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlTrRenk)) return false;
        final TlTrRenk tlTrRenk = (TlTrRenk) o;
        return Objects.equals(getAbIdst(), tlTrRenk.getAbIdst());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbIdst());
    }
}