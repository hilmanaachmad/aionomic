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
    // Page.Variables.getRoleUserMapList.pagination.sort = ['roleTitle ASC']

    //  handle authorization
    if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('MST-002') == -1) {
            App.Actions.goToPage_Main.invoke()
        }
    }
};

Page.totalAssign = function(data) {
    if (data) {
        // console.log(data)
        var dt = JSON.parse(JSON.stringify(data))
        var strArray = dt.split(",")

        if (strArray.length > 2) {
            var count = strArray.length
            return strArray[0] + ", " + strArray[1] + ", +" + (count - 2)
        }

        return strArray.join(", ")
    }

    return "-"
}

// add employee to list
Page.search2Select = function($event, widget, selectedValue) {
    var data = JSON.parse(JSON.stringify(selectedValue))
    var employee = {
        lgNik: "emp::" + data.nik,
        lgName: data.employeeName,
        isNew: true
    }

    if (Page.Variables.vmRoleUserModel.dataSet.urAdditionalData == 'Vendor') {
        employee.lgNik = "vend::" + data.vendorCode
        employee.lgName = data.vendorName
    }

    var alreadyExist = Page.Variables.vmEmployeeList.dataSet.filter(x => x.lgNik == employee.lgNik)
    if (alreadyExist.length == 0) {
        Page.Variables.vmEmployeeList.addItem(employee)
    }

    Page.Widgets.searchEmployee.datavalue = null
    Page.Widgets.searchVendor.datavalue = null
};

// submit insert or update Role
Page.button4Click = function($event, widget) {
    // Page.Widgets.spinnerDialog.show = true
    Page.Variables.vmLoading.dataSet.dataValue = true

    console.log("is unlimited", Page.Widgets.checkUnlimited.datavalue)
    console.log("form data", Page.Variables.vmRoleUserModel.dataSet)

    if (Page.Widgets.checkUnlimited.datavalue) {
        Page.Variables.vmRoleUserModel.setValue("urFromDate", null)
        Page.Variables.vmRoleUserModel.setValue("urToDate", null)
    }

    var dataSet = Page.Variables.vmRoleUserModel.dataSet
    var employeeList = Page.Variables.vmEmployeeList.dataSet
    console.log(dataSet, employeeList)

    if (dataSet.ids) {
        var data = employeeList.map((item) => {
            var dt = JSON.parse(JSON.stringify(dataSet))

            if (item.urId) {
                dt.urId = item.urId
            }

            if (item.isNew) {
                dt.isNew = item.isNew
            }
            dt.userId = item.lgNik
            dt.userFullName = item.lgName
            dt.urCreatedAt = dt.urCreatedAt || new Date()

            return dt
        })

        // if delete user
        if (Page.Variables.vmDeleteList.dataSet.length > 0) {
            return Page.delMultipleUserRole(Page.Variables.vmDeleteList.dataSet.reverse()).then(() => {
                Page.updateMultiUserRole(data.reverse()).then(() => {
                    App.Actions.appNotification.setMessage("Data Saved")
                    App.Actions.appNotification.setToasterClass("success")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                    Page.Widgets.addRole.close()
                    // Page.Widgets.spinnerDialog.show = false
                    Page.Variables.vmLoading.dataSet.dataValue = false
                    Page.Variables.getRoleUserMapList.update()
                })
            })
        }
        console.log('hehe1')

        // update
        return Page.updateMultiUserRole(data.reverse()).then(() => {
            console.log('hehe2')
            Page.Variables.DBUpdateUserMapStatus.invoke({
                "inputFields": {
                    "role_id": Page.Widgets.selectRole.datavalue,
                    "status": Page.Widgets.toggleStatus.datavalue,
                    "from_date": Page.Widgets.dateFrom.datavalue,
                    "to_date": Page.Widgets.dateTo.datavalue
                }
            }, function() {
                console.log('hehe3')
                // ganti dengan update all saja
                App.Actions.appNotification.setMessage("Data Saved")
                App.Actions.appNotification.setToasterClass("success")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
                Page.Widgets.addRole.close()
                // Page.Widgets.spinnerDialog.show = false
                Page.Variables.vmLoading.dataSet.dataValue = false
                Page.Variables.getRoleUserMapList.update()
            }, function(error) {
                console.log('heheerror')
                console.log('error', error)
            });

            console.log('hehe4')

        })
    } else {
        dataSet.urCreatedAt = new Date()

        // insert
        if (Page.Variables.vmEmployeeList.dataSet.length > 0) {
            var data = employeeList.map(function(item) {
                var dt = JSON.parse(JSON.stringify(dataSet))
                dt.userId = item.lgNik
                dt.userFullName = item.lgName
                return dt
            })
            return Page.insMultipleUserRole(data.reverse()).then(function() {
                Page.Variables.DBUpdateUserMapStatus.invoke({
                    "inputFields": {
                        "role_id": Page.Widgets.selectRole.datavalue,
                        "status": Page.Widgets.toggleStatus.datavalue,
                        "from_date": Page.Widgets.dateFrom.datavalue,
                        "to_date": Page.Widgets.dateTo.datavalue
                    }
                }, function() {

                    App.Actions.appNotification.setMessage("Data Saved")
                    App.Actions.appNotification.setToasterClass("success")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                    Page.Widgets.addRole.close()
                    // Page.Widgets.spinnerDialog.show = false
                    Page.Variables.vmLoading.dataSet.dataValue = false
                    Page.Variables.getRoleUserMapList.update()
                }, function(error) {
                    console.log("error", error)
                })
            })


        } else {
            // Page.Widgets.spinnerDialog.show = false
            App.Actions.appNotification.setMessage("Employee list cannot be empty")
            App.Actions.appNotification.setToasterClass("warning")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
            Page.Variables.vmLoading.dataSet.dataValue = false
        }
    }
};

