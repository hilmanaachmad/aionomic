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
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('BUD-004') !== -1) {
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
                    // check atasan 2439 BOD
                    if (App.Variables.loggedInUserData.dataSet.user_supervisor_nik === "2439") {
                        Page.Variables.qGetBODbyDepartementId.invoke({
                            "inputFields": {
                                "departementId": dt
                            }
                        }).then((res) => {
                            res = JSON.parse(res.body).content
                            if (res.length > 0) {
                                Page.Variables.varBod.dataSet = res[0]
                            } else {
                                // not found BOD
                                App.Actions.appNotification.setMessage('not found BOD')
                                App.Actions.appNotification.setToasterClass("error")
                                App.Actions.appNotification.setToasterDuration(5000)
                                App.Actions.appNotification.invoke()
                                App.Actions.goToPage_TaskListPage.invoke()
                            }
                        })
                    } else {
                        // without bod
                        Page.Variables.varBod.dataSet = null
                    }

                    // Yearpicker format
                    var a = new Yearpicker(Page.Widgets.selectYear.inputEl.nativeElement, {
                        onChange: function(year) {
                            if (Page.Widgets.selectYear.datavalue && year != Page.Widgets.selectYear.datavalue) {
                                Page.Widgets.searchIO.datavalue = null
                                Page.Variables.vmAdditionalModel.dataSet.ioNumber = null
                                Page.Variables.vmAdditionalModel.dataSet.ioDesc = null
                            }
                            Page.Widgets.selectYear.datavalue = year
                        }
                    })

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

// select category
Page.selectCategorySelect = function($event, widget, selectedValue) {
    Page.Variables.vmAdditionalModel.setValue("ubCatId", selectedValue)

    if (selectedValue === 1) {
        Page.Variables.modelFilterIO.setValue("accActivity", " ")
        Page.Variables.modelFilterIO.setValue("accNonActivity", " ")
        Page.Variables.modelFilterIO.setValue("accAsset", 3)
        Page.Variables.modelFilterIO.setValue("accSewaLeasing", 4)
    } else if (selectedValue === 2) {
        Page.Variables.modelFilterIO.setValue("accActivity", 1)
        Page.Variables.modelFilterIO.setValue("accNonActivity", 2)
        Page.Variables.modelFilterIO.setValue("accAsset", " ")
        Page.Variables.modelFilterIO.setValue("accSewaLeasing", " ")
    } else {
        Page.Variables.modelFilterIO.setValue("accActivity", null)
        Page.Variables.modelFilterIO.setValue("accNonActivity", null)
        Page.Variables.modelFilterIO.setValue("accAsset", null)
        Page.Variables.modelFilterIO.setValue("accSewaLeasing", null)
    }
};

// select IO
Page.searchIOSelect = function($event, widget, selectedValue) {
    Page.Variables.vmAdditionalModel.setValue("departementId", selectedValue.departmentId)
    Page.Variables.vmAdditionalModel.setValue("ioDepartement", selectedValue.department)
    Page.Variables.vmAdditionalModel.setValue("bhId", selectedValue.bhId)
    Page.Variables.vmAdditionalModel.setValue("ioNumber", selectedValue.ioNumber)
    Page.Variables.vmAdditionalModel.setValue("ioDesc", selectedValue.ioName)
    Page.Variables.vmAdditionalModel.setValue("brId", selectedValue.brId)
    Page.Variables.vmAdditionalModel.setValue("brTitle", selectedValue.brTitle)
};

// button save change status into Draft
Page.buttonSaveClick = function($event, widget) {
    var data = Page.Variables.vmAdditionalModel.dataSet
    data.baRemarks = 'Draft'

    Page.submitData(data)
};

// button submit status into Submitted & send notification
Page.buttonSubmitClick = function($event, widget) {
    var data = Page.Variables.vmAdditionalModel.dataSet
    data.baRemarks = 'Submit'

    Page.submitData(data)
};

// insert or update function
Page.submitData = function(data) {
    var creatorName;
    data.baUnbudget = data.baUnbudget.toString().replace(/\./g, '')
    data.baAmount = data.baAmount.toString().replace(/\./g, '')

    Page.Variables.varShowPdf.dataSet.dataValue = false
    Page.Variables.vmErrorMessage.clearData()
    // validation
    var isValid = true
    if (!data.ubCatId) {
        Page.Variables.vmErrorMessage.setValue("ubCatId", "this field is required")
        isValid = false
    }
    if (!data.baReasonPurpose) {
        Page.Variables.vmErrorMessage.setValue("baReasonPurpose", "this field is required")
        isValid = false
    }

    if (!data.baAmount || data.baAmount === '0') {
        Page.Variables.vmErrorMessage.setValue("baAmount", "this field is required")
        isValid = false
    } else if (parseInt(Page.Widgets.textAmount.datavalue.replace(/\./g, '')) > Page.Variables.varBudget.dataSet.dataValue.eplApproxMax) {
        Page.Variables.vmErrorMessage.setValue("baAmount", "Maximum budget amount " + App.formatCurrency(Page.Variables.varBudget.dataSet.dataValue.eplApproxMax))
        isValid = false
    } else if (parseInt(Page.Widgets.textAmount.datavalue.replace(/\./g, '')) < Page.Variables.varBudget.dataSet.dataValue.eplApproxMin) {
        Page.Variables.vmErrorMessage.setValue("baAmount", "Minimum budget amount " + App.formatCurrency(Page.Variables.varBudget.dataSet.dataValue.eplApproxMin))
        isValid = false
    }

    if (data.baYear === '') {
        Page.Variables.vmErrorMessage.setValue("baYear", "this field is required")
        isValid = false
    }



    // if (!data.baUnbudget || data.baUnbudget === '0') {
    //     Page.Variables.vmErrorMessage.setValue("baUnbudget", "this field is required")
    //     isValid = false
    // }
    if (!data.ioNumber) {
        Page.Variables.vmErrorMessage.setValue("ioNumber", "this field is required")
        isValid = false
    }
    if (!data.baYear) {
        Page.Variables.vmErrorMessage.setValue("baYear", "this field is required")
        isValid = false
    }

    if (!isValid) {
        data.baRemarks = ""
        return
    }



    if (Page.pageParams.baId) {
        // update
        Promise.resolve().then(() => {
            delete data.tblMunbudgetCat
            delete data.tblMio
        }).then(() => {
            Page.Variables.vdbBudgetAdditional.updateRecord({
                row: data
            }).then(res => {
                return Promise.resolve().then(() => {
                    var data = Page.Variables.varListFile.dataSet.filter(x => x.status === "new")
                    return Page.mapCreateAttachment(data, res.baId)
                }).then(() => {
                    return Page.mapDeleteAttachment(Page.Variables.varListFileDelete.dataSet)
                }).then(() => {
                    if (res.baRemarks == "Submit") {
                        return Page.updateTaskList(res)
                    } else {
                        return Promise.resolve()
                    }
                })
            }).then(res => {
                Page.submitResponse()
                App.Actions.goToPage_AdditionalBudgetPage.invoke()
            }).catch(error => {
                console.log(error)
            })
        })
    } else {
        // insert
        Promise.resolve().then(() => {
            data.baCreatedAt = new Date().toISOString()
            data.baCreatedBy = App.Variables.loggedInUserData.dataSet.user_full_name
            data.baCreatedId = App.Variables.loggedInUser.dataSet.name
        }).then(() => {
            delete data.tblMunbudgetCat
            return Page.Variables.vdbBudgetAdditional.createRecord({
                row: data
            }).then(res => {
                Page.Variables.mBaId.dataSet = res.baId;
                creatorName = (res.repNik ? res.repName : App.Variables.loggedInUserData.dataSet.user_full_name);
                return Promise.resolve().then(() => {
                    var data = Page.Variables.varListFile.dataSet.filter(x => x.status === "new")
                    return Page.mapCreateAttachment(data, res.baId)
                }).then(() => {
                    return Page.mapDeleteAttachment(Page.Variables.varListFileDelete.dataSet)
                }).then(async() => {
                    if (res.baRemarks == "Submit") {
                        //return Page.insertTaskList(res)
                        await Page.Variables.dbTblTAdditionalHistory.createRecord({
                            row: {
                                "baId": Page.Variables.mBaId.dataSet,
                                "adEmployeeName": creatorName,
                                "adAction": "Submitted",
                                "adTimestamp": new Date().toISOString(),
                                "adComment": null
                            }
                        }, async() => {
                            await Page.Variables.dbTblTAdditionalHistory.createRecord({
                                row: {
                                    "baId": Page.Variables.mBaId.dataSet,
                                    "adEmployeeName": "Checker",
                                    "adAction": "Checking",
                                    "adTimestamp": new Date().toISOString(),
                                    "adComment": null
                                }
                            }).catch(error => {
                                console.log("error insert by  checker : ", error)
                            });
                        }).catch(error => {
                            console.log("error insert by  user : ", error)
                        });
                        return Promise.resolve()
                    } else {
                        return Promise.resolve()
                    }
                })
            }).then(res => {
                Page.submitResponse()
                App.Actions.goToPage_AdditionalBudgetPage.invoke()
            }).catch(error => {
                console.log(error)
            })
        })
    }
}

Page.deleteFileUpload = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    return Promise.resolve().then(() => {
        return Page.Variables.FileServiceDeleteFile.invoke({
            "inputFields": {
                "file": dt.aaAttachment
            }
        })
    }).then(function(res) {
        return Page.deleteFileUpload(data)
    }).catch(function(err) {
        console.log(err)
        return Page.deleteFileUpload(data)
    })
}

