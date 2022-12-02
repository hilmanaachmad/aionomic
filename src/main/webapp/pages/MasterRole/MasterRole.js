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

    //  handle authorization
    if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('MST-001') == -1) {
            App.Actions.goToPage_Main.invoke()
        }
    }

    Page.Variables.vdbMAuth.invoke().then(function(res) {
        Page.Variables.vmAuthorization.setData(res.data)
    })
};

// add access to role
Page.search2Select = function($event, widget, selectedValue) {
    // check data isnt exist
    var alreadyExist = Page.Variables.vmRoleAuthList.dataSet.filter(x => x.authId == selectedValue.authId)
    if (alreadyExist.length == 0) {
        selectedValue.isNew = true
        Page.Variables.vmRoleAuthList.addItem(selectedValue)
    }

    Page.Widgets.search2.datavalue = null
};

// edit role
Page.picture4Click = function($event, widget, item, currentItemWidgets) {
    $(".form-error").removeClass("form-error")
    Page.Variables.vmErrorMessage.clearData()
    Page.Variables.vmRoleAuthList.clearData()
    console.log(item)

    if (item.authCodes && item.authDescs) {
        var authIds = item.authIds.split(",")
        var authCodes = item.authCodes.split(",")
        var authDescs = item.authDescs.split(";")

        authCodes.map((code, i) => {
            var access = {
                "authCode": code,
                "authDesc": authDescs[i],
                "authId": authIds[i]
            }
            Page.Variables.vmRoleAuthList.addItem(access)
        })
    }

    var data = JSON.parse(JSON.stringify(item))
    Page.Variables.vmRoleModel.setData(data)
    Page.Widgets.addRole.open()
};

// btn add role
Page.button1Click = function($event, widget) {
    $(".form-error").removeClass("form-error")
    Page.Variables.vmErrorMessage.clearData()
    Page.Variables.vmRoleModel.clearData()
    Page.Variables.vmRoleAuthList.clearData()
};

// delete role
Page.picture5Click = function($event, widget, item, currentItemWidgets) {
    Page.Widgets.dialogDelete.open()
    Page.Variables.vmDeleteItem.setData(item)
};

// delete access from role
Page.picture3Click = function($event, widget, item, currentItemWidgets) {
    Page.Widgets.dialogDelete.open()
    Page.Variables.vmDeleteItem.setData(item)
};

// confim delete
Page.dialogDeleteOk = function($event, widget) {
    var item = Page.Variables.vmDeleteItem.dataSet
    Page.Widgets.dialogDelete.close()
    Page.Widgets.spinner2.show = true

    if (item.roleId) {
        var access = []
        if (item.authIds) {
            var authIds = item.authIds.split(",")
            authIds.map(auth => {
                var tmp = {
                    "authId": auth
                }
                access.push(tmp)
            })
        }

        return Page.deleteAccess(access.reverse(), item.roleId).then(() => {
            Page.Variables.vdbMRole.deleteRecord({
                row: item
            }).then((data) => {
                Page.Widgets.spinner2.show = false
                App.Actions.appNotification.setMessage("Data Deleted")
                App.Actions.appNotification.setToasterClass("success")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
                Page.Variables.queryGetMasterRole.update()

                Page.Widgets.dialogDelete.close()
            })
        })

    } else if (item.authCode) {
        if (!item.isNew) {
            Page.Variables.vmDeleteList.addItem(item)
        }
        Page.Variables.vmRoleAuthList.removeItem(item)
        Page.Widgets.spinner2.show = false
    }

    Page.Widgets.dialogDelete.close()
};


// submit insert or update Role
Page.button4Click = function($event, widget) {
    $(".form-error").removeClass("form-error")
    Page.Variables.vmErrorMessage.clearData()
    var role = Page.Variables.vmRoleModel.dataSet

    // validation
    var isValid = true
    if (Page.Variables.vmRoleAuthList.dataSet.length == 0) {
        isValid = false
        Page.Variables.vmErrorMessage.dataSet.accessErrorMessage = "this field is required"
    }
    if (role.roleTitle == "" || !role.roleTitle) {
        isValid = false
        Page.Variables.vmErrorMessage.dataSet.roleErrorMessage = "this field is required"
    }
    if (!isValid) {
        return
    }

    // Page.Widgets.spinnerRole.show = true
    Page.Variables.vmLoading.dataSet.dataValue = true

    if (role.roleId) {
        // update
        role.roleStatus = Page.Widgets.toggleStatus.datavalue

        Page.Variables.vdbMRole.listRecords({
            filterFields: {
                "roleTitle": {
                    "value": role.roleTitle
                },
                "roleStatus": {
                    "value": "active"
                }
            }
        }).then(data => {
            data.data.map(item => {
                if (item.roleId != role.roleId) {
                    Page.Variables.vmLoading.dataSet.dataValue = false
                    Page.Variables.vmErrorMessage.dataSet.roleErrorMessage = "this role already exist"
                    isValid = false
                }
            })

            if (isValid) {
                Page.Variables.vdbMRole.updateRecord({
                    row: role
                }).then((data) => {
                    // delete all access
                    var deleteList = Page.Variables.vmDeleteList.dataSet

                    if (deleteList.length > 0) {
                        var dt = JSON.parse(JSON.stringify(deleteList))
                        return Page.deleteAccess(dt.reverse(), role.roleId)
                    } else {
                        return Promise.resolve()
                    }

                }).then((data) => {
                    // add new access
                    var roleAuth = Page.Variables.vmRoleAuthList.dataSet
                    var access = JSON.parse(JSON.stringify(roleAuth))

                    return Page.insertAccess(access.reverse(), role.roleId)
                }).then((data) => {
                    App.Actions.appNotification.setMessage("Data Saved")
                    App.Actions.appNotification.setToasterClass("success")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                    Page.Variables.queryGetMasterRole.update()

                    // Page.Widgets.spinnerRole.show = false
                    Page.Variables.vmLoading.dataSet.dataValue = false
                    Page.Widgets.addRole.close()
                    return Promise.resolve()
                }).catch((error) => {
                    console.log(error)
                })
            }
        })
    } else {
        Page.Variables.vdbMRole.listRecords({
            filterFields: {
                "roleTitle": {
                    "value": role.roleTitle
                },
                "roleStatus": {
                    "value": "active"
                }
            }
        }).then((data) => {
            if (data.data.length > 0) {
                // state error
                // $(".aio-p2p-form-container").addClass("form-error")
                Page.Variables.vmErrorMessage.dataSet.roleErrorMessage = "this role already exist"
                Page.Variables.vmLoading.dataSet.dataValue = false
                // Page.Variables.vdbMRole.update()
            } else {
                // insert
                role.roleStatus = 'active'
                role.roleCreatedAt = new Date()
                Page.Variables.vdbMRole.createRecord({
                    row: role
                }).then((data) => {
                    var roleAuth = Page.Variables.vmRoleAuthList.dataSet
                    var dt = JSON.parse(JSON.stringify(roleAuth))
                    return Page.insertAccess(dt.reverse(), data.roleId).then(() => {
                        App.Actions.appNotification.setMessage("Data Saved")
                        App.Actions.appNotification.setToasterClass("success")
                        App.Actions.appNotification.getToasterDuration(5000)
                        App.Actions.appNotification.invoke()
                        Page.Variables.queryGetMasterRole.update()

                        // Page.Widgets.spinnerRole.show = false
                        Page.Variables.vmLoading.dataSet.dataValue = false
                        Page.Widgets.addRole.close()
                    })
                }).catch((error) => {
                    console.log(error)
                })
            }
        })
    }
};

