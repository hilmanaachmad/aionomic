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

    if (Page.pageParams.baId === undefined) {
        App.Actions.goToPage_Main.invoke()
    } else {
        if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
            // if (App.Variables.loggedInUser.dataSet.roles.indexOf('BUD-009') !== -1) {
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
                }
            })

            // Yearpicker format
            var a = new Yearpicker(Page.Widgets.selectYear.inputEl.nativeElement, {
                onChange: function(year) {
                    Page.Widgets.selectYear.datavalue = year
                }
            })
            // } else {
            //     App.Actions.goToPage_Main.invoke()
            // }
        }
    }
};

Page.dbTblTTasklistonSuccess = function(variable, data) {
    Page.Variables.varTaskList.dataSet = data[0]

    return Page.Variables.dbEmployee.invoke({
        "filterFields": {
            "nik": {
                "value": data[0].userId.split("::")[1]
            }
        }
    }).then((dt) => {
        console.log('dt ', dt)
        Page.Variables.varUserSupervisor.dataSet.dataValue = dt.data[0]
    })
};

// set value when editted
Page.vdbBudgetAdditionalonSuccess = function(variable, data) {
    if (Page.pageParams.baId) {
        Page.Variables.vmAdditionalModel.setData(data[0])
        if (Page.Widgets.selectYear !== undefined) {
            if (data[0] !== undefined) {
                Page.Widgets.selectYear.datavalue = data[0].baYear
                // Page.Variables.dbTblMEmpPriceLimit.invoke()
            }
        }
    }
};

// button cancel redirect to previous page
Page.buttonCancelClick = function($event, widget) {
    App.Actions.goToPage_AdditionalBudgetPage.invoke()
};

Page.submitResponse = function() {
    // message
    App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
    // type: success, info, warning, danger
    App.Actions.appNotification.setToasterClass("success")
    // delayed
    App.Actions.appNotification.getToasterDuration(5000)
    // invoke
    App.Actions.appNotification.invoke()
    // Page.Variables.vmLoading.dataSet.dataValue = false
}

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

Page.picture4Click = function($event, widget) {
    Page.Variables.varShowPdf.dataSet.dataValue = false
};

Page.buttonReviseClick = function($event, widget) {
    Promise.resolve().then(() => {
        Page.Variables.varShowPdf.dataSet.dataValue = false
        return Page.Variables.qDeleteTaskListByParamId.invoke()
    }).then(() => {
        return Page.Variables.qChangeStatusAdditional.invoke({
            "inputFields": {
                "baId": Page.pageParams.baId,
                "status": "Revise"
            }
        })
    }).then(() => {
        var loggedInUser = App.Variables.loggedInUserData.dataSet
        return Page.Variables.dbTblTAdditionalHistory.createRecord({
            row: {
                "baId": Page.pageParams.baId,
                "adEmployeeName": loggedInUser.user_full_name,
                "adAction": "Revise",
                "adTimestamp": new Date().toISOString(),
                "adComment": Page.Widgets.textarea2.datavalue
            }
        })
    }).then(() => {
        return Page.Variables.dbTblTInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "Additional",
                "inbSubject": "<b>Additional</b> - Segera melakukan Submitted kembali pada halaman additional budget",
                "userId": Page.Variables.varUserAdditionalBudget.dataSet.baCreatedId,
                "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
            }
        })
    }).then(async() => {
        try {
            await Page.Variables.api_ApprovalEmail.invoke({
                inputFields: {
                    RequestBody: {
                        type: "Additional",
                        id_budget: Page.pageParams.baId,
                        action_type: "revise"
                    }
                }
            });
        } catch (err) {
            console.log('ERROR API')
        }
        App.Actions.goToPage_TaskListPage.invoke()
    }).catch((err) => {
        console.log("err ", err)
    })
};

