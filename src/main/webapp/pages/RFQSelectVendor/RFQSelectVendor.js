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

    // // authorization handle
    // if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
    //     if (App.Variables.loggedInUser.dataSet.roles.indexOf('RFQ-002') == -1) {
    //         App.Actions.goToPage_Main.invoke()
    //     }
    // }

}

Page.JSON = JSON

Page.checkboxFilterMGAllChange = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxsetFilterMG.datavalue = Page.Widgets.checkboxsetFilterMG.dataset.map(function(el) {
            return el.materialGroup
        })
    } else {
        Page.Widgets.checkboxsetFilterMG.datavalue = null
    }
};
Page.checkboxsetFilterMGChange = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxsetFilterMG.datavalue.length === Page.Widgets.checkboxsetFilterMG.dataset.length) {
        Page.Widgets.checkboxFilterMGAll.datavalue = true
    } else {
        Page.Widgets.checkboxFilterMGAll.datavalue = false
    }
};

Page.checkboxListAllChange = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        // Page.Widgets.checkboxList.datavalue = true
        Page.Widgets.listPRI.listItems._results.filter(function(el) {
            return el.item.rfqStatus == 'open' || el.item.rfqStatus == null
        }).map(function(item) {
            return item.currentItemWidgets.checkboxList.datavalue = true
        })
    } else {
        Page.Widgets.listPRI.listItems._results.map(function(item) {
            return item.currentItemWidgets.checkboxList.datavalue = false
        })
    }
};

Page.selectRowChange = function($event, widget, newVal, oldVal) {
    Page.Variables.vViewVendor.maxResults = newVal
    Page.Variables.vViewVendor.update()
};

Page.selectRowEVChange = function($event, widget, newVal, oldVal) {
    console.log(Page.Widgets.listRAV)
    Page.Widgets.listRAV.pagesize = newVal
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.vViewVendor.orderBy == field + " ASC") {
        Page.Variables.vViewVendor.orderBy = field + " DESC"
    } else if (Page.Variables.vViewVendor.orderBy == field + " DESC") {
        Page.Variables.vViewVendor.orderBy = ""
    } else {
        Page.Variables.vViewVendor.orderBy = field + " ASC"
    }
    Page.Variables.vViewVendor.invoke()
}

Page.container40Click = function($event, widget) {
    Page.toggleTableSort('vendorCode')
};
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('vendorName')
};
Page.container42Click = function($event, widget) {
    Page.toggleTableSort('email')
};
Page.container43Click = function($event, widget) {
    Page.toggleTableSort('email2')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('telephone')
};

Page.checkDisabled = function() {
    var dataChecklist = Page.Widgets.listPRI.listItems._results.filter(function(el) {
        return el.currentItemWidgets.checkboxList.datavalue === true
    })

    var dataRAV = Page.Widgets.listRAV.listItems._results.filter(function(el) {
        return el.currentItemWidgets.checkboxListRAV.datavalue === true
    })

    var dataCheck = dataChecklist.concat(dataRAV)

    // console.log("dataCheck", dataCheck)
    if (dataCheck.length >= 5) {
        Page.Widgets.listPRI.listItems._results.filter(function(el) {
            return el.currentItemWidgets.checkboxList.datavalue !== true
        }).forEach(function(item) {
            return item.currentItemWidgets.checkboxList.disabled = true
        })

        Page.Widgets.listRAV.listItems._results.filter(function(el) {
            return el.currentItemWidgets.checkboxListRAV.datavalue !== true
        }).forEach(function(item) {
            return item.currentItemWidgets.checkboxListRAV.disabled = true
        })
    } else {
        Page.Widgets.listPRI.listItems._results.forEach(function(item) {
            return item.currentItemWidgets.checkboxList.disabled = false
        })

        Page.Widgets.listRAV.listItems._results.forEach(function(item) {
            return item.currentItemWidgets.checkboxListRAV.disabled = false
        })
    }
}

Page.checkDisabledBtn = function() {
    var dataChecklist = Page.Widgets.listPRI.listItems._results.filter(function(el) {
        return el.currentItemWidgets.checkboxList.datavalue === true
    })

    var dataRAV = Page.Widgets.listRAV.listItems._results.filter(function(el) {
        return el.currentItemWidgets.checkboxListRAV.datavalue === true
    })

    var dataCheck = dataChecklist.concat(dataRAV)



    // console.log("dataCheck", dataCheck)
    if (dataCheck.length == 0) {
        return true
    } else {
        return false
    }
}

