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
        if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
            if (App.Variables.loggedInUser.dataSet.roles.indexOf('PRS-007') !== -1) {

            } else {
                App.Actions.goToPage_Main.invoke()
            }
        }
    }

};

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

Page.vPurchaseRequestonSuccess = function(variable, data) {
    Page.Variables.vmPRModel.setData(data[0])
    if (Array.isArray(data)) {
        if (data[0].prAttchData !== null) {
            var attachment = JSON.parse(atob(data[0].prAttchData))
            Page.Variables.vmAttachmentList.setData(attachment)
        }
    }

    return Promise.resolve().then(function() {
        return Page.Variables.dbTblTPrLineItem.invoke()
    }).then(function() {
        return Page.Variables.vEmployee.invoke({
            "filterFields": {
                "nik": {
                    "value": data[0].prRepUserNik ? data[0].prRepUserNik.split("::")[1] : data[0].prCreatedBy.split("::")[1]
                }
            }
        })
    }).then(function(res) {
        console.table(res.data)
        if (res.data[0].supervisorNik === '2439') {
            return Page.Variables.dbTblMUserDep.invoke({
                "filterFields": {
                    "userId": {
                        "value": data[0].prCreatedBy
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
                    App.Actions.appNotification.setMessage(res.data[0].employeeName + ' not found departement')
                    App.Actions.appNotification.setToasterClass("error")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                    App.Actions.goToPage_TaskListCheckerPage.invoke()
                }
            }).then((resDep2) => {
                resDep2 = JSON.parse(resDep2.body).content
                if (resDep2.length > 0) {
                    Page.Variables.vmApprove.setValue("status", "bod")
                    Page.Variables.varBod.dataSet = resDep2[0]
                    console.log("ss", Page.Variables.varBod.dataSet)
                } else {
                    // not found BOD
                    App.Actions.appNotification.setMessage(res.data[0].employeeName + ' not found BOD')
                    App.Actions.appNotification.setToasterClass("error")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                    App.Actions.goToPage_TaskListCheckerPage.invoke()
                }
            })
        } else {
            Page.Variables.vmApprove.setValue("status", "approve")
        }
    })
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

Page.dbTblTPrLineItemonBeforeDatasetReady = function(variable, data) {
    var arr = []

    Promise.resolve().then(function() {
        Page.Variables.vmListLineItem.clearData()
        Page.Variables.varPrMaterial.clearData()
        Page.Variables.vmApprove.setValue("amount", 0)
        Page.Variables.varShowAddDetail.dataSet.dataValue = true

        return Promise.resolve()
    }).then(() => {
        //     data.forEach((dt) => {
        //         dt.status = "old"
        //         arr.push(dt)

        //         var currency = Page.Variables.vmCurrency.dataSet.filter(function(el) {
        //             return el.fromCurrency == dt.pliCurrency
        //         })

        //         Page.Variables.vmListLineItem.addItem({
        //             "pliId": dt.pliId,
        //             "prId": dt.prId,
        //             "bhId": dt.bhId,
        //             "ioNumber": dt.tblTbudgetHeader.ioNumber,
        //             "pliIoNumber": null,
        //             "pliCreatedBy": dt.pliCreatedBy,
        //             "pliCreatedAt": dt.pliCreatedAt,
        //             "pliModifiedBy": dt.pliModifiedBy,
        //             "pliModifiedAt": dt.pliModifiedAt,
        //             "pliStatus": dt.pliStatus,
        //             "pliDesc": dt.pliDesc,
        //             "pliSpec": dt.pliSpec,
        //             "pliUom": dt.pliUom,
        //             "pliQty": dt.pliQty,
        //             "pliCurrency": currency[0],
        //             "pliEta": dt.pliEta,
        //             "pliCostCenter": dt.pliCostCenterId,
        //             "pliCostCenterTitle": dt.pliCostCenterTitle,
        //             "pliCoa": dt.pliCoa,
        //             "pliAssetNumber": dt.pliAssetNumber,
        //             "pliMatGroup": dt.pliMatGroupId,
        //             "pliMatGroupDesc": dt.pliMatGroupDesc,
        //             "pliUnitPrice": dt.pliUnitPrice,
        //             "index": Page.App.generateUUID(),
        //             "pliAdditionalData": dt.pliAdditionalData
        //         })
        //     })

        //     return arr
        // }).then(() => {
        //     return Page.Variables.varPrMaterial.setData(arr)
        // }).then(() => {
        return Page.addMapLine(data.reverse())
    }).then(() => {
        var cons = Page.Variables.varPrMaterial.dataSet.filter(function(item) {
            if (item.pliCoa === null || item.pliAssetNumber === null) {
                return true
            } else {
                return false
            }
        })

        var consCoa = Page.Variables.varPrMaterial.dataSet.filter(function(item) {
            if (item.pliCoa === null) {
                return true
            } else {
                return false
            }
        })

        // Untuk activity selain "Asset" dan "Sewa Leasing", field Asset Dissable
        if (Page.Variables.vmPRModel.dataSet.tblMaccountType.accId === 3 || Page.Variables.vmPRModel.dataSet.tblMaccountType.accId === 4) {
            // varShowApprove
            if (cons.length < 1) {
                Page.Variables.varShowApprove.dataSet.dataValue = true
            } else {
                Page.Variables.varShowApprove.dataSet.dataValue = false
            }
        } else {
            // varShowApprove
            if (consCoa.length < 1) {
                Page.Variables.varShowApprove.dataSet.dataValue = true
            } else {
                Page.Variables.varShowApprove.dataSet.dataValue = false
            }
        }

        Page.Variables.varShowAddDetail.dataSet.dataValue = false
    })

    return Page.Variables.vmListLineItem.dataSet
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
            "pliCurrency": dt.pliCurrency,
            "pliEta": dt.pliEta,
            "pliCostCenter": dt.pliCostCenterId,
            "pliCostCenterId": dt.pliCostCenterId,
            "pliCostCenterTitle": dt.pliCostCenterTitle,
            "pliCoa": dt.pliCoa,
            "pliAssetNumber": dt.pliAssetNumber,
            "pliMatGroup": dt.pliMatGroupId,
            "pliMatGroupDesc": dt.pliMatGroupDesc,
            "pliUnitPrice": dt.pliUnitPrice,
            "index": Page.App.generateUUID(),
            "pliAdditionalData": dt.pliAdditionalData,
            "kurs": tmpCurrs === 'IDR' ? 1 : JSON.parse(tmpCurrs.body).content[0].kurs
        })

        Page.Variables.varAvailableBugetTmp.dataSet.dataValue = Page.Variables.varAvailableBugetTmp.dataSet.dataValue + (dt.pliQty * parseInt(dt.pliUnitPrice) * (tmpCurrs === 'IDR' ? 1 : JSON.parse(tmpCurrs.body).content[0].kurs))

        dt.status = "old"
        Page.Variables.varPrMaterial.addItem(dt)

        // add currency amount
        if (tmpCurrs === 'IDR') {
            Page.Variables.vmApprove.setValue("amount", Page.Variables.vmApprove.getValue("amount") + (dt.pliQty * dt.pliUnitPrice))
        } else {
            Page.Variables.vmApprove.setValue("amount", Page.Variables.vmApprove.getValue("amount") + (dt.pliQty * dt.pliUnitPrice * JSON.parse(tmpCurrs.body).content[0].kurs))
        }
        return Promise.resolve()
    }).then(function(res) {
        return Page.addMapLine(data)
    }).catch(function(err) {
        console.log(err)
        return Page.addMapLine(data)
    })
}

