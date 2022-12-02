/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    /*
     * variables can be accessed through 'Page.Variables' property here
     * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
     * Page.Variables.loggedInUser.getData()
     *
     * widgets can be accessed through 'Page.Widgets' property here
     * e.g. to get value of text widget named 'username' use following script
     * 'Page.Widgets.username.datavalue'
     */
    Page.Variables.vdbIVendorData.invoke().then(function() {
        if (Page.Variables.vdbIVendorData.dataSet[0].statusSppkp === 0) {
            Page.Widgets.container13.show = false
        } else if (Page.Variables.vdbIVendorData.dataSet[0].statusSppkp === 1) {
            Page.Widgets.container13.show = true
        }
        Page.Variables.svGetDataPic.invoke({
                inputFields: {
                    "userName": Page.Widgets.labelUsername.caption
                }
            }).then(function() {
                Page.Variables.mGetDataPIC.dataSet = Page.Variables.svGetDataPic.dataSet
            }),
            Page.Variables.svGetDataSupDoc.invoke({
                inputFields: {
                    "idVendor": Page.Widgets.labelIdVendor.caption
                }
            }).then(function() {
                Page.Variables.mGetDataSupDoc.dataSet = Page.Variables.svGetDataSupDoc.dataSet
            }),
            Page.Variables.svGetDataBank.invoke({
                inputFields: {
                    "userName": Page.Widgets.labelUsername.caption
                }
            }).then(function() {
                Page.Variables.mGetDataBank.dataSet = Page.Variables.svGetDataBank.dataSet
            }),
            Page.Variables.svGetDataTax.invoke({
                inputFields: {
                    "userName": Page.Widgets.labelUsername.caption
                }
            }).then(function() {
                Page.Variables.mGetDataTax.dataSet = Page.Variables.svGetDataTax.dataSet;
            });
    })
};

Page.selectTitleChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.selectTitle.datavalue.invoke();
};

Page.FileServiceUploadPictureonSuccess = function(variable, data) {
    Page.Variables.getCompanyPic.dataSet = [{
        "pbAtId": 0,
        "fileName": data[0].fileName,
        "pathFile": data[0].inlinePath,
        "status": "new"
    }];
    Page.Widgets.labelPicName.caption = Page.Variables.getCompanyPic.dataSet[0].fileName;
    Page.Widgets.picture1.picturesource = Page.Variables.getCompanyPic.dataSet[0].pathFile;
};

Page.sVeCodeLocalSelect = function($event, widget, selectedValue) {
    Page.Widgets.radioLocation.selectedValue = selectedValue.location;
    Page.Widgets.radioLocation.displayValue = selectedValue.location;
    Page.Variables.getCompanyPic.dataSet = [{
        "pbAtId": 0,
        "fileName": "",
        "pathFile": selectedValue.picUrl,
        "status": "new"
    }];
};

Page.selectLocationChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.selectLocation.datavalue.invoke();
};

Page.FileServiceUploadCatalogueonSuccess = function(variable, data) {
    Page.Variables.getCatalogue.dataSet = [{
        "pbAtId": 0,
        "fileName": data[0].fileName,
        "pathFile": data[0].inlinePath,
        "status": "new"
    }];
};

Page.FileServiceUploadSupDoconSuccess = function(variable, data) {
    Page.Variables.getSupDoc.dataSet = [{
        "pbAtId": 0,
        "fileName": data[0].fileName,
        "pathFile": data[0].inlinePath,
        "status": "new"
    }];
    let i = Page.Widgets.mGetDataSupDocList1.getIndex(Page.Widgets.mGetDataSupDocList1.selecteditem);
    Page.Widgets.docSupUpload.show = false;
    Page.Widgets.labelDone.show = true;
    Page.Variables.mGetDataSupDoc.dataSet[i].abNale = Page.Variables.getSupDoc.dataSet[0].fileName
    Page.Variables.mGetDataSupDoc.dataSet[i].abPathFile = Page.Variables.getSupDoc.dataSet[0].pathFile
};

Page.FileServiceUploadSupDocBankonSuccess = function(variable, data) {
    Page.Variables.getSupDocBank.dataSet = [{
        "pbAtId": 0,
        "fileName": data[0].fileName,
        "pathFile": data[0].inlinePath,
        "status": "new"
    }];
};

Page.FileServiceUploadNpwponSuccess = function(variable, data) {
    Page.Variables.getNpwp.dataSet = [{
        "pbAtId": 0,
        "fileName": data[0].fileName,
        "pathFile": data[0].inlinePath,
        "status": "new"
    }];
    Page.Widgets.fileNameNpwp.show = false;
};

Page.FileServiceUploadSppkponSuccess = function(variable, data) {
    Page.Variables.getSppkp.dataSet = [{
        "pbAtId": 0,
        "fileName": data[0].fileName,
        "pathFile": data[0].inlinePath,
        "status": "new"
    }];
};

Page.FileServiceUploadTaxCertificateonSuccess = function(variable, data) {
    Page.Variables.getTaxCertificate.dataSet = [{
        "pbAtId": 0,
        "fileName": data[0].fileName,
        "pathFile": data[0].inlinePath,
        "status": "new"
    }];
};

