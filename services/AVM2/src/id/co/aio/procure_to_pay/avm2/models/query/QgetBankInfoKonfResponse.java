/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class QgetBankInfoKonfResponse implements Serializable {


    @ColumnAlias("ab_idng")
    private Integer abIdng;

    @ColumnAlias("ab_coorsa")
    private String abCoorsa;

    @ColumnAlias("ab_bame")
    private String abBame;

    @ColumnAlias("ab_coey")
    private String abCoey;

    @ColumnAlias("ab_acer")
    private String abAcer;

    @ColumnAlias("ab_acme")
    private String abAcme;

    @ColumnAlias("ab_bch")
    private String abBch;

    @ColumnAlias("ab_ccy")
    private String abCcy;

    @ColumnAlias("ab_bepe")
    private String abBepe;

    @ColumnAlias("ab_beus")
    private String abBeus;

    @ColumnAlias("ab_supdoc")
    private String abSupdoc;

    @ColumnAlias("ab_supdocme")
    private String abSupdocme;

    public Integer getAbIdng() {
        return this.abIdng;
    }

    public void setAbIdng(Integer abIdng) {
        this.abIdng = abIdng;
    }

    public String getAbCoorsa() {
        return this.abCoorsa;
    }

    public void setAbCoorsa(String abCoorsa) {
        this.abCoorsa = abCoorsa;
    }

    public String getAbBame() {
        return this.abBame;
    }

    public void setAbBame(String abBame) {
        this.abBame = abBame;
    }

    public String getAbCoey() {
        return this.abCoey;
    }

    public void setAbCoey(String abCoey) {
        this.abCoey = abCoey;
    }

    public String getAbAcer() {
        return this.abAcer;
    }

    public void setAbAcer(String abAcer) {
        this.abAcer = abAcer;
    }

    public String getAbAcme() {
        return this.abAcme;
    }

    public void setAbAcme(String abAcme) {
        this.abAcme = abAcme;
    }

    public String getAbBch() {
        return this.abBch;
    }

    public void setAbBch(String abBch) {
        this.abBch = abBch;
    }

    public String getAbCcy() {
        return this.abCcy;
    }

    public void setAbCcy(String abCcy) {
        this.abCcy = abCcy;
    }

    public String getAbBepe() {
        return this.abBepe;
    }

    public void setAbBepe(String abBepe) {
        this.abBepe = abBepe;
    }

    public String getAbBeus() {
        return this.abBeus;
    }

    public void setAbBeus(String abBeus) {
        this.abBeus = abBeus;
    }

    public String getAbSupdoc() {
        return this.abSupdoc;
    }

    public void setAbSupdoc(String abSupdoc) {
        this.abSupdoc = abSupdoc;
    }

    public String getAbSupdocme() {
        return this.abSupdocme;
    }

    public void setAbSupdocme(String abSupdocme) {
        this.abSupdocme = abSupdocme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QgetBankInfoKonfResponse)) return false;
        final QgetBankInfoKonfResponse qgetBankInfoKonfResponse = (QgetBankInfoKonfResponse) o;
        return Objects.equals(getAbIdng(), qgetBankInfoKonfResponse.getAbIdng()) &&
                Objects.equals(getAbCoorsa(), qgetBankInfoKonfResponse.getAbCoorsa()) &&
                Objects.equals(getAbBame(), qgetBankInfoKonfResponse.getAbBame()) &&
                Objects.equals(getAbCoey(), qgetBankInfoKonfResponse.getAbCoey()) &&
                Objects.equals(getAbAcer(), qgetBankInfoKonfResponse.getAbAcer()) &&
                Objects.equals(getAbAcme(), qgetBankInfoKonfResponse.getAbAcme()) &&
                Objects.equals(getAbBch(), qgetBankInfoKonfResponse.getAbBch()) &&
                Objects.equals(getAbCcy(), qgetBankInfoKonfResponse.getAbCcy()) &&
                Objects.equals(getAbBepe(), qgetBankInfoKonfResponse.getAbBepe()) &&
                Objects.equals(getAbBeus(), qgetBankInfoKonfResponse.getAbBeus()) &&
                Objects.equals(getAbSupdoc(), qgetBankInfoKonfResponse.getAbSupdoc()) &&
                Objects.equals(getAbSupdocme(), qgetBankInfoKonfResponse.getAbSupdocme());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbIdng(),
                getAbCoorsa(),
                getAbBame(),
                getAbCoey(),
                getAbAcer(),
                getAbAcme(),
                getAbBch(),
                getAbCcy(),
                getAbBepe(),
                getAbBeus(),
                getAbSupdoc(),
                getAbSupdocme());
    }
}