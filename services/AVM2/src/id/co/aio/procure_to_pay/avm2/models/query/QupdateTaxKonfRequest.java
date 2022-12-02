/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QupdateTaxKonfRequest implements Serializable {


    @JsonProperty("taxId")
    @NotNull
    private String taxId;

    @JsonProperty("typeNpwp")
    @NotNull
    private String typeNpwp;

    @JsonProperty("nameNpwp")
    @NotNull
    private String nameNpwp;

    @JsonProperty("addressNpwp")
    @NotNull
    private String addressNpwp;

    @JsonProperty("effectiveNpwp")
    @NotNull
    private String effectiveNpwp;

    @JsonProperty("taxConfirmationNumber")
    @NotNull
    private String taxConfirmationNumber;

    @JsonProperty("taxServiceOffice")
    @NotNull
    private String taxServiceOffice;

    @JsonProperty("addressTaxService")
    @NotNull
    private String addressTaxService;

    @JsonProperty("taxCertificateNumber")
    @NotNull
    private String taxCertificateNumber;

    @JsonProperty("typeCertificateNpwp")
    @NotNull
    private String typeCertificateNpwp;

    @JsonProperty("validCertificateFrom")
    @NotNull
    private String validCertificateFrom;

    @JsonProperty("validCertificateTo")
    @NotNull
    private String validCertificateTo;

    @JsonProperty("npwpUpload")
    @NotNull
    private String npwpUpload;

    @JsonProperty("nameNpwpUpload")
    @NotNull
    private String nameNpwpUpload;

    @JsonProperty("sppkpUpload")
    @NotNull
    private String sppkpUpload;

    @JsonProperty("nameSppkpUpload")
    @NotNull
    private String nameSppkpUpload;

    @JsonProperty("taxCertiUpload")
    @NotNull
    private String taxCertiUpload;

    @JsonProperty("nameCertiUpload")
    @NotNull
    private String nameCertiUpload;

    @JsonProperty("id")
    @NotNull
    private String id;

    public String getTaxId() {
        return this.taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getTypeNpwp() {
        return this.typeNpwp;
    }

    public void setTypeNpwp(String typeNpwp) {
        this.typeNpwp = typeNpwp;
    }

    public String getNameNpwp() {
        return this.nameNpwp;
    }

    public void setNameNpwp(String nameNpwp) {
        this.nameNpwp = nameNpwp;
    }

    public String getAddressNpwp() {
        return this.addressNpwp;
    }

    public void setAddressNpwp(String addressNpwp) {
        this.addressNpwp = addressNpwp;
    }

    public String getEffectiveNpwp() {
        return this.effectiveNpwp;
    }

    public void setEffectiveNpwp(String effectiveNpwp) {
        this.effectiveNpwp = effectiveNpwp;
    }

    public String getTaxConfirmationNumber() {
        return this.taxConfirmationNumber;
    }

    public void setTaxConfirmationNumber(String taxConfirmationNumber) {
        this.taxConfirmationNumber = taxConfirmationNumber;
    }

    public String getTaxServiceOffice() {
        return this.taxServiceOffice;
    }

    public void setTaxServiceOffice(String taxServiceOffice) {
        this.taxServiceOffice = taxServiceOffice;
    }

    public String getAddressTaxService() {
        return this.addressTaxService;
    }

    public void setAddressTaxService(String addressTaxService) {
        this.addressTaxService = addressTaxService;
    }

    public String getTaxCertificateNumber() {
        return this.taxCertificateNumber;
    }

    public void setTaxCertificateNumber(String taxCertificateNumber) {
        this.taxCertificateNumber = taxCertificateNumber;
    }

    public String getTypeCertificateNpwp() {
        return this.typeCertificateNpwp;
    }

    public void setTypeCertificateNpwp(String typeCertificateNpwp) {
        this.typeCertificateNpwp = typeCertificateNpwp;
    }

    public String getValidCertificateFrom() {
        return this.validCertificateFrom;
    }

    public void setValidCertificateFrom(String validCertificateFrom) {
        this.validCertificateFrom = validCertificateFrom;
    }

    public String getValidCertificateTo() {
        return this.validCertificateTo;
    }

    public void setValidCertificateTo(String validCertificateTo) {
        this.validCertificateTo = validCertificateTo;
    }

    public String getNpwpUpload() {
        return this.npwpUpload;
    }

    public void setNpwpUpload(String npwpUpload) {
        this.npwpUpload = npwpUpload;
    }

    public String getNameNpwpUpload() {
        return this.nameNpwpUpload;
    }

    public void setNameNpwpUpload(String nameNpwpUpload) {
        this.nameNpwpUpload = nameNpwpUpload;
    }

    public String getSppkpUpload() {
        return this.sppkpUpload;
    }

    public void setSppkpUpload(String sppkpUpload) {
        this.sppkpUpload = sppkpUpload;
    }

    public String getNameSppkpUpload() {
        return this.nameSppkpUpload;
    }

    public void setNameSppkpUpload(String nameSppkpUpload) {
        this.nameSppkpUpload = nameSppkpUpload;
    }

    public String getTaxCertiUpload() {
        return this.taxCertiUpload;
    }

    public void setTaxCertiUpload(String taxCertiUpload) {
        this.taxCertiUpload = taxCertiUpload;
    }

    public String getNameCertiUpload() {
        return this.nameCertiUpload;
    }

    public void setNameCertiUpload(String nameCertiUpload) {
        this.nameCertiUpload = nameCertiUpload;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QupdateTaxKonfRequest)) return false;
        final QupdateTaxKonfRequest qupdateTaxKonfRequest = (QupdateTaxKonfRequest) o;
        return Objects.equals(getTaxId(), qupdateTaxKonfRequest.getTaxId()) &&
                Objects.equals(getTypeNpwp(), qupdateTaxKonfRequest.getTypeNpwp()) &&
                Objects.equals(getNameNpwp(), qupdateTaxKonfRequest.getNameNpwp()) &&
                Objects.equals(getAddressNpwp(), qupdateTaxKonfRequest.getAddressNpwp()) &&
                Objects.equals(getEffectiveNpwp(), qupdateTaxKonfRequest.getEffectiveNpwp()) &&
                Objects.equals(getTaxConfirmationNumber(), qupdateTaxKonfRequest.getTaxConfirmationNumber()) &&
                Objects.equals(getTaxServiceOffice(), qupdateTaxKonfRequest.getTaxServiceOffice()) &&
                Objects.equals(getAddressTaxService(), qupdateTaxKonfRequest.getAddressTaxService()) &&
                Objects.equals(getTaxCertificateNumber(), qupdateTaxKonfRequest.getTaxCertificateNumber()) &&
                Objects.equals(getTypeCertificateNpwp(), qupdateTaxKonfRequest.getTypeCertificateNpwp()) &&
                Objects.equals(getValidCertificateFrom(), qupdateTaxKonfRequest.getValidCertificateFrom()) &&
                Objects.equals(getValidCertificateTo(), qupdateTaxKonfRequest.getValidCertificateTo()) &&
                Objects.equals(getNpwpUpload(), qupdateTaxKonfRequest.getNpwpUpload()) &&
                Objects.equals(getNameNpwpUpload(), qupdateTaxKonfRequest.getNameNpwpUpload()) &&
                Objects.equals(getSppkpUpload(), qupdateTaxKonfRequest.getSppkpUpload()) &&
                Objects.equals(getNameSppkpUpload(), qupdateTaxKonfRequest.getNameSppkpUpload()) &&
                Objects.equals(getTaxCertiUpload(), qupdateTaxKonfRequest.getTaxCertiUpload()) &&
                Objects.equals(getNameCertiUpload(), qupdateTaxKonfRequest.getNameCertiUpload()) &&
                Objects.equals(getId(), qupdateTaxKonfRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaxId(),
                getTypeNpwp(),
                getNameNpwp(),
                getAddressNpwp(),
                getEffectiveNpwp(),
                getTaxConfirmationNumber(),
                getTaxServiceOffice(),
                getAddressTaxService(),
                getTaxCertificateNumber(),
                getTypeCertificateNpwp(),
                getValidCertificateFrom(),
                getValidCertificateTo(),
                getNpwpUpload(),
                getNameNpwpUpload(),
                getSppkpUpload(),
                getNameSppkpUpload(),
                getTaxCertiUpload(),
                getNameCertiUpload(),
                getId());
    }
}