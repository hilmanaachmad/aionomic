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
     * 'Page.Widgets.username.datavalue'RFQSelectVendor
     */

    // // authorization handle
    // if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
    //     if (App.Variables.loggedInUser.dataSet.roles.indexOf('RFQ-002') == -1) {
    //         App.Actions.goToPage_Main.invoke()
    //     }
    // }


    // event listener on click outside filter
    Page.$page[0].addEventListener("click", function() {
        $('.aio-ptp-dropdown:not(.clicked)').removeClass("active")
        $('.aio-ptp-dropdown').removeClass("clicked")
    })

}

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

Page.listMaterialGrouptoString = function(data) {
    return data.map(function(item) {
        return item.materialGroupId
    })
}

Page.listMaterialGrouptoString2 = function(data) {
    return data.map(function(item) {
        return item.materialGroup
    })
}


// Page.btnExportExcellClick = function($event, widget) {
//     Page.Widgets.spinner1.show = true
//     var wsData = Page.Variables.vViewMaterialGroupList.dataSet
//     const workbook = new ExcelJS.Workbook();
//     for (var i = 0; i < wsData.length; i++) {
//         var dataLi = Page.Variables.vViewPurchseRequestItemExport.dataSet.filter(function(el) {
//             return el.materialGroup == wsData[i].materialGroup
//         })
//         // console.log("dataLi", dataLi)
//         if (dataLi.length > 0) {
//             let sheet = workbook.addWorksheet(wsData[i].materialGroupDesc);

//             var xls_content = [
//                 [null],
//                 ["Status : " + ((Page.Widgets.checkboxFilterStatusAll.datavalue === true || Page.Widgets.checkboxFilterStatusAll.datavalue === undefined) ? "All" : Page.Widgets.checkboxsetFilterStatus.datavalue),
//                     null,
//                     "Material Group : " + wsData[i].materialGroupDesc,
//                     null,
//                     "Purchase by : " + Page.Widgets.selectPurchaseBy.displayValue,
//                     null,
//                     (Page.Widgets.selectPurchaseBy.datavalue === 'User' && Page.Widgets.searchCreator.datavalue !== "") ? ("Creator : " + Page.Widgets.searchCreator.query) : (Page.Widgets.selectPurchaseBy.datavalue === 'User' && Page.Widgets.searchCreator.datavalue === "") ? "Creator : All" : null
//                 ],
//                 [null],
//                 [null],
//                 [null]
//             ]
//             var xls_header = [
//                 "PR Number",
//                 "PR Item No",
//                 "PR Description",
//                 "Quantity",
//                 "UOM",
//                 "RFQ Number",
//                 "RFQ Status"
//             ]
//             xls_content.push(xls_header)

//             dataLi.forEach(function(item) {
//                 var dataItem = [
//                     item.prNumber,
//                     item.item,
//                     item.prDescription,
//                     item.qty,
//                     item.uom,
//                     // item.eta.split("-")[2] + "-" + item.eta.split("-")[1] + "-" + item.eta.split("-")[0],
//                     item.rfqNumber,
//                     item.rfqStatus
//                 ]
//                 xls_content.push(dataItem)
//             })

//             sheet.addRows(xls_content)

//             // styling
//             let columnWidth = [30, 30, 30, 15, 15, 15, 20, 20];
//             sheet.columns.forEach((col, index) => {
//                 if (columnWidth[index]) {
//                     col.width = columnWidth[index];
//                 }
//             });
//             let alignCenter = {
//                 vertical: 'middle',
//                 'horizontal': 'center'
//             };
//             sheet.getRow(1).alignment = alignCenter;

//             let boldCell = function(cells) {
//                 cells.forEach(el => {
//                     sheet.getCell(el).font = {
//                         bold: true
//                     }
//                 });
//                 return;
//             }
//             boldCell(["A6", "B6", "C6", "D6", "E6", "F6", "G6", "A2", "C2", "E2", "G2"]);
//         }
//     }

//     // exporting
//     workbook.xlsx.writeBuffer().then(function(datas) {
//         var filename = "Good/Service RFQ";
//         var blob = new Blob([datas], {
//             type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
//         });

//         let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
//         saveAs(blob, filename + currentTime + ".xlsx");
//     })
//     Page.Widgets.spinner1.show = false
// };


