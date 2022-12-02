/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * TlTrAgli generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_tr_agli`")
@IdClass(TlTrAgliId.class)
public class TlTrAgli implements Serializable {

    private Integer abIdagli;
    private Integer abIdagch;
    private Integer abIdag;
    private LocalDateTime abCute;
    private String abInby;

    @Id
    @Column(name = "`ab_idagli`", nullable = false, scale = 0, precision = 10)
    public Integer getAbIdagli() {
        return this.abIdagli;
    }

    public void setAbIdagli(Integer abIdagli) {
        this.abIdagli = abIdagli;
    }

    @Id
    @Column(name = "`ab_idagch`", nullable = true, scale = 0, precision = 10)
    public Integer getAbIdagch() {
        return this.abIdagch;
    }

    public void setAbIdagch(Integer abIdagch) {
        this.abIdagch = abIdagch;
    }

    @Id
    @Column(name = "`ab_idag`", nullable = true, scale = 0, precision = 10)
    public Integer getAbIdag() {
        return this.abIdag;
    }

    public void setAbIdag(Integer abIdag) {
        this.abIdag = abIdag;
    }

    @Column(name = "`ab_cute`", nullable = true)
    public LocalDateTime getAbCute() {
        return this.abCute;
    }

    public void setAbCute(LocalDateTime abCute) {
        this.abCute = abCute;
    }

    @Column(name = "`ab_inby`", nullable = true, length = 50)
    public String getAbInby() {
        return this.abInby;
    }

    public void setAbInby(String abInby) {
        this.abInby = abInby;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlTrAgli)) return false;
        final TlTrAgli tlTrAgli = (TlTrAgli) o;
        return Objects.equals(getAbIdagli(), tlTrAgli.getAbIdagli()) &&
                Objects.equals(getAbIdagch(), tlTrAgli.getAbIdagch()) &&
                Objects.equals(getAbIdag(), tlTrAgli.getAbIdag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbIdagli(),
                getAbIdagch(),
                getAbIdag());
    }
}