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

    // Yearpicker format
    // var a = new Yearpicker(Page.Widgets.selectYear.inputEl.nativeElement, {
    //     onChange: function(year) {
    //         Page.Widgets.selectYear.datavalue = year
    //     }
    // })

    if (Page.pageParams.bdRcId) {
        Page.Variables.vmLoading.dataSet.spinner2 = true

        return Promise.all([
            Page.Variables.vdbBudgetReclass.invoke(),
            Page.Variables.vdbReclassAttachment.invoke(),
            Page.Variables.vdbReclassHistory.invoke()
        ]).then(res => {
            console.log("reclass", res[0].data[0])
            console.log("attachments", res[1].data)
            console.log("histories", res[2].data)

            Page.Variables.vmReclassModel.setData(res[0].data[0])
            Page.Variables.varListFile.setData(res[1].data)
            Page.Widgets.search4.query = res[0].data[0].repName;
            return Promise.resolve()
        }).then(() => {
            var arr = []
            App.Variables.loggedInUserData.dataSet.user_department.forEach((dep) => {
                arr.push(parseInt(dep.departmentId))
            })

            return Page.Variables.qGetBODbyDepartementId.invoke({
                "inputFields": {
                    "departementId": arr
                }
            })
        }).then(() => {
            Page.Variables.vmLoading.dataSet.spinner2 = false
        })
    }
};

// revise action
Page.buttonReviseClick = function($event, widget) {
    var data = Page.Variables.vmReclassModel.dataSet
    data.bdRcStatus = 'Revise'

    Page.submitData(data)
};
// reject action
Page.buttonRejectClick = function($event, widget) {
    var data = Page.Variables.vmReclassModel.dataSet
    data.bdRcStatus = 'Rejected'

    Page.submitData(data)
};
// approve action
Page.buttonApprovalClick = function($event, widget) {
    var data = Page.Variables.vmReclassModel.dataSet
    var dataLimit = Page.Variables.vdbPriceLimit.dataSet
    data.bdRcStatus = "Submitted";

    if (dataLimit.length > 0) {
        if (dataLimit[0].eplApproxMax > parseInt(data.reclassAmount.replace(/\./g, ''))) {
            data.bdRcStatus = 'Accepted'
        }
    }

    Page.submitData(data)
};

