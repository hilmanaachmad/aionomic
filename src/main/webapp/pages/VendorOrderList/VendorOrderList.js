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

    // Yearpicker format

    // var a = new Yearpicker(Page.Widgets.text3.inputEl.nativeElement, {
    //     onChange: function(year) {
    //         Page.Widgets.text3.datavalue = year
    //     }
    // })

    // departement user
    return Promise.resolve().then(() => {
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
        // Page.Widgets.text3.datavalue = ""
        return Page.Variables.varDepUser.dataSet
    })


};

Page.dialogItemOpened = function($event, widget) {
    var b = new Yearpicker(Page.Widgets.text11.inputEl.nativeElement, {
        onChange: function(year) {
            Page.Widgets.text11.datavalue = year
            Page.Widgets.search2.datavalue = null
        }
    })

    setTimeout(function() {
        Page.Widgets.text11.datavalue = parseInt(Page.Widgets.list3.selecteditem.pbYear)
        Page.Widgets.search2.datavalue = {
            "ioNumber": Page.Widgets.list3.selecteditem.pbIoNumber,
            "ioName": Page.Widgets.list3.selecteditem.pbIoDesc
        }
    }, 1000);
};

Page.dbTblPbAttachmentonSuccess = function(variable, data) {
    Page.Variables.varListFile.clearData()
    if (typeof data === "object") {
        if (data.length > 0) {
            data.forEach(function(dt) {
                Page.Variables.varListFile.addItem({
                    "pbAtId": dt.pbAtId,
                    "name": dt.attachment,
                    "status": "old"
                })
            })
        }
    }
};

Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.vendorListOrder.maxResults = newVal
    Page.Variables.vendorListOrder.update()
};

Page.selectSearchByChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.searchValue.datavalue = ""
    Page.Widgets.date5.datavalue = ""
    Page.Widgets.selectStatus.datavalue = ""
    // Page.Variables.getVendorListOrder.update()
};

// export excel
Page.button2Click = function($event, widget) {
    // intial
    var workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('Order List');
    var xls_content = []
    var xls_header = [
        App.appLocale.LANG_DOCUMENT_NUMBER,
        App.appLocale.LANG_PROJECT_NAME,
        App.appLocale.LANG_BUDGET_YEAR,
        App.appLocale.LANG_IO_NUMBER,
        App.appLocale.LANG_IO_DESCRIPTION,
        App.appLocale.LANG_AMOUNT_PROPOSAL_BUDGET,
        App.appLocale.LANG_START_DATE,
        App.appLocale.LANG_END_DATE,
        App.appLocale.LANG_CREATED_DATE,
        App.appLocale.LANG_STATUS
    ]
    xls_content.push(xls_header)

    Promise.resolve().then(() => {
        Page.Widgets.spinner2.show = true
        return Page.Variables.dbTblTProposalBudget.invoke()
    }).then((data) => {
        data = data.data
        data.forEach(item => {
            var dataItem = [
                item.pbDocumentNumber,
                item.pbProjectName,
                item.pbYear,
                item.pbIoNumber,
                item.pbIoDesc,
                item.pbAmount,
                item.pbStartDate.split('-')[2] + "-" + item.pbStartDate.split('-')[1] + "-" + item.pbStartDate.split('-')[0],
                item.pbEndDate.split('-')[2] + "-" + item.pbEndDate.split('-')[1] + "-" + item.pbEndDate.split('-')[0],
                item.pbCreatedAt.split('T')[0].split('-')[2] + "-" + item.pbCreatedAt.split('T')[0].split('-')[1] + "-" + item.pbCreatedAt.split('T')[0].split('-')[0] + " " + item.pbCreatedAt.split('T')[1],
                item.pbStatus
            ]
            xls_content.push(dataItem)
        })

        sheet.addRows(xls_content)

        // styling
        let columnWidth = [32, 32, 32, 32, 32, 32, 20, 20, 20, 12];
        sheet.columns.forEach((col, index) => {
            if (columnWidth[index]) {
                col.width = columnWidth[index];
            }
        });
        let alignCenter = {
            vertical: 'middle',
            'horizontal': 'center'
        };
        sheet.getRow(1).alignment = alignCenter;

        // exporting
        workbook.xlsx.writeBuffer().then(function(datas) {
            var filename = "Proposal Budget ";
            var blob = new Blob([datas], {
                type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            });

            let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
            saveAs(blob, filename + currentTime + ".xlsx");
        })

        Page.Widgets.spinner2.show = false
    }).catch((err) => {
        Page.Widgets.spinner2.show = false
        console.log('err ', err)
    })
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.vendorListOrder.orderBy == field + " ASC") {
        Page.Variables.vendorListOrder.orderBy = field + " DESC"
    } else if (Page.Variables.vendorListOrder.orderBy == field + " DESC") {
        Page.Variables.vendorListOrder.orderBy = ""
    } else {
        Page.Variables.vendorListOrder.orderBy = field + " ASC"
    }
    Page.Variables.vendorListOrder.invoke()
}
Page.container41Click = function($event, widget) {

};

