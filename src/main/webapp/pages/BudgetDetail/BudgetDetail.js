/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
var FilterAdmin = "";
var selectedItem;
var Obj = [];
var ObjFilter = [];
var ObjSpend = [];
let valBudgetYear, valCompany, valBrand, valDep, valAcc, valMonth;

Page.onReady = async function() {


    if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('BUD-006') !== -1) {
            Page.Variables.isAdminBudget.dataSet.dataValue = "TRUE"
            let depdata = await Page.Variables.vdbGetDepartment.invoke();
            Page.Variables.vmDepartment.dataSet = Page.Variables.vdbGetDepartment.dataSet;
            Page.Widgets.slct_account_type.dataset = Page.Variables.vdbAccountTypeAdmin.dataSet;
            Page.Widgets.slct_company.dataset = Page.Variables.vQueryCompany.dataSet;
            Page.Widgets.slct_brand.dataset = Page.Variables.vdbBrand.dataSet;
        } else {
            Page.Variables.isAdminBudget.dataSet.dataValue = "FALSE"
            let getval = await Page.Variables.vQueryDepMapUser.invoke({
                emp: App.Variables.loggedInUser.dataSet.name
            });
            // Page.Widgets.slct_department.dataset = Page.Variables.vQueryDepMapUser.dataSet;
            Page.Variables.vmDepartment.dataSet = Page.Variables.vQueryDepMapUser.dataSet;
        }
    }

    //ambil data dari storage
    let condition = window.localStorage.getItem("budget-session")
    if (condition != null) {
        Page.Widgets.slct_company.datavalue = Number(window.localStorage.getItem("session-company"))
        Page.Widgets.budgetYear.datavalue = Number(window.localStorage.getItem("session-year"))
        Page.Widgets.slct_brand.datavalue = Number(window.localStorage.getItem("session-brand"))
        Page.Widgets.slct_department.datavalue = Number(window.localStorage.getItem("session-dep"))
        Page.Widgets.slct_account_type.datavalue = Number(window.localStorage.getItem("session-acc"))

        let dataval = window.localStorage.getItem("budget-value")
        Obj = JSON.parse(dataval);
        const seen = new Set();
        const filteredArr = Obj.filter(el => {
            const duplicate = seen.has(el.ioNumber);
            seen.add(el.ioNumber);
            return !duplicate;
        });

        Page.Widgets.select_io_number.dataset = filteredArr;
        Page.Widgets.select_io_name.dataset = filteredArr;
        Page.Widgets.slct_io_number.dataset = filteredArr;
        Page.Widgets.slct_io_name.dataset = filteredArr;
        Page.ApplyDynamicFilter();
        window.localStorage.removeItem("budget-session")
    } else {
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('BUD-006') !== -1) {
            // FilterAdmin = "TRUE"
            Page.Variables.isAdminBudget.dataSet.dataValue = "TRUE"
        } else {
            Page.Variables.isAdminBudget.dataSet.dataValue = "FALSE"
        }
        var d = new Date();
        var n = d.getFullYear();

        $('.yearpicker').val(new Date().getFullYear())
        Page.Widgets.budgetYear.datavalue = new Date().getFullYear()

        let test = await Page.Variables.ProcedureBudgetDetail.invoke({
            inputFields: {
                emp: App.Variables.loggedInUser.dataSet.name,
                year: String(n),
                dep: "NULL",
                acc_type: "NULL",
                comp: "NULL",
                brand: "NULL",
                admin: Page.Variables.isAdminBudget.dataSet.dataValue,
                month: Page.Widgets.selectMonth.datavalue
            }
        }, function(data) {}, function(error) {
            // Error Callback
            console.log("error", error)
        });


        var result = Page.Variables.ProcedureBudgetDetail.getData();
        Obj = Page.amountCalculate(result.content, 'all');
        ObjFilter = Obj;
        window.localStorage.setItem("budget-value", JSON.stringify(Obj))
        const seen = new Set();
        const filteredArr = Obj.filter(el => {
            const duplicate = seen.has(el.ioNumber);
            seen.add(el.ioNumber);
            return !duplicate;
        });

        Page.Widgets.select_io_number.dataset = filteredArr;
        Page.Widgets.select_io_name.dataset = filteredArr;
        Page.Widgets.slct_io_number.dataset = filteredArr;
        Page.Widgets.slct_io_name.dataset = filteredArr;
        Page.ApplyDynamicFilter();
    }

    $('.yearpicker').yearpicker()
    $('.yearpicker').change(function() {
        var year_value = $(this).val()
        Page.Widgets.budgetYear.datavalue = year_value
    })


}

