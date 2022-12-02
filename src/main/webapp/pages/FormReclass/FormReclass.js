/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

var onLoad = false;

/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    /*
     * variables can be accessed through 'Page.Variables' property here
     * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
     * Page.Variables.loggedInUser.getData()
     *
     * widgets can be accessed through 'Page.Widgets' prowperty here
     * e.g. to get value of text widget named 'username' use following script
     * 'Page.Widgets.username.datavalue'
     */

    // Yearpicker format

    // authorization handle
    if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('BUD-003') == -1) {
            App.Actions.goToPage_Main.invoke()
        }
    }

    // department handle
    var userDepartment = App.Variables.loggedInUserData.dataSet.user_department
    if (userDepartment.length > 0) {
        var departments = []

        userDepartment.map(dep => {
            departments.push(dep.departmentId)
        })

        Page.Variables.vmUserDepartment.setData(departments)
    }

    if (Page.pageParams.bdRcId) {
        Page.Variables.vmLoading.dataSet.dataValue = true

        return Promise.all([
            Page.Variables.vdbBudgetReclass.invoke(),
            Page.Variables.vdbReclassAttachment.invoke(),
            Page.Variables.vdbReclassHistory.invoke()
        ]).then(res => {
            Page.Variables.vmReclassModel.setData(res[0].data[0])
            Page.Variables.varListFile.setData(res[1].data)
            /*
                CAPEX: ASSET(3), SEWA LEASING(4)
                OPEX: ACTIVITY(1), NON ACTIVITY(2)
            */
            var capex = "3,4"
            var opex = "1,2"

            switch (res[0].data[0].rcatId) {
                case 1:
                    Page.Variables.vmReclassModel.setValue("accIdOrigin", capex)
                    Page.Variables.vmReclassModel.setValue("accIdDest", capex)
                    break;
                case 2:
                    Page.Variables.vmReclassModel.setValue("accIdOrigin", opex)
                    Page.Variables.vmReclassModel.setValue("accIdDest", opex)
                    break;
                case 3:
                    Page.Variables.vmReclassModel.setValue("accIdOrigin", capex)
                    Page.Variables.vmReclassModel.setValue("accIdDest", opex)
                    break;
                default:
                    Page.Variables.vmReclassModel.setValue("accIdOrigin", opex)
                    Page.Variables.vmReclassModel.setValue("accIdDest", capex)
            }

            Page.Variables.vmLoading.dataSet.dataValue = false

            var a = new Yearpicker(Page.Widgets.selectYear.inputEl.nativeElement, {
                year: parseInt(res[0].data[0].budgetYear),
                onChange: function(year) {
                    Page.Widgets.selectYear.datavalue = year

                    if (!onLoad) {
                        onLoad = true
                        return
                    }
                    // set io number to empty
                    Page.Variables.vmReclassModel.dataSet.ioOriginId = ""
                    Page.Variables.vmReclassModel.dataSet.ioOriginNumber = ""
                    Page.Variables.vmReclassModel.dataSet.ioOriginName = ""
                    Page.Variables.vmReclassModel.dataSet.ioOriginDepartment = ""
                    Page.Variables.vmReclassModel.dataSet.ioDestId = ""
                    Page.Variables.vmReclassModel.dataSet.ioDestNumber = ""
                    Page.Variables.vmReclassModel.dataSet.ioDestName = ""
                    Page.Variables.vmReclassModel.dataSet.ioDestDepartment = ""
                }
            })

            return Promise.resolve()
        })
    } else {
        var a = new Yearpicker(Page.Widgets.selectYear.inputEl.nativeElement, {
            onChange: function(year) {
                Page.Widgets.selectYear.datavalue = year

                // set io number to empty
                Page.Variables.vmReclassModel.dataSet.ioOriginId = ""
                Page.Variables.vmReclassModel.dataSet.ioOriginNumber = ""
                Page.Variables.vmReclassModel.dataSet.ioOriginName = ""
                Page.Variables.vmReclassModel.dataSet.ioOriginDepartment = ""
                Page.Variables.vmReclassModel.dataSet.ioDestId = ""
                Page.Variables.vmReclassModel.dataSet.ioDestNumber = ""
                Page.Variables.vmReclassModel.dataSet.ioDestName = ""
                Page.Variables.vmReclassModel.dataSet.ioDestDepartment = ""
            }
        })
    }
};

