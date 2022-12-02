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

// submit form
Page.button1Click = function($event, widget) {
    Page.Variables.vmErrorMessage.clearData()

    var isValid = true
    var auth = JSON.parse(JSON.stringify(Page.Variables.vdbAuthorization.dataSet)).filter(function(item) {
        return item.authModule == 'BUD'
    })

    var data = Page.Variables.vmDelegationModel.dataSet
    data.delToUserId = "emp::" + data.delToUserId
    data.delCreatedAt = new Date().toISOString()

    if (!data.delToUserName) {
        Page.Variables.vmErrorMessage.setValue("employeeName", "this field is required")
        isValid = false
    }
    if (data.delReason == "") {
        Page.Variables.vmErrorMessage.setValue("reason", "this field is required")
        isValid = false
    }
    if (data.delAvailDatetime == "" || data.delExpDatetime == "") {
        Page.Variables.vmErrorMessage.setValue("date", "this field is required")
        isValid = false
    }
    if (!isValid) {
        return
    }

    console.log(data, auth)
    return
    // record user map history
    Page.Variables.vdbDelegation.createRecord({
        row: data
    }).then(res => {
        Page.insertRelDelAuth(auth.reverse(), res.delId)
    }).then(res => {
        // message
        App.Actions.appNotification.setMessage("Data Saved")
        // type: success, info, warning, danger
        App.Actions.appNotification.setToasterClass("success")
        // delayed
        App.Actions.appNotification.getToasterDuration(5000)
        // invoke
        App.Actions.appNotification.invoke()

        Page.Variables.vmDelegationModel.clearData()
    })
};

Page.insertRelDelAuth = function(data, delId) {
    if (!data || !data.length) {
        return Promise.resolve()
    }

    // loop till data empty
    var dt = data.pop()
    return Page.Variables.vdbDelAuth.createRecord({
        row: {
            "delId": delId,
            "authId": dt.authId
        }
    }).then(function(res) {
        return Page.insertRelDelAuth(data, delId)
    }).catch(function(err) {
        console.log(err)
        return Page.insertRelDelAuth(data, delId)
    })
}

// select employee
Page.searchEmployeeSelect = function($event, widget, selectedValue) {
    // if (selectedValue != undefined) {
    //     Page.Widgets.label7_1.show = false;
    // }
    Page.Variables.vmErrorMessage.setValue("employeeName", "")
    Page.Variables.vmDelegationModel.setValue('delToUserId', selectedValue.lgNik)
    Page.Variables.vmDelegationModel.setValue('delToUserName', selectedValue.lgName)

    console.log("dataset", Page.Variables.vmDelegationModel.dataSet)
};
Page.dateFromChange = function($event, widget, newVal, oldVal) {
    // Page.Widgets.label8.show = false;
    Page.Variables.vmErrorMessage.setValue("date", "")
};
Page.dateToChange = function($event, widget, newVal, oldVal) {
    Page.Variables.vmErrorMessage.setValue("date", "")
    // Page.Widgets.label9.show = false;
};
Page.textarea2Keyup = function($event, widget) {
    // Page.Widgets.label10.show = false;
    Page.Variables.vmErrorMessage.setValue("reason", "")
};