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

    Page.Widgets.container64.show = false
    Page.Variables.vmFilterPRtoRFQ.setValue('prStatus', Page.Variables.MODELprStatus.dataSet)

    // PR year
    $('input[name="textPrYear"]').yearpicker()
    $('input[name="textPrYear"]').change(function(year) {
        var year_value = $(this).val()
        Page.Widgets.textPrYear.datavalue = year_value
    })

    Page.Widgets.textPrYear.datavalue = ""

    // release date
    $('input[name="textRelease"]').daterangepicker({}, function(start, end, label) {
        Page.Variables.vmFilterHeader.setValue("releaseDateStart", start.format('YYYY-MM-DD'))
        Page.Variables.vmFilterHeader.setValue("releaseDateEnd", end.format('YYYY-MM-DD'))
    });
    $('input[name="textRelease"]').on('cancel.daterangepicker', function(ev, picker) {
        $('input[name="textRelease"]').val("")
        Page.Variables.vmFilterHeader.setValue("releaseDateStart", null)
        Page.Variables.vmFilterHeader.setValue("releaseDateEnd", null)
    })

    Page.Widgets.textRelease.datavalue = ""
    // flatpickr(Page.Widgets.textRelease.inputEl.nativeElement, {
    //     mode: 'range',
    //     dateFormat: 'd/m/Y',
    //     onChange: function(selectedDates) {
    //         var _this = this;
    //         var dateArr = selectedDates.map(function(date) {
    //             return _this.formatDate(date, 'Y-m-d');
    //         });
    //     }
    // });

    // delivery date
    $('input[name="textDelivery"]').daterangepicker({}, function(start, end, label) {
        Page.Variables.vmFilterHeader.setValue("etaStart", start.format('YYYY-MM-DD'))
        Page.Variables.vmFilterHeader.setValue("etaEnd", end.format('YYYY-MM-DD'))
    });
    $('input[name="textDelivery"]').on('cancel.daterangepicker', function(ev, picker) {
        $('input[name="textDelivery"]').val("")
        Page.Variables.vmFilterHeader.setValue("etaStart", null)
        Page.Variables.vmFilterHeader.setValue("etaEnd", null)
    })

    Page.Widgets.textDelivery.datavalue = ""
    // flatpickr(Page.Widgets.textDelivery.inputEl.nativeElement, {
    //     mode: 'range',
    //     dateFormat: 'd/m/Y',
    //     onChange: function(selectedDates) {
    //         var _this = this;
    //         var dateArr = selectedDates.map(function(date) {
    //             return _this.formatDate(date, 'Y-m-d');
    //         });
    //     }
    // });

    Page.Variables.vmListDepartment.clearData()

    if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
        Page.bindingRoleUser()
    } else {
        App.Variables.vdbqUserRole.invoke().then(res => {
            if (res.ok === true) {
                App.Variables.loggedInUser.setValue("roles", (JSON.parse(res.body).auth || "").split(","))
                Page.bindingRoleUser()
            }
        })
    }

    // event listener on click outside filter
    Page.$page[0].addEventListener("click", function() {
        $('.aio-ptp-dropdown:not(.clicked)').removeClass("active")
        $('.aio-ptp-dropdown').removeClass("clicked")
    })
}

Page.bindingRoleUser = function() {
    // limit department for user
    if (App.Variables.loggedInUser.dataSet.roles.indexOf('PRS-005') !== -1) {
        Page.Variables.qDepartmentMapUser.invoke({
            "inputFields": {
                "emp": App.Variables.loggedInUserData.dataSet.username
            }
        }).then(res => {
            var body = JSON.parse(res.body)
            for (var i = 0; i < body.content.length; i++) {
                Page.Variables.vmListDepartment.addItem({
                    "departmentTitle": body.content[i].department,
                    "departmentId": body.content[i].departmentId
                })
            }
        })
    }
    // open all department for admin
    else if (App.Variables.loggedInUser.dataSet.roles.indexOf('PRS-006') !== -1) {
        Page.Variables.vdbDepartment.invoke().then(res => {
            Page.Variables.vmListDepartment.setData(res.data)
        })
    }
}