// insert or update function
Page.submitData = function(data) {
    console.log("form data", data)
    Page.Variables.vmLoading.dataSet.spinner1 = true

    data = JSON.parse(JSON.stringify(data))
    data.reclassAmount = parseInt(data.reclassAmount.replace(/\./g, ''))
    var loggedInUser = App.Variables.loggedInUserData.dataSet
    var bdRemarks = "RECLASS FROM " + data.ioOriginNumber + " TO " + data.ioDestNumber
    var documentId = "2000-" + data.bdRcId

    if (data.bdRcId <= 9) {
        documentId = "2000-0" + data.bdRcId
    }

    if (data.bdRcStatus == "Submitted" || data.bdRcStatus == "Accepted") {
        // update
        Page.Variables.vdbBudgetReclass.updateRecord({
            row: data
        }).then((res) => {
            // update tasklist sequence
            console.log("task 1")
            var userId;
            if (res.repNik) {
                userId = res.repNik;
                Page.Variables.mCreatorName.dataSet = res.repName;
            } else {
                userId = res.bdRcCreatedId;
                Page.Variables.mCreatorName.dataSet = res.bdRcCreatedBy;
            }
            return Page.Variables.vdbEmployee.invoke({
                "filterFields": {
                    "nik": {
                        "value": userId.split("::")[1]
                    }
                }
            });
        }).then(function(dt) {
            console.log("task 2")
            var supervisorNik = "emp::" + dt.data[0].supervisorNik;
            Page.Variables.mSupervisorNik.dataSet = supervisorNik;
            var loggedInUser = App.Variables.loggedInUserData.dataSet;
            var currentDate = new Date();
            var subject = "2000-" + data.bdRcId;

            var dataSet = {
                "tlTimestamp": currentDate.toISOString(),
                "tlType": "Reclass",
                "tlSubject": subject,
                "tlDueDate": currentDate.setDate(currentDate.getDate() + 3),
                "tlParamId": data.bdRcId,
                "tlSubmitterName": dt.data[0].employeeName,
                "userId": supervisorNik,
                "tlModule": "BUD",
                "isApprovalBod": 0
            }

            return Page.Variables.dbTblTTasklist.createRecord({
                row: dataSet
            })
        }).then(function(res) {
            console.log("task 3", res)
            // return Page.Variables.svSendCheckerNotif.invoke()
            return Page.Variables.dbTblTInbox.createRecord({
                row: {
                    "inbTimestamp": new Date().toISOString(),
                    "inbTaskType": "Reclass",
                    "inbSubject": "<b>Reclass</b> - Segera melakukan approval pada halaman task list",
                    // "userId": Page.Variables.vmPRModel.dataSet.prCreatedBy,
                    "userId": res.userId,
                    "inbCreatedBy": Page.Variables.mCreatorName.dataSet
                }
            })
        }).then(() => {

            console.log("task 3.4.5")
            // record history
            var dataSet = {
                "bdRcId": data.bdRcId,
                "rhEmployeeName": loggedInUser.user_full_name,
                "rhAction": "Checked",
                "rhTimestamp": new Date().toISOString(),
                "rhComment": Page.Widgets.textComment.datavalue
            }

            return Page.Variables.vdbReclassHistory.createRecord({
                row: dataSet
            })
        }).then(async() => {
            console.log("task 6")
            if (data.bdRcStatus == "Accepted") {
                return App.sendNotification(
                    'Reclass',
                    data.bdRcCreatedId,
                    '<b>Reclass</b> - Budget from ' + data.ioOriginNumber + ' to ' + data.ioDestNumber + ' has Accepted'
                )
            } else if (data.bdRcStatus == "Submitted") {
                try {
                    await Page.Variables.api_ApprovalEmail.invoke({
                        inputFields: {
                            RequestBody: {
                                type: "Reclass",
                                id_budget: Page.pageParams.bdRcId,
                                action_type: "approval"
                            }
                        }
                    });
                } catch (err) {
                    console.log('ERROR API')
                }
                return App.sendNotification(
                    'Reclass',
                    "emp::" + Page.Variables.mSupervisorNik.dataSet.split("::")[1]
                )
            }
        }).then(() => {
            Page.submitResponse()
        }).catch(error => {
            console.log(error)
            Page.Variables.vmLoading.dataSet.spinner1 = false
        })
    } else {

        //jika revise atau reject
        Page.Variables.vdbBudgetReclass.updateRecord({
            row: data
        }).then(() => {

            console.log("task 3.4.5")
            // record history
            var dataSet = {
                "bdRcId": data.bdRcId,
                "rhEmployeeName": loggedInUser.user_full_name,
                "rhAction": "Checked",
                "rhTimestamp": new Date().toISOString(),
                "rhComment": Page.Widgets.textComment.datavalue
            }

            return Page.Variables.vdbReclassHistory.createRecord({
                row: dataSet
            })
        }).then(async() => {
            console.log("task 6")
            if (data.bdRcStatus == "Rejected") {
                try {
                    await Page.Variables.api_ApprovalEmail.invoke({
                        inputFields: {
                            RequestBody: {
                                type: "Reclass",
                                id_budget: Page.pageParams.bdRcId,
                                action_type: "reject"
                            }
                        }
                    });
                } catch (err) {
                    console.log('ERROR API')
                }
                return App.sendNotification(
                    'Reclass',
                    data.bdRcCreatedId,
                    '<b>Reclass</b> - Budget from ' + data.ioOriginNumber + ' to ' + data.ioDestNumber + ' has Rejected'
                )
            } else if (data.bdRcStatus == "Revise") {
                try {
                    await Page.Variables.api_ApprovalEmail.invoke({
                        inputFields: {
                            RequestBody: {
                                type: "Reclass",
                                id_budget: Page.pageParams.bdRcId,
                                action_type: "revise"
                            }
                        }
                    });
                } catch (err) {
                    console.log('ERROR API')
                }
                return App.sendNotification(
                    'Reclass',
                    data.bdRcCreatedId,
                    '<b>Reclass</b> - Budget from ' + data.ioOriginNumber + ' to ' + data.ioDestNumber + ' need Revise'
                )
            }
        }).then(() => {
            Page.submitResponse()
        }).catch(error => {
            console.log(error)
            Page.Variables.vmLoading.dataSet.spinner1 = false
        })
    }
}

