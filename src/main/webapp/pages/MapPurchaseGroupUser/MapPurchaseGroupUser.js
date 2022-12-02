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
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('MST-008') !== -1) {
            Page.Variables.vmEmployeeListDelete.clearData()
        } else {
            App.Actions.goToPage_Main.invoke();
        }
    }
};

// add employee to list
Page.search2Select = function($event, widget, selectedValue) {
    var data = JSON.parse(JSON.stringify(selectedValue));

    var employee = {
        upurgrId: 0,
        nik: data.nik,
        lgNik: "emp::" + data.nik,
        lgName: data.employeeName,
        status: "new"
    };

    // if (App.Variables.loggedInUserData.dataSet.user_type.indexOf("Vendor") !== -1) {
    //     employee.lgNik = "vend::" + data.vendorCode;
    //     employee.lgName = data.vendorName;
    // }

    var alreadyExist = Page.Variables.vmEmployeeList.dataSet.filter(x => x.lgNik == employee.lgNik);
    if (alreadyExist.length === 0) {
        Page.Variables.vmEmployeeList.addItem(employee);
    }

    Page.Widgets.searchEmployee.datavalue = null
};

// submit insert or update 
Page.button4Click = function($event, widget) {
    if (Page.Widgets.selectPurchaseGroup.datavalue === undefined) {
        //not select Purchase Group
        App.Actions.appNotification.setMessage("select Purchase Group")
        App.Actions.appNotification.setToasterClass("error")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    } else if (Page.Variables.vmEmployeeList.dataSet.length <= 0) {
        //not select user
        App.Actions.appNotification.setMessage("select employee")
        App.Actions.appNotification.setToasterClass("error")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    } else {
        if (Page.Widgets.mapUserList.selecteditem.upurgrId === undefined) {
            return Promise.resolve().then(() => {
                Page.Widgets.spinnerDialog.show = true
                return Page.addMapUsersAll(Page.Variables.vmEmployeeList.dataSet, Page.Widgets.selectPurchaseGroup.displayValue, Page.Widgets.selectPurchaseGroup.datavalue, "active")

            }).then(() => {
                App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
                App.Actions.appNotification.setToasterClass("success")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
                Page.Widgets.addMapUser.close()
                Page.Widgets.spinnerDialog.show = false
                return Page.Variables.DBPurchaseGroup.invoke()
            }).catch((err) => {
                Page.Widgets.spinnerDialog.show = false
                console.log("err ", err)
            })
        } else {
            return Promise.resolve().then(() => {
                Page.Widgets.spinnerDialog.show = true
                return Page.deleteMapUser(Page.Variables.vmEmployeeListDelete.dataSet)
            }).then(() => {
                if (Page.Variables.varEditAllPopUp.dataSet.dataValue === true) {
                    return Page.addMapUsersAll(Page.Variables.vmEmployeeList.dataSet, Page.Widgets.selectPurchaseGroup.displayValue, Page.Widgets.selectPurchaseGroup.datavalue, Page.Widgets.Page.Widgets.toggle1.datavalue)
                } else {
                    return Page.addMapUsers(Page.Variables.vmEmployeeList.dataSet, Page.Widgets.selectPurchaseGroup.displayValue, Page.Widgets.selectPurchaseGroup.datavalue, Page.Widgets.toggle1.datavalue)
                }
            }).then(() => {
                App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
                App.Actions.appNotification.setToasterClass("success")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
                Page.Widgets.addMapUser.close()
                Page.Widgets.spinnerDialog.show = false
                return Page.Variables.DBPurchaseGroup.invoke()
            }).catch((err) => {
                Page.Widgets.spinnerDialog.show = false
                console.log("err ", err)
            })
        }
    }
};

