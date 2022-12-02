/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */



/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    if (Page.Widgets.spinner) {
        Page.Widgets.spinner.show = true
    }

    if (!Page.pageParams.id) {
        App.Actions.appNotification.setMessage('Data not found')
        App.Actions.appNotification.setToasterClass("warning")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.Widgets.spinner.show = false
        App.Actions.goToPage_POReadyForGR.invoke()
    }
};

// Page.tableDataBeforedatarender = function(widget, $data) {
//     for (var i = 0; i < $data.length; i++) {
//         if (!$data[i].statusData) {
//             $data[i].numberItem = $data[i].id
//         }
//     }
// };

Page.DBGrLineItemonResult = function(variable, data) {
    if (Page.Widgets.spinner) {
        Page.Widgets.spinner.show = false
    }
    Page.Variables.ModelDBGrLineItem.dataSet = data
    for (var i = 0; i < data.length; i++) {
        data[i].confirmQty = data[i].deliveryQty
        Page.Variables.ModelDBGrLineItemDefault.dataSet.push(data[i])
    }

};

Page.buttonCancelClick = function($event, widget) {
    window.history.back();
};


//Manipulation data on list
Page.confirmQtyKeyup = function($event, widget, item, currentItemWidgets) {
    item.confirmQty = widget.datavalue
};

Page.confirmUnitSelect = function($event, widget, selectedValue, item, currentItemWidgets) {
    item.confirmUnit = widget.datavalue
};

Page.assignSelect = function($event, widget, selectedValue, item, currentItemWidgets) {
    item.assign = widget.datavalue
};

Page.assignClear = function($event, widget, item, currentItemWidgets) {
    item.assign = widget.datavalue
};


Page.submitDataClick = function($event, widget) {
    var dataList = Page.Widgets.tableData.listItems._results
    var statusItem = true

    for (var i = 0; i < dataList.length; i++) {
        if (
            dataList[i].item.confirmQty === '' ||
            dataList[i].item.confirmUnit === '' ||
            (dataList[i].item.assign === '' || dataList[i].item.assign === undefined || dataList[i].item.assign === null)
        ) {
            statusItem = false
        }


        if (dataList[i].item.confirmQty === undefined) {
            dataList[i].item.confirmQty = dataList[i].item.poQty
        }
        if (dataList[i].item.confirmUnit === undefined) {
            dataList[i].item.confirmUnit = dataList[i].item.uom
        }


        //Check Total Assign
        if (!dataList[i].item.statusData) {
            var ArrayFiltered = dataList.filter(function(el) {
                return (el.item.id === dataList[i].item.id && el.item.statusData === undefined) || (el.item.numberItem === dataList[i].item.id && el.item.statusData === 'new')
            })

            var filtered = ArrayFiltered.reduce(function(prev, cur) {
                return prev + Number(cur.item.confirmQty);
            }, 0);
            if (filtered > dataList[i].item.deliveryQty) {
                App.Actions.appNotification.setMessage('The total assignment is not equal to the delivery quantity')
                App.Actions.appNotification.setToasterClass("error")
                App.Actions.appNotification.setToasterDuration(5000)
                App.Actions.appNotification.invoke()
                statusItem = false
                break;
            }

        }



        if (statusItem === false) {
            App.Actions.appNotification.setMessage('Please complete the list item')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
            break;
        } else {
            if (i === dataList.length - 1) {
                Page.Widgets.modalConfirm.open()
            }
        }


    }

};


Page.btnProceedClick = async function($event, widget) {
    var dataList = Page.Widgets.tableData.listItems._results
    Page.Widgets.modalConfirm.close()
    Page.Widgets.spinner.show = true

    var InsertGrLineConfirm = await Page.InsertGrLineConfirm(dataList)
    if (InsertGrLineConfirm === true) {
        App.Actions.appNotification.setMessage('Successfully')
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.setToasterDuration(5000)
        App.Actions.appNotification.invoke()
        App.Actions.goToPage_POReadyForGR.invoke()
    }

};


Page.InsertGrLineConfirm = async function(dataList) {
    return new Promise(async(resolve, reject) => {
        for (var i = 0; i < dataList.length; i++) {
            var checkData = await Page.Variables.DBInsertGrConfirm.invoke({
                "filterFields": {
                    idGr: {
                        "value": dataList[i].item.id
                    },
                }
            })

            if (checkData.data.length > 0) {
                await Page.Variables.DBInsertGrConfirm.deleteRecord({
                    "row": {
                        id: checkData.data[0].id
                    }
                })
            }

            await Page.Variables.DBInsertGrConfirm.createRecord({
                row: {
                    assetsNo: dataList[i].item.assetsNo,
                    coa: dataList[i].item.coa,
                    condition: dataList[i].item.condition,
                    confirmQty: dataList[i].item.confirmQty,
                    confirmUnit: dataList[i].item.confirmUnit,
                    costCenter: dataList[i].item.costCenter,
                    deliveryDate: dataList[i].item.deliveryDate,
                    deliveryQty: dataList[i].item.deliveryQty,
                    discount: dataList[i].item.discount,
                    eta: dataList[i].item.eta,
                    idPoHeader: dataList[i].item.idPoHeader,
                    idGr: dataList[i].item.id,
                    idPrHeader: dataList[i].item.idPrHeader,
                    idPrLineItemHeader: dataList[i].item.idPrLineItemHeader,
                    ioNumber: dataList[i].item.ioNumber,
                    item: dataList[i].item.item,
                    itemTotalAmount: dataList[i].item.itemTotalAmount,
                    matlGroup: dataList[i].item.matlGroup,
                    matlGroupDesc: dataList[i].item.matlGroupDesc,
                    plant: dataList[i].item.plant,
                    poQty: dataList[i].item.poQty,
                    prNo: dataList[i].item.prNo,
                    prNoItem: dataList[i].item.prNoItem,
                    receivedDate: dataList[i].item.receivedDate,
                    remarks: dataList[i].item.remarks,
                    sapPrNo: dataList[i].item.sapPrNo,
                    status: 'CONFIRM',
                    unitPrice: dataList[i].item.unitPrice,
                    uom: dataList[i].item.uom,
                    files: dataList[i].item.files,
                    comment: Page.Widgets.comment.datavalue,
                    assign: 'emp::' + dataList[i].item.assign,
                    deliveryNumber: dataList[i].item.deliveryNumber
                }
            })

            console.log(dataList[i].item.id)
            if (i === dataList.length - 1) {
                console.log('close looping add GR Confirm')
                resolve(true)
            }
        }
    })
}
Page.addDataClick = function($event, widget, item, currentItemWidgets) {
    //Create New Row
    var array = Page.Widgets.tableData.listItems._results
    var max = Math.max.apply(Math, array.map(function(o) {
        return o.item.id;
    }))
    var new_data = Page.Widgets.tableData.selecteditem
    new_data.id = max + 1
    new_data.statusData = 'new'
    new_data.numberItem = item.id
    new_data.assign = null

    Page.Variables.ModelDBGrLineItem.dataSet.push(new_data)
};

Page.deleteDataClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.ModelDBGrLineItem.dataSet = Page.Variables.ModelDBGrLineItem.dataSet.filter(el => el.id !== item.id)
};

Page.getCalculateTotalAsign = function(current) {
    var filtered = Page.Widgets.tableData.listItems._results.filter(function(el) {
        return (el.item.id === current.id && el.item.statusData === undefined) || (el.item.numberItem === current.id && el.item.statusData === 'new')
    })

    return filtered.reduce(function(prev, cur) {
        return prev + Number(cur.item.confirmQty);
    }, 0);
}