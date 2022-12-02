/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_employee;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EmployeeData generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`employee_data`")
public class EmployeeData implements Serializable {

    private Integer id;
    private String employeeCode;
    private String nik;
    private String employeeName;
    private String addressLine1;
    private String addressLine2;
    private String mailId;
    private Integer employmentOu;
    private String employmentOuDesc;
    private String supervisor;
    private String supervisorNik;
    private String supervisorName;
    private String positionCode;
    private String positionDesc;
    private String jobGradeCode;
    private String jobGradeDesc;
    private String orgWorkLocnCode;
    private String orgWorkLocnDesc;
    private String genderCode;
    private Date dateOfJoining;
    private Date dateOfBirth;
    private String maritalStatus;
    private LocalDateTime lastUpdate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`employee_code`", nullable = true, length = 6)
    public String getEmployeeCode() {
        return this.employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    @Column(name = "`nik`", nullable = true, length = 6)
    public String getNik() {
        return this.nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    @Column(name = "`employee_name`", nullable = true, length = 200)
    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Column(name = "`Address_Line_1`", nullable = true, length = 65535)
    public String getAddressLine1() {
        return this.addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @Column(name = "`Address_Line_2`", nullable = true, length = 65535)
    public String getAddressLine2() {
        return this.addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @Column(name = "`MailId`", nullable = true, length = 100)
    public String getMailId() {
        return this.mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    @Column(name = "`employment_ou`", nullable = true, scale = 0, precision = 10)
    public Integer getEmploymentOu() {
        return this.employmentOu;
    }

    public void setEmploymentOu(Integer employmentOu) {
        this.employmentOu = employmentOu;
    }

    @Column(name = "`employment_ou_desc`", nullable = true, length = 100)
    public String getEmploymentOuDesc() {
        return this.employmentOuDesc;
    }

    public void setEmploymentOuDesc(String employmentOuDesc) {
        this.employmentOuDesc = employmentOuDesc;
    }

    @Column(name = "`supervisor`", nullable = true, length = 6)
    public String getSupervisor() {
        return this.supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    @Column(name = "`supervisor_nik`", nullable = true, length = 6)
    public String getSupervisorNik() {
        return this.supervisorNik;
    }

    public void setSupervisorNik(String supervisorNik) {
        this.supervisorNik = supervisorNik;
    }

    @Column(name = "`supervisor_name`", nullable = true, length = 200)
    public String getSupervisorName() {
        return this.supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    @Column(name = "`position_code`", nullable = true, length = 20)
    public String getPositionCode() {
        return this.positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    @Column(name = "`position_desc`", nullable = true, length = 100)
    public String getPositionDesc() {
        return this.positionDesc;
    }

    public void setPositionDesc(String positionDesc) {
        this.positionDesc = positionDesc;
    }

    @Column(name = "`job_grade_code`", nullable = true, length = 10)
    public String getJobGradeCode() {
        return this.jobGradeCode;
    }

    public void setJobGradeCode(String jobGradeCode) {
        this.jobGradeCode = jobGradeCode;
    }

    @Column(name = "`job_grade_desc`", nullable = true, length = 30)
    public String getJobGradeDesc() {
        return this.jobGradeDesc;
    }

    public void setJobGradeDesc(String jobGradeDesc) {
        this.jobGradeDesc = jobGradeDesc;
    }

    @Column(name = "`org_work_locn_code`", nullable = true, length = 10)
    public String getOrgWorkLocnCode() {
        return this.orgWorkLocnCode;
    }

    public void setOrgWorkLocnCode(String orgWorkLocnCode) {
        this.orgWorkLocnCode = orgWorkLocnCode;
    }

    @Column(name = "`org_work_locn_desc`", nullable = true, length = 100)
    public String getOrgWorkLocnDesc() {
        return this.orgWorkLocnDesc;
    }

    public void setOrgWorkLocnDesc(String orgWorkLocnDesc) {
        this.orgWorkLocnDesc = orgWorkLocnDesc;
    }

    @Column(name = "`gender_code`", nullable = true, length = 1)
    public String getGenderCode() {
        return this.genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    @Column(name = "`date_of_joining`", nullable = true)
    public Date getDateOfJoining() {
        return this.dateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    @Column(name = "`date_of_birth`", nullable = true)
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "`marital_status`", nullable = true, length = 30)
    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @Column(name = "`last_update`", nullable = true)
    public LocalDateTime getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeData)) return false;
        final EmployeeData employeeData = (EmployeeData) o;
        return Objects.equals(getId(), employeeData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}