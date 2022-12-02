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

    // event listener on click outside filter
    Page.$page[0].addEventListener("click", function() {
        $('.aio-ptp-dropdown:not(.clicked)').removeClass("active")
        $('.aio-ptp-dropdown').removeClass("clicked")
    })

    // department handle
    var userDepartment = App.Variables.loggedInUserData.dataSet.user_department
    if (userDepartment.length > 0) {
        var departments = []

        userDepartment.forEach(dep => {
            departments.push(dep.departmentId)
        })

        Page.Variables.varDepUser.setData(departments)
    }

    return Promise.resolve().then(() => {
        // Yearpicker format
        var a = new Yearpicker(Page.Widgets.text3.inputEl.nativeElement, {
            onChange: function(year) {
                Page.Widgets.text3.datavalue = year
            }
        })
    }).then(() => {
        Page.Widgets.text3.datavalue = ""

        return Promise.resolve()
    })

    //authorization
    // if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
    //     if (App.Variables.loggedInUser.dataSet.roles.indexOf('BUD-004') !== -1) {
    //         Page.Widgets.text3.datavalue = ""
    //     } else {
    //         App.Actions.goToPage_Main.invoke()
    //     }
    // }
};

Page.buttonAddClick = function($event, widget) {
    Page.Widgets.list3.selecteditem = null
    Page.Variables.modelProposalBudget.clearData()
    Page.Variables.varListFile.clearData()
    Page.Widgets.dialogItem.open()
};

Page.buttonViewClick = function($event, widget, item, currentItemWidgets) {
    // Page.Widgets.dialogItem.open()
    // Page.Variables.varLoading.dataSet.loadingSubmit = true

    // return Promise.resolve().then(() => {
    //     Page.Variables.modelProposalBudget.setValue("pbId", item.pbId)
    //     return Page.Variables.dbTblPbAttachment.invoke()
    // }).then(() => {
    //     Page.Variables.varLoading.dataSet.loadingSubmit = false
    // })


};

// select IO Number
Page.search2Select = function($event, widget, selectedValue) {
    // Page.Widgets.reqlab5.show = false;
    Page.Variables.modelProposalBudget.setValue("pbIoId", selectedValue.bhId)

    Page.Variables.vdbBudgetDetail.listRecords({
        "filterFields": {
            "bhId": {
                "value": selectedValue.bhId
            }
        }
    }).then(res => {
        if (!Page.Widgets.search2.datavalue) {
            return
        }

        Page.Variables.modelProposalBudget.setValue("pbAvailableBudget", Page.Variables.vdbBudgetDetail.dataSet[0].bdAfterAdjustment)
        return Promise.resolve()
    })
};

// submit form
Page.button11Click = function($event, widget) {
    Page.Variables.vmErrorMessage.clearData()

    Page.Variables.varLoading.dataSet.loadingSubmit = true
    var data = Page.Variables.modelProposalBudget.dataSet
    data.pbCreatedAt = new Date().toISOString()
    data.pbCreatedBy = App.Variables.loggedInUser.dataSet.name
    data.pbAmount = data.pbAmount.replace(/\./g, '')

    console.log("form data", data)

    // field mandatory validation
    if (!Page.fieldMandatoryCheck([
            "pbYear",
            "pbStartDate",
            "pbEndDate",
            "pbProjectName",
            "pbAmount",
            "pbIoNumber",
        ])) {
        Page.Variables.varLoading.dataSet.loadingSubmit = true
        return
    }

    Page.Variables.dbTblTProposalBudget.createRecord({
        row: data
    }).then(res => {
        var documentId = "8000-" + res.pbId

        if (res.pbId < 10) {
            documentId = "8000-0" + res.pbId
        }

        res.pbDocumentNumber = documentId
        Page.Variables.modelProposalBudget.dataSet = res

        return Page.Variables.dbTblTProposalBudget.updateRecord({
            row: res
        }).catch(error => {
            console.log(error);
            Variables.varLoading.dataSet.loadingSubmit = false;
        })
    }).then(() => {
        return Page.mapDeleteAttachment(Page.Variables.varListFileDelete.dataSet)
    }).then(() => {
        var data = Page.Variables.varListFile.dataSet.filter(x => x.status === "new")
        return Page.mapCreateAttachment(data, Page.Variables.modelProposalBudget.dataSet.pbId)
    }).then(() => {
        // message
        App.Actions.appNotification.setMessage("Data Saved")
        // type: success, info, warning, danger
        App.Actions.appNotification.setToasterClass("success")
        // delayed
        App.Actions.appNotification.setToasterDuration(5000)
        // invoke
        App.Actions.appNotification.invoke()

        Page.Variables.varLoading.dataSet.loadingSubmit = false
        Page.Variables.dbTblTProposalBudget.update();
        Page.Widgets.dialogItem.close()
    }).catch(error => {
        console.log(error)
    })
};

