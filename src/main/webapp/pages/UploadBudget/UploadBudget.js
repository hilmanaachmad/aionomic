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
    console.log(Page.Widgets.select2.datavalue)
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
        console.log(res)
        if (res.data.length) {
            return Promise.reject("Budget for company " + Page.Variables.vmUploadBudgetData.dataSet.company + " year " + Page.Variables.vmUploadBudgetData.dataSet.buYear + " already exist! Please choose different company and/or different year.")
        }
        return Page.parsingCSVFile(JSON.parse(Page.Variables.vmUploadBudgetData.dataSet.buFileData).fileUrl)
    }).then(function(data) {

        Page.Variables.vdbAIOEmployee.setFilter("nik", data.map(function(item) {
            var nik = ""
            switch (item[11].toString().length) {
                case 1:
                    nik = "000" + item[11]
                    break
                case 2:
                    nik = "00" + item[11]
                    break
                case 3:
                    nik = "0" + item[11]
                    break
                default:
                    nik = item[11]
            }
            return nik
        }).filter(function(e, i, arr) {
            return arr.indexOf(e) == i
        }).join(","))
        return Promise.all([
            Promise.resolve(data),
            Page.App.getVDBAllRecords(Page.Variables.vdbAIOEmployee),
            Promise.all(data.map(function(item) {
                return item[9].toString().trim().toLowerCase()
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
                    console.log(res)
                    if (res.data.length) {
                        return res.data[0]
                    }
                    return {}
                })
            })),
            Page.App.getVDBAllRecords(Page.Variables.vdbCompany),
            Page.App.getVDBAllRecords(Page.Variables.vdbDepartment),
            Page.App.getVDBAllRecords(Page.Variables.vdbBrand),
            Page.App.getVDBAllRecords(Page.Variables.vdbAccountType)
        ])
    }).then(function([budgetData, employee, coa, company, department, brand, accountType]) {

        // generate budget data
        budgetData = budgetData.map(function(rawItem) {
            var emp = employee.find(function(item) {
                return parseInt(item.nik.toString().trim()) == parseInt((rawItem[11] || "0").toString().trim())
            })
            var item = {
                "cid": (company.find(function(el) {
                    return el.ccode.trim().toLowerCase() == rawItem[1].toString().trim().toLowerCase()
                }) || {}).cid,
                "accId": (accountType.find(function(el) {
                    return el.accTitle.trim().toLowerCase() == rawItem[2].trim().toLowerCase()
                }) || {}).accId,
                "brId": (brand.find(function(el) {
                    return el.brTitle.trim().toLowerCase() == rawItem[5].trim().toLowerCase()
                }) || {}).brId,
                "bhYear": Page.Variables.vmUploadBudgetData.dataSet.buYear,
                "department": (department.find(function(el) {
                    return (el.departmentCode || "").trim().toLowerCase() == rawItem[4].trim().toLowerCase()
                }) || {}).departmentTitle,
                "departmentId": (department.find(function(el) {
                    return (el.departmentCode || "").trim().toLowerCase() == rawItem[4].trim().toLowerCase()
                }) || {}).departmentId,
                "bhEstDate": rawItem[3].trim(),
                "bhCurrency": rawItem[6].trim(),
                "ioNumber": rawItem[7].trim(),
                "ioName": rawItem[8].toString().trim().slice(0, 100),
                "coa": (coa.find(function(el) {
                    return parseInt(el.glNumber).toString().trim().toLowerCase() == rawItem[9].toString().trim().toLowerCase()
                }) || {}).glNumber,
                "bhAmount": rawItem[10].toString().split(",").join("").trim(),
                "userId": (emp ? "emp::" + emp.nik : null),
                "bhCreatedAt": new Date().toISOString(),
                "bhCreatedBy": Page.App.Variables.loggedInUserData.dataSet.username,
            }
            return item
        })

        var column_desc = {
            "cid": "Company",
            "accId": "Account Type",
            "brId": "Brand",
            "bhYear": "Budget Year",
            "department": "Dept",
            "departmentId": "Dept",
            "bhEstDate": "Estimate Date",
            "bhCurrency": "Currency",
            "ioNumber": "IO Number",
            "ioName": "IO Name",
            "coa": "COA",
            "bhAmount": "Amount budget per IO",
            "userId": "NIK"
        }
        // check data completion
        for (var i in budgetData) {
            var lineItem = budgetData[i]
            for (var attr in lineItem) {
                console.log(lineItem[attr])
                if (!lineItem[attr] || !lineItem[attr].toString().length) {

                    return Promise.reject("Line item " + (parseInt(i) + 1) + ", column \"" + column_desc[attr] + "\" invalid")
                }
            }
        }
        return budgetData
    }).then(function(budgetData) {
        // upload bu
        var buData = JSON.parse(JSON.stringify(Page.Variables.vmUploadBudgetData.dataSet))
        buData.buCreatedAt = new Date().toISOString()
        buData.buCreatedBy = Page.App.Variables.loggedInUserData.dataSet.username
        buData.buModifiedAt = new Date().toISOString()
        buData.buModifiedBy = Page.App.Variables.loggedInUserData.dataSet.username
        buData.buFileData = btoa(buData.buFileData)
        buData.buStatus = 'active'

        return Page.Variables.vdbBudgetUpload.createRecord({
            row: buData
        }).then(function(res) {
            return budgetData.map(function(item) {
                item.buId = res.buId
                return item
            })
        }).catch(function(err) {
            console.log(err)
            return Promise.reject("Failed upload budget.")
        })
    }).then(function(budgetData) {
        return Promise.all(budgetData.map(function(item) {
            return Page.Variables.vdbBudgetHeader.createRecord({
                row: item
            })
        })).catch(function(err) {
            console.log(err)
            return Promise.reject("Failed upload some of budget data.")
        })
    }).then(function(res) {
        var budgetDetails = res.map(function(item) {
            return {
                bhId: item.bhId,
                bdOriginal: item.bhAmount,
                bdDocumentId: "1000-" + item.bhId,
                bdAdjustmentType: "BEGINNING",
                bdRemarks: "Submission Budget " + item.bhYear,
                bdAfterAdjustment: item.bhAmount,
                bdCreatedAt: new Date(),
                bdCreatedBy: Page.App.Variables.loggedInUserData.dataSet.username,
                coaId: item.coa
            }
        })
        return Promise.all(budgetDetails.map(function(item) {
            return Page.Variables.vdbBudgetDetails.createRecord({
                row: item
            })
        }))
    }).then(function(res) {
        console.log(res)
        Page.Widgets.uploadBudget.close()
        Page.Variables.vdbqBudgetUpload.invoke()
        Page.Widgets.spinner1.show = false
        alert("Budget uploaded successfully.")
    }).catch(function(err) {
        Page.Widgets.spinner1.show = false
        console.log("err::", err)
        alert(err)
    })

};

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
    console.log("lalalal")
    if (item.transCount) {
        return alert("Cannot delete. Budget has been used!")
    }

    Page.Widgets.spinner1.show = true
    return Page.Variables.vdbqDeleteBudgetUpload.invoke({
        inputFields: {
            buId: item.buId
        }
    }).then(function(res) {
        Page.Variables.vdbqBudgetUpload.invoke()
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
    window.open("resources/files/Template_Upload_Budget.xlsx", "__blank")
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