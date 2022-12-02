/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */

var year_value, start_date, end_date
Page.onReady = function() {
    setTimeout(() => {
        $('.yearpicker').yearpicker()
        $('.yearpicker').change(function() {
            year_value = $(this).val()
        })
        $('.yearpicker').val(new Date().getFullYear())

        $('.flatpickr').daterangepicker({
            opens: 'left'
        }, function(start, end, label) {
            start_date = start.format('YYYY-MM-DD')
            end_date = end.format('YYYY-MM-DD')
        });
        $('.flatpickr').on('cancel.daterangepicker', function(ev, picker) {
            $(this).val('');
        });
        $('.flatpickr').val('');

    }, 1000);



    // event listener on click outside filter
    Page.$page[0].addEventListener("click", function() {
        $('.aio-ptp-dropdown:not(.clicked)').removeClass("active")
        $('.aio-ptp-dropdown').removeClass("clicked")
    })

}

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

Page.btnApplyClick = function($event, widget) {
    if (!Page.Widgets.selectCompany.datavalue) {
        App.Actions.appNotification.setMessage("Company is required")
        App.Actions.appNotification.setToasterClass("warning")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        return false
    }
    Page.Variables.vViewMaterialGroupList.invoke()
    Page.Variables.DBPRReadyPurchase.invoke()
    Page.Variables.statusShowDiv.dataSet.dataValue = true
    // Page.Widgets.spinnerItem.show = true
};

Page.listMaterialGrouptoString = function(data) {
    return data.map(function(item) {
        return item.materialGroupId ? item.materialGroupId : item.materialGroup
    })
}

Page.DBMaterialGroupPurchasingonSuccess = function(variable, data) {
    if (data.length > 0) {
        Page.Variables.MODELPurchasingGroup.dataSet = data
    } else {
        Page.Variables.MODELPurchasingGroup.dataSet = Page.Variables.vViewMaterialGroup.dataSet
    }
};


Page.checkboxListAllChange = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        // Page.Widgets.checkboxList.datavalue = true
        Page.Widgets.listPRI.listItems._results.filter(function(el) {
            // return el.item.rfqStatus == 'open'
            return true
        }).map(function(item) {
            return item.currentItemWidgets.checkboxList.datavalue = true
        })
    } else {
        Page.Widgets.listPRI.listItems._results.map(function(item) {
            return item.currentItemWidgets.checkboxList.datavalue = false
        })
    }
};

Page.checkboxListChange = function($event, widget, item, currentItemWidgets, newVal, oldVal) {
    var listItem = Page.Variables.MODELItemSelected.dataSet
    var rfqNumber = item.rfqNumber
    var reqvVendor = item.reqvVendor

    if (newVal === true) {
        if (rfqNumber !== null) {
            //     Page.Widgets.listPRI.listItems._results.filter(function(el) {
            //         return el.item.rfqNumber && el.item.rfqNumber == item.rfqNumber
            //     }).map(function(item) {
            //         return item.currentItemWidgets.checkboxList.datavalue = true
            //     })
            // } else {
            Page.Variables.MODELItemSelected.dataSet.push({
                "rfqNumber": item.rfqNumber
            })

            Page.Widgets.listPRI.listItems._results.filter(function(el) {
                return el.item.rfqNumber !== item.rfqNumber
            }).map(function(item) {
                return item.currentItemWidgets.checkboxList.disabled = true
            })

            Page.Widgets.listPRI.listItems._results.filter(function(el) {
                return el.item.rfqNumber == item.rfqNumber
            }).map(function(item) {
                return item.currentItemWidgets.checkboxList.datavalue = true
            })

        }
    } else {
        if (rfqNumber !== null) {
            Page.Variables.MODELItemSelected.dataSet = listItem.filter(function(item) {
                return item.rfqNumber !== rfqNumber
            })

            Page.Widgets.listPRI.listItems._results.filter(function(el) {
                return el.item.rfqNumber == item.rfqNumber || el.item.liId == item.liId
            }).map(function(item) {
                return item.currentItemWidgets.checkboxList.datavalue = false
            })

            if (Page.Variables.MODELItemSelected.dataSet.length === 0) {
                Page.Widgets.listPRI.listItems._results.map(function(item) {
                    return item.currentItemWidgets.checkboxList.disabled = false
                })
            }
        }
    }
};