Page.container3423Click = function($event, widget) {
    Page.toggleTableSort('sapPoNumber')
};

Page.container42Click = function($event, widget) {
    Page.toggleTableSort('prCreatedName')
};

Page.container68Click = function($event, widget) {
    Page.toggleTableSort('status')
};

Page.dialogDeleteOk = function($event, widget) {
    Page.Widgets.dialogDelete.close()
    Promise.resolve().then(() => {
        return Page.Variables.dbTblTPurchaseRequest.invoke()
    }).then((pr) => {
        pr = pr.data.length
        if (pr > 0) {
            //ga bisa delete karena telah digunakan
        } else {
            // bisa didelete
            Page.deleteData(Page.Widgets.list3.selecteditem);
        }
        return pr
    }).then((pr) => {
        if (pr > 0) {
            App.Actions.appNotification.setMessage("data is used, data cannot be deleted")
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
        } else {
            App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SUCCESS)
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
        }

        Page.Variables.dbTblPbAttachment.update()
        Page.Variables.dbTblTProposalBudget.update()
        Page.Variables.dbTblTProposalBudgetAll.update();
        Page.Widgets.dialogDelete.close()
    }).catch((err) => {
        console.log("err ", err)
    })
};

Page.deleteData = function(data) {
    return Promise.resolve().then(() => {
        // get data attachmet
        return Page.Variables.dbTblPbAttachment.invoke({
            "filterFields": {
                "pbId": {
                    "value": data.pbId
                }
            }
        })
    }).then((attch) => {
        attch = attch.data
        attch.forEach((dt) => {
            dt.name = dt.attachment
        })
        return attch
    }).then((attch) => {
        return Page.mapDeleteAttachment(attch)
    }).then(() => {
        data.pbStatus = "deleted"
        return Page.Variables.dbTblTProposalBudget.updateRecord({
            row: data
        })
    }).then(() => {
        Page.Variables.dbTblPbAttachment.update()
        Page.Variables.dbTblTProposalBudget.update()
        Page.Variables.dbTblTProposalBudgetAll.update();
    })
}

Page.button3Click = function($event, widget) {
    Page.Variables.dbTblTProposalBudget.invoke()
};

Page.button4Click = function($event, widget) {
    Promise.resolve().then(() => {
        Page.Widgets.checkbox1.datavalue = null
        Page.Widgets.checkbox3.datavalue = null
        Page.Widgets.checkbox4.datavalue = null
        Page.Widgets.checkbox5.datavalue = null
        Page.Widgets.checkbox6.datavalue = null
        Page.Widgets.checkbox7.datavalue = null

        Page.Widgets.text3.datavalue = ""
        Page.Widgets.date2.datavalue = ""
        Page.Widgets.date3.datavalue = ""
        Page.Widgets.checkboxset2.datavalue = []
        Page.Widgets.checkboxset3.datavalue = []
        Page.Widgets.checkboxset4.datavalue = []
        Page.Widgets.checkboxset5.datavalue = []
        Page.Widgets.checkboxset7.datavalue = []
        return Page.Widgets.checkboxset6.datavalue = []
    }).then(() => {
        return Page.Variables.dbTblTProposalBudget.invoke()
    }).then(() => {
        Page.Variables.dbTblTProposalBudget.invoke()
    })
};