Page.picture48Click = function($event, widget, item, currentItemWidgets) {
    Page.Widgets.list3.selecteditem = null
};

Page.picture64Click = function($event, widget, item, currentItemWidgets) {
    Page.Widgets.listTotalAdjusment.selecteditem = null
};

// PR to RFQ
Page.btnClosePRClick = function($event, widget) {
    if (!Page.Widgets.textarea2.datavalue) {
        return alert("Reason is required for action force close");
    }

    Page.closeFunction(Page.Widgets.textarea2.datavalue)
};

// PR to PO
Page.btnClosePR2Click = function($event, widget) {
    if (!Page.Widgets.textarea3.datavalue) {
        return alert("Reason is required for action force close");
    }

    Page.closeFunction(Page.Widgets.textarea3.datavalue)
};

Page.closeFunction = function(reason) {
    var data = {}
    Page.Variables.vmLoading.setValue("forceClose", true)

    Promise.resolve().then(function() {
        return Page.Variables.vdbPRLineItem.invoke()
    }).then(function(res) {
        data = res.data[0]
        data.pliStatus = 'closed'
        data.pliReason = reason
        data.pliModifiedAt = new Date().toISOString()
        data.pliModifiedBy = App.Variables.loggedInUserData.dataSet.user_full_name

        return Page.Variables.vdbqReverseLineItemBudget.invoke({
            inputFields: {
                userId: Page.App.Variables.loggedInUserData.dataSet.username,
                lineItemID: data.pliId,
                reason: data.pliReason,
                category: 'Force Close - '
            }
        })
    }).then(function() {
        return Page.Variables.vdbPRLineItem.updateRecord({
            row: data
        })
    }).then(function(res) {
        return Page.Variables.vdbPRLineItem2.invoke({
            "filterFields": {
                prId: {
                    "value": data.prId
                },
                pliStatus: {
                    "value": "active"
                }
            }
        })
    }).then(function(res) {
        if (res.data.length > 0) {
            return Promise.resolve()
        }

        return Page.Variables.qPrChangeStatus.invoke({
            "inputFields": {
                "prId": data.prId,
                "prModifiedAt": new Date().toISOString(),
                "prModifiedBy": App.Variables.loggedInUserData.dataSet.username,
                "prStatus": "Closed"
            }
        }).then(function() {
            return Page.Variables.qDeleteTasklist.invoke({
                "inputFields": {
                    "tlType": 'PR',
                    "tlParamId": data.prId
                }
            })
        })

        //Update remarks budget detail 2 

    }).then(() => {
        Page.Variables.viewPurchaseRequest.update()
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SUCCESS)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()

        Page.Variables.vmLoading.setValue("forceClose", false)
        Page.Widgets.dialogForceClose.close()
        Page.Widgets.dialogForceClose2.close()
        Page.Variables.vdbPRLineItem.clearData()
    })
};

// render numbering
Page.listTotalAdjusmentBeforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.viewPurchaseRequest.pagination.number * Page.Variables.viewPurchaseRequest.pagination.size) + (i + 1);
    }
};

Page.list3Beforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.viewPurchaseRequest.pagination.number * Page.Variables.viewPurchaseRequest.pagination.size) + (i + 1);
    }
};

Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.sortirNumberSelected.dataSet.dataValue = newVal
    Page.Variables.viewPurchaseRequest.maxResults = newVal
    Page.Variables.viewPurchaseRequest.update()
};

Page.select4Change = function($event, widget, newVal, oldVal) {
    Page.Variables.sortirNumberSelected.dataSet.dataValue = newVal
    Page.Variables.viewPurchaseRequest.maxResults = newVal
    Page.Variables.viewPurchaseRequest.update()
};

