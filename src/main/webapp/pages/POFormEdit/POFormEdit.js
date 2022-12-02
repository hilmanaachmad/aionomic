/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */



/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    if (!Page.pageParams.id) {
        App.Actions.goToPage_POList.invoke()
    } else {
        if (Page.Widgets.spinner) {
            Page.Widgets.spinner.show = true
        }
    }
    // Page.Variables.statusShowPDF.dataSet.dataValue = true
};

var statusRfq = 'YES'
var statusSubmit = 'Open'

Page.calulcateTotalAmountLine = function(data) {
    var amount = data.poQty * data.unitPrice
    var discount = amount * ((data.discount) ? data.discount : 0) / 100
    data.afterDiscount = amount - discount
    return App.formatCurrency(amount - discount)
}


Page.DBPurchaseOrderonResult = async function(variable, data) {
    if (Page.Widgets.spinner) {
        Page.Widgets.spinner.show = false
    }

    if (data.length > 0) {
        Page.Variables.MODELVendor.dataSet = {
            vendorCode: data[0].vendorCode,
            vendorName: data[0].vendorName
        }


        if (data[0].rfqNumber) {
            Page.Variables.MODELPaymentMethod.dataSet = [{
                name: 'Purchase Order',
                dataValue: 'purchase_order'
            }]
            if (Page.Widgets.headPaymentMethod) {
                Page.Widgets.headPaymentMethod.datavalue = data[0].paymentMethod
            }
            statusRfq = 'YES'

        } else if (!data[0].rfqNumber && data[0].paymentMethod.includes("purchase_order")) {
            Page.Variables.MODELPaymentMethod.dataSet = [{
                name: 'Purchase Order',
                dataValue: 'purchase_order'
            }]
            if (Page.Widgets.headPaymentMethod) {
                Page.Widgets.headPaymentMethod.datavalue = data[0].paymentMethod
            }

            statusRfq = 'NO'
        }

        await Page.Variables.DBDepartmentCode.invoke({
            "inputFields": {
                "departmentId": data[0].purchOrg
            }
        })



        // else if (!data[0].rfqNumber && data[0].paymentMethod.includes("advance_claim")) {
        //     Page.Variables.MODELPaymentMethod.dataSet = [{
        //         name: 'Advance/Claim Employee',
        //         dataValue: 'advance_claim'
        //     }]
        //     if (Page.Widgets.headPaymentMethod) {
        //         Page.Widgets.headPaymentMethod.datavalue = data[0].paymentMethod
        //     }

        //     statusRfq = 'NO'
        // }

        Page.Variables.MODELPurchaseBy.dataSet = Page.Variables.MODELPurchaseBy.dataSet.filter(item => item.dataValue === data[0].purchaseBy)

    }

    if (Page.Widgets.headTotalAmount) {
        if (data.length > 0) {
            Page.Widgets.headTotalAmount.datavalue = App.formatCurrency(data[0].totalAmount)
        }
    }

    // if (Page.Widgets.subTotal) {
    //     Page.CalculateTotal()
    // } else {
    setTimeout(function() {
        Page.CalculateTotal()
    }, 3000);
    // }

};


Page.CalculateTotal = function() {
    var subTotal = Page.Widgets.tableData.listItems._results.reduce((prep, data) =>
        prep + ((data.item.unitPrice * data.item.poQty) - ((data.item.unitPrice * data.item.poQty) * data.item.discount / 100)), 0)
    subTotal = (subTotal !== NaN) ? subTotal : 0
    Page.Widgets.subTotal.datavalue = App.formatCurrency(subTotal)
    var vat = Page.Widgets.vatPercentage.datavalue ? Number(Page.Widgets.vatPercentage.datavalue) : 0
    var vatTotal = Math.round(((subTotal * vat) / 100))
    var packingCost = Page.Widgets.totalPackingCost.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    var shippingCost = Page.Widgets.totalShippingCost.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    var otherCost = Page.Widgets.totalOtherCost.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    var costFromQuotation = Number(packingCost) + Number(shippingCost) + Number(otherCost)
    Page.Widgets.totalPercentage.datavalue = (vat === 0) ? vat : App.formatCurrency(vatTotal)
    Page.Widgets.totalAmount.datavalue = (vat === 0) ? subTotal + vatTotal + costFromQuotation : App.formatCurrency(subTotal + vatTotal + costFromQuotation)
}


