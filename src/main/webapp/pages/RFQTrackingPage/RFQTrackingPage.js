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

    Page.$page[0].addEventListener("click", function() {
        $('.aio-ptp-dropdown:not(.clicked)').removeClass("active")
        $('.aio-ptp-dropdown').removeClass("clicked")
    })

    //authorization
    if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('RFQ-004') !== -1) {
            let condition = window.localStorage.getItem("budget-value-rfq-tracking")
            // get data localStorage
            if (condition != null) {
                Promise.resolve().then(function() {
                    Page.Variables.varFilterTmp.dataSet.status = JSON.parse(condition).status
                    Page.Variables.varFilterTmp.dataSet.inputNumber = JSON.parse(condition).inputNumber
                    return Promise.resolve()
                }).then(function() {
                    Page.Variables.qGetRFGVendorTracking.invoke()
                })
            } else {
                Page.Variables.varFilterTmp.dataSet.status = null
                Page.Variables.varFilterTmp.dataSet.inputNumber = null
            }

        } else {
            App.Actions.goToPage_Main.invoke()
        }
    }
};

Page.getDays = function(data) {
    var tmp = ""
    if (data.overTime === 0) {
        tmp = ""
    } else {
        tmp = " (" + data.overTime + " Days)"
    }

    return tmp
}

Page.qGetRFGVendorTrackingonBeforeDatasetReady = function(variable, data) {
    var arr = []
    data.forEach((dt) => {
        dt.listvendor = JSON.parse(dt.listvendor)
        // dt.listvendor.sort((a, b) => (a.rfqvStatus < b.rfqvStatus) ? 1 : ((b.rfqvStatus < a.rfqvStatus) ? -1 : 0))
        dt.listvendor.sort((a, b) => (a.rfqvStatus === "Over Time Limit") ? 1 : ((b.rfqvStatus === "Over Time Limit") ? -1 : 0))
        arr.push(dt)
    })
    return arr
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.qGetRFGVendorTracking.orderBy == field + " ASC") {
        Page.Variables.qGetRFGVendorTracking.orderBy = field + " DESC"
    } else if (Page.Variables.qGetRFGVendorTracking.orderBy == field + " DESC") {
        Page.Variables.qGetRFGVendorTracking.orderBy = ""
    } else {
        Page.Variables.qGetRFGVendorTracking.orderBy = field + " ASC"
    }
    Page.Variables.qGetRFGVendorTracking.invoke()
}
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('rfqRef')
};
Page.container42Click = function($event, widget) {
    Page.toggleTableSort('rfqStatus')
};

Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.qGetRFGVendorTracking.maxResults = newVal
    Page.Variables.qGetRFGVendorTracking.update()
};

Page.select4Change = function($event, widget, item, currentItemWidgets, newVal, oldVal) {
    Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvId === Page.Widgets.list6.selecteditem.rfqvId).rfqvStatus = newVal
    Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvId === Page.Widgets.list6.selecteditem.rfqvId).statusUpdate = "new"
};
Page.search1Select = function($event, widget, selectedValue, item, currentItemWidgets) {
    if (Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvVendorCode === selectedValue.vendorCode) === undefined) {
        Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvId === Page.Widgets.list6.selecteditem.rfqvId).rfqvVendorCode = selectedValue.vendorCode
        Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvId === Page.Widgets.list6.selecteditem.rfqvId).rfqvVendorName = selectedValue.vendorName
        Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvId === Page.Widgets.list6.selecteditem.rfqvId).rfqvStatus = "Open"
        Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvId === Page.Widgets.list6.selecteditem.rfqvId).statusUpdate = "new"

        var address = selectedValue.add1 + ", " + selectedValue.add2 + (selectedValue.add3 !== null ? (!selectedValue.add3.length ? (", " + selectedValue.add3) : "") : "") + (selectedValue.city !== null ? (!selectedValue.city.length ? (", " + selectedValue.city) : "") : "")
        Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvId === Page.Widgets.list6.selecteditem.rfqvId).rfqvVendorAddress = address
        Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvId === Page.Widgets.list6.selecteditem.rfqvId).rfqvVendorTitle = selectedValue.title

        Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvId === Page.Widgets.list6.selecteditem.rfqvId).rfqvVendorEmail = selectedValue.email
        Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvId === Page.Widgets.list6.selecteditem.rfqvId).rfqvVendorPhone = selectedValue.mobilePhone
    } else {
        widget.datavalue = {
            "vendorName": Page.Widgets.list6.selecteditem.rfqvVendorName,
            "vendorCode": Page.Widgets.list6.selecteditem.rfqvVendorCode
        }
        alert("Please choose different vendor")
    }
};

