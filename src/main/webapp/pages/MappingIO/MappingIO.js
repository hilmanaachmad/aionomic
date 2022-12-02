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

    //authorization
    if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('MST-007') !== -1) {

        } else {
            App.Actions.goToPage_Main.invoke()
        }
    }
};

Page.parseInt = parseInt

Page.appResponse = function(message) {
    // message
    App.Actions.appNotification.setMessage(message)
    // type: success, info, warning, danger
    App.Actions.appNotification.setToasterClass("success")
    // delayed
    App.Actions.appNotification.getToasterDuration(5000)
    // invoke
    App.Actions.appNotification.invoke()

    Page.Variables.vdbGetIO.update()
}

// select COA
Page.search5Select = function($event, widget, selectedValue) {
    Page.Variables.vmIOModel.setValue("coaId", selectedValue.glNumber)
    Page.Variables.vmIOModel.setValue("coaTitle", selectedValue.glShortText)
    Page.Variables.vmIOModel.setValue("codDesc", selectedValue.glLongText)
};

// select department
Page.search3Select = function($event, widget, selectedValue) {
    if (selectedValue != undefined) {
        // Page.Widgets.labelreq3.show = false;
    }
    Page.Variables.vmIOModel.setValue("departmentId", selectedValue.departmentId)
    Page.Variables.vmIOModel.setValue("department", selectedValue.departmentTitle)
};

// change per page
Page.select9_2Change = function($event, widget, newVal, oldVal) {
    Page.Variables.vdbGetIO.maxResults = newVal
    Page.Variables.vdbGetIO.update()
};

// submit 
Page.button4Click = function($event, widget) {
    var data = Page.Variables.vmIOModel.dataSet
    var loggedInUser = App.Variables.loggedInUserData.dataSet
    var isValid = true
    console.log("dataModel", data)

    if (!data.ioNumber) {
        isValid = false
        Page.Variables.vmErrorMessage.setValue('ioNumber', 'this field is required')
    }
    if (!data.cid) {
        isValid = false
        Page.Variables.vmErrorMessage.setValue('company', 'this field is required')
    }
    if (!data.accId) {
        isValid = false
        Page.Variables.vmErrorMessage.setValue('accountType', 'this field is required')
    }
    if (!data.department) {
        isValid = false
        Page.Variables.vmErrorMessage.setValue('department', 'this field is required')
    }
    if (!data.brId) {
        isValid = false
        Page.Variables.vmErrorMessage.setValue('brand', 'this field is required')
    }
    if (!data.brId) {
        isValid = false
        Page.Variables.vmErrorMessage.setValue('brand', 'this field is required')
    }

    if (!isValid) {
        return
    }
    Page.Variables.vmLoading.dataSet.dataValue = true

    // update
    if (data.ioId) {
        delete data.tblMaccountType
        delete data.tblMbrand
        delete data.tblMcompany

        Page.Variables.vdbGetIO.updateRecord({
            row: data
        }).then(data => {
            Page.Variables.vmLoading.dataSet.dataValue = false
            Page.appResponse("Data Saved")
            Page.Widgets.addIO.close()
        }).catch(error => {
            console.log(error)
        })
    }
    // insert
    else {
        data.ioStatus = 'active'
        data.ioCreatedAt = new Date()
        data.ioCreatedBy = loggedInUser.user_full_name

        Page.Variables.vdbGetIO.createRecord({
            row: data
        }).then(function() {
            return Page.Variables.vdbBudgetHeader.createRecord({
                row: {
                    "buId": null,
                    "cid": data.cid,
                    "accId": data.accId,
                    "brId": data.brId,
                    "bhYear": data.budgetYear,
                    "department": data.department,
                    "departmentId": data.departmentId,
                    "bhEstDate": null,
                    "bhCurrency": "IDR",
                    "ioNumber": data.ioNumber,
                    "ioName": data.ioDesc,
                    "coa": data.coaId,
                    "bhAmount": 0,
                    "userId": loggedInUser.username,
                    "bhCreatedAt": data.ioCreatedAt,
                    "bhCreatedBy": data.ioCreatedBy,
                    "bhStatus": "active"
                }
            })
        }).then(function(res) {
            return Page.Variables.vdbBudgetDetails.createRecord({
                row: {
                    bhId: res.bhId,
                    bdOriginal: res.bhAmount,
                    bdDocumentId: "1000-" + res.bhId,
                    bdAdjustmentType: "BEGINNING",
                    bdRemarks: "Submission Budget " + res.bhYear,
                    bdAdjustment: res.bhAmount,
                    bdAfterAdjustment: res.bhAmount,
                    bdCreatedAt: new Date(),
                    bdCreatedBy: loggedInUser.username
                }
            })
        }).then(function() {
            Page.Variables.vmLoading.dataSet.dataValue = false
            Page.appResponse("Data Saved")
            Page.Widgets.addIO.close()
        }).catch(error => {
            console.log(error)
        })
    }
};

// numbering table
Page.dataListBeforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.vdbGetIO.pagination.number * Page.Variables.vdbGetIO.pagination.size) + (i + 1);
    }
};

