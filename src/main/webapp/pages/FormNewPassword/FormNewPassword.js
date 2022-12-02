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
    let vendorCode = window.location.href.split("=")[1]
    vendorCode = decodeURIComponent(vendorCode);
    Page.pageParams.vendorCode = atob(vendorCode);
};

Page.picture1Click = function($event, widget, item, currentItemWidgets) {};

Page.btnApproveClick = function($event, widget) {
    Page.Variables.svUpdateStatus.invoke({
        inputFields: {
            "id": Page.Widgets.list1.selecteditem.abId,
            "status": "Approved"
        }
    });
};

Page.btnRejectClick = function($event, widget) {
    Page.Variables.svUpdateStatus.invoke({
        inputFields: {
            "id": Page.Widgets.list1.selecteditem.abId,
            "status": "Rejected"
        }
    });
};

Page.matchPassword = function($event, $operation, $data, options) {
    if (Page.Widgets.password.datavalue != Page.Widgets.retypePassword.datavalue) {
        Page.Actions.naPasswordNotMatch.invoke();
    }
};

Page.retypePasswordChange = function($event, widget, newVal, oldVal) {
    Page.matchPassword();
    Page.Widgets.passEncrypt.caption = Page.Widgets.retypePassword.datavalue;
    Page.Widgets.passEncrypt.caption = CryptoJS.MD5(newVal).toString();
};

Page.button1Click = function($event, widget) {
    console.log(Page.pageParams.vendorCode)
};

Page.btn_checkPasswordClick = function($event, widget) {
    Page.Widgets.password.type = (Page.Widgets.password.type == 'password' ? 'text' : 'password')
    Page.Widgets.btn_checkPassword.iconclass = (Page.Widgets.btn_checkPassword.iconclass == 'fa fa-eye' ? 'fa fa-eye-slash' : 'fa fa-eye')
};
Page.btn_checkRePasswordClick = function($event, widget) {
    Page.Widgets.retypePassword.type = (Page.Widgets.retypePassword.type == 'password' ? 'text' : 'password')
    Page.Widgets.btn_checkRePassword.iconclass = (Page.Widgets.btn_checkRePassword.iconclass == 'fa fa-eye' ? 'fa fa-eye-slash' : 'fa fa-eye')
};