Page.dbTblTProposalBudgetAllonBeforeDatasetReady = function(variable, data) {

    var arr = []
    var documentNumber = Page.Variables.varFilter.dataSet.documentNumber
    var projectName = Page.Variables.varFilter.dataSet.projectName
    var iONumber = Page.Variables.varFilter.dataSet.iONumber
    var iODescription = Page.Variables.varFilter.dataSet.iODescription
    var status = Page.Variables.varFilter.dataSet.status

    data.forEach((dt) => {
        documentNumber.push(dt.pbDocumentNumber)
        projectName.push(dt.pbProjectName)
        iONumber.push(dt.pbIoNumber)
        iODescription.push(dt.pbIoDesc)
        status.push(dt.pbStatus)
        arr.push(dt)
    })

    documentNumber = [...new Set(documentNumber)];
    projectName = [...new Set(projectName)];
    iONumber = [...new Set(iONumber)];
    iODescription = [...new Set(iODescription)];
    status = [...new Set(status)];

    Page.Variables.varFilter.dataSet.documentNumber = documentNumber
    Page.Variables.varFilter.dataSet.projectName = projectName
    Page.Variables.varFilter.dataSet.iONumber = iONumber
    Page.Variables.varFilter.dataSet.iODescription = iODescription
    Page.Variables.varFilter.dataSet.status = status

    return arr
};

Page.text10Keyup = function($event, widget) {
    // Page.Widgets.text10.datavalue = Page.App.formatCurrency(Page.Widgets.text10.datavalue.replace(/\D/g, ''))
    Page.Variables.varIOTmp.setValue("amount", Page.App.formatCurrency(Page.Widgets.text10.datavalue.replace(/\D/g, '')))
}

Page.vdbBudgetDetailonSuccess = function(variable, data) {
    Page.Variables.varIOTmp.setValue("availableBudget", data[0].bdAfterAdjustment === null ? 0 : data[0].bdAfterAdjustment)
};

// Page.picture10_1Click = function($event, widget, item, currentItemWidgets) {
//     Page.Variables.dbTblPbAttachment.invoke()
//     Page.Variables.varIOTmp.setValue("ioDesc", Page.Widgets.list3.selecteditem.pbIoDesc)
//     Page.Variables.varIOTmp.setValue("ioNumber", Page.Widgets.list3.selecteditem.pbIoNumber)
//     Page.Variables.varIOTmp.setValue("bhId", Page.Widgets.list3.selecteditem.pbIoId)
//     Page.Variables.varIOTmp.setValue("availableBudget", Page.Widgets.list3.selecteditem.pbAvailableBudget)
//     Page.Variables.varIOTmp.setValue("amount", Page.Widgets.list3.selecteditem.pbAmount)
// };

Page.search2Select = function($event, widget, selectedValue) {
    Page.Variables.varIOTmp.setValue("ioDesc", selectedValue.ioName)
    Page.Variables.varIOTmp.setValue("ioNumber", selectedValue.ioNumber)
    Page.Variables.varIOTmp.setValue("bhId", selectedValue.bhId)
    Page.Variables.vdbBudgetDetail.invoke({
        "filterFields": {
            "bhId": {
                "value": selectedValue.bhId
            }
        }
    })
};

Page.button6Click = function($event, widget) {
    $(Page.Widgets.attachmentWidget.nativeElement).find("input").click();
};

Page.FileServiceUploadFileonSuccess = function(variable, data) {
    Page.Variables.varListFile.addItem({
        pbAtId: 0,
        name: data[0].fileName,
        status: "new"
    })
};

Page.label54Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.FileServiceDownloadFile.invoke({
        "inputFields": {
            "file": Page.Widgets.varListFileList1.selecteditem.name
        }
    })
};

Page.picture25Click = function($event, widget, item, currentItemWidgets) {
    var dt = Page.Widgets.varListFileList1.selecteditem
    var filters = Page.Variables.varListFile.dataSet.find(x => x.name === dt.name)

    if (filters.status === "old") {
        Promise.resolve().then(() => {
            return Page.Variables.varListFileDelete.addItem({
                pbAtId: filters.pbAtId,
                name: filters.name,
                status: filters.status
            })
        }).then(() => {
            return Page.Variables.varListFile.dataSet = Page.Variables.varListFile.dataSet.filter(x => x !== filters)
        })
    } else if (filters.status === "new") {
        return Page.Variables.varListFile.dataSet = Page.Variables.varListFile.dataSet.filter(x => x !== filters)
    }
};

