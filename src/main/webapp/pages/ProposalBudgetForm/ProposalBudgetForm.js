/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

var onLoad = false;

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

    // Yearpicker format

    // authorization handle
    // department handle

    if (Page.pageParams.pbId) {
        //Start


        Page.Variables.vmLoading.dataSet.dataValue = true

        return Promise.all([
            Page.Variables.dbTblTProposalBudget.invoke(),
            Page.Variables.dbTblPbAttachment.invoke()
        ]).then(res => {
            Page.Variables.modelProposalBudget.setData(res[0].data[0])
            Page.Variables.varListFile.setData(res[1].data)
            // let data = Page.Variables.getIOBudgetHeader.dataSet.filter(item => item.bhId == res[0].data[0].pbIoId)
            Page.Variables.vmLoading.dataSet.dataValue = false

            // var a = new Yearpicker(Page.Widgets.selectYear.inputEl.nativeElement, {
            //     year: parseInt(res[0].data[0].budgetYear),
            //     onChange: function(year) {
            //         Page.Widgets.selectYear.datavalue = year

            //         if (!onLoad) {
            //             onLoad = true
            //             return
            //         }
            //     }
            // })

            return Promise.resolve()
        })

        //End

    } else {
        var a = new Yearpicker(Page.Widgets.textYear.inputEl.nativeElement, {
            onChange: function(year) {
                Page.Widgets.textYear.datavalue = year
            }
        })
    }
};



Page.FileServiceUploadFileonSuccess = function(variable, data) {
    // Page.Variables.vmReclassModel.setValue("bdRcAttachment", data[0].fileName)
    // Page.Variables.vmReclassModel.setValue("pathFile", data[0].inlinePath)
    Page.Variables.varListFile.addItem({
        "pbAtId": 0,
        "attachment": data[0].fileName,
        "pathFile": data[0].inlinePath,
        "status": "new"
    })

    // Page.Variables.varListFile.addItem({
    //     pbAtId: 0,
    //     name: data[0].fileName,
    //     status: "new"
    // })
};
// view files
Page.picture7Click = function($event, widget, item, currentItemWidgets) {
    // document.getElementById("pdf-canvas-container").innerHTML = ""


    // Page.Variables.FileServiceDownloadFile.invoke({
    //     "inputFields": {
    //         "file": Page.Widgets.list2.selecteditem.attachment,
    //         "returnName": Page.Widgets.list2.selecteditem.attachment
    //     }
    // })



    Page.Variables.vmShowPdf.dataSet.dataValue = true
    var filePath = Page.Widgets.list2.selecteditem.pathFile
    // // Loaded via <script> tag, create shortcut to access PDF.js exports.
    // var pdfjsLib = window['pdfjs-dist/build/pdf'];
    // // The workerSrc property shall be specified.
    // pdfjsLib.GlobalWorkerOptions.workerSrc = '//cdnjs.cloudflare.com/ajax/libs/pdf.js/2.6.347/pdf.worker.min.js';

    // var loadingTask = pdfjsLib.getDocument(filePath);
    // return loadingTask.promise.then(function(pdf) {
    //     Page.Variables.vmLoading.dataSet.dataValue = true

    //     var pageNumber = 1;
    //     return Page.renderPDFPage(pdf, pageNumber)
    // }).then(function() {
    //     Page.Variables.vmLoading.dataSet.dataValue = false
    // });

    PDFObject.embed(filePath, "#pdf-canvas-container")
};
// delete files
Page.picture5Click = function($event, widget, item, currentItemWidgets) {
    var data = Page.Widgets.list2.selecteditem

    // delete old file
    if (data.raId) {
        Promise.resolve().then(() => {
            return Page.Variables.varListFileDelete.addItem(data)
        }).then(() => {
            Page.Variables.varListFile.removeItem(data)
        })
    }
    // delete new file
    else {
        Page.Variables.FileServiceDeleteFile.invoke({
            "inputFields": {
                "file": data.attachment
            }
        }).then(() => {
            Page.Variables.varListFile.removeItem(data)
        })
    }
};
// download file
Page.label26_2Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.FileServiceDownloadFile.invoke({
        "inputFields": {
            "file": Page.Widgets.list2.selecteditem.attachment,
            "returnName": Page.Widgets.list2.selecteditem.attachment
        }
    })
};

Page.mapDeleteAttachment = function(data) {

    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()
    return Promise.resolve().then(() => {
        return Page.Variables.dbTblPbAttachment.deleteRecord({
            row: {
                "pbAtId": dt.pbAtId
            }
        })
    }).then(() => {
        return Page.Variables.FileServiceDeleteFile.invoke({
            "inputFields": {
                "file": dt.attachment
            }
        })
    }).then(function(res) {
        return Page.mapDeleteAttachment(data)
    }).catch(function(err) {

        return Page.mapDeleteAttachment(data)
    })
}

Page.mapCreateAttachment = function(data, id) {

    if (!data || !data.length) {
        return Promise.resolve()
    }
    var dt = data.pop()

    return Promise.resolve().then(() => {
        return Page.Variables.dbTblPbAttachment.createRecord({
            row: {
                "pbId": id,
                "attachment": dt.attachment,
                "pathFile": dt.pathFile

            }
        })
    }).then(function(res) {
        return Page.mapCreateAttachment(data, id)
    }).catch(function(err) {

        return Page.mapCreateAttachment(data, id)
    })
}


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
}

Page.picture4Click = function($event, widget) {
    Page.Variables.vmShowPdf.dataSet.dataValue = false
    document.getElementById("pdf-canvas-container").innerHTML = ""
};


