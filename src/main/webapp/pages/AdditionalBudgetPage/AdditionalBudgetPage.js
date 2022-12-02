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
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('BUD-004') !== -1) {
            // Yearpicker format
            var a = new Yearpicker(Page.Widgets.text3.inputEl.nativeElement, {
                onChange: function(year) {
                    Page.Widgets.text3.datavalue = year
                }
            })

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
                Page.Widgets.text3.datavalue = ""
                return Page.Variables.varDepUser.dataSet
            }).then(() => {
                return Page.Variables.dbTblTBudgetAdditional.invoke()
            })
        } else {
            App.Actions.goToPage_Main.invoke()
        }
    }
};


Page.button1Click = function($event, widget) {
    App.Actions.goToPage_FormAdditional.clearData()
    App.Actions.goToPage_FormAdditional.invoke()
};

Page.picture20Click = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_FormAdditional.invoke({
        data: {
            'baId': Page.Widgets.list3.selecteditem.baId
        }
    })
};
Page.picture10_1Click = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_FormAdditional.invoke({
        data: {
            'baId': Page.Widgets.list3.selecteditem.baId
        }
    })
};

Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.dbTblTBudgetAdditional.maxResults = newVal
    Page.Variables.dbTblTBudgetAdditional.update()
};

Page.selectSearchByChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.searchValue.datavalue = ""
    Page.Variables.dbTblTBudgetAdditional.update()
};

