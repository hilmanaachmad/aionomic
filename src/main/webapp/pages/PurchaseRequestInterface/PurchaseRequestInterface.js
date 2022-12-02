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


    $('.off-autocomplete').attr("autocomplete", "off");

    console.log(App.Variables.loggedInUserData.dataSet)

    // if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
    //     if (App.Variables.loggedInUser.dataSet.roles.indexOf('PRS-004') !== -1) {

    //     } else {
    //         App.Actions.goToPage_Main.invoke()
    //     }
    // }
};

Page.buttonAddClick = function($event, widget) {
    App.Actions.goToPage_PurchaseRequestForm.invoke()
};

Page.picture10_1Click = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_PurchaseRequestFormEdit.invoke({
        data: {
            'prId': Page.Widgets.list2.selecteditem.prId
        }
    })
};

Page.picture25_1Click = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_PurchaseRequestFormView.invoke({
        data: {
            'prId': Page.Widgets.list2.selecteditem.prId
        }
    })
};

Page.picture26_1Click = function($event, widget, item, currentItemWidgets) {
    return Page.Variables.qPrChangeStatus.invoke({
        "inputFields": {
            'prId': Page.Widgets.list2.selecteditem.prId,
            "prModifiedAt": new Date().toISOString(),
            "prModifiedBy": App.Variables.loggedInUserData.dataSet.username,
            'prStatus': "Closed"
        }
    }).then(() => {
        Page.Variables.dbTblTPr.update()
        Page.Variables.dbTblPrAll.update()
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SUCCESS)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    })
};

Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.dbTblTPr.maxResults = newVal
    Page.Variables.dbTblTPr.update()
};

Page.selectSearchByChange = function($event, widget, newVal, oldVal) {
    return Promise.resolve().then(() => {
        return Page.Widgets.searchValue.datavalue = ""
    }).then(() => {
        return Page.Variables.dbTblTPr.update()
    }).then(() => {
        return Page.Variables.dbTblTPr.invoke()
    })
};

Page.searchValueKeyup = function($event, widget) {
    if ($event.key === "Enter") {
        Page.Variables.dbTblTPr.update()
    }
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.dbTblTPr.orderBy == field + " ASC") {
        Page.Variables.dbTblTPr.orderBy = field + " DESC"
    } else if (Page.Variables.dbTblTPr.orderBy == field + " DESC") {
        Page.Variables.dbTblTPr.orderBy = ""
    } else {
        Page.Variables.dbTblTPr.orderBy = field + " ASC"
    }
    Page.Variables.dbTblTPr.invoke()
}
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('tblMcompany.ctitle')
};
Page.container42Click = function($event, widget) {
    Page.toggleTableSort('tblMdepartment.departmentTitle')
};
Page.container43Click = function($event, widget) {
    Page.toggleTableSort('prRepUserName')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('prPurchaseBy')
};
Page.container45Click = function($event, widget) {
    Page.toggleTableSort('prPaymentMethod')
};
Page.container50Click = function($event, widget) {
    Page.toggleTableSort('prStatus')
};
Page.container110Click = function($event, widget) {
    // Page.toggleTableSort('tblTproposalBudget.pbAmount')
};
Page.container111Click = function($event, widget) {
    Page.toggleTableSort('prProject')
};
Page.container112Click = function($event, widget) {
    Page.toggleTableSort('prDate')
};
Page.container113Click = function($event, widget) {
    Page.toggleTableSort('tblMaccountType.accTitle')
};

Page.button3Click = function($event, widget) {
    Page.Variables.dbTblTPr.invoke()
};

