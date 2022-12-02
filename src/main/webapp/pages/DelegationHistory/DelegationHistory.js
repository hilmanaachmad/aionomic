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
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('MST-004') == -1) {
            App.Actions.goToPage_Main.invoke()
        }
    }
};

// sort function
Page.toggleTableSort = function(field, picture) {
    var table = Page.Variables.vdbDelHistory
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
    Page.toggleTableSort("authDesc", Page.Widgets.picture1)

    Page.Widgets.picture6.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture5.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture6_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container30Click = function($event, widget) {
    Page.toggleTableSort("delFromUserName", Page.Widgets.picture6)

    Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture5.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture6_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container18Click = function($event, widget) {
    Page.toggleTableSort("delToUserName", Page.Widgets.picture5)

    Page.Widgets.picture6.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture6_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container29Click = function($event, widget) {
    Page.toggleTableSort("delAvailDatetime", Page.Widgets.picture2)

    Page.Widgets.picture6.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture5.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture6_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container37Click = function($event, widget) {
    Page.toggleTableSort("delExpDatetime", Page.Widgets.picture7)

    Page.Widgets.picture6.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture5.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture6_1.picturesource = 'resources/images/logos/icon-up-down.png'
};
Page.container19Click = function($event, widget) {
    Page.toggleTableSort("delReason", Page.Widgets.picture6_1)

    Page.Widgets.picture6.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture5.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture2.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture7.picturesource = 'resources/images/logos/icon-up-down.png'
    Page.Widgets.picture1.picturesource = 'resources/images/logos/icon-up-down.png'
};

Page.roleUserListBeforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.vdbDelHistory.pagination.number * Page.Variables.vdbDelHistory.pagination.size) + (i + 1);
    }
};

Page.select9_2Change = function($event, widget, newVal, oldVal) {
    Page.Variables.vdbDelHistory.maxResults = newVal
    Page.Variables.vdbDelHistory.update()
};

// export
Page.button2Click = function($event, widget) {
    return Page.exportExcel(Page.Variables.vdbDelHistory.dataSet)
};

// export all
Page.button2_1Click = function($event, widget) {
    Page.Widgets.spinner1.show = true

    Page.App.getVDBQAllRecords(Page.Variables.vdbDelHistoryAll).then(function(res) {
        // var data = JSON.parse(res.body)
        // console.log("export", res)
        return Page.exportExcel(res)
    }).then(function() {
        Page.Widgets.spinner1.show = false
    })
};

Page.exportExcel = function(varData) {
    // intial
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('History');
    var filename = "Delegation_";

    // inserting data
    var xls_content = []

    var xls_header = [
        "Module",
        "From Employee",
        "To Employee",
        "From Date",
        "To Date",
        "Reason"
    ]
    xls_content.push(xls_header)

    // let data = Page.Variables.vdbDelHistory.dataSet
    let data = varData
    data.forEach(item => {
        var avaiDate = item.delAvailDatetime.split('T')
        var delAvailDatetime = avaiDate[0].split("-")[2] + "-" + avaiDate[0].split("-")[1] + "-" + avaiDate[0].split("-")[0]

        var exDate = item.delExpDatetime.split('T')
        var delExpDatetime = exDate[0].split("-")[2] + "-" + exDate[0].split("-")[1] + "-" + exDate[0].split("-")[0]

        var dataItem = [
            item.authDesc,
            item.delFromUserName,
            item.delToUserName,
            delAvailDatetime,
            delExpDatetime,
            item.delReason
        ]

        xls_content.push(dataItem)
    })

    sheet.addRows(xls_content)


    // styling
    let alignCenter = {
        vertical: 'middle',
        'horizontal': 'center'
    };
    sheet.getRow(1).alignment = alignCenter;

    // Adjust column width
    let columnWidth = [25, 20, 20, 14, 14, 14];
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
        saveAs(blob, filename + currentTime + ".xlsx");
    })
};