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
        udepId: 0,
        nik: data.nik,
        lgNik: "emp::" + data.nik,
        lgName: data.employeeName,
        status: "new"
    };


    var alreadyExist = Page.Variables.vmEmployeeList.dataSet.filter(x => x.lgNik == employee.lgNik);
    if (alreadyExist.length === 0) {
        Page.Variables.vmEmployeeList.addItem(employee);
        Page.Variables.vmEmployeeListAdd.addItem(employee);
    }

    Page.Widgets.searchEmployee.datavalue = null
};

// submit insert or update 
Page.button4Click = function($event, widget) {

    if (Page.Widgets.selectCompany.datavalue === undefined) {
        //not select company
        App.Actions.appNotification.setMessage("select company")
        App.Actions.appNotification.setToasterClass("error")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    } else if (Page.Widgets.selectDepartement.datavalue === undefined) {
        //not select departrment
        App.Actions.appNotification.setMessage("select departrment")
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
        if (Page.Widgets.mapUserList.selecteditem.udepId === undefined) {
            return Promise.resolve().then(() => {
                Page.Widgets.spinnerDialog.show = true

                return Page.addMapUsersAll(Page.Variables.vmEmployeeList.dataSet, Page.Widgets.selectCompany.displayValue, Page.Widgets.selectCompany.datavalue, Page.Widgets.selectDepartement.displayValue, Page.Widgets.selectDepartement.datavalue, "active")

            }).then(() => {
                App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
                App.Actions.appNotification.setToasterClass("success")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
                Page.Widgets.addMapUser.close()
                Page.Widgets.spinnerDialog.show = false
                return Page.Variables.qGetMappingToUser.invoke()
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
                    return Page.addMapUsersAll(Page.Variables.vmEmployeeList.dataSet, Page.Widgets.selectCompany.displayValue, Page.Widgets.selectCompany.datavalue, Page.Widgets.selectDepartement.displayValue, Page.Widgets.selectDepartement.datavalue, Page.Widgets.toggle1.datavalue)
                } else {
                    return Page.addMapUsers(Page.Variables.vmEmployeeList.dataSet, Page.Widgets.selectCompany.displayValue, Page.Widgets.selectCompany.datavalue, Page.Widgets.selectDepartement.displayValue, Page.Widgets.selectDepartement.datavalue, Page.Widgets.toggle1.datavalue)
                }

            }).then(async() => {
                await Page.Variables.DBUpdateUserDepStatus.invoke({
                    "inputFields": {
                        "department_id": Page.Widgets.selectDepartement.datavalue,
                        "company_id": Page.Widgets.selectCompany.datavalue,
                        "status": Page.Widgets.toggle1.datavalue
                    }
                })

                App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SAVED)
                App.Actions.appNotification.setToasterClass("success")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
                Page.Widgets.addMapUser.close()
                Page.Widgets.spinnerDialog.show = false
                return Page.Variables.qGetMappingToUser.invoke()
            }).catch((err) => {
                Page.Widgets.spinnerDialog.show = false
                console.log("err ", err)
            })
        }
    }
};

