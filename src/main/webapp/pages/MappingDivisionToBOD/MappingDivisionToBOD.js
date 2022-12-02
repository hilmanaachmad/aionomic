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
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('MST-010') !== -1) {

        } else {
            App.Actions.goToPage_Main.invoke();
        }
    }
};

var selectedItem;


Page.tableBeforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].num = (Page.Variables.read_divisionBod.pagination.number * Page.Variables.read_divisionBod.pagination.size) + (i + 1);
    }
};

//Tambah Data
Page.btn_saveClick = function($event, widget) {
    var divisionId = ''
    var divisionName = ''
    var bod1 = ''
    var bodName1 = ''
    var bod2 = ''
    var bodName2 = ''
    if (Page.Widgets.add_division.datavalue !== '' && Page.Widgets.add_division.datavalue !== undefined) {
        divisionId = Page.Widgets.add_division.datavalue.divisionId
        divisionName = Page.Widgets.add_division.datavalue.divisionTitle
    }

    if (Page.Widgets.add_bod1.datavalue !== '' && Page.Widgets.add_bod1.datavalue !== undefined) {
        bod1 = Page.Widgets.add_bod1.datavalue.nik
        bodName1 = Page.Widgets.add_bod1.datavalue.employeeName
    }

    if (Page.Widgets.add_bod2.datavalue !== '' && Page.Widgets.add_bod2.datavalue !== undefined) {
        bod2 = Page.Widgets.add_bod2.datavalue.nik
        bodName2 = Page.Widgets.add_bod2.datavalue.employeeName
    }


    Page.Widgets.add_spinner.show = true
    var formValid = true
    if (divisionId == undefined || divisionId == '' || divisionId == null) {
        formValid = false
        Page.Widgets.add_label_divison.show = true
        Page.Widgets.add_spinner.show = false
    }

    if (bod1 == undefined || bod1 == '' || bod1 == null) {
        formValid = false
        Page.Widgets.add_label_bod1.show = true
        Page.Widgets.add_spinner.show = false
    }


    if (bod2 == undefined || bod2 == '' || bod2 == null) {
        formValid = false
        Page.Widgets.add_label_bod2.show = true
        Page.Widgets.add_spinner.show = false
    }


    var d = new Date,
        dformat = [d.getFullYear(),
            d.getMonth() + 1,
            d.getDate()
        ].join('/') + ' ' + [d.getHours(),
            d.getMinutes(),
            d.getSeconds()
        ].join(':');


    if (formValid == true) {
        Page.Variables.forCheckData.listRecords({
            filterFields: {
                "divisionId": {
                    "value": divisionId
                },
            }
        }, function(result) {
            if (result.data.length == 0) {
                Page.Variables.insert_divisionBod.invoke({
                    inputFields: {
                        divisionId: divisionId,
                        divBodTitle: divisionName,
                        divBod1: "emp::" + bod1,
                        divBod2: "emp::" + bod2,
                        divBodName1: bodName1,
                        divBodName2: bodName2,
                        divBodStatus: 'Active',
                        divBodCreatedAt: dformat,
                        divBodCreatedBy: Page.App.Variables.loggedInUser.dataSet.name
                    }
                }, function(data) {
                    Page.Widgets.add_spinner.show = false
                    Page.Widgets.dialogAdd.close()
                    Page.Variables.read_divisionBod.invoke()
                    App.Actions.appNotification.setMessage("Data Insert Successfully")
                    App.Actions.appNotification.setToasterClass("success")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                })
            } else {
                Page.Widgets.add_spinner.show = false
                App.Actions.appNotification.setMessage("Data already exist")
                App.Actions.appNotification.setToasterClass("warning")
                App.Actions.appNotification.setToasterDuration(5000)
                App.Actions.appNotification.invoke()

            }
        })
    }
};


//Delete Data
Page.btn_trigger_deleteClick = function($event, widget, item, currentItemWidgets) {
    selectedItem = item;
    Page.Widgets.deleteConfirm.open();
};