Page.btnSubmitClick = function($event, widget) {
    statusSubmit = 'Open'
    Page.submitData()
};

Page.btnSaveClick = function($event, widget) {
    statusSubmit = 'Draft'
    Page.submitData()
};

Page.btnBackClick = function($event, widget) {
    window.history.back();
};



Page.headCompanyKeypress = function($event, widget) {
    Page.Variables.MODELMessageError.setValue('headCompany', null)
};
Page.headDocTypeChange = function($event, widget, newVal, oldVal) {
    Page.Variables.MODELMessageError.setValue('headDocType', null)
};
Page.headPurchaseOrgChange = function($event, widget, newVal, oldVal) {
    Page.Variables.MODELMessageError.setValue('headPurchaseOrg', null)
};

Page.headPaymentMethodChange = function($event, widget, newVal, oldVal) {
    Page.Variables.MODELMessageError.setValue('headPaymentMethod', null)
};

Page.headTOPSelect = function($event, widget, selectedValue) {
    Page.Variables.MODELMessageError.setValue('headTOP', null)
};

Page.headVendorSelect = function($event, widget, selectedValue) {
    Page.Variables.MODELMessageError.setValue('headVendor', null)
};
Page.headPurchaseGroupSelect = function($event, widget, selectedValue) {
    Page.Variables.MODELMessageError.setValue('headPurchaseGroup', null)
};
Page.headShipmentToKeypress = function($event, widget) {
    Page.Variables.MODELMessageError.setValue('headShipmentTo', null)
};