Page.textAmountChange = function($event, widget, newVal, oldVal) {
    // Page.Variables.vmReclassModel.dataSet.reclassAmount = newVal.replace(/\D/g, '');
    Page.Variables.vmReclassModel.dataSet.reclassAmount = App.formatCurrency(newVal.replace(/\D/g, ''))
};

// select category
Page.selectCategorySelect = function($event, widget, selectedValue) {
    Page.Variables.vmReclassModel.setValue("rcatId", selectedValue.rcatId)
    Page.Variables.vmReclassModel.setValue("rcatNote", selectedValue.rcatNote)
    Page.Variables.vmReclassModel.setValue("tblMreclassCategory", selectedValue)

    /*
        CAPEX: ASSET(3), SEWA LEASING(4)
        OPEX: ACTIVITY(1), NON ACTIVITY(2)
    */
    var capex = "3,4"
    var opex = "1,2"

    switch (selectedValue.rcatId) {
        case 1:
            Page.Variables.vmReclassModel.setValue("accIdOrigin", capex)
            Page.Variables.vmReclassModel.setValue("accIdDest", capex)
            break;
        case 2:
            Page.Variables.vmReclassModel.setValue("accIdOrigin", opex)
            Page.Variables.vmReclassModel.setValue("accIdDest", opex)
            break;
        case 3:
            Page.Variables.vmReclassModel.setValue("accIdOrigin", capex)
            Page.Variables.vmReclassModel.setValue("accIdDest", opex)
            break;
        default:
            Page.Variables.vmReclassModel.setValue("accIdOrigin", opex)
            Page.Variables.vmReclassModel.setValue("accIdDest", capex)
    }

    if (!onLoad) {
        onLoad = true
        return
    }
    // set io number to empty
    Page.Variables.vmReclassModel.dataSet.ioOriginId = ""
    Page.Variables.vmReclassModel.dataSet.ioOriginNumber = ""
    Page.Variables.vmReclassModel.dataSet.ioOriginName = ""
    Page.Variables.vmReclassModel.dataSet.ioOriginDepartment = ""
    Page.Variables.vmReclassModel.dataSet.ioDestId = ""
    Page.Variables.vmReclassModel.dataSet.ioDestNumber = ""
    Page.Variables.vmReclassModel.dataSet.ioDestName = ""
    Page.Variables.vmReclassModel.dataSet.ioDestDepartment = ""
};

// select IO Origin
Page.searchIOSelect = function($event, widget, selectedValue) {

    Page.Variables.vmErrorMessage.setValue("ioOrigin", "")
    if (Page.Variables.vmReclassModel.dataSet.ioDestId == selectedValue.bhId) {
        Page.Variables.vmErrorMessage.setValue("ioOrigin", "cannot have same value with io destination")
        return
    }

    Page.Variables.vmReclassModel.setValue("ioOriginId", selectedValue.bhId)
    Page.Variables.vmReclassModel.setValue("ioOriginNumber", selectedValue.ioNumber)
    Page.Variables.vmReclassModel.setValue("ioOriginName", selectedValue.ioName)
    Page.Variables.vmReclassModel.setValue("ioOriginDepartment", selectedValue.department)

    // get budget detail
    Page.Variables.vdbBudgetAvailable.listRecords({
        "filterFields": {
            "bhId": {
                "value": selectedValue.bhId
            }
        }
    })
};