// loop promise access
Page.insertAccess = function(data, roleId) {
    if (!data || !data.length) {
        return Promise.resolve()
    }

    // loop till data empty
    var dt = data.pop()
    dt.roleId = roleId
    if (dt.isNew) {
        return Page.Variables.vdbRoleAuth.createRecord({
            row: dt
        }).then(function(res) {
            return Page.insertAccess(data, roleId)
        }).catch(function(err) {
            console.log(err)
            return Page.insertAccess(data, roleId)
        })
    } else {
        return Page.insertAccess(data, roleId)
    }
}

Page.deleteAccess = function(data, roleId) {
    if (!data || !data.length) {
        return Promise.resolve()
    }

    // loop till data empty
    var dt = data.pop()
    dt.roleId = roleId
    return Page.Variables.vdbRoleAuth.deleteRecord({
        row: dt
    }).then(function(res) {
        return Page.deleteAccess(data, roleId)
    }).catch(function(err) {
        console.log(err)
        return Page.deleteAccess(data, roleId)
    })
}

// Page.vdbRoleAuthonSuccess = function(variable, data) {
//     // Page.Variables.vmRoleAuthList.clearData()

//     if (Page.Variables.vmRoleModel.dataSet.roleId) {
//         for (var i = 0; i < data.length; i++) {
//             Page.Variables.vmRoleAuthList.addItem(data[i].tblMauthorization)
//         }
//     }
// };

// sort function
Page.toggleTableSort = function(field, picture) {
    var table = Page.Variables.queryGetMasterRole
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
    Page.toggleTableSort('roleTitle', Page.Widgets.picture1)

    Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container29Click = function($event, widget) {
    Page.toggleTableSort('roleStatus', Page.Widgets.picture2)
    Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down.png'
};

// numbering data
Page.TblMRoleList1Beforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.queryGetMasterRole.pagination.number * Page.Variables.queryGetMasterRole.pagination.size) + (i + 1);
    }
};

// export data to excel
Page.button2Click = function($event, widget) {
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('Master Role');

    // inserting data
    var xls_content = []

    var xls_header = [
        "Role",
        "Status",
        "Access"
    ]
    xls_content.push(xls_header)

    let data = Page.Variables.queryGetMasterRole.dataSet
    data.forEach(item => {
        var auths = []

        if (item.authCodes && item.authDescs) {
            var authCodes = item.authCodes.split(",")
            var authDescs = item.authDescs.split(";")

            authCodes.map((auth, i) => {
                var tmp = auth + "::" + authDescs[i]
                auths.push(tmp)
            })
        }

        var dataItem = [
            item.roleTitle,
            item.roleStatus,
            auths
        ]

        xls_content.push(dataItem)
    })

    sheet.addRows(xls_content)

    let alignCenter = {
        vertical: 'middle',
        'horizontal': 'center'
    };
    sheet.getRow(1).alignment = alignCenter;

    let columnWidth = [22, 9, 22];
    sheet.columns.forEach((col, index) => {
        if (columnWidth[index]) {
            col.width = columnWidth[index];
        }
    });

    // exporting
    workbook.xlsx.writeBuffer().then(function(datas) {
        var filename = "Master Role - ";
        var blob = new Blob([datas], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        });

        let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
        saveAs(blob, filename + currentTime + ".xlsx");
    })
};

// change row data
Page.select9_2Change = function($event, widget, newVal, oldVal) {
    Page.Variables.queryGetMasterRole.maxResults = newVal
    Page.Variables.queryGetMasterRole.update()
};

Page.queryGetMasterRoleonSuccess = function(variable, data) {
    console.log("master role", data)
};