Page.mapDeleteAttachment = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    return Promise.resolve().then(() => {
        return Page.Variables.dbTblTAdditionalAttachment.deleteRecord({
            row: {
                "aaId": dt.aaId
            }
        })
    }).then(() => {
        return Page.Variables.FileServiceDeleteFile.invoke({
            "inputFields": {
                "file": dt.aaAttachment
            }
        })
    }).then(function(res) {
        return Page.mapDeleteAttachment(data)
    }).catch(function(err) {
        console.log(err)
        return Page.mapDeleteAttachment(data)
    })
}

Page.mapCreateAttachment = function(data, id) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    return Promise.resolve().then(() => {
        return Page.Variables.dbTblTAdditionalAttachment.createRecord({
            row: {
                "baId": id,
                "aaAttachment": dt.aaAttachment,
                "aaPathFile": dt.aaPathFile
            }
        })
    }).then(function(res) {
        return Page.mapCreateAttachment(data, id)
    }).catch(function(err) {
        console.log(err)
        return Page.mapCreateAttachment(data, id)
    })
}

Page.updateTaskList = function(data) {
    return Promise.resolve().then(() => {
        return Page.Variables.vdbTaskList.invoke({
            "filterFields": {
                "tlParamId": {
                    "value": Page.pageParams.baId
                },
                "tlType": {
                    "value": "Additional"
                },
                "tlStatus": {
                    "value": "active"
                }
            }
        })
    }).then((list) => {
        if (list.data.length < 1) {
            // insert
            return Page.insertTaskList(data);
        } else {
            // update
            var lists = list.data[0]
            // var lists = Page.Variables.varBod.dataSet === null ? list.data[0] : baAmount <= 300000000 ? list.data[0].userId = Page.Variables.varBod.dataSet.divBod1 : Page.Variables.varBod.dataSet.divBod2

            return Page.Variables.vdbTaskList.updateRecord({
                row: lists
            }).then(() => {
                var user_full_name = App.Variables.loggedInUserData.dataSet.user_full_name
                if (Page.Variables.modelRepresentative.dataSet.name) {
                    user_full_name = Page.Variables.modelRepresentative.dataSet.name
                }

                return Page.Variables.dbTblTAdditionalHistory.createRecord({
                    row: {
                        "baId": Page.pageParams.baId,
                        "adEmployeeName": user_full_name,
                        "adAction": "Submitted",
                        "adTimestamp": new Date().toISOString(),
                        "adComment": null
                    }
                })
            })
        }
    }).catch(error => {
        console.log(error)
    })
}

