/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TlMsLoc generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_ms_loc`")
public class TlMsLoc implements Serializable {

    private Long id;
    private String locationGroup;
    private String locationDescription;
    private String iclaim;
    private Integer zona;

    @Id
    @Column(name = "`id`", nullable = false, scale = 0, precision = 19)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "`location_group`", nullable = true, length = 255)
    public String getLocationGroup() {
        return this.locationGroup;
    }

    public void setLocationGroup(String locationGroup) {
        this.locationGroup = locationGroup;
    }

    @Column(name = "`location_description`", nullable = true, length = 255)
    public String getLocationDescription() {
        return this.locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    @Column(name = "`iClaim`", nullable = true, length = 255)
    public String getIclaim() {
        return this.iclaim;
    }

    public void setIclaim(String iclaim) {
        this.iclaim = iclaim;
    }

    @Column(name = "`zona`", nullable = true, scale = 0, precision = 10)
    public Integer getZona() {
        return this.zona;
    }

    public void setZona(Integer zona) {
        this.zona = zona;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlMsLoc)) return false;
        final TlMsLoc tlMsLoc = (TlMsLoc) o;
        return Objects.equals(getId(), tlMsLoc.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}