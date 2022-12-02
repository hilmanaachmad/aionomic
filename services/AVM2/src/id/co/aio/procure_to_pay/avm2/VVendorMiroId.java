/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

public class VVendorMiroId implements Serializable {

    private String abPuon;
    private String username;
    private String abEmss;
    private String sapcode;
    private Integer id;
    private String abParm;

    public String getAbPuon() {
        return this.abPuon;
    }

    public void setAbPuon(String abPuon) {
        this.abPuon = abPuon;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAbEmss() {
        return this.abEmss;
    }

    public void setAbEmss(String abEmss) {
        this.abEmss = abEmss;
    }

    public String getSapcode() {
        return this.sapcode;
    }

    public void setSapcode(String sapcode) {
        this.sapcode = sapcode;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAbParm() {
        return this.abParm;
    }

    public void setAbParm(String abParm) {
        this.abParm = abParm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VVendorMiro)) return false;
        final VVendorMiro vvendorMiro = (VVendorMiro) o;
        return Objects.equals(getAbPuon(), vvendorMiro.getAbPuon()) &&
                Objects.equals(getUsername(), vvendorMiro.getUsername()) &&
                Objects.equals(getAbEmss(), vvendorMiro.getAbEmss()) &&
                Objects.equals(getSapcode(), vvendorMiro.getSapcode()) &&
                Objects.equals(getId(), vvendorMiro.getId()) &&
                Objects.equals(getAbParm(), vvendorMiro.getAbParm());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbPuon(),
                getUsername(),
                getAbEmss(),
                getSapcode(),
                getId(),
                getAbParm());
    }
}