Page.getCurrentMonth = function() {
    let month = new Date();
    return (month.getMonth() + 1);
}


Page.btnApplyClick = async function($event, widget) {
    valBudgetYear = Page.Widgets.budgetYear.datavalue;
    valCompany = Page.Widgets.slct_company.datavalue;
    valBrand = Page.Widgets.slct_brand.datavalue;
    valDep = Page.Widgets.slct_department.datavalue;
    valAcc = Page.Widgets.slct_account_type.datavalue;
    valMonth = Page.Widgets.selectMonth.datavalue;

    console.log('oioi', valMonth);
    console.log('oioi2', valBudgetYear);

    window.localStorage.setItem('session-year', valBudgetYear);
    window.localStorage.setItem('session-company', valCompany);
    window.localStorage.setItem('session-brand', valBrand);
    window.localStorage.setItem('session-dep', valDep);
    window.localStorage.setItem('session-acc', valAcc);

    if ((valBudgetYear == undefined || valBudgetYear == '') &&
        // (valMonth == undefined || valMonth == '') &&
        (valCompany == undefined || valCompany == '') &&
        (valBrand == undefined || valBrand == '') &&
        (valDep == undefined || valDep == '') &&
        (valAcc == undefined || valAcc == '')) {
        App.Actions.appNotification.setMessage("Please select at least 1 filter")
        App.Actions.appNotification.setToasterClass("warning")
        App.Actions.appNotification.setToasterDuration(4000)
        App.Actions.appNotification.invoke()
    } else {

        if (valBudgetYear == undefined || valBudgetYear == '') {
            valBudgetYear = 'NULL';
        }
        if (valMonth == undefined || valMonth == '') {
            valMonth = 'NULL';
        }
        if (valCompany == undefined || valCompany == '') {
            valCompany = 'NULL';
        }
        if (valBrand == undefined || valBrand == '') {
            valBrand = 'NULL';
        }
        if (valDep == undefined || valDep == '') {
            valDep = 'NULL';
        }
        if (valAcc == undefined || valAcc == '') {
            valAcc = 'NULL';
        }

        let test = await Page.Variables.ProcedureBudgetDetail.invoke({
            inputFields: {
                emp: App.Variables.loggedInUser.dataSet.name,
                year: valBudgetYear,
                dep: valDep,
                acc_type: valAcc,
                comp: valCompany,
                brand: valBrand,
                admin: Page.Variables.isAdminBudget.dataSet.dataValue,
                month: valMonth
            }
        }, function(data) {}, function(error) {
            // Error Callback
            console.log("error", error)
        });


        var result = Page.Variables.ProcedureBudgetDetail.getData();
        Obj = Page.amountCalculate(result.content, 'all');
        ObjFilter = Obj;
        window.localStorage.setItem("budget-value", JSON.stringify(Obj))
        const seen = new Set();
        const filteredArr = Obj.filter(el => {
            const duplicate = seen.has(el.ioNumber);
            seen.add(el.ioNumber);
            return !duplicate;
        });

        Page.Widgets.select_io_number.dataset = filteredArr;
        Page.Widgets.select_io_name.dataset = filteredArr;
        Page.Widgets.slct_io_number.dataset = filteredArr;
        Page.Widgets.slct_io_name.dataset = filteredArr;
        Page.ApplyDynamicFilter();

    }



};



