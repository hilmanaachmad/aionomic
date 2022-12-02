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
        Page.Variables.svGetDataBank.invoke({
                inputFields: {
                    "userName": Page.Widgets.labelUsername.caption
                }
            }).then(function() {
                Page.Variables.mGetDataBank.dataSet = Page.Variables.svGetDataBank.dataSet
                Page.Variables.svGetRegion.invoke()
            }),
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
            Page.Variables.svGetDataTax.invoke({
                inputFields: {
                    "userName": Page.Widgets.labelUsername.caption
                }
            }).then(function() {
                Page.Variables.mGetDataTax.dataSet = Page.Variables.svGetDataTax.dataSet;
            });
    })
    $('form').disableAutoFill();
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
};

Page.sVeCodeOverseasSelect = function($event, widget, selectedValue) {
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
    Page.Widgets.npwpName.show = false;
};

Page.btnDeleteBankClick = function($event, widget) {
    if (Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.status == 'Exist') {
        Page.Variables.mGetDataBank.dataSet.filter(items =>
            items.idBank == Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.idBank
        ).map(function(result) {
            result.status = "Can't Deleted"
        })
    } else if (Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.status == 'New') {
        // Page.Variables.mGetDataBank.dataSet.filter(items =>
        //     items.id !== Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.id
        // )
        Page.Variables.mGetDataBank.removeItem(Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem);
    }
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
                        "password": Page.Widgets.passEncrypt.caption,
                        "userName": Page.Widgets.reCompanyEmail.datavalue,
                        "locationFor": Page.Widgets.radioLocation.datavalue,
                        "inputBy": Page.App.Variables.loggedInUserData.dataSet.user_full_name
                    }
                }),
                Page.Variables.svInsertHistory.invoke({
                    inputFields: {
                        "inputBy": Page.App.Variables.loggedInUserData.dataSet.user_full_name,
                        "History": `Insert Email Vendor - ${Page.Variables.vdbIVendorData.dataSet[0].vendorName} - ${listItems[i].item.abEmss}`
                    }
                }),
                Page.sendEmailUpdatedPICPIC();
        }
    }
}

Page.FileServiceUploadCertiDomonSuccess = function(variable, data) {
    Page.Variables.getCertiDom.dataSet = [{
        "pbAtId": 0,
        "fileName": data[0].fileName,
        "pathFile": data[0].inlinePath,
        "status": "new"
    }];
    Page.Variables.getCertiDom.dataSet = [];
};

Page.btn_addRowPICClick = function($event, widget) {
    Page.Variables.mGetDataPIC.addItem({
        "id": Date.now(),
        "abEmss": "",
        "abMobilePhone": "",
        "abName": "",
        "abPosition": "",
        "abPuon": "",
        "status": "New"
    });
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

Page.deleteRowSupDocClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.mGetDataSupDoc.removeItem(Page.Widgets.mGetDataSupDocList1.selecteditem);
};

Page.insertPIC = function($event, widget) {
    let inputData = Page.Variables.mGetDataPIC.dataSet;
    for (i = 0; i < inputData.length; i++) {
        Page.Variables.svInsertPICTemp.invoke({
            inputFields: {
                "vendorCode": Page.Variables.vdbIVendorData.dataSet[0].vendorCode,
                "namePIC": Page.Variables.mGetDataPIC.dataSet[i].abName,
                "emailPIC": Page.Variables.mGetDataPIC.dataSet[i].abEmss,
                "phonePIC": Page.Variables.mGetDataPIC.dataSet[i].abMobilePhone,
                "selectPositionPIC": Page.Variables.mGetDataPIC.dataSet[i].abPosition,
                "selectAreaPIC": Page.Variables.mGetDataPIC.dataSet[i].abPuon,
                "password": Page.Widgets.passEncrypt.caption,
                "userName": Page.Widgets.reCompanyEmail.datavalue
            }
        });
    }
};

Page.sVeCodeOverseasChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.sVeCodeOverseas.datavalue;
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

