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

    var vsi = Math.floor(Math.random() * 10000001);
    Page.Widgets.labelVsi.caption = vsi;



    if (!Page.pageParams.vendorType) {
        App.Actions.appNotification.setMessage('Vendor Type is null');
        App.Actions.appNotification.setToasterClass('warning');
        App.Actions.appNotification.setToasterDuration(5000);
        App.Actions.appNotification.invoke();
        App.Actions.goToPage_CompanyDomicilie.invoke();
    }

    $("#inputTax").inputmask({
        "mask": "00.000.000.0-000.000"
    });


    $('form').disableAutoFill();

    Page.Widgets.wizardstep8.active = true;

};

Page.FileServiceUploadPictureonSuccess = function(variable, data) {
    Page.Variables.getCompanyPic.dataSet = [{
        "pbAtId": 0,
        "fileName": data[0].fileName,
        "pathFile": data[0].inlinePath,
        "status": "new"
    }];
    Page.Widgets.companyPhoto.datavalue = data[0].fileName;
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

Page.docSupUploadSelect = function($event, widget, selectedFiles, item, currentItemWidgets) {
    Page.Variables.selectedListSupDoc.setData({
        dataValue: item.id
    });
    Page.Widgets.supDoc.datavalue = "uploaded";
};

Page.FileServiceUploadSupDoconSuccess = function(variable, data) {
    Page.Variables.mGetDataSupDoc.dataSet.filter(function(filter_item) {
        return filter_item.id === Page.Variables.selectedListSupDoc.dataSet.dataValue;
    }).map(function(map_item) {
        map_item.fileName = data[0].fileName;
        map_item.fileUrl = data[0].inlinePath;
    });
    Page.Widgets.docSupUpload.show = false;
    Page.Widgets.labelDone.show = true;
    // Page.Widgets.label179.caption = data[0].fileName.replace(/[()]/gi, ' ')
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
    Page.Widgets.attachmentNpwpText.datavalue = data[0].fileName;
    // Page.Widgets.wizardstep9.disablenext = false;
    // Page.validateWizardTax();
    // if (Page.Variables.svGetNpwp.dataSet.length > 0) {
    //     Page.Actions.naNpwpExist.invoke()
    //     Page.Widgets.wizardstep9.disablenext = true;
    //     Page.validateWizardTax();
    // }
};

Page.FileServiceUploadSppkponSuccess = function(variable, data) {
    Page.Variables.getSppkp.dataSet = [{
        "pbAtId": 0,
        "fileName": data[0].fileName,
        "pathFile": data[0].inlinePath,
        "status": "new"
    }];
    Page.Widgets.attachmentSPPKP.datavalue = data[0].fileName;
};

Page.FileServiceUploadTaxCertificateonSuccess = function(variable, data) {
    Page.Variables.getTaxCertificate.dataSet = [{
        "pbAtId": 0,
        "fileName": data[0].fileName,
        "pathFile": data[0].inlinePath,
        "status": "new"
    }];
};

Page.deleteRowPICClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.mGetDataPIC.removeItem(Page.Widgets.mGetDataPICList1.selecteditem);
};

Page.btn_addRowPICClick = function($event, widget) {
    Page.Variables.mGetDataPIC.addItem({
        "name": "",
        "email": "",
        "mobilePhone": "",
        "position": "",
        "area": ""
    });
    Page.Widgets.listPIC.datavalue = "uploaded"
};

Page.btn_addRowSupDocClick = function($event, widget) {
    let max_id;
    if (Page.Variables.mGetDataSupDoc.dataSet.length === 0) {
        max_id = 0;
    } else {
        max_id = Math.max.apply(Math, Page.Variables.mGetDataSupDoc.dataSet.map(function(item) {
            return item.id;
        }));

        if (Page.Variables.mGetDataSupDoc.dataSet[max_id - 1].fileUrl.length < 1) {
            console.log("call")
            Page.callValidationDialog('info', 'add-row');
            return 0;
        }
    }

    Page.Variables.mGetDataSupDoc.dataSet.push({
        "id": max_id + 1,
        "fileUrl": "",
        "fileName": "",
        "remark": ""
    });

};