Page.btn_addRowPICClick = function($event, widget) {
    Page.Variables.mGetDataPIC.addItem({
        "name": "",
        "email": "",
        "mobilePhone": "",
        "position": "",
        "area": "",
        "status": "New"
    });
};

Page.insertCheckPIC = function($event, widget) {
    var listItems = Page.Widgets.mGetDataPICList1.listItems._results
    for (var i = 0; i < listItems.length; i++) {
        if (listItems[i].item.status == 'Deleted') {
            Page.Variables.svRfusDelPIC.invoke()
            Page.Variables.svUpdatePIC.invoke({
                    inputFields: {
                        "idPIC": listItems[i].item.abIdil,
                        "namePic": listItems[i].item.abName,
                        "emailPic": listItems[i].item.abEmss,
                        "phonePic": listItems[i].item.abMobilePhone,
                        "positionPic": listItems[i].item.abPosition,
                        "areaPic": listItems[i].item.abPuon,
                        "reason": listItems[i].item.abRon,
                        "userName": Page.Widgets.reCompanyEmail.datavalue
                    }
                }),
                Page.sendEmailDeletedPIC();
        }
        if (listItems[i].item.status == 'New') {
            Page.Variables.svInsertPICTemp.invoke({
                    inputFields: {
                        "vendorCode": Page.Variables.vdbIVendorData.dataSet[0].vendorCode,
                        "namePIC": listItems[i].item.abName,
                        "emailPIC": listItems[i].item.abEmss,
                        "phonePIC": listItems[i].item.abMobilePhone,
                        "selectPositionPIC": listItems[i].item.abPosition,
                        "selectAreaPIC": listItems[i].item.abPuon,
                        "password": Page.Widgets.passEncryp.caption,
                        "userName": Page.Widgets.reCompanyEmail.datavalue,
                        "locationFor": Page.Widgets.radioLocation.datavalue,
                        "inputBy": Page.Variables.loggedInUserData.dataSet.user_full_name
                    }
                }),
                Page.Variables.svInsertHistory.invoke({
                    inputFields: {
                        "inputBy": Page.App.Variables.loggedInUserData.dataSet.user_full_name,
                        "History": `Insert Email Vendor - ${Page.Variables.vdbIVendorData.dataSet[0].vendorName} - ${listItems[i].item.abEmss}`
                    }
                }),
                Page.sendEmailUpdatedPIC();
        }
    }
};

Page.btn_deleteEmailClick = function($event, widget) {
    if (Page.Widgets.mGetDataPICList1.selecteditem.status == 'Exist') {
        Page.Variables.mGetDataPIC.dataSet.filter(items =>
                items.abIdil == Page.Widgets.mGetDataPICList1.selecteditem.abIdil
            ).map(function(result) {
                result.status = 'Deleted',
                    result.abRon = Page.Widgets.reason.datavalue
            }),
            Page.Variables.svInsertHistory.invoke({
                inputFields: {
                    "inputBy": Page.App.Variables.loggedInUserData.dataSet.user_full_name,
                    "History": `Delete Email - ${Page.Variables.vdbIVendorData.dataSet[0].vendorName} - ${Page.Widgets.mGetDataPICList1.selecteditem.abEmss}`
                }
            })
    } else if (Page.Widgets.mGetDataPICList1.selecteditem.status == 'New') {
        Page.Variables.mGetDataPIC.removeItem(Page.Widgets.mGetDataPICList1.selecteditem);
    }
};

Page.btn_addRowSupDocClick = function($event, widget) {
    Page.Variables.mGetDataSupDoc.addItem({
        "fileUrl": "",
        "fileName": "",
        "remark": "",
        "status": "New"
    });
};

Page.insertSupDoc = function($event, widget) {
    var listItems = Page.Widgets.mGetDataSupDocList1.listItems._results
    for (var i = 0; i < listItems.length; i++) {
        if (listItems[i].item.status == 'New') {
            Page.Variables.svInsertSupDoc.invoke({
                inputFields: {
                    "idVendor": Page.Variables.vdbIVendorData.dataSet[0].idVendor,
                    "docSupUpload": listItems[i].item.abPathFile,
                    "fileName": listItems[i].item.abNale,
                    "remark": listItems[i].item.abDon
                }
            });
        }
        // if (listItems[i].item.status == 'New') {
        //     Page.Variables.svUpdateSupDoc.invoke({
        //         inputFields: {
        //             "idSupDoc": Page.Variables.mGetDataSupDoc.dataSet[i].abIdnt,
        //             "filePath": (Page.Variables.mGetDataSupDoc.dataSet ? null : Page.Variables.getSupDoc.dataSet[0].pathFile),
        //             "fileName": Page.Variables.mGetDataSupDoc.dataSet[i].abNale,
        //             "remark": Page.Variables.mGetDataSupDoc.dataSet[i].abDon
        //         }
        //     });
        // }
    }
};

Page.deleteRowSupDocClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.mGetDataSupDoc.removeItem(Page.Widgets.mGetDataSupDocList1.selecteditem);
};

