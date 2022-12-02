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
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('MST-011') !== -1) {

        } else {
            App.Actions.goToPage_Main.invoke();
        }
    }
};

var selectedItem;

// export excel
Page.button2Click = function($event, widget) {
    // intial
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('RFQ Parameter');;
    let columnWidth = [12, 20, 10, 10, 10, 12];

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

    let data = Page.Variables.vdbMappingRFQ.dataSet
    let row = 2;


    // header
    sheet.getCell("A1").value = App.appLocale["LANG_YEAR"];
    sheet.getCell("B1").value = App.appLocale["LANG_PURCHASE_ORGANIZATION"];
    sheet.getCell("C1").value = App.appLocale["LANG_TOTAL_PRICE"];
    sheet.getCell("D1").value = App.appLocale["LANG_QUALITY"];
    sheet.getCell("E1").value = App.appLocale["LANG_LEAD_TIME"];
    sheet.getCell("F1").value = App.appLocale["LANG_STATUS"];
    boldCell(["A1", "B1", "C1", "D1", "E1", "F1"]);
    sheet.getRow(1).alignment = alignCenter;


    data.forEach(el => {
        sheet.getCell("A" + row).value = el.rfqYear.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("B" + row).value = el.purchOrg.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("C" + row).value = el.price
        sheet.getCell("D" + row).value = el.quality
        sheet.getCell("E" + row).value = el.leadTime
        sheet.getCell("F" + row).value = el.status.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        row++;
    });


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
        saveAs(blob, "RFQParameter" + currentTime + ".xlsx");
    })
};

// Change value list display
Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.vdbMappingRFQ.maxResults = newVal
    Page.Variables.vdbMappingRFQ.invoke()
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.vdbMappingRFQ.orderBy == field + " ASC") {
        Page.Variables.vdbMappingRFQ.orderBy = field + " DESC"
    } else if (Page.Variables.vdbMappingRFQ.orderBy == field + " DESC") {
        Page.Variables.vdbMappingRFQ.orderBy = ""
    } else {
        Page.Variables.vdbMappingRFQ.orderBy = field + " ASC"
    }
    Page.Variables.vdbMappingRFQ.invoke()
}

//onclic colom colom_header
Page.container28Click = function($event, widget) {
    Page.toggleTableSort("rfqYear")
};
Page.container30Click = function($event, widget) {
    Page.toggleTableSort("purchOrg")
};
Page.container29Click = function($event, widget) {
    Page.toggleTableSort("status")
};

Page.selectSearchByChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.searchValue.datavalue = ""
    // Page.Variables.qGetMappingToUser.invoke()
};

// RFQ Parameter 

Page.val_priceChange = function($event, widget, newVal, oldVal) {
    Page.calculate();
};
Page.val_qualityChange = function($event, widget, newVal, oldVal) {
    Page.calculate();
};
Page.val_leadtimeChange = function($event, widget, newVal, oldVal) {
    Page.calculate();
};

Page.calculate = function() {
    let price = Page.Widgets.val_price.datavalue;
    let lead = Page.Widgets.val_leadtime.datavalue;
    let quality = Page.Widgets.val_quality.datavalue;
    let hasil = price + lead + quality;

    Page.Widgets.val_persentase.caption = hasil;
}





