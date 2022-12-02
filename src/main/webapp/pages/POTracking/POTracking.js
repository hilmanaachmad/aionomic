/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
var year_value, start_date, end_date, start_date_eta, end_date_eta
Page.onReady = function() {
    setTimeout(() => {
        $('.yearpicker').yearpicker()
        $('.yearpicker').change(function() {
            year_value = $(this).val()
            console.log(year_value)
            Page.Variables.ModelFilter.setData({
                "year": year_value
            })
        })
        $('.yearpicker').val(new Date().getFullYear())
        Page.Variables.ModelFilter.setData({
            "year": new Date().getFullYear()
        })

        Page.$page[0].addEventListener("click", function() {
            $('.aio-ptp-dropdown:not(.clicked)').removeClass("active")
            $('.aio-ptp-dropdown').removeClass("clicked")
        })
    }, 1000);
};


Page.DBUserRoleonSuccess = function(variable, data) {
    Page.Variables.MODELConditionPurchase.dataSet = []
    for (var i = 0; i < data.length; i++) {
        if (data[i].roleId == 1) {
            Page.Variables.MODELConditionPurchaseAdmin.dataSet.push(data[i].roleId)
        }
        if (data[i].roleId == 2) {
            Page.Variables.MODELConditionPurchase.dataSet.push(data[i].roleId)
        }
    }

};

Page.headerButtonClick = function($event, widget) {
    Page.Variables.DBPOTracking.invoke()
};

Page.textFilterDepartmentClick = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
};

Page.container36Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').addClass("clicked")

};

Page.containerDepartmentClick = Page.container36Click





Page.checkboxFilterDepartmentAllChange = function($event, widget, newVal, oldVal) {
    console.log("newVal", newVal)
    if (newVal === true) {
        Page.Widgets.checkboxsetFilterDepartment.datavalue = Page.Widgets.checkboxsetFilterDepartment.dataset.map(function(el) {
            return el.departmentCode
        })
    } else {
        Page.Widgets.checkboxsetFilterDepartment.datavalue = null
    }
};
Page.checkboxsetFilterDepartmentChange = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxsetFilterDepartment.datavalue.length === Page.Widgets.checkboxsetFilterDepartment.dataset.length) {
        Page.Widgets.checkboxFilterDepartmentAll.datavalue = true
    } else {
        Page.Widgets.checkboxFilterDepartmentAll.datavalue = false
    }
};

Page.listDepartmenttoString = function(data) {
    return data.map(function(item) {
        return item.departmentCode
    })
}


Page.headerReleaseChange = function($event, widget, newVal, oldVal) {
    if (!newVal) {
        newVal = null
    }
    Page.Variables.ModelFilter.setData({
        "releaseDate": newVal,
    })
};

Page.headerETAChange = function($event, widget, newVal, oldVal) {
    if (!newVal) {
        newVal = null
    }
    Page.Variables.ModelFilter.setData({
        "ETADate": newVal,
    })
};

Page.DBDepartmentAllonSuccess = function(variable, data) {

};

Page.button3Click = function($event, widget) {
    Page.Variables.DBPOTracking.invoke()
};

Page.button4Click = function($event, widget) {
    Promise.resolve().then(() => {
        Page.Widgets.filterPONumber.datavalue = null
        Page.Widgets.filterDescription.datavalue = null
        Page.Widgets.filterActionBy.datavalue = null
        Page.Widgets.filterPOStatus.datavalue = null
        Page.Widgets.filterReleaseDate.datavalue = null
        // Page.Widgets.filterETADate. = null
        return true
    }).then(() => {
        return Page.Variables.DBPOTracking.invoke()
    }).then(() => {
        Page.Variables.DBPOTracking.invoke()
    })
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.DBPOTracking.orderBy == field + " ASC") {
        Page.Variables.DBPOTracking.orderBy = field + " DESC"
    } else if (Page.Variables.DBPOTracking.orderBy == field + " DESC") {
        Page.Variables.DBPOTracking.orderBy = ""
    } else {
        Page.Variables.DBPOTracking.orderBy = field + " ASC"
    }
    Page.Variables.DBPOTracking.invoke()
}
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('sapPoNumber')
};

Page.container42Click = function($event, widget) {
    Page.toggleTableSort('prNoItem')
};
Page.container43Click = function($event, widget) {
    Page.toggleTableSort('item')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('createdDate')
};
Page.container45Click = function($event, widget) {
    Page.toggleTableSort('lastApprovedDate')
};
Page.container110Click = function($event, widget) {
    Page.toggleTableSort('poQty')
};
Page.container111Click = function($event, widget) {
    Page.toggleTableSort('uom')
};
Page.container112Click = function($event, widget) {
    Page.toggleTableSort('currency')
};
Page.container113Click = function($event, widget) {
    Page.toggleTableSort('unitPrice')
};
Page.container50Click = function($event, widget) {
    Page.toggleTableSort('itemTotalAmount')
};
Page.container101_1Click = function($event, widget) {
    Page.toggleTableSort('eta')
};
Page.container102_1Click = function($event, widget) {
    Page.toggleTableSort('statusPo')
};
Page.container103_1Click = function($event, widget) {
    Page.toggleTableSort('creatorName')
};
Page.container106Click = function($event, widget) {
    Page.toggleTableSort('receivedBy')
};
// sort function

Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.DBPOTracking.maxResults = newVal
    Page.Variables.DBPOTracking.update()
};


