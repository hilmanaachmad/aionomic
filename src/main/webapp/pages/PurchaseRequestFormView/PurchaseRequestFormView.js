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

Page.vPurchaseRequestonSuccess = function(variable, data) {
    Page.Variables.vmPRModel.setData(data[0])
    // console.log("PRMODEL", Page.Variables.vmPRModel.dataSet)
    if (Array.isArray(data)) {
        if (data[0].prAttchData !== null) {
            var attachment = JSON.parse(atob(data[0].prAttchData))
            Page.Variables.vmAttachmentList.setData(attachment)
        }
    }

};

Page.svGetCurrencyonSuccess = function(variable, data) {
    for (var i = 0; i < data.length; i++) {
        Page.Variables.vmCurrency.addItem(data[i])
    }
    Page.Variables.vPrLineItem.invoke()
};

Page.vPrLineItemonSuccess = function(variable, data) {
    for (var i = 0; i < data.length; i++) {
        var currency = Page.Variables.vmCurrency.dataSet.filter(function(el) {
            return el.fromCurrency == data[i].pliCurrency
        })

        Page.Variables.vmListLineItem.addItem({
            "pliId": data[i].pliId,
            "prId": data[i].prId,
            "bhId": data[i].bhId,
            "ioNumber": data[i].tblTbudgetHeader.ioNumber,
            "pliIoNumber": null,
            "pliCreatedBy": data[i].pliCreatedBy,
            "pliCreatedAt": data[i].pliCreatedAt,
            "pliModifiedBy": data[i].pliModifiedBy,
            "pliModifiedAt": data[i].pliModifiedAt,
            "pliStatus": data[i].pliStatus,
            "pliDesc": data[i].pliDesc,
            "pliSpec": data[i].pliSpec,
            "pliUom": data[i].pliUom,
            "pliQty": data[i].pliQty,
            "pliCurrency": currency[0],
            "pliEta": data[i].pliEta,
            "pliCostCenter": data[i].pliCostCenterId,
            "pliCostCenterTitle": data[i].pliCostCenterTitle,
            "pliCoa": data[i].pliCoa,
            "pliAssetNumber": data[i].pliAssetNumber,
            "pliMatGroup": data[i].pliMatGroupId,
            "pliMatGroupDesc": data[i].pliMatGroupDesc,
            "pliUnitPrice": data[i].pliUnitPrice,
            "index": Page.App.generateUUID(),
            "pliAdditionalData": data[i].pliAdditionalData
        })

        Page.Variables.varAvailableBugetTmp.dataSet.dataValue = Page.Variables.varAvailableBugetTmp.dataSet.dataValue + (data[i].pliQty * parseInt(data[i].pliUnitPrice) * currency[0].kurs)
    }
    // console.log("data vmListLI", Page.Variables.vmListLineItem.dataSet)
};

Page.getBhIdList = function(data) {
    return data.map(function(item) {
        return item.bhId
    }).filter(function(e, i, a) {
        return a.indexOf(e) == i
    }).join(",")
}

Page.getAvailableBudget = function(lineItem, availableBudgetList) {
    try {
        var data = JSON.parse(atob(lineItem.pliAdditionalData))
        return Page.App.formatCurrency(data.availableBudgetLock)
    } catch (err) {
        console.log(err)
        return "0"
    }
}

// Page.getAvailableBudget = function(lineItem, availableBudgetList) {

//     var budget_data = availableBudgetList.find(function(item) {
//         return item.bhId == lineItem.bhId
//     })
//     if (!budget_data) {
//         return ""
//     }

//     var indexLineItem = Page.Variables.vmListLineItem.dataSet.map(function(item) {
//         return item.index
//     }).indexOf(lineItem.index)

//     var before_line_item = Page.Variables.vmListLineItem.dataSet.filter(function(e, i) {
//         return i < indexLineItem && e.bhId == lineItem.bhId && e.pliStatus == "active"
//     }).map(function(item) {
//         return item.pliQty * parseInt(item.pliUnitPrice) * item.pliCurrency.kurs
//     }).reduce(function(total, curr) {
//         return total + curr
//     }, 0)
//     return Page.App.formatCurrency(Math.round(budget_data.availableBudget - before_line_item))
// }

Page.getAvailableBudget2 = function(lineItem, availableBudgetList) {

    var budget_data = availableBudgetList.find(function(item) {
        return item.bhId == lineItem.bhId
    })
    if (!budget_data) {
        return ""
    }

    var indexLineItem = Page.Variables.vmListLineItem.dataSet.map(function(item) {
        return item.index
    }).indexOf(lineItem.index)

    var before_line_item = Page.Variables.vmListLineItem.dataSet.filter(function(e, i) {
        return i <= indexLineItem && e.bhId == lineItem.bhId && e.pliStatus == "active"
    }).map(function(item) {
        return item.pliQty * parseInt(item.pliUnitPrice) * item.pliCurrency.kurs
    }).reduce(function(total, curr) {
        return total + curr
    }, 0)

    return Page.App.formatCurrency(Math.round((budget_data.availableBudget + Page.Variables.varAvailableBugetTmp.dataSet.dataValue) - before_line_item))
}

