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

};

Page.parseInt = function(str) {
    return parseInt(str.toString().replace(/\D/g, '').replace(/\./g, ''))
}
Page.Math = Math



Page.buttonCancelClick = function($event, widget) {
    window.history.back()
};


Page.calculationVatItem = function(amountItem, Vat) {
    let vatItem = Number(amountItem) * (Number(Vat) / 100);
    return vatItem;
};


Page.calculationSubtotal = function() {
    var data = Page.Variables.modelLineItem.dataSet
    var subtotal = 0

    data.map(function(item) {
        subtotal = subtotal + Math.round(item.amountAfterDiscount)
    })
    return subtotal
};

Page.calculationVatrate = function() {
    return ((Page.formatCalaculate(Page.Widgets.textSubtotal.datavalue) * Page.formatCalaculate(Page.Widgets.textVatRate.datavalue)) / 100).toFixed(2);
}


Page.formatCalaculate = function(val) {

    if (typeof(val) == undefined) {
        return 0;
    } else {
        val = val.toString();
    }

    if (val.includes(".")) {
        val = App.replaceAll(val, '.', '');
    }

    if (val.includes(",")) {
        val = val.replace(',', '.');
    }

    return Number(val)
}

// download file
Page.label26_2Click = function($event, widget, item, currentItemWidgets) {
    Page.Widgets.spinner1.show = true
    Page.Variables.FileServiceDownloadFile.invoke({
        "inputFields": {
            "file": Page.Widgets.list2.selecteditem.filename,
            "returnName": Page.Widgets.list2.selecteditem.filename
        }
    }).then(function() {
        Page.Widgets.spinner1.show = false
    })
};

Page.picture7Click = function($event, widget, item, currentItemWidgets) {
    document.getElementById("pdf-canvas-container").innerHTML = ""
    Page.Variables.vmShowPdf.dataSet.dataValue = true
    Page.Widgets.spinner1.show = true
    var filePath = Page.Widgets.list2.selecteditem.pathFile
    // Loaded via <script> tag, create shortcut to access PDF.js exports.
    var pdfjsLib = window['pdfjs-dist/build/pdf'];
    // The workerSrc property shall be specified.
    pdfjsLib.GlobalWorkerOptions.workerSrc = '//cdnjs.cloudflare.com/ajax/libs/pdf.js/2.6.347/pdf.worker.min.js';

    var loadingTask = pdfjsLib.getDocument(filePath);
    return loadingTask.promise.then(function(pdf) {
        var pageNumber = 1;
        return Page.renderPDFPage(pdf, pageNumber)
    }).then(function() {
        Page.Widgets.spinner1.show = false
    }).catch(function(error) {
        Page.Widgets.spinner1.show = false
    });
};

Page.picture4Click = function($event, widget) {
    Page.Variables.vmShowPdf.dataSet.dataValue = false
    document.getElementById("pdf-canvas-container").innerHTML = ""
};

Page.renderPDFPage = function(pdf, pageNumber) {
    return pdf.getPage(pageNumber).then(function(page) {
        var scale = 1;
        var viewport = page.getViewport({
            scale: scale
        });

        // Prepare canvas using PDF page dimensions
        var canvas = document.createElement("canvas")
        var context = canvas.getContext('2d');
        canvas.height = viewport.height;
        canvas.width = viewport.width;
        canvas.style.marginBottom = "24px"

        // Render PDF page into canvas context
        var renderContext = {
            canvasContext: context,
            viewport: viewport
        };
        var renderTask = page.render(renderContext);
        return renderTask.promise.then(function() {
            return canvas
        })
    }).then(function(canvas) {
        document.getElementById("pdf-canvas-container").appendChild(canvas)
        if (pageNumber >= pdf.numPages) {
            return Promise.resolve()
        }
        return Page.renderPDFPage(pdf, pageNumber + 1)
    }).catch(function(err) {
        if (pageNumber >= pdf.numPages) {
            return Promise.resolve()
        }
        return Page.renderPDFPage(pdf, pageNumber + 1)
    })
};