Page.checkboxListChange = function($event, widget, item, currentItemWidgets, newVal, oldVal) {
    // Page.checkDisabled()
    var dataPRI;

    if (newVal) {
        dataPRI = Page.Widgets.listPRI.listItems._results.filter(function(el) {
            return el.currentItemWidgets.checkboxList.datavalue === true
        });
        dataPRI.forEach((item) => {
            Page.removeCheckbox(item);
            Page.Variables.mTempChecked.dataSet.push(item);
        });
    } else if (!newVal) {
        Page.Variables.mTempChecked.dataSet = Page.Variables.mTempChecked.dataSet.filter((el) => {
            return el.item.vendorCode != item.vendorCode
        });
    }

    console.log('dataset', Page.Variables.mTempChecked.dataSet);

    Page.checkDisabledBtn()

    // var dataChecklist = Page.Widgets.listPRI.listItems._results.filter(function(el) {
    //     return el.currentItemWidgets.checkboxList.datavalue === true
    // })
    // if (dataChecklist.length > 5) {
    //     alert("Max 5")
    //     return widget.datavalue = false
    // }
    // // console.log(newVal)
};

Page.removeCheckbox = function(element) {
    Page.Variables.mTempChecked.dataSet = Page.Variables.mTempChecked.dataSet.filter((el) => {
        return el.item.vendorCode != element.item.vendorCode
    });
}

Page.btnSelectVendorClick = function($event, widget) {
    console.log(App.pageParams.Liid)
    console.log(App.pageParams.Materialid)
};

Page.vMatVendoronSuccess = function(variable, data) {
    // console.log("MatVendor", variable)
};

Page.vViewVendoronSuccess = function(variable, data) {
    // console.log("ViewVendor", variable)
};

Page.listPRIBeforedatarender = function(widget, $data) {
    let checkedVendor = Page.Variables.mTempChecked.dataSet.map(el => {
        return el.item.vendorCode;
    });
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.vViewVendor.pagination.number * Page.Variables.vViewVendor.pagination.size) + (i + 1);
        $data[i].checked = (checkedVendor.includes($data[i].vendorCode))
    }
    console.log('widgetnya', widget)
};


Page.buttonSendQuotationClick = function($event, widget) {
    var dataChecklist = Page.Widgets.listPRI.listItems._results.filter(function(el) {
        return el.currentItemWidgets.checkboxList.datavalue === true
    })

    var dataRAV = Page.Widgets.listRAV.listItems._results.filter(function(el) {
        return el.currentItemWidgets.checkboxListRAV.datavalue === true
    })

    // var dataCheck = dataChecklist.concat(dataRAV)

    var dataCheck = Page.Variables.mTempChecked.dataSet.concat(dataRAV)

    console.log('ini datanya', dataCheck)
    // return alert("Limited to x vendor")
    if (dataCheck.length > 5) {
        return alert("Limited to 5 vendor")
    }


    var liData = JSON.parse(JSON.stringify(Page.Variables.vViewPurchseRequestItem.dataSet))

    Page.Widgets.spinner1.show = true
    return Page.insMultipleRFQ(liData.reverse()).then(function() {
        Page.Widgets.spinner1.show = false
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.App.Actions.goToPage_RFQlist.invoke()
    }).catch(function(err) {
        console.log("error", err)
        Page.Widgets.spinner1.show = false
    })

};

Page.insMultipleRFQ = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }

    // loop till data empty
    return Page.Variables.vRFQ.createRecord({
        row: {
            // "rfqId": null,
            "rfqCreatedBy": Page.App.Variables.loggedInUserData.dataSet.username,
            "rfqCreatedAt": new Date().toISOString(),
            "rfqModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
            "rfqModifiedAt": new Date().toISOString(),
            "rfqRef": null,
            "rfqStatus": "On Progress",
            "rfqAttachment": null,
            "rfqCreatedName": Page.App.Variables.loggedInUserData.dataSet.user_full_name
        }
    }).then(function(res) {
        var ref = ""
        if (res.rfqId < 10) {
            var ref = "9000-0" + res.rfqId
        } else {
            var ref = "9000-" + res.rfqId
        }
        return Page.Variables.svGenerateRFQRef.invoke({
            "inputFields": {
                "rfqId": res.rfqId,
                "rfqRef": ref
            }
        }).then(function(e) {
            return res
        })
    }).then(function(dataRFQ) {
        var dataChecklist = Page.Widgets.listPRI.listItems._results.filter(function(el) {
            return el.currentItemWidgets.checkboxList.datavalue === true
        })

        var dataRAV = Page.Widgets.listRAV.listItems._results.filter(function(el) {
            return el.currentItemWidgets.checkboxListRAV.datavalue === true
        })


        Page.Variables.vmRAV.dataSet

        var dataCheck = dataChecklist.concat(dataRAV)
        // console.log(dataCheck)

        return Page.insMultipleRFQVend(data, dataRFQ, dataCheck)
    }).then(function(res) {
        return Promise.resolve()
    }).catch(function(err) {
        console.log(err)
        return Promise.resolve()
    })
}

