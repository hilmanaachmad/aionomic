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
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('MST-009') !== -1) {

        } else {
            App.Actions.goToPage_Main.invoke();
        }
    }
};

// var selectedItem;


Page.tableBeforedatarender = function(widget, $data) {
    $data.map((i) => {
        i.eplApproxMax = Number(i.eplApproxMax),
            i.eplApproxMin = Number(i.eplApproxMin);
        return i;
    });


    for (var i = 0; i < $data.length; i++) {
        $data[i].num = (Page.Variables.readEmpPriceLimit.pagination.number * Page.Variables.readEmpPriceLimit.pagination.size) + (i + 1);
    }
};
Page.btn_saveClick = function($event, widget) {
    var employee_level = Page.Widgets.add_employee_level.datavalue
    var action = Page.Widgets.add_action.datavalue
    var approx_min = Page.Variables.model_add_approx_min.dataSet.dataValue.replace(/\D/g, '')
    var approx_max = Page.Variables.model_add_approx_max.dataSet.dataValue.replace(/\D/g, '')

    Page.Widgets.add_spinner.show = true
    var formValid = true
    if (employee_level == undefined || employee_level == '' || employee_level == null) {
        formValid = false
        Page.Widgets.add_label_employee_level.show = true
        Page.Widgets.add_spinner.show = false
    }

    if (action == undefined || action == '' || action == null) {
        formValid = false
        Page.Widgets.add_label_action.show = true
        Page.Widgets.add_spinner.show = false
    }


    if (approx_min == undefined || approx_min == '' || approx_min == null) {
        if (approx_min != 0) {
            formValid = false
            Page.Widgets.add_label_approx_min.caption = "This field is required"
            Page.Widgets.add_label_approx_min.show = true
            Page.Widgets.add_spinner.show = false
        }

    }

    if (approx_max == undefined || approx_max == '' || approx_max == null) {
        if (approx_max != 0) {
            formValid = false
            Page.Widgets.add_label_approx_max.caption = "This field is required"
            Page.Widgets.add_label_approx_max.show = true
            Page.Widgets.add_spinner.show = false
        }
    }

    if ((approx_min != undefined || approx_min != '' || approx_min != null) && (approx_max != undefined || approx_max != '' || approx_max != null)) {
        if (approx_min > approx_max) {
            formValid = false
            Page.Widgets.add_label_approx_min.caption = "Approx. Min Price must be less than Approx. Max Price"
            Page.Widgets.add_label_approx_min.show = true
            Page.Widgets.add_spinner.show = false
        }
        if (approx_max < approx_min) {
            formValid = false
            Page.Widgets.add_label_approx_max.caption = "Approx. Max Price must be greater than Approx. Min Price"
            Page.Widgets.add_label_approx_max.show = true
            Page.Widgets.add_spinner.show = false
        }
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
                "eplEmployeeLevel": {
                    "value": employee_level
                },
                "eplAction": {
                    "value": action
                }
            }
        }, function(result) {
            if (result.data.length == 0) {
                Page.Variables.insertEmpPriceLimit.invoke({
                    inputFields: {
                        eplEmployeeLevel: employee_level,
                        eplAction: action,
                        eplApproxMin: Number(approx_min),
                        eplApproxMax: Number(approx_max),
                        eplStatus: 'Active',
                        eplCreatedAt: dformat,
                        eplCreatedBy: Page.App.Variables.loggedInUser.dataSet.name
                    }
                }, function(data) {
                    Page.Widgets.add_spinner.show = false
                    Page.Widgets.dialogAdd.close()
                    Page.Variables.readEmpPriceLimit.invoke()
                    App.Actions.appNotification.setMessage("Data Insert Successfully")
                    App.Actions.appNotification.setToasterClass("success")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                })
            } else {
                App.Actions.appNotification.setMessage("Data already exist")
                App.Actions.appNotification.setToasterClass("warning")
                App.Actions.appNotification.setToasterDuration(4000)
                App.Actions.appNotification.invoke()
                Page.Widgets.add_spinner.show = false
            }
        })
    }
};


Page.add_employee_levelChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.add_label_employee_level.show = false
};

Page.add_actionChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.add_label_action.show = false
};

Page.add_approx_minKeypress = function($event, widget) {
    Page.Widgets.add_label_approx_min.show = false
    Page.Widgets.add_approx_min.maxchars = 15
};