Page.picture10_1Click = function($event, widget, item, currentItemWidgets) {
    Promise.resolve().then(() => {
        Page.Variables.varSelectListDialog.clearData()
        Page.Variables.varSelectListDialogDelete.clearData()

        Page.Widgets.list3.selecteditem.listvendor.forEach((dt) => {
            dt.statusUpdate = "old"
            dt.vendorPhone = ""
            dt.vendorOverTime = 0

            if (dt.rfqvStatus !== "Over Time Limit") {
                dt.statusList = "nochange"
                Page.Variables.varSelectListDialog.addItem(dt)
            } else {
                dt.statusList = "change"
                Page.Variables.varSelectListDialog.addItem(dt)
            }
        })
        return Page.Variables.varSelectListDialog.dataSet
    }).then(() => {
        Page.Variables.varShowListPopUpEdit.dataSet.dataValue = false
        Page.Variables.varShowListPopUp.dataSet.dataValue = false
        Page.Widgets.dialogItem.open()
    })
};

Page.button11Click = function($event, widget) {
    var datanew = Page.Variables.varSelectListDialog.dataSet.filter(x => x.statusUpdate === "new")

    return Promise.resolve().then(() => {
        Page.Widgets.spinner2.show = true
        return Page.mapRFQVendor(datanew, Page.Widgets.list3.selecteditem.rfqId, Page.Variables.varSelectListDialog.dataSet[0].rfqvId, Page.Widgets.list3.selecteditem.rfqCreatedName, Page.Widgets.list3.selecteditem.companySendEmail)
    }).then(() => {
        return Page.mapRFQVendorDelete(Page.Variables.varSelectListDialogDelete.dataSet)
    }).then(() => {
        Page.Widgets.spinner2.show = false
        Page.Variables.qGetRFGVendorTracking.invoke();
        Page.Widgets.dialogItem.close()
    }).catch(function(err) {
        console.log(err)
        Page.Widgets.spinner2.show = false
        Page.Widgets.dialogItem.close()
    })
};

Page.mapRFQVendorDelete = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    return Promise.resolve().then(() => {
        return Page.Variables.qChangeRFQVendorStatusWithoutCreated.invoke({
            "inputFields": {
                "rfqvModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                "rfqvModifiedAt": new Date().toISOString(),
                "rfqvStatus": "deleted",
                "rfqvId": dt.rfqvId
            }
        })
    }).then(function(res) {
        return Page.mapRFQVendorDelete(data)
    }).catch(function(err) {
        console.log(err)
        return Page.mapRFQVendorDelete(data)
    })
}