// select IO destination
Page.searchDesIOSelect = function($event, widget, selectedValue) {
    Page.Variables.vmErrorMessage.setValue("ioDest", "")
    if (Page.Variables.vmReclassModel.dataSet.ioOriginId == selectedValue.bhId) {
        Page.Variables.vmErrorMessage.setValue("ioDest", "cannot have same value with io origin")
        return
    }

    Page.Variables.vmReclassModel.setValue("ioDestId", selectedValue.bhId)
    Page.Variables.vmReclassModel.setValue("ioDestNumber", selectedValue.ioNumber)
    Page.Variables.vmReclassModel.setValue("ioDestName", selectedValue.ioName)
    Page.Variables.vmReclassModel.setValue("ioDestDepartment", selectedValue.department)
};

// button cancel redirect to previous page
Page.buttonCancelClick = function($event, widget) {
    App.Actions.goToPage_ReclassPage.invoke()
};

// button save change status into Draft
Page.buttonSaveClick = function($event, widget) {
    var data = Page.Variables.vmReclassModel.dataSet
    data.bdRcStatus = 'Draft'

    Page.submitData(data)
};

// button submit status into Submitted & send notification
Page.buttonSubmitClick = function($event, widget) {
    var data = Page.Variables.vmReclassModel.dataSet
    data.bdRcStatus = 'Submit';

    Page.submitData(data)
};

// insert or update function
Page.submitData = function(data) {
    Page.Variables.vmErrorMessage.clearData()
    var amountLimit = Page.Variables.vdbPriceLimit.dataSet
    if (data.reclassAmount) {
        data.reclassAmount = parseInt(data.reclassAmount.replace(/\./g, ''))
    }

    // validation
    var isValid = true
    if (!data.rcatId) {
        Page.Variables.vmErrorMessage.setValue("reclassCat", "this field is required")
        isValid = false
    }
    if (!data.reasonPurpose) {
        Page.Variables.vmErrorMessage.setValue("reasonPurpose", "this field is required")
        isValid = false
    }
    if (!data.reclassAmount) {
        Page.Variables.vmErrorMessage.setValue("reclassAmount", "this field is required")
        isValid = false
    } else if (amountLimit.length > 0) {
        if (data.reclassAmount > parseInt(amountLimit[0].eplApproxMax)) {
            Page.Variables.vmErrorMessage.setValue("reclassAmount", "Maximum budget amount " + App.formatCurrency(amountLimit[0].eplApproxMax))
            isValid = false
        }
    }
    if (!data.ioOriginNumber) {
        Page.Variables.vmErrorMessage.setValue("ioOrigin", "this field is required")
        isValid = false
    }
    if (data.ioOriginNumber && data.reclassAmount && Page.Variables.vdbBudgetAvailable.dataSet.length > 0) {
        if (data.reclassAmount > Page.Variables.vdbBudgetAvailable.dataSet[0].availableBudget) {
            Page.Variables.vmErrorMessage.setValue("reclassAmount", "Budget available " + App.formatCurrency(Page.Variables.vdbBudgetAvailable.dataSet[0].availableBudget))
            isValid = false
        }
    }
    if (!data.ioDestNumber) {
        Page.Variables.vmErrorMessage.setValue("ioDest", "this field is required")
        isValid = false
    }


    if (!isValid) {
        data.bdRcStatus = 'Draft';
        return
    }

    Page.Variables.vmLoading.dataSet.dataValue = true

    // update
    if (Page.pageParams.bdRcId) {
        delete data.tblMreclassCategory

        Page.Variables.vdbBudgetReclass.updateRecord({
            row: data
        }).then(res => {
            return Promise.resolve().then(() => {
                var newFile = Page.Variables.varListFile.dataSet.filter(x => !x.raId)
                return Page.mapCreateAttachment(newFile.reverse(), res.bdRcId)
            }).then(() => {
                return Page.mapDeleteAttachment(Page.Variables.varListFileDelete.dataSet.reverse())
            }).then(() => {
                if (res.bdRcStatus == "Submit") {
                    return Promise.all([
                        //Page.insertTaskList(res),
                        Page.recordHistory(res, 1)
                    ])
                } else {
                    return Promise.resolve()
                }
            })
        }).then(res => {
            Page.submitResponse()
        }).catch(error => {
            console.log(error)
        })
    }
    // insert
    else {
        data.bdRcCreatedAt = new Date().toISOString()
        data.bdRcCreatedBy = App.Variables.loggedInUserData.dataSet.user_full_name
        data.bdRcCreatedId = App.Variables.loggedInUserData.dataSet.username

        Page.Variables.vdbBudgetReclass.createRecord({
            row: data
        }).then(res => {
            return Promise.resolve().then(() => {
                var newFile = Page.Variables.varListFile.dataSet.filter(x => !x.raId)
                return Page.mapCreateAttachment(newFile.reverse(), res.bdRcId)
            }).then(() => {
                return Page.mapDeleteAttachment(Page.Variables.varListFileDelete.dataSet.reverse())
            }).then(() => {
                if (res.bdRcStatus == "Submit") {
                    return Promise.all([
                        //Page.insertTaskList(res),
                        //disabled after adding Checker roles
                        Page.recordHistory(res, 0)
                    ])
                } else {
                    return Promise.resolve()
                }
            })
        }).then(res => {
            Page.submitResponse()
        }).catch(error => {
            App.Actions.appNotification.setMessage(error)
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
            console.log(error)
        })
    }
}