Page.add_approx_maxKeypress = function($event, widget) {
    Page.Widgets.add_label_approx_max.show = false
};

//Sorting Data
Page.TableSort = function(field) {
    if (Page.Variables.readEmpPriceLimit.orderBy == field + " ASC") {
        Page.Variables.readEmpPriceLimit.orderBy = field + " DESC"
    } else if (Page.Variables.readEmpPriceLimit.orderBy == field + " DESC") {
        Page.Variables.readEmpPriceLimit.orderBy = ""
    } else {
        Page.Variables.readEmpPriceLimit.orderBy = field + " ASC"
    }

    Page.Variables.readEmpPriceLimit.update()
}


Page.kolom_levelClick = function($event, widget) {
    Page.TableSort("eplEmployeeLevel");

    if (Page.Variables.readEmpPriceLimit.orderBy == 'eplEmployeeLevel ASC') {
        Page.Widgets.picture_kolom.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.readEmpPriceLimit.orderBy == 'eplEmployeeLevel DESC') {
        Page.Widgets.picture_kolom.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_kolom.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};
Page.kolom_actionClick = function($event, widget) {
    Page.TableSort("eplAction");

    if (Page.Variables.readEmpPriceLimit.orderBy == 'eplAction ASC') {
        Page.Widgets.picture_action.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.readEmpPriceLimit.orderBy == 'eplAction DESC') {
        Page.Widgets.picture_action.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_action.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.kolom_approx_minClick = function($event, widget) {
    Page.TableSort("eplApproxMin");

    if (Page.Variables.readEmpPriceLimit.orderBy == 'eplApproxMin ASC') {
        Page.Widgets.picture_approx_min.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.readEmpPriceLimit.orderBy == 'eplApproxMin DESC') {
        Page.Widgets.picture_approx_min.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_approx_min.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.kolom_approx_maxClick = function($event, widget) {
    Page.TableSort("eplApproxMax");

    if (Page.Variables.readEmpPriceLimit.orderBy == 'eplApproxMax ASC') {
        Page.Widgets.picture_approx_max.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.readEmpPriceLimit.orderBy == 'eplApproxMax DESC') {
        Page.Widgets.picture_approx_max.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_approx_max.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.kolom_statusClick = function($event, widget) {
    Page.TableSort("eplStatus");

    if (Page.Variables.readEmpPriceLimit.orderBy == 'eplStatus ASC') {
        Page.Widgets.picture_status.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.readEmpPriceLimit.orderBy == 'eplStatus DESC') {
        Page.Widgets.picture_status.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_status.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.btn_trigger_deleteClick = function($event, widget, item, currentItemWidgets) {
    selectedItem = item;
    Page.Widgets.deleteConfirm.open();
};

Page.deleteConfirmOk = function($event, widget) {

    widget.close();
    Page.Variables.deleteEmpPriceLimit.invoke({
        inputFields: {
            eplId: selectedItem.eplId
        }
    }, function(res) {
        Page.Variables.readEmpPriceLimit.invoke();
    });

    App.Actions.appNotification.setMessage("Data Deleted Successfully")
    App.Actions.appNotification.setToasterClass("success")
    App.Actions.appNotification.getToasterDuration(5000)
    App.Actions.appNotification.invoke()
};
Page.search_dataSubmit = function($event, widget) {
    switch (Page.Widgets.select1.datavalue) {
        case '1':
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[0].value = Page.Widgets.search_data.query;
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[1].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[2].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[3].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[4].value = '';
            break;
        case '2':
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[0].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[1].value = Page.Widgets.search_data.query;
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[2].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[3].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[4].value = '';
            break;
        case '3':
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[0].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[1].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[2].value = Page.Widgets.search_data.query;
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[3].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[4].value = ''
            break;
        case '4':
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[0].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[1].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[2].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[3].value = Page.Widgets.search_data.query;
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[4].value = '';
            break;
        case '5':
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[0].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[1].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[2].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[3].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[4].value = Page.Widgets.search_data.query;
            break;
        default:
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[0].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[1].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[2].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[3].value = '';
            Page.Variables.readEmpPriceLimit.filterExpressions.rules[4].value = '';
            break;
    }
    Page.Variables.readEmpPriceLimit.invoke()
};


//Export Excel
Page.button_excelClick = function($event, widget) {
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('Mapping Employee Level to Price Limit ');;
    let columnWidth = [18, 9, 23, 23];

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

    let data = Page.Variables.readEmpPriceLimit.dataSet
    let row = 2;


    // header
    sheet.getCell("A1").value = App.appLocale["LANG_EMPLOYEE_LEVEL"];
    sheet.getCell("B1").value = App.appLocale["LANG_ACTION"];
    sheet.getCell("C1").value = App.appLocale["LANG_APPROX_MIN_PRICE"];
    sheet.getCell("D1").value = App.appLocale["LANG_APPROX_MAX_PRICE"];
    sheet.getCell("E1").value = App.appLocale["LANG_STATUS"];
    boldCell(["A1", "B1", "C1", "D1", "E1"]);
    sheet.getRow(1).alignment = alignCenter;


    data.forEach(el => {
        sheet.getCell("A" + row).value = el.eplEmployeeLevel.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
        sheet.getCell("A" + row).alignment = alignCenter
        sheet.getCell("B" + row).value = el.eplAction.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
        sheet.getCell("C" + row).value = el.eplApproxMin
        sheet.getCell("C" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("D" + row).value = el.eplApproxMax
        sheet.getCell("D" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("E" + row).value = el.eplStatus.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
        sheet.getCell("E" + row).alignment = alignCenter
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
        saveAs(blob, "MappingEmployeLevelToPriceLimit" + currentTime + ".xlsx");
    })
};

Page.btn_trigger_editClick = function($event, widget, item, currentItemWidgets) {
    selectedItem = item;
    Page.Widgets.EditDialog.open();
};

Page.EditDialogOpened = function($event, widget) {
    Page.Widgets.toggle1.datavalue = selectedItem.eplStatus
    $(document).ready(function() {
        $(".only_number").keypress(function(value) {
            var charCode = value.charCode
            if (charCode === 45) {
                return true
            } else {
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
            }

            return true;
        });
    });


    Page.Widgets.edit_employee_level.datavalue = selectedItem.eplEmployeeLevel
    Page.Widgets.edit_action.datavalue = selectedItem.eplAction
    // Page.Widgets.edit_approx_min.datavalue = Number(selectedItem.eplApproxMin)
    // Page.Widgets.edit_approx_max.datavalue = Number(selectedItem.eplApproxMax)
    Page.Variables.model_edit_approx_min.dataSet.dataValue = Number(selectedItem.eplApproxMin)
    Page.Variables.model_edit_approx_max.dataSet.dataValue = Number(selectedItem.eplApproxMax)
    Page.Widgets.toggle1.datavalue = selectedItem.eplStatus;
};


Page.btn_updateClick = function($event, widget) {
    var employee_level = Page.Widgets.edit_employee_level.datavalue
    var action = Page.Widgets.edit_action.datavalue
    var approx_min = Page.Variables.model_edit_approx_min.dataSet.dataValue
    var approx_max = Page.Variables.model_edit_approx_max.dataSet.dataValue
    if (!Number.isInteger(approx_min)) {
        approx_min = Page.Variables.model_edit_approx_min.dataSet.dataValue.replace(/\D/g, '')
    }
    if (!Number.isInteger(approx_max)) {
        approx_max = Page.Variables.model_edit_approx_max.dataSet.dataValue.replace(/\D/g, '')
    }
    var status = Page.Widgets.toggle1.datavalue


    Page.Widgets.edit_spinner.show = true
    var formValid = true
    if (employee_level == undefined || employee_level == '' || employee_level == null) {
        formValid = false
        Page.Widgets.edit_label_employee_level.show = true
        Page.Widgets.edit_spinner.show = false
    }

    if (action == undefined || action == '' || action == null) {
        formValid = false
        Page.Widgets.edit_label_action.show = true
        Page.Widgets.edit_spinner.show = false
    }


    if (approx_min == undefined || approx_min == '' || approx_min == null) {
        if (approx_min != 0) {
            formValid = false
            Page.Widgets.edit_label_approx_min.caption = "This field is required"
            Page.Widgets.edit_label_approx_min.show = true
            Page.Widgets.edit_spinner.show = false
        }

    }

    if (approx_max == undefined || approx_max == '' || approx_max == null) {
        if (approx_max != 0) {
            formValid = false
            Page.Widgets.edit_label_approx_max.caption = "This field is required"
            Page.Widgets.edit_label_approx_max.show = true
            Page.Widgets.edit_spinner.show = false
        }
    }


    if ((approx_min != undefined || approx_min != '' || approx_min != null) && (approx_max != undefined || approx_max != '' || approx_max != null)) {
        if (approx_min > approx_max) {
            formValid = false
            Page.Widgets.edit_label_approx_min.caption = "Approx. Min Price must be less than Approx. Max Price"
            Page.Widgets.edit_label_approx_min.show = true
            Page.Widgets.edit_spinner.show = false
        }
        if (approx_max < approx_min) {
            formValid = false
            Page.Widgets.edit_label_approx_max.caption = "Approx. Max Price must be greater than Approx. Min Price"
            Page.Widgets.edit_label_approx_max.show = true
            Page.Widgets.edit_spinner.show = false
        }
    }

    if (formValid == true) {
        Page.Variables.updateEmpPriceLimit.invoke({
            inputFields: {
                eplId: Number(selectedItem.eplId),
                TblMEmpPriceLimit: {
                    eplEmployeeLevel: employee_level,
                    eplAction: action,
                    eplApproxMin: Number(approx_min),
                    eplApproxMax: Number(approx_max),
                    eplStatus: status,
                    eplCreatedAt: new Date()
                }
            }
        }, function(res) {
            Page.Variables.readEmpPriceLimit.invoke();
            Page.Widgets.edit_spinner.show = false;
            Page.Widgets.EditDialog.close();
            App.Actions.appNotification.setMessage("Data Updated Successfully")
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
        });
    }
};
Page.select_dataChange = function($event, widget, newVal, oldVal) {
    Page.Variables.readEmpPriceLimit.maxResults = Number(Page.Widgets.select_data.datavalue)
    Page.Variables.readEmpPriceLimit.invoke()
};
Page.select1Change = function($event, widget, newVal, oldVal) {

    if (newVal == '') {
        Page.Variables.readEmpPriceLimit.filterExpressions.rules[0].value = '';
        Page.Variables.readEmpPriceLimit.filterExpressions.rules[1].value = '';
        Page.Variables.readEmpPriceLimit.filterExpressions.rules[2].value = '';
        Page.Variables.readEmpPriceLimit.filterExpressions.rules[3].value = '';
        Page.Variables.readEmpPriceLimit.filterExpressions.rules[4].value = '';
        Page.Widgets.search_data.datavalue = ''
        Page.Variables.readEmpPriceLimit.invoke()
    }
};

Page.dialogAddOpened = function($event, widget) {
    Page.Variables.model_add_approx_max.dataSet.dataValue = '';
    Page.Variables.model_add_approx_min.dataSet.dataValue = '';

    $(document).ready(function() {
        $(".only_number").keypress(function(value) {
            var charCode = value.charCode
            if (charCode === 45) {
                return true
            } else {
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
            }

            return true;
        });
    });
};




Page.add_approx_minChange = function($event, widget, newVal, oldVal) {
    Page.Variables.model_add_approx_min.dataSet.dataValue = Page.App.formatCurrency(Page.Widgets.add_approx_min.datavalue.replace(/\D/g, ''))
    Page.Widgets.add_label_approx_min.show = false
};
Page.add_approx_maxChange = function($event, widget, newVal, oldVal) {
    Page.Variables.model_add_approx_max.dataSet.dataValue = Page.App.formatCurrency(Page.Widgets.add_approx_max.datavalue.replace(/\D/g, ''))
    Page.Widgets.add_label_approx_max.show = false
};
Page.edit_approx_minChange = function($event, widget, newVal, oldVal) {
    Page.Variables.model_edit_approx_min.dataSet.dataValue = Page.App.formatCurrency(Page.Widgets.edit_approx_min.datavalue.replace(/\D/g, ''))
    Page.Widgets.edit_label_approx_min.show = false
};
Page.edit_approx_maxChange = function($event, widget, newVal, oldVal) {
    Page.Variables.model_edit_approx_max.dataSet.dataValue = Page.App.formatCurrency(Page.Widgets.edit_approx_max.datavalue.replace(/\D/g, ''))
    Page.Widgets.edit_label_approx_max.show = false
};
