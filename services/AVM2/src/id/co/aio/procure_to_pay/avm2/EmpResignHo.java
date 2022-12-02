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
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * EmpResignHo generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`emp_resign_ho`")
@IdClass(EmpResignHoId.class)
public class EmpResignHo implements Serializable {

    private String nik;
    private String sapcode;
    private String name;

    @Id
    @Column(name = "`NIK`", nullable = true, length = 255)
    public String getNik() {
        return this.nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    @Id
    @Column(name = "`SAPCode`", nullable = true, length = 255)
    public String getSapcode() {
        return this.sapcode;
    }

    public void setSapcode(String sapcode) {
        this.sapcode = sapcode;
    }

    @Column(name = "`Name`", nullable = true, length = 255)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmpResignHo)) return false;
        final EmpResignHo empResignHo = (EmpResignHo) o;
        return Objects.equals(getNik(), empResignHo.getNik()) &&
                Objects.equals(getSapcode(), empResignHo.getSapcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNik(),
                getSapcode());
    }
}