// apply filter header
Page.button8Click = function($event, widget) {
    var filterHeader = Page.Variables.vmFilterHeader.dataSet
    if (filterHeader.cid || filterHeader.departmentId || filterHeader.requestDateStart || filterHeader.requestDateEnd || filterHeader.releaseDateStart || filterHeader.releaseDateEnd || filterHeader.etaStart || filterHeader.etaEnd) {
        Page.Widgets.spinnerFilterHeader.show = true
        Page.clearFilterPRtoRFQ()

        // Page.Variables.vmFilterPRtoRFQ.setValue('uom', uom);
        Page.Variables.vmFilterPRtoRFQ.setValue('prStatus', Page.Variables.MODELprStatus.dataSet)


        Page.Variables.viewPurchaseRequest.invoke()
        Page.Widgets.spinnerFilterHeader.show = false
        Page.Widgets.container64.show = true


        // Page.Variables.viewPurchaseRequest.invoke().then(() => {

        //     // Get All Data
        //     var arr = []
        //     var prNumber = []
        //     var prDesc = []
        //     var item = []
        //     var quantity = []
        //     var uom = []
        //     var unitPrice = []
        //     var currency = []
        //     var prAmount = []
        //     var prStatus = []
        //     var rfqNumber = []

        //     Page.App.getVDBAllRecords(Page.Variables.viewPurchaseRequestAll).then(function(res) {
        //         for (var i = 0; i < res.length; i++) {
        //             var dt = res[i]

        //             if (dt.prNumber) {
        //                 prNumber.push(dt.prNumber)
        //             }
        //             if (dt.prDesc) {
        //                 prDesc.push(dt.prDesc)
        //             }
        //             if (dt.item) {
        //                 item.push(dt.item)
        //             }
        //             if (dt.qty) {
        //                 quantity.push(dt.qty)
        //             }
        //             if (dt.uom) {
        //                 uom.push(dt.uom)
        //             }
        //             if (dt.unitPrice) {
        //                 unitPrice.push(dt.unitPrice)
        //             }
        //             if (dt.currency) {
        //                 currency.push(dt.currency)
        //             }
        //             if (dt.prAmount) {
        //                 prAmount.push(dt.prAmount)
        //             }
        //             if (dt.prStatus) {
        //                 prStatus.push(dt.prStatus)
        //             }
        //             if (dt.rfqNumber) {
        //                 rfqNumber.push(dt.rfqNumber)
        //             }


        //             if (i === res.length - 1) {
        //                 prNumber = [...new Set(prNumber)]
        //                 prDesc = [...new Set(prDesc)]
        //                 item = [...new Set(item)]
        //                 quantity = [...new Set(quantity)]
        //                 uom = [...new Set(uom)]
        //                 unitPrice = [...new Set(unitPrice)]
        //                 currency = [...new Set(currency)]
        //                 prAmount = [...new Set(prAmount)]
        //                 prStatus = [...new Set(prStatus)]
        //                 rfqNumber = [...new Set(rfqNumber)]

        //                 Page.Variables.vmFilterPRtoRFQ.setValue('prNumber', prNumber)
        //                 Page.Variables.vmFilterPRtoRFQ.setValue('prDesc', prDesc)
        //                 Page.Variables.vmFilterPRtoRFQ.setValue('item', item)
        //                 Page.Variables.vmFilterPRtoRFQ.setValue('quantity', quantity)
        //                 Page.Variables.vmFilterPRtoRFQ.setValue('uom', uom);
        //                 Page.Variables.vmFilterPRtoRFQ.setValue('unitPrice', unitPrice)
        //                 Page.Variables.vmFilterPRtoRFQ.setValue('currency', currency)
        //                 Page.Variables.vmFilterPRtoRFQ.setValue('prStatus', prStatus)
        //                 Page.Variables.vmFilterPRtoRFQ.setValue('prAmount', prAmount)
        //                 Page.Variables.vmFilterPRtoRFQ.setValue('rfqNumber', rfqNumber)

        //                 return arr
        //             }
        //         }

        //     })



        // }).then(function(res) {
        //     Page.Widgets.spinnerFilterHeader.show = false
        //     Page.Widgets.container64.show = true

        //     return Promise.resolve()
        // })
    }
};

// apply filter PR to RFQ
Page.button9Click = function($event, widget) {
    Page.Widgets.spinnerFilterRFQ.show = true

    Page.Variables.viewPurchaseRequest.invoke().then(() => {
        Page.Widgets.spinnerFilterRFQ.show = false

        return Promise.resolve()
    })
};

