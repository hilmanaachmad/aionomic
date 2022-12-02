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

    Page.$page[0].addEventListener("click", function() {
        $('.aio-ptp-dropdown:not(.clicked)').removeClass("active")
        $('.aio-ptp-dropdown').removeClass("clicked")
    })
};


Page.container41Click = function($event, widget) {
    Page.toggleTableSort('company')
};
Page.containerPOClick = function($event, widget) {
    Page.toggleTableSort('sapPoNumber')
};
Page.container42Click = function($event, widget) {
    Page.toggleTableSort('department')
};
Page.container43Click = function($event, widget) {
    Page.toggleTableSort('statusRfq')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('docType')
};
Page.container45Click = function($event, widget) {
    Page.toggleTableSort('purchOrg')
};
Page.container110Click = function($event, widget) {
    Page.toggleTableSort('vendorName')
};
Page.container111Click = function($event, widget) {
    Page.toggleTableSort('totalAmount')
};
Page.container112Click = function($event, widget) {
    Page.toggleTableSort('currency')
};
Page.container113Click = function($event, widget) {
    Page.toggleTableSort('top')
};
Page.container50Click = function($event, widget) {
    Page.toggleTableSort('status')
};

Page.container43_1Click = function($event, widget) {
    Page.toggleTableSort('item')
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.DBPurchaseOrder.orderBy == field + " ASC") {
        Page.Variables.DBPurchaseOrder.orderBy = field + " DESC"
    } else if (Page.Variables.DBPurchaseOrder.orderBy == field + " DESC") {
        Page.Variables.DBPurchaseOrder.orderBy = ""
    } else {
        Page.Variables.DBPurchaseOrder.orderBy = field + " ASC"
    }
    Page.Variables.DBPurchaseOrder.invoke()
}

Page.searchValueKeyup = function($event, widget) {
    if ($event.key === "Enter") {
        Page.Variables.DBPurchaseOrder.update()
    }
};

// Page.selectSearchByChange = function($event, widget, newVal, oldVal) {
//     if (newVal === '') {
//         return Promise.resolve().then(() => {
//             return Page.Widgets.searchValue.datavalue = ""
//         }).then(() => {
//             return Page.Variables.DBPurchaseOrder.update()
//         }).then(() => {
//             return Page.Variables.DBPurchaseOrder.invoke()
//         })
//     }
// };

Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.DBPurchaseOrder.maxResults = newVal
    Page.Variables.DBPurchaseOrder.update()
};


// export excel
Page.button2Click = function($event, widget) {
    // intial
    var workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('Purchase Request');
    var xls_content = []
    var xls_header = [
        'Company',
        'Department',
        'Item',
        'SAP PO Number',
        'PO Date',
        'RFQ',
        'Doc Type',
        'Purchase Org',
        'Vendor',
        'Total Amount',
        'Currency',
        'TOP',
        'Status'
    ]
    xls_content.push(xls_header)

    Promise.resolve().then(() => {
        Page.Widgets.spinner2.show = true
        return Page.Variables.DBPurchaseOrder.invoke()
    }).then((data) => {
        data = data.data
        data.forEach(item => {
            var dataItem = [
                item.company,
                item.department,
                item.item,
                item.sapPoNumber,
                App.formatDate(item.createdDate),
                item.statusRfq,
                item.docType,
                item.purchOrg,
                item.vendorCode + ' - ' + item.vendorName,
                App.formatCurrency(item.totalAmount),
                item.currency,
                item.top,
                item.status
            ]
            xls_content.push(dataItem)
        })

        sheet.addRows(xls_content)

        // styling
        let columnWidth = [42, 32, 30, 30, 32, 32, 20, 20, 20, 12];
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
            var filename = "Purchase Order";
            var blob = new Blob([datas], {
                type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            });

            let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
            saveAs(blob, filename + currentTime + ".xlsx");
        })

        Page.Widgets.spinner2.show = false
    }).catch((err) => {
        Page.Widgets.spinner2.show = false
        console.log('err ', err)
    })
};


