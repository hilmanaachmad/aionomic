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

    Page.Variables.varPrMaterial.dataSet = []
};

Page.button11Click = function($event, widget) {
    var ioNumberTmp = Page.Variables.varPrMaterial.dataSet.filter(x => x.ioNumber === Page.Widgets.search12.datavalue.ioNumber)
    if (ioNumberTmp.length === 0) {
        if (Page.Widgets.text5.datavalue > Page.Widgets.text10.datavalue) {
            App.Actions.appNotification.setMessage("insufficient cost")
            App.Actions.appNotification.setToasterClass("error")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
        } else {
            Page.Variables.varPrMaterial.addItem({
                "materialGroup": Page.Widgets.search7.datavalue,
                "description": Page.Widgets.textarea2.datavalue,
                "spesification": Page.Widgets.text7.datavalue,
                "uom": Page.Widgets.search10.datavalue,
                "quantity": Page.Widgets.text8.datavalue,
                "currency": "",
                "unitPrice": Page.Widgets.text9.datavalue,
                "totaAmount": Page.Widgets.text8.datavalue * Page.Widgets.text9.datavalue,
                "eta": Page.Widgets.date2.datavalue,
                "ioNumber": Page.Widgets.search12.datavalue.ioNumber,
                "bhAmount": Page.Widgets.text10.datavalue,
                "costCenter": Page.Widgets.search14.datavalue,
                "glNumber": Page.Widgets.search15.datavalue,
                "assetNo": Page.Widgets.text11.datavalue
            })
            Page.Widgets.dialogItem.close()
        }
    } else {
        var totaAmountTmp = 0

        Promise.resolve().then(() => {
            ioNumberTmp.forEach(tmp => {
                totaAmountTmp += tmp.totaAmount
            })
            totaAmountTmp = Page.Widgets.search12.datavalue.bhAmount - totaAmountTmp
            return totaAmountTmp
        }).then((data) => {
            if (Page.Widgets.text5.datavalue > Page.Widgets.text10.datavalue) {
                App.Actions.appNotification.setMessage("insufficient cost")
                App.Actions.appNotification.setToasterClass("error")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
            } else {
                Page.Variables.varPrMaterial.addItem({
                    "materialGroup": Page.Widgets.search7.datavalue,
                    "description": Page.Widgets.textarea2.datavalue,
                    "spesification": Page.Widgets.text7.datavalue,
                    "uom": Page.Widgets.search10.datavalue,
                    "quantity": Page.Widgets.text8.datavalue,
                    "currency": "",
                    "unitPrice": Page.Widgets.text9.datavalue,
                    "totaAmount": Page.Widgets.text8.datavalue * Page.Widgets.text9.datavalue,
                    "eta": Page.Widgets.date2.datavalue,
                    "ioNumber": Page.Widgets.search12.datavalue.ioNumber,
                    "bhAmount": data,
                    "costCenter": Page.Widgets.search14.datavalue,
                    "glNumber": Page.Widgets.search15.datavalue,
                    "assetNo": Page.Widgets.text11.datavalue
                })
                Page.Widgets.dialogItem.close()
            }
        })
    }
};

Page.search12Select = function($event, widget, selectedValue) {
    var ioNumberTmp = Page.Variables.varPrMaterial.dataSet.filter(x => x.ioNumber === Page.Widgets.search12.datavalue.ioNumber)
    if (ioNumberTmp.length === 0) {
        Page.Widgets.text10.datavalue = Page.Widgets.search12.datavalue.bhAmount
    } else {
        var totaAmountTmp = 0
        Promise.resolve().then(() => {
            ioNumberTmp.forEach(tmp => {
                totaAmountTmp += tmp.totaAmount
            })
            totaAmountTmp = Page.Widgets.search12.datavalue.bhAmount - totaAmountTmp
            return totaAmountTmp
        }).then((data) => {
            Page.Widgets.text10.datavalue = data
        })
    }
};
