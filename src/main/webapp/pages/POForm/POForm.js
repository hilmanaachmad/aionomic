/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */



/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    if (Page.Widgets.spinner) {
        Page.Widgets.spinner.show = true
    }

    if (!Page.pageParams.item) {
        App.Actions.appNotification.setMessage('Data not found')
        App.Actions.appNotification.setToasterClass("warning")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.Widgets.spinner.show = false
        App.Actions.goToPage_POList.invoke()
    }

    $('.only-number').keypress(function(evt) {
        evt = (evt) ? evt : window.event;
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57)) {
            return false;
        }
        return true;
    })
};


var statusRfq = 'YES'
var statusSubmit = 'Open'


Page.DBPurchaseOrderonResult = async function(variable, data) {
    if (Page.Widgets.spinner) {
        Page.Widgets.spinner.show = false
    }

    if (data.length > 0) {
        if (data[0].rfqNumber) {
            Page.Variables.MODELPaymentMethod.dataSet.push({
                name: 'Purchase Order',
                dataValue: 'purchase_order'
            })
            if (Page.Widgets.headPaymentMethod) {
                Page.Widgets.headPaymentMethod.datavalue = Page.pageParams.processPurchase
            }


            if (data[0].rfqNumber && (data[0].rfqStatus !== 'deleted' && data[0].rfqStatus !== 'closed')) {
                statusRfq = 'YES'
            } else {
                statusRfq = 'NO'
            }

            if (data[0].reqvVendor) {
                var vendor = data[0].reqvVendor.split("-")
                var vendorCode = vendor[0].trim()
                var vendorName = vendor[1].trim()
                Page.Variables.MODELVendor.dataSet = [{
                    vendorCode: vendorCode,
                    vendorName: vendorName
                }]
            }
        } else if (!data[0].rfqNumber && data[0].prPurchaseBy.includes("Purchasing")) {
            Page.Variables.MODELPaymentMethod.dataSet.push({
                name: 'Purchase Order',
                dataValue: 'purchase_order'
            })
            if (Page.Widgets.headPaymentMethod) {
                Page.Widgets.headPaymentMethod.datavalue = Page.pageParams.processPurchase
            }

            statusRfq = 'NO'
        } else if (!data[0].rfqNumber && data[0].prPurchaseBy.includes("User")) {
            Page.Variables.MODELPaymentMethod.dataSet.push({
                name: 'Purchase Order',
                dataValue: 'purchase_order'
            })
            if (Page.Widgets.headPaymentMethod) {
                Page.Widgets.headPaymentMethod.datavalue = Page.pageParams.processPurchase
            }
            statusRfq = 'NO'
        }


        Page.Variables.MODELPurchaseBy.dataSet = Page.Variables.MODELPurchaseBy.dataSet.filter(item => item.dataValue === data[0].prPurchaseBy)

    }

    // if (Page.Widgets.headTotalAmount) {
    //     if (data.length > 0) {
    //         if (data[0].rfqNumber && data[0].rfqStatus !== 'deleted' && data[0].rfqNumber && data[0].rfqStatus !== 'closed') {
    //             Page.Widgets.headTotalAmount.datavalue = App.formatCurrency(data.reduce((prep, item) => prep + (item.liqUnitPrice * item.qty), 0))
    //         } else {
    //             Page.Widgets.headTotalAmount.datavalue = App.formatCurrency(data.reduce((prep, item) => prep + (item.pliUnitPrice * item.qty), 0))
    //         }
    //     } else {
    //         Page.Widgets.headTotalAmount.datavalue = App.formatCurrency(data.reduce((prep, item) => prep + (item.pliUnitPrice * item.qty), 0))
    //     }
    // }

};

Page.tableDataRender = function(widget, $data) {
    Page.CalculateTotal()
};

