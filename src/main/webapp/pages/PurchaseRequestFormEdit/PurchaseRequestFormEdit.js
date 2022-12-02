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

    if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('PRS-004') !== -1) {
            // departement user
            Promise.resolve().then(() => {
                var arr = []
                Page.Variables.varDepUser.clearData()
                App.Variables.loggedInUserData.dataSet.user_department.forEach((dep) => {
                    arr.push(parseInt(dep.departmentId))
                })
                return arr
            }).then((dt) => {
                if (dt.length > 0) {
                    Page.Variables.varDepUser.dataSet = dt
                } else {
                    // not found departement
                    App.Actions.appNotification.setMessage('not found departement')
                    App.Actions.appNotification.setToasterClass("error")
                    App.Actions.appNotification.setToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                    App.Actions.goToPage_AdditionalBudgetPage.invoke()
                }

            })
        } else {
            App.Actions.goToPage_Main.invoke()
        }
    }

};

Page.getDepartmentList = function(data) {
    return data.map(function(item) {
        return item.departmentId
    }).join(",")
}

Page.vPurchaseRequestonSuccess = function(variable, data) {
    Page.Variables.vmPRModel.setData(data[0])
    if (Array.isArray(data)) {
        if (data[0].prAttchData !== null) {
            var attachment = JSON.parse(atob(data[0].prAttchData))
            Page.Variables.vmAttachmentList.setData(attachment)
        }
        if (data[0].prRepUserId !== null || data[0].prRepUserNik) {
            Page.Variables.getUserDep.listRecords({
                filterFields: {
                    "userId": {
                        "value": data[0].prRepUserNik
                    }
                }
            }).then(function(data_dept) {
                Page.Widgets.spinner1.show = false
                if (Page.getDepartmentList(data_dept.data) !== '') {
                    Page.Variables.model_filter.setValue("departmentId", Page.getDepartmentList(data_dept.data))
                } else {
                    App.Actions.appNotification.setMessage('Department Not Found')
                    App.Actions.appNotification.setToasterClass("error")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                }
            })
        }

        if (data[0].accId) {
            Page.Variables.vAccountType.dataSet.filter(item => {
                return item.accId == data[0].accId
            }).map((data_acc) => {
                if (data_acc.accChildOf) {
                    Page.Variables.model_filter.setValue("childOfAccountType", data_acc.accChildOf)
                }
            })
        }

        // if (data[0].prCreatedBy !== null) {
        //     Page.Widgets.spinner1.show = true
        //     Page.Widgets.searchDepartment.datavalue = null
        //     Page.Variables.getUserDep.listRecords({
        //         filterFields: {
        //             "userId": {
        //                 "value": 'emp::' + data[0].prCreatedBy
        //             }
        //         }
        //     }).then(function(data_dept) {
        //         Page.Widgets.spinner1.show = false
        //         if (Page.getDepartmentList(data_dept.data) !== '') {
        //             Page.Variables.vDepartment.listRecords({
        //                 filterFields: {
        //                     "departmentId": {
        //                         "value": Page.getDepartmentList(data_dept.data)
        //                     }
        //                 }
        //             })
        //         } else {
        //             App.Actions.appNotification.setMessage('Department Not Found')
        //             App.Actions.appNotification.setToasterClass("error")
        //             App.Actions.appNotification.getToasterDuration(5000)
        //             App.Actions.appNotification.invoke()
        //         }
        //     })
        // }

    }

};

Page.svGetCurrencyonSuccess = function(variable, data) {
    for (var i = 0; i < data.length; i++) {
        Page.Variables.vmCurrency.addItem(data[i])
    }
    Page.Variables.vPrLineItem.invoke()
};

Page.vPrLineItemonSuccess = function(variable, data) {
    data.forEach((dt) => {
        var currency = Page.Variables.vmCurrency.dataSet.filter(function(el) {
            return el.fromCurrency == dt.pliCurrency
        })
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
            "pliCurrency": currency[0],
            "pliEta": dt.pliEta,
            "pliCostCenter": dt.pliCostCenterId,
            "pliCostCenterId": dt.pliCostCenterId,
            "pliCostCenterTitle": dt.pliCostCenterTitle,
            "pliCoa": dt.pliCoa,
            "pliAssetNumber": dt.pliAssetNumber,
            "pliMatGroup": dt.pliMatGroupId,
            "pliMatGroupDesc": dt.pliMatGroupDesc,
            "pliUnitPrice": dt.pliUnitPrice,
            "index": Page.App.generateUUID()
        })
    })
};


Page.getBhIdList = function(data) {
    return [0, -1].concat(data.map(function(item) {
        return item.bhId
    }).filter(function(e, i, a) {
        return a.indexOf(e) == i
    })).join(",")
}


Page.getAvailableBudget = function(lineItem, availableBudgetList) {

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
        return i < indexLineItem && e.bhId == lineItem.bhId && e.pliStatus != "deleted"
    }).map(function(item) {
        return item.pliQty * parseInt(item.pliUnitPrice) * item.pliCurrency.kurs
    }).reduce(function(total, curr) {
        return total + curr
    }, 0)
    return Page.App.formatCurrency(Math.round(budget_data.availableBudget - before_line_item))
}

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
        return i <= indexLineItem && e.bhId == lineItem.bhId && e.pliStatus != "deleted"
    }).map(function(item) {
        return item.pliQty * parseInt(item.pliUnitPrice) * item.pliCurrency.kurs
    }).reduce(function(total, curr) {
        return total + curr
    }, 0)
    return Page.App.formatCurrency(Math.round(budget_data.availableBudget - before_line_item))
}

Page.selectBPNChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.textprName.datavalue = newVal.pbProjectName
};

Page.buttonAddAttachmentClick = function($event, widget) {
    $(Page.Widgets.attachmentPR.nativeElement).find("input").click();
};

