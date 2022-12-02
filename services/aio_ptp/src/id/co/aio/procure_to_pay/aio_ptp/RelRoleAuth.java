/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * RelRoleAuth generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`rel_role_auth`")
@IdClass(RelRoleAuthId.class)
public class RelRoleAuth implements Serializable {

    private Integer authId;
    private Integer roleId;
    private TblMRole tblMrole;
    private TblMAuthorization tblMauthorization;

    @Id
    @Column(name = "`auth_id`", nullable = false, scale = 0, precision = 10)
    public Integer getAuthId() {
        return this.authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    @Id
    @Column(name = "`role_id`", nullable = false, scale = 0, precision = 10)
    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`role_id`", referencedColumnName = "`role_id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`rel_role_auth_ibfk_1`"))
    @Fetch(FetchMode.JOIN)
    public TblMRole getTblMrole() {
        return this.tblMrole;
    }

    public void setTblMrole(TblMRole tblMrole) {
        if(tblMrole != null) {
            this.roleId = tblMrole.getRoleId();
        }

        this.tblMrole = tblMrole;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`auth_id`", referencedColumnName = "`auth_id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`rel_role_auth_ibfk_2`"))
    @Fetch(FetchMode.JOIN)
    public TblMAuthorization getTblMauthorization() {
        return this.tblMauthorization;
    }

    public void setTblMauthorization(TblMAuthorization tblMauthorization) {
        if(tblMauthorization != null) {
            this.authId = tblMauthorization.getAuthId();
        }

        this.tblMauthorization = tblMauthorization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelRoleAuth)) return false;
        final RelRoleAuth relRoleAuth = (RelRoleAuth) o;
        return Objects.equals(getAuthId(), relRoleAuth.getAuthId()) &&
                Objects.equals(getRoleId(), relRoleAuth.getRoleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthId(),
                getRoleId());
    }
}