Page.button11Click = function($event, widget) {
    // validation
    var isValid = true
    if (Page.Widgets.text11.datavalue.length < 1) {
        Page.Variables.vmErrorMessage.setValue("baYear", "this field is required")
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("baYear", null)
    }

    if (Page.Widgets.date8.datavalue.length < 1) {
        Page.Variables.vmErrorMessage.setValue("startDate", "this field is required")
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("startDate", null)
    }

    if (Page.Widgets.date7.datavalue.length < 1) {
        Page.Variables.vmErrorMessage.setValue("endDate", "this field is required")
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("endDate", null)
    }

    if (Page.Widgets.text9.datavalue.length < 1) {
        Page.Variables.vmErrorMessage.setValue("projectName", "this field is required")
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("projectName", null)
    }

    if (Page.Widgets.text10.datavalue.length < 1) {
        Page.Variables.vmErrorMessage.setValue("amount", "this field is required")
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("amount", null)
    }

    if (Page.Widgets.search2.datavalue.length < 1) {
        Page.Variables.vmErrorMessage.setValue("ioNumber", "this field is required")
        isValid = false
    } else {
        Page.Variables.vmErrorMessage.setValue("ioNumber", null)
    }

    if (isValid) {
        var dataSet = {
            pbId: Page.Widgets.list3.selecteditem.pbId,
            pbDocumentNumber: Page.Widgets.list3.selecteditem.pbDocumentNumber,
            pbYear: Page.Widgets.text11.datavalue,
            pbStartDate: Page.Widgets.date8.datavalue,
            pbEndDate: Page.Widgets.date7.datavalue,
            pbProjectName: Page.Widgets.text9.datavalue,
            pbAmount: parseInt(Page.Widgets.text10.datavalue.replace(/\./g, '')),
            pbIoId: Page.Variables.varIOTmp.dataSet.bhId,
            pbIoNumber: Page.Variables.varIOTmp.dataSet.ioNumber,
            pbIoDesc: Page.Variables.varIOTmp.dataSet.ioDesc,
            pbAvailableBudget: Page.Variables.varIOTmp.dataSet.availableBudget,
            pbCreatedAt: Page.Widgets.list3.selecteditem.pbCreatedAt,
            pbCreatedBy: Page.Widgets.list3.selecteditem.pbCreatedBy,
            pbStatus: Page.Widgets.toggle1.datavalue
        }

        Promise.resolve().then(() => {
            return Page.Variables.dbTblTProposalBudget.updateRecord({
                row: dataSet
            })
        }).then(() => {
            return Page.mapDeleteAttachment(Page.Variables.varListFileDelete.dataSet)
        }).then(() => {
            var data = Page.Variables.varListFile.dataSet.filter(x => x.status === "new")
            return Page.mapCreateAttachment(data, Page.Widgets.list3.selecteditem.pbId)
        }).then(() => {
            Page.Variables.dbTblTProposalBudget.update();
            Page.Variables.dbTblTProposalBudgetAll.update();
            Page.Variables.dbTblPbAttachment.update();
            Page.Widgets.dialogItem.close()
        }).catch(function(err) {
            console.log(err)
        })
    }
};

Page.button8Click = function($event, widget) {
    Promise.resolve().then(() => {
        var data = Page.Variables.varListFile.dataSet.filter(x => x.status === "new")
        if (data.length > 0) {
            return Page.deleteFileUpload(data)
        } else {
            return Promise.resolve()
        }
    }).then(() => {
        Page.Widgets.dialogItem.close()
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
                "file": dt.name
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
        return Page.Variables.dbTblPbAttachment.deleteRecord({
            row: {
                "pbAtId": dt.pbAtId
            }
        })
    }).then(() => {
        return Page.Variables.FileServiceDeleteFile.invoke({
            "inputFields": {
                "file": dt.name
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
        return Page.Variables.dbTblPbAttachment.createRecord({
            row: {
                "pbId": id,
                "attachment": dt.name
            }
        })
    }).then(function(res) {
        return Page.mapCreateAttachment(data, id)
    }).catch(function(err) {
        console.log(err)
        return Page.mapCreateAttachment(data, id)
    })
}

// ========================= filter =========================
Page.picture11_1Click = function($event, widget) {
    Page.Variables.varFilterShow.dataSet.dataValue = true
    // Page.Widgets.text3.datavalue = ""
};
Page.picture12_1Click = function($event, widget) {
    Page.Variables.varFilterShow.dataSet.dataValue = false
};

Page.checkboxset2Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset2.datavalue.length === Page.Widgets.checkboxset2.dataset.length) {
        Page.Widgets.checkbox1.datavalue = true
    } else {
        Page.Widgets.checkbox1.datavalue = false
    }
};

Page.checkbox1Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset2.datavalue = Page.Widgets.checkboxset2.dataset
    } else {
        Page.Widgets.checkboxset2.datavalue = null
    }
};

