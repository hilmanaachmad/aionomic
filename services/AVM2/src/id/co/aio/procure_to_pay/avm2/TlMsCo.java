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
 * TlMsCo generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_ms_co`")
public class TlMsCo implements Serializable {

    private String abCoey;
    private String abCome;

    @Id
    @Column(name = "`ab_coey`", nullable = true, length = 255)
    public String getAbCoey() {
        return this.abCoey;
    }

    public void setAbCoey(String abCoey) {
        this.abCoey = abCoey;
    }

    @Column(name = "`ab_come`", nullable = true, length = 255)
    public String getAbCome() {
        return this.abCome;
    }

    public void setAbCome(String abCome) {
        this.abCome = abCome;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlMsCo)) return false;
        final TlMsCo tlMsCo = (TlMsCo) o;
        return Objects.equals(getAbCoey(), tlMsCo.getAbCoey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbCoey());
    }
}