// recursive function insert
Page.addMapUsers = function(data, purchaseGroup, purchaseGroupId, toggle) {
    if (!data || !data.length) {
        return Promise.resolve()
    }

    var dt = data.pop()

    return Promise.resolve().then(() => {
        return Page.Variables.DBCRUDUserPurchaseGroup.invoke({
            "filterFields": {
                "purchaseGroupId": {
                    "value": purchaseGroupId
                },
                "userId": {
                    "value": dt.lgNik
                }
            }
        })
    }).then((datas) => {
        datas = datas.data.length
        if (datas <= 0) {
            if (dt.status === "new") {
                return Page.Variables.DBCRUDUserPurchaseGroup.createRecord({
                    row: {
                        "purchaseGroup": purchaseGroup,
                        "purchaseGroupId": purchaseGroupId,
                        "userId": dt.lgNik,
                        "userFullName": dt.lgName,
                        "udepCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name,
                        "udepCreatedAt": new Date().toISOString(),
                        "status": Page.Widgets.mapUserList.selecteditem.status === undefined ? "active" : toggle
                    }
                })
            } else {
                return Promise.resolve()
            }
        } else {
            return Promise.resolve()
        }
    }).then(function(res) {
        return Page.addMapUsers(data, purchaseGroup, purchaseGroupId, toggle)
    }).catch(function(err) {
        console.log(err)
        return Page.addMapUsers(data, purchaseGroup, purchaseGroupId, toggle)
    })
}

Page.addMapUsersAll = function(data, purchaseGroup, purchaseGroupId, toggles) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()

    return Promise.resolve().then(() => {
        if (dt.status === "new") {
            return Page.Variables.DBCRUDUserPurchaseGroup.invoke({
                "filterFields": {
                    "purchaseGroupId": {
                        "value": purchaseGroupId
                    },
                    "userId": {
                        "value": dt.lgNik
                    }
                }
            }).then((datas) => {
                datas = datas.data.length
                if (datas <= 0) {
                    return Page.Variables.DBCRUDUserPurchaseGroup.createRecord({
                        row: {
                            "purchaseGroup": purchaseGroup,
                            "purchaseGroupId": purchaseGroupId,
                            "userId": dt.lgNik,
                            "userFullName": dt.lgName,
                            "udepCreatedBy": App.Variables.loggedInUserData.dataSet.user_full_name,
                            "udepCreatedAt": new Date().toISOString(),
                            "status": toggles
                        }
                    })
                } else {
                    return Promise.resolve()
                }
            })
        } else {
            return Page.Variables.DBCRUDUserPurchaseGroup.updateRecord({
                row: {
                    "upurgrId": dt.upurgrId,
                    "purchaseGroup": purchaseGroup,
                    "purchaseGroupId": purchaseGroupId,
                    "userId": dt.lgNik,
                    "userFullName": dt.lgName,
                    "udepCreatedBy": Page.Widgets.mapUserList.selecteditem.udepCreatedBy,
                    "udepCreatedAt": Page.Widgets.mapUserList.selecteditem.udepCreatedAt,
                    "status": toggles
                }
            })
        }
    }).then(function(res) {
        return Page.addMapUsersAll(data, purchaseGroup, purchaseGroupId, toggles)
    }).catch(function(err) {
        console.log(err)
        return Page.addMapUsersAll(data, purchaseGroup, purchaseGroupId, toggles)
    })
}

// recursive function delete
Page.deleteMapUser = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    // if (!Array.isArray(data)) {
    //     dt = JSON.parse(data)
    // } else {
    //     dt = data.pop()
    // }


    return Page.Variables.DBCRUDUserPurchaseGroup.deleteRecord({
        row: {
            "upurgrId": dt.upurgrId
        }
    }).then(function(res) {
        return Page.deleteMapUser(data)
    }).catch(function(err) {
        console.log(err)
        return Page.deleteMapUser(data)
    })
}

// btn add role
Page.button1Click = function($event, widget) {
    Page.Widgets.mapUserList.selecteditem = undefined
    Page.Variables.varEditAllPopUp.dataSet.dataValue = false
    Page.Variables.vmEmployeeList.clearData()
    Page.Widgets.addMapUser.open()
};

// delete employee from list
Page.picture3Click = function($event, widget, item, currentItemWidgets) {
    //item
    var select = Page.Widgets.listEmployee.selecteditem
    var list = Page.Variables.vmEmployeeList.dataSet

    if (list.find(x => x.lgNik === select.lgNik).status === "new") {
        var data = list.filter(x => x.lgNik !== select.lgNik)
        Page.Variables.vmEmployeeList.dataSet = data
    } else {
        var data = list.filter(x => x.lgNik !== select.lgNik)
        Page.Variables.vmEmployeeList.dataSet = data
        Page.Variables.vmEmployeeListDelete.addItem(list.find(x => x.lgNik === select.lgNik))
    }
};

