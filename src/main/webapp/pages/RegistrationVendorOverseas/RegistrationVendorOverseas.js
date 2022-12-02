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

    $("#inputTax").inputmask({
        "mask": "00.000.000.0-000.000"
    });

    $('form').disableAutoFill();


};

Page.selectTitleChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.selectTitle.datavalue.invoke();
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
};

Page.FileServiceUploadCertiDomonSuccess = function(variable, data) {
    Page.Variables.getCertiDom.dataSet = [{
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
};

Page.btn_addRowSupDocClick = function($event, widget) {
    if (Page.Variables.mGetDataSupDoc.dataSet.length == 0) {
        var max_id = 0;
    } else {
        max_id = Math.max.apply(Math, Page.Variables.mGetDataSupDoc.dataSet.map(function(item) {
            return item.id;
        }))
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
};

Page.matchEmail = function($event, $operation, $data, options) {
    if (Page.Widgets.companyEmail.datavalue != Page.Widgets.reCompanyEmail.datavalue) {
        Page.Actions.naEmailNotMatch.invoke();
        Page.Widgets.wizardstep7_1.disablenext = true;
    } else {
        Page.Widgets.wizardstep7_1.disablenext = false;
    }
};

Page.matchPassword = function($event, $operation, $data, options) {
    if (Page.Widgets.password.datavalue != Page.Widgets.retypePassword.datavalue) {
        Page.Actions.naPasswordNotMatch.invoke();
        Page.Widgets.wizardstep7_1.disablenext = true;
    } else {
        Page.Widgets.wizardstep7_1.disablenext = false;
    }
};

Page.companyEmailChange = function($event, widget, newVal, oldVal) {
    var email = Page.Widgets.companyEmail.datavalue;
    var emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(email)) {
        Page.Actions.naEmailFormat.invoke();
        Page.Widgets.wizardstep7_1.disablenext = true;
    } else {
        Page.Widgets.wizardstep7_1.disablenext = false;
    }
};


Page.reCompanyEmailChange = function($event, widget, newVal, oldVal) {
    Page.matchEmail();
    var email = Page.Widgets.reCompanyEmail.datavalue;
    var emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(email)) {
        Page.Actions.naEmailFormat.invoke();
        Page.Widgets.wizardstep7_1.disablenext = true;
    } else {
        Page.Widgets.wizardstep7_1.disablenext = false;
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
};

Page.svDeleteTaxKonfonSuccess = function(variable, data) {
    if (Page.Widgets.listTax.dataset.length == 0) {
        Page.Widgets.wizardstep9.disablenext = true;
    } else {
        Page.Widgets.wizardstep9.disablenext = false;
    };
};

Page.directorEmailChange = function($event, widget, newVal, oldVal) {
    var email = Page.Widgets.directorEmail.datavalue;
    var emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(email)) {
        Page.Actions.naEmailFormat.invoke();
        Page.Widgets.wizardstep7_1.disablenext = true;
    } else {
        Page.Widgets.wizardstep7_1.disablenext = false;
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
            });

            if (i === inputData.length - 1) {
                resolve(true)
            }
        }
    });
};

Page.sVeCodeOverseasChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.sVeCodeOverseas.datavalue;
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
    Page.Widgets.wizardstep8.disablenext = false;
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
                    "userName": Page.Widgets.reCompanyEmail.datavalue
                }
            })

            if (i === inputData.length - 1) {
                resolve(true)
            }
        }
    })
};

Page.btnSaveTaxClick = function($event, widget) {
    Page.Variables.mGetDataTax.addItem({
        "id": Date.now(),
        "companyCode": Page.Widgets.selectCompany.datavalue,
        "validFrom": Page.Widgets.validFrom.datavalue,
        // "taxNumber": Page.Widgets.taxtNumber.datavalue,
        "validTo": Page.Widgets.validTo.datavalue,
        // "taxEffective": Page.Widgets.taxEffective.datavalue,
        // "taxFileUpload": (Page.Variables.getNpwp.dataSet[0] ? Page.Variables.getNpwp.dataSet[0].pathFile : null),
        // "taxFileName": (Page.Variables.getNpwp.dataSet[0] ? Page.Variables.getNpwp.dataSet[0].fileName : null),
        "fileNameCertiDom": (Page.Variables.getCertiDom.dataSet[0] ? Page.Variables.getCertiDom.dataSet[0].fileName : null),
        "fileCertiDom": (Page.Variables.getCertiDom.dataSet[0] ? Page.Variables.getCertiDom.dataSet[0].pathFile : null),
    });
    Page.Widgets.wizardstep9.disablenext = false;
    Page.Variables.getCertiDom.dataSet = [];
};