Page.insertPIC = function($event, widget) {
    let inputData = Page.Widgets.mGetDataPICList1.dataset;
    for (i = 0; i < inputData.length; i++) {
        Page.Variables.svInsertPIC.invoke({
            inputFields: {
                "sVeCode": Page.Widgets.sVeCodeLocal.query,
                "namePIC": Page.Widgets.mGetDataPICList1.dataset[i].name,
                "emailPIC": Page.Widgets.mGetDataPICList1.dataset[i].email,
                "phonePIC": Page.Widgets.mGetDataPICList1.dataset[i].mobilePhone,
                "selectPositionPIC": Page.Widgets.mGetDataPICList1.dataset[i].position,
                "selectAreaPIC": Page.Widgets.mGetDataPICList1.dataset[i].area,
                "password": Page.Widgets.passEncrypt.caption,
                "userName": Page.Widgets.labelUme.caption
            }
        });
    }
};

Page.sVeCodeLocalChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.sVeCodeLocal.datavalue;
};


Page.matchEmail = function($event, $operation, $data, options) {
    if (Page.Widgets.companyEmail.datavalue != Page.Widgets.reCompanyEmail.datavalue) {
        Page.Actions.naEmailNotMatch.invoke();
    }
};

Page.matchPassword = function($event, $operation, $data, options) {
    if (Page.Widgets.password.datavalue != Page.Widgets.retypePassword.datavalue) {
        Page.Actions.naPasswordNotMatch.invoke();
    }
};

Page.companyEmailChange = function($event, widget, newVal, oldVal) {
    Page.matchEmail();
    var email = Page.Widgets.companyEmail.datavalue;
    var emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(email)) {
        Page.Actions.naEmailFormat.invoke();
    }
};

Page.retypePasswordChange = function($event, widget, newVal, oldVal) {
    Page.matchPassword();
    Page.Widgets.passEncrypt.caption = Page.Widgets.retypePassword.datavalue;
    Page.Widgets.passEncrypt.caption = CryptoJS.MD5(newVal).toString();
};

Page.reCompanyEmailChange = function($event, widget, newVal, oldVal) {
    Page.matchEmail();
    var email = Page.Widgets.reCompanyEmail.datavalue;
    var emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(email)) {
        Page.Actions.naEmailFormat.invoke();
    }
};

Page.wizardstep8Load = function(widget, stepIndex) {
    if (Page.Widgets.executeQGetBankInfoKonfTable1.dataset.length == 0) {}

};

Page.directorEmailChange = function($event, widget, newVal, oldVal) {
    var email = Page.Widgets.directorEmail.datavalue;
    var emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(email)) {
        Page.Actions.naEmailFormat.invoke();
    }
};

Page.webCompanyChange = function($event, widget, newVal, oldVal) {
    var web = Page.Widgets.webCompany.datavalue;
    var webRegex = /\.\S+/;
    if (!webRegex.test(web)) {
        Page.Actions.naWebFormat.invoke();
    }
};

Page.btnSaveBankClick = function($event, widget) {
    Page.Variables.mGetDataBank.addItem({
        "id": Date.now(),
        "beneficiaryStatus": Page.Widgets.beneficiaryStatus.datavalue,
        "countryKey": Page.Widgets.sCountryCode.datavalue,
        "beneficiaryType": Page.Widgets.beneficiaryType.datavalue,
        "accountName": Page.Widgets.accountName.datavalue,
        "bankKey": Page.Widgets.bankName.datavalue.abBaey,
        "bankName": Page.Widgets.bankName.datavalue.abBame,
        "currency": Page.Widgets.currency.datavalue,
        "branch": Page.Widgets.branch.datavalue,
        "accountNumber": Page.Widgets.accountNumber.datavalue,
        "pathFile": Page.Variables.getSupDocBank.dataSet[0].pathFile,
        "fileName": Page.Variables.getSupDocBank.dataSet[0].fileName,
        "userName": Page.Widgets.reCompanyEmail.datavalue,
        "status": 'New'
    });
};

Page.btnUpdateBankClick = function($event, widget) {
    let index = Page.Variables.mGetDataBank.dataSet.findIndex(function(e) {
        return e.id == Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.id;
    });
    let parameter = {
        "id": Date.now(),
        "idDocBank": Page.Widgets.labelIdDocBank.caption,
        "beneficiaryStatus": Page.Widgets.beneficiaryStatus.datavalue,
        "countryKey": Page.Widgets.sCountryCode.datavalue,
        "beneficiaryType": Page.Widgets.beneficiaryType.datavalue,
        "accountName": Page.Widgets.accountName.datavalue,
        "bankKey": Page.Widgets.bankName.datavalue.abBaey,
        "bankName": Page.Widgets.bankName.datavalue.abBame,
        "currency": Page.Widgets.currency.datavalue,
        "branch": Page.Widgets.branch.datavalue,
        "accountNumber": Page.Widgets.accountNumber.datavalue,
        "status": 'Updated'
    }
    if (Page.Variables.getSupDocBank.dataSet.length > 0) {
        parameter.pathFile = Page.Variables.getSupDocBank.dataSet[0].pathFile;
        parameter.fileName = Page.Variables.getSupDocBank.dataSet[0].fileName;
    } else {
        parameter.pathFile = Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.pathFile;
        parameter.fileName = Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.fileName;
    }
    Page.Variables.mGetDataBank.setItem(index, parameter);
    Page.Variables.getSupDocBank.dataSet = [];
};