Page.buttonRejectClick = function($event, widget) {
    Promise.resolve().then(() => {
        Page.Variables.varShowPdf.dataSet.dataValue = false
        return Page.Variables.qDeleteTaskListByParamId.invoke()
    }).then(() => {
        return Page.Variables.qChangeStatusAdditional.invoke({
            "inputFields": {
                "baId": Page.pageParams.baId,
                "status": "Rejected"
            }
        })
    }).then(() => {
        var loggedInUser = App.Variables.loggedInUserData.dataSet
        return Page.Variables.dbTblTAdditionalHistory.createRecord({
            row: {
                "baId": Page.pageParams.baId,
                "adEmployeeName": loggedInUser.user_full_name,
                "adAction": "Rejected",
                "adTimestamp": new Date().toISOString(),
                "adComment": Page.Widgets.textarea2.datavalue
            }
        })
    }).then(() => {
        return Page.Variables.dbTblTInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "Additional",
                "inbSubject": "<b>Additional</b> - Segera melakukan Submitted kembali pada halaman additional budget",
                "userId": Page.Variables.varUserAdditionalBudget.dataSet.baCreatedId,
                "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
            }
        })
    }).then(async() => {
        try {
            await Page.Variables.api_ApprovalEmail.invoke({
                inputFields: {
                    RequestBody: {
                        type: "Additional",
                        id_budget: Page.pageParams.baId,
                        action_type: "reject"
                    }
                }
            });
        } catch (err) {
            console.log('ERROR API')
        }
        App.Actions.goToPage_TaskListPage.invoke()
    }).catch((err) => {
        console.log("err ", err)
    })
};

Page.buttonApproveClick = function($event, widget) {
    Promise.resolve().then(() => {
        Page.Variables.varShowPdf.dataSet.dataValue = false
        return Page.Variables.qChangeTaskListUserId.invoke({
            "inputFields": {
                "tlParamId": Page.pageParams.baId,
                "userId": "emp::" + Page.Variables.dbEmployee.dataSet[0].supervisorNik,
                "tlType": "Additional",
                "isBod": 0
            }
        })
    }).then(() => {
        var loggedInUser = App.Variables.loggedInUserData.dataSet
        return Page.Variables.dbTblTAdditionalHistory.createRecord({
            row: {
                "baId": Page.pageParams.baId,
                "adEmployeeName": loggedInUser.user_full_name,
                "adAction": "Submitted",
                "adTimestamp": new Date().toISOString(),
                "adComment": Page.Widgets.textarea2.datavalue
            }
        })
    }).then(() => {
        return Page.Variables.dbTblTInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "Additional",
                "inbSubject": "<b>Additional</b> - Segera melakukan approval pada halaman task list",
                "userId": "emp::" + Page.Variables.dbEmployee.dataSet[0].supervisorNik,
                "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
            }
        })
    }).then(async() => {
        try {
            await Page.Variables.api_ApprovalEmail.invoke({
                inputFields: {
                    RequestBody: {
                        type: "Additional",
                        id_budget: Page.pageParams.baId,
                        action_type: "Approval"
                    }
                }
            });
        } catch (err) {
            console.log('ERROR API')
        }
        App.Actions.goToPage_TaskListPage.invoke()
    }).catch((err) => {
        console.log("err ", err)
    })
};

