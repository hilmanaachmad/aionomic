/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Partial.onReady = function() {
    /*
     * variables can be accessed through 'Partial.Variables' property here
     * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
     * Partial.Variables.loggedInUser.getData()
     *
     * widgets can be accessed through 'Partial.Widgets' property here
     * e.g. to get value of text widget named 'username' use following script
     * 'Partial.Widgets.username.datavalue'
     */
};

Partial.checkUserRole = function(roles = []) {
    var userAccess = false

    for (var i = 0; i < roles.length; i++) {
        if (App.Variables.loggedInUser.dataSet.roles.indexOf(roles[i]) >= 0) {
            userAccess = true
        }
    }

    return userAccess;
}

Partial.Roles = function() {
    return Partial.App.Variables.loggedInUser.dataSet.roles.join(",")
}
Partial.container32Click = function($event, widget) {
    window.localStorage.removeItem("budget-value-rfq-tracking")
};
Partial.container40_1Click = function($event, widget) {
    Partial.Variables.menuCondition.dataSet[0].showOrderList = !Partial.Variables.menuCondition.dataSet[0].showOrderList
};
Partial.container43Click = function($event, widget) {
    Partial.Actions.gotoVendorListWithParam.invoke({
        data: {
            'company_code': '1000',
        }
    });
};
Partial.container46Click = function($event, widget) {

    Partial.Actions.gotoVendorListWithParam.invoke({
        data: {
            'company_code': '2000',
        }
    });
};

Partial.ContainerGoodReceiptClick = function($event, widget) {
    Partial.Variables.menuGoodReceipt.dataSet.dataValue = !Partial.Variables.menuGoodReceipt.dataSet.dataValue
};
Partial.containerMenuMasterClick = function($event, widget) {
    Partial.Variables.menuMaster.dataSet.dataValue = !Partial.Variables.menuMaster.dataSet.dataValue
};
Partial.containerMenuHistoryClick = function($event, widget) {
    Partial.Variables.menuHistory.dataSet.dataValue = !Partial.Variables.menuHistory.dataSet.dataValue
};
Partial.containerMenuBudgetClick = function($event, widget) {
    Partial.Variables.menuBudget.dataSet.dataValue = !Partial.Variables.menuBudget.dataSet.dataValue
};
Partial.containerMenuPRClick = function($event, widget) {
    Partial.Variables.menuPR.dataSet.dataValue = !Partial.Variables.menuPR.dataSet.dataValue
};
Partial.containerMenuRFQClick = function($event, widget) {
    Partial.Variables.menuRFQ.dataSet.dataValue = !Partial.Variables.menuRFQ.dataSet.dataValue
};
Partial.containerMenuReadyPOClick = function($event, widget) {
    Partial.Variables.menuPO.dataSet.dataValue = !Partial.Variables.menuPO.dataSet.dataValue
};

Partial.container55Click = function($event, widget) {
    let zona = ['ZNTO', 'ZNAO', 'ZTAO', 'ZTPO']
    if (zona.includes(App.Variables.vdbVendorData.dataSet[0].groupVendor)) {
        App.Actions.goToPage_RegistrationVendorOverseasEdit.invoke()
    } else {
        App.Actions.goToPage_RegistrationVendorLocalEdit.invoke()
    }
    // if (App.Variables.vdbVendorData.dataSet[0].ab_acup == 'zntl' || App.Variables.vdbVendorData.dataSet[0].ab_acup == 'zntl') {
    //     App.Actions.goToPage_RegistrationVendorLocalEdit.invoke()
    // } else {
    //     App.Actions.goToPage_RegistrationVendorOverseasEdit.invoke()
    // }
    console.log(App.Variables.vdbVendorData.dataSet)
};
Partial.containerBillionClick = function($event, widget) {
    window.open('https://billion.aio.co.id/', '_blank').focus();
};


Partial.anchor2Click = function($event, widget) {
    let id = localStorage.getItem("unique-id");
    let token = localStorage.getItem("unique-token");
    if (id && token) {
        $('#billion-username').val(atob(localStorage.getItem("unique-id")));
        $('#billion-password').val(atob(localStorage.getItem("unique-token")));
        $('#billion-form').submit();
    } else {
        window.open('https://billion.aio.co.id/', '_blank').focus();
    }
};
Partial.laporAIOAnchorClick = function($event, widget) {
    $('#laporaio-form').submit();
};
Partial.ContainerDelegationClick = function($event, widget) {
    Partial.Variables.menuDelegation.dataSet.dataValue = !Partial.Variables.menuDelegation.dataSet.dataValue
};