Page.insertBankTemp = function($event, widget) {
    var listItems = Page.Widgets.executeQGetBankInfoKonfTable1.listItems._results
    for (var i = 0; i < listItems.length; i++) {
        if (listItems[i].item.status == 'New') {
            Page.Variables.svUpdateDataBank.invoke({
                    inputFields: {
                        "beneficiaryStatus": listItems[i].item.beneficiaryStatus,
                        "countryCode": listItems[i].item.countryKey,
                        "beneficiaryType": listItems[i].item.beneficiaryType,
                        "accountName": listItems[i].item.accountName,
                        "bankKey": listItems[i].item.bankKey,
                        "currency": listItems[i].item.currency,
                        "branch": listItems[i].item.branch,
                        "accountNumber": listItems[i].item.accountNumber,
                        "userName": listItems[i].item.userName,
                        "vendorCode": Page.Variables.vdbIVendorData.dataSet[0].vendorCode,
                        "userName": Page.Widgets.reCompanyEmail.datavalue
                    }
                }),
                Page.Variables.svInsertDocBank.invoke({
                    inputFields: {
                        "idVendor": Page.Variables.vdbIVendorData.dataSet[0].idVendor,
                        "fileUrl": listItems[i].item.pathFile,
                        "fileName": listItems[i].item.fileName,
                    }
                }),
                Page.Variables.svInsertHistory.invoke({
                    inputFields: {
                        "inputBy": Page.App.Variables.loggedInUserData.dataSet.user_full_name,
                        "History": `Insert Bank Vendor - ${Page.Variables.vdbIVendorData.dataSet[0].vendorName}`
                    }
                })
            Page.Actions.naUpdateSucces.invoke();
            Page.Variables.svRfusUpdateBank.invoke()
        }
        if (listItems[i].item.status == 'Updated') {
            Page.Variables.svUpdateDataBank.invoke({
                    inputFields: {
                        "beneficiaryStatus": listItems[i].item.beneficiaryStatus,
                        "countryCode": listItems[i].item.countryKey,
                        "beneficiaryType": listItems[i].item.beneficiaryType,
                        "accountName": listItems[i].item.accountName,
                        "bankKey": listItems[i].item.bankKey,
                        "currency": listItems[i].item.currency,
                        "branch": listItems[i].item.branch,
                        "accountNumber": listItems[i].item.accountNumber,
                        "userName": listItems[i].item.userName,
                        "vendorCode": Page.Variables.vdbIVendorData.dataSet[0].vendorCode,
                        "userName": Page.Widgets.reCompanyEmail.datavalue
                    }
                }),
                Page.Variables.svUpdateDocBank.invoke({
                    inputFields: {
                        "idDocBank": listItems[i].item.idDocBank,
                        "filePath": listItems[i].item.pathFile,
                        "fileName": listItems[i].item.fileName,
                    }
                }),
                Page.Variables.svInsertHistory.invoke({
                    inputFields: {
                        "inputBy": Page.App.Variables.loggedInUserData.dataSet.user_full_name,
                        "History": `Update Bank Vendor - ${Page.Variables.vdbIVendorData.dataSet[0].vendorName}`
                    }
                })
            Page.Actions.naUpdateSucces.invoke();
            Page.Variables.svRfusUpdateBank.invoke()
        }
    }
};

// Page.updateDocBank = function($event, widget) {
//     return new Promise(async(resolve, reject) => {
//         let inputData = Page.Variables.mGetDataBank.dataSet;
//         for (i = 0; i < inputData.length; i++) {
//             await Page.Variables.svUpdateDocBank.invoke({
//                 inputFields: {
//                     "idDocBank": Page.Variables.mGetDataBank.dataSet[i].idDocBank,
//                     "filePath": Page.Variables.mGetDataBank.dataSet[i].pathFile,
//                     "fileName": Page.Variables.mGetDataBank.dataSet[i].fileName,
//                 }
//             });
//             if (i === inputData.length - 1) {
//                 resolve(true)
//             }
//         }
//     });
// };

Page.btn_updateBankClick = function($event, widget) {
    Page.insertBankTemp();

};

Page.btn_deleteBankClick = function($event, widget) {
    if (Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.status == 'Exist') {
        Page.Variables.mGetDataBank.dataSet.filter(items =>
            items.idBank == Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.idBank
        ).map(function(result) {
            result.status = "Can't Be Deleted"
        })
    } else if (Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.status == 'New') {
        // Page.Variables.mGetDataBank.dataSet.filter(items =>
        //     items.id !== Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.id
        // )
        Page.Variables.mGetDataBank.removeItem(Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem);
    }
    // console.log(Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.id)
};

Page.button22Click = function($event, widget) {
    Page.Variables.svInsertVendorKonfirmasi.invoke();
    Page.insertPIC();
    Page.insertSupDoc();
};

Page.supDocBankSelect = function($event, widget, selectedFiles) {
    Page.Widgets.fileNameBank.show = false;
};

