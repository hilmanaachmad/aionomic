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
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('MST-003') == -1) {
            App.Actions.goToPage_Main.invoke()
        }
    }
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.getUserRoleMap.orderBy == field + " ASC") {
        Page.Variables.getUserRoleMap.orderBy = field + " DESC"
    } else if (Page.Variables.getUserRoleMap.orderBy == field + " DESC") {
        Page.Variables.getUserRoleMap.orderBy = ""
    } else {
        Page.Variables.getUserRoleMap.orderBy = field + " ASC"
    }

    console.log("sortby", Page.Variables.getUserRoleMap.orderBy)
    Page.Variables.getUserRoleMap.update()
}
Page.container28Click = function($event, widget) {
    Page.toggleTableSort("tblMrole.roleTitle")

    if (Page.Variables.getUserRoleMap.orderBy == "tblMrole.roleTitle ASC") {
        Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.getUserRoleMap.orderBy == "tblMrole.roleTitle DESC") {
        Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down.png'
    }

    Page.Widgets.picture6.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container30Click = function($event, widget) {
    Page.toggleTableSort("userFullName")

    if (Page.Variables.getUserRoleMap.orderBy == "userFullName ASC") {
        Page.Widgets.picture6.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.getUserRoleMap.orderBy == "userFullName DESC") {
        Page.Widgets.picture6.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture6.picturesource = 'resources/images/logos/icon-up-down.png'
    }

    Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container29Click = function($event, widget) {
    Page.toggleTableSort("urFromDate")

    if (Page.Variables.getUserRoleMap.orderBy == "urFromDate ASC") {
        Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.getUserRoleMap.orderBy == "urFromDate DESC") {
        Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down.png'
    }

    Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture6.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container37Click = function($event, widget) {
    Page.toggleTableSort("urToDate")

    if (Page.Variables.getUserRoleMap.orderBy == "urToDate ASC") {
        Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.getUserRoleMap.orderBy == "urToDate DESC") {
        Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    }

    Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture6.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down.png'
};

Page.roleUserListBeforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.getUserRoleMap.pagination.number * Page.Variables.getUserRoleMap.pagination.size) + (i + 1);
    }
};

Page.select9_2Change = function($event, widget, newVal, oldVal) {
    Page.Variables.getUserRoleMap.maxResults = newVal
    Page.Variables.getUserRoleMap.update()
};

// export excel
Page.button2Click = function($event, widget) {
    // intial
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('User Map History');

    // inserting data
    var xls_content = []

    var xls_header = [
        "Role",
        "Employee",
        "From Date",
        "To Date"
    ]
    xls_content.push(xls_header)

    let data = Page.Variables.getUserRoleMap.dataSet
    data.forEach(item => {
        var dataItem = [
            item.tblMrole.roleTitle,
            item.userFullName,
            item.urFromDate ? Page.dateFormat(item.urFromDate) : 'Unlimited',
            Page.dateFormat(item.urToDate)
        ]
        xls_content.push(dataItem)
    })
    sheet.addRows(xls_content)

    let alignCenter = {
        vertical: 'middle',
        'horizontal': 'center'
    };
    sheet.getRow(1).alignment = alignCenter;

    let columnWidth = [12, 27, 14, 14];
    sheet.columns.forEach((col, index) => {
        if (columnWidth[index]) {
            col.width = columnWidth[index];
        }
    });

    // exporting
    workbook.xlsx.writeBuffer().then(function(datas) {
        var filename = "User Map History - ";
        var blob = new Blob([datas], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        });

        let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
        saveAs(blob, filename + currentTime + ".xlsx");
    })
};

// change format date
Page.dateFormat = function(data) {
    var format = ""

    if (data) {
        // format YYYY-MM-DD hh:mm:ss (ISO Timestamp format)
        var d = data.toString()
        var date = d.split(" ")
        var formatDate = date[0].split("-")

        // parse date
        var year = formatDate[0]
        var month = formatDate[1]
        var day = formatDate[2]

        // format into DD-MM-YYYY
        format = day + "-" + month + "-" + year
    }

    return format
}

Page.button2_1Click = function($event, widget) {
    // intial
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('User Map History');

    // inserting data
    var xls_content = []

    var xls_header = [
        "Role",
        "Employee",
        "From Date",
        "To Date"
    ]
    xls_content.push(xls_header)

    Page.App.getVDBQAllRecords(Page.Variables.qGetMapRoleAll).then(function(res) {
        let data = res
        data.forEach(item => {
            var dataItem = [
                item.roleTitle,
                item.userFullName,
                item.urFromDate ? Page.dateFormat(item.urFromDate) : 'Unlimited',
                Page.dateFormat(item.urToDate)
            ]
            xls_content.push(dataItem)
        })
        sheet.addRows(xls_content)

        let alignCenter = {
            vertical: 'middle',
            'horizontal': 'center'
        };
        sheet.getRow(1).alignment = alignCenter;

        let columnWidth = [12, 27, 14, 14];
        sheet.columns.forEach((col, index) => {
            if (columnWidth[index]) {
                col.width = columnWidth[index];
            }
        });

        // exporting
        workbook.xlsx.writeBuffer().then(function(datas) {
            var filename = "User Map History - ";
            var blob = new Blob([datas], {
                type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            });

            let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
            saveAs(blob, filename + currentTime + ".xlsx");
        })
    })
};