Page.deleteRowSupDocClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.mGetDataSupDoc.removeItem(Page.Widgets.mGetDataSupDocList1.selecteditem);
    if (Page.Widgets.mGetDataSupDocList1.dataset.length === 0) {
        Page.Widgets.supDoc.value = "";
    }
};

Page.matchEmail = function($event, $operation, $data, options) {
    if (Page.Widgets.companyEmail.datavalue != Page.Widgets.reCompanyEmail.datavalue) {
        Page.Actions.naEmailNotMatch.invoke();
        Page.Widgets.wizardstep7.disablenext = true;
    } else {
        Page.Widgets.wizardstep7.disablenext = false;
    }
};

Page.matchPassword = function($event, $operation, $data, options) {
    if (Page.Widgets.password.datavalue != Page.Widgets.retypePassword.datavalue) {
        Page.Actions.naPasswordNotMatch.invoke();
        Page.Widgets.wizardstep7.disablenext = true;
    } else {
        Page.Widgets.wizardstep7.disablenext = false;
    }
};

Page.companyEmailChange = function($event, widget, newVal, oldVal) {
    var email = Page.Widgets.companyEmail.datavalue;
    var emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(email)) {
        Page.Actions.naEmailFormat.invoke();
        Page.Widgets.wizardstep7.disablenext = true;
    } else {
        Page.Widgets.wizardstep7.disablenext = false;
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
        Page.Widgets.wizardstep7.disablenext = true;
    } else {
        Page.Widgets.wizardstep7.disablenext = false;
    }
};

Page.VeNameChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.labelSplit.caption = Page.Widgets.VeName.datavalue.split(" ")[0];
};

Page.wizardstep8Load = function(widget, stepIndex) {
    if (Page.Widgets.executeQGetBankInfoKonfTable1.dataset.length == 0) {
        Page.Widgets.wizardstep8.disablenext = true;
    } else {
        Page.Widgets.wizardstep8.disablenext = false;
    }

};

Page.svDeleteBankonSuccess = function(variable, data) {
    if (Page.Widgets.executeQGetBankInfoKonfTable1.dataset.length == 0) {
        Page.Widgets.wizardstep8.disablenext = true;
    } else {
        Page.Widgets.wizardstep8.disablenext = false;
    }

};

Page.svInsertTaxonSuccess = function(variable, data) {
    Page.Widgets.wizardstep9.disablenext = false;
    Page.validateWizardTax();
};

Page.svDeleteTaxonSuccess = function(variable, data) {
    if (Page.Widgets.listTax.dataset.length == 0) {
        Page.Widgets.wizardstep9.disablenext = true;
        Page.validateWizardTax();
    } else {
        Page.Widgets.wizardstep9.disablenext = false;
        Page.validateWizardTax();
    }
};

Page.directorEmailChange = function($event, widget, newVal, oldVal) {
    var email = Page.Widgets.directorEmail.datavalue;
    var emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(email)) {
        Page.Actions.naEmailFormat.invoke();
        Page.Widgets.wizardstep7.disablenext = true;
    } else {
        Page.Widgets.wizardstep7.disablenext = false;
    }
};

Page.insertPIC = function($event, widget) {
    return new Promise(async(resolve, reject) => {
        let inputData = Page.Widgets.mGetDataPICList1.dataset
        for (i = 0; i < inputData.length; i++) {
            await Page.Variables.svInsertPIC.invoke({
                    inputFields: {
                        "namePIC": Page.Widgets.mGetDataPICList1.dataset[i].name,
                        "emailPIC": Page.Widgets.mGetDataPICList1.dataset[i].email,
                        "phonePIC": Page.Widgets.mGetDataPICList1.dataset[i].mobilePhone,
                        "selectPositionPIC": Page.Widgets.mGetDataPICList1.dataset[i].position,
                        "selectAreaPIC": Page.Widgets.mGetDataPICList1.dataset[i].area,
                        "password": Page.Widgets.passEncrypt.caption,
                        "userName": Page.Widgets.reCompanyEmail.datavalue
                    }
                },
                function(error) {
                    console.log('error pic')
                })

            if (i === inputData.length - 1) {
                resolve(true)
            };
        }
    });
};