Page.insMultipleRFQVend = function(dataLi, dataRFQ, dataChecklist) {
    if (!dataChecklist || !dataChecklist.length) {
        return Promise.resolve()
    }

    // loop till data empty
    var dt = dataChecklist.pop()

    if (dt.item.uuid === undefined) {
        var dataIns = {
            // "rfqvId": null,
            "rfqId": dataRFQ.rfqId,
            "rfqvCreatedBy": Page.App.Variables.loggedInUserData.dataSet.username,
            "rfqvCreatedAt": new Date().toISOString(),
            "rfqvModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
            "rfqvModifiedAt": new Date().toISOString(),
            "rfqvStatus": "Open",
            "rfqvVendorCode": dt.item.vendorCode,
            "rfqvVendorName": dt.item.vendorName,
            "rfqvVendorAddress": dt.item.add1 + ", " + dt.item.add2 + ((dt.item.add3 !== null && dt.item.add3 !== "") ? (", " + dt.item.add3) : ""),
            "rfqvVendorTitle": dt.item.title,
            "rfqvVendorEmail": dt.item.email,
            "rfqvVendorPhone": dt.item.telephone == " " ? null : dt.item.telephone,
            "rfqvTimeLimit": dt.currentItemWidgets.textTimeLimit.datavalue
        }
    } else {
        var dataIns = {
            // "rfqvId": null,
            "rfqId": dataRFQ.rfqId,
            "rfqvCreatedBy": Page.App.Variables.loggedInUserData.dataSet.username,
            "rfqvCreatedAt": new Date().toISOString(),
            "rfqvModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
            "rfqvModifiedAt": new Date().toISOString(),
            "rfqvStatus": "Open",
            "rfqvVendorCode": dt.item.vendorCode,
            "rfqvVendorName": dt.item.vendorName,
            "rfqvVendorAddress": dt.item.add1 !== null ? (dt.item.add1 + ", " + dt.item.add2 + ((dt.item.add3 !== null && dt.item.add3 !== "") ? (", " + dt.item.add3) : "")) : null,
            "rfqvVendorTitle": null,
            "rfqvVendorEmail": dt.item.email,
            "rfqvVendorPhone": dt.item.telephone,
            "rfqvTimeLimit": dt.currentItemWidgets.textTimeLimitRAV.datavalue
        }
    }

    return Page.Variables.vRFQVendor.createRecord({
        row: dataIns
    }).then(function(dti) {
        var rfqv_promises = dataLi.map(function(item) {
            return Page.Variables.vRFQVendQuotation.createRecord({
                row: {
                    // "liqId": null,
                    "rfqvId": dti.rfqvId,
                    "rlsId": item.rlsId,
                    "liqCreatedBy": Page.App.Variables.loggedInUserData.dataSet.username,
                    "liqCreatedAt": new Date().toISOString(),
                    "liqModifiedBy": Page.App.Variables.loggedInUserData.dataSet.username,
                    "liqModifiedAt": new Date().toISOString(),
                    "liqStatus": "active"
                }
            }).then(function(data) {
                return Page.Variables.svUpdateStatusRFQ.invoke({
                    "inputFields": {
                        "rlsId": data.rlsId,
                        "status": "On Progress"
                    }
                })
            })
        })
        return Promise.all(rfqv_promises).then(function() {
            return dti
        }).catch(function(err) {
            console.log(err)
            return dti
        })
    }).then(function(data) {
        return Page.Variables.vInbox.createRecord({
            row: {
                "inbTimestamp": new Date().toISOString(),
                "inbTaskType": "RFQ",
                "inbSubject": "<b>RFQ</b> - RFQ Ready to Submit",
                "userId": "ven::" + data.rfqvVendorEmail,
                "inbCreatedBy": Page.App.Variables.loggedInUserData.dataSet.user_full_name
            }
        }).then(function(res) {
            // console.log("res", res)
            return data
        })
    }).then(function(data) {
        var sendTo = ""

        if (dt.item.email1 && dt.item.email2) {
            sendTo = dt.item.email1 + ", " + dt.item.email2
        } else if (dt.item.email1 || dt.item.email2) {
            sendTo = dt.item.email1 || dt.item.email2
        } else {
            sendTo = dt.item.email
        }

        if (data.rfqvVendorCode == null) {
            var url = "RFQEmailPage?rfqvId=" + data.rfqvId
        } else {
            var url = "VendorQuotationPage"
        }

        // if data has no email will not sent
        if (!sendTo) {
            return
        }

        return App.sendEmailRFQ(sendTo, data.rfqvVendorName, url, Page.App.Variables.loggedInUserData.dataSet.user_full_name, dataLi[0].company)
    }).then(function(res) {
        return Page.insMultipleRFQVend(dataLi, dataRFQ, dataChecklist)
    }).catch(function(err) {
        console.log(err)
        return Page.insMultipleRFQVend(dataLi, dataRFQ, dataChecklist)
    })
}