Page.amountCalculate = function(newArray, key) {
    let i = 0;
    // for (i = 0; i < newArray.length; i++) {
    //     newArray[i].number = i + 1;
    //     // newArray[i].budgetAdditional = Number(Math.abs(newArray[i].budgetAdditional))

    //     if (newArray[i].budgetAdditional != null) {
    //         newArray[i].budgetAdditional = Number(newArray[i].budgetAdditional.toFixed(0))
    //     } else {
    //         newArray[i].budgetAdditional = Number(newArray[i].budgetAdditional)
    //     }
    //     newArray[i].budgetOriginal = Number(newArray[i].budgetOriginal)
    //     if (newArray[i].budgetAdjusment == null) {
    //         newArray[i].budgetAdjusment = Number(newArray[i].budgetAdjusment)
    //     }
    //     if (newArray[i].budgetGr == null) {
    //         newArray[i].budgetGr = Number(newArray[i].budgetGr)
    //     }
    //     if (newArray[i].budgetPo == null) {
    //         newArray[i].budgetPo = Number(newArray[i].budgetPo)
    //     }
    //     if (newArray[i].budgetPr == null) {
    //         newArray[i].budgetPr = Number(newArray[i].budgetPr)
    //     }
    //     if (newArray[i].budgetReclass == null) {
    //         newArray[i].budgetReclass = Number(newArray[i].budgetReclass)
    //     }
    //     newArray[i].budgetAfterAdjusment = newArray[i].budgetOriginal + newArray[i].budgetReclass + newArray[i].budgetAdjusment + newArray[i].budgetAdditional;
    //     newArray[i].budgetAvailable = newArray[i].budgetAfterAdjusment - (newArray[i].budgetPr + newArray[i].budgetPo + newArray[i].budgetGr);
    // }

    newArray.forEach(function(item, index) {
        item.number = index + 1;
        if (item.budgetAdditional != null) {
            item.budgetAdditional = Number(item.budgetAdditional.toFixed(0))
        } else {
            item.budgetAdditional = Number(item.budgetAdditional)
        }
        if (item.budgetOriginal == null) {
            item.budgetOriginal = Number(item.budgetOriginal)
        }
        if (item.budgetAdjusment == null) {
            item.budgetAdjusment = Number(item.budgetAdjusment)
        }
        if (item.budgetGr == null) {
            item.budgetGr = Number(item.budgetGr)
        }
        if (item.budgetPo == null) {
            item.budgetPo = Number(item.budgetPo)
        }
        if (item.budgetPr == null) {
            item.budgetPr = Number(item.budgetPr)
        }
        if (item.budgetReclass == null) {
            item.budgetReclass = Number(item.budgetReclass)
        }
        item.budgetAfterAdjusment = item.budgetOriginal + item.budgetReclass + item.budgetAdjusment + item.budgetAdditional;
        item.budgetAvailable = item.budgetAfterAdjusment - (item.budgetPr + item.budgetPo + item.budgetGr);
    })


    if (key == 'all') {
        let total = 0;

        Page.Variables.vmTotalValue.dataSet.totalBudgetOriginal = newArray.reduce((val, element) => {
            return val + element.budgetOriginal
        }, 0);

        Page.Variables.vmTotalValue.dataSet.totalReclass = newArray.reduce((val, element) => {
            return val + element.budgetReclass
        }, 0);

        Page.Variables.vmTotalValue.dataSet.totalAdditional = newArray.reduce((val, element) => {
            return val + element.budgetAdditional
        }, 0);

        Page.Variables.vmTotalValue.dataSet.totalAdjusment = newArray.reduce((val, element) => {
            return val + element.budgetAdjusment
        }, 0);

        Page.Variables.vmTotalValue.dataSet.totalAfterAdjusment = newArray.reduce((val, element) => {
            return val + element.budgetAfterAdjusment
        }, 0);


        Page.Variables.vmTotalValue.dataSet.totalReserved = newArray.reduce((val, element) => {
            return val + element.budgetPr
        }, 0);

        Page.Variables.vmTotalValue.dataSet.totalCommitment = newArray.reduce((val, element) => {
            return val + element.budgetPo
        }, 0);

        Page.Variables.vmTotalValue.dataSet.totalActual = newArray.reduce((val, element) => {
            return val + element.budgetGr
        }, 0);

        Page.Variables.vmTotalValue.dataSet.totalAvailable = newArray.reduce((val, element) => {
            return val + element.budgetAvailable
        }, 0);

        Page.Variables.vmTotalValue.dataSet.show = true;

    }

    return newArray;
}