Page.FileServiceUploadFileonSuccess = function(variable, data) {
    Page.Variables.vmAttachmentList.addItem({
        "fileName": data[0].fileName,
        "path": data[0].inlinePath,
        "size": data[0].length
    })
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
        if (pageNumber >= pdf.numPages) {
            return Promise.resolve()
        }
        return Page.renderPDFPage(pdf, pageNumber + 1)
    })
}

Page.pictViewAttachmentClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.varShowPdf.dataSet.dataValue = true
    // document.getElementById("pdf-canvas-container").innerHTML = ""
    // // var url = Page.Variables.vmAdditionalModel.dataSet.pathFile;
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
        total[item.bhId] += item.pliQty * parseInt(item.pliUnitPrice) * item.pliCurrency.kurs
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

    if (!Page.Widgets.selectPurchaseBy.datavalue) {
        Page.Variables.vmErrorMessage.setValue("prPurchaseBy", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.selectPaymentMethod.datavalue) {
        Page.Variables.vmErrorMessage.setValue("prPaymentMethod", "this field is required")
        isValid = false
    }

    if (!isValid) {
        alert("Please fill all mandatory field(s) to continue!")
        return
    }

    if (isBudgetEnough == false) {
        return alert("Budget is not enough")
    }

    // Check total amount
    var totalAmountPR = Page.Variables.vmListLineItem.dataSet.filter(function(el) {
        return el.pliStatus == "draft"
    }).map(function(item) {
        return (item.pliQty * item.pliUnitPrice) * item.pliCurrency.kurs
    }).reduce(function(a, b) {
        return a + b
    }, 0)

    if (totalAmountPR > parseInt(Page.Variables.vEmpPriceLimit.firstRecord.eplApproxMax)) {
        return alert("Maximum budget amount " + App.formatCurrency(Page.Variables.vEmpPriceLimit.firstRecord.eplApproxMax))
    } else if (totalAmountPR < parseInt(Page.Variables.vEmpPriceLimit.firstRecord.eplApproxMin)) {
        return alert("Minimum budget amount " + App.formatCurrency(Page.Variables.vEmpPriceLimit.firstRecord.eplApproxMin))
    }

    var attachment = btoa(JSON.stringify(Page.Variables.vmAttachmentList.dataSet))
    Page.Widgets.spinner1.show = true
    Page.Variables.vPurchaseRequest.updateRecord({
        row: {
            "prId": App.pageParams.prId,
            "cid": Page.Widgets.searchCompany.datavalue.cid,
            "departmentId": Page.Widgets.searchDepartment.datavalue.departmentId,
            "pbId": !Page.Widgets.selectBPN.datavalue ? null : Page.Widgets.selectBPN.datavalue.pbId,
            "accId": Page.Widgets.selectAccountType.datavalue.accId,
            "prRef": Page.Variables.vPurchaseRequest.dataSet[0].prRef,
            // "prCreatedBy": (Page.Widgets.searchRepresentative.datavalue ? ("emp::" + Page.Widgets.searchRepresentative.datavalue.nik) : Page.App.Variables.loggedInUserData.dataSet.username),
            "prCreatedBy": (Page.App.Variables.loggedInUserData.dataSet.username),
            "prCreatedName": (Page.Widgets.searchRepresentative.datavalue ? (Page.Widgets.searchRepresentative.datavalue.employeeName) : Page.App.Variables.loggedInUserData.dataSet.user_full_name),
            "prCreatedAt": new Date().toISOString(),
            "prModifiedBy": (Page.Widgets.searchRepresentative.datavalue ? ("emp::" + Page.Widgets.searchRepresentative.datavalue.nik) : Page.App.Variables.loggedInUserData.dataSet.username),
            "prModifiedAt": new Date().toISOString(),
            "prStatus": "Draft",
            "prRepUserId": Page.Widgets.searchRepresentative.datavalue ? Page.Widgets.searchRepresentative.datavalue.id : null,
            "prRepUserName": Page.Widgets.searchRepresentative.datavalue ? Page.Widgets.searchRepresentative.datavalue.employeeName : null,
            "prRepUserNik": Page.Widgets.searchRepresentative.datavalue ? "emp::" + Page.Widgets.searchRepresentative.datavalue.nik : null,
            "prPurchaseBy": Page.Widgets.selectPurchaseBy.datavalue,
            "prPaymentMethod": Page.Widgets.selectPaymentMethod.datavalue,
            "prProject": Page.Widgets.textprName.datavalue,
            "prDate": Page.Widgets.selectDate.datavalue,
            "prPriority": Page.Widgets.selectPriority.datavalue,
            "prAttchData": !Page.Variables.vmAttachmentList.dataSet.length ? null : attachment
        }
    }).then(function(res) {
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
        var line_item_promise = []
        for (var i = 0; i < Page.Variables.vmListLineItem.dataSet.length; i++) {
            if (dataList[i].pliId == null) {
                line_item_promise.push(Page.Variables.insert_PRLineItem.createRecord({
                        row: {
                            // "pliId": null,
                            "prId": App.pageParams.prId,
                            "bhId": dataList[i].bhId,
                            "pliCreatedBy": Page.App.Variables.loggedInUserData.dataSet.username,
                            "pliCreatedAt": dataList[i].pliCreatedAt,
                            "pliModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
                            "pliModifiedAt": new Date(),
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
                            "pliMatGroupId": dataList[i].pliMatGroup,
                            "pliMatGroupDesc": dataList[i].pliMatGroupDesc,
                            "pliUnitPrice": dataList[i].pliUnitPrice
                        }
                    })
                    // .then(function() {
                    //     Page.vPrLineItemonSuccess()
                    // })
                )
            } else {
                line_item_promise.push(Page.Variables.insert_PRLineItem.updateRecord({
                        row: {
                            "pliId": dataList[i].pliId,
                            "prId": App.pageParams.prId,
                            "bhId": dataList[i].bhId,
                            "pliCreatedBy": dataList[i].pliCreatedBy,
                            "pliCreatedAt": dataList[i].pliCreatedAt,
                            "pliModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
                            "pliModifiedAt": new Date(),
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
                            "pliMatGroupId": dataList[i].pliMatGroup,
                            "pliMatGroupDesc": dataList[i].pliMatGroupDesc,
                            "pliUnitPrice": dataList[i].pliUnitPrice
                        }
                    })
                    // .then(function() {
                    //     Page.vPrLineItemonSuccess()
                    // })
                )
            }
        }
        return Promise.all(line_item_promise).then(function() {
            return res
        })
    }).then(function(res) {
        Page.Widgets.spinner1.show = false
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        App.Actions.goToPage_PurchaseRequestPage.invoke()
    }).catch(function(err) {
        Page.Widgets.spinner1.show = false
    })
};