// export excel
Page.button2Click = function($event, widget) {
    // intial
    var workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('Purchase Group to User');
    var xls_content = []
    var xls_header = [
        "Purchase Group",
        App.appLocale["LANG_TOTAL_ASSIGNED"],
        App.appLocale["LANG_STATUS"]
    ]
    xls_content.push(xls_header)

    Promise.resolve().then(() => {
        return Page.Variables.DBPurchaseGroup.invoke()
    }).then((data) => {
        data = JSON.parse(data.body).content
        data.forEach(item => {
            var dataItem = [
                item.purchaseGroup,
                item.nameUsers,
                item.status
            ]
            xls_content.push(dataItem)
        })

        sheet.addRows(xls_content)

        // styling
        let columnWidth = [23, 32, 52, 12];
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
            var filename = "Departement to User ";
            var blob = new Blob([datas], {
                type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            });

            let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
            saveAs(blob, filename + currentTime + ".xlsx");
        })

    }).catch((err) => {
        console.log('err ', err)
    })
};

Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.DBPurchaseGroup.maxResults = newVal
    Page.Variables.DBPurchaseGroup.invoke()
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.DBPurchaseGroup.orderBy == field + " ASC") {
        Page.Variables.DBPurchaseGroup.orderBy = field + " DESC"
    } else if (Page.Variables.DBPurchaseGroup.orderBy == field + " DESC") {
        Page.Variables.DBPurchaseGroup.orderBy = ""
    } else {
        Page.Variables.DBPurchaseGroup.orderBy = field + " ASC"
    }
    Page.Variables.DBPurchaseGroup.invoke()
}

Page.container28Click = function($event, widget) {
    Page.toggleTableSort("purchaseGroup")
};

Page.container29Click = function($event, widget) {
    Page.toggleTableSort("status")
};
Page.container40Click = function($event, widget) {
    Page.toggleTableSort("nameUsers")
};

Page.qGetMappingToUseronSuccess = function(variable, data) {
    // console.log(data)
};

Page.picture4Click = function($event, widget, item, currentItemWidgets) {
    console.log(Page.Widgets.mapUserList.selecteditem)
    Page.Variables.vmEmployeeList.clearData()
    Page.Variables.varEditAllPopUp.dataSet.dataValue = false
    Promise.resolve().then(() => {
        Page.Widgets.mapUserList.selecteditem.listUsers.forEach((user) => {
            var employee = {
                upurgrId: user.upurgrId,
                nik: user.user_id.split('::')[1],
                lgNik: user.user_id,
                lgName: user.userFullName,
                status: "old"
            }
            Page.Variables.vmEmployeeList.addItem(employee)
        })
        return Page.Variables.vmEmployeeList
    }).then(() => {
        Page.Widgets.addMapUser.open()
    })
};
Page.dialogDeleteOk = function($event, widget) {
    Promise.resolve().then(() => {
        return Page.deleteMapUser(Page.Widgets.mapUserList.selecteditem.listUsers)
    }).then(() => {
        Page.Variables.DBPurchaseGroup.invoke()
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SUCCESS)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.Widgets.dialogDelete.close()
    }).catch((err) => {
        console.log("err ", err)
    })
};
Page.selectSearchByChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.searchValue.datavalue = ""
    Page.Variables.DBPurchaseGroup.invoke()
};


Page.toggle1Change = function($event, widget, newVal, oldVal) {
    Page.Variables.varEditAllPopUp.dataSet.dataValue = true
};

Page.selectPurchaseGroupChange = function($event, widget, newVal, oldVal) {
    Page.Variables.varEditAllPopUp.dataSet.dataValue = true
};

Page.DBPurchaseGrouponBeforeDatasetReady = function(variable, data) {
    var arr = []
    data.forEach((dt) => {
        dt.listUsers = JSON.parse("[" + dt.listUsers + "]")
        arr.push(dt)
    })
    return arr
};