Page.filterString = function(object, key, value) {
    var newObj = [];
    var i = 0;
    for (i = 0; i < object.length; i++) {
        if (object[i][key] == value) {
            newObj.push(object[i])
        }
    }
    if (i == object.length) {
        return newObj;
    }
}

Page.filterhasValue = function(object, key, value) {
    var newObj = [];
    var i = 0;
    for (i = 0; i < object.length; i++) {
        if (object[i][key] > 0 || object[i][key] < 0) {
            newObj.push(object[i])
        }
    }
    if (i == object.length) {
        return newObj;
    }
}

Page.filterhasNoValue = function(object, key, value) {
    var newObj = [];
    var i = 0;
    for (i = 0; i < object.length; i++) {
        if (object[i][key] == 0) {
            newObj.push(object[i])
        }
    }
    if (i == object.length) {
        return newObj;
    }
}



Page.notifyError = function(message) {
    App.Actions.appNotification.invoke({
        "class": "error",
        "message": message,
        "position": "bottom right",
        "duration": 2000
    });
}

Page.notifySuccess = function(message) {
    App.Actions.appNotification.invoke({
        "class": "success",
        "message": message,
        "position": "bottom right",
        "duration": 2000
    });
}

Page.tab1_arrowbottom1Click = function($event, widget) {
    Page.Variables.boolean_showTab1.dataSet.dataValue = true;
};
Page.tab1_arrowtop1Click = function($event, widget) {
    Page.Variables.boolean_showTab1.dataSet.dataValue = false;
};
Page.picture1Click = function($event, widget, item, currentItemWidgets) {
    selectedItem = item;
    Page.Widgets.EditStatus.open();
};
Page.tab2_arrowbottomClick = function($event, widget) {
    Page.Variables.boolean_showTab2.dataSet.dataValue = true;
};
Page.tab2_arrowtopClick = function($event, widget) {
    Page.Variables.boolean_showTab2.dataSet.dataValue = false;
};
Page.lbl_io_numberClick = function($event, widget, item, currentItemWidgets) {
    Page.NavagationFun(item);
};
Page.picture2Click = function($event, widget, item, currentItemWidgets) {
    Page.NavagationFun(item);
};

Page.label88Click = function($event, widget, item, currentItemWidgets) {
    Page.NavagationFun(item);
};

Page.picture4Click = function($event, widget, item, currentItemWidgets) {
    Page.NavagationFun(item);
};

Page.NavagationFun = function(item) {
    Page.Actions.navigationAction1.invoke({
        data: {
            'ionumber': item.ioNumber,
            'id': item.bhId
        }
    });
}
Page.EditStatusOpened = function($event, widget) {
    Page.Widgets.valueStatus.datavalue = selectedItem.bhStatus;
};
Page.btn_SaveClick = function($event, widget) {
    Page.Variables.QueryStatusBH.invoke({
        inputFields: {
            id: Number(selectedItem.bhId),
            status: Page.Widgets.valueStatus.datavalue
        }
    }, function(res) {
        Page.notifySuccess('Status Changed successfully');
        Page.btnApplyClick();
        Page.Widgets.EditStatus.close();
    });
};