Page.submitData = async function() {

    // if (!(Page.Variables.DBPurchaseOrder.dataSet[0].rfqNumber && (Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'deleted' && Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'closed'))) {
    var subTotal = Page.Widgets.tableData.listItems._results.reduce((prep, data) =>
        prep + ((data.item.unitPrice * data.item.poQty)), 0)
    subTotal = (subTotal !== NaN) ? subTotal : 0

    var subTotalPR = Page.Variables.DBPurchaseOrder.dataSet.reduce((prep, data) =>
        prep + ((data.unitPrice * data.poQty)), 0)
    subTotalPR = (subTotalPR !== NaN) ? subTotalPR : 0

    if (subTotal > subTotalPR) {
        App.Actions.appNotification.setMessage('PO amount cannot greather than PR')
        App.Actions.appNotification.setToasterClass("error")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        return false
    }
    // }


    var formValid = true
    if (!Page.Widgets.headCompany.datavalue) {
        Page.Variables.MODELMessageError.setValue('headCompany', 'error')
        formValid = false
    }
    if (!Page.Widgets.headDocType.datavalue) {
        Page.Variables.MODELMessageError.setValue('headDocType', 'error')
        formValid = false
    }

    if (!Page.Widgets.headPurchaseOrg.datavalue) {
        Page.Variables.MODELMessageError.setValue('headPurchaseOrg', 'error')
        formValid = false
    }

    if (!Page.Widgets.headPurchaseGroup.datavalue) {
        Page.Variables.MODELMessageError.setValue('headPurchaseGroup', 'error')
        formValid = false
    }

    if (!Page.Widgets.headVendor.datavalue) {
        Page.Variables.MODELMessageError.setValue('headVendor', 'error')
        formValid = false
    }

    if (!Page.Widgets.headPaymentMethod.datavalue) {
        Page.Variables.MODELMessageError.setValue('headPaymentMethod', 'error')
        formValid = false
    }

    if (!Page.Widgets.headTOP.datavalue) {
        Page.Variables.MODELMessageError.setValue('headTOP', 'error')
        formValid = false
    }

    if (!Page.Widgets.headShipmentTo.datavalue) {
        Page.Variables.MODELMessageError.setValue('headShipmentTo', 'error')
        formValid = false
    }

    if (formValid === false) {
        App.Actions.appNotification.setMessage('Please complete the form')
        App.Actions.appNotification.setToasterClass("error")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        return false
    }


    await Page.Variables.DBDepartmentCode.invoke({
        "inputFields": {
            "departmentId": Page.Widgets.headPurchaseOrg.datavalue
        }
    })

    var prCreator = Page.Variables.DBPurchaseOrder.dataSet[0].prCreator.toString().replace("emp::", '')

    var requestBy = Page.Variables.DBPurchaseOrder.dataSet[0].prRepUserId ? Page.Variables.DBEmployeeRepresentative.dataSet[0].nik.toString().replace("emp::", '') : Page.Variables.DBPurchaseOrder.dataSet[0].prCreator.toString().replace("emp::", '')

    var departmentCode = Page.Variables.DBDepartmentCode.dataSet.departmentCode
    var paymentMethod = Page.Widgets.headPaymentMethod.datavalue

    var dataBody = {
        id: Page.pageParams.id,
        companyId: Page.Variables.DBPurchaseOrder.dataSet[0].companyCode,
        docType: Page.Widgets.headDocType.datavalue,
        purchOrg: Page.Widgets.headPurchaseOrg.datavalue,
        purchGroup: Page.Widgets.headPurchaseGroup.datavalue,
        vendorCode: Page.Widgets.headVendor.datavalue.vendorCode,
        vendorName: Page.Widgets.headVendor.datavalue.vendorName.trim(),
        currency: Page.Widgets.headCurrency.datavalue,
        paymentMethod: Page.Widgets.headPaymentMethod.datavalue,
        top: Page.Widgets.headTOP.datavalue,
        reference: Page.Widgets.headReference.datavalue,
        ppn: Page.Widgets.headPPN.datavalue,
        shipmentTo: Page.Widgets.headShipmentTo.datavalue,
        totalAmount: Page.Widgets.headTotalAmount.datavalue,
        additionalNotesPo: Page.Widgets.additionalNotesPO.datavalue,
        additionalNotesLegal: Page.Widgets.additionalNotesLegal.datavalue,
        status: statusSubmit,
        createdDate: App.HariIni(),
        subTotal: Page.Widgets.subTotal.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.'),
        totalAmount: Page.Widgets.totalAmount.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.'),
        vat: Page.Widgets.vatPercentage.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.'),
        totalVat: Page.Widgets.totalPercentage.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.'),
        statusRfq: statusRfq,
        packingCost: Page.Widgets.totalPackingCost.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.'),
        shippingHandling: Page.Widgets.totalShippingCost.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.'),
        otherCost: Page.Widgets.totalOtherCost.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.'),
        prCreator: prCreator,
        requestBy: requestBy,
        statusRepresentative: Page.Variables.DBPurchaseOrder.dataSet[0].statusRepresentative,
        departmentCode: departmentCode,
        createdBy: Page.Variables.DBPurchaseOrder.dataSet[0].createdBy,
        purchaseBy: Page.Widgets.headPurchaseBy.datavalue

    }

    var listItems = Page.Widgets.tableData.listItems._results
    var statusItem = true
    var dataBodyItem = []
    for (var i = 0; i < listItems.length; i++) {
        if (!listItems[i].item.poQty || listItems[i].item.poQty === '') {
            statusItem = false
        }
        if (!listItems[i].item.unitPrice || listItems[i].item.unitPrice === '') {
            statusItem = false
        }


        var poQty = listItems[i].item.poQty.toString().replace(/\./g, '').replace(/\,/g, '.')
        var unitPrice = listItems[i].item.unitPrice.toString().replace(/\./g, '').replace(/\,/g, '.')
        var StringDiscount = listItems[i].item.discount
        listItems[i].item.itemAmountAfterDiscount = (Number(poQty) * Number(unitPrice)) - ((unitPrice * poQty) * StringDiscount / 100)

        dataBodyItem.push(listItems[i].item)

        if (statusItem == false) {
            App.Actions.appNotification.setMessage('Please complete the list item')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            return false
        }
    }


    var fileItems = Page.Widgets.listFile.listItems._results
    var statusItemFile = true
    for (var i = 0; i < fileItems.length; i++) {
        if (fileItems[i].item.filePath && (fileItems[i].item.fileType === '' || !fileItems[i].item.fileType)) {
            statusItemFile = false
        }

        if (statusItemFile == false) {
            App.Actions.appNotification.setMessage('Please complete the list file item')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            return false
        }
    }


    var dataBodyItem_promise = []
    Page.Widgets.spinner.show = true


    if (statusSubmit == 'Open') {
        //Get DeptApproval
        if (Page.Variables.DBPurchaseOrder.firstRecord.purchaseBy === 'Purchasing') {
            var DBDepartmentApproval = Page.Variables.DBDepartmentApproval
            await DBDepartmentApproval.setFilter({
                departmentId: 'Z028'
            })
            await DBDepartmentApproval.listRecords();
        } else {
            var DBDepartmentApproval = Page.Variables.DBDepartmentApproval
            await DBDepartmentApproval.setFilter({
                departmentId: departmentCode
            })
            await DBDepartmentApproval.listRecords();
        }

        //Define Purchase Order Type Approval
        var userIdApprovalPO = 'emp::' + DBDepartmentApproval.dataSet[0].sectionHead
        var tlStatus = 'approval_1'
        if (Page.Variables.DBPurchaseOrder.firstRecord.purchaseBy !== 'Purchasing') {
            if (App.Variables.loggedInUserData.dataSet.username === 'emp::' + DBDepartmentApproval.dataSet[0].sectionHead) {
                userIdApprovalPO = 'emp::' + DBDepartmentApproval.dataSet[0].deptHead
                tlStatus = 'approval_2'
            }
        }
    }


    Page.Variables.DBUpdatePO.updateRecord({
        row: dataBody
    }).then(async res => {
        await Page.Variables.DBDeletePODocByIDPO.invoke()
        var dataDoc = Page.Widgets.listFile.listItems._results
        if (dataDoc.length > 0) {
            await Page.UploadNewFile(dataDoc)
        }

        return res

    }).then(async function(res) {
        if (statusSubmit == 'Open') {
            // Create Tasklist
            var DBTaskList = Page.Variables.DBTaskList
            await DBTaskList.createRecord({
                row: {
                    tlTimestamp: App.HariIni(),
                    tlType: 'PO',
                    tlSubject: listItems[0].item.item,
                    tlDueDate: App.formatDateAndTime(App.addDays(App.HariIni(), 3)),
                    userId: userIdApprovalPO,
                    tlParamId: Page.pageParams.id,
                    tlSubmitterName: App.Variables.loggedInUserData.dataSet.user_full_name,
                    tlModule: 'PO',
                    tlStatus: tlStatus,
                    isApprovalBod: 0
                }
            })

            if (Page.Variables.DBPurchaseOrder.dataSet[0].status === "Rejected") {
                var DBPOHistory = Page.Variables.DBPOHistory
                await DBPOHistory.createRecord({
                    row: {
                        poId: Page.pageParams.id,
                        action: 'Revised',
                        actionBy: Page.Variables.DBPurchaseOrder.dataSet[0].prCreator,
                        actionByName: App.Variables.loggedInUserData.dataSet.user_full_name,
                        actionAt: App.HariIni()
                    }
                })
            } else {
                var DBPOHistory = Page.Variables.DBPOHistory
                await DBPOHistory.createRecord({
                    row: {
                        poId: Page.pageParams.id,
                        action: 'Submitted',
                        actionBy: Page.Variables.DBPurchaseOrder.dataSet[0].prCreator,
                        actionByName: App.Variables.loggedInUserData.dataSet.user_full_name,
                        actionAt: App.HariIni()
                    }
                })

                try {
                    await Page.Variables.api_EmailApproval.invoke({
                        inputFields: {
                            RequestBody: {
                                id_po: Page.pageParams.id,
                                type: 'PO'
                            }
                        }
                    })
                } catch (e) {
                    console.log(e);
                }
            }
        }

        var SubmitPOlineItem = await Page.SubmitPOlineItem(res, dataBodyItem)
        if (SubmitPOlineItem === true) {
            App.Actions.appNotification.setMessage('Update Data Successfully')
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            Page.Widgets.spinner.show = false
            App.Actions.goToPage_POList.invoke()
        }

    })
};