// select list popup
Page.container115Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.varShowListPopUp.dataSet.dataValue = true
    console.log(Page.Widgets.list4.selecteditem)
};
Page.button9Click = function($event, widget) {
    //ambilCostCenter title dan id
    console.log(Page.Variables.vmErrorMessage.dataSet)
    if (!Page.Widgets.searchCoa.datavalue) {
        Page.Variables.vmErrorMessage.setValue('detailCoa', 'error')
        return false
    }

    if (!Page.Widgets.searchIO.datavalue) {
        Page.Variables.vmErrorMessage.setValue('detailIo', 'error')
        return false
    }

    if (!Page.Widgets.searchCostCenter.datavalue) {
        Page.Variables.vmErrorMessage.setValue('detailCostCenter', 'error')
        return false
    }



    let selectedIO = Page.Variables.svGetIONumber.dataSet.filter(x => x.ioNumber == Page.Widgets.searchIO.datavalue);
    let selectedCostCenter = Page.Variables.vViewCostCenter.dataSet.filter(x => x.costCenter == Page.Widgets.searchCostCenter.datavalue);

    Page.Variables.varPrMaterial.dataSet.find(x => x.pliId === Page.Widgets.list4.selecteditem.pliId).bhId = selectedIO[0].bhId
    Page.Variables.varPrMaterial.dataSet.find(x => x.pliId === Page.Widgets.list4.selecteditem.pliId).ioNumber = selectedIO[0].ioNumber
    Page.Variables.varPrMaterial.dataSet.find(x => x.pliId === Page.Widgets.list4.selecteditem.pliId).pliCostCenterId = selectedCostCenter[0].costCenter
    Page.Variables.varPrMaterial.dataSet.find(x => x.pliId === Page.Widgets.list4.selecteditem.pliId).pliCostCenterTitle = selectedCostCenter[0].longText
    Page.Variables.varPrMaterial.dataSet.find(x => x.pliId === Page.Widgets.list4.selecteditem.pliId).pliCoa = Page.Widgets.searchCoa.datavalue
    Page.Variables.varPrMaterial.dataSet.find(x => x.pliId === Page.Widgets.list4.selecteditem.pliId).pliAssetNumber = Page.Widgets.textassetno.datavalue
    Page.Variables.varPrMaterial.dataSet.find(x => x.pliId === Page.Widgets.list4.selecteditem.pliId).status = "new"
    Page.Variables.varShowListPopUp.dataSet.dataValue = false

    console.log(Page.Variables.varPrMaterial.dataSet)
};
Page.picture8_1Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.varPrMaterial.dataSet.find(x => x.pliId === Page.Widgets.list4.selecteditem.pliId).pliCoa = null
    Page.Variables.varPrMaterial.dataSet.find(x => x.pliId === Page.Widgets.list4.selecteditem.pliId).pliAssetNumber = null
    Page.Variables.varPrMaterial.dataSet.find(x => x.pliId === Page.Widgets.list4.selecteditem.pliId).status = "new"
};
Page.button11Click = function($event, widget) {
    Page.Variables.varShowAddDetail.dataSet.dataValue = true
    Promise.resolve().then(() => {
        var data = Page.Variables.varPrMaterial.dataSet.filter(x => x.status === 'new')
        return Page.mapChangeStatus(data)
    }).then(() => {
        Page.Variables.dbTblTPrLineItem.update()
        Page.Variables.varShowAddDetail.dataSet.dataValue = false
        Page.Widgets.dialogItem.close()
    })
};

