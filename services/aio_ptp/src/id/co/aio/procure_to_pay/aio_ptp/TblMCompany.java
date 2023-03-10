/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp;

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
 * TblMCompany generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tbl_m_company`")
public class TblMCompany implements Serializable {

    private Integer cid;
    private String ccode;
    private String ctitle;
    private String cstatus = "active";
    private String caddress;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`c_id`", nullable = false, scale = 0, precision = 10)
    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    @Column(name = "`c_code`", nullable = true, length = 100)
    public String getCcode() {
        return this.ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    @Column(name = "`c_title`", nullable = true, length = 255)
    public String getCtitle() {
        return this.ctitle;
    }

    public void setCtitle(String ctitle) {
        this.ctitle = ctitle;
    }

    @Column(name = "`c_status`", nullable = true, length = 20)
    public String getCstatus() {
        return this.cstatus;
    }

    public void setCstatus(String cstatus) {
        this.cstatus = cstatus;
    }

    @Column(name = "`c_address`", nullable = true, length = 255)
    public String getCaddress() {
        return this.caddress;
    }

    public void setCaddress(String caddress) {
        this.caddress = caddress;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TblMCompany)) return false;
        final TblMCompany tblMcompany = (TblMCompany) o;
        return Objects.equals(getCid(), tblMcompany.getCid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCid());
    }
}