Page.CalculateTotal = function() {
    var subTotal = 0
    if (Page.Variables.DBPurchaseOrder.dataSet[0].rfqNumber && (Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'deleted' && Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'closed')) {
        subTotal = Page.Widgets.tableData.listItems._results.reduce((prep, data) =>
            prep + ((data.item.liqUnitPrice * data.item.qty) - ((data.item.liqUnitPrice * data.item.qty) * data.item.discount / 100)), 0)
        subTotal = (subTotal !== NaN) ? subTotal : 0
    } else {
        subTotal = Page.Widgets.tableData.listItems._results.reduce((prep, data) =>
            prep + ((data.item.pliUnitPrice * data.item.qty) - ((data.item.pliUnitPrice * data.item.qty) * data.item.discount / 100)), 0)
        subTotal = (subTotal !== NaN) ? subTotal : 0
    }

    Page.Widgets.subTotal.datavalue = App.formatCurrency(subTotal)
    var vat = Page.Widgets.vatPercentage.datavalue ? Number(Page.Widgets.vatPercentage.datavalue) : 0
    var vatTotal = Math.round(((subTotal * vat) / 100)).toFixed(0)
    var packingCost = Page.Widgets.totalPackingCost.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    var shippingCost = Page.Widgets.totalShippingCost.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    var otherCost = Page.Widgets.totalOtherCost.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    var costFromQuotation = Number(packingCost) + Number(shippingCost) + Number(otherCost)
    Page.Widgets.totalPercentage.datavalue = (vat === 0) ? vat : App.formatCurrency(vatTotal)
    Page.Widgets.totalAmount.datavalue = (vat === 0) ? (Number(subTotal) + Number(vatTotal) + Number(costFromQuotation)) : App.formatCurrency(
        (Number(subTotal) + Number(vatTotal) + Number(costFromQuotation)).toFixed(0)
    )
    Page.Widgets.headTotalAmount.datavalue = (vat === 0) ? Number(subTotal) + Number(vatTotal) + Number(costFromQuotation) : App.formatCurrency(
        (Number(subTotal) + Number(vatTotal) + Number(costFromQuotation)).toFixed(0)
    )
}


Page.tableFormQTYKeyup = function($event, widget, item, currentItemWidgets) {
    var value = widget.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    widget.datavalue = App.formatCurrency(value)
    item.qty = value
    Page.CalculateTotal()
};

Page.tableFormUnitPriceKeyup = function($event, widget, item, currentItemWidgets) {
    var value = widget.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    widget.datavalue = App.formatCurrency(value)
    if (Page.Variables.DBPurchaseOrder.dataSet[0].rfqNumber && (Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'deleted' && Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'closed')) {
        item.liqUnitPrice = value
    } else {
        item.pliUnitPrice = value
    }
    Page.CalculateTotal()
};

Page.tableFormDiscountKeyup = function($event, widget, item, currentItemWidgets) {
    var value = widget.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    widget.datavalue = App.formatCurrency(value)
    item.liqDiscount = value
    setTimeout(function() {
        Page.CalculateTotal()
    }, 1000);

};


Page.valueLineTotalAmount = function(widget) {
    var qty = widget.tableFormQTY.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    var unitPrice = widget.tableFormUnitPrice.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    return App.formatCurrency(Number(qty) * Number(unitPrice))
}

Page.valueLineTotalAmountAfterDiscount = function(widget) {
    var qty = widget.tableFormQTY.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    var unitPrice = widget.tableFormUnitPrice.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    return App.formatCurrency(Number(qty) * Number(unitPrice))
}

Page.calulcateTotalAmountLine = function(widget, data) {
    var qty = widget.tableFormQTY.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    var unitPrice = widget.tableFormUnitPrice.datavalue.toString().replace(/\./g, '').replace(/\,/g, '.')
    var amount = Number(qty) * Number(unitPrice)
    var discount = amount * Page.getDiscountRFQ(data) / 100
    return App.formatCurrency(amount - discount)
}




Page.vatPercentageKeyup = function($event, widget) {
    Page.CalculateTotal()
};

Page.submitDataClick = function($event, widget) {
    statusSubmit = 'Open'
    Page.submitData()
};

Page.draftDataClick = function($event, widget) {
    statusSubmit = 'Draft'
    Page.submitData()
};