Page.btnSaveTaxClick = function($event, widget) {
    Page.Variables.mGetDataTax.addItem({
            "id": Date.now(),
            "companyCode": Page.Widgets.selectCompany.datavalue,
            // "addressTaxService": Page.Widgets.addressTaxService.datavalue,
            "dateTo": Page.Widgets.validCertificateTo.datavalue,
            // "addressNpwp": Page.Widgets.addressNpwp.datavalue,
            // "taxServiceOffice": Page.Widgets.taxServiceOffice.datavalue,
            "exemptionNumber": Page.Widgets.taxCertificateNumber.datavalue,
            // "taxId": Page.Widgets.taxtId.datavalue,
            // "nameNpwp": Page.Widgets.nameNpwp.datavalue,
            // "effectiveNpwp": Page.Widgets.effectiveNpwp.datavalue,
            // "npwpUpload": Page.Variables.getNpwp.dataSet[0].pathFile,
            "dateFrom": Page.Widgets.validCertificateFrom.datavalue,
            "exemptionType": Page.Widgets.typeCertificateNpwp.datavalue,
            // "sppkpNumber": Page.Widgets.taxConfirmationNumber.datavalue,
            // "typeNpwp": Page.Widgets.typeNpwp.datavalue,
            // "fileSppkp": Page.Variables.getSppkp.dataSet[0].pathFile,
            // "fileNameSppkp": Page.Variables.getSppkp.dataSet[0].fileName,
            "fileExemption": (Page.Variables.getTaxCertificate.dataSet[0] ? Page.Variables.getTaxCertificate.dataSet[0].pathFile : null),
            "fileNameExemption": (Page.Variables.getTaxCertificate.dataSet[0] ? Page.Variables.getTaxCertificate.dataSet[0].fileName : null),
            "status": 'New'
            // "nameNpwpUpload": Page.Variables.getNpwp.dataSet[0].fileName,
            // "vendorCode": Page.Variables.vdbIVendorData.dataSet[0].vendorCode
        }),
        Page.Variables.getTaxCertificate.dataSet = [];
};

Page.btn_deleteTaxClick = function($event, widget) {
    Page.Variables.mGetDataTax.removeItem(Page.Widgets.listTax.selecteditem);
};

Page.btnUpdateTaxClick = function($event, widget) {
    let index = Page.Variables.mGetDataTax.dataSet.findIndex(function(e) {
        return e.id == Page.Widgets.listTax.selecteditem.id;
    });
    let parameter = {
        "id": Date.now(),
        "companyCode": Page.Widgets.selectCompany.datavalue,
        // "addressTaxService": Page.Widgets.addressTaxService.datavalue,
        "dateTo": Page.Widgets.validCertificateTo.datavalue,
        // "addressNpwp": Page.Widgets.addressNpwp.datavalue,
        // "taxServiceOffice": Page.Widgets.taxServiceOffice.datavalue,
        "exemptionNumber": Page.Widgets.taxCertificateNumber.datavalue,
        // "taxId": Page.Widgets.taxtId.datavalue,
        // "nameNpwp": Page.Widgets.nameNpwp.datavalue,
        // "effectiveNpwp": Page.Widgets.effectiveNpwp.datavalue,
        // "npwpUpload": Page.Variables.getNpwp.dataSet[0].pathFile,
        "dateFrom": Page.Widgets.validCertificateFrom.datavalue,
        "exemptionType": Page.Widgets.typeCertificateNpwp.datavalue,
        // "sppkpNumber": Page.Widgets.taxConfirmationNumber.datavalue,
        // "typeNpwp": Page.Widgets.typeNpwp.datavalue,
        // "idSppkp": Page.Widgets.labelIdSppkp.caption,
        // "fileNameSppkp": (Page.Variables.mGetDataTax.dataSet ? Page.Widgets.listTax.selecteditem.fileNameSppkp : Page.Variables.getSppkp.dataSet[0].fileName),
        // "fileSppkp": (Page.Variables.mGetDataTax.dataSet ? Page.Widgets.listTax.selecteditem.fileSppkp : Page.Variables.getSppkp.dataSet[0].pathFile),
        // "idExemption": Page.Widgets.labelIdExemption.caption,
        "status": 'Updated'
    }
    if (Page.Variables.getTaxCertificate.dataSet.length > 0) {
        parameter.fileNameExemption = Page.Variables.getTaxCertificate.dataSet[0].fileName;
        parameter.fileExemption = Page.Variables.getTaxCertificate.dataSet[0].pathFile;
    } else {
        parameter.fileNameExemption = Page.Widgets.listTax.selecteditem.fileNameExemption;
        parameter.fileExemption = Page.Widgets.listTax.selecteditem.fileExemption;
    }
    Page.Variables.mGetDataTax.setItem(index, parameter);
    Page.Variables.getTaxCertificate.dataSet = [];
};