Page.UploadNewFile = function(dataDoc) {
    return new Promise(async(resolve, reject) => {
        for (var i = 0; i < dataDoc.length; i++) {
            if (dataDoc[i].item.filePath) {
                await Page.Variables.DBInsertPODoc.createRecord({
                    row: {
                        idPoHeader: Page.pageParams.id,
                        filePath: dataDoc[i].item.filePath,
                        fileName: dataDoc[i].item.fileName,
                        fileType: dataDoc[i].item.fileType
                    }
                })
            }
            if (i === dataDoc.length - 1) {
                resolve(true)
            }
        }
    })
}


Page.SubmitPOlineItem = function(res, dataBodyItem) {
    return new Promise(async(resolve, reject) => {
        if (dataBodyItem.length > 0) {
            for (var i = 0; i < dataBodyItem.length; i++) {
                await Page.Variables.DBUpdatePOLineItem.updateRecord({
                    row: {
                        id: dataBodyItem[i].idPoLineItem,
                        idPrLineItemHeader: dataBodyItem[i].idPrLineItemHeader,
                        idPoHeader: Page.pageParams.id,
                        idPrHeader: dataBodyItem[i].idPrHeader,
                        item: dataBodyItem[i].item,
                        poQty: dataBodyItem[i].poQty.toString().replace(/\./g, '').replace(/\,/g, '.'),
                        uom: dataBodyItem[i].uom,
                        unitPrice: dataBodyItem[i].unitPrice.toString().replace(/\./g, ''),
                        itemTotalAmount: (Number(dataBodyItem[i].poQty.toString().replace(/\./g, '').replace(/\,/g, '.')) * Number(dataBodyItem[i].unitPrice.toString().replace(/\./g, '').replace(/\,/g, '.'))),
                        itemAmountAfterDiscount: dataBodyItem[i].itemAmountAfterDiscount,
                        matlGroup: dataBodyItem[i].matlGroup,
                        matlGroupDesc: dataBodyItem[i].matlGroupDesc,
                        plant: 'Head Office',
                        eta: dataBodyItem[i].eta,
                        ioNumber: dataBodyItem[i].ioNumber,
                        costCenter: dataBodyItem[i].costCenter,
                        coa: dataBodyItem[i].coa,
                        assetsNo: dataBodyItem[i].assetsNo,
                        prNo: dataBodyItem[i].prNo,
                        prNoItem: dataBodyItem[i].prNoItem,
                        sapPrNumber: dataBodyItem[i].sapPrNo,
                        discount: dataBodyItem[i].discount
                    }
                })

                if (i === dataBodyItem.length - 1) {
                    resolve(true)
                }
            }
        } else {
            resolve(true)
        }
    })
}