Page.buttonSubmitClick = function($event, widget) {
    // check availabel budget io
    var budget_spend = Page.Variables.vmListLineItem.dataSet.reduce(function(total, item) {
        if (!total[item.bhId]) {
            total[item.bhId] = 0
        }
        total[item.bhId] += item.pliQty * parseInt(item.pliUnitPrice) * item.pliCurrency.kurs
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

    if (!Page.Widgets.selectPurchaseBy.datavalue) {
        Page.Variables.vmErrorMessage.setValue("prPurchaseBy", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.selectPaymentMethod.datavalue) {
        Page.Variables.vmErrorMessage.setValue("prPaymentMethod", "this field is required")
        isValid = false
    }

    if (Page.Widgets.budgetProposalOption.datavalue) {
        if (!Page.Widgets.selectBPN.datavalue) {
            Page.Variables.vmErrorMessage.setValue("selectBPN", "this field is required")
            isValid = false
        }
    }

    if (!isValid) {
        alert("Please fill all mandatory field(s) to continue!")
        return
    }

    if (isBudgetEnough == false) {
        return alert("Budget is not enough")
    }

    // Check total amount
    var totalAmountPR = Page.Variables.vmListLineItem.dataSet.filter(function(el) {
        return el.pliStatus != "deleted"
    }).map(function(item) {
        return (item.pliQty * item.pliUnitPrice) * item.pliCurrency.kurs
    }).reduce(function(a, b) {
        return a + b
    }, 0)

    if (totalAmountPR > parseInt(Page.Variables.vEmpPriceLimit.firstRecord.eplApproxMax)) {
        return alert("Maximum budget amount " + App.formatCurrency(Page.Variables.vEmpPriceLimit.firstRecord.eplApproxMax))
    } else if (totalAmountPR < parseInt(Page.Variables.vEmpPriceLimit.firstRecord.eplApproxMin)) {
        return alert("Minimum budget amount " + App.formatCurrency(Page.Variables.vEmpPriceLimit.firstRecord.eplApproxMin))
    }

    var attachment = btoa(JSON.stringify(Page.Variables.vmAttachmentList.dataSet))
    Page.Widgets.spinner1.show = true
    Page.Variables.vPurchaseRequest.updateRecord({
        row: {
            "prId": App.pageParams.prId,
            "cid": Page.Widgets.searchCompany.datavalue.cid,
            "departmentId": Page.Widgets.searchDepartment.datavalue.departmentId,
            "pbId": !Page.Widgets.selectBPN.datavalue ? null : Page.Widgets.selectBPN.datavalue.pbId,
            "accId": Page.Widgets.selectAccountType.datavalue.accId,
            "prRef": null,
            // "prCreatedBy": (Page.Widgets.searchRepresentative.datavalue ? ("emp::" + Page.Widgets.searchRepresentative.datavalue.nik) : Page.App.Variables.loggedInUserData.dataSet.username),
            "prCreatedBy": (Page.App.Variables.loggedInUserData.dataSet.username),
            "prCreatedName": (Page.Widgets.searchRepresentative.datavalue ? (Page.Widgets.searchRepresentative.datavalue.employeeName) : Page.App.Variables.loggedInUserData.dataSet.user_full_name),
            "prCreatedAt": new Date().toISOString(),
            "prModifiedBy": (Page.Widgets.searchRepresentative.datavalue ? ("emp::" + Page.Widgets.searchRepresentative.datavalue.nik) : Page.App.Variables.loggedInUserData.dataSet.username),
            "prModifiedAt": new Date().toISOString(),
            "prStatus": "Submitted",
            "prRepUserId": Page.Widgets.searchRepresentative.datavalue ? Page.Widgets.searchRepresentative.datavalue.id : null,
            "prRepUserName": Page.Widgets.searchRepresentative.datavalue ? Page.Widgets.searchRepresentative.datavalue.employeeName : null,
            "prRepUserNik": Page.Widgets.searchRepresentative.datavalue ? "emp::" + Page.Widgets.searchRepresentative.datavalue.nik : null,
            "prPurchaseBy": Page.Widgets.selectPurchaseBy.datavalue,
            "prPaymentMethod": Page.Widgets.selectPaymentMethod.datavalue,
            "prProject": Page.Widgets.textprName.datavalue,
            "prDate": Page.Widgets.selectDate.datavalue,
            "prPriority": Page.Widgets.selectPriority.datavalue,
            "prAttchData": !Page.Variables.vmAttachmentList.dataSet.length ? null : attachment,
            "prBudgetProposalOption": Page.Widgets.budgetProposalOption.datavalue ? Page.Widgets.budgetProposalOption.datavalue : false
        }
    }).then(function(data) {
        var prRef = 90000000 + (data.prId % 10000000)
        return Page.Variables.svGeneratePrRef.invoke({
            "inputFields": {
                "prId": App.pageParams.prId,
                "prRef": prRef
            }
        }).then(function() {
            return data
        })
    }).then(function(res) {
        return Page.Variables.vPrHistory.createRecord({
            row: {
                "prId": res.prId,
                "prhAction": "Submitted",
                "prhActionBy": (Page.Widgets.searchRepresentative.datavalue ? ("emp::" + Page.Widgets.searchRepresentative.datavalue.nik) : Page.App.Variables.loggedInUserData.dataSet.username),
                "prhActionByName": (Page.Widgets.searchRepresentative.datavalue ? (Page.Widgets.searchRepresentative.datavalue.employeeName) : Page.App.Variables.loggedInUserData.dataSet.user_full_name),
                "prhActionAt": new Date().toISOString(),
                "prhNotes": null
            }
        })
    }).then(function(res) {
        // Add/Update Line Item
        var dataList = Page.Variables.vmListLineItem.dataSet
        var line_item_promise = []
        for (var i = 0; i < Page.Variables.vmListLineItem.dataSet.length; i++) {
            var pli_data = {
                availableBudgetLock: parseInt(Page.getAvailableBudget2(dataList[i], Page.Variables.vdbActiveBudget.dataSet).toString().replace(/\D/g, ''))
            }
            if (dataList[i].pliId == null) {
                line_item_promise.push(Page.Variables.vPrLineItem.createRecord({
                    row: {
                        // "pliId": null,
                        "prId": App.pageParams.prId,
                        "bhId": dataList[i].bhId,
                        "pliCreatedBy": Page.App.Variables.loggedInUserData.dataSet.username,
                        "pliCreatedAt": dataList[i].pliCreatedAt,
                        "pliModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
                        "pliModifiedAt": new Date(),
                        "pliStatus": dataList[i].pliStatus == 'draft' ? "active" : dataList[i].pliStatus,
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
                        "pliMatGroupId": dataList[i].pliMatGroup,
                        "pliMatGroupDesc": dataList[i].pliMatGroupDesc,
                        "pliUnitPrice": dataList[i].pliUnitPrice,
                        "pliAdditionalData": btoa(JSON.stringify(pli_data))
                    }
                }))
            } else {
                line_item_promise.push(Page.Variables.vPrLineItem.updateRecord({
                    row: {
                        "pliId": dataList[i].pliId,
                        "prId": App.pageParams.prId,
                        "bhId": dataList[i].bhId,
                        "pliCreatedBy": dataList[i].pliCreatedBy,
                        "pliCreatedAt": dataList[i].pliCreatedAt,
                        "pliModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
                        "pliModifiedAt": new Date(),
                        "pliStatus": dataList[i].pliStatus == 'draft' ? "active" : dataList[i].pliStatus,
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
                        "pliMatGroupId": dataList[i].pliMatGroup,
                        "pliMatGroupDesc": dataList[i].pliMatGroupDesc,
                        "pliUnitPrice": dataList[i].pliUnitPrice,
                        "pliAdditionalData": btoa(JSON.stringify(pli_data))
                    }
                }))
            }
        }

        return Promise.all(line_item_promise)
    }).then(function(res) {
        // get used currency
        return Promise.all([
            Page.Variables.vdbqGetCurrencyByCode.invoke({
                inputFields: {
                    currs: res.map(function(item) {
                        return item.pliCurrency
                    }).join(",")
                }
            }),
            Promise.resolve(res)
        ])
    }).then(function([{
        body
    }, res]) {
        var curr = JSON.parse(body).content
        var budget_detail_ins = res.filter(function(item) {
            return item.pliStatus == 'active'
        }).map(function(item) {
            return Page.Variables.vdbqInsertBudgetDetails.invoke({
                inputFields: {
                    "createdBy": Page.App.Variables.loggedInUserData.dataSet.username,
                    "ubCatId": null,
                    "addDocId": item.pliId,
                    "coaId": item.pliCoa,
                    "rCatId": null,
                    "createdAt": new Date(),
                    "addDocType": "pr_line_item",
                    "bdAdjustmentType": "PR",
                    "bdDocumentId": item.tblTpr.prRef,
                    "bhId": item.bhId,
                    "bdAdjustment": (curr.find(function(e) {
                        return e.fromCurrency.trim() == item.pliCurrency
                    }) || {
                        kurs: 1
                    }).kurs * item.pliQty * item.pliUnitPrice * -1,
                    "bdRemarks": "PR - " + item.tblTpr.prProject + " - " + item.pliDesc
                }
            })
        })
        return Promise.all(budget_detail_ins)
    }).then(function(res) {
        // Send Notification to Checker
        return Page.Variables.svSendCheckerNotif.invoke()
    }).then(function(res) {
        Page.Widgets.spinner1.show = false
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        App.Actions.goToPage_PurchaseRequestPage.invoke()
    }).catch(function(err) {
        Page.Widgets.spinner1.show = false
    })
};


// LINE ITEM
Page.numberOnlyInputTrap = function($event, widget) {
    setTimeout(function() {
        widget.nativeElement.children[0].value = widget.datavalue.toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
    }, 100);
}

Page.currencyInputFormator = function($event, widget) {
    if (Page.Widgets.searchCurrency.datavalue.fromCurrency === 'IDR' || Page.Widgets.searchCurrency.datavalue.fromCurrency === 'JPY') {
        Page.Widgets.textUnitPrice.datavalue = (Page.Widgets.textUnitPrice.datavalue || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
        Page.numberOnlyInputTrap($event, Page.Widgets.textUnitPrice)
    } else {
        if (Page.Widgets.textUnitPrice.datavalue.includes(",")) {
            Page.Widgets.textUnitPrice.datavalue = Page.parseDataFloatByComma(Page.Widgets.textUnitPrice.datavalue)
            Page.numberOnlyInputTrapComma($event, Page.Widgets.textUnitPrice, Page.parseDataFloatByComma(Page.Widgets.textUnitPrice.datavalue))
        } else {
            Page.Widgets.textUnitPrice.datavalue = (Page.Widgets.textUnitPrice.datavalue || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
            Page.numberOnlyInputTrap($event, Page.Widgets.textUnitPrice)
        }

    }
    Page.generateTotalAmount($event, Page.Widgets.textUnitPrice.datavalue, Page.Widgets.textQuantity.datavalue, Page.Widgets.searchCurrency.datavalue.fromCurrency, "add")
}

Page.currencyInputFormatorEdit = function($event, widget) {
    if (Page.Widgets.searchCurrencyEdit.datavalue.fromCurrency === 'IDR' || Page.Widgets.searchCurrencyEdit.datavalue.fromCurrency === 'JPY') {
        Page.Widgets.textUnitPriceEdit.datavalue = (Page.Widgets.textUnitPriceEdit.datavalue || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
        Page.numberOnlyInputTrap($event, Page.Widgets.textUnitPriceEdit)
    } else {
        if (Page.Widgets.textUnitPriceEdit.datavalue.includes(",")) {
            Page.Widgets.textUnitPriceEdit.datavalue = Page.parseDataFloatByComma(Page.Widgets.textUnitPriceEdit.datavalue)
            Page.numberOnlyInputTrapComma($event, Page.Widgets.textUnitPriceEdit, Page.parseDataFloatByComma(Page.Widgets.textUnitPriceEdit.datavalue))
        } else {
            Page.Widgets.textUnitPriceEdit.datavalue = (Page.Widgets.textUnitPriceEdit.datavalue || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
            Page.numberOnlyInputTrap($event, Page.Widgets.textUnitPriceEdit)
        }

    }
    Page.generateTotalAmount($event, Page.Widgets.textUnitPriceEdit.datavalue, Page.Widgets.textQuantityEdit.datavalue, Page.Widgets.searchCurrencyEdit.datavalue.fromCurrency, "edit")
}


Page.convertInt = function(data) {
    return parseInt(data.replace(/\D/g, ''))
}
Page.textUnitPriceChange = Page.currencyInputFormator

Page.textUnitPriceEditChange = Page.currencyInputFormatorEdit

Page.textQuantityKeyup = function($event, widget) {
    if (widget.datavalue.includes(",")) {
        widget.datavalue = Page.parseDataFloatByComma(widget.datavalue)
        Page.generateTotalAmount($event, Page.Widgets.textUnitPrice.datavalue, Page.Widgets.textQuantity.datavalue, Page.Widgets.searchCurrency.datavalue.fromCurrency, "add")
        return true
    } else {
        widget.datavalue = widget.datavalue.replace(/\D/g, '')
        Page.generateTotalAmount($event, Page.Widgets.textUnitPrice.datavalue, Page.Widgets.textQuantity.datavalue, Page.Widgets.searchCurrency.datavalue.fromCurrency, "add")
        return true
    }
};
Page.textQuantityEditKeyup = function($event, widget) {
    if (widget.datavalue.includes(",")) {
        widget.datavalue = Page.parseDataFloatByComma(widget.datavalue)
        Page.generateTotalAmount($event, Page.Widgets.textUnitPriceEdit.datavalue, Page.Widgets.textQuantityEdit.datavalue, Page.Widgets.searchCurrencyEdit.datavalue.fromCurrency, "edit")
        return true
    } else {
        widget.datavalue = widget.datavalue.replace(/\D/g, '')
        Page.generateTotalAmount($event, Page.Widgets.textUnitPriceEdit.datavalue, Page.Widgets.textQuantityEdit.datavalue, Page.Widgets.searchCurrencyEdit.datavalue.fromCurrency, "edit")
        return true
    }
};

Page.button8Click = function($event, widget) {
    Page.Variables.vmErrorMessage.clearData()
};
Page.button10Click = function($event, widget) {
    Page.Variables.vmErrorMessage.clearData()
};

Page.buttonSaveLIClick = function($event, widget) {
    Page.Widgets.buttonSaveLI.disabled = true
    Page.Variables.vmErrorMessage.clearData()
    // validation
    var isValid = true
    if (!Page.Widgets.searchMaterialGroup.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liMaterialGroup", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.textareaDescription.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liDesc", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.textSpesification.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liSpec", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.searchUOM.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liUOM", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.textQuantity.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liQty", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.textUnitPrice.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liUnitPrice", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.searchCurrency.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liCurrency", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.dateETA.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liETA", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.searchIONumber.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liIONumber", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.searchCostCenter.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liCostCenter", "this field is required")
        isValid = false
    }

    if (!isValid) {
        alert("Please fill all mandatory field(s) to continue!")
        Page.Widgets.buttonSaveLI.disabled = false
        return
    }

    if (parseInt(Page.Widgets.textTotalAmount.datavalue.replace(/\./g, '').replace(/\,/g, '.')) * Page.Widgets.searchCurrency.datavalue.kurs > Page.Variables.vEmpPriceLimit.dataSet[0].eplApproxMax) {
        Page.Widgets.buttonSaveLI.disabled = false
        return alert("Exceeds the maximum budget amount")
    } else if (parseInt(Page.Widgets.textTotalAmount.datavalue.replace(/\./g, '').replace(/\,/g, '.')) * Page.Widgets.searchCurrency.datavalue.kurs < Page.Variables.vEmpPriceLimit.dataSet[0].eplApproxMin) {
        Page.Widgets.buttonSaveLI.disabled = false
        return alert("Less than minimum budget amount")
    }

    Page.Variables.vmListLineItem.addItem({
        "pliId": null,
        "prId": App.pageParams.prId,
        "bhId": Page.Widgets.searchIONumber.datavalue.bhId,
        "ioNumber": Page.Widgets.searchIONumber.datavalue.ioNumber,
        "pliIoNumber": Page.Widgets.searchIONumber.datavalue,
        "pliCreatedBy": Page.App.Variables.loggedInUserData.dataSet.username,
        "pliCreatedAt": new Date().toISOString(),
        // "pliModifiedBy": null,
        // "pliModifiedAt": null,
        "pliModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
        "pliModifiedAt": new Date().toISOString(),
        "pliStatus": "draft",
        "pliDesc": Page.Widgets.textareaDescription.datavalue,
        "pliSpec": Page.Widgets.textSpesification.datavalue,
        "pliUom": Page.Widgets.searchUOM.datavalue.uom,
        "pliQty": Page.Widgets.textQuantity.datavalue.replace(/\,/g, '.'),
        "pliCurrency": Page.Widgets.searchCurrency.datavalue,
        "pliEta": Page.Widgets.dateETA.datavalue,
        "pliCostCenter": Page.Widgets.searchCostCenter.datavalue.costCenter,
        "pliCostCenterTitle": Page.Widgets.searchCostCenter.datavalue.shortText,
        "pliCoa": !Page.Widgets.searchCoa.datavalue ? null : Page.Widgets.searchCoa.datavalue.glNumber,
        "pliAssetNumber": Page.Widgets.textAssetNumber.datavalue,
        "pliMatGroup": Page.Widgets.searchMaterialGroup.datavalue.materialGroup,
        "pliMatGroupDesc": Page.Widgets.searchMaterialGroup.datavalue.materialGroupDesc,
        "pliUnitPrice": Page.Widgets.textUnitPrice.datavalue.replace(/\./g, '').replace(/\,/g, '.'),
        "index": Page.App.generateUUID()
    })

    Page.Widgets.dialogItem.close()
    Page.Widgets.buttonSaveLI.disabled = false
};

Page.dialogDeleteOk = function($event, widget) {
    var currLI = Page.Variables.vmListLineItem.dataSet.filter(function(el) {
        return el.index == Page.Widgets.listLineItem.selecteditem.index
    })

    Page.Variables.vmListLineItem.setItem(Page.Variables.vmListLineItem.dataSet.findIndex(function(el) {
        return el.index === Page.Widgets.listLineItem.selecteditem.index
    }), {
        "pliId": currLI[0].pliId,
        "prId": currLI[0].prId,
        "bhId": currLI[0].bhId,
        "ioNumber": currLI[0].ioNumber,
        "pliIoNumber": currLI[0].pliIoNumber,
        "pliCreatedBy": currLI[0].pliCreatedBy,
        "pliCreatedAt": currLI[0].pliCreatedAt,
        "pliModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
        "pliModifiedAt": new Date().toISOString(),
        "pliStatus": "deleted",
        "pliDesc": currLI[0].pliDesc,
        "pliSpec": currLI[0].pliSpec,
        "pliUom": currLI[0].pliUom,
        "pliQty": currLI[0].pliQty,
        "pliCurrency": currLI[0].pliCurrency,
        "pliEta": currLI[0].pliEta,
        "pliCostCenter": currLI[0].pliCostCenter,
        "pliCostCenterTitle": currLI[0].pliCostCenterTitle,
        "pliCoa": currLI[0].pliCoa,
        "pliAssetNumber": currLI[0].pliAssetNumber,
        "pliMatGroup": currLI[0].pliMatGroup,
        "pliMatGroupDesc": currLI[0].pliMatGroupDesc,
        "pliUnitPrice": currLI[0].pliUnitPrice,
        "index": currLI[0].index,
    })
    Page.Widgets.dialogDelete.close()
};

Page.filterListItem = function(variableLI) {
    var data = variableLI.filter(function(el) {
        return el.pliStatus != "deleted"
    })
    return data

}

Page.buttonSaveLiEditClick = function($event, widget) {
    Page.Variables.vmErrorMessage.clearData()
    // validation
    var isValid = true
    if (!Page.Widgets.searchMaterialGroupEdit.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liMaterialGroup", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.textareaDescriptionEdit.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liDesc", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.textSpesificationEdit.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liSpec", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.searchUOMEdit.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liUOM", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.textQuantityEdit.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liQty", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.textUnitPriceEdit.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liUnitPrice", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.searchCurrencyEdit.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liCurrency", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.dateETAEdit.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liETA", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.searchIONumberEdit.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liIONumber", "this field is required")
        isValid = false
    }
    if (!Page.Widgets.searchCostCenterEdit.datavalue) {
        Page.Variables.vmErrorMessage.setValue("liCostCenter", "this field is required")
        isValid = false
    }

    if (!isValid) {
        alert("Please fill all mandatory field(s) to continue!")
        return
    }

    if (parseInt(Page.Widgets.textTotalAmountEdit.datavalue.replace(/\./g, '').replace(/\,/g, '.')) * Page.Widgets.searchCurrencyEdit.datavalue.kurs > Page.Variables.vEmpPriceLimit.dataSet[0].eplApproxMax) {
        return alert("Exceeds the maximum budget amount")
    } else if (parseInt(Page.Widgets.textTotalAmountEdit.datavalue.replace(/\./g, '').replace(/\,/g, '.')) * Page.Widgets.searchCurrencyEdit.datavalue.kurs < Page.Variables.vEmpPriceLimit.dataSet[0].eplApproxMin) {
        return alert("Less than minimum budget amount")
    }

    var currLI = Page.Variables.vmListLineItem.dataSet.filter(function(el) {
        return el.index === Page.Widgets.listLineItem.selecteditem.index
    })

    Page.Variables.vmListLineItem.setItem(Page.Variables.vmListLineItem.dataSet.findIndex(function(el) {
        return el.index === Page.Widgets.listLineItem.selecteditem.index
    }), {
        "pliId": currLI[0].pliId,
        "prId": currLI[0].prId,
        "bhId": Page.Widgets.searchIONumberEdit.datavalue.bhId,
        "ioNumber": Page.Widgets.searchIONumberEdit.datavalue.ioNumber,
        "pliIoNumber": Page.Widgets.searchIONumberEdit.datavalue,
        "pliCreatedBy": currLI[0].pliCreatedBy,
        "pliCreatedAt": currLI[0].pliCreatedAt,
        "pliModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
        "pliModifiedAt": new Date().toISOString(),
        "pliStatus": "draft",
        "pliDesc": Page.Widgets.textareaDescriptionEdit.datavalue,
        "pliSpec": Page.Widgets.textSpesificationEdit.datavalue,
        "pliUom": Page.Widgets.searchUOMEdit.datavalue.uom,
        "pliQty": Page.Widgets.textQuantityEdit.datavalue.toString().replace(/\,/g, '.'),
        "pliCurrency": Page.Widgets.searchCurrencyEdit.datavalue,
        "pliEta": Page.Widgets.dateETAEdit.datavalue,
        "pliCostCenter": Page.Widgets.searchCostCenterEdit.datavalue.costCenter,
        "pliCostCenterTitle": Page.Widgets.searchCostCenterEdit.datavalue.shortText,
        "pliCoa": !Page.Widgets.searchCoaEdit.datavalue ? null : Page.Widgets.searchCoaEdit.datavalue.glNumber,
        "pliAssetNumber": Page.Widgets.textAssetNumberEdit.datavalue,
        "pliMatGroup": Page.Widgets.searchMaterialGroupEdit.datavalue.materialGroup,
        "pliMatGroupDesc": Page.Widgets.searchMaterialGroupEdit.datavalue.materialGroupDesc,
        "pliUnitPrice": Page.Widgets.textUnitPriceEdit.datavalue.replace(/\./g, '').replace(/\,/g, '.'),
        "index": currLI[0].index
    })

    Page.Widgets.dialogItemEdit.close()
};

Page.getTableNumberingLI = function(variable, data, idField) {
    return variable.dataSet.filter(function(el) {
        return el.pliStatus != "deleted"
    }).map(function(item) {
        return item[idField]
    }).indexOf(data[idField]) + 1
}
Page.labelAttachmentClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.FileServiceDownloadFile.invoke({
        "inputFields": {
            "file": Page.Widgets.listAttachment.selecteditem.fileName,
            "returnName": Page.Widgets.listAttachment.selecteditem.fileName
        }
    })
};



Page.generateTotalAmount = function($event, textUnitPrice, quantity, fromCurrency, type_form) {
    // var textUnitPrice = Page.Widgets.textUnitPrice.datavalue
    // var quantity = Page.Widgets.textQuantity.datavalue

    if (textUnitPrice === undefined || textUnitPrice === '') {
        textUnitPrice = '0'
    }
    if (quantity === undefined || quantity === '') {
        quantity = '0'
    }

    if (quantity.includes(",")) {
        var replaceQuantitySeperator = quantity.replace(/\./g, '')
        var replaceQuantityComma = replaceQuantitySeperator.replace(/\,/g, '.')
        quantity = parseFloat(replaceQuantityComma)
    }


    if (fromCurrency === 'IDR' || fromCurrency === 'JPY') {
        //Total Amount Item
        var total = (quantity * parseInt(textUnitPrice.replace(/\./g, ''))).toString();
        if (total.includes(".")) {
            if (type_form === 'add') {
                Page.Widgets.textTotalAmount.datavalue = Page.parseDataFloatByPoint(total)
            } else if (type_form === 'edit') {
                Page.Widgets.textTotalAmountEdit.datavalue = Page.parseDataFloatByPoint(total)
            }

        } else {
            if (type_form === 'add') {
                Page.Widgets.textTotalAmount.datavalue = App.formatCurrency(total)
            } else if (type_form === 'edit') {
                Page.Widgets.textTotalAmountEdit.datavalue = App.formatCurrency(total)
            }
        }
    } else {
        if (textUnitPrice.includes(",")) {
            //Total Amount Item
            var replaceSeperator = textUnitPrice.replace(/\./g, '')
            var replaceComma = replaceSeperator.replace(/\,/g, '.')
            var total = (quantity * parseFloat(replaceComma)).toString();
            //Check Quantity
            if (quantity.toString().includes(".")) {
                total = (quantity * parseFloat(replaceComma)).toString();
            }

            //Sum Total
            if (total.includes(".")) {
                if (type_form === 'add') {
                    Page.Widgets.textTotalAmount.datavalue = Page.parseDataFloatByPoint(total)
                } else if (type_form === 'edit') {
                    Page.Widgets.textTotalAmountEdit.datavalue = Page.parseDataFloatByPoint(total)
                }
            } else {
                if (type_form === 'add') {
                    Page.Widgets.textTotalAmount.datavalue = App.formatCurrency(total)
                } else if (type_form === 'edit') {
                    Page.Widgets.textTotalAmountEdit.datavalue = App.formatCurrency(total)
                }
            }
        } else {
            //Total Amount Item
            var total = (quantity * parseInt(textUnitPrice.replace(/\./g, ''))).toString();
            if (quantity.toString().includes(".")) {
                total = (parseFloat(quantity) * parseInt(textUnitPrice.replace(/\./g, ''))).toString();

                if (total.includes(".")) {
                    if (type_form === 'add') {
                        Page.Widgets.textTotalAmount.datavalue = Page.parseDataFloatByPoint(total)
                    } else if (type_form === 'edit') {
                        Page.Widgets.textTotalAmountEdit.datavalue = Page.parseDataFloatByPoint(total)
                    }
                } else {
                    if (type_form === 'add') {
                        Page.Widgets.textTotalAmount.datavalue = App.formatCurrency(total)
                    } else if (type_form === 'edit') {
                        Page.Widgets.textTotalAmountEdit.datavalue = App.formatCurrency(total)
                    }
                }

            } else {
                if (type_form === 'add') {
                    Page.Widgets.textTotalAmount.datavalue = App.formatCurrency(quantity * parseInt(textUnitPrice.replace(/\./g, '')))
                } else if (type_form === 'edit') {
                    Page.Widgets.textTotalAmountEdit.datavalue = App.formatCurrency(quantity * parseInt(textUnitPrice.replace(/\./g, '')))
                }
            }

        }
    }
}


Page.parseDataFloatByComma = function(data) {
    var splitString = data.split(",");
    var beforeComma = splitString[0]
    var afterComma = splitString[1]
    return (beforeComma || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.') + ',' + afterComma
}

Page.parseDataFloatByPoint = function(data) {
    var splitString = parseFloat(data).toFixed(2).toString().split(".")
    var beforeComma = splitString[0]
    var afterComma = splitString[1]
    return (beforeComma || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.') + ',' + afterComma
}

Page.numberOnlyInputTrapComma = function($event, widget, CombineComma) {
    setTimeout(function() {
        widget.nativeElement.children[0].value = CombineComma
    }, 100);
}
Page.dialogItemEditOpened = function($event, widget) {
    var quantity = Page.Widgets.listLineItem.selecteditem.pliQty.toString()
    var price = Page.Widgets.listLineItem.selecteditem.pliUnitPrice.toString()
    if (quantity.includes(".")) {
        Page.Widgets.textQuantityEdit.datavalue = Page.parseDataFloatByPoint(quantity)
        quantity = parseFloat(Page.Widgets.listLineItem.selecteditem.pliQty)
    } else {
        quantity = parseInt(Page.Widgets.listLineItem.selecteditem.pliQty)
    }

    if (price.includes(".")) {
        Page.Widgets.textUnitPriceEdit.datavalue = Page.parseDataFloatByPoint(price)
        price = parseFloat(Page.Widgets.listLineItem.selecteditem.pliUnitPrice)
    } else {
        price = parseInt(Page.Widgets.listLineItem.selecteditem.pliUnitPrice)
    }

    var total = price * quantity
    if (total.toString().includes(".")) {
        Page.Widgets.textTotalAmountEdit.datavalue = Page.parseDataFloatByPoint(total)
    } else {
        Page.Widgets.textTotalAmountEdit.datavalue = App.formatCurrency(total)
    }
};
Page.attachmentPRSelect = function($event, widget, selectedFiles) {
    var pattern = /[~!@#$%^&*,?"'`:{}|<>+]/g;
    if (pattern.test(selectedFiles[0].name)) {
        // message
        App.Actions.appNotification.setMessage("<b>Warning</b><br>Sorry, file name can't contain special characters. Please delete special characters to upload attachment.<br>Thank you.")
        // type: success, info, warning, danger
        App.Actions.appNotification.setToasterClass("warning")
        // delayed
        App.Actions.appNotification.setToasterDuration(5000)
        // invoke
        App.Actions.appNotification.invoke()

        return;
    }

    Page.Variables.FileServiceUploadFile.invoke()
};

Page.searchRepresentativeSelect = function($event, widget, selectedValue) {
    Page.Variables.model_filter.setValue("departmentId", null)
    Page.Variables.model_filter.setValue("userLevelCode", null)
    Page.Widgets.searchDepartment.datavalue = null
    Page.Widgets.spinner1.show = true
    Page.Variables.getUserDep.listRecords({
        filterFields: {
            "userId": {
                "value": 'emp::' + selectedValue.nik
            }
        }
    }).then(function(data_dept) {
        Page.Widgets.spinner1.show = false
        if (Page.getDepartmentList(data_dept.data) !== '') {
            Page.Variables.model_filter.setValue("departmentId", Page.getDepartmentList(data_dept.data))
            Page.Variables.model_filter.setValue("userLevelCode", selectedValue.jobGradeCode.split('-')[0])
        } else {
            App.Actions.appNotification.setMessage('Data Not Found')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
        }
    })
};

Page.searchRepresentativeClear = function($event, widget) {
    Page.Variables.model_filter.setValue("departmentId", null)
    Page.Variables.model_filter.setValue("userLevelCode", null)
    Page.Widgets.searchDepartment.datavalue = null
};

Page.vEmployeeDefonSuccess = function(variable, data) {
    if (data.length > 0) {
        Page.Variables.model_filter.setValue("userLevelCode", data[0].jobGradeCode.split('-')[0])
    }
};
Page.searchCompanySelect = function($event, widget, selectedValue) {
    if (selectedValue.ccode == '1000') {
        Page.Variables.vViewCostCenter.filterExpressions.rules[0].value = 1;
        // var filtered = Page.Variables.getCostCenter.dataSet.filter(p => String(p.costCenter).startsWith('1'));
        // Page.Variables.modelCostCenter.dataSet = filtered
    } else {
        Page.Variables.vViewCostCenter.filterExpressions.rules[0].value = 2;
        // var filtered = Page.Variables.getCostCenter.dataSet.filter(p => String(p.costCenter).startsWith('2'));
        // Page.Variables.modelCostCenter.dataSet = filtered
    }
    Page.Variables.vViewCostCenter.invoke()
};
Page.selectAccountTypeChange = function($event, widget, newVal, oldVal) {
    if (newVal.accChildOf) {
        Page.Variables.model_filter.setValue("childOfAccountType", newVal.accChildOf)
    } else {
        Page.Variables.model_filter.setValue("childOfAccountType", null)
    }
};