Page.mapRFQVendor = function(data, rfqId, rfqvId, nameCreated, companySendEmail) {
    var tmpNewDataVendor = null

    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    return Promise.resolve().then(() => {

        if (dt.statusList === "nochange") {
            if (dt.rfqvStatus === "Open") {
                return Page.Variables.qChangeRFQVendorStatus.invoke({
                    "inputFields": {
                        "rfqvCreatedAt": new Date().toISOString(),
                        "rfqvModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                        "rfqvModifiedAt": new Date().toISOString(),
                        "rfqvStatus": dt.rfqvStatus,
                        "rfqvId": dt.rfqvId
                    }
                }).then(function() {
                    if (!dt.rfqvVendorCode) {
                        return App.sendEmailRFQ(dt.rfqvVendorEmail, dt.rfqvVendorName, "RFQEmailPage?rfqvId=" + dt.rfqvId, nameCreated, companySendEmail)
                    }

                    return Page.Variables.vVendor.invoke({
                        "filterFields": {
                            "vendorCode": {
                                "value": dt.rfqvVendorCode
                            }
                        }
                    }).then(function(res) {
                        var email_receiver = Page.getSendEmail(res)
                        if (!email_receiver) {
                            return Promise.resolve()
                        }

                        return App.sendEmailRFQ(email_receiver, dt.rfqvVendorName, "VendorQuotationPage", nameCreated, companySendEmail)
                    })

                    // }).then(function(res) {
                    //     if (Page.getSendEmail(res)) {
                    //         return App.sendEmailRFQ(Page.getSendEmail(res), dt.rfqvVendorName, "VendorQuotationPage", nameCreated, companySendEmail)
                    //     } else {
                    //         return Promise.resolve()
                    //     }
                }).then(function() {
                    return Page.Variables.dbTblTInbox.createRecord({
                        row: {
                            "inbTimestamp": new Date().toISOString(),
                            "inbTaskType": "RFQ",
                            "inbSubject": "<b>RFQ</b> - RFQ Ready to Submit",
                            "userId": "ven::" + dt.rfqvVendorEmail,
                            "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
                        }
                    })
                })
            } else {
                return Promise.resolve()
            }
        } else if (dt.statusList === "add") {
            return Page.Variables.dbTblTRfqVendor.createRecord({
                row: {
                    "rfqId": rfqId,
                    "rfqvCreatedBy": App.Variables.loggedInUserData.dataSet.username,
                    "rfqvCreatedAt": new Date().toISOString(),
                    "rfqvModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                    "rfqvModifiedAt": new Date().toISOString(),
                    "rfqvStatus": "Open",
                    "rfqvVendorCode": dt.rfqvVendorCode,
                    "rfqvVendorName": dt.rfqvVendorName,
                    "rfqvVendorAddress": dt.rfqvVendorAddress,
                    "rfqvVendorTitle": null,
                    "rfqvVendorEmail": dt.rfqvVendorEmail,
                    "rfqvVendorPhone": dt.rfqvVendorPhone,
                    "rfqvTimeLimit": dt.vendorOverTime,
                }
            }).then(function(res) {
                tmpNewDataVendor = res
                return Page.Variables.dbTblTRfqVenQuotation.invoke({
                    "filterFields": {
                        "rfqvId": {
                            "value": rfqvId
                        }
                    }
                })
            }).then(async function(res2) {
                res2 = res2.data;
                for (let item of res2) {
                    await Page.Variables.dbTblTRfqVenQuotation.createRecord({
                        row: {
                            "rfqvId": tmpNewDataVendor.rfqvId,
                            "rlsId": item.rlsId,
                            "liqCreatedBy": App.Variables.loggedInUserData.dataSet.username,
                            "liqCreatedAt": new Date().toISOString(),
                            "liqModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                            "liqModifiedAt": new Date().toISOString(),
                            "liqStatus": "active"
                        }
                    })
                }
                return res2[0]
            }).then(function(res) {
                return App.sendEmailRFQ(dt.rfqvVendorEmail, dt.rfqvVendorName, "RFQEmailPage?rfqvId=" + tmpNewDataVendor.rfqvId, nameCreated, companySendEmail)
            }).then(function() {
                return Page.Variables.dbTblTInbox.createRecord({
                    row: {
                        "inbTimestamp": new Date().toISOString(),
                        "inbTaskType": "RFQ",
                        "inbSubject": "<b>RFQ</b> - RFQ Ready to Submit",
                        "userId": "ven::" + dt.rfqvVendorEmail,
                        "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
                    }
                })
            })
        } else {
            return Page.Variables.qChangeRFQSetNull.invoke({
                "inputFields": {
                    "rfqvCreatedAt": new Date().toISOString(),
                    "rfqvModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                    "rfqvModifiedAt": new Date().toISOString(),
                    "rfqvStatus": "Open",
                    "rfqvVendorCode": dt.rfqvVendorCode,
                    "rfqvVendorName": dt.rfqvVendorName,
                    "rfqvVendorEmail": dt.rfqvVendorEmail,
                    "rfqvVendorPhone": dt.rfqvVendorPhone,
                    "rfqvVendorAddress": dt.rfqvVendorAddress,
                    "rfqvVendorTitle": dt.rfqvVendorTitle,
                    "rfqvId": dt.rfqvId
                }
            }).then(function() {
                return Page.Variables.vVendor.invoke({
                    "filterFields": {
                        "vendorCode": {
                            "value": dt.rfqvVendorCode
                        }
                    }
                })
            }).then(function(res) {
                if (!dt.rfqvVendorCode) {
                    return App.sendEmailRFQ(dt.rfqvVendorEmail, dt.rfqvVendorName, "VendorQuotationPage", nameCreated, companySendEmail)
                }

                return Page.Variables.vVendor.invoke({
                    "filterFields": {
                        "vendorCode": {
                            "value": dt.rfqvVendorCode
                        }
                    }
                }).then(function(res) {
                    var email_receiver = Page.getSendEmail(res)
                    if (!email_receiver) {
                        return Promise.resolve()
                    }

                    return App.sendEmailRFQ(email_receiver, dt.rfqvVendorName, "VendorQuotationPage", nameCreated, companySendEmail)
                })

                // if (Page.getSendEmail(res)) {
                //     return App.sendEmailRFQ(Page.getSendEmail(res), dt.rfqvVendorName, "VendorQuotationPage", nameCreated, companySendEmail)
                // } else {
                //     return Promise.resolve()
                // }
            }).then(function() {
                return Page.Variables.dbTblTInbox.createRecord({
                    row: {
                        "inbTimestamp": new Date().toISOString(),
                        "inbTaskType": "RFQ",
                        "inbSubject": "<b>RFQ</b> - RFQ Ready to Submit",
                        "userId": "ven::" + dt.rfqvVendorEmail,
                        "inbCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name
                    }
                })
            })
        }
    }).then(function(res) {
        return Page.mapRFQVendor(data, rfqId, rfqvId, nameCreated, companySendEmail)
    }).catch(function(err) {
        console.log(err)
        return Page.mapRFQVendor(data, rfqId, rfqvId, nameCreated, companySendEmail)
    })
}