// export PDF
Page.buttonSaveExportClick = function($event, widget) {
    Page.Widgets.spinner2.show = true
    var element = Page.Widgets.containerExportBg.nativeElement;
    var opt = {
        margin: 0.25,
        scale: 5,
        filename: "Detail Purchase Order.pdf",
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

Page.RejectClick = function($event, widget) {

    Page.Variables.updateStatusPO.invoke({
        inputFields: {
            status: 'Rejected',
            idpo: '1'
        }
    }, function(res) {
        Page.notifySuccess('Status Rejected successfully');
        Page.Widgets.RejectOrder.close();
    });
};

Page.calculationVatItem = function(amountItem, Vat) {
    let vatItem = Number(amountItem) * (Number(Vat) / 100);
    return Number(vatItem);
};

Page.notifySuccess = function(message) {
    App.Actions.appNotification.invoke({
        "class": "success",
        "message": message,
        "position": "bottom right",
        "duration": 2000
    });
}
Page.BtnConfirmClick = function($event, widget) {
    for (let i = 0; i < Page.Variables.getPOLineItem.dataSet.length; i++) {
        let gr = Page.Variables.GoodReceipt;
        gr.createRecord({
            row: {
                'idPoHeader': Page.Variables.getPOLineItem.dataSet[i].idPoHeader,
                'idPrHeader': Page.Variables.getPOLineItem.dataSet[i].idPrHeader,
                'idPrLineItemHeader': Page.Variables.getPOLineItem.dataSet[i].idPrLineItemHeader,
                'item': Page.Variables.getPOLineItem.dataSet[i].item,
                'uom': Page.Variables.getPOLineItem.dataSet[i].uom,
                'unitPrice': Page.Variables.getPOLineItem.dataSet[i].unitPrice,
                'itemTotalAmount': Page.Variables.getPOLineItem.dataSet[i].itemTotalAmount,
                'matlGroup': Page.Variables.getPOLineItem.dataSet[i].matlGroup,
                'matlGroupDesc': Page.Variables.getPOLineItem.dataSet[i].matlGroupDesc,
                'plant': Page.Variables.getPOLineItem.dataSet[i].plant,
                'eta': Page.Variables.getPOLineItem.dataSet[i].eta,
                'ioNumber': Page.Variables.getPOLineItem.dataSet[i].ioNumber,
                'costCenter': Page.Variables.getPOLineItem.dataSet[i].costCenter,
                'coa': Page.Variables.getPOLineItem.dataSet[i].coa,
                'assetsNo': Page.Variables.getPOLineItem.dataSet[i].assetsNo,
                'prNo': Page.Variables.getPOLineItem.dataSet[i].prNo,
                'prNoItem': Page.Variables.getPOLineItem.dataSet[i].prNoItem,
                'sapPrNo': Page.Variables.getPOLineItem.dataSet[i].sapPrNo,
                'discount': Page.Variables.getPOLineItem.dataSet[i].discount,
                'deliveryQty': 0,
                'status': '',
                'comment': ''
            }
        }, function(data) {}, function(error) {});
    }
    var lv = Page.Variables.insertAcceptance;
    lv.createRecord({
        row: {
            'name': Page.Widgets.NameText.datavalue,
            'position': Page.Widgets.PositionText.datavalue,
            'idPo': Page.pageParams.id_po,
            'acceptedAt': new Date()
        }
    }, function(data) {}, function(error) {});

    Page.Variables.updateStatusPO.invoke({
        inputFields: {
            status: 'Accepted',
            idpo: Page.pageParams.id_po
        }
    }, function(res) {
        Page.notifySuccess('Status Accepted successfully');
        Page.Widgets.dialogItem.close();
    });
};

Page.button11Click = async function($event, widget) {
    let deliverData = Page.Variables.modelItem.dataSet.filter(x => x.isDone == false);


    if (deliverData.length == 0) {
        Page.notify('All item has been delivered', 'warning');
        return false;
    }

    if (Page.Widgets.deliveryNumber.datavalue == '' || Page.Widgets.deliveryNumber.datavalue == undefined) {
        Page.notify('Delivery Number is required', 'warning');
        return false;
    }

    if (Page.Widgets.textarea1.datavalue == '' || Page.Widgets.textarea1.datavalue == undefined || Page.Widgets.textarea1.datavalue.length > 255) {
        Page.notify('Comment is required', 'warning');
        return false;
    }

    Page.Widgets.spinner1.show = true
    let count = 0;
    try {
        for (let i = 0; i < deliverData.length; i++) {
            let gr = Page.Variables.gRLineItem;
            if (deliverData[i].deliveryQty && deliverData[i].deliveryDate) {
                count++;
                await gr.createRecord({
                    row: {
                        "assetsNo": deliverData[i].assetsNo,
                        "coa": deliverData[i].coa,
                        "costCenter": deliverData[i].costCenter,
                        "discount": deliverData[i].discount,
                        "eta": deliverData[i].eta,
                        "idPoLineItem": deliverData[i].id,
                        "deliveryQty": deliverData[i].deliveryQty.replaceAll(',', "."),
                        "deliveryDate": deliverData[i].deliveryDate,
                        "comment": Page.Widgets.textarea1.datavalue,
                        "deliveryNumber": Page.Widgets.deliveryNumber.datavalue,
                        "idPoHeader": deliverData[i].idPoHeader,
                        "idPrHeader": deliverData[i].idPrHeader,
                        "idPrLineItemHeader": deliverData[i].idPrLineItemHeader,
                        "ioNumber": deliverData[i].ioNumber,
                        "item": deliverData[i].item,
                        "itemAmountAfterDiscount": deliverData[i].itemAmountAfterDiscount,
                        "itemTotalAmount": deliverData[i].itemTotalAmount,
                        "matlGroup": deliverData[i].matlGroup,
                        "matlGroupDesc": deliverData[i].matlGroupDesc,
                        "plant": deliverData[i].plant,
                        "poQty": deliverData[i].poQty,
                        "prNo": deliverData[i].prNo,
                        "prNoItem": deliverData[i].prNoItem,
                        "sapPrNo": deliverData[i].sapPrNo,
                        "unitPrice": deliverData[i].unitPrice,
                        "uom": deliverData[i].uom,
                        "deliveryUom": deliverData[i].uom,
                        "status": "DELIVERY"
                    }
                }, function(data) {}, function(error) {
                    Page.notify(`Submitting item no. ${deliverData[i].nomor} Failed`, 'error');
                    Page.notify(`Error`, 'please check your browser console');
                    console.error("error", error);
                    Page.Widgets.spinner1.show = false;
                    //tambahkan notif ke yusuf
                    throw error;
                });
            }

            if (i == deliverData.length - 1) {
                if (count > 0) {
                    Page.notify('Delivery Confirmation Successfully', 'success');
                    await Page.Variables.ItemToDeliver.invoke();
                    Page.Widgets.spinner1.show = false;
                    await Page.Variables.api_PONotifDelivery.invoke({
                        inputFields: {
                            RequestBody: {
                                id_po: Page.pageParams.id_po
                            }
                        }
                    });
                } else {
                    Page.notify('Delivery Qty and Delivery Date are required', 'warning');
                    Page.Widgets.spinner1.show = false;
                }
            }
        }
        Page.Widgets.spinner1.show = false;
    } catch (err) {
        Page.Widgets.spinner1.show = false
        Page.notify(`Submitting item Failed`, 'please check your browser console!');
        Page.notify(`Error`, err);
        console.error("error", err);
    }

    Page.Widgets.spinner1.show = false

};

Page.ItemToDeliveronSuccess = function(variable, data) {
    Page.Variables.modelItem.dataSet = [];
    for (let i = 0; i < data.length; i++) {

        let isDone = false;

        if (data[i].delivered != null && Number(data[i].delivered) >= Number(data[i].poQty)) {
            isDone = true;
        }

        Page.Variables.modelItem.addItem({
            "nomor": i + 1,
            "assetsNo": data[i].assetsNo,
            "coa": data[i].coa,
            "costCenter": data[i].costCenter,
            "delivered": data[i].delivered,
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
            "status": data[i].status,
            "isDone": isDone,
            "unitPrice": data[i].unitPrice,
            "uom": data[i].uom,
            "deliveryQty": 0,
            "deliveryUnit": "",
            "deliveryDate": "",
            "deliveryUom": data[i].uom,
            "tanggaldelivery": data[i].deliveryDate
        })

        if (i === data.length - 1) {
            console.log("modelItem", Page.Variables.modelItem)
        }

    }
};
Page.text4Keyup = function($event, widget, item, currentItemWidgets) {
    if (item.uom === 'UNT' || item.uom === 'SET' || item.uom === 'AU') {
        var newVal = widget.datavalue
        var conditionValue = widget.datavalue
        var splitString = newVal.toString().split(",");
        var beforeComma = splitString[0]
        var afterComma = splitString[1]
        if (newVal.includes(",")) {
            currentItemWidgets.text4.datavalue = String(newVal).replaceAll(',', ".");

            if (afterComma && afterComma.length > 0) {
                //newVal = (beforeComma || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.') + ',' + afterComma
                //conditionValue = (beforeComma || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.') + '.' + afterComma
            } else {
                //newVal = (beforeComma || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.') + ','
                //conditionValue = (beforeComma || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.') + '.'
            }
        } else {
            //newVal = (beforeComma || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
            //conditionValue = (beforeComma || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
        }

        var indexOfItem = Page.Variables.modelItem.dataSet.findIndex(i => i.id == item.id);
        if (Number(conditionValue) > (item.poQty - item.delivered)) {
            Page.notify('Quantity more than Quantity Order', 'warning');
            currentItemWidgets.text4.datavalue = String(Number(item.poQty - item.delivered)).replaceAll(',', ".");
            Page.Variables.modelItem.dataSet[indexOfItem].deliveryQty = String(item.poQty - item.delivered);
        } else {
            Page.Variables.modelItem.dataSet[indexOfItem].deliveryQty = newVal;
            currentItemWidgets.text4.datavalue = newVal
        }

        let totalAmount = Number(item.unitPrice) * Number(conditionValue)
        Page.Variables.modelItem.dataSet[indexOfItem].itemTotalAmount = totalAmount;
        let discountVal = Number(totalAmount) * (Number(item.discount) / 100)
        Page.Variables.modelItem.dataSet[indexOfItem].itemAmountAfterDiscount = Number(totalAmount) - Number(discountVal)
    } else {
        // var newVal = Page.numberFormat(widget.datavalue)
        var newVal = widget.datavalue;
        // newVal = newVal.substr(0, 15);
        var indexOfItem = Page.Variables.modelItem.dataSet.findIndex(i => i.id == item.id);
        if (Number(newVal) > (item.poQty - item.delivered)) {
            Page.notify('Quantity more than Quantity Order', 'warning');
            // currentItemWidgets.text4.datavalue = String(Number(item.poQty - item.delivered)).replaceAll('.', ",");
            currentItemWidgets.text4.datavalue = String(Number(item.poQty - item.delivered).toFixed(5));
            Page.Variables.modelItem.dataSet[indexOfItem].deliveryQty = String((item.poQty - item.delivered).toFixed(5)).replaceAll(',', ".");;
        } else {
            Page.Variables.modelItem.dataSet[indexOfItem].deliveryQty = newVal;
            currentItemWidgets.text4.datavalue = newVal
        }
        let totalAmount = Number(item.unitPrice) * Number(Page.Variables.modelItem.dataSet[indexOfItem].deliveryQty)
        Page.Variables.modelItem.dataSet[indexOfItem].itemTotalAmount = totalAmount;
        let discountVal = Number(totalAmount) * (Number(item.discount) / 100)
        Page.Variables.modelItem.dataSet[indexOfItem].itemAmountAfterDiscount = Number(totalAmount) - Number(discountVal)
    }

};

Page.text4Keypress = function($event, widget, item, currentItemWidgets) {

};

Page.notify = function(message, typenotif) {
    App.Actions.appNotification.invoke({
        "class": typenotif,
        "message": message,
        "position": "bottom right",
        "duration": 2000
    });
}

Page.numberFormat = function(newVal) {
    newVal = newVal.toString();
    newVal = newVal.replace(/\D/g, '')
    // if (newVal.charAt(0) == '0' && newVal.length > 0) {
    //     newVal = newVal.substr(1)
    // }
    if (newVal == '') {
        newVal = '0'
    }
    return newVal;
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

Page.getDataDetailDOonSuccess = function(variable, data) {
    if (data.length > 0) {
        Page.Variables.getTop.invoke({
            inputFields: {
                top: data[0].top
            }
        }, function(res) {

        });
    }
};

Page.getPOLineItemonSuccess = function(variable, data) {


    Page.Variables.modelLineItemPO.dataSet = [];

    for (var i = 0; i < data.length; i++) {

        Page.Variables.totalLineItem.dataSet.subtotal = data.reduce((val, element) => {
            return val + Number(element.itemAmountAfterDiscount)
        }, 0);

        Page.Variables.totalLineItem.dataSet.totalquantity = data.reduce((val, element) => {
            return val + Number(element.poQty)
        }, 0);

        Page.Variables.totalLineItem.dataSet.totalvat = parseInt(Page.calculationVat(data, Page.Variables.getDataDetailDO.dataSet[0].vat));

        Page.Variables.totalLineItem.dataSet.subtotalchangesdisc = Number(Page.Variables.totalLineItem.dataSet.subtotal) + Number(Page.Variables.getDataDetailDO.dataSet[0].shippingHandling) + Number(Page.Variables.getDataDetailDO.dataSet[0].packingCost) + Number(Page.Variables.getDataDetailDO.dataSet[0].otherCost);

        Page.Variables.totalLineItem.dataSet.grandtotal = parseInt(Number(Page.Variables.totalLineItem.dataSet.subtotalchangesdisc) + Number(Page.Variables.totalLineItem.dataSet.totalvat));


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


};

Page.ItemToDeliveronError = function(variable, data) {
    Page.notify('Get Data', 'error');
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

    let data = $data;
    for (var i = 0; i < data.length; i++) {

        data[i].nomor = (Page.Variables.getPOLineItem.pagination.number * Page.Variables.getPOLineItem.pagination.size) + (i + 1);
        temp_items += item.replace('[item]', data[i].nomor);
        temp_items += item.replace('[item]', data[i].item);
        temp_items += item.replace('[item]', moment(data[i].eta).format('DD/MM/YYYY'));
        temp_items += item.replace('[item]', data[i].poQty);
        temp_items += item.replace('[item]', data[i].uom);
        temp_items += item.replace('[item]', App.formatCurrencyPTP(data[i].unitPrice));
        temp_items += item.replace('[item]', App.formatCurrencyPTP(parseInt(data[i].itemTotalAmount)));
        temp_items += item.replace('[item]', App.formatCurrencyPTP(data[i].discount));
        temp_items += item.replace('[item]', App.formatCurrencyPTP(data[i].itemAmountAfterDiscount));
        temp_items += item.replace('[item]', App.formatCurrencyPTP(parseInt(Page.calculationVatItem(data[i].itemAmountAfterDiscount, Page.Variables.getDataDetailDO.dataSet[0].vat))));
        temp_rows += row.replace('[items]', temp_items);
        temp_items = "";
    }

    table = table.replace('[rows]', temp_rows);
    console.log('hehe', document.getElementById('new-table'))
    document.getElementById('new-table').innerHTML = table;
};