Page.button5Click = function($event, widget) {
    Page.Widgets.spinner2.show = true
    // intial
    var workbook = new ExcelJS.Workbook();
    var sheet = workbook.addWorksheet('Purchase Request');
    var xls_content = []
    var xls_header = [
        'Company',
        'Department',
        'Item',
        'SAP PO Number',
        'PO Date',
        'RFQ',
        'Doc Type',
        'Purchase Org',
        'Vendor',
        'Total Amount',
        'Currency',
        'TOP',
        'Status'
    ]
    xls_content.push(xls_header)

    Page.App.getVDBAllRecords(Page.Variables.DBPurchaseOrderAll).then(function(res) {
        res.forEach(item => {
            var dataItem = [
                item.company,
                item.department,
                item.item,
                item.sapPoNumber,
                App.formatDate(item.createdDate),
                item.statusRfq,
                item.docType,
                item.purchOrg,
                item.vendorCode + ' - ' + item.vendorName,
                App.formatCurrency(item.totalAmount),
                item.currency,
                item.top,
                item.status
            ]
            xls_content.push(dataItem)
        })

        sheet.addRows(xls_content)

        // styling
        let columnWidth = [42, 32, 30, 30, 32, 32, 20, 20, 20, 12];
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
            var filename = "Purchase Order All";
            var blob = new Blob([datas], {
                type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            });

            let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
            saveAs(blob, filename + currentTime + ".xlsx");
        })

        Page.Widgets.spinner2.show = false
    })
};


Page.dialogDeleteOk = async function($event, widget) {
    Page.Widgets.dialogDelete.close()
    Page.Widgets.spinner2.show = true
    Page.Variables.DBPOLineItem.invoke().then(async function(res) {
        if (res.data.length > 0) {
            var dataBodyItem = res.data
            var updatePRLineItem = await Page.updatePRLineItem(dataBodyItem)
            var updatePR = await Page.updatePR(dataBodyItem)

            Promise.resolve().then(async() => {
                return Page.Variables.DBPOHeader.deleteRecord({
                    row: {
                        "id": Page.Widgets.list2.selecteditem.id
                    }
                })
            }).then(function() {
                return Page.Variables.DBDeletePOLineItem.invoke({
                    "inputFields": {
                        "idPoHeader": Page.Widgets.list2.selecteditem.id
                    }
                })
            }).then(function() {
                return Page.Variables.DBPODoc.invoke({
                    "inputFields": {
                        "idPoHeader": Page.Widgets.list2.selecteditem.id
                    }
                })
            }).then(function() {
                return Page.Variables.deleteTaskListByParamsId.invoke({
                    "inputFields": {
                        "tlParamId": Page.Widgets.list2.selecteditem.id,
                        "tlType": "PO"
                    }
                })
            }).then(() => {
                App.Actions.appNotification.setMessage('Delete Data Successfully')
                App.Actions.appNotification.setToasterClass("success")
                App.Actions.appNotification.setToasterDuration(5000)
                App.Actions.appNotification.invoke()
                Page.Variables.DBPurchaseOrder.invoke()
                Page.Widgets.spinner2.show = false
            })
        } else {
            App.Actions.appNotification.setMessage('Data is null')
            App.Actions.appNotification.setToasterClass("warning")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            Page.Widgets.spinner2.show = false
        }

    })

};


Page.updatePRLineItem = function(dataBodyItem) {
    return new Promise((resolve, reject) => {
        if (dataBodyItem.length > 0) {
            for (var i = 0; i < dataBodyItem.length; i++) {
                Page.Variables.DBUpdatePRLineItem.invoke({
                    "inputFields": {
                        "pli_id": dataBodyItem[i].idPrLineItemHeader,
                        "pli_status": 'active'
                    }
                })
                if (i === 0) {
                    resolve(true)
                }
            }
        } else {
            resolve(true)
        }
    })
}


Page.updatePR = function(dataBodyItem) {
    return new Promise((resolve, reject) => {
        if (dataBodyItem.length > 0) {
            for (var i = 0; i < dataBodyItem.length; i++) {
                Page.Variables.DBUpdatePR.invoke({
                    "inputFields": {
                        "prId": dataBodyItem[i].idPrHeader,
                        "prStatus": 'Interfaced'
                    }
                })
                if (i === 0) {
                    resolve(true)
                }
            }
        } else {
            resolve(true)
        }
    })
}



