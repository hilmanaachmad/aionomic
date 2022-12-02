/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.sap_master.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class GetTopResponse implements Serializable {


    @ColumnAlias("payment_term_code")
    private String paymentTermCode;

    @ColumnAlias("top_description")
    private String topDescription;

    @ColumnAlias("number_of_days")
    private Short numberOfDays;

    @ColumnAlias("TEXT1")
    private String text1;

    public String getPaymentTermCode() {
        return this.paymentTermCode;
    }

    public void setPaymentTermCode(String paymentTermCode) {
        this.paymentTermCode = paymentTermCode;
    }

    public String getTopDescription() {
        return this.topDescription;
    }

    public void setTopDescription(String topDescription) {
        this.topDescription = topDescription;
    }

    public Short getNumberOfDays() {
        return this.numberOfDays;
    }

    public void setNumberOfDays(Short numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getText1() {
        return this.text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetTopResponse)) return false;
        final GetTopResponse getTopResponse = (GetTopResponse) o;
        return Objects.equals(getPaymentTermCode(), getTopResponse.getPaymentTermCode()) &&
                Objects.equals(getTopDescription(), getTopResponse.getTopDescription()) &&
                Objects.equals(getNumberOfDays(), getTopResponse.getNumberOfDays()) &&
                Objects.equals(getText1(), getTopResponse.getText1());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPaymentTermCode(),
                getTopDescription(),
                getNumberOfDays(),
                getText1());
    }
}