Page.insertTaskList = function(data) {
    var loggedInUser = App.Variables.loggedInUserData.dataSet
    var currentDate = new Date()
    var subject = "2000-" + data.bdRcId

    var bod = Page.Variables.qGetBODbyDepartementId.dataSet
    var userId = "emp::" + loggedInUser.user_supervisor_nik
    var approvalBOD = 0

    var user_full_name = loggedInUser.user_full_name

    if (Page.Variables.modelRepresentative.dataSet.nikSupervisor) {
        userId = "emp::" + Page.Variables.modelRepresentative.dataSet.nikSupervisor
        user_full_name = Page.Variables.modelRepresentative.dataSet.name
    } else {
        if (bod.length > 0) {
            approvalBOD = 1
            if (data.reclassAmount <= 500000000) {
                userId = bod[0].divBod1
            } else {
                userId = bod[0].divBod2
            }
        }
    }

    if (data.bdRcId < 10) {
        subject = "2000-0" + data.bdRcId
    }

    var dataSet = {
        "tlTimestamp": currentDate.toISOString(),
        "tlType": "Reclass",
        "tlSubject": subject,
        "tlDueDate": currentDate.setDate(currentDate.getDate() + 3),
        "tlParamId": data.bdRcId,
        "tlSubmitterName": user_full_name,
        "userId": userId,
        "tlModule": "BUD",
        "isApprovalBod": approvalBOD
    }

    return Page.Variables.vdbTaskList.createRecord({
        row: dataSet
    }).then(res => {
        return App.sendNotification(res.tlType, res.userId)
    }).catch(error => {
        console.log(error)
    })
}

Page.recordHistory = async function(data, status) {
    var loggedInUser = App.Variables.loggedInUserData.dataSet
    var user_full_name = loggedInUser.user_full_name

    if (Page.Variables.modelRepresentative.dataSet.name) {
        user_full_name = Page.Variables.modelRepresentative.dataSet.name
    }

    var dataSet = {
        "bdRcId": data.bdRcId,
        "rhEmployeeName": user_full_name,
        "rhAction": (status == 1 ? "Updated" : "Submitted"),
        "rhTimestamp": new Date().toISOString(),
        "rhComment": '-'
    }

    //insert recordHistory by checker
    await Page.Variables.vdbReclassHistory.createRecord({
        row: dataSet
    }, async() => {
        dataSet = {
            "bdRcId": data.bdRcId,
            "rhEmployeeName": "Checker",
            "rhAction": "Checking",
            "rhTimestamp": new Date().toISOString(),
            "rhComment": '-'
        };
        await Page.Variables.vdbReclassHistory.createRecord({
            row: dataSet
        }).catch(error => {
            console.log("error insert by checker : ", error)
        })
    }).catch(error => {
        console.log("error insert by  user : ", error)
    })
}




