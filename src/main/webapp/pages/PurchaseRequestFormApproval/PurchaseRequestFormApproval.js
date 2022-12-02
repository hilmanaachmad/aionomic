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

    if (Page.pageParams.prId === undefined) {
        App.Actions.goToPage_Main.invoke()
    } else {
        Page.Widgets.spinnerLoading.show = true
    }
};

Page.vPurchaseRequestonSuccess = function(variable, data) {
    Page.Variables.vmPRModel.setData(data[0])
    // console.log("PRMODEL", Page.Variables.vmPRModel.dataSet)
    if (Array.isArray(data)) {
        if (data[0].prAttchData !== null) {
            var attachment = JSON.parse(atob(data[0].prAttchData))
            Page.Variables.vmAttachmentList.setData(attachment)
        }
    }

};

Page.svGetCurrencyonSuccess = function(variable, data) {
    for (var i = 0; i < data.length; i++) {
        Page.Variables.vmCurrency.addItem(data[i])
    }
    Page.Variables.vPrLineItem.invoke()
};

Page.vPrLineItemonSuccess = function(variable, data) {
    // for (var i = 0; i < data.length; i++) {
    //     var currency = Page.Variables.vmCurrency.dataSet.filter(function(el) {
    //         return el.fromCurrency == data[i].pliCurrency
    //     })
    //     Page.Variables.vmListLineItem.addItem({
    //         "pliId": data[i].pliId,
    //         "prId": data[i].prId,
    //         "bhId": data[i].bhId,
    //         "ioNumber": data[i].tblTbudgetHeader.ioNumber,
    //         "pliIoNumber": null,
    //         "pliCreatedBy": data[i].pliCreatedBy,
    //         "pliCreatedAt": data[i].pliCreatedAt,
    //         "pliModifiedBy": data[i].pliModifiedBy,
    //         "pliModifiedAt": data[i].pliModifiedAt,
    //         "pliStatus": data[i].pliStatus,
    //         "pliDesc": data[i].pliDesc,
    //         "pliSpec": data[i].pliSpec,
    //         "pliUom": data[i].pliUom,
    //         "pliQty": data[i].pliQty,
    //         "pliCurrency": currency[0],
    //         "pliEta": data[i].pliEta,
    //         "pliCostCenter": data[i].pliCostCenterId,
    //         "pliCostCenterTitle": data[i].pliCostCenterTitle,
    //         "pliCoa": data[i].pliCoa,
    //         "pliAssetNumber": data[i].pliAssetNumber,
    //         "pliMatGroup": data[i].pliMatGroupId,
    //         "pliMatGroupDesc": data[i].pliMatGroupDesc,
    //         "pliUnitPrice": data[i].pliUnitPrice,
    //         "index": Page.App.generateUUID(),
    //         "pliAdditionalData": data[i].pliAdditionalData
    //     })
    // }
    // console.log("data vmListLI", Page.Variables.vmListLineItem.dataSet)

    Page.Variables.vmListLineItem.clearData()
    Page.Widgets.spinnerLoading.show = true
    Promise.resolve().then(function() {
        return Page.addMapLine(data.reverse())
    }).then(function() {
        return Page.Variables.vEmpPriceLimit.invoke()
    })

    return data
};

Page.addMapLine = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()

    return Promise.resolve().then(() => {
        if (dt.pliCurrency === "IDR") {
            return Promise.resolve()
        } else {
            return Page.Variables.qGetCurrencyByCode.invoke({
                "inputFields": {
                    "currs": dt.pliCurrency
                }
            })
        }
    }).then((datares) => {
        var tmpCurrs = datares ? datares : "IDR"
        Page.Variables.vmListLineItem.addItem({
            "pliId": dt.pliId,
            "prId": dt.prId,
            "bhId": dt.bhId,
            "ioNumber": dt.tblTbudgetHeader.ioNumber,
            "pliIoNumber": null,
            "pliCreatedBy": dt.pliCreatedBy,
            "pliCreatedAt": dt.pliCreatedAt,
            "pliModifiedBy": dt.pliModifiedBy,
            "pliModifiedAt": dt.pliModifiedAt,
            "pliStatus": dt.pliStatus,
            "pliDesc": dt.pliDesc,
            "pliSpec": dt.pliSpec,
            "pliUom": dt.pliUom,
            "pliQty": dt.pliQty,
            // "pliCurrency": currency[0],
            "pliCurrency": dt.pliCurrency,
            "pliEta": dt.pliEta,
            "pliCostCenter": dt.pliCostCenterId,
            "pliCostCenterTitle": dt.pliCostCenterTitle,
            "pliCoa": dt.pliCoa,
            "pliAssetNumber": dt.pliAssetNumber,
            "pliMatGroupId": dt.pliMatGroupId,
            "pliMatGroupDesc": dt.pliMatGroupDesc,
            "pliUnitPrice": dt.pliUnitPrice,
            "index": Page.App.generateUUID(),
            "pliAdditionalData": dt.pliAdditionalData,
            "kurs": tmpCurrs === 'IDR' ? 1 : JSON.parse(tmpCurrs.body).content[0].kurs
        })

        Page.Variables.varAvailableBugetTmp.dataSet.dataValue = Page.Variables.varAvailableBugetTmp.dataSet.dataValue + (dt.pliQty * parseInt(dt.pliUnitPrice) * (tmpCurrs === 'IDR' ? 1 : JSON.parse(tmpCurrs.body).content[0].kurs))

        return Promise.resolve()
    }).then(function(res) {
        return Page.addMapLine(data)
    }).catch(function(err) {
        console.log(err)
        return Page.addMapLine(data)
    })
}