Page.btnUpdateTaxClick = function($event, widget) {
    let index = Page.Variables.mGetDataTax.dataSet.findIndex(function(e) {
        return e.id == Page.Widgets.listTax.selecteditem.id;
    });
    let parameter = {
        "id": Date.now(),
        "companyCode": Page.Widgets.selectCompany.datavalue,
        "validFrom": Page.Widgets.validFrom.datavalue,
        "validTo": Page.Widgets.validTo.datavalue,
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
    return new Promise(async(resolve, reject) => {
        let inputData = Page.Widgets.listTax.dataset;
        for (i = 0; i < inputData.length; i++) {
            await Page.Variables.svInsertTax.invoke({
                inputFields: {
                    "companyCode": Page.Widgets.listTax.dataset[i].companyCode,
                    "dateFrom": Page.Widgets.listTax.dataset[i].validFrom,
                    "dateTo": Page.Widgets.listTax.dataset[i].validTo,
                    "userName": Page.Widgets.listTax.dataset[i].userName,
                }

            });
        }
        resolve(true)
    });
};

Page.checkTaxConfirmClick = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Variables.getCertiDom.dataSet[0].fileName);
};

Page.btnDeleteBankClick = function($event, widget) {
    Page.Variables.mGetDataBank.removeItem(Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem);
    if (Page.Widgets.executeQGetBankInfoKonfTable1.dataset.length == 0) {
        Page.Widgets.wizardstep8.disablenext = true;
    } else {
        Page.Widgets.wizardstep8.disablenext = false;
    }
};

Page.btnDeleteTaxClick = function($event, widget) {
    Page.Variables.mGetDataTax.removeItem(Page.Widgets.listTax.selecteditem);
    if (Page.Widgets.listTax.dataset.length == 0) {
        Page.Widgets.wizardstep9.disablenext = true;
    } else {
        Page.Widgets.wizardstep9.disablenext = false;
    }
};

// insert all document attcahment
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
    });
};

Page.insertCertidom = function(data) {
    return new Promise(async(resolve, reject) => {
        let inputData = Page.Widgets.listTax.dataset;
        for (i = 0; i < inputData.length; i++) {
            await Page.Variables.svInsertCertidom.invoke({
                inputFields: {
                    "idVendor": Page.Variables.mGetMaxVendor.dataSet.id,
                    "fileUrl": Page.Widgets.listTax.dataset[i].fileCertiDom,
                    "fileName": Page.Widgets.listTax.dataset[i].fileNameCertiDom,
                }
            })
        }
        resolve(true)
    });
};

Page.checkCertidomClick = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Widgets.listTax.selecteditem.fileCertiDom);
};

Page.sCountryCodeChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.btnUpdateBank.disabled = false;
};

Page.updateBankOpened = function($event, widget) {
    Page.Widgets.btnUpdateBank.disabled = true;
};

Page.picture13Click = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Widgets.executeQGetBankInfoKonfTable1.selecteditem.supDocBank);
};

Page.button25Click = function($event, widget) {
    window.open(Page.Variables.getCatalogue.dataSet[0].pathFile);
};

Page.btn_checkFileBankClick = function($event, widget) {
    window.open(Page.Variables.getSupDocBank.dataSet[0].pathFile);
};

Page.button27_1Click = function($event, widget) {
    Page.insertBank();
};

Page.emailPICChange = function($event, widget, item, currentItemWidgets, newVal, oldVal) {
    var email = Page.Widgets.emailPIC.datavalue;
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

Page.btn_checkSupDocClick = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Widgets.mGetDataSupDocList1.selecteditem.fileUrl);
};
Page.checkTaxCertificateClick = function($event, widget, item, currentItemWidgets) {
    window.open(Page.Widgets.listTax.selecteditem.taxFileUpload);
};
Page.btnCheckTaxFileClick = function($event, widget) {
    window.open(Page.Variables.getNpwp.dataSet[0].pathFile);
};
Page.btn_checkTaxFileUpdateClick = function($event, widget) {
    window.open(Page.Variables.getNpwp.dataSet[0].pathFile);
};

Page.wizard2_1Done = function(widget, steps) {
    Page.Variables.svInsertVendor.invoke()
        .then(async function(data) {
            await Page.insertPIC();
            await Page.insertBank();
            await Page.insertDocBank(data);
            await Page.insertTax();
            await Page.insertNpwp(data);
            await Page.insertCertidom(data);
            await Page.insertSupDoc(data);
            Page.Actions.naAddSuccess.invoke();
            Page.App.Actions.goToPage_LoginAIO.invoke({
                data: {
                    status: "success"
                }
            });
        })
};

Page.wizardstep10Load = function(widget, stepIndex) {
    Page.Variables.modelGeneral.setValue("npwpNumber", $("#inputTax").val())
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
Page.offceAddressChange = function($event, widget, newVal, oldVal) {
    let str = newVal;
    if (str.length > 60) {
        Page.Variables.addressNPWP.dataSet.one = str.match(/.{1,60}/g)[0];
        let str2 = str.substring(60);
        Page.Variables.addressNPWP.dataSet.two = str2.match(/.{1,40}/g)[0];
        Page.Variables.addressNPWP.dataSet.three = str2.match(/.{1,40}/g)[1];
    } else {
        Page.Variables.addressNPWP.dataSet.one = str;
        Page.Variables.addressNPWP.dataSet.two = "";
        Page.Variables.addressNPWP.dataSet.three = "";
    }
};