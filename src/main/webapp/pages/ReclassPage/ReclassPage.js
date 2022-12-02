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
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('BUD-003') !== -1) {

        } else {
            App.Actions.goToPage_Main.invoke()
        }
    }

    setTimeout(function() {
        Page.Widgets.text3.datavalue = ""
    }, 1000);

    // Yearpicker format
    var a = new Yearpicker(Page.Widgets.text3.inputEl.nativeElement, {
        onChange: function(year) {
            Page.Widgets.text3.datavalue = year
        }
    })

    // event listener on click outside filter
    Page.$page[0].addEventListener("click", function() {
        $('.aio-ptp-dropdown:not(.clicked)').removeClass("active")
        $('.aio-ptp-dropdown').removeClass("clicked")
    })
};


Page.button1Click = function($event, widget) {
    App.Actions.goToPage_FormReclass.clearData()
    App.Actions.goToPage_FormReclass.invoke()
};

Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.dbTblTBudgetReclass.maxResults = newVal
    Page.Variables.dbTblTBudgetReclass.update()
};

Page.selectSearchByChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.searchValue.datavalue = ""
    Page.Variables.dbTblTBudgetReclass.update()
};

Page.picture20Click = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_FormReclass.invoke({
        data: {
            'bdRcId': Page.Widgets.list3.selecteditem.bdRcId
        }
    })
};
Page.picture10_1Click = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_FormReclass.invoke({
        data: {
            'bdRcId': Page.Widgets.list3.selecteditem.bdRcId
        }
    })
};

// export excel all
Page.button6Click = function($event, widget) {
    Page.Widgets.spinner2.show = true

    Page.App.getVDBAllRecords(Page.Variables.dbTblTBudgetReclassAll).then(function(res) {
        // var data = JSON.parse(res.body)
        // console.log("export", res)
        return Page.exportExcel(res)
    }).then(function() {
        Page.Widgets.spinner2.show = false
    })
};
// export excel by filter
Page.button2Click = function($event, widget) {
    return Page.exportExcel(Page.Variables.dbTblTBudgetReclass.dataSet)
};
Page.exportExcel = function(data) {
    // intial
    var workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('Reclass');
    var xls_content = []
    var xls_header = [
        App.appLocale.LABEL_RECLASSIFICATION,
        App.appLocale.LABEL_AMOUNT,
        App.appLocale.LABEL_BUDGET_YEAR,
        App.appLocale.LABEL_ORIGIN_IO,
        App.appLocale.LABEL_DESTINATION,
        App.appLocale.LABEL_STATUS
    ]
    xls_content.push(xls_header)

    // var data = Page.Variables.dbTblTBudgetReclass.dataSet
    data.forEach(item => {
        var dataItem = [
            item.tblMreclassCategory.rcatTitle,
            item.reclassAmount,
            item.budgetYear,
            item.ioOriginNumber,
            item.ioDestNumber,
            item.bdRcStatus
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
        var filename = "Reclass ";
        var blob = new Blob([datas], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        });

        let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
        saveAs(blob, filename + currentTime + ".xlsx");
    })
}

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.dbTblTBudgetReclass.orderBy == field + " ASC") {
        Page.Variables.dbTblTBudgetReclass.orderBy = field + " DESC"
    } else if (Page.Variables.dbTblTBudgetReclass.orderBy == field + " DESC") {
        Page.Variables.dbTblTBudgetReclass.orderBy = ""
    } else {
        Page.Variables.dbTblTBudgetReclass.orderBy = field + " ASC"
    }
    Page.Variables.dbTblTBudgetReclass.invoke()
}
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('tblMreclassCategory.rcatTitle')
};
Page.container42Click = function($event, widget) {
    Page.toggleTableSort('reclassAmount')
};
Page.container43Click = function($event, widget) {
    Page.toggleTableSort('budgetYear')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('ioOriginNumber')
};
Page.container45Click = function($event, widget) {
    Page.toggleTableSort('ioDestNumber')
};

Page.container50Click = function($event, widget) {
    Page.toggleTableSort('bdRcStatus')
};

