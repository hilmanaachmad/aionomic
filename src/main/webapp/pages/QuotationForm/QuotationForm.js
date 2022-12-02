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

    Page.Variables.modelLineItem.clearData()

    if (Page.pageParams.rfqvId) {
        Page.Widgets.spinner1.show = true

        return Promise.all([
            Page.Variables.vdbRfqVendor.invoke(),
            Page.Variables.viewItemQuotation.invoke()
        ]).then(function(res) {
            var quotation = res[0].data[0]
            var listLiQuotation = res[1].data
            // goto form view if status Submitted

            if (quotation.rfqvStatus == 'Submitted') {
                return Page.Actions.goto_QuotaionFormView.invoke()
            }

            // add data quotation to model
            Page.Variables.modelRfqVendor.setData(res[0].data[0])

            // add data line item to model
            for (var i = 0; i < listLiQuotation.length; i++) {
                listLiQuotation[i].nomor = i + 1
                Page.Variables.modelLineItem.addItem(listLiQuotation[i])
                if (listLiQuotation[i].amountAfterDiscount) {
                    Page.Variables.modelCalculation.setValue("subtotal", Page.Variables.modelCalculation.getValue("subtotal") + Math.round(listLiQuotation[i].amountAfterDiscount))
                }
            }

            if (quotation.rfqvAttachments) {
                var attachment = JSON.parse(atob(quotation.rfqvAttachments))
                Page.Variables.varListFile.setData(attachment)
            }
        }).then(function() {
            var nik = Page.Variables.modelRfqVendor.getValue('rfqvCreatedBy')
            return Page.Variables.vdbEmployee.listRecords({
                "filterFields": {
                    "nik": {
                        "value": nik.split('::')[1]
                    }
                }
            })
        }).then(function(res) {
            Page.Variables.modelRfqVendor.setValue("rfqvCreatedName", res.data[0].employeeName)
            Page.Widgets.spinner1.show = false
        })
    }
};

Page.parseInt = function(str) {
    if (!str) {
        return
    }

    return parseInt(str.toString().replace(/\D/g, '').replace(/\./g, ''))
}

Page.Math = Math

Page.buttonSubmitClick = function($event, widget) {
    var data = Page.Variables.modelRfqVendor.dataSet
    var dataItem = JSON.parse(JSON.stringify(Page.Variables.modelLineItem.dataSet))
    Page.Variables.modelErrors.clearData()
    Page.Widgets.spinner1.show = true
    var isValid = true
    var emailRegex = /\S+@\S+\.\S+/
    var file = Page.Variables.varListFile.dataSet.length > 0 ? btoa(JSON.stringify(Page.Variables.varListFile.dataSet)) : null

    if (!data.rfqvSalesPerson) {
        Page.Variables.modelErrors.setValue("rfqvSalesPerson", "this field is required")
        isValid = false
    }
    if (!data.rfqvSalesNumber) {
        Page.Variables.modelErrors.setValue("rfqvSalesNumber", "this field is required")
        isValid = false
    }
    if (!data.rfqvSalesEmail) {
        Page.Variables.modelErrors.setValue("rfqvSalesEmail", "this field is required")
        isValid = false
    } else if (!emailRegex.test(data.rfqvSalesEmail)) {
        Page.Variables.modelErrors.setValue("rfqvSalesEmail", 'Please enter a part followed by "@" and "."')
        isValid = false
    }
    if (!data.rfqvDate) {
        Page.Variables.modelErrors.setValue("rfqvDate", "this field is required")
        isValid = false
    }
    if (!data.rfqvWarranty) {
        Page.Variables.modelErrors.setValue("rfqvWarranty", "this field is required")
        isValid = false
    }
    if (!data.rfqvKursAssumption) {
        Page.Variables.modelErrors.setValue("rfqvKursAssumption", "this field is required")
        isValid = false
    }
    if (!data.rfqvDuration) {
        Page.Variables.modelErrors.setValue("rfqvDuration", "this field is required")
        isValid = false
    }

    if (!data.rfqvTermPayment) {
        Page.Variables.modelErrors.setValue("rfqvTOP", "this field is required")
        isValid = false
    }

    if (!Page.Widgets.selectTop.datavalue) {
        Page.Variables.modelErrors.setValue("selectTop", "this field is required")
        isValid = false
    }






    var itemdata = Page.Widgets.listItem.listItems._results
    for (var i = 0; i < itemdata.length; i++) {
        if (!itemdata[i].item.unitPrice) {
            App.Actions.appNotification.setMessage("the list item cannot be empty, check the list item again")
            App.Actions.appNotification.setToasterClass("warning")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            Page.Widgets.spinner1.show = false
            return false
        }
    }




    if (!isValid) {
        Page.Widgets.spinner1.show = false
        return
    }

    data.rfqvBasePrice = App.formatDecimalStr(data.rfqvBasePrice)
    data.rfqvOtherCost = App.formatDecimalStr(Page.Widgets.textOtherCost.datavalue);
    data.rfqvPackingCost = App.formatDecimalStr(Page.Widgets.textPackingCost.datavalue)
    data.rfqvShippingHandling = App.formatDecimalStr(Page.Widgets.textShipping.datavalue)
    data.rfqvVatRate = App.formatDecimalStr(Page.Widgets.textVatRate.datavalue)

    data.rfqvModifiedAt = new Date().toISOString()
    data.rfqvModifiedBy = App.Variables.loggedInUserData.dataSet.username
    data.rfqvStatus = 'Submitted'
    data.rfqvAttachments = file

    delete data.tblTrfq

    // default currency & kurs
    if (!data.rfqvCurrency && !data.rfqvCurrencyKurs || data.rfqvCurrency == 'IDR') {
        data.rfqvCurrency = 'IDR'
        data.rfqvCurrencyKurs = 1
    }

    // Page.Widgets.spinner1.show = false
    // console.log(data)
    Page.Variables.vdbRfqVendor.updateRecord({
        row: data
    }).then(function() {
        return Page.changeItemQuotation(dataItem.reverse())
    }).then(function() {
        return App.sendNotification("RFQ", data.rfqvCreatedBy, "<b>RFQ</b> - RFQ Submitted by Vendor")
    }).then(function() {
        App.Actions.appNotification.setMessage("Data Saved")
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()

        Page.Widgets.spinner1.show = false
        App.Actions.goToPage_VendorQuotationPage.invoke()
    })
};