Page.insertSupDoc = function(data) {
    return new Promise(async(resolve, reject) => {
        let inputData = Page.Widgets.mGetDataSupDocList1.dataset;
        if (Page.Widgets.mGetDataSupDocList1.dataset.length > 0) {
            for (i = 0; i < inputData.length; i++) {
                await Page.Variables.svInsertSupDoc.invoke({
                    inputFields: {
                        "idVendor": Page.Variables.mGetMaxVendor.dataSet.id,
                        "docSupUpload": Page.Widgets.mGetDataSupDocList1.dataset[i].fileUrl,
                        "fileName": Page.Widgets.mGetDataSupDocList1.dataset[i].fileName,
                        "remark": Page.Widgets.mGetDataSupDocList1.dataset[i].remark
                    }
                })
            }
        }
        resolve(true)
    });
};

Page.btnSaveBankClick = function($event, widget) {
    Page.Variables.mGetDataBank.addItem({
        "id": Date.now(),
        "beneficiaryStatus": Page.Widgets.beneficiaryStatus.datavalue,
        "countryCode": Page.Widgets.sCountryCode.datavalue,
        "beneficiaryType": Page.Widgets.beneficiaryType.datavalue,
        "accountName": Page.Widgets.accountName.datavalue,
        "bankKey": Page.Widgets.bankName.datavalue.abBaey,
        "bankName": Page.Widgets.bankName.datavalue.abBame,
        "currency": Page.Widgets.currency.datavalue,
        "branch": Page.Widgets.branch.datavalue,
        "accountNumber": Page.Widgets.accountNumber.datavalue,
        "supDocBank": Page.Variables.getSupDocBank.dataSet[0].pathFile,
        "fileNameSupDocBank": Page.Variables.getSupDocBank.dataSet[0].fileName,
    });
    // Page.Widgets.wizardstep8.disablenext = false;
    Page.Widgets.bankInformation.datavalue = "uploaded"
};

Page.btnUpdateBankClick = function($event, widget) {
    let index = Page.Variables.mGetDataBank.dataSet.findIndex(function(e) {
        return e.id == Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.id;
    });
    Page.Variables.mGetDataBank.setItem(index, {
        "id": Date.now(),
        "beneficiaryStatus": Page.Widgets.beneficiaryStatus.datavalue,
        "countryCode": Page.Widgets.sCountryCode.datavalue,
        "beneficiaryType": Page.Widgets.beneficiaryType.datavalue,
        "accountName": Page.Widgets.accountName.datavalue,
        "bankKey": Page.Widgets.bankName.datavalue.abBaey,
        "bankName": Page.Widgets.bankName.datavalue.abBame,
        "currency": Page.Widgets.currency.datavalue,
        "branch": Page.Widgets.branch.datavalue,
        "accountNumber": Page.Widgets.accountNumber.datavalue,
        "supDocBank": (Page.Variables.mGetDataBank.dataSet ? Page.Variables.getSupDocBank.dataSet[0].pathFile : Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.pathFile),
        "fileNameSupDocBank": (Page.Variables.mGetDataBank.dataSet ? Page.Variables.getSupDocBank.dataSet[0].fileName : Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.fileName),
    });
};

Page.insertBank = function($event, widget) {
    return new Promise(async(resolve, reject) => {
        let inputData = Page.Widgets.executeQGetBankInfoKonfTable1.dataset;
        for (i = 0; i < inputData.length; i++) {
            await Page.Variables.svInsertBank.invoke({
                inputFields: {
                    "beneficiaryStatus": Page.Widgets.executeQGetBankInfoKonfTable1.dataset[i].beneficiaryStatus,
                    "countryCode": Page.Widgets.executeQGetBankInfoKonfTable1.dataset[i].countryCode,
                    "beneficiaryType": Page.Widgets.executeQGetBankInfoKonfTable1.dataset[i].beneficiaryType,
                    "accountName": Page.Widgets.executeQGetBankInfoKonfTable1.dataset[i].accountName,
                    "bankName": Page.Widgets.executeQGetBankInfoKonfTable1.dataset[i].bankKey,
                    "currency": Page.Widgets.executeQGetBankInfoKonfTable1.dataset[i].currency,
                    "branch": Page.Widgets.executeQGetBankInfoKonfTable1.dataset[i].branch,
                    "accountNumber": Page.Widgets.executeQGetBankInfoKonfTable1.dataset[i].accountNumber,
                    "userName": Page.Widgets.reCompanyEmail.datavalue,
                }
            })

            if (i === inputData.length - 1) {
                resolve(true)
            };
        }
    });
};