Page.qGetBODbyDepartementIdonSuccess = function(variable, data) {


    //     Page.Variables.vmBOD.setData(data[0])
    //     console.log(Page.Variables.vmBOD.dataSet)
    // } else {
    //     Page.Variables.vmBOD.clearData()
    // }
};

Page.queryGetIOListBHonSuccess = function(variable, data) {

};

//Baru Brad
Page.textAmountChange = function($event, widget, newVal, oldVal) {
    Page.textAmountChange = function($event, widget) {
        Page.Widgets.textAmount.datavalue = App.formatCurrency(Page.Widgets.textAmount.datavalue.replace(/\D/g, ''));
    };
};

Page.search2Select = function($event, widget, selectedValue) {
    Page.Variables.modelProposalBudget.setValue("pbIoId", selectedValue.bhId)
    Page.Variables.vdbBudgetDetail.listRecords({
        "filterFields": {
            "bhId": {
                "value": selectedValue.bhId
            }
        }
    }).then(res => {
        if (!Page.Widgets.search2.datavalue) {
            return
        }

        Page.Variables.modelProposalBudget.setValue("pbAvailableBudget", Page.Variables.vdbBudgetDetail.dataSet[0].bdAfterAdjustment)
        return Promise.resolve()
    })
};
Page.button6Click = function($event, widget) {
    $(Page.Widgets.fileupload1.nativeElement).find("input").click();
};
Page.fileupload1Select = function($event, widget, selectedFiles) {
    var pattern = /[~!@#$%^&*,?"'`:{}|<>+]/g;
    if (pattern.test(selectedFiles[0].name)) {
        // message
        App.Actions.appNotification.setMessage("<b>Warning</b><br>Sorry, file name can't contain special characters. Please delete special characters to upload attachment.<br>Thank you.")
        // type: success, info, warning, danger
        App.Actions.appNotification.setToasterClass("warning")
        // delayed
        App.Actions.appNotification.setToasterDuration(5000)
        // invoke
        App.Actions.appNotification.invoke()

        return;
    }

    Page.Variables.FileServiceUploadFile.invoke()
};
Page.buttonCloseClick = function($event, widget) {
    App.Actions.goToPage_ProposalBudget.invoke()
};

Page.dbTblPbAttachmentonSuccess = function(variable, data) {
    Page.Variables.varListFile.clearData()
    if (typeof data === "object") {
        if (data.length > 0) {
            data.forEach(function(dt) {
                Page.Variables.varListFile.addItem({
                    "pbAtId": dt.pbAtId,
                    "name": dt.attachment,
                    "status": "old"
                })
            })
        }
    }
};

Page.fieldMandatoryCheck = function(fields = []) {
    var isValid = true

    for (var i = 0; i < fields.length; i++) {
        if (!Page.Variables.modelProposalBudget.getValue(fields[i])) {
            isValid = false
            Page.Variables.vmErrorMessage.setValue(fields[i], "this field is required")
        }
    }

    return isValid
};
Page.buttonSaveClick = function($event, widget) {


    Page.Variables.vmErrorMessage.clearData()

    Page.Variables.varLoading.dataSet.loadingSubmit = true
    var data = Page.Variables.modelProposalBudget.dataSet
    data.pbCreatedAt = new Date().toISOString()
    data.pbCreatedBy = App.Variables.loggedInUser.dataSet.name
    data.pbAmount = data.pbAmount.replace(/\./g, '')



    // field mandatory validation
    if (!Page.fieldMandatoryCheck([
            "pbYear",
            "pbStartDate",
            "pbEndDate",
            "pbProjectName",
            "pbAmount",
            "pbIoNumber",
        ])) {
        Page.Variables.varLoading.dataSet.loadingSubmit = true
        return
    }




    Page.Variables.dbTblTProposalBudget.createRecord({
        row: data
    }).then(res => {
        var documentId = "8000-" + res.pbId

        if (res.pbId < 10) {
            documentId = "8000-0" + res.pbId
        }

        res.pbDocumentNumber = documentId
        Page.Variables.modelProposalBudget.dataSet = res

        return Page.Variables.dbTblTProposalBudget.updateRecord({
            row: res
        }).catch(error => {

            Variables.varLoading.dataSet.loadingSubmit = false;
        })
    }).then(() => {
        return Page.mapDeleteAttachment(Page.Variables.varListFileDelete.dataSet)
    }).then(() => {
        var data = Page.Variables.varListFile.dataSet.filter(x => x.status === "new")

        return Page.mapCreateAttachment(data, Page.Variables.modelProposalBudget.dataSet.pbId)
    }).then(() => {
        // message
        App.Actions.appNotification.setMessage("Data Saved")
        // type: success, info, warning, danger
        App.Actions.appNotification.setToasterClass("success")
        // delayed
        App.Actions.appNotification.setToasterDuration(5000)
        // invoke
        App.Actions.appNotification.invoke()

        Page.Variables.varLoading.dataSet.loadingSubmit = false
        App.Actions.goToPage_ProposalBudget.invoke()
        // Page.Variables.dbTblTProposalBudget.update();
        // Page.Widgets.dialogItem.close()
    }).catch(error => {

    })

};

Page.dbTblTProposalBudgetonSuccess = function(variable, data) {
    setTimeout(function() {
        Page.Variables.getIOBudgetHeader.invoke({
            "inputFields": {
                "ioNumber": data.length ? data[0].pbIoNumber : null,
                "ioName": data.length ? data[0].pbIoNumber : null

            }
        }).then(res => {
            result = JSON.parse(res.body)
            // Page.Variables.MODELIO.setData(result.content[0])
            // Page.Variables.MODELIO.dataSet = result.content
            Page.Widgets.search2.datavalue = result.content ? result.content : null
        })
    }, 2000);

};