Page.buttonSubmitRFQClick = function($event, widget) {
    var rfq = false
    var prPurchaseBy = []

    var listItem = Page.Widgets.listPRI.listItems._results.filter(function(el) {
        return el.currentItemWidgets.checkboxList.datavalue === true
    })

    for (var i = 0; i < listItem.length; i++) {
        console.log(listItem[i].item.rfqNumber)
        if (listItem[i].item.rfqNumber) {
            rfq = true
        }
        if (!prPurchaseBy.includes(listItem[i].item.prPurchaseBy)) {
            prPurchaseBy.push(listItem[i].item.prPurchaseBy)
        }

        if (i === 0) {



            if (rfq === true) {
                Page.Variables.modelProcessPurchasing.dataSet = [{
                    name: 'Purchase Order',
                    dataValue: 'purchase_order'
                }]
            } else if (rfq === false && prPurchaseBy.length === 1 && prPurchaseBy.includes("Purchasing")) {
                Page.Variables.modelProcessPurchasing.dataSet = [{
                    name: 'Purchase Order',
                    dataValue: 'purchase_order'
                }, {
                    name: 'Advance/Claim Employee',
                    dataValue: 'advance_claim'
                }]
            } else if (rfq === false && prPurchaseBy.length === 1 && prPurchaseBy.includes("User")) {
                Page.Variables.modelProcessPurchasing.dataSet = [{
                    name: 'Purchase Order',
                    dataValue: 'purchase_order'
                }]
            }

            Page.Widgets.ModalNext.open()
        }
    }
};



Page.btn_SaveClick = function($event, widget) {
    if (Page.Widgets.processPurchase.datavalue === 'advance_claim') {
        App.Actions.appNotification.setMessage("Mohon maaf aplikasi Advance Claim masih pada proses development")
        App.Actions.appNotification.setToasterClass("warning")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    } else {
        var dataChecklist = Page.Widgets.listPRI.listItems._results.filter(function(el) {
            return el.currentItemWidgets.checkboxList.datavalue === true
        }).map(function(dt) {
            return dt.item.liId
        }).join(",")
        Page.Actions.goTo_POForm.invoke({
            data: {
                "item": dataChecklist,
                "processPurchase": Page.Widgets.processPurchase.datavalue
            }
        })
    }

};



Page.btnExportExcellClick = function($event, widget) {
    // Page.Widgets.spinner1.show = true
    var wsData = Page.Variables.vViewMaterialGroupList.dataSet
    const workbook = new ExcelJS.Workbook();
    // for (var i = 0; i < wsData.length; i++) {
    // var dataLi = Page.Variables.DBPRReadyPurchase.dataSet.filter(function(el) {
    //     return el.materialGroup == wsData[i].materialGroup
    // })
    var dataLi = Page.Variables.DBPRReadyPurchase.dataSet
    if (dataLi.length > 0) {
        let sheet = workbook.addWorksheet("Ready for Purchase");
        // let sheet = workbook.addWorksheet(wsData[i].materialGroupDesc);

        var xls_content = []
        var xls_header = [
            "RFQ Number",
            "PR Number",
            "Currency",
            "Unit Price PR",
            "Unit Price RFQ",
            "Vendor",
            "Description",
            "Purchase Group",
            "Material Group",
            "Qty",
            "UoM",
            "ETA",
            "Status"
        ]
        xls_content.push(xls_header)

        dataLi.forEach(function(item) {
            var dataItem = [
                item.rfqNumber,
                item.prNumber,
                item.pliCurrency,
                item.pliUnitPrice,
                item.liqUnitPrice,
                item.reqvVendor,
                item.prDescription,
                item.purchaseGroupId,
                item.materialGroupDesc,
                item.qty,
                item.uom,
                item.eta,
                item.prStatus
            ]
            xls_content.push(dataItem)
        })

        sheet.addRows(xls_content)

        // styling
        let columnWidth = [30, 30, 30, 15, 15, 15, 20, 20];
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

        let boldCell = function(cells) {
            cells.forEach(el => {
                sheet.getCell(el).font = {
                    bold: true
                }
            });
            return;
        }
        boldCell(["A6", "B6", "C6", "D6", "E6", "F6", "G6", "A2", "C2", "E2", "G2"]);
    }
    // }

    // exporting
    workbook.xlsx.writeBuffer().then(function(datas) {
        var filename = "PR Ready for Purchase";
        var blob = new Blob([datas], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        });

        let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
        saveAs(blob, filename + currentTime + ".xlsx");
    })
    // Page.Widgets.spinner1.show = false
};