Page.dialogDeleteOk = function($event, widget) {
    Promise.resolve().then(() => {
        var dataItem = Page.Widgets.list3.selecteditem
        dataItem.bdRcStatus = 'deleted'
        return Page.Variables.dbTblTBudgetReclass.updateRecord({
            row: dataItem
        })
    }).then(() => {
        Page.Variables.dbTblTBudgetReclass.update()
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SUCCESS)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.Widgets.dialogDelete.close()
    }).catch((err) => {
        console.log("err ", err)
    })
};

Page.button3Click = function($event, widget) {
    Page.Variables.dbTblTBudgetReclass.invoke()
};

Page.button4Click = function($event, widget) {
    Promise.resolve().then(() => {
        Page.Widgets.text3.datavalue = ""
        Page.Widgets.checkboxset2.datavalue = []
        Page.Widgets.checkboxset3.datavalue = []
        Page.Widgets.checkboxset4.datavalue = []
        Page.Widgets.checkboxset5.datavalue = []
        return Page.Widgets.checkboxset6.datavalue = []
    }).then(() => {
        return Page.Variables.dbTblTBudgetReclass.invoke()
    }).then(() => {
        Page.Variables.dbTblTBudgetReclass.invoke()
    })
};

Page.list3Beforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.dbTblTBudgetReclass.pagination.number * Page.Variables.dbTblTBudgetReclass.pagination.size) + (i + 1);
    }
};

// Page.dbTblTBudgetReclassonBeforeDatasetReady = function(variable, data) {
//     var pages = 0
//     if (document.getElementsByClassName("pagecount disabled")[0] !== undefined) {
//         var pagesTmp = document.getElementsByClassName("pagecount disabled")[0].childNodes[0].childNodes[0].value
//         pages = pagesTmp === "1" ? 0 : ((pagesTmp - 1) * 10)
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

Page.dbTblTBudgetReclassAllonBeforeDatasetReady = function(variable, data) {
    var arr = []
    var category = Page.Variables.varFilter.dataSet.category
    var originIo = Page.Variables.varFilter.dataSet.originIo
    var destinationIo = Page.Variables.varFilter.dataSet.destinationIo
    var status = Page.Variables.varFilter.dataSet.status

    data.forEach((dt) => {
        if (dt.tblMreclassCategory.rcatTitle) {
            category.push(dt.tblMreclassCategory.rcatTitle)
        }
        if (dt.ioOriginNumber) {
            originIo.push(dt.ioOriginNumber)
        }
        if (dt.ioDestNumber) {
            destinationIo.push(dt.ioDestNumber)
        }
        if (dt.bdRcStatus) {
            status.push(dt.bdRcStatus)
        }
        arr.push(dt)
    })

    category = [...new Set(category)];
    originIo = [...new Set(originIo)];
    destinationIo = [...new Set(destinationIo)];
    status = [...new Set(status)];

    Page.Variables.varFilter.dataSet.category = category
    Page.Variables.varFilter.dataSet.originIo = originIo
    Page.Variables.varFilter.dataSet.destinationIo = destinationIo
    Page.Variables.varFilter.dataSet.status = status

    return arr
};
Page.picture11_1Click = function($event, widget) {
    Page.Variables.varFilterShow.dataSet.dataValue = true
    Page.Widgets.text3.datavalue = ""
};
Page.picture12_1Click = function($event, widget) {
    Page.Variables.varFilterShow.dataSet.dataValue = false
};

Page.text5Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
};
Page.container37_1Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').addClass("clicked")
};

Page.text4Click = Page.text5Click
Page.text8Click = Page.text5Click
Page.text6Click = Page.text5Click
Page.text7Click = Page.text5Click
Page.container36Click = Page.container37_1Click
Page.container43_1Click = Page.container37_1Click
Page.container56_1Click = Page.container37_1Click
Page.container56_2Click = Page.container37_1Click


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

Page.searchValueKeyup = function($event, widget) {
    if ($event.key === "Enter") {
        Page.Variables.dbTblTBudgetReclass.update()
    }
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