// clear filter PR to RFQ
Page.button10Click = function($event, widget) {
    Promise.resolve().then(() => {
        Page.Widgets.spinnerFilterRFQ.show = true
        return Page.clearFilterPRtoRFQ()
    }).then(() => {
        return Page.Variables.viewPurchaseRequest.invoke()
    }).then(() => {
        return Page.Variables.viewPurchaseRequest.invoke()
    }).then(() => {
        Page.Widgets.spinnerFilterRFQ.show = false

        return Promise.resolve()
    })
};

// clear filter field
Page.clearFilterPRtoRFQ = function() {
    Page.Widgets.checkboxset2_1.datavalue = []
    Page.Widgets.checkboxset4.datavalue = []
    Page.Widgets.checkboxset5.datavalue = []
    Page.Widgets.checkboxset6.datavalue = []
    Page.Widgets.checkboxset8.datavalue = []
    Page.Widgets.checkboxset9.datavalue = []
    Page.Widgets.date2.datavalue = ""
    Page.Widgets.date3.datavalue = ""
    Page.Widgets.date4.datavalue = ""
    Page.Widgets.prDescPRRFQ.datavalue = null
    Page.Widgets.prNumberPRRFQ.datavalue = null
    Page.Widgets.rfqNumberPRRFQ.datavalue = null
    Page.Widgets.uomPRRFQ.datavalue = null

}

Page.container125Click = function($event, widget) {
    Page.Variables.boolean_showTab1.dataSet.dataValue = !Page.Variables.boolean_showTab1.dataSet.dataValue
};

Page.container144_1Click = function($event, widget) {
    Page.Variables.boolean_showTab2.dataSet.dataValue = !Page.Variables.boolean_showTab2.dataSet.dataValue
};

Page.ExportExcelClick = function($event, widget) {
    // intial
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('PR Tracking');
    var filename = "PR Tracking";

    // inserting data
    var xls_content = []

    var xls_header = [
        "PR Number",
        "Item",
        "PR Description",
        "Request Date",
        "Release Date",
        "Qty",
        "UOM",
        "Price Unit",
        "Currency",
        "Amount PR",
        "ETA",
        "PR Status",
        "RFQ Number",
        "RFQ Status",
        "IO Number",
        "SAP PR Number"
    ]
    xls_content.push(xls_header)

    var data = Page.Variables.viewPurchaseRequest.dataSet
    data.forEach(item => {
        xls_content.push([
            item.prNumber,
            item.item,
            item.prDesc,
            item.requestDate,
            item.releaseDate,
            item.qty,
            item.uom,
            item.unitPrice,
            item.currency,
            item.prAmount,
            item.releaseDate,
            item.prStatus,
            '',
            '',
            item.ioNumber,
            item.sapPrNumber
        ])
    })

    sheet.addRows(xls_content)

    // styling
    let alignCenter = {
        vertical: 'middle',
        'horizontal': 'center'
    };
    sheet.getRow(1).alignment = alignCenter;

    // Adjust column width
    let columnWidth = [14, 8, 23, 14, 14, 8, 8, 14, 12, 14, 12, 12, 12, 12];
    sheet.columns.forEach((col, index) => {
        if (columnWidth[index]) {
            col.width = columnWidth[index];
        }
    });

    let border = {
        top: {
            style: 'thin'
        },
        left: {
            style: 'thin'
        },
        bottom: {
            style: 'thin'
        },
        right: {
            style: 'thin'
        }
    };

    let boldCell = function(cells) {
        cells.forEach(el => {
            sheet.getCell(el).font = {
                bold: true
            }
        });
        return;
    }
    boldCell(["A1", "B1", "C1", "D1"]);

    // exporting
    workbook.xlsx.writeBuffer().then(function(datas) {
        var blob = new Blob([datas], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        });

        let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
        saveAs(blob, filename + currentTime + ".xlsx");
    })
};

Page.viewPurchaseRequestonSuccess = function(variable, data) {

};

// to get filter checkboxes
Page.viewPurchaseRequestAllonBeforeDatasetReady = function(variable, data) {
    // return arr
};