Page.getBhIdList = function(data) {
    return data.map(function(item) {
        return item.bhId
    }).filter(function(e, i, a) {
        return a.indexOf(e) == i
    }).join(",")
}

Page.getAvailableBudget = function(lineItem, availableBudgetList) {
    try {
        var data = JSON.parse(atob(lineItem.pliAdditionalData))
        return Page.App.formatCurrency(data.availableBudgetLock)
    } catch (err) {
        console.log(err)
        return "0"
    }
}

// Page.getAvailableBudget = function(lineItem, availableBudgetList) {

//     var budget_data = availableBudgetList.find(function(item) {
//         return item.bhId == lineItem.bhId
//     })
//     if (!budget_data) {
//         return ""
//     }

//     var indexLineItem = Page.Variables.vmListLineItem.dataSet.map(function(item) {
//         return item.index
//     }).indexOf(lineItem.index)

//     var before_line_item = Page.Variables.vmListLineItem.dataSet.filter(function(e, i) {
//         return i < indexLineItem && e.bhId == lineItem.bhId && e.pliStatus == "active"
//     }).map(function(item) {
//         return item.pliQty * parseInt(item.pliUnitPrice) * item.kurs
//     }).reduce(function(total, curr) {
//         return total + curr
//     }, 0)

//     return Page.App.formatCurrency(Math.round(budget_data.availableBudget - before_line_item))
// }

Page.getAvailableBudget2 = function(lineItem, availableBudgetList) {

    var budget_data = availableBudgetList.find(function(item) {
        return item.bhId == lineItem.bhId
    })
    if (!budget_data) {
        return ""
    }

    var indexLineItem = Page.Variables.vmListLineItem.dataSet.map(function(item) {
        return item.index
    }).indexOf(lineItem.index)

    var before_line_item = Page.Variables.vmListLineItem.dataSet.filter(function(e, i) {
        return i <= indexLineItem && e.bhId == lineItem.bhId && e.pliStatus == "active"
    }).map(function(item) {
        return item.pliQty * parseInt(item.pliUnitPrice) * item.kurs
    }).reduce(function(total, curr) {
        return total + curr
    }, 0)

    return Page.App.formatCurrency(Math.round((budget_data.availableBudget + Page.Variables.varAvailableBugetTmp.dataSet.dataValue) - before_line_item))
}

Page.selectBPNChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.textprName.datavalue = newVal.pbProjectName
};

Page.buttonAddAttachmentClick = function($event, widget) {
    $(Page.Widgets.attachmentPR.nativeElement).find("input").click();
};

Page.FileServiceUploadFileonSuccess = function(variable, data) {
    // console.log(data)
    Page.Variables.vmAttachmentList.addItem({
        "fileName": data[0].fileName,
        "path": data[0].inlinePath,
        "size": data[0].length
    })
    // console.log(Page.Variables.vmAttachmentList.dataSet)
};
Page.pictDeleteAttachmentClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.vmAttachmentList.setData(Page.Variables.vmAttachmentList.dataSet.filter(function(el) {
        return el.fileName != item.fileName
    }))
};

Page.renderPDFPage = function(pdf, pageNumber) {
    return pdf.getPage(pageNumber).then(function(page) {
        var scale = 1;
        var viewport = page.getViewport({
            scale: scale
        });

        // Prepare canvas using PDF page dimensions
        var canvas = document.createElement("canvas")
        canvas.width = "50%"
        canvas.style.marginBottom = "24px"
        var context = canvas.getContext('2d');
        canvas.height = viewport.height;
        canvas.width = viewport.width;

        // Render PDF page into canvas context
        var renderContext = {
            canvasContext: context,
            viewport: viewport
        };
        var renderTask = page.render(renderContext);
        return renderTask.promise.then(function() {
            return canvas
        })
    }).then(function(canvas) {
        document.getElementById("pdf-canvas-container").appendChild(canvas)
        if (pageNumber >= pdf.numPages) {
            return Promise.resolve()
        }
        return Page.renderPDFPage(pdf, pageNumber + 1)
    }).catch(function(err) {
        console.log(err)
        if (pageNumber >= pdf.numPages) {
            return Promise.resolve()
        }
        return Page.renderPDFPage(pdf, pageNumber + 1)
    })
}

Page.pictViewAttachmentClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.varShowPdf.dataSet.dataValue = true
    // document.getElementById("pdf-canvas-container").innerHTML = ""
    // var url = Page.Variables.vmAdditionalModel.dataSet.pathFile;
    var url = Page.Widgets.listAttachment.selecteditem.path

    // // Loaded via <script> tag, create shortcut to access PDF.js exports.
    // var pdfjsLib = window['pdfjs-dist/build/pdf'];
    // // The workerSrc property shall be specified.
    // pdfjsLib.GlobalWorkerOptions.workerSrc = '//cdnjs.cloudflare.com/ajax/libs/pdf.js/2.6.347/pdf.worker.min.js';

    // var loadingTask = pdfjsLib.getDocument(url);
    // return loadingTask.promise.then(function(pdf) {
    //     // Page.Widgets.spinner1.show = true

    //     // Fetch the first page
    //     var pageNumber = 1;
    //     return Page.renderPDFPage(pdf, pageNumber)
    // }).then(function() {
    //     // Page.Widgets.spinner1.show = false
    // });
    PDFObject.embed(url, "#pdf-canvas-container")
};
Page.picture6Click = function($event, widget) {
    Page.Variables.varShowPdf.dataSet.dataValue = false
};