Page.buttonAddEVClick = function($event, widget) {
    Page.Variables.vmErrorMessage.clearData()
    // validation
    var isValid = true
    if (!Page.Widgets.searchVendorEV.datavalue) {
        Page.Variables.vmErrorMessage.setValue("vendorName", "this field is required")
        isValid = false
    }

    if (!isValid) {
        return
    }

    Page.Variables.vmRAV.addItem({
        "uuid": Page.App.generateUUID(),
        "vendorName": Page.Widgets.searchVendorEV.datavalue.vendorName,
        "vendorCode": Page.Widgets.searchVendorEV.datavalue.vendorCode,
        "email": Page.Widgets.searchVendorEV.datavalue.email,
        "add1": Page.Widgets.searchVendorEV.datavalue.add1,
        "add2": Page.Widgets.searchVendorEV.datavalue.add2,
        "add3": Page.Widgets.searchVendorEV.datavalue.add3,
        "title": Page.Widgets.searchVendorEV.datavalue.title,
        "region": Page.Widgets.searchVendorEV.datavalue.region,
        "country": Page.Widgets.searchVendorEV.datavalue.country,
        "email1": Page.Widgets.searchVendorEV.datavalue.email1,
        "email2": Page.Widgets.searchVendorEV.datavalue.email2,
        "telephone": Page.Widgets.searchVendorEV.datavalue.telephone,
        "mobilePhone": Page.Widgets.searchVendorEV.datavalue.mobilePhone
    })

    Page.Widgets.dialogExistingVendor.close()
    console.log(Page.Variables.vmRAV.dataSet)
};


Page.buttonAddNVClick = function($event, widget) {
    var emailRegex = /\S+@\S+\.\S+/

    Page.Variables.vmErrorMessage.clearData()
    // validation
    var isValid = true
    if (!Page.Widgets.textVenNameNV.datavalue) {
        Page.Variables.vmErrorMessage.setValue("vendorNameNV", "this field is required")
        isValid = false
    }

    if (!Page.Widgets.textVenEmailNV.datavalue) {
        Page.Variables.vmErrorMessage.setValue("vendorEmailNV", "this field is required")
        isValid = false
    } else if (!emailRegex.test(Page.Widgets.textVenEmailNV.datavalue)) {
        Page.Variables.vmErrorMessage.setValue("vendorEmailNV", "Email format is not match")
        isValid = false
    }

    if (!Page.Widgets.textVenPhoneNV.datavalue) {
        Page.Variables.vmErrorMessage.setValue("vendorPhoneNV", "this field is required")
        isValid = false
    }

    if (!isValid) {
        return
    }

    Page.Variables.vmRAV.addItem({
        "uuid": Page.App.generateUUID(),
        "vendorName": Page.Widgets.textVenNameNV.datavalue,
        "vendorCode": null,
        "email": Page.Widgets.textVenEmailNV.datavalue,
        "add1": null,
        "add2": null,
        "add3": null,
        "title": null,
        "region": null,
        "country": null,
        "email1": Page.Widgets.textVenEmailNV.datavalue,
        "email2": null,
        "telephone": Page.Widgets.textVenPhoneNV.datavalue,
        "mobilePhone": null
    })

    Page.Widgets.dialogNewVendor.close()
    console.log(Page.Variables.vmRAV.dataSet)
};
Page.textVenPhoneNVKeyup = function($event, widget) {
    Page.Widgets.textVenPhoneNV.datavalue = widget.datavalue.replace(/\D/g, '')
};