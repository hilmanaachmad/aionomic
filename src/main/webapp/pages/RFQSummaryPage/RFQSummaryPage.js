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

    Page.Variables.modelRfqVendorList.clearData()
    if (!Page.pageParams.rfqId) {
        return
    }

    // department handle
    var userDepartment = App.Variables.loggedInUserData.dataSet.user_department
    var departments = []
    if (userDepartment.length > 0) {
        var departments = []

        userDepartment.map(dep => {
            departments.push(dep.departmentId)
        })
    }

    //authorization
    // if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
    //     if (App.Variables.loggedInUser.dataSet.roles.indexOf('RFQ-004') !== -1) {

    //     } else {
    //         App.Actions.goToPage_Main.invoke()
    //     }
    // }

    Page.Widgets.spinner2.show = true

    Page.Variables.qGetRfqSummary.invoke({
        "inputFields": {
            "rfqId": Page.pageParams.rfqId
        }
    }).then(function(data) {
        data = JSON.parse(data.body)
        if (data.content.length === 0) {
            return
        }

        var sorted = data.content.map(function(item) {
            return item.rfqvDuration
        }).sort(function(a, b) {
            return b - a;
        })

        data.content.map(function(item) {
            item.leadTime = sorted.indexOf(item.rfqvDuration) + 1
        })


        Page.Variables.modelRfqVendorList.setData(data.content)
    }).then(function() {
        if (departments.length === 0) {
            return Promise.resolve()
        }

        return Page.Variables.qGetRFQParameter.invoke({
            "inputFields": {
                "userDepartment ": departments
            }
        })
    }).then(function(res) {
        if (res) {
            res = JSON.parse(res.body)
            Page.Variables.modelParameterRFQ.setValue("quality", res.content[0].quality)
            Page.Variables.modelParameterRFQ.setValue("leadTime", res.content[0].leadTime)
            Page.Variables.modelParameterRFQ.setValue("totalPrice", res.content[0].price)
        }

        Page.Widgets.spinner2.show = false
    })
};

// cancel action
Page.button7Click = function($event, widget) {
    window.history.back()
};

// submit action
Page.button8_1Click = function($event, widget) {
    // console.log("form data", Page.Variables.modelRfqVendorList.dataSet)

    var empty = Page.Variables.modelRfqVendorList.dataSet.filter(x => x.quality == null || x.quality == '0')
    if (empty.length > 0) {
        return alert("Field quality is required")
    }

    var overwrite = Page.Variables.modelRfqVendorList.dataSet.filter(item => item.summaryOverwrite && !item.summaryReason)
    if (overwrite.length > 0) {
        return alert("Field summary reason is required for overwrite")
    }

    Page.Widgets.spinner2.show = true
    var formData = JSON.parse(JSON.stringify(Page.Variables.modelRfqVendorList.dataSet))

    return Promise.resolve().then(function() {
        return Page.updateMultiItem(formData.reverse())
    }).then(function() {
        var dataRFQ = Page.Variables.vdbRFQ.dataSet[0]
        dataRFQ.rfqStatus = 'Completed'

        return Page.Variables.vdbRFQ.updateRecord({
            row: dataRFQ
        })
    }).then(function() {
        var RLS = Page.Variables.modelRfqVendorList.dataSet
        return Page.Variables.qUpdateStatusRFQ.invoke({
            "inputFields": {
                "rlsId": RLS[0].rlsId,
                "status": 'Completed'
            }
        })
    }).then(function() {
        App.Actions.appNotification.setMessage("Data Saved")
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()

        Page.Widgets.spinner2.show = false
        App.Actions.goToPage_RFQTrackingPage.invoke()
    })
};