Page.text5Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
};

Page.container36Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').addClass("clicked")
};

Page.checkbox3Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset3.datavalue = Page.Widgets.checkboxset3.dataset
    } else {
        Page.Widgets.checkboxset3.datavalue = null
    }
};
Page.checkboxset3Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset3.datavalue.length === Page.Widgets.checkboxset3.dataset.length) {
        Page.Widgets.checkbox3.datavalue = true
    } else {
        Page.Widgets.checkbox3.datavalue = false
    }
};

Page.container37_1Click = Page.container36Click
Page.text4Click = Page.text5Click

Page.text6Click = Page.text5Click
Page.container43_1Click = Page.container36Click

Page.checkbox4Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset4.datavalue = Page.Widgets.checkboxset4.dataset
    } else {
        Page.Widgets.checkboxset4.datavalue = null
    }
};
Page.checkboxset4Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset4.datavalue.length === Page.Widgets.checkboxset4.dataset.length) {
        Page.Widgets.checkbox4.datavalue = true
    } else {
        Page.Widgets.checkbox4.datavalue = false
    }
};
Page.text7Click = Page.text5Click
Page.container56_1Click = Page.container36Click

Page.checkboxset5Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset5.datavalue.length === Page.Widgets.checkboxset5.dataset.length) {
        Page.Widgets.checkbox5.datavalue = true
    } else {
        Page.Widgets.checkbox5.datavalue = false
    }
};
Page.checkbox5Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset5.datavalue = Page.Widgets.checkboxset5.dataset
    } else {
        Page.Widgets.checkboxset5.datavalue = null
    }
};
Page.text8Click = Page.text5Click
Page.container56_2Click = Page.container36Click

Page.checkboxset6Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset6.datavalue.length === Page.Widgets.checkboxset6.dataset.length) {
        Page.Widgets.checkbox6.datavalue = true
    } else {
        Page.Widgets.checkbox6.datavalue = false
    }
};
Page.checkbox6Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset6.datavalue = Page.Widgets.checkboxset6.dataset
    } else {
        Page.Widgets.checkboxset6.datavalue = null
    }
};

Page.checkboxset7Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset7.datavalue.length === Page.Widgets.checkboxset7.dataset.length) {
        Page.Widgets.checkbox7.datavalue = true
    } else {
        Page.Widgets.checkbox7.datavalue = false
    }
};
Page.checkbox7Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset7.datavalue = Page.Widgets.checkboxset7.dataset
    } else {
        Page.Widgets.checkboxset7.datavalue = null
    }
};
Page.text8_1Click = Page.text5Click
Page.container72_1Click = Page.container36Click

Page.searchValueKeyup = function($event, widget) {
    if ($event.key === "Enter") {
        Page.Variables.vendorListOrder.update()
    }
};

// Page.date5Change = function($event, widget, newVal, oldVal) {
//     Page.Variables.vendorListOrder.update()
// };

Page.list3Beforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.vendorListOrder.pagination.number * Page.Variables.vendorListOrder.pagination.size) + (i + 1);
    }
};
Page.detailPOClick = function($event, widget, item, currentItemWidgets) {
    console.log(item)
    console.log(Page.pageParams)

    Page.Actions.gotoDetailPO.invoke({
        data: {
            'company_code': Page.pageParams.company_code,
            'id_po': item.id
        }
    });
};

Page.DetailDeliveryOrderClick = function($event, widget, item, currentItemWidgets) {
    Page.Actions.goToDetailDO.invoke({
        data: {
            'company_code': Page.pageParams.company_code,
            'id_po': item.id
        }
    });
};

Page.BindingStatus = function(status) {
    if (status == 'PO Interfaced') {
        return "Not Yet Responded"
    } else if (status == 'Accepted') {
        return "In Progress"
    }
};

Page.vendorListOrderonSuccess = function(variable, data) {
    console.log(data)
};
