/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */



/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    if (Page.Widgets.spinner) {
        Page.Widgets.spinner.show = false
    }

    if (!Page.pageParams.id) {
        App.Actions.appNotification.setMessage('Data not found')
        App.Actions.appNotification.setToasterClass("warning")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.Widgets.spinner.show = false
        App.Actions.goToPage_POReadyForGR.invoke()
    }
};


Page.buttonCancelClick = function($event, widget) {
    window.history.back();
};


//Manipulation data on list
Page.confirmQtyKeyup = function($event, widget, item, currentItemWidgets) {
    item.confirmQty = widget.datavalue
};

Page.confirmUnitSelect = function($event, widget, selectedValue, item, currentItemWidgets) {
    item.confirmUnit = widget.datavalue
};

Page.receivingDateChange = function($event, widget, item, currentItemWidgets, newVal, oldVal) {
    item.receivedDate = widget.datavalue
};

Page.remarksKeyup = function($event, widget, item, currentItemWidgets) {
    item.remarks = widget.datavalue
};


Page.submitDataClick = function($event, widget) {
    var dataList = Page.Widgets.tableData.listItems._results
    var statusItem = true
    for (var i = 0; i < dataList.length; i++) {
        if (
            dataList[i].item.confirmQty === '' ||
            (dataList[i].item.receivedDate === '' || dataList[i].item.receivedDate === undefined) ||
            (dataList[i].item.condition === '' || dataList[i].item.condition === undefined) ||
            (dataList[i].item.remarks === '' || dataList[i].item.remarks === undefined)
        ) {
            statusItem = false
        }

        if (!dataList[i].item.confirmQty) {
            dataList[i].item.confirmQty = dataList[i].item.deliveryQty
        }


        dataList[i].item.confirmUnit = dataList[i].item.uom

        if (statusItem == false) {
            App.Actions.appNotification.setMessage('Please complete the list item')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            return false
        }

        if (i === dataList.length - 1) {
            Page.Widgets.modalConfirm.open()
        }
    }

};




//File
Page.uploadDocClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.selectedList.dataValue = item.id
    $(Page.Widgets.fileSupportDocument.nativeElement).find("input").click();
};

Page.fileSupportDocumentSelect = function($event, widget, selectedFiles) {
    // Page.Widgets.spinner.show = true
};


Page.uploadFileonSuccess = function(variable, data) {
    Page.Widgets.spinner.show = false
    var dataList = Page.Widgets.tableData.listItems._results

    dataList.filter(filter_item => {
        return filter_item.item.id === Page.Variables.selectedList.dataValue
    }).map(function(map_item) {
        map_item.item.files = data[0].path;
    })
};

Page.deleteDocClick = function($event, widget, item, currentItemWidgets) {
    item.files = ''
};
//FIle



Page.btnProceedClick = async function($event, widget) {
    var dataList = Page.Widgets.tableData.listItems._results
    Page.Widgets.modalConfirm.close()
    Page.Widgets.spinner.show = true

    let valid = true;
    //check input
    dataList.forEach((x) => {
        if (x.item.confirmQty != x.item.deliveryQty) {
            valid = false
        }
    });

    if (!valid) {
        App.Actions.appNotification.setMessage('GR Qty Less than Delivery Qty')
        App.Actions.appNotification.setToasterClass("error")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.Widgets.spinner.show = false;
        return 0;
    }
    await Page.Variables.DBGrLineItemAllData.invoke()

    var poQty = dataList.reduce((prep, item) => prep + item.item.poQty, 0)
    var deliveryQty = Page.Variables.DBGrLineItemAllData.dataSet.reduce((prep, item) => prep + item.deliveryQty, 0)




    try {
        var InsertGrLineConfirm = await Page.InsertGrLineConfirm(dataList)
        await Page.INTERFACEGR(dataList)

        if (Page.Variables.interfaceGR.dataSet.status === false) {
            Page.Widgets.spinner.show = false
            Page.Variables.DBErrorSAP.invoke()
            App.Actions.appNotification.setMessage('Interface Error')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
        } else {
            await Page.Variables.DBGrLineItemAll.invoke()
            await Page.Variables.DBPoLineItem.invoke()
            let grQtyCompare = Page.Variables.DBGrLineItemAll.dataSet.reduce((prep, item) => prep + item.deliveryQty, 0)
            let poQtyCompare = Page.Variables.DBPoLineItem.dataSet.reduce((prep, item) => prep + item.poQty, 0)
            if (poQtyCompare <= grQtyCompare) {
                await Page.Variables.DBUpdatePO.invoke({
                    "inputFields": {
                        "status": 'Complete',
                        "id": Page.pageParams.id
                    }
                })
            }
            Page.Widgets.spinner.show = false
            App.Actions.appNotification.setMessage('Successfully')
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            App.Actions.goToPage_POReadyForGR.invoke()
        }

    } catch (e) {
        Page.Widgets.spinner.show = false
        Page.Variables.DBGrLineItem.invoke()
        Page.Variables.DBErrorSAP.invoke()
        App.Actions.appNotification.setMessage('Internal Server Error')
        App.Actions.appNotification.setToasterClass("error")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
    }
};