Page.buttonSaveClick = function($event, widget) {
    // check availabel budget io
    var budget_spend = Page.Variables.vmListLineItem.dataSet.reduce(function(total, item) {
        if (!total[item.bhId]) {
            total[item.bhId] = 0
        }
        total[item.bhId] += item.pliQty * parseInt(item.pliUnitPrice) * item.kurs
        return total
    }, {})

    var budget_available_after = []
    for (var bhid in budget_spend) {
        budget_available_after.push(
            (Page.Variables.vdbActiveBudget.dataSet.find(function(item) {
                return item.bhId == bhid
            }) || {
                availableBudget: 0
            }).availableBudget - budget_spend[bhid]
        )
    }
    var isBudgetEnough = budget_available_after.reduce(function(result, curr) {
        if (curr < 0) {
            result = false
        }
        return result
    }, true)

    Page.Variables.vmErrorMessage.clearData()
    // validation
    var isValid = true
    if (!Page.Widgets.searchCompany.datavalue) {
        Page.Variables.vmErrorMessage.setValue("cid", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.searchDepartment.datavalue) {
        Page.Variables.vmErrorMessage.setValue("departmentId", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.selectAccountType.datavalue) {
        Page.Variables.vmErrorMessage.setValue("accId", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.searchRepresentative.datavalue) {
        Page.Variables.vmErrorMessage.setValue("prRepUserId", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.selectPurchaseBy.datavalue) {
        Page.Variables.vmErrorMessage.setValue("prPurchaseBy", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.selectPaymentMethod.datavalue) {
        Page.Variables.vmErrorMessage.setValue("prPaymentMethod", "this field is required")
        isValid = false
    }

    if (!isValid) {
        return
    }

    if (isBudgetEnough == false) {
        return alert("Budget is not enough")
    }

    var attachment = btoa(JSON.stringify(Page.Variables.vmAttachmentList.dataSet))
    Page.Widgets.spinnerLoading.show = true
    Page.Variables.vPurchaseRequest.updateRecord({
        row: {
            "prId": App.pageParams.prId,
            "cid": Page.Widgets.searchCompany.datavalue.cid,
            "departmentId": Page.Widgets.searchDepartment.datavalue.departmentId,
            "pbId": !Page.Widgets.selectBPN.datavalue ? null : Page.Widgets.selectBPN.datavalue.pbId,
            "accId": Page.Widgets.selectAccountType.datavalue.accId,
            "prRef": null,
            "prCreatedBy": Page.Variables.vmPRModel.dataSet.prCreatedBy,
            "prCreatedAt": Page.Variables.vmPRModel.dataSet.prCreatedAt,
            "prModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
            "prModifiedAt": new Date().toISOString(),
            "prStatus": "Draft",
            "prRepUserId": Page.Widgets.searchRepresentative.datavalue.id,
            "prRepUserName": Page.Widgets.searchRepresentative.datavalue.employeeName,
            "prRepUserNik": Page.Widgets.searchRepresentative.datavalue.nik,
            "prPurchaseBy": Page.Widgets.selectPurchaseBy.datavalue,
            "prPaymentMethod": Page.Widgets.selectPaymentMethod.datavalue,
            "prProject": Page.Widgets.textprName.datavalue,
            "prDate": Page.Widgets.selectDate.datavalue,
            "prPriority": Page.Widgets.selectPriority.datavalue,
            "prAttchData": !Page.Variables.vmAttachmentList.dataSet.length ? null : attachment
        }
    }).then(function(res) {
        // console.log("res", res)
        return Page.Variables.vPrHistory.createRecord({
            row: {
                "prId": res.prId,
                "prhAction": "Saved as Draft",
                "prhActionBy": Page.App.Variables.loggedInUserData.dataSet.username,
                "prhActionByName": App.Variables.loggedInUserData.dataSet.user_full_name,
                "prhActionAt": new Date().toISOString(),
                "prhNotes": null
            }
        })
    }).then(function(res) {
        var dataList = Page.Variables.vmListLineItem.dataSet
        for (var i = 0; i < Page.Variables.vmListLineItem.dataSet.length; i++) {
            if (dataList[i].pliId == null) {
                Page.Variables.vPrLineItem.createRecord({
                    row: {
                        // "pliId": null,
                        "prId": App.pageParams.prId,
                        "bhId": dataList[i].bhId,
                        "pliCreatedBy": Page.App.Variables.loggedInUserData.dataSet.username,
                        "pliCreatedAt": dataList[i].pliCreatedAt,
                        "pliModifiedBy": dataList[i].pliModifiedBy,
                        "pliModifiedAt": dataList[i].pliModifiedAt,
                        "pliStatus": dataList[i].pliStatus,
                        "pliDesc": dataList[i].pliDesc,
                        "pliSpec": dataList[i].pliSpec,
                        "pliUom": dataList[i].pliUom,
                        "pliQty": dataList[i].pliQty,
                        "pliCurrency": dataList[i].pliCurrency.fromCurrency,
                        "pliEta": dataList[i].pliEta,
                        "pliCostCenterId": dataList[i].pliCostCenter,
                        "pliCostCenterTitle": dataList[i].pliCostCenterTitle,
                        "pliCoa": dataList[i].pliCoa,
                        "pliAssetNumber": dataList[i].pliAssetNumber,
                        "pliMatGroupId": dataList[i].pliMatGroupId,
                        "pliMatGroupDesc": dataList[i].pliMatGroupDesc,
                        "pliUnitPrice": dataList[i].pliUnitPrice
                    }
                })
            } else {
                Page.Variables.vPrLineItem.updateRecord({
                    row: {
                        "pliId": dataList[i].pliId,
                        "prId": App.pageParams.prId,
                        "bhId": dataList[i].bhId,
                        "pliCreatedBy": dataList[i].pliCreatedBy,
                        "pliCreatedAt": dataList[i].pliCreatedAt,
                        "pliModifiedBy": dataList[i].pliModifiedBy,
                        "pliModifiedAt": dataList[i].pliModifiedAt,
                        "pliStatus": dataList[i].pliStatus,
                        "pliDesc": dataList[i].pliDesc,
                        "pliSpec": dataList[i].pliSpec,
                        "pliUom": dataList[i].pliUom,
                        "pliQty": dataList[i].pliQty,
                        "pliCurrency": dataList[i].pliCurrency.fromCurrency,
                        "pliEta": dataList[i].pliEta,
                        "pliCostCenterId": dataList[i].pliCostCenter,
                        "pliCostCenterTitle": dataList[i].pliCostCenterTitle,
                        "pliCoa": dataList[i].pliCoa,
                        "pliAssetNumber": dataList[i].pliAssetNumber,
                        "pliMatGroupId": dataList[i].pliMatGroupId,
                        "pliMatGroupDesc": dataList[i].pliMatGroupDesc,
                        "pliUnitPrice": dataList[i].pliUnitPrice
                    }
                })
            }
        }
        return res
    }).then(function(res) {
        Page.Widgets.spinnerLoading.show = false
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        App.Actions.goToPage_PurchaseRequestPage.invoke()
    }).catch(function(err) {
        console.log("error", err)
        Page.Widgets.spinnerLoading.show = false
    })
};


// LINE ITEM
Page.numberOnlyInputTrap = function($event, widget) {
    setTimeout(function() {
        widget.nativeElement.children[0].value = widget.datavalue.toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
    }, 100);
}

Page.currencyInputFormator = function($event, widget) {
    widget.datavalue = (widget.datavalue || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
    Page.numberOnlyInputTrap($event, widget)
}
Page.convertInt = function(data) {
    return parseInt(data.replace(/\D/g, ''))
}
Page.textUnitPriceChange = Page.currencyInputFormator


Page.filterListItem = function(variableLI) {
    var data = variableLI.filter(function(el) {
        return el.pliStatus == "active"
    })
    // console.log("data", data)
    return data

}

Page.getTableNumberingLI = function(variable, data, idField) {
    return variable.dataSet.filter(function(el) {
        return el.pliStatus == "active"
    }).map(function(item) {
        return item[idField]
    }).indexOf(data[idField]) + 1
}

Page.buttonAcceptClick = function($event, widget) {
    Page.Widgets.spinnerLoading.show = true
    Page.Variables.vPurchaseRequest.updateRecord({
        row: {
            "prId": Page.Variables.vPurchaseRequest.firstRecord.prId,
            "cid": Page.Variables.vPurchaseRequest.firstRecord.cid,
            "departmentId": Page.Variables.vPurchaseRequest.firstRecord.departmentId,
            "pbId": Page.Variables.vPurchaseRequest.firstRecord.pbId,
            "accId": Page.Variables.vPurchaseRequest.firstRecord.accId,
            "prRef": Page.Variables.vPurchaseRequest.firstRecord.prRef,
            "prCreatedBy": Page.Variables.vPurchaseRequest.firstRecord.prCreatedBy,
            "prCreatedName": Page.Variables.vPurchaseRequest.firstRecord.prCreatedName,
            "prCreatedAt": Page.Variables.vPurchaseRequest.firstRecord.prCreatedAt,
            "prModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
            "prModifiedAt": new Date().toISOString(),
            "prStatus": "Approved",
            "prRepUserId": Page.Variables.vPurchaseRequest.firstRecord.prRepUserId,
            "prRepUserName": Page.Variables.vPurchaseRequest.firstRecord.prRepUserName,
            "prRepUserNik": Page.Variables.vPurchaseRequest.firstRecord.prRepUserNik,
            "prPurchaseBy": Page.Variables.vPurchaseRequest.firstRecord.prPurchaseBy,
            "prPaymentMethod": Page.Variables.vPurchaseRequest.firstRecord.prPaymentMethod,
            "prProject": Page.Variables.vPurchaseRequest.firstRecord.prProject,
            "prDate": Page.Variables.vPurchaseRequest.firstRecord.prDate,
            "prPriority": Page.Variables.vPurchaseRequest.firstRecord.prPriority,
            "prAttchData": Page.Variables.vPurchaseRequest.firstRecord.prAttchData
        }
    }).then(function(res) {
        console.log("create history")
        return Page.Variables.vPrHistory.createRecord({
            row: {
                "prId": App.pageParams.prId,
                "prhAction": "Approved",
                "prhActionBy": Page.App.Variables.loggedInUserData.dataSet.username,
                "prhActionByName": App.Variables.loggedInUserData.dataSet.user_full_name,
                "prhActionAt": new Date().toISOString(),
                "prhNotes": Page.Widgets.textareaComment.datavalue
            }
        })
    }).then(function(res) {
        console.log("delete task list")
        return Page.Variables.svDeleteTaskList.invoke()
    }).then(function(res) {
        console.log("create inbox")
        return Page.Variables.vInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "PR",
                "inbSubject": "<b>PR</b> - Accepted Purchase Request",
                "userId": Page.Variables.vPurchaseRequest.firstRecord.prCreatedBy,
                "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
            }
        })
    }).then(async function(res) {
        console.log("Interface")
        try {
            await Page.Variables.api_PRPost.invoke({
                inputFields: {
                    RequestBody: {
                        id_pr: App.pageParams.prId
                    }
                }
            })
        } catch {
            console.log('ERROR API')
        }

        return true
    }).then(function(res) {
        console.log("notif")
        Page.Widgets.spinnerLoading.show = false
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        App.Actions.goToPage_TaskListPage.invoke()
    }).catch(function(err) {
        console.log("error", err)
        Page.Widgets.spinnerLoading.show = false
    })
};

Page.buttonApproveClick = function($event, widget) {
    Page.Widgets.spinnerLoading.show = true
    Page.Variables.vPurchaseRequest.updateRecord({
        row: {
            "prId": Page.Variables.vPurchaseRequest.firstRecord.prId,
            "cid": Page.Variables.vPurchaseRequest.firstRecord.cid,
            "departmentId": Page.Variables.vPurchaseRequest.firstRecord.departmentId,
            "pbId": Page.Variables.vPurchaseRequest.firstRecord.pbId,
            "accId": Page.Variables.vPurchaseRequest.firstRecord.accId,
            "prRef": Page.Variables.vPurchaseRequest.firstRecord.prRef,
            "prCreatedBy": Page.Variables.vPurchaseRequest.firstRecord.prCreatedBy,
            "prCreatedName": Page.Variables.vPurchaseRequest.firstRecord.prCreatedName,
            "prCreatedAt": Page.Variables.vPurchaseRequest.firstRecord.prCreatedAt,
            "prModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
            "prModifiedAt": new Date().toISOString(),
            "prStatus": Page.Variables.vPurchaseRequest.firstRecord.prStatus,
            "prRepUserId": Page.Variables.vPurchaseRequest.firstRecord.prRepUserId,
            "prRepUserName": Page.Variables.vPurchaseRequest.firstRecord.prRepUserName,
            "prRepUserNik": Page.Variables.vPurchaseRequest.firstRecord.prRepUserNik,
            "prPurchaseBy": Page.Variables.vPurchaseRequest.firstRecord.prPurchaseBy,
            "prPaymentMethod": Page.Variables.vPurchaseRequest.firstRecord.prPaymentMethod,
            "prProject": Page.Variables.vPurchaseRequest.firstRecord.prProject,
            "prDate": Page.Variables.vPurchaseRequest.firstRecord.prDate,
            "prPriority": Page.Variables.vPurchaseRequest.firstRecord.prPriority,
            "prAttchData": Page.Variables.vPurchaseRequest.firstRecord.prAttchData
        }
    }).then(function(res) {
        console.log("create history")
        return Page.Variables.vPrHistory.createRecord({
            row: {
                "prId": res.prId,
                "prhAction": Page.Variables.vPurchaseRequest.firstRecord.prStatus,
                "prhActionBy": Page.App.Variables.loggedInUserData.dataSet.username,
                "prhActionByName": App.Variables.loggedInUserData.dataSet.user_full_name,
                "prhActionAt": new Date().toISOString(),
                "prhNotes": Page.Widgets.textareaComment.datavalue
            }
        })
    }).then(function(res) {
        console.log("change task list")
        return Page.Variables.svChangeTaskListUserId.invoke({
            "inputFields": {
                "tlParamId": App.pageParams.prId,
                "userId": "emp::" + Page.Variables.vmUserSupervisor.dataSet.dataValue.supervisorNik,
                "tlType": "PR",
                "isBod": 0
            }
        })
    }).then(async function(res) {
        console.log("create inbox")

        try {
            await Page.Variables.api_ApprovalEmail.invoke({
                inputFields: {
                    RequestBody: {
                        id_pr: App.pageParams.prId,
                        type: "PR"
                    }
                }
            })
        } catch {
            console.log('ERROR API')
        }

        return Page.Variables.vInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "PR",
                "inbSubject": "<b>PR</b> - Segera melakukan approval pada halaman task list",
                "userId": "emp::" + Page.Variables.vmUserSupervisor.dataSet.dataValue.supervisorNik,
                "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
            }
        })
    }).then(function(res) {
        console.log("notif")
        Page.Widgets.spinnerLoading.show = false
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        App.Actions.goToPage_TaskListPage.invoke()
    }).catch(function(err) {
        console.log("error", err)
        Page.Widgets.spinnerLoading.show = false
    })
};