Page.getSendEmail = function(res) {
    var output = "-"
    res = res.data

    if (res.length < 1) {
        output = null
    } else {
        if (res[0].email1) {
            if (res[0].email2) {
                output = res[0].email1 + "," + res[0].email2
            } else {
                output = res[0].email1
            }
        } else {
            if (res[0].email2) {
                output = res[0].email2
            } else {
                output = null
            }
        }
    }
    return output
}

Page.button5Click = function($event, widget) {
    Page.Variables.varListFileDel.clearData()
    Page.Variables.varSelect.clearData()
    var select = Page.Widgets.list3.listItems._results.filter(x => x.currentItemWidgets.checkbox1.datavalue === true).map((x) => {
        return x.item
    })
    if (select.length > 0) {
        Page.Variables.varSelect.dataSet = select
        Page.Widgets.dialogForceClose.open()
    }
};

Page.button6Click = function($event, widget) {
    $(Page.Widgets.attachmentWidget.nativeElement).find("input").click();
};

Page.FileServiceUploadFileonSuccess = function(variable, data) {
    Page.Variables.varListFileDel.addItem({
        rfqDelAttachment: data[0].fileName
    })
    Page.Widgets.spinner2.show = false
};

Page.FileServiceUploadFileonProgress = function(variable, data, event) {
    Page.Widgets.spinner2.show = true
};

Page.label54Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.FileServiceDownloadFile.invoke({
        "inputFields": {
            "file": Page.Widgets.list5.selecteditem.rfqDelAttachment
        }
    })
};