Page.insertTax = function($event, widget) {
    var listItems = Page.Widgets.listTax.listItems._results
    for (var i = 0; i < listItems.length; i++) {
        if (listItems[i].item.status == 'New') {
            Page.Variables.svInsertTaxTemp.invoke({
                    inputFields: {
                        "companyCode": listItems[i].item.companyCode,
                        "userName": Page.Widgets.reCompanyEmail.datavalue,
                        // "effectiveSince": Page.Widgets.effectiveNpwp.datavalue,
                        // "serviceOfficeAddress": listItems[i].item.serviceOfficeAddress,
                        "dateTo": listItems[i].item.dateTo,
                        // "serviceOffice": listItems[i].item.serviceOffice,
                        "exemptionNumber": listItems[i].item.exemptionNumber,
                        "dateFrom": listItems[i].item.dateFrom,
                        "exemptionType": listItems[i].item.exemptionType,
                        // "sppkpNumber": listItems[i].item.sppkpNumber,
                        // "typeNpwp": listItems[i].item.typeNpwp,
                        "vendorCode": Page.Variables.vdbIVendorData.dataSet[0].vendorCode,
                        "inputBy": Page.Variables.loggedInUserData.dataSet.user_full_name
                    }
                }),
                Page.Variables.svInsertExemption.invoke({
                    inputFields: {
                        "idVendor": Page.Variables.vdbIVendorData.dataSet[0].idVendor,
                        "fileUrl": listItems[i].item.fileExemption,
                        "fileName": listItems[i].item.fileNameExemption
                    }
                }),
                Page.Variables.svInsertHistory.invoke({
                    inputFields: {
                        "inputBy": Page.App.Variables.loggedInUserData.dataSet.user_full_name,
                        "History": `Insert WHT Vendor - ${Page.Variables.vdbIVendorData.dataSet[0].vendorName}`
                    }
                })
        }
    }
    // if (listItems[i].item.status == 'Deleted') {
    //     Page.Variables.svRfusDelTax.invoke()
    //     Page.Variables.svUpdateTax.invoke({
    //         inputFields: {
    //             "userName": Page.Widgets.reCompanyEmail.datavalue,
    //             "effectiveSince": Page.Widgets.effectiveNpwp.datavalue,
    //             "serviceOfficeAddress": Page.Widgets.listTax.dataset[i].serviceOfficeAddress,
    //             "dateTo": Page.Widgets.listTax.dataset[i].dateTo,
    //             "serviceOffice": Page.Widgets.listTax.dataset[i].serviceOffice,
    //             "exemptionNumber": Page.Widgets.listTax.dataset[i].exemptionNumber,
    //             "dateFrom": Page.Widgets.listTax.dataset[i].dateFrom,
    //             "exemptionType": Page.Widgets.listTax.dataset[i].exemptionType,
    //             "sppkpNumber": Page.Widgets.listTax.dataset[i].sppkpNumber,
    //             "typeNpwp": Page.Widgets.listTax.dataset[i].typeNpwp,
    //             "vendorCode": Page.Widgets.listTax.dataset[i].vendorCode,
    //         }
    //     });
    // }
};

Page.btn_checkSupDocClick = function($event, widget, item, currentItemWidgets) {
    if (Page.Widgets.mGetDataSupDocList1.selecteditem.status == 'New') {
        window.open(Page.Widgets.mGetDataSupDocList1.selecteditem.abPathFile);
    }
    if (Page.Widgets.mGetDataSupDocList1.selecteditem.status == 'Exist') {
        window.open(Page.Widgets.mGetDataSupDocList1.selecteditem.abPathfile);
    }
};

//insert document
Page.insertDocBank = function(data) {
    return new Promise(async(resolve, reject) => {
        let inputData = Page.Widgets.executeQGetBankInfoKonfTable1.dataset;
        for (i = 0; i < inputData.length; i++) {
            await Page.Variables.svInsertDocBank.invoke({
                inputFields: {
                    "idVendor": Page.Variables.mGetMaxVendor.dataSet.id,
                    "fileUrl": Page.Widgets.executeQGetBankInfoKonfTable1.dataset[i].supDocBank,
                    "fileName": Page.Widgets.executeQGetBankInfoKonfTable1.dataset[i].fileNameSupDocBank,
                }
            })
            if (i === inputData.length - 1) {
                resolve(true)
            }
        }
    })
};
// update document
// Page.updateSppkpFile = function($event, widget) {
//     return new Promise(async(resolve, reject) => {
//         let inputData = Page.Variables.mGetDataTax.dataSet;
//         for (i = 0; i < inputData.length; i++) {
//             Page.Variables.svUpdateFileSppkp.invoke({
//                 inputFields: {
//                     "idFileSppkp": Page.Variables.mGetDataTax.dataSet[i].idSppkp,
//                     "filePath": Page.Variables.mGetDataTax.dataSet[i].fileSppkp,
//                     "fileName": Page.Variables.mGetDataTax.dataSet[i].fileNameSppkp,
//                 }
//             });
//             if (i === inputData.length - 1) {
//                 resolve(true)
//             }
//         }
//     });
// };

// Page.updateExemptionFile = function($event, widget) {
//     return new Promise(async(resolve, reject) => {
//         let inputData = Page.Variables.mGetDataTax.dataSet;
//         for (i = 0; i < inputData.length; i++) {
//             Page.Variables.svUpdateFileExemption.invoke({
//                 inputFields: {
//                     "idFileExemption": Page.Variables.mGetDataTax.dataSet[i].idExemption,
//                     "filePath": Page.Variables.mGetDataTax.dataSet[i].fileExemption,
//                     "fileName": Page.Variables.mGetDataTax.dataSet[i].fileNameExemption,
//                 }
//             });
//             if (i === inputData.length - 1) {
//                 resolve(true)
//             }
//         }
//     });
// };

