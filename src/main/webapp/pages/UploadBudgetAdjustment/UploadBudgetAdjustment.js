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

    Page.App.getVDBAllRecords(Page.Variables.vdbCompany).then(function(data) {
        console.log("vdbCompany", data)
    })
};

Page.vdbqBudgetUploadonResult = function(variable, data) {
    console.log(Page.Variables.vdbqBudgetUpload)
};
Page.uploadBudgetOpened = function($event, widget) {
    var a = new Yearpicker(Page.Widgets.uploadBudgetYearInp.inputEl.nativeElement, {
        onChange: function(year) {
            Page.Widgets.uploadBudgetYearInp.datavalue = year
        }
    })
};
Page.uploadBudgetBtnClick = function($event, widget) {
    // console.log(Page.Widgets.select2.datavalue)
    if (!Page.Variables.vmUploadBudgetData.dataSet.buYear || !Page.Variables.vmUploadBudgetData.dataSet.companyId || !JSON.parse(Page.Variables.vmUploadBudgetData.dataSet.buFileData).fileUrl) {
        return alert("Please fill form completely before continue.")
    }
    Page.Widgets.spinner1.show = true
    return Page.Variables.vdbBudgetUpload.invoke({
            filterFields: {
                buYear: {
                    value: Page.Variables.vmUploadBudgetData.dataSet.buYear
                },
                companyId: {
                    value: Page.Variables.vmUploadBudgetData.dataSet.companyId
                }
            }
        }).then(function(res) {
            return Page.parsingCSVFile(JSON.parse(Page.Variables.vmUploadBudgetData.dataSet.buFileData).fileUrl)
        }).then(function(data) {
            Page.Variables.vdbAIOEmployee.setFilter("nik", data.map(function(item) {
                var nik = ""
                switch (item[4].toString().length) {
                    case 1:
                        nik = "000" + item[4]
                        break
                    case 2:
                        nik = "00" + item[4]
                        break
                    case 3:
                        nik = "0" + item[4]
                        break
                    default:
                        nik = item[4]
                }
                return nik
            }).filter(function(e, i, arr) {
                return arr.indexOf(e) == i
            }).join(","))

            return Promise.all([
                Promise.resolve(data),
                Page.App.getVDBAllRecords(Page.Variables.vdbAIOEmployee),
                Promise.all(data.map(function(item) {
                    return item[3].toString().trim().toLowerCase()
                }).filter(function(e, i, arr) {
                    return arr.indexOf(e) == i
                }).map(function(gl) {
                    return Page.Variables.vdbCOA.invoke({
                        filterFields: {
                            glNumber: {
                                value: "%" + gl
                            }
                        }
                    }).then(function(res) {
                        if (res.data.length) {
                            return res.data[0]
                        }
                        return {}
                    })
                })),

                Promise.all(data.map(function(item) {
                    return item[2].toString().trim().toLowerCase()
                }).filter(function(e, i, arr) {
                    return arr.indexOf(e) == i
                }).map(function(gl) {
                    return Page.Variables.vdbBudgetHeader.invoke({
                        filterFields: {
                            ioNumber: {
                                value: "%" + gl
                            },
                            bhYear: {
                                value: Page.Variables.vmUploadBudgetData.dataSet.buYear
                            },
                            bhStatus: {
                                value: 'active'
                            },
                        }
                    }).then(function(res) {
                        if (res.data.length) {
                            return res.data[0]
                        }
                        return {}
                    })
                }))
            ])
        })
        .then(function([budgetData, employee, coa, budgetHeader]) {
            // generate budget data
            budgetData = budgetData.map(function(rawItem) {
                var emp = employee.find(function(item) {
                    return parseInt(item.nik.toString().trim()) == parseInt((rawItem[4] || "0").toString().trim())
                })
                var bh = budgetHeader.find(function(item) {
                    return item.ioNumber == (rawItem[2] || "0")
                })
                var item = {
                    "bhId": bh ? bh.bhId : null,
                    "bdDocumentId": rawItem[6],
                    "bdAdjustmentType": rawItem[5],
                    "bdCreatedBy": (emp ? "emp::" + emp.nik : null),
                    "bdCreatedAt": new Date().toISOString(),
                    "bdRemarks": rawItem[8],
                    "coaId": (coa.find(function(el) {
                        return parseInt(el.glNumber).toString().trim().toLowerCase() == rawItem[3].toString().trim().toLowerCase()
                    }) || {}).glNumber,
                    "bdAdjustment": -Math.abs(1) * rawItem[7],
                    "prActual": rawItem[5] == 'PO' ? -Math.abs(1) * rawItem[7] : null,
                    // "poActual": rawItem[5] == 'GR' ? -Math.abs(1) * rawItem[7] : null,
                    "bdStatus": 'active'
                }
                return item
            })


            var column_desc = {
                "bdAdjustment": "Adjustment",
                "bdAdjustmentType": "Adjustment Type",
                "bdDocumentId": "Document Id",
                "bdRemarks": "Adjustment Remarks",
                "bhId": "Budget Id",
                "coaId": "Coa",
            }
            // check data completion
            for (var i in budgetData) {
                var lineItem = budgetData[i]
                for (var attr in lineItem) {
                    console.log(lineItem[attr], attr)
                    if (attr !== 'prActual' && attr !== 'poActual') {
                        if (!lineItem[attr] || !lineItem[attr].toString().length) {
                            return Promise.reject("Line item " + (parseInt(i) + 1) + ", column \"" + column_desc[attr] + "\" invalid")
                        }
                    }
                }
            }
            return budgetData
        }).then(async function(budgetData) {
            var DBBudgetUploadAdjustment = []
            await Page.Variables.DBBudgetUploadAdjustment.createRecord({
                row: {
                    buYear: Page.Variables.vmUploadBudgetData.dataSet.buYear,
                    company: Page.Widgets.select2.datavalue.ctitle,
                    companyId: Page.Widgets.select2.datavalue.ccode,
                    buCreatedBy: Page.App.Variables.loggedInUserData.dataSet.username,
                    buCreatedAt: new Date().toISOString(),
                    buModifiedBy: Page.App.Variables.loggedInUserData.dataSet.username,
                    buModifiedAt: new Date().toISOString(),
                    buStatus: 'active'
                }
            }).then(function(res) {
                DBBudgetUploadAdjustment = res
            })

            // upload bu
            var bdData = JSON.parse(JSON.stringify(Page.Variables.vmUploadBudgetData.dataSet))
            var varInsertBudgetDetail = await Page.insertBudgetDetail(budgetData, DBBudgetUploadAdjustment.buId)
            return varInsertBudgetDetail

        }).then(function(res) {
            Page.Widgets.uploadBudget.close()
            Page.Variables.DBBudgetUploadAdjustment.invoke()
            Page.Widgets.spinner1.show = false
            alert("Budget uploaded successfully.")
        }).catch(function(err) {
            Page.Widgets.spinner1.show = false
            console.log("err::", err)
            alert(err)
        })
};