Page.supDocBankSelect = function($event, widget, selectedFiles) {
    Page.Widgets.fileNameBank.show = false;
};

Page.btnSaveTaxClick = function($event, widget) {
    Page.Variables.mGetDataTax.addItem({
        "id": Date.now(),
        "companyCode": Page.Widgets.selectCompany.datavalue,
        // "serviceOfficeAddress": Page.Widgets.addressTaxService.datavalue,
        "dateTo": Page.Widgets.validCertificateTo.datavalue,
        // "addressNpwp": Page.Widgets.addressNpwp.datavalue,
        // "serviceOffice": Page.Widgets.taxServiceOffice.datavalue,
        "exemptionNumber": Page.Widgets.taxCertificateNumber.datavalue,
        // "taxId": Page.Widgets.taxtId.datavalue,
        // "nameNpwp": Page.Widgets.nameNpwp.datavalue,
        // "effectiveSince": Page.Widgets.effectiveNpwp.datavalue,
        "dateFrom": Page.Widgets.validCertificateFrom.datavalue,
        "exemptionType": Page.Widgets.typeCertificateNpwp.datavalue,
        "fileNameExemption": (Page.Variables.getTaxCertificate.dataSet[0] ? Page.Variables.getTaxCertificate.dataSet[0].fileName : null),
        "fileExemption": (Page.Variables.getTaxCertificate.dataSet[0] ? Page.Variables.getTaxCertificate.dataSet[0].pathFile : null),
        "userName": Page.Widgets.reCompanyEmail.datavalue,
    });
    Page.Widgets.wizardstep9.disablenext = false;
    Page.validateWizardTax();
    Page.Variables.getTaxCertificate.dataSet = [];
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
        "dateFrom": Page.Widgets.validCertificateFrom.datavalue,
        "exemptionType": Page.Widgets.typeCertificateNpwp.datavalue,
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
    return new Promise(async(resolve, reject) => {
        let inputData = Page.Widgets.listTax.dataset;
        for (i = 0; i < inputData.length; i++) {
            if (Page.Variables.mGetDataTax.dataSet.length > 0) {
                await Page.Variables.svInsertTax.invoke({
                    inputFields: {
                        "companyCode": Page.Widgets.listTax.dataset[i].companyCode,
                        "dateTo": Page.Widgets.listTax.dataset[i].dateTo,
                        "exemptionNumber": Page.Widgets.listTax.dataset[i].exemptionNumber,
                        "dateFrom": Page.Widgets.listTax.dataset[i].dateFrom,
                        "exemptionType": Page.Widgets.listTax.dataset[i].exemptionType,
                        "userName": Page.Widgets.listTax.dataset[i].userName,
                    }
                })
            }
        }
        resolve(true)
    });
};

Page.wizardstep9Load = function(widget, stepIndex) {
    var taxMask = IMask(
        document.getElementById('inputTax'), {
            mask: '00.000.000.0-000.000'
        });
    if (Page.pageParams.vendorType == 'NONNPWP') {
        Page.Widgets.wizardstep9.disablenext = false;
        Page.validateWizardTax();
    }
    if (Page.pageParams.vendorType == 'NPWP') {
        Page.Widgets.wizardstep9.disablenext = true;
        Page.validateWizardTax();
    }
    if (Page.Widgets.nameNpwp.datavalue && Page.Widgets.nameNpwp.datavalue.length > 0) {
        Page.Widgets.wizardstep9.disablenext = false;
        Page.validateWizardTax();
    }
    if (Page.Widgets.select16.datavalue == 'PT') {
        Page.Widgets.selectVaer.datavalue = 1
        Page.Widgets.layoutgrid21.show = true
    }
    console.log(Page.Widgets.select16.datavalue)
    // if (Page.Widgets.listTax.dataset.length == 0) {
    //     Page.Widgets.wizardstep9.disablenext = true;
    // } else {
    //     Page.Widgets.wizardstep9.disablenext = false;
    // }

};