Page.ApplyDynamicFilter = async function() {

    // if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
    //     if (App.Variables.loggedInUser.dataSet.roles.indexOf('BUD-006') !== -1) {
    //         Page.Variables.isAdminBudget.dataSet.dataValue = "TRUE"


    //         let depdata = await Page.Variables.vdbGetDepartment.invoke();
    //         // Page.Widgets.slct_department.dataset = Page.Variables.vdbGetDepartment.dataSet;
    //         Page.Variables.vmDepartment.dataSet = Page.Variables.vdbGetDepartment.dataSet
    //         Page.Widgets.slct_company.dataset = Page.Variables.vQueryCompany.dataSet;

    //         Page.Widgets.slct_brand.dataset = Page.Variables.vdbBrand.dataSet;
    //     } else {
    //         Page.Variables.isAdminBudget.dataSet.dataValue = "FALSE"
    //         let getval = await Page.Variables.vQueryDepMapUser.invoke({
    //             emp: App.Variables.loggedInUser.dataSet.name
    //         });
    //         Page.Variables.vmDepartment.dataSet = Page.Variables.vQueryDepMapUser.dataSet;

    //     }
    // }

    ObjFilter = Obj;

    if (Page.Widgets.select_io_number.datavalue == undefined || Page.Widgets.select_io_number.datavalue == 'All') {
        Page.Widgets.select_io_number.datavalue = "";
    }
    if (Page.Widgets.select_io_name.datavalue == undefined || Page.Widgets.select_io_name.datavalue == 'All') {
        Page.Widgets.select_io_name.datavalue = "";
    }

    if (Page.Widgets.select_status.datavalue == undefined || Page.Widgets.select_status.datavalue == 'All') {
        Page.Widgets.select_status.datavalue = ""
    }


    if (Page.Widgets.select_io_number.datavalue != "") {
        ObjFilter = await Page.filterString(ObjFilter, "ioNumber", Page.Widgets.select_io_number.datavalue);
    }

    if (Page.Widgets.select_io_name.datavalue != "") {
        ObjFilter = await Page.filterString(ObjFilter, "ioName", Page.Widgets.select_io_name.datavalue);
    }

    if (Page.Widgets.select_status.datavalue != "") {
        ObjFilter = await Page.filterString(ObjFilter, "bhStatus", Page.Widgets.select_status.datavalue);
    }

    if (Page.Widgets.select_budget_ori.datavalue == "hasvalue") {
        ObjFilter = await Page.filterhasValue(ObjFilter, "budgetOriginal", Page.Widgets.select_budget_ori.datavalue);
    } else if (Page.Widgets.select_budget_ori.datavalue == "hasnovalue") {
        ObjFilter = await Page.filterhasNoValue(ObjFilter, "budgetOriginal", Page.Widgets.select_budget_ori.datavalue);
    }

    if (Page.Widgets.select_additional.datavalue == "hasvalue") {
        ObjFilter = await Page.filterhasValue(ObjFilter, "budgetAdditional", Page.Widgets.select_additional.datavalue);
    } else if (Page.Widgets.select_additional.datavalue == "hasnovalue") {
        ObjFilter = await Page.filterhasNoValue(ObjFilter, "budgetAdditional", Page.Widgets.select_additional.datavalue);
    }


    if (Page.Widgets.select_adj.datavalue == "hasvalue") {
        ObjFilter = await Page.filterhasValue(ObjFilter, "budgetAdjusment", Page.Widgets.select_adj.datavalue);
    } else if (Page.Widgets.select_adj.datavalue == "hasnovalue") {
        ObjFilter = await Page.filterhasNoValue(ObjFilter, "budgetAdjusment", Page.Widgets.select_adj.datavalue);
    }
    if (Page.Widgets.select_after_adj.datavalue == "hasvalue") {
        ObjFilter = await Page.filterhasValue(ObjFilter, "budgetAfterAdjusment", Page.Widgets.select_after_adj.datavalue);
    } else if (Page.Widgets.select_after_adj.datavalue == "hasnovalue") {
        ObjFilter = await Page.filterhasNoValue(ObjFilter, "budgetAfterAdjusment", Page.Widgets.select_after_adj.datavalue);
    }

    if (Page.Widgets.slct_io_number.datavalue == undefined) {
        Page.Widgets.slct_io_number.datavalue = "";
    }
    if (Page.Widgets.slct_io_name.datavalue == undefined) {
        Page.Widgets.slct_io_name.datavalue = "";
    }

    if (Page.Widgets.slct_status.datavalue == undefined) {
        Page.Widgets.slct_status.datavalue = ""
    }

    if (Page.Widgets.slct_io_number.datavalue != "") {
        ObjFilter = await Page.filterString(ObjFilter, "ioNumber", Page.Widgets.slct_io_number.datavalue);
    }

    if (Page.Widgets.slct_io_name.datavalue != "") {
        ObjFilter = await Page.filterString(ObjFilter, "ioName", Page.Widgets.slct_io_name.datavalue);
    }

    if (Page.Widgets.slct_status.datavalue != "") {
        ObjFilter = await Page.filterString(ObjFilter, "bhStatus", Page.Widgets.slct_status.datavalue);
    }

    if (Page.Widgets.slct_budget_original.datavalue == "hasvalue") {
        ObjFilter = await Page.filterhasValue(ObjFilter, "budgetOriginal", Page.Widgets.slct_budget_original.datavalue);
    } else if (Page.Widgets.slct_budget_original.datavalue == "hasnovalue") {
        ObjFilter = await Page.filterhasNoValue(ObjFilter, "budgetOriginal", Page.Widgets.slct_budget_original.datavalue);
    }

    if (Page.Widgets.slct_after_adjustment.datavalue == "hasvalue") {
        ObjFilter = await Page.filterhasValue(ObjFilter, "budgetAfterAdjusment", Page.Widgets.slct_after_adjustment.datavalue);
    } else if (Page.Widgets.slct_after_adjustment.datavalue == "hasnovalue") {
        ObjFilter = await Page.filterhasNoValue(ObjFilter, "budgetAfterAdjusment", Page.Widgets.slct_after_adjustment.datavalue);
    }
    if (Page.Widgets.slct_reserved.datavalue == "hasvalue") {
        ObjFilter = await Page.filterhasValue(ObjFilter, "budgetPr", Page.Widgets.slct_reserved.datavalue);
    } else if (Page.Widgets.slct_reserved == "hasnovalue") {
        ObjFilter = await Page.filterhasNoValue(ObjFilter, "budgetPr", Page.Widgets.slct_reserved.datavalue);
    }

    if (Page.Widgets.slct_commitment.datavalue == "hasvalue") {
        ObjFilter = await Page.filterhasValue(ObjFilter, "budgetPo", Page.Widgets.slct_commitment.datavalue);
    } else if (Page.Widgets.slct_commitment.datavalue == "hasnovalue") {
        ObjFilter = await Page.filterhasNoValue(ObjFilter, "budgetPo", Page.Widgets.slct_commitment.datavalue);
    }

    if (Page.Widgets.slct_actual.datavalue == "hasvalue") {
        ObjFilter = await Page.filterhasValue(ObjFilter, "budgetGr", Page.Widgets.slct_actual.datavalue);
    } else if (Page.Widgets.slct_actual.datavalue == "hasnovalue") {
        ObjFilter = await Page.filterhasNoValue(ObjFilter, "budgetGr", Page.Widgets.slct_actual.datavalue);
    }
    if (Page.Widgets.slct_available.datavalue == "hasvalue") {
        ObjFilter = await Page.filterhasValue(ObjFilter, "budgetAvailable", Page.Widgets.slct_available.datavalue);
    } else if (Page.Widgets.slct_available.datavalue == "hasnovalue") {
        ObjFilter = await Page.filterhasNoValue(ObjFilter, "budgetAvailable", Page.Widgets.slct_available.datavalue);
    }


    ObjFilter = Page.amountCalculate(ObjFilter, 'all');
    Page.Variables.ProcedureBudgetDetail.dataSet.content = ObjFilter
    // Page.Variables.vmValueBudgetAdjustment.dataSet = ObjFilter;
    // Page.Variables.vmValueSpendUsage.dataSet = ObjFilter;

    //robi

}