Page.INTERFACEGR = async function(dataList) {
    return new Promise(async(resolve, reject) => {
        for (var i = 0; i < dataList.length; i++) {

            try {
                var interfaceGR = await Page.Variables.api_GRPost.invoke({
                    inputFields: {
                        RequestBody: {
                            id_gr: dataList[i].item.id,
                            user_created: App.Variables.loggedInUser.dataSet.name,
                            type: 'GR'
                        }
                    }
                })
                interfaceGR = JSON.parse(interfaceGR.body)
                if (interfaceGR.status === false) {
                    Page.Variables.interfaceGR.setValue('status', interfaceGR.status)
                    Page.Variables.interfaceGR.setValue('data', interfaceGR.data)
                }
            } catch (err) {
                reject(err);
            }


            if (i === dataList.length - 1) {
                resolve(true)
            }

        }
    })
}


Page.InsertGrLineConfirm = async function(dataList) {
    return new Promise(async(resolve, reject) => {
        for (var i = 0; i < dataList.length; i++) {
            try {
                var checkData = await Page.Variables.DBInsertGrConfirm.invoke({
                    "filterFields": {
                        idGr: {
                            "value": dataList[i].item.id
                        },
                    }
                })

                if (checkData.data.length > 0) {
                    await Page.Variables.DBInsertGrConfirm.deleteRecord({
                        "row": {
                            id: checkData.data[0].id
                        }
                    })
                }

                await Page.Variables.DBInsertGrConfirm.createRecord({
                    row: {
                        assetsNo: dataList[i].item.assetsNo,
                        coa: dataList[i].item.coa,
                        condition: dataList[i].item.condition,
                        confirmQty: dataList[i].item.confirmQty,
                        confirmUnit: dataList[i].item.confirmUnit,
                        costCenter: dataList[i].item.costCenter,
                        deliveryDate: dataList[i].item.deliveryDate,
                        deliveryQty: dataList[i].item.deliveryQty,
                        discount: dataList[i].item.discount,
                        eta: dataList[i].item.eta,
                        idGr: dataList[i].item.id,
                        idPoHeader: dataList[i].item.idPoHeader,
                        idPrHeader: dataList[i].item.idPrHeader,
                        idPrLineItemHeader: dataList[i].item.idPrLineItemHeader,
                        ioNumber: dataList[i].item.ioNumber,
                        item: dataList[i].item.item,
                        itemTotalAmount: dataList[i].item.itemTotalAmount,
                        matlGroup: dataList[i].item.matlGroup,
                        matlGroupDesc: dataList[i].item.matlGroupDesc,
                        plant: dataList[i].item.plant,
                        poQty: dataList[i].item.poQty,
                        prNo: dataList[i].item.prNo,
                        prNoItem: dataList[i].item.prNoItem,
                        receivedDate: dataList[i].item.receivedDate,
                        remarks: dataList[i].item.remarks,
                        sapPrNo: dataList[i].item.sapPrNo,
                        status: 'COMPLETE',
                        unitPrice: dataList[i].item.unitPrice,
                        uom: dataList[i].item.uom,
                        files: dataList[i].item.files,
                        comment: Page.Widgets.comment.datavalue,
                        assign: Page.App.Variables.loggedInUserData.dataSet.username
                    }
                })

            } catch (err) {
                reject(err);
            }

            if (i === dataList.length - 1) {
                resolve(true)
            }
        }
    })
}



Page.UpdateStatusGrLineItem = async function(dataList) {
    return new Promise((resolve, reject) => {
        for (var i = 0; i < dataList.length; i++) {
            try {
                Page.Variables.QSetStatusGRLineItem.invoke({
                    "inputFields": {
                        "id": dataList[i].item.id,
                        "status": 'GR'
                    }
                })
            } catch (err) {
                reject(err);
            }
            if (i === dataList.length - 1) {
                resolve(true)
            }
        }
    })
}

Page.ViewClick = function($event, widget, item, currentItemWidgets) {
    // Page.Widgets.spinner.show = true
    var isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);
    var filePath = Page.Widgets.tableData.selecteditem.files

    fileExtension = filePath.substr((filePath.lastIndexOf('.') + 1));
    if (fileExtension === 'pdf' || fileExtension === 'PDF') {
        if (isChrome) {
            Page.Variables.statusShowPDF.dataSet.dataValue = true
            var $node = $("#pdf-canvas-container");
            if (filePath) {
                Page.Widgets.spinner.show = false
                PDFObject.embed(filePath, $node);
                $('#pdf-canvas-container').css('height', '700px')
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
Page.buttonClosePDFClick = function($event, widget) {
    Page.Variables.statusShowPDF.dataSet.dataValue = false
    $("#pdf-canvas-container").html('')
    $("#pdf-canvas-container").removeClass('pdfobject-container')
    $("#pdf-canvas-container").removeAttr("style");
};

Page.conditionChange = function($event, widget, item, currentItemWidgets, newVal, oldVal) {
    item.condition = widget.datavalue
};


Page.DBGrLineItemonResult = function(variable, data) {
    if (Page.Widgets.spinner) {
        Page.Widgets.spinner.show = false
    }
};

Page.DBUserRoleonSuccess = function(variable, data) {
    Page.Variables.MODELConditionPurchase.dataSet = []
    Page.Variables.MODELConditionPurchaseAdmin.dataSet = []
    for (var i = 0; i < data.length; i++) {
        if (data[i].roleId == 1) {
            Page.Variables.MODELConditionPurchaseAdmin.dataSet.push(data[i].roleId)
        }
        if (data[i].roleId == 2) {
            Page.Variables.MODELConditionPurchase.dataSet.push(data[i].roleId)
        }
    }
};