/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VGrTrackingOrdered generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`v_gr_tracking_ordered`")
public class VGrTrackingOrdered implements Serializable {

    private Integer idPoLineItem = 0;
    private String companyId;
    private String creatorName;
    private String departmentName;
    private String createdBy;

    @Id
    @Column(name = "`id_po_line_item`", nullable = false, scale = 0, precision = 10)
    public Integer getIdPoLineItem() {
        return this.idPoLineItem;
    }

    public void setIdPoLineItem(Integer idPoLineItem) {
        this.idPoLineItem = idPoLineItem;
    }

    @Column(name = "`company_id`", nullable = true, length = 50)
    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Column(name = "`creator_name`", nullable = true, length = 100)
    public String getCreatorName() {
        return this.creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Column(name = "`department_name`", nullable = true, length = 100)
    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Column(name = "`created_by`", nullable = true, length = 100)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VGrTrackingOrdered)) return false;
        final VGrTrackingOrdered vgrTrackingOrdered = (VGrTrackingOrdered) o;
        return Objects.equals(getIdPoLineItem(), vgrTrackingOrdered.getIdPoLineItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdPoLineItem());
    }
}