Page.btnExportExcellAllClick = function($event, widget) {
    // Page.Widgets.spinner1.show = true
    var wsData = Page.Variables.vViewMaterialGroupList.dataSet
    const workbook = new ExcelJS.Workbook();
    // for (var i = 0; i < wsData.length; i++) {
    // var dataLi = Page.Variables.DBPRReadyPurchaseAll.dataSet.filter(function(el) {
    //     return el.materialGroup == wsData[i].materialGroup
    // })
    var dataLi = Page.Variables.DBPRReadyPurchaseAll.dataSet;
    if (dataLi.length > 0) {
        let sheet = workbook.addWorksheet("Ready for Purchase");

        var xls_content = []
        var xls_header = [
            "RFQ Number",
            "PR Number",
            "Currency",
            "Unit Price PR",
            "Unit Price RFQ",
            "Vendor",
            "Description",
            "Purchase Group",
            "Material Group",
            "Qty",
            "UoM",
            "ETA",
            "Status"
        ]
        xls_content.push(xls_header)

        dataLi.forEach(function(item) {
            var dataItem = [
                item.rfqNumber,
                item.prNumber,
                item.pliCurrency,
                item.pliUnitPrice,
                item.liqUnitPrice,
                item.reqvVendor,
                item.prDescription,
                item.purchaseGroupId,
                item.materialGroupDesc,
                item.qty,
                item.uom,
                item.eta,
                item.prStatus
            ]
            xls_content.push(dataItem)
        })

        sheet.addRows(xls_content)

        // styling
        let columnWidth = [30, 30, 30, 15, 15, 15, 20, 20];
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

        let boldCell = function(cells) {
            cells.forEach(el => {
                sheet.getCell(el).font = {
                    bold: true
                }
            });
            return;
        }
        boldCell(["A6", "B6", "C6", "D6", "E6", "F6", "G6", "A2", "C2", "E2", "G2"]);
    }
    // }

    // exporting
    workbook.xlsx.writeBuffer().then(function(datas) {
        var filename = "PR Ready for Purchase";
        var blob = new Blob([datas], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        });

        let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
        console.log(blob, filename + currentTime + ".xlsx");
        saveAs(blob, filename + currentTime + ".xlsx");
    })
    // Page.Widgets.spinner1.show = false
};

// Sorting Table
Page.container40Click = function($event, widget) {
    Page.toggleTableSort('rfqNumber')
};
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('prNumber')
};
Page.container42Click = function($event, widget) {
    Page.toggleTableSort('pliCurrency')
};
Page.container43Click = function($event, widget) {
    Page.toggleTableSort('pliUnitPrice')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('liqUnitPrice')
};
Page.container45Click = function($event, widget) {
    Page.toggleTableSort('reqvVendor')
};
Page.container58Click = function($event, widget) {
    Page.toggleTableSort('prDescription')
};
Page.container59Click = function($event, widget) {
    Page.toggleTableSort('purchaseGroupId')
};
Page.container51_1Click = function($event, widget) {
    Page.toggleTableSort('materialGroup')
};
Page.container52_1Click = function($event, widget) {
    Page.toggleTableSort('qty')
};
Page.container53_1Click = function($event, widget) {
    Page.toggleTableSort('uom')
};
Page.container54_1Click = function($event, widget) {
    Page.toggleTableSort('eta')
};
Page.container55_1Click = function($event, widget) {
    Page.toggleTableSort('prStatus')
};
// Sorting Table


//Form Filter
Page.DBPRReadyPurchaseonSuccess = function(variable, data) {
    if (Page.Widgets.spinnerItem) {
        Page.Widgets.spinnerItem.show = false
        // Page.Widgets.spinner1.show = false
    }

    if (Page.Widgets.filterStatusRFQ) {
        var status = ((Page.Widgets.filterStatusRFQ.datavalue) ? Page.Widgets.filterStatusRFQ.datavalue : null)
        if (status === 'has_value') {
            variable.dataSet = data.filter(function(el) {
                return el.rfqNumber !== null
            })
        } else if (status === 'blank') {
            variable.dataSet = data.filter(function(el) {
                return el.rfqNumber === null
            })
        }
    }
};

Page.button8_1Click = function($event, widget) {
    Page.Widgets.spinnerItem.show = true

    var vendor = (Page.Widgets.filterVendor.datavalue ? Page.Widgets.filterVendor.datavalue : null)
    var status = (Page.Widgets.filterStatus.datavalue ? Page.Widgets.filterStatus.datavalue : null)
    var desc = (Page.Widgets.filterDesc.datavalue ? Page.Widgets.filterDesc.datavalue : null)
    var eta = (Page.Widgets.filterETA.datavalue ? Page.Widgets.filterETA.datavalue : null)
    var statusRFQ = (Page.Widgets.filterStatusRFQ.datavalue ? Page.Widgets.filterStatusRFQ.datavalue : null)
    var RFQ = (Page.Widgets.filterRFQ.datavalue ? Page.Widgets.filterRFQ.datavalue : null)
    var PR = (Page.Widgets.filterPR.datavalue ? Page.Widgets.filterPR.datavalue : null)
    var purchaseGroupId = (Page.Widgets.filterPurchaseGroup.datavalue ? Page.Widgets.filterPurchaseGroup.datavalue : null)

    Page.Variables.ModelParamsDBPRReadyPurchase.setData({
        reqvVendor: vendor,
        rfqStatus: status,
        prDescription: desc,
        eta: eta,
        rfqNumber: RFQ,
        prNumber: PR,
        purchaseGroupId: purchaseGroupId
    })

    if (statusRFQ) {
        Page.Variables.DBPRReadyPurchase.invoke()
    }

    Page.Widgets.spinnerItem.show = false
};

