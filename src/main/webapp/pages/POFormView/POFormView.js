/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */



/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    if (Page.Widgets.spinner) {
        // Page.Widgets.spinner.show = true
    }
    // $('#pdf-canvas-container').css('height', '700px')

    if (!Page.pageParams.id) {
        App.Actions.appNotification.setMessage('Data not found')
        App.Actions.appNotification.setToasterClass("warning")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.Widgets.spinner.show = false
        window.history.back();
    }
};


Page.calulcateTotalAmountLine = function(data) {
    var amount = data.poQty * data.unitPrice
    var discount = amount * ((data.discount) ? data.discount : 0) / 100
    data.afterDiscount = amount - discount
    return App.formatCurrency(amount - discount)
}
Page.backFormClick = function($event, widget) {
    window.history.back();
};


var statusSubmit = null
Page.btnActionClick = function($event, widget) {
    Page.submitForm()
};

Page.approveClick = async function($event, widget) {
    statusSubmit = 'APPROVE'
};

Page.rejectClick = function($event, widget) {
    statusSubmit = 'REJECT'
};


Page.submitForm = async function() {
    Page.Widgets.spinner.show = true

    var DBPurchaseOrder = Page.Variables.DBPurchaseOrder
    var departmentCode = DBPurchaseOrder.dataSet[0].departmentCode
    var paymentMethod = DBPurchaseOrder.dataSet[0].paymentMethod

    var notes = Page.Widgets.notesAction.datavalue ? Page.Widgets.notesAction.datavalue : null
    Page.Widgets.modalAction.close()
    if (statusSubmit == 'APPROVE') {
        if (Page.pageParams.action === 'approval_1') {
            if (Page.Variables.DBPurchaseOrder.firstRecord.purchaseBy === 'Purchasing') {
                var DBDepartmentApproval = Page.Variables.DBDepartmentApproval
                await DBDepartmentApproval.setFilter({
                    departmentId: 'Z028'
                })
                await DBDepartmentApproval.listRecords();
            } else {
                // var DBGetBODDept = Page.Variables.DBGetBODDept
                // await DBGetBODDept.setFilter({
                //     departmentCode: departmentCode
                // })
                // await DBGetBODDept.listRecords();

                var DBDepartmentApproval = Page.Variables.DBDepartmentApproval
                await DBDepartmentApproval.setFilter({
                    departmentId: departmentCode
                })
                await DBDepartmentApproval.listRecords();
            }

            //Define Purchase Order Type Approval
            if (Page.Variables.DBPurchaseOrder.firstRecord.purchaseBy === 'Purchasing') {
                var userIdApprovalPO = 'emp::' + DBDepartmentApproval.dataSet[0].deptHead
            } else {
                if (DBDepartmentApproval.dataSet.length === 0) {
                    App.Actions.appNotification.setMessage('Mapping Approval is not found')
                    App.Actions.appNotification.setToasterClass("warning")
                    App.Actions.appNotification.setToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                    Page.Widgets.spinner.show = false
                    return false
                }
                var userIdApprovalPO = 'emp::' + DBDepartmentApproval.dataSet[0].deptHead
            }

            // Create Tasklist
            Page.Variables.DBDeleteTaskList.invoke({
                inputFields: {
                    tlParamId: Page.pageParams.id,
                    tlType: 'PO'
                }
            }).then(async function(data) {
                await Page.Variables.DBUpdatePO.invoke({
                    inputFields: {
                        id: Page.pageParams.id,
                        status: 'In Approval Stage'
                    }
                })
                try {
                    console.log("start");
                    await Page.UpdatePOlineItem();
                } catch (e) {
                    console.log(e)
                    App.Actions.appNotification.setMessage('Error Updating ETA!')
                    App.Actions.appNotification.setToasterClass("error")
                    App.Actions.appNotification.setToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                }

                await Page.Variables.DBTaskList.createRecord({
                    row: {
                        tlTimestamp: App.HariIni(),
                        tlType: 'PO',
                        tlSubject: DBPurchaseOrder.dataSet[0].item,
                        tlDueDate: App.formatDateAndTime(App.addDays(App.HariIni(), 3)),
                        userId: userIdApprovalPO,
                        tlParamId: Page.pageParams.id,
                        tlSubmitterName: Page.Variables.DBEmployee.dataSet[0].employeeName,
                        tlModule: 'PO',
                        tlStatus: 'approval_2',
                        isApprovalBod: 0
                    }
                })

                try {
                    await Page.Variables.api_EmailApproval.invoke({
                        inputFields: {
                            RequestBody: {
                                id_po: Page.pageParams.id,
                                type: 'PO'
                            }
                        }
                    })
                } catch (e) {

                }

                var DBPOHistory = Page.Variables.DBPOHistory
                await DBPOHistory.createRecord({
                    row: {
                        poId: Page.pageParams.id,
                        action: 'Approved',
                        actionBy: App.Variables.loggedInUser.dataSet.name,
                        actionByName: Page.Variables.loggedInUserData.dataSet.user_full_name,
                        actionAt: App.HariIni(),
                        notes: notes
                    }
                })

                App.Actions.appNotification.setMessage('Update Data Successfully')
                App.Actions.appNotification.setToasterClass("success")
                App.Actions.appNotification.setToasterDuration(5000)
                App.Actions.appNotification.invoke()
                Page.Widgets.spinner.show = false
                App.Actions.goToPage_TaskListPage.invoke()
            })
        } else {

            try {
                await Page.Variables.DBDeleteTaskList.invoke({
                    inputFields: {
                        tlParamId: Page.pageParams.id,
                        tlType: 'PO'
                    }
                })
                await Page.Variables.DBUpdatePO.invoke({
                    inputFields: {
                        id: Page.pageParams.id,
                        status: 'Approved',

                    }
                })
                await Page.UpdatePOlineItem();
            } catch (e) {
                console.log(e)
                App.Actions.appNotification.setMessage('Error Updating ETA!')
                App.Actions.appNotification.setToasterClass("error")
                App.Actions.appNotification.setToasterDuration(5000)
                App.Actions.appNotification.invoke()
            }

            await Page.Variables.DBUpdatePO.invoke({
                inputFields: {
                    id: Page.pageParams.id,
                    status: 'Approved',

                }
            })

            await Page.Variables.DBPOHistory.createRecord({
                row: {
                    poId: Page.pageParams.id,
                    action: 'Approved',
                    actionBy: App.Variables.loggedInUser.dataSet.name,
                    actionByName: Page.Variables.loggedInUserData.dataSet.user_full_name,
                    actionAt: App.HariIni(),
                    notes: notes
                }
            })

            try {
                await Page.Variables.api_POPost.invoke({
                    inputFields: {
                        RequestBody: {
                            id_po: Page.pageParams.id,
                            user_created: App.Variables.loggedInUser.dataSet.name
                        }
                    }
                })

            } catch {
                console.log('Error API POST PO')
            }

            try {
                await Page.Variables.api_PONotifVendor.invoke({
                    inputFields: {
                        RequestBody: {
                            id_po: Page.pageParams.id
                        }
                    }
                });
            } catch {
                console.log('Notif email Failed');
            }

            App.Actions.appNotification.setMessage('Update Data Successfully')
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            Page.Widgets.spinner.show = false
            App.Actions.goToPage_TaskListPage.invoke()
        }
    }


    if (statusSubmit == 'REJECT') {
        Page.Variables.DBDeleteTaskList.invoke({
            inputFields: {
                tlParamId: Page.pageParams.id,
                tlType: 'PO'
            }
        })

        var DBPOHistory = Page.Variables.DBPOHistory
        await DBPOHistory.createRecord({
            row: {
                poId: Page.pageParams.id,
                action: 'Rejected',
                actionBy: App.Variables.loggedInUser.dataSet.name,
                actionByName: Page.Variables.loggedInUserData.dataSet.user_full_name,
                actionAt: App.HariIni(),
                notes: notes
            }
        })

        await Page.Variables.DBUpdatePO.invoke({
            inputFields: {
                id: Page.pageParams.id,
                status: 'Rejected',

            }
        })

        App.Actions.appNotification.setMessage('Update Data Successfully')
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.Widgets.spinner.show = false
        App.Actions.goToPage_TaskListPage.invoke()
    }


}

