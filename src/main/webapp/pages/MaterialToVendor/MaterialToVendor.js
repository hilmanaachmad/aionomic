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
};

Page.responseRequest = function(message) {
    App.Actions.appNotification.setMessage(message)
    App.Actions.appNotification.setToasterClass("success")
    App.Actions.appNotification.getToasterDuration(5000)
    App.Actions.appNotification.invoke()
}

Page.subStrVendor = function(name, id) {
    if (name && id) {
        var vendorName = JSON.parse(name)
        var vendorCode = JSON.parse(id)
        var strArray = []

        vendorName.map((item, i) => {
            strArray.push(vendorCode[i] + " - " + item)
        })

        if (strArray.length > 2) {
            var count = strArray.length
            return strArray[0] + ", " + strArray[1] + ", +" + (count - 2)
        }

        return strArray.join(", ")
    }

    return "-"
}

// numbering data
Page.dataListBeforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.vdbGetMatVendor.pagination.number * Page.Variables.vdbGetMatVendor.pagination.size) + (i + 1);
    }
};

// select vendor
Page.search2_1Select = function($event, widget, selectedValue) {
    // if (selectedValue != undefined) {
    //     Page.Widgets.labelreq2.show = false;
    // }
    var arlearyExist = Page.Variables.vmVendorList.dataSet.filter(x => x.vendorCode == selectedValue.vendorCode)

    if (arlearyExist.length == 0) {
        selectedValue.isNew = true
        Page.Variables.vmVendorList.addItem(selectedValue)
    }

    Page.Widgets.search2_1.datavalue = null
};

// select Material
Page.searchMatCategorySelect = function($event, widget, selectedValue) {
    // if (selectedValue != undefined) {
    //     Page.Widgets.labelreq1.show = false;
    // }
    Page.Variables.vmMatVendorModel.setValue('material', selectedValue.materialGroupDesc)
    Page.Variables.vmMatVendorModel.setValue('materialId', selectedValue.materialGroup)
};

// submit insert or update
Page.button4Click = function($event, widget) {
    Page.Variables.vmErrorMessage.clearData()
    var vendors = Page.Variables.vmVendorList.dataSet
    var data = Page.Variables.vmMatVendorModel.dataSet
    var vendorName = []
    var vendorId = []
    var isValid = true

    if (!data.material || data.material === '') {
        Page.Variables.vmErrorMessage.setValue('material', 'this field is required')
        isValid = false
    }

    if (vendors.length === 0) {
        Page.Variables.vmErrorMessage.setValue('vendor', 'this field is required')
        isValid = false
    }

    if (!isValid) {
        return
    }

    vendors.map(item => {
        vendorName.push(item.vendorName)
        vendorId.push(item.vendorCode)
    })

    data.vendor = JSON.stringify(vendorName)
    data.vendorId = JSON.stringify(vendorId)

    // update
    if (data.mvId) {
        Page.Variables.vdbGetMatVendor.listRecords({
            filterFields: {
                "materialId": {
                    "value": data.materialId
                },
                "material": {
                    "value": data.material
                }
            }
        }).then(result => {
            result.data.map(item => {
                if (item.mvId != data.mvId) {
                    Page.Variables.vmErrorMessage.setValue('material', 'this field already exist')
                    isValid = false
                }
            })

            if (isValid) {
                console.log("valid", isValid)
                Page.Variables.vdbGetMatVendor.updateRecord({
                    row: data
                }).then(data => {
                    Page.responseRequest("Data Saved")

                    Page.Widgets.addMapGroupVendor.close()
                }).catch(error => {
                    console.log(error)
                })
            }
        }).then(res => {
            Page.Variables.vdbGetMatVendor.update()
        })
    }
    // insert
    else {
        data.mvStatus = 'active'
        data.mvCreatedAt = new Date().toISOString()

        Page.Variables.vdbGetMatVendor.listRecords({
            filterFields: {
                "materialId": {
                    "value": data.materialId
                },
                "material": {
                    "value": data.material
                }
            }
        }).then(result => {
            if (result.data.length == 0) {
                Page.Variables.vdbGetMatVendor.createRecord({
                    row: data
                }).then(() => {
                    Page.responseRequest("Data Saved")

                    Page.Widgets.addMapGroupVendor.close()
                }).catch(error => {
                    console.log(error)
                })
            } else {
                Page.Variables.vmErrorMessage.setValue('material', 'this field already exist')
            }
        }).then(res => {
            Page.Variables.vdbGetMatVendor.update()
        })
    }
};