Page.deleteConfirmOk = function($event, widget) {
    widget.close();
    Page.Variables.delete_divisionBod.invoke({
        inputFields: {
            divBodId: selectedItem.divBodId
        }
    }, function(res) {
        Page.Variables.read_divisionBod.invoke();
    });

    App.Actions.appNotification.setMessage("Data Deleted Successfully")
    App.Actions.appNotification.setToasterClass("success")
    App.Actions.appNotification.getToasterDuration(5000)
    App.Actions.appNotification.invoke()
};


//Edit data

Page.btn_trigger_editClick = function($event, widget, item, currentItemWidgets) {
    selectedItem = item;
    const nik1 = item.divBod1.replace('emp::', '')
    const nik2 = item.divBod2.replace('emp::', '')
    Page.Variables.selected_bod1.dataSet.dataValue = nik1
    Page.Variables.selected_bod2.dataSet.dataValue = nik2

    Page.Widgets.EditDialog.open();
};

Page.EditDialogOpened = function($event, widget) {
    Page.Widgets.edit_division.datavalue = selectedItem.divisionId
    Page.Widgets.toggle1.datavalue = selectedItem.divBodStatus;
};

Page.btn_updateClick = async function($event, widget) {
    var divisionId = Page.Widgets.edit_division.datavalue
    var bod1 = Page.Widgets.edit_bod1.datavalue
    var bod2 = Page.Widgets.edit_bod2.datavalue
    var status = Page.Widgets.toggle1.datavalue



    Page.Widgets.edit_spinner.show = true
    var formValid = true
    if (divisionId == undefined || divisionId == '' || divisionId == null) {
        formValid = false
        Page.Widgets.edit_label_division.show = true
        Page.Widgets.edit_spinner.show = false
    }

    if (bod1 == undefined || bod1 == '' || bod1 == null) {
        formValid = false
        Page.Widgets.edit_label_bod1.show = true
        Page.Widgets.edit_spinner.show = false
    }


    if (bod2 == undefined || bod2 == '' || bod2 == null) {
        formValid = false
        Page.Widgets.edit_label_bod2.show = true
        Page.Widgets.edit_spinner.show = false
    }

    if (formValid == true) {
        var get_bodName1 = await Page.Variables.get_employee.listRecords({
            filterFields: {
                "nik": {
                    "value": bod1
                },
            }
        })
        var get_bodName2 = await Page.Variables.get_employee.listRecords({
            filterFields: {
                "nik": {
                    "value": bod2
                },
            }
        })
        var bodName1 = '-'
        if (get_bodName1.data.length > 0) {
            bodName1 = get_bodName1.data[0].employeeName
        }
        var bodName2 = '-'
        if (get_bodName2.data.length > 0) {
            bodName2 = get_bodName2.data[0].employeeName
        }


        var filter_division = Page.Variables.get_division.dataSet.filter(item =>
            item.divisionId == divisionId
        )

        var divisionName = '-'
        if (filter_division.length > 0) {
            divisionName = filter_division[0].divisionTitle
        }


        var d = new Date,
            dformat = [d.getFullYear(),
                d.getMonth() + 1,
                d.getDate()
            ].join('-') + ' ' + [d.getHours(),
                d.getMinutes()
            ].join(':');


        Page.Variables.forCheckData.listRecords({
            filterFields: {
                "divisionId": {
                    "value": divisionId
                },
            }
        }, function(result) {
            if (result.data.length == 0 || (result.data.length > 0 && result.data[0].divBodId == selectedItem.divBodId)) {
                Page.Variables.update_divisionBod.invoke({
                    inputFields: {
                        divBodId: Number(selectedItem.divBodId),
                        TblMDivisionBod: {
                            divisionId: divisionId,
                            divBodTitle: divisionName,
                            divBod1: "emp::" + bod1,
                            divBod2: "emp::" + bod2,
                            divBodName1: bodName1,
                            divBodName2: bodName2,
                            divBodStatus: status,
                            divBodCreatedAt: new Date()
                        }
                    }
                }, function(res) {
                    Page.Variables.read_divisionBod.invoke();
                    Page.Widgets.edit_spinner.show = false;
                    Page.Widgets.EditDialog.close();
                    App.Actions.appNotification.setMessage("Data Updated Successfully")
                    App.Actions.appNotification.setToasterClass("success")
                    App.Actions.appNotification.setToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                });
            } else {
                Page.Widgets.edit_spinner.show = false;
                App.Actions.appNotification.setMessage("Data Division Already exist")
                App.Actions.appNotification.setToasterClass("warning")
                App.Actions.appNotification.setToasterDuration(5000)
                App.Actions.appNotification.invoke()
            }
        })
    }
};



