/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.sap_master;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Tcurr generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`TCURR`")
@IdClass(TcurrId.class)
public class Tcurr implements Serializable {

    private String kurst;
    private String fcurr;
    private String tcurr;
    private String gdatu;
    private Double ukurs;

    @Id
    @Column(name = "`KURST`", nullable = false, length = 4)
    public String getKurst() {
        return this.kurst;
    }

    public void setKurst(String kurst) {
        this.kurst = kurst;
    }

    @Id
    @Column(name = "`FCURR`", nullable = false, length = 5)
    public String getFcurr() {
        return this.fcurr;
    }

    public void setFcurr(String fcurr) {
        this.fcurr = fcurr;
    }

    @Id
    @Column(name = "`TCURR`", nullable = false, length = 5)
    public String getTcurr() {
        return this.tcurr;
    }

    public void setTcurr(String tcurr) {
        this.tcurr = tcurr;
    }

    @Id
    @Column(name = "`GDATU`", nullable = false, length = 10)
    public String getGdatu() {
        return this.gdatu;
    }

    public void setGdatu(String gdatu) {
        this.gdatu = gdatu;
    }

    @Column(name = "`UKURS`", nullable = true, scale = 5, precision = 10)
    public Double getUkurs() {
        return this.ukurs;
    }

    public void setUkurs(Double ukurs) {
        this.ukurs = ukurs;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tcurr)) return false;
        final Tcurr tcurrInstance = (Tcurr) o;
        return Objects.equals(getKurst(), tcurrInstance.getKurst()) &&
                Objects.equals(getFcurr(), tcurrInstance.getFcurr()) &&
                Objects.equals(getTcurr(), tcurrInstance.getTcurr()) &&
                Objects.equals(getGdatu(), tcurrInstance.getGdatu());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKurst(),
                getFcurr(),
                getTcurr(),
                getGdatu());
    }
}