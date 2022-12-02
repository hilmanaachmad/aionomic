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

var selectedItem;


Page.SaveBtnClick = async function($event, widget) {

    Page.Widgets.spinner.show = true;
    Page.Widgets.errorcost.show = false;
    Page.Widgets.errordiv.show = false;
    Page.Widgets.errordep.show = false;
    Page.Widgets.errorpurchaseorg.show = false;
    //Validation 
    var isValid = true;
    if (Page.Widgets.CostCenterSelect.datavalue == undefined) {
        isValid = false
        Page.Widgets.errorcost.show = true;
    }
    if (Page.Widgets.DepartmentSelect.datavalue == undefined) {
        isValid = false
        Page.Widgets.errordep.show = true;
    }
    if (Page.Widgets.divisionSelect.datavalue == undefined) {
        isValid = false
        Page.Widgets.errordiv.show = true;
    }

    if (Page.Widgets.selectPurchOrg.datavalue == undefined) {
        isValid = false
        Page.Widgets.errorpurchaseorg.show = true;
    }


    // Page.Variables.forCheckCostCenter.listRecords({
    //     filterFields: {
    //         "costCenterId": {
    //             "value": Page.Widgets.CostCenterSelect.datavalue
    //         }
    //     }
    // }).then(result => {
    //     console.log(result.data.length)
    //     if (result.data.length == 0) {
    //         console.log('jalan')
    //         var Obj = Page.Variables.getCostCenter.dataSet;
    //         var SelectedCostCenter = Obj.filter(item =>
    //             item.costCenter == Page.Widgets.CostCenterSelect.datavalue)
    //         Page.Variables.insertMapCostCenter.invoke({
    //             inputFields: {
    //                 costCenterId: Page.Widgets.CostCenterSelect.datavalue,
    //                 costCenterTitle: SelectedCostCenter[0].longText,
    //                 departmentId: Page.Widgets.DepartmentSelect.datavalue,
    //                 departmentTitle: Page.Widgets.DepartmentSelect.displayValue,
    //                 divisionId: Page.Widgets.divisionSelect.datavalue,
    //                 divisionTitle: Page.Widgets.divisionSelect.displayValue,
    //                 purchOrg: Page.Widgets.selectPurchOrg.datavalue,
    //                 purchOrgDesc: Page.Widgets.selectPurchOrg.displayValue,
    //                 omStatus: 'active',
    //             }
    //         }, function(res) {
    //             console.log(res)
    //             Page.Variables.Aio_ptpTblMOrganizationMapData.invoke();
    //             Page.Widgets.spinner.show = false
    //             Page.Widgets.dialogAdd.close();
    //             App.Actions.appNotification.setMessage("Data Saved Successfully")
    //             App.Actions.appNotification.setToasterClass("success")
    //             App.Actions.appNotification.setToasterDuration(4000)
    //             App.Actions.appNotification.invoke()
    //         });
    //     }
    //     if (result.data.length > 0) {
    //         isValid = false;
    //         App.Actions.appNotification.setMessage("Data Costcenter already exist")
    //         App.Actions.appNotification.setToasterClass("warning")
    //         App.Actions.appNotification.setToasterDuration(4000)
    //         App.Actions.appNotification.invoke()
    //         Page.Widgets.spinner.show = false;
    //     }
    // })

    if (isValid == false) {
        Page.Widgets.spinner.show = false
        return
    }



    if (isValid == true) {
        var Obj = await Page.Variables.getCostCenter.dataSet;
        console.log(Obj)
        var SelectedCostCenter = await Obj.filter(item =>
            item.costCenter == Page.Widgets.CostCenterSelect.datavalue)

        console.log('data : ' + SelectedCostCenter)
        Page.Variables.insertMapCostCenter.invoke({
            inputFields: {
                // costCenterId: Page.Widgets.CostCenterSelect.datavalue,
                // costCenterTitle: SelectedCostCenter[0].longText,
                // departmentId: Page.Widgets.DepartmentSelect.datavalue,
                // departmentTitle: Page.Widgets.DepartmentSelect.displayValue,
                // divisionId: Page.Widgets.divisionSelect.datavalue,
                // divisionTitle: Page.Widgets.divisionSelect.displayValue,
                // omStatus: 'active',
                costCenterId: Page.Widgets.CostCenterSelect.datavalue.costCenter,
                costCenterTitle: Page.Widgets.CostCenterSelect.datavalue.longText,
                departmentId: Page.Widgets.DepartmentSelect.datavalue.departmentId,
                departmentTitle: Page.Widgets.DepartmentSelect.datavalue.departmentTitle,
                divisionId: Page.Widgets.divisionSelect.datavalue,
                divisionTitle: Page.Widgets.divisionSelect.displayValue,
                purchOrg: Page.Widgets.selectPurchOrg.datavalue,
                purchOrgDesc: Page.Widgets.selectPurchOrg.displayValue,
                omStatus: 'active',
                omCreatedAt: new Date()
            }
        }, function(res) {
            console.log(res)
            Page.Variables.Aio_ptpTblMOrganizationMapData.invoke();
            Page.Widgets.spinner.show = false
            Page.Widgets.dialogAdd.close();
            App.Actions.appNotification.setMessage("Data Saved Successfully")
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.setToasterDuration(4000)
            App.Actions.appNotification.invoke()
        });
    }

};