Page.btn_deleteBankClick = function($event, widget) {
    Page.Variables.mGetDataBank.removeItem(Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem);
    if (Page.Widgets.executeQGetBankInfoKonfTable1.dataset.length == 0) {
        Page.Widgets.wizardstep8.disablenext = true;
    } else {
        Page.Widgets.wizardstep8.disablenext = false;
    }
};

Page.btn_deleteTaxClick = function($event, widget) {
    Page.Variables.mGetDataTax.removeItem(Page.Widgets.listTax.selecteditem);
    if (Page.Widgets.listTax.dataset.length == 0) {
        Page.Widgets.wizardstep9.disablenext = true;
        Page.validateWizardTax();
    } else {
        Page.Widgets.wizardstep9.disablenext = false;
        Page.validateWizardTax();
    }
};

// insert all document attachment
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
            };
        }
    });
};

Page.insertNpwp = function(data) {
    return new Promise(async(resolve, reject) => {
        // let inputData = Page.Widgets.listTax.dataset;
        // for (i = 0; i < inputData.length; i++) {
        if (Page.Variables.getNpwp.dataSet.length > 0) {
            await Page.Variables.svInsertNpwp.invoke({
                inputFields: {
                    "idVendor": Page.Variables.mGetMaxVendor.dataSet.id,
                    "fileUrl": Page.Variables.getNpwp.dataSet[0].pathFile,
                    "fileName": Page.Variables.getNpwp.dataSet[0].fileName,
                }
            })
        }
        resolve(true)
        //     }
    });
};

Page.insertSppkp = function(data) {
    return new Promise(async(resolve, reject) => {
        let inputData = Page.Widgets.listTax.dataset;
        for (i = 0; i < inputData.length; i++) {
            if (Page.Widgets.selectVaer.datavalue == 1) {
                await Page.Variables.svInsertSppkp.invoke({
                    inputFields: {
                        "idVendor": Page.Variables.mGetMaxVendor.dataSet.id,
                        "fileUrl": Page.Variables.getSppkp.dataSet[0].pathFile,
                        "fileName": Page.Variables.getSppkp.dataSet[0].fileName,
                    }
                })
            }
        }
        resolve(true)
    })
};

Page.insertExemption = function(data) {
    return new Promise(async(resolve, reject) => {
        let inputData = Page.Widgets.listTax.dataset;
        for (i = 0; i < inputData.length; i++) {
            if (Page.Variables.mGetDataTax.dataSet > 0) {
                await Page.Variables.svInsertExemption.invoke({
                    inputFields: {
                        "idVendor": Page.Variables.mGetMaxVendor.dataSet.id,
                        "fileUrl": Page.Widgets.listTax.dataset[i].fileExemption,
                        "fileName": Page.Widgets.listTax.dataset[i].fileNameExemption,
                    }
                })
            }
        }
        resolve(true)
    })
};

Page.btn_checkSupDocClick = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Widgets.mGetDataSupDocList1.selecteditem.fileUrl);
};

Page.bankNameChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.btnUpdateBank.disabled = false;
};

Page.updateBankOpened = function($event, widget) {
    Page.Widgets.btnUpdateBank.disabled = true;
};

Page.picture19_4Click = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.supDocBank);
};

Page.button23Click = function($event, widget) {
    window.open(Page.Variables.getNpwp.dataSet[0].pathFile);
};

Page.webCompanyChange = function($event, widget, newVal, oldVal) {
    var web = Page.Widgets.webCompany.datavalue;
    var webRegex = /\.\S+/;
    if (!webRegex.test(web)) {
        Page.Actions.naWebFormat.invoke();
    }
};

