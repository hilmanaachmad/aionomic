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

Partial.container3Click = function($event, widget) {
    if (Partial.pageParams.page.indexOf('additional') !== -1) {
        App.Actions.goToPage_AdditionalBudgetPage.invoke()
    } else if (Partial.pageParams.page.indexOf('reclass') !== -1) {
        App.Actions.goToPage_ReclassPage.invoke()
    } else if (Partial.pageParams.page.indexOf('approval') !== -1) {
        App.Actions.goToPage_TaskListPage.invoke()
    } else if (Partial.pageParams.page.indexOf('RFQlist') !== -1) {
        App.Actions.goToPage_Main.invoke()
    } else {
        window.history.back();
        window.localStorage.removeItem("budget-session")
    }
};