Page.UpdatePOlineItem = function() {
    let dataBodyItem = Page.Widgets.tableData.fieldDefs;
    console.log(dataBodyItem);
    return new Promise(async(resolve, reject) => {
        if (dataBodyItem.length > 0) {
            for (var i = 0; i < dataBodyItem.length; i++) {

                await Page.Variables.updatePoLineItem.invoke({
                    inputFields: {
                        eta: dataBodyItem[i].eta,
                        id: dataBodyItem[i].idPoLineItem
                    }
                })

                if (i === dataBodyItem.length - 1) {
                    console.log("done");
                    resolve(true)
                }
            }
        } else {
            console.log("done failed");
            reject("error")
        }
    })
}

Page.showItemFileClick = function($event, widget, item, currentItemWidgets) {
    Page.Widgets.spinner.show = true
    var isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);
    var filePath = Page.Widgets.list5.selecteditem.filePath

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

};


Page.closeItemFileClick = function($event, widget) {
    Page.Variables.statusShowPDF.dataSet.dataValue = false
    $("#pdf-canvas-container").html('')
    $("#pdf-canvas-container").removeClass('pdfobject-container')
};


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
        return b - a
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

Page.DBHistoryonSuccess = function(variable, data) {
    Page.Variables.VTaskList.invoke()
        .then(function(res) {
            if (res.data.length > 0) {
                Page.Variables.DBPOHistory.dataSet.push({
                    actionBy: res.data[0].userId,
                    actionByName: res.data[0].approvalName,
                    action: 'Waiting Approval'
                })
            }
        })
};

Page.DBGetPODoconResult = function(variable, data) {

};

Page.DBPurchaseOrderonSuccess = function(variable, data) {

};