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
 * TblMAccountType generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tbl_m_account_type`")
public class TblMAccountType implements Serializable {

    private Integer accId;
    private String accCode;
    private String accTitle;
    private String accStatus = "active";
    private String accCodeSap;
    private String accChildOf;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`acc_id`", nullable = false, scale = 0, precision = 10)
    public Integer getAccId() {
        return this.accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    @Column(name = "`acc_code`", nullable = true, length = 100)
    public String getAccCode() {
        return this.accCode;
    }

    public void setAccCode(String accCode) {
        this.accCode = accCode;
    }

    @Column(name = "`acc_title`", nullable = true, length = 255)
    public String getAccTitle() {
        return this.accTitle;
    }

    public void setAccTitle(String accTitle) {
        this.accTitle = accTitle;
    }

    @Column(name = "`acc_status`", nullable = true, length = 20)
    public String getAccStatus() {
        return this.accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    @Column(name = "`acc_code_sap`", nullable = true, length = 255)
    public String getAccCodeSap() {
        return this.accCodeSap;
    }

    public void setAccCodeSap(String accCodeSap) {
        this.accCodeSap = accCodeSap;
    }

    @Column(name = "`acc_child_of`", nullable = true, length = 255)
    public String getAccChildOf() {
        return this.accChildOf;
    }

    public void setAccChildOf(String accChildOf) {
        this.accChildOf = accChildOf;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TblMAccountType)) return false;
        final TblMAccountType tblMaccountType = (TblMAccountType) o;
        return Objects.equals(getAccId(), tblMaccountType.getAccId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccId());
    }
}