Page.DynamicFilterClick = function($event, widget) {
    Page.ApplyDynamicFilter();
    Page.notifySuccess('The data is filtered successfully');
};

Page.reesetfilterClick = function($event, widget) {
    Page.Widgets.select_io_number.datavalue = undefined;
    Page.Widgets.select_io_name.datavalue = undefined;
    Page.Widgets.select_budget_ori.datavalue = undefined;
    Page.Widgets.select_additional.datavalue = undefined;
    Page.Widgets.select_adj.datavalue = undefined;
    Page.Widgets.select_after_adj.datavalue = undefined;
    Page.Widgets.select_status.datavalue = undefined;
    Page.ApplyDynamicFilter();
    Page.notifySuccess('The filter is cleared successfully');
};

Page.BtnClearFilterClick = function($event, widget) {
    Page.Widgets.slct_io_number.datavalue = undefined;
    Page.Widgets.slct_io_name.datavalue = undefined;
    Page.Widgets.slct_budget_original.datavalue = undefined;
    Page.Widgets.slct_after_adjustment.datavalue = undefined;
    Page.Widgets.slct_reserved.datavalue = undefined;
    Page.Widgets.slct_commitment.datavalue = undefined;
    Page.Widgets.slct_actual.datavalue = undefined;
    Page.Widgets.slct_available.datavalue = undefined;
    Page.Widgets.slct_status.datavalue = undefined;
    Page.ApplyDynamicFilter();
    Page.notifySuccess('The filter is cleared successfully');
};