// export excel
Page.button2Click = function($event, widget) {
    // intial
    var workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('Additional Budget');
    var xls_content = []
    var xls_header = [
        App.appLocale.LANG_UNBUDGETED_CATEGORY,
        App.appLocale.LANG_ACCUMULATED_UNBUDGET,
        App.appLocale.LANG_ADDITIONAL_AMOUNT,
        App.appLocale.LANG_BUDGET_YEAR,
        App.appLocale.LANG_IO_NUMBER,
        App.appLocale.LANG_STATUS
    ]
    xls_content.push(xls_header)

    Promise.resolve().then(() => {
        Page.Widgets.spinner2.show = true
        return Page.Variables.dbTblTBudgetAdditional.invoke()
    }).then((data) => {
        data = data.data
        data.forEach(item => {
            var dataItem = [
                item.tblMunbudgetCat.ubCatTitle,
                item.baUnbudget,
                item.baAmount,
                item.baYear,
                item.ioNumber,
                item.baRemarks
            ]
            xls_content.push(dataItem)
        })

        sheet.addRows(xls_content)

        // styling
        let columnWidth = [32, 32, 32, 32, 32, 12];
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
            var filename = "Additional Budget ";
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
    if (Page.Variables.dbTblTBudgetAdditional.orderBy == field + " ASC") {
        Page.Variables.dbTblTBudgetAdditional.orderBy = field + " DESC"
    } else if (Page.Variables.dbTblTBudgetAdditional.orderBy == field + " DESC") {
        Page.Variables.dbTblTBudgetAdditional.orderBy = ""
    } else {
        Page.Variables.dbTblTBudgetAdditional.orderBy = field + " ASC"
    }
    Page.Variables.dbTblTBudgetAdditional.invoke()
}
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('tblMunbudgetCat.ubCatTitle')
};
Page.container42Click = function($event, widget) {
    Page.toggleTableSort('baUnbudget')
};
Page.container43Click = function($event, widget) {
    Page.toggleTableSort('baAmount')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('baYear')
};
Page.container45Click = function($event, widget) {
    Page.toggleTableSort('ioNumber')
};

Page.container50Click = function($event, widget) {
    Page.toggleTableSort('baRemarks')
};

Page.dialogDeleteOk = function($event, widget) {
    Promise.resolve().then(() => {
        return Page.Variables.qdeleteAdditionalHistory.invoke({
            "inputFields": {
                "baId": Page.Widgets.list3.selecteditem.baId
            }
        })
    }).then(() => {
        if (Page.Variables.dbTblTAdditionalAttachment.dataSet.length > 0) {
            return Page.deleteFileUpload(Page.Variables.dbTblTAdditionalAttachment.dataSet)
        } else {
            return Promise.resolve()
        }
    }).then(() => {
        return Page.Variables.qdeleteAttionalAttachment.invoke({
            "inputFields": {
                "baId": Page.Widgets.list3.selecteditem.baId
            }
        })
    }).then(() => {
        return Page.Variables.dbTblTBudgetAdditional.deleteRecord({
            row: {
                "baId": Page.Widgets.list3.selecteditem.baId
            }
        })
    }).then(() => {
        Page.Variables.dbTblTBudgetAdditional.update()
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.Widgets.dialogDelete.close()
    }).catch((err) => {
        console.log("err ", err)
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

Page.button3Click = function($event, widget) {
    Page.Variables.dbTblTBudgetAdditional.invoke()
};

Page.button4Click = function($event, widget) {
    Promise.resolve().then(() => {
        Page.Widgets.checkbox1.datavalue = null
        Page.Widgets.checkbox3.datavalue = null
        Page.Widgets.checkbox4.datavalue = null
        Page.Widgets.checkbox5.datavalue = null
        Page.Widgets.checkbox6.datavalue = null

        Page.Widgets.text3.datavalue = ""
        Page.Widgets.checkboxset2.datavalue = []
        Page.Widgets.checkboxset3.datavalue = []
        Page.Widgets.checkboxset4.datavalue = []
        Page.Widgets.checkboxset5.datavalue = []
        return Page.Widgets.checkboxset6.datavalue = []
    }).then(() => {
        return Page.Variables.dbTblTBudgetAdditional.invoke()
    }).then(() => {
        Page.Variables.dbTblTBudgetAdditional.invoke()
    })
};


Page.dbTblTAdditionalBudgetsAllonBeforeDatasetReady = function(variable, data) {

    var arr = []
    var unbudgetedCategory = Page.Variables.varFilter.dataSet.unbudgetedCategory
    var iONumber = Page.Variables.varFilter.dataSet.iONumber
    var status = Page.Variables.varFilter.dataSet.status

    data.forEach((dt) => {
        unbudgetedCategory.push(dt.tblMunbudgetCat.ubCatTitle)
        iONumber.push(dt.ioNumber)
        status.push(dt.baRemarks)
        arr.push(dt)
    })

    unbudgetedCategory = [...new Set(unbudgetedCategory)];
    iONumber = [...new Set(iONumber)];
    status = [...new Set(status)];

    Page.Variables.varFilter.dataSet.unbudgetedCategory = unbudgetedCategory
    Page.Variables.varFilter.dataSet.iONumber = iONumber
    Page.Variables.varFilter.dataSet.status = status

    return arr
};
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

Page.text4Click = Page.text5Click
Page.container37_1Click = Page.container36Click

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

Page.searchValueKeyup = function($event, widget) {
    if ($event.key === "Enter") {
        Page.Variables.dbTblTBudgetAdditional.update()
    }
};
Page.button6Click = function($event, widget) {
    Page.Widgets.spinner2.show = true

    Page.App.getVDBAllRecords(Page.Variables.dbTblTBudgetAdditionalAll).then(function(res) {
        // intial
        var workbook = new ExcelJS.Workbook();
        var sheet = workbook.addWorksheet('Additional Budget');
        var xls_content = []
        var xls_header = [
            App.appLocale.LANG_UNBUDGETED_CATEGORY,
            App.appLocale.LANG_ACCUMULATED_UNBUDGET,
            App.appLocale.LANG_ADDITIONAL_AMOUNT,
            App.appLocale.LANG_BUDGET_YEAR,
            App.appLocale.LANG_IO_NUMBER,
            App.appLocale.LANG_STATUS
        ]
        xls_content.push(xls_header)

        res.forEach(item => {
            var dataItem = [
                item.tblMunbudgetCat.ubCatTitle,
                item.baUnbudget,
                item.baAmount,
                item.baYear,
                item.ioNumber,
                item.baRemarks
            ]
            xls_content.push(dataItem)
        })

        sheet.addRows(xls_content)

        // styling
        let columnWidth = [32, 32, 32, 32, 32, 12];
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
            var filename = "Additional Budget ";
            var blob = new Blob([datas], {
                type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            });

            let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
            saveAs(blob, filename + currentTime + ".xlsx");
        })

        Page.Widgets.spinner2.show = false
    })

};

Page.DBUserRoleonSuccess = function(variable, data) {
    Page.Variables.MODELConditionPurchase.dataSet = []
    Page.Variables.MODELConditionPurchaseAdmin.dataSet = []
    // console.log('role', data)
    for (var i = 0; i < data.length; i++) {
        if (data[i].roleId == 1) {
            Page.Variables.MODELConditionPurchaseAdmin.dataSet.push(data[i].roleId)
        }
        if (data[i].roleId == 2) {
            Page.Variables.MODELConditionPurchase.dataSet.push(data[i].roleId)
        }
    }
};