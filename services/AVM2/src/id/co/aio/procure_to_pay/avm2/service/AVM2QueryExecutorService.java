/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.export.ExportOptions;

import id.co.aio.procure_to_pay.avm2.models.query.*;

public interface AVM2QueryExecutorService {

    Integer executeQUpdateStatusForgot(QupdateStatusForgotRequest qupdateStatusForgotRequest);

    Page<QgetForgotUserResponse> executeQGetForgotUser(Pageable pageable);

    void exportQGetForgotUser(ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<QgetDataBankResponse> executeQGetDataBank(String userName, Pageable pageable);

    void exportQGetDataBank(String userName, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQInsertCertidom(QinsertCertidomRequest qinsertCertidomRequest);

    Integer executeQInsertTaxOverseasTemp(QinsertTaxOverseasTempRequest qinsertTaxOverseasTempRequest);

    Page<GetVendroDataByNameResponse> executeGetVendroDataByName(String vendorName, Pageable pageable);

    void exportGetVendroDataByName(String vendorName, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<QgetTaxTypeResponse> executeQGetTaxType(Pageable pageable);

    void exportQGetTaxType(ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<QcheckUserVendorResponse> executeQCheckUserVendor(String username, String password, Pageable pageable);

    void exportQCheckUserVendor(String username, String password, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<GetNpwpVendorResponse> executeGetNpwpVendor(String npwp, Pageable pageable);

    void exportGetNpwpVendor(String npwp, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<GetVendorDataByNamesResponse> executeGetVendorDataByNames(String vendorName, Pageable pageable);

    void exportGetVendorDataByNames(String vendorName, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<GetVendorDataResponse> executeGetVendorData(Pageable pageable);

    void exportGetVendorData(ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQUpdateExemptionFile(QupdateExemptionFileRequest qupdateExemptionFileRequest);

    Integer executeQInsertSupDoc(QinsertSupDocRequest qinsertSupDocRequest);

    Integer executeQUpdateVendorOverseas(QupdateVendorOverseasRequest qupdateVendorOverseasRequest);

    Page<QgetBankInfoResponse> executeQGetBankInfo(Pageable pageable);

    void exportQGetBankInfo(ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQInsertTaxTemp(QinsertTaxTempRequest qinsertTaxTempRequest);

    Page<GetVendorDataEmailResponse> executeGetVendorDataEmail(String email, String password, Pageable pageable);

    void exportGetVendorDataEmail(String email, String password, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQInsertCertificate(QinsertCertificateRequest qinsertCertificateRequest);

    Integer executeQInsertVendorOverseas(QinsertVendorOverseasRequest qinsertVendorOverseasRequest);

    Page<QmaxVendorResponse> executeQMaxVendor(Pageable pageable);

    void exportQMaxVendor(ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<QcheckUserEmailVenResponse> executeQCheckUserEmailVen(String username, String email, Pageable pageable);

    void exportQCheckUserEmailVen(String username, String email, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQRfusDelPIC(QrfusDelPicRequest qrfusDelPicRequest);

    Page<GetVendorUmeResponse> executeGetVendorUme(String username, Pageable pageable);

    void exportGetVendorUme(String username, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQInsertNpwp(QinsertNpwpRequest qinsertNpwpRequest);

    Integer executeQInsertPicTemp(QinsertPicTempRequest qinsertPicTempRequest);

    Integer executeQUpdateTaxKonf(QupdateTaxKonfRequest qupdateTaxKonfRequest);

    Page<QgetBankInfoKonfResponse> executeQGetBankInfoKonf(String sveCode, Pageable pageable);

    void exportQGetBankInfoKonf(String sveCode, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQDeleteTaxKonf(String id);

    Integer executeQUpdateSppkp(QupdateSppkpRequest qupdateSppkpRequest);

    Integer executeQUpdateNpwpFile(QupdateNpwpFileRequest qupdateNpwpFileRequest);

    Page<QgetTaxKonfResponse> executeQGetTaxKonf(String sveCode, Pageable pageable);

    void exportQGetTaxKonf(String sveCode, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQChangePasswordVen(QchangePasswordVenRequest qchangePasswordVenRequest);

    Integer executeQInsertVendor(QinsertVendorRequest qinsertVendorRequest);

    Integer executeQInsertForgotUser(QinsertForgotUserRequest qinsertForgotUserRequest);

    Integer executeQInsertTax(QinsertTaxRequest qinsertTaxRequest);

    Integer executeQInsertBank(QinsertBankRequest qinsertBankRequest);

    Page<GetTaxOverseasResponse> executeGetTaxOverseas(String userName, Pageable pageable);

    void exportGetTaxOverseas(String userName, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQUpdatePic(QupdatePicRequest qupdatePicRequest);

    Integer executeQRfusUpdateBank(QrfusUpdateBankRequest qrfusUpdateBankRequest);

    Page<GetIvendorDataOverseasResponse> executeGetIvendorDataOverseas(Pageable pageable);

    void exportGetIvendorDataOverseas(ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<QgetRegionResponse> executeQGetRegion(String country, Pageable pageable);

    void exportQGetRegion(String country, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQUpdateTaxKonfOverseeas(QupdateTaxKonfOverseeasRequest qupdateTaxKonfOverseeasRequest);

    Page<QgetVvendorResponse> executeQGetVVendor(String vcode, String vname, Pageable pageable);

    void exportQGetVVendor(String vcode, String vname, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<QgetBankNameByCountryResponse> executeQGetBankNameByCountry(String countryCode, Pageable pageable);

    void exportQGetBankNameByCountry(String countryCode, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeSvUpdateUserTax(SvUpdateUserTaxRequest svUpdateUserTaxRequest);

    Integer executeQUpdateUserPIC(QupdateUserPicRequest qupdateUserPicRequest);

    Page<QgetDocBankResponse> executeQGetDocBank(String vendorCode, Pageable pageable);

    void exportQGetDocBank(String vendorCode, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQInsertPIC(QinsertPicRequest qinsertPicRequest);

    Integer executeQUpdateDataBank(QupdateDataBankRequest qupdateDataBankRequest);

    Page<GetVendorDataForgotUserResponse> executeGetVendorDataForgotUser(String npwpNumber, String idDirector, Pageable pageable);

    void exportGetVendorDataForgotUser(String npwpNumber, String idDirector, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQInsertDocBank(QinsertDocBankRequest qinsertDocBankRequest);

    Integer executeUpdateIVendorData(UpdateIvendorDataRequest updateIvendorDataRequest);

    Page<GetPicResponse> executeGetPic(String userName, Pageable pageable);

    void exportGetPic(String userName, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQUpdateDocBank(QupdateDocBankRequest qupdateDocBankRequest);

    Integer executeQUpdatePassword(QupdatePasswordRequest qupdatePasswordRequest);

    Integer executeQUpdateVendor(QupdateVendorRequest qupdateVendorRequest);

    Integer executeQForgotPassVen(QforgotPassVenRequest qforgotPassVenRequest);

    Integer executeQInsertHistory(QinsertHistoryRequest qinsertHistoryRequest);

    Integer executeQUpdateSupDoc(QupdateSupDocRequest qupdateSupDocRequest);

    Page<GetEmailListResponse> executeGetEmailList(String puon, Pageable pageable);

    void exportGetEmailList(String puon, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<GrtrackingVendorInfoResponse> executeGRTrackingVendorInfo(String vendorcode, Pageable pageable);

    void exportGRTrackingVendorInfo(String vendorcode, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQInsertTaxOverseas(QinsertTaxOverseasRequest qinsertTaxOverseasRequest);

    Page<QgetCurrencyDataResponse> executeQGetCurrencyData(Pageable pageable);

    void exportQGetCurrencyData(ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQInsertSppkp(QinsertSppkpRequest qinsertSppkpRequest);

    Integer executeUpdateVendorData(UpdateVendorDataRequest updateVendorDataRequest);

    Page<QgetBankNameByBankeyResponse> executeQGetBankNameByBankey(String countryCode, String bankCode, Pageable pageable);

    void exportQGetBankNameByBankey(String countryCode, String bankCode, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQUpdateBank(QupdateBankRequest qupdateBankRequest);

    Page<QgetCountyDataResponse> executeQGetCountyData(Pageable pageable);

    void exportQGetCountyData(ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQUpdateUserBank(QupdateUserBankRequest qupdateUserBankRequest);

    Integer executeQUpdateCertiDom(QupdateCertiDomRequest qupdateCertiDomRequest);

    Page<GetSupDocResponse> executeGetSupDoc(String idVendor, Pageable pageable);

    void exportGetSupDoc(String idVendor, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<GetIvendorDataResponse> executeGetIvendorData(String email, Pageable pageable);

    void exportGetIvendorData(String email, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Integer executeQDeleteBankKonf(String id);

    Page<GetTaxResponse> executeGetTax(String userName, Pageable pageable);

    void exportGetTax(String userName, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<GetDataVendorByCodeResponse> executeGetDataVendorByCode(String vendorCode, Pageable pageable);

    void exportGetDataVendorByCode(String vendorCode, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

    Page<QgetRegIndoResponse> executeQGetRegIndo(Pageable pageable);

    void exportQGetRegIndo(ExportOptions exportOptions, Pageable pageable, OutputStream outputStream);

}