Page.picture25Click = function($event, widget, item, currentItemWidgets) {
    var dt = Page.Widgets.list5.selecteditem
    var filters = Page.Variables.varListFileDel.dataSet.find(x => x.rfqDelAttachment === dt.rfqDelAttachment)
    Page.Variables.varListFileDel.dataSet = Page.Variables.varListFileDel.dataSet.filter(x => x !== filters)
    return Page.Variables.FileServiceDeleteFile.invoke({
        "inputFields": {
            "file": filters.rfqDelAttachment
        }
    })
};

Page.button11_1Click = function($event, widget) {
    var data = Page.Variables.varListFileDel.dataSet
    return Promise.resolve().then(() => {
        return Page.deleteFileUpload(data)
    }).then(() => {
        // Page.Variables.qGetRFGVendorTracking.invoke();
        Page.Widgets.dialogForceClose.close()
    })
};

Page.deleteFileUpload = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    return Promise.resolve().then(() => {
        return Page.Variables.FileServiceDeleteFile.invoke({
            "inputFields": {
                "file": dt.rfqDelAttachment
            }
        })
    }).then(function(res) {
        return Page.deleteFileUpload(data)
    }).catch(function(err) {
        console.log(err)
        return Page.deleteFileUpload(data)
    })
}

Page.button12Click = function($event, widget) {
    var file = Page.Variables.varListFileDel.dataSet.length > 0 ? btoa(JSON.stringify(Page.Variables.varListFileDel.dataSet)) : null
    return Promise.resolve().then(() => {
        return Page.mapRFQ(Page.Variables.varSelect.dataSet, file)
    }).then(() => {
        Page.Variables.qGetRFGVendorTracking.invoke();
        Page.Widgets.dialogForceClose.close()
    })
};

Page.mapRFQ = function(data, file) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    return Promise.resolve().then(() => {
        return Page.Variables.qChangeRGQ.invoke({
            "inputFields": {
                "rfqId": dt.rfqId,
                "rfqModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                "rfqModifiedAt": new Date().toISOString(),
                "rfqStatus": "Closed",
                "rfqDelAttachment": file,
                "rfqSumAttachment": dt.rfqSumAttachment
            }
        })
    }).then(function(res) {
        return Page.mapRFQ(data, file)
    }).catch(function(err) {
        console.log(err)
        return Page.mapRFQ(data, file)
    })
}

Page.checkboxset2Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset2.datavalue.length === 3) {
        Page.Widgets.checkbox3.datavalue = true
    } else {
        Page.Widgets.checkbox3.datavalue = false
    }
};

Page.checkbox3Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset2.datavalue = Page.Widgets.checkboxset2.dataset
    } else {
        Page.Widgets.checkboxset2.datavalue = null
    }
};

Page.text5Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
};

Page.container59Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').addClass("clicked")
};

Page.checkbox4Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.list3.listItems._results.filter(function(el) {
            return el.item.rfqStatus === 'On Progress'
        }).map(function(item) {
            return item.currentItemWidgets.checkbox1.datavalue = true
        })
    } else {
        Page.Widgets.list3.listItems._results.map(function(item) {
            return item.currentItemWidgets.checkbox1.datavalue = false
        })
    }
};
Page.picture21Click = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_RFQSummaryPage.invoke({
        data: {
            "rfqId": Page.Widgets.list3.selecteditem.rfqId
        }
    })
};

Page.labelSubmitClick = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_QuotationFormView.invoke({
        data: {
            "rfqvId": item.rfqvId
        }
    })
};

Page.button11_2Click = function($event, widget) {
    Page.Variables.MODELConditionExistingVendor.dataSet.dataValue = false
    Page.Widgets.textVendorCode.datavalue = "";
    Page.Widgets.textVendorAddress.datavalue = "";
    Page.Widgets.text6_1.datavalue = ""
    Page.Widgets.text7.datavalue = ""
    Page.Widgets.text6.datavalue = ""
    Page.Widgets.text8.datavalue = 0
    Page.Variables.varShowListPopUpEdit.dataSet.dataValue = false
    Page.Variables.varShowListPopUp.dataSet.dataValue = true
    Page.Variables.vmErrorMessage.clearData()
};

