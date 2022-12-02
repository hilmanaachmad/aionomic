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

Page.button1Click = function($event, widget) {
    var email = Page.Widgets.text1.datavalue
    var emailRegex = /\S+@\S+\.\S+/

    if (!email) {
        Page.Variables.modelErrors.setValue("email", "this field is required")
        return
    } else if (!emailRegex.test(email)) {
        Page.Variables.modelErrors.setValue("email", "Email format is not match")
        return
    }
    Page.Widgets.spinner1.show = true

    // redirect to public Quotation Form
    Promise.resolve().then(function() {
        return Page.Variables.dbGetRfqVendor.invoke()
    }).then(function(res) {
        var data = JSON.parse(res.body)
        Page.Widgets.spinner1.show = false
        console.log(Page.pageParams.rfqvId)
        console.log(data.hasOwnProperty('rfqvVendorEmail'))

        if (!Page.pageParams.rfqvId || !data.hasOwnProperty('rfqvVendorEmail')) {
            Page.Variables.modelErrors.setValue("email", "Access denied, please check your email")
            return
        } else if (data.rfqvVendorEmail != email) {
            Page.Variables.modelErrors.setValue("email", "Email is not registered")
            return
        }
        Page.Actions.goto_QuotaionFormVendor.invoke()
    })
};

Page.text1Change = function($event, widget, newVal, oldVal) {
    Page.Variables.modelErrors.clearData()
};