// export excel
Page.button2Click = function($event, widget) {
    // intial
    var workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('PO Tracking');
    var xls_content = []
    var xls_header = [
        "PO Number",
        "Vendor Name",
        "PR Number",
        "User Department",
        "Item",
        "PO Description",
        "Doc. Date",
        "Release Date",
        "Qty",
        "Uom",
        "Curr",
        "Price Unit",
        "Amount PO",
        "ETA",
        "PO Status",
        "PO Creator",
        "PR Creator",
        "User Representative",
        "Request By",
        "Qty Needs to be Delivered",
        "Value Needs to be Delivered",
        "Received By",
        // "Invoice No",
        "Qty Needs to be Invoiced",
        "Value Needs to be Invoiced",
    ]
    xls_content.push(xls_header)

    Promise.resolve().then(() => {
        Page.Widgets.spinner2.show = true
        return Page.Variables.DBPOTracking.invoke()
    }).then((data) => {
        data = data.data
        data.forEach(item => {
            var dataItem = [
                item.sapPoNumber,
                item.vendorName,
                item.prNo,
                item.department,
                item.prNoItem,
                item.item,
                item.createdDate,
                item.lastApprovedDate,
                item.poQty,
                item.uom,
                item.currency,
                App.formatCurrency(item.unitPrice),
                App.formatCurrency(item.itemTotalAmount),
                moment(item.eta).format("YYYY-MM-DD"),
                item.statusPo,
                item.poCreator,
                item.prCreator,
                item.prRepName,
                item.requestByName,
                item.deliveryitemQty ? Number(item.poQty) - Number(item.deliveryitemQty) : item.poQty,
                item.deliveryitemTotalAmount ? App.formatCurrency(Number(item.itemTotalAmount) - Number(item.deliveryitemTotalAmount)) : App.formatCurrency(item.itemTotalAmount),
                item.receivedBy,
                // '-',
                item.qtyNeedsInvoiced,
                item.amountNeedsInvoiced
            ]
            xls_content.push(dataItem)
        })

        sheet.addRows(xls_content)

        // styling
        let columnWidth = [20, 20, 20, 20, 20, 20, 20, 20, 20, 12, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20];
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
            var filename = "PO Tracking";
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



Page.button5Click = function($event, widget) {
    Page.Widgets.spinner2.show = true
    // intial
    var workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('PO Tracking All');
    var xls_content = []
    var xls_header = [
        "PO Number",
        "Vendor Name",
        "PR Number",
        "User Department",
        "Item",
        "PO Description",
        "Doc. Date",
        "Release Date",
        "Qty",
        "Uom",
        "Curr",
        "Price Unit",
        "Amount PO",
        "ETA",
        "PO Status",
        "PO Creator",
        "PR Creator",
        "User Representative",
        "Request By",
        "Qty Needs to be Delivered",
        "Value Needs to be Delivered",
        "Received By",
        // "Invoice No",
        "Qty Needs to be Invoiced",
        "Value Needs to be Invoiced",
    ]
    xls_content.push(xls_header)

    Page.App.getVDBAllRecords(Page.Variables.DBPOTrackingAll).then(function(res) {
        console.log('res', res);
        res.forEach(item => {
            var dataItem = [
                item.sapPoNumber,
                item.vendorName,
                item.prNo,
                item.department,
                item.prNoItem,
                item.item,
                item.createdDate,
                item.lastApprovedDate,
                item.poQty,
                item.uom,
                item.currency,
                App.formatCurrency(item.unitPrice),
                App.formatCurrency(item.itemTotalAmount),
                moment(item.eta).format("YYYY-MM-DD"),
                item.statusPo,
                item.poCreator,
                item.prCreator,
                item.prRepName,
                item.requestByName,
                item.deliveryitemQty ? Number(item.poQty) - Number(item.deliveryitemQty) : item.poQty,
                item.deliveryitemTotalAmount ? App.formatCurrency(Number(item.itemTotalAmount) - Number(item.deliveryitemTotalAmount)) : App.formatCurrency(item.itemTotalAmount),
                item.receivedBy,
                // '-',
                item.qtyNeedsInvoiced,
                item.amountNeedsInvoiced
            ]
            xls_content.push(dataItem)
        })
        console.log(xls_content);

        sheet.addRows(xls_content)

        // styling
        let columnWidth = [20, 20, 20, 20, 20, 20, 20, 20, 20, 12, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20];
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
            var filename = "PO Tracking All";
            var blob = new Blob([datas], {
                type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            });

            let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
            saveAs(blob, filename + currentTime + ".xlsx");
        })

        Page.Widgets.spinner2.show = false
    })
};
Page.picture25_1Click = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_GRTracking.invoke({
        data: {
            'id': item.id
        }
    })
};

Page.picture11_1Click = function($event, widget) {
    Page.Variables.varFilterShow.dataSet.dataValue = true
};
Page.picture12_1Click = function($event, widget) {
    Page.Variables.varFilterShow.dataSet.dataValue = false
};