Page.label33Click = function($event, widget) {
    Page.Variables.varShowListPopUpEdit.dataSet.dataValue = true
    Page.Variables.varShowListPopUp.dataSet.dataValue = true
    Page.Widgets.text6_1.datavalue = Page.Widgets.list6.selecteditem.rfqvVendorEmail
    Page.Widgets.text7.datavalue = Page.Widgets.list6.selecteditem.rfqvVendorPhone
    Page.Widgets.text6.datavalue = Page.Widgets.list6.selecteditem.rfqvVendorName
    Page.Widgets.text8.datavalue = Page.Widgets.list6.selecteditem.vendorOverTime
};

Page.button12_2Click = function($event, widget) {
    Page.Variables.varShowListPopUpEdit.dataSet.dataValue = false
    Page.Variables.varShowListPopUp.dataSet.dataValue = false
    Page.Widgets.text6_1.datavalue = ""
    Page.Widgets.text7.datavalue = ""
    Page.Widgets.text6.datavalue = ""
    Page.Widgets.text8.datavalue = 0
};

Page.button13_1Click = function($event, widget) {
    var isValid = true
    var emailRegex = /\S+@\S+\.\S+/

    if (!Page.Widgets.text6.datavalue && !Page.Widgets.text6.datavalue.length) {
        Page.Variables.vmErrorMessage.setValue("name", "this field is required")
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("name", null)
    }

    if (!Page.Widgets.text6_1.datavalue && !Page.Widgets.text6_1.datavalue.length) {
        Page.Variables.vmErrorMessage.setValue("email", "this field is required")
        isValid = false
    } else if (!emailRegex.test(Page.Widgets.text6_1.datavalue)) {
        Page.Variables.vmErrorMessage.setValue("email", 'Please enter a part followed by "@" and "."')
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("email", null)
    }

    if (!Page.Widgets.text7.datavalue && !Page.Widgets.text7.datavalue.length) {
        Page.Variables.vmErrorMessage.setValue("phone", "this field is required")
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("phone", null)
    }

    if (!Page.Widgets.text8.datavalue && Page.Widgets.text8.datavalue !== 0) {
        Page.Variables.vmErrorMessage.setValue("overTime", "this field is required")
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("overTime", null)
    }

    if (isValid) {
        if (Page.Variables.varSelectListDialog.dataSet.length < 5) {
            Promise.resolve().then(function() {
                Page.Variables.varSelectListDialog.addItem({
                    "rfqvId": 0,
                    "rfqvStatus": "Open",
                    "rfqvVendorCode": Page.Widgets.textVendorCode.datavalue,
                    "rfqvVendorAddress": Page.Widgets.textVendorAddress.datavalue,
                    "rfqvVendorEmail": Page.Widgets.text6_1.datavalue,
                    "rfqvVendorPhone": Page.Widgets.text7.datavalue,
                    "rfqvVendorName": Page.Widgets.text6.datavalue,
                    "statusList": "add",
                    "statusUpdate": "new",
                    "vendorOverTime": Page.Widgets.text8.datavalue
                })
            }).then(function() {
                Page.Widgets.text6_1.datavalue = ""
                Page.Widgets.text7.datavalue = ""
                Page.Widgets.text6.datavalue = ""
                Page.Widgets.text8.datavalue = 0
                Page.Variables.varShowListPopUp.dataSet.dataValue = false
                Page.Variables.varShowListPopUpEdit.dataSet.dataValue = false
            })
        } else {
            alert("Limited 5 vendor")
        }
    }
};