Page.buttonAcceptClick = function($event, widget) {
    var additionalBudget = Page.Variables.vmAdditionalModel.dataSet
    // var io = Page.Variables.vmAdditionalModel.dataSet.tblMio
    var task = Page.Variables.varTaskList.dataSet.tlSubject
    Page.Variables.varShowPdf.dataSet.dataValue = false

    // var budgetHeader = {
    //     "buId": null,
    //     "cid": io.cid,
    //     "accId": io.accId,
    //     "brId": io.brId,
    //     "bhYear": additionalBudget.baYear,
    //     "department": io.department,
    //     "departmentId": io.departmentId,
    //     "bhEstDate": null,
    //     "bhCurrency": "IDR",
    //     "ioNumber": io.ioNumber,
    //     "ioName": io.ioDesc,
    //     "coa": io.coaId,
    //     "bhAmount": 0,
    //     "userId": App.Variables.loggedInUser.dataSet.name,
    //     "bhCreatedAt": additionalBudget.baCreatedAt,
    //     "bhCreatedBy": additionalBudget.baCreatedId,
    //     "bhStatus": "active"
    // }

    Promise.resolve().then(() => {
        return Page.Variables.qChangeStatusAdditional.invoke({
            "inputFields": {
                "baId": Page.pageParams.baId,
                "status": "Accepted"
            }
        })
    }).then(() => {
        var loggedInUser = App.Variables.loggedInUserData.dataSet
        return Page.Variables.dbTblTAdditionalHistory.createRecord({
            row: {
                "baId": Page.pageParams.baId,
                "adEmployeeName": loggedInUser.user_full_name,
                "adAction": "Accepted",
                "adTimestamp": new Date().toISOString(),
                "adComment": Page.Widgets.textarea2.datavalue
            }
        })
    }).then(() => {
        //     return Page.Variables.dbTblTBudgetHeader.createRecord({
        //         row: budgetHeader
        //     })
        // }).then((res) => {
        //     var budgetDetail = {
        //         "rCatId": null,
        //         "ubCatId": additionalBudget.ubCatId,
        //         "bhId": res.bhId,
        //         "bdOriginal": 0,
        //         "bdDocumentId": task,
        //         "bdAdjustment": parseInt(additionalBudget.baAmount.toString().replace(/\./g, '')),
        //         "bdAdjustmentType": "ADDITIONAL",
        //         "bdAfterAdjustment": 0 + parseInt(additionalBudget.baAmount.toString().replace(/\./g, '')),
        //         "bdStatus": "active",
        //         "bdCreatedBy": additionalBudget.baCreatedId,
        //         "bdCreatedAt": additionalBudget.baCreatedAt,
        //         "bdRemarks": "Additional RAB for IO " + io.ioNumber
        //     }

        // Page.Variables.qUpdatebhIdAdditionalBudget.invoke({
        //     "inputFields": {
        //         "bhId": res.bhId,
        //         "baId": Page.pageParams.baId,
        //     }
        // })

        // return Page.Variables.dbTblTBudgetDetails.createRecord({
        //     row: budgetDetail
        // })

        return Page.Variables.qInsertBudgetDetail.invoke({
            "inputFields": {
                "bhId": additionalBudget.bhId,
                "bdAdjustment": parseInt(additionalBudget.baAmount.toString().replace(/\./g, '')),
                "bdAdjustmentType": "ADDITIONAL",
                "bdDocumentId": task,
                "bdRemarks": "Additional RAB for IO " + additionalBudget.ioNumber,
                "createdBy": additionalBudget.baCreatedId,
                "createdAt": new Date().toISOString(),
                "rCatId": null,
                "ubCatId": additionalBudget.ubCatId,
            }
        })

    }).then(() => {
        return Page.Variables.qDeleteTaskListByParamId.invoke()
    }).then(() => {
        return Page.Variables.dbTblTInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "Additional",
                "inbSubject": "<b>Additional</b> - Accepted budget",
                "userId": Page.Variables.varUserAdditionalBudget.dataSet.baCreatedId,
                "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
            }
        })
    }).then(() => {
        App.Actions.goToPage_TaskListPage.invoke()
    }).catch((err) => {
        console.log("err ", err)
    })
};


Page.dbGetUseronSuccess = function(variable, data) {
    Page.Variables.varUserAdditionalBudget.dataSet = data[0]
};

Page.qDeleteTaskListByParamIdonBeforeDatasetReady = function(variable, data) {
    console.log(data)
};

Page.picture5Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.varShowPdf.dataSet.dataValue = true
    document.getElementById("pdf-canvas-container").innerHTML = ""
    // var url = Page.Variables.vmAdditionalModel.dataSet.pathFile;
    var url = Page.Widgets.list2.selecteditem.aaPathFile

    // Loaded via <script> tag, create shortcut to access PDF.js exports.
    var pdfjsLib = window['pdfjs-dist/build/pdf'];
    // The workerSrc property shall be specified.
    pdfjsLib.GlobalWorkerOptions.workerSrc = '//cdnjs.cloudflare.com/ajax/libs/pdf.js/2.6.347/pdf.worker.min.js';

    var loadingTask = pdfjsLib.getDocument(url);
    return loadingTask.promise.then(function(pdf) {
        Page.Widgets.spinner1.show = true

        // Fetch the first page
        var pageNumber = 1;
        return Page.renderPDFPage(pdf, pageNumber)
    }).then(function() {
        Page.Widgets.spinner1.show = false
    });
};