// filter PR number
// Page.text4Click = function($event, widget) {
//     widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
//     // Page.Widgets.container91_2.show = !Page.Widgets.container91_2.show
// };
// Page.checkbox1Change = function($event, widget, newVal, oldVal) {
//     if (newVal === true) {
//         Page.Widgets.checkboxset2.datavalue = Page.Widgets.checkboxset2.dataset
//     } else {
//         Page.Widgets.checkboxset2.datavalue = null
//     }
// };
// Page.checkboxset2Change = function($event, widget, newVal, oldVal) {
//     if (Page.Widgets.checkboxset2.datavalue.length === Page.Widgets.checkboxset2.dataset.length) {
//         Page.Widgets.checkbox1.datavalue = true
//     } else {
//         Page.Widgets.checkbox1.datavalue = false
//     }
// };
Page.text13Click = function($event, widget) {

};
// filter PR Desc
Page.text6Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
    // Page.Widgets.container112_1.show = !Page.Widgets.container112_1.show
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
// filter item
Page.text5Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
    // Page.Widgets.container110_1.show = !Page.Widgets.container110_1.show
};

Page.checkboxset2_1Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset2_1.datavalue.length === Page.Widgets.checkboxset2_1.dataset.length) {
        Page.Widgets.checkbox2.datavalue = true
    } else {
        Page.Widgets.checkbox2.datavalue = false
    }
};
// filter quantity
Page.text7Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
    // Page.Widgets.container114_1.show = !Page.Widgets.container114_1.show
};
Page.checkbox4Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset4.datavalue = Page.Widgets.checkboxset4.dataset
    }
};
Page.checkboxset4Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset4.datavalue.length === Page.Widgets.checkboxset4.dataset.length) {
        Page.Widgets.checkbox4.datavalue = true
    } else {
        Page.Widgets.checkbox4.datavalue = false
    }
};
// filter UOM
Page.text11Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
    // Page.Widgets.container120_1.show = !Page.Widgets.container120_1.show
};
Page.checkbox7Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset7.datavalue = Page.Widgets.checkboxset7.dataset
    } else {
        Page.Widgets.checkboxset7.datavalue = null
    }
};
Page.checkboxset7Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset7.datavalue.length === Page.Widgets.checkboxset7.dataset.length) {
        Page.Widgets.checkbox7.datavalue = true
    } else {
        Page.Widgets.checkbox7.datavalue = false
    }
};
// filter unit price
Page.text10Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
    // Page.Widgets.container118_1.show = !Page.Widgets.container118_1.show
};
Page.checkbox6Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset6.datavalue = Page.Widgets.checkboxset6.dataset
    }
};
Page.checkboxset6Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset6.datavalue.length === Page.Widgets.checkboxset6.dataset.length) {
        Page.Widgets.checkbox6.datavalue = true
    } else {
        Page.Widgets.checkbox6.datavalue = false
    }
};
// filter currency
Page.text8Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
    // Page.Widgets.container116_1.show = !Page.Widgets.container116_1.show
};
Page.checkbox5Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset5.datavalue = Page.Widgets.checkboxset5.dataset
    }
};
Page.checkboxset5Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset5.datavalue.length === Page.Widgets.checkboxset5.dataset.length) {
        Page.Widgets.checkbox5.datavalue = true
    } else {
        Page.Widgets.checkbox5.datavalue = false
    }
};
// filter PR Amount
Page.text12Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
};
Page.checkbox8Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset8.datavalue = Page.Widgets.checkboxset8.dataset
    }
};
Page.checkboxset8Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset8.datavalue.length === Page.Widgets.checkboxset8.dataset.length) {
        Page.Widgets.checkbox8.datavalue = true
    } else {
        Page.Widgets.checkbox8.datavalue = false
    }
};
// filter PR Status
Page.text9Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
    // Page.Widgets.container124.show = !Page.Widgets.container124.show
};

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

Page.text22Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
};

Page.checkbox12Change = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxset12.datavalue = Page.Widgets.checkboxset12.dataset
    } else {
        Page.Widgets.checkboxset12.datavalue = null
    }
};
Page.checkboxset12Change = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxset12.datavalue.length === Page.Widgets.checkboxset12.dataset.length) {
        Page.Widgets.checkbox12.datavalue = true
    } else {
        Page.Widgets.checkbox12.datavalue = false
    }
};