Page.wizardDone = function(widget, steps) {
    Page.insertPIC();
    Page.insertSupDoc();
    Page.Actions.naAddSuccess.invoke();
};

Page.companyEmailChange = function($event, widget, newVal, oldVal) {
    Page.matchEmail();
    var email = Page.Widgets.companyEmail.datavalue;
    var emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(email)) {
        Page.Actions.naEmailFormat.invoke();
    }
};

Page.passwordChange = function($event, widget, newVal, oldVal) {
    Page.matchPassword();
};

Page.reCompanyEmailChange = function($event, widget, newVal, oldVal) {
    Page.matchEmail();
    var email = Page.Widgets.reCompanyEmail.datavalue;
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

Page.button29Click = function($event, widget) {
    window.open("resources/files/New_DGT_Form.xlsx", "__blank");
};

Page.button28_1Click = function($event, widget) {
    window.open("resources/files/New_DGT_Form_Instruction.pdf");
};

Page.label125Click = function($event, widget) {
    Page.Widgets.containerLink.show = true;
};

Page.veNameChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.labelSplit.caption = Page.Widgets.veName.datavalue.split(" ")[0];
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

Page.updateDocBank = function($event, widget) {
    return new Promise(async(resolve, reject) => {
        let inputData = Page.Variables.mGetDataBank.dataSet;
        for (i = 0; i < inputData.length; i++) {
            await Page.Variables.svUpdateDocBank.invoke({
                inputFields: {
                    "idDocBank": Page.Variables.mGetDataBank.dataSet[i].idDocBank,
                    "filePath": Page.Variables.mGetDataBank.dataSet[i].pathFile,
                    "fileName": Page.Variables.mGetDataBank.dataSet[i].fileName,
                }
            });
            if (i === inputData.length - 1) {
                resolve(true)
            }
        }
    });
};

Page.btn_updateBankClick = function($event, widget) {
    Page.insertBankTemp();
};

Page.btnDeleteBankClick = function($event, widget) {
    if (Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.status == 'Exist') {
        Page.Variables.mGetDataBank.dataSet.filter(items =>
            items.idBank == Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.idBank
        ).map(function(result) {
            result.status = 'Deleted'
        })
    } else if (Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.status == 'New') {
        // Page.Variables.mGetDataBank.dataSet.filter(items =>
        //     items.id !== Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.id
        // )
        Page.Variables.mGetDataBank.removeItem(Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem);
    }
};

Page.checkTaxConfirmClick = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Variables.getCertiDom.dataSet[0].fileName);
};

Page.btnSaveTaxClick = function($event, widget) {
    Page.Variables.mGetDataTax.addItem({
        "id": Date.now(),
        "companyCode": Page.Widgets.selectCompany.datavalue,
        "dateFrom": Page.Widgets.validFrom.datavalue,
        "dateTo": Page.Widgets.validTo.datavalue,
        "fileCertiDom": (Page.Variables.getCertiDom.dataSet[0] ? Page.Variables.getCertiDom.dataSet[0].pathFile : null),
        "fileNameCertiDom": (Page.Variables.getCertiDom.dataSet[0] ? Page.Variables.getCertiDom.dataSet[0].fileName : null),
        "status": 'New'
    });
    Page.Variables.getCertiDom.dataSet = [];
};

Page.btnUpdateTaxClick = function($event, widget) {
    let index = Page.Variables.mGetDataTax.dataSet.findIndex(function(e) {
        return e.id == Page.Widgets.listTax.selecteditem.id;
    });
    let parameter = {
        "id": Date.now(),
        "companyCode": Page.Widgets.selectCompany.datavalue,
        "dateFrom": Page.Widgets.validFrom.datavalue,
        "dateTo": Page.Widgets.validTo.datavalue,
        "status": 'Updated'
    }
    if (Page.Variables.getCertiDom.dataSet.length > 0) {
        parameter.fileNameCertiDom = Page.Variables.getCertiDom.dataSet[0].fileName;
        parameter.fileCertiDom = Page.Variables.getCertiDom.dataSet[0].pathFile;
    } else {
        parameter.fileNameCertiDom = Page.Widgets.listTax.selecteditem.fileNameCertiDom;
        parameter.fileCertiDom = Page.Widgets.listTax.selecteditem.fileCertiDom;
    }
    Page.Variables.mGetDataTax.setItem(index, parameter);
    Page.Variables.getCertiDom.dataSet = [];
};