Page.SubmitbtnClick = function($event, widget) {
    if (Page.Widgets.select_purchase_org.datavalue == undefined || Page.Widgets.select_purchase_org.datavalue == "") {
        //validasi tahun
        App.Actions.appNotification.setMessage("Select Purchase Organization")
        App.Actions.appNotification.setToasterClass("error")
        App.Actions.appNotification.getToasterDuration(3000)
        App.Actions.appNotification.invoke();
        return false;
    } else if (Page.Widgets.val_persentase.caption != '100') {
        //validasi persentase tidak bulat
        App.Actions.appNotification.setMessage("total weight must be round within 100%")
        App.Actions.appNotification.setToasterClass("warning")
        App.Actions.appNotification.getToasterDuration(3000)
        App.Actions.appNotification.invoke();
        return false;
    } else {
        // Action to save

        if (Page.Variables.vmdialogStatus.dataSet.dataValue == App.appLocale["LANG_NEW_RFQ_PARAMETER"]) {
            var ada = Page.Variables.vdbMappingRFQ.dataSet.filter(x => Number(x.rfqYear) == Page.Widgets.rfq_year.datavalue && x.purchOrg == Page.Widgets.select_purchase_org.datavalue)
            if (ada.length > 0) {
                App.Actions.appNotification.setMessage("data mapping already exists !")
                App.Actions.appNotification.setToasterClass("warning")
                App.Actions.appNotification.getToasterDuration(3000)
                App.Actions.appNotification.invoke();
                return false;
            } else {
                Page.Variables.insertRFQ.invoke({
                    inputFields: {
                        rfqYear: Page.Widgets.rfq_year.datavalue,
                        purchOrg: Page.Widgets.select_purchase_org.datavalue,
                        purchOrgDesc: Page.Widgets.select_purchase_org.displayValue,
                        price: Page.Widgets.val_price.datavalue,
                        quality: Page.Widgets.val_quality.datavalue,
                        leadTime: Page.Widgets.val_leadtime.datavalue,
                        status: 'active',
                    }
                }, function(res) {
                    Page.Variables.vdbMappingRFQ.invoke();
                    Page.Widgets.MapRFQParameter.close();
                    App.Actions.appNotification.setMessage("Data Saved Successfully")
                    App.Actions.appNotification.setToasterClass("success")
                    App.Actions.appNotification.setToasterDuration(4000)
                    App.Actions.appNotification.invoke()
                });
            }

        } else if (Page.Variables.vmdialogStatus.dataSet.dataValue == App.appLocale["LANG_EDIT_RFQ_PARAMETER"]) {

            var ada = Page.Variables.vdbMappingRFQ.dataSet.filter(x => Number(x.rfqYear) == Page.Widgets.rfq_year.datavalue &&
                x.purchOrg == Page.Widgets.select_purchase_org.datavalue)
            if (ada.length > 0) {
                if (ada[0].rfqYear == Number(selectedItem.rfqYear) && ada[0].purchOrg == selectedItem.purchOrg) {
                    Page.simpanEdit();
                } else {
                    App.Actions.appNotification.setMessage("data mapping already exists !")
                    App.Actions.appNotification.setToasterClass("warning")
                    App.Actions.appNotification.getToasterDuration(3000)
                    App.Actions.appNotification.invoke();
                    return false;
                }
            } else {
                Page.simpanEdit();
            }
        }
    }
};


//Fun Simpan edit 
Page.simpanEdit = function() {

    Page.Variables.updateMappingRFQ.invoke({
        inputFields: {
            rfqId: Number(selectedItem.rfqId),
            TblMRfqParameter: {
                rfqYear: Page.Widgets.rfq_year.datavalue,
                purchOrg: Page.Widgets.select_purchase_org.datavalue,
                purchOrgDesc: Page.Widgets.select_purchase_org.displayValue,
                price: Page.Widgets.val_price.datavalue,
                quality: Page.Widgets.val_quality.datavalue,
                leadTime: Page.Widgets.val_leadtime.datavalue,
                status: Page.Widgets.toggle1.datavalue,
            }
        }
    }, function(res) {
        Page.Variables.vdbMappingRFQ.invoke();
        Page.Widgets.MapRFQParameter.close();
        App.Actions.appNotification.setMessage("Data Saved Successfully")
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.setToasterDuration(4000)
        App.Actions.appNotification.invoke()
    });
}

//On Render List RFQ
Page.RFQParameterBeforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.vdbMappingRFQ.pagination.number * Page.Variables.vdbMappingRFQ.pagination.size) + (i + 1);
    }
};