Page.btnExportExcellClick = function($event, widget) {
    Page.Widgets.spinner1.show = true
    var DataList = Page.Variables.vViewPurchseRequestItemExport.dataSet
    const workbook = new ExcelJS.Workbook();

    let sheet = workbook.addWorksheet("Ready for Quotation");

    var xls_content = [
        [null],
        ["Status : " + ((Page.Widgets.checkboxFilterStatusAll.datavalue === true || Page.Widgets.checkboxFilterStatusAll.datavalue === undefined) ? "All" : Page.Widgets.checkboxsetFilterStatus.datavalue),
            null,
            // "Material Group : " + wsData[i].materialGroupDesc,
            null,
            null,
            "Purchase by : " + Page.Widgets.selectPurchaseBy.displayValue,
            null,
            (Page.Widgets.selectPurchaseBy.datavalue === 'User' && Page.Widgets.searchCreator.datavalue !== "") ? ("Creator : " + Page.Widgets.searchCreator.query) : (Page.Widgets.selectPurchaseBy.datavalue === 'User' && Page.Widgets.searchCreator.datavalue === "") ? "Creator : All" : null
        ],
        [null],
        [null],
        [null]
    ]

    var xls_header = [
        "Material Group",
        "PR Number",
        "PR Item No",
        "PR Description",
        "Quantity",
        "UOM",
        "ETA",
        "RFQ Number",
        "RFQ Status"
    ]
    xls_content.push(xls_header)

    DataList.forEach(function(item) {
        var dataItem = [
            item.materialGroup,
            item.prNumber,
            item.item,
            item.prDescription,
            item.qty,
            item.uom,
            item.eta,
            item.rfqNumber,
            item.rfqStatus
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


    // exporting
    workbook.xlsx.writeBuffer().then(function(datas) {
        var filename = "Good/Service RFQ";
        var blob = new Blob([datas], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        });

        let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
        saveAs(blob, filename + currentTime + ".xlsx");
    })
    Page.Widgets.spinner1.show = false
}

Page.ExportExcelClick = function($event, widget) {
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('Budget Detail 1');;
    let columnWidth = [5, 35, 5, 20, 5, 20, 20, 20, 5, 20, 5, 20, 20, 20, 20];

    let alignCenter = {
        vertical: 'middle',
        'horizontal': 'center'
    };

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

    let data = ObjFilter;
    let row = 14;


    //style

    sheet.getColumn('C').alignment = alignCenter;
    sheet.getRow(11).alignment = alignCenter;
    sheet.getRow(12).alignment = alignCenter;

    sheet.mergeCells('B11:B12');
    sheet.mergeCells('D11:D12');
    sheet.mergeCells('J11:J12');
    sheet.mergeCells('L11:L12');
    sheet.mergeCells('M11:M12');
    sheet.mergeCells('N11:N12');
    sheet.mergeCells('O11:O12');
    sheet.mergeCells('F11:H11');

    // header
    sheet.getCell("B4").value = "Budget Year";
    sheet.getCell("B5").value = "Company";
    sheet.getCell("B6").value = "Department";
    sheet.getCell("B7").value = "Brands";
    sheet.getCell("B8").value = "Account";
    sheet.getCell("B9").value = "Currency";

    sheet.getCell("C4").value = ":";
    sheet.getCell("C5").value = ":";
    sheet.getCell("C6").value = ":";
    sheet.getCell("C7").value = ":";
    sheet.getCell("C8").value = ":";
    sheet.getCell("C9").value = ":";

    sheet.getCell("D4").value = Page.Widgets.budgetYear.datavalue;
    if (Page.Widgets.slct_company.displayValue != undefined) {
        sheet.getCell("D5").value = Page.Widgets.slct_company.displayValue.toUpperCase();
    }

    if (Page.Widgets.slct_department.displayValue != undefined) {
        sheet.getCell("D6").value = Page.Widgets.slct_department.displayValue.toUpperCase();
    }

    if (Page.Widgets.slct_brand.displayValue != undefined) {
        sheet.getCell("D7").value = Page.Widgets.slct_brand.displayValue.toUpperCase();
    }

    if (Page.Widgets.slct_account_type.displayValue != undefined) {
        sheet.getCell("D8").value = Page.Widgets.slct_account_type.displayValue.toUpperCase();
    }

    sheet.getCell("D9").value = "IDR";

    sheet.getCell("B11").value = "IO Number";
    sheet.getCell("D11").value = "Budget Original";
    sheet.getCell("F11").value = "Total Adjustment";
    sheet.getCell("F12").value = "Reclass";
    sheet.getCell("G12").value = "Additional";
    sheet.getCell("H12").value = "Adjustment";
    sheet.getCell("J11").value = "After Adjustment";
    sheet.getCell("L11").value = "Reserved";
    sheet.getCell("M11").value = "Commitment";
    sheet.getCell("N11").value = "Actual";
    sheet.getCell("O11").value = "Available";


    sheet.getCell("L4").value = new Date().getDate().toString().padStart(2, "0") + '/' + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + '/' + new Date().getFullYear();
    sheet.getCell("L5").value = "This Data autogenerated by system";



    boldCell(["A11", "B11", "C11", "D11", "E11", "F12", "F11", "G12", "H12", "I11", "J11", "K11", "L11", "M11", "N11", "O11", "P11"]);
    sheet.getCell('B1').alignment = alignCenter;


    var data_border = ["B11", "D11", "F11", "J11", "L11", "M11", "N11", "O11", "F12", "G12", "H12"];

    data.forEach(el => {
        // sheet.getCell("A" + row).value = el.departmentTitle.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("B" + row).value = el.ioNumber + ' - ' + el.ioName.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");
        sheet.getCell("D" + row).value = el.budgetOriginal;
        sheet.getCell("D" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("F" + row).value = el.budgetReclass;
        sheet.getCell("F" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("G" + row).value = el.budgetAdditional;
        sheet.getCell("G" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("H" + row).value = el.budgetAdjusment;
        sheet.getCell("H" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("J" + row).value = el.budgetAfterAdjusment;
        sheet.getCell("J" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("L" + row).value = el.budgetPr;
        sheet.getCell("L" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("M" + row).value = el.budgetPo;
        sheet.getCell("M" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("N" + row).value = el.budgetGr;
        sheet.getCell("N" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("O" + row).value = el.budgetAvailable;
        sheet.getCell("O" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        data_border.push("B" + row, "D" + row, "F" + row, "G" + row, "H" + row, "J" + row, "L" + row, "M" + row, "N" + row, "O" + row);
        // sheet.getCell("D" + row).value = el.omStatus.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        row++;
    });



    sheet.getCell("B" + (row + 1)).value = "TOTAL";
    sheet.getCell("D" + (row + 1)).value = Page.Variables.vmTotalValue.dataSet.totalBudgetOriginal;
    sheet.getCell("D" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("F" + (row + 1)).value = Page.Variables.vmTotalValue.dataSet.totalReclass;
    sheet.getCell("F" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("G" + (row + 1)).value = Page.Variables.vmTotalValue.dataSet.totalAdditional;
    sheet.getCell("G" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("H" + (row + 1)).value = Page.Variables.vmTotalValue.dataSet.totalAdjustment;
    sheet.getCell("H" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("J" + (row + 1)).value = Page.Variables.vmTotalValue.dataSet.totalAfterAdjustment;
    sheet.getCell("J" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("L" + (row + 1)).value = Page.Variables.vmTotalValue.dataSet.totalReserved;
    sheet.getCell("L" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("M" + (row + 1)).value = Page.Variables.vmTotalValue.dataSet.totalCommitment;
    sheet.getCell("M" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("N" + (row + 1)).value = Page.Variables.vmTotalValue.dataSet.totalActual;
    sheet.getCell("N" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("O" + (row + 1)).value = Page.Variables.vmTotalValue.dataSet.totalAvailable;
    sheet.getCell("O" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    data_border.push("B" + (row + 1), "D" + (row + 1), "F" + (row + 1), "G" + (row + 1), "H" + (row + 1), "J" + (row + 1), "L" + (row + 1), "M" + (row + 1), "N" + (row + 1), "O" + (row + 1));
    //border
    data_border.map(key => {
        sheet.getCell(key).border = {
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
        }
    })


    // Adjust column width
    sheet.columns.forEach((col, index) => {
        if (columnWidth[index]) {
            col.width = columnWidth[index];
        }
    });

    // exporting
    workbook.xlsx.writeBuffer().then(function(datas) {
        var blob = new Blob([datas], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        });

        let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
        saveAs(blob, "BudgetDetail1" + currentTime + ".xlsx");
    })
};

Page.container111_1Click = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').addClass("clicked")
};
Page.container112_1Click = Page.container111_1Click

Page.textFilterStatusClick = function($event, widget) {
    widget.$element.closest('.aio-ptp-dropdown').toggleClass("active")
};
Page.textFilterMaterialGroupClick = Page.textFilterStatusClick

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

Page.btnApplyClick = function($event, widget) {
    // Page.Variables.vViewMaterialGroupList.invoke()
    Page.Variables.vViewPurchseRequestItem.invoke()
};
Page.label28Click = function($event, widget, item, currentItemWidgets) {
    // Page.Variables.vViewPurchseRequestItem.invoke()
};

Page.checkboxListAllChange = function($event, widget, newVal, oldVal) {
    if (newVal === true) {
        // Page.Widgets.checkboxList.datavalue = true
        Page.Widgets.listPRI.listItems._results.filter(function(el) {
            return el.item.rfqStatus == 'open'
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
    Page.Variables.vViewPurchseRequestItem.maxResults = newVal
    Page.Variables.vViewPurchseRequestItem.update()
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.vViewPurchseRequestItem.orderBy == field + " ASC") {
        Page.Variables.vViewPurchseRequestItem.orderBy = field + " DESC"
    } else if (Page.Variables.vViewPurchseRequestItem.orderBy == field + " DESC") {
        Page.Variables.vViewPurchseRequestItem.orderBy = ""
    } else {
        Page.Variables.vViewPurchseRequestItem.orderBy = field + " ASC"
    }
    Page.Variables.vViewPurchseRequestItem.invoke()
}

Page.container40Click = function($event, widget) {
    Page.toggleTableSort('prNumber')
};
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('item')
};
Page.container42Click = function($event, widget) {
    Page.toggleTableSort('prDescription')
};
Page.container43Click = function($event, widget) {
    Page.toggleTableSort('qty')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('uom')
};
Page.container45Click = function($event, widget) {
    Page.toggleTableSort('eta')
};
Page.container58Click = function($event, widget) {
    Page.toggleTableSort('rfqNumber')
};
Page.container59Click = function($event, widget) {
    Page.toggleTableSort('rfqStatus')
};
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
    if (!confirm("Are you sure to close this line item quotation ?")) {
        return
    }
    Page.Widgets.spinner1.show = true
    return Page.Variables.svUpdateStatusRFQ.invoke({
        "inputFields": {
            "rlsId": item.rlsId,
            "status": "closed"
        }
    }).then(function(res) {
        return Page.Variables.vViewPurchseRequestItem.update()
    }).then(function(res) {
        Page.Widgets.spinner1.show = false
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    }).catch(function(err) {
        console.log("error", err)
        Page.Widgets.spinner1.show = false
    })
};
Page.buttonSubmitRFQClick = function($event, widget) {
    var dataChecklist = Page.Widgets.listPRI.listItems._results.filter(function(el) {
        return el.currentItemWidgets.checkboxList.datavalue === true
    }).map(function(dt) {
        return dt.item.liId
    }).join(",")

    console.log(Page.Variables.MODELSelectedItem)

    Page.Actions.goToPageSelectVendor.invoke({
        data: {
            "Liid": dataChecklist,
            // "Materialid": Page.Widgets.VPtpMaterialGroupList.selecteditem.materialGroup
            "Materialid": Page.Variables.MODELSelectedItem.dataSet.length > 0 ? Page.Variables.MODELSelectedItem.dataSet[0].materialGroup : Page.Widgets.VPtpMaterialGroupList.selecteditem.materialGroup
        }
    })
};

Page.listDeptId = function(data) {
    return data.user_department.map(function(item) {
        return item.departmentId
    })
}

Page.DBUserRolePurchaseonSuccess = function(variable, data) {
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

Page.DBMaterialGroupPurchasingonSuccess = function(variable, data) {
    if (data.length > 0) {
        Page.Variables.MODELPurchasingGroup.dataSet = data
    }
};

Page.vViewPurchseRequestItemonSuccess = function(variable, data) {};
Page.selectPurchaseByChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.searchCreator.datavalue = null
};


Page.checkboxListChange = function($event, widget, item, currentItemWidgets, newVal, oldVal) {
    if (newVal === true) {
        Page.Widgets.listPRI.listItems._results.map(function(item) {
            return item.currentItemWidgets.checkboxList.disabled = false
        })

        Page.Widgets.listPRI.listItems._results.filter(function(el) {
            return el.item.materialGroup !== item.materialGroup
        }).map(function(item) {
            return item.currentItemWidgets.checkboxList.disabled = true
        })
        Page.Widgets.listPRI.listItems._results.filter(function(el) {
            return el.item.rfqStatus !== 'open'
        }).map(function(item) {
            return item.currentItemWidgets.checkboxList.disabled = true
        })

        Page.Variables.MODELSelectedItem.dataSet.push({
            itemData: item.liId,
            materialGroup: item.materialGroup
        })
    } else {
        Page.Variables.MODELSelectedItem.dataSet = Page.Variables.MODELSelectedItem.dataSet.filter(function(item) {
            return item.liId !== item.liId
        })


        if (Page.Variables.MODELSelectedItem.dataSet.length === 0) {
            Page.Widgets.listPRI.listItems._results.map(function(item) {
                return item.currentItemWidgets.checkboxList.disabled = false
            })

            Page.Widgets.listPRI.listItems._results.filter(function(el) {
                return el.item.rfqStatus !== 'open'
            }).map(function(item) {
                return item.currentItemWidgets.checkboxList.disabled = true
            })
        }



    }

};