// recursive function insert
Page.insMultipleUserRole = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }

    var dt = data.pop()
    return Page.Variables.vdbRoleUser.createRecord({
        row: dt
    }).then(function(res) {
        return Page.insMultipleUserRole(data)
    }).catch(function(err) {
        console.log(err)
        return Page.insMultipleUserRole(data)
    })
}

Page.updateMultiUserRole = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }

    var dt = data.pop()
    if (dt.isNew) {
        return Page.Variables.vdbRoleUser.createRecord({
            row: dt
        }).then(function(res) {
            return Page.updateMultiUserRole(data)
        }).catch(function(err) {
            console.log(err)
            return Page.updateMultiUserRole(data)
        })
    } else {
        return Page.updateMultiUserRole(data)
    }

    // if (dt.urId) {
    //     return Page.Variables.vdbRoleUser.updateRecord({
    //         row: dt
    //     }).then(function(res) {
    //         return Page.updateMultiUserRole(data)
    //     }).catch(function(err) {
    //         console.log(err)
    //         return Page.updateMultiUserRole(data)
    //     })
    // }
}

// btn add role
Page.button1Click = function($event, widget) {
    // $(".form-error").removeClass("form-error")
    Page.Widgets.roleUserList.selecteditem = undefined
    Page.Variables.vmRoleUserModel.clearData()
    Page.Variables.vmRoleUserModel.setValue("urAdditionalData", "Internal Employee")
    Page.Variables.vmEmployeeList.clearData()
    Page.Variables.vmDeleteList.clearData()
};

// btn edit role
Page.picture4Click = function($event, widget, item, currentItemWidgets) {
    // $(".form-error").removeClass("form-error")
    Page.Variables.vmEmployeeList.clearData()
    Page.Variables.vmDeleteList.clearData()
    Page.Variables.vmRoleUserModel.clearData()

    var selecteditem = Page.Widgets.roleUserList.selecteditem

    var arrId = item.ids.split(',')
    var arrNik = item.usersNik.split(',')
    var arrName = item.usersName.split(',')
    var arr_created_at = item.createdAt.split(",")
    for (var i = 0; i < arrNik.length; i++) {
        var employee = {
            urId: arrId[i],
            lgNik: arrNik[i],
            lgName: arrName[i],
            urCreatedAt: arr_created_at[i]
        }
        Page.Variables.vmEmployeeList.addItem(employee)
    }

    Page.Variables.vmRoleUserModel.setData(selecteditem)
    Page.Widgets.addRole.open()
};

// delete
Page.picture5Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.vmDeleteItem.setData(item)
    Page.Widgets.dialogDelete.open()
};

// delete employee from list
Page.picture3Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.vmDeleteItem.setData(item)
    Page.Widgets.dialogDelete.open()
};

