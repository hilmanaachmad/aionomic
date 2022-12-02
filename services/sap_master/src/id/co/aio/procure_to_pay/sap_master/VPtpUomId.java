/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.sap_master;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

public class VPtpUomId implements Serializable {

    private String uomChar10;
    private String uom;
    private String uomChar3;
    private String uomChar30;
    private String uomChar6;

    public String getUomChar10() {
        return this.uomChar10;
    }

    public void setUomChar10(String uomChar10) {
        this.uomChar10 = uomChar10;
    }

    public String getUom() {
        return this.uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getUomChar3() {
        return this.uomChar3;
    }

    public void setUomChar3(String uomChar3) {
        this.uomChar3 = uomChar3;
    }

    public String getUomChar30() {
        return this.uomChar30;
    }

    public void setUomChar30(String uomChar30) {
        this.uomChar30 = uomChar30;
    }

    public String getUomChar6() {
        return this.uomChar6;
    }

    public void setUomChar6(String uomChar6) {
        this.uomChar6 = uomChar6;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VPtpUom)) return false;
        final VPtpUom vptpUom = (VPtpUom) o;
        return Objects.equals(getUomChar10(), vptpUom.getUomChar10()) &&
                Objects.equals(getUom(), vptpUom.getUom()) &&
                Objects.equals(getUomChar3(), vptpUom.getUomChar3()) &&
                Objects.equals(getUomChar30(), vptpUom.getUomChar30()) &&
                Objects.equals(getUomChar6(), vptpUom.getUomChar6());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUomChar10(),
                getUom(),
                getUomChar3(),
                getUomChar30(),
                getUomChar6());
    }
}