Page.emailPICChange = function($event, widget, item, currentItemWidgets, newVal, oldVal) {
    var email = Page.Widgets.emailPIC.datavalue;
    var emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(email)) {
        Page.Actions.naEmailFormat.invoke();
    }
};

Page.btn_checkDocBankClick = function($event, widget) {
    window.open(Page.Variables.getSupDocBank.dataSet[0].pathFile);
};

// split addres into 3 address input
Page.addressNpwpChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.npwpadd1.datavalue = Page.Widgets.addressNpwp.datavalue.slice(0, 60);
    Page.Widgets.npwpadd2.datavalue = Page.Widgets.addressNpwp.datavalue.slice(61, 100);
    Page.Widgets.npwpadd3.datavalue = Page.Widgets.addressNpwp.datavalue.slice(100, 140);
    Page.Widgets.taxIdText.datavalue = Page.Variables.modelGeneral.dataSet.npwpNumber;
};

Page.btn_checkCatClick = function($event, widget) {
    window.open(Page.Variables.getCatalogue.dataSet[0].pathFile)
};

Page.checkTaxCertificateClick = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Widgets.listTax.selecteditem.fileExemption);
};

Page.checkTaxConfirmClick = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Widgets.listTax.selecteditem.sppkpUpload);
};

Page.btn_checkFileNpwpUpdateClick = function($event, widget) {
    window.open(Page.Variables.getNpwp.dataSet[0].pathFile);
};

Page.btn_checkFileSppkpClick = function($event, widget) {
    window.open(Page.Variables.getSppkp.dataSet[0].pathFile);
};

Page.btn_checkFileExemptionClick = function($event, widget) {
    window.open(Page.Variables.getTaxCertificate.dataSet[0].pathFile);
};

Page.insertVendor = function($event, widget) {
    new Promise(async(resolve, reject) => {
        var vendorTittle = []
        if (Page.Widgets.other.datavalue) {
            vendorTittle = Page.Widgets.other.datavalue
        } else {
            vendorTittle = Page.pageParams.vendorType === 'NPWP' ? Page.Widgets.select16.datavalue : Page.Widgets.selectTitleNonNpwp.datavalue
        }
        await Page.Variables.svInsertVendor.invoke({
            inputFields: {
                "nib": Page.Widgets.nib.datavalue,
                "directorEmail": Page.Widgets.directorEmail.datavalue,
                "city": Page.Widgets.city.datavalue,
                "veName": Page.Widgets.VeName.datavalue,
                "picName": Page.Variables.getCompanyPic.dataSet[0].fileName,
                "rtRw": Page.Widgets.rtRw.datavalue,
                "password": Page.Widgets.passEncrypt.caption,
                "businessFieldSector": Page.Widgets.businessFieldSector.datavalue,
                "picUpload": Page.Variables.getCompanyPic.dataSet[0].pathFile,
                "catName": Page.Variables.getCatalogue.dataSet[0].fileName,
                "companyEmail": Page.Widgets.companyEmail.datavalue,
                "province": Page.Widgets.sProvince.datavalue,
                "pos": Page.Widgets.pos.datavalue,
                "idDirector": Page.Widgets.idDirector.datavalue,
                "tittle": vendorTittle,
                "radioLocation": Page.Widgets.radioLocation.datavalue,
                "telephone": Page.Widgets.telephone.datavalue,
                "kelurahan": Page.Widgets.kelurahan.datavalue,
                "officeAddress": Page.Widgets.officeAddress.datavalue,
                "catUpload": Page.Variables.getCatalogue.dataSet[0].pathFile,
                "nameDirector": Page.Widgets.nameDirector.datavalue,
                "mobilePhone": Page.Widgets.mobilePhone.datavalue,
                "kecamatan": Page.Widgets.kecamatan.datavalue,
                "doi": Page.Widgets.doi.datavalue,
                "webCompany": Page.Widgets.webCompany.datavalue,
                "npwpName": Page.Widgets.nameNpwp.datavalue,
                "npwpNumber": Page.Variables.modelGeneral.dataSet.npwpNumber,
                "npwpAddress": Page.Widgets.npwpadd1.datavalue,
                "npwpAddress2": Page.Widgets.npwpadd2.datavalue,
                "npwpAddress3": Page.Widgets.npwpadd3.datavalue,
                "npwpEffective": Page.Widgets.effectiveNpwp.datavalue,
                "npwpType": Page.Widgets.typeNpwp.datavalue,
                "sppkpNumber": Page.Widgets.taxConfirmationNumber.datavalue,
                "statSppkp": Page.Widgets.selectVaer.datavalue,
                "vsi": Page.Widgets.labelVsi.caption,
                "addServiceOffice": Page.Widgets.addressTaxService.datavalue,
                "serviceOffice": Page.Widgets.taxServiceOffice.datavalue
            }
        })
        resolve(true)
    })
}