Page.buttonRejectClick = function($event, widget) {
    Page.Widgets.spinnerLoading.show = true
    return Page.Variables.vPurchaseRequest.updateRecord({
        row: {
            "prId": Page.Variables.vPurchaseRequest.firstRecord.prId,
            "cid": Page.Variables.vPurchaseRequest.firstRecord.cid,
            "departmentId": Page.Variables.vPurchaseRequest.firstRecord.departmentId,
            "pbId": Page.Variables.vPurchaseRequest.firstRecord.pbId,
            "accId": Page.Variables.vPurchaseRequest.firstRecord.accId,
            "prRef": Page.Variables.vPurchaseRequest.firstRecord.prRef,
            "prCreatedBy": Page.Variables.vPurchaseRequest.firstRecord.prCreatedBy,
            "prCreatedName": Page.Variables.vPurchaseRequest.firstRecord.prCreatedName,
            "prCreatedAt": Page.Variables.vPurchaseRequest.firstRecord.prCreatedAt,
            "prModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
            "prModifiedAt": new Date().toISOString(),
            "prStatus": "Rejected",
            "prRepUserId": Page.Variables.vPurchaseRequest.firstRecord.prRepUserId,
            "prRepUserName": Page.Variables.vPurchaseRequest.firstRecord.prRepUserName,
            "prRepUserNik": Page.Variables.vPurchaseRequest.firstRecord.prRepUserNik,
            "prPurchaseBy": Page.Variables.vPurchaseRequest.firstRecord.prPurchaseBy,
            "prPaymentMethod": Page.Variables.vPurchaseRequest.firstRecord.prPaymentMethod,
            "prProject": Page.Variables.vPurchaseRequest.firstRecord.prProject,
            "prDate": Page.Variables.vPurchaseRequest.firstRecord.prDate,
            "prPriority": Page.Variables.vPurchaseRequest.firstRecord.prPriority,
            "prAttchData": Page.Variables.vPurchaseRequest.firstRecord.prAttchData
        }
    }).then(function(res) {
        console.log("create history")
        return Page.Variables.vPrHistory.createRecord({
            row: {
                "prId": res.prId,
                "prhAction": "Rejected",
                "prhActionBy": Page.App.Variables.loggedInUserData.dataSet.username,
                "prhActionByName": App.Variables.loggedInUserData.dataSet.user_full_name,
                "prhActionAt": new Date().toISOString(),
                "prhNotes": Page.Widgets.textareaComment.datavalue
            }
        })
    }).then(function(res) {
        console.log("create inbox")
        return Page.Variables.vInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "PR",
                "inbSubject": "<b>PR</b> - Segera melakukan Submitted kembali pada halaman purchase request",
                "userId": Page.Variables.vPurchaseRequest.firstRecord.prCreatedBy,
                "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
            }
        })
    }).then(function() {
        return Page.Variables.vPrLineItem.update()
    }).then(function() {
        // reject all pr line item and reverse budget

        var active_line_item = Page.Variables.vPrLineItem.dataSet.filter(function(item) {
            return item.pliStatus == 'active'
        })

        var reject_line_item_promise = Promise.all(active_line_item.map(function(item) {
            item.pliModifiedAt = new Date()
            item.pliModifiedBy = Page.App.Variables.loggedInUserData.dataSet.username
            item.pliStatus = 'deleted'
            return Page.Variables.vPrLineItem.updateRecord({
                row: item
            })
        }))
        var budget_reverse_promise = Promise.all(active_line_item.map(function(item) {
            return Page.Variables.vdbqReverseLineItemBudget.invoke({
                inputFields: {
                    userId: Page.App.Variables.loggedInUserData.dataSet.username,
                    lineItemID: item.pliId,
                    category: "rej::PR - ",
                    reason: Page.Widgets.textprName.datavalue + " - " + item.pliDesc
                }
            })
        }))
        return Promise.all([reject_line_item_promise, budget_reverse_promise])
    }).then(function(res) {
        console.log("delete task list")
        return Page.Variables.svDeleteTaskList.invoke()
    }).then(async function(res) {
        console.log("notif")
        try {
            await Page.Variables.api_ApprovalEmail.invoke({
                inputFields: {
                    RequestBody: {
                        id_pr: App.pageParams.prId,
                        type: "PR",
                        pr_type: "reject"
                    }
                }
            })
        } catch {
            console.log('ERROR API')
        }
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.Widgets.spinnerLoading.show = false
        App.Actions.goToPage_TaskListPage.invoke()
    }).catch(function(err) {
        console.log("error", err)
        Page.Widgets.spinnerLoading.show = false
    })
};

