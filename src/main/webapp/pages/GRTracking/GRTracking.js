/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Page.onReady = async function() {
    Page.Variables.DBGRTracking.invoke()
}


Page.tab2_arrowbottomClick = function($event, widget) {
    Page.Variables.boolean_showFilter.dataSet.dataValue = true;
};

Page.tab2_arrowtopClick = function($event, widget) {
    Page.Variables.boolean_showFilter.dataSet.dataValue = false;
};

Page.SpendFilterClick = function($event, widget) {
    Page.Variables.DBGRTracking.invoke()
};


Page.BtnClearFilterClick = function($event, widget) {
    Promise.resolve().then(() => {
        Page.Widgets.filterReceivedDate.datavalue = null
        Page.Widgets.filterReceivedBy.datavalue = null
        Page.Widgets.filterReceivedQty.datavalue = null
        Page.Widgets.filterCondition.datavalue = null
        return true
    }).then(() => {
        return Page.Variables.DBGRTracking.invoke()
    }).then(() => {
        Page.Variables.DBGRTracking.invoke()
    })
};


Page.ExportExcelClick = function($event, widget) {
    // intial
    var workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('PO Tracking');
    var xls_content = []

    xls_content.push(["PO Number", Page.Variables.DBGRTracking.dataSet[0].sapPoNumber ? Page.Variables.DBGRTracking.dataSet[0].sapPoNumber : "-"])
    xls_content.push(["Item", Page.Variables.DBGRTracking.dataSet[0].item.toString().toUpperCase()])
    xls_content.push(["Quantity", Page.Variables.DBGRTracking.dataSet[0].deliveryQty + " Pcs"])
    xls_content.push(["ETA", Page.Variables.DBGRTracking.dataSet[0].eta])
    xls_content.push([])
    xls_content.push([])
    xls_content.push([Page.Variables.DBGRTrackingOrdered.dataSet[0].companyId === '2000' ? "PT. Otsuka Distribution" : "PT. Amerta Indah Otsuka"])
    xls_content.push([Page.Variables.DBGRTrackingOrdered.dataSet[0].creatorName])
    xls_content.push([Page.Variables.DBGRTrackingOrdered.dataSet[0].departmentName])
    xls_content.push(["Purchase By. " + Page.Variables.DBGRTrackingOrdered.dataSet[0].createdBy])
    xls_content.push([])
    xls_content.push([])

    var xls_header = [
        "GR Number",
        "Received Date",
        "Received By",
        "NIK",
        "Received Qty",
        "Uom",
        "Condition",
        "Comment",
    ]
    xls_content.push(xls_header)




    Promise.resolve().then(() => {
        return Page.Variables.DBGRTracking.invoke()
    }).then((data) => {
        data = data.data
        data.forEach(item => {
            var dataItem = [
                item.sapGrNumber,
                item.receivedDate,
                item.assignName,
                item.assign.toString().replace("emp::", ""),
                item.confirmQty,
                item.confirmUnit,
                item.condition,
                item.comment
            ]
            xls_content.push(dataItem)
        })

        sheet.addRows(xls_content)

        // styling
        let columnWidth = [20, 20, 20, 20, 20, 20, 20, 20, 20, 12];
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
            var filename = "GR Tracking";
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

Page.viewFileClick = function($event, widget, item, currentItemWidgets) {
    window.open(item.files);
};

Page.buttonSaveExportClick = function($event, widget) {
    Page.Widgets.spinner2.show = true
    var element = Page.Widgets.containerExportBg.nativeElement;
    var opt = {
        margin: 0.25,
        scale: 5,
        filename: "GR Tracking.pdf",
        jsPDF: {
            unit: 'in',
            format: 'letter',
            orientation: 'portrait'
        }
    }

    return Promise.resolve().then(function() {
        return html2pdf().set(opt).from(element).save();
    }).then(function() {
        Page.Widgets.spinner2.show = false
    })
};
Page.ExportPDFClick = function($event, widget) {
    Page.Widgets.dialogExport.open();
};