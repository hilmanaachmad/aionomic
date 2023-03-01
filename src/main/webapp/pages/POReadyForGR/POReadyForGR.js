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

    // Page.$page[0].addEventListener("click", function() {
    //     $('.aio-ptp-dropdown:not(.clicked)').removeClass("active")
    //     $('.aio-ptp-dropdown').removeClass("clicked")
    // })

    setTimeout(() => {
        $('.yearpicker').yearpicker()
        $('.yearpicker').change(function() {
            year_value = $(this).val()
            Page.setYear(year_value)
        })
        $('.yearpicker').val(new Date().getFullYear())
        Page.Widgets.year.datavalue = new Date().getFullYear()



        $('.flatpickr_create_date').daterangepicker({
            opens: 'left'
        }, function(start, end, label) {
            Page.Variables.MODELVariable.setValue('create_date_start', start.format('YYYY-MM-DD'))
            Page.Variables.MODELVariable.setValue('create_date_end', end.format('YYYY-MM-DD'))
            Page.Widgets.create_date.datavalue = start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD')
        });

        $('.flatpickr_delivery_date').daterangepicker({
            opens: 'left'
        }, function(start, end, label) {
            Page.Variables.MODELVariable.setValue('delivery_date_start', start.format('YYYY-MM-DD'))
            Page.Variables.MODELVariable.setValue('delivery_date_end', end.format('YYYY-MM-DD'))
            Page.Widgets.delivery_date.datavalue = start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD')
        });

        $('.flatpickr_create_date').on('cancel.daterangepicker', function(ev, picker) {
            $(this).val('');
            Page.Widgets.create_date.datavalue = ''
            Page.Variables.MODELVariable.setValue('create_date_start', null)
            Page.Variables.MODELVariable.setValue('create_date_end', null)
        });
        $('.flatpickr_delivery_date').on('cancel.daterangepicker', function(ev, picker) {
            $(this).val('');
            Page.Widgets.delivery_date.datavalue = ''
            Page.Variables.MODELVariable.setValue('delivery_date_start', null)
            Page.Variables.MODELVariable.setValue('delivery_date_end', null)
        });

        $('.flatpickr_create_date').val('');
        $('.flatpickr_delivery_date').val('');

    }, 1000);

};


// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.DBPOReadyToGR.orderBy == field + " ASC") {
        Page.Variables.DBPOReadyToGR.orderBy = field + " DESC"
    } else if (Page.Variables.DBPOReadyToGR.orderBy == field + " DESC") {
        Page.Variables.DBPOReadyToGR.orderBy = ""
    } else {
        Page.Variables.DBPOReadyToGR.orderBy = field + " ASC"
    }
    Page.Variables.DBPOReadyToGR.invoke()
}
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('poNumber')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('releaseDate')
};
Page.container111Click = function($event, widget) {
    Page.toggleTableSort('currency')
};
Page.container41_1Click = function($event, widget) {
    Page.toggleTableSort('deliveryDate')
};
Page.container45Click = function($event, widget) {
    Page.toggleTableSort('status')
};
Page.container46Click = function($event, widget) {
    Page.toggleTableSort('shipping')
};
Page.container47Click = function($event, widget) {
    Page.toggleTableSort('poItem')
};
Page.container48Click = function($event, widget) {
    Page.toggleTableSort('deliveryItem')
};
Page.container51Click = function($event, widget) {
    Page.toggleTableSort('confirmItem')
};
// sort function

//Show row table 
Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.DBPOReadyToGR.maxResults = newVal
    Page.Variables.DBPOReadyToGR.update()
};



//Filter
Page.buttonApplyClick = function($event, widget) {
    console.log(Page.Widgets.year.datavalue)
    Page.Variables.DBPOReadyToGR.invoke()
};

Page.buttonClearClick = function($event, widget) {
    Page.Widgets.create_date.datavalue = null
    Page.Widgets.delivery_date.datavalue = null
    Page.Variables.MODELVariable.setValue('create_date_start', null)
    Page.Variables.MODELVariable.setValue('create_date_end', null)
    Page.Variables.MODELVariable.setValue('delivery_date_start', null)
    Page.Variables.MODELVariable.setValue('delivery_date_end', null)
    Page.Widgets.company.datavalue = null
    Page.Widgets.department.datavalue = null
    Page.Widgets.po_status.datavalue = null
    Page.Widgets.item.datavalue = null
    Page.Widgets.po_number.datavalue = null
    Page.Variables.DBPOReadyToGR.invoke()
};



Page.buttonGrClick = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_GRForm.invoke({
        data: {
            'id': Page.Widgets.list2.selecteditem.id
        }
    })
};