Page.MapRFQParameterOpened = function($event, widget) {
    var a = new Yearpicker(Page.Widgets.rfq_year.inputEl.nativeElement, {
        onChange: function(year) {
            Page.Widgets.rfq_year.datavalue = year
        }
    })
    const dialogStatus = Page.Variables.vmdialogStatus.dataSet.dataValue;
    switch (dialogStatus) {
        case App.appLocale["LANG_NEW_RFQ_PARAMETER"]:
            Page.Widgets.container33_1.show = false;
            break;
        case App.appLocale["LANG_EDIT_RFQ_PARAMETER"]:
            Page.Widgets.container33_1.show = true;
            Page.Widgets.rfq_year.datavalue = selectedItem.rfqYear;
            Page.Widgets.select_purchase_org.datavalue = selectedItem.purchOrg;
            Page.Widgets.val_price.datavalue = selectedItem.price;
            Page.Widgets.val_quality.datavalue = selectedItem.quality;
            Page.Widgets.val_leadtime.datavalue = selectedItem.leadTime;
            Page.Widgets.val_persentase.caption = selectedItem.price + selectedItem.quality + selectedItem.leadTime;
            break;
        default:
    }
};

Page.dialogDeleteOk = function($event, widget) {

    Page.Variables.deleteMapRFQ.invoke({
        inputFields: {
            rfqId: selectedItem.rfqId
        }
    }, function(res) {
        Page.Variables.vdbMappingRFQ.invoke();
    });
    widget.close();
    App.Actions.appNotification.setMessage("Data Deleted Successfully")
    App.Actions.appNotification.setToasterClass("success")
    App.Actions.appNotification.getToasterDuration(5000)
    App.Actions.appNotification.invoke()
};

//Search Feature
Page.searchValueChange = function($event, widget, newVal, oldVal) {
    console.log(Page.Variables.vdbMappingRFQ.filterExpressions)
    console.log(Page.Widgets.searchValue.datavalue)
    console.log(Page.Widgets.selectSearchBy.datavalue)
    switch (Page.Widgets.selectSearchBy.datavalue) {
        case 1:
            Page.Variables.vdbMappingRFQ.filterExpressions.rules[0].value = Page.Widgets.searchValue.datavalue;
            Page.Variables.vdbMappingRFQ.filterExpressions.rules[1].value = '';
            Page.Variables.vdbMappingRFQ.filterExpressions.rules[2].value = '';

            break;
        case 2:
            Page.Variables.vdbMappingRFQ.filterExpressions.rules[0].value = '';
            Page.Variables.vdbMappingRFQ.filterExpressions.rules[1].value = Page.Widgets.searchValue.datavalue;
            Page.Variables.vdbMappingRFQ.filterExpressions.rules[2].value = '';
            break;
        case 3:
            Page.Variables.vdbMappingRFQ.filterExpressions.rules[0].value = '';
            Page.Variables.vdbMappingRFQ.filterExpressions.rules[1].value = '';
            Page.Variables.vdbMappingRFQ.filterExpressions.rules[2].value = Page.Widgets.searchValue.datavalue;
            break;
        default:
            Page.Variables.vdbMappingRFQ.filterExpressions.rules[0].value = '';
            Page.Variables.vdbMappingRFQ.filterExpressions.rules[1].value = '';
            Page.Variables.vdbMappingRFQ.filterExpressions.rules[2].value = '';
            break;
    }
    Page.Variables.vdbMappingRFQ.invoke()
};
Page.buttonAddClick = function($event, widget) {
    Page.Variables.vmdialogStatus.dataSet.dataValue = App.appLocale["LANG_NEW_RFQ_PARAMETER"];
    Page.Widgets.MapRFQParameter.open();
};
Page.picture5Click = function($event, widget, item, currentItemWidgets) {
    selectedItem = item;
    Page.Widgets.dialogDelete.open();
};
Page.EditbtnClick = function($event, widget, item, currentItemWidgets) {
    selectedItem = item;
    Page.Variables.vmdialogStatus.dataSet.dataValue = App.appLocale["LANG_EDIT_RFQ_PARAMETER"];
    console.log(selectedItem)
    Page.Widgets.MapRFQParameter.open();
};
