/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * TblTPr generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tbl_t_pr`")
public class TblTPr implements Serializable {

    private Integer prId;
    private Integer cid;
    private Integer departmentId;
    private Integer pbId;
    private Integer accId;
    private String prRef;
    private String prCreatedBy = "SYSTEM";
    private LocalDateTime prCreatedAt;
    private String prModifiedBy = "SYSTEM";
    private LocalDateTime prModifiedAt;
    private String prStatus = "active";
    private String prRepUserId;
    private String prRepUserName;
    private String prPurchaseBy;
    private String prPaymentMethod;
    private String prProject;
    private Date prDate;
    private String prPriority;
    private String prAttchData;
    private String prCreatedName;
    private String prBudgetProposalOption;
    private String prRepUserNik;
    private TblMAccountType tblMaccountType;
    private TblTProposalBudget tblTproposalBudget;
    private TblMCompany tblMcompany;
    private TblMDepartment tblMdepartment;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`pr_id`", nullable = false, scale = 0, precision = 10)
    public Integer getPrId() {
        return this.prId;
    }

    public void setPrId(Integer prId) {
        this.prId = prId;
    }

    @Column(name = "`c_id`", nullable = true, scale = 0, precision = 10)
    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    @Column(name = "`department_id`", nullable = true, scale = 0, precision = 10)
    public Integer getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "`pb_id`", nullable = true, scale = 0, precision = 10)
    public Integer getPbId() {
        return this.pbId;
    }

    public void setPbId(Integer pbId) {
        this.pbId = pbId;
    }

    @Column(name = "`acc_id`", nullable = true, scale = 0, precision = 10)
    public Integer getAccId() {
        return this.accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    @Column(name = "`pr_ref`", nullable = true, length = 100)
    public String getPrRef() {
        return this.prRef;
    }

    public void setPrRef(String prRef) {
        this.prRef = prRef;
    }

    @Column(name = "`pr_created_by`", nullable = true, length = 100)
    public String getPrCreatedBy() {
        return this.prCreatedBy;
    }

    public void setPrCreatedBy(String prCreatedBy) {
        this.prCreatedBy = prCreatedBy;
    }

    @Column(name = "`pr_created_at`", nullable = true)
    public LocalDateTime getPrCreatedAt() {
        return this.prCreatedAt;
    }

    public void setPrCreatedAt(LocalDateTime prCreatedAt) {
        this.prCreatedAt = prCreatedAt;
    }

    @Column(name = "`pr_modified_by`", nullable = true, length = 100)
    public String getPrModifiedBy() {
        return this.prModifiedBy;
    }

    public void setPrModifiedBy(String prModifiedBy) {
        this.prModifiedBy = prModifiedBy;
    }

    @Column(name = "`pr_modified_at`", nullable = true)
    public LocalDateTime getPrModifiedAt() {
        return this.prModifiedAt;
    }

    public void setPrModifiedAt(LocalDateTime prModifiedAt) {
        this.prModifiedAt = prModifiedAt;
    }

    @Column(name = "`pr_status`", nullable = true, length = 20)
    public String getPrStatus() {
        return this.prStatus;
    }

    public void setPrStatus(String prStatus) {
        this.prStatus = prStatus;
    }

    @Column(name = "`pr_rep_user_id`", nullable = true, length = 100)
    public String getPrRepUserId() {
        return this.prRepUserId;
    }

    public void setPrRepUserId(String prRepUserId) {
        this.prRepUserId = prRepUserId;
    }

    @Column(name = "`pr_rep_user_name`", nullable = true, length = 100)
    public String getPrRepUserName() {
        return this.prRepUserName;
    }

    public void setPrRepUserName(String prRepUserName) {
        this.prRepUserName = prRepUserName;
    }

    @Column(name = "`pr_purchase_by`", nullable = true, length = 100)
    public String getPrPurchaseBy() {
        return this.prPurchaseBy;
    }

    public void setPrPurchaseBy(String prPurchaseBy) {
        this.prPurchaseBy = prPurchaseBy;
    }

    @Column(name = "`pr_payment_method`", nullable = true, length = 100)
    public String getPrPaymentMethod() {
        return this.prPaymentMethod;
    }

    public void setPrPaymentMethod(String prPaymentMethod) {
        this.prPaymentMethod = prPaymentMethod;
    }

    @Column(name = "`pr_project`", nullable = true, length = 255)
    public String getPrProject() {
        return this.prProject;
    }

    public void setPrProject(String prProject) {
        this.prProject = prProject;
    }

    @Column(name = "`pr_date`", nullable = true)
    public Date getPrDate() {
        return this.prDate;
    }

    public void setPrDate(Date prDate) {
        this.prDate = prDate;
    }

    @Column(name = "`pr_priority`", nullable = true, length = 100)
    public String getPrPriority() {
        return this.prPriority;
    }

    public void setPrPriority(String prPriority) {
        this.prPriority = prPriority;
    }

    @Column(name = "`pr_attch_data`", nullable = true, length = 2147483647)
    public String getPrAttchData() {
        return this.prAttchData;
    }

    public void setPrAttchData(String prAttchData) {
        this.prAttchData = prAttchData;
    }

    @Column(name = "`pr_created_name`", nullable = true, length = 255)
    public String getPrCreatedName() {
        return this.prCreatedName;
    }

    public void setPrCreatedName(String prCreatedName) {
        this.prCreatedName = prCreatedName;
    }

    @Column(name = "`pr_budget_proposal_option`", nullable = true, length = 255)
    public String getPrBudgetProposalOption() {
        return this.prBudgetProposalOption;
    }

    public void setPrBudgetProposalOption(String prBudgetProposalOption) {
        this.prBudgetProposalOption = prBudgetProposalOption;
    }

    @Column(name = "`pr_rep_user_nik`", nullable = true, length = 100)
    public String getPrRepUserNik() {
        return this.prRepUserNik;
    }

    public void setPrRepUserNik(String prRepUserNik) {
        this.prRepUserNik = prRepUserNik;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`acc_id`", referencedColumnName = "`acc_id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`tbl_t_pr_ibfk_1`"))
    @Fetch(FetchMode.JOIN)
    public TblMAccountType getTblMaccountType() {
        return this.tblMaccountType;
    }

    public void setTblMaccountType(TblMAccountType tblMaccountType) {
        if(tblMaccountType != null) {
            this.accId = tblMaccountType.getAccId();
        }

        this.tblMaccountType = tblMaccountType;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`pb_id`", referencedColumnName = "`pb_id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`tbl_t_pr_ibfk_2`"))
    @Fetch(FetchMode.JOIN)
    public TblTProposalBudget getTblTproposalBudget() {
        return this.tblTproposalBudget;
    }

    public void setTblTproposalBudget(TblTProposalBudget tblTproposalBudget) {
        if(tblTproposalBudget != null) {
            this.pbId = tblTproposalBudget.getPbId();
        }

        this.tblTproposalBudget = tblTproposalBudget;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`c_id`", referencedColumnName = "`c_id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`tbl_t_pr_ibfk_3`"))
    @Fetch(FetchMode.JOIN)
    public TblMCompany getTblMcompany() {
        return this.tblMcompany;
    }

    public void setTblMcompany(TblMCompany tblMcompany) {
        if(tblMcompany != null) {
            this.cid = tblMcompany.getCid();
        }

        this.tblMcompany = tblMcompany;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`department_id`", referencedColumnName = "`department_id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`tbl_t_pr_ibfk_4`"))
    @Fetch(FetchMode.JOIN)
    public TblMDepartment getTblMdepartment() {
        return this.tblMdepartment;
    }

    public void setTblMdepartment(TblMDepartment tblMdepartment) {
        if(tblMdepartment != null) {
            this.departmentId = tblMdepartment.getDepartmentId();
        }

        this.tblMdepartment = tblMdepartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TblTPr)) return false;
        final TblTPr tblTpr = (TblTPr) o;
        return Objects.equals(getPrId(), tblTpr.getPrId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrId());
    }
}