Page.fieldMandatoryCheck = function(fields = []) {
    var isValid = true

    for (var i = 0; i < fields.length; i++) {
        if (!Page.Variables.modelProposalBudget.getValue(fields[i])) {
            isValid = false
            Page.Variables.vmErrorMessage.setValue(fields[i], "this field is required")
        }
    }

    return isValid
};

Page.dialogItemOpened = function($event, widget) {
    if (!Page.Widgets.list3.selecteditem.pbId) {
        // Yearpicker format
        var a = new Yearpicker(Page.Widgets.textYear.inputEl.nativeElement, {
            onChange: function(year) {
                Page.Widgets.textYear.datavalue = year

                // clear io number data
                Page.Widgets.search2.datavalue = null
                Page.Variables.modelProposalBudget.setValue("pbAvailableBudget", "")
            }
        })
    }

};

Page.textAmountChange = function($event, widget) {
    // Page.Widgets.textAmount.datavalue = Page.Widgets.textAmount.datavalue.replace(/\D/g, '');
    Page.Widgets.textAmount.datavalue = App.formatCurrency(Page.Widgets.textAmount.datavalue.replace(/\D/g, ''));
};

Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.dbTblTProposalBudget.maxResults = newVal
    Page.Variables.dbTblTProposalBudget.update()
};

Page.selectSearchByChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.searchValue.datavalue = ""
    Page.Widgets.date5.datavalue = ""
    Page.Widgets.date6.datavalue = ""
    Page.Variables.dbTblTProposalBudget.update()
};

// export excel
Page.button2Click = function($event, widget) {
    // intial
    var workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('Propsal Budget');
    var xls_content = []
    var xls_header = [
        App.appLocale.LABEL_DOCUMENT_NUMBER,
        App.appLocale.LABEL_PROJECT_NAME,
        App.appLocale.LABEL_BUDGET_YEAR,
        App.appLocale.LABEL_IO_NUMBER,
        App.appLocale.LABEL_IO_DESCRIPTION,
        App.appLocale.LABEL_AMOUNT_PROPOSAL_BUDGET,
        App.appLocale.LABEL_START_DATE,
        App.appLocale.LABEL_END_DATE,
        App.appLocale.LABEL_CREATED_DATE,
        App.appLocale.LABEL_STATUS
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
    if (Page.Variables.dbTblTProposalBudget.orderBy == field + " ASC") {
        Page.Variables.dbTblTProposalBudget.orderBy = field + " DESC"
    } else if (Page.Variables.dbTblTProposalBudget.orderBy == field + " DESC") {
        Page.Variables.dbTblTProposalBudget.orderBy = ""
    } else {
        Page.Variables.dbTblTProposalBudget.orderBy = field + " ASC"
    }
    Page.Variables.dbTblTProposalBudget.invoke()
}
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('pbDocumentNumber')
};
Page.container42Click = function($event, widget) {
    Page.toggleTableSort('pbProjectName')
};
Page.container43Click = function($event, widget) {
    Page.toggleTableSort('pbYear')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('pbIoNumber')
};
Page.container45Click = function($event, widget) {
    Page.toggleTableSort('pbIoDesc')
};