Page.insertTax = function($event, widget) {
    var listItems = Page.Widgets.listTax.listItems._results
    for (var i = 0; i < listItems.length; i++) {
        if (listItems[i].item.status == 'New') {
            Page.Variables.svInsertTaxTemp.invoke({
                    inputFields: {
                        "companyCode": listItems[i].item.companyCode,
                        "userName": Page.Widgets.reCompanyEmail.datavalue,
                        "dateTo": listItems[i].item.dateTo,
                        "dateFrom": listItems[i].item.dateFrom,
                        "vendorCode": Page.Variables.vdbIVendorData.dataSet[0].vendorCode,
                        "inputBy": Page.Variables.loggedInUserData.dataSet.user_full_name
                    }
                }),
                Page.Variables.svInsertCertidom.invoke({
                    inputFields: {
                        "idVendor": Page.Variables.vdbIVendorData.dataSet[0].idVendor,
                        "fileUrl": listItems[i].item.fileCertiDom,
                        "fileName": listItems[i].item.fileNameCertiDom
                    }
                }),
                Page.Variables.svInsertHistory.invoke({
                    inputFields: {
                        "inputBy": Page.App.Variables.loggedInUserData.dataSet.user_full_name,
                        "History": `Insert WHT Vendor - ${Page.Variables.vdbIVendorData.dataSet[0].vendorName}`
                    }
                })
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
    }
};


Page.updateCertiDom = function($event, widget) {
    // return new Promise(async(resolve, reject) => {
    let inputData = Page.Variables.mGetDataTax.dataSet;
    for (i = 0; i < inputData.length; i++) {
        Page.Variables.svUpdateCertiDom.invoke({
            inputFields: {
                "idCertiDom": Page.Variables.mGetDataTax.dataSet[i].idCertiDom,
                "filePath": Page.Variables.mGetDataTax.dataSet[i].fileCertiDom,
                "fileName": Page.Variables.mGetDataTax.dataSet[i].fileNameCertiDom,
            }
        });
        // if (i === inputData.length - 1) {
        //     resolve(true)
        // }
    }
};

// Page.updateSupDocFile = function($event, widget) {
//     // return new Promise(async(resolve, reject) => {
//     let inputData = Page.Variables.mGetDataSupDoc.dataSet;
//     for (i = 0; i < inputData.length; i++) {
//         Page.Variables.svUpdateSupDoc.invoke({
//             inputFields: {
//                 "idSupDoc": Page.Variables.mGetDataSupDoc.dataSet[i].abIdnt,
//                 "filePath": (Page.Variables.mGetDataSupDoc.dataSet ? Page.Variables.getSupDoc.dataSet[0].pathFile : null),
//                 "fileName": Page.Variables.mGetDataSupDoc.dataSet[i].abNale,
//                 "remark": Page.Variables.mGetDataSupDoc.dataSet[i].abDon
//             }
//         });
//         // if (i === inputData.length - 1) {
//         //     resolve(true)
//     }
//     // });
// };

Page.checkCertidomClick = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Variables.getCertiDom.dataSet[0].pathFile);
};

Page.bankNameChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.btnUpdateBank.disabled = false;
};

Page.updateBankOpened = function($event, widget) {
    Page.Widgets.btnUpdateBank.disabled = true;
};

Page.picture13Click = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Variables.getSupDocBank.dataset[0].pathFile);
};