Page.button9Click = function($event, widget) {
    Page.Widgets.spinnerItem.show = true

    Page.Widgets.filterVendor.datavalue = null
    Page.Widgets.filterStatus.datavalue = null
    Page.Widgets.filterDesc.datavalue = null
    Page.Widgets.filterETA.datavalue = null
    Page.Widgets.filterStatusRFQ.datavalue = null
    Page.Widgets.filterRFQ.datavalue = null
    Page.Widgets.filterPR.datavalue = null
    Page.Widgets.filterPurchaseGroup.datavalue = null

    Page.Variables.ModelParamsDBPRReadyPurchase.setData({
        reqvVendor: null,
        prStatus: null,
        prDescription: null,
        eta: null,
        rfqNumber: null,
        prNumber: null,
        purchaseGroupId: null
    })

    Page.Variables.DBPRReadyPurchase.invoke()
};


//Perhitungan Style ETA
Page.diffDay = function($start, $end) {
    const oneDay = 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds
    const firstDate = new Date($start);
    const secondDate = new Date($end);

    return Math.round(((firstDate - secondDate) / oneDay));
}


Page.container112_1Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').addClass("clicked")
};



Page.textFilterMaterialGroupClick = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
};

Page.filterhasValue = function(object, key, value) {
    var newObj = [];
    var i = 0;
    for (i = 0; i < object.length; i++) {
        if (object[i][key] > 0 || object[i][key] < 0) {
            newObj.push(object[i])
        }
    }
    if (i == object.length) {
        return newObj;
    }
}

Page.checkboxFilterStatusAllChange = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.checkboxsetFilterStatus.datavalue = Page.Widgets.checkboxsetFilterStatus.dataset.map(function(el) {
            return el.dataValue
        })
    } else {
        Page.Widgets.checkboxsetFilterStatus.datavalue = null
    }
};
Page.checkboxsetFilterStatusChange = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.checkboxsetFilterStatus.datavalue.length === Page.Widgets.checkboxsetFilterStatus.dataset.length) {
        Page.Widgets.checkboxFilterStatusAll.datavalue = true
    } else {
        Page.Widgets.checkboxFilterStatusAll.datavalue = false
    }
};

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


Page.label28Click = function($event, widget, item, currentItemWidgets) {
    // Page.Variables.vViewPurchseRequestItem.invoke()
    Page.Widgets.spinnerItem.show = true
};


Page.selectRowChange = function($event, widget, newVal, oldVal) {
    Page.Variables.DBPRReadyPurchase.maxResults = newVal
    Page.Variables.DBPRReadyPurchase.update()
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.DBPRReadyPurchase.orderBy == field + " ASC") {
        Page.Variables.DBPRReadyPurchase.orderBy = field + " DESC"
    } else if (Page.Variables.DBPRReadyPurchase.orderBy == field + " DESC") {
        Page.Variables.DBPRReadyPurchase.orderBy = ""
    } else {
        Page.Variables.DBPRReadyPurchase.orderBy = field + " ASC"
    }
    Page.Variables.DBPRReadyPurchase.invoke()
}


Page.checkDisabled = function() {
    var dataChecklist = Page.Widgets.listPRI.listItems._results.filter(function(el) {
        return el.currentItemWidgets.checkboxList.datavalue === true
    })
    if (dataChecklist.length > 0) {
        return false
    } else {
        return true
    }
}

Page.pictureForceCloseClick = function($event, widget, item, currentItemWidgets) {
    // if (!confirm("Are you sure to close this line item quotation ?")) {
    //     return
    // }
    // Page.Widgets.spinner1.show = true
    // return Page.Variables.svUpdateStatusRFQ.invoke({
    //     "inputFields": {
    //         "rlsId": item.rlsId,
    //         "status": "closed"
    //     }
    // }).then(function(res) {
    //     return Page.Variables.vViewPurchseRequestItem.update()
    // }).then(function(res) {
    //     Page.Widgets.spinner1.show = false
    //     App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
    //     App.Actions.appNotification.setToasterClass("success")
    //     App.Actions.appNotification.getToasterDuration(5000)
    //     App.Actions.appNotification.invoke()
    // }).catch(function(err) {
    //     console.log("error", err)
    //     Page.Widgets.spinner1.show = false
    // })
};


Page.listDeptId = function(data) {
    return data.user_department.map(function(item) {
        return item.departmentId
    })
}