Page.updateMultiItem = function(data) {
    // exit recursive
    if (!data || !data.length) {
        return Promise.resolve()
    }

    // loop till data empty
    var dt = data.pop()
    return Page.Variables.vdbRFQVendor.listRecords({
        "filterFields": {
            "rfqvId": {
                "value": dt.rfqvId
            }
        }
    }).then(function(res) {
        console.log('res', res)
        var dataItem = res.data[0]
        dataItem.rfqvQuality = dt.quality
        dataItem.rfqvSummaryOverwrite = dt.summaryOverwrite
        dataItem.rfqvSummaryReason = dt.summaryReason
        console.log('dataitem bedore overwritten', dataItem)
        if (dt.summaryOverwrite) {
            dataItem.rfqvRank = Page.summaryOverwriteRank(dt, Page.Variables.modelRfqVendorList.dataSet)
            console.log("overwritten to", Page.summaryOverwriteRank(dt, Page.Variables.modelRfqVendorList.dataSet))
        } else {
            console.log("not overwritten")
            dataItem.rfqvRank = Page.summaryRank(dt, Page.Variables.modelRfqVendorList.dataSet)
        }

        console.log('dataitem after overwritten', dataItem)
        return Page.Variables.vdbRFQVendor.updateRecord({
            row: dataItem
        })
    }).then(function() {
        console.log('next')
        return Page.updateMultiItem(data)
    }).catch(function(err) {
        console.log('err', err)
        return Page.updateMultiItem(data)
    })
};

// change quantity value
Page.text3Keyup = function($event, widget, item, currentItemWidgets) {
    var quality = Page.numberFormat(widget.datavalue)

    if (quality.length > 1) {
        quality = quality.substr(1)
    }
    if (parseInt(quality) > 5) {
        quality = '5'
    }

    widget.datavalue = quality
    Page.Variables.modelRfqVendorList.dataSet.find(x => x.rfqvId == Page.Widgets.list3.selecteditem.rfqvId).quality = widget.datavalue
};
Page.text4Keyup = function($event, widget, item, currentItemWidgets) {
    widget.datavalue = widget.datavalue.replace(/\D/g, '')
    Page.Variables.modelRfqVendorList.dataSet.find(x => x.rfqvId == Page.Widgets.list3.selecteditem.rfqvId).summaryOverwrite = widget.datavalue
};
Page.text6Keyup = function($event, widget, item, currentItemWidgets) {
    Page.Variables.modelRfqVendorList.dataSet.find(x => x.rfqvId == Page.Widgets.list3.selecteditem.rfqvId).summaryReason = widget.datavalue
};

Page.summaryRank = function(currentItem, allItem) {
    var quality = Page.Variables.modelParameterRFQ.dataSet.quality
    var leadTime = Page.Variables.modelParameterRFQ.dataSet.leadTime
    var totalPrice = Page.Variables.modelParameterRFQ.dataSet.totalPrice

    allItem = Page.Variables.modelRfqVendorList.dataSet

    var currentQuality = currentItem.quality ? currentItem.quality : 0
    var totalCurrent = (quality * parseInt(currentQuality) / 100) + (leadTime * parseInt(currentItem.leadTime) / 100) + (totalPrice * parseInt(currentItem.totalPrice) / 100)

    var totalAll = allItem.map(function(item) {
        item.quality = item.quality ? item.quality : 0
        return (quality * parseInt(item.quality) / 100) + (leadTime * parseInt(item.leadTime) / 100) + (totalPrice * parseInt(item.totalPrice) / 100)
    }).sort(function(a, b) {
        return b - a
    })

    return totalAll.indexOf(totalCurrent) + 1
};


Page.summaryOverwriteRank = function(currentItem, allItem) {
    allItem = Page.Variables.modelRfqVendorList.dataSet

    var sortirItem = allItem.map(function(item) {
        return item.summaryOverwrite
    }).sort(function(a, b) {
        return a - b
    })
    console.log('all', allItem);
    console.log('current', currentItem);
    console.log('sort', sortirItem);

    return sortirItem.indexOf(currentItem.summaryOverwrite) + 1
};

