/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VGoodsRfq generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`v_goods_rfq`")
public class VGoodsRfq implements Serializable {

    private Integer liId = 0;
    private String prNumber;
    private String company;
    private String companyCode;
    private String prPurchaseBy;
    private String prCreator = "SYSTEM";
    private String item;
    private String prDescription;
    private Integer qty;
    private String uom;
    private Date eta;
    private String materialGroup;
    private String materialGroupDesc;
    private Integer departmentId;
    private String rfqNumber;
    private String rfqStatus;
    private Integer rlsId = 0;
    private String repNik;

    @Id
    @Column(name = "`li_id`", nullable = false, scale = 0, precision = 10)
    public Integer getLiId() {
        return this.liId;
    }

    public void setLiId(Integer liId) {
        this.liId = liId;
    }

    @Column(name = "`pr_number`", nullable = true, length = 100)
    public String getPrNumber() {
        return this.prNumber;
    }

    public void setPrNumber(String prNumber) {
        this.prNumber = prNumber;
    }

    @Column(name = "`company`", nullable = true, length = 255)
    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "`company_code`", nullable = true, length = 100)
    public String getCompanyCode() {
        return this.companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Column(name = "`pr_purchase_by`", nullable = true, length = 100)
    public String getPrPurchaseBy() {
        return this.prPurchaseBy;
    }

    public void setPrPurchaseBy(String prPurchaseBy) {
        this.prPurchaseBy = prPurchaseBy;
    }

    @Column(name = "`pr_creator`", nullable = true, length = 100)
    public String getPrCreator() {
        return this.prCreator;
    }

    public void setPrCreator(String prCreator) {
        this.prCreator = prCreator;
    }

    @Column(name = "`item`", nullable = true, length = 550)
    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "`pr_description`", nullable = true, length = 255)
    public String getPrDescription() {
        return this.prDescription;
    }

    public void setPrDescription(String prDescription) {
        this.prDescription = prDescription;
    }

    @Column(name = "`qty`", nullable = true, scale = 9, precision = 11)
    public Integer getQty() {
        return this.qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Column(name = "`uom`", nullable = true, length = 20)
    public String getUom() {
        return this.uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    @Column(name = "`eta`", nullable = true)
    public Date getEta() {
        return this.eta;
    }

    public void setEta(Date eta) {
        this.eta = eta;
    }

    @Column(name = "`material_group`", nullable = true, length = 20)
    public String getMaterialGroup() {
        return this.materialGroup;
    }

    public void setMaterialGroup(String materialGroup) {
        this.materialGroup = materialGroup;
    }

    @Column(name = "`material_group_desc`", nullable = true, length = 255)
    public String getMaterialGroupDesc() {
        return this.materialGroupDesc;
    }

    public void setMaterialGroupDesc(String materialGroupDesc) {
        this.materialGroupDesc = materialGroupDesc;
    }

    @Column(name = "`department_id`", nullable = true, scale = 0, precision = 10)
    public Integer getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "`rfq_number`", nullable = true, length = 100)
    public String getRfqNumber() {
        return this.rfqNumber;
    }

    public void setRfqNumber(String rfqNumber) {
        this.rfqNumber = rfqNumber;
    }

    @Column(name = "`rfq_status`", nullable = true, length = 100)
    public String getRfqStatus() {
        return this.rfqStatus;
    }

    public void setRfqStatus(String rfqStatus) {
        this.rfqStatus = rfqStatus;
    }

    @Column(name = "`rls_id`", nullable = true, scale = 0, precision = 10)
    public Integer getRlsId() {
        return this.rlsId;
    }

    public void setRlsId(Integer rlsId) {
        this.rlsId = rlsId;
    }

    @Column(name = "`rep_nik`", nullable = true, length = 105)
    public String getRepNik() {
        return this.repNik;
    }

    public void setRepNik(String repNik) {
        this.repNik = repNik;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VGoodsRfq)) return false;
        final VGoodsRfq vgoodsRfq = (VGoodsRfq) o;
        return Objects.equals(getLiId(), vgoodsRfq.getLiId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLiId());
    }
}