//Searching
Page.select1Change = function($event, widget, newVal, oldVal) {
    if (newVal == '') {
        Page.Variables.read_divisionBod.filterExpressions.rules[0].value = '';
        Page.Variables.read_divisionBod.filterExpressions.rules[1].value = '';
        Page.Variables.read_divisionBod.filterExpressions.rules[2].value = '';
        Page.Variables.read_divisionBod.filterExpressions.rules[3].value = '';
        Page.Widgets.search_data.datavalue = ''
        Page.Variables.read_divisionBod.invoke()
    }
};

Page.search_dataSubmit = function($event, widget) {
    switch (Page.Widgets.select1.datavalue) {
        case '1':
            Page.Variables.read_divisionBod.filterExpressions.rules[0].value = Page.Widgets.search_data.query;
            Page.Variables.read_divisionBod.filterExpressions.rules[1].value = '';
            Page.Variables.read_divisionBod.filterExpressions.rules[2].value = '';
            Page.Variables.read_divisionBod.filterExpressions.rules[3].value = '';
            break;
        case '2':
            Page.Variables.read_divisionBod.filterExpressions.rules[0].value = '';
            Page.Variables.read_divisionBod.filterExpressions.rules[1].value = Page.Widgets.search_data.query;
            Page.Variables.read_divisionBod.filterExpressions.rules[2].value = '';
            Page.Variables.read_divisionBod.filterExpressions.rules[3].value = '';
            break;
        case '3':
            Page.Variables.read_divisionBod.filterExpressions.rules[0].value = '';
            Page.Variables.read_divisionBod.filterExpressions.rules[1].value = '';
            Page.Variables.read_divisionBod.filterExpressions.rules[2].value = Page.Widgets.search_data.query;
            Page.Variables.read_divisionBod.filterExpressions.rules[3].value = '';
            break;
        case '4':
            Page.Variables.read_divisionBod.filterExpressions.rules[0].value = '';
            Page.Variables.read_divisionBod.filterExpressions.rules[1].value = '';
            Page.Variables.read_divisionBod.filterExpressions.rules[2].value = '';
            Page.Variables.read_divisionBod.filterExpressions.rules[3].value = Page.Widgets.search_data.query;
            break;
        default:
            Page.Variables.read_divisionBod.filterExpressions.rules[0].value = '';
            Page.Variables.read_divisionBod.filterExpressions.rules[1].value = '';
            Page.Variables.read_divisionBod.filterExpressions.rules[2].value = '';
            Page.Variables.read_divisionBod.filterExpressions.rules[3].value = '';
            Page.Variables.read_divisionBod.filterExpressions.rules[4].value = '';
            break;
    }
    Page.Variables.read_divisionBod.invoke()
};

// Show Row
Page.select_dataChange = function($event, widget, newVal, oldVal) {
    Page.Variables.read_divisionBod.maxResults = Number(Page.Widgets.select_data.datavalue)
    Page.Variables.read_divisionBod.invoke()
};