Page.vdbRFQonSuccess = function(variable, data) {
    if (Array.isArray(data)) {
        if (data[0].rfqSumAttachment) {
            Page.Variables.modelAttachmentList.setData(JSON.parse(atob(data[0].rfqSumAttachment)))
        }
    }
};
Page.container49_2Click = function($event, widget, item, currentItemWidgets) {
    Page.Widgets.spinner2.show = true

    Page.Variables.FileServiceDownloadFile.invoke({
        "inputFields": {
            "file": Page.Widgets.listAttachment.selecteditem.rfqSumAttachment,
            "returnName": Page.Widgets.listAttachment.selecteditem.rfqSumAttachment
        }
    }).then(function() {
        Page.Widgets.spinner2.show = false
    })
};

Page.numberFormat = function(newVal) {
    newVal = newVal.replace(/\D/g, '')
    if (newVal.charAt(0) == '0' && newVal.length > 0) {
        newVal = newVal.substr(1)
    }
    if (newVal == '') {
        newVal = '0'
    }
    return newVal;
};

Page.button3Click = function($event, widget) {
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('Dept, Div & Cost Center Report');;
    let columnWidth = [12, 8, 11, 23];

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

    let data = Page.Variables.qGetRfqSummary.dataSet
    let row = 2;


    // header
    sheet.getCell("A1").value = "RFQ Number";
    sheet.getCell("B1").value = "Line Items";
    sheet.getCell("C1").value = "Description";
    sheet.getCell("D1").value = "Vendor Code";
    sheet.getCell("E1").value = "Vendor Name";
    sheet.getCell("F1").value = "Total Price 30%";
    sheet.getCell("G1").value = "Quality 20%";
    sheet.getCell("H1").value = "Lead Time 50%";
    sheet.getCell("I1").value = "Summary (Rank)";
    sheet.getCell("J1").value = "Summary (Overwrite)";
    sheet.getCell("K1").value = "Reason Overwrite";

    boldCell(["A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1"]);
    sheet.getRow(1).alignment = alignCenter;


    data.forEach(el => {
        sheet.getCell("A" + row).value = el.rfqNumber.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("B" + row).value = el.item
        sheet.getCell("C" + row).value = el.description.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("D" + row).value = el.vendorCode.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("E" + row).value = el.vendorName.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("F" + row).value = el.totalPrice
        sheet.getCell("G" + row).value = el.quality
        sheet.getCell("H" + row).value = el.leadTime
        sheet.getCell("I" + row).value = Page.summaryRank(el.summaryRank, data)
        sheet.getCell("J" + row).value = el.summaryOverwrite
        sheet.getCell("K" + row).value = el.summaryOverwrite
        sheet.getCell("L" + row).value = el.summaryReason
        row++;
    });


    // header
    row = row + 4
    sheet.getCell("A" + row).value = "RFQ Number";
    sheet.getCell("B" + row).value = "Line Items";
    sheet.getCell("C" + row).value = "Description";
    sheet.getCell("D" + row).value = "Vendor Code";
    sheet.getCell("E" + row).value = "Vendor Name";
    sheet.getCell("F" + row).value = "Unit Price";
    sheet.getCell("G" + row).value = "Total Price";
    sheet.getCell("H" + row).value = "TOP";
    sheet.getCell("I" + row).value = "Lead Time";

    boldCell(["A" + row, "B" + row, "C" + row, "D" + row, "E" + row, "F" + row, "G" + row, "H" + row, "I" + row]);
    sheet.getRow(1).alignment = alignCenter;

    row = row + 1
    data.forEach(el => {
        sheet.getCell("A" + row).value = el.rfqNumber.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("B" + row).value = el.item
        sheet.getCell("C" + row).value = el.description.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("D" + row).value = el.vendorCode.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("E" + row).value = el.vendorName.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("F" + row).value = el.unitPrice
        sheet.getCell("G" + row).value = el.totalPrice
        sheet.getCell("H" + row).value = el.top.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("I" + row).value = el.leadTime
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
        saveAs(blob, "QuotationSummary_" + currentTime + ".xlsx");
    })
};