Page.insertBudgetDetail = async function(data, buId) {
    return new Promise(async function(resolve, reject) {
        for (var i = 0; i < data.length; i++) {
            var DBBudgetAvailable = Page.Variables.DBBudgetAvailable
            console.log(data[i])
            var bdAdjustment = data[i].bdAdjustment
            var check = await DBBudgetAvailable.invoke({
                filterFields: {
                    bhId: {
                        value: data[i].bhId
                    }
                }
            }).then(function(result_check) {
                if (result_check.data.length) {
                    if (result_check.data[0].availableBudget > (-Math.abs(1) * bdAdjustment)) {
                        console.log('masih bisa')
                        return true
                    } else {
                        return false
                    }
                } else {
                    return false
                }
            })

            console.log("check", check)

            data[i].buId = buId
            await Page.Variables.vdbBudgetDetails.createRecord({
                row: data[i]
            })

            if (i === data.length - 1) {
                resolve(true)
            }
        }
    })
}

Page.parsingCSVFile = function(url) {
    return new Promise(function(resolve, reject) {
        Papa.parse(url, {
            download: true,
            skipEmptyLines: true,
            complete: function(results) {
                if (results.errors.length) {
                    return reject(results.errors.map(function(e) {
                        return "Line " + row + " :: " + e.message
                    }).join("\n"))
                }
                return resolve(results.data.slice(1).map(function(item) {
                    return [0].concat(item)
                }))
            }
        });
    })
}
Page.button1Click = function($event, widget) {
    Page.Variables.vmUploadBudgetData.setData({
        "buId": null,
        "company": null,
        "companyId": null,
        "buYear": null,
        "byCreatedBy": null,
        "buCreatedAt": null,
        "buModifiedBy": null,
        "buModifiedAt": null,
        "buStatus": null,
        "buFileData": null
    })
    Page.Widgets.uploadBudget.open()
};
Page.picture9Click = function($event, widget, item, currentItemWidgets) {
    if (item.transCount) {
        return alert("Cannot delete. Budget has been used!")
    }

    Page.Widgets.spinner1.show = true
    return Page.Variables.vdbqDeleteBudgetUpload.invoke({
        inputFields: {
            buId: item.buId
        }
    }).then(function(res) {
        Page.Variables.DBBudgetUploadAdjustment.invoke()
        Page.Widgets.spinner1.show = false
        alert("Budget deleted.")
    }).catch(function(err) {
        console.log(err)
        Page.Widgets.spinner1.show = false
        alert("Failed delete budget.")
    })
};
Page.picture8Click = function($event, widget, item, currentItemWidgets) {
    console.log(item)

    Page.Variables.vmBudgetEdit.setData(JSON.parse(JSON.stringify(item)))
    Page.Variables.vmBudgetEdit.setValue("fileData", JSON.parse(atob(item.buFileData)))
};
Page.button10Click = function($event, widget) {
    if (!Page.Variables.vmBudgetEdit.dataSet.companyId || !Page.Variables.vmBudgetEdit.dataSet.buYear || !Page.Variables.vmBudgetEdit.dataSet.buYear.toString().length) {
        return alert("Please fill form completely before continue.")
    }

    var buData = Page.Variables.vmBudgetEdit.getData()
    buData.buModifiedAt = new Date().toISOString()
    buData.buModifiedBy = Page.App.Variables.loggedInUserData.dataSet.username

    Page.Widgets.spinner1.show = true
    return Page.Variables.vdbBudgetUpload.updateRecord({
        row: buData
    }).then(function() {
        Page.Widgets.uploadBudgetEdit.close()
        Page.Widgets.spinner1.show = false
        Page.Variables.vdbqBudgetUpload.invoke()
        alert("Budget upload saved.")
    }).catch(function(err) {
        console.log(err)
        Page.Widgets.spinner1.show = false
        alert("Failed save budget upload, please try again.")
    })
};
Page.uploadBudgetEditOpened = function($event, widget) {
    var year = Page.Variables.vmBudgetEdit.getValue("buYear")
    Page.Widgets.text5.datavalue = year
    var b = new Yearpicker(Page.Widgets.text5.inputEl.nativeElement, {
        year: parseInt(year)
    })
};
Page.button2Click = function($event, widget) {
    window.open("resources/files/Template_Upload_Budget_Adjustment.xlsx", "__blank")
};