Page.picture25_1Click = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_POFormView.invoke({
        data: {
            'id': Page.Widgets.list2.selecteditem.id
        }
    })
};


Page.picture39Click = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_POFormEdit.invoke({
        data: {
            'id': Page.Widgets.list2.selecteditem.id
        }
    })
};

Page.dialogInterfaceOk = function($event, widget) {
    Page.Widgets.dialogInterface.close()
    Page.Widgets.spinnerInterface.show = true
    Page.Variables.api_POPost.invoke({
        inputFields: {
            RequestBody: {
                id_po: Page.Widgets.list2.selecteditem.id
            }
        }
    }).then((data) => {
        Page.Widgets.spinnerInterface.show = false
        var result = JSON.parse(data.body)
        if (result.status === true) {

            Page.Variables.DBPurchaseOrder.invoke()
            App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SUCCESS)
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()

            try {
                Page.Variables.api_PONotifVendor.invoke({
                    inputFields: {
                        RequestBody: {
                            id_po: Page.pageParams.id
                        }
                    }
                });
            } catch {
                console.log('Notif email Failed');
            }
        } else {
            Page.Variables.DBPurchaseOrder.invoke()
            if (!Array.isArray(result.data.message)) {
                App.Actions.appNotification.setMessage("SAP :" + result.data.message)
                App.Actions.appNotification.setToasterClass("warning")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
            } else {
                App.Actions.appNotification.setMessage("SAP :" + result.data.message[0].MESSAGE)
                App.Actions.appNotification.setToasterClass("warning")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
            }
        }
    })
};

Page.showErrorClick = function($event, widget, item, currentItemWidgets) {
    var DBSAPError = Page.Variables.DBSAPError
    DBSAPError.dataSet = []
    DBSAPError.setFilter({
        "type": 'PO',
        "idHeader": Page.Widgets.list2.selecteditem.id
    });
    DBSAPError.listRecords();
};


Page.DBMaterialGroupPurchasingonSuccess = function(variable, data) {
    if (data.length > 0) {
        Page.Variables.MODELPurchasingGroup.dataSet = data
    } else {
        Page.Variables.MODELPurchasingGroup.dataSet = Page.Variables.vViewMaterialGroupList.dataSet
    }
};


Page.DBUserRoleonSuccess = function(variable, data) {
    console.log("data", data)
    Page.Variables.MODELConditionPurchase.dataSet = []
    Page.Variables.MODELConditionPurchaseAdmin.dataSet = []
    for (var i = 0; i < data.length; i++) {
        if (data[i].roleId == 1) {
            Page.Variables.MODELConditionPurchaseAdmin.dataSet.push(data[i].roleId)
        }
        if (data[i].roleId == 2) {
            Page.Variables.MODELConditionPurchase.dataSet.push(data[i].roleId)
        }
    }
};



Page.button9Click = async function($event, widget, item, currentItemWidgets) {
    Page.Widgets.spinnerInterface.show = true
    await Page.Variables.DataHeaderPO.invoke({
        "inputFields": {
            "id_po": Page.Widgets.list2.selecteditem.id
        }
    })

    await Page.Variables.getTop.invoke({
        "inputFields": {
            "top": Page.Widgets.list2.selecteditem.top
        }
    })

    await Page.Variables.getDataVendor.invoke({
        "inputFields": {
            "vendor_code": Page.Widgets.list2.selecteditem.vendorCode
        }
    })

    await Page.Variables.DBRFQTOPO.setFilter({
        "id": Page.Widgets.list2.selecteditem.id
    });
    await Page.Variables.DBRFQTOPO.listRecords();


    await Page.Variables.getAcceptance.setFilter({
        "idPo": Page.Widgets.list2.selecteditem.id
    });
    await Page.Variables.getAcceptance.listRecords();

    await Page.Variables.getPOLineItem.invoke({
        "inputFields": {
            "id_po": Page.Widgets.list2.selecteditem.id
        }
    }).then((data) => {
        if (data.ok) {
            data = JSON.parse(data.body).content
        }
        console.log("datadata", data)
        Page.Variables.modelLineItemPO.dataSet = []
        for (var i = 0; i < data.length; i++) {
            Page.Variables.modelLineItemPO.addItem({
                "nomor": i + 1,
                "assetsNo": data[i].assetsNo,
                "coa": data[i].coa,
                "costCenter": data[i].costCenter,
                "discount": data[i].discount,
                "eta": data[i].eta,
                "id": data[i].id,
                "idPoHeader": data[i].idPoHeader,
                "idPrHeader": data[i].idPrHeader,
                "idPrLineItemHeader": data[i].idPrLineItemHeader,
                "ioNumber": data[i].ioNumber,
                "item": data[i].item,
                "itemAmountAfterDiscount": data[i].itemAmountAfterDiscount,
                "itemTotalAmount": data[i].itemTotalAmount,
                "matlGroup": data[i].matlGroup,
                "matlGroupDesc": data[i].matlGroupDesc,
                "plant": data[i].plant,
                "poQty": data[i].poQty,
                "prNo": data[i].prNo,
                "prNoItem": data[i].prNoItem,
                "sapPrNo": data[i].sapPrNo,
                "unitPrice": data[i].unitPrice,
                "uom": data[i].uom
            })
        }
    })


    Page.Widgets.spinnerInterface.show = false
    Page.Widgets.dialogExport.open()
};