Page.container50Click = function($event, widget) {
    Page.toggleTableSort('pbAmount')
};

Page.container65Click = function($event, widget) {
    Page.toggleTableSort('pbStartDate')
};

Page.container66Click = function($event, widget) {
    Page.toggleTableSort('pbEndDate')
};

Page.container67Click = function($event, widget) {
    Page.toggleTableSort('pbCreatedAt')
};

Page.container68Click = function($event, widget) {
    Page.toggleTableSort('pbStatus')
};

Page.dialogDeleteOk = function($event, widget) {
    Promise.resolve().then(() => {
        return Page.Variables.qdeleteAdditionalHistory.invoke({
            "inputFields": {
                "baId": Page.Widgets.list3.selecteditem.baId
            }
        })
    }).then(() => {
        return Page.Variables.dbTblTProposalBudget.deleteRecord({
            row: {
                "baId": Page.Widgets.list3.selecteditem.baId
            }
        })
    }).then(() => {
        if (Page.Widgets.list3.selecteditem.baAttachment !== null) {
            return Page.Variables.FileServiceDeleteFile.invoke()
        } else {
            return Promise.resolve()
        }
    }).then(() => {
        Page.Variables.dbTblTProposalBudget.update()
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SUCCESS)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.Widgets.dialogDelete.close()
    }).catch((err) => {
        console.log("err ", err)
    })
};

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

Page.list3Beforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.dbTblTProposalBudget.pagination.number * Page.Variables.dbTblTProposalBudget.pagination.size) + (i + 1);
    }
};
// Page.dbTblTProposalBudgetonBeforeDatasetReady = function(variable, data) {
//     var pages = 0
//     if (document.getElementsByClassName('pagination-page page-item active')[0] !== undefined) {
//         var pagesTmp = document.getElementsByClassName("pagination-page page-item active")[0].childNodes[0].innerHTML
//         pages = pagesTmp === 1 ? 0 : ((pagesTmp - 1) * 10)
//     }

//     var arr = []
//     var no = 1
//     data.forEach((dt) => {
//         dt.no = no + pages
//         no += 1
//         arr.push(dt)
//     })
//     return arr
// };

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

// ========================= filter =========================
Page.defaultCloseFilter = function() {
    Page.Widgets.container41_1.show = false
    Page.Widgets.container47.show = false
    Page.Widgets.container51_1.show = false
    Page.Widgets.container55_1.show = false
    Page.Widgets.container60.show = false
    Page.Widgets.container77.show = false
};
Page.container36Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').addClass("clicked")
};
Page.text5Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
};
Page.container37_1Click = Page.container36Click
Page.container43_1Click = Page.container36Click
Page.container56_1Click = Page.container36Click
Page.container72_1Click = Page.container36Click
Page.container56_2Click = Page.container36Click
Page.text4Click = Page.text5Click
Page.text6Click = Page.text5Click
Page.text8_1Click = Page.text5Click
Page.text8Click = Page.text5Click
Page.text7Click = Page.text5Click

Page.picture11_1Click = function($event, widget) {
    Page.Variables.varFilterShow.dataSet.dataValue = true
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

Page.searchValueKeyup = function($event, widget) {
    if ($event.key === "Enter") {
        Page.Variables.dbTblTProposalBudget.update()
    }
};

/*
    =============== Multi files handle ==============
*/

Page.button6Click = function($event, widget) {
    $(Page.Widgets.fileupload1.nativeElement).find("input").click();
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

Page.date5Change = function($event, widget, newVal, oldVal) {
    Page.Variables.dbTblTProposalBudget.update()
};
Page.date6Change = function($event, widget, newVal, oldVal) {
    Page.Variables.dbTblTProposalBudget.update()
};
Page.date8Change = function($event, widget, newVal, oldVal) {
    // Page.Widgets.reqlab1.show = false;
};
Page.date7Change = function($event, widget, newVal, oldVal) {
    // Page.Widgets.reqlab2.show = false;
};
Page.fileupload1Select = function($event, widget, selectedFiles) {
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