Page.SpendFilterClick = function($event, widget) {
    Page.ApplyDynamicFilter();
    Page.notifySuccess('The data is filtered successfully');

};
Page.picture3Click = function($event, widget, item, currentItemWidgets) {
    selectedItem = item;
    Page.Widgets.EditStatus.open();
};
Page.ExportExcelClick = function($event, widget) {
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('Budget Detail 1');;
    let columnWidth = [5, 35, 5, 20, 5, 20, 20, 20, 5, 20, 5, 20, 20, 20, 20];

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

    let data = ObjFilter;
    let row = 14;


    //style

    sheet.getColumn('C').alignment = alignCenter;
    sheet.getRow(11).alignment = alignCenter;
    sheet.getRow(12).alignment = alignCenter;

    sheet.mergeCells('B11:B12');
    sheet.mergeCells('D11:D12');
    sheet.mergeCells('J11:J12');
    sheet.mergeCells('L11:L12');
    sheet.mergeCells('M11:M12');
    sheet.mergeCells('N11:N12');
    sheet.mergeCells('O11:O12');
    sheet.mergeCells('F11:H11');

    // header
    sheet.getCell("B4").value = "Budget Year";
    sheet.getCell("B5").value = "Company";
    sheet.getCell("B6").value = "Department";
    sheet.getCell("B7").value = "Brands";
    sheet.getCell("B8").value = "Account";
    sheet.getCell("B9").value = "Currency";

    sheet.getCell("C4").value = ":";
    sheet.getCell("C5").value = ":";
    sheet.getCell("C6").value = ":";
    sheet.getCell("C7").value = ":";
    sheet.getCell("C8").value = ":";
    sheet.getCell("C9").value = ":";

    sheet.getCell("D4").value = Page.Widgets.budgetYear.datavalue;
    if (Page.Widgets.slct_company.displayValue != undefined) {
        sheet.getCell("D5").value = Page.Widgets.slct_company.displayValue.toUpperCase();
    }

    if (Page.Widgets.slct_department.displayValue != undefined) {
        sheet.getCell("D6").value = Page.Widgets.slct_department.displayValue.toUpperCase();
    }

    if (Page.Widgets.slct_brand.displayValue != undefined) {
        sheet.getCell("D7").value = Page.Widgets.slct_brand.displayValue.toUpperCase();
    }

    if (Page.Widgets.slct_account_type.displayValue != undefined) {
        sheet.getCell("D8").value = Page.Widgets.slct_account_type.displayValue.toUpperCase();
    }

    sheet.getCell("D9").value = "IDR";

    sheet.getCell("B11").value = "IO Number";
    sheet.getCell("D11").value = "Budget Original";
    sheet.getCell("F11").value = "Total Adjustment";
    sheet.getCell("F12").value = "Reclass";
    sheet.getCell("G12").value = "Additional";
    sheet.getCell("H12").value = "Adjustment";
    sheet.getCell("J11").value = "After Adjustment";
    sheet.getCell("L11").value = "Reserved";
    sheet.getCell("M11").value = "Commitment";
    sheet.getCell("N11").value = "Actual";
    sheet.getCell("O11").value = "Available";


    sheet.getCell("L4").value = new Date().getDate().toString().padStart(2, "0") + '/' + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + '/' + new Date().getFullYear();
    sheet.getCell("L5").value = "This Data autogenerated by system";



    boldCell(["A11", "B11", "C11", "D11", "E11", "F12", "F11", "G12", "H12", "I11", "J11", "K11", "L11", "M11", "N11", "O11", "P11"]);
    sheet.getCell('B1').alignment = alignCenter;


    var data_border = ["B11", "D11", "F11", "J11", "L11", "M11", "N11", "O11", "F12", "G12", "H12"];

    data.forEach(el => {
        // sheet.getCell("A" + row).value = el.departmentTitle.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        sheet.getCell("B" + row).value = el.ioNumber + ' - ' + el.ioName.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");
        sheet.getCell("D" + row).value = Number(el.budgetOriginal.toFixed(0));
        sheet.getCell("D" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("F" + row).value = Number(el.budgetReclass.toFixed(0));
        sheet.getCell("F" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("G" + row).value = Number(el.budgetAdditional.toFixed(0));
        sheet.getCell("G" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("H" + row).value = Number(el.budgetAdjusment.toFixed(0));
        sheet.getCell("H" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("J" + row).value = Number(el.budgetAfterAdjusment.toFixed(0));
        sheet.getCell("J" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("L" + row).value = Number(el.budgetPr.toFixed(0)) != 0 ? Number(el.budgetPr.toFixed(0)) * -1 : Number(el.budgetPr.toFixed(0));
        sheet.getCell("L" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("M" + row).value = Number(el.budgetPo.toFixed(0));
        sheet.getCell("M" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("N" + row).value = Number(el.budgetGr.toFixed(0));
        sheet.getCell("N" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        sheet.getCell("O" + row).value = Number(el.budgetAvailable.toFixed(0));
        sheet.getCell("O" + row).numFmt = '#,##0.00;[Red]-#,##0.00';
        data_border.push("B" + row, "D" + row, "F" + row, "G" + row, "H" + row, "J" + row, "L" + row, "M" + row, "N" + row, "O" + row);
        // sheet.getCell("D" + row).value = el.omStatus.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">");;
        row++;
    });


    sheet.getCell("B" + (row + 1)).value = "TOTAL";
    sheet.getCell("D" + (row + 1)).value = Number(Page.Variables.vmTotalValue.dataSet.totalBudgetOriginal.toFixed(0));
    sheet.getCell("D" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("F" + (row + 1)).value = Number(Page.Variables.vmTotalValue.dataSet.totalReclass.toFixed(0));
    sheet.getCell("F" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("G" + (row + 1)).value = Number(Page.Variables.vmTotalValue.dataSet.totalAdditional.toFixed(0));
    sheet.getCell("G" + (row + 1)).numFmt = '#,###.00;[Red]-#,###.00';
    sheet.getCell("H" + (row + 1)).value = Number(Page.Variables.vmTotalValue.dataSet.totalAdjusment.toFixed(0));
    sheet.getCell("H" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("J" + (row + 1)).value = Number(Page.Variables.vmTotalValue.dataSet.totalAfterAdjusment.toFixed(0));
    sheet.getCell("J" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("L" + (row + 1)).value = Number(Page.Variables.vmTotalValue.dataSet.totalReserved.toFixed(0)) != 0 ? Number(Page.Variables.vmTotalValue.dataSet.totalReserved.toFixed(0)) * -1 : Number(Page.Variables.vmTotalValue.dataSet.totalReserved.toFixed(0));
    sheet.getCell("L" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("M" + (row + 1)).value = Number(Page.Variables.vmTotalValue.dataSet.totalCommitment.toFixed(0));
    sheet.getCell("M" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("N" + (row + 1)).value = Number(Page.Variables.vmTotalValue.dataSet.totalActual.toFixed(0));
    sheet.getCell("N" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    sheet.getCell("O" + (row + 1)).value = Number(Page.Variables.vmTotalValue.dataSet.totalAvailable.toFixed(0));
    sheet.getCell("O" + (row + 1)).numFmt = '#,##0.00;[Red]-#,##0.00';
    data_border.push("B" + (row + 1), "D" + (row + 1), "F" + (row + 1), "G" + (row + 1), "H" + (row + 1), "J" + (row + 1), "L" + (row + 1), "M" + (row + 1), "N" + (row + 1), "O" + (row + 1));
    //border
    data_border.map(key => {
        sheet.getCell(key).border = {
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
        }
    })


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
        saveAs(blob, "BudgetDetail1" + currentTime + ".xlsx");
    })
};