Page.buttonSaveExportClick = function($event, widget) {
    //window.open(App.JasperLink() + "reports/PO_PTP.pdf?j_username=scm&j_password=123456&id_po=" + Page.Widgets.list2.selecteditem.id)
    Page.Widgets.spinner2.show = true
    var element = Page.Widgets.containerExportBg.nativeElement;
    var opt = {
        margin: 0.25,
        scale: 5,
        filename: "Purchase Order.pdf",
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

Page.listExportBeforedatarender = function(widget, $data) {

    //table view elements

    let table = `<table style="border: 1px solid #000000;border-collapse: collapse;width: 100%;">
			<thead>
				<tr style="border: 1px solid #000000;border-collapse: collapse!important">
					<th style="border: 1px solid #000000;border-collapse: collapse;background-color: #eeeeee;width: 5%;text-align: center;padding: 5px;">No</th>
					<th style="border: 1px solid #000000;border-collapse: collapse;background-color: #eeeeee;width: 25%;text-align: center;padding: 5px;">Description</th>
					<th style="border: 1px solid #000000;border-collapse: collapse;background-color: #eeeeee;width: 11%;text-align: center;padding: 5px;">ETA</th>
					<th style="border: 1px solid #000000;border-collapse: collapse;background-color: #eeeeee;width: 6%;text-align: center;padding: 5px;">Qty</th>
					<th style="border: 1px solid #000000;border-collapse: collapse;background-color: #eeeeee;width: 7%;text-align: center;padding: 5px;">UOM</th>
					<th style="border: 1px solid #000000;border-collapse: collapse;background-color: #eeeeee;width: 10%;text-align: center;padding: 5px;">Unit Price</th>
					<th style="border: 1px solid #000000;border-collapse: collapse;background-color: #eeeeee;width: 10%;text-align: center;padding: 5px;">Amount</th>
					<th style="border: 1px solid #000000;border-collapse: collapse;background-color: #eeeeee;width: 6%;text-align: center;padding: 5px;">Disc.</th>
					<th style="border: 1px solid #000000;border-collapse: collapse;background-color: #eeeeee;width: 10%;text-align: center;padding: 5px;">Amount After Discount</th>
					<th style="border: 1px solid #000000;border-collapse: collapse;background-color: #eeeeee;width: 10%;text-align: center;padding: 5px;">VAT</th>
				</tr>
			</thead>
			<tbody>
				[rows]
			</tbody>
		</table>`;
    let row = `<tr style="border: 1px solid #000000;border-collapse: collapse!important">
			[items]
		</tr>`;
    let item = `<td style="border: 1px solid #000000;border-collapse: collapse;text-align: center;padding: 5px;">[item]</td>`

    let temp_rows = "";
    let temp_items = "";

    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.getPOLineItem.pagination.number * Page.Variables.getPOLineItem.pagination.size) + (i + 1);
        temp_items += item.replace('[item]', $data[i].nomor);
        temp_items += item.replace('[item]', $data[i].item);
        temp_items += item.replace('[item]', moment($data[i].eta).format('DD/MM/YYYY'));
        temp_items += item.replace('[item]', $data[i].poQty);
        temp_items += item.replace('[item]', $data[i].uom);
        temp_items += item.replace('[item]', App.formatCurrencyPTP($data[i].unitPrice));
        temp_items += item.replace('[item]', App.formatCurrencyPTP(parseInt($data[i].itemTotalAmount)));
        temp_items += item.replace('[item]', App.formatCurrencyPTP($data[i].discount));
        temp_items += item.replace('[item]', App.formatCurrencyPTP($data[i].itemAmountAfterDiscount));
        temp_items += item.replace('[item]', App.formatCurrencyPTP(parseInt(Page.calculationVatItem($data[i].itemAmountAfterDiscount, Page.Variables.DataHeaderPO.dataSet[0].vat))));
        temp_rows += row.replace('[items]', temp_items);
        temp_items = "";
    }

    table = table.replace('[rows]', temp_rows);
    document.getElementById('new-table').innerHTML = table;

    Page.Variables.totalLineItem.dataSet.subtotal = $data.reduce((val, element) => {
        return val + Number(element.itemAmountAfterDiscount)
    }, 0);


    Page.Variables.totalLineItem.dataSet.totalquantity = $data.reduce((val, element) => {
        return val + Number(element.poQty)
    }, 0);

    Page.Variables.totalLineItem.dataSet.totalvat = parseInt(Page.calculationVat($data, Page.Variables.DataHeaderPO.dataSet[0].vat));

    Page.Variables.totalLineItem.dataSet.subtotalchangesdisc = Number(Page.Variables.totalLineItem.dataSet.subtotal) + Number(Page.Variables.DataHeaderPO.dataSet[0].shippingHandling) + Number(Page.Variables.DataHeaderPO.dataSet[0].packingCost) + Number(Page.Variables.DataHeaderPO.dataSet[0].otherCost);
    Page.subtotalchangesdisc(1);

    Page.Variables.totalLineItem.dataSet.grandtotal = parseInt(Number(Page.Variables.totalLineItem.dataSet.subtotalchangesdisc) + Number(Page.Variables.totalLineItem.dataSet.totalvat));

};

