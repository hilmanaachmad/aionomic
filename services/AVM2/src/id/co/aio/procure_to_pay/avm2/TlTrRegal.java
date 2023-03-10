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
 * TlTrRegal generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_tr_regal`")
public class TlTrRegal implements Serializable {

    private Integer rlId;
    private Integer rlVsi;
    private String rlUme;
    private String rlReason;
    private String rlInputby;
    private LocalDateTime rlInputdate;

    @Id
    @Column(name = "`rl_id`", nullable = false, scale = 0, precision = 10)
    public Integer getRlId() {
        return this.rlId;
    }

    public void setRlId(Integer rlId) {
        this.rlId = rlId;
    }

    @Column(name = "`rl_vsi`", nullable = true, scale = 0, precision = 10)
    public Integer getRlVsi() {
        return this.rlVsi;
    }

    public void setRlVsi(Integer rlVsi) {
        this.rlVsi = rlVsi;
    }

    @Column(name = "`rl_ume`", nullable = true, length = 150)
    public String getRlUme() {
        return this.rlUme;
    }

    public void setRlUme(String rlUme) {
        this.rlUme = rlUme;
    }

    @Column(name = "`rl_reason`", nullable = true, length = 2147483647)
    public String getRlReason() {
        return this.rlReason;
    }

    public void setRlReason(String rlReason) {
        this.rlReason = rlReason;
    }

    @Column(name = "`rl_inputby`", nullable = true, length = 50)
    public String getRlInputby() {
        return this.rlInputby;
    }

    public void setRlInputby(String rlInputby) {
        this.rlInputby = rlInputby;
    }

    @Column(name = "`rl_inputdate`", nullable = true)
    public LocalDateTime getRlInputdate() {
        return this.rlInputdate;
    }

    public void setRlInputdate(LocalDateTime rlInputdate) {
        this.rlInputdate = rlInputdate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlTrRegal)) return false;
        final TlTrRegal tlTrRegal = (TlTrRegal) o;
        return Objects.equals(getRlId(), tlTrRegal.getRlId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRlId());
    }
}