Page.dbTblTAdditionalAttachmentonSuccess = function(variable, data) {
    Page.Variables.varListFile.clearData()
    if (Page.pageParams.baId) {
        if (data.length > 0) {
            data.forEach((dt) => {
                Page.Variables.varListFile.addItem({
                    "aaId": dt.aaId,
                    "aaAttachment": dt.aaAttachment,
                    "aaPathFile": dt.aaPathFile,
                    "status": "old"
                })
            })
        }
    }
};
Page.label26_2Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.FileServiceDownloadFile.invoke({
        "inputFields": {
            "file": Page.Widgets.list2.selecteditem.aaAttachment,
            "returnName": Page.Widgets.list2.selecteditem.aaAttachment
        }
    })
};
Page.buttonBodClick = function($event, widget) {
    var bod = Page.Variables.varBod.dataSet
    var baAmount = parseInt(Page.Variables.vmAdditionalModel.dataSet.baAmount.toString().replace(/\./g, ''))

    Promise.resolve().then(() => {
        Page.Variables.varShowPdf.dataSet.dataValue = false
        return Page.Variables.qChangeTaskListUserId.invoke({
            "inputFields": {
                "tlParamId": Page.pageParams.baId,
                "userId": baAmount <= 300000000 ? bod.divBod1 : bod.divBod2,
                "tlType": "Additional",
                "isBod": 1
            }
        })
    }).then(() => {
        var loggedInUser = App.Variables.loggedInUserData.dataSet
        return Page.Variables.dbTblTAdditionalHistory.createRecord({
            row: {
                "baId": Page.pageParams.baId,
                "adEmployeeName": loggedInUser.user_full_name,
                "adAction": "Submitted",
                "adTimestamp": new Date().toISOString(),
                "adComment": Page.Widgets.textarea2.datavalue
            }
        })
    }).then(() => {
        return Page.Variables.dbTblTInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "Additional",
                "inbSubject": "<b>Additional</b> - Segera melakukan approval pada halaman task list",
                "userId": baAmount <= 300000000 ? bod.divBod1 : bod.divBod2,
                "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
            }
        })
    }).then(() => {
        App.Actions.goToPage_TaskListPage.invoke()
    }).catch((err) => {
        console.log("err ", err)
    })
};

Page.dbTblMEmpPriceLimitonSuccess = function(variable, data) {
    if (Page.Variables.varTaskList.dataSet.isApprovalBod === true) {
        Page.Variables.varFirstApprove.dataSet.dataValue = "bodaccepted"
        console.log(Page.Variables.varFirstApprove.dataSet.dataValue)
    } else {
        // check atasan 2439 BOD
        if (Page.Variables.dbEmployee.dataSet[0].supervisorNik === "2439") {
            Page.Variables.qGetBODbyDepartementId.invoke({
                "inputFields": {
                    "departementId": Page.Variables.varDepUser.dataSet
                }
            }).then((res) => {
                res = JSON.parse(res.body).content
                if (res.length > 0) {
                    if (typeof Page.Variables.varDepUser.dataSet[0] === 'number') {
                        if (data.length > 0) {
                            if (parseInt(Page.Variables.vmAdditionalModel.dataSet.baAmount.toString().replace(/\./g, '')) <= data[0].eplApproxMax) {
                                Page.Variables.varFirstApprove.dataSet.dataValue = "accept"
                            } else {
                                Page.Variables.varFirstApprove.dataSet.dataValue = "bod"
                                Page.Variables.varBod.dataSet = res[0]
                            }

                            console.log(Page.Variables.varFirstApprove.dataSet.dataValue)
                            //  Page.Variables.varFirstApprove.dataSet.dataValue = "bod"
                            //         Page.Variables.varBod.dataSet = res[0]
                        } else {
                            App.Actions.appNotification.setMessage("not found price limit")
                            App.Actions.appNotification.setToasterClass("error")
                            App.Actions.appNotification.getToasterDuration(5000)
                            App.Actions.appNotification.invoke()
                            App.Actions.goToPage_TaskListPage.invoke()
                        }
                    } else {
                        // not found departement
                        App.Actions.appNotification.setMessage('not found departement')
                        App.Actions.appNotification.setToasterClass("error")
                        App.Actions.appNotification.getToasterDuration(5000)
                        App.Actions.appNotification.invoke()
                        App.Actions.goToPage_TaskListPage.invoke()
                    }
                } else {
                    // not found BOD
                    App.Actions.appNotification.setMessage('not found BOD')
                    App.Actions.appNotification.setToasterClass("error")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                    App.Actions.goToPage_TaskListPage.invoke()
                }
            })
        } else {
            // tanpa BOD
            if (typeof Page.Variables.varDepUser.dataSet[0] === 'number') {
                if (data.length > 0) {
                    if (parseInt(Page.Variables.vmAdditionalModel.dataSet.baAmount.toString().replace(/\./g, '')) <= data[0].eplApproxMax) {
                        Page.Variables.varFirstApprove.dataSet.dataValue = "accept"
                    } else {
                        Page.Variables.varFirstApprove.dataSet.dataValue = "approve"
                    }
                } else {
                    App.Actions.appNotification.setMessage("not found price limit")
                    App.Actions.appNotification.setToasterClass("error")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                    App.Actions.goToPage_TaskListPage.invoke()
                }
            } else {
                // not found departement
                App.Actions.appNotification.setMessage('not found departement')
                App.Actions.appNotification.setToasterClass("error")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
                App.Actions.goToPage_TaskListPage.invoke()
            }
        }
    }
};