Page.submitResponse = function() {
    // message
    App.Actions.appNotification.setMessage("Data Saved")
    // type: success, info, warning, danger
    App.Actions.appNotification.setToasterClass("success")
    // delayed
    App.Actions.appNotification.getToasterDuration(5000)
    // invoke
    App.Actions.appNotification.invoke()

    Page.Variables.vmLoading.dataSet.spinner1 = false
    App.Actions.goToPage_TaskListPage.invoke()
}

// // invoke btn upload
// Page.button6Click = function($event, widget) {
//     $(Page.Widgets.attachmentReclass.nativeElement).find("input").click();
// };
// // select file upload
// Page.attachmentReclassSelect = function($event, widget, selectedFiles) {
//     Page.Variables.vmReclassModel.setValue("bdRcAttachment", selectedFiles[0].name)
// };

// view files
Page.picture7Click = function($event, widget, item, currentItemWidgets) {
    document.getElementById("pdf-canvas-container").innerHTML = ""
    Page.Variables.vmShowPdf.dataSet.dataValue = true
    var filePath = Page.Widgets.list2.selecteditem.raPathFile
    // Loaded via <script> tag, create shortcut to access PDF.js exports.
    var pdfjsLib = window['pdfjs-dist/build/pdf'];
    // The workerSrc property shall be specified.
    pdfjsLib.GlobalWorkerOptions.workerSrc = '//cdnjs.cloudflare.com/ajax/libs/pdf.js/2.6.347/pdf.worker.min.js';

    var loadingTask = pdfjsLib.getDocument(filePath);
    return loadingTask.promise.then(function(pdf) {
        Page.Variables.vmLoading.dataSet.dataValue = true

        var pageNumber = 1;
        return Page.renderPDFPage(pdf, pageNumber)
    }).then(function() {
        Page.Variables.vmLoading.dataSet.dataValue = false
    });
};
// download file
Page.label26_2Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.FileServiceDownloadFile.invoke({
        "inputFields": {
            "file": Page.Widgets.list2.selecteditem.raAttachment,
            "returnName": Page.Widgets.list2.selecteditem.raAttachment
        }
    })
};
// open PDF or download file
Page.button8Click = function($event, widget) {
    var fileName = Page.Variables.vmReclassModel.dataSet.bdRcAttachment.toLowerCase()
    var filePath = Page.Variables.vmReclassModel.dataSet.pathFile
    Page.Variables.vmShowPdf.dataSet.dataValue = !Page.Variables.vmShowPdf.dataSet.dataValue

    if (fileName && filePath && Page.Variables.vmShowPdf.dataSet.dataValue) {
        // pdf
        if (fileName.indexOf('.pdf') >= 0) {
            // Loaded via <script> tag, create shortcut to access PDF.js exports.
            var pdfjsLib = window['pdfjs-dist/build/pdf'];
            // The workerSrc property shall be specified.
            pdfjsLib.GlobalWorkerOptions.workerSrc = '//cdnjs.cloudflare.com/ajax/libs/pdf.js/2.6.347/pdf.worker.min.js';

            var loadingTask = pdfjsLib.getDocument(filePath);
            loadingTask.promise.then(function(pdf) {
                Page.Variables.vmLoading.dataSet.dataValue = true

                var pageNumber = 1;
                return Page.renderPDFPage(pdf, pageNumber)
            }).then(function() {
                Page.Variables.vmLoading.dataSet.dataValue = false
            });

        } else {
            // download action
            window.open(filePath)
        }
    }
}

Page.renderPDFPage = function(pdf, pageNumber) {
    return pdf.getPage(pageNumber).then(function(page) {
        var scale = 1;
        var viewport = page.getViewport({
            scale: scale
        });

        // Prepare canvas using PDF page dimensions
        var canvas = document.createElement("canvas")
        var context = canvas.getContext('2d');
        canvas.height = viewport.height;
        canvas.width = viewport.width;
        canvas.style.marginBottom = "24px"

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
    Page.Variables.vmShowPdf.dataSet.dataValue = false
};


Page.vdbPriceLimitonSuccess = function(variable, data) {
    console.log("price limit", data)
};

Page.vdbEmployeeonSuccess = function(variable, data) {
    console.log("employee success", data)
};

Page.qGetBODbyDepartementIdonSuccess = function(variable, data) {
    console.log("bod", data)
};

Page.vdbBudgetReclassonSuccess = function(variable, data) {};