Page.buttonReviseClick = function($event, widget) {
    Page.Widgets.spinnerLoading.show = true
    Page.Variables.vPurchaseRequest.updateRecord({
        row: {
            "prId": Page.Variables.vPurchaseRequest.firstRecord.prId,
            "cid": Page.Variables.vPurchaseRequest.firstRecord.cid,
            "departmentId": Page.Variables.vPurchaseRequest.firstRecord.departmentId,
            "pbId": Page.Variables.vPurchaseRequest.firstRecord.pbId,
            "accId": Page.Variables.vPurchaseRequest.firstRecord.accId,
            "prRef": Page.Variables.vPurchaseRequest.firstRecord.prRef,
            "prCreatedBy": Page.Variables.vPurchaseRequest.firstRecord.prCreatedBy,
            "prCreatedName": Page.Variables.vPurchaseRequest.firstRecord.prCreatedName,
            "prCreatedAt": Page.Variables.vPurchaseRequest.firstRecord.prCreatedAt,
            "prModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
            "prModifiedAt": new Date().toISOString(),
            "prStatus": "Revise",
            "prRepUserId": Page.Variables.vPurchaseRequest.firstRecord.prRepUserId,
            "prRepUserName": Page.Variables.vPurchaseRequest.firstRecord.prRepUserName,
            "prRepUserNik": Page.Variables.vPurchaseRequest.firstRecord.prRepUserNik,
            "prPurchaseBy": Page.Variables.vPurchaseRequest.firstRecord.prPurchaseBy,
            "prPaymentMethod": Page.Variables.vPurchaseRequest.firstRecord.prPaymentMethod,
            "prProject": Page.Variables.vPurchaseRequest.firstRecord.prProject,
            "prDate": Page.Variables.vPurchaseRequest.firstRecord.prDate,
            "prPriority": Page.Variables.vPurchaseRequest.firstRecord.prPriority,
            "prAttchData": Page.Variables.vPurchaseRequest.firstRecord.prAttchData
        }
    }).then(function(res) {
        console.log("create history")
        return Page.Variables.vPrHistory.createRecord({
            row: {
                "prId": res.prId,
                "prhAction": "Revise",
                "prhActionBy": Page.App.Variables.loggedInUserData.dataSet.username,
                "prhActionByName": App.Variables.loggedInUserData.dataSet.user_full_name,
                "prhActionAt": new Date().toISOString(),
                "prhNotes": Page.Widgets.textareaComment.datavalue
            }
        })
    }).then(function(res) {
        console.log("create inbox")
        return Page.Variables.vInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "PR",
                "inbSubject": "<b>PR</b> - Segera melakukan Submitted kembali pada halaman purchase request",
                "userId": Page.Variables.vPurchaseRequest.firstRecord.prCreatedBy,
                "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
            }
        })
    }).then(function() {
        return Page.Variables.vPrLineItem.update()
    }).then(function() {
        // revise all pr line item and reverse budget

        var active_line_item = Page.Variables.vPrLineItem.dataSet.filter(function(item) {
            return item.pliStatus == 'active'
        })

        var reject_line_item_promise = Promise.all(active_line_item.map(function(item) {
            item.pliModifiedAt = new Date()
            item.pliModifiedBy = Page.App.Variables.loggedInUserData.dataSet.username
            item.pliStatus = 'draft'
            return Page.Variables.vPrLineItem.updateRecord({
                row: item
            })
        }))
        var budget_reverse_promise = Promise.all(active_line_item.map(function(item) {
            return Page.Variables.vdbqReverseLineItemBudget.invoke({
                inputFields: {
                    userId: Page.App.Variables.loggedInUserData.dataSet.username,
                    lineItemID: item.pliId,
                    category: "rev::PR - ",
                    reason: Page.Widgets.textprName.datavalue + " - " + item.pliDesc
                }
            })
        }))

        return Promise.all([reject_line_item_promise, budget_reverse_promise])
    }).then(function(res) {
        console.log("delete task list")
        return Page.Variables.svDeleteTaskList.invoke()
    }).then(async function(res) {
        try {
            await Page.Variables.api_ApprovalEmail.invoke({
                inputFields: {
                    RequestBody: {
                        id_pr: App.pageParams.prId,
                        type: "PR",
                        pr_type: "revise"
                    }
                }
            })
        } catch {
            console.log('ERROR API')
        }
        console.log("notif")
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.Widgets.spinnerLoading.show = false
        App.Actions.goToPage_TaskListPage.invoke()
    }).catch(function(err) {
        console.log("error", err)
        Page.Widgets.spinnerLoading.show = false
    })
};