// Page.updateSupDocFile = function($event, widget) {
//     return new Promise(async(resolve, reject) => {
//         let inputData = Page.Variables.mGetDataSupDoc.dataSet;
//         for (i = 0; i < inputData.length; i++) {
//             Page.Variables.svUpdateSupDoc.invoke({
//                 inputFields: {
//                     "idSupDoc": Page.Variables.mGetDataSupDoc.dataSet[i].abIdnt,
//                     "filePath": (Page.Variables.mGetDataSupDoc.dataSet ? null : Page.Variables.getSupDoc.dataSet[0].pathFile),
//                     "fileName": Page.Variables.mGetDataSupDoc.dataSet[i].abNale,
//                     "remark": Page.Variables.mGetDataSupDoc.dataSet[i].abDon
//                 }
//             });
//             if (i === inputData.length - 1) {
//                 resolve(true)
//             }
//         }
//     });
// };

Page.bankNameChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.btnUpdateBank.disabled = false;
};

Page.updateBankOpened = function($event, widget) {
    Page.Widgets.btnUpdateBank.disabled = true;
};

Page.btn_checkFileBankClick = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.pathFile);
};

Page.button23Click = function($event, widget) {
    window.open(Page.Variables.getCatalogue.dataSet[0].pathFile);
};

Page.btn_checkFileExemptionClick = function($event, widget) {
    window.open(Page.Variables.getTaxCertificate.dataSet[0].pathFile);
};

Page.emailPICChange = function($event, widget, item, currentItemWidgets, newVal, oldVal) {
    var email = Page.Widgets.emailPIC.datavalue;
    var emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(email)) {
        Page.Actions.naEmailFormat.invoke();
    }
};

Page.svUpdateDataPIConSuccess = function(variable, data) {
    Page.Variables.svGetDataPic.invoke({
        inputFields: {
            "vendorCode": Page.Widgets.labelVecode.caption
        }
    }).then(function() {
        Page.Variables.mGetDataPIC.dataSet = Page.Variables.svGetDataPic.dataSet
    });
};

Page.svUpdateBankonSuccess = function(variable, data) {
    Page.Variables.svGetDataBank.invoke({
        inputFields: {
            "vendorCode": Page.Widgets.labelVecode.caption
        }
    }).then(function() {
        Page.Variables.mGetDataBank.dataSet = Page.Variables.svGetDataBank.dataSet
    });
    Page.Actions.naUpdateSucces.invoke();
};

Page.checkTaxCertificateClick = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Widgets.listTax.selecteditem.fileExemption);
};

Page.checkTaxConfirmClick = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Widgets.listTax.selecteditem.fileSppkp);
};

Page.btn_checkFileNpwpClick = function($event, widget) {
    window.open(Page.Variables.getNpwp.dataSet[0].pathFile);
};
Page.btn_checkNpwpFileClick = function($event, widget) {
    if (Page.Variables.vdbIVendorData.dataSet[0].fileNpwp == null) {
        Page.Actions.naPleaseNpwp.invoke();
    } else {
        window.open(Page.Variables.vdbIVendorData.dataSet[0].fileNpwp)
    }
};

Page.btnUpdateClick = function($event, widget) {
    Page.Variables.svUpdateDataVendor.invoke();
    Page.insertSupDoc();
    Page.insertCheckPIC();
    Page.Actions.naUpdateSucces.invoke();
};

Page.picture10_1Click = function($event, widget, item, currentItemWidgets) {
    console.log(Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem)
};

Page.btn_updateTaxClick = function($event, widget) {
    Page.insertTax();
};

Page.button38Click = function($event, widget, item, currentItemWidgets) {
    console.log(Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem)
};
Page.button37Click = function($event, widget) {
    console.log(Page.Widgets.listTax.selecteditem)
};
Page.btnCheckFileCatalogueClick = function($event, widget) {
    if (Page.Variables.vdbIVendorData.dataSet[0].catalogueUrl == null) {
        Page.Actions.naFileNotFound.invoke()
    } else {
        window.open(Page.Variables.vdbIVendorData.dataSet[0].catalogueUrl)
    }
};
Page.selectVaerChange = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.selectVaer.datavalue == 1) {
        Page.Widgets.container13.show = true
    } else if (Page.Widgets.selectVaer.datavalue == 0) {
        Page.Widgets.container13.show = false
    }
};
Page.btn_checkSppkpFileClick = function($event, widget) {
    if (Page.Variables.vdbIVendorData.dataSet[0].filesppkp == null) {
        Page.Actions.naFileNotFound.invoke();
    } else {
        window.open(Page.Variables.vdbIVendorData.dataSet[0].fileNpwp)
    }
};
Page.btn_checkFileSppkpClick = function($event, widget) {
    window.open(Page.Variables.getSppkp.dataSet[0].pathFile);
};