Page.mapChangeStatus = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    console.log('change PR item Data', dt)
    return Promise.resolve().then(() => {
        return Page.Variables.qPrLineItemChangeCaoAsset.invoke({
            "inputFields": {
                "pliId": dt.pliId,
                "pliCostCenterId": dt.pliCostCenterId,
                "bhId": dt.bhId,
                "pliCostCenterTitle": dt.pliCostCenterTitle,
                "pliModifiedAt": new Date().toISOString(),
                "pliModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                "pliCoa": dt.pliCoa,
                "assetNumber": dt.pliAssetNumber
            }
        })
    }).then(function(res2) {
        return Page.Variables.qChangetCOABudgetDetail.invoke({
            "inputFields": {
                "pliId": dt.pliId,
                "coaId": dt.pliCoa,
                "bhId": dt.bhId
            }
        })
    }).then(function(res) {
        return Page.mapChangeStatus(data)
    }).catch(function(err) {
        console.log(err)
        return Page.mapChangeStatus(data)
    })
}
Page.buttonReviseClick = function($event, widget) {
    Page.Variables.varShowPdf.dataSet.dataValue = false
    Page.Variables.varShowAddDetail.dataSet.dataValue = true
    Promise.resolve().then(function() {
        return Page.Variables.qPrChangeStatus.invoke({
            "inputFields": {
                'prId': Page.pageParams.prId,
                "prModifiedAt": new Date().toISOString(),
                "prModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                'prStatus': "Revise"
            }
        })
    }).then(function(res) {
        return Page.Variables.vPrHistory.createRecord({
            row: {
                "prId": Page.pageParams.prId,
                "prhAction": "Revise",
                "prhActionBy": Page.App.Variables.loggedInUserData.dataSet.username,
                "prhActionByName": App.Variables.loggedInUserData.dataSet.user_full_name,
                "prhActionAt": new Date().toISOString(),
                "prhNotes": Page.Widgets.textareaComment.datavalue
            }
        })
    }).then(function() {
        return Page.Variables.dbTblTPrLineItem.update()
    }).then(function() {
        console.log('after1', Page.Variables.dbTblTPrLineItem.dataSet);
        // revise all pr line item and reverse budget
        var active_line_item = Page.Variables.dbTblTPrLineItem.dataSet.filter(function(item) {
            return item.pliStatus == 'active'
        })
        console.log('after2', active_line_item);

        var reject_line_item_promise = Promise.all(active_line_item.map(function(item) {
            item.pliModifiedAt = new Date()
            item.pliModifiedBy = Page.App.Variables.loggedInUserData.dataSet.username
            item.pliMatGroupId = item.pliMatGroup
            item.pliStatus = 'draft';
            console.log('after3', item);
            return Page.Variables.dbTblTPrLineItem.updateRecord({
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
        // return Page.Variables.svSendCheckerNotif.invoke()
        return Page.Variables.dbTblTInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "PR",
                "inbSubject": "<b>PR</b> - Segera melakukan Submitted kembali pada halaman purchase request",
                "userId": Page.Variables.vmPRModel.dataSet.prCreatedBy,
                "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
            }
        })
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

        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        App.Actions.goToPage_TaskListCheckerPage.invoke()
        Page.Variables.varShowAddDetail.dataSet.dataValue = false
    }).catch(function(err) {
        console.log("error", err)
    })
};
Page.buttonRejectClick = function($event, widget) {
    Page.Variables.varShowPdf.dataSet.dataValue = false
    Page.Variables.varShowAddDetail.dataSet.dataValue = true
    Promise.resolve().then(function() {
        return Page.Variables.dbTblTPrLineItem.update()
    }).then(function() {
        return Page.Variables.qPrChangeStatus.invoke({
            "inputFields": {
                'prId': Page.pageParams.prId,
                "prModifiedAt": new Date().toISOString(),
                "prModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                'prStatus': "Rejected"
            }
        })
    }).then(function(res) {
        return Page.Variables.vPrHistory.createRecord({
            row: {
                "prId": Page.pageParams.prId,
                "prhAction": "Rejected",
                "prhActionBy": Page.App.Variables.loggedInUserData.dataSet.username,
                "prhActionByName": App.Variables.loggedInUserData.dataSet.user_full_name,
                "prhActionAt": new Date().toISOString(),
                "prhNotes": Page.Widgets.textareaComment.datavalue
            }
        })
    }).then(function() {
        // reject all pr line item and reverse budget
        var active_line_item = Page.Variables.dbTblTPrLineItem.dataSet.filter(function(item) {
            return item.pliStatus == 'active'
        })

        var reject_line_item_promise = Promise.all(active_line_item.map(function(item) {
            item.pliModifiedAt = new Date()
            item.pliModifiedBy = Page.App.Variables.loggedInUserData.dataSet.username
            item.pliStatus = 'canceled'
            return Page.Variables.dbTblTPrLineItem.updateRecord({
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
        console.log(res)
        // return Page.Variables.svSendCheckerNotif.invoke()
        return Page.Variables.dbTblTInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "PR",
                "inbSubject": "<b>PR</b> - Segera melakukan Submitted kembali pada halaman purchase request",
                "userId": Page.Variables.vmPRModel.dataSet.prCreatedBy,
                "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
            }
        })
    }).then(async function(res) {
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
        App.Actions.goToPage_TaskListCheckerPage.invoke()
        Page.Variables.varShowAddDetail.dataSet.dataValue = false
    }).catch(function(err) {
        console.log("error", err)
    })
};

Page.buttonApproveClick = async function($event, widget) {
    var ListvarPrMaterial = Page.Variables.varPrMaterial.dataSet
    for (var i = 0; i < ListvarPrMaterial.length; i++) {
        if (Page.Variables.vmPRModel.dataSet.tblMaccountType.accCode === 'ASSET') {
            if (!ListvarPrMaterial[i].pliAssetNumber) {
                App.Actions.appNotification.setMessage("Asset Number cannot be empty")
                App.Actions.appNotification.setToasterClass("error")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
                return false
            }
        }

        if (!ListvarPrMaterial[i].pliCoa) {
            App.Actions.appNotification.setMessage("COA cannot be empty")
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
            return false
        }
    }


    Page.Variables.varShowPdf.dataSet.dataValue = false
    Page.Variables.varShowAddDetail.dataSet.dataValue = true
    var supervisor = ""
    if (Page.Widgets.budgetProposalOption.datavalue) {
        Page.Variables.qPrChangeStatus.invoke({
            "inputFields": {
                'prId': Page.pageParams.prId,
                "prModifiedAt": new Date().toISOString(),
                "prModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                'prStatus': "Approved"
            }
        })

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
            Page.Variables.varShowAddDetail.dataSet.dataValue = false
        }

        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        App.Actions.goToPage_TaskListCheckerPage.invoke()
        Page.Variables.varShowAddDetail.dataSet.dataValue = false
    } else {
        Promise.resolve().then(function() {
            return Page.Variables.qPrChangeStatus.invoke({
                "inputFields": {
                    'prId': Page.pageParams.prId,
                    "prModifiedAt": new Date().toISOString(),
                    "prModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                    'prStatus': "Waiting Approval"
                }
            })
        }).then(function() {
            return Page.Variables.dbEmployee.invoke({
                "filterFields": {
                    "nik": {
                        "value": Page.Variables.vmPRModel.dataSet.prCreatedBy.split("::")[1]
                    }
                }
            })
        }).then(function(dt) {
            supervisorNik = "emp::" + dt.data[0].supervisorNik
            var loggedInUser = App.Variables.loggedInUserData.dataSet
            var currentDate = new Date()
            // var subject = Page.Variables.vmPRModel.dataSet.prRef
            var subject = Page.Variables.vmListLineItem.dataSet[0].pliDesc
            console.log(Page.Variables.vmPRModel.dataSet)
            var prCreatedName = Page.Variables.vmPRModel.dataSet.prCreatedName


            if (Page.Widgets.searchRepresentative.datavalue.supervisorNik) {
                supervisorNik = "emp::" + Page.Widgets.searchRepresentative.datavalue.supervisorNik
                prCreatedName = Page.Variables.vmPRModel.dataSet.prRepUserName
            }

            var dataSet = {
                "tlTimestamp": currentDate.toISOString(),
                "tlType": "PR",
                "tlSubject": subject,
                "tlDueDate": currentDate.setDate(currentDate.getDate() + 3),
                "tlParamId": Page.pageParams.prId,
                // "tlSubmitterName": loggedInUser.user_full_name,
                "tlSubmitterName": prCreatedName,
                "userId": supervisorNik,
                "tlModule": "PRS",
                "isApprovalBod": 0
            }

            return Page.Variables.dbTblTTasklist.createRecord({
                row: dataSet
            })
        }).then(async function() {

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

            return Page.Variables.vPrHistory.createRecord({
                row: {
                    "prId": Page.pageParams.prId,
                    "prhAction": "Waiting Approval",
                    "prhActionBy": Page.App.Variables.loggedInUserData.dataSet.username,
                    "prhActionByName": App.Variables.loggedInUserData.dataSet.user_full_name,
                    "prhActionAt": new Date().toISOString(),
                    "prhNotes": Page.Widgets.textareaComment.datavalue
                }
            })
        }).then(function() {
            // return Page.Variables.svSendCheckerNotif.invoke()
            // send notification to creator
            Page.Variables.dbTblTInbox.createRecord({
                row: {
                    // "inbTimestamp": new Date().toISOString(),
                    "inbTaskType": "PR",
                    "inbSubject": "<b>PR</b> - PR baru telah dibuat",
                    "userId": App.Variables.loggedInUser.dataSet.name,
                    "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
                }
            });

            // send notification to representative
            if (Page.Widgets.searchRepresentative.datavalue) {
                let representative = Page.Widgets.searchRepresentative.datavalue.employeeCode;
                if (representative.length > 4) {
                    let firstNik = representative.substring(0, 1);
                    if (firstNik == "0") {
                        representative = representative.substring(1, representative.length);
                    }
                }

                Page.Variables.dbTblTInbox.createRecord({
                    row: {
                        // "inbTimestamp": new Date().toISOString(),
                        "inbTaskType": "PR",
                        "inbSubject": "<b>PR</b> - PR baru telah dibuat",
                        "userId": "emp::" + representative,
                        "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
                    }
                });
            }
            return Page.Variables.dbTblTInbox.createRecord({
                row: {
                    "inbTimestamp": new Date().toISOString(),
                    "inbTaskType": "PR",
                    "inbSubject": "<b>PR</b> - Segera melakukan approval pada halaman task list",
                    // "userId": Page.Variables.vmPRModel.dataSet.prCreatedBy,
                    "userId": supervisorNik,
                    "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
                }
            })
        }).then(function() {
            App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
            App.Actions.goToPage_TaskListCheckerPage.invoke()
            Page.Variables.varShowAddDetail.dataSet.dataValue = false
        }).catch(function(err) {
            console.log("error", err)
        })
    }


};

Page.svGetCurrencyonBeforeDatasetReady = function(variable, data) {
    for (var i = 0; i < data.length; i++) {
        Page.Variables.vmCurrency.addItem(data[i])
    }
};
Page.button7Click = function($event, widget) {
    Page.Variables.varShowListPopUp.dataSet.dataValue = false
    Page.Variables.varShowAddDetail.dataSet.dataValue = true
    Promise.resolve().then(function() {
        return Page.Variables.dbTblTPrLineItem.update()
    }).then(function() {
        Page.Widgets.dialogItem.open()
    })
};

Page.buttonBODClick = function($event, widget) {
    Page.Variables.varShowPdf.dataSet.dataValue = false
    Page.Variables.varShowAddDetail.dataSet.dataValue = true
    Promise.resolve().then(function() {
        return Page.Variables.qPrChangeStatus.invoke({
            "inputFields": {
                'prId': Page.pageParams.prId,
                "prModifiedAt": new Date().toISOString(),
                "prModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                'prStatus': "Waiting Approval"
            }
        })
    }).then(function() {
        var loggedInUser = App.Variables.loggedInUserData.dataSet
        var currentDate = new Date()
        var subject = Page.Variables.vmPRModel.dataSet.prRef
        var dataSet = {
            "tlTimestamp": currentDate.toISOString(),
            "tlType": "PR",
            "tlSubject": subject,
            "tlDueDate": currentDate.setDate(currentDate.getDate() + 3),
            "tlParamId": Page.pageParams.prId,
            // "tlSubmitterName": loggedInUser.user_full_name,
            "tlSubmitterName": Page.Variables.vmPRModel.dataSet.prCreatedName,
            //"userId": Page.Variables.vmApprove.dataSet.amount <= 300000000 ? Page.Variables.varBod.dataSet.divBod1 : Page.Variables.varBod.dataSet.divBod2,
            "userId": Page.Variables.varBod.dataSet.divBod1,
            "tlModule": "PRS",
            "isApprovalBod": 0
        }

        return Page.Variables.dbTblTTasklist.createRecord({
            row: dataSet
        })
    }).then(function() {
        return Page.Variables.vPrHistory.createRecord({
            row: {
                "prId": Page.pageParams.prId,
                "prhAction": "Waiting Approval",
                "prhActionBy": Page.App.Variables.loggedInUserData.dataSet.username,
                "prhActionByName": App.Variables.loggedInUserData.dataSet.user_full_name,
                "prhActionAt": new Date().toISOString(),
                "prhNotes": Page.Widgets.textareaComment.datavalue
            }
        })
    }).then(function() {
        // return Page.Variables.svSendCheckerNotif.invoke()
        return Page.Variables.dbTblTInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "PR",
                "inbSubject": "<b>PR</b> - Segera melakukan approval pada halaman task list",
                //"userId": Page.Variables.vmApprove.dataSet.amount <= 300000000 ? Page.Variables.varBod.dataSet.divBod1 : Page.Variables.varBod.dataSet.divBod2,
                "userId": Page.Variables.varBod.dataSet.divBod1,
                "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
            }
        })
    }).then(async function() {
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
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        App.Actions.goToPage_TaskListCheckerPage.invoke()
        Page.Variables.varShowAddDetail.dataSet.dataValue = false



    }).catch(function(err) {
        console.log("error", err)
    })
};

Page.searchCoaSelect = function($event, widget, selectedValue) {
    Page.Variables.vmErrorMessage.setValue('detailCoa', '')
};
Page.searchIOSelect = function($event, widget, selectedValue) {
    Page.Variables.vmErrorMessage.setValue('detailIo', '')
};
Page.searchCostCenterSelect = function($event, widget, selectedValue) {
    Page.Variables.vmErrorMessage.setValue('detailCostCenter', '')
};
Page.selectAccountTypeChange = function($event, widget, newVal, oldVal) {
    if (newVal.accChildOf) {
        Page.Variables.model_filter.setValue("childOfAccountType", newVal.accChildOf)
    } else {
        Page.Variables.model_filter.setValue("childOfAccountType", null)
    }
};

//change to reclass on-success
Page.vPrHistoryonSuccess = function(variable, data) {
    Page.Variables.MODELHistory.dataSet = data
    Page.Variables.DBTaskList.invoke()
        .then(function(res) {
            if (res.data.length > 0) {
                Page.Variables.MODELHistory.dataSet.push({
                    prhActionByName: res.data[0].approvalName,
                    prhAction: 'Waiting Approval'
                })
            }
        })
};