Page.labelAttachmentClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.FileServiceDownloadFile.invoke({
        "inputFields": {
            "file": Page.Widgets.listAttachment.selecteditem.fileName,
            "returnName": Page.Widgets.listAttachment.selecteditem.fileName
        }
    })
};

Page.vTasklistonSuccess = function(variable, data) {
    Page.Variables.varTaskList.dataSet = data[0]
    return Page.Variables.vEmployee.invoke({
        "filterFields": {
            "nik": {
                "value": data[0].userId.split("::")[1]
            }
        }
    }).then(function(dt) {
        Page.Variables.vmUserSupervisor.dataSet.dataValue = dt.data[0]
        return Promise.resolve()
    })
};

Page.vEmpPriceLimitonSuccess = function(variable, data) {
    // Check total amount
    var totalAmountPR = Page.Variables.vmListLineItem.dataSet.filter(function(el) {
        return el.pliStatus == "active"
    }).map(function(item) {
        return (item.pliQty * item.pliUnitPrice) * item.kurs
    }).reduce(function(a, b) {
        return a + b
    }, 0)


    if (Page.Variables.varTaskList.dataSet.isApprovalBod === true) {
        Page.Widgets.spinnerLoading.show = false
        Page.Variables.vmFirstApprove.dataSet.dataValue = "bodaccepted"
        //tambahin jika diatas price limit harus diapprove lagi 
    } else {
        // check atasan 2439 BOD
        if (Page.Variables.vmUserSupervisor.dataSet.dataValue.supervisorNik === "2439") {
            return Page.Variables.dbTblMUserDep.invoke({
                "filterFields": {
                    "userId": {
                        "value": "emp::" + Page.Variables.vmUserSupervisor.dataSet.dataValue.nik
                    },
                    "status": {
                        "value": "active"
                    }
                }
            }).then(function(resdep) {
                if (resdep.data.length > 0) {
                    return Page.Variables.qGetBODbyDepartementId.invoke({
                        "inputFields": {
                            "departementId": (resdep.data.map(function(dep) {
                                return parseInt(dep.departmentId)
                            }))
                        }
                    })
                } else {
                    // not found departement
                    App.Actions.appNotification.setMessage(Page.Variables.vmUserSupervisor.dataSet.dataValue.employeeName + ' not found departement')
                    App.Actions.appNotification.setToasterClass("error")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                    App.Actions.goToPage_TaskListCheckerPage.invoke()
                }
            }).then((resDep2) => {
                resDep2 = JSON.parse(resDep2.body).content
                if (resDep2.length > 0) {
                    if (data.length > 0) {
                        Page.Widgets.spinnerLoading.show = false
                        console.table(parseInt(totalAmountPR))
                        if (parseInt(totalAmountPR) <= data[0].eplApproxMax) {
                            Page.Variables.vmFirstApprove.dataSet.dataValue = "accept"
                        } else {
                            Page.Variables.vmFirstApprove.dataSet.dataValue = "bod"
                            Page.Variables.varBod.dataSet = resDep2[0]
                        }
                    } else {
                        console.log('bod approval, not found price limit')
                        App.Actions.appNotification.setMessage("not found price limit")
                        App.Actions.appNotification.setToasterClass("error")
                        App.Actions.appNotification.getToasterDuration(5000)
                        App.Actions.appNotification.invoke()
                        App.Actions.goToPage_TaskListPage.invoke()
                    }

                } else {
                    // not found BOD
                    App.Actions.appNotification.setMessage(Page.Variables.vmUserSupervisor.dataSet.dataValue.employeeName + ' not found BOD')
                    App.Actions.appNotification.setToasterClass("error")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                    App.Actions.goToPage_TaskListCheckerPage.invoke()
                }

            })

        } else {
            // tanpa BOD
            if (data.length > 0) {
                Page.Widgets.spinnerLoading.show = false
                if (parseInt(totalAmountPR) <= data[0].eplApproxMax) {
                    Page.Variables.vmFirstApprove.dataSet.dataValue = "accept"
                } else {
                    Page.Variables.vmFirstApprove.dataSet.dataValue = "approve"
                }
            } else {
                console.log('without bod approval, not found price limit')
                App.Actions.appNotification.setMessage("not found price limit")
                App.Actions.appNotification.setToasterClass("error")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
                App.Actions.goToPage_TaskListPage.invoke()
            }
        }
    }

};
Page.buttonBodClick = function($event, widget) {
    Page.Widgets.spinnerLoading.show = true
    var bod = Page.Variables.varBod.dataSet

    var totalAmountPR = Page.Variables.vmListLineItem.dataSet.filter(function(el) {
        return el.pliStatus == "active"
    }).map(function(item) {
        return (item.pliQty * item.pliUnitPrice) * item.kurs
    }).reduce(function(a, b) {
        return a + b
    }, 0)

    Page.Variables.vPurchaseRequest.updateRecord({
        row: {
            "prId": Page.Variables.vPurchaseRequest.firstRecord.prId,
            "cid": Page.Variables.vPurchaseRequest.firstRecord.cid,
            "departmentId": Page.Variables.vPurchaseRequest.firstRecord.departmentId,
            "pbId": Page.Variables.vPurchaseRequest.firstRecord.pbId,
            "accId": Page.Variables.vPurchaseRequest.firstRecord.accId,
            "prRef": Page.Variables.vPurchaseRequest.firstRecord.prRef,
            "prCreatedBy": Page.Variables.vPurchaseRequest.firstRecord.prCreatedBy,
            "prCreatedName": Page.Variables.vPurchaseRequest.firstRecord.prCreatedName,
            "prCreatedAt": Page.Variables.vPurchaseRequest.firstRecord.prCreatedAt,
            "prModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
            "prModifiedAt": new Date().toISOString(),
            "prStatus": Page.Variables.vPurchaseRequest.firstRecord.prStatus,
            "prRepUserId": Page.Variables.vPurchaseRequest.firstRecord.prRepUserId,
            "prRepUserName": Page.Variables.vPurchaseRequest.firstRecord.prRepUserName,
            "prRepUserNik": Page.Variables.vPurchaseRequest.firstRecord.prRepUserNik,
            "prPurchaseBy": Page.Variables.vPurchaseRequest.firstRecord.prPurchaseBy,
            "prPaymentMethod": Page.Variables.vPurchaseRequest.firstRecord.prPaymentMethod,
            "prProject": Page.Variables.vPurchaseRequest.firstRecord.prProject,
            "prDate": Page.Variables.vPurchaseRequest.firstRecord.prDate,
            "prPriority": Page.Variables.vPurchaseRequest.firstRecord.prPriority,
            "prAttchData": Page.Variables.vPurchaseRequest.firstRecord.prAttchData
        }
    }).then(function(res) {
        console.log("create history")
        return Page.Variables.vPrHistory.createRecord({
            row: {
                "prId": res.prId,
                "prhAction": Page.Variables.vPurchaseRequest.firstRecord.prStatus,
                "prhActionBy": Page.App.Variables.loggedInUserData.dataSet.username,
                "prhActionByName": App.Variables.loggedInUserData.dataSet.user_full_name,
                "prhActionAt": new Date().toISOString(),
                "prhNotes": Page.Widgets.textareaComment.datavalue
            }
        })
    }).then(function(res) {
        console.log("change task list")
        return Page.Variables.svChangeTaskListUserId.invoke({
            "inputFields": {
                "tlParamId": App.pageParams.prId,
                // "userId": totalAmountPR <= 300000000 ? bod.divBod1 : bod.divBod2,
                "userId": Page.Variables.varBod.dataSet.divBod2,
                "tlType": "PR",
                "isBod": 1
            }
        })
    }).then(async function(res) {
        console.log("create inbox")

        try {
            await Page.Variables.api_ApprovalEmail.invoke({
                inputFields: {
                    RequestBody: {
                        id_pr: App.pageParams.prId,
                        type: "PR"
                    }
                }
            })
        } catch {
            console.log('ERROR API')
        }

        return Page.Variables.vInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "PR",
                "inbSubject": "<b>PR</b> - Segera melakukan approval pada halaman task list",
                // "userId": totalAmountPR <= 300000000 ? bod.divBod1 : bod.divBod2,
                "userId": bod.divBod2,
                "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
            }
        })
    }).then(function(res) {
        console.log("notif")
        Page.Widgets.spinnerLoading.show = false
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        App.Actions.goToPage_TaskListPage.invoke()
    }).catch(function(err) {
        console.log("error", err)
        Page.Widgets.spinnerLoading.show = false
    })
};
Page.selectAccountTypeChange = function($event, widget, newVal, oldVal) {
    if (newVal.accChildOf) {
        Page.Variables.model_filter.setValue("childOfAccountType", newVal.accChildOf)
    } else {
        Page.Variables.model_filter.setValue("childOfAccountType", null)
    }
};