Page.submitResponse = function() {
    // message
    App.Actions.appNotification.setMessage("Data Saved")
    // type: success, info, warning, danger
    App.Actions.appNotification.setToasterClass("success")
    // delayed
    App.Actions.appNotification.setToasterDuration(5000)
    // invoke
    App.Actions.appNotification.invoke()

    Page.Variables.vmLoading.dataSet.dataValue = false
    App.Actions.goToPage_ReclassPage.invoke()
}

// invoke btn upload
Page.button6Click = function($event, widget) {
    $(Page.Widgets.attachmentReclass.nativeElement).find("input").click();
};

Page.attachmentReclassSelect = function($event, widget, selectedFiles) {
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
Page.FileServiceUploadFileonSuccess = function(variable, data) {
    // Page.Variables.vmReclassModel.setValue("bdRcAttachment", data[0].fileName)
    // Page.Variables.vmReclassModel.setValue("pathFile", data[0].inlinePath)

    Page.Variables.varListFile.addItem({
        "raAttachment": data[0].fileName,
        "raPathFile": data[0].inlinePath
    })
};
// view files
Page.picture7Click = function($event, widget, item, currentItemWidgets) {
    // document.getElementById("pdf-canvas-container").innerHTML = ""
    Page.Variables.vmShowPdf.dataSet.dataValue = true
    var filePath = Page.Widgets.list2.selecteditem.raPathFile
    // // Loaded via <script> tag, create shortcut to access PDF.js exports.
    // var pdfjsLib = window['pdfjs-dist/build/pdf'];
    // // The workerSrc property shall be specified.
    // pdfjsLib.GlobalWorkerOptions.workerSrc = '//cdnjs.cloudflare.com/ajax/libs/pdf.js/2.6.347/pdf.worker.min.js';

    // var loadingTask = pdfjsLib.getDocument(filePath);
    // return loadingTask.promise.then(function(pdf) {
    //     Page.Variables.vmLoading.dataSet.dataValue = true

    //     var pageNumber = 1;
    //     return Page.renderPDFPage(pdf, pageNumber)
    // }).then(function() {
    //     Page.Variables.vmLoading.dataSet.dataValue = false
    // });

    PDFObject.embed(filePath, "#pdf-canvas-container")
};
// delete files
Page.picture5Click = function($event, widget, item, currentItemWidgets) {
    var data = Page.Widgets.list2.selecteditem

    // delete old file
    if (data.raId) {
        Promise.resolve().then(() => {
            return Page.Variables.varListFileDelete.addItem(data)
        }).then(() => {
            Page.Variables.varListFile.removeItem(data)
        })
    }
    // delete new file
    else {
        Page.Variables.FileServiceDeleteFile.invoke({
            "inputFields": {
                "file": data.raAttachment
            }
        }).then(() => {
            Page.Variables.varListFile.removeItem(data)
        })
    }
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

Page.mapDeleteAttachment = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    return Promise.resolve().then(() => {
        return Page.Variables.vdbReclassAttachment.deleteRecord({
            row: dt
        })
    }).then(() => {
        return Page.Variables.FileServiceDeleteFile.invoke({
            "inputFields": {
                "file": dt.raAttachment
            }
        })
    }).then(function(res) {
        return Page.mapDeleteAttachment(data)
    }).catch(function(err) {
        console.log("error : ", err)
        return Page.mapDeleteAttachment(data)
    })
}

Page.mapCreateAttachment = function(data, id) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    dt.bdRcId = id
    return Promise.resolve().then(() => {
        return Page.Variables.vdbReclassAttachment.createRecord({
            row: dt
        })
    }).then(function(res) {
        return Page.mapCreateAttachment(data, id)
    }).catch(function(err) {
        console.log("error : ", err)
        return Page.mapCreateAttachment(data, id)
    })
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
    document.getElementById("pdf-canvas-container").innerHTML = ""
};