Page.insertTaskList = function(data) {
    var loggedInUser = App.Variables.loggedInUserData.dataSet
    var user_supervisor_nik = App.Variables.loggedInUserData.dataSet.user_supervisor_nik
    if (Page.Variables.modelRepresentative.dataSet.nikSupervisor) {
        user_supervisor_nik = Page.Variables.modelRepresentative.dataSet.nikSupervisor
    }

    var baAmount = parseInt(Page.Variables.vmAdditionalModel.dataSet.baAmount.replace(/\./g, ''))

    var currentDate = new Date()
    var subject = "3000-" + data.baId

    if (data.baId < 10) {
        subject = "3000-0" + data.baId
    }

    var user_full_name = App.Variables.loggedInUserData.dataSet.user_full_name
    if (Page.Variables.modelRepresentative.dataSet.name) {
        user_full_name = Page.Variables.modelRepresentative.dataSet.name
    }

    var dataSet = {
        "tlTimestamp": currentDate.toISOString(),
        "tlType": "Additional",
        "tlSubject": subject,
        "tlDueDate": currentDate.setDate(currentDate.getDate() + 3),
        "tlParamId": data.baId,
        "tlSubmitterName": user_full_name,
        "userId": Page.Variables.varBod.dataSet === null ? "emp::" + user_supervisor_nik : baAmount <= 300000000 ? Page.Variables.varBod.dataSet.divBod1 : Page.Variables.varBod.dataSet.divBod2,
        "tlModule": "BUD",
        "isApprovalBod": Page.Variables.varBod.dataSet === null ? 0 : 1
    }

    return Page.Variables.vdbTaskList.createRecord({
        row: dataSet
    }).then((res) => {
        // return App.sendNotification(res.tlType, res.userId)
        return Page.Variables.dbTblTAdditionalHistory.createRecord({
            row: {
                "baId": data.baId,
                "adEmployeeName": user_full_name,
                "adAction": "Submitted",
                "adTimestamp": new Date().toISOString(),
                "adComment": null,
                "tlStatus": "active"
            }
        })
    }).then(() => {
        var user_supervisor_nik = App.Variables.loggedInUserData.dataSet.user_supervisor_nik
        if (Page.Variables.modelRepresentative.dataSet.nikSupervisor) {
            user_supervisor_nik = Page.Variables.modelRepresentative.dataSet.nikSupervisor
        }
        var baAmount = parseInt(Page.Variables.vmAdditionalModel.dataSet.baAmount.replace(/\./g, ''))
        return Page.Variables.dbTblTInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "Additional",
                "inbSubject": "<b>Additional</b> - Segera melakukan approval pada halaman task list",
                "userId": Page.Variables.varBod.dataSet === null ? "emp::" + user_supervisor_nik : baAmount <= 300000000 ? Page.Variables.varBod.dataSet.divBod1 : Page.Variables.varBod.dataSet.divBod2,
                "inbCreatedBy": loggedInUser.user_full_name
            }
        })
    }).catch(error => {
        console.log(error)
    })
}

