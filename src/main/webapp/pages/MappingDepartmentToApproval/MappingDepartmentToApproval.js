/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('MST-010') !== -1) {

        } else {
            App.Actions.goToPage_Main.invoke();
        }
    }
};


Page.tableBeforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].num = (Page.Variables.DBDeptApproval.pagination.number * Page.Variables.DBDeptApproval.pagination.size) + (i + 1);
    }
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

Page.kolomDepartmentClick = function($event, widget) {
    Page.TableSort("departmentName");
    if (Page.Variables.DBDeptApproval.orderBy == 'departmentName ASC') {
        Page.Widgets.picture_department.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.DBDeptApproval.orderBy == 'departmentName DESC') {
        Page.Widgets.picture_department.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_department.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.kolomSectionHeadClick = function($event, widget) {
    Page.TableSort("sectionHeadName");
    if (Page.Variables.DBDeptApproval.orderBy == 'sectionHeadName ASC') {
        Page.Widgets.picture_sectionhead.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.DBDeptApproval.orderBy == 'sectionHeadName DESC') {
        Page.Widgets.picture_sectionhead.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_sectionhead.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.kolomDeptHeadClick = function($event, widget) {
    Page.TableSort("deptHeadName");
    if (Page.Variables.DBDeptApproval.orderBy == 'deptHeadName ASC') {
        Page.Widgets.picture_depthead.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.DBDeptApproval.orderBy == 'deptHeadName DESC') {
        Page.Widgets.picture_depthead.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_depthead.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.kolomStatusClick = function($event, widget) {
    Page.TableSort("status");
    if (Page.Variables.DBDeptApproval.orderBy == 'status ASC') {
        Page.Widgets.picture_status.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.DBDeptApproval.orderBy == 'status DESC') {
        Page.Widgets.picture_status.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_status.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};


//Tambah Data
Page.btn_saveClick = function($event, widget) {
    var departmentId = ''
    var departmentName = ''
    var sectionHead = ''
    var sectionHeadName = ''
    var deptHead = ''
    var deptHeadName = ''

    if (Page.Widgets.addDepartment.datavalue !== '' && Page.Widgets.addDepartment.datavalue !== undefined) {
        departmentId = Page.Widgets.addDepartment.datavalue.departmentCode
        departmentName = Page.Widgets.addDepartment.datavalue.departmentTitle
    }

    if (Page.Widgets.addSectionHead.datavalue !== '' && Page.Widgets.addSectionHead.datavalue !== undefined) {
        sectionHead = Page.Widgets.addSectionHead.datavalue.nik
        sectionHeadName = Page.Widgets.addSectionHead.datavalue.employeeName
    }

    if (Page.Widgets.addDeptHead.datavalue !== '' && Page.Widgets.addDeptHead.datavalue !== undefined) {
        deptHead = Page.Widgets.addDeptHead.datavalue.nik
        deptHeadName = Page.Widgets.addDeptHead.datavalue.employeeName
    }


    Page.Widgets.add_spinner.show = true
    var formValid = true
    if (departmentId == undefined || departmentId == '' || departmentId == null) {
        formValid = false
        Page.Widgets.add_label_divison.show = true
        Page.Widgets.add_spinner.show = false
    }

    if (sectionHead == undefined || sectionHead == '' || sectionHead == null) {
        formValid = false
        Page.Widgets.add_label_bod1.show = true
        Page.Widgets.add_spinner.show = false
    }


    if (deptHead == undefined || deptHead == '' || deptHead == null) {
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
                "departmentId": {
                    "value": departmentId
                },
            }
        }, function(result) {
            if (result.data.length == 0) {
                Page.Variables.DBInsertDeptApproval.invoke({
                    inputFields: {
                        departmentId: departmentId,
                        sectionHead: sectionHead,
                        deptHead: deptHead,
                        status: 'Active',
                        createdBy: Page.App.Variables.loggedInUser.dataSet.name,
                        createdAt: dformat,
                        departmentName: departmentName,
                        sectionHeadName: sectionHeadName,
                        deptHeadName: deptHeadName
                    }
                }, function(data) {
                    Page.Widgets.add_spinner.show = false
                    Page.Widgets.dialogAdd.close()
                    Page.Variables.DBDeptApproval.invoke()
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
    Page.Variables.DBDeleteDeptApproval.invoke({
        inputFields: {
            id: selectedItem.id
        }
    }, function(res) {
        Page.Variables.DBDeptApproval.invoke();
    });

    App.Actions.appNotification.setMessage("Data Deleted Successfully")
    App.Actions.appNotification.setToasterClass("success")
    App.Actions.appNotification.getToasterDuration(5000)
    App.Actions.appNotification.invoke()
};



var selectedItem;
//Edit data
Page.btn_trigger_editClick = function($event, widget, item, currentItemWidgets) {
    selectedItem = item;
    Page.Widgets.EditDialog.open();
};

Page.EditDialogOpened = function($event, widget) {
    Page.Widgets.editDepartment.datavalue = selectedItem.departmentId
    Page.Widgets.editSectionHead.datavalue = selectedItem.sectionHead
    Page.Widgets.editDeptHead.datavalue = selectedItem.deptHead
    Page.Widgets.toggle1.datavalue = selectedItem.status;
};

Page.btn_updateClick = async function($event, widget) {
    var departmentId = Page.Widgets.editDepartment.datavalue
    var sectionHead = Page.Widgets.editSectionHead.datavalue
    var deptHead = Page.Widgets.editDeptHead.datavalue
    var status = Page.Widgets.toggle1.datavalue


    Page.Widgets.edit_spinner.show = true
    var formValid = true
    if (departmentId == undefined || departmentId == '' || departmentId == null) {
        formValid = false
        Page.Widgets.edit_label_division.show = true
        Page.Widgets.edit_spinner.show = false
    }

    if (sectionHead == undefined || sectionHead == '' || sectionHead == null) {
        formValid = false
        Page.Widgets.edit_label_bod1.show = true
        Page.Widgets.edit_spinner.show = false
    }


    if (deptHead == undefined || deptHead == '' || deptHead == null) {
        formValid = false
        Page.Widgets.edit_label_bod2.show = true
        Page.Widgets.edit_spinner.show = false
    }

    if (formValid == true) {
        var getSectionHead = await Page.Variables.get_employee.listRecords({
            filterFields: {
                "nik": {
                    "value": sectionHead
                },
            }
        })
        var getDeptHead = await Page.Variables.get_employee.listRecords({
            filterFields: {
                "nik": {
                    "value": deptHead
                },
            }
        })
        var sectionHeadName = '-'
        if (getSectionHead.data.length > 0) {
            sectionHeadName = getSectionHead.data[0].employeeName
        }
        var deptHeadName = '-'
        if (getDeptHead.data.length > 0) {
            deptHeadName = getDeptHead.data[0].employeeName
        }

        var filterDepartment = Page.Variables.DBGetDepartment.dataSet.filter(item =>
            item.departmentCode == departmentId
        )

        var departmentName = '-'
        if (filterDepartment.length > 0) {
            departmentName = filterDepartment[0].departmentTitle
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
                "departmentId": {
                    "value": departmentId
                },
            }
        }, function(result) {
            if (result.data.length == 0 || (result.data.length > 0 && result.data[0].divBodId == selectedItem.divBodId)) {
                Page.Variables.DBUpdateDeptApproval.invoke({
                    inputFields: {
                        id: Number(selectedItem.id),
                        departmentId: departmentId,
                        sectionHead: sectionHead,
                        deptHead: deptHead,
                        status: status,
                        createdBy: Page.App.Variables.loggedInUser.dataSet.name,
                        createdAt: dformat,
                        departmentName: departmentName,
                        sectionHeadName: sectionHeadName,
                        deptHeadName: deptHeadName
                    }
                }, function(res) {
                    Page.Variables.DBDeptApproval.invoke();
                    Page.Widgets.edit_spinner.show = false;
                    Page.Widgets.EditDialog.close();
                    App.Actions.appNotification.setMessage("Data Updated Successfully")
                    App.Actions.appNotification.setToasterClass("success")
                    App.Actions.appNotification.setToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                });
            } else {
                Page.Widgets.edit_spinner.show = false;
                App.Actions.appNotification.setMessage("Data Department Already exist")
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
        Page.Variables.DBDeptApproval.filterExpressions.rules[0].value = '';
        Page.Variables.DBDeptApproval.filterExpressions.rules[1].value = '';
        Page.Variables.DBDeptApproval.filterExpressions.rules[2].value = '';
        Page.Variables.DBDeptApproval.filterExpressions.rules[3].value = '';
        Page.Widgets.search_data.datavalue = ''
        Page.Variables.DBDeptApproval.invoke()
    }
};

Page.search_dataSubmit = function($event, widget) {
    switch (Page.Widgets.select1.datavalue) {
        case '1':
            Page.Variables.DBDeptApproval.filterExpressions.rules[0].value = Page.Widgets.search_data.query;
            Page.Variables.DBDeptApproval.filterExpressions.rules[1].value = '';
            Page.Variables.DBDeptApproval.filterExpressions.rules[2].value = '';
            Page.Variables.DBDeptApproval.filterExpressions.rules[3].value = '';
            break;
        case '2':
            Page.Variables.DBDeptApproval.filterExpressions.rules[0].value = '';
            Page.Variables.DBDeptApproval.filterExpressions.rules[1].value = Page.Widgets.search_data.query;
            Page.Variables.DBDeptApproval.filterExpressions.rules[2].value = '';
            Page.Variables.DBDeptApproval.filterExpressions.rules[3].value = '';
            break;
        case '3':
            Page.Variables.DBDeptApproval.filterExpressions.rules[0].value = '';
            Page.Variables.DBDeptApproval.filterExpressions.rules[1].value = '';
            Page.Variables.DBDeptApproval.filterExpressions.rules[2].value = Page.Widgets.search_data.query;
            Page.Variables.DBDeptApproval.filterExpressions.rules[3].value = '';
            break;
        case '4':
            Page.Variables.DBDeptApproval.filterExpressions.rules[0].value = '';
            Page.Variables.DBDeptApproval.filterExpressions.rules[1].value = '';
            Page.Variables.DBDeptApproval.filterExpressions.rules[2].value = '';
            Page.Variables.DBDeptApproval.filterExpressions.rules[3].value = Page.Widgets.search_data.query;
            break;
        default:
            Page.Variables.DBDeptApproval.filterExpressions.rules[0].value = '';
            Page.Variables.DBDeptApproval.filterExpressions.rules[1].value = '';
            Page.Variables.DBDeptApproval.filterExpressions.rules[2].value = '';
            Page.Variables.DBDeptApproval.filterExpressions.rules[3].value = '';
            Page.Variables.DBDeptApproval.filterExpressions.rules[4].value = '';
            break;
    }
    Page.Variables.DBDeptApproval.invoke()
};

// Show Row
Page.select_dataChange = function($event, widget, newVal, oldVal) {
    Page.Variables.DBDeptApproval.maxResults = Number(Page.Widgets.select_data.datavalue)
    Page.Variables.DBDeptApproval.invoke()
};










//Form Action 
Page.addDepartmentChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.add_label_divison.show = false
};

Page.addSectionHeadSelect = function($event, widget, selectedValue) {
    if (selectedValue != undefined) {
        Page.Widgets.add_label_bod1.show = false;
    }
    Page.Widgets.add_label_bod1.show = false
};

Page.addDeptHeadSelect = function($event, widget, selectedValue) {
    Page.Widgets.add_label_bod2.show = false
};

Page.editDepartmentChange = function($event, widget, newVal, oldVal) {
    if (selectedValue != undefined) {
        Page.Widgets.add_label_bod1.show = false;
    }
    Page.Widgets.edit_label_division.show = false
};

Page.editSectionHeadSelect = function($event, widget, selectedValue) {
    Page.Widgets.edit_label_bod1.show = false
};

Page.editDeptHeadSelect = function($event, widget, selectedValue) {
    Page.Widgets.edit_label_bod2.show = false
};



//Excel
//Export Excel
Page.button_excelClick = function($event, widget) {
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('Mapping Department to Approval ');
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

    let data = Page.Variables.DBDeptApproval.dataSet
    let row = 2;


    // header
    sheet.getCell("A1").value = "Department";
    sheet.getCell("B1").value = "Approval 1";
    sheet.getCell("C1").value = "Approval 2";
    sheet.getCell("D1").value = "Status";
    boldCell(["A1", "B1", "C1", "D1"]);
    sheet.getRow(1).alignment = alignCenter;


    data.forEach(el => {
        sheet.getCell("A" + row).value = el.departmentName.toString().replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
        sheet.getCell("B" + row).value = el.sectionHead.toString().replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace("emp::", "") + ' - ' + el.sectionHeadName.toString().replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
        sheet.getCell("C" + row).value = el.deptHead.toString().replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace("emp::", "") + ' - ' + el.deptHeadName.toString().replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
        sheet.getCell("D" + row).value = el.status.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
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
        saveAs(blob, "MappingDepartmentToApproval" + currentTime + ".xlsx");
    })
};
