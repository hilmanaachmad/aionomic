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
 * TlMsBaEntity generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_ms_ba_`")
public class TlMsBaEntity implements Serializable {

    private String abCoey;
    private String abBaey;
    private String abBame;
    private String abCty;
    private String abBass;
    private String abRon;
    private String abBch;

    @Id
    @Column(name = "`ab_coey`", nullable = true, length = 255)
    public String getAbCoey() {
        return this.abCoey;
    }

    public void setAbCoey(String abCoey) {
        this.abCoey = abCoey;
    }

    @Column(name = "`ab_baey`", nullable = true, length = 255)
    public String getAbBaey() {
        return this.abBaey;
    }

    public void setAbBaey(String abBaey) {
        this.abBaey = abBaey;
    }

    @Column(name = "`ab_bame`", nullable = true, length = 255)
    public String getAbBame() {
        return this.abBame;
    }

    public void setAbBame(String abBame) {
        this.abBame = abBame;
    }

    @Column(name = "`ab_cty`", nullable = true, length = 255)
    public String getAbCty() {
        return this.abCty;
    }

    public void setAbCty(String abCty) {
        this.abCty = abCty;
    }

    @Column(name = "`ab_bass`", nullable = true, length = 255)
    public String getAbBass() {
        return this.abBass;
    }

    public void setAbBass(String abBass) {
        this.abBass = abBass;
    }

    @Column(name = "`ab_ron`", nullable = true, length = 255)
    public String getAbRon() {
        return this.abRon;
    }

    public void setAbRon(String abRon) {
        this.abRon = abRon;
    }

    @Column(name = "`ab_bch`", nullable = true, length = 255)
    public String getAbBch() {
        return this.abBch;
    }

    public void setAbBch(String abBch) {
        this.abBch = abBch;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlMsBaEntity)) return false;
        final TlMsBaEntity tlMsBaEntity = (TlMsBaEntity) o;
        return Objects.equals(getAbCoey(), tlMsBaEntity.getAbCoey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbCoey());
    }
}