Page.submitData = async function() {
    // if (!(Page.Variables.DBPurchaseOrder.dataSet[0].rfqNumber && (Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'deleted' && Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'closed'))) {
    var subTotal = Page.Widgets.tableData.listItems._results.reduce((prep, data) =>
        prep + ((data.item.pliUnitPrice * data.item.qty)), 0)
    subTotal = (subTotal !== NaN) ? subTotal : 0

    var subTotalPR = Page.Variables.DBPurchaseOrder.dataSet.reduce((prep, data) =>
        prep + ((data.pliUnitPrice * data.qty)), 0)
    subTotalPR = (subTotalPR !== NaN) ? subTotalPR : 0

    console.log('pr', subTotalPR);
    console.log('po', subTotal);
    if (subTotal > subTotalPR) {
        App.Actions.appNotification.setMessage('PO amount cannot greather than PR')
        App.Actions.appNotification.setToasterClass("error")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        return false
    }
    // }
    // return false;

    Page.Widgets.spinner.show = true
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

    if (!Page.Widgets.headVendor.datavalue && Page.Widgets.headVendor.datavalue.length === 0) {
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

    if (statusRfq === 'NO') {
        if (!Page.Variables.MODELListFile.dataSet[0].filePath) {
            Page.Widgets.spinner.show = false
            App.Actions.appNotification.setMessage('PO without RFQ, please attach files, at least one file')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            return false
        }
    }


    if (formValid === false) {
        Page.Widgets.spinner.show = false
        App.Actions.appNotification.setMessage('Please complete the form')
        App.Actions.appNotification.setToasterClass("error")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        return false
    }






    var prCreator = Page.Variables.DBPurchaseOrder.dataSet[0].prCreator.toString().replace("emp::", '')

    var requestBy = Page.Variables.DBPurchaseOrder.dataSet[0].prRepUserId ? Page.Variables.DBEmployeeRepresentative.dataSet[0].nik.toString().replace("emp::", '') : Page.Variables.DBPurchaseOrder.dataSet[0].prCreator.toString().replace("emp::", '')

    await Page.Variables.DBDepartmentCode.invoke({
        "inputFields": {
            "departmentId": Page.Widgets.headPurchaseOrg.datavalue
        }
    })

    var departmentCode = Page.Variables.DBDepartmentCode.dataSet.departmentCode
    var paymentMethod = Page.Widgets.headPaymentMethod.datavalue

    var dataBody = {
        companyId: Page.Variables.DBPurchaseOrder.dataSet[0].companyCode,
        docType: Page.Widgets.headDocType.datavalue,
        purchOrg: Page.Widgets.headPurchaseOrg.datavalue,
        purchGroup: Page.Widgets.headPurchaseGroup.datavalue,
        vendorCode: Page.Widgets.headVendor.datavalue.vendorCode,
        vendorName: Page.Widgets.headVendor.datavalue.vendorName.trim(),
        currency: Page.Widgets.headCurrency.datavalue,
        paymentMethod: paymentMethod,
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
        prCreator: 'emp::' + prCreator,
        requestBy: 'emp::' + requestBy,
        statusRepresentative: Page.Variables.DBPurchaseOrder.dataSet[0].repNik ? 'TRUE' : 'FALSE',
        departmentCode: departmentCode,
        createdBy: App.Variables.loggedInUserData.dataSet.username,
        purchaseBy: Page.Widgets.headPurchaseBy.datavalue
    }

    var listItems = Page.Widgets.tableData.listItems._results
    var statusItem = true
    var dataBodyItem = []
    for (var i = 0; i < listItems.length; i++) {
        if (!listItems[i].item.qty || listItems[i].item.qty === '') {
            statusItem = false
        }

        if (Page.Variables.DBPurchaseOrder.dataSet[0].rfqNumber && (Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'deleted' && Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'closed')) {
            if (!listItems[i].item.liqUnitPrice || listItems[i].item.liqUnitPrice === '') {
                statusItem = false
            }
        } else {
            if (!listItems[i].item.pliUnitPrice || listItems[i].item.pliUnitPrice === '') {
                statusItem = false
            }
        }


        var StringQty = listItems[i].item.qty.toString().replace(/\./g, '').replace(/\,/g, '.')
        var StringLiqUnitPrice = 0
        if (listItems[i].item.liqUnitPrice) {
            StringLiqUnitPrice = listItems[i].item.liqUnitPrice.toString().replace(/\./g, '').replace(/\,/g, '.')
        }
        var StringPliUnitPrice = listItems[i].item.pliUnitPrice.toString().replace(/\./g, '').replace(/\,/g, '.')
        var StringDiscount = listItems[i].item.discount

        if (Page.Variables.DBPurchaseOrder.dataSet[0].rfqNumber && (Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'deleted' && Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'closed')) {
            listItems[i].item.itemTotalAmount = (Number(StringQty) * Number(StringLiqUnitPrice))

            listItems[i].item.itemAmountAfterDiscount = (Number(StringQty) * Number(StringLiqUnitPrice)) - ((StringLiqUnitPrice * StringQty) * StringDiscount / 100)

            listItems[i].item.unitPrice = StringLiqUnitPrice
        } else {
            listItems[i].item.itemTotalAmount = (Number(StringQty) * Number(StringPliUnitPrice))

            listItems[i].item.itemAmountAfterDiscount = (Number(StringQty) * Number(StringPliUnitPrice)) - ((StringPliUnitPrice * StringQty) * StringDiscount / 100)

            listItems[i].item.unitPrice = StringPliUnitPrice
        }


        dataBodyItem.push(listItems[i].item)

        if (statusItem == false) {
            App.Actions.appNotification.setMessage('Please complete the list item')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            Page.Widgets.spinner.show = false
            return false
        }
    }

    var fileItems = Page.Widgets.listFile.listItems._results
    var statusItemFile = true
    for (var i = 0; i < fileItems.length; i++) {
        if (fileItems[i].item.filePath && (!fileItems[i].item.fileType || fileItems[i].item.fileType === '')) {
            statusItemFile = false
        }

        if (statusItemFile == false) {
            App.Actions.appNotification.setMessage('Please complete the list file item')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            Page.Widgets.spinner.show = false
            return false
        }
    }

    var dataBodyItem_promise = []
    var dataDoc_promise = []


    if (statusSubmit == 'Open') {
        //Get DeptApproval
        if (Page.Variables.DBPurchaseOrder.firstRecord.prPurchaseBy === 'Purchasing') {
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

        if (DBDepartmentApproval.dataSet.length === 0) {
            App.Actions.appNotification.setMessage('Purchase by user, Please manage department for approval')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            Page.Widgets.spinner.show = false
            return false
        }

        //Define Purchase Order Type Approval
        // if (Page.Variables.DBPurchaseOrder.firstRecord.prPurchaseBy === 'Purchasing') {
        var userIdApprovalPO = 'emp::' + DBDepartmentApproval.dataSet[0].sectionHead
        // } else {
        //     var userIdApprovalPO = 'emp::' + DBDepartmentApproval.dataSet[0].deptHead
        // }
    }


    Page.Variables.DBInsertPO.createRecord({
            row: dataBody
        })
        .then(async function(res) {
            var dataDoc = Page.Variables.MODELListFile.dataSet
            if (dataDoc.length > 0) {
                await Page.UploadNewFile(res, dataDoc)
            }
            return Promise.all([
                Promise.resolve(res)
            ])
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
                        tlParamId: res[0].id,
                        // tlSubmitterName: Page.Variables.DBPurchaseOrder.dataSet[0].prRepUserId ? Page.Variables.DBEmployeeRepresentative.dataSet[0].employeeName : Page.Variables.DBPurchaseOrder.dataSet[0].prCreatedName,
                        tlSubmitterName: App.Variables.loggedInUserData.dataSet.user_full_name,
                        tlModule: 'PO',
                        tlStatus: 'approval_1',
                        isApprovalBod: 0
                    }
                })

                var DBPOHistory = Page.Variables.DBPOHistory
                await DBPOHistory.createRecord({
                    row: {
                        poId: res[0].id,
                        action: 'Submitted',
                        // actionBy: 'emp::' + prCreator,
                        //  actionByName: Page.Variables.DBPurchaseOrder.dataSet[0].prRepUserId ? Page.Variables.DBEmployeeRepresentative.dataSet[0].employeeName : Page.Variables.DBPurchaseOrder.dataSet[0].prCreatedName,
                        actionBy: App.Variables.loggedInUserData.dataSet.username,
                        actionByName: App.Variables.loggedInUserData.dataSet.user_full_name,
                        actionAt: App.HariIni()
                    }
                })

                try {
                    await Page.Variables.api_EmailApproval.invoke({
                        inputFields: {
                            RequestBody: {
                                id_po: res[0].id,
                                type: 'PO'
                            }
                        }
                    })
                } catch (e) {

                }
            }

            var SubmitPOlineItem = await Page.SubmitPOlineItem(res, dataBodyItem)
            var submitUpdatePRLineItem = await Page.submitUpdatePRLineItem(dataBodyItem)
            var submitUpdatePR = await Page.submitUpdatePR(dataBodyItem)

            // console.log(SubmitPOlineItem)
            // console.log(submitUpdatePRLineItem)
            // console.log(submitUpdatePR)

            if (SubmitPOlineItem === true && submitUpdatePRLineItem === true && submitUpdatePR === true) {
                App.Actions.appNotification.setMessage('Add Data Successfully')
                App.Actions.appNotification.setToasterClass("success")
                App.Actions.appNotification.setToasterDuration(5000)
                App.Actions.appNotification.invoke()
                Page.Widgets.spinner.show = false
                App.Actions.goToPage_POList.invoke()
            }

        })
};