//to do list lemburan
Page.wizardDone = function(widget, steps) {
    Page.Variables.svInsertVendor.invoke()
        .then(async function(data) {
            try {
                await Page.insertPIC();
                await Page.insertBank();
                await Page.insertDocBank(data);
                await Page.insertTax();
                await Page.insertNpwp(data);
                await Page.insertExemption(data);
                await Page.insertSppkp(data);
                await Page.insertSupDoc(data);
                Page.Actions.naAddSuccess.invoke();
                Page.App.Actions.goToPage_LoginAIO.invoke({
                    data: {
                        status: "success"
                    }
                });
            } catch (e) {
                console.log(e);
                Page.Widgets.labelVsi.caption = Math.floor(Math.random() * 10000001);
            }
        }).catch(e => {
            Page.Widgets.labelVsi.caption = Math.floor(Math.random() * 10000001);
            console.log(e)
        });
};
Page.wizardstep10Load = function(widget, stepIndex) {
    Page.Variables.modelGeneral.setValue("npwpNumber", $("#inputTax").val().replace(/\D+/g, ''));
};
Page.selectVaerChange = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.selectVaer.datavalue == 1) {
        Page.Widgets.layoutgrid21.show = true
    } else if (Page.Widgets.selectVaer.datavalue == 0) {
        Page.Widgets.layoutgrid21.show = false
    }
    Page.validateWizardTax();
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

Page.typeNpwpChange = function($event, widget, newVal, oldVal) {
    Page.Variables.modelGeneral.setValue("npwpNumber", $("#inputTax").val().replace(/\D+/g, ''))
    Page.Widgets.taxIdText.datavalue = Page.Variables.modelGeneral.dataSet.npwpNumber;
};

Page.checkNpwpDuplicate = function($event, widget, newVal, oldVal) {
    console.log('hasil npwpnya', Page.Variables.modelGeneral.dataSet.npwpNumber);
}

//send email
Page.sendEmail = function($event, widget) {
    var email = []
    if (Page.Widgets.selectAreaPIC.datavalue == 'PHOF') {
        email = "gsaputra@aio.co.id"
    } else {
        email = "dsetiavitri@aio.co.id"
    }

    Page.Variables.svNotifyAdminVendorRegister.invoke({
        inputFields: {
            RequestBody: {
                "to": email,
                "cc": "driyadi.aio.co.id;mzaelani@aio.co.id",
                "subject": `Registration Vendor`,
                "body": `<p> Terdapat vendor yang baru saja mendaftar atas nama ${Page.Widgets.VeName.datavalue} </p>
                        <p>Silahkan cek list data vendor pada link berikut:
                        <a href="http://ivendor.aio.co.id" target="_blank">Buka Link</a>
                        <p>`
            }
        }
    }, function(data) {
        console.log(data);
    }, function(error) {});
}