Page.tableFormQTYKeyup = function($event, widget, item, currentItemWidgets) {
    Page.CalculateTotal()
};
Page.tableFormUnitPriceKeyup = function($event, widget, item, currentItemWidgets) {
    Page.CalculateTotal()
};

Page.DBGetPODoconResult = function(variable, data) {
    if (data.length == 0) {
        Page.Variables.MODELListFile.dataSet.push({
            filePath: null,
            fileName: null,
            id: 1
        })
    } else {
        var id = 0
        for (var i = 0; i < data.length; i++) {
            Page.Variables.MODELListFile.dataSet.push({
                filePath: data[i].filePath,
                fileName: data[i].fileName,
                fileType: data[i].fileType,
                id: id++
            })
        }
    }
};
Page.button9Click = function($event, widget) {
    var max_id = Math.max.apply(Math, Page.Variables.MODELListFile.dataSet.map(function(item) {
        return item.id;
    }))
    Page.Variables.MODELListFile.dataSet.push({
        filePath: null,
        fileName: null,
        id: max_id + 1
    })
};
Page.removeItemFileClick = function($event, widget, item, currentItemWidgets) {
    if (item.id > 1) {
        var filteredItems = Page.Variables.MODELListFile.dataSet.filter(filter_item => {
            return filter_item.id !== item.id
        })
        Page.Variables.MODELListFile.dataSet = filteredItems
    } else {
        Page.Variables.MODELListFile.dataSet.filter(filter_item => {
            return filter_item.id === item.id
        }).map(function(map_item) {
            map_item.fileName = null
            map_item.filePath = null
        })
    }
};
Page.buttonSupportDocumentClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.selectedListFile.dataValue = item.id
    $(Page.Widgets.fileSupportDocument.nativeElement).find("input").click();
};