Page.submitResponse = function() {
    // message
    App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
    // type: success, info, warning, danger
    App.Actions.appNotification.setToasterClass("success")
    // delayed
    App.Actions.appNotification.setToasterDuration(5000)
    // invoke
    App.Actions.appNotification.invoke()
    Page.Variables.vmLoading.dataSet.dataValue = false
}

// invoke btn upload
Page.button6Click = function($event, widget) {
    $(Page.Widgets.attachmentReclass.nativeElement).find("input").click();
};

Page.FileServiceUploadFileonSuccess = function(variable, data) {
    // Page.Variables.vmAdditionalModel.setValue("baAttachment", data[0].fileName)
    // Page.Variables.vmAdditionalModel.setValue("pathFile", data[0].inlinePath)

    Page.Variables.varListFile.addItem({
        "aaId": 0,
        "aaAttachment": data[0].fileName,
        "aaPathFile": data[0].inlinePath,
        "status": 'new'
    })
};

// set value when editted
Page.vdbBudgetAdditionalonSuccess = function(variable, data) {
    if (Page.pageParams.baId) {
        Page.Variables.vmAdditionalModel.setData(data[0])
        if (Page.Widgets.selectYear !== undefined) {
            if (data[0] !== undefined) {
                Page.Widgets.selectYear.datavalue = data[0].baYear
            }
        }
    }
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
Page.picture4Click = function($event, widget) {
    Page.Variables.varShowPdf.dataSet.dataValue = false
};

Page.dbTblMEmpPriceLimitonSuccess = function(variable, data) {
    if (data.length > 0) {
        Page.Variables.varBudget.dataSet.dataValue = data[0]
    } else {
        App.Actions.appNotification.setMessage("not found price limit")
        App.Actions.appNotification.setToasterClass("error")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        App.Actions.goToPage_AdditionalBudgetPage.invoke()
    }
};

Page.textUnbudgetChange = function($event, widget, newVal, oldVal) {
    // Page.Variables.vmAdditionalModel.dataSet.baUnbudget = newVal.replace(/\D/g, '');
    Page.Variables.vmAdditionalModel.setValue("baUnbudget", Page.App.formatCurrency(Page.Widgets.textUnbudget.datavalue.replace(/\D/g, '')))
};
Page.textAmountChange = function($event, widget, newVal, oldVal) {
    // Page.Variables.vmAdditionalModel.dataSet.baAmount = newVal.replace(/\D/g, '');
    Page.Variables.vmAdditionalModel.setValue("baAmount", Page.App.formatCurrency(Page.Widgets.textAmount.datavalue.replace(/\D/g, '')))
};
Page.label26_2Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.FileServiceDownloadFile.invoke({
        "inputFields": {
            "file": Page.Widgets.list2.selecteditem.aaAttachment,
            "returnName": Page.Widgets.list2.selecteditem.aaAttachment
        }
    })
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
Page.picture5Click = function($event, widget, item, currentItemWidgets) {
    var dt = Page.Widgets.list2.selecteditem
    var filters = Page.Variables.varListFile.dataSet.find(x => x.aaAttachment === dt.aaAttachment)

    if (filters.status === "old") {
        Promise.resolve().then(() => {
            return Page.Variables.varListFileDelete.addItem({
                aaId: filters.aaId,
                aaAttachment: filters.aaAttachment,
                aaPathFile: filters.aaPathFile,
                status: filters.status
            })
        }).then(() => {
            return Page.Variables.varListFile.dataSet = Page.Variables.varListFile.dataSet.filter(x => x !== filters)
        })
    } else if (filters.status === "new") {
        Promise.resolve().then(() => {
            return Page.Variables.FileServiceDeleteFile.invoke({
                "inputFields": {
                    "file": filters.aaAttachment
                }
            })
        }).then(() => {
            return Page.Variables.varListFile.dataSet = Page.Variables.varListFile.dataSet.filter(x => x !== filters)
        })
    }
};
Page.picture7Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.varShowPdf.dataSet.dataValue = true
    // document.getElementById("pdf-canvas-container").innerHTML = ""
    // // var url = Page.Variables.vmAdditionalModel.dataSet.pathFile;
    var url = Page.Widgets.list2.selecteditem.aaPathFile

    // // Loaded via <script> tag, create shortcut to access PDF.js exports.
    // var pdfjsLib = window['pdfjs-dist/build/pdf'];
    // // The workerSrc property shall be specified.
    // pdfjsLib.GlobalWorkerOptions.workerSrc = '//cdnjs.cloudflare.com/ajax/libs/pdf.js/2.6.347/pdf.worker.min.js';

    // var loadingTask = pdfjsLib.getDocument(url);
    // return loadingTask.promise.then(function(pdf) {
    //     Page.Widgets.spinner1.show = true

    //     // Fetch the first page
    //     var pageNumber = 1;
    //     return Page.renderPDFPage(pdf, pageNumber)
    // }).then(function() {
    //     Page.Widgets.spinner1.show = false
    // });

    PDFObject.embed(url, "#pdf-canvas-container")
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
            //Model Data Untuk Filter Representative
            Page.Variables.modelFilter.setValue("department", data_dept.data[0].departmentId)
            Page.Variables.queryGetIOListBH.invoke()
            console.log(selectedValue)
            //Model Data Supervisor untuk tasklist
            Page.Variables.modelRepresentative.setValue("nik", selectedValue.nik)
            Page.Variables.modelRepresentative.setValue("name", selectedValue.employeeName)
            Page.Variables.modelRepresentative.setValue("nikSupervisor", selectedValue.supervisorNik)
            Page.Variables.modelRepresentative.setValue("nameSupervisor", selectedValue.supervisorName)
            Page.Variables.modelRepresentative.setValue("level", selectedValue.jobGradeCode)

            // Update Model CRUD
            Page.Variables.vmAdditionalModel.setValue("repNik", 'emp::' + selectedValue.nik)
            Page.Variables.vmAdditionalModel.setValue("repName", selectedValue.employeeName)

            Page.Variables.dbTblMEmpPriceLimit.invoke()
        } else {
            App.Actions.appNotification.setMessage('Data Not Found')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
        }
    })
};

Page.selectRepClear = function($event, widget) {
    // Update Model CRUD
    Page.Variables.vmAdditionalModel.setValue("repNik", null)
    Page.Variables.vmAdditionalModel.setValue("repName", null)
    //Model Data Supervisor untuk tasklist
    Page.Variables.modelRepresentative.setValue("nik", null)
    Page.Variables.modelRepresentative.setValue("name", null)
    Page.Variables.modelRepresentative.setValue("nikSupervisor", null)
    Page.Variables.modelRepresentative.setValue("nameSupervisor", null)
    Page.Variables.modelRepresentative.setValue("level", null)
    Page.Variables.modelFilter.setValue("department", null)
    Page.Variables.queryGetIOListBH.invoke()
    Page.Variables.dbTblMEmpPriceLimit.invoke()
};

Page.dbTblTAdditionalHistoryonSuccess = function(variable, data) {
    Page.Variables.MODELHistory.dataSet = data
    Page.Variables.DBTaskList.invoke()
        .then(function(res) {
            if (res.data.length > 0) {
                Page.Variables.MODELHistory.dataSet.push({
                    adEmployeeName: res.data[0].approvalName,
                    adAction: 'Waiting Approval'
                })
            }
        })

};