Page.button14_1Click = function($event, widget) {
    var isValid = true
    var emailRegex = /\S+@\S+\.\S+/

    if (!Page.Widgets.text6.datavalue && !Page.Widgets.text6.datavalue.length) {
        Page.Variables.vmErrorMessage.setValue("name", "this field is required")
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("name", null)
    }

    if (!Page.Widgets.text6_1.datavalue && !Page.Widgets.text6_1.datavalue.length) {
        Page.Variables.vmErrorMessage.setValue("email", "this field is required")
        isValid = false
    } else if (!emailRegex.test(Page.Widgets.text6_1.datavalue)) {
        Page.Variables.vmErrorMessage.setValue("email", 'Please enter a part followed by "@" and "."')
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("email", null)
    }

    if (!Page.Widgets.text7.datavalue && !Page.Widgets.text7.datavalue.length) {
        Page.Variables.vmErrorMessage.setValue("phone", "this field is required")
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("phone", null)
    }

    if (!Page.Widgets.text8.datavalue && Page.Widgets.text8.datavalue !== 0) {
        Page.Variables.vmErrorMessage.setValue("overTime", "this field is required")
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("overTime", null)
    }

    if (isValid) {
        Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvVendorName === Page.Widgets.list6.selecteditem.rfqvVendorName).rfqvVendorEmail = Page.Widgets.text6_1.datavalue

        Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvVendorName === Page.Widgets.list6.selecteditem.rfqvVendorName).rfqvVendorPhone = Page.Widgets.text7.datavalue

        Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvVendorName === Page.Widgets.list6.selecteditem.rfqvVendorName).rfqvVendorName = Page.Widgets.text6.datavalue

        Page.Variables.varSelectListDialog.dataSet.find(x => x.rfqvVendorName === Page.Widgets.list6.selecteditem.rfqvVendorName).vendorOverTime = Page.Widgets.text8.datavalue

        Page.Variables.varShowListPopUp.dataSet.dataValue = false
        Page.Variables.varShowListPopUpEdit.dataSet.dataValue = false
    }
};

Page.picture11Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.varSelectListDialogDelete.addItem(item)
    Page.Variables.varSelectListDialog.dataSet = Page.Variables.varSelectListDialog.dataSet.filter(x => x !== item)
};

Page.text7Keyup = function($event, widget) {
    Page.Widgets.text7.datavalue = widget.datavalue.replace(/\D/g, '')
};

// ======================= summerize =====================

Page.FileServiceUploadFileSumonSuccess = function(variable, data) {
    Page.Variables.varListFileSum.addItem({
        rfqSumAttachment: data[0].fileName
    })
    Page.Widgets.spinner2.show = false
};

Page.FileServiceUploadFileSumonProgress = function(variable, data, event) {
    Page.Widgets.spinner2.show = true
};

Page.button14Click = function($event, widget) {
    $(Page.Widgets.fileuploadSum.nativeElement).find("input").click();
};
Page.pictureSum1Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.varListFileSum.clearData()
};
Page.button12_1Click = function($event, widget) {
    var data = Page.Variables.varListFileSum.dataSet
    return Promise.resolve().then(() => {
        return Page.deleteFileUploadSum(data)
    }).then(() => {
        // Page.Variables.qGetRFGVendorTracking.invoke();
        Page.Widgets.dialogSummerize.close()
    })
};