Page.addTaxOpened = function($event, widget) {
    Page.Widgets.btnSaveTax.disabled = true;
};

Page.selectCompanyChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.btnSaveTax.disabled = false;
};

Page.btn_removeCatClick = function($event, widget) {
    Page.Variables.getCatalogue.dataSet = []
    let uploadStatus = document.querySelector("#gridCatalogue ul.list-group.file-upload");
    if (uploadStatus) uploadStatus.style.visibility = "hidden";
};

Page.catUploadSelect = function($event, widget, selectedFiles) {
    let uploadStatus = document.querySelector("#gridCatalogue ul.list-group.file-upload");
    if (uploadStatus) uploadStatus.style.visibility = "visible";
};

Page.svUpdateDataVendoronSuccess = function(variable, data) {
    Page.Variables.svInsertHistory.invoke({
        inputFields: {
            "inputBy": Page.App.Variables.loggedInUserData.dataSet.user_full_name,
            "History": `Update Vendor On SAP - ${Page.Variables.vdbIVendorData.dataSet[0].vendorName}`
        }
    })
};

Page.button33Click = function($event, widget) {
    Page.insertBankTemp()
};
Page.deleteRowPICClick = function($event, widget, item, currentItemWidgets) {
    console.log(Page.Widgets.mGetDataPICList1.selecteditem.abEmss)
};
Page.tabpane4Load = function($event, widget) {
    Page.Widgets.checkbox2.datavalue = true;
};

Page.checkbox2Click = function($event, widget) {
    Page.Widgets.btn_updateBank.disabled = false;
    Page.Widgets.btn_updateTax.disabled = false;
    Page.Widgets.btnUpdate.disabled = false;
};

Page.container163_1Mouseenter = function($event, widget) {
    if (Page.Widgets.btn_updateTax.disabled == true) {
        Page.Actions.naTickLicense.invoke()
    }
};

Page.container160_2Mouseenter = function($event, widget) {
    if (Page.Widgets.btn_updateTax.disabled == true) {
        Page.Actions.naTickLicense.invoke()
    }
};

Page.container162_1Mouseenter = function($event, widget) {
    if (Page.Widgets.btn_updateTax.disabled == true) {
        Page.Actions.naTickLicense.invoke()
    }
};

Page.btn_checkDocBankClick = function($event, widget) {
    window.open(Page.Variables.getSupDocBank.dataSet[0].pathFile);
};

Page.button34Click = function($event, widget) {
    console.log(Page.Variables.svGetDataPic.dataSet)
};

Page.sendEmailDeletedPIC = function($event, Widget) {
    let EmailLocation;
    if (Page.Widgets.labelPuon.length > 1) {
        EmailLocation = Page.Variables.svGetDataEmailNotif.dataSet[0].abEmail
    } else {
        EmailLocation = 'ekusumaningtyas@aio.co.id'
    }
    Page.Variables.svNotifyAdminInsertEmail.invoke({
        inputFields: {
            RequestBody: {
                "to": EmailLocation,
                "cc": "",
                "subject": `Deleting Email Data on iVendor`,
                "body": `<p> Kami beritahukan bahwa Vendor dengan Nama <font color="red"><b>${Page.Variables.vdbIVendorData.dataSet[0].vendorName}</b></font> telah menghapus data Emailnya, silahkan mengakses Aplikasi ivendor untuk melihat perubahan datanya. <br><br> Klik link berikut : <a href="https://ivendor.aio.co.id:11000"> <b>ivendor.aio.co.id</b></font></a> <br /><br /><br />Terimakasih<br />Application Vendor Management`
            }
        }
    }, function(data) {
        console.log(data);
    }, function(error) {});
}

Page.sendEmailUpdatedPIC = function($event, Widget) {
    let EmailLocation;
    if (Page.Widgets.labelPuon.length > 1) {
        EmailLocation = Page.Variables.svGetDataEmailNotif.dataSet[0].abEmail
    } else {
        EmailLocation = 'ekusumaningtyas@aio.co.id'
    }
    Page.Variables.svNotifyAdminInsertEmail.invoke({
        inputFields: {
            RequestBody: {
                "to": EmailLocation,
                "cc": "",
                "subject": `Updating Email Data on iVendor`,
                "body": `<p> Kami beritahukan bahwa Vendor dengan Nama <font color="red"><b>${Page.Variables.vdbIVendorData.dataSet[0].vendorName}</b></font> telah menambahkan data Emailnya, silahkan mengakses Aplikasi ivendor untuk melihat perubahan datanya. <br><br> Klik link berikut : <a href="https://ivendor.aio.co.id:11000"> <b>ivendor.aio.co.id</b></font></a> <br /><br /><br />Terimakasih<br />Application Vendor Management`
            }
        }
    }, function(data) {
        console.log(data);
    }, function(error) {});
}

Page.vdbIVendorDataonSuccess = function(variable, data) {
    if (Page.Variables.vdbIVendorData.dataSet[0].license == 1) {
        Page.Widgets.checkbox2.datavalue = true;
        Page.Widgets.btn_updateBank.disabled = false;
        Page.Widgets.btn_updateTax.disabled = false;
        Page.Widgets.btnUpdate.disabled = false;
    }
};