Page.updateClick = async function($event, widget) {
    Page.Widgets.spinner2.show = true;
    console.log(Page.Widgets.editDepartment.displayValue)
    // var Obj = await Page.Variables.getCostCenter.dataSet;
    // console.log(Obj)
    // var SelectedCostCenter = await Obj.filter(item =>
    //     item.costCenter == Page.Widgets.editCost.datavalue)
    // console.log('data : ' + SelectedCostCenter)
    Page.Variables.updateMapping.invoke({
        inputFields: {
            omId: Number(selectedItem.omId),
            TblMOrganizationMap: {
                costCenterId: Page.Widgets.editCost.datavalue.costCenter,
                costCenterTitle: Page.Widgets.editCost.datavalue.longText,
                departmentId: Page.Widgets.editDepartment.datavalue.departmentId,
                departmentTitle: Page.Widgets.editDepartment.datavalue.departmentTitle,
                divisionId: Page.Widgets.editDivision.datavalue,
                divisionTitle: Page.Widgets.editDivision.displayValue,
                omStatus: Page.Widgets.toggle1.datavalue,
                purchOrg: Page.Widgets.selectPurchOrgEdit.datavalue,
                purchOrgDesc: Page.Widgets.selectPurchOrgEdit.displayValue,
                omCreatedAt: selectedItem.omCreatedAt
            }
        }
    }, function(res) {
        Page.Variables.Aio_ptpTblMOrganizationMapData.invoke();
        Page.Widgets.spinner2.show = false;
        Page.Widgets.EditDialog.close();
        App.Actions.appNotification.setMessage("Data Updated Successfully")
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    });

    // Page.Variables.forCheckCostCenter.listRecords({
    //     filterFields: {
    //         "costCenterId": {
    //             "value": Page.Widgets.editCost.datavalue
    //         }
    //     }
    // }).then(result => {
    //     console.log(result.data.length)
    //     if (result.data.length > 0) {
    //         if (result.data.data[0].omId == Number(selectedItem.omId) && result.data.data[0].costCenterId == Page.Widgets.editCost.datavalue) {
    //             Page.Variables.updateMapping.invoke({
    //                 inputFields: {
    //                     omId: Number(selectedItem.omId),
    //                     TblMOrganizationMap: {
    //                         costCenterId: Page.Widgets.editCost.datavalue,
    //                         costCenterTitle: SelectedCostCenter[0].longText,
    //                         departmentId: Page.Widgets.editDepartment.datavalue,
    //                         departmentTitle: Page.Widgets.editDepartment.displayValue,
    //                         divisionId: Page.Widgets.editDivision.datavalue,
    //                         divisionTitle: Page.Widgets.editDivision.displayValue,
    //                         omStatus: Page.Widgets.toggle1.datavalue,
    //                         purchOrg: Page.Widgets.selectPurchOrgEdit.datavalue,
    //                         purchOrgDesc: Page.Widgets.selectPurchOrgEdit.displayValue,
    //                     }
    //                 }
    //             }, function(res) {
    //                 Page.Variables.Aio_ptpTblMOrganizationMapData.invoke();
    //                 Page.Widgets.spinner2.show = false;
    //                 Page.Widgets.EditDialog.close();
    //                 App.Actions.appNotification.setMessage("Data Updated Successfully")
    //                 App.Actions.appNotification.setToasterClass("success")
    //                 App.Actions.appNotification.setToasterDuration(5000)
    //                 App.Actions.appNotification.invoke()
    //             });
    //         } else {
    //             App.Actions.appNotification.setMessage("Data Costcenter already exist")
    //             App.Actions.appNotification.setToasterClass("warning")
    //             App.Actions.appNotification.setToasterDuration(4000)
    //             App.Actions.appNotification.invoke()
    //             Page.Widgets.spinner2.show = false;

    //         }

    //     } else {
    //         Page.Variables.updateMapping.invoke({
    //             inputFields: {
    //                 omId: Number(selectedItem.omId),
    //                 TblMOrganizationMap: {
    //                     costCenterId: Page.Widgets.editCost.datavalue,
    //                     costCenterTitle: SelectedCostCenter[0].longText,
    //                     departmentId: Page.Widgets.editDepartment.datavalue,
    //                     departmentTitle: Page.Widgets.editDepartment.displayValue,
    //                     divisionId: Page.Widgets.editDivision.datavalue,
    //                     divisionTitle: Page.Widgets.editDivision.displayValue,
    //                     omStatus: Page.Widgets.toggle1.datavalue,
    //                     purchOrg: Page.Widgets.selectPurchOrgEdit.datavalue,
    //                     purchOrgDesc: Page.Widgets.selectPurchOrgEdit.displayValue,
    //                 }
    //             }
    //         }, function(res) {
    //             Page.Variables.Aio_ptpTblMOrganizationMapData.invoke();
    //             Page.Widgets.spinner2.show = false;
    //             Page.Widgets.EditDialog.close();
    //             App.Actions.appNotification.setMessage("Data Updated Successfully")
    //             App.Actions.appNotification.setToasterClass("success")
    //             App.Actions.appNotification.getToasterDuration(5000)
    //             App.Actions.appNotification.invoke()
    //         });
    //     }
    // })


};