Page.validateWizardTax = function() {
    let valid = true;
    //validation start
    //npwp

    let WidgetsArray = ['taxConfirmationNumber', 'taxServiceOffice', 'addressTaxService']
    WidgetsArray.forEach(x => {
        if (!Page.Widgets[x].datavalue) {
            valid = false;
            $(`input[name="${x}"]`).addClass("ng-invalid")
        } else {
            $(`input[name="${x}"]`).removeClass("ng-invalid")
        }
    })

    //type npwp
    // if (!Page.Widgets.typeNpwp.datavalue) {
    //     valid = false;
    //     $('input[name="typeNpwp"]').addClass("ng-invalid")
    // } else {
    //     $('input[name="typeNpwp"]').removeClass("ng-invalid")
    // }
    // //name npwpNumber
    // if (!Page.Widgets.nameNpwp.datavalue) {
    //     valid = false;
    //     $('input[name="nameNpwp"]').addClass("ng-invalid")
    // } else {
    //     $('input[name="nameNpwp"]').removeClass("ng-invalid")
    // }
    // if (!Page.Widgets.addressNpwp.datavalue) {
    //     valid = false;
    //     $('input[name="addressNpwp"]').addClass("ng-invalid")
    // } else {
    //     $('input[name="addressNpwp"]').removeClass("ng-invalid")
    // }



    //bypass validation if non npwp
    if (Page.pageParams.vendorType == 'NONNPWP') {
        valid = true;
    }
    if (Page.Widgets.selectVaer.datavalue == 0) {
        valid = true;
    } else if (Page.Widgets.selectVaer.datavalue == 1) {
        if (Page.Variables.getSppkp.dataSet[0].fileName) {
            valid = true;
        } else {
            valid = false;
        }
    }
    Page.Widgets.wizardstep9.disablenext = !valid;
    return valid;
}
Page.buttonConfirmDeclarationClick = function($event, widget) {
    Page.Widgets.checkbox2.datavalue = true;
    Page.Widgets.dialogDeclare.close();
};
Page.checkbox2Click = function($event, widget) {
    if (!Page.Widgets.checkbox2.datavalue) {
        Page.Widgets.dialogDeclare.open();
    }
};
Page.dialogDeclareOpened = function($event, widget) {
    Page.Widgets.checkbox2.datavalue = false;
};

Page.wizardstep7Next = function(widget, currentStep, stepIndex) {

};
Page.picture1Click = function($event, widget) {
    console.log('wizard', Page.Widgets.wizard.currentStep);
    console.log('wizard7', Page.Widgets.wizardstep7);
};


Page.callValidationDialog = function(title, body) {
    Page.Variables.mDialogStatus.dataSet.title = title;
    Page.Variables.mDialogStatus.dataSet.value = body;
    Page.Widgets.dialogAlert.open();
}

Page.buttonNextClick = async function($event, widget) {
    console.log('wizard', Page.Widgets.wizard.currentStep);
    if (Page.Widgets.wizard.currentStep.name == "wizardstep9" && Page.pageParams.vendorType == 'NPWP') {
        if (!Page.validateCurrentWizard()) {
            Page.callValidationDialog('warning', 'invalid');
        } else {
            Page.typeNpwpChange();
            await Page.Variables.svGetNpwp.invoke({
                inputFields: {
                    "Npwp": Page.Variables.modelGeneral.dataSet.npwpNumber
                }
            }).then((data) => {
                let vendorData = JSON.parse(data.body);
                if (vendorData.content.length > 0) {
                    Page.callValidationDialog('info', 'duplicate');
                } else {
                    Page.Widgets.wizard.next();
                }
            }).catch((e) => {
                Page.callValidationDialog('info', 'error');
            });
        }
    } else {
        if (Page.validateCurrentWizard()) {
            Page.Widgets.wizard.next();
        }
    }

};

Page.buttonPreviousClick = function($event, widget) {
    Page.Widgets.wizard.prev();
};

Page.validateCurrentWizard = function() {
    let result = true;
    if (!Page.Widgets.wizard.currentStep.isValid) {
        Page.callValidationDialog('warning', 'invalid');
        result = false;
    }
    return result;
}
Page.buttonDoneClick = function($event, widget) {
    if (Page.validateCurrentWizard()) {
        Page.Widgets.wizard.done();
    }
};
Page.buttonSkipClick = function($event, widget) {
    Page.Widgets.wizard.skip();
};