Page.vdbPriceLimitonSuccess = function(variable, data) {

};

Page.qGetBODbyDepartementIdonSuccess = function(variable, data) {
    // console.log("bod", data)

    //     Page.Variables.vmBOD.setData(data[0])
    //     console.log(Page.Variables.vmBOD.dataSet)
    // } else {
    //     Page.Variables.vmBOD.clearData()
    // }
};

Page.queryGetIOListBHonSuccess = function(variable, data) {

};
Page.selectRepSelect = function($event, widget, selectedValue) {
    Page.Widgets.spinner1.show = true
    Page.Variables.getUserDep.listRecords({
        filterFields: {
            "userId": {
                "value": 'emp::' + selectedValue.nik
            }
        }
    }).then(function(data_dept) {
        Page.Widgets.spinner1.show = false
        if (data_dept.data.length > 0) {
            var departments = data_dept.data.map(function(item) {
                return item['departmentId'];
            });
            // departments.push(data_dept.data[0].departmentId)
            Page.Variables.vmUserDepartment.setData(departments)
            Page.Variables.vmReclassModel.setValue("repNik", 'emp::' + selectedValue.nik)
            Page.Variables.vmReclassModel.setValue("repName", selectedValue.employeeName)

            //Model Data Supervisor untuk tasklist
            Page.Variables.modelRepresentative.setValue("nik", selectedValue.nik)
            Page.Variables.modelRepresentative.setValue("name", selectedValue.employeeName)
            Page.Variables.modelRepresentative.setValue("nikSupervisor", selectedValue.supervisorNik)
            Page.Variables.modelRepresentative.setValue("nameSupervisor", selectedValue.supervisorName)
            Page.Variables.modelRepresentative.setValue("jobGradeCode", selectedValue.jobGradeCode)


            //invoke Variables
            Page.Variables.queryGetIOListBH.invoke()
        } else {
            App.Actions.appNotification.setMessage('Data Not Found')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
        }
    })
};
Page.selectRepClear = function($event, widget) {
    var userDepartment = App.Variables.loggedInUserData.dataSet.user_department
    if (userDepartment.length > 0) {
        var departments = []

        userDepartment.map(dep => {
            departments.push(dep.departmentId)
        })

        Page.Variables.vmUserDepartment.setData(departments)
        Page.Variables.vmReclassModel.setValue("repNik", null)
        Page.Variables.vmReclassModel.setValue("repName", null)
        Page.Variables.queryGetIOListBH.invoke()

        //Model Data Supervisor untuk tasklist
        Page.Variables.modelRepresentative.setValue("nik", null)
        Page.Variables.modelRepresentative.setValue("name", null)
        Page.Variables.modelRepresentative.setValue("nikSupervisor", null)
        Page.Variables.modelRepresentative.setValue("nameSupervisor", null)
        Page.Variables.modelRepresentative.setValue("jobGradeCode", null)
    }
};

Page.vEmployeeDefonSuccess = function(variable, data) {
    //Model Data Supervisor untuk tasklist
    if (data.length > 0) {
        Page.Variables.modelRepresentative.setValue("nik", data[0].nik)
        Page.Variables.modelRepresentative.setValue("name", data[0].employeeName)
        Page.Variables.modelRepresentative.setValue("nikSupervisor", data[0].supervisorNik)
        Page.Variables.modelRepresentative.setValue("nameSupervisor", data[0].supervisorName)
        Page.Variables.modelRepresentative.setValue("jobGradeCode", data[0].jobGradeCode)
    }

};

Page.vdbReclassHistoryonSuccess = function(variable, data) {
    Page.Variables.MODELHistory.dataSet = data
    Page.Variables.DBTaskList.invoke()
        .then(function(res) {
            if (res.data.length > 0 && res.data[-1].rhAction == 'Submitted') {
                Page.Variables.MODELHistory.dataSet.push({
                    rhEmployeeName: res.data[0].approvalName,
                    rhAction: 'Waiting Approval'
                })
            }
        })
};