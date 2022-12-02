/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class GetIvendorDataResponse implements Serializable {


    @ColumnAlias("idVendor")
    private Integer idVendor;

    @ColumnAlias("vendorCode")
    private String vendorCode;

    @ColumnAlias("Username")
    private String username;

    @ColumnAlias("Password")
    private String password;

    @ColumnAlias("Title")
    private String title;

    @ColumnAlias("vendorName")
    private String vendorName;

    @ColumnAlias("add1")
    private String add1;

    @ColumnAlias("add2")
    private String add2;

    @ColumnAlias("add3")
    private String add3;

    @ColumnAlias("address")
    private String address;

    @ColumnAlias("businessFieldSector")
    private String businessFieldSector;

    @ColumnAlias("city")
    private String city;

    @ColumnAlias("directorName")
    private String directorName;

    @ColumnAlias("email")
    private String email;

    @ColumnAlias("emailDirector")
    private String emailDirector;

    @ColumnAlias("groupVendor")
    private String groupVendor;

    @ColumnAlias("idDirector")
    private String idDirector;

    @ColumnAlias("kecamatan")
    private String kecamatan;

    @ColumnAlias("kelurahan")
    private String kelurahan;

    @ColumnAlias("location")
    private String location;

    @ColumnAlias("picUrl")
    private String picUrl;

    @ColumnAlias("picName")
    private String picName;

    @ColumnAlias("mobilePhone")
    private String mobilePhone;

    @ColumnAlias("postalCode")
    private String postalCode;

    @ColumnAlias("province")
    private String province;

    @ColumnAlias("region")
    private String region;

    @ColumnAlias("country")
    private String country;

    @ColumnAlias("nib")
    private String nib;

    @ColumnAlias("doi")
    private String doi;

    @ColumnAlias("rtRw")
    private String rtRw;

    @ColumnAlias("telephone")
    private String telephone;

    @ColumnAlias("webAddress")
    private String webAddress;

    @ColumnAlias("companyDomicilie")
    private Integer companyDomicilie;

    @ColumnAlias("puon")
    private String puon;

    @ColumnAlias("license")
    private String license;

    @ColumnAlias("npwpName")
    private String npwpName;

    @ColumnAlias("npwpNumber")
    private String npwpNumber;

    @ColumnAlias("npwpType")
    private String npwpType;

    @ColumnAlias("effectiveSince")
    private LocalDateTime effectiveSince;

    @ColumnAlias("idNpwp")
    private Integer idNpwp;

    @ColumnAlias("fileNameNpwp")
    private String fileNameNpwp;

    @ColumnAlias("fileNpwp")
    private String fileNpwp;

    @ColumnAlias("statusSppkp")
    private Integer statusSppkp;

    @ColumnAlias("sppkpNumber")
    private String sppkpNumber;

    @ColumnAlias("serviceOffice")
    private String serviceOffice;

    @ColumnAlias("serviceOfficeAddress")
    private String serviceOfficeAddress;

    @ColumnAlias("fileSppkp")
    private String fileSppkp;

    @ColumnAlias("catalogueUrl")
    private String catalogueUrl;

    @ColumnAlias("catalogueName")
    private String catalogueName;

    public Integer getIdVendor() {
        return this.idVendor;
    }

    public void setIdVendor(Integer idVendor) {
        this.idVendor = idVendor;
    }

    public String getVendorCode() {
        return this.vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getAdd1() {
        return this.add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return this.add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getAdd3() {
        return this.add3;
    }

    public void setAdd3(String add3) {
        this.add3 = add3;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessFieldSector() {
        return this.businessFieldSector;
    }

    public void setBusinessFieldSector(String businessFieldSector) {
        this.businessFieldSector = businessFieldSector;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDirectorName() {
        return this.directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailDirector() {
        return this.emailDirector;
    }

    public void setEmailDirector(String emailDirector) {
        this.emailDirector = emailDirector;
    }

    public String getGroupVendor() {
        return this.groupVendor;
    }

    public void setGroupVendor(String groupVendor) {
        this.groupVendor = groupVendor;
    }

    public String getIdDirector() {
        return this.idDirector;
    }

    public void setIdDirector(String idDirector) {
        this.idDirector = idDirector;
    }

    public String getKecamatan() {
        return this.kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKelurahan() {
        return this.kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicName() {
        return this.picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNib() {
        return this.nib;
    }

    public void setNib(String nib) {
        this.nib = nib;
    }

    public String getDoi() {
        return this.doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getRtRw() {
        return this.rtRw;
    }

    public void setRtRw(String rtRw) {
        this.rtRw = rtRw;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWebAddress() {
        return this.webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public Integer getCompanyDomicilie() {
        return this.companyDomicilie;
    }

    public void setCompanyDomicilie(Integer companyDomicilie) {
        this.companyDomicilie = companyDomicilie;
    }

    public String getPuon() {
        return this.puon;
    }

    public void setPuon(String puon) {
        this.puon = puon;
    }

    public String getLicense() {
        return this.license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getNpwpName() {
        return this.npwpName;
    }

    public void setNpwpName(String npwpName) {
        this.npwpName = npwpName;
    }

    public String getNpwpNumber() {
        return this.npwpNumber;
    }

    public void setNpwpNumber(String npwpNumber) {
        this.npwpNumber = npwpNumber;
    }

    public String getNpwpType() {
        return this.npwpType;
    }

    public void setNpwpType(String npwpType) {
        this.npwpType = npwpType;
    }

    public LocalDateTime getEffectiveSince() {
        return this.effectiveSince;
    }

    public void setEffectiveSince(LocalDateTime effectiveSince) {
        this.effectiveSince = effectiveSince;
    }

    public Integer getIdNpwp() {
        return this.idNpwp;
    }

    public void setIdNpwp(Integer idNpwp) {
        this.idNpwp = idNpwp;
    }

    public String getFileNameNpwp() {
        return this.fileNameNpwp;
    }

    public void setFileNameNpwp(String fileNameNpwp) {
        this.fileNameNpwp = fileNameNpwp;
    }

    public String getFileNpwp() {
        return this.fileNpwp;
    }

    public void setFileNpwp(String fileNpwp) {
        this.fileNpwp = fileNpwp;
    }

    public Integer getStatusSppkp() {
        return this.statusSppkp;
    }

    public void setStatusSppkp(Integer statusSppkp) {
        this.statusSppkp = statusSppkp;
    }

    public String getSppkpNumber() {
        return this.sppkpNumber;
    }

    public void setSppkpNumber(String sppkpNumber) {
        this.sppkpNumber = sppkpNumber;
    }

    public String getServiceOffice() {
        return this.serviceOffice;
    }

    public void setServiceOffice(String serviceOffice) {
        this.serviceOffice = serviceOffice;
    }

    public String getServiceOfficeAddress() {
        return this.serviceOfficeAddress;
    }

    public void setServiceOfficeAddress(String serviceOfficeAddress) {
        this.serviceOfficeAddress = serviceOfficeAddress;
    }

    public String getFileSppkp() {
        return this.fileSppkp;
    }

    public void setFileSppkp(String fileSppkp) {
        this.fileSppkp = fileSppkp;
    }

    public String getCatalogueUrl() {
        return this.catalogueUrl;
    }

    public void setCatalogueUrl(String catalogueUrl) {
        this.catalogueUrl = catalogueUrl;
    }

    public String getCatalogueName() {
        return this.catalogueName;
    }

    public void setCatalogueName(String catalogueName) {
        this.catalogueName = catalogueName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetIvendorDataResponse)) return false;
        final GetIvendorDataResponse getIvendorDataResponse = (GetIvendorDataResponse) o;
        return Objects.equals(getIdVendor(), getIvendorDataResponse.getIdVendor()) &&
                Objects.equals(getVendorCode(), getIvendorDataResponse.getVendorCode()) &&
                Objects.equals(getUsername(), getIvendorDataResponse.getUsername()) &&
                Objects.equals(getPassword(), getIvendorDataResponse.getPassword()) &&
                Objects.equals(getTitle(), getIvendorDataResponse.getTitle()) &&
                Objects.equals(getVendorName(), getIvendorDataResponse.getVendorName()) &&
                Objects.equals(getAdd1(), getIvendorDataResponse.getAdd1()) &&
                Objects.equals(getAdd2(), getIvendorDataResponse.getAdd2()) &&
                Objects.equals(getAdd3(), getIvendorDataResponse.getAdd3()) &&
                Objects.equals(getAddress(), getIvendorDataResponse.getAddress()) &&
                Objects.equals(getBusinessFieldSector(), getIvendorDataResponse.getBusinessFieldSector()) &&
                Objects.equals(getCity(), getIvendorDataResponse.getCity()) &&
                Objects.equals(getDirectorName(), getIvendorDataResponse.getDirectorName()) &&
                Objects.equals(getEmail(), getIvendorDataResponse.getEmail()) &&
                Objects.equals(getEmailDirector(), getIvendorDataResponse.getEmailDirector()) &&
                Objects.equals(getGroupVendor(), getIvendorDataResponse.getGroupVendor()) &&
                Objects.equals(getIdDirector(), getIvendorDataResponse.getIdDirector()) &&
                Objects.equals(getKecamatan(), getIvendorDataResponse.getKecamatan()) &&
                Objects.equals(getKelurahan(), getIvendorDataResponse.getKelurahan()) &&
                Objects.equals(getLocation(), getIvendorDataResponse.getLocation()) &&
                Objects.equals(getPicUrl(), getIvendorDataResponse.getPicUrl()) &&
                Objects.equals(getPicName(), getIvendorDataResponse.getPicName()) &&
                Objects.equals(getMobilePhone(), getIvendorDataResponse.getMobilePhone()) &&
                Objects.equals(getPostalCode(), getIvendorDataResponse.getPostalCode()) &&
                Objects.equals(getProvince(), getIvendorDataResponse.getProvince()) &&
                Objects.equals(getRegion(), getIvendorDataResponse.getRegion()) &&
                Objects.equals(getCountry(), getIvendorDataResponse.getCountry()) &&
                Objects.equals(getNib(), getIvendorDataResponse.getNib()) &&
                Objects.equals(getDoi(), getIvendorDataResponse.getDoi()) &&
                Objects.equals(getRtRw(), getIvendorDataResponse.getRtRw()) &&
                Objects.equals(getTelephone(), getIvendorDataResponse.getTelephone()) &&
                Objects.equals(getWebAddress(), getIvendorDataResponse.getWebAddress()) &&
                Objects.equals(getCompanyDomicilie(), getIvendorDataResponse.getCompanyDomicilie()) &&
                Objects.equals(getPuon(), getIvendorDataResponse.getPuon()) &&
                Objects.equals(getLicense(), getIvendorDataResponse.getLicense()) &&
                Objects.equals(getNpwpName(), getIvendorDataResponse.getNpwpName()) &&
                Objects.equals(getNpwpNumber(), getIvendorDataResponse.getNpwpNumber()) &&
                Objects.equals(getNpwpType(), getIvendorDataResponse.getNpwpType()) &&
                Objects.equals(getEffectiveSince(), getIvendorDataResponse.getEffectiveSince()) &&
                Objects.equals(getIdNpwp(), getIvendorDataResponse.getIdNpwp()) &&
                Objects.equals(getFileNameNpwp(), getIvendorDataResponse.getFileNameNpwp()) &&
                Objects.equals(getFileNpwp(), getIvendorDataResponse.getFileNpwp()) &&
                Objects.equals(getStatusSppkp(), getIvendorDataResponse.getStatusSppkp()) &&
                Objects.equals(getSppkpNumber(), getIvendorDataResponse.getSppkpNumber()) &&
                Objects.equals(getServiceOffice(), getIvendorDataResponse.getServiceOffice()) &&
                Objects.equals(getServiceOfficeAddress(), getIvendorDataResponse.getServiceOfficeAddress()) &&
                Objects.equals(getFileSppkp(), getIvendorDataResponse.getFileSppkp()) &&
                Objects.equals(getCatalogueUrl(), getIvendorDataResponse.getCatalogueUrl()) &&
                Objects.equals(getCatalogueName(), getIvendorDataResponse.getCatalogueName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdVendor(),
                getVendorCode(),
                getUsername(),
                getPassword(),
                getTitle(),
                getVendorName(),
                getAdd1(),
                getAdd2(),
                getAdd3(),
                getAddress(),
                getBusinessFieldSector(),
                getCity(),
                getDirectorName(),
                getEmail(),
                getEmailDirector(),
                getGroupVendor(),
                getIdDirector(),
                getKecamatan(),
                getKelurahan(),
                getLocation(),
                getPicUrl(),
                getPicName(),
                getMobilePhone(),
                getPostalCode(),
                getProvince(),
                getRegion(),
                getCountry(),
                getNib(),
                getDoi(),
                getRtRw(),
                getTelephone(),
                getWebAddress(),
                getCompanyDomicilie(),
                getPuon(),
                getLicense(),
                getNpwpName(),
                getNpwpNumber(),
                getNpwpType(),
                getEffectiveSince(),
                getIdNpwp(),
                getFileNameNpwp(),
                getFileNpwp(),
                getStatusSppkp(),
                getSppkpNumber(),
                getServiceOffice(),
                getServiceOfficeAddress(),
                getFileSppkp(),
                getCatalogueUrl(),
                getCatalogueName());
    }
}