// confim delete
Page.dialogDeleteOk = function($event, widget) {
    var item = Page.Variables.vmDeleteItem.dataSet

    if (item.ids) {
        Page.Widgets.spinnerTable.show = true
        Page.Widgets.dialogDelete.close()

        var ids = item.ids.split(",")
        var data = []
        ids.map(id => {
            data.push({
                "urId": id
            })
        })
        return Page.delMultipleUserRole(data.reverse()).then(() => {
            App.Actions.appNotification.setMessage("Data Deleted")
            App.Actions.appNotification.setToasterClass("'success")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
            Page.Variables.getRoleUserMapList.update()
        })
    } else if (item.lgNik) {
        Page.Variables.vmDeleteList.addItem(item)
        Page.Variables.vmEmployeeList.removeItem(item)
        Page.Widgets.dialogDelete.close()
    }

};

Page.delMultipleUserRole = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }

    // loop till data empty
    var dt = data.pop()
    return Page.Variables.vdbRoleUser.deleteRecord({
        row: dt
    }).then(function(res) {
        return Page.delMultipleUserRole(data)
    }).catch(function(err) {
        console.log(err)
        return Page.delMultipleUserRole(data)
    })
}

// sort function
Page.toggleTableSort = function(field, picture) {
    var table = Page.Variables.getRoleUserMapList
    var orderBy = ""

    if (table.pagination.sort.length == 0) {
        orderBy = field + " ASC"
        picture.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else {
        if (table.pagination.sort[0].direction == "ASC" && table.pagination.sort[0].property == field) {
            orderBy = field + " DESC"
            picture.picturesource = 'resources/images/logos/icon-up-down-desc.png'
        } else if (table.pagination.sort[0].property != field) {
            orderBy = field + " ASC"
            picture.picturesource = 'resources/images/logos/icon-up-down-asc.png'
        } else {
            orderBy = ""
            picture.picturesource = 'resources/images/logos/icon-up-down.png'
        }
    }

    table.invoke({
        "orderBy": orderBy
    })
}
Page.container28Click = function($event, widget) {
    Page.toggleTableSort("roleTitle", Page.Widgets.picture1)

    Page.Widgets.picture6.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container30Click = function($event, widget) {
    Page.toggleTableSort("usersName", Page.Widgets.picture6)

    Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container29Click = function($event, widget) {
    Page.toggleTableSort("urStatus", Page.Widgets.picture2)

    Page.Widgets.picture6.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down.png'
};

Page.roleUserListBeforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.getRoleUserMapList.pagination.number * Page.Variables.getRoleUserMapList.pagination.size) + (i + 1);
    }
};

// export excel
Page.button2Click = function($event, widget) {
    // intial
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('User Mapping');

    // inserting data
    var xls_content = []

    var xls_header = [
        "Role",
        "Asignee",
        "Status"
    ]
    xls_content.push(xls_header)

    let data = Page.Variables.getRoleUserMapList.dataSet
    data.forEach(item => {
        var dataItem = [
            item.roleTitle,
            item.usersName,
            item.urStatus
        ]
        xls_content.push(dataItem)
    })

    sheet.addRows(xls_content)

    // styling
    let columnWidth = [23, 32, 12];
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

    // exporting
    workbook.xlsx.writeBuffer().then(function(datas) {
        var filename = "User Mapping - ";
        var blob = new Blob([datas], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        });

        let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
        saveAs(blob, filename + currentTime + ".xlsx");
    })
};

Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.getRoleUserMapList.maxResults = newVal
    Page.Variables.getRoleUserMapList.invoke()
};

Page.nikConvert = function(nik) {
    var nikFormat = nik.split("::")
    var nikDisplay = "E:" + nikFormat[1]

    if (nikFormat[0] == "vend") {
        nikDisplay = "V:" + nikFormat[1]
    }

    return nikDisplay;
}
Page.filterListEmployeeKeyup = function($event, widget) {
    if (widget.datavalue) {
        Page.Variables.vmEmployeeListSearch.dataSet = Page.Variables.vmEmployeeList.dataSet.filter(x => {
            return x.lgNik.toString().includes(widget.datavalue.toString())
        });
    } else {
        Page.Variables.vmEmployeeListSearch.dataSet = []
    }
};