//Sorting Data
Page.TableSort = function(field) {
    if (Page.Variables.read_divisionBod.orderBy == field + " ASC") {
        Page.Variables.read_divisionBod.orderBy = field + " DESC"
    } else if (Page.Variables.read_divisionBod.orderBy == field + " DESC") {
        Page.Variables.read_divisionBod.orderBy = ""
    } else {
        Page.Variables.read_divisionBod.orderBy = field + " ASC"
    }

    Page.Variables.read_divisionBod.update()
}

Page.kolom_divisionClick = function($event, widget) {
    Page.TableSort("divBodTitle");
    if (Page.Variables.read_divisionBod.orderBy == 'divBodTitle ASC') {
        Page.Widgets.picture_division.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.read_divisionBod.orderBy == 'divBodTitle DESC') {
        Page.Widgets.picture_division.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_division.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.kolom_bod1Click = function($event, widget) {
    Page.TableSort("divBodName1");
    if (Page.Variables.read_divisionBod.orderBy == 'divBodName1 ASC') {
        Page.Widgets.picture_bod1.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.read_divisionBod.orderBy == 'divBodName1 DESC') {
        Page.Widgets.picture_bod1.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_bod1.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};



Page.kolom_bod2Click = function($event, widget) {
    Page.TableSort("divBodName2");
    if (Page.Variables.read_divisionBod.orderBy == 'divBodName2 ASC') {
        Page.Widgets.picture_bod2.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.read_divisionBod.orderBy == 'divBodName2 DESC') {
        Page.Widgets.picture_bod2.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_bod2.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.kolom_statusClick = function($event, widget) {
    Page.TableSort("divBodStatus");

    if (Page.Variables.read_divisionBod.orderBy == 'divBodStatus ASC') {
        Page.Widgets.picture_status.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.read_divisionBod.orderBy == 'divBodStatus DESC') {
        Page.Widgets.picture_status.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_status.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

//Form Action 
Page.add_divisionChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.add_label_divison.show = false
};

Page.add_bod1Select = function($event, widget, selectedValue) {
    if (selectedValue != undefined) {
        Page.Widgets.add_label_bod1.show = false;
    }
    Page.Widgets.add_label_bod1.show = false
};

Page.add_bod2Select = function($event, widget, selectedValue) {
    Page.Widgets.add_label_bod2.show = false
};

Page.edit_divisionChange = function($event, widget, newVal, oldVal) {
    if (selectedValue != undefined) {
        Page.Widgets.add_label_bod1.show = false;
    }
    Page.Widgets.edit_label_division.show = false
};

Page.edit_bod1Select = function($event, widget, selectedValue) {
    Page.Widgets.edit_label_bod1.show = false
};

Page.edit_bod2Select = function($event, widget, selectedValue) {
    Page.Widgets.edit_label_bod2.show = false
};



//Excel
//Export Excel
Page.button_excelClick = function($event, widget) {
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('Mapping Division to BOD ');
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

    let data = Page.Variables.read_divisionBod.dataSet
    let row = 2;


    // header
    sheet.getCell("A1").value = "Divison";
    sheet.getCell("B1").value = "BOD 1";
    sheet.getCell("C1").value = "BOD 2";
    sheet.getCell("D1").value = "Status";
    boldCell(["A1", "B1", "C1", "D1"]);
    sheet.getRow(1).alignment = alignCenter;


    data.forEach(el => {
        console.log(el)
        sheet.getCell("A" + row).value = el.divBodTitle.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
        sheet.getCell("B" + row).value = el.divBod1.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace("emp::", "") + ' - ' + el.divBodName1.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
        sheet.getCell("C" + row).value = el.divBod2.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace("emp::", "") + ' - ' + el.divBodName2.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
        sheet.getCell("D" + row).value = el.divBodStatus.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
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
        saveAs(blob, "MappingDivisionToBod" + currentTime + ".xlsx");
    })
};