Page.button4Click = function($event, widget) {
    Promise.resolve().then(() => {
        Page.Widgets.checkbox1.datavalue = null
        Page.Widgets.checkbox3.datavalue = null
        Page.Widgets.checkbox4.datavalue = null
        Page.Widgets.checkbox5.datavalue = null
        Page.Widgets.checkbox6.datavalue = null
        Page.Widgets.checkbox7.datavalue = null
        Page.Widgets.checkbox8.datavalue = null
        Page.Widgets.checkbox9.datavalue = null
        Page.Widgets.checkbox10.datavalue = null

        Page.Widgets.checkboxset2.datavalue = []
        Page.Widgets.checkboxset3.datavalue = []
        Page.Widgets.checkboxset4.datavalue = []
        Page.Widgets.checkboxset5.datavalue = []
        Page.Widgets.checkboxset7.datavalue = []
        Page.Widgets.checkboxset8.datavalue = []
        Page.Widgets.checkboxset9.datavalue = []
        Page.Widgets.checkboxset10.datavalue = []
        Page.Widgets.date1.datavalue = ""
        return Page.Widgets.checkboxset6.datavalue = []
    }).then(() => {
        return Page.Variables.dbTblTPr.invoke()
    }).then(() => {
        Page.Variables.dbTblTPr.invoke()
    })
};


Page.dbTblPrAllonBeforeDatasetReady = function(variable, data) {
    var arr = []
    var company = Page.Variables.varFilter.dataSet.company
    var department = Page.Variables.varFilter.dataSet.department
    var pepresentative = Page.Variables.varFilter.dataSet.pepresentative
    var purchaseBy = Page.Variables.varFilter.dataSet.purchaseBy
    var paymentMethod = Page.Variables.varFilter.dataSet.paymentMethod
    var projectName = Page.Variables.varFilter.dataSet.projectName
    var accountType = Page.Variables.varFilter.dataSet.accountType
    var status = Page.Variables.varFilter.dataSet.status

    data.forEach((dt) => {
        company.push(dt.tblMcompany.ctitle)
        department.push(dt.tblMdepartment.departmentTitle)
        pepresentative.push(dt.prRepUserName)
        purchaseBy.push(dt.prPurchaseBy)
        paymentMethod.push(dt.prPaymentMethod)
        projectName.push(dt.prProject)
        accountType.push(dt.tblMaccountType.accTitle)
        status.push(dt.prStatus)
        arr.push(dt)
    })

    company = [...new Set(company)];
    department = [...new Set(department)];
    pepresentative = [...new Set(pepresentative)];
    purchaseBy = [...new Set(purchaseBy)];
    paymentMethod = [...new Set(paymentMethod)];
    projectName = [...new Set(projectName)];
    accountType = [...new Set(accountType)];
    status = [...new Set(status)];

    Page.Variables.varFilter.dataSet.company = company
    Page.Variables.varFilter.dataSet.department = department
    Page.Variables.varFilter.dataSet.pepresentative = pepresentative.filter((x) => {
        return x !== null
    })
    Page.Variables.varFilter.dataSet.purchaseBy = purchaseBy
    Page.Variables.varFilter.dataSet.paymentMethod = paymentMethod
    Page.Variables.varFilter.dataSet.projectName = projectName.filter((x) => {
        return x !== null
    })
    Page.Variables.varFilter.dataSet.accountType = accountType
    Page.Variables.varFilter.dataSet.status = status

    return arr
};
// export excel
Page.button2Click = function($event, widget) {
    // intial
    var workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('Purchase Request');
    var xls_content = []
    var xls_header = [
        App.appLocale.LANG_COMPANY,
        App.appLocale.LANG_DEPARTMENT,
        App.appLocale.LANG_REPRESENTATIVE,
        App.appLocale.LANG_PURCHASE_BY,
        App.appLocale.LANG_PAYMENT_METHOD,
        App.appLocale.LANG_PROPOSAL_BUDGET_AMOUNT,
        App.appLocale.LANG_PROJECT_NAME,
        App.appLocale.LANG_DATE,
        App.appLocale.LANG_ACCOUNT_TYPE,
        App.appLocale.LANG_STATUS
    ]
    xls_content.push(xls_header)

    Promise.resolve().then(() => {
        Page.Widgets.spinner2.show = true
        return Page.Variables.dbTblTPr.invoke()
    }).then((data) => {
        data = data.data
        data.forEach(item => {
            var dataItem = [
                item.tblMcompany.cCode + " - " + item.tblMcompany.ctitle,
                item.tblMdepartment.departmentTitle,
                item.prRepUserName,
                item.prPurchaseBy,
                item.prPaymentMethod,
                item.tblTproposalBudget ? item.tblTproposalBudget.pbAmount : "",
                item.prProject,
                item.prDate.split("-")[2] + "-" + item.prDate.split("-")[1] + "-" + item.prDate.split("-")[0],
                item.tblMaccountType.accTitle,
                item.prStatus
            ]
            xls_content.push(dataItem)
        })

        sheet.addRows(xls_content)

        // styling
        let columnWidth = [42, 32, 30, 30, 32, 32, 20, 20, 20, 12];
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
            var filename = "Purchase Request";
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


// =====================================================
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
Page.text10Click = Page.text5Click
Page.container81Click = Page.container36Click

Page.text11Click = Page.text5Click
Page.container87Click = Page.container36Click

Page.checkbox8Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset8.datavalue = Page.Widgets.checkboxset8.dataset
    } else {
        Page.Widgets.checkboxset8.datavalue = null
    }
};

Page.checkboxset8Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset8.datavalue.length === Page.Widgets.checkboxset8.dataset.length) {
        Page.Widgets.checkbox8.datavalue = true
    } else {
        Page.Widgets.checkbox8.datavalue = false
    }
};
Page.text12Click = Page.text5Click
Page.container93Click = Page.container36Click