// recursive function insert
Page.addMapUsers = function(data, company, companyId, department, departmentId, toggle) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()

    if (dt.status === "new") {
        return Promise.resolve().then(() => {
            return Page.Variables.dbTblMUserDep.invoke({
                "filterFields": {
                    "companyId": {
                        "value": companyId
                    },
                    "departmentId": {
                        "value": departmentId
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
                    return Page.Variables.dbTblMUserDep.createRecord({
                        row: {
                            "company": company,
                            "companyId": companyId,
                            "departrment": department,
                            "departmentId": departmentId,
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
            return Page.addMapUsers(data, company, companyId, department, departmentId, toggle)
        }).catch(function(err) {
            console.log(err)
            return Page.addMapUsers(data, company, companyId, department, departmentId, toggle)
        })
    } else {
        return Page.addMapUsers(data, company, companyId, department, departmentId, toggle)
    }
}

Page.addMapUsersAll = function(data, company, companyId, department, departmentId, toggles) {
    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    return Promise.resolve().then(() => {
        if (dt.status === "new") {
            return Page.Variables.dbTblMUserDep.invoke({
                "filterFields": {
                    "companyId": {
                        "value": companyId
                    },
                    "departmentId": {
                        "value": departmentId
                    },
                    "userId": {
                        "value": dt.lgNik
                    }
                }
            }).then((datas) => {
                datas = datas.data.length
                if (datas <= 0) {
                    return Page.Variables.dbTblMUserDep.createRecord({
                        row: {
                            "company": company,
                            "companyId": companyId,
                            "departrment": department,
                            "departmentId": departmentId,
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
            return true
            // return Page.Variables.dbTblMUserDep.updateRecord({
            //     row: {
            //         "udepId": dt.udepId,
            //         "company": company,
            //         "companyId": companyId,
            //         "departrment": department,
            //         "departmentId": departmentId,
            //         "userId": dt.lgNik,
            //         "userFullName": dt.lgName,
            //         "udepCreatedBy": Page.Widgets.mapUserList.selecteditem.udepCreatedBy,
            //         "udepCreatedAt": Page.Widgets.mapUserList.selecteditem.udepCreatedAt,
            //         "status": toggles
            //     }
            // })
        }
    }).then(function(res) {
        return Page.addMapUsersAll(data, company, companyId, department, departmentId, toggles)
    }).catch(function(err) {
        console.log(err)
        return Page.addMapUsersAll(data, company, companyId, department, departmentId, toggles)
    })
}

// recursive function delete
Page.deleteMapUser = function(data) {
    if (!data || !data.length) {
        return Promise.resolve()
    }

    var dt = data.pop()
    return Page.Variables.dbTblMUserDep.deleteRecord({
        row: {
            "udepId": dt.udepId
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
    var listFilter = Page.Variables.vmEmployeeListSearch.dataSet

    if (list.find(x => x.lgNik === select.lgNik).status === "new") {
        var data = list.filter(x => x.lgNik !== select.lgNik)
        Page.Variables.vmEmployeeList.dataSet = data
    } else {
        var data = list.filter(x => x.lgNik !== select.lgNik)
        Page.Variables.vmEmployeeList.dataSet = data
        Page.Variables.vmEmployeeListDelete.addItem(list.find(x => x.lgNik === select.lgNik))
    }

    if (listFilter.length > 0) {
        Page.Variables.vmEmployeeListSearch.dataSet = Page.Variables.vmEmployeeList.dataSet.filter(x => {
            return x.nik.toString().includes(Page.Widgets.filterListEmployee.datavalue.toString())
        });
    }
};

// export excel
Page.button2Click = function($event, widget) {
    // intial
    var workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('Departement to User');
    var xls_content = []
    var xls_header = [
        App.appLocale.LANG_COMPANY,
        App.appLocale["LANG_DEPARTMENT"],
        App.appLocale["LANG_TOTAL_ASSIGNED"],
        App.appLocale["LANG_STATUS"]
    ]
    xls_content.push(xls_header)

    Promise.resolve().then(() => {
        return Page.Variables.qGetMappingToUser.invoke()
    }).then((data) => {
        data = JSON.parse(data.body).content
        data.forEach(item => {
            var dataItem = [
                item.company,
                item.departrment,
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
    Page.Variables.qGetMappingToUser.maxResults = newVal
    Page.Variables.qGetMappingToUser.invoke()
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.qGetMappingToUser.orderBy == field + " ASC") {
        Page.Variables.qGetMappingToUser.orderBy = field + " DESC"
    } else if (Page.Variables.qGetMappingToUser.orderBy == field + " DESC") {
        Page.Variables.qGetMappingToUser.orderBy = ""
    } else {
        Page.Variables.qGetMappingToUser.orderBy = field + " ASC"
    }
    Page.Variables.qGetMappingToUser.invoke()
}

Page.container28Click = function($event, widget) {
    Page.toggleTableSort("company")
};
Page.container30Click = function($event, widget) {
    Page.toggleTableSort("departrment")
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
    Page.Variables.vmEmployeeList.clearData()
    Page.Variables.varEditAllPopUp.dataSet.dataValue = false
    Promise.resolve().then(() => {
        Page.Widgets.mapUserList.selecteditem.listUsers.forEach((user) => {
            var employee = {
                udepId: user.udepId,
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
        Page.Variables.qGetMappingToUser.invoke()
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
    Page.Variables.qGetMappingToUser.invoke()
};

Page.qGetMappingToUseronBeforeDatasetReady = function(variable, data) {
    var arr = []
    data.forEach((dt) => {
        dt.listUsers = JSON.parse("[" + dt.listUsers + "]")
        arr.push(dt)
    })
    return arr
};
Page.toggle1Change = function($event, widget, newVal, oldVal) {
    Page.Variables.varEditAllPopUp.dataSet.dataValue = true
};
Page.selectCompanyChange = function($event, widget, newVal, oldVal) {
    Page.Variables.varEditAllPopUp.dataSet.dataValue = true
};
Page.selectDepartementChange = function($event, widget, newVal, oldVal) {
    Page.Variables.varEditAllPopUp.dataSet.dataValue = true
};

Page.filterListEmployeeKeyup = function($event, widget) {
    if (widget.datavalue) {
        Page.Variables.vmEmployeeListSearch.dataSet = Page.Variables.vmEmployeeList.dataSet.filter(x => {
            return x.nik.toString().includes(widget.datavalue.toString())
        });
    } else {
        Page.Variables.vmEmployeeListSearch.dataSet = []
    }
};