Page.uploadFileonSuccess = function(variable, data) {
    Page.Widgets.spinner.show = false
    Page.Variables.MODELListFile.dataSet.filter(filter_item => {
        return filter_item.id === Page.Variables.selectedListFile.dataValue
    }).map(function(map_item) {
        map_item.fileName = data[0].fileName;
        map_item.filePath = data[0].path.replaceAll(data[0].fileName, encodeURIComponent(data[0].fileName));
    })
};

Page.vatPercentageKeyup = function($event, widget) {
    Page.CalculateTotal()
};

Page.headPurchaseByChange = async function($event, widget, newVal, oldVal) {
    if (Page.Widgets.headPurchaseOrg.datavalue && Page.Widgets.headPurchaseOrg.datavalue !== 'purchase_order') {
        await Page.Variables.DBDepartmentCode.invoke({
            "inputFields": {
                "departmentId": Page.Widgets.headPurchaseOrg.datavalue
            }
        })
    }
};





// Export Summary
Page.dialogExportOpened = function($event, widget) {
    Page.Widgets.spinnerExport.show = true
};
Page.labelViewSummaryClick = function($event, widget) {
    Page.Variables.modelRfqVendorList.dataSet = []
    var userDepartment = App.Variables.loggedInUserData.dataSet.user_department
    var departments = []
    if (userDepartment.length > 0) {
        var departments = []

        userDepartment.map(dep => {
            departments.push(dep.departmentId)
        })
    }

    Page.Variables.getRFQSummaryPrint.invoke({
        "inputFields": {
            "rfqId": Page.Variables.DBPurchaseOrder.dataSet[0].rfqNumber.toString().replace("9000-", "")
        }
    })

    Page.Variables.getRFQSummary.invoke({
        "inputFields": {
            "rfqId": Page.Variables.DBPurchaseOrder.dataSet[0].rfqNumber.toString().replace("9000-", "")
        }
    }).then(function(data) {
        data = JSON.parse(data.body)
        if (data.content.length === 0) {
            return
        }

        var sorted = data.content.map(function(item) {
            return item.rfqvDuration
        }).sort(function(a, b) {
            return b - a;
        })

        data.content.map(function(item) {
            item.leadTime = sorted.indexOf(item.rfqvDuration) + 1
        })


        Page.Variables.modelRfqVendorList.setData(data.content)
    }).then(function() {
        if (departments.length === 0) {
            return Promise.resolve()
        }

        return Page.Variables.qGetRFQParameter.invoke({
            "inputFields": {
                "userDepartment ": departments
            }
        })
    }).then(function(res) {
        if (res) {
            res = JSON.parse(res.body)
            Page.Variables.modelParameterRFQ.setValue("quality", res.content[0].quality)
            Page.Variables.modelParameterRFQ.setValue("leadTime", res.content[0].leadTime)
            Page.Variables.modelParameterRFQ.setValue("totalPrice", res.content[0].price)
        }

        return true
    }).then(function(res) {
        var formData = JSON.parse(JSON.stringify(Page.Variables.modelRfqVendorList.dataSet))

        Page.Widgets.spinnerExport.show = false
        return Promise.resolve().then(function() {
            return Page.updateMultiItem(formData.reverse())
        })


    })
};

Page.updateMultiItem = function(data) {
    // exit recursive
    if (!data || !data.length) {
        return Promise.resolve()
    }

    // loop till data empty
    var dt = data.pop()
    return Page.Variables.vdbRFQVendor.listRecords({
        "filterFields": {
            "rfqvId": {
                "value": dt.rfqvId
            }
        }
    }).then(function(res) {
        var dataItem = res.data[0]
        dataItem.rfqvQuality = dt.quality
        dataItem.rfqvSummaryOverwrite = dt.summaryOverwrite
        dataItem.rfqvSummaryReason = dt.summaryReason
        if (dt.summaryOverwrite) {
            dataItem.rfqvRank = Page.summaryOverwriteRank(dt, Page.Variables.modelRfqVendorList.dataSet)
        } else {
            dataItem.rfqvRank = Page.summaryRank(dt, Page.Variables.modelRfqVendorList.dataSet)
        }


        return Page.Variables.vdbRFQVendor.updateRecord({
            row: dataItem
        })
    }).then(function() {
        return Page.updateMultiItem(data)
    }).catch(function(err) {
        return Page.updateMultiItem(data)
    })
};