Page.calculationVat = function(data, Vat) {
    let value = 0;
    for (let i = 0; i < data.length; i++) {
        let vatItem = Number(data[i].itemAmountAfterDiscount) * (Number(Vat) / 100);
        value = Number(value) + Number(vatItem);
        if (i == data.length - 1) {
            return Number(value);
        }
    }
};

Page.subtotalchangesdisc = function(data) {
    console.log(Page.Variables.DataHeaderPO.dataSet)
}

Page.calculationVatItem = function(amountItem, Vat) {
    // console.log(amountItem, Vat)
    let vatItem = Number(amountItem) * (Number(Vat) / 100);
    return Number(vatItem);
};

Page.picture10_1Click = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_PurchaseRequestFormEdit.invoke({
        data: {
            'prId': Page.Widgets.list2.selecteditem.prId
        }
    })
};


Page.picture26_1Click = function($event, widget, item, currentItemWidgets) {
    return Page.Variables.qPrChangeStatus.invoke({
        "inputFields": {
            'prId': Page.Widgets.list2.selecteditem.prId,
            "prModifiedAt": new Date().toISOString(),
            "prModifiedBy": App.Variables.loggedInUserData.dataSet.username,
            'prStatus': "Closed"
        }
    }).then(() => {
        Page.Variables.dbTblTPr.update()
        Page.Variables.dbTblPrAll.update()
        App.Actions.appNotification.setMessage(App.appLocale.NOTIF_DATA_SUCCESS)
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    })
};

Page.buttonSearchClick = function($event, widget) {
    Page.Variables.DBPurchaseOrder.update()
};
Page.buttonClearClick = function($event, widget) {
    Page.Widgets.searchPONumber.datavalue = null
    Page.Widgets.searchPOStatus.datavalue = null
    Page.Widgets.searchValue.datavalue = null
    Page.Widgets.selectSearchBy.datavalue = null
    Page.Variables.DBPurchaseOrder.update()
};

Page.DBRFQTOPOonSuccess = function(variable, data) {
    console.log('sales', data)
};