/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VGrQuantityDelivery generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`v_gr_quantity_delivery`")
public class VGrQuantityDelivery implements Serializable {

    private Integer idPoHeader;
    private BigDecimal totalPoQty;
    private BigDecimal totalConfirmQty;

    @Id
    @Column(name = "`id_po_header`", nullable = true, scale = 0, precision = 10)
    public Integer getIdPoHeader() {
        return this.idPoHeader;
    }

    public void setIdPoHeader(Integer idPoHeader) {
        this.idPoHeader = idPoHeader;
    }

    @Column(name = "`total_po_qty`", nullable = true, scale = 9, precision = 17)
    public BigDecimal getTotalPoQty() {
        return this.totalPoQty;
    }

    public void setTotalPoQty(BigDecimal totalPoQty) {
        this.totalPoQty = totalPoQty;
    }

    @Column(name = "`total_confirm_qty`", nullable = true, scale = 4, precision = 21)
    public BigDecimal getTotalConfirmQty() {
        return this.totalConfirmQty;
    }

    public void setTotalConfirmQty(BigDecimal totalConfirmQty) {
        this.totalConfirmQty = totalConfirmQty;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VGrQuantityDelivery)) return false;
        final VGrQuantityDelivery vgrQuantityDelivery = (VGrQuantityDelivery) o;
        return Objects.equals(getIdPoHeader(), vgrQuantityDelivery.getIdPoHeader());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdPoHeader());
    }
}