Page.checkbox9Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset9.datavalue = Page.Widgets.checkboxset9.dataset
    } else {
        Page.Widgets.checkboxset9.datavalue = null
    }
};

Page.checkboxset9Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset9.datavalue.length === Page.Widgets.checkboxset9.dataset.length) {
        Page.Widgets.checkbox9.datavalue = true
    } else {
        Page.Widgets.checkbox9.datavalue = false
    }
};
Page.checkboxset10Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset10.datavalue.length === Page.Widgets.checkboxset10.dataset.length) {
        Page.Widgets.checkbox10.datavalue = true
    } else {
        Page.Widgets.checkbox10.datavalue = false
    }
};

Page.checkbox10Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset10.datavalue = Page.Widgets.checkboxset10.dataset
    } else {
        Page.Widgets.checkboxset10.datavalue = null
    }
};

Page.text8_1Click = Page.text5Click
Page.container65Click = Page.container36Click

Page.button5Click = function($event, widget) {
    Page.Widgets.spinner2.show = true
    // intial
    var workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('Purchase Request');
    var xls_content = []
    var xls_header = [
        App.appLocale.LANG_COMPANY,
        App.appLocale.LANG_DEPARTMENT,
        App.appLocale.LANG_REPRESENTATIVE,
        App.appLocale.LANG_PURCHASE_BY,
        App.appLocale.LANG_PAYMENT_METHOD,
        App.appLocale.LANG_PROPOSAL_BUDGET_AMOUNT,
        App.appLocale.LANG_PROJECT_NAME,
        App.appLocale.LANG_DATE,
        App.appLocale.LANG_ACCOUNT_TYPE,
        App.appLocale.LANG_STATUS
    ]
    xls_content.push(xls_header)

    Page.App.getVDBAllRecords(Page.Variables.dbTblPrAll).then(function(res) {
        res.forEach(item => {
            var dataItem = [
                item.tblMcompany.cCode + " - " + item.tblMcompany.ctitle,
                item.tblMdepartment.departmentTitle,
                item.prRepUserName,
                item.prPurchaseBy,
                item.prPaymentMethod,
                item.tblTproposalBudget ? item.tblTproposalBudget.pbAmount : "",
                item.prProject,
                item.prDate.split("-")[2] + "-" + item.prDate.split("-")[1] + "-" + item.prDate.split("-")[0],
                item.tblMaccountType.accTitle,
                item.prStatus
            ]
            xls_content.push(dataItem)
        })

        sheet.addRows(xls_content)

        // styling
        let columnWidth = [42, 32, 30, 30, 32, 32, 20, 20, 20, 12];
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
            var filename = "Purchase Request";
            var blob = new Blob([datas], {
                type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            });

            let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
            saveAs(blob, filename + currentTime + ".xlsx");
        })

        Page.Widgets.spinner2.show = false
    })
};