Page.button25Click = function($event, widget) {
    window.open(Page.Variables.getCatalogue.dataSet[0].pathFile);
};

Page.emailPICChange = function($event, widget, item, currentItemWidgets, newVal, oldVal) {
    var email = Page.Widgets.emailPIC.datavalue;
    var emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(email)) {
        Page.Actions.naEmailFormat.invoke();
    }
};

Page.svUpdateBankonSuccess = function(variable, data) {
    Page.Variables.svGetDataBank.invoke({
        inputFields: {
            "vendorCode": Page.Widgets.labelVecode.caption
        }
    }).then(function() {
        Page.Variables.mGetDataBank.dataSet = Page.Variables.svGetDataBank.dataSet
    });
};

Page.picture19_4Click = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.pathFile);
};

Page.btn_checkFileTaxClick = function($event, widget) {
    if (Page.Variables.vdbIVendorData.dataSet[0].fileNpwp == null) {
        Page.Actions.naPleaseNpwp.invoke();
    } else {
        window.open(Page.Variables.vdbIVendorData.dataSet[0].fileNpwp)
    }
    console.log(Page.Variables.vdbIVendorData.dataSet)
};

Page.btn_checkCertiDomClick = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Widgets.listTax.selecteditem.fileCertiDom)
};

Page.btn_checkFileNpwpClick = function($event, widget) {
    window.open(Page.Variables.getNpwp.dataSet[0].pathFile);
};

Page.btnUpdateClick = function($event, widget) {
    Page.Variables.svUpdateDataVendor.invoke();
    Page.insertSupDoc();
    Page.insertCheckPIC();
    Page.Actions.naUpdateSucces.invoke();
};

Page.btn_updateTaxClick = function($event, widget) {
    Page.insertTax();
};

Page.btnCheckFileCatalogueClick = function($event, widget) {
    if (Page.Variables.vdbIVendorData.dataSet[0].catalogueUrl > 0) {
        window.open(Page.Variables.vdbIVendorData.dataSet[0].catalogueUrl)
    } else {
        Page.Actions.naFileNotFound.invoke()
    }
};

Page.btnDeleteTaxClick = function($event, widget) {
    Page.Variables.mGetDataTax.removeItem(Page.Widgets.listTax.selecteditem);
};

Page.button32Click = function($event, widget) {
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

Page.checkbox2Click = function($event, widget) {
    Page.Widgets.btnUpdate.disabled = false;
    Page.Widgets.btn_updateBank.disabled = false;
    Page.Widgets.btn_updateTax.disabled = false;

};

Page.container142Mouseenter = function($event, widget) {
    if (Page.Widgets.btn_updateTax.disabled == true) {
        Page.Actions.naTickLicense.invoke()
    }
};

Page.container132_1Mouseenter = function($event, widget) {
    if (Page.Widgets.btn_updateTax.disabled == true) {
        Page.Actions.naTickLicense.invoke()
    }
};

Page.container133_1Mouseenter = function($event, widget) {
    if (Page.Widgets.btn_updateTax.disabled == true) {
        Page.Actions.naTickLicense.invoke()
    }
};

Page.btn_checkSupDocClick = function($event, widget, item, currentItemWidgets) {
    if (Page.Widgets.mGetDataSupDocList1.selecteditem.status == 'New') {
        window.open(Page.Widgets.mGetDataSupDocList1.selecteditem.abPathFile);
    }
    if (Page.Widgets.mGetDataSupDocList1.selecteditem.status == 'Exist') {
        window.open(Page.Widgets.mGetDataSupDocList1.selecteditem.abPathfile);
    }
};

Page.sendEmailDeletedPIC = function($event, Widget) {
    let EmailLocation;
    if (Page.Widgets.labelPuon.length > 1) {
        EmailLocation = Page.Variables.svGetDataEmailNotif.dataSet[0].abEmail
    } else {
        EmailLocation = 'driyadi@aio.co.id'
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
        EmailLocation = 'driyadi@aio.co.id'
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