Page.summaryOverwriteRank = function(currentItem, allItem) {
    allItem = Page.Variables.modelRfqVendorList.dataSet

    var sortirItem = allItem.map(function(item) {
        return item.summaryOverwrite
    }).sort(function(a, b) {
        return b - a
    })

    return sortirItem.indexOf(currentItem.summaryOverwrite) + 1
};

Page.summaryRank = function(currentItem, allItem) {
    var quality = Page.Variables.modelParameterRFQ.dataSet.quality
    var leadTime = Page.Variables.modelParameterRFQ.dataSet.leadTime
    var totalPrice = Page.Variables.modelParameterRFQ.dataSet.totalPrice

    allItem = Page.Variables.modelRfqVendorList.dataSet

    var currentQuality = currentItem.quality ? currentItem.quality : 0
    var totalCurrent = (quality * parseInt(currentQuality) / 100) + (leadTime * parseInt(currentItem.leadTime) / 100) + (totalPrice * parseInt(currentItem.totalPrice) / 100)

    var totalAll = allItem.map(function(item) {
        item.quality = item.quality ? item.quality : 0
        return (quality * parseInt(item.quality) / 100) + (leadTime * parseInt(item.leadTime) / 100) + (totalPrice * parseInt(item.totalPrice) / 100)
    }).sort(function(a, b) {
        return b - a
    })

    return totalAll.indexOf(totalCurrent) + 1
};
Page.button3Click = function($event, widget) {
    Page.Widgets.spinnerExport.show = true
    var element = Page.Widgets.containerExport.nativeElement;
    var opt = {
        margin: 0.2,
        filename: "RFQ Summary.pdf",
        jsPDF: {
            unit: 'in',
            format: 'letter',
            orientation: 'landscape'
        }
    }
    Page.Widgets.spinnerExport.show = false
    return Promise.resolve().then(function() {
        return html2pdf().set(opt).from(element).save();
    })
};

Page.DBHistoryonSuccess = function(variable, data) {
    Page.Variables.VTaskList.invoke()
        .then(function(res) {
            if (res.data.length > 0) {
                Page.Variables.DBPOHistory.dataSet.push({
                    actionBy: res.data[0].userId,
                    actionByName: res.data[0].approvalName,
                    action: 'Waiting Approval'
                })
            }
        })
};

Page.viewItemFileClick = async function($event, widget, item, currentItemWidgets) {
    Page.Widgets.spinner.show = true
    var isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);
    var filePath = Page.Widgets.listFile.selecteditem.filePath

    fileExtension = filePath.substr((filePath.lastIndexOf('.') + 1));
    if (fileExtension === 'pdf' || fileExtension === 'PDF') {
        if (isChrome) {
            Page.Variables.statusShowPDF.dataSet.dataValue = true
            var $node = $("#pdf-canvas-container");
            if (filePath) {
                Page.Widgets.spinner.show = false
                PDFObject.embed(filePath, $node);
            }
        } else {
            Page.Widgets.spinner.show = false
            window.open("https://docs.google.com/viewer?url=" + filePath + "&embedded=true", "_blank")
        }

    } else {
        Page.Widgets.spinner.show = false
        window.open(filePath)
    }


};

Page.closeItemFileClick = function($event, widget) {
    Page.Variables.statusShowPDF.dataSet.dataValue = false
    $("#pdf-canvas-container").html('')
    $("#pdf-canvas-container").removeClass('pdfobject-container')
};

Page.headPPNChange = function($event, widget, newVal, oldVal) {
    var filterPPN = Page.Variables.ModelPPN.dataSet.filter(item =>
        item.dataValue == newVal
    )
    if (filterPPN.length > 0) {
        Page.Widgets.vatPercentage.datavalue = filterPPN[0].taxValue
        Page.CalculateTotal()
    }
};
Page.tableFormItemKeyup = function($event, widget, item, currentItemWidgets) {
    var value = widget.datavalue
    item.item = value
};