// button add
Page.button1Click = function($event, widget) {
    Page.Variables.vmVendorList.clearData()
    Page.Variables.vmMatVendorModel.clearData()
    Page.Variables.vmErrorMessage.clearData()
};

// button edit
Page.picture4Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.vmErrorMessage.clearData()
    Page.Variables.vmVendorList.clearData()

    var vendorName = JSON.parse(item.vendor)
    var vendorCode = JSON.parse(item.vendorId)

    vendorName.map((name, i) => {
        var vendor = {
            vendorCode: vendorCode[i],
            vendorName: name
        }
        Page.Variables.vmVendorList.addItem(vendor)
    })

    Page.Variables.vmMatVendorModel.setData(item)
    Page.Widgets.addMapGroupVendor.open()
};

// delete list vendor
Page.picture3Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.vmDeleteItem.setData(item)
    Page.Widgets.dialogDelete.open()
};

// delete list mat vendor
Page.picture5Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.vmDeleteItem.setData(item)
    Page.Widgets.dialogDelete.open()
};

// delete confirm
Page.dialogDeleteOk = function($event, widget) {
    var item = Page.Variables.vmDeleteItem.dataSet

    // confirm delete list vendor
    if (item.vendorCode) {
        Page.Variables.vmVendorList.removeItem(item)
    }
    // confirm delete mat vendor
    else if (item.mvId) {
        Page.Variables.vdbGetMatVendor.deleteRecord({
            row: item
        }).then(data => {
            Page.Variables.vdbGetMatVendor.update()
            Page.responseRequest("Data Deleted")
        })
    }

    Page.Widgets.dialogDelete.close()
};

// export data
Page.button2Click = function($event, widget) {
    // intial
    const workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('Material Vendor');
    var filename = "Material Vendor_";

    // inserting data
    var xls_content = []

    var xls_header = [
        "Material Group/Category",
        "Vendor",
        "Status"
    ]
    xls_content.push(xls_header)

    var data = Page.Variables.vdbGetMatVendor.dataSet
    var dt = JSON.parse(JSON.stringify(data))
    dt.forEach(item => {
        var vendorName = JSON.parse(item.vendor)
        var vendorCode = JSON.parse(item.vendorId)
        var vendors = []

        vendorName.map((name, i) => {
            vendors.push(vendorCode[i] + " - " + name)
        })

        var dataItem = [
            item.material,
            vendors.join(", "),
            item.mvStatus
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
    let columnWidth = [24, 45, 8];
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

// sort functions
Page.toggleTableSort = function(field, picture) {
    if (Page.Variables.vdbGetMatVendor.orderBy == field + " ASC") {
        Page.Variables.vdbGetMatVendor.orderBy = field + " DESC"
        picture.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else if (Page.Variables.vdbGetMatVendor.orderBy == field + " DESC") {
        Page.Variables.vdbGetMatVendor.orderBy = ""
        picture.picturesource = 'resources/images/logos/icon-up-down.png'
    } else {
        Page.Variables.vdbGetMatVendor.orderBy = field + " ASC"
        picture.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    }

    Page.Variables.vdbGetMatVendor.update()
}
Page.container30Click = function($event, widget) {
    Page.toggleTableSort('material', Page.Widgets.picture7);

    // reset pict
    Page.Widgets.picture8.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9.picturesource = 'resources/images/logos/icon-up-down.png'

};
Page.container31Click = function($event, widget) {
    Page.toggleTableSort('vendor', Page.Widgets.picture8);

    // reset pict
    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture9.picturesource = 'resources/images/logos/icon-up-down.png'

};
Page.container32Click = function($event, widget) {
    Page.toggleTableSort('mvStatus', Page.Widgets.picture9);

    // reset pict
    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture8.picturesource = 'resources/images/logos/icon-up-down.png'
};


Page.selectPerPageChange = function($event, widget, newVal, oldVal) {
    Page.Variables.vdbGetMatVendor.maxResults = newVal
    Page.Variables.vdbGetMatVendor.update()
};