Page.changeItemQuotation = function(data) {
    // exit recursive
    if (!data || !data.length) {
        return Promise.resolve()
    }

    var dt = data.pop()
    return Page.Variables.qUpdateItemQuotation.invoke({
        "inputFields": {
            "liqId": dt.id,
            "modifiedAt": new Date(),
            "modifiedBy": App.Variables.loggedInUserData.dataSet.username,
            "unitPrice": dt.unitPrice,
            "amount": dt.amount,
            "discount": dt.discount,
            "leadTime": dt.leadTime,
            "currency": Page.Variables.modelRfqVendor.dataSet.rfqvCurrency
        }
    }).then(function(res) {
        return Page.changeItemQuotation(data)
    }).catch(function(err) {
        return Page.changeItemQuotation(data)
    })
}

Page.buttonExportClick = function($event, widget) {
    // initial
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('Quotation Form');
    let filename = "Excel of Quotation Form"
    let columnWidth = [5, 20, 10, 15, 20, 13, 30, 13];
    var dataRFQVendor = Page.Variables.modelRfqVendor.dataSet
    var dataRFQItem = JSON.parse(JSON.stringify(Page.Variables.modelLineItem.dataSet))

    // styling
    let alignCenter = {
        vertical: 'middle',
        horizontal: 'center'
    }
    let alignRight = {
        vertical: 'middle',
        horizontal: 'right'
    }

    sheet.getCell("E2").value = "QUOTATION FORM";
    sheet.getCell("B5").value = "VENDOR INFORMATION";
    sheet.getCell("B6").value = dataRFQVendor.rfqvVendorCode + " - " + dataRFQVendor.rfqvVendorName;
    sheet.getCell("B7").value = dataRFQVendor.rfqvVendorAddress;
    sheet.getCell("B7").value = dataRFQVendor.rfqvVendorAddress;
    sheet.getCell("B9").value = "Sales Person : " + dataRFQVendor.rfqvSalesPerson;
    sheet.getCell("B10").value = "Sales Phone Number : " + dataRFQVendor.rfqvSalesNumber;
    sheet.getCell("B11").value = "Sales Email : " + dataRFQVendor.rfqvSalesEmail;
    sheet.getCell("B12").value = "Delivery Term : " + dataRFQVendor.rfqvTermDelivery;
    sheet.getCell("F14").value = "Quote No : ";
    sheet.getCell("G14").value = dataRFQVendor.tblTrfq.rfqRef;
    sheet.getCell("F14").alignment = alignRight;
    sheet.getCell("F15").value = "Date : ";
    sheet.getCell("G15").value = dataRFQVendor.rfqvDate;
    sheet.getCell("F15").alignment = alignRight;
    sheet.getCell("F16").value = "Valid Until : ";
    sheet.getCell("G16").value = dataRFQVendor.rfqvValidThru;
    sheet.getCell("F16").alignment = alignRight;
    sheet.getCell("F17").value = "TOP : ";
    sheet.getCell("G17").value = dataRFQVendor.rfqvTermPayment;
    sheet.getCell("F17").alignment = alignRight;
    sheet.getCell("F18").value = "Duration/lead time (for service/project) : ";
    sheet.getCell("G18").value = dataRFQVendor.rfqvDuration + " days";
    sheet.getCell("F18").alignment = alignRight;
    sheet.getCell("F19").value = "Currency : ";
    sheet.getCell("G19").value = dataRFQVendor.rfqvCurrency;
    sheet.getCell("F19").alignment = alignRight;
    sheet.getCell("F20").value = "Requestor : ";
    sheet.getCell("G20").value = dataRFQVendor.rfqvCreatedName;
    sheet.getCell("F20").alignment = alignRight;
    sheet.addRows([""])

    // add line item
    var item_header = [
        "NO",
        "DESCRIPTION",
        "QTY",
        "UNIT PRICE",
        "AMOUNT",
        "DISCOUNT (%)",
        "AMOUNT AFTER DISCOUNT",
        "LEAD TIME"
    ]
    sheet.addRow(item_header)
    var row_data = 22;
    sheet.getRow(row_data).alignment = alignCenter
    sheet.columns.forEach((col, index) => {
        if (columnWidth[index]) {
            col.width = columnWidth[index];
        }
    });

    row_data++;
    dataRFQItem.forEach((item, index) => {
        if (item) {
            var dataItem = [
                index + 1,
                item.itemDescription,
                item.qty,
                item.unitPrice,
                item.amount,
                item.discount,
                Math.round(item.amountAfterDiscount),
                item.leadTime
            ]

            sheet.addRow(dataItem)
            sheet.getCell("A" + (row_data + index)).alignment = alignCenter;
            sheet.getCell("C" + (row_data + index)).alignment = alignCenter;
            sheet.getCell("F" + (row_data + index)).alignment = alignCenter;
            sheet.getCell("H" + (row_data + index)).alignment = alignCenter;
            sheet.getCell("D" + (row_data + index)).numFmt = '#,##0.00;[Red]-#,##0.00';
            sheet.getCell("E" + (row_data + index)).numFmt = '#,##0.00;[Red]-#,##0.00';
            sheet.getCell("G" + (row_data + index)).numFmt = '#,##0.00;[Red]-#,##0.00';
        }
    })

    row_data = row_data + dataRFQItem.length + 1;
    sheet.getCell("F" + (row_data + 1)).value = "Subtotal : ";
    sheet.getCell("G" + (row_data + 1)).value = Page.parseInt(Page.Widgets.textSubtotal.datavalue);
    sheet.getCell("G" + (row_data + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("F" + (row_data + 1)).alignment = alignRight;
    sheet.getCell("F" + (row_data + 2)).value = "Vat Rate (" + Page.Widgets.textVatRate.datavalue + "%) : ";
    sheet.getCell("G" + (row_data + 2)).value = Page.parseInt(Page.Widgets.textVatRate2.datavalue);
    sheet.getCell("G" + (row_data + 2)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("F" + (row_data + 2)).alignment = alignRight;
    sheet.getCell("F" + (row_data + 3)).value = "Total inc TAX : ";
    sheet.getCell("G" + (row_data + 3)).value = Page.parseInt(Page.Widgets.textTotalTax.datavalue);
    sheet.getCell("G" + (row_data + 3)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("F" + (row_data + 3)).alignment = alignRight;
    sheet.getCell("F" + (row_data + 4)).value = "Packing Cost : ";
    sheet.getCell("G" + (row_data + 4)).value = Page.parseInt(Page.Widgets.textPackingCost.datavalue);
    sheet.getCell("G" + (row_data + 4)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("F" + (row_data + 4)).alignment = alignRight;
    sheet.getCell("F" + (row_data + 5)).value = "Shipping Handling : ";
    sheet.getCell("G" + (row_data + 5)).value = Page.parseInt(Page.Widgets.textShipping.datavalue);
    sheet.getCell("G" + (row_data + 5)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("F" + (row_data + 5)).alignment = alignRight;
    sheet.getCell("F" + (row_data + 6)).value = "Other cost (insurance/fee/unloading/stamp etc) : ";
    sheet.getCell("G" + (row_data + 6)).value = Page.parseInt(Page.Widgets.textOtherCost.datavalue);
    sheet.getCell("G" + (row_data + 6)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("F" + (row_data + 6)).alignment = alignRight;
    sheet.getCell("F" + (row_data + 7)).value = "QUOTE TOTAL : ";
    sheet.getCell("G" + (row_data + 7)).value = Page.parseInt(Page.Widgets.textTotal.datavalue);
    sheet.getCell("G" + (row_data + 7)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("F" + (row_data + 7)).alignment = alignRight;

    var borderThinTop = {
        top: {
            style: 'thin'
        }
    };
    sheet.getCell("E" + (row_data + 7)).border = borderThinTop;
    sheet.getCell("F" + (row_data + 7)).border = borderThinTop;
    sheet.getCell("G" + (row_data + 7)).border = borderThinTop;

    sheet.getCell("B" + (row_data + 9)).value = "NOTES & TERMS";
    sheet.getCell("B" + (row_data + 10)).value = "Notes : " + dataRFQVendor.rfqvNotes;
    sheet.getCell("B" + (row_data + 11)).value = "Warranty : " + dataRFQVendor.rfqvWarranty;
    sheet.getCell("B" + (row_data + 12)).value = "Asumsi Kurs : " + dataRFQVendor.rfqvKursAssumption;
    sheet.getCell("B" + (row_data + 13)).value = "Based Price Original : " + dataRFQVendor.rfqvBasePrice;
    sheet.getCell("B" + (row_data + 14)).value = "Packing Size : " + dataRFQVendor.rfqvPackingSize;
    sheet.getCell("B" + (row_data + 15)).value = "Origin/Manufacturer : " + dataRFQVendor.rfqvOriginManufacturer;
    sheet.getCell("B" + (row_data + 16)).value = "Self Life Material : " + dataRFQVendor.rfqvMaterial;

    // exporting
    workbook.xlsx.writeBuffer().then(function(datas) {
        var blob = new Blob([datas], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        });

        // let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
        // saveAs(blob, filename + currentTime + ".xlsx");
        saveAs(blob, filename + ".xlsx");
    })
};

Page.buttonCancelClick = function($event, widget) {
    window.history.back()
};

Page.calculationSubtotal = function() {
    var data = Page.Variables.modelLineItem.dataSet
    var subtotal = 0
    data.map(function(item) {
        var amountAfterDiscount = (item.amountAfterDiscount) ? item.amountAfterDiscount : 0
        subtotal = subtotal + Number(amountAfterDiscount)
    })
    subtotal = Number(subtotal).toFixed(2)
    return subtotal
};


Page.calculationVatrate = function() {
    // App.formatDecimalVate((Page.formatCalaculate(Page.Widgets.textSubtotal.datavalue) * Page.formatCalaculate(Page.Widgets.textVatRate.datavalue)) / 100).toFixed(2)
    return ((Page.formatCalaculate(Page.Widgets.textSubtotal.datavalue) * Page.formatCalaculate(Page.Widgets.textVatRate.datavalue)) / 100);
}

Page.calculationPackingCost = function() {
    return (Page.formatCalaculate(Page.Widgets.textSubtotal.datavalue) + Page.formatCalaculate(Page.Widgets.textVatRate2.datavalue));
}

// save edit item
Page.button6_1Click = function($event, widget) {
    Page.Variables.modelErrors.clearData()
    var data = Page.Variables.modelItemTmp.dataSet
    var index = Page.Variables.modelLineItem.dataSet.findIndex(x => x.id == data.id)
    var isValid = true

    // if (!data.leadTime) {
    //     Page.Variables.modelErrors.setValue("leadTime", "this field is required")
    //     isValid = false
    // }
    if (data.unitPrice == "0") {
        Page.Variables.modelErrors.setValue("unitPrice", "this field is required")
        isValid = false
    }
    if (!data.discount) {
        Page.Variables.modelErrors.setValue("discount", "this field is required")
        isValid = false
    }
    if (!isValid) {
        return
    }

    data.leadTime = Page.parseInt(data.leadTime)
    data.unitPrice = Page.parseNumberComma(data.unitPrice)
    data.discount = Page.parseNumberComma(data.discount).toFixed(2)
    data.amount = data.qty * data.unitPrice
    data.amountAfterDiscount = data.amount - (data.amount * data.discount / 100)
    Page.Variables.modelCalculation.dataSet.subtotal = data.amountAfterDiscount + Page.Variables.modelCalculation.dataSet.subtotal

    Page.Variables.modelLineItem.dataSet[index] = data
    Page.Variables.modelItemTmp.clearData()
    Page.calculationVatrate();
    Page.Widgets.dialogItem.close()
};

// edit item
Page.picture1Click = function($event, widget, item, currentItemWidgets) {
    // Page.Variables.modelItemTmp.setData(Page.Widgets.listItem.selecteditem)

    Page.Widgets.dialogItem.open();
    Page.Variables.modelItemTmp.setData(Page.Widgets.listItem.selecteditem)
    let formatedUnitPrice, formatedDiscount;


    if (Page.Widgets.listItem.selecteditem.unitPrice != null) {
        formatedUnitPrice = Page.Widgets.listItem.selecteditem.unitPrice.toString();
        if (formatedUnitPrice.includes(".")) {
            formatedUnitPrice = formatedUnitPrice.replace('.', ',');
            formatedUnitPrice = Page.parseDataFloatByComma(formatedUnitPrice)
        } else {
            formatedUnitPrice = App.formatCurrency(Page.Widgets.listItem.selecteditem.unitPrice)
        }

    }

    Page.Variables.modelItemTmp.dataSet.unitPrice = formatedUnitPrice


    if (Page.Widgets.listItem.selecteditem.discount != null) {
        formatedDiscount = Page.Widgets.listItem.selecteditem.discount.toString();
        if (formatedDiscount.includes(".")) {
            formatedDiscount = formatedDiscount.replace('.', ',');
            formatedDiscount = Page.parseDataFloatByComma(formatedDiscount)
        } else {
            formatedDiscount = Page.Widgets.listItem.selecteditem.discount
        }

    }

    Page.Variables.modelItemTmp.dataSet.discount = formatedDiscount
};

Page.textDurationKeyup = function($event, widget) {
    Page.Widgets.textDuration.datavalue = Page.numberFormat(widget.datavalue)
};
Page.textMobileNumberKeyup = function($event, widget) {
    if (widget.datavalue != null) {
        Page.Widgets.textMobileNumber.datavalue = widget.datavalue.replace(/\D/g, '')
    }

};
Page.textVatRateKeyup = function($event, widget) {
    // Page.Widgets.textVatRate.datavalue = Page.numberFormat(widget.datavalue)

    if (Page.Widgets.search3.datavalue.fromCurrency == 'IDR' || Page.Widgets.search3.datavalue.fromCurrency == 'JPY') {
        var newVal = Page.numberFormat(widget.datavalue)
        Page.Widgets.textVatRate.datavalue = newVal
    } else {
        var newVal = Page.isNumber(widget.datavalue)
        // newVal = newVal.substr(0, 15);
        var indexKe = newVal.indexOf(",");
        if (newVal.includes(",") && indexKe != (newVal.length - 1)) {
            newVal = newVal.replace(' ', '');
            var splitString = newVal.split(",");
            var beforeComma = splitString[0]
            var afterComma = splitString[1]
            afterComma = afterComma.substr(0, 2);
            let after = beforeComma + ',' + afterComma
            Page.Widgets.textVatRate.datavalue = Page.parseDataFloatByComma(after)
        } else {
            Page.Widgets.textVatRate.datavalue = App.formatCurrency(newVal)
        }

    }
};
Page.textPackingCostKeyup = function($event, widget) {


    if (Page.Widgets.search3.datavalue.fromCurrency == 'IDR' || Page.Widgets.search3.datavalue.fromCurrency == 'JPY') {
        var newVal = Page.numberFormat(widget.datavalue)
        newVal = newVal.substr(0, 15);
        Page.Widgets.textPackingCost.datavalue = App.formatCurrency(newVal)
    } else {
        var newVal = Page.isNumber(widget.datavalue)
        newVal = newVal.substr(0, 15);
        var indexKe = newVal.indexOf(",");
        if (newVal.includes(",") && indexKe != (newVal.length - 1)) {
            newVal = newVal.replace(' ', '');
            var splitString = newVal.split(",");
            var beforeComma = splitString[0]
            var afterComma = splitString[1]
            afterComma = afterComma.substr(0, 2);
            let after = beforeComma + ',' + afterComma
            Page.Widgets.textPackingCost.datavalue = Page.parseDataFloatByComma(after)
        } else {
            Page.Widgets.textPackingCost.datavalue = App.formatCurrency(newVal)
        }

    }

    // var newVal = Page.numberFormat(widget.datavalue)
    // newVal = newVal.substr(0, 15);
    // Page.Widgets.textPackingCost.datavalue = App.formatCurrency(newVal)
};
Page.textShippingKeyup = function($event, widget) {
    if (Page.Widgets.search3.datavalue.fromCurrency == 'IDR' || Page.Widgets.search3.datavalue.fromCurrency == 'JPY') {
        var newVal = Page.numberFormat(widget.datavalue)
        newVal = newVal.substr(0, 15);
        Page.Widgets.textShipping.datavalue = App.formatCurrency(newVal)
    } else {
        var newVal = Page.isNumber(widget.datavalue)
        newVal = newVal.substr(0, 15);
        var indexKe = newVal.indexOf(",");
        if (newVal.includes(",") && indexKe != (newVal.length - 1)) {
            newVal = newVal.replace(' ', '');
            var splitString = newVal.split(",");
            var beforeComma = splitString[0]
            var afterComma = splitString[1]
            afterComma = afterComma.substr(0, 2);
            let after = beforeComma + ',' + afterComma
            Page.Widgets.textShipping.datavalue = Page.parseDataFloatByComma(after)
        } else {
            Page.Widgets.textShipping.datavalue = App.formatCurrency(newVal)
        }

    }
    // var newVal = Page.numberFormat(widget.datavalue)
    // newVal = newVal.substr(0, 15);
    // Page.Widgets.textShipping.datavalue = App.formatCurrency(newVal)
};
Page.textOtherCostKeyup = function($event, widget) {
    if (Page.Widgets.search3.datavalue.fromCurrency == 'IDR' || Page.Widgets.search3.datavalue.fromCurrency == 'JPY') {
        var newVal = Page.numberFormat(widget.datavalue)
        newVal = newVal.substr(0, 15);
        Page.Widgets.textOtherCost.datavalue = App.formatCurrency(newVal)
    } else {
        var newVal = Page.isNumber(widget.datavalue)
        newVal = newVal.substr(0, 15);
        var indexKe = newVal.indexOf(",");
        if (newVal.includes(",") && indexKe != (newVal.length - 1)) {
            newVal = newVal.replace(' ', '');
            var splitString = newVal.split(",");
            var beforeComma = splitString[0]
            var afterComma = splitString[1]
            afterComma = afterComma.substr(0, 2);
            let after = beforeComma + ',' + afterComma
            Page.Widgets.textOtherCost.datavalue = Page.parseDataFloatByComma(after)
        } else {
            Page.Widgets.textOtherCost.datavalue = App.formatCurrency(newVal)
        }

    }
    // var newVal = Page.numberFormat(widget.datavalue)
    // newVal = newVal.substr(0, 15);
    // Page.Widgets.textOtherCost.datavalue = App.formatCurrency(newVal)
};
Page.textBasePriceKeyup = function($event, widget) {
    var newVal = Page.numberFormat(widget.datavalue)
    newVal = newVal.substr(0, 15);
    Page.Widgets.textBasePrice.datavalue = App.formatCurrency(newVal)
};
Page.textUnitPriceKeyup = function($event, widget) {
    if (Page.Widgets.search3.datavalue.fromCurrency == 'IDR' || Page.Widgets.search3.datavalue.fromCurrency == 'JPY') {
        var newVal = Page.numberFormat(widget.datavalue)
        newVal = newVal.substr(0, 15);
        Page.Variables.modelItemTmp.dataSet.unitPrice = App.formatCurrency(newVal)
    } else {
        var newVal = Page.isNumber(widget.datavalue)
        newVal = newVal.substr(0, 15);
        var indexKe = newVal.indexOf(",");
        if (newVal.includes(",") && indexKe != (newVal.length - 1)) {
            newVal = newVal.replace(' ', '');
            var splitString = newVal.split(",");
            var beforeComma = splitString[0]
            var afterComma = splitString[1]
            afterComma = afterComma.substr(0, 2);
            let after = beforeComma + ',' + afterComma
            Page.Variables.modelItemTmp.dataSet.unitPrice = Page.parseDataFloatByComma(after)
        } else {
            Page.Variables.modelItemTmp.dataSet.unitPrice = App.formatCurrency(newVal)
        }

    }
};

//


Page.textLeadTimeKeyup = function($event, widget) {
    Page.Variables.modelItemTmp.dataSet.leadTime = Page.numberFormat(widget.datavalue)
};
Page.textDiscountKeyup = function($event, widget) {
    // Page.Variables.modelItemTmp.dataSet.discount = Page.numberFormat(widget.datavalue)

    var newVal = Page.isNumber(widget.datavalue)
    var indexKe = newVal.indexOf(",");
    if (newVal.includes(",") && indexKe != (newVal.length - 1)) {
        let before = newVal.replace(' ', '').replace(',', '.')
        before = Number(before).toFixed(2);
        before = Math.floor(before * 100) / 100;
        if (before > 100) {
            alert('discount cannot more than 100 %')
            before = 100;
        }
        before = before.toString()
        var splitString = newVal.split(",");
        var beforeComma = splitString[0]
        var afterComma = splitString[1]
        // afterComma = afterComma.substr(0, 2);
        let after = beforeComma + ',' + afterComma
        Page.Variables.modelItemTmp.dataSet.discount = after
    } else {
        if (newVal > 100) {
            alert('discount cannot more than 100 %')
            newVal = 100;
        }
        Page.Variables.modelItemTmp.dataSet.discount = newVal
    }
};
Page.selectCurrencyChange = function($event, widget, newVal, oldVal) {
    Page.Variables.modelItemTmp.setValue("currency", newVal.fromCurrency)
    Page.Variables.modelItemTmp.setValue("kurs", newVal.kurs)
};
// select currency
Page.search3Select = function($event, widget, selectedValue) {
    Page.Variables.modelRfqVendor.setValue("rfqvCurrency", selectedValue.fromCurrency)
    Page.Variables.modelRfqVendor.setValue("rfqvCurrencyKurs", selectedValue.kurs)

};

Page.formatCalaculate = function(val) {

    if (typeof(val) == undefined) {
        return 0;
    } else {
        val = val.toString();
    }

    if (val.includes(".")) {
        val = App.replaceAll(val, '.', '');
    }

    if (val.includes(",")) {
        val = val.replace(',', '.');
    }

    return Number(val)
}

Page.numberFormat = function(newVal) {
    newVal = newVal.toString();
    newVal = newVal.replace(/\D/g, '')
    if (newVal.charAt(0) == '0' && newVal.length > 0) {
        newVal = newVal.substr(1)
    }
    if (newVal == '') {
        newVal = '0'
    }
    return newVal;
};

Page.parseDataFloatByComma = function(data) {
    var splitString = data.split(",");
    var beforeComma = splitString[0]
    var afterComma = splitString[1]
    return (beforeComma || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.') + ',' + afterComma
}

Page.parseNumberComma = function(str) {
    let newdata = str;
    newdata = newdata.toString();
    newdata = Page.replaceAll(newdata, '.', '');
    return Number(newdata.replace(',', '.'));
}

Page.isNumber = function(newVal) {
    let newdata = ''
    if (newVal != undefined) {
        newVal = newVal.toString();
        newVal = newVal.match(/[0-9,]/g)
        if (newVal != null) {
            newdata = newVal.join('')
            if (newdata.charAt(0) == '0' && newdata.charAt(1) != ',' && newdata.length > 0) {
                newdata = newdata.substr(1)
            }
        }
        if (newdata == '') {
            newdata = '0'
        }
        return newdata;
    } else {
        return '0'
    }
}

Page.replaceAll = function(string, search, replace) {
    return string.split(search).join(replace);
}


// Page.queryGetCurrencyonSuccess = function(variable, data) {
//     for (var i = 0; i < data.length; i++) {
//         Page.Variables.modelCurrency.addItem(data[i])
//     }
// };

// invoke btn upload
Page.button6Click = function($event, widget) {
    $(Page.Widgets.attachmentUpload.nativeElement).find("input").click();
};
Page.FileServiceUploadFileonSuccess = function(variable, data) {
    Page.Widgets.spinner1.show = false
    Page.Variables.varListFile.addItem({
        "filename": data[0].fileName,
        "pathFile": data[0].inlinePath
    })
};
Page.attachmentUploadSelect = function($event, widget, selectedFiles) {
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

    Page.Widgets.spinner1.show = true
    Page.Variables.FileServiceUploadFile.invoke()
};

Page.FileServiceUploadFileonError = function(variable, data) {
    Page.Widgets.spinner1.show = false
};
// download file
Page.label26_2Click = function($event, widget, item, currentItemWidgets) {
    Page.Widgets.spinner1.show = true
    Page.Variables.FileServiceDownloadFile.invoke({
        "inputFields": {
            "file": Page.Widgets.list2.selecteditem.filename,
            "returnName": Page.Widgets.list2.selecteditem.filename
        }
    }).then(function() {
        Page.Widgets.spinner1.show = false
    })
};
// delete file
Page.picture5Click = function($event, widget, item, currentItemWidgets) {
    var data = Page.Widgets.list2.selecteditem

    if (!confirm('Are you sure you want to remove ' + data.filename + '?')) {
        return
    }

    Page.Widgets.spinner1.show = true
    Page.Variables.FileServiceDeleteFile.invoke({
        "inputFields": {
            "file": data.filename
        }
    }).then(() => {
        Page.Variables.varListFile.removeItem(data)
        Page.Widgets.spinner1.show = false
    })
};

Page.picture7Click = function($event, widget, item, currentItemWidgets) {
    document.getElementById("pdf-canvas-container").innerHTML = ""
    Page.Variables.vmShowPdf.dataSet.dataValue = true
    Page.Widgets.spinner1.show = true
    var filePath = Page.Widgets.list2.selecteditem.pathFile
    // Loaded via <script> tag, create shortcut to access PDF.js exports.
    var pdfjsLib = window['pdfjs-dist/build/pdf'];
    // The workerSrc property shall be specified.
    pdfjsLib.GlobalWorkerOptions.workerSrc = '//cdnjs.cloudflare.com/ajax/libs/pdf.js/2.6.347/pdf.worker.min.js';

    var loadingTask = pdfjsLib.getDocument(filePath);
    return loadingTask.promise.then(function(pdf) {
        var pageNumber = 1;
        return Page.renderPDFPage(pdf, pageNumber)
    }).then(function() {
        Page.Widgets.spinner1.show = false
    }).catch(function(error) {
        Page.Widgets.spinner1.show = false
    });
};

Page.picture4Click = function($event, widget) {
    Page.Variables.vmShowPdf.dataSet.dataValue = false
    document.getElementById("pdf-canvas-container").innerHTML = ""
};

Page.renderPDFPage = function(pdf, pageNumber) {
    return pdf.getPage(pageNumber).then(function(page) {
        var scale = 1;
        var viewport = page.getViewport({
            scale: scale
        });

        // Prepare canvas using PDF page dimensions
        var canvas = document.createElement("canvas")
        var context = canvas.getContext('2d');
        canvas.height = viewport.height;
        canvas.width = viewport.width;
        canvas.style.marginBottom = "24px"

        // Render PDF page into canvas context
        var renderContext = {
            canvasContext: context,
            viewport: viewport
        };
        var renderTask = page.render(renderContext);
        return renderTask.promise.then(function() {
            return canvas
        })
    }).then(function(canvas) {
        document.getElementById("pdf-canvas-container").appendChild(canvas)
        if (pageNumber >= pdf.numPages) {
            return Promise.resolve()
        }
        return Page.renderPDFPage(pdf, pageNumber + 1)
    }).catch(function(err) {
        if (pageNumber >= pdf.numPages) {
            return Promise.resolve()
        }
        return Page.renderPDFPage(pdf, pageNumber + 1)
    })
};

// export PDF
Page.buttonSaveExportClick = function($event, widget) {
    Page.Widgets.spinner2.show = true
    var element = Page.Widgets.containerExportBg.nativeElement;
    var opt = {
        margin: 0.5,
        filename: "Form Quotation.pdf",
        jsPDF: {
            unit: 'in',
            format: 'letter',
            orientation: 'portrait'
        }
    }

    return Promise.resolve().then(function() {
        return html2pdf().set(opt).from(element).save();
    }).then(function() {
        Page.Widgets.spinner2.show = false
    })
};