Page.button9Click = function($event, widget, item, currentItemWidgets) {
    selectedItem = item;
    Page.Widgets.deleteConfirm.open();
};
Page.deleteConfirmOk = function($event, widget) {
    widget.close();
    Page.Variables.deleteMapCostCenter.invoke({
        inputFields: {
            omId: selectedItem.omId
        }
    }, function(res) {
        Page.Variables.Aio_ptpTblMOrganizationMapData.invoke();
    });

    App.Actions.appNotification.setMessage("Data Deleted Successfully")
    App.Actions.appNotification.setToasterClass("success")
    App.Actions.appNotification.getToasterDuration(5000)
    App.Actions.appNotification.invoke()
};
Page.button8Click = function($event, widget, item, currentItemWidgets) {
    selectedItem = item;
    Page.Widgets.EditDialog.open();
    console.log(selectedItem)
};
Page.EditDialogOpened = function($event, widget) {
    // console.log(Page.Widgets.editDepartment)
    Page.Widgets.editDivision.datavalue = Number(selectedItem.divisionId);
    Page.Widgets.editDepartment.datavalue = {
        departmentId: Number(selectedItem.departmentId),
        departmentTitle: selectedItem.departmentTitle
    };
    Page.Widgets.editCost.datavalue = {
        costCenter: Number(selectedItem.costCenterId),
        longText: selectedItem.costCenterTitle
    };
    Page.Widgets.toggle1.datavalue = selectedItem.omStatus;
    Page.Widgets.selectPurchOrgEdit.datavalue = selectedItem.purchOrg;


    //     departmentId: Page.Widgets.DepartmentSelect.datavalue.departmentId,
    // departmentTitle: Page.Widgets.DepartmentSelect.datavalue.departmentTitle,
};


//Sorting Data
Page.TableSort = function(field) {
    if (Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy == field + " ASC") {
        Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy = field + " DESC"
    } else if (Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy == field + " DESC") {
        Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy = ""
    } else {
        Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy = field + " ASC"
    }
    Page.Variables.Aio_ptpTblMOrganizationMapData.update()
}