Page.deleteFileUploadSum = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    return Promise.resolve().then(() => {
        return Page.Variables.FileServiceDeleteFile.invoke({
            "inputFields": {
                "file": dt.rfqSumAttachment
            }
        })
    }).then(function(res) {
        return Page.deleteFileUploadSum(data)
    }).catch(function(err) {
        console.log(err)
        return Page.deleteFileUploadSum(data)
    })
}
Page.label37Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.FileServiceDownloadFile.invoke({
        "inputFields": {
            "file": Page.Widgets.list7.selecteditem.rfqSumAttachment
        }
    })
};
Page.picture13Click = function($event, widget, item, currentItemWidgets) {
    var dt = Page.Widgets.list7.selecteditem
    var filters = Page.Variables.varListFileSum.dataSet.find(x => x.rfqSumAttachment === dt.rfqSumAttachment)
    Page.Variables.varListFileSum.dataSet = Page.Variables.varListFileSum.dataSet.filter(x => x !== filters)
    return Page.Variables.FileServiceDeleteFile.invoke({
        "inputFields": {
            "file": filters.rfqSumAttachment
        }
    })
};
Page.button13Click = function($event, widget) {
    var file = Page.Variables.varListFileSum.dataSet.length > 0 ? btoa(JSON.stringify(Page.Variables.varListFileSum.dataSet)) : null
    return Promise.resolve().then(() => {
        return Page.Variables.qChangeRGQ.invoke({
            "inputFields": {
                "rfqId": Page.Widgets.list3.selecteditem.rfqId,
                "rfqModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                "rfqModifiedAt": new Date().toISOString(),
                "rfqStatus": Page.Widgets.list3.selecteditem.rfqStatus,
                "rfqDelAttachment": Page.Widgets.list3.selecteditem.rfqDelAttachment,
                "rfqSumAttachment": file
            }
        })
    }).then(() => {
        // Page.Variables.qGetRFGVendorTracking.invoke();
        Page.Widgets.dialogSummerize.close()
        App.Actions.goToPage_RFQSummaryPage.invoke({
            data: {
                "rfqId": Page.Widgets.list3.selecteditem.rfqId
            }
        })
    })
};

Page.button7Click = function($event, widget) {

    var tmpFilter = {
        "status": Page.Widgets.text5.datavalue,
        "inputNumber": Page.Widgets.textRfqNumber.datavalue,
        "companyCode": Page.Widgets.searchCompany.datavalue.ccode
    }
    window.localStorage.setItem("budget-value-rfq-tracking", JSON.stringify(tmpFilter))
};
Page.attachmentWidgetSelect = function($event, widget, selectedFiles) {
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
Page.fileuploadSumSelect = function($event, widget, selectedFiles) {
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

    Page.Variables.FileServiceUploadFileSum.invoke()
};
Page.list3Beforedatarender = function(widget, $data) {
    console.log($data)
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
Page.button16Click = function($event, widget) {
    Page.Variables.MODELConditionExistingVendor.dataSet.dataValue = true
    Page.Widgets.text6_1.datavalue = ""
    Page.Widgets.text7.datavalue = ""
    Page.Widgets.text6.datavalue = ""
    Page.Widgets.textVendorCode.datavalue = "";
    Page.Widgets.textVendorAddress.datavalue = "";
    Page.Widgets.text8.datavalue = 0
    Page.Variables.varShowListPopUpEdit.dataSet.dataValue = false
    Page.Variables.varShowListPopUp.dataSet.dataValue = true
    Page.Variables.vmErrorMessage.clearData()
};
Page.search3Select = function($event, widget, selectedValue) {
    Page.Widgets.text6.datavalue = selectedValue.vendorName
    Page.Widgets.text6_1.datavalue = selectedValue.email1
    Page.Widgets.text7.datavalue = selectedValue.telephone
    Page.Widgets.textVendorCode.datavalue = selectedValue.vendorCode;
    Page.Widgets.textVendorAddress.datavalue = selectedValue.add1;
};

Page.button19Click = function($event, widget, item, currentItemWidgets) {
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
            "rfqId": item.rfqId
        }
    })

    Page.Variables.qGetRfqSummary.invoke({
        "inputFields": {
            "rfqId": item.rfqId
        }
    }).then(function(data) {
        console.log('data', data);
        data = JSON.parse(data.body)
        console.log('parsed-data', data);
        if (data.content.length === 0) {
            return
        }

        var sorted = data.content.map(function(item) {
            return item.rfqvDuration
        }).sort(function(a, b) {
            return b - a;
        })


        Page.Variables.mQuotationAttachment.dataSet = data.content.map((item) => {
            console.log('item', item)
            let data = item;
            data.attachments = JSON.parse(atob(item.attachments))
            return data
        })

        console.log('mapped', Page.Variables.mQuotationAttachment.dataSet)

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
    Page.Widgets.spinner2.show = true
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

    return Promise.resolve().then(function() {
        return html2pdf().set(opt).from(element).save();
    }).then(function() {
        Page.Widgets.spinner2.show = false
    })
};