Page.selectBPNChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.textprName.datavalue = newVal.pbProjectName
};

Page.buttonAddAttachmentClick = function($event, widget) {
    $(Page.Widgets.attachmentPR.nativeElement).find("input").click();
};

Page.FileServiceUploadFileonSuccess = function(variable, data) {
    // console.log(data)
    Page.Variables.vmAttachmentList.addItem({
        "fileName": data[0].fileName,
        "path": data[0].inlinePath,
        "size": data[0].length
    })
    // console.log(Page.Variables.vmAttachmentList.dataSet)
};
Page.pictDeleteAttachmentClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.vmAttachmentList.setData(Page.Variables.vmAttachmentList.dataSet.filter(function(el) {
        return el.fileName != item.fileName
    }))
};

Page.renderPDFPage = function(pdf, pageNumber) {
    return pdf.getPage(pageNumber).then(function(page) {
        var scale = 1;
        var viewport = page.getViewport({
            scale: scale
        });

        // Prepare canvas using PDF page dimensions
        var canvas = document.createElement("canvas")
        canvas.width = "50%"
        canvas.style.marginBottom = "24px"
        var context = canvas.getContext('2d');
        canvas.height = viewport.height;
        canvas.width = viewport.width;

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
        console.log(err)
        if (pageNumber >= pdf.numPages) {
            return Promise.resolve()
        }
        return Page.renderPDFPage(pdf, pageNumber + 1)
    })
}

Page.pictViewAttachmentClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.varShowPdf.dataSet.dataValue = true
    // document.getElementById("pdf-canvas-container").innerHTML = ""
    // // var url = Page.Variables.vmAdditionalModel.dataSet.pathFile;
    var url = Page.Widgets.listAttachment.selecteditem.path

    // // Loaded via <script> tag, create shortcut to access PDF.js exports.
    // var pdfjsLib = window['pdfjs-dist/build/pdf'];
    // // The workerSrc property shall be specified.
    // pdfjsLib.GlobalWorkerOptions.workerSrc = '//cdnjs.cloudflare.com/ajax/libs/pdf.js/2.6.347/pdf.worker.min.js';

    // var loadingTask = pdfjsLib.getDocument(url);
    // return loadingTask.promise.then(function(pdf) {
    //     // Page.Widgets.spinner1.show = true

    //     // Fetch the first page
    //     var pageNumber = 1;
    //     return Page.renderPDFPage(pdf, pageNumber)
    // }).then(function() {
    //     // Page.Widgets.spinner1.show = false
    // });

    PDFObject.embed(url, "#pdf-canvas-container")
};
Page.picture6Click = function($event, widget) {
    Page.Variables.varShowPdf.dataSet.dataValue = false
};


// LINE ITEM
Page.numberOnlyInputTrap = function($event, widget) {
    setTimeout(function() {
        widget.nativeElement.children[0].value = widget.datavalue.toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
    }, 100);
}

Page.currencyInputFormator = function($event, widget) {
    widget.datavalue = (widget.datavalue || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
    Page.numberOnlyInputTrap($event, widget)
}
Page.convertInt = function(data) {
    return parseInt(data.replace(/\D/g, ''))
}
Page.textUnitPriceChange = Page.currencyInputFormator


Page.filterListItem = function(variableLI) {
    var data = variableLI.filter(function(el) {
        return el.pliStatus === "active" || el.pliStatus === "closed"
    })
    // console.log("data", data)
    // return data
    return variableLI

}

Page.getTableNumberingLI = function(variable, data, idField) {
    return variable.dataSet.filter(function(el) {
        return el.pliStatus == "active" || el.pliStatus === "closed"
    }).map(function(item) {
        return item[idField]
    }).indexOf(data[idField]) + 1
}

Page.labelAttachmentClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.FileServiceDownloadFile.invoke({
        "inputFields": {
            "file": Page.Widgets.listAttachment.selecteditem.fileName,
            "returnName": Page.Widgets.listAttachment.selecteditem.fileName
        }
    })
};

Page.vPrHistoryonSuccess = function(variable, data) {
    Page.Variables.MODELHistory.dataSet = data
    Page.Variables.DBTaskList.invoke()
        .then(function(res) {
            if (res.data.length > 0) {
                Page.Variables.MODELHistory.dataSet.push({
                    prhActionByName: res.data[0].approvalName,
                    prhAction: 'Waiting Approval'
                })
            }
        })
};