Page.UploadNewFile = function(res, dataDoc) {
    return new Promise(async(resolve, reject) => {
        for (var i = 0; i < dataDoc.length; i++) {
            if (dataDoc[i].filePath && dataDoc[i].filePath !== '') {
                await Page.Variables.DBInsertPODoc.createRecord({
                    row: {
                        idPoHeader: res.id,
                        filePath: dataDoc[i].filePath,
                        fileName: dataDoc[i].fileName,
                        fileType: dataDoc[i].fileType
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
                await Page.Variables.DBInsertPOLineItem.createRecord({
                    row: {
                        idPrLineItemHeader: dataBodyItem[i].liId,
                        idPoHeader: res[0].id,
                        idPrHeader: dataBodyItem[i].prId,
                        item: dataBodyItem[i].item,
                        poQty: dataBodyItem[i].qty.toString().replace(/\./g, '').replace(/\,/g, '.'),
                        uom: dataBodyItem[i].uom,
                        unitPrice: dataBodyItem[i].unitPrice,
                        itemTotalAmount: dataBodyItem[i].itemTotalAmount,
                        itemAmountAfterDiscount: dataBodyItem[i].itemAmountAfterDiscount,
                        matlGroup: dataBodyItem[i].materialGroup,
                        matlGroupDesc: dataBodyItem[i].materialGroupDesc,
                        plant: 'Head Office',
                        eta: dataBodyItem[i].eta,
                        ioNumber: dataBodyItem[i].ioNumber,
                        costCenter: dataBodyItem[i].pliCostCenterId,
                        coa: dataBodyItem[i].pliCoa,
                        assetsNo: dataBodyItem[i].pliAssetNumber,
                        prNo: dataBodyItem[i].prNumber,
                        prNoItem: dataBodyItem[i].pliLineItemSap,
                        sapPrNumber: dataBodyItem[i].sapPrNumber,
                        discount: Page.getDiscountRFQ(dataBodyItem[i])
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

Page.submitUpdatePRLineItem = function(dataBodyItem) {
    return new Promise(async(resolve, reject) => {
        if (dataBodyItem.length > 0) {
            for (var i = 0; i < dataBodyItem.length; i++) {
                await Page.Variables.DBUpdatePRLineItem.invoke({
                    "inputFields": {
                        "pli_id": dataBodyItem[i].liId,
                        "pli_status": 'used'
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

Page.submitUpdatePR = function(dataBodyItem) {
    return new Promise(async(resolve, reject) => {
        if (dataBodyItem.length > 0) {
            for (var i = 0; i < dataBodyItem.length; i++) {
                await Page.Variables.DBUpdatePR.invoke({
                    "inputFields": {
                        "prId": dataBodyItem[i].prId,
                        "prStatus": 'Purchase Order'
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



Page.headCompanyKeypress = function($event, widget) {
    Page.Variables.MODELMessageError.setValue('headCompany', null)
};
Page.headDocTypeChange = function($event, widget, newVal, oldVal) {
    Page.Variables.MODELMessageError.setValue('headDocType', null)
};
Page.headPurchaseOrgChange = async function($event, widget, newVal, oldVal) {
    Page.Variables.MODELMessageError.setValue('headPurchaseOrg', null)
    // if (Page.Widgets.headPurchaseOrg.datavalue && Page.Widgets.headPurchaseOrg.datavalue !== 'Purchasing') {
    await Page.Variables.DBDepartmentCode.invoke({
        "inputFields": {
            "departmentId": Page.Widgets.headPurchaseOrg.datavalue
        }
    })
    // }
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

Page.buttonSupportDocumentClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.selectedListFile.dataValue = item.id
    $(Page.Widgets.fileSupportDocument.nativeElement).find("input").click();
};

Page.fileSupportDocumentSelect = function($event, widget, selectedFiles) {
    Page.Widgets.spinner.show = true
};

Page.uploadFileonSuccess = function(variable, data) {
    Page.Widgets.spinner.show = false
    Page.Variables.MODELListFile.dataSet.filter(filter_item => {
        return filter_item.id === Page.Variables.selectedListFile.dataValue
    }).map(function(map_item) {
        map_item.fileName = data[0].fileName;
        map_item.filePath = data[0].path.replaceAll(data[0].fileName, encodeURIComponent(data[0].fileName));
        console.log('item', map_item);
    })
};

Page.button8Click = function($event, widget) {
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


Page.getDiscountRFQ = function(currentItem) {
    if (!(Page.Variables.DBPurchaseOrder.dataSet[0].rfqNumber && (Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'deleted' && Page.Variables.DBPurchaseOrder.dataSet[0].rfqStatus !== 'closed'))) {
        if (currentItem.liqDiscount) {
            currentItem.discount = currentItem.liqDiscount
            return currentItem.liqDiscount
        } else {
            currentItem.discount = 0
            return 0
        }

    } else {
        var data = Page.Variables.DBRFQVendorTOP.dataSet.filter(filter_item => {
            return filter_item.rlsId == currentItem.rlsId
        })

        if (data.length > 0) {
            currentItem.discount = data[0].liqDiscount
            return data[0].liqDiscount
        } else {
            currentItem.discount = 0
            return 0
        }
    }

}
Page.viewItemFileClick = function($event, widget, item, currentItemWidgets) {
    // Prepare canvas using PDF page dimensions
    var canvas = document.createElement("canvas")
    canvas.width = "50%"
    canvas.style.marginBottom = "24px"
    var context = canvas.getContext('2d');
    document.getElementById("pdf-canvas-container").appendChild(canvas)


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
        window.open(filePath)
        Page.Widgets.spinner.show = false
    }


};
Page.closeItemFileClick = function($event, widget, item, currentItemWidgets) {
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

Page.totalPackingCostKeyup = function($event, widget) {
    Page.CalculateTotal()
};
Page.totalShippingCostKeyup = function($event, widget) {
    Page.CalculateTotal()
};
Page.totalOtherCostKeyup = function($event, widget) {
    Page.CalculateTotal()
};
Page.headPurchaseByChange = async function($event, widget, newVal, oldVal) {
    if (Page.Widgets.headPurchaseOrg.datavalue && Page.Widgets.headPurchaseOrg.datavalue !== 'Purchasing') {
        await Page.Variables.DBDepartmentCode.invoke({
            "inputFields": {
                "departmentId": Page.Widgets.headPurchaseOrg.datavalue
            }
        })
    }
};

// Page.DBDepartmentCodeonResult = function(variable, data) {
//     console.log(data)
// };
// Page.headPurchaseOrgSelect = function($event, widget, selectedValue) {
//     console.log('on select')
// };






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
        return a - b
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
Page.tableFormItemKeyup = function($event, widget, item, currentItemWidgets) {
    var value = widget.datavalue
    item.item = value
};