Page.button3Click = function($event, widget) {
    var preserve_page = Page.Variables.vdbqBudgetUpload.pagination.number + 1
    Page.App.getVDBQAllRecords(Page.Variables.vdbqBudgetUpload).then(function(res) {
        Page.Variables.vdbqBudgetUpload.invoke({
            page: preserve_page
        })
        return res
    }).then(function(data) {
        // generate excel data
        var excelData = [
            ["Budget Year", "Company", "File"]
        ].concat(data.map(function(item) {
            return [item.buYear, item.company, JSON.parse(atob(item.buFileData)).fileUrl]
        }))
        console.log(excelData)

        // generate excel
        const workbook = new ExcelJS.Workbook();
        let sheet = workbook.addWorksheet('Budget Upload');
        sheet.addRows(excelData)

        // exporting
        workbook.xlsx.writeBuffer().then(function(datas) {
            var filename = "Upload Budget - ";
            var blob = new Blob([datas], {
                type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            });

            let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
            saveAs(blob, filename + currentTime + ".xlsx");
        })
    })
};
Page.picture3Click = function($event, widget) {
    if (Page.Variables.vdbqBudgetUpload.orderBy == "buYear ASC") {
        Page.Variables.vdbqBudgetUpload.orderBy = "buYear DESC"
    } else if (Page.Variables.vdbqBudgetUpload.orderBy == "buYear DESC") {
        Page.Variables.vdbqBudgetUpload.orderBy = "buCreatedAt ASC"
    } else {
        Page.Variables.vdbqBudgetUpload.orderBy = "buYear ASC"
    }
    Page.Variables.vdbqBudgetUpload.update()
};
Page.picture4Click = function($event, widget) {
    if (Page.Variables.vdbqBudgetUpload.orderBy == "company ASC") {
        Page.Variables.vdbqBudgetUpload.orderBy = "company DESC"
    } else if (Page.Variables.vdbqBudgetUpload.orderBy == "company DESC") {
        Page.Variables.vdbqBudgetUpload.orderBy = "buCreatedAt ASC"
    } else {
        Page.Variables.vdbqBudgetUpload.orderBy = "company ASC"
    }
    Page.Variables.vdbqBudgetUpload.update()
};