/* SORTING TABLE */
Page.toggleTableSort = function(field) {
    var filterHeader = Page.Variables.vmFilterHeader.dataSet
    if (filterHeader.cid || filterHeader.departmentId || filterHeader.requestDateStart || filterHeader.requestDateEnd || filterHeader.releaseDateStart || filterHeader.releaseDateEnd || filterHeader.etaStart || filterHeader.etaEnd) {
        if (Page.Variables.viewPurchaseRequest.orderBy == field + " ASC") {
            Page.Variables.viewPurchaseRequest.orderBy = field + " DESC"
        } else if (Page.Variables.viewPurchaseRequest.orderBy == field + " DESC") {
            Page.Variables.viewPurchaseRequest.orderBy = ""
        } else {
            Page.Variables.viewPurchaseRequest.orderBy = field + " ASC"
        }

        Page.Variables.viewPurchaseRequest.update()
    }
}
// sort PR to RFQ
Page.container112_2Click = function($event, widget) {
    Page.toggleTableSort("prDesc")
};
Page.container110_2Click = function($event, widget) {
    Page.toggleTableSort("prNumber")
};
Page.container111_2Click = function($event, widget) {
    Page.toggleTableSort("item")
};
Page.container113_2Click = function($event, widget) {
    Page.toggleTableSort("requestDate")
};
Page.container114_2Click = function($event, widget) {
    Page.toggleTableSort("releaseDate")
};
Page.container121_2Click = function($event, widget) {
    Page.toggleTableSort("eta")
};
Page.container115_2Click = function($event, widget) {
    Page.toggleTableSort("qty")
};
Page.container116_2Click = function($event, widget) {
    Page.toggleTableSort("uom")
};
Page.container117_2Click = function($event, widget) {
    Page.toggleTableSort("unitPrice")
};
Page.container118_2Click = function($event, widget) {
    Page.toggleTableSort("currency")
};
Page.container120_2Click = function($event, widget) {
    Page.toggleTableSort("prAmount")
};
Page.container122_2Click = function($event, widget) {
    Page.toggleTableSort("prStatus")
};
// sort PR to PO
Page.container144Click = function($event, widget) {
    Page.toggleTableSort("prNumber")
};
Page.container155Click = function($event, widget) {
    Page.toggleTableSort("prStatus")
};
Page.container151Click = function($event, widget) {
    Page.toggleTableSort("matGroupDesc")
};
Page.container152Click = function($event, widget) {
    Page.toggleTableSort("currency")
};
Page.container128Click = function($event, widget) {
    Page.toggleTableSort("rfqNumber")
};
Page.container129Click = function($event, widget) {
    Page.toggleTableSort("prPurchaseBy")
};

Page.vdbPRLineItemonSuccess = function(variable, data) {

};


Page.container91Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').addClass("clicked")
};
Page.container92Click = Page.container91Click;
Page.container93_1Click = Page.container91Click;
Page.container94_2Click = Page.container91Click;
Page.container98_1Click = Page.container91Click;
Page.container97_2Click = Page.container91Click;
Page.container95_2Click = Page.container91Click;
Page.container99_1Click = Page.container91Click;
Page.container96_2Click = Page.container91Click;
Page.container100_2Click = Page.container91Click;


Page.filterRFQClick = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
};

Page.container145Click = function($event, widget) {
    Page.toggleTableSort("prDesc")
};
Page.button9_1Click = function($event, widget) {
    Page.Widgets.spinnerFilterRFQ.show = true

    Page.Variables.viewPurchaseRequest.invoke().then(() => {
        Page.Widgets.spinnerFilterRFQ.show = false

        return Promise.resolve()
    })
};
Page.button10_1Click = function($event, widget) {
    Page.Widgets.prNumberPRPO.datavalue = null
    Page.Widgets.poNumberPRPO.datavalue = null
    Page.Widgets.prDescPRPO.datavalue = null
    Page.Widgets.requestByPRPO.datavalue = null
    Page.Widgets.materialGroupPRPO.datavalue = null
};
Page.uomPRRFQChange = function($event, widget, newVal, oldVal) {

};