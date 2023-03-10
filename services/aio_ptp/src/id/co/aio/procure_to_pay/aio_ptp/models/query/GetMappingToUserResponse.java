/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class GetMappingToUserResponse implements Serializable {


    @ColumnAlias("udep_id")
    private Integer udepId;

    @ColumnAlias("company_id")
    private String companyId;

    @ColumnAlias("company")
    private String company;

    @ColumnAlias("department_id")
    private String departmentId;

    @ColumnAlias("departrment")
    private String departrment;

    @ColumnAlias("udep_created_at")
    private LocalDateTime udepCreatedAt;

    @ColumnAlias("udep_created_by")
    private String udepCreatedBy;

    @ColumnAlias("jumlah_user")
    private Long jumlahUser;

    public Integer getUdepId() {
        return this.udepId;
    }

    public void setUdepId(Integer udepId) {
        this.udepId = udepId;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartrment() {
        return this.departrment;
    }

    public void setDepartrment(String departrment) {
        this.departrment = departrment;
    }

    public LocalDateTime getUdepCreatedAt() {
        return this.udepCreatedAt;
    }

    public void setUdepCreatedAt(LocalDateTime udepCreatedAt) {
        this.udepCreatedAt = udepCreatedAt;
    }

    public String getUdepCreatedBy() {
        return this.udepCreatedBy;
    }

    public void setUdepCreatedBy(String udepCreatedBy) {
        this.udepCreatedBy = udepCreatedBy;
    }

    public Long getJumlahUser() {
        return this.jumlahUser;
    }

    public void setJumlahUser(Long jumlahUser) {
        this.jumlahUser = jumlahUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetMappingToUserResponse)) return false;
        final GetMappingToUserResponse getMappingToUserResponse = (GetMappingToUserResponse) o;
        return Objects.equals(getUdepId(), getMappingToUserResponse.getUdepId()) &&
                Objects.equals(getCompanyId(), getMappingToUserResponse.getCompanyId()) &&
                Objects.equals(getCompany(), getMappingToUserResponse.getCompany()) &&
                Objects.equals(getDepartmentId(), getMappingToUserResponse.getDepartmentId()) &&
                Objects.equals(getDepartrment(), getMappingToUserResponse.getDepartrment()) &&
                Objects.equals(getUdepCreatedAt(), getMappingToUserResponse.getUdepCreatedAt()) &&
                Objects.equals(getUdepCreatedBy(), getMappingToUserResponse.getUdepCreatedBy()) &&
                Objects.equals(getJumlahUser(), getMappingToUserResponse.getJumlahUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUdepId(),
                getCompanyId(),
                getCompany(),
                getDepartmentId(),
                getDepartrment(),
                getUdepCreatedAt(),
                getUdepCreatedBy(),
                getJumlahUser());
    }
}