Page.dialogInterfaceOk = function($event, widget) {
    Page.Widgets.dialogInterface.close()
    Page.Widgets.spinnerInterface.show = true
    try {
        Page.Variables.api_PRPost.invoke({
            inputFields: {
                RequestBody: {
                    id_pr: Page.Widgets.list2.selecteditem.prId
                }
            }
        }).then((data) => {
            Page.Widgets.spinnerInterface.show = false
            var result = JSON.parse(data.body)
            if (result.status === true) {
                Page.Variables.dbTblTPr.invoke()
                App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SUCCESS)
                App.Actions.appNotification.setToasterClass("success")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
            } else {
                Page.Variables.dbTblTPr.invoke()
                if (!Array.isArray(result.data.message)) {
                    App.Actions.appNotification.setMessage("SAP :" + result.data.message)
                    App.Actions.appNotification.setToasterClass("warning")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                } else {
                    App.Actions.appNotification.setMessage("SAP :" + result.data.message[0].MESSAGE)
                    App.Actions.appNotification.setToasterClass("warning")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                }
            }
        })
    } catch (err) {
        Page.Widgets.spinnerInterface.show = false
        App.Actions.appNotification.setMessage("Error Interface, please check console")
        console.log(err)
        App.Actions.appNotification.setToasterClass("Alert")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    }


};
Page.dialogDeleteOk = function($event, widget) {
    Page.Widgets.dialogDelete.close()
    Page.Widgets.spinnerInterface.show = true
    Page.Variables.api_PRDelete.invoke({
        inputFields: {
            RequestBody: {
                type: 'Delete',
                id_pr: Page.Widgets.list2.selecteditem.prId
            }
        }
    }).then((data) => {
        Page.Widgets.spinnerInterface.show = false
        var result = JSON.parse(data.body)
        if (result.status === true) {
            Page.Variables.dbTblTPr.invoke()
            App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SUCCESS)
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
        } else {
            Page.Variables.dbTblTPr.invoke()
            if (!Array.isArray(result.data.message)) {
                App.Actions.appNotification.setMessage("SAP :" + result.data.message)
                App.Actions.appNotification.setToasterClass("warning")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
            } else {
                App.Actions.appNotification.setMessage("SAP :" + result.data.message[0].MESSAGE)
                App.Actions.appNotification.setToasterClass("warning")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
            }
        }
    })
};

Page.dialogForceCloseOk = function($event, widget) {
    Page.Widgets.dialogForceClose.close()
    Page.Widgets.spinnerInterface.show = true
    Page.Variables.api_PRDelete.invoke({
        inputFields: {
            RequestBody: {
                type: 'Force Close',
                id_pr: Page.Widgets.list2.selecteditem.prId
            }
        }
    }).then((data) => {
        Page.Widgets.spinnerInterface.show = false
        var result = JSON.parse(data.body)
        if (result.status === true) {
            Page.Variables.dbTblTPr.invoke()
            App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SUCCESS)
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
        } else {
            Page.Variables.dbTblTPr.invoke()
            if (!Array.isArray(result.data.message)) {
                App.Actions.appNotification.setMessage("SAP :" + result.data.message)
                App.Actions.appNotification.setToasterClass("warning")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
            } else {
                App.Actions.appNotification.setMessage("SAP :" + result.data.message[0].MESSAGE)
                App.Actions.appNotification.setToasterClass("warning")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
            }
        }
    })
};
Page.showErrorSapClick = function($event, widget, item, currentItemWidgets) {
    var prId = Page.Widgets.list2.selecteditem.prId
    var DBSAPError = Page.Variables.DBSAPError
    DBSAPError.dataSet = []
    DBSAPError.setFilter({
        "type": 'PR',
        "idHeader": prId
    });
    DBSAPError.listRecords();
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