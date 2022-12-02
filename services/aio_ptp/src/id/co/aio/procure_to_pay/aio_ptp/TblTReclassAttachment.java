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
 * TblTReclassAttachment generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tbl_t_reclass_attachment`")
public class TblTReclassAttachment implements Serializable {

    private Integer raId;
    private Integer bdRcId;
    private String raAttachment;
    private String raPathFile;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ra_id`", nullable = false, scale = 0, precision = 10)
    public Integer getRaId() {
        return this.raId;
    }

    public void setRaId(Integer raId) {
        this.raId = raId;
    }

    @Column(name = "`bd_rc_id`", nullable = true, scale = 0, precision = 10)
    public Integer getBdRcId() {
        return this.bdRcId;
    }

    public void setBdRcId(Integer bdRcId) {
        this.bdRcId = bdRcId;
    }

    @Column(name = "`ra_attachment`", nullable = true, length = 255)
    public String getRaAttachment() {
        return this.raAttachment;
    }

    public void setRaAttachment(String raAttachment) {
        this.raAttachment = raAttachment;
    }

    @Column(name = "`ra_path_file`", nullable = true, length = 255)
    public String getRaPathFile() {
        return this.raPathFile;
    }

    public void setRaPathFile(String raPathFile) {
        this.raPathFile = raPathFile;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TblTReclassAttachment)) return false;
        final TblTReclassAttachment tblTreclassAttachment = (TblTReclassAttachment) o;
        return Objects.equals(getRaId(), tblTreclassAttachment.getRaId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRaId());
    }
}