/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */



/* perform any action on widgets/variables within this block */
Page.onReady = async function() {
    if (Page.Widgets.spinner) {
        Page.Widgets.spinner.show = true
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



Page.DBGrLineItemConfirmonResult = function(variable, data) {
    if (Page.Widgets.spinner) {
        Page.Widgets.spinner.show = false
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

Page.conditionKeyup = function($event, widget, item, currentItemWidgets) {
    item.condition = widget.datavalue
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


        if (dataList[i].item.confirmQty === undefined) {
            dataList[i].item.confirmQty = dataList[i].item.confirmQty
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
    Page.Widgets.spinner.show = true
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


    try {
        await Page.INTERFACEGR(dataList)
        if (Page.Variables.interfaceGR.dataSet.status === false) {
            Page.Widgets.spinner.show = false
            Page.Variables.DBErrorSAP.invoke()
            App.Actions.appNotification.setMessage('Interface Error')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
        } else {
            var InsertGrLineConfirm = await Page.InsertGrLineConfirm(dataList)
            var grQuantityStatus = await Page.Variables.DBGrQuantityDelivery.invoke({
                inputFields: {
                    idPoHeader: Page.pageParams.id
                }
            }).then(function(res) {
                return res
            })
            if (grQuantityStatus.ok === true) {
                var grQuantity = JSON.parse(grQuantityStatus.body)
                if (grQuantity.totalPoQty === grQuantity.totalConfirmQty) {
                    await Page.Variables.DBUpdatePOStatus.invoke({
                        "inputFields": {
                            "status": 'Complete',
                            "id": Page.pageParams.id
                        }
                    })
                }
            }

            App.Actions.appNotification.setMessage('Successfully')
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            App.Actions.goToPage_GRConfirm.invoke()
        }

    } catch (e) {
        Page.Widgets.spinner.show = false
        App.Actions.appNotification.setMessage('Internal Server Error')
        App.Actions.appNotification.setToasterClass("error")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
    }



};


Page.InsertGrLineConfirm = async function(dataList) {
    return new Promise(async(resolve, reject) => {
        try {
            for (var i = 0; i < dataList.length; i++) {
                await Page.Variables.DBInsertGrConfirm.updateRecord({
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
                        id: dataList[i].item.id,
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

                await Page.Variables.DBGRCompareQtyConfirm.invoke({
                    inputFields: {
                        id_pr_line_item_header: dataList[i].item.idPrLineItemHeader
                    }
                })

                var DBGRCompareQtyConfirm = Page.Variables.DBGRCompareQtyConfirm.dataSet
                if (DBGRCompareQtyConfirm[0].deliveryItem === DBGRCompareQtyConfirm[0].confirmItem) {
                    await Page.Variables.DBSetStatusGRLineItemByPRLineHeader.invoke({
                        inputFields: {
                            id_pr_line_item_header: dataList[i].item.idPrLineItemHeader,
                            status: 'GR'
                        }
                    })
                }


                if (i === dataList.length - 1) {
                    resolve(true)
                }
            }
        } catch (e) {
            reject(e);
        }


    })
}

Page.INTERFACEGR = async function(dataList) {
    return new Promise(async(resolve, reject) => {
        for (var i = 0; i < dataList.length; i++) {

            var interfaceGR = await Page.Variables.api_GRPost.invoke({
                inputFields: {
                    RequestBody: {
                        id_gr: dataList[i].item.idGr,
                        user_created: App.Variables.loggedInUser.dataSet.name,
                        type: 'CONFIRM'
                    }
                }
            })
            interfaceGR = JSON.parse(interfaceGR.body)
            if (interfaceGR.status === false) {
                Page.Variables.interfaceGR.setValue('status', interfaceGR.status)
                Page.Variables.interfaceGR.setValue('data', interfaceGR.data)
            }

            if (i === dataList.length - 1) {
                resolve(true)
            }

        }
    })
}

// Page.UpdateStatusGrLineItem = async function(dataList) {
//     return new Promise((resolve, reject) => {
//         for (var i = 0; i < dataList.length; i++) {
//             Page.Variables.QSetStatusGRLineItem.invoke({
//                 "inputFields": {
//                     "id": dataList[i].item.id,
//                     "status": 'GR'
//                 }
//             })
//             if (i === dataList.length - 1) {
//                 resolve(true)
//             }
//         }
//     })
// }
Page.buttonViewPDFClick = function($event, widget, item, currentItemWidgets) {
    Page.Widgets.spinner.show = true
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

Page.buttonCloseClick = function($event, widget) {
    Page.Variables.statusShowPDF.dataSet.dataValue = false
    $("#pdf-canvas-container").html('')
    $("#pdf-canvas-container").removeClass('pdfobject-container')
    $("#pdf-canvas-container").removeAttr("style");
};
Page.select1Change = function($event, widget, item, currentItemWidgets, newVal, oldVal) {
    item.condition = widget.datavalue
};