Page.buttonAssignClick = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_GRFormAssign.invoke({
        data: {
            'id': Page.Widgets.list2.selecteditem.id
        }
    })
};
Page.button6Click = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_GRForm.invoke({
        data: {
            'id': Page.Widgets.list2.selecteditem.id
        }
    })
};
Page.buttonConfirmClick = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_GRFormConfirm.invoke({
        data: {
            'id': Page.Widgets.list2.selecteditem.id,
            'assign': Page.Widgets.list2.selecteditem.id,
        }
    })
};


Page.DBUserRoleonSuccess = function(variable, data) {
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
Page.buttonBypassClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.MODELVariable.setValue("selectedItem", item)
};
Page.dialogBypassOk = function($event, widget) {
    Page.Widgets.spinner.show = true
    Page.Widgets.dialogBypass.close()
    var DBPOReadyToBypass = Page.Variables.DBPOReadyToBypass
    DBPOReadyToBypass.invoke({
        "filterFields": {
            "idPo": {
                "value": Page.Variables.MODELVariable.dataSet.selectedItem.id
            }
        }
    }).then(async(data) => {
        try {
            await Page.BypassVerification(data.data)
            await Page.Variables.DBPOReadyToGR.invoke()
            await Page.Variables.api_PONotifDelivery.invoke({
                inputFields: {
                    RequestBody: {
                        id_po: Page.Variables.MODELVariable.dataSet.selectedItem.id
                    }
                }
            });
            Page.Widgets.spinner.show = false
            App.Actions.appNotification.setMessage('Bypassing Verification Successfully')
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
        } catch (e) {
            Page.Widgets.spinner.show = false
            App.Actions.appNotification.setMessage('Bypassing Verification Error')
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
        }
    })
};

Page.BypassVerification = function(data) {
    return new Promise(async(resolve, reject) => {
        for (var i = 0; i < data.length; i++) {
            if (data[i].poItem !== data[i].deliveryItem) {
                var deliveryItem = data[i].deliveryItem ? data[i].deliveryItem : 0
                var DBPOLineItem = Page.Variables.DBPOLineItem
                await DBPOLineItem.invoke({
                    "filterFields": {
                        "id": {
                            "value": data[i].idPoLineItem
                        }
                    }
                })

                await Page.Variables.DBGrLineItem.createRecord({
                    row: {
                        idPoHeader: DBPOLineItem.dataSet[0].idPoHeader,
                        idPoLineItem: data[i].idPoLineItem,
                        idPrHeader: DBPOLineItem.dataSet[0].idPrHeader,
                        idPrLineItemHeader: DBPOLineItem.dataSet[0].idPrLineItemHeader,
                        item: DBPOLineItem.dataSet[0].item,
                        poQty: DBPOLineItem.dataSet[0].poQty,
                        uom: DBPOLineItem.dataSet[0].uom,
                        unitPrice: DBPOLineItem.dataSet[0].unitPrice,
                        itemAmountAfterDiscount: DBPOLineItem.dataSet[0].itemAmountAfterDiscount,
                        itemTotalAmount: DBPOLineItem.dataSet[0].itemTotalAmount,
                        matlGroup: DBPOLineItem.dataSet[0].matlGroup,
                        matlGroupDesc: DBPOLineItem.dataSet[0].matlGroupDesc,
                        plant: DBPOLineItem.dataSet[0].plant,
                        eta: DBPOLineItem.dataSet[0].eta,
                        ioNumber: DBPOLineItem.dataSet[0].ioNumber,
                        costCenter: DBPOLineItem.dataSet[0].costCenter,
                        coa: DBPOLineItem.dataSet[0].coa,
                        assetsNo: DBPOLineItem.dataSet[0].assetsNo,
                        prNo: DBPOLineItem.dataSet[0].prNo,
                        prNoItem: DBPOLineItem.dataSet[0].prNoItem,
                        sapPrNo: DBPOLineItem.dataSet[0].sapPrNo,
                        discount: DBPOLineItem.dataSet[0].discount,
                        deliveryQty: DBPOLineItem.dataSet[0].poQty - deliveryItem,
                        deliveryDate: App.HariIni(),
                        status: 'DELIVERY',
                        comment: 'Bypassing Verification',
                        deliveryUom: DBPOLineItem.dataSet[0].uom
                    }
                })
            }

            if (i === data.length - 1) {
                if (data[i].statusPoHeader !== 'Accepted') {
                    await Page.Variables.DBUpdatePO.invoke({
                        "inputFields": {
                            "status": 'Accepted',
                            "id": data[i].idPo
                        }
                    })
                }
                resolve(true)
            }
        }

    })
}

Page.setYear = function(year) {
    Page.Variables.mYear.dataSet = year
}
Page.yearChange = function($event, widget, newVal, oldVal) {
    Page.Variables.mYear.dataSet = newVal;
};