Page.container11Click = function($event, widget) {
    //Department
    Page.TableSort("departmentTitle");

    if (Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy == 'departmentTitle ASC') {
        Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy == 'departmentTitle DESC') {
        Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.container12Click = function($event, widget) {
    //division
    Page.TableSort("divisionTitle")
    console.log(Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy)

    if (Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy == 'divisionTitle ASC') {
        Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy == 'divisionTitle DESC') {
        Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.container13Click = function($event, widget) {
    // Cost Center
    Page.TableSort("costCenterTitle")
    if (Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy == 'costCenterTitle ASC') {
        Page.Widgets.picture3.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy == 'costCenterTitle DESC') {
        Page.Widgets.picture3.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture3.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.container14Click = function($event, widget) {
    //Status
    Page.TableSort("omStatus")

    if (Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy == 'omStatus ASC') {
        Page.Widgets.picture4.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.Aio_ptpTblMOrganizationMapData.orderBy == 'omStatus DESC') {
        Page.Widgets.picture4.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture4.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

//Export Excel
Page.button_excelClick = function($event, widget) {
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

    let data = Page.Variables.Aio_ptpTblMOrganizationMapData.dataSet
    let row = 2;


    // header
    sheet.getCell("A1").value = "Department";
    sheet.getCell("B1").value = "Division";
    sheet.getCell("C1").value = "Cost Center";
    sheet.getCell("D1").value = "Status";
    boldCell(["A1", "B1", "C1", "D1"]);
    sheet.getRow(1).alignment = alignCenter;


    data.forEach(el => {
        sheet.getCell("A" + row).value = el.departmentTitle.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("B" + row).value = el.divisionTitle.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("C" + row).value = el.costCenterTitle.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("D" + row).value = el.omStatus.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
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
        saveAs(blob, "CostCenterDeptDivReport_" + currentTime + ".xlsx");
    })
};

//Filter Pencarian
Page.search1Submit = function($event, widget) {
    switch (Page.Widgets.select1.datavalue) {
        case '1':
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[0].value = Page.Widgets.search1.query;
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[1].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[2].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[3].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[4].value = '';
            break;
        case '2':
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[0].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[1].value = Page.Widgets.search1.query;
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[2].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[3].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[4].value = '';
            break;
        case '3':
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[0].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[1].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[2].value = Page.Widgets.search1.query;
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[3].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[4].value = Page.Widgets.search1.query;
            break;
        case '4':
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[0].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[1].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[2].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[3].value = Page.Widgets.search1.query;
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[4].value = '';
            break;
        default:
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[0].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[1].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[2].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[3].value = '';
            Page.Variables.Aio_ptpTblMOrganizationMapData.filterExpressions.rules[4].value = '';
            break;
    }
    Page.Variables.Aio_ptpTblMOrganizationMapData.invoke()
};

//Numbering
Page.TblMRoleList1Beforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].num = (Page.Variables.Aio_ptpTblMOrganizationMapData.pagination.number * Page.Variables.Aio_ptpTblMOrganizationMapData.pagination.size) + (i + 1);
    }

};

//Jumlah data dalam list
Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.Aio_ptpTblMOrganizationMapData.maxResults = Number(Page.Widgets.select9.datavalue)
    Page.Variables.Aio_ptpTblMOrganizationMapData.invoke()
};
Page.divisionSelectChange = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.divisionSelect.datavalue != undefined) {
        Page.Widgets.errordiv.show = false;
    }
};

Page.selectPurchOrgChange = function($event, widget, newVal, oldVal) {


    if (Page.Widgets.selectPurchOrg.datavalue != undefined) {
        Page.Widgets.errorpurchaseorg.show = false;
    }
};


Page.CostCenterSelectChange = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.CostCenterSelect.datavalue != undefined) {
        Page.Widgets.errorcost.show = false;
    }
};
Page.button9ClickClick = function($event, widget, item, currentItemWidgets) {

};
Page.button_export_allClick = function($event, widget) {

    console.log(Page.Variables.read_deptDivCostCenter)
    Page.App.getVDBQAllRecords(Page.Variables.read_deptDivCostCenter).then(function(res) {
        // var data = JSON.parse(res.body)
        // console.log("export", res)
        return Page.export_all(res)
    }).then(function() {
        Page.Widgets.spinner1.show = false
    })
};


Page.export_all = function(data) {
    console.log(data)
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

    let row = 2;


    // header
    sheet.getCell("A1").value = "Department";
    sheet.getCell("B1").value = "Division";
    sheet.getCell("C1").value = "Cost Center";
    sheet.getCell("D1").value = "Status";
    boldCell(["A1", "B1", "C1", "D1"]);
    sheet.getRow(1).alignment = alignCenter;


    data.forEach(el => {
        sheet.getCell("A" + row).value = el.departmentTitle.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("B" + row).value = el.divisionTitle.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("C" + row).value = el.costCenterTitle.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("D" + row).value = el.omStatus.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
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
        saveAs(blob, "CostCenterDeptDivReport_" + currentTime + ".xlsx");
    })
}

Page.DepartmentSelectChange = function($event, widget, newVal, oldVal) {
    console.log(Page.Widgets.DepartmentSelect.datavalue.departmentId)
    console.log(newVal)
    if (Page.Widgets.DepartmentSelect.datavalue != undefined) {
        Page.Widgets.errordep.show = false;
    }
};
Page.DepartmentSelectSelect = function($event, widget, selectedValue) {
    if (selectedValue != undefined) {
        Page.Widgets.errordep.show = false;
    }
};
Page.CostCenterSelectSelect = function($event, widget, selectedValue) {
    if (selectedValue != undefined) {
        Page.Widgets.errorcost.show = false;
    }
};