// button edit
Page.pictureEditClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.vmErrorMessage.clearData()

    var data = JSON.parse(JSON.stringify(item))
    Page.Variables.vmIOModel.setData(data)
    Page.Widgets.addIO.open()
};
// button add
Page.button1Click = function($event, widget) {
    Page.Variables.vmErrorMessage.clearData()
    Page.Variables.vmIOModel.clearData()
    Page.Widgets.addIO.open()
};
// button delete
Page.pictureDeleteClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.vmDeleteItem.setData(item)
    Page.Widgets.dialogDelete.open()
};
// confirm delete
Page.dialogDeleteOk = function($event, widget) {
    var data = Page.Variables.vmDeleteItem.dataSet

    Page.Variables.vdbGetIO.deleteRecord({
        row: data
    }).then(data => {
        Page.appResponse("Data Deleted")
    }).catch(error => {
        console.log(error)
    })

    Page.Widgets.dialogDelete.close()
};

// export table
Page.button2Click = function($event, widget) {
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('Mapping IO');
    var filename = "Mapping IO - ";

    // inserting data
    var xls_content = []

    var xls_header = [
        "Budget Year",
        "Company",
        "Account Type",
        "Department",
        "Brand",
        "IO Number",
        "IO Description",
        "COA",
        "COA Description",
        "Status"
    ]
    xls_content.push(xls_header)

    var data = Page.Variables.vdbGetIO.dataSet
    data.map(item => {
        var dataItem = [
            item.budgetYear,
            item.tblMcompany ? item.tblMcompany.ctitle : '',
            item.tblMaccountType ? item.tblMaccountType.accTitle : '',
            item.department,
            item.tblMbrand ? item.tblMbrand.brTitle : '',
            item.ioNumber,
            item.ioDesc,
            item.coaTitle,
            item.codDesc,
            item.ioStatus
        ]

        xls_content.push(dataItem)
    })

    sheet.addRows(xls_content)

    // styling
    let alignCenter = {
        vertical: 'middle',
        'horizontal': 'center'
    };
    sheet.getRow(1).alignment = alignCenter;

    // Adjust column width
    let columnWidth = [12, 15, 14, 23, 13, 13, 15, 14, 15, 13];
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
        saveAs(blob, filename + currentTime + ".xlsx");
    })
};

Page.addIOOpened = function($event, widget) {
    var a = new Yearpicker(Page.Widgets.textBudgetYear.inputEl.nativeElement, {
        onChange: function(year) {
            Page.Widgets.textBudgetYear.datavalue = Page.Variables.vmIOModel.dataSet.ioId ? Page.Variables.vmIOModel.dataSet.budgetYear : year
        }
    })
};

// sort table Function
Page.toggleTableSort = function(field, picture) {
    if (Page.Variables.vdbGetIO.orderBy == field + " ASC") {
        Page.Variables.vdbGetIO.orderBy = field + " DESC"
        picture.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else if (Page.Variables.vdbGetIO.orderBy == field + " DESC") {
        Page.Variables.vdbGetIO.orderBy = ""
        picture.picturesource = 'resources/images/logos/icon-up-down.png'
    } else {
        Page.Variables.vdbGetIO.orderBy = field + " ASC"
        picture.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    }

    Page.Variables.vdbGetIO.update()
}
Page.container36_1Click = function($event, widget) {
    Page.toggleTableSort('budgetYear', Page.Widgets.picture7)

    Page.Widgets.picture8.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture10.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture12.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container37Click = function($event, widget) {
    Page.toggleTableSort('tblMcompany.ctitle', Page.Widgets.picture8)

    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture10.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture12.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container38_1Click = function($event, widget) {
    Page.toggleTableSort('tblMaccountType.accTitle', Page.Widgets.picture9)

    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture10.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture12.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container39_1Click = function($event, widget) {
    Page.toggleTableSort('department', Page.Widgets.picture10)

    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture12.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container40_1Click = function($event, widget) {
    Page.toggleTableSort('tblMbrand.brTitle', Page.Widgets.picture11)

    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture10.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture12.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container41_1Click = function($event, widget) {
    Page.toggleTableSort('ioNumber', Page.Widgets.picture12)

    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture10.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container52Click = function($event, widget) {
    Page.toggleTableSort('ioDesc', Page.Widgets.picture7_1)

    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture10.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture12.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container53Click = function($event, widget) {
    Page.toggleTableSort('coaTitle', Page.Widgets.picture8_1)

    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture10.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture12.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container54Click = function($event, widget) {
    Page.toggleTableSort('codDesc', Page.Widgets.picture9_1)

    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture10.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture12.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container56Click = function($event, widget) {
    Page.toggleTableSort('ioStatus', Page.Widgets.picture11_1)

    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture10.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture11.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture12.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8_1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.searchCompanySelect = function($event, widget, selectedValue) {
    if (selectedValue != undefined) {
        // Page.Widgets.labelreq1.show = false;
    }
};
Page.searchAccTypeSelect = function($event, widget, selectedValue) {
    if (selectedValue != undefined) {
        // Page.Widgets.labelreq2.show = false;
    }
};
Page.searchBrandSelect = function($event, widget, selectedValue) {
    if (selectedValue != undefined) {
        // Page.Widgets.labelreq4.show = false;
    }
};
Page.textIONumberChange = function($event, widget, newVal, oldVal) {
    if (newVal != undefined) {
        // Page.Widgets.labelreq1.show = false;
    }
};