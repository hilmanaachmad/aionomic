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

Page.button2Click = function($event, widget) {
    if (Page.Widgets.checkbox1.datavalue) {
        if (Page.Widgets.select1.datavalue) {
            App.Actions.goToPage_RegistrationVendorLocal.invoke({
                data: {
                    vendorType: Page.Widgets.select1.datavalue
                }
            });
        } else {
            // warning message
        }

    } else if (Page.Widgets.checkbox2.datavalue) {
        App.Actions.goToPage_RegistrationVendorOverseas.invoke();
    }
};

Page.checkbox1Click = function($event, widget) {
    Page.Widgets.checkbox2.datavalue = false;
};

Page.checkbox2Click = function($event, widget) {
    Page.Widgets.checkbox1.datavalue = false;
};